package com.runtime_terror.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.runtime_terror.myapplication.adapters.KitchenTableOrderAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KitchenTableOrder extends AppCompatActivity {
    RecyclerView tableOrderRecycler;
    RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_table_order);

        setupToolbar();
        setupRecylcerAdapter();
    }

    private void setupRecylcerAdapter() {

        tableOrderRecycler = findViewById(R.id.tableOrderRecycler);
        tableOrderRecycler.setLayoutManager(new LinearLayoutManager(this));
        tableOrderRecycler.setNestedScrollingEnabled(false);

        ArrayList<Food> list = new ArrayList<Food>();
        list.add(new Food("someImage", "Some title1", "Some reqs1", "3", true));
        list.add(new Food("someImage", "Some title1", "Some reqs1", "3", true));
        list.add(new Food("someImage", "Some title1", "Some reqs1", "3", true));
        list.add(new Food("someImage", "Some title1", "Some reqs1", "3", true));
        list.add(new Food("someImage", "Some title1", "Some reqs1", "3", true));
        list.add(new Food("someImage", "Some title1", "Some reqs1", "3", true));
        list.add(new Food("someImage", "Some title1", "Some reqs1", "3", true));
        list.add(new Food("someImage", "Some title1", "Some reqs1", "3", true));

        adapter = new KitchenTableOrderAdapter(list);
        tableOrderRecycler.setAdapter(adapter);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Order for Table #1");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


}
