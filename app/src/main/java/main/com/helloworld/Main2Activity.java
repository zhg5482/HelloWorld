package main.com.helloworld;

import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class Main2Activity extends Activity {

    private TextView mTitleTextView;
    private Button mBackwardbButton;
    private Button mForwardButton;
    private FrameLayout mContentLayout;
    private WebView webview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupViews();  //加载 activity_title 布局,并获取标题及两侧按钮

        //saveFrom();

        openWebView();
        backward();

    }

    /**
     * 加载视图
     */
    private void setupViews(){
        super.setContentView(R.layout.activity_main2);
        TextView title = (TextView)findViewById(R.id.text_title);
        title.setText("Hello Android");

        Intent intent = getIntent();
        //Bundle bundle = getIntent().getExtras();
        //SerializableMap serializableMap = (SerializableMap) bundle.get("map");

        String userid = intent.getStringExtra("userid").toString();
        String username = intent.getStringExtra("username").toString();
        String nickname = intent.getStringExtra("nickname").toString();
        String avatar = intent.getStringExtra("avatar").toString();


        ImageView img5 = (ImageView) this.findViewById(R.id.img5);

        Glide.with(getBaseContext())  //ImageView 显示图片
                .load(Uri.parse(avatar))
                .into(img5);

        ((TextView) this.findViewById(R.id.txt5_1)).setText("用户编号: "+userid);
        ((TextView) this.findViewById(R.id.txt5_2)).setText("用户名: "+username);
        ((TextView) this.findViewById(R.id.txt5_3)).setText("用户昵称: "+nickname);
        //this.findViewById(R.id.txt5_1).
        //Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }


    /**
     * 打开浏览器
     */
    private void saveFrom(){
        mForwardButton   = (Button) findViewById(R.id.button_forward);
        mForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://www.baidu.com/");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
    }

    /**
     * webview 渲染网页
     */
    private void openWebView(){
        mForwardButton   = (Button) findViewById(R.id.button_forward);
        mForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Main2Activity.this, "reer", Toast.LENGTH_SHORT).show();

                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_edit2); //隐藏控件
                linearLayout.setVisibility(View.GONE);

                LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.webxml); //显示控件
                linearLayout2.setVisibility(View.VISIBLE);

                webview = (WebView) findViewById(R.id.webview);

                WebSettings webSettings = webview.getSettings();
                webSettings.setJavaScriptEnabled(true); //设置 webview 属性,能够执行 javascript脚本
                webSettings.setAllowFileAccess(true); //设置可以访问文件
                webSettings.setBuiltInZoomControls(true); //设置支持缩放

                webview.loadUrl("https://www.baidu.com/"); //加载需要显示的网页
                webview.setWebViewClient(new WebViewClient()); //设置web视图
            }
        });
    }


    /**
     * webview 渲染网页
     */
    private void backward(){
        mBackwardbButton   = (Button) findViewById(R.id.button_backward);
        mBackwardbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
