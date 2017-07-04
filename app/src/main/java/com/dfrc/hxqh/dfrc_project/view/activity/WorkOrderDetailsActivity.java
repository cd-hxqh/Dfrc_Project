package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.WORKORDER;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.NetWorkHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 工单详情
 */
public class WorkOrderDetailsActivity extends BaseActivity {

    private static final String TAG = "WorkOrderDetailsActivity";

    @Bind(R.id.title_name)
    TextView titleTextView; //标题
    @Bind(R.id.wonum_text_id)
    TextView wonumTextView; //工单
    @Bind(R.id.description_text_id)
    TextView descriptionTextView; //描述
    @Bind(R.id.bz_text_id)
    TextView bzTextView; //班组
    @Bind(R.id.year_text_id)
    TextView yearTextView; //年度
    @Bind(R.id.month_text_id)
    TextView monthTextView; //月度
    @Bind(R.id.n_wpnum_text_id)
    TextView n_wpnumTextView; //周计划编号
    @Bind(R.id.status_text_id)
    TextView statusTextView; //状态
    @Bind(R.id.n_qtyopen_text_id)
    TextView n_qtyopenTextView; //未完成项目数
    @Bind(R.id.n_qtycomp_text_id)
    TextView n_qtycompTextView; //已完成项目数
    @Bind(R.id.n_qtyok_text_id)
    TextView n_qtyokTextView; //OK数
    @Bind(R.id.n_qtyng_text_id)
    TextView n_qtyngTextView; //NG数


    private WORKORDER workorder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workorder_details);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        workorder = (WORKORDER) getIntent().getSerializableExtra("workorder");
    }

    @Override
    protected void findViewById() {


    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.ddjgd_text);
        if (workorder != null) {
            wonumTextView.setText(workorder.getWONUM());
            descriptionTextView.setText(workorder.getDESCRIPTION());
            bzTextView.setText(workorder.getCREWID());
            yearTextView.setText(workorder.getYEAR());
            monthTextView.setText(workorder.getMONTH());
            n_wpnumTextView.setText(workorder.getN_WPNUM());
            statusTextView.setText(workorder.getSTATUS());
            n_qtyopenTextView.setText(workorder.getN_QTYOPEN());
            n_qtycompTextView.setText(workorder.getN_QTYCOMP());
            n_qtyokTextView.setText(workorder.getN_QTYOK());
            n_qtyngTextView.setText(workorder.getN_QTYNG());

        }

    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageViewOnClickListener() {
        finish();
    }

    //个人点击明细行
    @OnClick(R.id.item_wotask_btn_id)
    void setItemWotaskBtnOnClickListener() {
        if (NetWorkHelper.isWifi(this) && !AccountUtils.getIsOffLine(this)) { //在线
            Intent intent = new Intent(WorkOrderDetailsActivity.this, WotaskActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("wonum", workorder.getWONUM());
            bundle.putSerializable("crewid", workorder.getCREWID());
            bundle.putSerializable("siteid", workorder.getSITEID());
            bundle.putSerializable("n_responsor", AccountUtils.getloginUserName(WorkOrderDetailsActivity.this));
            bundle.putString("assetNum", "");
            intent.putExtras(bundle);
            startActivityForResult(intent, 1000);
        } else {//离线
            Intent intent = new Intent(WorkOrderDetailsActivity.this, WotaskLocationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("wonum", workorder.getWONUM());
            bundle.putSerializable("crewid", workorder.getCREWID());
            bundle.putSerializable("siteid", workorder.getSITEID());
            bundle.putString("assetNum", "");
            intent.putExtras(bundle);
            startActivityForResult(intent, 1000);
        }

    }

    //点击明细行
    @OnClick(R.id.all_wotask_btn_id)
    void setAllWotaskBtnOnClickListener() {
        Log.i(TAG, "line=" + !AccountUtils.getIsOffLine(this));
        if (NetWorkHelper.isWifi(this) && !AccountUtils.getIsOffLine(this)) { //在线
            Intent intent = new Intent(WorkOrderDetailsActivity.this, WotaskActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("wonum", workorder.getWONUM());
            bundle.putSerializable("crewid", workorder.getCREWID());
            bundle.putSerializable("siteid", workorder.getSITEID());
            bundle.putSerializable("siteid", workorder.getSITEID());
            bundle.putString("assetNum", "");
            intent.putExtras(bundle);
            startActivityForResult(intent, 1000);
        } else {//离线
            Intent intent = new Intent(WorkOrderDetailsActivity.this, WotaskLocationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("wonum", workorder.getWONUM());
            bundle.putSerializable("crewid", workorder.getCREWID());
            bundle.putSerializable("siteid", workorder.getSITEID());
            bundle.putString("assetNum", "");
            intent.putExtras(bundle);
            startActivityForResult(intent, 1000);
        }

    }


}
