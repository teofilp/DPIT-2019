package com.runtime_terror.myapplication.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.runtime_terror.myapplication.AddToCartActivity;
import com.runtime_terror.myapplication.KitchenTableOrder;
import com.runtime_terror.myapplication.OrderDetailsActivity;
import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.models.Food;

import java.util.List;

public class MenuItemListAdapter extends RecyclerView.Adapter<MenuItemListAdapter.MyViewHolder> {

    private List<Food> menuItemsList;
    private Context context;

    public MenuItemListAdapter(Context context, List<Food> menuItemsList) {
        this.menuItemsList = menuItemsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), AddToCartActivity.class);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return menuItemsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout container;

        public MyViewHolder(View itemView) {
            super(itemView);

            container = (RelativeLayout) itemView;
        }


    }}

