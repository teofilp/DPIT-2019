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

import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.adapters.OrderListAdapter;
import com.runtime_terror.myapplication.models.BillOrder;
import com.runtime_terror.myapplication.models.Food;
import com.runtime_terror.myapplication.models.FoodOrder;
import com.runtime_terror.myapplication.models.HelpOrder;
import com.runtime_terror.myapplication.models.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderContainerFragment extends Fragment {

    View view;
    RecyclerView orderRecycler;
    String purpose;
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

        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
        orderRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        orderRecycler.setAdapter(getRandomAdapter());
    }

    private OrderListAdapter getRandomAdapter() {

        if(purpose.equals("food"))
            return getRandomFoodAdapter();
        else if(purpose.equals("operations"))
            return getRandomRequestAdapter();

        return null;
    }

    private OrderListAdapter getRandomRequestAdapter(){

        final List<Order> requestOrders = new ArrayList<>();

        for(int i=0; i<11; i++){
            Random random = new Random();
            Random tableRandom = new Random();
            if(Math.abs(random.nextInt() % 2) == 0)
                requestOrders.add(new BillOrder(Math.abs(tableRandom.nextInt() % 10)));
            else
                requestOrders.add(new HelpOrder(Math.abs(tableRandom.nextInt() % 10)));
        }

        adapter = new OrderListAdapter(getContext(), requestOrders, this);
        return adapter;
    }

    private OrderListAdapter getRandomFoodAdapter() {

        final List<FoodOrder> foodOrders = new ArrayList<>();
        for( int i=0; i < 11; i++) {

            Random random = new Random();
            int max = Math.abs(random.nextInt() % 11);
            int tableNumber = Math.abs(random.nextInt() % 20);
            FoodOrder foodOrder = new FoodOrder(tableNumber);

            for(int j=0; j<=max; j++){
                foodOrder.addFood(new Food("someImage", "Some title1", 35, "Some reqs1", 3, true,"description"));
            }
            foodOrders.add(foodOrder);
        }

        adapter = new OrderListAdapter(getContext(), foodOrders, this);
        return adapter;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}

