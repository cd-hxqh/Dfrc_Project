package com.dfrc.hxqh.dfrc_project.dao;

import android.content.Context;
import android.util.Log;

import com.dfrc.hxqh.dfrc_project.model.USERPERMISSIONS;
import com.dfrc.hxqh.dfrc_project.ormLiteOpenHelper.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by think on 2016/7/12.
 * 用户权限表
 */
public class UserPermissionsDao {
    private static final String TAG = "UserDao";
    private Context context;
    private Dao<USERPERMISSIONS, Integer> UserPermissionsDaoOpe;
    private DatabaseHelper helper;

    public UserPermissionsDao(Context context) {
        this.context = context;
        try {
            helper = DatabaseHelper.getHelper(context);
            UserPermissionsDaoOpe = helper.getDao(USERPERMISSIONS.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建userpermissions
     *
     * @param userpermissions
     */
    public void create(USERPERMISSIONS userpermissions) {
        try {
            UserPermissionsDaoOpe.create(userpermissions);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新userpermissions
     *
     * @param userpermissions
     */
    public void update(USERPERMISSIONS userpermissions) {
        try {
            UserPermissionsDaoOpe.update(userpermissions);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新userpermissionss
     *
     * @param list
     */
    public void update(final ArrayList<USERPERMISSIONS> list) {
        try {
            UserPermissionsDaoOpe.callBatchTasks(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for (USERPERMISSIONS userpermissions : list) {
                        if (!isexitByNum(userpermissions.getPERSONID(), userpermissions.getAPPID())) {
                            UserPermissionsDaoOpe.create(userpermissions);
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
     * 根据persionId与appid查询USERPERMISSIONS表是否有该权限
     *
     * @param personId
     * @param appid
     * @return
     */
    public boolean isexitByNum(String personId, String appid) {
        try {
            List<USERPERMISSIONS> orderMainList = UserPermissionsDaoOpe.queryBuilder().where().eq("PERSONID", personId).and().eq("APPID", appid).query();
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
    public List<USERPERMISSIONS> findByPersonid(String personid) {
        Log.i(TAG,"personid="+personid);
        try {
            return UserPermissionsDaoOpe.queryBuilder().where().eq("PERSONID", personid).query();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
