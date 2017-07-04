package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.api.HttpManager;
import com.dfrc.hxqh.dfrc_project.api.HttpRequestHandler;
import com.dfrc.hxqh.dfrc_project.api.JsonUtils;
import com.dfrc.hxqh.dfrc_project.bean.Results;
import com.dfrc.hxqh.dfrc_project.dao.WoTaskDao;
import com.dfrc.hxqh.dfrc_project.dao.WorkOrderDao;
import com.dfrc.hxqh.dfrc_project.model.WORKORDER;
import com.dfrc.hxqh.dfrc_project.model.WOTASK;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.until.NetWorkHelper;
import com.dfrc.hxqh.dfrc_project.view.adapter.BaseQuickAdapter;
import com.dfrc.hxqh.dfrc_project.view.adapter.WorkDownListAdapter;
import com.dfrc.hxqh.dfrc_project.view.widght.SwipeRefreshLayout;
import com.dfrc.hxqh.dfrc_project.webserviceclient.AndroidClientService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by Administrator on 2017/2/15.
 * 定期点检工单
 */

public class WorkorderActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private static final String TAG = "WorkorderActivity";

    @Bind(R.id.title_name)
    TextView titleTextView; //标题
    LinearLayoutManager layoutManager;

    @Bind(R.id.recyclerView_id)
    RecyclerView recyclerView; //RecyclerView
    @Bind(R.id.have_not_data_id)
    LinearLayout nodatalayout; //暂无数据

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout refresh_layout;//界面刷新
    /**
     * 适配器*
     */
    private WorkDownListAdapter workDownListAdapter;

    @Bind(R.id.edt_input)
    EditText search; //编辑框
    @Bind(R.id.btn_delete)
    Button deleteBtn; //删除

    @Bind(R.id.down_btn_id)
    Button downBtn; //已下载任务
    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;


    ArrayList<WORKORDER> items = new ArrayList<WORKORDER>();
    private boolean isCodePda; //判断是扫描还是手输

    private String assetNum; //设备编号

    private WorkOrderDao workOrderDao;
    private WoTaskDao woTaskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_list);
        ButterKnife.bind(this);
        initDAO();
        findViewById();
        initView();
    }

    //初始化DAO
    private void initDAO() {
        workOrderDao = new WorkOrderDao(this);
        woTaskDao = new WoTaskDao(this);
    }


    @Override
    protected void findViewById() {

    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.ddjgd_text);
        setSearchEdit();

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
        if (!NetWorkHelper.isWifi(WorkorderActivity.this) && AccountUtils.getIsOffLine(this)) {
            MessageUtils.showMiddleToast(WorkorderActivity.this, "暂无Wifi网络,请进行离线操作");
            nodatalayout.setVisibility(View.VISIBLE);
            refresh_layout.setRefreshing(false);
            refresh_layout.setLoading(false);
        } else {
            getData(searchText);
        }


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageViewOnClickListener() {
        finish();
    }

    @OnTextChanged(value = R.id.edt_input, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @OnTextChanged(value = R.id.edt_input, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        if (start == 0 && before == 0 && count > 1) {
            //扫描
            isCodePda = true;
        } else {
            //手输
            isCodePda = false;
        }
    }

    @OnTextChanged(value = R.id.edt_input, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged(Editable s) {
        if (s.length() > 0) {
            deleteBtn.setVisibility(View.VISIBLE);
        } else {
            deleteBtn.setVisibility(View.GONE);
        }

        if (isCodePda) {
            assetNum = parsingResult(s.toString());
            workDownListAdapter.removeAll(items);
            items = new ArrayList<WORKORDER>();
            nodatalayout.setVisibility(View.GONE);
            refresh_layout.setRefreshing(true);
            page = 1;
            startAsyncTask();
        }
    }


    //删除
    @OnClick(R.id.btn_delete)
    void setDeleteBtnOnClickListener() {
        search.setText("");
    }

    //已下载任务
    @OnClick(R.id.down_btn_id)
    void setDownBtnOnClickListener() {
        Intent intent = new Intent(WorkorderActivity.this, WorkorderLocationActivity.class);
        startActivityForResult(intent, 0);
    }


    @Override
    public void onLoad() {
        if (!isCodePda) {
            page++;
            getData(searchText);
        } else {
            refresh_layout.setLoading(false);
        }

    }

    @Override
    public void onRefresh() {
        if (!isCodePda) {
            page = 1;
            getData(searchText);
        } else {
            refresh_layout.setRefreshing(false);
        }
    }


    private void setSearchEdit() {
        SpannableString msp = new SpannableString("XX搜索");
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_search);
        msp.setSpan(new ImageSpan(drawable), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        search.setHint(msp);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    WorkorderActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    workDownListAdapter.removeAll(workDownListAdapter.getData());
                    items = new ArrayList<WORKORDER>();
                    nodatalayout.setVisibility(View.GONE);
                    refresh_layout.setRefreshing(true);
                    page = 1;
                    getData(searchText);
                    return true;
                }
                return false;
            }
        });
    }


    /**
     * 获取数据*
     */
    private void getData(String search) {


        HttpManager.getDataPagingInfo(WorkorderActivity.this, HttpManager.getWORKORDERURL(search, AccountUtils.getCrewid(WorkorderActivity.this), AccountUtils.getloginSite(WorkorderActivity.this), page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<WORKORDER> item = JsonUtils.parsingWORKORDER(results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        for (int i = 0; i < item.size(); i++) {
                            String wonum = item.get(i).getWONUM();
                            boolean is = workOrderDao.isexitByNum(wonum);
                            if (is) {
                                item.get(i).setDOWNSTATUS("已下载");
                            }
                        }


                        if (page == 1) {
                            items = new ArrayList<WORKORDER>();
                            initAdapter(items);
                        }
                        if (page > totalPages) {
                            MessageUtils.showMiddleToast(WorkorderActivity.this, "已加载出全部数据");
                        } else {
                            addData(item);
                        }

                    }
                    nodatalayout.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(String error) {
                refresh_layout.setRefreshing(false);
                nodatalayout.setVisibility(View.VISIBLE);
            }
        });

    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<WORKORDER> list) {
        workDownListAdapter = new WorkDownListAdapter(WorkorderActivity.this, R.layout.list_item_workdown, list);
        recyclerView.setAdapter(workDownListAdapter);

        //点击下载按钮
        workDownListAdapter.setDownOnClickListener(new WorkDownListAdapter.DownOnClickListener() {
            @Override
            public void cDownOnClickListener(int postion, TextView statusText, View pb) {
                WORKORDER workorder = (WORKORDER) workDownListAdapter.getData().get(postion);
                if(workorder.getSTATUS().equals("进行中")) {
                    workOrderDao.update(workorder);
                    statusText.setVisibility(View.GONE);
                    pb.setVisibility(View.VISIBLE);
                    getItemData(postion, workorder, workorder.getWONUM(), statusText, pb);
                }else{
                    MessageUtils.showMiddleToast(WorkorderActivity.this,"只能下载状态为进行中的点检工单,该状态下不能下载");

                }

            }


        });

        workDownListAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = getIntent();
                intent.setClass(WorkorderActivity.this, WorkOrderDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("workorder", (Serializable) workDownListAdapter.getData().get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });

    }

    /**
     * 添加数据*
     */
    private void addData(final List<WORKORDER> list) {
        workDownListAdapter.addData(list);
    }


    /**
     * 根据设备查询编号*
     */
    private void startAsyncTask() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
//                return AndroidClientService.searchMaint2(WorkorderActivity.this, "ASSETNUM", assetNum, "line"); //根据设备获取定期点检工单
                return AndroidClientService.searchMaint2(WorkorderActivity.this, "ASSETNUM","H2-Z2-CV-063", "line"); //根据设备获取定期点检工单
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                String result = JsonUtils.parsingsearchMaint2(s);
                if (result.equals("暂无数据")) {
                } else {
                    ArrayList<WORKORDER> item = JsonUtils.parsingWORKORDER(s);

                    if (item == null || item.isEmpty()) {
                        nodatalayout.setVisibility(View.VISIBLE);
                    } else {
                        workDownListAdapter.removeAll(workDownListAdapter.getData());
                        addData(item);
                    }
                }
            }
        }.execute();


    }


    /**
     * 根据编号获取子表信息
     **/
    private void getItemData(final int postion, final WORKORDER workorder, final String wonum, final TextView statusText, final View pb) {
//        HttpManager.getDataPagingInfo(WorkorderActivity.this, HttpManager.getWOTASKURL(wonum, page, 10000), new HttpRequestHandler<Results>() {
//            @Override
//            public void onSuccess(Results results) {
//            }
//
//            @Override
//            public void onSuccess(Results results, int totalPages, int currentPage) {
//                ArrayList<WOTASK> item = JsonUtils.parsingWOTASK(results.getResultlist());
//
//                if (item == null || item.isEmpty()) {
//                } else {
//                    woTaskDao.update(item);
//                }
//                workorder.setDOWNSTATUS("已下载");
//                WORKORDER w = (WORKORDER) workDownListAdapter.getData().get(postion);
//                workDownListAdapter.remove(postion);
//                workDownListAdapter.add(postion, workorder);
//                statusText.setText(R.string.down_success_text);
//                statusText.setTextColor(getResources().getColor(R.color.red));
//                statusText.setVisibility(View.VISIBLE);
//                pb.setVisibility(View.GONE);
//                workDownListAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(String error) {
//                workorder.setDOWNSTATUS("下载失败");
//                WORKORDER w = (WORKORDER) workDownListAdapter.getData().get(postion);
//                workDownListAdapter.remove(postion);
//                workDownListAdapter.add(postion, workorder);
//                statusText.setText(R.string.down_fail_text);
//                statusText.setTextColor(getResources().getColor(R.color.red));
//                statusText.setVisibility(View.VISIBLE);
//                pb.setVisibility(View.GONE);
//                workDownListAdapter.notifyDataSetChanged();
//            }
//        });



        HttpManager.getData(WorkorderActivity.this, HttpManager.getWOTASKURL(wonum), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<WOTASK> item = JsonUtils.parsingWOTASK(results.getResultlist());

                if (item == null || item.isEmpty()) {
                } else {
                    woTaskDao.update(item);
                }
                workorder.setDOWNSTATUS("已下载");
                WORKORDER w = (WORKORDER) workDownListAdapter.getData().get(postion);
                workDownListAdapter.remove(postion);
                workDownListAdapter.add(postion, workorder);
                statusText.setText(R.string.down_success_text);
                statusText.setTextColor(getResources().getColor(R.color.red));
                statusText.setVisibility(View.VISIBLE);
                pb.setVisibility(View.GONE);
                workDownListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String error) {
                workorder.setDOWNSTATUS("下载失败");
                WORKORDER w = (WORKORDER) workDownListAdapter.getData().get(postion);
                workDownListAdapter.remove(postion);
                workDownListAdapter.add(postion, workorder);
                statusText.setText(R.string.down_fail_text);
                statusText.setTextColor(getResources().getColor(R.color.red));
                statusText.setVisibility(View.VISIBLE);
                pb.setVisibility(View.GONE);
                workDownListAdapter.notifyDataSetChanged();
            }
        });
    }


}
