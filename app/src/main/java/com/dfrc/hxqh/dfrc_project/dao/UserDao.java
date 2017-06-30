package com.dfrc.hxqh.dfrc_project.dao;

import android.content.Context;

import com.dfrc.hxqh.dfrc_project.model.USER;
import com.dfrc.hxqh.dfrc_project.ormLiteOpenHelper.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by think on 2016/7/12.
 * 用户表
 */
public class UserDao {
    private static final String TAG = "UserDao";
    private Context context;
    private Dao<USER, Integer> UserDaoOpe;
    private DatabaseHelper helper;

    public UserDao(Context context) {
        this.context = context;
        try {
            helper = DatabaseHelper.getHelper(context);
            UserDaoOpe = helper.getDao(USER.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建user
     *
     * @param user
     */
    public void create(USER user) {
        try {
            UserDaoOpe.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新user
     *
     * @param user
     */
    public void update(USER user) {
        try {
            UserDaoOpe.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新users
     *
     * @param list
     */
    public void update(final ArrayList<USER> list) {
        try {
            UserDaoOpe.callBatchTasks(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for (USER user : list) {
                        if (!isexitByNum(user.getUSERNAME())) {
                            UserDaoOpe.create(user);
                        }
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据USERNAME查询USER表是否有该人员
     *
     * @param username
     * @return
     */
    public boolean isexitByNum(String username) {
        try {
            List<USER> orderMainList = UserDaoOpe.queryBuilder().where().eq("USERNAME", username).query();
            if (orderMainList.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据UserName查询该用户
     **/
    public USER findByUserName(String username) {
        try {
            List<USER> orderMainList = UserDaoOpe.queryBuilder().where().eq("USERNAME", username).query();
            if (null != orderMainList && orderMainList.size() > 0) {
                return orderMainList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 根据username与password查询USER是否验证成功
     */
    public boolean isLoginSccessful(String username, String password) {
        try {
            List<USER> orderMainList = UserDaoOpe.queryBuilder().where().eq("USERNAME", username).and().eq("PASSWORD", password).query();
            if (null != orderMainList && orderMainList.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
