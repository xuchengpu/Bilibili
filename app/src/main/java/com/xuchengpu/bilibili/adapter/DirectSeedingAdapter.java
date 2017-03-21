package com.xuchengpu.bilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.bean.DirectSeedingTypeBean;
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

    public DirectSeedingAdapter(Context mContext, DirectSeedingTypeBean.DataBean data) {
        this.mContext = mContext;
        this.datas = data;
        inflater = LayoutInflater.from(mContext);
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
        }else  if(viewType==entranceIcons) {
            return new EntranceIconsViewHolder(inflater.inflate(R.layout.item_entranceicons, null));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(datas.getBanner());
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


        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        void setData(List<DirectSeedingTypeBean.DataBean.BannerBean> banner_info){
            List images = new ArrayList();
            for (int i = 0; i < banner_info.size(); i++) {
                images.add( banner_info.get(i).getImg());
                images.add( banner_info.get(i).getImg());
                images.add( banner_info.get(i).getImg());
                images.add( banner_info.get(i).getImg());
            }
            banner.setImages(images)
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
            banner.setBannerAnimation(ZoomOutSlideTransformer.class);
        }

    }
    class EntranceIconsViewHolder extends RecyclerView.ViewHolder{

        public EntranceIconsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
