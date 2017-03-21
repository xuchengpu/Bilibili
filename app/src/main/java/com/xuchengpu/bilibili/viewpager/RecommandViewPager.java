package com.xuchengpu.bilibili.viewpager;

import android.content.Context;
import android.widget.TextView;

import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.base.BaseViewPager;

import butterknife.BindView;

/**
 * Created by 许成谱 on 2017/3/21 15:45.
 * qq:1550540124
 * for:
 */

public class RecommandViewPager extends BaseViewPager {


    @BindView(R.id.tv_recommand)
    TextView tvRecommand;

    public RecommandViewPager(Context context) {
        super(context);
    }

    @Override
    protected int getChildLayoutId() {
        return R.layout.recomand;
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
        tvRecommand.setText("lalalla");
    }

}
