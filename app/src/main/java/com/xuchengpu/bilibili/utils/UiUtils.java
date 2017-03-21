package com.xuchengpu.bilibili.utils;

import android.content.Context;
import android.view.View;

import com.xuchengpu.bilibili.view.MyApplication;


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


}
