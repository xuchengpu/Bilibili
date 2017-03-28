package com.xuchengpu.bilibili.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.view.MyProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/3/28 16:23.
 * qq:1550540124
 * for:
 */

public class DownloadAdapter extends BaseAdapter {


    private final Context mContext;
    public  int max;
    private final List<String> datas;
    public boolean flag=false;

    public DownloadAdapter(Context context, int max, List<String> datas) {
        this.mContext = context;
        this.max = max;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder holder=null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_download, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        if(flag) {
            holder.btnDownload.setEnabled(false);
        }else {
            holder.btnDownload.setEnabled(true);
        }
        holder.setData(this,mContext,"",max);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_downloadInfo)
        TextView tvDownloadInfo;
        @BindView(R.id.tv_progress)
        TextView tvProgress;
        @BindView(R.id.progressBar)
        MyProgressBar progressBar;
        @BindView(R.id.btn_download)
        Button btnDownload;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);

        }

        public void setData(final DownloadAdapter downloadAdapter, final Context mContext, final String url, final int max) {
            btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    RequestMethod.download(downloadAdapter,btnDownload,mContext,url,tvProgress,progressBar,max);
                    progressBar.download(downloadAdapter,btnDownload,mContext,url,tvProgress,max);
                }
            });
            tvProgress.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }
    }
}
