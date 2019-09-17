package com.runtime_terror.myapplication.fragments;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.protobuf.DescriptorProtos;
import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.adapters.OrderListAdapter;
import com.runtime_terror.myapplication.database.FirestoreSetup;
import com.runtime_terror.myapplication.interfaces.CompleteOrder;
import com.runtime_terror.myapplication.models.BillOrder;
import com.runtime_terror.myapplication.models.DrinkOrder;
import com.runtime_terror.myapplication.models.FoodOrder;
import com.runtime_terror.myapplication.models.ProductItem;
import com.runtime_terror.myapplication.models.ProductOrder;
import com.runtime_terror.myapplication.models.HelpOrder;
import com.runtime_terror.myapplication.models.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class OrderContainerFragment extends Fragment implements CompleteOrder {

    View view;
    RecyclerView orderRecycler;
    String purpose;
    List<Order> orders = new ArrayList<>();
    OrderListAdapter adapter;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int deletePosition;
        if(resultCode == 2) {
            try {
                deletePosition = data.getIntExtra("deletePosition", -1);
            }catch(Exception ex){
                Log.e("error", ex.toString());
                return;
            }

            if(deletePosition == -1)
                return; // show a toast saying something went wrong

            adapter.deleteOrder(deletePosition);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.order_fragment, container, false);
        orderRecycler = view.findViewById(R.id.orderRecycler);
        adapter = new OrderListAdapter(getContext(), orders, this);
        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
        orderRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        orderRecycler.setAdapter(getAdapter());
    }

    public void loadOrder(List<Integer> orderTypes) {
        final String restaurantId = "lrApMZq9rBNLQGtzVjKa";

        adapter = new OrderListAdapter(getContext(), orders, this);
        for(Integer orderType : orderTypes)
            new FirestoreSetup().getDb().collection("RESTAURANTS")
                    .document(restaurantId).collection("ORDERS").whereEqualTo("status", Order.ORDER_STATUS.PLACED).whereEqualTo("orderType", orderType).get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()) {
                                Order order = getOrderObject(document, orderType);
                                order.setId(document.getId());
                                orders.add(order);
                            }
                            Collections.reverse(orders);
                            adapter.notifyItemInserted(orders.size() - 1);
                        }
                    });
    }

    private Order getOrderObject(QueryDocumentSnapshot document, Integer orderType) {
        if(orderType == BillOrder.BILL_ORDER_TYPE)
            return document.toObject(BillOrder.class);
        else if(orderType == HelpOrder.HELP_ORDER_TYPE)
            return document.toObject(HelpOrder.class);
        else if(orderType == FoodOrder.FOOD_ORDER_TYPE)
            return document.toObject(FoodOrder.class);
//        else if(orderType == DrinkOrder.DRINK_ORDER_TYPE)
//            return document.toObject(DrinkOrder.class);
        else
            return null;
    }

    private OrderListAdapter getAdapter() {

        if(purpose.equals("food"))
            return getRandomFoodAdapter();
        else if(purpose.equals("operations"))
            return adapter;

        return null;
    }

    private OrderListAdapter getRandomFoodAdapter() {

        final List<ProductOrder> productOrders = new ArrayList<>();
        for( int i=0; i < 11; i++) {

            Random random = new Random();
            int max = Math.abs(random.nextInt() % 11);
            int tableNumber = Math.abs(random.nextInt() % 20);
            ProductOrder productOrder = new ProductOrder(tableNumber);

            for(int j=0; j<=max; j++){
                productOrder.addFood(new ProductItem("someImage", "Some title1", 35, "Some reqs1", 3, true,"description"));
            }
            productOrders.add(productOrder);
        }

        adapter = new OrderListAdapter(getContext(), productOrders, this);
        return adapter;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public void completeOrder(Order order) {

        final String restaurantId = "lrApMZq9rBNLQGtzVjKa";
        order.setStatus(Order.ORDER_STATUS.COMPLETED);

        new FirestoreSetup().getDb().collection("RESTAURANTS").document(restaurantId).collection("ORDERS")
                .document(order.getId()).set(order, SetOptions.merge());
    }
}