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

import com.google.gson.Gson;
import com.runtime_terror.myapplication.interfaces.AddToCartListener;
import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.adapters.StaffPagerAdapter;
import com.runtime_terror.myapplication.fragments.CategoriesMenuFragment;
import com.runtime_terror.myapplication.models.Food;
import com.runtime_terror.myapplication.models.HelpDialog;

import java.util.ArrayList;
import java.util.List;


public class MenuActivity extends AppCompatActivity {

    private Context mContext;
    private Intent mIntent;

    List<Pair<Food, Integer>> cartList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setupToolbar();
        setupViewPagerAndTablayout();
        setupCartButton();

        mContext = this;
        mIntent = new Intent(this, OrderDetailsActivity.class);
    }

    private void setupViewPagerAndTablayout() {

        ViewPager viewPager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        StaffPagerAdapter adapter = new StaffPagerAdapter(getSupportFragmentManager());

        String[] cateogories = {"Soups", "Pizza", "Main Course", "Side Dishes", "Dessert", "Drinks"};

        for(String category: cateogories) {

            CategoriesMenuFragment fragment = new CategoriesMenuFragment();
            fragment.registerAddToCartListener(new AddToCartListener() {
                @Override
                public void addToCart(Pair<Food, Integer> food) {
                    cartList.add(food);
                    if(cartList.size() > 0) {
                        findViewById(R.id.cart_count).setVisibility(View.VISIBLE);
                        ((TextView) findViewById(R.id.cart_count)).setText(Integer.toString(cartList.size()));
                    }
                    else {
                        findViewById(R.id.cart_count).setVisibility(View.GONE);
                    }
                }
            });

            adapter.addFragment(fragment, category);

        }

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

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
