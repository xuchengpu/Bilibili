package com.xuchengpu.bilibili.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.adapter.TopicCenterAdapter;
import com.xuchengpu.bilibili.bean.TopicCenterBean;
import com.xuchengpu.bilibili.utils.ConstantUtils;
import com.xuchengpu.bilibili.utils.RequestMethod;
import com.xuchengpu.bilibili.utils.TransferData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicCenterActivity extends AppCompatActivity {


    @BindView(R.id.iv_head_topiccenter)
    ImageView ivHeadTopiccenter;
    @BindView(R.id.lv_topic_center)
    ListView lvTopicCenter;
    @BindView(R.id.swip_topiccenter)
    SwipeRefreshLayout swView;
    @BindView(R.id.activity_topic_center)
    LinearLayout activityTopicCenter;
    @BindView(R.id.center_name)
    TextView centerName;
    private TopicCenterAdapter adapter;
    boolean isEnd = false;
    private List<TopicCenterBean.ListBean> list;
    private String[] stringArrayExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_center);
        ButterKnife.bind(this);
        getIntentData();
        initView();
        initLisenter();
    }

    private void getIntentData() {
        stringArrayExtra = getIntent().getStringArrayExtra(ConstantUtils.CENTER);
        if(stringArrayExtra !=null&& stringArrayExtra.length>0) {
            centerName.setText(stringArrayExtra[1]);
        }
    }

    private void initView() {
        adapter = new TopicCenterAdapter(this);
        lvTopicCenter.setAdapter(adapter);
        getNetData();

    }

    private void getNetData() {
        RequestMethod.getDataFromNet(stringArrayExtra[0], new TransferData() {
            @Override
            public void onsucess(String data) {
                processData(data);
            }

            @Override
            public void failure(String data) {

            }
        });
    }

    private void processData(String data) {
        TopicCenterBean bean = JSON.parseObject(data, TopicCenterBean.class);
        list = bean.getList();
        swView.setRefreshing(false);
        if (list != null && list.size() > 0) {
            adapter.refresh(list);
        }

    }

    public void initLisenter() {
        swView.setDistanceToTriggerSync(100);
        // 设置颜色
        swView.setColorSchemeColors(Color.BLACK, Color.RED);
        swView.setColorSchemeResources(R.color.colorAccent);
        //设置背景颜色
        swView.setProgressBackgroundColorSchemeResource(R.color.white);
        // 下拉刷新

        swView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNetData();
            }
        });

        lvTopicCenter.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && isEnd) {
                    getMoreData();
                    isEnd = false;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


                if (firstVisibleItem + visibleItemCount + 1 == totalItemCount) {
                    isEnd = true;
                }
            }
        });

    }

    private void getMoreData() {
        list.addAll(list);
        adapter.refresh(list);
    }

}
