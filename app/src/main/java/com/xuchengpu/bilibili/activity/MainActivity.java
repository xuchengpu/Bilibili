package com.xuchengpu.bilibili.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.adapter.MainViewPagerAdapter;
import com.xuchengpu.bilibili.base.BaseViewPager;
import com.xuchengpu.bilibili.utils.CacheUtils;
import com.xuchengpu.bilibili.utils.ConstantUtils;
import com.xuchengpu.bilibili.utils.UiUtils;
import com.xuchengpu.bilibili.view.CircleImageView;
import com.xuchengpu.bilibili.viewpager.ChaseViewPager;
import com.xuchengpu.bilibili.viewpager.DirectSeedingViewPager;
import com.xuchengpu.bilibili.viewpager.DiscoverViewPager;
import com.xuchengpu.bilibili.viewpager.partition.PartitionViewPager;
import com.xuchengpu.bilibili.viewpager.recommand.RecommandViewPager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


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
    private  int count=1;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //解决底部虚拟键问题
        UiUtils.transportStatus(this);

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
        //模式
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
//        navigationView.setItemIconTintList(null);//让item中的图片显示原色
        navigationView.setNavigationItemSelectedListener(this);//设置item监听
        View headerView = navigationView.getHeaderView(0);
        CircleImageView mUserAvatarView = (CircleImageView) headerView.findViewById(
                R.id.user_avatar_view);
        TextView mUserName = (TextView) headerView.findViewById(R.id.user_name);
        TextView mUserSign = (TextView) headerView.findViewById(R.id.user_other_info);
        TextView exit = (TextView) headerView.findViewById(R.id.tv_exit);
        ImageView mSwitchMode = (ImageView) headerView.findViewById(R.id.iv_head_switch_mode);

        //设置头像
        mUserAvatarView.setImageResource(R.drawable.ic_hotbitmapgg_avatar);
        //设置用户名 签名
        mUserName.setText(getResources().getText(R.string.hotbitmapgg));
        mUserSign.setText(getResources().getText(R.string.about_user_head_layout));

        //设置日夜间模式切换
        mSwitchMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchNightMode();
            }
        });

        boolean flag = CacheUtils.getBoolean(this, ConstantUtils.SWITCH_MODE_KEY);
        if (flag) {
            mSwitchMode.setImageResource(R.drawable.ic_switch_daily);
        } else {
            mSwitchMode.setImageResource(R.drawable.ic_switch_night);
        }
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSp();
                Toast.makeText(MainActivity.this, "登录数据已清除", Toast.LENGTH_SHORT).show();
            }
        });

    }


    /**
     * 日夜间模式切换
     */
    private void switchNightMode() {

        boolean isNight = CacheUtils.getBoolean(this,ConstantUtils.SWITCH_MODE_KEY);
        if (isNight) {
            // 日间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            CacheUtils.putBoolean(this,ConstantUtils.SWITCH_MODE_KEY, false);
        } else {
            // 夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            CacheUtils.putBoolean(this,ConstantUtils.SWITCH_MODE_KEY, true);
        }

        recreate();
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

                    Intent intent=new Intent(this,WebViewActivity.class);
                    intent.putExtra(ConstantUtils.SCAN,result);
                    startActivity(intent);

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
            closePopupWindow();
            return;
        } else {
            initPopuptWindow();
        }
    }

    public void initPopuptWindow() {
        //调用虚拟键盘
        initKeyboard();
        //1、利用layoutInflater获得View

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popupwindow_search, null);

        //2、两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        // 3、 参数设置
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable cw = new ColorDrawable(0xFFFFFFFF);
        window.setBackgroundDrawable(cw);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        //设置点击外部区域是否会消失
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setOutsideTouchable(true);
        //设置其他区域半透明
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.6f;
        getWindow().setAttributes(params);
        //消失后恢复
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                window = null;
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });

        // 5 在顶部显示
        window.showAtLocation(this.findViewById(R.id.iv_download_search),
                Gravity.TOP, 0, 0);


    }

    /**
     * 关闭窗口
     */
    private void closePopupWindow() {
        if (window != null && window.isShowing()) {
            window.dismiss();
            window = null;
            WindowManager.LayoutParams params =getWindow().getAttributes();
            params.alpha = 1f;
            getWindow().setAttributes(params);
        }
    }


    @OnClick({R.id.iv_drawer_home, R.id.top_head, R.id.iv_new_feature_pink_dot, R.id.iv_menu_top_game_center, R.id.iv_toolbar_menu_download, R.id.iv_download_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_drawer_home:
                activityMain.openDrawer(GravityCompat.START);
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
                getPopupWindow();
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        activityMain.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.item_home:
                // 主页
//                changeFragmentIndex(item, 0);
                return true;

            case R.id.item_download:
                // 离线缓存
//                startActivity(new Intent(MainActivity.this, OffLineDownloadActivity.class));
                return true;

            case R.id.item_vip:
                //大会员
//                startActivity(new Intent(MainActivity.this, VipActivity.class));
                return true;

            case R.id.item_favourite:
                // 我的收藏
//                changeFragmentIndex(item, 1);
                return true;

            case R.id.item_history:
                // 历史记录
//                changeFragmentIndex(item, 2);
                return true;

            case R.id.item_group:
                // 关注的人
//                changeFragmentIndex(item, 3);
                return true;

            case R.id.item_tracker:
                // 我的钱包
//                changeFragmentIndex(item, 4);
                return true;

            case R.id.item_theme:
                // 主题选择
                // CardPickerDialog dialog = new CardPickerDialog();
                // dialog.setClickListener(this);
                // dialog.show(getSupportFragmentManager(), CardPickerDialog.TAG);
                return true;

            case R.id.item_app:
                // 应用推荐

                return true;

            case R.id.item_settings:
                // 设置中心
//                changeFragmentIndex(item, 5);
                return true;
        }

        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            if(count%2==0) {
                finish();
            }
            Toast.makeText(MainActivity.this, "再次点击退出", Toast.LENGTH_SHORT).show();
            count++;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    count=1;
                }
            },2000);
//            CountDownTimer timer = new CountDownTimer(10000, 1000) {
//              第一个参数是总时间 第二个参数间隔时间
//                @Override
//                public void onTick(long millisUntilFinished) {
//                    //每倒计时一次调用一次
//                }
//
//                @Override
//                public void onFinish() {
//                    //执行完成后调用
//                }
//            }.start();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    public void initKeyboard(){
        //如果输入法在窗口上已经显示，则隐藏，反之则显示
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //清除所有的sp操作
    public void clearSp(){
        SharedPreferences user = getSharedPreferences("userinfo", MODE_PRIVATE);
        user.edit().clear().commit(); //清除的是内容
    }
}
