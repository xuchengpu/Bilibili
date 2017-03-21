package com.xuchengpu.bilibili.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.xuchengpu.bilibili.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.activity_main)
    DrawerLayout activityMain;

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
    }
}
