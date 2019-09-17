package com.runtime_terror.myapplication.activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.adapters.FoodListAdapter;
import com.runtime_terror.myapplication.database.FirestoreSetup;
import com.runtime_terror.myapplication.interfaces.OrderUpdatesListener;
import com.runtime_terror.myapplication.models.BillOrder;
import com.runtime_terror.myapplication.models.Order;
import com.runtime_terror.myapplication.models.ProductItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class KitchenTableOrder extends AppCompatActivity {
    RecyclerView tableOrderRecycler;
    RecyclerView.Adapter adapter;
    String purpose;
    Button completeButton;
    int position;
    Order order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_table_order);

        completeButton = findViewById(R.id.complete_all);

        try{
            purpose = getIntent().getStringExtra("purpose");
            position = getIntent().getIntExtra("position", -1);
        } catch (Exception ex) {
            purpose = "delivery";
        }

        if(purpose.equals("operations"))
            completeButton.setText("Done");


        setupToolbar();
        setupRecyclerAdapter();
    }

    private void setupRecyclerAdapter() {

        tableOrderRecycler = findViewById(R.id.tableOrderRecycler);
        tableOrderRecycler.setLayoutManager(new LinearLayoutManager(this));
        tableOrderRecycler.setNestedScrollingEnabled(false);

        String orderJSON = getIntent().getStringExtra("orderList");
        Type cartType = new TypeToken<BillOrder>(){}.getType();
        order = (BillOrder)new Gson().fromJson(orderJSON,  cartType);
        List<ProductItem> list = ((BillOrder)order).getOrderList();

        adapter = new FoodListAdapter(list, purpose, getApplicationContext());

        if(!purpose.equals("operations")) {
            if (!isOrderPrepared(list))
                completeButton.setEnabled(false);

            ((FoodListAdapter) adapter).registerOrderCompleteListener(new OrderUpdatesListener() {
                @Override
                public void onOrderUpdate(boolean isComplete) {
                    Log.d("isComplete", Boolean.toString(isComplete));
                    if (isComplete) {
                        completeButton.setEnabled(true);
                        completeButton.setText("Complete Order");
                    } else {
                        completeButton.setEnabled(false);
                        completeButton.setText("Complete");
                    }
                }
            });
        }
        tableOrderRecycler.setAdapter(adapter);
    }

    private boolean isOrderPrepared(List<ProductItem> productItemList) {
        int preparedMeals = 0;
        for(ProductItem productItem : productItemList)
            if(productItem.isPrepared())
                preparedMeals++;

        return preparedMeals == productItemList.size();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("ProductOrder for Table #1");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void completeOrder(View view){
        // emit a complete order message and delete order from list
        final String restaurantId = "lrApMZq9rBNLQGtzVjKa";
        order.setStatus(Order.ORDER_STATUS.COMPLETED);

        new FirestoreSetup().getDb().collection("RESTAURANTS").document(restaurantId).collection("ORDERS")
                .document(order.getId()).set(order, SetOptions.merge());

        Intent resultIntent = new Intent();
        resultIntent.putExtra("deletePosition", position);
        int result_code = 2;
        setResult(result_code, resultIntent);
        finish();
    }
}