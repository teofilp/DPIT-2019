package com.runtime_terror.myapplication.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.adapters.CustomPagerAdapter;
import com.runtime_terror.myapplication.fragments.CategoriesMenuFragment;


public class ComplexCategoriesMenuFragment extends Fragment {
    View view;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.drinks_categories_fragment, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.pager);

        CustomPagerAdapter adapter = new CustomPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new CategoriesMenuFragment(), "");
        adapter.addFragment(new CategoriesMenuFragment(), "");
        adapter.addFragment(new CategoriesMenuFragment(), "");
        adapter.addFragment(new CategoriesMenuFragment(), "");
        adapter.addFragment(new CategoriesMenuFragment(), "");
        adapter.addFragment(new CategoriesMenuFragment(), "");

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_coke_bottle);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_coffee_beans);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_cup_of_tea);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_beer);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_cocktail);
        tabLayout.getTabAt(5).setIcon(R.drawable.ic_glass_with_wine);

        return view;
    }


}

