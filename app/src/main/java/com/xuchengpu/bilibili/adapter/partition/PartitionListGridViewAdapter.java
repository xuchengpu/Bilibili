package com.xuchengpu.bilibili.adapter.partition;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.bean.PartitionRecycleViewBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/3/23 18:57.
 * qq:1550540124
 * for:
 */

public class PartitionListGridViewAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<PartitionRecycleViewBean.DataBean.BodyBean> datas;

    public PartitionListGridViewAdapter(Context mContext, List<PartitionRecycleViewBean.DataBean.BodyBean> body) {
        this.mContext = mContext;
        this.datas = body;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size()/2*2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_partiotion_list_gridview, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(datas.get(position).getCover()).into(holder.ivRecommend);
        holder.tvPlayNumber.setText(""+datas.get(position).getPlay());
        holder.tvDanmuNumber.setText(datas.get(position).getDanmaku()+"");
        holder.tvTypeName.setText(datas.get(position).getTitle());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_recommend)
        ImageView ivRecommend;
        @BindView(R.id.tv_type_name)
        TextView tvTypeName;
        @BindView(R.id.tv_play_number)
        TextView tvPlayNumber;
        @BindView(R.id.tv_danmu_number)
        TextView tvDanmuNumber;
        @BindView(R.id.item_live_layout)
        CardView itemLiveLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
