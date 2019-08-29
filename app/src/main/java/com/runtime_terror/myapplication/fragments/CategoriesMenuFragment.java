package com.runtime_terror.myapplication.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.interfaces.AddToCartListener;
import com.runtime_terror.myapplication.models.Food;
import com.runtime_terror.myapplication.adapters.MenuItemListAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoriesMenuFragment extends Fragment {
    View view;
    RecyclerView menuRecycler;
    AddToCartListener listener;

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
        for( int i=0; i < 15; i++) {


            Food order = new Food("someImage", "Some title1", 10,"Some reqs1", 3, false,"description");

            orders.add(order);
        }

        final MenuItemListAdapter adapter = new MenuItemListAdapter(getContext(), orders, listener);

        return adapter;
    }

    public void registerAddToCartListener(AddToCartListener listener) {

        this.listener = listener;
    }
}



