package com.xuchengpu.bilibili.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.xuchengpu.bilibili.view.MyApplication;

import java.lang.reflect.Method;


/**
 * Created by 许成谱 on 2017/3/12 11:22.
 * qq:1550540124
 * for:
 */

public class UiUtils {



    public static Context getContext() {
        return MyApplication.getContext();
    }

    public static View getView(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    public static int getColor(int colorId) {
        return getContext().getResources().getColor(colorId);
    }

    public static String[] getStringArr(int arrId) {
        return getContext().getResources().getStringArray(arrId);
    }

    public static int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }
    public static int px2dp(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }
    public static void runOnUiThread(Runnable runnable ){
        //判断两个线程是否一致即知道是否在主线程  application肯定是在主线程中的
        if(MyApplication.getThreadid()==android.os.Process.myPid()) {
            runnable.run();
        }else {
            //handler.post是实现在子线程中将消息发送到主线程queue中，达到更新UI的快递通道。
            MyApplication.getHandler().post(runnable);
        }

    }

    public static boolean isHaveNavigationBar(Context context) {

        boolean isHave = false;
        boolean   hasNavigationBar;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                isHave = false;
            } else if ("0".equals(navBarOverride)) {
                isHave = true;
            }
        } catch (Exception e) {
            Log.w("TAG", e);
        }
        return isHave;
    }
//    返回为true的话就不给他设置，反之设置，具体代码如下：
    public static void transportStatus(Activity context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (isHaveNavigationBar(context))
                context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }


}
