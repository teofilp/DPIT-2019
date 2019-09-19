package com.runtime_terror.myapplication.activities;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;

import android.util.Pair;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.runtime_terror.myapplication.interfaces.CartListener;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.adapters.CustomPagerAdapter;
import com.runtime_terror.myapplication.fragments.CategoriesMenuFragment;

import com.runtime_terror.myapplication.models.MyApplication;
import com.runtime_terror.myapplication.models.ProductItem;

import com.runtime_terror.myapplication.models.HelpDialog;

import java.util.ArrayList;
import java.util.List;



public class MenuActivity extends AppCompatActivity {

    private Context mContext;
    private Intent mIntent;
    private FirebaseFirestore db;
    private CollectionReference menuRef;
    private int tableNr;
    private ArrayList<String> foodTypes;
    private final String TAG = "DebugMenu";// for debug purposes only

    List<Pair<ProductItem, Integer>> cartList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ((MyApplication) this.getApplication()).setCartList(cartList);
        ((MyApplication) this.getApplication()).setCartItems((findViewById(R.id.cart_count)));

        setupToolbar();
        setupDataBase();
        setupViewPagerAndTablayout();
        setupCartButton();

        mContext = this;
        mIntent = new Intent(this, OrderDetailsActivity.class);
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

                        frag.registerCartListener(new CartListener() {
                            @Override
                            public void addToCart(Pair<ProductItem, Integer> food) {
                                cartList.add(food);
                                if(cartList.size() > 0) {
                                    findViewById(R.id.cart_count).setVisibility(View.VISIBLE);
                                    ((TextView) findViewById(R.id.cart_count)).setText(Integer.toString(cartList.size()));
                                }
                                else {
                                    findViewById(R.id.cart_count).setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void removeFromCart(String productTitle) {
                                Toast.makeText(mContext, productTitle, Toast.LENGTH_SHORT).show();
                            }
                        });

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

                String cartJSON = new Gson().toJson(cartList);
                mIntent.putExtra("cart", cartJSON);

                startActivity(mIntent);
            }
        });
    }
}
