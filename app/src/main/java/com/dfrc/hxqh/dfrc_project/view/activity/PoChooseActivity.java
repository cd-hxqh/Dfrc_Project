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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.api.HttpManager;
import com.dfrc.hxqh.dfrc_project.api.HttpRequestHandler;
import com.dfrc.hxqh.dfrc_project.api.JsonUtils;
import com.dfrc.hxqh.dfrc_project.bean.Results;
import com.dfrc.hxqh.dfrc_project.model.MATRECTRANS;
import com.dfrc.hxqh.dfrc_project.model.POLINE;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.view.adapter.BaseQuickAdapter;
import com.dfrc.hxqh.dfrc_project.view.adapter.PoLineChooseListAdapter;
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
 * 物料接收
 */

public class PoChooseActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private static final String TAG = "PoChooseActivity";

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

    @Bind(R.id.edt_input)
    EditText search; //编辑框
    @Bind(R.id.btn_delete)
    Button deleteBtn; //删除
    /**
     * 适配器*
     */
    private PoLineChooseListAdapter poLineChooseListAdapter;
    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;


    ArrayList<POLINE> items = new ArrayList<POLINE>();

    List<POLINE> chooseItems = new ArrayList<POLINE>();


    private String ponum; //采购单号



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
        titleTextView.setText(R.string.xzydg_text);

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
        initAdapter(new ArrayList<POLINE>());
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
    }

    @OnTextChanged(value = R.id.edt_input, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged(Editable s) {
        if (s.length() > 0) {
            deleteBtn.setVisibility(View.VISIBLE);
        } else {
            deleteBtn.setVisibility(View.GONE);
        }
    }

    //删除
    @OnClick(R.id.btn_delete)
    void setDeleteBtnOnClickListener() {
        search.setText("");
    }



    @OnClick(R.id.sbmittext_id)
    void setSbmitImageButtonOnClickListener() {

        if (chooseItems.size() == 0) {
            MessageUtils.showMiddleToast(PoChooseActivity.this, "请选择物料");
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
                                    PoChooseActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    poLineChooseListAdapter.removeAll(items);
                    items = new ArrayList<POLINE>();
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
        String url = HttpManager.getPOLINEURL(search, ponum, page, 20);

        HttpManager.getDataPagingInfo(PoChooseActivity.this, url, new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<POLINE> item = JsonUtils.parsingPOLINE(results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {
                    if (page == 1) {
                        items = new ArrayList<POLINE>();
                        initAdapter(items);
                    }
                    if (page > totalPages) {
                        MessageUtils.showMiddleToast(PoChooseActivity.this, getString(R.string.have_load_out_all_the_data));
                    } else {

                        for (POLINE poline : item) {
                            int orderqty = Integer.valueOf(poline.getORDERQTY()).intValue();
                            int receivedqty = 0;
                            if (poline.getRECEIVEDQTY() == null) {
                                receivedqty = 0;
                            } else {
                                double dd = Double.valueOf(poline.getRECEIVEDQTY());
                                receivedqty = (int) dd;
                            }
                            if (receivedqty == 0 || receivedqty < orderqty) {  //接收判断条件
                                items.add(poline);
                            }
                        }

                        if (items != null && items.size() != 0) {
                            addData(items);
                            nodatalayout.setVisibility(View.GONE);
                        } else {
                            nodatalayout.setVisibility(View.VISIBLE);
                        }
                    }
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
    private void initAdapter(final List<POLINE> list) {
        poLineChooseListAdapter = new PoLineChooseListAdapter(PoChooseActivity.this, R.layout.list_item_choose_poline, list);
        recyclerView.setAdapter(poLineChooseListAdapter);
        poLineChooseListAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
        poLineChooseListAdapter.setOnCheckedChangeListener(new PoLineChooseListAdapter.OnCheckedChangeListener() {
            @Override
            public void cOnCheckedChangeListener(boolean b, int postion, String t,String hg) {
                items.get(postion).setRECEIVEDQTY(t);
                items.get(postion).setBINNUM(hg);
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
    private void addData(final List<POLINE> list) {
        poLineChooseListAdapter.addData(list);
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
                        POLINE poline = chooseItems.get(i);
                        result = AndroidClientService.INV02RecByPOLine(PoChooseActivity.this, getMATRECTRANS(poline));
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
                    MessageUtils.showMiddleToast(PoChooseActivity.this, s);
                    finish();

                }
            }.execute();
        }


    }


    /**
     * 封装新增的信息
     *
     * @param poline
     */
    private MATRECTRANS getMATRECTRANS(POLINE poline) {
        MATRECTRANS matrectrans = new MATRECTRANS();
        matrectrans.setPOLINENUM(poline.getPOLINENUM());
        matrectrans.setRECEIPTQUANTITY(poline.getRECEIVEDQTY());
        matrectrans.setBINNUM(poline.getBINNUM());
        matrectrans.setPONUM(ponum);
        matrectrans.setENTERBY(AccountUtils.getloginUserName(PoChooseActivity.this));
        return matrectrans;
    }


}
