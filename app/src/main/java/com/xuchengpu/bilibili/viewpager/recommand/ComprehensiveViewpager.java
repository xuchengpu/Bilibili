package com.xuchengpu.bilibili.viewpager.recommand;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.adapter.recommandadapter.ComprehensiveRecycleViewadapter;
import com.xuchengpu.bilibili.base.BaseViewPager;
import com.xuchengpu.bilibili.bean.RecommandComprehensiveBean;
import com.xuchengpu.bilibili.utils.ConstantUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 许成谱 on 2017/3/23 10:49.
 * qq:1550540124
 * for:
 */

public class ComprehensiveViewpager extends BaseViewPager {

    @BindView(R.id.recycleview_comprehensive_recommand)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_comprehensive_recommand)
    SwipeRefreshLayout swipeComprehensiveRecommand;

    public ComprehensiveViewpager(Context context) {
        super(context);
    }

    @Override
    protected int getChildLayoutId() {
        return R.layout.comprehensive;
    }

    @Override
    protected String getChildUrl() {
        return ConstantUtils.COMPREHENSIVE_VIEWPAGER;
    }

    @Override
    public void initListener() {
        refresh(swipeComprehensiveRecommand);
    }

    @Override
    public void initData(String json) {
        RecommandComprehensiveBean bean = JSON.parseObject(json, RecommandComprehensiveBean.class);
        List<RecommandComprehensiveBean.DataBean> data = bean.getData();
        if (data != null && data.size() > 0) {
            setAdapter(data);
        }
    }

    private void setAdapter(List<RecommandComprehensiveBean.DataBean> data) {
        ComprehensiveRecycleViewadapter adapter = new ComprehensiveRecycleViewadapter(mContext, data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));

    }

}
