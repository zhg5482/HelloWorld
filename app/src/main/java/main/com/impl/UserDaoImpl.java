package main.com.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.dao.UserDao;
import main.com.entry.User;

/**
 * Created by zhg-pc on 16/11/8.
 */

public class UserDaoImpl implements UserDao {

    public List<User> getUserList(){

        return null;
    }

    public List<User> addUser(User user){

        List<User> userList = new ArrayList<User>();
        userList.add(user);

        return userList;
    }
}
