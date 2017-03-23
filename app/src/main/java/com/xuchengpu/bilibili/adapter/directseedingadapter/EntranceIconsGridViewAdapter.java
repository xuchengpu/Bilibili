package com.xuchengpu.bilibili.adapter.directseedingadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.bean.DirectSeedingTypeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/3/22 9:32.
 * qq:1550540124
 * for:
 */

public class EntranceIconsGridViewAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<DirectSeedingTypeBean.DataBean.PartitionsBean.LivesBean> datas;

    public EntranceIconsGridViewAdapter(Context mContext, List<DirectSeedingTypeBean.DataBean.PartitionsBean.LivesBean> lives) {
        this.mContext = mContext;
        this.datas = lives;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
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
         ViewHolder holder=null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_gridview_entranceicons, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(datas.get(position).getCover().getSrc()).into(holder.ivItemGridviewEntranceicons);
        holder.tvDescItemGridviewEntranceicons.setText(datas.get(position).getTitle());
        holder.tvNameItemGridviewEntranceicons.setText(datas.get(position).getOwner().getName());
//        holder.tvEyeItemGridviewEntranceicons.setText(datas.get(position).getOnline());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_item_gridview_entranceicons)
        ImageView ivItemGridviewEntranceicons;
        @BindView(R.id.tv_desc_item_gridview_entranceicons)
        TextView tvDescItemGridviewEntranceicons;
        @BindView(R.id.tv_name_item_gridview_entranceicons)
        TextView tvNameItemGridviewEntranceicons;
        @BindView(R.id.tv_eye_item_gridview_entranceicons)
        TextView tvEyeItemGridviewEntranceicons;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
