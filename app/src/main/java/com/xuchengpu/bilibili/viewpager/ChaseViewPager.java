package com.xuchengpu.bilibili.viewpager;

import android.content.Context;
import android.widget.TextView;

import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.base.BaseViewPager;

import butterknife.BindView;

/**
 * Created by 许成谱 on 2017/3/21 15:45.
 * qq:1550540124
 * for:
 */

public class ChaseViewPager extends BaseViewPager {
    @BindView(R.id.tv_chase)
    TextView tvChase;

    public ChaseViewPager(Context context) {
        super(context);
    }


    @Override
    protected int getChildLayoutId() {
        return R.layout.chase;
    }

    @Override
    protected String getChildUrl() {
        return null;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData(String json) {
        tvChase.setText("zhuifan");
    }

}
