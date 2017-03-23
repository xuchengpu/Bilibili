package com.xuchengpu.bilibili.viewpager.partition;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.adapter.partition.PartitionAdapter;
import com.xuchengpu.bilibili.base.BaseViewPager;
import com.xuchengpu.bilibili.utils.RequestMethod;
import com.xuchengpu.bilibili.utils.TransferData;

import butterknife.BindView;

/**
 * Created by 许成谱 on 2017/3/21 15:45.
 * qq:1550540124
 * for:
 */

public class PartitionViewPager extends BaseViewPager {


    @BindView(R.id.recycleview_partition)
    RecyclerView recyclerView;
    String result="";

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
        String urlChannel="http://live.bilibili.com/AppIndex/areas?_device=android&appkey=1d8b6e7d45233436&build=501000&mobi_app=android&platform=android&scale=xxhdpi&ts=1490016232000&sign=678fafda8c1c1e2db3d8224c2b31044f";
        String urlList="http://app.bilibili.com/x/v2/show/region?appkey=1d8b6e7d45233436&build=501000&mobi_app=android&platform=android&ts=1490014674000&sign=93edb7634f38498a60e5c3ad0b8b0974";
        RequestMethod.getDataFromNet(urlChannel, new TransferData() {
            @Override
            public void onsucess(String data) {
                setGridAdapter(data);
            }

            @Override
            public void failure(String data) {

            }
        });
        RequestMethod.getDataFromNet(urlList, new TransferData() {
            @Override
            public void onsucess(String data) {
                setRecycleAdapter(data);
            }

            @Override
            public void failure(String data) {

            }
        });
    }

    private void setRecycleAdapter(String data) {
        PartitionAdapter adapter=new PartitionAdapter(mContext);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private void setGridAdapter(String data) {

    }

}
