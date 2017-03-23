package com.xuchengpu.bilibili.viewpager.recommand;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.Toast;

import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.adapter.recommandadapter.RecommandAdapter;
import com.xuchengpu.bilibili.base.BaseViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 许成谱 on 2017/3/21 15:45.
 * qq:1550540124
 * for:
 */

public class RecommandViewPager extends BaseViewPager {


    @BindView(R.id.tablayout_recommand)
    TabLayout tablayoutRecommand;
    @BindView(R.id.iv_tag_recommand)
    ImageView ivTagRecommand;
    @BindView(R.id.viewpager_recommand)
    ViewPager viewpagerRecommand;


    private List<BaseViewPager> viewPagers;


    public RecommandViewPager(Context context) {
        super(context);
    }

    @Override
    protected int getChildLayoutId() {
        return R.layout.recomand;
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

        initViewPagers();
        setAdapter();


    }

    private void initTablayout() {
        //关联viewpager
        tablayoutRecommand.setupWithViewPager(viewpagerRecommand);
        //滚动模式
        tablayoutRecommand.setTabMode(TabLayout.MODE_FIXED);

    }

    private void setAdapter() {
        String[] titles={"综合","动态"};
        RecommandAdapter adapter=new RecommandAdapter(mContext,viewPagers,titles);
        viewpagerRecommand.setAdapter(adapter);
        initTablayout();
    }

    private void initViewPagers() {
        viewPagers=new ArrayList<>();
        viewPagers.add(new ComprehensiveViewpager(mContext));
        viewPagers.add(new DynamicViewpager(mContext));
    }


    @OnClick(R.id.iv_tag_recommand)
    public void onClick() {
        Toast.makeText(mContext, "没有数据，待实现……", Toast.LENGTH_SHORT).show();
    }
}
