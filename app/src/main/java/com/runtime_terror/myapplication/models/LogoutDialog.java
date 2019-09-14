package com.runtime_terror.myapplication.models;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.runtime_terror.myapplication.Launcher;
import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.RestaurantLoginFragment;
import com.runtime_terror.myapplication.SignupActivity;
import com.runtime_terror.myapplication.activities.StaffActivity;


public class LogoutDialog extends Dialog {
    private Button cancelButton;
    private Button logoutButton;

    public LogoutDialog(Context mContext){
        super(mContext);
        setContentView(R.layout.logout_dialog);
        setCancelable(false);
        //Item binding
        this.cancelButton = findViewById(R.id.cancelDialog);
        this.logoutButton = findViewById(R.id.logoutButton);
    }

    public void setupDialog(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(v.getContext(), Launcher.class);
                intent.putExtra("redirectToLogin", true);
                v.getContext().startActivity(intent);

            }
        });
        show();
    }
}
