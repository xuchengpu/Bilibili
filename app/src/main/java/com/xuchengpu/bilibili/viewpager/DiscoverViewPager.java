package com.xuchengpu.bilibili.viewpager;

import android.content.Context;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.base.BaseViewPager;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.BindView;

/**
 * Created by 许成谱 on 2017/3/21 15:45.
 * qq:1550540124
 * for:
 */

public class DiscoverViewPager extends BaseViewPager {

    @BindView(R.id.tv_search_discover)
    TextView tvSearchDiscover;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;
    @BindView(R.id.sl_content_discover)
    ScrollView slContentDiscover;
    @BindView(R.id.tv_more_discover)
    TextView tvMoreDiscover;
    @BindView(R.id.tv_interest_discover)
    TextView tvInterestDiscover;
    @BindView(R.id.tv_topiccenter_discover)
    TextView tvTopiccenterDiscover;
    @BindView(R.id.tv_activity_discover)
    TextView tvActivityDiscover;
    @BindView(R.id.tv_smallhouse_discover)
    TextView tvSmallhouseDiscover;
    @BindView(R.id.tv_origin_discover)
    TextView tvOriginDiscover;
    @BindView(R.id.tv_allarea_discover)
    TextView tvAllareaDiscover;
    @BindView(R.id.tv_gamecenter_discover)
    TextView tvGamecenterDiscover;
    @BindView(R.id.tv_gameinfo_discover)
    TextView tvGameinfoDiscover;
    @BindView(R.id.tv_shoppingmall_discover)
    TextView tvShoppingmallDiscover;

    public DiscoverViewPager(Context context) {
        super(context);
    }


    @Override
    protected int getChildLayoutId() {
        return R.layout.discover;
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

    }

}
