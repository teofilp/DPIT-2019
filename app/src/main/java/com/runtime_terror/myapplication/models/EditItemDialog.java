package com.runtime_terror.myapplication.models;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.runtime_terror.myapplication.interfaces.CartListener;
import com.runtime_terror.myapplication.interfaces.EditItemInterface;
import com.runtime_terror.myapplication.R;

public class EditItemDialog extends Dialog {

    private int initialQty;

    private ImageButton increaseButton;
    private ImageButton decreaseButton;
    private TextView qtyDisplay;
    private EditText reqsEditor;
    private TextView deleteLabel;
    private ImageButton deleteButton;
    private Button saveButton;
    private TextView dialogTitle;
    private TextView dialogDescription;
    private Button cancelButton;

    private EditItemInterface editItemInterface;
    private CartListener cartListener;

    private String purpose;
    private ProductItem productItemItem;

    private Activity parentActivity;


    public EditItemDialog(Context mContext, EditItemInterface editItemInterface, ProductItem productItemItem){

        //Dialog creation
        super(mContext);
        setContentView(R.layout.edit_item_dialog);
        setCancelable(false);
        //Item binding
        this.productItemItem = productItemItem;

        this.editItemInterface = editItemInterface;
        this.increaseButton = findViewById(R.id.qtyIncrease);
        this.decreaseButton = findViewById(R.id.qtyDecrease);
        this.qtyDisplay = findViewById(R.id.qtyDisplay);
        this.reqsEditor = findViewById(R.id.requirementsEditor);
        this.deleteLabel = findViewById(R.id.deleteLabel);
        this.deleteButton = findViewById(R.id.deleteButton);
        this.saveButton = findViewById(R.id.saveEditButton);
        this.cancelButton = findViewById(R.id.cancelEditButton);
        this.dialogTitle= findViewById(R.id.dialogTitle);
        this.dialogDescription= findViewById(R.id.dialogDescription);
        bindDataToViews();
    }

    public void bindDataToViews()
    {
        this.dialogTitle.setText(this.productItemItem.getTitle());
        this.dialogDescription.setText(this.productItemItem.getDescription());
    }

    public void registerCartListener(CartListener listener) {
        this.cartListener = listener;
    }

    public void setVisibilities(String purpose){
        this.purpose = purpose;
        if(purpose.equals("addToCart")){
            deleteLabel.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            saveButton.setText(R.string.addToCartButton);
        }
        if(purpose.equals("editItem")){
            dialogDescription.setVisibility(View.GONE);
        }
    }

    private int getQty() {
       return Integer.parseInt(qtyDisplay.getText().toString());
    }
    public void setupDialog(){
        initialQty = editItemInterface.getQty() == 0 ? 1 : editItemInterface.getQty();
        qtyDisplay.setText(initialQty + "");
        reqsEditor.setText(editItemInterface.getReqs());
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = getQty() + 1;
                qtyDisplay.setText(qty + "");
                productItemItem.setQty(qty);
            }
        });

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = getQty();
                if(qty > 1)
                    qty = getQty() - 1;
                qtyDisplay.setText(qty + "");
                productItemItem.setQty(qty);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();
            }
        });

        final String dialogPurpose = this.purpose;

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                productItemItem.setQty(Integer.parseInt(qtyDisplay.getText().toString()));
                if(getQty() < 1) {
                    dismiss();
                    return;
                }

                String reqs = reqsEditor.getText().toString();
                if(reqs.equals("")){
                    productItemItem.setReqs(editItemInterface.getTranslation(R.string.noReqs));
                } else {
                    productItemItem.setReqs(reqs);
                }

                if(dialogPurpose.equals("addToCart"))
                    cartListener.addToCart(new Pair<>(ProductItem.copyProduct(productItemItem), Integer.parseInt(qtyDisplay.getText().toString())));

                editItemInterface.itemChanged();
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editItemInterface.setQty(initialQty);
                dismiss();
            }
        });
        show();
    }

    private void deleteItem(){
        int position = editItemInterface.getItemPosition();
        dismiss();
        removeProductFromCart(editItemInterface.getDataSet().get(position));
        editItemInterface.getDataSet().remove(position);
        editItemInterface.dialogNotifyItemRemoved(position);
        editItemInterface.itemChanged();
    }

    public void setActivityForDialog(Activity act) {
        parentActivity = act;
    }

    private void removeProductFromCart(Object o) {
        ProductItem item = (ProductItem) o;
        ((MyApplication)parentActivity.getApplication()).deleteItemFromCart(item.getTitle());
    }
}