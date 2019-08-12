package com.runtime_terror.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.models.Food;
import com.runtime_terror.myapplication.adapters.MenuItemListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CategoriesMenuFragment extends Fragment {
    View view;
    RecyclerView menuRecycler;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.categories_menu_fragment, container, false);
        menuRecycler = view.findViewById(R.id.menuRecycler);

        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
        menuRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        menuRecycler.setAdapter(getRandomAdapter());
        
    }

    private MenuItemListAdapter getRandomAdapter() {

        final List<Food> orders = new ArrayList<>();
        for( int i=0; i < 11; i++) {

            Random random = new Random();
            int max = Math.abs(random.nextInt() % 11);
            int tableNumber = Math.abs(random.nextInt() % 20);
            Food order = new Food("someImage", "Veggie Burger", 10,"Some reqs1", 3, false);

            orders.add(order);
        }

        final MenuItemListAdapter adapter = new MenuItemListAdapter(getContext(), orders);

        return adapter;
    }
}



