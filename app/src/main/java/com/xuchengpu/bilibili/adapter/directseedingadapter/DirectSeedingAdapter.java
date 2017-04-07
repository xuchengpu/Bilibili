package com.xuchengpu.bilibili.adapter.directseedingadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.activity.MainActivity;
import com.xuchengpu.bilibili.activity.PicassoSampleActivity;
import com.xuchengpu.bilibili.bean.DirectSeedingTypeBean;
import com.xuchengpu.bilibili.view.CircleImageView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.transformer.ZoomOutSlideTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/3/21 22:39.
 * qq:1550540124
 * for:
 */

public class DirectSeedingAdapter extends RecyclerView.Adapter {

    private final Context mContext;
    private final DirectSeedingTypeBean.DataBean datas;

    /*
   根据json解析可知result中含有六种数据类型，对应六种viewholder
   * */
    public static final int BANNER = 0;
    public static final int entranceIcons = 1;



    private int currentType = BANNER;
    private final LayoutInflater inflater;
    private final MainActivity mainActivity;

    public DirectSeedingAdapter(Context mContext, DirectSeedingTypeBean.DataBean data) {
        this.mContext = mContext;
        this.datas = data;
        inflater = LayoutInflater.from(mContext);
        mainActivity = (MainActivity) mContext;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == BANNER) {
            currentType = BANNER;
        } else {
            currentType = entranceIcons;
        }
        return currentType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(inflater.inflate(R.layout.item_banner, null));
        } else if (viewType == entranceIcons) {
            return new EntranceIconsViewHolder(inflater.inflate(R.layout.item_entranceicons, null));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(datas.getBanner());
        } else if (getItemViewType(position) == entranceIcons) {
            EntranceIconsViewHolder entranceViewHolder = (EntranceIconsViewHolder) holder;
            entranceViewHolder.setData(datas.getPartitions().get(position - 1));

        }

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.directseeding_banner)
        Banner banner;
        @BindView(R.id.ll_live_follow)
        LinearLayout llLiveFollow;
        @BindView(R.id.ll_live_center)
        LinearLayout llLiveCenter;
        @BindView(R.id.ll_live_video)
        LinearLayout llLiveVideo;
        @BindView(R.id.ll_live_search)
        LinearLayout llLiveSearch;
        @BindView(R.id.ll_live_categroy)
        LinearLayout llLiveCategroy;
        @BindView(R.id.iv_attention)
        ImageView ivAttention;
        @BindView(R.id.iv_center)
        ImageView ivCenter;
        @BindView(R.id.iv_movie)
        ImageView ivMovie;
        @BindView(R.id.iv_search)
        ImageView ivSearch;
        @BindView(R.id.imageView)
        ImageView imageView;

        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setData(final List<DirectSeedingTypeBean.DataBean.BannerBean> banner_info) {
            List images = new ArrayList();
            for (int i = 0; i < banner_info.size(); i++) {
                images.add(banner_info.get(i).getImg());
                images.add(banner_info.get(i).getImg());
                images.add(banner_info.get(i).getImg());
                images.add(banner_info.get(i).getImg());
            }
            banner.setImages(images)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(final Context context, final Object path, ImageView imageView) {
                            Glide.with(context)
                                    .load(path)
                                    .crossFade()
                                    .into(imageView);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(mContext, PicassoSampleActivity.class);
                                    intent.putExtra("url", (String)path);
                                    context.startActivity(intent);
                                }
                            });
                        }
                    })
                    .start();
            //设置样式
//            banner.setBannerAnimation(BackgroundToForegroundTransformer.class);
            banner.setBannerAnimation(ZoomOutSlideTransformer.class);




            ivAttention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.setCurrentItem(2);

                }
            });
            ivCenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.setCurrentItem(4);

                }
            });
            ivMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.setCurrentItem(1);

                }
            });
            ivSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.setCurrentItem(4);

                }
            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.setCurrentItem(3);

                }
            });

        }

    }

    class EntranceIconsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_drawer_entranceicons)
        ImageView ivDrawerEntranceicons;
        @BindView(R.id.tv_entranceicons_name)
        TextView tvEntranceiconsName;
        @BindView(R.id.tv_total)
        TextView tvTotal;
        @BindView(R.id.gv_entranceicons)
        GridView gv;
        @BindView(R.id.tv_more_entranceicons)
        TextView tvMoreEntranceicons;
        @BindView(R.id.tv_dynamic_entranceicons)
        TextView tvDynamicEntranceicons;
        @BindView(R.id.iv_refresh_entranceicons)
        CircleImageView ivRefreshEntranceicons;

        public EntranceIconsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(DirectSeedingTypeBean.DataBean.PartitionsBean partitionsBean) {
            Glide.with(mContext).load(partitionsBean.getPartition().getSub_icon().getSrc()).into(ivDrawerEntranceicons);
            tvEntranceiconsName.setText(partitionsBean.getPartition().getName());
            tvTotal.setText("当前" + partitionsBean.getPartition().getCount() + "个直播");
            tvDynamicEntranceicons.setText(partitionsBean.getPartition().getCount() + 18 + "条新动态，点击刷新");
            tvDynamicEntranceicons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "点击刷新", Toast.LENGTH_SHORT).show();
                }
            });
            tvMoreEntranceicons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "加载更多", Toast.LENGTH_SHORT).show();
                }
            });
            EntranceIconsGridViewAdapter adapter = new EntranceIconsGridViewAdapter(mContext, partitionsBean.getLives());
            gv.setAdapter(adapter);
            /*gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, ""+position, Toast.LENGTH_SHORT).show();
                }
            });*/

        }
    }
}
