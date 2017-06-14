package com.dfrc.hxqh.dfrc_project.dao;

import android.content.Context;
import android.util.Log;

import com.dfrc.hxqh.dfrc_project.model.WOTASKNG;
import com.dfrc.hxqh.dfrc_project.ormLiteOpenHelper.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by think on 2016/7/12.
 * NG的DAO
 */
public class WoTaskNgDao {
    private static final String TAG = "WoTaskNgDao";
    private Context context;
    private Dao<WOTASKNG, Integer> WotaskDaoOpe;
    private DatabaseHelper helper;

    public WoTaskNgDao(Context context) {
        this.context = context;
        try {
            helper = DatabaseHelper.getHelper(context);
            WotaskDaoOpe = helper.getDao(WOTASKNG.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建wotaskng
     *
     * @param wotaskng
     */
    public void create(WOTASKNG wotaskng) {
        try {
            if (!isexitByNum(wotaskng.getWOSEQUENCE())) {
                WotaskDaoOpe.create(wotaskng);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新wotaskng
     *
     * @param wotaskng
     */
    public void update(WOTASKNG wotaskng) {
        try {
            WotaskDaoOpe.update(wotaskng);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新wotaskng
     *
     * @param list
     */
    public void update(final ArrayList<WOTASKNG> list) {
        try {
            WotaskDaoOpe.callBatchTasks(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for (WOTASKNG wotaskng : list) {
                        if (!isexitByNum(wotaskng.getWOSEQUENCE())) {
                            WotaskDaoOpe.createOrUpdate(wotaskng);
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
     * 按照工单查询本地是否存在此记录
     *
     * @param num
     * @return
     */
    public boolean isexitByNum(String num) {
        try {
            List<WOTASKNG> orderMainList = WotaskDaoOpe.queryBuilder().where().eq("WOSEQUENCE", num).query();
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
     * 根据WOSEQUENCE查询WoTaskNg
     *
     * @wosequence
     **/

    public WOTASKNG findByWosequence(String wosequence) {
        try {
            List<WOTASKNG> wotaskngList = WotaskDaoOpe.queryBuilder().where().eq("WOSEQUENCE", wosequence).query();
            Log.i(TAG, "size=" + wotaskngList.size());
            if (null != wotaskngList && wotaskngList.size() != 0) {
                return wotaskngList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 根据wonum查询WotaskNG
     *
     * @wonum
     **/
    public List<WOTASKNG> findByWonum(String wonum) {
        try {
            return WotaskDaoOpe.queryBuilder().where().eq("WONUM", wonum).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 删除选中的记录
     *
     * @wotaskngs
     * 1 表示删除成功
     * 0 表示删除失败
     **/
    public int deleteByWotaskngs(List<WOTASKNG> wotaskngs) {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        int deletemark=0;
        try {
            for (WOTASKNG wotaskng : wotaskngs) {
                ids.add(wotaskng.getId());
            }
            deletemark=WotaskDaoOpe.deleteIds(ids);
        } catch (SQLException e) {
            return deletemark;
        }
        return deletemark;
    }
}
