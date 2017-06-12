package com.dfrc.hxqh.dfrc_project.dao;

import android.content.Context;
import android.util.Log;

import com.dfrc.hxqh.dfrc_project.model.WOTASK;
import com.dfrc.hxqh.dfrc_project.ormLiteOpenHelper.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by think on 2016/7/12.
 * 定期点检工单子表
 */
public class WoTaskDao {
    private static final String TAG="WoTaskDao";
    private Context context;
    private Dao<WOTASK, Integer> WotaskDaoOpe;
    private DatabaseHelper helper;

    public WoTaskDao(Context context) {
        this.context = context;
        try {
            helper = DatabaseHelper.getHelper(context);
            WotaskDaoOpe = helper.getDao(WOTASK.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新wotask
     *
     * @param wotask
     */
    public void update(WOTASK wotask) {
        try {
            if (!isexitByNum(wotask.getASSETNUM())) {
                WotaskDaoOpe.createOrUpdate(wotask);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新wotask
     *
     * @param list
     */
    public void update(final ArrayList<WOTASK> list) {
        Log.i(TAG,"size="+list.size());
        try {
            WotaskDaoOpe.callBatchTasks(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for (WOTASK wotask : list) {
                        if (!isexitByNum(wotask.getWOTASKID())) {
                            Log.i(TAG,"111111111");
                            WotaskDaoOpe.createOrUpdate(wotask);
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
     * 查询所有
     *
     * @return
     */
    public List<WOTASK> queryForAll() {
        try {
            return WotaskDaoOpe.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 删除所有信息
     */
    public void deleteall() {
        try {
            WotaskDaoOpe.delete(WotaskDaoOpe.queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据工单id删除信息
     */
    public void deleteById(int id) {
        try {
            WotaskDaoOpe.delete(WotaskDaoOpe.queryBuilder().where().eq("id", id).query());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按照工单id查询工单
     *
     * @param id
     * @return
     */
    public WOTASK SearchByNum(int id) {
        try {
            return WotaskDaoOpe.queryBuilder().where().eq("id", id).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 按照工单查询本地是否存在此记录
     *
     * @param num
     * @return
     */
    public boolean isexitByNum(String num) {
        try {
            List<WOTASK> orderMainList = WotaskDaoOpe.queryBuilder().where().eq("WOTASKID", num).query();
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
}
