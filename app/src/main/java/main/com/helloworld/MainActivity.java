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

import org.json.JSONArray;
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

    private String mStrContent = null;
    private static final int MSG_UPDATE_TEXT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login();//登录
    }

    /**
     * 主线程
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg){
            switch (msg.what){
                case MSG_UPDATE_TEXT:
                    if(mStrContent != null){
                        String content = "";
                        Map<String, String> map = JSONAnalysis(mStrContent);

                        if(null != map){
                            Iterator iter = map.keySet().iterator();
                            while (iter.hasNext()) {
                                String key = iter.next().toString();
                                String val = map.get(key);
                                content += key+": "+val+"\n";
                            }

                            //访问Main2Activity
                            Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                            intent.putExtra("content",content);
                           /* final SerializableMap myMap = new SerializableMap();
                            myMap.setMap(map);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("map",myMap);
                            intent.putExtras(bundle);*/
                            //Log.i("++++++",content);
                            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "用户名密码错误", Toast.LENGTH_SHORT).show();
                        }

                    }
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
                EditText hellotv1 = (EditText) findViewById(R.id.tx1);
                EditText hellotv2 = (EditText) findViewById(R.id.tx2);

                String username = hellotv1.getText().toString();
                String password = hellotv2.getText().toString();

                Pattern p = Pattern.compile("[0-9]*");
                //p=Pattern.compile("[a-zA-Z]");
                //p=Pattern.compile("[\u4e00-\u9fa5]");
                Matcher m = p.matcher(username);

                if(m.matches() && !username.equals("")){
                    //请求接口
                    String url = "http://test.api.medbanks.cn/user/login?username="+username+"&password="+password;
                    getJsonByInternet(url);

                }else{
                    Toast.makeText(MainActivity.this, "输入有误! 不能为空或非数字!", Toast.LENGTH_SHORT).show();
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
                        handler.sendEmptyMessage(MSG_UPDATE_TEXT);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }

    /**
     * json 解析
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
        if(code != 0){
            return null;
        }

        JSONObject ObjectInfo = object.optJSONObject("data");


        Map<String, String> map =
                new HashMap<String, String>();
        if(null != ObjectInfo){

            map.put("userid",ObjectInfo.optString("userid"));
            map.put("username",ObjectInfo.optString("username"));
            map.put("nickname",ObjectInfo.optString("nickname"));
            map.put("avatar",ObjectInfo.optString("avatar"));
        }
        return map;
    }
}
