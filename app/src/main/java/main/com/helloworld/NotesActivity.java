package main.com.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class NotesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Toast.makeText(this, "打开记事本", Toast.LENGTH_SHORT).show();
    }
}
