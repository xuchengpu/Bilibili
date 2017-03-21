package com.xuchengpu.bilibili.utils;

import java.io.Serializable;

/**
 * Created by 许成谱 on 2017/3/12 20:55.
 * qq:1550540124
 * for:接口回调，两个activity之间交互传输数据用的
 */

public interface TransferData extends Serializable{
    void onsucess(String data);
    void failure(String data);
}
