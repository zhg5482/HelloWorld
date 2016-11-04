package main.com.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

                if (message.equals("")) {
                    message = "输入有误!";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                    String num1 = "3444";
                    String num2 = "sgsdg";
                    intent.putExtra("one",num1);
                    intent.putExtra("two",num2);
                    startActivity(intent);
                }

            }
        });

    }
}
