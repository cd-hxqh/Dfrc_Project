package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Context;
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
import com.dfrc.hxqh.dfrc_project.model.N_MATERIAL;
import com.dfrc.hxqh.dfrc_project.model.ZKWORKORDER;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.view.adapter.N_materialListAdapter;
import com.dfrc.hxqh.dfrc_project.view.widght.SwipeRefreshLayout;
import com.dfrc.hxqh.dfrc_project.webserviceclient.AndroidClientService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by Administrator on 2017/2/15.
 * 申请领用物料明细
 */

public class N_materialActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private static final String TAG = "N_materialActivity";
    private static final int N_MATERIAL_CODE = 1005;
    @Bind(R.id.title_name) //标题
            TextView titleTextView;
    LinearLayoutManager layoutManager;

    @Bind(R.id.recyclerView_id)//RecyclerView
            RecyclerView recyclerView;
    @Bind(R.id.have_not_data_id)
    LinearLayout nodatalayout; //暂无数据
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout refresh_layout;//界面刷新

    @Bind(R.id.edt_input)
    EditText search; //编辑框
    @Bind(R.id.btn_delete)
    Button deleteBtn; //删除

    @Bind(R.id.sureff_btn_id)
    Button sureBtn; //确认发放


    /**
     * 适配器*
     */
    private N_materialListAdapter n_materialListAdapter;
    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;


    ArrayList<N_MATERIAL> items = new ArrayList<N_MATERIAL>();

    ArrayList<N_MATERIAL> chooseItems = new ArrayList<N_MATERIAL>(); //修改的记录

    private String wonum;
    private ZKWORKORDER zkworkorder;


    private boolean isCodePda; //判断是扫描还是手输
    private String itemnum; //物料编码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_list);
        ButterKnife.bind(this);
        initDate();
        findViewById();
        initView();
    }

    private void initDate() {
        wonum = getIntent().getExtras().getString("wonum");
        zkworkorder = (ZKWORKORDER) getIntent().getSerializableExtra("zkworkorder");

    }


    @Override
    protected void findViewById() {
    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.sqlywlmx_text);
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
        initAdapter(new ArrayList<N_MATERIAL>());
        items = new ArrayList<>();
        getData(searchText);
    }


    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackOnClickListener() {
        finish();
    }


    @OnTextChanged(value = R.id.edt_input, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    void beforeTextChanged(CharSequence s, int start, int before, int count) {
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
            itemnum = parsingResult(s.toString());
            n_materialListAdapter.removeAll(items);
            items = new ArrayList<N_MATERIAL>();
            nodatalayout.setVisibility(View.GONE);
            refresh_layout.setRefreshing(true);
            page = 1;
            getData(parsingResult(s.toString()));
        }
    }

    //删除
    @OnClick(R.id.btn_delete)
    void setDeleteBtnOnClickListener() {
        search.setText("");
    }


    //确认发放
    @OnClick(R.id.sureff_btn_id)
    void setSureffBtnOnClickListener() {
        getLoadingDialog("正在提交...").show();
        updateAsyncTask();

    }


    @Override
    public void onLoad() {
        page++;
        getData(searchText);
    }

    @Override
    public void onRefresh() {
        page = 1;
        getData(searchText);
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
                                    N_materialActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    n_materialListAdapter.removeAll(items);
                    items = new ArrayList<N_MATERIAL>();
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
        String url = null;
        if (isCodePda && itemnum != null) {
            url = HttpManager.getN_MATERIAL1(itemnum, wonum, page, 20);
        } else {
            url = HttpManager.getN_MATERIAL(search, wonum, page, 20);
        }

        HttpManager.getDataPagingInfo(N_materialActivity.this, url, new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<N_MATERIAL> item = JsonUtils.parsingN_MATERIAL(results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {

                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<N_MATERIAL>();
                            initAdapter(items);
                        }
                        if (page > totalPages) {
                            MessageUtils.showMiddleToast(N_materialActivity.this, getString(R.string.have_load_out_all_the_data));
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
    private void initAdapter(final List<N_MATERIAL> list) {
        n_materialListAdapter = new N_materialListAdapter(N_materialActivity.this, R.layout.list_item_n_material, list);
        recyclerView.setAdapter(n_materialListAdapter);
        n_materialListAdapter.setOnclicklistener(new N_materialListAdapter.OnClickListener() {
            @Override
            public void cOnClickListener(int postion, String t) {
                N_MATERIAL n_material = (N_MATERIAL) n_materialListAdapter.getData().get(postion);
                n_material.setN_SAP3(t);
            }
        });
        if (n_materialListAdapter.getItemCount() == 0) {
            sureBtn.setVisibility(View.GONE);
        } else {
            sureBtn.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 添加数据*
     */
    private void addData(final List<N_MATERIAL> list) {
        n_materialListAdapter.addData(list);
    }


    /**
     * 修改数据
     */
    private void updateAsyncTask() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                List<N_MATERIAL> list = n_materialListAdapter.getData();
                for (N_MATERIAL item : list) {
                    AndroidClientService.UpdateMbo(N_materialActivity.this, JsonUtils.potoN_MATERIAL(item.getN_SAP3()), "N_MATERIAL", "N_MATERIALID", item.getN_MATERIALID());
                }
                if (list.size() == n_materialListAdapter.getItemCount()) {
                    return "提交成功";
                }
                return "提交失败";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.i(TAG, "s=" + s);
                QueRenAsyncTask();


            }
        }.execute();


    }

    //确认发放
    private void QueRenAsyncTask() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                if (n_materialListAdapter.getItemCount() == 1) {
                    N_MATERIAL n_material = (N_MATERIAL) n_materialListAdapter.getData().get(0);
                    return AndroidClientService.INV03Issue(N_materialActivity.this, AccountUtils.getloginUserName(N_materialActivity.this), wonum, n_material.getITEMNUM(), zkworkorder.getN_SAP1(), zkworkorder.getCREWID(), zkworkorder.getSITEID());
                }
                return AndroidClientService.INV03Issue(N_materialActivity.this, AccountUtils.getloginUserName(N_materialActivity.this), wonum, "", zkworkorder.getN_SAP1(), zkworkorder.getCREWID(), zkworkorder.getSITEID());
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.i(TAG, "s=" + s);
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(N_materialActivity.this, s);


            }
        }.execute();


    }




}
