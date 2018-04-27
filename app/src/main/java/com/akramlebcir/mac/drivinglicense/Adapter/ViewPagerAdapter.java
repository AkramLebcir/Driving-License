package com.akramlebcir.mac.drivinglicense.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {


    private final List<Fragment> listfragment = new ArrayList<>();
    private final List<String> listtitle = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listfragment.get(position);
    }

    @Override
    public int getCount() {
        return listtitle.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listtitle.get(position);
    }

    public void AddFragment(Fragment fragment, String title){
        listfragment.add(fragment);
        listtitle.add(title);
    }
}
