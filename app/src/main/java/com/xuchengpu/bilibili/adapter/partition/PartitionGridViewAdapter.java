package com.xuchengpu.bilibili.adapter.partition;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.bean.PartitonGridViewBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/3/23 16:18.
 * qq:1550540124
 * for:
 */

public class PartitionGridViewAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<PartitonGridViewBean.DataBean> data;

    public PartitionGridViewAdapter(Context mContext, List<PartitonGridViewBean.DataBean> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_partition_gridview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(data.get(position).getEntrance_icon().getSrc()).into(holder.ivGridViewPartition);
        holder.tvGridviewPartition.setText(data.get(position).getName());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_gridView_partition)
        ImageView ivGridViewPartition;
        @BindView(R.id.tv_gridview_partition)
        TextView tvGridviewPartition;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
