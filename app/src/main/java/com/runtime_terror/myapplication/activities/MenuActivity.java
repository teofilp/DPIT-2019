package com.runtime_terror.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.common.io.LittleEndianDataInputStream;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.firestore.FirebaseFirestore;
//import com.runtime_terror.myapplication.database.FirestoreSetup;

import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.adapters.CustomPagerAdapter;
import com.runtime_terror.myapplication.fragments.CategoriesMenuFragment;
import com.runtime_terror.myapplication.fragments.ComplexCategoriesMenuFragment;
import com.runtime_terror.myapplication.models.HelpDialog;

import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MenuActivity extends AppCompatActivity {

    private Context mContext;
    private Intent mIntent;
    private FirebaseFirestore db;
    private CollectionReference menuRef;
    private int tableNr;
    private ArrayList<String> foodTypes;
    private final String TAG = "DebugMenu";// for debug purposes only

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setupToolbar();
        setupDataBase();
        setupViewPagerAndTablayout();
        setupCartButton();

        mContext = this;
        mIntent = new Intent(this,OrderDetailsActivity.class);
    }

    private void setupDataBase() {
        db = FirebaseFirestore.getInstance();
        //Log.d(TAG, barcodeData.substring(1, barcodeData.indexOf(" ")) + "/MENU");
        menuRef = db.collection("RESTAURANTS/" + getIntent().getStringExtra("rest") + "/MENU");
        try {
            tableNr = getIntent().getIntExtra("tableNr", 0);
            //Log.d(TAG, tableNr + "");
        } catch(NumberFormatException nfe) {
            Log.d(TAG, "Table number is invalid.");
        }
    }

    private void setupViewPagerAndTablayout() {

        ViewPager viewPager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        CustomPagerAdapter adapter = new CustomPagerAdapter(getSupportFragmentManager());

        setupTablayout(adapter);
        //Log.d(TAG, "After setupTablayout");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupTablayout(CustomPagerAdapter adapter) {

        menuRef.get().addOnCompleteListener(task -> {
            Log.d(TAG, "Loading categories...");
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot foodCategory : task.getResult()) {
                    Log.d(TAG, foodCategory.getString("food_category"));

                    Bundle fragData = new Bundle();
                    foodTypes = (ArrayList<String>) foodCategory.get("food_types");
                    fragData.putString("path", foodCategory.getReference().getPath());
                    fragData.putStringArrayList("foodTypes", foodTypes);

                    if(foodCategory.getBoolean("isComplex")==null) {
                        CategoriesMenuFragment frag = new CategoriesMenuFragment();
                        frag.setArguments(fragData);
                        adapter.addFragment(frag, foodCategory.getString("food_category"));
                    }
                    else {
                        /*ComplexCategoriesMenuFragment Cfrag = new ComplexCategoriesMenuFragment();
                        Cfrag.setArguments(fragData);
                        adapter.addFragment(Cfrag, foodCategory.getString("food_category"));*/
                    }
                }
                Log.d(TAG, "All loaded.");
            }
            else {
                Log.d(TAG, "Error getting food categories documents.", task.getException());
            }
        });
    }


    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Restaurant name");

        ImageButton helpButton = findViewById(R.id.imageButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpDialog helpDialog = new HelpDialog(mContext);
                helpDialog.setupDialog();
            }
        });

    }

    private void setupCartButton(){

        FloatingActionButton cartButton = findViewById(R.id.cartButton);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mIntent);
            }
        });
    }
}
