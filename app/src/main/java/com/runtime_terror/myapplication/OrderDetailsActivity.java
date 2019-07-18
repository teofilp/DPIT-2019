package com.runtime_terror.myapplication;


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
import android.widget.Toast;

import com.runtime_terror.myapplication.adapters.FoodListAdapter;


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
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.help_dialog);
            dialog.setCancelable(false);

            //Setup Cancel and Call Waiter buttons

            Button callWaiterButton = dialog.findViewById(R.id.callWaiterButton);
            callWaiterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            Button cancelHelpButton = dialog.findViewById(R.id.cancelHelp);
            cancelHelpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
