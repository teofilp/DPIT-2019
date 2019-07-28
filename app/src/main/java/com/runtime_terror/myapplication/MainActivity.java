package com.runtime_terror.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(getApplicationContext(), StaffActivity.class));
        findViewById(R.id.tempStaffSide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchStaffSideActivity(view);
            }
        });
    }

    public void launchClientSideActivity(View view){
        Intent intent = new Intent(this, DisplayMenuActivity.class);
        startActivity(intent);
    }

    private void launchStaffSideActivity(View view){
        Intent intent = new Intent(this, KitchenTableOrder.class);
        startActivity(intent);

    }
}
