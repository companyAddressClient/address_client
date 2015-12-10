package com.xyf.AddressList.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shxiayf on 2015/12/2.
 */
public class AddressSqliteOpenHelper extends SQLiteOpenHelper {

    private AddressModel modelData;

    public AddressSqliteOpenHelper(Context context,SQLiteDatabase.CursorFactory factory, int version,AddressModel model) {
        super(context, model.DBName, factory, version);

        this.modelData = model;
    }

    public AddressSqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + modelData.TableName + " (" + modelData._ID + " INTEGER PRIMARY KEY,"
                    + modelData.COL_NAME + " TEXT NOT NULL,"
                    + modelData.COL_MOBILEPHONE + " TEXT NOT NULL,"
                    + modelData.COL_WORKPHONE + " TEXT,"
                    + modelData.COL_JOB + " TEXT);";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE " + modelData.TableName + " IF EXISTS";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
