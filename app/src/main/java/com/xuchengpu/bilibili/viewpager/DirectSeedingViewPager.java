package com.xuchengpu.bilibili.viewpager;

import android.content.Context;
import android.view.View;

import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.base.BasePager;

/**
 * Created by 许成谱 on 2017/3/21 15:45.
 * qq:1550540124
 * for:
 */

public class DirectSeedingViewPager extends BasePager{
    public DirectSeedingViewPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view=View.inflate(mContext, R.layout.directseeding,null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
