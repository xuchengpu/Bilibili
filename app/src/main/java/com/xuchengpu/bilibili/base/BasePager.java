package com.xuchengpu.bilibili.base;

import android.content.Context;
import android.view.View;

/**
 * Created by 许成谱 on 2017/2/6.
 */
/*作用：视图的基类
 */
public abstract class BasePager {

    /**
     * 上下文
     */
    public final Context mContext;
    /**
     * 代表各个详情页面的实例，视图
     */
    public View rootView;

    public BasePager(Context context) {
        this.mContext = context;
        rootView = initView();
    }

    /**
     * 由子类实现该方法，初始化子类的视图
     *
     * @return
     */
    public abstract View initView();

    /**
     * 绑定数据或者请求数据再绑定数据
     */
    public void initData() {

    }

}
