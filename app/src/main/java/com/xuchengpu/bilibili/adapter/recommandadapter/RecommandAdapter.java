package com.xuchengpu.bilibili.adapter.recommandadapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.xuchengpu.bilibili.base.BaseViewPager;

import java.util.List;

/**
 * Created by 许成谱 on 2017/3/23 10:56.
 * qq:1550540124
 * for:
 */

public class RecommandAdapter extends PagerAdapter {
    private final Context mContext;
    private final List<BaseViewPager> datas;
    String[] titles;


    public RecommandAdapter(Context mContext, List<BaseViewPager> viewPagers, String[] titles) {
        this.mContext=mContext;
        this.datas=viewPagers;
        this.titles=titles;

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View rootView = datas.get(position).rootView;
        container.addView(rootView);
        return rootView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return datas.size();
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
