package com.xuchengpu.bilibili.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.bean.ComprehensiveSearchBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/3/30 16:55.
 * qq:1550540124
 * for:
 */

public class ComprehensiveSortingAdapter extends RecyclerView.Adapter<ComprehensiveSortingAdapter.ViewHolder> {


    private Context mContext;
    private List<ComprehensiveSearchBean.DataBean.ItemsBean.ArchiveBean> datas;
    private LayoutInflater inflater;

    public ComprehensiveSortingAdapter(Context mContext, List<ComprehensiveSearchBean.DataBean.ItemsBean.ArchiveBean> archive) {
        this.mContext = mContext;
        this.datas = archive;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(inflater.inflate(R.layout.item_comprehensive_sort, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(mContext).load(datas.get(position).getCover()).into(holder.itemImg);
        holder.itemUserName.setText(datas.get(position).getAuthor());
        holder.itemPlay.setText(datas.get(position).getPlay()+"");
        holder.itemReview.setText(datas.get(position).getDanmaku()+"");
        holder.itemTitle.setText(datas.get(position).getTitle());
        holder.itemDuration.setText(datas.get(position).getDuration());
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_img)
        ImageView itemImg;
        @BindView(R.id.item_duration)
        TextView itemDuration;
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.item_user_name)
        TextView itemUserName;
        @BindView(R.id.item_play)
        TextView itemPlay;
        @BindView(R.id.item_review)
        TextView itemReview;
        @BindView(R.id.layout_play)
        LinearLayout layoutPlay;
        @BindView(R.id.item_view)
        RelativeLayout itemView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


}
