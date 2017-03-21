package com.xuchengpu.bilibili.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.xuchengpu.bilibili.base.BaseFragment;

/**
 * Created by 许成谱 on 2017/3/21 18:44.
 * qq:1550540124
 * for:
 */

public class fragment4 extends BaseFragment {
    TextView textView;
    @Override
    public View initView() {
        textView=new TextView(getActivity());
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("fragment4");
    }
}
