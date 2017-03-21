package com.xuchengpu.bilibili.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.activity_main)
    DrawerLayout activityMain;
    @BindView(R.id.iv_drawer_home)
    ImageView ivDrawerHome;
    @BindView(R.id.top_head)
    CircleImageView topHead;
    @BindView(R.id.iv_new_feature_pink_dot)
    ImageView ivNewFeaturePinkDot;
    @BindView(R.id.iv_menu_top_game_center)
    ImageView ivMenuTopGameCenter;
    @BindView(R.id.iv_toolbar_menu_download)
    ImageView ivToolbarMenuDownload;
    @BindView(R.id.iv_download_search)
    ImageView ivDownloadSearch;
    @BindView(R.id.dl_left)
    LinearLayout dlLeft;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.activity_toolbar)
    CoordinatorLayout activityToolbar;
    @BindView(R.id.view_pager_main)
    ViewPager viewPagerMain;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏顶部的状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();


    }

    private void initView() {
        navigationView.setItemIconTintList(null);
        //关联viewpager
        tablayout.setupWithViewPager(viewPagerMain);
        //滚动模式
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }
}
