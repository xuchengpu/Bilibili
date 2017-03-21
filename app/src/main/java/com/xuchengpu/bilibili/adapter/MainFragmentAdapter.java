package com.xuchengpu.bilibili.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 许成谱 on 2017/3/21 18:51.
 * qq:1550540124
 * for:
 */

public class MainFragmentAdapter extends FragmentPagerAdapter {
    private final List<Fragment> datas;
    private final String[] titles;

    public MainFragmentAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
        super(fm);
        this.datas=fragments;
        this.titles=titles;
    }


    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
