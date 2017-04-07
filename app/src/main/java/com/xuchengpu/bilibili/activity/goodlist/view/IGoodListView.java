package com.xuchengpu.bilibili.activity.goodlist.view;

import com.xuchengpu.bilibili.bean.GoodListBean;

import java.util.List;

/**
 * Created by 许成谱 on 2017/4/7 9:13.
 * qq:1550540124
 * for:畅想美好生活每一天！
 */

public interface IGoodListView {
    void success(List<GoodListBean.ResultBean.RecordsBean> body);
    void failure(Throwable t);
    void hideProgress();
    void showProgress();
    String getUrl();
}
