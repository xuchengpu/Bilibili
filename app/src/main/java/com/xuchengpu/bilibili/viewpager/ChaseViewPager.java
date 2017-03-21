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

public class ChaseViewPager extends BasePager{
    public ChaseViewPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view=View.inflate(mContext, R.layout.chase,null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
