package com.xuchengpu.bilibili.utils;

import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 许成谱 on 2017/3/12 20:45.
 * qq:1550540124
 * for:二次封装 方便维护
 */

public class RequestMethod {
    public static void getDataFromNet(String url,  final  TransferData transferData){
        OkHttpUtils
                .get()
                .url(url)
                .id(100)//100:http 、101：https
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("tag","联网请求失败=="+e.getMessage());
                        if(transferData!=null) {
                            transferData.onsucess(e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("tag","联网请求成功=="+response);
                        if(transferData!=null) {
                            transferData.failure(response);
                        }
                    }
                });

    }



}
