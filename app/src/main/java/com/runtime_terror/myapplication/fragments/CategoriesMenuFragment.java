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

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.interfaces.CartListener;
import com.runtime_terror.myapplication.models.ProductItem;
import com.runtime_terror.myapplication.adapters.MenuItemListAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoriesMenuFragment extends Fragment {
    View view;
    RecyclerView menuRecycler;
    CartListener listener;

    private FirebaseFirestore db;
    private CollectionReference typeRef;
    private String path;
    private final String TAG = "DebugFrag";// for debug purposes only


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.categories_menu_fragment, container, false);
        menuRecycler = view.findViewById(R.id.menuRecycler);
        path = getArguments().getString("path") + "/" + getArguments().getStringArrayList("foodTypes").get(0);

        setupDataBase();
        setupRecyclerView();

        return view;
    }

    private void setupDataBase() {

        db = FirebaseFirestore.getInstance();
        typeRef = db.collection(path);
    }

    private void setupRecyclerView() {
        menuRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        menuRecycler.setAdapter(getAdapter());
        
    }

    private MenuItemListAdapter getAdapter() {// TODO: Implement my thing (DB) here.

        final List<ProductItem> orders = new ArrayList<>();
        final MenuItemListAdapter adapter = new MenuItemListAdapter(getContext(), orders, listener);
        typeRef.get().addOnCompleteListener(task -> {
            Log.d(TAG, "Loading food items...");
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot food : task.getResult()) {
                    Log.d(TAG, food.getString("name"));

                    ProductItem productItemItem = new ProductItem("someImage", food.getString("name"), (double) food.get("price"), "", 1, food.getBoolean("isAvailable"), food.getString("desc"));
                    orders.add(productItemItem);
                }
                adapter.notifyDataSetChanged();
            }
            else {
                Log.d(TAG, "Error getting food documents: ", task.getException());
            }
        });
        return adapter;
    }

    public void registerCartListener(CartListener listener) {
        this.listener = listener;
    }
}



