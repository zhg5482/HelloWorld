package main.com.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Handler;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.LogRecord;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import main.com.helper.HttpUtils;


public class MainActivity extends Activity {

    private String mStrContent = null;
    private static final int MSG_UPDATE_TEXT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getJsonByInternet("http://test.api.medbanks.cn/user/login?username=13812345678&password=medbankstest");

        /*HttpUrlThread httpUrlThread = new HttpUrlThread();
        Log.i("=====","ggee");
        httpUrlThread.getjson("http://test.api.medbanks.cn/user/login?username=13812345678&password=medbankstest");
*/
        login();
    }

    /**
     * 主线程
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg){
            switch (msg.what){
                case MSG_UPDATE_TEXT:
                    if(mStrContent != null)
                        Log.i("handle",mStrContent);
                    break;

                default:
                    break;
            }
            super.handleMessage(msg);
        };
    };
    /**
     * 登录
     */
    private void login(){
        //得到按钮实例
        Button hellobtn = (Button) findViewById(R.id.btn1);
        hellobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到textview实例
                EditText hellotv = (EditText) findViewById(R.id.tx1);

                String message = hellotv.getText().toString();

                Pattern p = Pattern.compile("[0-9]*");
                //p=Pattern.compile("[a-zA-Z]");
                //p=Pattern.compile("[\u4e00-\u9fa5]");
                Matcher m = p.matcher(message);

                if(m.matches() && !message.equals("")){
                    //请求接口
                    //getJsonByInternet("http://test.api.medbanks.cn/user/login?username=13812345678&password=medbankstest");

                    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                    String num1 = "3444";
                    String num2 = "sgsdg";
                    intent.putExtra("one",num1);
                    intent.putExtra("two",num2);
                    startActivity(intent);
                }else{
                    message = "输入有误! 不能为空或非数字!";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * 请求接口
     * @param path
     */
    public void getJsonByInternet(final String path) {
        new Thread() {
            public  void run() {
                int code;
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setRequestMethod("GET");//使用GET方法获取
                    conn.setConnectTimeout(5000);
                    code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream is = conn.getInputStream();
                        String result = HttpUtils.readMyInputStream(is);
                        mStrContent = result.toString();
                        Log.i("====",mStrContent);
                        handler.sendEmptyMessage(MSG_UPDATE_TEXT);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }

}
