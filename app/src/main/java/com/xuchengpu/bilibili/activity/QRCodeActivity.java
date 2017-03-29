package com.xuchengpu.bilibili.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.bean.GoodsBean;
import com.xuchengpu.bilibili.utils.ConstantUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QRCodeActivity extends AppCompatActivity {

    @BindView(R.id.qrcode_image)
    ImageView qrcodeImage;
    private GoodsBean goodsBean;
    private Bitmap logo;
    private String textContent1;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);
        goodsBean = (GoodsBean) getIntent().getSerializableExtra(ConstantUtils.GOODSBEAN);
        creatQRCode();
    }

    private void creatQRCode() {
        textContent1 = goodsBean.getFigure() + "," + goodsBean.getName() + "," + goodsBean.getCover_price() + "," + goodsBean.getProduct_id();
        new Thread(new Runnable() {
            @Override
            public void run() {
                logo = returnBitMap( goodsBean.getFigure());
                Log.e("tag", "log==" + logo.toString());
                mBitmap = CodeUtils.createImage(textContent1, 400, 400, logo);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        qrcodeImage.setImageBitmap(mBitmap);

                    }
                });
            }
        }).start();





    }

    public Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return bitmap;
    }
}
