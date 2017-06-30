package com.dfrc.hxqh.dfrc_project.dao;

import android.content.Context;

import com.dfrc.hxqh.dfrc_project.model.PERSON;
import com.dfrc.hxqh.dfrc_project.ormLiteOpenHelper.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by think on 2016/7/12.
 * 人员表
 */
public class PersonDao {
    private static final String TAG = "PersonDao";
    private Context context;
    private Dao<PERSON, Integer> PersonDaoOpe;
    private DatabaseHelper helper;

    public PersonDao(Context context) {
        this.context = context;
        try {
            helper = DatabaseHelper.getHelper(context);
            PersonDaoOpe = helper.getDao(PERSON.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建person
     *
     * @param person
     */
    public void create(PERSON person) {
        try {
            PersonDaoOpe.create(person);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新person
     *
     * @param person
     */
    public void update(PERSON person) {
        try {
            PersonDaoOpe.update(person);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新persons
     *
     * @param list
     */
    public void update(final ArrayList<PERSON> list) {
        try {
            PersonDaoOpe.callBatchTasks(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for (PERSON person : list) {
                        if (!isexitByNum(person.getPERSONID())) {
                            PersonDaoOpe.create(person);
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
     * 根据PERSONNAME查询PERSON表是否有该人员
     *
     * @param personid
     * @return
     */
    public boolean isexitByNum(String personid) {
        try {
            List<PERSON> orderMainList = PersonDaoOpe.queryBuilder().where().eq("PERSONID", personid).query();
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
     * 根据班组查询PERSION数据
     *
     * @param crewid
     * @param locationsite
     **/

    public List<PERSON> findByCrewid(String crewid,String locationsite) {
        try {
            return PersonDaoOpe.queryBuilder().where().eq("N_CREWID", crewid).and().eq("LOCATIONSITE",locationsite).query();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 根据PERSONID与DISPLAYNAME查询PERSION信息
     *
     * @param search
     * @param locationsite
     **/
    public List<PERSON> findByPersionIdAndDisplayNames(String search,String locationsite) {
        try {
            return PersonDaoOpe.queryBuilder().where().eq("LOCATIONSITE",locationsite).and().like("PERSONID", "%"+search+"%").or().like("DISPLAYNAME", "%"+search+"%").query();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
