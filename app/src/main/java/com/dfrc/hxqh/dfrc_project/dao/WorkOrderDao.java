package com.dfrc.hxqh.dfrc_project.dao;

import android.content.Context;

import com.dfrc.hxqh.dfrc_project.model.WORKORDER;
import com.dfrc.hxqh.dfrc_project.ormLiteOpenHelper.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by think on 2016/7/12.
 * 定期点检工单
 */
public class WorkOrderDao {
    private static final String TAG = "WorkOrderDao";
    private Context context;
    private Dao<WORKORDER, Integer> WorkOrderDaoOpe;
    private DatabaseHelper helper;

    public WorkOrderDao(Context context) {
        this.context = context;
        try {
            helper = DatabaseHelper.getHelper(context);
            WorkOrderDaoOpe = helper.getDao(WORKORDER.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新工单
     *
     * @param workOrder
     */
    public void update(WORKORDER workOrder) {
        try {
            if (!isexitByNum(workOrder.WONUM)) {
                WorkOrderDaoOpe.createOrUpdate(workOrder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新工单信息
     *
     * @param list
     */
    public void update(final ArrayList<WORKORDER> list) {
        try {
            WorkOrderDaoOpe.callBatchTasks(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for (WORKORDER workOrder : list) {
                        if (!isexitByNum(workOrder.WONUM)) {
                            WorkOrderDaoOpe.createOrUpdate(workOrder);
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
     * 根据当前WORKTYPE,WKTYPE,CREWID班组查询相应工单
     * WORKTYPE
     * WKTYPE
     * CREWID
     *
     * @return
     */
    public List<WORKORDER> queryForByCrewId(String worktype, String wktype, String crewid) {
        try {
            return WorkOrderDaoOpe.queryBuilder().orderBy("WONUM", false)
                    .where().eq("WORKTYPE", worktype).and().eq("WKTYPE", wktype)
                    .and().eq("CREWID", crewid).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 按照工单查询本地是否存在此工单
     *
     * @param num
     * @return
     */
    public boolean isexitByNum(String num) {
        try {
            List<WORKORDER> orderMainList = WorkOrderDaoOpe.queryBuilder().where().eq("WONUM", num).query();
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
     * 删除选中的记录
     *
     * @workorders
     * 1 表示删除成功
     * 0 表示删除失败
     **/
    public int deleteByWorkorders(List<WORKORDER> workorders) {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        int deletemark=0;
        try {
            for (WORKORDER workorder : workorders) {
                ids.add(workorder.getId());
            }
            deletemark= WorkOrderDaoOpe.deleteIds(ids);
        } catch (SQLException e) {
            return deletemark;
        }
        return deletemark;
    }


}
