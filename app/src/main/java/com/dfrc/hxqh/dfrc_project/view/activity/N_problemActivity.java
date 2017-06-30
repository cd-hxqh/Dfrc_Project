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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import com.dfrc.hxqh.dfrc_project.model.N_PROBLEM;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.until.NetWorkHelper;
import com.dfrc.hxqh.dfrc_project.view.adapter.BaseQuickAdapter;
import com.dfrc.hxqh.dfrc_project.view.adapter.N_problemListAdapter;
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
 * 问题点管理
 */

public class N_problemActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private static final String TAG = "N_problemActivity";
    public static final int PROBLEM_CODE = 1003;
    @Bind(R.id.title_name)
    TextView titleTextView; //标题
    @Bind(R.id.title_add) //菜单
            ImageView menuImageView; //菜单
    PopupWindow popupWindow;
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
    private N_problemListAdapter n_problemListAdapter;

    @Bind(R.id.edt_input)
    EditText search; //编辑框
    @Bind(R.id.btn_delete)
    Button deleteBtn; //删除

    @Bind(R.id.add_btn_id)
    Button addbtn;
    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;


    ArrayList<N_PROBLEM> items = new ArrayList<N_PROBLEM>();
    private String assetNum;

    private boolean isCodePda; //判断是扫描还是手输

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_list);
        ButterKnife.bind(this);
        initDate();
        findViewById();
        initView();
    }


    private void initDate() {
        assetNum = getIntent().getExtras().getString("assetNum");

    }

    @Override
    protected void findViewById() {

    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.wtdgl_text);
        menuImageView.setImageResource(R.mipmap.ic_more);
        menuImageView.setVisibility(View.VISIBLE);
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
        initAdapter(new ArrayList<N_PROBLEM>());
        items = new ArrayList<>();
        if (NetWorkHelper.isWifi(this)) {
            getData(searchText);
        } else {
            MessageUtils.showMiddleToast(this, getString(R.string.please_check_the_network_settings));
            refresh_layout.setRefreshing(false);
            refresh_layout.setLoading(false);
            nodatalayout.setVisibility(View.VISIBLE);
        }

    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageViewOnClickListener() {
        finish();
    }

    //菜单
    @OnClick(R.id.title_add)
    void setCodeImageButtonOnClickListener() {
        showPopupWindow(menuImageView);
    }

    //新建
    @OnClick(R.id.add_btn_id)
    void setAddBtnOnClickListener() {
        Intent intent = getIntent();
        intent.setClass(N_problemActivity.this, N_ProblemAddActivity.class);
        intent.putExtra("mark", PROBLEM_CODE);
        startActivityForResult(intent, 0);
    }

    @OnTextChanged(value = R.id.edt_input, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @OnTextChanged(value = R.id.edt_input, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        if (start == 0 && before == 0 && count > 3) {
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
            n_problemListAdapter.removeAll(n_problemListAdapter.getData());
            assetNum = parsingResult(s.toString());
            nodatalayout.setVisibility(View.GONE);
            refresh_layout.setRefreshing(true);
            page = 1;
            getData(assetNum);
        }
    }

    //删除
    @OnClick(R.id.btn_delete)
    void setDeleteBtnOnClickListener() {
        search.setText("");
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
                                    N_problemActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    n_problemListAdapter.removeAll(n_problemListAdapter.getData());
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
        if (isCodePda && !assetNum.equals("")) { //扫描
            url = HttpManager.getByASSETNUMN_PROBLEMURL(assetNum, AccountUtils.getloginSite(this), AccountUtils.getCrewid(this), page, 20);
        } else { //手动
            url = HttpManager.getN_PROBLEMURL(search, AccountUtils.getloginSite(this), AccountUtils.getCrewid(this), page, 20);
        }

        HttpManager.getDataPagingInfo(N_problemActivity.this, url, new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<N_PROBLEM> item = JsonUtils.parsingN_PROBLEM(results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<N_PROBLEM>();
                            initAdapter(items);
                        }
                        if (page > totalPages) {
                            MessageUtils.showMiddleToast(N_problemActivity.this, getString(R.string.have_load_out_all_the_data));
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
    private void initAdapter(final List<N_PROBLEM> list) {
        n_problemListAdapter = new N_problemListAdapter(N_problemActivity.this, R.layout.list_item_problem, list);
        recyclerView.setAdapter(n_problemListAdapter);
        n_problemListAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = getIntent();
                intent.setClass(N_problemActivity.this, N_ProblemDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("n_problem", (Serializable) n_problemListAdapter.getData().get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });

    }

    /**
     * 添加数据*
     */
    private void addData(final List<N_PROBLEM> list) {
        n_problemListAdapter.addData(list);
    }


    /**
     * 初始化showPopupWindow*
     */
    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(N_problemActivity.this).inflate(
                R.layout.n_problem_popup_window, null);


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
//        LinearLayout xjLinearLayout = (LinearLayout) contentView.findViewById(R.id.add_text_id);
//        xjLinearLayout.setOnClickListener(xjOnClickListener);
        LinearLayout sysLinearLayout = (LinearLayout) contentView.findViewById(R.id.sys_text_id);
        sysLinearLayout.setOnClickListener(sysOnClickListener);

    }


    //    //新建
//    private View.OnClickListener xjOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Intent intent = getIntent();
//            intent.setClass(N_problemActivity.this, N_ProblemAddActivity.class);
//            intent.putExtra("mark", PROBLEM_CODE);
//            startActivityForResult(intent, 0);
//            popupWindow.dismiss();
//        }
//    };
    //扫一扫
    private View.OnClickListener sysOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            intent.setClass(N_problemActivity.this, MipcaActivityCapture.class);
            intent.putExtra("mark", PROBLEM_CODE);
            startActivityForResult(intent, 0);
            popupWindow.dismiss();
        }
    };


}
