package com.xuchengpu.bilibili.table;

/**
 * Created by 许成谱 on 2017/3/26 16:30.
 * qq:1550540124
 * for:
 */

public class AccountTable {
    public static final String TABLE_NAME="userinfo";

    public static final String USER_NAME="username";
    public static final String USER_PHONE="phone";
    public static final String USER_CODE="code";


    public static final String CREATE_TABLE="create table "+TABLE_NAME+"("
            +USER_PHONE+" text primary key,"
            +USER_NAME+" text,"
            + USER_CODE + " text);";
}
