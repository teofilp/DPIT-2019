package com.runtime_terror.myapplication.models;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.database.FirestoreSetup;

public class HelpDialog extends Dialog {

    private Button cancelButton;
    private Button callHelpButton;

    public HelpDialog(Context mContext){
        super(mContext);
        setContentView(R.layout.help_dialog);
        setCancelable(false);
        //Item binding
        this.cancelButton = findViewById(R.id.cancelHelp);
        this.callHelpButton = findViewById(R.id.callWaiterButton);
    }

    public void setupDialog(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        callHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeHelpRequest();
                dismiss();
            }
        });
        show();
    }

    private void placeHelpRequest() {

        final int RANDOM_TABLE_NUMBER = 213;
        final String restaurantId = "lrApMZq9rBNLQGtzVjKa";
        HelpOrder order = new HelpOrder(RANDOM_TABLE_NUMBER);

        FirebaseFirestore db = new FirestoreSetup().getDb();

        db.collection("RESTAURANTS").document(restaurantId)
                .collection("ORDERS")
                .add(order)
                .addOnSuccessListener(documentReference -> {
                    Log.d("Order id", documentReference.getId());
                    Toast.makeText(getContext(), "Your request has been sent. Wait for the waiter to come and help you",
                            Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(e -> {
                    Log.e("could not place order", e.toString());
                    Toast.makeText(getContext(), "Your request could not be send. Try again later",
                            Toast.LENGTH_LONG).show();
                });


    }
}
