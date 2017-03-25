package com.xuchengpu.bilibili.viewpager.recommand;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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
    private GridLayoutManager gridLayoutManager;
    private int lastVisibleItem;
    private ComprehensiveRecycleViewadapter adapter;
    private List<RecommandComprehensiveBean.DataBean> data;

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

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //拖动停止时的状态  由于position是从零开始计数 所以要加1  表明是最后一条
                if (newState == RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem+1==adapter.getItemCount()) {
                    getMoreData();
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //记录最后一条item的位置  从0开始计数
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void getMoreData() {
        //此处可根据服务端接口自由定义
        data.addAll(data);
        adapter.notifyDataSetChanged();
        Toast.makeText(mContext, "加载更多数据完成", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void initData(String json) {
        RecommandComprehensiveBean bean = JSON.parseObject(json, RecommandComprehensiveBean.class);
        data = bean.getData();
        if (data != null && data.size() > 0) {
            setAdapter(data);
        }
    }

    private void setAdapter(List<RecommandComprehensiveBean.DataBean> data) {
        adapter = new ComprehensiveRecycleViewadapter(mContext, data);
        recyclerView.setAdapter(adapter);
        gridLayoutManager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(gridLayoutManager);


    }

}
