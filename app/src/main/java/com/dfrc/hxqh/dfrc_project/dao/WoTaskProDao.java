package com.dfrc.hxqh.dfrc_project.dao;

import android.content.Context;
import android.util.Log;

import com.dfrc.hxqh.dfrc_project.model.WOTASKPRO;
import com.dfrc.hxqh.dfrc_project.ormLiteOpenHelper.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by think on 2016/7/12.
 * 新增问题点DAO
 */
public class WoTaskProDao {
    private static final String TAG = "WoTaskProDao";
    private Context context;
    private Dao<WOTASKPRO, Integer> WotaskDaoOpe;
    private DatabaseHelper helper;

    public WoTaskProDao(Context context) {
        this.context = context;
        try {
            helper = DatabaseHelper.getHelper(context);
            WotaskDaoOpe = helper.getDao(WOTASKPRO.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建wotaskpro
     *
     * @param wotaskpro
     */
    public void create(WOTASKPRO wotaskpro) {
        try {
            if (!isexitByNum(wotaskpro.getWOSEQUENCE())) {
                WotaskDaoOpe.create(wotaskpro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新wotaskpro
     **/
    public void update(WOTASKPRO wotaskpro) {
        try {
            WotaskDaoOpe.update(wotaskpro);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新wotaskpro
     *
     * @param list
     */
    public void update(final ArrayList<WOTASKPRO> list) {
        try {
            WotaskDaoOpe.callBatchTasks(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for (WOTASKPRO wotaskpro : list) {
                        if (!isexitByNum(wotaskpro.getWOSEQUENCE())) {
                            WotaskDaoOpe.createOrUpdate(wotaskpro);
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
            List<WOTASKPRO> orderMainList = WotaskDaoOpe.queryBuilder().where().eq("WOSEQUENCE", num).query();
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
     * 根据WOSEQUENCE查询WoTaskPro
     *
     * @wosequence
     **/

    public WOTASKPRO findByWosequence(String wosequence) {
        try {
            List<WOTASKPRO> wotaskproList = WotaskDaoOpe.queryBuilder().where().eq("WOSEQUENCE", wosequence).query();
            Log.i(TAG, "size=" + wotaskproList.size());
            if (null != wotaskproList && wotaskproList.size() != 0) {
                return wotaskproList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 根据wonum查询WOTASKPRO
     *
     * @wonum
     **/
    public List<WOTASKPRO> findByWonum(String wonum) {
        Log.i(TAG,"PRO wonum="+wonum);
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
     * @wotaskpros
     * 1 表示删除成功
     * 0 表示删除失败
     **/
    public int deleteByWotaskpros(List<WOTASKPRO> wotaskpros) {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        int deletemark=0;
        try {
            for (WOTASKPRO wotaskpro : wotaskpros) {
                ids.add(wotaskpro.getId());
            }
            deletemark= WotaskDaoOpe.deleteIds(ids);
        } catch (SQLException e) {
            return deletemark;
        }
        return deletemark;
    }

}
