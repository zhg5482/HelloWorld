package main.com.helloworld;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class NotesActivity extends Activity {

    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    private Button button_safe;
    private Button button_back;
    public static final String DIR = "HelloNotes";
    public static final String FILENAME = "notes.txt";
    private EditText notes_txt;
    private String notes;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        TextView title = (TextView) findViewById(R.id.text_title);
        title.setText("记事本");

        //button_forward
        button_safe = (Button) findViewById(R.id.button_forward);
        button_safe.setText("保存");

        //Toast.makeText(this, "打开记事本", Toast.LENGTH_SHORT).show();

        readSd();//读取记事本内容

        safeNotes();

        backNotes();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * 保存
     */
    private void safeNotes() {
        button_safe = (Button) findViewById(R.id.button_forward);
        button_safe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes_txt = (EditText) findViewById(R.id.notes_txt);
                notes = notes_txt.getText().toString();

                if (notes.equals("")) {
                    Toast.makeText(NotesActivity.this, "文本内容不能为空!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("===", notes);

                    writeSd(notes);

                    //保存后隐藏键盘
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
    }

    /**
     * 授权
     */
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};


    /**
     * 检查权限
     * @param context
     * @param requestPermission
     * @param requestCode
     * @return
     */
    public static boolean checkPermission(Activity context, String requestPermission, int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(context, requestPermission);

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context,PERMISSIONS_STORAGE, requestCode);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    /**
     * 授权
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 1) {
                writeSd(notes);
            }

        } else {
            Toast.makeText(NotesActivity.this, "请授权!", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    // 文件写操作函数
    public void writeSd(String content) {

        if (checkPermission(this,READ_EXTERNAL_STORAGE,1)) {
            Log.i("++++", Environment.getExternalStorageDirectory().canWrite() + "  " + Environment.getExternalStorageDirectory().canRead() );
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { // 如果sdcard存在


                File file_dir = new File(Environment.getExternalStorageDirectory()
                        .toString()
                        + File.separator
                        + DIR); // 定义File类对象

                if (!file_dir.exists()) { // 父文件夹不存在
                    Log.i("++file_dir.mkdirs()++", file_dir.mkdirs() + "");
                    // 创建文件夹
                }
                Log.i("++file_dir.exists()++", file_dir.exists() + "");


                File file = new File(Environment.getExternalStorageDirectory()
                        .toString()
                        + File.separator
                        + DIR
                        + File.separator
                        + FILENAME); // 定义File类对象


                PrintStream out = null; // 打印流对象用于输出

                try {
                    out = new PrintStream(new FileOutputStream(file, false)); // 追加文件
                    out.println(content);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close(); // 关闭打印流
                    }
                }

                Toast.makeText(NotesActivity.this, "保存成功！", Toast.LENGTH_LONG).show();

            } else { // SDCard不存在，使用Toast提示用户

                Toast.makeText(NotesActivity.this, "保存失败，SD卡不存在！", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 判断SDcard是否存在,[当没有外挂SD卡时,内置ROM也被识别为存在sd卡]
     *
     * @return
     */
    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static String getSdCardPath() {
        boolean exist = isSdCardExist();
        String sdpath = "";
        if (exist) {
            sdpath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
        } else {
            sdpath = "不适用";
        }
        return sdpath;
    }

    /**
     * 获取默认的文件路径
     *
     * @return
     */
    public static String getDefaultFilePath(String fileName) {
        String filepath = "";
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        if (file.exists()) {
            filepath = file.getAbsolutePath();
        } else {
            filepath = "不适用";
        }
        return filepath;
    }

    /**
     * 返回
     */
    private void backNotes() {
        button_back = (Button) findViewById(R.id.button_backward);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // 声明监听器
    /*private View.OnClickListener listener = new OnClickListener() {
        public void onClick(View v) {
            Button view = (Button) v;
            switch (view.getId()) {
                case R.id.addButton:
                    save();
                    break;
                case R.id.showButton:
                    read();
                    break;

            }
        }
    };*/

    /*private void read() {
        try {
            FileInputStream inputStream = this.openFileInput(fileName);
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            while (inputStream.read(bytes) != -1) {
                arrayOutputStream.write(bytes, 0, bytes.length);
            }
            inputStream.close();
            arrayOutputStream.close();
            String content = new String(arrayOutputStream.toByteArray());
            showTextView.setText(content);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    public String readSd() {

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) { // 如果sdcard存在

            File file = new File(Environment.getExternalStorageDirectory()
                    .toString()
                    + File.separator
                    + DIR
                    + File.separator
                    + FILENAME); // 定义File类对象
            if (!file.getParentFile().exists()) { // 父文件夹不存在
                file.getParentFile().mkdirs(); // 创建文件夹
            }


            Scanner scan = null; // 扫描输入
            StringBuilder sb = new StringBuilder();

            try {
                scan = new Scanner(new FileInputStream(file)); // 实例化Scanner
                while (scan.hasNext()) { // 循环读取
                    sb.append(scan.next()).append("\n"); // 设置文本
                }

                notes_txt = (EditText) findViewById(R.id.notes_txt);
                notes_txt.setText(sb.toString());

                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (scan != null) {
                    scan.close(); // 关闭打印流
                }
            }
        } else { // SDCard不存在，使用Toast提示用户
            Toast.makeText(NotesActivity.this, "读取失败，SD卡不存在！", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Notes Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
