package com.xyf.AddressList.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import com.xyf.AddressList.bean.ContactsBean;
import com.xyf.AddressList.database.AddressModel;
import com.xyf.AddressList.database.AddressSqliteOpenHelper;
import com.xyf.AddressList.database.DBInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shxiayf on 2015/12/2.
 */
public class DBUtils implements DBInterface{

    private static class Utils {
        public static final DBUtils INSTANCES = new DBUtils();
    }

    public static DBUtils getInstances()
    {
        return Utils.INSTANCES;
    }

    private AddressSqliteOpenHelper m_OpenHelper;

    private void initDB(Context context)
    {
        if (m_OpenHelper == null)
        {
            m_OpenHelper = new AddressSqliteOpenHelper(context,null,1,new AddressModel());
        }
    }

    public List<ContactsBean> QueryAddressList(Context mContext)
    {
        initDB(mContext);

        List<ContactsBean> mAddressLists = new ArrayList<ContactsBean>();

        Cursor cur  = doSqliteQuery(AddressModel.TableName);

        if (cur == null)
        {
            return  mAddressLists;
        }

        if (cur.moveToNext())
        {
            do{
                ContactsBean temp = new ContactsBean();
                temp.setName(cur.getString(cur.getColumnIndex(AddressModel.COL_NAME)));
                temp.setMobilephone(cur.getString(cur.getColumnIndex(AddressModel.COL_MOBILEPHONE)));
                temp.setWorkphone(cur.getString(cur.getColumnIndex(AddressModel.COL_WORKPHONE)));
                temp.setJob(cur.getString(cur.getColumnIndex(AddressModel.COL_JOB)));
                mAddressLists.add(temp);
            }while (cur.moveToNext());
        }


        cur.close();

        return mAddressLists;
    }

    public long InsertAddress(Context mContext,ContactsBean tmp)
    {
        initDB(mContext);

        ContentValues contentValues = new ContentValues();
        contentValues.put(AddressModel.COL_NAME,tmp.getName());
        contentValues.put(AddressModel.COL_MOBILEPHONE,tmp.getMobilephone());
        contentValues.put(AddressModel.COL_WORKPHONE,tmp.getWorkphone());
        contentValues.put(AddressModel.COL_JOB,tmp.getJob());

        return doSqliteInsert(AddressModel.TableName,contentValues);
    }

    public void deleteAll(Context mContext,String table)
    {
        initDB(mContext);

        SQLiteDatabase sqLiteDatabase = m_OpenHelper.getWritableDatabase();
        sqLiteDatabase.delete(table,null,null);
    }

    @Override
    public Cursor doSqliteQuery(String sql) {
        if (m_OpenHelper != null)
        {
            SQLiteDatabase sqLiteDatabase = m_OpenHelper.getReadableDatabase();
            return sqLiteDatabase.query(sql,null,null,null,null,null,null,null);
        }
        return null;
    }

    @Override
    public long doSqliteInsert(String table,ContentValues values) {
        long id = -1;

        if (m_OpenHelper != null)
        {
            SQLiteDatabase sqLiteDatabase = m_OpenHelper.getWritableDatabase();
            return sqLiteDatabase.insert(table,null,values);
        }

        return id;
    }

}
