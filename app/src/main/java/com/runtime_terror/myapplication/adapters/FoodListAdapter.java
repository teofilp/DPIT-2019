package com.runtime_terror.myapplication.adapters;

import android.app.Activity;
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

import com.bumptech.glide.Glide;
import com.runtime_terror.myapplication.interfaces.EditItemInterface;
import com.runtime_terror.myapplication.interfaces.ItemChanged;
import com.runtime_terror.myapplication.interfaces.OrderUpdatesListener;
import com.runtime_terror.myapplication.models.EditItemDialog;
import com.runtime_terror.myapplication.models.ProductItem;
import com.runtime_terror.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.MyViewHolder> {

    List<ProductItem> dataset;
    List<OrderUpdatesListener> preparedMealListeners = new ArrayList<>();
    public String purpose = "N/A";
    public Context mContext;

    public ItemChanged itemChanged;

    public Activity parentActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder implements EditItemInterface {
        RelativeLayout container;
        CircleImageView image;
        TextView title;
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
            price = v.findViewById(R.id.price);
            reqs = v.findViewById(R.id.food_reqs);
            qty = v.findViewById(R.id.food_qty);
            prepared = v.findViewById(R.id.food_preparation_completed);
            options = v.findViewById(R.id.options);
            this.setVisibilities(purpose);
        }


        private void setVisibilities(String purpose){
            if (purpose.equals("delivery") || purpose.equals("operations")){
                price.setVisibility(View.GONE);
                options.setVisibility(View.GONE);

                if(purpose.equals("operations"))
                    prepared.setVisibility(View.GONE);

            } else if (purpose.equals("client")) {
                prepared.setVisibility(View.GONE);
            }
        }

        public void setImage(String imageLink) {

            Glide.with(mContext).load(imageLink).into(this.image);
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }

        public void setReqs(String reqs) {
            this.reqs.setText(reqs);
        }

        public void setPrice(double price, int qty) {
            double totalPrice = ((double)((int) (price * qty * 100)) / 100);
            this.price.setText(totalPrice + " Lei");
        }

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
            notifyDataSetChanged();
            itemChanged.onItemChange();
        }

        public void applyTranslateAnimation() {
            container.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_transition));
        }
    }

    public FoodListAdapter(List<ProductItem> dataset, String purpose, Context context, ItemChanged listener){
        this.dataset = dataset;
        this.purpose = purpose;
        this.mContext = context;
        this.itemChanged = listener;
    }

    public FoodListAdapter(List<ProductItem> dataset, String purpose, Context context, ItemChanged listener, Activity act){
        this.dataset = dataset;
        this.purpose = purpose;
        this.mContext = context;
        this.itemChanged = listener;
        this.parentActivity = act;
    }

    public FoodListAdapter(List<ProductItem> dataset, String purpose, Context context){
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
        myViewHolder.setPrice(dataset.get(position).getPrice(), dataset.get(position).getQty());

        myViewHolder.setPrepared(dataset.get(position).isPrepared());


        if(purpose.equals("delivery")) {
            myViewHolder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataset.get(myViewHolder.getAdapterPosition()).setPrepared(!myViewHolder.prepared.isChecked());
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
                dialog.setActivityForDialog(parentActivity);
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
        for(ProductItem productItem : dataset)
            if(!productItem.isPrepared())
                return false;
        return true;
    }

    public void notifyOrderComplete(boolean isComplete){
        for(OrderUpdatesListener listener : preparedMealListeners)
            listener.onOrderUpdate(isComplete);
    }

}
