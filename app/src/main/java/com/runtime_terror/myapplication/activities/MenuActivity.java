package com.runtime_terror.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.adapters.StaffPagerAdapter;
import com.runtime_terror.myapplication.fragments.CategoriesMenuFragment;


public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setupToolbar();
        setupViewPagerAndTablayout();
    }
    public void viewOrderDetails(View view){
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        startActivity(intent);
        Toolbar myToolbar = findViewById(R.id.toolbar);
       setSupportActionBar(myToolbar);
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

    }
}
