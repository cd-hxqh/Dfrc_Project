package com.dfrc.hxqh.dfrc_project.ormLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dfrc.hxqh.dfrc_project.constants.Constants;
import com.dfrc.hxqh.dfrc_project.model.ASSET;
import com.dfrc.hxqh.dfrc_project.model.PERSON;
import com.dfrc.hxqh.dfrc_project.model.SPAREPART;
import com.dfrc.hxqh.dfrc_project.model.USER;
import com.dfrc.hxqh.dfrc_project.model.USERPERMISSIONS;
import com.dfrc.hxqh.dfrc_project.model.WORKORDER;
import com.dfrc.hxqh.dfrc_project.model.WOTASK;
import com.dfrc.hxqh.dfrc_project.model.WOTASKNG;
import com.dfrc.hxqh.dfrc_project.model.WOTASKOK;
import com.dfrc.hxqh.dfrc_project.model.WOTASKPRO;
import com.dfrc.hxqh.dfrc_project.until.Utils;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by think on 2016/7/12.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private Map<String, Dao> daos = new HashMap<String, Dao>();

    private DatabaseHelper(Context context) {
        super(context, Utils.getFilePath(context), null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database,
                         ConnectionSource connectionSource) {
        Log.i(TAG, "新建");
        try {
            TableUtils.createTable(connectionSource, USER.class);
            TableUtils.createTable(connectionSource, WORKORDER.class);
            TableUtils.createTable(connectionSource, WOTASK.class);
            TableUtils.createTable(connectionSource, WOTASKOK.class);
            TableUtils.createTable(connectionSource, WOTASKNG.class);
            TableUtils.createTable(connectionSource, WOTASKPRO.class);
            TableUtils.createTable(connectionSource, USERPERMISSIONS.class);
            TableUtils.createTable(connectionSource, PERSON.class);
            TableUtils.createTable(connectionSource, ASSET.class);
            TableUtils.createTable(connectionSource, SPAREPART.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(TAG, "更新");
            TableUtils.dropTable(connectionSource, USER.class, true);
            TableUtils.dropTable(connectionSource, WORKORDER.class, true);
            TableUtils.dropTable(connectionSource, WOTASK.class, true);
            TableUtils.dropTable(connectionSource, WOTASKOK.class, true);
            TableUtils.dropTable(connectionSource, WOTASKNG.class, true);
            TableUtils.dropTable(connectionSource, WOTASKPRO.class, true);
            TableUtils.dropTable(connectionSource, USERPERMISSIONS.class, true);
            TableUtils.dropTable(connectionSource, PERSON.class, true);
            TableUtils.dropTable(connectionSource, ASSET.class, true);
            TableUtils.dropTable(connectionSource, SPAREPART.class, true);

            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static DatabaseHelper instance;

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DatabaseHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null)
                    instance = new DatabaseHelper(context);
            }
        }

        return instance;
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            try {
                dao = super.getDao(clazz);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            daos.put(className, dao);
        }
        return dao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();

        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }

}
