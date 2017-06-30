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
import com.dfrc.hxqh.dfrc_project.dao.AssetDao;
import com.dfrc.hxqh.dfrc_project.dao.SparepartDao;
import com.dfrc.hxqh.dfrc_project.model.ASSET;
import com.dfrc.hxqh.dfrc_project.model.SPAREPART;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.until.NetWorkHelper;
import com.dfrc.hxqh.dfrc_project.view.adapter.AssetListAdapter;
import com.dfrc.hxqh.dfrc_project.view.adapter.BaseQuickAdapter;
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
 * 设备管理
 */

public class AssetActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private static final String TAG = "AssetActivity";
    public static final int ASSET_CODE = 1001;
    @Bind(R.id.title_name) //标题
            TextView titleTextView;
//    @Bind(R.id.sbmittext_id)
//    ImageButton codeImageButton;

    @Bind(R.id.title_add) //菜单
            ImageView menuImageView; //菜单
    PopupWindow popupWindow;

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
    private AssetListAdapter assetListAdapter;
    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;


    ArrayList<ASSET> items = new ArrayList<ASSET>();

    private int mark;
    private String assetNum;

    private boolean isCodePda; //判断是扫描还是手输
    private AssetDao assetDao; //主表DAO
    private SparepartDao sparepartDao; //子表DAO


    /**
     * 判断根据班组查询还是根据站点查询
     **/
    private int isMark; //0表示根据班组查询,1表示根据站点查询

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        initDAO();
        initDate();
        findViewById();
        initView();
    }

    /**
     * 初始化DAO
     **/
    private void initDAO() {
        assetDao = new AssetDao(this);
        sparepartDao = new SparepartDao(this);
    }

    private void initDate() {
        assetNum = getIntent().getExtras().getString("assetNum");

    }


    @Override
    protected void findViewById() {
    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.sbcx_text);
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
        initAdapter(new ArrayList<ASSET>());
        items = new ArrayList<>();
        if (NetWorkHelper.isWifi(this)) {//在线
            getData(searchText);
        } else if (AccountUtils.getIsOffLine(this)) { //离线
            offlinegetData();
        }else{
            MessageUtils.showMiddleToast(this,"暂无网络,请离线登陆操作");
        }

    }


    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackOnClickListener() {
        finish();
    }


    //菜单
    @OnClick(R.id.title_add)
    void setMenuImageViewOnClickListener() {
        showPopupWindow(menuImageView);
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
            assetNum = parsingResult(s.toString());
            assetListAdapter.removeAll(assetListAdapter.getData());
            items = new ArrayList<ASSET>();
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
                                    AssetActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    assetListAdapter.removeAll(assetListAdapter.getData());
                    items = new ArrayList<ASSET>();
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
        if (isCodePda && !assetNum.equals("")) {//扫描
            url = HttpManager.getASSETByNuMURL(assetNum, page, 20);
        } else {//手动
            if (isMark == 0) {//根据班组查询
                url = HttpManager.getASSETURL(search, AccountUtils.getloginSite(AssetActivity.this), AccountUtils.getCrewid(this), page, 20);
            } else if (isMark == 1) {//根据站点查询
                url = HttpManager.getASSETURL(search, AccountUtils.getloginSite(AssetActivity.this), null, page, 20);
            }


        }

        HttpManager.getDataPagingInfo(AssetActivity.this, url, new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<ASSET> item = JsonUtils.parsingASSET(results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<ASSET>();
                            initAdapter(items);
                        }
                        if (page > totalPages) {
                            MessageUtils.showMiddleToast(AssetActivity.this, getString(R.string.have_load_out_all_the_data));
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
     * 离线--根据站点查询Asset的数据
     **/
    private void offlinegetData() {
        List<ASSET> aList = assetDao.findBySiteds(AccountUtils.getloginSite(this));
        refresh_layout.setRefreshing(false);
        refresh_layout.setLoading(false);
        if (aList == null || aList.isEmpty()) {
            nodatalayout.setVisibility(View.VISIBLE);
        } else {

            addData(aList);
        }
    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<ASSET> list) {
        assetListAdapter = new AssetListAdapter(AssetActivity.this, R.layout.list_item_asset, list);
        recyclerView.setAdapter(assetListAdapter);
        assetListAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = getIntent();
                intent.setClass(AssetActivity.this, AssetDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("asset", (Serializable) assetListAdapter.getData().get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<ASSET> list) {
        assetListAdapter.addData(list);
    }


    /**
     * 初始化showPopupWindow*
     */
    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(AssetActivity.this).inflate(
                R.layout.asset_popup_window, null);


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
        TextView sysTextView = (TextView) contentView.findViewById(R.id.sys_text_id); //扫一扫
        TextView bzcxTextView = (TextView) contentView.findViewById(R.id.bzcx_text_id); //按班组查询
        TextView zdcxTextView = (TextView) contentView.findViewById(R.id.zdcx_text_id); //按站点查询
        TextView sbxzTextView = (TextView) contentView.findViewById(R.id.sb_xz_text); //设备下载
        sysTextView.setOnClickListener(sysOnClickListener);
        bzcxTextView.setOnClickListener(bzcxOnClickListener);
        zdcxTextView.setOnClickListener(zdcxOnClickListener);
        sbxzTextView.setOnClickListener(sbxzOnClickListener);

    }

    //扫一扫
    private View.OnClickListener sysOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            popupWindow.dismiss();
            Intent intent = getIntent();
            intent.setClass(AssetActivity.this, MipcaActivityCapture.class);
            intent.putExtra("mark", ASSET_CODE);
            startActivityForResult(intent, 0);
        }
    };

    //根据班组查询设备数据
    private View.OnClickListener bzcxOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            isMark = 0;
            popupWindow.dismiss();
            refresh_layout.setRefreshing(true);
            assetListAdapter.removeAll(assetListAdapter.getData());
            getData(searchText);
        }
    };
    //根据站点查询设备数据
    private View.OnClickListener zdcxOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            isMark = 1;
            popupWindow.dismiss();
            refresh_layout.setRefreshing(true);
            assetListAdapter.removeAll(assetListAdapter.getData());
            getData(searchText);
        }
    };

    //根据站点查询设备数据
    private View.OnClickListener sbxzOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            popupWindow.dismiss();
            getLoadingDialog("正在下载数据,请稍后...").show();
            offLineDownData();
        }
    };


    /**
     * 离线下载数据(主表)*
     */
    private void offLineDownData() {
        String url = null;
        url = HttpManager.getASSETURL("", AccountUtils.getloginSite(AssetActivity.this), null, page, 50);
        HttpManager.getDataPagingInfo(AssetActivity.this, url, new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                colseProgressBar();
                ArrayList<ASSET> item = JsonUtils.parsingASSET(results.getResultlist());
                if (item != null || !item.isEmpty()) {
                    assetDao.addAssets(item);
                    MessageUtils.showMiddleToast(AssetActivity.this, "设备数据下载成功");
                    for (ASSET asset : item) {
                        offLineDownItemData(asset.getASSETNUM());
                    }
                }
            }

            @Override
            public void onFailure(String error) {
                colseProgressBar();
                MessageUtils.showMiddleToast(AssetActivity.this, "设备数据下载失败");
            }
        });

    }

    /**
     * 离线下载子表
     **/

    private void offLineDownItemData(String assetNum) {
        String url = null;
        url = HttpManager.getSPAREPARTURL("", assetNum, page, 30);
        HttpManager.getDataPagingInfo(AssetActivity.this, url, new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<SPAREPART> item = JsonUtils.parsingSPAREPART(results.getResultlist());
                if (item != null || !item.isEmpty()) {
                    sparepartDao.update(item);
                }
            }

            @Override
            public void onFailure(String error) {
            }
        });

    }


}
