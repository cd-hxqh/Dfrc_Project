package com.dfrc.hxqh.dfrc_project.dao;

import android.content.Context;

import com.dfrc.hxqh.dfrc_project.model.SPAREPART;
import com.dfrc.hxqh.dfrc_project.ormLiteOpenHelper.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by think on 2016/7/12.
 * 设备表
 */
public class SparepartDao {
    private static final String TAG = "SparepartDao";
    private Context context;
    private Dao<SPAREPART, Integer> SparepartDaoOpe;
    private DatabaseHelper helper;

    public SparepartDao(Context context) {
        this.context = context;
        try {
            helper = DatabaseHelper.getHelper(context);
            SparepartDaoOpe = helper.getDao(SPAREPART.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建sparepart
     *
     * @param sparepart
     */
    public void create(SPAREPART sparepart) {
        try {
            SparepartDaoOpe.create(sparepart);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新sparepart
     *
     * @param sparepart
     */
    public void update(SPAREPART sparepart) {
        try {
            SparepartDaoOpe.update(sparepart);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新spareparts
     *
     * @param list
     */
    public void update(final ArrayList<SPAREPART> list) {
        try {
            SparepartDaoOpe.callBatchTasks(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for (SPAREPART sparepart : list) {
                        if (!isexitByNum(sparepart.getITEMNUM())) {
                            SparepartDaoOpe.create(sparepart);
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
     * 根据itemnum查询SPAREPART表是否有该人员
     *
     * @param itemnum
     * @return
     */
    public boolean isexitByNum(String itemnum) {
        try {
            List<SPAREPART> orderMainList = SparepartDaoOpe.queryBuilder().where().eq("ITEMNUM", itemnum).query();
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
     * 根据AssetNum查询Sparepart表的数据
     **/
    public List<SPAREPART> findByAssetNums(String assetNum) {
        try {
            return SparepartDaoOpe.queryBuilder().where().eq("ASSETNUM", assetNum).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 根据AssetNum与ITEMNUM查询Sparepart表的数据
     **/
    public List<SPAREPART> findByAssetNumAndItemNums(String assetNum,String itemnum) {
        try {
            return SparepartDaoOpe.queryBuilder().where().eq("ASSETNUM", assetNum).and().like("ITEMNUM","%"+itemnum+"%").query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
