package com.runtime_terror.myapplication.adapters;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.runtime_terror.myapplication.Menu_Item;
import com.runtime_terror.myapplication.R;

import java.util.List;

public class Menu_Item_Adapter extends RecyclerView.Adapter<Menu_Item_Adapter.ViewHolder>{

    List<Menu_Item> MenuItemList;
    Context context;

    public Menu_Item_Adapter(List<Menu_Item> MenuItemList){
        this.MenuItemList=MenuItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Menu_Item menu_item = MenuItemList.get(position);


        holder.nameItem.setText(Menu_Item.getName());
        holder.imgTvShow.setImageResource(tvShow.getImgTvshow());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"The position is:"+position,Toast.LENGTH_SHORT).show();
            }
        });


    }


}

