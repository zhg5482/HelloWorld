package main.com.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import android.os.Handler;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import main.com.helper.HttpUtils;

public class MainActivity extends Activity {

    private static String LOG_URL = "http://test.api.medbanks.cn/user/login";
    private static final int MSG_UPDATE_TEXT = 1;
    private RelativeLayout relativeLayout;
    private String mStrContent = null;
    private ScrollView scrollView;
    private EditText hellotv1;
    private EditText hellotv2;
    private String username;
    private String password;
    private int frist_h = 0;
    private int second_h = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView = (ScrollView) findViewById(R.id.scrollView);

        relativeLayout = (RelativeLayout) super.findViewById(R.id.activity_main);

        relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { //全局监听页面
            @Override
            public void onGlobalLayout() {
                if(frist_h == 0){
                    frist_h = relativeLayout.getMeasuredHeight();
                }
                Log.i("frist_h",frist_h+"");
            }
        });
        login();//登录
    }


    //线程接收 滑动activity
    private Handler handler1 = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_TEXT:
                    scrollView.scrollTo(0, (frist_h-second_h));
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    //全局监控屏幕高度
    private void getScreenHeight(){

        relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { //全局监听页面
            @Override
            public void onGlobalLayout() {
                if(second_h == 0){
                    second_h = relativeLayout.getMeasuredHeight();
                }
                handler1.sendEmptyMessage(MSG_UPDATE_TEXT);
            }
        });
    }


    /**
     * 防止遮挡登录按钮
     *
     * @param passwordView
     */
    private void preventCoverLogin(EditText passwordView) {
        passwordView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                changeScrollView();
                return false;
            }
        });
    }

    /**
     * 200毫秒之后使ScrollView指向底部--防止遮挡登录按钮
     */
    private void changeScrollView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // scrollview.smoothScrollTo(0, tvLogin.getMeasuredHeight() * 2);
                scrollView.smoothScrollTo(0, 200);
            }

        }, 100);
    }

    /**
     * 主线程
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_TEXT:
                    if (mStrContent != null) {

                        Map<String, String> map = JSONAnalysis(mStrContent);

                        if (null != map) {
                            //访问Main2Activity
                            Intent intent = new Intent(MainActivity.this, Main2Activity.class);

                            Iterator iter = map.keySet().iterator();
                            while (iter.hasNext()) {
                                String key = iter.next().toString();
                                String val = map.get(key);
                                //content += key+": "+val+"\n";
                                intent.putExtra(key, val);

                            }

                            Log.i("====++++", mStrContent + "++++");
                            //intent.putExtra("content",content);
                           /* final SerializableMap myMap = new SerializableMap();
                            myMap.setMap(map);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("map",myMap);
                            intent.putExtras(bundle);*/
                            //Log.i("++++++",content);
                            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                        } else {
                            Toast.makeText(MainActivity.this, "用户名密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 登录
     */
    private void login() {

        //得到textview实例
        hellotv1 = (EditText) findViewById(R.id.tx1);
        hellotv2 = (EditText) findViewById(R.id.tx2);

        hellotv1.setOnTouchListener(new View.OnTouchListener() {  //监控editText框onTouch 事件
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getScreenHeight();
                return false;
            }
        });

        hellotv2.setOnTouchListener(new View.OnTouchListener() {  //监控editText框onTouch 事件
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getScreenHeight();
                return false;
            }
        });

        username = (String) PreferenceUtil.getObject(getSharedPreferences("userInfo", MODE_PRIVATE), "name", "");
        password = (String) PreferenceUtil.getObject(getSharedPreferences("userInfo", MODE_PRIVATE), "pass", "");

        if (username != null || password != null) {
            hellotv1.setText(username);
            hellotv2.setText(password);
        }

        //得到按钮实例
        Button hellobtn = (Button) findViewById(R.id.btn1);
        hellobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = hellotv1.getText().toString();
                password = hellotv2.getText().toString();


                Pattern p = Pattern.compile("^[1][3-8][0-9]{9}$");
                //p=Pattern.compile("[a-zA-Z]");
                //p=Pattern.compile("[\u4e00-\u9fa5]");
                Matcher m = p.matcher(username);

                if (m.matches() && !username.equals("")) {
                    //请求接口
                    String url = LOG_URL + "?username=" + username + "&password=" + password;
                    getJsonByInternet(url);

                    /**
                     * 保存登录信息
                     **/
                    PreferenceUtil.putObject(getSharedPreferences("userInfo", MODE_PRIVATE), "name", username);
                    PreferenceUtil.putObject(getSharedPreferences("userInfo", MODE_PRIVATE), "pass", password);
                } else {
                    Toast.makeText(MainActivity.this, "输入有误! 不能为空或非数字!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 请求接口
     *
     * @param path
     */
    public void getJsonByInternet(final String path) {
        new Thread() {
            public void run() {
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
                        handler.sendEmptyMessage(MSG_UPDATE_TEXT);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ;
        }.start();
    }

    /**
     * json 解析
     *
     * @param string
     */
    protected Map<String, String> JSONAnalysis(String string) {
        JSONObject object = null;
        try {
            object = new JSONObject(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /**
         * 在你获取的string这个JSON对象中，提取你所需要的信息。
         */
        int code = object.optInt("code");  //请求是否成功
        if (code != 0) {
            return null;
        }

        JSONObject ObjectInfo = object.optJSONObject("data");


        Map<String, String> map =
                new HashMap<String, String>();
        if (null != ObjectInfo) {

            map.put("userid", ObjectInfo.optString("userid"));
            map.put("username", ObjectInfo.optString("username"));
            map.put("nickname", ObjectInfo.optString("nickname"));
            map.put("avatar", ObjectInfo.optString("avatar"));
        }
        return map;
    }
}
