package com.xuchengpu.bilibili.activity.goodlist.model;

import com.xuchengpu.bilibili.bean.GoodListBean;

import java.util.List;

/**
 * Created by 许成谱 on 2017/4/7 9:23.
 * qq:1550540124
 * for:畅想美好生活每一天！
 */

public interface IListener {
    void success(List<GoodListBean.ResultBean.RecordsBean> datas);
    void failure(Throwable t);
}
