package com.xyf.AddressList.database;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by shxiayf on 2015/12/2.
 */
public interface DBInterface {

    Cursor doSqliteQuery(String sql);

    long doSqliteInsert(String table,ContentValues values);

}
