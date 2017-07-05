package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.dfrc.hxqh.dfrc_project.dao.WoTaskDao;
import com.dfrc.hxqh.dfrc_project.model.WOTASK;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.view.adapter.BaseQuickAdapter;
import com.dfrc.hxqh.dfrc_project.view.adapter.WotaskListAdapter;
import com.dfrc.hxqh.dfrc_project.view.widght.SwipeRefreshLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by Administrator on 2017/2/15.
 * 点检明细行本地查询
 */

public class WotaskLocationActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private static final String TAG = "WotaskLocationActivity";
    public static final int WOTASK_CODE = 1002;

    @Bind(R.id.title_name)
    TextView titleTextView; //标题
    @Bind(R.id.sbmittext_id)
    ImageButton codeImageButton;

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
    private WotaskListAdapter wotaskListAdapter;

    @Bind(R.id.edt_input)
    EditText search; //编辑框
    @Bind(R.id.btn_delete)
    Button deleteBtn; //删除
    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;


    ArrayList<WOTASK> items = new ArrayList<WOTASK>();

    private String wonum; //工单编号
    private String assetNum; //设备编号
    private String n_responsor; //实施负负责人

    private boolean isCodePda; //判断是扫描还是手输

    @OnClick(R.id.title_back_id)
    public void setBackOnClickListener() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        initData();
        findViewById();
        initView();
    }

    private void initData() {
        wonum = getIntent().getExtras().getString("wonum");
        assetNum = getIntent().getExtras().getString("assetNum");
        if (getIntent().hasExtra("n_responsor")) {
            n_responsor = getIntent().getExtras().getString("n_responsor");

        }
    }


    @Override
    protected void findViewById() {

    }


    //二维码扫描
    @OnClick(R.id.sbmittext_id)
    void setCodeImageButtonOnClickListener() {
        Intent intent = getIntent();
        intent.setClass(WotaskLocationActivity.this, MipcaActivityCapture.class);
        intent.putExtra("mark", WOTASK_CODE);
        startActivityForResult(intent, 0);
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
            wotaskListAdapter.removeAll(items);
            items = new ArrayList<WOTASK>();
            nodatalayout.setVisibility(View.GONE);
            refresh_layout.setRefreshing(true);
            page = 1;
            getDataSearch(assetNum);
        }
    }

    //删除
    @OnClick(R.id.btn_delete)
    void setDeleteBtnOnClickListener() {
        search.setText("");
    }

    @Override
    protected void initView() {
        titleTextView.setText(R.string.wotask_text);
        codeImageButton.setImageResource(R.drawable.ic_code);
        codeImageButton.setVisibility(View.VISIBLE);
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
        initAdapter(new ArrayList<WOTASK>());
        items = new ArrayList<>();
        getData();
    }


    @Override
    public void onLoad() {
//        page++;
//        getData(searchText);
        MessageUtils.showMiddleToast(WotaskLocationActivity.this, getString(R.string.have_load_out_all_the_data));
        refresh_layout.setLoading(false);
    }

    @Override
    public void onRefresh() {
//        page = 1;
//        getData(searchText);

        refresh_layout.setRefreshing(false);
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
                                    WotaskLocationActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    wotaskListAdapter.removeAll(wotaskListAdapter.getData());
                    nodatalayout.setVisibility(View.GONE);
                    refresh_layout.setRefreshing(true);
                    page = 1;
                    getDataSearch(searchText);
                    return true;
                }
                return false;
            }
        });
    }


    /**
     * 获取数据*
     */
    private void getData() {

        List<WOTASK> wotaskList = new WoTaskDao(WotaskLocationActivity.this).queryForByWonum(wonum, n_responsor);
        if (wotaskList != null && wotaskList.size() != 0) {
            addData(wotaskList);
            refresh_layout.setRefreshing(false);
            refresh_layout.setLoading(false);
            nodatalayout.setVisibility(View.GONE);
        } else {
            refresh_layout.setRefreshing(false);
            nodatalayout.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 获取根据查询条件查询数据*
     */
    private void getDataSearch(String search) {
        List<WOTASK> wotaskList = null;
        if (assetNum.equals("")) {
            wotaskList = new WoTaskDao(WotaskLocationActivity.this).findByWonum(wonum, n_responsor, search);
        } else {
            Log.i(TAG, "assetNum=" + assetNum);
            wotaskList = new WoTaskDao(WotaskLocationActivity.this).findByAssetNum(wonum, assetNum, n_responsor);
        }
        wotaskListAdapter.removeAll(wotaskListAdapter.getData());
        if (wotaskList != null && wotaskList.size() != 0) {
            Log.i(TAG, "wotaskList=" + wotaskList.size());
            Log.i(TAG, "2");
            addData(wotaskList);
            refresh_layout.setRefreshing(false);
            refresh_layout.setLoading(false);
            nodatalayout.setVisibility(View.GONE);
        } else {
            Log.i(TAG, "3");
            refresh_layout.setRefreshing(false);
            nodatalayout.setVisibility(View.VISIBLE);
        }

    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<WOTASK> list) {
        wotaskListAdapter = new WotaskListAdapter(WotaskLocationActivity.this, R.layout.list_item_wotask, list);
        recyclerView.setAdapter(wotaskListAdapter);
        wotaskListAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = getIntent();
                intent.setClass(WotaskLocationActivity.this, WotaskDetailsLocationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("wotask", (Serializable) wotaskListAdapter.getData().get(position));
                bundle.putString("wonum", wonum);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });

    }

    /**
     * 添加数据*
     */
    private void addData(final List<WOTASK> list) {
        wotaskListAdapter.addData(list);
    }


}
