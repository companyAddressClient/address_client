package com.xyf.AddressList.utils;

import android.util.Log;

/**
 * Created by shxiayf on 2015/12/1.
 */
public class LogUtils {

    public static void i(String ...args)
    {
        if (!ConfigUtils.debug)
        {
            return;
        }

        if (args.length != 2)
        {
            return;
        }

        Log.i(args[0],args[1]);
    }

    public static void e(String ...args)
    {
        if (!ConfigUtils.debug)
        {
            return;
        }

        if (args.length != 2)
        {
            return;
        }

        Log.e(args[0],args[1]);
    }

    public static void error(Exception e)
    {
        if (!ConfigUtils.debug)
        {
            return;
        }

       e.printStackTrace();
    }

}
