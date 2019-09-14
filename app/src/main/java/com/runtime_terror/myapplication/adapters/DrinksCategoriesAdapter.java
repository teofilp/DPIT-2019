package com.runtime_terror.myapplication.adapters;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.models.ProductItem;

import java.util.List;

public class DrinksCategoriesAdapter extends RecyclerView.Adapter<DrinksCategoriesAdapter.MyViewHolder> {

    private List<ProductItem> drinksItemsList;
    private Context context;

    public DrinksCategoriesAdapter(Context context, List<ProductItem> drinksItemsList) {
        this.drinksItemsList = drinksItemsList;
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

    }


    @Override
    public int getItemCount() {
        return drinksItemsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout container;

        public MyViewHolder(View itemView) {
            super(itemView);

            container = (RelativeLayout) itemView;
        }


    }}

