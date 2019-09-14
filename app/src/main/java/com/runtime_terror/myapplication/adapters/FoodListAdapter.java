package com.runtime_terror.myapplication.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runtime_terror.myapplication.interfaces.EditItemInterface;
import com.runtime_terror.myapplication.interfaces.ItemChanged;
import com.runtime_terror.myapplication.interfaces.OrderUpdatesListener;
import com.runtime_terror.myapplication.models.EditItemDialog;
import com.runtime_terror.myapplication.models.Food;
import com.runtime_terror.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.MyViewHolder> {

    List<Food> dataset;
    List<OrderUpdatesListener> preparedMealListeners = new ArrayList<>();
    public String purpose = "N/A";
    public Context mContext;

    public ItemChanged itemChanged;


    public class MyViewHolder extends RecyclerView.ViewHolder implements EditItemInterface {
        RelativeLayout container;
        CircleImageView image;
        TextView title;
        View dash;
        TextView price;
        TextView reqs;
        TextView qty;
        CheckBox prepared;
        ImageButton options;


        public MyViewHolder(View v, String purpose){
            super(v);
            container = (RelativeLayout) v;
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
            if (purpose.equals("delivery") || purpose.equals("operations")){
                dash.setVisibility(View.GONE);
                price.setVisibility(View.GONE);
                options.setVisibility(View.GONE);

                if(purpose.equals("operations"))
                    prepared.setVisibility(View.GONE);

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

        public void setPrice(double price) { this.price.setText("$" + price); }

        public void setQty(int qty) {
            this.qty.setText("QTY: " + qty);
        }

        public void setPrepared(Boolean prepared) {
            this.prepared.setChecked(prepared);
        }

        @Override
        public int getQty() {
            String tmp = qty.getText().toString();
            if(tmp.contains("QTY: ")){
                tmp = tmp.substring(tmp.indexOf("QTY: ")+5);
            }
            if(tmp.length()>0) {
                return Integer.parseInt(tmp);
            }
            return 1;
        }

        @Override
        public String getReqs() {
            return reqs.getText().toString();
        }

        @Override
        public List getDataSet() {
            return dataset;
        }

        @Override
        public int getItemPosition() {
            return getAdapterPosition();
        }

        @Override
        public void dialogNotifyItemRemoved(int position) {
            notifyItemRemoved(position);
        }

        @Override
        public String getTranslation(int resource) {
            return mContext.getString(resource);
        }

        @Override
        public void itemChanged() {
            itemChanged.onItemChange();
        }

        public void applyTranslateAnimation() {
            container.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_transition));
        }
    }

    public FoodListAdapter(List<Food> dataset, String purpose, Context context, ItemChanged listener){
        this.dataset = dataset;
        this.purpose = purpose;
        this.mContext = context;
        this.itemChanged = listener;
    }


    public FoodListAdapter(List<Food> dataset, String purpose, Context context){
        this.dataset = dataset;
        this.purpose = purpose;
        this.mContext = context;
    }



    @NonNull
    @Override
    public FoodListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new MyViewHolder(view, purpose);
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodListAdapter.MyViewHolder myViewHolder, final int position) {

        myViewHolder.setImage(dataset.get(position).getImage());
        myViewHolder.setTitle(dataset.get(position).getTitle());
        myViewHolder.setReqs(dataset.get(position).getReqs());
        myViewHolder.setQty(dataset.get(position).getQty());
        myViewHolder.setPrice(dataset.get(position).getPrice());

        myViewHolder.setPrepared(dataset.get(position).isPrepared());


        if(purpose.equals("delivery")) {
            myViewHolder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataset.get(position).setPrepared(!myViewHolder.prepared.isChecked());
                    myViewHolder.prepared.setChecked(!myViewHolder.prepared.isChecked());

                    notifyOrderComplete(isOrderComplete());
                }
            });
        }

        myViewHolder.setPrepared(dataset.get(position).isPrepared());

        myViewHolder.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dialog Setup
                final EditItemDialog dialog = new EditItemDialog(mContext, myViewHolder, dataset.get(position));
                dialog.setVisibilities("editItem");
                dialog.setupDialog();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
    public void registerOrderCompleteListener(OrderUpdatesListener listener){
        preparedMealListeners.add(listener);
    }

    private boolean isOrderComplete() {
        for(Food food : dataset)
            if(!food.isPrepared())
                return false;
        return true;
    }

    public void notifyOrderComplete(boolean isComplete){
        for(OrderUpdatesListener listener : preparedMealListeners)
            listener.onOrderUpdate(isComplete);
    }
}
