package com.xuchengpu.bilibili.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xuchengpu.bilibili.adapter.DownloadAdapter;
import com.xuchengpu.bilibili.utils.RequestMethod;

/**
 * Created by 许成谱 on 2017/3/28 20:46.
 * qq:1550540124
 * for:
 */

public class MyProgressBar extends ProgressBar {

    public MyProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void download(DownloadAdapter downloadAdapter, Button btnDownload, Context mContext, String url, TextView tvProgress, int max) {
        RequestMethod.download(downloadAdapter,btnDownload,mContext,url,tvProgress,this,max);
    }
}
