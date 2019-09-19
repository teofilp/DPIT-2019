package com.runtime_terror.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.adapters.FoodListAdapter;
import com.runtime_terror.myapplication.database.FirestoreSetup;
import com.runtime_terror.myapplication.interfaces.ItemChanged;
import com.runtime_terror.myapplication.models.BillOrder;
import com.runtime_terror.myapplication.models.DrinkOrder;
import com.runtime_terror.myapplication.models.FoodOrder;
import com.runtime_terror.myapplication.models.MyApplication;
import com.runtime_terror.myapplication.models.ProductItem;
import com.runtime_terror.myapplication.models.HelpDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {

    private RecyclerView mainList;
    private RecyclerView.Adapter adapter;
    List<ProductItem> productItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        bindMainList();
        productItemList = getFoodData();
        computeOrderPrice(productItemList);
        setupAdapterAndData(productItemList);
        setupToolbar();

    }

    private void bindMainList() {
        mainList = findViewById(R.id.mainOrderList);
        mainList.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<ProductItem> getFoodData() {

        String cartJson = getIntent().getStringExtra("cart");

        Type cartType = new TypeToken<List<Pair<ProductItem, Integer>>>() {}.getType();
        List<Pair<ProductItem, Integer>> cartList = new Gson().fromJson(cartJson, cartType);

        Log.d("cart length", cartList.size() + "");

        List<ProductItem> productItemList = new ArrayList<>();
        for (Pair<ProductItem, Integer> pair : cartList) {
            ProductItem productItem = pair.first;
            int qty = pair.second;
            productItem.setQty(qty);

            productItemList.add(productItem);
        }
        return productItemList;
    }

    private void setupAdapterAndData(List<ProductItem> productItemList) {

        adapter = new FoodListAdapter(productItemList, "client", this, new ItemChanged() {
            @Override
            public void onItemChange() {
                computeOrderPrice(productItemList);
            }
        }, this);

        mainList.setAdapter(adapter);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.shoppingCartTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_order_details_client, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.helpButton) {
            HelpDialog dialog = new HelpDialog(this);
            dialog.setupDialog();
        } else if(id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void computeOrderPrice(List<ProductItem> productItemList) {
        double total = 0.0;
        for (ProductItem productItem : productItemList) {
            total += productItem.getPrice() * productItem.getQty();
        }
        if (total == 0)
            finish();
        total = ((double) ((int) (total * 100)) / 100);
        ((TextView) findViewById(R.id.total)).setText("Total price: " + total + " Lei");
    }

    public void requestBill(View view) { ;
        final int tableNumber = getMyApplication().getClientTableNumber();
        final String restaurantId = getMyApplication().getClientRestaurantId();
        BillOrder order = new BillOrder(tableNumber, productItemList);

        FirebaseFirestore db = new FirestoreSetup().getDb();

        db.collection("RESTAURANTS").document(restaurantId).collection("ORDERS").add(order)
                .addOnSuccessListener(documentReference -> Log.d("Order id", documentReference.getId()))
                .addOnFailureListener(e -> Log.e("could not place order", e.toString()));
    }

    public void placeOrder(View view) {
        view.setEnabled(false);
        final String restaurantId = getMyApplication().getClientRestaurantId();
        List<ProductItem> drinksList = new ArrayList<>();
        List<ProductItem> foodList = new ArrayList<>();

        for (ProductItem item : productItemList) {
            item.setPrepared(false);
            if (item.isFood())
                foodList.add(item);
            else drinksList.add(item);
        }
        FirebaseFirestore db = new FirestoreSetup().getDb();

        placeOrder(db, restaurantId, foodList, drinksList);
//        if (foodList.size() > 0) {
//            FoodOrder foodOrder = new FoodOrder(getMyApplication().getClientTableNumber(), foodList);
//            Toast.makeText(this, foodOrder.getOrderList().size() + "", Toast.LENGTH_SHORT).show();
//            db.collection("RESTAURANTS").document(restaurantId).collection("ORDERS").add(foodOrder)
//                    .addOnSuccessListener(documentReference -> Log.d("Order id", documentReference.getId()))
//                    .addOnFailureListener(e -> Log.e("could not place order", e.toString()));
//        }
//
//        if (drinksList.size() > 0) {
//            DrinkOrder drinkOrder = new DrinkOrder(getMyApplication().getClientTableNumber(), drinksList);
//
//            db.collection("RESTAURANTS").document(restaurantId).collection("ORDERS").add(drinkOrder)
//                    .addOnSuccessListener(documentReference -> Log.d("Order id", documentReference.getId()))
//                    .addOnFailureListener(e -> Log.e("could not place order", e.toString()));
//        }
    }

    private void placeOrder(FirebaseFirestore db, String restaurantId, List<ProductItem> foodList, List<ProductItem> drinksList) {

        FoodOrder foodOrder = new FoodOrder(getMyApplication().getClientTableNumber(), foodList);
        DrinkOrder drinkOrder = new DrinkOrder(getMyApplication().getClientTableNumber(), drinksList);
        getMyApplication().placeOrder(this);
        if (foodList.size() > 0) {
            db.collection("RESTAURANTS").document(restaurantId).collection("ORDERS").add(foodOrder)
                    .addOnSuccessListener(documentReference -> {
                        if(drinksList.size() > 0)
                            db.collection("RESTAURANTS").document(restaurantId).collection("ORDERS")
                                    .add(drinkOrder)
                                    .addOnFailureListener(e -> Log.e("could not place order", e.toString()));
                        finish();
                    })
                    .addOnFailureListener(e -> Log.e("could not place order", e.toString()));

        } else if (drinksList.size() > 0) {
            db.collection("RESTAURANTS").document(restaurantId).collection("ORDERS").add(drinkOrder)
                    .addOnSuccessListener(documentReference -> {
                        if (foodList.size() > 0)
                            db.collection("RESTAURANTS").document(restaurantId).collection("ORDERS")
                                    .add(foodOrder)
                                    .addOnFailureListener(e -> Log.e("could not place order", e.toString()));
                        finish();
                    })
                    .addOnFailureListener(e -> Log.e("could not place order", e.toString()));
        }

    }

    MyApplication getMyApplication() {
        MyApplication app = (MyApplication) getApplication();
        return app;

    }
}
