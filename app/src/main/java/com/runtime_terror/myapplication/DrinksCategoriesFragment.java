package com.runtime_terror.myapplication;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runtime_terror.myapplication.adapters.StaffPagerAdapter;
import com.runtime_terror.myapplication.fragments.CategoriesMenuFragment;


public class DrinksCategoriesFragment extends Fragment {
    View view;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.drinks_categories_fragment, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.pager);

        StaffPagerAdapter adapter = new StaffPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new CategoriesMenuFragment(), "Tea");
        adapter.addFragment(new CategoriesMenuFragment(), "Coffee");
        adapter.addFragment(new CategoriesMenuFragment(), "Refreshments");
        adapter.addFragment(new CategoriesMenuFragment(), "Non-Alcoholic Cocktails");
        adapter.addFragment(new CategoriesMenuFragment(), "Alcoholic Cocktails");
        adapter.addFragment(new CategoriesMenuFragment(), "Beer & Cider ");
        adapter.addFragment(new CategoriesMenuFragment(), "Wine");

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(adapter);

        return view;
    }


}

