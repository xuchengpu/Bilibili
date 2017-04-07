package com.xuchengpu.bilibili.utils.retrofit;

import com.xuchengpu.bilibili.bean.GoodListBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by 许成谱 on 2017/4/6 15:17.
 * qq:1550540124
 * for:畅想美好生活每一天！
 */

public interface RetrofitService {
   /* @GET("{id}")
    Call<GoodListBean> listRepos(@Path("id") String url);*/
    @GET()
    Call<GoodListBean> listRepos(@Url String url);

    @FormUrlEncoded
    @POST()
    Call<GoodListBean> listRepos(@Url String url, @FieldMap Map<String, String> map);
}
