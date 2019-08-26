package com.runtime_terror.myapplication.activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.adapters.FoodListAdapter;
import com.runtime_terror.myapplication.interfaces.OrderUpdatesListener;
import com.runtime_terror.myapplication.models.Food;

import java.util.ArrayList;

public class KitchenTableOrder extends AppCompatActivity {
    RecyclerView tableOrderRecycler;
    RecyclerView.Adapter adapter;
    String purpose;
    Button completeButton;
    int position;
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
        ArrayList<Food> list = new ArrayList<Food>();
        list.add(new Food("someImage", "Chicken Cheeseburger", 18,"Some reqs1", 1, false,"description"));
        list.add(new Food("someImage", "Peanut Jelly Burger", 20,"Some reqs1", 2, true, "description"));
        list.add(new Food("someImage", "Veggie Burger", 10,"Some reqs1", 3, true, "description"));
        list.add(new Food("someImage", "Chicken Cheeseburger", 18,"Some reqs1", 1, true, "description"));
        list.add(new Food("someImage", "Peanut Jelly Burger", 20,"Some reqs1", 3, false, "description"));
        list.add(new Food("someImage", "Veggie Burger", 10,"Some reqs1", 2, true, "description"));
        list.add(new Food("someImage", "Chicken Cheeseburger", 18,"Some reqs1", 2, true, "description"));
        list.add(new Food("someImage", "Peanut Jelly Burger", 20,"Some reqs1", 1, true, "description"));
        list.add(new Food("someImage", "Veggie Burger", 10,"Some reqs1", 3, false, "description"));

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

    private boolean isOrderPrepared(ArrayList<Food> foodList) {
        int preparedMeals = 0;
        for(Food food: foodList)
            if(food.isPrepared())
                preparedMeals++;

        return preparedMeals == foodList.size();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("FoodOrder for Table #1");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void completeOrder(View view){
        // emit a complete order message and delete order from list
        Intent resultIntent = new Intent();
        resultIntent.putExtra("deletePosition", position);
        int result_code = 2;
        setResult(result_code, resultIntent);
        finish();
    }


}
