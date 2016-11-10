package main.com.helloworld;

import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Main2Activity extends Activity {

    private Button mBackwardbButton;
    private Button mForwardButton;
    private WebView webview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupViews();  //加载 activity_title 布局,并获取标题及两侧按钮 读取数据

        //saveFrom();

        openWebView();  //加载webview

        openNotes();   //打开记事本

        backward();   //返回到上一页

    }


    //第三种方式(Android1.6版本及以后的版本中提供了)
    public void Btn3OnClick(View view) {

        switch (view.getId()) {

            case R.id.bottom_btn1:
                Toast.makeText(this, "bottom_btn1", Toast.LENGTH_SHORT).show();
                break;

            case R.id.bottom_btn2:
                Toast.makeText(this, "bottom_btn2", Toast.LENGTH_SHORT).show();
                break;

            case R.id.bottom_btn3:
                //Toast.makeText(this, "bottom_btn3", Toast.LENGTH_SHORT).show();
                Toast toast = Toast.makeText(getApplicationContext(),"自定义",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                break;

        }

    }

    /**
     * 底部菜单
     * @param v
     */
    public void onClick(View v) {
        RelativeLayout view = (RelativeLayout) v;
        Toast toast;
        switch (view.getId()) {
            case R.id.bottom_btn1:
                Toast.makeText(Main2Activity.this, "bottom_btn1", Toast.LENGTH_SHORT).show();
                break;

            case R.id.bottom_btn2:
                toast = Toast.makeText(getApplicationContext(),"bottom_btn2",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                LinearLayout toastView = (LinearLayout) toast.getView();
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setImageResource(R.mipmap.ic_launcher);
                toastView.addView(imageView,0);
                toast.show();
                break;

            case R.id.bottom_btn3:
                toast = Toast.makeText(getApplicationContext(),"bottom_btn3",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                break;

        }
    }

    /**
     * 加载视图
     */
    private void setupViews() {
        super.setContentView(R.layout.activity_main2);
        TextView title = (TextView) findViewById(R.id.text_title);
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

        ((TextView) this.findViewById(R.id.txt5_1)).setText("用户编号: " + userid);
        ((TextView) this.findViewById(R.id.txt5_2)).setText("用户名: " + username);
        ((TextView) this.findViewById(R.id.txt5_3)).setText("用户昵称: " + nickname);
        //this.findViewById(R.id.txt5_1).
        //Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }


    /**
     * 打开浏览器
     */
    private void saveFrom() {
        mForwardButton = (Button) findViewById(R.id.button_forward);
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
    private void openWebView() {
        mForwardButton = (Button) findViewById(R.id.button_forward);
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
    private void backward() {
        mBackwardbButton = (Button) findViewById(R.id.button_backward);
        mBackwardbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void openNotes() {
        Button notesBtn = (Button) findViewById(R.id.btn_text5);
        notesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Main2Activity.this, "打开记事本", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Main2Activity.this, NotesActivity.class);
                startActivity(intent);
            }
        });

    }
}
