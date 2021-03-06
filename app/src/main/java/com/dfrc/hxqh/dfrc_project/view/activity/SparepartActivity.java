package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
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
import com.dfrc.hxqh.dfrc_project.dao.SparepartDao;
import com.dfrc.hxqh.dfrc_project.model.SPAREPART;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.until.NetWorkHelper;
import com.dfrc.hxqh.dfrc_project.view.adapter.SparepartListAdapter;
import com.dfrc.hxqh.dfrc_project.view.widght.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by Administrator on 2017/2/15.
 * 设备备件
 */

public class SparepartActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private static final String TAG = "SparepartActivity";

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
    private SparepartListAdapter sparepartListAdapter;

    @Bind(R.id.edt_input)
    EditText search; //编辑框
    @Bind(R.id.btn_delete)
    Button deleteBtn; //删除
    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;


    ArrayList<SPAREPART> items = new ArrayList<SPAREPART>();

    private String assetnum; //设备编号
    private String itemnum = ""; //物料编码

    private boolean isCodePda; //判断是扫描还是手输
    private SparepartDao sparepartdao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        initDAO();
        initData();
        findViewById();
        initView();
    }

    /**
     * 初始化界面DAO
     **/
    private void initDAO() {
        sparepartdao = new SparepartDao(this);
    }

    private void initData() {
        assetnum = getIntent().getExtras().getString("assetnum");
    }


    @Override
    protected void findViewById() {

    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.beijian_text);
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
        initAdapter(new ArrayList<SPAREPART>());
        items = new ArrayList<>();
        if (NetWorkHelper.isWifi(this)) {//在线
            getData(searchText);
        } else {
            offLineByAssetNums();
        }

    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageViewOnClickListener() {
        finish();
    }

    @OnTextChanged(value = R.id.edt_input, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    void beforeTextChanged(CharSequence s, int start, int before, int count) {
        if (start == 0 && before == 0 && count > 1) {
            //扫描
            isCodePda = true;
        } else {
            //手输
            isCodePda = false;
        }
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
        if (isCodePda) {
            itemnum = parsingResult(s.toString());
            sparepartListAdapter.removeAll(items);
            items = new ArrayList<SPAREPART>();
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

    @Override
    public void onLoad() {
        if (NetWorkHelper.isWifi(this)) {
            page++;
            getData(searchText);
        } else {
            refresh_layout.setRefreshing(false);
            refresh_layout.setLoading(false);
        }

    }

    @Override
    public void onRefresh() {
        if (NetWorkHelper.isWifi(this)) {
            page = 1;
            getData(searchText);
        } else {
            refresh_layout.setRefreshing(false);
            refresh_layout.setLoading(false);
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
                                    SparepartActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    sparepartListAdapter.removeAll(items);
                    items = new ArrayList<SPAREPART>();
                    nodatalayout.setVisibility(View.GONE);
                    page = 1;
                    if(NetWorkHelper.isWifi(SparepartActivity.this)){
                        getData(searchText);
                    }else{
                        offLineByAssetNumAndItemNums(searchText);
                    }

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
        if (!isCodePda && itemnum.equals("")) {
            url = HttpManager.getSPAREPARTURL(search, assetnum, page, 20);
        } else {
            url = HttpManager.getSPAREPARTURL1(assetnum, itemnum, page, 20);
            itemnum = "";
        }

        HttpManager.getDataPagingInfo(SparepartActivity.this, url, new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<SPAREPART> item = JsonUtils.parsingSPAREPART(results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<SPAREPART>();
                            initAdapter(items);
                        }
                        if (page > totalPages) {
                            MessageUtils.showMiddleToast(SparepartActivity.this, getString(R.string.have_load_out_all_the_data));
                        } else {
                            sparepartdao.update(item);
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
     * 离线查询Sparepart数据
     **/
    private void offLineByAssetNums() {
        List<SPAREPART> sList = sparepartdao.findByAssetNums(assetnum);
        refresh_layout.setRefreshing(false);
        refresh_layout.setLoading(false);
        if (sList == null || sList.isEmpty()) {
            nodatalayout.setVisibility(View.VISIBLE);
        } else {

            addData(sList);
        }
    }


    /**离线 根据AssetNum与ItemNum模糊查询Sparepart的数据**/
    private void offLineByAssetNumAndItemNums(String itemnum){
        List<SPAREPART> sList = sparepartdao.findByAssetNumAndItemNums(assetnum,itemnum);
        refresh_layout.setRefreshing(false);
        refresh_layout.setLoading(false);
        if (sList == null || sList.isEmpty()) {
            nodatalayout.setVisibility(View.VISIBLE);
        } else {
            addData(sList);
        }
    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<SPAREPART> list) {
        sparepartListAdapter = new SparepartListAdapter(SparepartActivity.this, R.layout.list_item_sparepart, list);
        recyclerView.setAdapter(sparepartListAdapter);

    }

    /**
     * 添加数据*
     */
    private void addData(final List<SPAREPART> list) {
        sparepartListAdapter.addData(list);
    }


}
