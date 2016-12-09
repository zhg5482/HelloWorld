package main.com.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import main.com.dao.FileDao;
import main.com.impl.FileDaoImpl;

public class RegistActivity extends Activity {

    private EditText username;
    private EditText password;
    private RadioButton sex;
    private String message;
    private EditText nickname;
    private EditText age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        showViews();   //加载初始化页面
        register();    //注册

    }

    public void showViews(){
        TextView title = (TextView) findViewById(R.id.text_title);
        title.setText("注册");

        Button Left_btn = (Button) findViewById(R.id.button_backward); //隐藏控件
        Left_btn.setVisibility(View.GONE);

        Button right_btn = (Button) findViewById(R.id.button_forward); //隐藏控件
        right_btn.setVisibility(View.GONE);
    }


    public void register(){
        Button register = (Button) findViewById(R.id.btn_register);
        message = "注册成功";
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //接收数据
                username = (EditText) findViewById(R.id.re_name);
                password = (EditText) findViewById(R.id.re_pwd);
                nickname = (EditText) findViewById(R.id.re_nickname);
                age      = (EditText) findViewById(R.id.re_age);
                sex      = (RadioButton) findViewById(R.id.male);


                //判断数据
                if(null == username.getText() || null == password.getText() || null == nickname.getText() || null == age.getText()){
                    message = "参数为空";
                }

                if(username.getText().length() > 5){
                    message = "用户名太长";
                }

                if(password.getText().length() < 6){
                    message = "密码太短";
                }

                int age_int = Integer.parseInt(age.getText().toString());

                if(age_int < 1 || age_int > 100){
                    message = "年龄输出有误";
                }

                //存储数据
                FileDao fileDao = new FileDaoImpl();
                String readContent = fileDao.readFile();

                boolean flag = fileDao.writeFile(RegistActivity.this,readContent);

                if (!flag){
                    message = "注册失败";
                }

                Toast.makeText(RegistActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
