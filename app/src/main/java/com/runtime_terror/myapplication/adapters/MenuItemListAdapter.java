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
import com.runtime_terror.myapplication.interfaces.EditItemInterface;
import com.runtime_terror.myapplication.models.EditItemDialog;
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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditItemDialog addDialog = new EditItemDialog(context,holder);
                addDialog.setVisibilities("addToCart");
                addDialog.setupDialog();
            }
        });


    }

    @Override
    public int getItemCount() {
        return menuItemsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements EditItemInterface {
        RelativeLayout container;

        public MyViewHolder(View itemView) {
            super(itemView);

            container = (RelativeLayout) itemView;
        }

        @Override
        public int getQty(){
            return 0;
        }

        @Override
        public void setQty(int quantity) {
            return;
        }

        @Override
        public String getReqs() {
            return null;
        }

        @Override
        public void setReqs(String reqs) {

        }

        @Override
        public List<Object> getDataSet() {
            return null;
        }

        @Override
        public int getItemPosition() {
            return 0;
        }

        @Override
        public void dialogNotifyItemRemoved(int position) {

        }

        @Override
        public String getTranslation(int resource) {
            return null;
        }


    }}

