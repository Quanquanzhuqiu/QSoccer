package com.qqzq.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jie.xiao on 9/29/2015.
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static DbOpenHelper instance;

    private static final String NATIONS_TABLE_CREATE = "CREATE TABLE"
            + LocationDao.TABLE_NAME + " ("
            + LocationDao.COLUMN_NAME_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + LocationDao.COLUMN_NAME_CODE + " TEXT, "
            + LocationDao.COLUMN_NAME_PROVINCE + " TEXT, "
            + LocationDao.COLUMN_NAME_CITY + " TEXT, "
            + LocationDao.COLUMN_NAME_DISTRICT + " TEXT, "
            + LocationDao.COLUMN_NAME_PARENT + " TEXT); ";

    private DbOpenHelper(Context context) {
        super(context, getUserDbName(), null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static String getUserDbName() {
        return "qqzq.db";
    }
}
