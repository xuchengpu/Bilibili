package com.xuchengpu.bilibili.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.adapter.MainViewPagerAdapter;
import com.xuchengpu.bilibili.base.BaseViewPager;
import com.xuchengpu.bilibili.view.CircleImageView;
import com.xuchengpu.bilibili.viewpager.ChaseViewPager;
import com.xuchengpu.bilibili.viewpager.DirectSeedingViewPager;
import com.xuchengpu.bilibili.viewpager.DiscoverViewPager;
import com.xuchengpu.bilibili.viewpager.PartitionViewPager;
import com.xuchengpu.bilibili.viewpager.RecommandViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


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
    @BindView(R.id.view_pager_main)
    ViewPager viewPagerMain;
    @BindView(R.id.activity_toolbar)
    CoordinatorLayout activityToolbar;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.activity_main)
    DrawerLayout activityMain;
    private ArrayList<BaseViewPager> basePagers;
    private String[] titles = {"直播", "推荐", "追番", "分区", "发现"};
    public static final int REQUEST_CODE = 1;
    private PopupWindow window;

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
        initViewPager();
        setAdapter();
        initListener();


    }

    private void initListener() {
        viewPagerMain.setCurrentItem(1);

    }

    public void Scan() {
        Intent intent = new Intent(this, CaptureActivity.class);

        startActivityForResult(intent, REQUEST_CODE);
    }

    ;

    private void setAdapter() {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(basePagers, titles);
//        MainFragmentAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager(), fragments, titles);
        viewPagerMain.setAdapter(adapter);
        //关联viewpager
        tablayout.setupWithViewPager(viewPagerMain);
        //滚动模式
        tablayout.setTabMode(TabLayout.MODE_FIXED);


    }

    private void initViewPager() {
        basePagers = new ArrayList<>();
        basePagers.add(new DirectSeedingViewPager(this));
        basePagers.add(new RecommandViewPager(this));
        basePagers.add(new ChaseViewPager(this));
        basePagers.add(new PartitionViewPager(this));
        basePagers.add(new DiscoverViewPager(this));
    }

    private void initView() {
        navigationView.setItemIconTintList(null);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();

                }
            }
        }
    }
    /***
     * 获取PopupWindow实例
     */
    public void getPopupWindow() {

        if (null != window) {
//            closePopupWindow();
            return;
        } else {
            initPopuptWindow();
        }
    }

    public void initPopuptWindow() {
        //1、利用layoutInflater获得View

        LayoutInflater inflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.popupwindow_search,null);

        //2、两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);

        // 3、 参数设置
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable cw=new ColorDrawable(0xFFFFFFFF);
        window.setBackgroundDrawable(cw);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        //设置点击外部区域是否会消失
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setOutsideTouchable(true);
        //设置其他区域半透明
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.alpha=0.6f;
        getWindow().setAttributes(params);
        //消失后恢复
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                window = null;
                WindowManager.LayoutParams params=getWindow().getAttributes();
                params.alpha=1f;
                getWindow().setAttributes(params);
            }
        });

        // 5 在顶部显示
        window.showAtLocation(this.findViewById(R.id.iv_download_search),
                Gravity.TOP, 0, 0);


    }



    @OnClick({R.id.iv_drawer_home, R.id.top_head, R.id.iv_new_feature_pink_dot, R.id.iv_menu_top_game_center, R.id.iv_toolbar_menu_download, R.id.iv_download_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_drawer_home:
                break;
            case R.id.top_head:
                break;
            case R.id.iv_new_feature_pink_dot:
                break;
            case R.id.iv_menu_top_game_center:
                break;
            case R.id.iv_toolbar_menu_download:
                break;
            case R.id.iv_download_search:
                initPopuptWindow();
                break;
        }
    }

}
