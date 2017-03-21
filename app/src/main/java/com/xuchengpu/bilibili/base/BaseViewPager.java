package com.xuchengpu.bilibili.base;

import android.content.Context;
import android.view.View;

import com.xuchengpu.bilibili.view.LoadingPager;

import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/3/9 9:58.
 * qq:1550540124
 * for:抽取过后的basefragment  可添加加载数据、没有网络、出现错误等不同界面
 */

public abstract class BaseViewPager {

    private LoadingPager loadingPager;
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
        loadingPager.loadData();
    }


    public  View initView(){
        loadingPager = new LoadingPager(mContext) {
            @Override
            protected void onSucess(LoadingPager.ResultState resultState, View sucessView) {
                ButterKnife.bind(BaseViewPager.this, sucessView);
                initData(resultState.getJson());

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

    protected abstract int getChildLayoutId();

    protected abstract String getChildUrl();
    public abstract void initListener();

    public abstract void initData(String json) ;
}
