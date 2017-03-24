package com.xuchengpu.bilibili.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.utils.RequestMethod;
import com.xuchengpu.bilibili.utils.TransferData;
import com.xuchengpu.bilibili.utils.UiUtils;

/**
 * Created by 许成谱 on 2017/3/13 22:36.
 * qq:1550540124
 * for:
 */

public abstract class LoadingPager extends FrameLayout {
    private Context mContext;
    private View loadingView;
    private View errorView;
    private View emptyView;
    private View sucessView;
    private LayoutParams params;
    private ResultState resultState;

    private int STATE_LOADING = 1; //加载中
    private int STATE_ERROR = 2; //加载失败
    private int STATE_SUCCESS = 3; //加载成功
    private int STATE_EMPTY = 4; //空

    public LoadingPager(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public LoadingPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        //初始化四种状态的视图
        params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        if (loadingView == null) {
            loadingView = View.inflate(mContext, R.layout.page_loading, null);
            //new 出来了后一定要添加到framelayout中才起作用
            this.addView(loadingView, params);
        }
        if (errorView == null) {
            errorView = View.inflate(mContext, R.layout.page_error, null);
            this.addView(errorView, params);
        }
        if (emptyView == null) {
            emptyView = View.inflate(mContext, R.layout.page_empty, null);
            this.addView(emptyView, params);
        }
        if (sucessView == null) {
            sucessView = View.inflate(mContext, getLayoutId(), null);
            this.addView(sucessView,params);
        }

        //展示布局
        showSafeView();
    }

    private void showSafeView() {
        //判断是否是否在分线程  安全更新UI
        UiUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showView();
            }
        });
    }
    //定义一个状态变量 来记录当前或希望展现的状态界面
    private int currentState = STATE_LOADING;

    private void showView() {
    //根据currentstate来展示不同界面  但前提是此视图已经被添加进LoadingPager这个容器中
        loadingView.setVisibility(currentState == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        errorView.setVisibility(currentState == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
        emptyView.setVisibility(currentState == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
        sucessView.setVisibility(currentState == STATE_SUCCESS ? View.VISIBLE : View.INVISIBLE);

    }
    //这个方法由basefragment在activitycreated之后加载
    public void loadData(final SwipeRefreshLayout swView) {
        String url = getUrl();
        //这是为投资等其他几个不用请求数据的fragment页面准备的 跳过请求数据这一段
        if(TextUtils.isEmpty(url)) {
            resultState = ResultState.SUCCESS;
            loadImage();
            return;
        }

        RequestMethod.getDataFromNet(url, new TransferData() {
            @Override
            public void onsucess(String data) {
                if(swView!=null) {
                    swView.setRefreshing(false);
                    Toast.makeText(mContext, "刷新完成", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(data)) {
//                    Log.e("tag","EMPTY  onSuccess=="+data);
                    resultState = ResultState.EMPTY;
                    resultState.setJson(data);
                } else {
                    Log.e("tag","SUCCESS  onSuccess=="+data);
                    resultState = ResultState.SUCCESS;
                    resultState.setJson(data);
                }
                loadImage();
            }

            @Override
            public void failure(String data) {
                if(swView!=null) {
                    swView.setRefreshing(false);
                    Toast.makeText(mContext, "网络异常，刷新失败", Toast.LENGTH_SHORT).show();
                }
//                Log.e("tag","SUCCESS  failure=="+data);
                resultState = ResultState.ERROR;
                resultState.setJson(data);

                loadImage();
            }
        });
    }
    //通过resultState 中转记录了一下 也可在请求成功或失败后直接对currentstate进行设置
    private void loadImage() {
        switch (resultState) {
            case ERROR:
                currentState = STATE_ERROR;
                break;
            case SUCCESS:
                currentState = STATE_SUCCESS;
                break;
            case EMPTY:
                currentState = STATE_EMPTY;
                break;

        }
        //刷新视图 根据请求数据后得到的情况 显示不同的界面
        showSafeView();
        if (currentState == STATE_SUCCESS) {
            //成功后将数据传递出去 视图传递出去是为了方便butterknife初始化
            onSucess(resultState, sucessView);
//            Log.e("tag", " loadImage thread== "+Thread.currentThread().getName());
        }
    }
//每个枚举值实际上是该类的一个实例  包含该类的全部属性 ，因此可用来携带上数据
    public enum ResultState {
        ERROR, SUCCESS, EMPTY;
        private String json;

        public String getJson() {
            return json;
        }

        public void setJson(String json) {
            this.json = json;
        }
    }
    //这些抽象的方法的调用实际上应用到Java基础中的重写与多态  父类的引用指向子类的对象
    protected abstract void onSucess(ResultState resultState, View sucessView);

    protected abstract String getUrl();

    public abstract int getLayoutId();


}
