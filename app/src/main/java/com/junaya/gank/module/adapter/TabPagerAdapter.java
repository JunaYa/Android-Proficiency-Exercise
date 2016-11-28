package com.junaya.gank.module.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;

import com.junaya.gank.module.fragment.GankFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aya on 2016/11/24.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {


    private static final List<String> initTabs() {
        List<String> tabs = new ArrayList<>();
        tabs.add("Android");
        tabs.add("iOS");
        tabs.add("前端");
        return tabs;
    }

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return GankFragment.newFragment(initTabs().get(position));
    }

    @Override
    public int getCount() {
        return initTabs().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //
        return new SpannableString(initTabs().get(position));
    }
}
