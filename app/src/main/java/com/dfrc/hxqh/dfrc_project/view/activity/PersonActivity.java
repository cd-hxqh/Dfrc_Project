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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.api.HttpManager;
import com.dfrc.hxqh.dfrc_project.api.HttpRequestHandler;
import com.dfrc.hxqh.dfrc_project.api.JsonUtils;
import com.dfrc.hxqh.dfrc_project.bean.Results;
import com.dfrc.hxqh.dfrc_project.dao.PersonDao;
import com.dfrc.hxqh.dfrc_project.model.PERSON;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.until.NetWorkHelper;
import com.dfrc.hxqh.dfrc_project.view.adapter.BaseQuickAdapter;
import com.dfrc.hxqh.dfrc_project.view.adapter.PersonListAdapter;
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
 * 人员选择
 */

public class PersonActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private static final String TAG = "PersonActivity";

    public static final int PERSION_REQUESTCODE = 1000;

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
    /**
     * 适配器*
     */
    private PersonListAdapter personListAdapter;
    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;


    ArrayList<PERSON> items = new ArrayList<PERSON>();

    private int mark;
    private String assetNum;
    private String crewid; //班组
    private String siteid; //站点


    private PersonDao personDao;

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
        personDao = new PersonDao(this);
    }

    private void initData() {
        if (getIntent().hasExtra("crewid")) { //班组
            crewid = getIntent().getExtras().getString("crewid");
        }
        if (getIntent().hasExtra("siteid")) { //地点
            siteid = getIntent().getExtras().getString("siteid");
        }
    }


    @Override
    protected void findViewById() {
    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.person_text);
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
        initAdapter(new ArrayList<PERSON>());
        items = new ArrayList<>();
        if (NetWorkHelper.isWifi(this)) {
            Log.i(TAG, "在线");
            getData(searchText);
        } else {
            Log.i(TAG, "离线");
            offLinegetData();
        }

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
                                    PersonActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    items = new ArrayList<PERSON>();
                    personListAdapter.removeAll(personListAdapter.getData());
                    nodatalayout.setVisibility(View.GONE);
                    refresh_layout.setRefreshing(true);
                    page = 1;
                    if (NetWorkHelper.isWifi(PersonActivity.this)) { //在线
                        getData(searchText);
                    } else { //离线
                        offLineByPERSIONIDAndDISPLAYNAME(searchText);
                    }

                    return true;
                }
                return false;
            }
        });
    }


    /**
     * 在线获取数据*
     */
    private void getData(String search) {
        String url = null;
        if (crewid.equals("")) {
            url = HttpManager.getPERSIONURL(search, siteid, page, 20);
        } else {
            url = HttpManager.getPERSIONURL(search, crewid, siteid, page, 20);
        }
        HttpManager.getDataPagingInfo(PersonActivity.this, url, new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<PERSON> item = JsonUtils.parsingPERSON(results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<PERSON>();
                            initAdapter(items);
                        }
                        if (page > totalPages) {
                            MessageUtils.showMiddleToast(PersonActivity.this, getString(R.string.have_load_out_all_the_data));
                        } else {
                            personDao.update(item);
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
     * 离线获取数据
     **/
    private void offLinegetData() {
        Log.i(TAG, "crewid=" + crewid);
        if (null == crewid || crewid.equals("")) {

        } else {

            List<PERSON> pList = personDao.findByCrewid(crewid, siteid);
            refresh_layout.setRefreshing(false);
            refresh_layout.setLoading(false);
            if (pList == null || pList.isEmpty()) {
                nodatalayout.setVisibility(View.VISIBLE);
            } else {

                addData(pList);
            }
        }
    }

    /**
     * 离线根据PERSONID与DISPLAYNAME查询PERSION数据
     **/
    private void offLineByPERSIONIDAndDISPLAYNAME(String seach) {
        refresh_layout.setRefreshing(false);
        refresh_layout.setLoading(false);
        List<PERSON> pList = personDao.findByPersionIdAndDisplayNames(seach, siteid);
        Log.i(TAG, "pList size=" + pList.size());
        if (pList == null || pList.isEmpty()) {
            nodatalayout.setVisibility(View.VISIBLE);
        } else {
            addData(pList);
        }
    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<PERSON> list) {
        personListAdapter = new PersonListAdapter(PersonActivity.this, R.layout.list_item_person, list);
        recyclerView.setAdapter(personListAdapter);
        personListAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("person", (Serializable) personListAdapter.getData().get(position));
                intent.putExtras(bundle);
                setResult(PERSION_REQUESTCODE, intent);
                finish();
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<PERSON> list) {
        personListAdapter.addData(list);
    }

}
