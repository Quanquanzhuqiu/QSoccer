package com.qqzq.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.qqzq.common.dto.EntLocation;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie.xiao on 9/29/2015.
 */
public class LocationDao {

    public static final String TAG = "LocationDao";

    public static final String TABLE_NAME = "qqzq_nation";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_CODE = "code";
    public static final String COLUMN_NAME_PROVINCE = "province";
    public static final String COLUMN_NAME_CITY = "city";
    public static final String COLUMN_NAME_DISTRICT = "district";
    public static final String COLUMN_NAME_PARENT = "parent";

    private DbOpenHelper dbOpenHelper;

    public LocationDao(Context context) {
        dbOpenHelper = DbOpenHelper.getInstance(context);
    }

    public void saveLocaitonList(EntLocation[] entLocations) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(TABLE_NAME, null, null);
            for (EntLocation entLocation : entLocations) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_NAME_ID, entLocation.getId());
                values.put(COLUMN_NAME_PARENT, entLocation.getParent());
                if (entLocation.getProvince() != null) {
                    values.put(COLUMN_NAME_PROVINCE, entLocation.getProvince());
                }
                if (entLocation.getCity() != null) {
                    values.put(COLUMN_NAME_CITY, entLocation.getCity());
                }
                if (entLocation.getDistrict() != null) {
                    values.put(COLUMN_NAME_DISTRICT, entLocation.getDistrict());
                }
                db.replace(TABLE_NAME, null, values);
            }
        }
    }

    public EntLocation findLocationById(String id) {
        String sql = "SELECT * FROM {0} WHERE {1} = {2}";
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        EntLocation entLocation = null;
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(MessageFormat.format(sql, TABLE_NAME, COLUMN_NAME_ID, id), null);
            while (cursor.moveToNext()) {
                entLocation = new EntLocation();
                entLocation.setId(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ID)));
                entLocation.setParent(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PARENT)));
                entLocation.setProvince(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PROVINCE)));
                entLocation.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CITY)));
                entLocation.setDistrict(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DISTRICT)));
            }
        }

        return entLocation;
    }

    public List<EntLocation> findLoactionByParent(String parent) {
        String sql = "SELECT * FROM {0} WHERE {1} = {2} ";
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        List<EntLocation> list = new ArrayList<>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(MessageFormat.format(sql, TABLE_NAME, COLUMN_NAME_PARENT, parent), null);
            while (cursor.moveToNext()) {
                EntLocation entLocation = new EntLocation();
                entLocation.setId(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ID)));
                entLocation.setParent(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PARENT)));
                entLocation.setProvince(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PROVINCE)));
                entLocation.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CITY)));
                entLocation.setDistrict(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DISTRICT)));
                list.add(entLocation);
            }
        }

        return list;
    }

    public List<EntLocation> findAll() {
        String sql = "SELECT * FROM {0}";
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        List<EntLocation> list = new ArrayList<>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(MessageFormat.format(sql, TABLE_NAME), null);
            while (cursor.moveToNext()) {
                EntLocation entLocation = new EntLocation();
                entLocation.setId(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ID)));
                entLocation.setParent(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PARENT)));
                entLocation.setProvince(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PROVINCE)));
                entLocation.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CITY)));
                entLocation.setDistrict(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DISTRICT)));
                list.add(entLocation);
            }
        }

        return list;
    }
}
