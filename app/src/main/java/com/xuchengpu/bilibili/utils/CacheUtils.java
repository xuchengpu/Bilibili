package com.xuchengpu.bilibili.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.xuchengpu.bilibili.bean.DataBean;
import com.xuchengpu.bilibili.bean.UserInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 许成谱 on 2017/2/5.
 */

public class CacheUtils {
    public static void putBoolean(Context context, String key, Boolean value) {
        SharedPreferences sp = context.getSharedPreferences("atguigu", MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();

    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("atguigu", MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

   /* *//**
     * 缓存文本信息
     *
     * @param context
     * @param key
     * @param value
     *//*
    public static void putString(Context context, String key, String value) {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //sdcard可用
            //就用文本缓存到sdcard
            try {
                //lklkkslkskkskskksk
                String fileName = MD5Encoder.encode(key);

                String dir = Environment.getExternalStorageDirectory() + "/beijingnews/text/";
                //mnt/sdcard/beijintnews/text/lklkkslkskkskskksk
                File file = new File(dir, fileName);
                //mnt/sdcard/beijintnews/text/
                File parent = file.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();//创建多级目录
                }
                //创建
                if (!file.exists()) {
                    file.createNewFile();
                }

                //保持到sdcard上了
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(value.getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();


            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
            sp.edit().putString(key,value).commit();
        }
    }

    */

    /**
     * 得到缓存的文本信息
     *
     * @param context
     * @param key
     * @return
     *//*
    public static String getString(Context context, String key) {
        String result = "";
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

            //sdcard可用
            //就用文本缓存到sdcard
            try {
                //lklkkslkskkskskksk
                String fileName = MD5Encoder.encode(key);

                String dir = Environment.getExternalStorageDirectory() + "/beijingnews/text/";
                //mnt/sdcard/beijintnews/text/lklkkslkskkskskksk
                File file = new File(dir, fileName);
                //mnt/sdcard/beijintnews/text/

                if(file.exists()){

                    int length;
                    byte[] buffer = new byte[1024];

                    //文件输入流
                    FileInputStream inputStream = new FileInputStream(file);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                    while ((length=inputStream.read(buffer))!=-1){
                        outputStream.write(buffer,0,length);
                    }

                    //转换成字符串
                    result = outputStream.toString();
                    inputStream.close();
                    outputStream.close();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
            result = sp.getString(key,"");
        }

        return  result;
    }*/
    public static void putString(Context context, String key, String value) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //sdcard可用
            //就用文本缓存到sdcard
            try {
                //lklkkslkskkskskksk
                String fileName = MD5Encoder.encode(key);

                String dir = Environment.getExternalStorageDirectory() + "/beijingnews/text/";
                //mnt/sdcard/beijintnews/text/lklkkslkskkskskksk
                File file = new File(dir, fileName);
                //mnt/sdcard/beijintnews/text/
                File parent = file.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();//创建多级目录
                }
                //创建
                if (!file.exists()) {
                    file.createNewFile();
                }

                //保持到sdcard上了
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(value.getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            SharedPreferences sp = context.getSharedPreferences("atguigu", MODE_PRIVATE);
            sp.edit().putString(key, value).commit();
        }

    }

    /**
     * 得到缓存的文本信息
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        String result = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            //sdcard可用
            //就用文本缓存到sdcard
            try {
                //lklkkslkskkskskksk
                String fileName = MD5Encoder.encode(key);

                String dir = Environment.getExternalStorageDirectory() + "/beijingnews/text/";
                //mnt/sdcard/beijintnews/text/lklkkslkskkskskksk
                File file = new File(dir, fileName);
                //mnt/sdcard/beijintnews/text/

                if (file.exists()) {

                    int length;
                    byte[] buffer = new byte[1024];

                    //文件输入流
                    FileInputStream inputStream = new FileInputStream(file);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                    while ((length = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, length);
                    }

                    //转换成字符串
                    result = outputStream.toString();
                    inputStream.close();
                    outputStream.close();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            SharedPreferences sp = context.getSharedPreferences("atguigu", MODE_PRIVATE);
            result = sp.getString(key, "");
        }
        return result;
    }
    /**
     * private String imageurl;
     * private String iscredit;
     * private String name;
     * private String phone;
     */
    public static void saveUserInfo(UserInfo userInfo) {
        SharedPreferences sp = UiUtils.getContext().getSharedPreferences("userinfo", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        String imageurl=userInfo.getData().getImageurl();
        String iscredit=userInfo.getData().getIscredit();
        String name=userInfo.getData().getName();
        String phone=userInfo.getData().getPhone();
        edit.putString("imageurl",imageurl);
        edit.putString("iscredit",iscredit);
        edit.putString("name",name);
        edit.putString("phone",phone);
        edit.commit();
//        Toast.makeText(BaseActivity.this, "存储成功=="+phone+"  "+name, Toast.LENGTH_SHORT).show();
    }
    public static UserInfo getUserInfo() {
        SharedPreferences sp = UiUtils.getContext().getSharedPreferences("userinfo", MODE_PRIVATE);
        String imageurl = sp.getString("imageurl", "");
        String iscredit = sp.getString("iscredit", "");
        String name = sp.getString("name", "");
        String phone = sp.getString("phone", "");
        DataBean dataBean=new DataBean();
        dataBean.setImageurl(imageurl);
        dataBean.setIscredit(iscredit);
        dataBean.setName(name);
        dataBean.setPhone(phone);
        UserInfo userInfo=new UserInfo();
        userInfo.setData(dataBean);
//        Toast.makeText(BaseActivity.this, "获取成功=="+phone+"  "+name, Toast.LENGTH_SHORT).show();
        return userInfo;
    }

}
