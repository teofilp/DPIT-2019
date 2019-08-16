package com.runtime_terror.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.runtime_terror.myapplication.DrinksCategoriesFragment;
import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.adapters.StaffPagerAdapter;
import com.runtime_terror.myapplication.fragments.CategoriesMenuFragment;
import com.runtime_terror.myapplication.models.HelpDialog;


public class MenuActivity extends AppCompatActivity {

    private Context mContext;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setupToolbar();
        setupViewPagerAndTablayout();
        setupCartButton();

        mContext = this;
        mIntent = new Intent(this,OrderDetailsActivity.class);
    }

    private void setupViewPagerAndTablayout() {

        ViewPager viewPager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        StaffPagerAdapter adapter = new StaffPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new CategoriesMenuFragment(), "Soups");
        adapter.addFragment(new CategoriesMenuFragment(), "Pizza");
        adapter.addFragment(new CategoriesMenuFragment(), "Main Course");
        adapter.addFragment(new CategoriesMenuFragment(), "Side Dishes");
        adapter.addFragment(new CategoriesMenuFragment(), "Dessert");
        adapter.addFragment(new DrinksCategoriesFragment(),"Drinks");

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
                startActivity(mIntent);
            }
        });
    }
}
