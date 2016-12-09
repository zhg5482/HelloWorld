package main.com.dao;

import android.app.Activity;

import java.util.List;
import java.util.concurrent.Future;

import main.com.entry.User;

/**
 * Created by zhg-pc on 16/11/8.
 */

public interface FileDao {

    public String  readFile();  //读取文件
    public boolean writeFile(Activity activity,String text); //保存文件
}
