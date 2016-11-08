package main.com.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NotesActivity extends Activity {

    private Button button_safe;
    private Button button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        TextView title = (TextView)findViewById(R.id.text_title);
        title.setText("记事本");

        //button_forward
        button_safe = (Button)findViewById(R.id.button_forward);
        button_safe.setText("保存");

        Toast.makeText(this, "打开记事本", Toast.LENGTH_SHORT).show();

        safeNotes();
        backNotes();
    }

    /**
     * 保存
     */
    private void safeNotes(){
        button_safe = (Button)findViewById(R.id.button_forward);
        button_safe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NotesActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 返回
     */
    private void backNotes(){
        button_back = (Button) findViewById(R.id.button_backward);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
