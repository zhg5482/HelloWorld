package main.com.helper;

import java.io.Serializable;
import java.util.Map;

/**
 * 序列化map供Bundle传递map使用
 * Created by zhg-pc on 16/11/7.
 */

public class SerializableMap implements Serializable{

    private Map<String,String> map;

    public Map<String,String> getMap(){
        return map;
    }

    public void setMap(Map<String,String> map){
        this.map = map;
    }
}
