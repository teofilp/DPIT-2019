package com.runtime_terror.myapplication;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class DisplayMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_menu);
        String barcodeData = getIntent().getStringExtra(EXTRA_MESSAGE);
        String[] split = barcodeData.split(" ");
        String restPath = split[0];
        int tableNr = Integer.parseInt(split[1]);
        Log.d("clientela", restPath);
        Log.d("clientela", tableNr+"");
    }

    public void viewOrderDetails(View view){
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        startActivity(intent);
//        Toolbar myToolbar = findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
    }

}
