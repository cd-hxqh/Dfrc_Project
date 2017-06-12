package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.FKWORKORDER;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 总库领料单详情
 */
public class FkworkorderDetailsActivity extends BaseActivity {
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
    @Bind(R.id.tbrmc_text_id)
    TextView tbrmcTextView; //提报人名称
    @Bind(R.id.status_text_id)
    TextView statusTextView; //状态
    @Bind(R.id.reportdate_text_id)
    TextView reportdateTextView; //报告日期
    @Bind(R.id.statusdate_text_id)
    TextView statusdateTextView; //状态日期
    @Bind(R.id.siteid_text_id)
    TextView siteidTextView; //地点


    private FKWORKORDER fkworkorder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fkworkorder_details);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        fkworkorder = (FKWORKORDER) getIntent().getSerializableExtra("fkworkorder");
    }

    @Override
    protected void findViewById() {


    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.fklld_text);
        if (fkworkorder != null) {
            wonumTextView.setText(fkworkorder.getWONUM());
            descriptionTextView.setText(fkworkorder.getDESCRIPTION());
            crewidTextView.setText(fkworkorder.getCREWID());
            tbrTextView.setText(fkworkorder.getREPORTEDBY());
            tbrmcTextView.setText(fkworkorder.getREPORTEDBYNAME());
            statusTextView.setText(fkworkorder.getSTATUS());
            reportdateTextView.setText(fkworkorder.getREPORTDATE());
            statusdateTextView.setText(fkworkorder.getSTATUSDATE());
            siteidTextView.setText(fkworkorder.getSITEID());

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
        intent.setClass(FkworkorderDetailsActivity.this, MatusetransActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("wonum", fkworkorder.getWONUM());
        intent.putExtras(bundle);
        startActivityForResult(intent, 1000);
    }

    //新建行
    @OnClick(R.id.add_btn_id)
    void setAddBtnOnClickListener() {
        Intent intent = getIntent();
        intent.setClass(FkworkorderDetailsActivity.this, Fkmatusetrans_AddActivity.class);
        startActivityForResult(intent, 1000);
    }


}
