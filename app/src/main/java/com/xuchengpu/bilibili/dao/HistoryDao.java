package com.xuchengpu.bilibili.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xuchengpu.bilibili.db.AccountDataBase;
import com.xuchengpu.bilibili.table.HistoryTable;
import com.xuchengpu.bilibili.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 许成谱 on 2017/3/26 16:38.
 * qq:1550540124
 * for:数据库增删改查类
 */

public class HistoryDao {
    private static HistoryDao dao = new HistoryDao(UiUtils.getContext());
    private final AccountDataBase accountDataBase;


    private HistoryDao(Context context) {
        accountDataBase = AccountDao.getDao().getAccountDataBase();
    }

    public static HistoryDao getDao() {
        return dao;
    }

    // 添加用户到数据库
    public void add(String content) {
        if (content == null) {
            return;
        }
        //获取数据库链接
        SQLiteDatabase db = accountDataBase.getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put(HistoryTable.CONTENT, content);
        db.replace(HistoryTable.TABLE_NAME, null, value);
        db.close();

    }

    //查询
    public List<String> getAll() {
        //获取数据库连接
        SQLiteDatabase db = accountDataBase.getReadableDatabase();

        List<String> history = new ArrayList<>();
        //第二个参数是条件选择
        Cursor cursor = db.query(HistoryTable.TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                history.add(cursor.getString(cursor.getColumnIndex(HistoryTable.CONTENT)));
            }
            cursor.close();
            db.close();
            return history;
        } else {
            db.close();
            return null;
        }
    }

    public void delete(String content) {
        SQLiteDatabase db = accountDataBase.getReadableDatabase();
        int delete = db.delete(HistoryTable.TABLE_NAME, HistoryTable.CONTENT + "=?", new String[]{content});
        Log.e("tag", "删除了" + delete);
        db.close();
    }
    public void deleteAll(){
        SQLiteDatabase db = accountDataBase.getReadableDatabase();
        db.delete(HistoryTable.TABLE_NAME,null,null);
    }

}
