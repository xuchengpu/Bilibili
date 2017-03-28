package com.xuchengpu.bilibili.view;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.util.Log;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 许成谱 on 2017/3/12 11:09.
 * qq:1550540124
 * for:
 */

public class MyApplication extends Application {



    private static Context context;
    private static Thread mainThread;
    private static int  threadid;
    private static Handler handler;
    private static List<String> downloadData;

    public static Context getContext() {
        return context;
    }

    public static Thread getMainThread() {
        return mainThread;
    }

    public static int getThreadid() {
        return threadid;
    }

    public static Handler getHandler() {
        return handler;
    }
    public static List<String> getDownloadData(){
        return downloadData;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context=this;
        //得到当前线程
        threadid = android.os.Process.myPid();
        downloadData=new ArrayList<>();
        handler = new Handler();
        //二维码扫描功能
        ZXingLibrary.initDisplayOpinion(this);

//        CrashHandler.getCrashHandler().init();
    }

    
    //五个生命周期

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i("appliction", "onTerminate: ");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("appliction", "onConfigurationChanged: ");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i("appliction", "onLowMemory: ");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.i("appliction", "onTrimMemory: ");
    }
}
