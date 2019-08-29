package com.runtime_terror.myapplication.fragments;

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

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.models.Food;
import com.runtime_terror.myapplication.adapters.MenuItemListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.runtime_terror.myapplication.activities.MenuActivity;

public class CategoriesMenuFragment extends Fragment {
    View view;
    RecyclerView menuRecycler;
    private FirebaseFirestore db;
    private DocumentReference catRef;
    private String path;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.categories_menu_fragment, container, false);
        menuRecycler = view.findViewById(R.id.menuRecycler);
        path = getArguments().getString("path");

        setupRecyclerView();
        setupDataBase();

        return view;
    }

    private void setupDataBase() {

        db = FirebaseFirestore.getInstance();
        catRef = db.document(path);
    }

    private void setupRecyclerView() {
        menuRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        menuRecycler.setAdapter(getRandomAdapter());
        
    }

    private MenuItemListAdapter getRandomAdapter() {// TODO: Implement my thing (DB) here.



        final List<Food> orders = new ArrayList<>();



        /*for( int i=0; i < 15; i++) {

            Food order = new Food("someImage", "Some title1", 10,"Some reqs1", 3, false,"description");

            orders.add(order);
        }*/

        final MenuItemListAdapter adapter = new MenuItemListAdapter(getContext(), orders);

        return adapter;
    }
}



