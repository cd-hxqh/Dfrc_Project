package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.api.HttpManager;
import com.dfrc.hxqh.dfrc_project.api.HttpRequestHandler;
import com.dfrc.hxqh.dfrc_project.api.JsonUtils;
import com.dfrc.hxqh.dfrc_project.bean.Results;
import com.dfrc.hxqh.dfrc_project.model.POLINE;
import com.dfrc.hxqh.dfrc_project.view.adapter.BaseQuickAdapter;
import com.dfrc.hxqh.dfrc_project.view.adapter.PoLineListAdapter;
import com.dfrc.hxqh.dfrc_project.view.widght.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/15.
 * 采购接收
 */

public class PoLineActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private static final String TAG = "PoActivity";
    @Bind(R.id.title_name) //标题
            TextView titleTextView;
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
    private PoLineListAdapter poLineListAdapter;
    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;


    ArrayList<POLINE> items = new ArrayList<POLINE>();

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

    //采购单号
    private void initData() {
        ponum=getIntent().getExtras().getString("ponum");
    }


    @Override
    protected void findViewById() {
    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.poline_text);
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
        initAdapter(new ArrayList<POLINE>());
        items = new ArrayList<>();
        getData(searchText);
    }


    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackOnClickListener() {
        finish();
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
                                    PoLineActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    poLineListAdapter.removeAll(items);
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
        String url = HttpManager.getPOLINEURL(search,ponum, page, 20);

        HttpManager.getDataPagingInfo(PoLineActivity.this, url, new HttpRequestHandler<Results>() {
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

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<POLINE>();
                            initAdapter(items);
                        }
                        for (int i = 0; i < item.size(); i++) {
                            items.add(item.get(i));
                        }
                        addData(item);
                    }
                    nodatalayout.setVisibility(View.GONE);

                    initAdapter(items);
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
        poLineListAdapter = new PoLineListAdapter(PoLineActivity.this, R.layout.list_item_poline, list);
        recyclerView.setAdapter(poLineListAdapter);
        poLineListAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = getIntent();
//                intent.setClass(PoLineActivity.this, PoDetailsActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("po", items.get(position));
//                intent.putExtras(bundle);
//                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<POLINE> list) {
        poLineListAdapter.addData(list);
    }

}