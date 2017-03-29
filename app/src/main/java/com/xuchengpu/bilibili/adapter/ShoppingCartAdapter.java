package com.xuchengpu.bilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.anye.greendao.gen.UserDao;
import com.bumptech.glide.Glide;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.bean.GoodsBean;
import com.xuchengpu.bilibili.bean.User;
import com.xuchengpu.bilibili.view.AddSubView;
import com.xuchengpu.bilibili.view.MyApplication;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/2/28 15:38.
 * qq:1550540124
 * for:
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder> {

    private TextView tvShopcartTotal;
    private CheckBox checkboxAll;
    private CheckBox checkboxDeleteAll;
    private Context mContext;
    private List<GoodsBean> datas;
    private UserDao mUserDao;

    /*
    * 将tvShopcartTotal、checkboxAll、checkboxDeleteAll对象直接传进来，方把根据每一个item数据的监听结果回调操作
    * */
    public ShoppingCartAdapter(Context mContext, List<GoodsBean> goodsBeens, TextView tvShopcartTotal, CheckBox checkboxAll, CheckBox checkboxDeleteAll) {
        this.mContext = mContext;
        this.datas = goodsBeens;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        this.checkboxDeleteAll = checkboxDeleteAll;
        mUserDao = MyApplication.getInstances().getDaoSession().getUserDao();
        //刚进入时就刷新一下价格及是否全选状态
        showTotalPrice();


    }
    /*
    * 创建viewholder对象
    * */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(View.inflate(mContext, R.layout.item_shop_cart, null));
    }
    /*
    * 绑定数据
    * */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final GoodsBean goodsBean = datas.get(position);
        //对每一个holder属性设置参数，在recycleview中显示出来
        holder.cbGov.setChecked(goodsBean.isChecked());
        Glide.with(mContext).load( goodsBean.getFigure()).into(holder.ivGov);
        holder.tvDescGov.setText(goodsBean.getName());
        holder.tvPriceGov.setText("￥" + goodsBean.getCover_price());
        holder.addSubView.setValue(goodsBean.getNumber());
        holder.addSubView.setMaxValue(100);
        holder.addSubView.setMinValue(1);

        //实现AddSubView类对外提供的接口，接受传递过来的数量value数据，动态的设置到每一个 goodsBean数据中
       holder.addSubView.setOnNumberChangerListener(new AddSubView.OnNumberChangerListener() {
            @Override
            public void OnNumberChanger(int value) {
                //得到从AddSubView类回调的value值后  重新设置给goodsbean对象 再刷新
                //从集合中取出的 goodsBean设置属性值后不用再重新添加回list集合中，因为引用的本身就是bean对象的地址值，
                // 改变属性后，list集合中相应对象属性自动就改变了
                goodsBean.setNumber(value);

                User mUser = new User(Long.parseLong(goodsBean.getProduct_id()), goodsBean.getCover_price(), goodsBean.getFigure(), goodsBean.getName(), goodsBean.getNumber(), goodsBean.isChecked());
                mUserDao.update(mUser);
//                CartStorage.getInstance(mContext).updata(goodsBean);
                showTotalPrice();
            }
        });

    }

    public void showTotalPrice() {
        //显示总价格
        tvShopcartTotal.setText("合计:" + getTotalPrice());
        checkAll();
    }
/*
* 显示总价格
* */
    public double getTotalPrice() {
        double totlePrice = 0;
        //通过遍历每一条数据
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i).isChecked()) {
                    totlePrice += Double.parseDouble(datas.get(i).getCover_price()) * datas.get(i).getNumber();
                }
            }
        }
        return totlePrice;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    //通过遍历每一条数据具体设置各个属性
    public void checkAll_none(boolean ischecked) {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                goodsBean.setChecked(ischecked);

                User mUser = new User(Long.parseLong(goodsBean.getProduct_id()), goodsBean.getCover_price(), goodsBean.getFigure(), goodsBean.getName(), goodsBean.getNumber(), goodsBean.isChecked());
                mUserDao.update(mUser);

//                CartStorage.getInstance(mContext).updata(goodsBean);
                checkboxAll.setChecked(ischecked);
                checkboxDeleteAll.setChecked(ischecked);
                //更新视图 改变属性的刷新数据方法
                notifyItemChanged(i);
                // notifyDataSetChanged();

            }
        } else {
            checkboxAll.setChecked(false);
        }

    }
    // 遍历时根据各自的check属性来决定是否移除
    public void deleteData() {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                //当勾选的才删除
                if (goodsBean.isChecked()) {
                    //1.内存中删除
                    datas.remove(goodsBean);
                    //2.本地也好保持

                    mUserDao.deleteByKey(Long.parseLong(goodsBean.getProduct_id()));
//                    CartStorage.getInstance(mContext).deleteData(goodsBean);
                    //移除的刷新数据方法
                    notifyItemRemoved(i);
                    i--;
                }
            }
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cb_gov)
        CheckBox cbGov;
        @BindView(R.id.iv_gov)
        ImageView ivGov;
        @BindView(R.id.tv_desc_gov)
        TextView tvDescGov;
        @BindView(R.id.tv_price_gov)
        TextView tvPriceGov;
        @BindView(R.id.addSubView)
        AddSubView addSubView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //屏蔽了CheckBox被点击的属性后，通过对item设置监听来改变CheckBox的选中状态，并设置到bean对象里面。
            // 以此避免CheckBox被复用产生的对应全选或全不选的问题
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoodsBean goodsBean = datas.get(getLayoutPosition());
                    goodsBean.setChecked(!goodsBean.isChecked());

                    User mUser = new User(Long.parseLong(goodsBean.getProduct_id()), goodsBean.getCover_price(), goodsBean.getFigure(), goodsBean.getName(), goodsBean.getNumber(), goodsBean.isChecked());
                    mUserDao.update(mUser);
//                    CartStorage.getInstance(mContext).updata(goodsBean);

                    showTotalPrice();
                    notifyDataSetChanged();

                }
            });
        }

    }
    //检查每个item的CheckBox属性，来决定全选按钮的显示状态
    public void checkAll() {
        if (datas != null && datas.size() > 0) {
            int num = 0;
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (goodsBean.isChecked()) {
                    num++;
                }
            }
            if (num == datas.size()) {
                checkboxAll.setChecked(true);
                checkboxDeleteAll.setChecked(true);
            } else {
                checkboxAll.setChecked(false);
                checkboxDeleteAll.setChecked(false);
            }
        }
        /*if (datas != null && datas.size() > 0) {
            int number = 0;
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (!goodsBean.isChecked()) {
                    checkboxAll.setChecked(false);
                    checkboxDeleteAll.setChecked(false);
                } else {
                    number++;

                }

            }

            if(datas.size() == number){
                checkboxAll.setChecked(true);
                checkboxDeleteAll.setChecked(true);
            }
        }else {
            checkboxAll.setChecked(false);
            checkboxDeleteAll.setChecked(false);
        }*/

    }
}
