package com.runtime_terror.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runtime_terror.myapplication.KitchenTableOrder;
import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.models.Food;
import com.runtime_terror.myapplication.models.Order;

import java.util.Date;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {

    private List<Order> ordersList;
    private Context context;

    public OrderListAdapter(Context context, List<Order> ordersList) {
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setTableNumber(ordersList.get(position).getOrderTable());
        holder.setElapsedTime(ordersList.get(position).getOrderDate());
        holder.setCardColor(ordersList.get(position).getOrderList());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context.getApplicationContext(), KitchenTableOrder.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout container;
        TextView tableNumber;
        TextView elapsedTime;

        public MyViewHolder(View itemView) {
            super(itemView);

            container = (RelativeLayout) itemView;
            tableNumber = itemView.findViewById(R.id.table_number);
            elapsedTime = itemView.findViewById(R.id.elapsed_time);

        }

        public void setTableNumber(int number) {
            tableNumber.setText(Integer.toString(number));
        }

        public void setElapsedTime(Date timestamp) {
            int timeDifferenceInMills = (int)(new Date().getTime() - timestamp.getTime())/1000/60;
            String time = timeDifferenceInMills + 1 + (timeDifferenceInMills > 1 ? " mins ago" : " min ago");

            elapsedTime.setText(time);
        }

        public void setCardColor(List<Food> orderList) {

            int color;
            if(orderList.size() <= 2)
                color = context.getResources().getColor(R.color.lightGreen);
            else if (orderList.size() <= 4)
                color = context.getResources().getColor(R.color.darkGreen);
            else if(orderList.size() <= 6)
                color = context.getResources().getColor(R.color.yellow);
            else if(orderList.size() <= 10)
                color = context.getResources().getColor(R.color.orange);
            else
                color = context.getResources().getColor(R.color.red);

            container.setBackgroundColor(color);
        }
    }
}
