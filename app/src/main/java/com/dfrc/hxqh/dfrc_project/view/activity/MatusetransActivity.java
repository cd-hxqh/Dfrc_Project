package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Context;
import android.content.Intent;
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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.api.HttpManager;
import com.dfrc.hxqh.dfrc_project.api.HttpRequestHandler;
import com.dfrc.hxqh.dfrc_project.api.JsonUtils;
import com.dfrc.hxqh.dfrc_project.bean.Results;
import com.dfrc.hxqh.dfrc_project.model.MATUSETRANS;
import com.dfrc.hxqh.dfrc_project.view.adapter.BaseQuickAdapter;
import com.dfrc.hxqh.dfrc_project.view.adapter.MatusetransListAdapter;
import com.dfrc.hxqh.dfrc_project.view.widght.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/15.
 * 分库领用明细
 */

public class MatusetransActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private static final String TAG = "MatusetransActivity";
    public static final int ASSET_CODE = 1001;
    @Bind(R.id.title_name) //标题
            TextView titleTextView;
    @Bind(R.id.title_add)
    ImageView menuImageView; //菜单
    PopupWindow popupWindow;
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
    private MatusetransListAdapter matusetransListAdapter;
    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;


    ArrayList<MATUSETRANS> items = new ArrayList<MATUSETRANS>();

    private int mark;
    private String wonum;

    private LinearLayout addLinearLayout;//新建
    private LinearLayout sysLinearLayout;//扫一扫

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        initDate();
        findViewById();
        initView();
    }

    private void initDate() {
        wonum = getIntent().getExtras().getString("wonum");

    }


    @Override
    protected void findViewById() {
    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.lymx_text);
        setSearchEdit();
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setImageResource(R.mipmap.ic_more);
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
        initAdapter(new ArrayList<MATUSETRANS>());
        items = new ArrayList<>();
        getData(searchText);
    }


    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackOnClickListener() {
        finish();
    }

    //菜单事件
    @OnClick(R.id.title_add)
    void setMenuImageViewOnClickListener() {
        showPopupWindow(menuImageView);
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
                                    MatusetransActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    matusetransListAdapter.removeAll(items);
                    items = new ArrayList<MATUSETRANS>();
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
        String url = HttpManager.getMATUSETRANSURL(search, wonum, page, 20);

        HttpManager.getDataPagingInfo(MatusetransActivity.this, url, new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<MATUSETRANS> item = JsonUtils.parsingMATUSETRANS(results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<MATUSETRANS>();
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
    private void initAdapter(final List<MATUSETRANS> list) {
        matusetransListAdapter = new MatusetransListAdapter(MatusetransActivity.this, R.layout.list_item_asset, list);
        recyclerView.setAdapter(matusetransListAdapter);
        matusetransListAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = getIntent();
                intent.setClass(MatusetransActivity.this, MatusetransDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("matusetrans", items.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<MATUSETRANS> list) {
        matusetransListAdapter.addData(list);
    }


    /**
     * 初始化showPopupWindow*
     */
    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(MatusetransActivity.this).inflate(
                R.layout.n_material_popup_window, null);


        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.popup_background_mtrl_mult));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);
        addLinearLayout = (LinearLayout) contentView.findViewById(R.id.add_linearlayout_id);
        sysLinearLayout = (LinearLayout) contentView.findViewById(R.id.code_linearlayout_id);
        addLinearLayout.setOnClickListener(addOnClickListener);
        sysLinearLayout.setOnClickListener(sysOnClickListener);

    }


    private View.OnClickListener addOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            intent.setClass(MatusetransActivity.this, Fkmatusetrans_AddActivity.class);
            startActivityForResult(intent, 1000);
            popupWindow.dismiss();
        }
    };
    private View.OnClickListener sysOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            intent.setClass(MatusetransActivity.this, MipcaActivityCapture.class);
            intent.putExtra("mark", ASSET_CODE);
            startActivityForResult(intent, 0);
            popupWindow.dismiss();
        }
    };

}
