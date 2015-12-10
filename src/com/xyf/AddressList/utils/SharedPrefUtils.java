package com.xyf.AddressList.utils;

import android.content.Context;

/**
 * Created by shxiayf on 2015/12/3.
 */
public class SharedPrefUtils {

    private static class Utils {
        public static final SharedPrefUtils INSTANCES = new SharedPrefUtils();
    }

    public static SharedPrefUtils getInstances()
    {
        return Utils.INSTANCES;
    }

    private static final String SP_NAME = "addressSP";

    public boolean isNeedImportDefautXLS(Context mContext)
    {
        return mContext.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE).getBoolean("needImport", true);
    }

    public void setNeedImportDefaultXLS(Context mContext,boolean value)
    {
        mContext.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE).edit().putBoolean("needImport", false).commit();
    }

    public long getLastUpdateTime(Context mContext)
    {
        return mContext.getSharedPreferences(SP_NAME,mContext.MODE_PRIVATE).getLong("lastUpdateTime", 0);
    }

    public void setLastUpdateTime(Context mContext,long time)
    {
        mContext.getSharedPreferences(SP_NAME,mContext.MODE_PRIVATE).edit().putLong("lastUpdateTime",time).commit();
    }

    public String getXlsVersion(Context mContext)
    {
        return mContext.getSharedPreferences(SP_NAME,mContext.MODE_PRIVATE).getString("xlsVersion",ConfigUtils.XLS_DEFAULT_VERSION);
    }

    public void setXlsVersion(Context mContext,String version)
    {
        mContext.getSharedPreferences(SP_NAME,mContext.MODE_PRIVATE).edit().putString("xlsVersion",version).commit();
    }
}
