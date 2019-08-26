package com.runtime_terror.myapplication.adapters;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runtime_terror.myapplication.activities.KitchenTableOrder;
import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.models.Order;

import java.util.Date;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {

    private List<? extends Order> ordersList;
    private Context context;

    public OrderListAdapter(Context context, List<? extends Order> ordersList) {
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
        holder.setTableNumber(ordersList.get(position).getTableNumber());
        holder.setElapsedTime(ordersList.get(position).getRequestDate());
        holder.setCardColor(ordersList.get(position).getColor());

        if(ordersList.get(position).isClickable())
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(), KitchenTableOrder.class);
                    intent.putExtra("purpose", ordersList.get(position).getPurpose());
                    context.startActivity(intent);
                }
            });

        if(ordersList.get(position).isClosable())
            holder.closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ordersList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            });
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

    }
}
