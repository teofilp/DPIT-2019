package com.runtime_terror.myapplication.models;

import android.app.Dialog;
import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.runtime_terror.myapplication.interfaces.AddToCartListener;
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
    private Button cancelButton;
    private EditItemInterface editItemInterface;
    private AddToCartListener addToCartListener;

    private String purpose;
    private Food foodItem;

    public EditItemDialog(Context mContext, EditItemInterface editItemInterface, Food foodItem){
        //Dialog creation
        super(mContext);
        setContentView(R.layout.edit_item_dialog);
        setCancelable(false);
        //Item binding
        this.foodItem = foodItem;
        this.editItemInterface = editItemInterface;
        this.increaseButton = findViewById(R.id.qtyIncrease);
        this.decreaseButton = findViewById(R.id.qtyDecrease);
        this.qtyDisplay = findViewById(R.id.qtyDisplay);
        this.reqsEditor = findViewById(R.id.requirementsEditor);
        this.deleteLabel = findViewById(R.id.deleteLabel);
        this.deleteButton = findViewById(R.id.deleteButton);
        this.saveButton = findViewById(R.id.saveEditButton);
        this.cancelButton = findViewById(R.id.cancelEditButton);
    }

    public void registerAddToCartListener(AddToCartListener listener) {
        this.addToCartListener = listener;
    }

    public void setVisibilities(String purpose){
        this.purpose = purpose;
        if(purpose.equals("addToCart")){
            deleteLabel.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            saveButton.setText(R.string.addToCartButton);
        }
    }

    public void setupDialog(){
        initialQty = editItemInterface.getQty();
        qtyDisplay.setText(initialQty + "");
        reqsEditor.setText(editItemInterface.getReqs());
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = Integer.parseInt(qtyDisplay.getText().toString()) + 1;
                qtyDisplay.setText(qty + "");
            }
        });

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = Integer.parseInt(qtyDisplay.getText().toString());
                if(qty > 1)
                    qty = Integer.parseInt(qtyDisplay.getText().toString()) - 1;
                qtyDisplay.setText(qty + "");
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = editItemInterface.getItemPosition();
                dismiss();
                editItemInterface.getDataSet().remove(position);
                editItemInterface.dialogNotifyItemRemoved(position);
            }
        });

        final String dialogPurpose = this.purpose;

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                editItemInterface.setQty(Integer.parseInt(qtyDisplay.getText().toString()));
                String reqs = reqsEditor.getText().toString();
                if(reqs.equals("")){
                    editItemInterface.setReqs(editItemInterface.getTranslation(R.string.noReqs));
                } else {
                    editItemInterface.setReqs(reqs);
                }

                if(dialogPurpose.equals("addToCart"))
                    addToCartListener.addToCart(new Pair<>(foodItem, Integer.parseInt(qtyDisplay.getText().toString())));

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

}
