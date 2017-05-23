package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.constants.Constants;
import com.dfrc.hxqh.dfrc_project.model.ProdctBean;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.view.adapter.MyGridViewAdpter;
import com.dfrc.hxqh.dfrc_project.view.adapter.MyViewPagerAdapter;
import com.dfrc.hxqh.dfrc_project.webserviceclient.AndroidClientService;
import com.mpt.hxqh.dfrc_project.AppManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * MainActivity
 */
public class MainActivity extends BaseActivity {

    public static final int ROTASSETNUM_CODE = 10001;  //移动设备查询

    public static final int ITEMNUM_CODE = 10002;  //工具查询查询
    @Bind(R.id.txt_member)
    TextView memberText;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.points)
    LinearLayout group;//圆点指示器
    private ImageView[] ivPoints;//小圆点图片的集合
    private int totalPage; //总的页数
    private int mPageSize = 8; //每页显示的最大的数量
    private List<ProdctBean> listDatas;//总的数据源
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中
    private int currentPage;//当前页
    private ArrayList<String> list = new ArrayList<>();//appid集合
    private String[] proName = {"设备查询", "定期点检工单", "问题点管理", "采购接收", "库存查询", "总库领料单", "分库领料单", "备件借用", "设置"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getIntentData();
        findViewById();
        initView();
    }

    private void getIntentData() {
        list = getIntent().getStringArrayListExtra("appidArray");
    }

    @Override
    protected void findViewById() {
//        startAsyncTask();
    }


    @Override
    protected void initView() {
        memberText.setText(AccountUtils.getdisplayName(MainActivity.this));
        listDatas = new ArrayList<ProdctBean>();
        if (list != null && list.size() != 0) {
            if (list.contains(Constants.ASSET_APPID)) {//设备管理
                listDatas.add(new ProdctBean(proName[0], Constants.ASSET_APPID, R.mipmap.ic_asset));
            }
            if (list.contains(Constants.N_MATWO_APPID)) {//定期点检工单
                listDatas.add(new ProdctBean(proName[1], Constants.N_MATWO_APPID, R.mipmap.ic_ddjgd));
            }
            if (list.contains(Constants.N_PROB2_APPID)) {//问题点管理
                listDatas.add(new ProdctBean(proName[2], Constants.N_PROB2_APPID, R.mipmap.ic_wtdj));
            }
            if (list.contains(Constants.RECEIPTS_APPID)) {//采购接收
                listDatas.add(new ProdctBean(proName[3], Constants.RECEIPTS_APPID, R.mipmap.ic_cgjs));
            }
            if (list.contains(Constants.INVENTOR_APPID)) {//库存查询
                listDatas.add(new ProdctBean(proName[4], Constants.INVENTOR_APPID, R.mipmap.ic_kccx));
            }
            if (list.contains(Constants.N_WORKOR2)) {//总库领料单
                listDatas.add(new ProdctBean(proName[5], Constants.N_WORKOR2, R.mipmap.ic_zklld));
            }
            if (list.contains(Constants.N_WORKORDE)) {//分库领料单
                listDatas.add(new ProdctBean(proName[6], Constants.N_WORKORDE, R.mipmap.ic_zklld));
            }
            if (list.contains(Constants.N_BORROW_APPID)) {//备件借用
                listDatas.add(new ProdctBean(proName[7], Constants.N_BORROW_APPID, R.mipmap.ic_bjjy));
            }

            listDatas.add(new ProdctBean(proName[8], getString(R.string.setting_text), R.mipmap.ic_setting));

        }
        //总的页数向上取整
        totalPage = (int) Math.ceil(listDatas.size() * 1.0 / mPageSize);
        viewPagerList = new ArrayList<View>();
        for (int i = 0; i < totalPage; i++) {
            //每个页面都是inflate出一个新实例
            final GridView gridView = (GridView) View.inflate(this, R.layout.item_gridview, null);
            gridView.setAdapter(new MyGridViewAdpter(this, listDatas, i, mPageSize));
            //添加item点击监听
            gridView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {
                    // TODO Auto-generated method stub
                    Object obj = gridView.getItemAtPosition(position);
                    if (obj != null && obj instanceof ProdctBean) {
                        ProdctBean prodctBean = (ProdctBean) obj;
                        Intent intent;
                        switch (prodctBean.getAppid()) {
                            case Constants.ASSET_APPID: // 设备查询
                                intent = new Intent(MainActivity.this, AssetActivity.class);
                                intent.putExtra("assetNum", "");
                                startActivityForResult(intent, 0);
                                break;
                            case Constants.N_MATWO_APPID: //定期点检工单
                                intent = new Intent(MainActivity.this, WorkorderActivity.class);
                                startActivityForResult(intent, 0);
                                break;
                            case Constants.N_PROB2_APPID: //问题点管理
                                intent = new Intent(MainActivity.this, N_problemActivity.class);
                                intent.putExtra("assetNum", "");
                                startActivityForResult(intent, 0);
                                break;
                            case Constants.RECEIPTS_APPID: //采购接收
                                intent = new Intent(MainActivity.this, PoActivity.class);
                                startActivityForResult(intent, 0);
                                break;
                            case Constants.INVENTOR_APPID: //库存查询
                                intent = new Intent(MainActivity.this, Inventoryactivity.class);
                                intent.putExtra("assetNum", "");
                                startActivityForResult(intent, 0);
                                break;
                            case Constants.N_WORKOR2: //总库领料单
                                intent = new Intent(MainActivity.this, ZkworkorderActivity.class);
                                startActivityForResult(intent, 0);
                                break;
                            case Constants.N_WORKORDE: //分库领料单
                                intent = new Intent(MainActivity.this, FkworkorderActivity.class);
                                startActivityForResult(intent, 0);
                                break;
                            case Constants.N_BORROW_APPID: //备件借用
                                intent = new Intent(MainActivity.this, N_borrowheadactivity.class);
                                startActivityForResult(intent, 0);
                                break;
                            case "设置": //设置
                                intent = new Intent(MainActivity.this, SettingActivity.class);
                                startActivityForResult(intent, 0);
                                break;

                        }
                    }
                }
            });
            //每一个GridView作为一个View对象添加到ViewPager集合中
            viewPagerList.add(gridView);
        }
        //设置ViewPager适配器
        viewPager.setAdapter(new MyViewPagerAdapter(viewPagerList));

        if (totalPage == 1) {
            group.setVisibility(View.GONE);
        }
        //添加小圆点
        ivPoints = new ImageView[totalPage];
        for (int i = 0; i < totalPage; i++) {
            //循坏加入点点图片组
            ivPoints[i] = new ImageView(this);
            if (i == 0) {
                ivPoints[i].setImageResource(R.drawable.page_focuese);
            } else {
                ivPoints[i].setImageResource(R.drawable.page_unfocused);
            }
            ivPoints[i].setPadding(8, 8, 8, 8);
            group.addView(ivPoints[i]);
        }
        //设置ViewPager的滑动监听，主要是设置点点的背景颜色的改变
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                //currentPage = position;
                for (int i = 0; i < totalPage; i++) {
                    if (i == position) {
                        ivPoints[i].setImageResource(R.drawable.page_focuese);
                    } else {
                        ivPoints[i].setImageResource(R.drawable.page_unfocused);
                    }
                }
            }
        });
    }


    private long exitTime = 0;

    @Override
    public void onBackPressed() {


        if ((System.currentTimeMillis() - exitTime) > 2000) {
            MessageUtils.showMiddleToast(this, getString(R.string.tccx_text));
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.AppExit(MainActivity.this);
        }
    }


    /**
     * 提交数据*
     */
    private void startAsyncTask() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return AndroidClientService.searchMaint2(MainActivity.this, "ASSETNUM", "H2-Z2-CP-047");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.i("MainActivity", "s=" + s);
                MessageUtils.showMiddleToast(MainActivity.this, s);


            }
        }.execute();


    }


}
