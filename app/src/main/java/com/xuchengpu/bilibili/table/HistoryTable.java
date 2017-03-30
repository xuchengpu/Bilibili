package com.xuchengpu.bilibili.table;

/**
 * Created by 许成谱 on 2017/3/26 16:30.
 * qq:1550540124
 * for:
 */

public class HistoryTable {
    public static final String TABLE_NAME="History";

//    public static final String ID="id";
    public static final String CONTENT="content";


    public static final String CREATE_TABLE="create table "+TABLE_NAME+"("
//            +ID+" text primary key,"
//            +CONTENT+" text,"
            + CONTENT + " text primary key);";
}
