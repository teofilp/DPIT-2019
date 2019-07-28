package com.runtime_terror.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runtime_terror.myapplication.adapters.OrderListAdapter;
import com.runtime_terror.myapplication.models.Food;
import com.runtime_terror.myapplication.models.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class OrderContainerFragment extends Fragment {

    View view;
    RecyclerView orderRecycler;
    boolean isBar;

    public void setIsBar(boolean isBar){
        this.isBar = isBar;
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

        final List<Order> orders = new ArrayList<>();
        for( int i=0; i < 11; i++) {

            Random random = new Random();
            int max = Math.abs(random.nextInt() % 11);
            int tableNumber = Math.abs(random.nextInt() % 20);
            Order order = new Order(tableNumber);

            for(int j=0; j<=max; j++){
                order.addFood(new Food("someImage", "Some title1", 35, "Some reqs1", 3, true));
            }
            orders.add(order);
        }

        final OrderListAdapter adapter = new OrderListAdapter(getContext(), orders);

        return adapter;
    }
}
