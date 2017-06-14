package com.dfrc.hxqh.dfrc_project.dao;

import android.content.Context;

import com.dfrc.hxqh.dfrc_project.model.WOTASKOK;
import com.dfrc.hxqh.dfrc_project.ormLiteOpenHelper.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by think on 2016/7/12.
 * WoTaskOKDAO
 */
public class WoTaskOKDao {
    private static final String TAG = "WoTaskOKDao";
    private Context context;
    private Dao<WOTASKOK, Integer> WotaskDaoOpe;
    private DatabaseHelper helper;

    public WoTaskOKDao(Context context) {
        this.context = context;
        try {
            helper = DatabaseHelper.getHelper(context);
            WotaskDaoOpe = helper.getDao(WOTASKOK.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建wotaskok
     *
     * @param wotaskok
     */
    public void create(WOTASKOK wotaskok) {
        try {
            WotaskDaoOpe.create(wotaskok);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新wotaskok
     *
     * @param wotaskok
     */
    public void update(WOTASKOK wotaskok) {
        try {
            WotaskDaoOpe.update(wotaskok);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新wotaskok
     *
     * @param list
     */
    public void update(final ArrayList<WOTASKOK> list) {
        try {
            WotaskDaoOpe.callBatchTasks(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for (WOTASKOK wotaskok : list) {
                        if (!isexitByNum(wotaskok.getWOSEQUENCE())) {
                            WotaskDaoOpe.createOrUpdate(wotaskok);
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
     * 根据序号查询
     *
     * @param num
     * @return
     */
    public boolean isexitByNum(String num) {
        try {
            List<WOTASKOK> orderMainList = WotaskDaoOpe.queryBuilder().where().eq("WOSEQUENCE", num).query();
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
     * 根据wonum查询WotaskOk
     *
     * @wonum
     **/
    public List<WOTASKOK> findByWonum(String wonum) {
        try {
            return WotaskDaoOpe.queryBuilder().where().eq("WONUM", wonum).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据序号查询
     *
     * @param wosequence
     * @return
     */
    public WOTASKOK findByWosequence(String wosequence) {
        try {
            List<WOTASKOK> orderMainList = WotaskDaoOpe.queryBuilder().where().eq("WOSEQUENCE", wosequence).query();
           if(null!=orderMainList&&orderMainList.size()!=0){
               return orderMainList.get(0);
           }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }


    /**
     * 删除选中的记录
     *
     * @wotaskoks 1 表示删除成功
     * 0 表示删除失败
     **/
    public int deleteByWotaskoks(List<WOTASKOK> wotaskoks) {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        int deletemark = 0;
        try {
            for (WOTASKOK wotaskok : wotaskoks) {
                ids.add(wotaskok.getId());
            }
            deletemark = WotaskDaoOpe.deleteIds(ids);
        } catch (SQLException e) {
            e.printStackTrace();
            return deletemark;
        }
        return deletemark;
    }
}
