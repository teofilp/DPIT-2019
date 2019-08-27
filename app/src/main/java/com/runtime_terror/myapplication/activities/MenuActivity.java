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

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.runtime_terror.myapplication.fragments.DrinksCategoriesFragment;
import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.adapters.CustomPagerAdapter;
import com.runtime_terror.myapplication.fragments.CategoriesMenuFragment;
import com.runtime_terror.myapplication.models.HelpDialog;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class MenuActivity extends AppCompatActivity {

    private Context mContext;
    private Intent mIntent;
    private FirebaseFirestore db;
    private CollectionReference menuRef;
    private int tableNr;
    private String TAG = "DebugMenu";// for debug purposes only

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
        Intent intent = getIntent();
        String barcodeData = intent.getStringExtra(EXTRA_MESSAGE);
        try {
            tableNr = Integer.parseInt(barcodeData.substring(barcodeData.indexOf(" ") + 1));
            //Log.d(TAG, tableNr + "");
        } catch(NumberFormatException nfe) {
            Log.d(TAG, "Table number is invalid.");
        }

        db = FirebaseFirestore.getInstance();
        //Log.d(TAG, barcodeData.substring(1, barcodeData.indexOf(" ")) + "/MENU");
        menuRef = db.collection(barcodeData.substring(1, barcodeData.indexOf(" ")) + "/MENU");
        //Log.d(TAG, "After menuRef=...");
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

        menuRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d(TAG, "Before task.isSuccessful()");
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot foodCategory : task.getResult()) {
                        Log.d(TAG, foodCategory.getId());
                    }
                } else {
                    Log.d(TAG, "Error getting documents.", task.getException());
                }
            }
        });

        /*adapter.addFragment(new CategoriesMenuFragment(), "Soups");
        adapter.addFragment(new CategoriesMenuFragment(), "Pizza");
        adapter.addFragment(new CategoriesMenuFragment(), "Main Course");
        adapter.addFragment(new CategoriesMenuFragment(), "Side Dishes");
        adapter.addFragment(new CategoriesMenuFragment(), "Dessert");
        adapter.addFragment(new DrinksCategoriesFragment(),"Drinks");*/
        //Log.d(TAG, "Instead of hardcoded categories");
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
