package com.dfrc.hxqh.dfrc_project.view.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.GZWORKORDER;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 故障工单详情
 */
public class GZworkorderDetailsActivity extends BaseActivity {
    private static String TAG = "GZworkorderDetailsActivity";


    @Bind(R.id.title_back_id)
    ImageView backImageView; //返回按钮

    @Bind(R.id.title_name)
    TextView titleTextView; //标题


    @Bind(R.id.wonum_text_id)
    TextView wonumTextView; //工单
    @Bind(R.id.description_text_id)
    TextView descriptionTextView; //描述
    @Bind(R.id.status_text_id)
    TextView statusTextView; //状态
    @Bind(R.id.n_happendate_text_id)
    TextView n_happendateTextView; //发生日期
    @Bind(R.id.estdur_text_id)
    TextView estdurTextView; //持续时间
    @Bind(R.id.n_plstoptime_text_id)
    TextView n_plstoptimeTextView; //生产线停止时间
    @Bind(R.id.n_failurproblem_text_id)
    TextView n_failurproblemTextView; //故障现象
    @Bind(R.id.n_failurcause_text_id)
    TextView n_failurcauseTextView; //故障原因
    @Bind(R.id.dc_text_id)
    TextView dcTextView; //对策


    private GZWORKORDER gzworkorder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzworkorder_details);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        gzworkorder = (GZWORKORDER) getIntent().getSerializableExtra("gzworkorder");
    }

    @Override
    protected void findViewById() {


    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.gzjl_text);
        if (gzworkorder != null) {
            wonumTextView.setText(gzworkorder.getWONUM());
            descriptionTextView.setText(gzworkorder.getDESCRIPTION());
            statusTextView.setText(gzworkorder.getSTATUS());
            n_happendateTextView.setText(gzworkorder.getN_HAPPENDATE());
            estdurTextView.setText(gzworkorder.getESTDUR());
            n_plstoptimeTextView.setText(gzworkorder.getN_PLSTOPTIME());
            n_failurproblemTextView.setText(gzworkorder.getN_FAILURPROBLEM());
            n_failurcauseTextView.setText(gzworkorder.getN_FAILURCAUSE());
            dcTextView.setText(gzworkorder.getN_COUNTERMEASURE());

        }

    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageViewOnClickListener() {
        finish();
    }


}
