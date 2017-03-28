package com.xuchengpu.bilibili.utils;

/**
 * Created by 许成谱 on 2017/2/6.
 */

public class ConstantUtils {
    //设置夜间模式用的key常量
    public static final String SWITCH_MODE_KEY = "mode_key";
    //扫描二维码及发现页面传递给webview用的key值
    public static final String SCAN="scan";
    //发现中的中心名称
    public static final String CENTER="center";
    //推荐视频
    public static final String RECOMMAND_VIDEO="recommand_video";




    //地址值
    //登录地址值
    public static final String HOST ="47.93.118.241";//提供ip地址
    public static final String BASE_URL ="http://" +HOST +":8081/P2PInvest/";
    public static final String LOGIN =BASE_URL +"login";//访问登录的url
    //注册
    public static final String REGISTER =BASE_URL +"UserRegister";//注册



    //直播界面内容地址
    public static final String DIRECTSEEDING_CONTENT= "http://live.bilibili.com/AppNewIndex/common?_device=android&appkey=1d8b6e7d45233436&build=501000&mobi_app=android&platform=android&scale=xxhdpi&ts=1490021691000&sign=f283ef788f4b302d0d1783e3f3e98aa4";
    //推荐综合地址
    public static final String  COMPREHENSIVE_VIEWPAGER="http://app.bilibili.com/x/feed/index?appkey=1d8b6e7d45233436&build=501000&idx=1490013261&mobi_app=android&network=wifi&platform=android&pull=true&style=2&ts=1490015599000&sign=af4edc66aef7e443c98c28de2b660aa4";
    //追番地址
    public static final String  CHASE_VIEWPAGER="http://bangumi.bilibili.com/api/app_index_page_v4?build=3940&device=phone&mobi_app=iphone&platform=ios";

    //分区频道地址
    public static final String  PARTITION_CHANNEL="http://live.bilibili.com/AppIndex/areas?_device=android&appkey=1d8b6e7d45233436&build=501000&mobi_app=android&platform=android&scale=xxhdpi&ts=1490016232000&sign=678fafda8c1c1e2db3d8224c2b31044f";
    //分区内容地址
    public static final String  PARTITION_LIST="http://app.bilibili.com/x/v2/show/region?appkey=1d8b6e7d45233436&build=501000&mobi_app=android&platform=android&ts=1490014674000&sign=93edb7634f38498a60e5c3ad0b8b0974";
    //发现地址
    public static final String  DISCOVER_TAG="http://app.bilibili.com/x/v2/search/hot?appkey=1d8b6e7d45233436&build=501000&limit=50&mobi_app=android&platform=android&ts=1490014710000&sign=e5ddf94fa9a0d6876cb85756c37c4adc";
    //话题中心
    public static final String TOPIC_CENTER="http://api.bilibili.com/topic/getlist?appkey=1d8b6e7d45233436&build=501000&mobi_app=android&page=1&pageSize=20&platform=android&ts=1490015740000&sign=be68382cdc99c168ef87f2fa423dd280";
    //活动中心
    public static final String ACTIVITY_CENTER="http://api.bilibili.com/event/getlist?appkey=1d8b6e7d45233436&build=501000&mobi_app=android&page=1&pageSize=20&platform=android&ts=1490015812000&sign=0d9d37f01da5a7d425c10cee0cf3a5f4";

}
