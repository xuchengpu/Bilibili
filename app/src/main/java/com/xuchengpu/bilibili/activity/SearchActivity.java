package com.xuchengpu.bilibili.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.adapter.MainViewPagerAdapter;
import com.xuchengpu.bilibili.base.BaseViewPager;
import com.xuchengpu.bilibili.bean.ComprehensiveSearchBean;
import com.xuchengpu.bilibili.utils.ConstantUtils;
import com.xuchengpu.bilibili.utils.RequestMethod;
import com.xuchengpu.bilibili.utils.TransferData;
import com.xuchengpu.bilibili.viewpager.ChaseViewPager;
import com.xuchengpu.bilibili.viewpager.DiscoverViewPager;
import com.xuchengpu.bilibili.viewpager.partition.PartitionViewPager;
import com.xuchengpu.bilibili.viewpager.recommand.RecommandViewPager;
import com.xuchengpu.bilibili.viewpager.search.ComprehensiveSorting;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.search_back)
    ImageView searchBack;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.search_text_clear)
    ImageView searchTextClear;
    @BindView(R.id.search_img)
    ImageView searchImg;
    @BindView(R.id.tablayout_search)
    TabLayout tablayout;
    @BindView(R.id.tv_default)
    TextView tvDefault;
    @BindView(R.id.tv_alltimer)
    TextView tvAlltimer;
    @BindView(R.id.tv_allpartition)
    TextView tvAllpartition;
    @BindView(R.id.view_pager_search)
    ViewPager viewPagerSearch;
    @BindView(R.id.activity_toolbar)
    CoordinatorLayout activityToolbar;
    @BindView(R.id.activity_search)
    RelativeLayout activitySearch;

    private List<String> titles;
    private String[] searchTitles;
    private ArrayList<BaseViewPager> basePagers;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        getIntentData();
        getDataFromNet();

    }

    private void getIntentData() {
        key = getIntent().getStringExtra(ConstantUtils.SEARCH);
    }

    private void getDataFromNet() {
        RequestMethod.getDataFromNet(ConstantUtils.SEACHER_TOP_URL + key + ConstantUtils.SEACHER_BUTTON_URL, new TransferData() {
            @Override
            public void onsucess(String data) {
                processData(data);

            }

            @Override
            public void failure(String data) {

            }
        });
    }

    private void processData(String json) {
        ComprehensiveSearchBean bean= JSON.parseObject(json,ComprehensiveSearchBean.class);
        ComprehensiveSearchBean.DataBean data = bean.getData();
        if(data.getItems()!=null&&data.getItems().getArchive()!=null&&data.getItems().getArchive().size()>0) {
            initViewPager(data);
            initTitles(data);
        }

    }

    private void initTitles(ComprehensiveSearchBean.DataBean data) {
        titles=new ArrayList<>();
        titles.add("综合");
        for(int i = 0; i < data.getNav().size(); i++) {
            titles.add(data.getNav().get(i).getName()+"("+data.getNav().get(i).getTotal()+")") ;
        }
        searchTitles=new String[titles.size()];
        for(int i = 0; i < titles.size(); i++) {
            searchTitles[i]=titles.get(i);
        }
        setAdapter();
    }

    private void initViewPager(ComprehensiveSearchBean.DataBean data) {
        basePagers = new ArrayList<>();
        basePagers.add(new ComprehensiveSorting(this,data));
        basePagers.add(new RecommandViewPager(this));
        basePagers.add(new ChaseViewPager(this));
        basePagers.add(new PartitionViewPager(this));
        basePagers.add(new DiscoverViewPager(this));
    }
    private void setAdapter() {

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(basePagers, searchTitles);
//        MainFragmentAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager(), fragments, titles);
        viewPagerSearch.setAdapter(adapter);
        //关联viewpager
        tablayout.setupWithViewPager(viewPagerSearch);
        //模式
        tablayout.setTabMode(TabLayout.MODE_FIXED);

    }
}
