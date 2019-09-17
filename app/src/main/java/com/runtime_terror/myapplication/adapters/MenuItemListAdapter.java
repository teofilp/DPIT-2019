package com.runtime_terror.myapplication.adapters;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.interfaces.CartListener;
import com.runtime_terror.myapplication.interfaces.EditItemInterface;
import com.runtime_terror.myapplication.models.EditItemDialog;
import com.runtime_terror.myapplication.models.ProductItem;

import java.util.List;

public class MenuItemListAdapter extends RecyclerView.Adapter<MenuItemListAdapter.MyViewHolder> {

    private List<ProductItem> menuItemsList;
    private Context context;
    private CartListener listener;

    public MenuItemListAdapter(Context context, List<ProductItem> menuItemsList, CartListener listener) {
        this.menuItemsList = menuItemsList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.setImage(menuItemsList.get(position).getImage());
        holder.setTitle(menuItemsList.get(position).getTitle());
        holder.setPrice(menuItemsList.get(position).getPrice());
        holder.setDescription(menuItemsList.get(position).getDescription());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("menuItem pressed", menuItemsList.get(position).getTitle());
                EditItemDialog addDialog = new EditItemDialog(context, holder, menuItemsList.get(position));
                addDialog.registerCartListener(listener);
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
        ImageView image;
        TextView title;
        TextView price;
        TextView description;

        public MyViewHolder(View itemView) {
            super(itemView);

            container = (RelativeLayout) itemView;
                image = itemView.findViewById(R.id.icon);
                title = itemView.findViewById(R.id.title);
                price = itemView.findViewById(R.id.price);
                description= itemView.findViewById(R.id.description);

            }

        public void setImage(String image) {
            this.image.setImageResource(R.drawable.food);
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }

        public void setPrice(double price) {
            this.price.setText("$" + price);
        }

        public void setDescription(String description) { this.description.setText(description); }

        @Override
        public int getQty() {
            return 0;
        }

        @Override
        public void setQty(int quantity) {

        }

        @Override
        public String getReqs() {
            return null;
        }

        @Override
        public void setReqs(String reqs) {

        }

        @Override
        public List getDataSet() {
            return menuItemsList;
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
        public void itemChanged() {

        }

        @Override
        public String getTranslation(int resource) {
            return context.getString(resource);
        }

    }
}

