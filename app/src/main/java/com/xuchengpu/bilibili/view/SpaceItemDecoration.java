package com.xuchengpu.bilibili.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 许成谱 on 2017/3/6 15:13.
 * qq:1550540124
 * for:
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int space;

    public SpaceItemDecoration(int space) {
        this.space=space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left=space;
        outRect.bottom=space;
        //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0
        if(parent.getChildLayoutPosition(view)%2==0) {
            outRect.left=0;
        }
    }
}
