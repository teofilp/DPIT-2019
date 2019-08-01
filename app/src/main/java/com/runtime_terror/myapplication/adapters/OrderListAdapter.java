package com.runtime_terror.myapplication.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.runtime_terror.myapplication.KitchenTableOrder;
import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.models.Food;
import com.runtime_terror.myapplication.models.FoodOrder;
import com.runtime_terror.myapplication.models.Order;

import java.util.Date;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {

    private List<? extends Order> ordersList;
    private Context context;
    private Fragment parent;
    public static final int ORDER_ACTIVITY_REQUEST_CODE = 199;

    public OrderListAdapter(Context context, List<? extends Order> ordersList, Fragment parent) {
        this.parent = parent;
        this.ordersList = ordersList;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final @NonNull MyViewHolder holder, final int position) {
        holder.setTableNumber(ordersList.get(holder.getAdapterPosition()).getTableNumber());
        holder.setElapsedTime(ordersList.get(holder.getAdapterPosition()).getRequestDate());
        holder.setCardColor(ordersList.get(holder.getAdapterPosition()).getColor());
        holder.setIsClickable(ordersList.get(holder.getAdapterPosition()).isClickable());
        holder.setIsClosable(ordersList.get(holder.getAdapterPosition()).isClosable());

        if(holder.isClickable)
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(), KitchenTableOrder.class);
                    intent.putExtra("purpose", ordersList.get(holder.getAdapterPosition()).getPurpose());
                    intent.putExtra("position", holder.getAdapterPosition());
                    parent.startActivityForResult(intent, ORDER_ACTIVITY_REQUEST_CODE);
                }
            });
        else
            holder.container.setOnClickListener(null);

        if(holder.isClosable) {
            holder.closeButton.setVisibility(View.VISIBLE);
            holder.closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ordersList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            });
        }
        else holder.closeButton.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout container;
        TextView tableNumber;
        TextView elapsedTime;
        ImageButton closeButton;

        boolean isClickable;
        boolean isClosable;

        public MyViewHolder(View itemView) {
            super(itemView);

            container = (RelativeLayout) itemView;
            tableNumber = itemView.findViewById(R.id.table_number);
            elapsedTime = itemView.findViewById(R.id.elapsed_time);
            closeButton = itemView.findViewById(R.id.close_button);

        }

        public void setTableNumber(int number) {
            tableNumber.setText(Integer.toString(number));
        }

        public void setElapsedTime(Date timestamp) {
            int timeDifferenceInMills = (int)(new Date().getTime() - timestamp.getTime())/1000/60;
            String time = timeDifferenceInMills + 1 + (timeDifferenceInMills > 1 ? " mins ago" : " min ago");

            elapsedTime.setText(time);
        }

        public void setCardColor(int cardColor) {
            int color = context.getResources().getColor(cardColor);
            container.setBackgroundColor(color);
        }

        public void setIsClickable(boolean isClickable){
            this.isClickable = isClickable;
        }

        public void setIsClosable(boolean isClosable){
            this.isClosable = isClosable;
        }

    }

    public void deleteOrder(int position){
        ordersList.remove(position);
        notifyItemRangeRemoved(position, 1);
    }
}
