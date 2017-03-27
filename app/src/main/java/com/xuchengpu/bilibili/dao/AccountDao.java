package com.xuchengpu.bilibili.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.xuchengpu.bilibili.bean.LoginInfo;
import com.xuchengpu.bilibili.db.AccountDataBase;
import com.xuchengpu.bilibili.table.AccountTable;
import com.xuchengpu.bilibili.utils.UiUtils;

/**
 * Created by 许成谱 on 2017/3/26 16:38.
 * qq:1550540124
 * for:数据库增删改查类
 */

public class AccountDao {
    private static AccountDao dao=new AccountDao(UiUtils.getContext());
    private final AccountDataBase accountDataBase;
    private LoginInfo loginInfo;

    private AccountDao(Context context){
        accountDataBase = new AccountDataBase(context);
    }
    public static AccountDao getDao(){
        return  dao;
    }
    // 添加用户到数据库
    public void addAccount(LoginInfo info){
        if(info==null) {
            return;
        }
        //获取数据库链接
        SQLiteDatabase db = accountDataBase.getReadableDatabase();
        ContentValues value=new ContentValues();
        value.put(AccountTable.USER_PHONE,info.getPhone());
        value.put(AccountTable.USER_NAME,info.getName());
        value.put(AccountTable.USER_CODE,info.getCode());
        db.replace(AccountTable.TABLE_NAME,null,value);
        db.close();

    }
    //查询
    public LoginInfo getLoginInfoByPhone(String phone){
        loginInfo=null;

        if (phone == null || TextUtils.isEmpty(phone)){
            return null;
        }

        //获取数据库连接
        SQLiteDatabase db = accountDataBase.getReadableDatabase();

        String sql = "select * from "+AccountTable.TABLE_NAME
                +" where "+AccountTable.USER_PHONE+"=?";
        //第二个参数是条件选择
        Cursor cursor = db.rawQuery(sql, new String[]{phone});
        if(cursor!=null) {
            while (cursor.moveToNext()){
                loginInfo = new LoginInfo();
                loginInfo.setPhone(cursor.getString(cursor.getColumnIndex(AccountTable.USER_PHONE)));
                loginInfo.setName(cursor.getString(cursor.getColumnIndex(AccountTable.USER_NAME)));
                loginInfo.setCode(cursor.getString(cursor.getColumnIndex(AccountTable.USER_CODE)));
            }
            cursor.close();
            db.close();
            return loginInfo;
        }else {
            db.close();
            return null;
        }
    }

    public void deleteAccount(String phone) {
        SQLiteDatabase db = accountDataBase.getReadableDatabase();
        int delete = db.delete(AccountTable.TABLE_NAME, AccountTable.USER_PHONE+"=?", new String[]{phone});
        Log.e("tag", "删除了"+delete);
        db.close();
    }

}
