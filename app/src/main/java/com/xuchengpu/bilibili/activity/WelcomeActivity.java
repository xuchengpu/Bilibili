package com.xuchengpu.bilibili.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.utils.CacheUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.activity_welcome)
    LinearLayout activityWelcome;
    private ObjectAnimator animalpha;
    private boolean isLogined;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initTitleBar();

        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        initData();

        initAnimator();
        initListener();
    }

    private void initData() {
        isLogined=getIsLogined();
    }

    private boolean getIsLogined() {
        String name = CacheUtils.getUserInfo().getData().getName();
        if(TextUtils.isEmpty(name)) {
            return false;
        }
        return  true;
    }

    private void initListener() {
        //设置监听 跳转到主界面
        animalpha.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                toMain();

            }
        });
    }

    private void initAnimator() {
        //设置属性动画
        animalpha = ObjectAnimator.ofFloat(activityWelcome,"alpha",1,1);
        animalpha.setDuration(1500);
        //启动动画
        animalpha.start();
    }

    private void initTitleBar() {
        // 去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏顶部的状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void toMain() {
        Intent intent;
        //判断是否登陆过
        if(isLogined) {
            intent= new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
        }else{
            intent=new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
