package main.com.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                if(m.matches()){
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
                /*if (message.equals("")) {
                    message = "输入有误!";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                    String num1 = "3444";
                    String num2 = "sgsdg";
                    intent.putExtra("one",num1);
                    intent.putExtra("two",num2);
                    startActivity(intent);
                }*/

            }
        });

    }
}
