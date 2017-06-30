package com.dfrc.hxqh.dfrc_project.dao;

import android.content.Context;

import com.dfrc.hxqh.dfrc_project.model.ASSET;
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
public class AssetDao {
    private static final String TAG = "AssetDao";
    private Context context;
    private Dao<ASSET, Integer> AssetDaoOpe;
    private DatabaseHelper helper;

    public AssetDao(Context context) {
        this.context = context;
        try {
            helper = DatabaseHelper.getHelper(context);
            AssetDaoOpe = helper.getDao(ASSET.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建asset
     *
     * @param asset
     */
    public void create(ASSET asset) {
        try {
            AssetDaoOpe.create(asset);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新asset
     *
     * @param asset
     */
    public void update(ASSET asset) {
        try {
            AssetDaoOpe.update(asset);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新assets
     *
     * @param list
     */
    public void update(final ArrayList<ASSET> list) {
        try {
            AssetDaoOpe.callBatchTasks(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for (ASSET asset : list) {
                        if (!isexitByNum(asset.getASSETNUM())) {
                            AssetDaoOpe.create(asset);
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
     * 更新assets
     *
     * @param list
     */
    public void addAssets(final ArrayList<ASSET> list) {
        try {

           AssetDaoOpe.callBatchTasks(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for (ASSET asset : list) {
                        if (!isexitByNum(asset.getASSETNUM())) {
                            AssetDaoOpe.createIfNotExists(asset);
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
     * 根据ASSETNAME查询ASSET表是否有该人员
     *
     * @param assetname
     * @return
     */
    public boolean isexitByNum(String assetname) {
        try {
            List<ASSET> orderMainList = AssetDaoOpe.queryBuilder().where().eq("ASSETNUM", assetname).query();
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
     * 根据siteid查询ASSET的信息
     *
     * @param siteid
     **/
    public List<ASSET> findBySiteds(String siteid) {
        try {
            return AssetDaoOpe.queryBuilder().where().eq("SITEID", siteid).query();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
