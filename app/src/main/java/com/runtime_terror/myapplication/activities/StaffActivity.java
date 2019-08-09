package com.runtime_terror.myapplication;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.runtime_terror.myapplication.adapters.StaffPagerAdapter;

public class StaffActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        setupToolbar();
        setupViewPagerAndTablayout();
    }

    private void setupViewPagerAndTablayout() {

        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tablayout);

        StaffPagerAdapter adapter = new StaffPagerAdapter(getSupportFragmentManager());

        OrderContainerFragment kitchen = new OrderContainerFragment();
        kitchen.setPurpose("food");

        OrderContainerFragment bar = new OrderContainerFragment();
        bar.setPurpose("food");

        OrderContainerFragment waiter = new OrderContainerFragment();
        waiter.setPurpose("requests");

        adapter.addFragment(kitchen, "");
        adapter.addFragment(bar, "");
        adapter.addFragment(waiter, "");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_hamburger);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_cocktail);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_q_mark);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Staff Activity");

    }
}
