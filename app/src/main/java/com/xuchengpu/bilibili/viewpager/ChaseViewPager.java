package com.xuchengpu.bilibili.viewpager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.adapter.ChaseAdapter;
import com.xuchengpu.bilibili.base.BaseViewPager;
import com.xuchengpu.bilibili.bean.ChaseBean;
import com.xuchengpu.bilibili.utils.ConstantUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 许成谱 on 2017/3/21 15:45.
 * qq:1550540124
 * for:
 */

public class ChaseViewPager extends BaseViewPager {


    @BindView(R.id.rv_chase)
    RecyclerView rvChase;

    public ChaseViewPager(Context context) {
        super(context);
    }


    @Override
    protected int getChildLayoutId() {
        return R.layout.chase;
    }

    @Override
    protected String getChildUrl() {
        return ConstantUtils.CHASE_VIEWPAGER;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData(String json) {
        ChaseBean bean= JSON.parseObject(json,ChaseBean.class);
        ChaseBean.ResultBean result = bean.getResult();
        List<ChaseBean.ResultBean.AdBean.HeadBean> head = result.getAd().getHead();
        ChaseBean.ResultBean.PreviousBean previous = result.getPrevious();
        List<ChaseBean.ResultBean.SerializingBean> serializing = result.getSerializing();
        if(result!=null) {
            setAdapter(result);
        }


    }

    private void setAdapter(ChaseBean.ResultBean result) {
        ChaseAdapter adapter=new ChaseAdapter(mContext,result);
        rvChase.setAdapter(adapter);
        rvChase.setLayoutManager(new LinearLayoutManager(mContext));
    }

}
