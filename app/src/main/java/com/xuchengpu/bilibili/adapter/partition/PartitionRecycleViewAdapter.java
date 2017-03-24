package com.xuchengpu.bilibili.adapter.partition;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.bean.PartitionRecycleViewBean;
import com.xuchengpu.bilibili.bean.PartitonGridViewBean;
import com.xuchengpu.bilibili.view.CircleImageView;
import com.xuchengpu.bilibili.view.MyGridView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.transformer.ZoomOutSlideTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/3/23 15:46.
 * qq:1550540124
 * for:
 */

public class PartitionRecycleViewAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private final List<PartitionRecycleViewBean.DataBean> listData;
    private final List<PartitonGridViewBean.DataBean> channelData;

    private LayoutInflater inflater;
    private static final int CHANNAL = 0;
    private static final int LIST = 1;
    private int currentItem = 0;

    public PartitionRecycleViewAdapter(Context mContext, List<PartitionRecycleViewBean.DataBean> data, List<PartitonGridViewBean.DataBean> dataBeen) {
        this.mContext = mContext;
        this.listData = data;
        this.channelData = dataBeen;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        if (position ==0) {
            currentItem = CHANNAL;
        } else {
            currentItem = LIST;
        }
        return currentItem;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == CHANNAL) {
            return new ChannelViewHolder(inflater.inflate(R.layout.item_channel_partition, null));
        } else if(viewType==LIST) {
            return new ListViewHolder(inflater.inflate(R.layout.item_recycleview_partition, null));
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==CHANNAL) {
            ChannelViewHolder viewHolder= (ChannelViewHolder) holder;
            viewHolder.setData(channelData);
        }else if(getItemViewType(position)==LIST){
            ListViewHolder viewHolder= (ListViewHolder) holder;
            viewHolder.setData(listData.get(position-1));
        }


    }

    @Override
    public int getItemCount() {
        return listData.size() + 1;
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.gv_partition)
        MyGridView gvPartition;
        public ChannelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(final List<PartitonGridViewBean.DataBean> channelData) {
            PartitionGridViewAdapter gridAdapter = new PartitionGridViewAdapter(mContext, channelData);
            gvPartition.setAdapter(gridAdapter);
            gvPartition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, "" + channelData.get(position).getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_drawer_partition)
        ImageView ivDrawerPartition;
        @BindView(R.id.tv_partition_name)
        TextView tvPartitionName;
        @BindView(R.id.tv_total_partition)
        TextView tvTotalPartition;
        @BindView(R.id.gv_partition)
        MyGridView gvPartition;
        @BindView(R.id.tv_more_partition)
        TextView tvMorePartition;
        @BindView(R.id.tv_dynamic_partition)
        TextView tvDynamicPartition;
        @BindView(R.id.iv_refresh_partition)
        CircleImageView ivRefreshPartition;
        @BindView(R.id.banner_pritition)
        Banner banner;

        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(PartitionRecycleViewBean.DataBean bean) {
            tvPartitionName.setText(bean.getTitle());
            String title = bean.getTitle();
            if (title.length() > 2) {
                tvMorePartition.setText("更多" + bean.getTitle().substring(0, 2));
            }
            tvDynamicPartition.setText(bean.getParam() + "条新动态，点击刷新");
            PartitionListGridViewAdapter listGridViewAdapter = new PartitionListGridViewAdapter(mContext, bean.getBody());
            gvPartition.setAdapter(listGridViewAdapter);
            List images = new ArrayList();
            this.banner.setVisibility(View.GONE);
            PartitionRecycleViewBean.DataBean.BannerBean bannerData = bean.getBanner();
            if (bannerData != null && bannerData.getBottom().size() > 0) {
                this.banner.setVisibility(View.VISIBLE);
                for (int i = 0; i < bean.getBanner().getBottom().size(); i++) {
                    images.add(bannerData.getBottom().get(i).getImage());
                }
                this.banner.setImages(images)
                        .setImageLoader(new ImageLoader() {
                            @Override
                            public void displayImage(Context context, Object path, ImageView imageView) {
                                Glide.with(context)
                                        .load(path)
                                        .crossFade()
                                        .into(imageView);
                            }
                        })
                        .start();
                //设置样式
//            banner.setBannerAnimation(BackgroundToForegroundTransformer.class);
                this.banner.setBannerAnimation(ZoomOutSlideTransformer.class);
            }

        }
    }
}
