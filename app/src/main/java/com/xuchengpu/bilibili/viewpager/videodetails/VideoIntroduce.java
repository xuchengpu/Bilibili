package com.xuchengpu.bilibili.viewpager.videodetails;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.activity.CartActivity;
import com.xuchengpu.bilibili.activity.DownLoadActivity;
import com.xuchengpu.bilibili.base.BaseViewPager;
import com.xuchengpu.bilibili.bean.RecommandComprehensiveBean;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 许成谱 on 2017/3/27 18:50.
 * qq:1550540124
 * for:
 */

public class VideoIntroduce extends BaseViewPager {
    private final RecommandComprehensiveBean.DataBean data;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_play_time)
    TextView tvPlayTime;
    @BindView(R.id.tv_review_count)
    TextView tvReviewCount;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.share_num)
    TextView shareNum;
    @BindView(R.id.iv_share)
    ImageButton ivShare;
    @BindView(R.id.btn_share)
    LinearLayout btnShare;
    @BindView(R.id.coin_num)
    TextView coinNum;
    @BindView(R.id.btn_coin)
    LinearLayout btnCoin;
    @BindView(R.id.fav_num)
    TextView favNum;
    @BindView(R.id.btn_fav)
    LinearLayout btnFav;
    @BindView(R.id.iv_download)
    ImageButton ivDownload;
    @BindView(R.id.btn_download)
    LinearLayout btnDownload;
    @BindView(R.id.tags_layout)
    TagFlowLayout tagsLayout;
    @BindView(R.id.iv_push)
    ImageButton ivPush;
    @BindView(R.id.iv_tocart)
    ImageButton ivTocart;
    private Intent intent;

    public VideoIntroduce(Context context, RecommandComprehensiveBean.DataBean data) {
        super(context);
        this.data = data;
        tvTitle.setText(data.getTitle() + "");
        tvPlayTime.setText(data.getDuration() + "");
        tvReviewCount.setText(data.getPlay() + "");
        tvDescription.setText(data.getDesc() + "");
    }

    @Override
    protected int getChildLayoutId() {
        return R.layout.video_introduce;
    }

    @Override
    protected String getChildUrl() {
        return null;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData(String json) {


    }

    @OnClick({R.id.iv_share, R.id.iv_download,R.id.iv_push, R.id.iv_tocart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_share:
                share();
                break;
            case R.id.iv_download:
                 intent = new Intent(mContext, DownLoadActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.iv_push:
                break;
            case R.id.iv_tocart:
                Intent intent = new Intent(mContext, CartActivity.class);
                mContext.startActivity(intent);

                break;
        }
    }

    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "来自「许成谱」的分享:" + data.getCover());
        mContext.startActivity(Intent.createChooser(intent, data.getTitle()));

    }
}
