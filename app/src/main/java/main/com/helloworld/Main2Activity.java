package main.com.helloworld;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private TextView mTitleTextView;
    private Button mBackwardbButton;
    private Button mForwardButton;
    private FrameLayout mContentLayout;
    private WebView webview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupViews();  //加载 activity_title 布局,并获取标题及两侧按钮

        //setIntent();

        //saveFrom();

        openWebView();

    }

    /**
     * 加载页面布局
     */
    private void setupViews(){
        super.setContentView(R.layout.activity_main2);
        TextView title = (TextView)findViewById(R.id.text_title);
        title.setText("Hello Android");

        //mTitleTextView   = (TextView)findViewById(R.id.text_title);
        //mContentLayout   = (FrameLayout) findViewById(R.id.layout_content);
        //mBackwardbButton = (Button) findViewById(R.id.button_backward);
        //mForwardButton   = (Button) findViewById(R.id.button_forward);
    }

    /**
     * 接受参数
     * toast 提示
     */
    private void setIntent(){

        Intent intent = getIntent();
        String one = intent.getStringExtra("one");

        Toast.makeText(this, one, Toast.LENGTH_SHORT).show();
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
}
