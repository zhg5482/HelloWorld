package main.com.helloworld;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class BookManagerActivity extends Activity {

    private static final String TAG = "BookManagerActivity";
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;


    private Handler mHandler = new Handler(){

        public void handleMessage(Message msg){
            switch (msg.what){
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.d("fewef","wef");
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
    }
}
