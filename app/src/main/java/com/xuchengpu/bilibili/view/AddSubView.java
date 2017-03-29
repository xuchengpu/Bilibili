package com.xuchengpu.bilibili.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuchengpu.bilibili.R;


/**
 * Created by 许成谱 on 2017/2/28 14:35.
 * qq:1550540124
 * for:
 */

public class AddSubView extends LinearLayout {
    private  ImageView iv_sub;
    private  TextView tv_value;
    private  ImageView iv_add;
    private int value=1;
    private int minValue=1;
    private int maxValue=10;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_value.setText(value+"");
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }




    public AddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //把布局文件实例化成View,并且添加到AddSubView.this类中，成为他的子视图
        View.inflate(context, R.layout.add_sub_view,AddSubView.this);
        iv_sub = (ImageView) findViewById(R.id.iv_sub);
        tv_value = (TextView) findViewById(R.id.tv_value);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        /*减少*/
        iv_sub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value>minValue) {
                    value--;
                }
                setValue(value);
                /*
                数字状态变化的监听
                * */
                onNumberChangerListener.OnNumberChanger(value);
            }
        });
        /*增加*/
        iv_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value<maxValue) {
                    value++;

                }
                setValue(value);
                /*
                数字状态变化的监听
                * */
                onNumberChangerListener.OnNumberChanger(value);
            }

        });
        /*
        * 自定义属性时需通过以下代码将读取到的属性值具体设置到布局视图中显示
        * */

        if (attrs != null) {
            //取出属性
            TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.AddSubView);
            int value = tintTypedArray.getInt(R.styleable.AddSubView_value, 0);
            if (value > 0) {
                setValue(value);
            }
            int minValue = tintTypedArray.getInt(R.styleable.AddSubView_minValue, 0);
            if (value > 0) {
                setMinValue(minValue);
            }
            int maxValue = tintTypedArray.getInt(R.styleable.AddSubView_maxValue, 0);
            if (value > 0) {
                setMaxValue(maxValue);
            }
            Drawable addDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberAddBackground);
            if (addDrawable != null) {
                iv_add.setImageDrawable(addDrawable);
            }
            Drawable subDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberSubBackground);
            if (subDrawable != null) {
                iv_sub.setImageDrawable(subDrawable);
            }

        }

    }
    /*
    * 提供一个接口来将value值的变化传递给实现类
    * */
    public interface OnNumberChangerListener{
        void OnNumberChanger(int value);
    }
    private OnNumberChangerListener onNumberChangerListener;

    public void setOnNumberChangerListener(OnNumberChangerListener onNumberChangerListener) {
        this.onNumberChangerListener = onNumberChangerListener;
    }
}


