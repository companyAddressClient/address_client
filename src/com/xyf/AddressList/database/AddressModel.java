package com.xyf.AddressList.database;

import android.provider.BaseColumns;

/**
 * Created by shxiayf on 2015/12/2.
 */
public class AddressModel implements BaseColumns {

    public static final String DBName = "address.adb";
    public static final String TableName = "addresses";

    public static final String COL_NAME = "name";
    public static final String COL_MOBILEPHONE = "mobilephone";
    public static final String COL_WORKPHONE = "workphone";
    public static final String COL_JOB = "job";


}
