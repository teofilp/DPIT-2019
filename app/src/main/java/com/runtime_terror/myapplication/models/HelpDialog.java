package com.runtime_terror.myapplication.models;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.runtime_terror.myapplication.R;

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
                dismiss();
            }
        });
        show();
    }
}
