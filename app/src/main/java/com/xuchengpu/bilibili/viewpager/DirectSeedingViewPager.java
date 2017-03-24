package com.xuchengpu.bilibili.viewpager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;

import com.alibaba.fastjson.JSON;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.adapter.directseedingadapter.DirectSeedingAdapter;
import com.xuchengpu.bilibili.base.BaseViewPager;
import com.xuchengpu.bilibili.bean.DirectSeedingTypeBean;
import com.xuchengpu.bilibili.utils.ConstantUtils;

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
        return ConstantUtils.DIRECTSEEDING_CONTENT;
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
