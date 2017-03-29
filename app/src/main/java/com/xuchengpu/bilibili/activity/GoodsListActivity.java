package com.xuchengpu.bilibili.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.adapter.GoodListAdapter;
import com.xuchengpu.bilibili.bean.GoodListBean;
import com.xuchengpu.bilibili.bean.GoodsBean;
import com.xuchengpu.bilibili.utils.ConstantUtils;
import com.xuchengpu.bilibili.utils.RequestMethod;
import com.xuchengpu.bilibili.utils.TransferData;
import com.xuchengpu.bilibili.view.SpaceItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsListActivity extends AppCompatActivity {


    @BindView(R.id.ib_goods_list_back)
    ImageButton ibGoodsListBack;
    @BindView(R.id.tv_goods_list_search)
    TextView tvGoodsListSearch;
    @BindView(R.id.ib_goods_list_cart)
    ImageButton ibGoodsListCart;
    @BindView(R.id.tv_goods_list_sort)
    TextView tvGoodsListSort;
    @BindView(R.id.tv_goods_list_price)
    TextView tvGoodsListPrice;
    @BindView(R.id.iv_goods_list_arrow)
    ImageView ivGoodsListArrow;
    @BindView(R.id.ll_goods_list_price)
    LinearLayout llGoodsListPrice;
    @BindView(R.id.tv_goods_list_select)
    TextView tvGoodsListSelect;
    @BindView(R.id.ll_goods_list_head)
    LinearLayout llGoodsListHead;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.dl_left)
    DrawerLayout dlLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        ButterKnife.bind(this);


        //请求网络
        getDataFormNet(ConstantUtils.GOODLIST);

        initView();

    }

    private void initView() {

    }

    private void getDataFormNet(String goodlist) {
        RequestMethod.getDataFromNet(goodlist, new TransferData() {
            @Override
            public void onsucess(String data) {
                if (data != null) {
                    processData(data);
                }
            }

            @Override
            public void failure(String data) {

            }
        });

    }

    private void processData(String json) {
        GoodListBean bean = JSON.parseObject(json, GoodListBean.class);
        List<GoodListBean.ResultBean.RecordsBean> datas = bean.getResult().getRecords();
        if (datas != null && datas.size() > 0) {
            datas.addAll(datas);
            datas.addAll(datas);
            datas.addAll(datas);
            datas.addAll(datas);
            setAdapter(datas);
        }
    }

    private void setAdapter(List<GoodListBean.ResultBean.RecordsBean> datas) {
        GoodListAdapter adapter = new GoodListAdapter(this, datas);
        recyclerview.setAdapter(adapter);

        recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerview.addItemDecoration(new SpaceItemDecoration(10));

        //封装到bean对象中，实现跳转到商品详情页面
        adapter.setOnItemClickListener(new GoodListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GoodListBean.ResultBean.RecordsBean data) {
                GoodsBean goodsBean = new GoodsBean();
                goodsBean.setProduct_id(data.getSkuId() + "");
                goodsBean.setName(data.getTitle());
                goodsBean.setCover_price(data.getVipPlusPrice() + "");
                goodsBean.setFigure(data.getImgUrl());

                Intent intent = new Intent(GoodsListActivity.this, GoodsInfoActivity.class);
                intent.putExtra(ConstantUtils.GOODSBEAN, goodsBean);
                startActivity(intent);
            }
        });
    }


    @OnClick({R.id.ib_goods_list_back,R.id.ib_goods_list_cart, R.id.tv_goods_list_search, R.id.tv_goods_list_sort, R.id.tv_goods_list_price})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_goods_list_back:
                break;
            case R.id.tv_goods_list_search:
                break;
            case R.id.tv_goods_list_sort:
                break;
            case R.id.tv_goods_list_price:
                break;
            case R.id.ib_goods_list_cart:
                Intent intent = new Intent(this, CartActivity.class);
                startActivity(intent);
                break;
        }
    }
}
