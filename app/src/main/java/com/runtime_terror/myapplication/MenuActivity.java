package com.runtime_terror.myapplication;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.adapters.StaffPagerAdapter;


public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setupToolbar();
        setupViewPagerAndTablayout();
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
        adapter.addFragment(new CategoriesMenuFragment(), "Alcoholic Drinks");
        adapter.addFragment(new CategoriesMenuFragment(), "Non-Alcoholic Drinks");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }


    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("MENU");

    }
}
