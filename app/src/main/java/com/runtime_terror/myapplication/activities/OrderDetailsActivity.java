package com.runtime_terror.myapplication.activities;


import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.adapters.FoodListAdapter;
import com.runtime_terror.myapplication.models.Food;
import com.runtime_terror.myapplication.models.HelpDialog;


import java.util.ArrayList;
import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {

    private RecyclerView mainList;
    private RecyclerView.Adapter adapter;
    public static final String TAG = "OrderDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        bindMainList();
        List<Food> foodList = getFoodData();
        computeOrderPrice(foodList);
        setupAdapterAndData(foodList);
        setupToolbar();

    }

    private void bindMainList(){
        mainList = findViewById(R.id.mainOrderList);
        mainList.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<Food> getFoodData(){
        List<Food> foodList = new ArrayList<>();
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        return foodList;
    }

    private void setupAdapterAndData(List<Food> foodList){

        adapter = new FoodListAdapter(foodList, "client", this);
        mainList.setAdapter(adapter);
    }

    private void setupToolbar(){
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
        }

        return super.onOptionsItemSelected(item);
    }

    private void computeOrderPrice(List<Food> foodList){
        double total = 0.0;
        for(Food food : foodList){
            total += food.getPrice();
        }

        ((TextView)findViewById(R.id.total)).setText("Total price: " + total);
    }
}

