package com.xuchengpu.bilibili.view;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;

import com.anye.greendao.gen.DaoMaster;
import com.anye.greendao.gen.DaoSession;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 许成谱 on 2017/3/12 11:09.
 * qq:1550540124
 * for:
 */

public class MyApplication extends Application {

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static MyApplication instances;



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

        instances = this;
        setDatabase();

        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
    public static MyApplication getInstances(){
        return instances;
    }
    /**
     * 设置greenDao*/

    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
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
