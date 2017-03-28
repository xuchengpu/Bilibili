package com.xuchengpu.bilibili.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.adapter.DownloadAdapter;
import com.xuchengpu.bilibili.view.MyApplication;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownLoadActivity extends AppCompatActivity {

    @BindView(R.id.tv_max)
    TextView tvMax;
    @BindView(R.id.et_max)
    EditText etMax;
    @BindView(R.id.lv_download)
    ListView lvDownload;
    @BindView(R.id.activity_down_load)
    RelativeLayout activityDownLoad;
    private int max;
    private DownloadAdapter adapter;
    private List<String> download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        ButterKnife.bind(this);
        initData();
        setAdapter();
        initListener();
    }

    private void setAdapter() {
        adapter = new DownloadAdapter(this, max,download);
        lvDownload.setAdapter(adapter);
    }

    private void initListener() {
        etMax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapter.max=max;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String trim = etMax.getText().toString().trim();
                if(!TextUtils.isEmpty(trim)) {
                    max = Integer.parseInt(trim);
                    adapter.max=max;
                    adapter.notifyDataSetChanged();
                }

            }
        });

    }

    private void initData() {
        download= MyApplication.getDownloadData();
        String trim = etMax.getText().toString().trim();
        if(!TextUtils.isEmpty(trim)) {
            max = Integer.parseInt(trim);

        }
    }
}
