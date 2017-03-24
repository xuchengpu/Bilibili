package com.xuchengpu.bilibili.viewpager.partition;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.adapter.partition.PartitionRecycleViewAdapter;
import com.xuchengpu.bilibili.base.BaseViewPager;
import com.xuchengpu.bilibili.bean.PartitionRecycleViewBean;
import com.xuchengpu.bilibili.bean.PartitonGridViewBean;
import com.xuchengpu.bilibili.utils.RequestMethod;
import com.xuchengpu.bilibili.utils.TransferData;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 许成谱 on 2017/3/21 15:45.
 * qq:1550540124
 * for:
 */

public class PartitionViewPager extends BaseViewPager {


    @BindView(R.id.recycleview_partition)
    RecyclerView recyclerView;

    public PartitionViewPager(Context context) {
        super(context);
    }


    @Override
    protected int getChildLayoutId() {
        return R.layout.partition;
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
        getJson();
    }

    private void getJson() {
        String urlChannel = "http://live.bilibili.com/AppIndex/areas?_device=android&appkey=1d8b6e7d45233436&build=501000&mobi_app=android&platform=android&scale=xxhdpi&ts=1490016232000&sign=678fafda8c1c1e2db3d8224c2b31044f";

        RequestMethod.getDataFromNet(urlChannel, new TransferData() {
            @Override
            public void onsucess(String data) {
                PartitonGridViewBean bean = JSON.parseObject(data, PartitonGridViewBean.class);
                List<PartitonGridViewBean.DataBean> dataBeen = bean.getData();
                getAnotherData(dataBeen);
            }

            @Override
            public void failure(String data) {

            }
        });

    }

    private void getAnotherData(final List<PartitonGridViewBean.DataBean> dataBeen) {
        String urlList = "http://app.bilibili.com/x/v2/show/region?appkey=1d8b6e7d45233436&build=501000&mobi_app=android&platform=android&ts=1490014674000&sign=93edb7634f38498a60e5c3ad0b8b0974";
        RequestMethod.getDataFromNet(urlList, new TransferData() {
            @Override
            public void onsucess(String data) {
                PartitionRecycleViewBean bean = JSON.parseObject(data, PartitionRecycleViewBean.class);
                List<PartitionRecycleViewBean.DataBean> data1 = bean.getData();
                setRecycleAdapter(dataBeen, data1);
            }

            @Override
            public void failure(String data) {

            }
        });
    }

    private void setRecycleAdapter(List<PartitonGridViewBean.DataBean> dataBeen, List<PartitionRecycleViewBean.DataBean> data) {
        PartitionRecycleViewAdapter recycleViewAdapteradapter = new PartitionRecycleViewAdapter(mContext, data, dataBeen);
        recyclerView.setAdapter(recycleViewAdapteradapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

    }


}
