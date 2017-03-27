package com.xuchengpu.bilibili.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xuchengpu.bilibili.table.AccountTable;

/**
 * Created by 许成谱 on 2017/3/26 16:23.
 * qq:1550540124
 * for:
 */

public class AccountDataBase extends SQLiteOpenHelper {

    public AccountDataBase(Context context) {
        super(context, "account.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库
        //将具体执行的语句封装到AccountTable中的CREATE_TABLE变量中
        db.execSQL(AccountTable.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
