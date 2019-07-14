package com.runtime_terror.myapplication.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.runtime_terror.myapplication.models.Food;
import com.runtime_terror.myapplication.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.MyViewHolder> {

    List<Food> dataset;
    public String purpose = "N/A";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CircleImageView image;
        TextView title;
        View dash;
        TextView price;
        TextView reqs;
        TextView qty;
        CheckBox prepared;
        ImageButton options;

        public MyViewHolder(View v, String purpose) {
            super(v);
            image = v.findViewById(R.id.food_image);
            title = v.findViewById(R.id.food_title);
            dash = v.findViewById(R.id.dash);
            price = v.findViewById(R.id.price);
            reqs = v.findViewById(R.id.food_reqs);
            qty = v.findViewById(R.id.food_qty);
            prepared = v.findViewById(R.id.food_preparation_completed);
            options = v.findViewById(R.id.options);

            this.setVisibilities(purpose);
        }


        private void setVisibilities(String purpose){
            if (purpose.equals("kitchen")){
                dash.setVisibility(View.GONE);
                price.setVisibility(View.GONE);
                options.setVisibility(View.GONE);

            } else if (purpose.equals("client")) {
                prepared.setVisibility(View.GONE);
            }
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

        public void setPrice(double price) {
            this.price.setText("$" + price);
        }

        public void setQty(int qty) {
            this.qty.setText("QTY: " + qty);
        }

        public void setPrepared(Boolean prepared) {
            this.prepared.setChecked(prepared);
        }

    }

    public FoodListAdapter(List<Food> dataset, String purpose) {
        this.dataset = dataset;
        this.purpose = purpose;
    }

    @NonNull
    @Override
    public FoodListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new MyViewHolder(view, purpose);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter.MyViewHolder myViewHolder, int position) {
        myViewHolder.setImage(dataset.get(position).getImage());
        myViewHolder.setTitle(dataset.get(position).getTitle());
        myViewHolder.setReqs(dataset.get(position).getReqs());
        myViewHolder.setQty(dataset.get(position).getQty());
        myViewHolder.setPrice(dataset.get(position).getPrice());
        myViewHolder.setPrepared(dataset.get(position).getPrepared());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

}
