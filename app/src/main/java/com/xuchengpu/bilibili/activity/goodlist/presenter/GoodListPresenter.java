package com.xuchengpu.bilibili.activity.goodlist.presenter;

import com.xuchengpu.bilibili.activity.goodlist.model.GetDataImpl;
import com.xuchengpu.bilibili.activity.goodlist.model.IListener;
import com.xuchengpu.bilibili.activity.goodlist.view.IGoodListView;
import com.xuchengpu.bilibili.bean.GoodListBean;

import java.util.List;

/**
 * Created by 许成谱 on 2017/4/7 9:42.
 * qq:1550540124
 * for:热爱生活每一天！
 */

public class GoodListPresenter {

    private final IGoodListView goodListView;
    private final GetDataImpl goodListModel;

    public GoodListPresenter(IGoodListView goodListView) {
        this.goodListView = goodListView;
        this.goodListModel = new GetDataImpl();
    }

    public void getDataFromNet() {
        goodListView.showProgress();
        goodListModel.getDataFromNet(goodListView.getUrl(), new IListener() {
            @Override
            public void success(List<GoodListBean.ResultBean.RecordsBean> body) {
                goodListView.hideProgress();
                goodListView.success(body);
            }

            @Override
            public void failure(Throwable t) {
                goodListView.hideProgress();
                goodListView.failure(t);
            }
        });
    }


}
