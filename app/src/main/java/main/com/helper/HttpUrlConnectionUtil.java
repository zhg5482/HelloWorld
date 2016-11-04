package main.com.helper;

import android.os.Debug;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zhg-pc on 16/11/4.
 */

public class HttpUrlConnectionUtil {

    public static String get(String params){
        try {
            URL url = new URL(params);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();
            if (code == 200){
                InputStream is = conn.getInputStream();//字节流转换成字符串
                return streamToString(is);
            }else{
                return "网络访问失败";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "网络访问失败";
        }
    }

    public static String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            //Log.e(tag, e.toString());
            return null;
        }
    }
}
