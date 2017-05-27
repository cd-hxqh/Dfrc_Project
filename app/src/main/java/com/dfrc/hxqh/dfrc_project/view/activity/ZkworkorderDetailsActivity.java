package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.ZKWORKORDER;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 总库领料单详情
 */
public class ZkworkorderDetailsActivity extends BaseActivity {
    private static String TAG = "ZkworkorderDetailsActivity";


    @Bind(R.id.title_back_id)
    ImageView backImageView; //返回按钮

    @Bind(R.id.title_name)
    TextView titleTextView; //标题
    @Bind(R.id.wonum_text_id)
    TextView wonumTextView; //领料单号
    @Bind(R.id.description_text_id)
    TextView descriptionTextView; //描述
    @Bind(R.id.crewid_text_id)
    TextView crewidTextView; //班组
    @Bind(R.id.tbr_text_id)
    TextView tbrTextView; //提报人
    @Bind(R.id.n_sap1_text_id)
    TextView n_sap1TextView; //发货仓库
    @Bind(R.id.n_kostl_text_id)
    TextView n_kostlTextView; //成本中心
    @Bind(R.id.n_site_text_id)
    TextView n_siteTextView; //工厂代码
    @Bind(R.id.outtype_text_id)
    TextView outtypeTextView; //总库移动类型
    @Bind(R.id.totalcost_text_id)
    TextView totalcostTextView; //领料单合计
    @Bind(R.id.sumwobudget_text_id)
    TextView sumwobudgetTextView; //领用年预算
    @Bind(R.id.wobudget_text_id)
    TextView wobudgetTextView; //领用月预算
    @Bind(R.id.expwo_text_id)
    TextView expwoTextView; //本月领用支出（实际）
    @Bind(R.id.actyearexpwo_text_id)
    TextView actyearexpwoTextView; //当年领用支出（实际）
    @Bind(R.id.status_text_id)
    TextView statusTextView; //状态
    @Bind(R.id.reportdate_text_id)
    TextView reportdateTextView; //报告日期
    @Bind(R.id.statusdate_text_id)
    TextView statusdateTextView; //状态日期
    @Bind(R.id.siteid_text_id)
    TextView siteidTextView; //地点


    private ZKWORKORDER zkworkorder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zkworkorder_details);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        zkworkorder = (ZKWORKORDER) getIntent().getSerializableExtra("zkworkorder");
    }

    @Override
    protected void findViewById() {

    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.zklld_text);
        if (zkworkorder != null) {
            wonumTextView.setText(zkworkorder.getWONUM());
            descriptionTextView.setText(zkworkorder.getDESCRIPTION());
            crewidTextView.setText(zkworkorder.getCREWID());
            tbrTextView.setText(zkworkorder.getREPORTEDBYNAME());
            n_sap1TextView.setText(zkworkorder.getN_SAP1());
            n_kostlTextView.setText(zkworkorder.getN_KOSTL());
            n_siteTextView.setText(zkworkorder.getN_SITE());
            outtypeTextView.setText(zkworkorder.getOUTTYPE());
            sumwobudgetTextView.setText(zkworkorder.getSUMWOBUDGET());
            wobudgetTextView.setText(zkworkorder.getWOBUDGET());
            expwoTextView.setText(zkworkorder.getEXPWO());
            actyearexpwoTextView.setText(zkworkorder.getACTYEAREXPWO());
            statusTextView.setText(zkworkorder.getSTATUS());
            reportdateTextView.setText(zkworkorder.getREPORTDATE());
            statusdateTextView.setText(zkworkorder.getSTATUSDATE());
            totalcostTextView.setText(zkworkorder.getTOTALCOST());
            siteidTextView.setText(zkworkorder.getSITEID());

        }

    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageViewOnClickListener() {
        finish();
    }
    //物资领用明细
    @OnClick(R.id.sqlywlmx_text_id)
    void setSqlywlmxBtnOnClickListener() {
        Intent intent = getIntent();
        intent.setClass(ZkworkorderDetailsActivity.this, N_materialActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("wonum", zkworkorder.getWONUM());
        bundle.putSerializable("zkworkorder", zkworkorder);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1000);
    }
    //新建行
    @OnClick(R.id.add_btn_id)void setAddBtnOnClickListener(){
        Intent intent = getIntent();
        intent.setClass(ZkworkorderDetailsActivity.this, N_material_AddActivity.class);
        startActivityForResult(intent, 1000);
    }


}
