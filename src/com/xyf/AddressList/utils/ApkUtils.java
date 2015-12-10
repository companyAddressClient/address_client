package com.xyf.AddressList.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by shxiayf on 2015/12/9.
 */
public class ApkUtils {

    public static String getVersion(Context mContext)
    {
        try
        {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);

            return packageInfo.versionName;
        }
        catch (Exception e)
        {
            LogUtils.error(e);
        }

        return "";
    }

}
