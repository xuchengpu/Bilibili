package com.xuchengpu.bilibili.viewpager.videodetails;

import android.content.Context;

import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.base.BaseViewPager;

/**
 * Created by 许成谱 on 2017/3/27 18:50.
 * qq:1550540124
 * for:
 */

public class VideoComments extends BaseViewPager{
    public VideoComments(Context context) {
        super(context);
    }

    @Override
    protected int getChildLayoutId() {
        return R.layout.video_comments;
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
}
