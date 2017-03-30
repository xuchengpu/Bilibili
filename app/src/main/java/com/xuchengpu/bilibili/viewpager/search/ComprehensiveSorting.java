package com.xuchengpu.bilibili.viewpager.search;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.adapter.ComprehensiveSortingAdapter;
import com.xuchengpu.bilibili.base.BaseViewPager;
import com.xuchengpu.bilibili.bean.ComprehensiveSearchBean;

import butterknife.BindView;

/**
 * Created by 许成谱 on 2017/3/30 15:53.
 * qq:1550540124
 * for:
 */

public class ComprehensiveSorting extends BaseViewPager {
    private  ComprehensiveSearchBean.DataBean data;
    @BindView(R.id.rv_comprehensive_sorting)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_comprehensive_sorting)
    SwipeRefreshLayout swipe;

    public ComprehensiveSorting(Context context, ComprehensiveSearchBean.DataBean data) {
        super(context);
        this.mContext=context;
        this.data=data;
        setadapter(data);

    }

    private void setadapter(ComprehensiveSearchBean.DataBean data) {

        ComprehensiveSortingAdapter adapter=new ComprehensiveSortingAdapter(mContext,data.getItems().getArchive());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    protected int getChildLayoutId() {
        return R.layout.comprehensive_sorting;
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

    }
}
