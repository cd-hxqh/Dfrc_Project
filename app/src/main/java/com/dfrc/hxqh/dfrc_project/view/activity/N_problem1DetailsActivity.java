package com.dfrc.hxqh.dfrc_project.view.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.N_PROBLEM;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 点检问题点记录
 */
public class N_problem1DetailsActivity extends BaseActivity {
    private static String TAG = "N_problem1DetailsActivity";


    @Bind(R.id.title_back_id)
    ImageView backImageView; //返回按钮

    @Bind(R.id.title_name)
    TextView titleTextView; //标题


    @Bind(R.id.wonum_text_id)
    TextView wonumTextView; //工单
    @Bind(R.id.finddate_text_id)
    TextView finddateTextView; //发现日期
    @Bind(R.id.n_problemnum_text_id)
    TextView n_problemnumTextView; //问题点编号
    @Bind(R.id.prodesc_text_id)
    TextView prodescTextView; //问题点描述
    @Bind(R.id.reason_text_id)
    TextView reasonTextView; //原因
    @Bind(R.id.dc_text_id)
    TextView dcTextView; //对策
    @Bind(R.id.jz_text_id)
    TextView jzTextView; //进展
    @Bind(R.id.finishdate_text_id)
    TextView finishdateTextView; //完成日期
    @Bind(R.id.abc_text_id)
    TextView abcTextView; //重要度
    @Bind(R.id.result_text_id)
    TextView resultTextView; //整改结果
    @Bind(R.id.confirmby_text_id)
    TextView confirmbyTextView; //确认人


    private N_PROBLEM n_problem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_problem1_details);
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
        titleTextView.setText(R.string.djwtdjl_text);
        if (n_problem != null) {
            wonumTextView.setText(n_problem.getWONUM());
            finddateTextView.setText(n_problem.getFINDDATE());
            n_problemnumTextView.setText(n_problem.getN_PROBLEMNUM());
            prodescTextView.setText(n_problem.getPRODESC());
            reasonTextView.setText(n_problem.getREASON());
            dcTextView.setText(n_problem.getSOLVE());
            jzTextView.setText(n_problem.getSTATUS());
            finishdateTextView.setText(n_problem.getFINISHDATE());
            abcTextView.setText(n_problem.getABC());
            resultTextView.setText(n_problem.getRESULT());
            confirmbyTextView.setText(n_problem.getDISPLAYNAME());

        }

    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageViewOnClickListener() {
        finish();
    }


}
