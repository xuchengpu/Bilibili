package com.xuchengpu.bilibili.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.adapter.HistoryListAdapter;
import com.xuchengpu.bilibili.adapter.MainViewPagerAdapter;
import com.xuchengpu.bilibili.base.BaseViewPager;
import com.xuchengpu.bilibili.dao.HistoryDao;
import com.xuchengpu.bilibili.utils.CacheUtils;
import com.xuchengpu.bilibili.utils.ConstantUtils;
import com.xuchengpu.bilibili.utils.UiUtils;
import com.xuchengpu.bilibili.utils.fly.JsonParser;
import com.xuchengpu.bilibili.view.CircleImageView;
import com.xuchengpu.bilibili.viewpager.ChaseViewPager;
import com.xuchengpu.bilibili.viewpager.DirectSeedingViewPager;
import com.xuchengpu.bilibili.viewpager.DiscoverViewPager;
import com.xuchengpu.bilibili.viewpager.partition.PartitionViewPager;
import com.xuchengpu.bilibili.viewpager.recommand.RecommandViewPager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.iv_drawer_home)
    ImageView ivDrawerHome;
    @BindView(R.id.top_head)
    CircleImageView topHead;
    @BindView(R.id.iv_new_feature_pink_dot)
    ImageView ivNewFeaturePinkDot;
    @BindView(R.id.iv_menu_top_game_center)
    ImageView ivMenuTopGameCenter;
    @BindView(R.id.iv_toolbar_menu_download)
    ImageView ivToolbarMenuDownload;
    @BindView(R.id.iv_download_search)
    ImageView ivDownloadSearch;
    @BindView(R.id.dl_left)
    LinearLayout dlLeft;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.view_pager_main)
    ViewPager viewPagerMain;
    @BindView(R.id.activity_toolbar)
    CoordinatorLayout activityToolbar;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.activity_main)
    DrawerLayout activityMain;
    private ArrayList<BaseViewPager> basePagers;
    private String[] titles = {"直播", "推荐", "追番", "分区", "发现"};
    public static final int REQUEST_CODE = 1;
    private PopupWindow window;
    private int count = 1;
    private List<String> historys;
    private LinearLayout ll_history;
    private EditText et_search_discover;


    //科大讯飞初始化
    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private SharedPreferences mSharedPreferences;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    private Toast mToast;
    int ret = 0; // 函数调用返回值


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //解决底部虚拟键问题
        UiUtils.transportStatus(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFly();
        initView();
        initViewPager();
        setAdapter();
        initListener();


    }


    private void initListener() {
        viewPagerMain.setCurrentItem(1);
    }

    public void Scan() {
        Intent intent = new Intent(this, CaptureActivity.class);

        startActivityForResult(intent, REQUEST_CODE);
    }

    ;

    private void setAdapter() {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(basePagers, titles);
//        MainFragmentAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager(), fragments, titles);
        viewPagerMain.setAdapter(adapter);
        //关联viewpager
        tablayout.setupWithViewPager(viewPagerMain);
        //模式
        tablayout.setTabMode(TabLayout.MODE_FIXED);

    }

    private void initViewPager() {
        basePagers = new ArrayList<>();
        basePagers.add(new DirectSeedingViewPager(this));
        basePagers.add(new RecommandViewPager(this));
        basePagers.add(new ChaseViewPager(this));
        basePagers.add(new PartitionViewPager(this));
        basePagers.add(new DiscoverViewPager(this));
    }

    private void initView() {
//        navigationView.setItemIconTintList(null);//让item中的图片显示原色
        navigationView.setNavigationItemSelectedListener(this);//设置item监听
        View headerView = navigationView.getHeaderView(0);
        CircleImageView mUserAvatarView = (CircleImageView) headerView.findViewById(
                R.id.user_avatar_view);
        TextView mUserName = (TextView) headerView.findViewById(R.id.user_name);
        TextView mUserSign = (TextView) headerView.findViewById(R.id.user_other_info);
        TextView exit = (TextView) headerView.findViewById(R.id.tv_exit);
        ImageView mSwitchMode = (ImageView) headerView.findViewById(R.id.iv_head_switch_mode);

        //设置头像
        mUserAvatarView.setImageResource(R.drawable.ic_hotbitmapgg_avatar);
        //设置用户名 签名
        mUserName.setText(getResources().getText(R.string.hotbitmapgg));
        mUserSign.setText(getResources().getText(R.string.about_user_head_layout));

        //设置日夜间模式切换
        mSwitchMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchNightMode();
            }
        });

        boolean flag = CacheUtils.getBoolean(this, ConstantUtils.SWITCH_MODE_KEY);
        if (flag) {
            mSwitchMode.setImageResource(R.drawable.ic_switch_daily);
        } else {
            mSwitchMode.setImageResource(R.drawable.ic_switch_night);
        }
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSp();
                Toast.makeText(MainActivity.this, "登录数据已清除", Toast.LENGTH_SHORT).show();
            }
        });

    }


    /**
     * 日夜间模式切换
     */
    private void switchNightMode() {

        boolean isNight = CacheUtils.getBoolean(this, ConstantUtils.SWITCH_MODE_KEY);
        if (isNight) {
            // 日间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            CacheUtils.putBoolean(this, ConstantUtils.SWITCH_MODE_KEY, false);
        } else {
            // 夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            CacheUtils.putBoolean(this, ConstantUtils.SWITCH_MODE_KEY, true);
        }

        recreate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra(ConstantUtils.SCAN, result);
                    startActivity(intent);

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /***
     * 获取PopupWindow实例
     */
    public void getPopupWindow() {

        if (null != window) {
            closePopupWindow();
            return;
        } else {
            initPopuptWindow();
        }
    }

    public void initPopuptWindow() {
        //调用虚拟键盘
        initKeyboard();
        //1、利用layoutInflater获得View

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popupwindow_search, null);

        //2、两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        // 3、 参数设置
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable cw = new ColorDrawable(0xFFFFFFFF);
        window.setBackgroundDrawable(cw);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        //设置点击外部区域是否会消失
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setOutsideTouchable(true);
        //设置其他区域半透明
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.6f;
        getWindow().setAttributes(params);

        // 5 在顶部显示
        window.showAtLocation(this.findViewById(R.id.iv_download_search),
                Gravity.TOP, 0, 0);

        //初始化数据
        et_search_discover = (EditText) view.findViewById(R.id.et_search_discover);
        ImageView iv_search = (ImageView) view.findViewById(R.id.iv_search);
        ImageView iv_voice_search = (ImageView) view.findViewById(R.id.iv_voice_search);
        ll_history = (LinearLayout) view.findViewById(R.id.ll_history);
        final ListView lv_search = (ListView) view.findViewById(R.id.lv_search);
        //不管有没有数据，进来先隐藏
        ll_history.setVisibility(View.GONE);
        //从数据库拿数据
        historys = new ArrayList<>();
        historys = HistoryDao.getDao().getAll();
        //给历史记录列表设置适配器
        final HistoryListAdapter adapter = new HistoryListAdapter(MainActivity.this, historys);
        lv_search.setAdapter(adapter);
        //对搜索框设置监听
        et_search_discover.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //搜索框获得焦点后，再判断有没有数据，再决定要不要显示列表
                if (historys != null && historys.size() > 0) {
                    ll_history.setVisibility(View.VISIBLE);
                }
            }
        });

        //点击搜索图标 保存数据 实现跳转
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = et_search_discover.getText().toString().trim();
                boolean isAdded = false;
                if (!TextUtils.isEmpty(text)) {
                    //查询以前是否有该条记录
                    for (int i = 0; i < historys.size(); i++) {
                        if (historys.get(i).equals(text)) {
                            isAdded = true;
                        }
                    }
                    //没有添加过 则添加到数据库
                    if (!isAdded) {
                        HistoryDao.getDao().add(text);
                    }
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    intent.putExtra(ConstantUtils.SEARCH, text);
                    startActivity(intent);
                    window.dismiss();
                    window = null;
                }
            }
        });

        //集成科大讯飞

        iv_voice_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initVoices();
            }
        });


        //消失后恢复
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                window = null;
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }


    /**
     * 关闭窗口
     */
    private void closePopupWindow() {
        if (window != null && window.isShowing()) {
            window.dismiss();
            window = null;
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.alpha = 1f;
            getWindow().setAttributes(params);
        }
    }


    @OnClick({R.id.iv_drawer_home, R.id.top_head, R.id.iv_new_feature_pink_dot, R.id.iv_menu_top_game_center, R.id.iv_toolbar_menu_download, R.id.iv_download_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_drawer_home:
                activityMain.openDrawer(GravityCompat.START);
                break;
            case R.id.top_head:

                break;

            case R.id.iv_new_feature_pink_dot:
                break;
            case R.id.iv_menu_top_game_center:
                break;
            case R.id.iv_toolbar_menu_download:
                Intent intent = new Intent(this, DownLoadActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_download_search:
                getPopupWindow();
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        activityMain.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.item_home:
                // 主页
//                changeFragmentIndex(item, 0);
                return true;

            case R.id.item_download:
                // 离线缓存
//                startActivity(new Intent(MainActivity.this, OffLineDownloadActivity.class));
                Intent intent = new Intent(this, DownLoadActivity.class);
                startActivity(intent);
                return true;

            case R.id.item_vip:
                //大会员
//                startActivity(new Intent(MainActivity.this, VipActivity.class));
                return true;

            case R.id.item_favourite:
                // 我的收藏
//                changeFragmentIndex(item, 1);
                return true;

            case R.id.item_history:
                // 历史记录
//                changeFragmentIndex(item, 2);
                return true;

            case R.id.item_group:
                // 关注的人
//                changeFragmentIndex(item, 3);
                return true;

            case R.id.item_tracker:
                // 我的钱包
//                changeFragmentIndex(item, 4);
                return true;

            case R.id.item_theme:
                // 主题选择
                // CardPickerDialog dialog = new CardPickerDialog();
                // dialog.setClickListener(this);
                // dialog.show(getSupportFragmentManager(), CardPickerDialog.TAG);
                return true;

            case R.id.item_app:
                // 应用推荐

                return true;

            case R.id.item_settings:
                // 设置中心
//                changeFragmentIndex(item, 5);
                return true;
        }

        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (count % 2 == 0) {
                finish();
            }
            Toast.makeText(MainActivity.this, "再次点击退出", Toast.LENGTH_SHORT).show();
            count++;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    count = 1;
                }
            }, 2000);
//            CountDownTimer timer = new CountDownTimer(10000, 1000) {
//              第一个参数是总时间 第二个参数间隔时间
//                @Override
//                public void onTick(long millisUntilFinished) {
//                    //每倒计时一次调用一次
//                }
//
//                @Override
//                public void onFinish() {
//                    //执行完成后调用
//                }
//            }.start();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void initKeyboard() {
        //如果输入法在窗口上已经显示，则隐藏，反之则显示
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //清除所有的sp操作
    public void clearSp() {
        SharedPreferences user = getSharedPreferences("userinfo", MODE_PRIVATE);
        user.edit().clear().commit(); //清除的是内容
    }


    //集成科大续费

    private void initFly() {
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(this, mInitListener);

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(this, mInitListener);

        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        mSharedPreferences = getSharedPreferences(ConstantUtils.PREFER_NAME,
                Activity.MODE_PRIVATE);
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {

        }
    };


    private void initVoices() {
        // 移动数据分析，收集开始听写事件
        FlowerCollector.onEvent(this, "iat_recognize");

        et_search_discover.setText(null);// 清空显示内容
        mIatResults.clear();
        // 设置参数
        setParam();
        boolean isShowDialog = mSharedPreferences.getBoolean(
                getString(R.string.pref_key_iat_show), true);
        if (isShowDialog) {
            // 显示听写对话框
            mIatDialog.setListener(mRecognizerDialogListener);
            mIatDialog.show();
            showTip(getString(R.string.text_begin));
        } else {
            // 不显示听写对话框
            ret = mIat.startListening(mRecognizerListener);
            if (ret != ErrorCode.SUCCESS) {
                showTip("听写失败,错误码：" + ret);
            } else {
                showTip(getString(R.string.text_begin));
            }
        }
    }
    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            showTip(error.getPlainDescription(true));
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results);

            if (isLast) {
                // TODO 最后的结果
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("当前正在说话，音量大小：" + volume);
            Log.d("tag", "返回音频数据："+data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };
    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        et_search_discover.setText(resultBuffer.toString());
        et_search_discover.setSelection(et_search_discover.length());
    }

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results);
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true));
        }

    };

/*
    *
     * 参数设置
     *
     * @param param
     * @return*/

    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        String lag = mSharedPreferences.getString("iat_language_preference",
                "mandarin");
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
    }

}
