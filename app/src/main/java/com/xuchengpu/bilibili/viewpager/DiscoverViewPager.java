package com.xuchengpu.bilibili.viewpager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.activity.goodlist.view.GoodsListActivity;
import com.xuchengpu.bilibili.activity.MainActivity;
import com.xuchengpu.bilibili.activity.TopicCenterActivity;
import com.xuchengpu.bilibili.base.BaseViewPager;
import com.xuchengpu.bilibili.bean.DiscoverTagBean;
import com.xuchengpu.bilibili.utils.ConstantUtils;
import com.xuchengpu.bilibili.utils.UiUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 许成谱 on 2017/3/21 15:45.
 * qq:1550540124
 * for:
 */

public class DiscoverViewPager extends BaseViewPager {

    @BindView(R.id.tv_search_discover)
    TextView tvSearchDiscover;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.sl_content_discover)
    NestedScrollView slContentDiscover;
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
    @BindView(R.id.iv_scan_discover)
    ImageView ivScanDiscover;
    private String[] array;
    private TagAdapter<String> mAdapter;
    private boolean isMore = true;
    private MainActivity mainActivity;
    private Intent intent;
    private String[] info;


    public DiscoverViewPager(Context context) {
        super(context);
    }


    @Override
    protected int getChildLayoutId() {
        return R.layout.discover;
    }

    @Override
    protected String getChildUrl() {
        return ConstantUtils.DISCOVER_TAG;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData(String json) {
        DiscoverTagBean bean = JSON.parseObject(json, DiscoverTagBean.class);
        List<DiscoverTagBean.DataBean.ListBean> list = bean.getData().getList();
        array = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).getKeyword();
        }
        if (array != null && array.length > 0) {
            setAdapter();
        }


    }

    private void setAdapter() {
        final LayoutInflater mInflater = LayoutInflater.from(mContext);
        //mFlowLayout.setMaxSelectCount(3);

        mFlowLayout.setAdapter(mAdapter = new TagAdapter<String>(array) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(mContext, array[position], Toast.LENGTH_SHORT).show();
                return true;
            }
        });



    }


    @OnClick({R.id.tv_search_discover, R.id.iv_scan_discover, R.id.tv_more_discover, R.id.tv_interest_discover, R.id.tv_topiccenter_discover, R.id.tv_activity_discover, R.id.tv_smallhouse_discover, R.id.tv_origin_discover, R.id.tv_allarea_discover, R.id.tv_gamecenter_discover, R.id.tv_gameinfo_discover, R.id.tv_shoppingmall_discover})
    public void onClick(View view) {
        mainActivity = (MainActivity) mContext;
        switch (view.getId()) {
            case R.id.tv_search_discover:

                mainActivity.getPopupWindow();

                break;
            case R.id.tv_more_discover:
                if (isMore) {
                    isMore = false;
                    tvMoreDiscover.setText("收起");
                    tvMoreDiscover.setTextColor(Color.BLACK);
                    tvMoreDiscover.setTextSize(12);
                    Drawable leftDrawable = mContext.getResources().getDrawable(R.drawable.ic_arrow_up);
                    leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
                    tvMoreDiscover.setCompoundDrawables(leftDrawable, null, null, null);
                    ViewGroup.LayoutParams layoutParams = slContentDiscover.getLayoutParams();
                    layoutParams.height = UiUtils.dp2px(250);
                    slContentDiscover.setLayoutParams(layoutParams);
                } else {
                    isMore = true;
                    tvMoreDiscover.setText("加载更多");
                    tvMoreDiscover.setTextColor(Color.BLACK);
                    tvMoreDiscover.setTextSize(12);
                    Drawable leftDrawable = mContext.getResources().getDrawable(R.drawable.ic_arrow_down);
                    leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
                    tvMoreDiscover.setCompoundDrawables(leftDrawable, null, null, null);
                    ViewGroup.LayoutParams layoutParams = slContentDiscover.getLayoutParams();
                    layoutParams.height = UiUtils.dp2px(100);
                    slContentDiscover.setLayoutParams(layoutParams);
                }
                break;
            case R.id.tv_interest_discover:
                break;
            case R.id.tv_topiccenter_discover:
                intent = new Intent(mContext, TopicCenterActivity.class);
                info = new String[]{ConstantUtils.TOPIC_CENTER,"话题中心"};
                intent.putExtra(ConstantUtils.CENTER, info);
                mainActivity.startActivity(intent);
                break;
            case R.id.tv_activity_discover:
                intent = new Intent(mContext, TopicCenterActivity.class);
                info = new String[]{ConstantUtils.ACTIVITY_CENTER,"活动中心"};
                intent.putExtra(ConstantUtils.CENTER, info);
                mainActivity.startActivity(intent);
                break;
            case R.id.tv_smallhouse_discover:
                break;
            case R.id.tv_origin_discover:
                break;
            case R.id.tv_allarea_discover:
                break;
            case R.id.tv_gamecenter_discover:
                break;
            case R.id.tv_gameinfo_discover:
                break;
            case R.id.tv_shoppingmall_discover:
                intent = new Intent(mContext, GoodsListActivity.class);
                mainActivity.startActivity(intent);
                break;
            case R.id.iv_scan_discover:
                mainActivity.Scan();
                break;
        }
    }


}
