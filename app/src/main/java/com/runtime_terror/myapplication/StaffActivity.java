package com.runtime_terror.myapplication;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.runtime_terror.myapplication.adapters.CustomPagerAdapter;

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

        CustomPagerAdapter adapter = new CustomPagerAdapter(getSupportFragmentManager());

        OrderContainerFragment kitchen = new OrderContainerFragment();
        kitchen.setPurpose("food");

        OrderContainerFragment bar = new OrderContainerFragment();
        bar.setPurpose("food");

        OrderContainerFragment waiter = new OrderContainerFragment();
        waiter.setPurpose("operations");

        adapter.addFragment(kitchen, "Kitchen");
        adapter.addFragment(bar, "Bar");
        adapter.addFragment(waiter, "Waiter");

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
