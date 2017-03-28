package com.xuchengpu.bilibili.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xuchengpu.bilibili.adapter.DownloadAdapter;
import com.xuchengpu.bilibili.view.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 许成谱 on 2017/3/12 20:45.
 * qq:1550540124
 * for:二次封装 方便维护
 */

public class RequestMethod {
    private static int num;

    public static void getDataFromNet(String url, final TransferData transferData) {
        OkHttpUtils
                .get()
                .url(url)
                .id(100)//100:http 、101：https
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        Log.e("tag","联网请求失败=="+e.getMessage());
                        if (transferData != null) {
                            transferData.failure(e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("tag","联网请求成功=="+response);
                        if (transferData != null) {
                            transferData.onsucess(response);
                        }
                    }
                });

    }

    public static void getDataFromNet(String url, Map<String, String> map, final TransferData transferData) {
        OkHttpUtils
                .post()
                .url(url)
                .params(map)
                .id(100)//100:http 、101：https
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        Log.e("tag","联网请求失败=="+e.getMessage());
                        if (transferData != null) {
                            transferData.failure(e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("tag","联网请求成功=="+response);
                        if (transferData != null) {
                            transferData.onsucess(response);
                        }
                    }
                });
    }

    public static void download(final DownloadAdapter downloadAdapter, final Button btnDownload, final Context mContext, String url, final TextView tvProgress, final ProgressBar progressBar, int max) {
        if(num>=max) {
            Toast.makeText(mContext, "num=="+num+"max=="+max, Toast.LENGTH_SHORT).show();
            return;
        }
        num++;
        if(num==max) {
            Toast.makeText(mContext, "num2=="+num+"max2=="+max, Toast.LENGTH_SHORT).show();
            downloadAdapter.flag=true;
            downloadAdapter.notifyDataSetChanged();
        }
        ThreadPool.getInstance().getExecutorService().execute(new Runnable() {
            private double a;
            @Override
            public void run() {
                Log.e("tag", "11111111111");
                InputStream is = null;
                File path;
                FileOutputStream os = null;
                try {
                    //获取url地址
                    URL url = new URL(ConstantUtils.BASE_URL + "app_new.apk");
                    //获取连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    conn.connect();
                    if (conn.getResponseCode() == 200) {
                        double length = conn.getContentLength();
//                        progressDialog.setMax(100);
                        progressBar.setMax((int) length);
                        progressBar.setProgress(0);
                        is = conn.getInputStream();
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            path = mContext.getExternalFilesDir(null);
                        } else {
                            path = mContext.getFilesDir();
                        }
                        File file = new File(path, "updataversion.apk");
                        os = new FileOutputStream(file);
                        byte[] b = new byte[1024];
                        int len = 0;
                        while ((len = is.read(b)) != -1) {
                            MyApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    btnDownload.setEnabled(false);
                                }
                            });
                            os.write(b, 0, len);
                            a += len / length;
                            MyApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    tvProgress.setText((int) (a * 100 + 0.5) + "%");
                                }
                            });
                            progressBar.incrementProgressBy(len);
                        }

                        MyApplication.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                btnDownload.setEnabled(true);
                                downloadAdapter.flag=false;
                                downloadAdapter.notifyDataSetChanged();
                            }
                        });

                    } else {
                        //连网失败

                    }
                } catch (Exception e) {
                    Log.e("TAG", "eeeee=" + e.getMessage());
                    e.printStackTrace();
                } finally {
                    num--;
                    //关闭流前先判断 是防止在new is 前就产生异常了  这样就导致is为空
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (os != null) {
                        try {
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        });
    }
}
