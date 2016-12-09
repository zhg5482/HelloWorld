package main.com.impl;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.com.dao.FileDao;
import main.com.dao.UserDao;
import main.com.entry.User;
import main.com.helloworld.NotesActivity;
import main.com.helloworld.R;

/**
 * Created by zhg-pc on 16/11/8.
 */

public class FileDaoImpl implements FileDao {

    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String DIR = "UserNote";
    public static final String FILENAME = "users.txt";

    //读取文件
    public String  readFile(){
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

                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (scan != null) {
                    scan.close(); // 关闭打印流
                }
            }
        } else { // SDCard不存在，记录日志
            Log.i("read file info: ","SDCard不存在");
        }
        return null;
    }

    //写入文件
    public boolean writeFile(Activity activity,String file_text){
        if (checkPermission(activity,READ_EXTERNAL_STORAGE,1)) {
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
                    out.println(file_text);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close(); // 关闭打印流
                    }
                }

                return true;

            } else { // SDCard不存在，使用Toast提示用户
                Log.i("read file info: ","SDCard不存在");
                return false;
            }
        }
        return true;
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

}
