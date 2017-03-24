package com.xuchengpu.bilibili.base;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import com.xuchengpu.bilibili.view.LoadingPager;

import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/3/9 9:58.
 * qq:1550540124
 * for:抽取过后的basefragment  可添加加载数据、没有网络、出现错误等不同界面
 */

public abstract class BaseViewPager {

    public LoadingPager loadingPager;
    /**
     * 上下文
     */
    public final Context mContext;
    /**
     * 代表各个详情页面的实例，视图
     */
    public View rootView;

    public BaseViewPager(Context context) {
        this.mContext = context;
        rootView = initView();
        loadingPager.loadData(null);
    }


    public  View initView(){
        loadingPager = new LoadingPager(mContext) {
            @Override
            protected void onSucess(LoadingPager.ResultState resultState, View sucessView) {
                ButterKnife.bind(BaseViewPager.this, sucessView);
                initData(resultState.getJson());
                Log.e("tag","BaseViewPager-initView-onSucess=="+resultState.getJson());
                initListener();
            }

            @Override
            protected String getUrl() {
                return getChildUrl();
            }

            @Override
            public int getLayoutId() {
                return getChildLayoutId();
            }
        };

        return loadingPager;
    }
    //下拉刷新

    public void refresh(final SwipeRefreshLayout swView) {
        swView.setDistanceToTriggerSync(100);
        // 设置颜色
        swView.setColorSchemeColors(Color.BLACK, Color.RED);
        //设置背景颜色
        swView.setProgressBackgroundColorSchemeResource(android.R.color.holo_orange_dark);
        // 下拉刷新
        swView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadingPager.loadData(swView);
            }
        });
    }

    protected abstract int getChildLayoutId();

    protected abstract String getChildUrl();
    public abstract void initListener();

    public abstract void initData(String json) ;
}
