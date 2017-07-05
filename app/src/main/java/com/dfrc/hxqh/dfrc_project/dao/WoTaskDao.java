package com.dfrc.hxqh.dfrc_project.dao;

import android.content.Context;

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
    private static final String TAG = "WoTaskDao";
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
     * 创建wotask
     *
     * @param wotask
     */
    public void create(WOTASK wotask) {
        try {
            WotaskDaoOpe.create(wotask);
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
            WotaskDaoOpe.update(wotask);
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
        try {
            WotaskDaoOpe.callBatchTasks(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for (WOTASK wotask : list) {
                        if (!isexitByNum(wotask.getWOTASKID())) {
                            WotaskDaoOpe.create(wotask);
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
     * 根据工单号查询WOTASK
     * wonum
     * n_responsor
     *
     * @return
     */
    public List<WOTASK> queryForByWonum(String wonum, String n_responsor) {
        try {
            if (null == n_responsor) {
                return WotaskDaoOpe.queryBuilder().where().eq("WONUM", wonum).query();
            } else {
                return WotaskDaoOpe.queryBuilder().where().eq("WONUM", wonum).and().eq("N_RESPONSOR", n_responsor).query();
            }
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


    /**
     * 根据wonum查询Wotask
     *
     * @wonum
     **/
    public List<WOTASK> findByWonum(String wonum) {
        try {
            return WotaskDaoOpe.queryBuilder().where().eq("WONUM", wonum).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据wonum与update查询wotask
     *
     * @param wonum
     * @param update
     **/
    public List<WOTASK> findByWonumAndUpdate(String wonum, int update) {
        try {
            return WotaskDaoOpe.queryBuilder().where().eq("WONUM", wonum).and().eq("UPDATE", update).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 删除选中的记录
     *
     * @wotask 1 表示删除成功
     * 0 表示删除失败
     **/
    public int deleteByWotasks(List<WOTASK> wotasks) {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        int deletemark = 0;
        try {
            for (WOTASK wotask : wotasks) {
                ids.add(wotask.getId());
            }
            deletemark = WotaskDaoOpe.deleteIds(ids);
        } catch (SQLException e) {
            return deletemark;
        }
        return deletemark;
    }


    /**
     * 根据当前WOSEQUENCE,ASSETNUM,N_RESULT查询相应点检明细行的数据
     * WOSEQUENCE
     * ASSETNUM
     * N_RESULT
     *
     * @return
     */
    public List<WOTASK> findByWonum(String wonum, String n_responsor, String search) {
        try {
            if (null == n_responsor) {

                return WotaskDaoOpe.queryBuilder().where().eq("WONUM", wonum).and().like("WOSEQUENCE", "%" + search + "%").or().like("ASSETNUM", search).or().like("ASSETNUM", search).or().like("N_RESULT", search).query();
            } else {
                return WotaskDaoOpe.queryBuilder().where().eq("WONUM", wonum).and().eq("N_RESPONSOR", n_responsor).and().like("WOSEQUENCE", "%" + search + "%").or().like("ASSETNUM", search).or().like("ASSETNUM", search).or().like("N_RESULT", search).query();

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据当前WOSEQUENCE,ASSETNUM,N_RESULT查询相应点检明细行的数据
     * WOSEQUENCE
     * ASSETNUM
     * N_RESULT
     *
     * @return
     */
    public List<WOTASK> findByAssetNum(String wonum, String assetnum,String n_responsor) {
        try {
            if (null == n_responsor) {

                return WotaskDaoOpe.queryBuilder().where().eq("WONUM", wonum).and().eq("ASSETNUM", assetnum).query();
            } else {
                return WotaskDaoOpe.queryBuilder().where().eq("WONUM", wonum).and().eq("N_RESPONSOR", n_responsor).and().eq("ASSETNUM", assetnum).query();

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
