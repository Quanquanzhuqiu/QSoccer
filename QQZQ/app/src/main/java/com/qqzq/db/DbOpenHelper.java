package com.qqzq.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.qqzq.config.Constants;

/**
 * Created by jie.xiao on 9/29/2015.
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbOpenHelper";

    private static final int DB_VERSION = 1;
    private static DbOpenHelper instance;

    private static final String NATIONS_TABLE_CREATE = "CREATE TABLE "
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

    public static DbOpenHelper getInstance(Context context) {
        if (instance == null) {
            Log.d(TAG, getUserDbName());
            instance = new DbOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NATIONS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void closeDB() {
        if (instance != null) {
            try {
                SQLiteDatabase db = instance.getWritableDatabase();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            instance = null;
        }
    }

    private static String getUserDbName() {
        return Constants.DB_PATH + "qqzq.db";
    }
}
