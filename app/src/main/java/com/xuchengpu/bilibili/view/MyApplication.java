package com.xuchengpu.bilibili.view;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.util.Log;

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

    @Override
    public void onCreate() {
        super.onCreate();

        context=this;
        //得到当前线程
        threadid = android.os.Process.myPid();
        handler = new Handler();

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
