package com.xuchengpu.bilibili.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.activity.WebViewActivity;
import com.xuchengpu.bilibili.bean.TopicCenterBean;
import com.xuchengpu.bilibili.utils.ConstantUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/3/25 16:44.
 * qq:1550540124
 * for:
 */

public class TopicCenterAdapter extends BaseAdapter {
    private final Context mContext;
    private List<TopicCenterBean.ListBean> datas;

    public TopicCenterAdapter(Context context) {
        this.mContext = context;
        datas = new ArrayList<TopicCenterBean.ListBean>();
    }

    public void refresh(List<TopicCenterBean.ListBean> invitationInfos) {
        //校验
        if (invitationInfos == null) {
            return;
        }
        //清楚原有数据
        datas.clear();
        //添加新的数据
        datas.addAll(invitationInfos);
        //刷新
        notifyDataSetChanged();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_topic_center, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        String cover = datas.get(position).getCover();
        if(!TextUtils.isEmpty(cover)) {
            Glide.with(mContext).load(datas.get(position).
                    getCover()).placeholder(mContext.getResources().getDrawable(R.drawable.ic_group_header_bg)).into(holder.ivTopiccenter);
            holder.tvTopiccenter.setText(datas.get(position).getTitle());
        }
        holder.ivTopiccenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, ""+datas.get(position).getLink(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(ConstantUtils.SCAN,datas.get(position).getLink());
                mContext.startActivity(intent);
            }
        });


        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_topiccenter)
        ImageView ivTopiccenter;
        @BindView(R.id.tv_topiccenter)
        TextView tvTopiccenter;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
