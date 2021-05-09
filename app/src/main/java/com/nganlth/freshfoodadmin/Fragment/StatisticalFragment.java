package com.nganlth.freshfoodadmin.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nganlth.freshfoodadmin.Adapter.TabAdapter;
import com.nganlth.freshfoodadmin.R;
import com.nganlth.freshfoodadmin.TabFragment.Tab_Statistical_Day;
import com.nganlth.freshfoodadmin.TabFragment.Tab_Statistical_Month;
import com.nganlth.freshfoodadmin.TabFragment.Tab_Statistical_Year;

public class StatisticalFragment extends Fragment {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistical,container,false);

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);


        adapter = new TabAdapter(getActivity().getSupportFragmentManager());

        adapter.addFragment(new Tab_Statistical_Day(), "");
        adapter.addFragment(new Tab_Statistical_Month(), "");
        adapter.addFragment(new Tab_Statistical_Year(), "");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic__4_hours);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_june_calendar_monthly_page);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_calendar);

        return view;
    }
}
