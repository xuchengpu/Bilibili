package com.xuchengpu.bilibili.viewpager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;

import com.alibaba.fastjson.JSON;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.adapter.DirectSeedingAdapter;
import com.xuchengpu.bilibili.base.BaseViewPager;
import com.xuchengpu.bilibili.bean.DirectSeedingTypeBean;

import butterknife.BindView;

/**
 * Created by 许成谱 on 2017/3/21 15:45.
 * qq:1550540124
 * for:
 */

public class DirectSeedingViewPager extends BaseViewPager {


    @BindView(R.id.rv_directseeding)
    RecyclerView rvDirectseeding;
    @BindView(R.id.ib_top)
    ImageButton ibTop;

    public DirectSeedingViewPager(Context context) {
        super(context);

    }


    @Override
    protected int getChildLayoutId() {
        return R.layout.directseeding;
    }

    @Override
    protected String getChildUrl() {
        return "http://live.bilibili.com/AppNewIndex/common?_device=android&appkey=1d8b6e7d45233436&build=501000&mobi_app=android&platform=android&scale=xxhdpi&ts=1490021691000&sign=f283ef788f4b302d0d1783e3f3e98aa4";
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData(String json) {
        DirectSeedingTypeBean bean= JSON.parseObject(json,DirectSeedingTypeBean.class);
        DirectSeedingTypeBean.DataBean data = bean.getData();


        DirectSeedingAdapter adapter=new DirectSeedingAdapter(mContext,data);

        rvDirectseeding.setAdapter(adapter);
        rvDirectseeding.setLayoutManager(new LinearLayoutManager(mContext));

    }

}
