package main.com.helper;

import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by zhg-pc on 16/11/7.
 */

public class HttpUrlThread {

    private final int SUCCESS = 1;
    private final int FAILURE = 0;
    private final int ERRORCODE = 2;
    protected String weatherResult;
    public Message msg;
    public String result;

    private Handler handler = new Handler() {
        @Override
        public void close() {

        }

        @Override
        public void flush() {

        }

        @Override
        public void publish(LogRecord record) {

        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    /**
                     * 获取信息成功后，对该信息进行JSON解析，得到所需要的信息，然后在textView上展示出来。
                     */
                    JSONAnalysis(msg.obj.toString());

                    break;

                case FAILURE:
                    break;

                case ERRORCODE:

                    break;
                default:
                    break;
            }
        };
    };



    /**
     * JSON解析方法
     */
    protected void JSONAnalysis(String string) {
        JSONObject object = null;
        try {
            object = new JSONObject(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /**
         * 在你获取的string这个JSON对象中，提取你所需要的信息。
         */
        JSONObject ObjectInfo = object.optJSONObject("weatherinfo");
        String city = ObjectInfo.optString("city");
        String date = ObjectInfo.optString("date_y");
        String week = ObjectInfo.optString("week");
        String temp = ObjectInfo.optString("temp1");
        String weather = ObjectInfo.optString("weather1");
        String index = ObjectInfo.optString("index_d");

        weatherResult = "城市：" + city + "\n日期：" + date + "\n星期：" + week
                + "\n温度：" + temp + "\n天气情况：" + weather + "\n穿衣指数：" + index;
    }


    public void  getjson(final String path) {
        /**
         * 点击按钮事件，在主线程中开启一个子线程进行网络请求
         * （因为在4.0只有不支持主线程进行网络请求，所以一般情况下，建议另开启子线程进行网络请求等耗时操作）。
         */
        new Thread() {
            public void run() {

                int code;
                try {

                    URL url = new URL(path);
                    /**
                     * 这里网络请求使用的是类HttpURLConnection，另外一种可以选择使用类HttpClient。
                     */
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setRequestMethod("GET");//使用GET方法获取
                    conn.setConnectTimeout(5000);
                    code = conn.getResponseCode();

                    if (code == 200) {
                        /**
                         * 如果获取的code为200，则证明数据获取是正确的。
                         */
                        InputStream is = conn.getInputStream();
                        result = HttpUtils.readMyInputStream(is);

                        /**
                         * 子线程发送消息到主线程，并将获取的结果带到主线程，让主线程来更新UI。
                         */
                        Log.i("3333","+++++++++");
                        msg.obj = result;
                        msg.what = SUCCESS;

                        //handler.sendMessage(msg);

                    } else {

                        msg.what = ERRORCODE;
                        //handler.sendMessage(msg);
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                    /**
                     * 如果获取失败，或出现异常，那么子线程发送失败的消息（FAILURE）到主线程，主线程显示Toast，来告诉使用者，数据获取是失败。
                     */
                    msg.what = FAILURE;
                    //handler.sendMessage(msg);
                }
            };
        }.start();
    }

}
