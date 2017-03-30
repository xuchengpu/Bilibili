package com.xuchengpu.bilibili.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.activity.MainActivity;
import com.xuchengpu.bilibili.dao.HistoryDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许成谱 on 2017/3/8 10:55.
 * qq:1550540124
 * for:
 */

public class HistoryListAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<String> datas;


    public HistoryListAdapter(MainActivity mainActivity, List<String> historys) {
        this.mContext = mainActivity;
        this.datas = historys;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_history, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvItemHistory.setText(datas.get(position));
        holder.ivDeleteHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryDao.getDao().delete(datas.get(position));
                datas.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_item_history)
        TextView tvItemHistory;
        @BindView(R.id.iv_delete_history)
        ImageView ivDeleteHistory;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
