package main.com.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class NotesActivity extends Activity {

    private Button button_safe;
    private Button button_back;
    private String fileName = "notes.txt";

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

        safeNotes();
        backNotes();
    }

    /**
     * 保存
     */
    private void safeNotes() {
        button_safe = (Button) findViewById(R.id.button_forward);
        button_safe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText notes_txt = (EditText) findViewById(R.id.notes_txt);
                String notes = notes_txt.getText().toString();

                if (notes.equals("")) {
                    Toast.makeText(NotesActivity.this, "文本内容不能为空!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("===", notes);

                    /*try {
                        FileOutputStream outputStream = openFileOutput(fileName,Activity.MODE_PRIVATE);
                        outputStream.write(notes.getBytes());
                        Log.i("+++++++",notes.getBytes()+"");
                        outputStream.flush();
                        outputStream.close();
                        Toast.makeText(NotesActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }*/


                    writeSd(notes);

                   /* Log.i("===", getSdCardPath());
                    try {
                        File root = Environment.getExternalStorageDirectory();
                        Log.i("========", root + " 222");
                        if (isSdCardExist() && root.canWrite()) {
                            File file = new File(root, fileName);

                            if (!file.exists()) {
                                file.createNewFile();
                            }

                            FileOutputStream fos = new FileOutputStream(file);
                            fos.write(notes.getBytes());
                            fos.close();
                        } else {
                            Toast.makeText(NotesActivity.this, "Can't write.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                }

            }
        });
    }


    public static final String DIR = "PictureSiPai";
    public static final String FILENAME = "photoUpload";

    // 文件写操作函数
    public void writeSd(String content) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { // 如果sdcard存在
            File file = new File(Environment.getExternalStorageDirectory()
                    .toString()
                    + File.separator
                    + DIR
                    + File.separator
                    + FILENAME); // 定义File类对象

            if (!file.getParentFile().exists()) { // 父文件夹不存在
                file.getParentFile().mkdirs(); // 创建文件夹
            }

            PrintStream out = null; // 打印流对象用于输出

            try {
                out = new PrintStream(new FileOutputStream(file, true)); // 追加文件
                out.println(content);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close(); // 关闭打印流
                }
            }

            Log.i("haha",file.getAbsolutePath());

            Toast.makeText(NotesActivity.this, "保存chenggong！", Toast.LENGTH_LONG).show();

        } else { // SDCard不存在，使用Toast提示用户

            Toast.makeText(NotesActivity.this, "保存失败，SD卡不存在！", Toast.LENGTH_LONG).show();
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

    public  String readSd() {

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

                //ToastUtils.showShort(context,sb.toString());

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
}
