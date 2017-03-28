package com.xuchengpu.bilibili.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.opendanmaku.DanmakuItem;
import com.opendanmaku.DanmakuView;
import com.opendanmaku.IDanmakuItem;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.adapter.recommandadapter.RecommandAdapter;
import com.xuchengpu.bilibili.base.BaseViewPager;
import com.xuchengpu.bilibili.bean.RecommandComprehensiveBean;
import com.xuchengpu.bilibili.event.AppBarStateChangeEvent;
import com.xuchengpu.bilibili.utils.ConstantUtils;
import com.xuchengpu.bilibili.utils.SystemBarHelper;
import com.xuchengpu.bilibili.utils.UiUtils;
import com.xuchengpu.bilibili.viewpager.videodetails.VideoComments;
import com.xuchengpu.bilibili.viewpager.videodetails.VideoIntroduce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideoDetailsActivity extends AppCompatActivity {

    @BindView(R.id.video_preview)
    ImageView videoPreview;
    @BindView(R.id.tv_av)
    TextView mAvText;
    @BindView(R.id.tv_player)
    TextView mTvPlayer;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.fab)
    FloatingActionButton mFAB;
    @BindView(R.id.jcv_videoplayer)
    JCVideoPlayerStandard jcvVideoplayer;
    @BindView(R.id.video_danmu)
    FrameLayout videoDanmu;
    @BindView(R.id.danmakuView)
    DanmakuView danmakuView;
    private List<BaseViewPager> viewPagers;
    private RecommandComprehensiveBean.DataBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
        ButterKnife.bind(this);
        getData();
        initToolBar();
        initView();
        initViewPagers();
        setAdapter();

    }

    private void getData() {
        data = (RecommandComprehensiveBean.DataBean) getIntent().getSerializableExtra(ConstantUtils.RECOMMAND_VIDEO);
    }

    private void initView() {
        mFAB.setClickable(true);
        mFAB.setBackgroundTintList(
                ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        mCollapsingToolbarLayout.setTitle("");

//        mFAB.setClickable(false);
//        mFAB.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray_20)));
        mFAB.setTranslationY(-getResources().getDimension(R.dimen.floating_action_button_size_half));
//        mFAB.setOnClickListener(v -> VideoPlayerActivity.launch(VideoDetailsActivity.this,
//                mVideoDetailsInfo.getPages().get(0).getCid(), mVideoDetailsInfo.getTitle()));

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                setViewsTranslation(verticalOffset);
            }
        });

        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeEvent() {

            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state, int verticalOffset) {

                if (state == State.EXPANDED) {
                    //展开状态
                    mTvPlayer.setVisibility(View.GONE);
                    mAvText.setVisibility(View.VISIBLE);
                    mToolbar.setContentInsetsRelative(UiUtils.dp2px(15), 0);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    mTvPlayer.setVisibility(View.VISIBLE);
                    mAvText.setVisibility(View.GONE);
                    mToolbar.setContentInsetsRelative(UiUtils.dp2px(150), 0);
                } else {
                    mTvPlayer.setVisibility(View.GONE);
                    mAvText.setVisibility(View.VISIBLE);
                    mToolbar.setContentInsetsRelative(UiUtils.dp2px(15), 0);
                }
            }
        });
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VideoDetailsActivity.this, "mfa", Toast.LENGTH_SHORT).show();
                initPlayer();


            }
        });
        mTvPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VideoDetailsActivity.this, "mtvplayer", Toast.LENGTH_SHORT).show();
                initPlayer();
            }
        });
    }

    private void initPlayer() {
        mFAB.setVisibility(View.GONE);
        mAppBarLayout.setExpanded(true);
        videoDanmu.setVisibility(View.VISIBLE);
        mToolbar.setVisibility(View.INVISIBLE);
        viewPager.setEnabled(false);
        jcvVideoplayer.topContainer.setVisibility(View.INVISIBLE);
        jcvVideoplayer.setUp(ConstantUtils.VIDEOURL, JCVideoPlayer.SCREEN_LAYOUT_LIST,
                "");
        jcvVideoplayer.onClick(jcvVideoplayer.startButton);
        jcvVideoplayer.ACTION_BAR_EXIST=false;
        jcvVideoplayer.TOOL_BAR_EXIST=false;

        //显示弹幕  具体操作可参考demo
        List<String> strings =new ArrayList<>();
        String [] array={"新手福利计划","财神道90天计划", "硅谷钱包计划", "30天理财计划(加息2%)", "180天理财计划(加息5%)", "月月升理财计划(加息10%)",
                "中情局投资商业经营", "大学老师购买车辆", "屌丝下海经商计划", "美人鱼影视拍摄投资", "Android培训老师自己周转", "养猪场扩大经营",
                "旅游公司扩大规模", "铁路局回款计划", "屌丝迎娶白富美计划","新手福利计划", "财神道90天计划", "硅谷钱包计划", "30天理财计划(加息2%)", "180天理财计划(加息5%)", "月月升理财计划(加息10%)",
                "中情局投资商业经营", "大学老师购买车辆", "屌丝下海经商计划", "美人鱼影视拍摄投资", "Android培训老师自己周转", "养猪场扩大经营",
                "旅游公司扩大规模", "铁路局回款计划", "屌丝迎娶白富美计划","新手福利计划", "财神道90天计划", "硅谷钱包计划", "30天理财计划(加息2%)", "180天理财计划(加息5%)", "月月升理财计划(加息10%)",
                "中情局投资商业经营", "大学老师购买车辆", "屌丝下海经商计划", "美人鱼影视拍摄投资", "Android培训老师自己周转", "养猪场扩大经营",
                "旅游公司扩大规模", "铁路局回款计划", "屌丝迎娶白富美计划"};

        for(int i = 0; i < array.length; i++) {
          strings.add(array[i]);
        }
        Log.e("TAG", "strings=="+strings);
        Log.e("TAG", "array=="+array);
        if (strings != null && strings.size() > 0) {
            //有弹幕数据
            List<IDanmakuItem> list = initItems(danmakuView, strings);
            Collections.shuffle(list);
            danmakuView.addItem(list, true);
            danmakuView.show();
            danmakuView.setVisibility(View.VISIBLE);
        } else {
            danmakuView.hide();
            danmakuView.setVisibility(View.GONE);
        }

    }
    private List<IDanmakuItem> initItems(DanmakuView danmakuView, List<String> strings ) {
        List<IDanmakuItem> list = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            IDanmakuItem item = new DanmakuItem(this, strings.get(i), danmakuView.getWidth());
            list.add(item);
        }
        return list;
    }

    private void setViewsTranslation(int target) {

        mFAB.setTranslationY(target);
        if (target == 0) {
            showFAB();
        } else if (target < 0) {
            hideFAB();
        }
    }


    private void showFAB() {

        mFAB.animate().scaleX(1f).scaleY(1f)
                .setInterpolator(new OvershootInterpolator())
                .start();

        mFAB.setClickable(true);
    }


    private void hideFAB() {

        mFAB.animate().scaleX(0f).scaleY(0f)
                .setInterpolator(new AccelerateInterpolator())
                .start();

        mFAB.setClickable(false);
    }

    public void initToolBar() {

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        //设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        //设置收缩后Toolbar上字体的颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        //设置StatusBar透明
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, mToolbar);

        mAvText.setText("av" + data.getName());
        Glide.with(VideoDetailsActivity.this)
                .load(data.getFace())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(videoPreview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_video, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick({R.id.video_preview, R.id.tv_player, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.video_preview:
                break;
            case R.id.tv_player:
                break;
            case R.id.fab:
                break;
        }
    }

    private void initTablayout() {
        //关联viewpager
        tabLayout.setupWithViewPager(viewPager);
        //滚动模式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }

    private void setAdapter() {
        String[] titles = {"简介", "评论"};
        RecommandAdapter adapter = new RecommandAdapter(this, viewPagers, titles);
        viewPager.setAdapter(adapter);
        initTablayout();
    }

    private void initViewPagers() {
        viewPagers = new ArrayList<>();
        viewPagers.add(new VideoIntroduce(this,data));
        viewPagers.add(new VideoComments(this));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        JCVideoPlayerStandard.backPress();
    }
}
