package com.dfrc.hxqh.dfrc_project.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.api.JsonUtils;
import com.dfrc.hxqh.dfrc_project.constants.Constants;
import com.dfrc.hxqh.dfrc_project.dao.WoTaskDao;
import com.dfrc.hxqh.dfrc_project.dao.WoTaskNgDao;
import com.dfrc.hxqh.dfrc_project.dao.WoTaskOKDao;
import com.dfrc.hxqh.dfrc_project.dao.WoTaskProDao;
import com.dfrc.hxqh.dfrc_project.dao.WorkOrderDao;
import com.dfrc.hxqh.dfrc_project.model.WORKORDER;
import com.dfrc.hxqh.dfrc_project.model.WOTASK;
import com.dfrc.hxqh.dfrc_project.model.WOTASKNG;
import com.dfrc.hxqh.dfrc_project.model.WOTASKOK;
import com.dfrc.hxqh.dfrc_project.model.WOTASKPRO;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.until.NetWorkHelper;
import com.dfrc.hxqh.dfrc_project.view.adapter.BaseQuickAdapter;
import com.dfrc.hxqh.dfrc_project.view.adapter.WorkOrderListAdapter;
import com.dfrc.hxqh.dfrc_project.view.widght.SwipeRefreshLayout;
import com.dfrc.hxqh.dfrc_project.webserviceclient.AndroidClientService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/15.
 * 本地任务
 */

public class WorkorderLocationActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private static final String TAG = "WorkorderLocationActivity";

    @Bind(R.id.title_name)
    TextView titleTextView; //标题
    LinearLayoutManager layoutManager;

    @Bind(R.id.recyclerView_id)
    RecyclerView recyclerView; //RecyclerView
    @Bind(R.id.have_not_data_id)
    LinearLayout nodatalayout; //暂无数据

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout refresh_layout;//界面刷新


    @Bind(R.id.all_btn_id)
    Button allBtn; //全选
    @Bind(R.id.upload_btn_id)
    Button uploadBtn; //上传
    @Bind(R.id.delete_btn_id)
    Button deleteBtn; //删除

    /**
     * 适配器*
     */
    private WorkOrderListAdapter workOrderListAdapter;

    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;


    ArrayList<WORKORDER> items = new ArrayList<WORKORDER>();

    private ArrayList<WORKORDER> chooseWorkOrderList = new ArrayList<WORKORDER>(); //选中的记录

    private WorkOrderDao workOrderDao;
    private WoTaskDao woTaskDao;
    private WoTaskOKDao woTaskOKDao;
    private WoTaskNgDao woTaskNgDao;
    private WoTaskProDao woTaskProDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locworkorder_list);
        ButterKnife.bind(this);
        initDao();
        findViewById();
        initView();
    }

    /**
     * 初始化DAO
     **/
    private void initDao() {
        workOrderDao = new WorkOrderDao(this);
        woTaskDao = new WoTaskDao(this);
        woTaskOKDao = new WoTaskOKDao(this);
        woTaskNgDao = new WoTaskNgDao(this);
        woTaskProDao = new WoTaskProDao(this);
    }


    @Override
    protected void findViewById() {

    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.ddjgd_text);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        refresh_layout.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refresh_layout.setRefreshing(true);

        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setOnLoadListener(this);
        refresh_layout.setRefreshing(true);
        initAdapter(new ArrayList<WORKORDER>());
        items = new ArrayList<>();
        getData();

    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageViewOnClickListener() {
        finish();
    }


    //全选
    @OnClick(R.id.all_btn_id)
    void setAllBtnOnClickListener() {
        if (allBtn.getText().toString().equals("全选")) {
            workOrderListAdapter.setAll(true);
            workOrderListAdapter.notifyDataSetChanged();
            allBtn.setText("全不选");
        } else if (allBtn.getText().toString().equals("全不选")) {
            workOrderListAdapter.setAll(false);
            workOrderListAdapter.notifyDataSetChanged();
            allBtn.setText("全选");
        }

    }

    //上传
    @OnClick(R.id.upload_btn_id)
    void setUploadBtnOnClickListener() {
        if (null == chooseWorkOrderList || chooseWorkOrderList.size() == 0) {
            MessageUtils.showMiddleToast(WorkorderLocationActivity.this, getString(R.string.please_upload_data_text));
        } else if(NetWorkHelper.isWifi(WorkorderLocationActivity.this)){  //有Wifi网络
            alerDialog();
        } else if(NetWorkHelper.isWifi(WorkorderLocationActivity.this)) {
            MessageUtils.showMiddleToast(WorkorderLocationActivity.this, getString(R.string.not_upload_data));
        }



    }

    //删除
    @OnClick(R.id.delete_btn_id)
    void setDeleteBtnOnClickListener() {
        if (null == chooseWorkOrderList || chooseWorkOrderList.size() == 0) {
            MessageUtils.showMiddleToast(WorkorderLocationActivity.this, getString(R.string.please_upload_data_text));
        } else {
            deleteAlerDialog();

        }
    }


    @Override
    public void onLoad() {
        refresh_layout.setLoading(false);

    }

    @Override
    public void onRefresh() {
        refresh_layout.setRefreshing(false);
    }


    /**
     * 获取数据*
     */
    private void getData() {


        List<WORKORDER> workorderList = workOrderDao.queryForByCrewId("MAINT", "MAINT", AccountUtils.getCrewid(WorkorderLocationActivity.this));
        if (workorderList != null || workorderList.size() != 0) {
            addData(workorderList);
            refresh_layout.setRefreshing(false);
            refresh_layout.setLoading(false);
            nodatalayout.setVisibility(View.GONE);
        } else {
            refresh_layout.setRefreshing(false);
            nodatalayout.setVisibility(View.VISIBLE);
        }

    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<WORKORDER> list) {
        workOrderListAdapter = new WorkOrderListAdapter(WorkorderLocationActivity.this, R.layout.list_item_locworkorder, list);
        recyclerView.setAdapter(workOrderListAdapter);
        workOrderListAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = getIntent();
                intent.setClass(WorkorderLocationActivity.this, WorkOrderDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("workorder", (Serializable) workOrderListAdapter.getData().get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
        workOrderListAdapter.setOnCheckedChangeListener(new WorkOrderListAdapter.OnCheckedChangeListener() {
            @Override
            public void cOnCheckedChangeListener(boolean b, int postion, WORKORDER item) {
                if (b) {
                    chooseWorkOrderList.add(item);
                } else {
                    chooseWorkOrderList.remove(item);
                }
            }
        });


    }

    /**
     * 添加数据*
     */
    private void addData(final List<WORKORDER> list) {
        workOrderListAdapter.addData(list);
        workOrderListAdapter.notifyDataSetChanged();
    }


    /**
     * 上传弹出框*
     */
    private void alerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WorkorderLocationActivity.this);
        builder.setMessage("已选择" + chooseWorkOrderList.size() + "条记录，确定上传吗？").setTitle("提示")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                getLoadingDialog("正在提交...").show();
                findWotaskOk();
                submitData();


            }
        }).create().show();
    }

    /**
     * 删除弹出框*
     */
    private void deleteAlerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WorkorderLocationActivity.this);
        builder.setMessage("已选择" + chooseWorkOrderList.size() + "条记录，确定删除吗？").setTitle("提示")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                getLoadingDialog("正在提交...").show();
                findWotaskOk();
                deleteData();

            }
        }).create().show();
    }

    /**
     * 获取需要提交的数据
     **/
    private void submitData() {
        saveAsyncTask();
        startAsyncTaskOK();
        startAsyncTaskNG();
        startAsyncTaskPRO();
    }


    /**
     * 删除选中的记录j
     **/
    private void deleteData() {
        int deWorkOrder = workOrderDao.deleteByWorkorders(chooseWorkOrderList);
        int deTask = woTaskDao.deleteByWotasks(wotasks);
        int deOk = woTaskOKDao.deleteByWotaskoks(wotaskoks);
        int deNG = woTaskNgDao.deleteByWotaskngs(wotaskngs);
        int dePRO = woTaskProDao.deleteByWotaskpros(wotaskpros);
        colseProgressBar();
        if (deWorkOrder != 0 || deTask != 0 || deOk != 0 || deNG != 1 || dePRO != 0) {
            MessageUtils.showMiddleToast(WorkorderLocationActivity.this, "删除成功");
        } else {
            MessageUtils.showMiddleToast(WorkorderLocationActivity.this, "删除失败");
        }
        initAdapter(new ArrayList<WORKORDER>());
        getData();
    }


    private List<WOTASK> wotasks = new ArrayList<WOTASK>(); //选中的WOTASK选项
    private List<WOTASKOK> wotaskoks = new ArrayList<WOTASKOK>(); //需要提交的OK选项
    private List<WOTASKNG> wotaskngs = new ArrayList<WOTASKNG>(); //需要提交的NG选项
    private List<WOTASKPRO> wotaskpros = new ArrayList<WOTASKPRO>(); //需要提交的PRO选项

    //根据编号获取选中的子表数据
    private void findWotaskOk() {
        for (WORKORDER workorder : chooseWorkOrderList) {
            String wonum = workorder.getWONUM();
            List<WOTASK> wotasklist = woTaskDao.findByWonum(wonum);
            if (null != wotasklist && wotasklist.size() != 0) {
                for (WOTASK wotask : wotasklist) {
                    wotasks.add(wotask);
                }
            }
            List<WOTASKOK> wotaskoklist = woTaskOKDao.findByWonum(wonum);
            if (null != wotaskoklist && wotaskoklist.size() != 0) {
                for (WOTASKOK wotaskok : wotaskoklist) {
                    wotaskoks.add(wotaskok);
                }
            }
            List<WOTASKNG> wotasknglist = woTaskNgDao.findByWonum(wonum);
            if (null != wotasknglist && wotasknglist.size() != 0) {
                for (WOTASKNG wotaskng : wotasknglist) {
                    wotaskngs.add(wotaskng);
                }
            }
            List<WOTASKPRO> wotaskprolist = woTaskProDao.findByWonum(wonum);
            if (null != wotaskprolist && wotaskprolist.size() != 0) {
                for (WOTASKPRO wotaskpro : wotaskprolist) {
                    wotaskpros.add(wotaskpro);
                }
            }
        }

    }

    private List<WOTASK> updateWotasks = new ArrayList<WOTASK>(); //本地修改过的WoTask

    /**
     * 根据wonum查询本地修改过的Wotask
     **/
    private List<WOTASK> updaeWoTasks() {
        for (WORKORDER workorder : chooseWorkOrderList) {
            String wonum = workorder.getWONUM();
            List<WOTASK> wotasklist = woTaskDao.findByWonumAndUpdate(wonum, 1);
            if (null != wotasklist && wotasklist.size() != 0) {
                for (WOTASK wotask : wotasklist) {
                    updateWotasks.add(wotask);
                }
            }
        }
        return updateWotasks;
    }


    /**
     * 提交保存数据Wotask
     **/

    private void saveAsyncTask() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                List<WOTASK> list = updaeWoTasks();
                if (null != list && list.size() != 0) {
                    for (WOTASK wotask : list) {
                        String relut=AndroidClientService.UpdateMbo(WorkorderLocationActivity.this, JsonUtils.potoWOTASK(wotask), Constants.WOTASK_NAME, "WOTASKID", wotask.getWOTASKID());
                    }
                }

                return "提交成功";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


            }
        }.execute();


    }


    /**
     * 提交数据OK*
     */
    private void startAsyncTaskOK() {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                for (WOTASKOK wotaskok : wotaskoks) {
                    String relut = AndroidClientService.MaintWOIsOk(WorkorderLocationActivity.this, wotaskok);

                }
                return "提交成功";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                woTaskOKDao.deleteByWotaskoks(wotaskoks);
                MessageUtils.showMiddleToast(WorkorderLocationActivity.this, s);

            }
        }.execute();


    }


    /**
     * 提交数据NG*
     */
    private void startAsyncTaskNG() {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                for (WOTASKNG wotaskng : wotaskngs) {
                    AndroidClientService.MaintWOIsNo(WorkorderLocationActivity.this, wotaskng);
                }
                return "提交成功";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                woTaskNgDao.deleteByWotaskngs(wotaskngs);
            }
        }.execute();


    }

    /**
     * 提交数据PRO*
     */
    private void startAsyncTaskPRO() {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                for (WOTASKPRO wotaskpro : wotaskpros) {
                    String relut = AndroidClientService.MaintWOPro(WorkorderLocationActivity.this, wotaskpro);
                }
                return "提交成功";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                woTaskProDao.deleteByWotaskpros(wotaskpros);

            }
        }.execute();


    }


}
