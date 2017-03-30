package com.xuchengpu.bilibili.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.xuchengpu.bilibili.base.BaseViewPager;

import java.util.ArrayList;

/**
 * Created by 许成谱 on 2017/3/21 16:03.
 * qq:1550540124
 * for:
 */

public class MainViewPagerAdapter extends PagerAdapter {

    private final ArrayList<BaseViewPager> data;
    private final String[] titles;

    public MainViewPagerAdapter(ArrayList<BaseViewPager> basePagers, String[] titles) {
        this.data=basePagers;
        this.titles=titles;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BaseViewPager basePager=data.get(position);
        View view = basePager.rootView;
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
