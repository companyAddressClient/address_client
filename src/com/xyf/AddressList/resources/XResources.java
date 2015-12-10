package com.xyf.AddressList.resources;

import android.content.Context;
import com.xyf.AddressList.utils.LogUtils;

/**
 * Created by shxiayf on 2015/12/1.
 */
public class XResources {

    public enum ResourceType
    {
        layout,
        drawable,
        id,
        string
    }

    public static String getType(ResourceType tt)
    {
        String typeName = "";
        switch (tt)
        {
            case drawable:
                typeName = "drawable";
                break;

            case layout:
                typeName = "layout";
                break;

            case id:
                typeName = "id";
                break;

            case string:
                typeName = "string";
                break;

            default:
                typeName = "unknow";
                break;
        }

        return typeName;
    }

    public static String getString(Context mContext,String name)
    {
        return mContext.getResources().getString(getIdByName(mContext,ResourceType.string,name));
    }

    public static int getIdByName(Context mContext,ResourceType tt,String name)
    {
        int id = 0;
        String typeName = getType(tt);
        if (typeName.equals("unknow"))
        {
            return id;
        }

        String packageName = mContext.getPackageName();

        try {
            id = mContext.getResources().getIdentifier(name,typeName,packageName);
        }catch (Exception e)
        {
            LogUtils.error(e);
        }

        return id;
    }


}
