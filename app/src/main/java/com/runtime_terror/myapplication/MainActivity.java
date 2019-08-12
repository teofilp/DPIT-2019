package com.runtime_terror.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.runtime_terror.myapplication.activities.MenuActivity;
import com.runtime_terror.myapplication.activities.StaffActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchClientSideActivity(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    private void launchStaffSideActivity(View view){
        Intent intent = new Intent(this, StaffActivity.class);
        startActivity(intent);

    }
}
