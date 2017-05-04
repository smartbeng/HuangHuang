package com.lansum.eip.util;

import android.os.StrictMode;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by MaiBenBen on 2017/4/18.
 */

public class GetImageForInterNet {
    public void saveImage(final String url, final String fileName) {
        // TODO Auto-generated method stub
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        // 从网络上获取图片
        URL url1;
        try {
            url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            // 不使用缓存
            conn.setUseCaches(false);

            InputStream is = conn.getInputStream();
            FileOutputStream fos = new FileOutputStream(fileName);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            is.close();
            fos.close();
            Log.e("js", "path:" + fileName);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
