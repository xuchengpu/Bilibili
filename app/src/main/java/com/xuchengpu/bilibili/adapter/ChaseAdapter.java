package com.xuchengpu.bilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.bean.ChaseBean;
import com.xuchengpu.bilibili.view.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/3/24 8:41.
 * qq:1550540124
 * for:
 */

public class ChaseAdapter extends RecyclerView.Adapter {
    private final ChaseBean.ResultBean result;
    private final Context mContext;
    private static final int HEAD = 0;
    private static final int PREVIOUS = 1;
    private static final int SERIALIZING = 2;


    private int currentItem;
    private LayoutInflater inflate;

    public ChaseAdapter(Context mContext, ChaseBean.ResultBean result) {
        this.mContext = mContext;
        this.result = result;
        inflate = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            currentItem = HEAD;
        } else if (position == 1) {
            currentItem = PREVIOUS;
        } else if (position == 2) {
            currentItem = SERIALIZING;
        }
        return currentItem;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD) {
            return new HeadViewHolder(inflate.inflate(R.layout.item_head_chase, null));
        } else if (viewType == PREVIOUS) {
            return new BodyViewHolder(mContext, inflate.inflate(R.layout.item_body_chase, null));
        } else if (viewType == SERIALIZING) {
            return new BodyViewHolder(mContext, inflate.inflate(R.layout.item_body_chase, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HEAD) {
            HeadViewHolder viewHolder = (HeadViewHolder) holder;
            viewHolder.setData();
        } else if (getItemViewType(position) == PREVIOUS) {
            BodyViewHolder viewHolder = (BodyViewHolder) holder;
            viewHolder.setPrevioustData(result.getPrevious().getList());
        } else if (getItemViewType(position) == SERIALIZING) {
            BodyViewHolder viewHolder = (BodyViewHolder) holder;
            viewHolder.setSerializingData(result.getSerializing());
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_fanju_chase)
        ImageView ivFanjuChase;
        @BindView(R.id.iv_fanjuimage_chase)
        ImageView ivFanjuimageChase;
        @BindView(R.id.iv_guoman_chase)
        ImageView ivGuomanChase;
        @BindView(R.id.iv_guomanimage_chase)
        ImageView ivGuomanimageChase;
        @BindView(R.id.iv_timer_chase)
        ImageView ivTimerChase;
        @BindView(R.id.tv_timer_chase)
        TextView tvTimerChase;
        @BindView(R.id.iv_index_chase)
        ImageView ivIndexChase;
        @BindView(R.id.tv_index_chase)
        TextView tvIndexChase;
        @BindView(R.id.iv_guide_chase)
        ImageView ivGuideChase;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData() {

        }
    }

    class BodyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_drawer_entranceicons)
        ImageView ivDrawerEntranceicons;
        @BindView(R.id.tv_entranceicons_name)
        TextView tvEntranceiconsName;
        @BindView(R.id.tv_total)
        TextView tvTotal;
        @BindView(R.id.rl_entranceicons)
        RelativeLayout rlEntranceicons;
        @BindView(R.id.ll_hot_right)
        LinearLayout llHotRight;
        @BindView(R.id.hsl_chase)
        HorizontalScrollView hslChase;
        @BindView(R.id.tv_more_entranceicons)
        TextView tvMoreEntranceicons;
        @BindView(R.id.tv_dynamic_entranceicons)
        TextView tvDynamicEntranceicons;
        @BindView(R.id.iv_refresh_entranceicons)
        CircleImageView ivRefreshEntranceicons;


        public BodyViewHolder(Context mContext, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


        public void setPrevioustData(List<ChaseBean.ResultBean.PreviousBean.ListBean> list) {
            for(int i = 0; i < list.size(); i++) {
                View view;
                ImageView ivPreviousChase;
                TextView tvNumPreviousChase;
                TextView tvDescPreviousChase;
                TextView tvUpdataPreviousChase;
                view = View.inflate(mContext, R.layout.item_previous, null);
                ivPreviousChase= (ImageView) view.findViewById(R.id.iv_previous_chase);
                tvNumPreviousChase= (TextView) view.findViewById(R.id.tv_num_previous_chase);
                tvDescPreviousChase= (TextView) view.findViewById(R.id.tv_desc_previous_chase);
                tvUpdataPreviousChase= (TextView) view.findViewById(R.id.tv_updata_previous_chase);
                Glide.with(mContext).load(list.get(i).getCover()).into(ivPreviousChase);
                tvNumPreviousChase.setText(list.get(i).getFavourites()+"人追番");
                tvDescPreviousChase.setText(list.get(i).getTitle());
                tvUpdataPreviousChase.setText("更新至第"+list.get(i).getNewest_ep_index()+"话");
                llHotRight.addView(view);
            }
        }


        public void setSerializingData(List<ChaseBean.ResultBean.SerializingBean> serializing) {
            for(int i = 0; i < serializing.size(); i++) {
                View view;
                ImageView ivPreviousChase;
                TextView tvNumPreviousChase;
                TextView tvDescPreviousChase;
                TextView tvUpdataPreviousChase;
                view = View.inflate(mContext, R.layout.item_previous, null);
                ivPreviousChase= (ImageView) view.findViewById(R.id.iv_previous_chase);
                tvNumPreviousChase= (TextView) view.findViewById(R.id.tv_num_previous_chase);
                tvDescPreviousChase= (TextView) view.findViewById(R.id.tv_desc_previous_chase);
                tvUpdataPreviousChase= (TextView) view.findViewById(R.id.tv_updata_previous_chase);
                Glide.with(mContext).load(serializing.get(i).getCover()).into(ivPreviousChase);
                tvNumPreviousChase.setText(serializing.get(i).getFavourites()+"人追番");
                tvDescPreviousChase.setText(serializing.get(i).getTitle());
                tvUpdataPreviousChase.setText("更新至第"+serializing.get(i).getNewest_ep_index()+"话");
                llHotRight.addView(view);
            }
        }
    }
}
