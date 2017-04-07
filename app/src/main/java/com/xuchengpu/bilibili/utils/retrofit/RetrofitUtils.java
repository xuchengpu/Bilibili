package com.xuchengpu.bilibili.utils.retrofit;

import com.xuchengpu.bilibili.utils.ConstantUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 许成谱 on 2017/4/6 15:15.
 * qq:1550540124
 * for:畅想美好生活每一天！
 */

public class RetrofitUtils {

    public static RetrofitService getRetrofitService(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtils.GOODLISTBASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);
        return service;


    }


}
