package com.runtime_terror.myapplication.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runtime_terror.myapplication.OrderUpdatesListener;
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
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

        public void setPrice(double price) {
            this.price.setText("$" + price);
        }

        public void setQty(int qty) {
            this.qty.setText("QTY: " + qty);
        }

        public void setPrepared(Boolean prepared) {
            this.prepared.setChecked(prepared);
        }

        public void applyTranslateAnimation() {
            container.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_transition));
        }
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

                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.edit_item_dialog);
                dialog.setCancelable(false);

                //Setup qty picker

                final ImageButton decreaseButtton = dialog.findViewById(R.id.qtyDecrease);
                final ImageButton increaseButton = dialog.findViewById(R.id.qtyIncrease);
                final TextView qtyDisplay = dialog.findViewById(R.id.qtyDisplay);

                qtyDisplay.setText("1");

                final int initialQty = Integer.parseInt(qtyDisplay.getText().toString());

                decreaseButtton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!qtyDisplay.getText().toString().equals("1")){
                            int qty = Integer.parseInt(qtyDisplay.getText().toString()) - 1;
                            qtyDisplay.setText(qty+"");
                        }
                    }
                });

                increaseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            int qty = Integer.parseInt(qtyDisplay.getText().toString()) + 1;
                            qtyDisplay.setText(qty+"");
                    }
                });

                //Setup delete button

                ImageButton deleteButton = dialog.findViewById(R.id.deleteButton);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = myViewHolder.getAdapterPosition();
                        dialog.dismiss();
                        dataset.remove(position);
                        notifyItemRemoved(position);
                    }
                });


                //Setup Cancel and Save buttons

                Button saveButton = dialog.findViewById(R.id.saveEditButton);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myViewHolder.setQty(Integer.parseInt(qtyDisplay.getText().toString()));

                        EditText reqsEditor = dialog.findViewById(R.id.requirementsEditor);
                        String reqs = reqsEditor.getText().toString();
                        if(reqs.equals("")){
                            myViewHolder.setReqs(mContext.getString(R.string.noReqs));
                        }
                        else {
                            myViewHolder.setReqs(reqs);
                        }

                        dialog.dismiss();
                    }
                });

                Button cancelButton = dialog.findViewById(R.id.cancelEditButton);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myViewHolder.setQty(initialQty);

                        dialog.dismiss();
                    }
                });

                dialog.show();
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
