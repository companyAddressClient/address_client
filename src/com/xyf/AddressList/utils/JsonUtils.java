package com.xyf.AddressList.utils;

import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by shxiayf on 2015/12/7.
 */
public class JsonUtils {

    public static String parseObj2String(Object obj)
    {
        try{
            JSONObject jsonObject = new JSONObject();
            for (Field tmp : obj.getClass().getDeclaredFields())
            {
                tmp.setAccessible(true);
                jsonObject.put(tmp.getName(),tmp.get(obj));
            }
            return jsonObject.toString();
        }
        catch (Exception e)
        {
            LogUtils.error(e);
        }

        return "";
    }

    public static Object parseString2Obj(String json,Class<?> clz)
    {
        try{
            return parseString2Obj(new JSONObject(json),clz);
        }catch (Exception e)
        {
            LogUtils.error(e);
        }

        return null;
    }

    public static Object parseString2Obj(JSONObject json,Class<?> clz)
    {
        try{
            Object obj = clz.newInstance();
            for (Field tmp : clz.getDeclaredFields())
            {
                tmp.setAccessible(true);
                try
                {
                    if (tmp.getType().getName().equals("int"))
                    {
                        tmp.setInt(obj,json.getInt(tmp.getName()));
                    }
                    else if (tmp.getType().getName().equals("long"))
                    {
                        tmp.set(obj,json.getLong(tmp.getName()));
                    }
                    else
                    {
                        tmp.set(obj,json.get(tmp.getName()));
                    }
                }
                catch (Exception e)
                {
                    LogUtils.error(e);
                }
            }

            return obj;
        }catch (Exception e)
        {
            LogUtils.error(e);
        }

        return null;
    }

}
