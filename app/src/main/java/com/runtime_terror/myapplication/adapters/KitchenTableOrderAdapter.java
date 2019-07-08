package com.runtime_terror.myapplication.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.runtime_terror.myapplication.Food;
import com.runtime_terror.myapplication.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class KitchenTableOrderAdapter extends RecyclerView.Adapter<KitchenTableOrderAdapter.MyViewHolder> {

    List<Food> dataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CircleImageView image;
        TextView title;
        TextView reqs;
        TextView qty;
        CheckBox prepared;


        public MyViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.food_image);
            title = v.findViewById(R.id.food_title);
            reqs = v.findViewById(R.id.food_reqs);
            qty = v.findViewById(R.id.food_qty);
            prepared = v.findViewById(R.id.food_preparation_completed);
        }

        public void setImage(String image) {
            this.image.setImageResource(R.drawable.food);
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }

        public void setReqs(String reqs) {
            this.reqs.setText(reqs);
        }

        public void setQty(int qty) {
            this.qty.setText("Qty: " + qty);
        }

        public void setPrepared(Boolean prepared) {
            this.prepared.setChecked(prepared);
        }

    }

    public KitchenTableOrderAdapter(List<Food> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public KitchenTableOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kitchen_food_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KitchenTableOrderAdapter.MyViewHolder myViewHolder, int position) {
        myViewHolder.setImage(dataset.get(position).getImage());
        myViewHolder.setTitle(dataset.get(position).getTitle());
        myViewHolder.setReqs(dataset.get(position).getReqs());
        myViewHolder.setQty(dataset.get(position).getQty());
        myViewHolder.setPrepared(dataset.get(position).getPrepared());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
