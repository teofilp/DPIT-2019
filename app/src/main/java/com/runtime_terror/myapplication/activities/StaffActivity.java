package com.runtime_terror.myapplication.activities;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.runtime_terror.myapplication.Launcher;
import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.fragments.OrderContainerFragment;

import com.runtime_terror.myapplication.adapters.CustomPagerAdapter;
import com.runtime_terror.myapplication.models.BillOrder;
import com.runtime_terror.myapplication.models.DrinkOrder;
import com.runtime_terror.myapplication.models.FoodOrder;
import com.runtime_terror.myapplication.models.HelpDialog;
import com.runtime_terror.myapplication.models.HelpOrder;
import com.runtime_terror.myapplication.models.LogoutDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StaffActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        setupToolbar();
        setupViewPagerAndTablayout();
        mContext=this;
    }

    private void setupViewPagerAndTablayout() {

        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tablayout);

        CustomPagerAdapter adapter = new CustomPagerAdapter(getSupportFragmentManager());

        OrderContainerFragment kitchen = new OrderContainerFragment();
        kitchen.setPurpose("food");
        kitchen.loadOrder(new ArrayList<Integer>(
                Arrays.asList(
                        FoodOrder.FOOD_ORDER_TYPE
                )
        ));
        OrderContainerFragment bar = new OrderContainerFragment();
        bar.setPurpose("food");
        bar.loadOrder(new ArrayList<Integer>(
                Arrays.asList(
                        DrinkOrder.DRINK_ORDER_TYPE
                )
        ));

        OrderContainerFragment waiter = new OrderContainerFragment();
        waiter.setPurpose("operations");

        waiter.loadOrder(new ArrayList<Integer>(
                Arrays.asList(
                        BillOrder.BILL_ORDER_TYPE,
                        HelpOrder.HELP_ORDER_TYPE
                        )
        ));


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

        ImageButton logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutDialog logoutDialog = new LogoutDialog(mContext);
                logoutDialog.setupDialog();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(getApplicationContext(), Launcher.class);
            intent.putExtra("redirectToLogin", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}