package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.api.HttpManager;
import com.dfrc.hxqh.dfrc_project.api.HttpRequestHandler;
import com.dfrc.hxqh.dfrc_project.api.JsonUtils;
import com.dfrc.hxqh.dfrc_project.bean.Results;
import com.dfrc.hxqh.dfrc_project.dialog.FlippingLoadingDialog;
import com.dfrc.hxqh.dfrc_project.model.UDCANRTN;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.view.adapter.BaseQuickAdapter;
import com.dfrc.hxqh.dfrc_project.view.adapter.UdcanrtnChooseListAdapter;
import com.dfrc.hxqh.dfrc_project.view.widght.SwipeRefreshLayout;
import com.dfrc.hxqh.dfrc_project.webserviceclient.AndroidClientService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/15.
 * 物料退回
 */

public class UdcanrtnActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private static final String TAG = "UdcanrtnActivity";

    @Bind(R.id.title_name) //标题
            TextView titleTextView;
    @Bind(R.id.sbmittext_id)
    ImageButton sbmitImageButton; //提交


    LinearLayoutManager layoutManager;

    @Bind(R.id.recyclerView_id)//RecyclerView
            RecyclerView recyclerView;
    @Bind(R.id.have_not_data_id)
    LinearLayout nodatalayout; //暂无数据
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout refresh_layout;//界面刷新

    @Bind(R.id.search_edit)
    EditText search;//编辑框
    /**
     * 适配器*
     */
    private UdcanrtnChooseListAdapter udcanrtnChooseListAdapter;
    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;


    ArrayList<UDCANRTN> items = new ArrayList<UDCANRTN>();

    List<UDCANRTN> chooseItems = new ArrayList<UDCANRTN>();


    private String ponum; //采购单号

    protected FlippingLoadingDialog mLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        initData();
        findViewById();
        initView();
    }


    //接收上个界面的数量
    private void initData() {
        ponum = getIntent().getExtras().getString("ponum");
    }


    @Override
    protected void findViewById() {
    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.xzth_text);

        setSearchEdit();
        sbmitImageButton.setVisibility(View.VISIBLE);
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
        initAdapter(new ArrayList<UDCANRTN>());
        items = new ArrayList<>();
        getData(searchText);
    }


    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackOnClickListener() {
        finish();
    }

    @OnClick(R.id.sbmittext_id)
    void setSbmitImageButtonOnClickListener() {

        if (chooseItems.size() == 0) {
            MessageUtils.showMiddleToast(UdcanrtnActivity.this, "请选择物料");
        } else {
            getLoadingDialog("正在提交...").show();
            startAsyncTask();
        }
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
                                    UdcanrtnActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    udcanrtnChooseListAdapter.removeAll(items);
                    items = new ArrayList<UDCANRTN>();
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
        String url = HttpManager.getUDCANRTNURL(search, ponum, page, 20);

        HttpManager.getDataPagingInfo(UdcanrtnActivity.this, url, new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<UDCANRTN> item = JsonUtils.parsingUDCANRTN(results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {
                    if (page == 1) {
                        items = new ArrayList<UDCANRTN>();
                        initAdapter(items);
                    }
                    for (int i = 0; i < item.size(); i++) {
                        items.add(item.get(i));
                    }
                    addData(item);
                }
                nodatalayout.setVisibility(View.GONE);
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
    private void initAdapter(final List<UDCANRTN> list) {
        udcanrtnChooseListAdapter = new UdcanrtnChooseListAdapter(UdcanrtnActivity.this, R.layout.list_item_choose_udcanrtn, list);
        recyclerView.setAdapter(udcanrtnChooseListAdapter);
        udcanrtnChooseListAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
        udcanrtnChooseListAdapter.setOnCheckedChangeListener(new UdcanrtnChooseListAdapter.OnCheckedChangeListener() {
            @Override
            public void cOnCheckedChangeListener(boolean b, int postion, String t) {
                items.get(postion).setQUANTITY(t);
                if (b) {
                    chooseItems.add(items.get(postion));
                } else {
                    chooseItems.remove(items.get(postion));
                }
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<UDCANRTN> list) {
        udcanrtnChooseListAdapter.addData(list);
    }


    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }

    /**
     * 提交数据*
     */
    private void startAsyncTask() {
        if (chooseItems != null || chooseItems.size() != 0) {

            new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... strings) {

                    String result = null;
                    for (int i = 0; i < chooseItems.size(); i++) {
                        UDCANRTN udcanrtn = chooseItems.get(i);
                        result = AndroidClientService.INV02RecByPOLine1(UdcanrtnActivity.this, getUDCANRTN(udcanrtn));
                        if (i == chooseItems.size() - 1) {
                            return result;
                        }
                    }

                    return result;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    mLoadingDialog.dismiss();
                    MessageUtils.showMiddleToast(UdcanrtnActivity.this, s);
                    finish();

                }
            }.execute();
        }


    }


    /**
     * 封装新增的信息
     *
     * @param udcanrtn
     */
    private UDCANRTN getUDCANRTN(UDCANRTN udcanrtn) {
        UDCANRTN udcanrtn1 = new UDCANRTN();
        udcanrtn1.setPOLINENUM(udcanrtn.getPOLINENUM());
        udcanrtn1.setQUANTITY("-" + udcanrtn.getQUANTITY());
        udcanrtn1.setTOBIN("");
        udcanrtn1.setPONUM(ponum);
        udcanrtn1.setENTERBY(AccountUtils.getloginUserName(UdcanrtnActivity.this));
        return udcanrtn1;
    }


}
