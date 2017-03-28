package com.xuchengpu.bilibili.adapter.recommandadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.activity.VideoDetailsActivity;
import com.xuchengpu.bilibili.bean.RecommandComprehensiveBean;
import com.xuchengpu.bilibili.utils.ConstantUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/3/23 14:00.
 * qq:1550540124
 * for:
 */

public class ComprehensiveRecycleViewadapter extends RecyclerView.Adapter {
    private final Context mContext;
    private final List<RecommandComprehensiveBean.DataBean> data;

    private LayoutInflater inflater;

    public ComprehensiveRecycleViewadapter(Context mContext, List<RecommandComprehensiveBean.DataBean> data) {
        this.mContext = mContext;
        this.data = data;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(inflater.inflate(R.layout.item_comprehensive, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.setData(data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_recommend)
        ImageView ivRecommend;
        @BindView(R.id.tv_play_number)
        TextView tvPlayNumber;
        @BindView(R.id.tv_danmu_number)
        TextView tvDanmuNumber;
        @BindView(R.id.tv_vedio_duration)
        TextView tvVedioDuration;
        @BindView(R.id.tv_type_name)
        TextView tvTypeName;
        @BindView(R.id.tv_recom_name)
        TextView tvRecomName;
        @BindView(R.id.tv_more)
        TextView tvMore;
        @BindView(R.id.item_live_layout)
        CardView itemLiveLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(final RecommandComprehensiveBean.DataBean dataBean) {
            Glide.with(mContext).load(dataBean.getCover()).into(ivRecommend);
            tvPlayNumber.setText(""+dataBean.getPlay());
            tvDanmuNumber.setText(""+dataBean.getDanmaku());
            tvVedioDuration.setText(""+dataBean.getDuration());
            tvTypeName.setText(dataBean.getTname());
            tvRecomName.setText(dataBean.getName());
            itemLiveLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, VideoDetailsActivity.class);
                    intent.putExtra(ConstantUtils.RECOMMAND_VIDEO,dataBean);
                    mContext.startActivity(intent);
                }
            });

        }
    }
}
