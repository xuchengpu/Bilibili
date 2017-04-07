package com.xuchengpu.bilibili.activity.goodlist.model;

import com.xuchengpu.bilibili.bean.GoodListBean;
import com.xuchengpu.bilibili.utils.retrofit.RetrofitUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 许成谱 on 2017/4/7 9:25.
 * qq:1550540124
 * for:畅想美好生活每一天！
 */

public class GetDataImpl implements IGetData {
    @Override
    public void getDataFromNet(String url, final IListener listener) {
        Call<GoodListBean> repos = RetrofitUtils.getRetrofitService().listRepos(url);
        repos.enqueue(new Callback<GoodListBean>() {
            @Override
            public void onResponse(Call<GoodListBean> call, Response<GoodListBean> response) {
                GoodListBean body = response.body();
                if(body!=null) {
                    List<GoodListBean.ResultBean.RecordsBean> datas = processData(body);
                    listener.success(datas);
                }
            }

            @Override
            public void onFailure(Call<GoodListBean> call, Throwable t) {
                listener.failure(t);
            }
        });
    }
    private List<GoodListBean.ResultBean.RecordsBean> processData(GoodListBean bean) {
        List<GoodListBean.ResultBean.RecordsBean> datas = bean.getResult().getRecords();
        if (datas != null && datas.size() > 0) {
            datas.addAll(datas);
            datas.addAll(datas);
            datas.addAll(datas);
            datas.addAll(datas);
        }
        return datas;
    }
}
