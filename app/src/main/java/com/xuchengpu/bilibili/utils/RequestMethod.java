package com.xuchengpu.bilibili.utils;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

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
//                        Log.e("tag","联网请求失败=="+e.getMessage());
                        if(transferData!=null) {
                            transferData.failure(e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("tag","联网请求成功=="+response);
                        if(transferData!=null) {
                            transferData.onsucess(response);
                        }
                    }
                });

    }
    public static void getDataFromNet(String url, Map<String,String> map, final  TransferData transferData){
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
                        if(transferData!=null) {
                            transferData.failure(e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("tag","联网请求成功=="+response);
                        if(transferData!=null) {
                            transferData.onsucess(response);
                        }
                    }
                });
    }



}
