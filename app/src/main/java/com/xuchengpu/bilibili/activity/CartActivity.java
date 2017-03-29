package com.xuchengpu.bilibili.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anye.greendao.gen.UserDao;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.adapter.ShoppingCartAdapter;
import com.xuchengpu.bilibili.bean.GoodsBean;
import com.xuchengpu.bilibili.bean.User;
import com.xuchengpu.bilibili.view.MyApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartActivity extends AppCompatActivity {

    @BindView(R.id.tv_shopcart_edit)
    TextView tvShopcartEdit;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @BindView(R.id.tv_shopcart_total)
    TextView tvShopcartTotal;
    @BindView(R.id.btn_check_out)
    Button btnCheckOut;
    @BindView(R.id.ll_check_all)
    LinearLayout llCheckAll;
    @BindView(R.id.checkbox_delete_all)
    CheckBox checkboxDeleteAll;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_collection)
    Button btnCollection;
    @BindView(R.id.ll_delete)
    LinearLayout llDelete;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.tv_empty_cart_tobuy)
    TextView tvEmptyCartTobuy;
    @BindView(R.id.ll_empty_shopcart)
    LinearLayout llEmptyShopcart;

    private ShoppingCartAdapter adapter;
    private LocalBroadcastManager manager;

    //编辑状态
    private static final int ACTION_EDIT = 1;
    //完成状态
    private static final int ACTION_COMPLETE = 2;
    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    private void initView() {
        /*
        * 处理右上角编辑与完成的textview按钮代码
        * */

        //设置编辑状态
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");

        //显示去结算布局
        llCheckAll.setVisibility(View.VISIBLE);
        tvShopcartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.得到状态
                int action = (int) v.getTag();
                //2.根据不同状态做不同的处理
                if (action == ACTION_EDIT) {
                    //切换完成状态
                    showDelete();
                } else {
                    //切换成编辑状态
                    hideDelete();
                }
            }
        });
    }
    //隐藏底部删除购物车商品栏
    private void hideDelete() {
        //1.设置编辑
        tvShopcartEdit.setTag(ACTION_EDIT);
        //2.隐藏删除控件
        llDelete.setVisibility(View.GONE);
        //3.显示结算控件
        llCheckAll.setVisibility(View.VISIBLE);
        //4.设置文本为-编辑
        tvShopcartEdit.setText("编辑");
        //5.把所有的数据设置勾选择状态
        if(adapter != null){
            adapter.checkAll_none(true);
//            adapter.checkAll();
            adapter.showTotalPrice();
        }
    }
    //显示底部删除购物车商品栏
    private void showDelete() {
        //1.设置完成
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        //2.显示删除控件
        llDelete.setVisibility(View.VISIBLE);
        //3.隐藏结算控件
        llCheckAll.setVisibility(View.GONE);
        //4.设置文本为-完成
        tvShopcartEdit.setText("完成");
        //5.把所有的数据设置非选择状态
        if(adapter != null){
            adapter.checkAll_none(false);
//            adapter.checkAll();
            adapter.showTotalPrice();
        }

    }

    public void initData() {
        mUserDao = MyApplication.getInstances().getDaoSession().getUserDao();
        manager = LocalBroadcastManager.getInstance(this);
        showData();

    }


    //得到数据，并设置适配器，将数据加载到recycleview中显示
    private void showData() {

        List<User> users = mUserDao.loadAll();
        Log.e("TAG", "CartActivity.user=="+users);
        List<GoodsBean> goodsBeens=new ArrayList<>();
        for(int i = 0; i < users.size(); i++) {
          GoodsBean goodsBean=new GoodsBean();
            goodsBean.setCover_price(users.get(i).getCover_price());
            goodsBean.setFigure(users.get(i).getFigure());
            goodsBean.setName(users.get(i).getName());
            goodsBean.setNumber(users.get(i).getNumber());
            goodsBean.setChecked(users.get(i).getIsChecked());
            goodsBean.setProduct_id(users.get(i).getId()+"");
            goodsBeens.add(goodsBean);
        }

//        List<GoodsBean> goodsBeens = CartStorage.getInstance(mContext).getAllData();
        //有数据，影藏空界面
        if(goodsBeens!=null&goodsBeens.size()>0) {
            llEmptyShopcart.setVisibility(View.GONE);
            /*
            * 设置适配器
            * */
            adapter = new ShoppingCartAdapter(this,goodsBeens,tvShopcartTotal,checkboxAll,checkboxDeleteAll);
            recyclerview.setAdapter(adapter);
            //设置布局管理器
            recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            //没有数据，显示空界面
        }else{
            llEmptyShopcart.setVisibility(View.VISIBLE);

        }
    }
    @OnClick({R.id.tv_shopcart_edit, R.id.recyclerview, R.id.checkbox_all, R.id.tv_shopcart_total, R.id.btn_check_out, R.id.ll_check_all, R.id.checkbox_delete_all, R.id.btn_delete, R.id.btn_collection, R.id.ll_delete, R.id.iv_empty, R.id.tv_empty_cart_tobuy, R.id.ll_empty_shopcart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_shopcart_edit:
                break;
            case R.id.recyclerview:
                break;
            //全选按钮
            case R.id.checkbox_all:
                //得到全选的CheckBox按钮状态
                boolean ischecked=checkboxAll.isChecked();
                //调用adapter中的方法具体操作每个item数据
                adapter.checkAll_none(ischecked);
                //刷新价格
                adapter.showTotalPrice();
                break;
            case R.id.tv_shopcart_total:
                break;
            case R.id.btn_check_out:
//                pay();
               /* Intent toPayIntent=new Intent(mContext, MyPayActivity.class);
                mContext.startActivity(toPayIntent);*/
                break;
            case R.id.ll_check_all:
                break;
            //删除状态下的全选按钮
            case R.id.checkbox_delete_all:
                ischecked=checkboxDeleteAll.isChecked();
                adapter.checkAll_none(ischecked);
                adapter.showTotalPrice();
                break;
            case R.id.btn_delete:
                //调用adapter中的方法具体操作
                adapter.deleteData();
                //检查状态 设置全选按钮的状态
                adapter.checkAll();
                //检查是否为空数据，来决定显示的界面
                showEempty();
                break;
            case R.id.btn_collection:
                break;
            case R.id.ll_delete:
                break;
            case R.id.iv_empty:
                break;
            case R.id.tv_empty_cart_tobuy:

                /*Intent intent=new Intent(Constants.GOTOHOME);
                manager.sendBroadcast(intent);*/
                Intent intent = new Intent(this, GoodsListActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.ll_empty_shopcart:
                break;
        }
    }
    //检查是否为空数据，来决定显示的界面
    private void showEempty() {
        if(adapter.getItemCount() == 0){
            llEmptyShopcart.setVisibility(View.VISIBLE);
        }
    }

}
