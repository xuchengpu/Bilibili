package com.xuchengpu.bilibili.viewpager.recommand;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.adapter.recommandadapter.ComprehensiveRecycleViewadapter;
import com.xuchengpu.bilibili.base.BaseViewPager;
import com.xuchengpu.bilibili.bean.RecommandComprehensiveBean;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 许成谱 on 2017/3/23 10:49.
 * qq:1550540124
 * for:
 */

public class DynamicViewpager extends BaseViewPager {

    @BindView(R.id.recycleview_comprehensive_recommand)
    RecyclerView recyclerView;

    public DynamicViewpager(Context context) {
        super(context);
    }

    @Override
    protected int getChildLayoutId() {
//        return R.layout.dyamic;
          return R.layout.comprehensive;
    }
    @Override
    protected String getChildUrl() {
        return "http://app.bilibili.com/x/feed/index?appkey=1d8b6e7d45233436&build=501000&idx=1490013261&mobi_app=android&network=wifi&platform=android&pull=true&style=2&ts=1490015599000&sign=af4edc66aef7e443c98c28de2b660aa4";
    }

    @Override
    public void initListener() {

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
        ComprehensiveRecycleViewadapter adapter=new ComprehensiveRecycleViewadapter(mContext,data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));

    }


}
