package com.xyf.AddressList.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by shxiayf on 2015/12/7.
 */
public class HttpUrlUtils {

    private static final class Util {
        public static HttpUrlUtils INSTANCES = new HttpUrlUtils();
    }

    public static HttpUrlUtils getInstances()
    {
        return Util.INSTANCES;
    }

    public String parseStream2String(InputStream is)
    {
        try {
            byte[] buf = new byte[1024];
            int rs = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while((rs = is.read(buf)) != -1)
            {
                baos.write(buf,0,rs);
            }
            baos.flush();
            is.close();
            String content = new String(baos.toByteArray());
            baos.close();

            LogUtils.i(HttpUrlUtils.class.getSimpleName(),String.format("download:[%s]",content));

            return content;
        }
        catch (Exception e)
        {
            LogUtils.error(e);
        }

        return "";
    }

    public interface HttpResultListener
    {
        void onResponse(InputStream is);
    }

    private HttpResultListener callback;
    public void Post(final String url,final String content, final HttpResultListener listener)
    {
        this.callback = listener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL mUrl = null;
                HttpURLConnection conn = null;
                try{
                    mUrl = new URL(url);
                    conn = (HttpURLConnection) mUrl.openConnection();
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(20000);

                    conn.getOutputStream().write(content.getBytes("UTF-8"));

                    if (conn.getResponseCode() == 200)
                    {
                        listener.onResponse(conn.getInputStream());
                        return;
                    }

                    listener.onResponse(null);

                }catch (Exception e)
                {
                    LogUtils.error(e);
                }
                finally {
                    if (conn != null)
                    {
                        conn.disconnect();
                    }
                }
            }
        }).start();

    }

}
