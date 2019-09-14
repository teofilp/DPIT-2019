package com.runtime_terror.myapplication;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;


import com.runtime_terror.myapplication.adapters.CustomPagerAdapter;

public class Launcher extends AppCompatActivity {

    ViewPager viewPager;
    CustomPagerAdapter adapter;

    ImageButton scan;
    ImageButton avatar;
    View indicator;

    private ArgbEvaluator argbEvaluator;
    private int leftColor;
    private int rightColor;
    private int indicatorTranslationX;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                recreate();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        viewPager = findViewById(R.id.viewPager);
        scan = findViewById(R.id.scan_ic);
        avatar = findViewById(R.id.avatar_ic);
        indicator = findViewById(R.id.indicator);

        adapter = new CustomPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new BarcodeFragment(), "");
        adapter.addFragment(new RestaurantLoginFragment(), "");
        viewPager.setAdapter(adapter);
        setupPermissions();
        setupCustomTabsAnimations();

        handleLoginRedirect();
    }

    public void handleLoginRedirect() {
        if(getIntent().getBooleanExtra("redirectToLogin", false))
            viewPager.setCurrentItem(1);
    }

    private void setupCustomTabsAnimations(){

        argbEvaluator = new ArgbEvaluator();
        leftColor = ContextCompat.getColor(getApplicationContext(), R.color.white);
        rightColor = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);

        avatar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                indicatorTranslationX = (int)(avatar.getX() - indicator.getX());
                avatar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == 0) {
                    setColor(positionOffset);
                    moveIndicator(positionOffset);
                } else if (position == 1) {
                    setColor(1 - positionOffset);
                    moveIndicator(1 - positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void setColor(float fractionFromCenter) {
        int color = (int) argbEvaluator.evaluate(fractionFromCenter, leftColor, rightColor);

        avatar.setColorFilter(color);
        scan.setColorFilter(color);
        indicator.setBackgroundColor(color);

    }

    private void moveIndicator(float fractionFromCenter) {
        indicator.setTranslationX(fractionFromCenter * indicatorTranslationX);
    }

    public void setupPermissions() {
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);
    }
}
