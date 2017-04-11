package com.dfrc.hxqh.dfrc_project.view.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.N_PROBLEM;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 问题点管理
 */
public class N_ProblemDetailsActivity extends BaseActivity {
    private static String TAG = "N_ProblemDetailsActivity";


    @OnClick(R.id.title_back_id) //返回按钮
    public void setBackOnClickListener() {
        finish();
    }

    @Bind(R.id.title_name)
    TextView titleTextView; //标题


    @Bind(R.id.n_problemnum_text_id)
    TextView n_problemnumTextView; //问题点编号
    @Bind(R.id.prodesc_text_id)
    TextView prodescTextView; //问题点描述
    @Bind(R.id.wonum_text_id)
    TextView wonumTextView; //工单
    @Bind(R.id.gdms_text_id)
    TextView gdmsTextView; //工单描述
    @Bind(R.id.bz_text_id)
    TextView bzTextView; //班组
    @Bind(R.id.finddate_text_id)
    TextView finddateTextView; //发现日期
    @Bind(R.id.responsor_text_id)
    TextView responsorTextView; //担当
    @Bind(R.id.pl_text_id)
    TextView plTextView; //生产线
    @Bind(R.id.position_text_id)
    TextView positionTextView; //部位
    @Bind(R.id.assetnum_text_id)
    TextView assetnumTextView; //设备编号
    @Bind(R.id.sbmc_text_id)
    TextView sbmsTextView; //设备描述
    @Bind(R.id.reason_text_id)
    TextView reasonTextView; //原因
    @Bind(R.id.dc_text_id)
    TextView dcTextView; //对策
    @Bind(R.id.jz_text_id)
    TextView jzTextView; //进展
    @Bind(R.id.abc_text_id)
    TextView abcTextView; //重要度
    @Bind(R.id.confirmby_text_id)
    TextView confirmbyTextView; //确认人
    @Bind(R.id.result_text_id)
    TextView resultTextView; //整改结果

    private N_PROBLEM n_problem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_problem_details);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        n_problem = (N_PROBLEM) getIntent().getSerializableExtra("n_problem");
    }

    @Override
    protected void findViewById() {


    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.wtdgl_text);
        if (n_problem != null) {
            n_problemnumTextView.setText(n_problem.getN_PROBLEMNUM());
            prodescTextView.setText(n_problem.getPRODESC());
            wonumTextView.setText(n_problem.getWONUM());
            gdmsTextView.setText(n_problem.getWORKORDER_DESCRIPTION());
            bzTextView.setText(n_problem.getCREWID());
            finddateTextView.setText(n_problem.getFINDDATE());
            responsorTextView.setText(n_problem.getRESPONSOR());
            plTextView.setText(n_problem.getPL());
            positionTextView.setText(n_problem.getPOSITION());
            assetnumTextView.setText(n_problem.getASSETNUM());
            sbmsTextView.setText(n_problem.getASSET_DESCRIPTION());
            reasonTextView.setText(n_problem.getREASON());
            dcTextView.setText(n_problem.getSTATUS());
            jzTextView.setText(n_problem.getSOLVE());
            abcTextView.setText(n_problem.getABC());
            confirmbyTextView.setText(n_problem.getCONFIRMBY_DISPLAYNAME());
            resultTextView.setText(n_problem.getRESULT());

        }

    }


}
