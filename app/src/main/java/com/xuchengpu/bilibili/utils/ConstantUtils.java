package com.xuchengpu.bilibili.utils;

/**
 * Created by 许成谱 on 2017/2/6.
 */

public class ConstantUtils {
    //设置夜间模式用的key常量
    public static final String SWITCH_MODE_KEY = "mode_key";
    //扫描二维码传递给webview用的key值
    public static final String SCAN="scan";



    //地址值
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
}
