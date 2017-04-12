package com.dfrc.hxqh.dfrc_project.view.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.WOTASK;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 设备详情
 */
public class WotaskDetailsActivity extends BaseActivity {


    @OnClick(R.id.title_back_id)
    public void setBackOnClickLIstener() {
        finish();
    }

    @Bind(R.id.title_name)
    TextView titleTextView; //标题

    @Bind(R.id.wosequence_text_id)
    TextView wosequenceTextView; //序号
    @Bind(R.id.assetnum_text_id)
    TextView assetnumTextView; //设备编号
    @Bind(R.id.sbmc_text_id)
    TextView sbmcTextView; //设备名称
    @Bind(R.id.item_text_id)
    TextView itemTextView; //项目
    @Bind(R.id.position_text_id)
    TextView positionTextView; //部位
    @Bind(R.id.type_text_id)
    TextView typeTextView; //类别
    @Bind(R.id.method_text_id)
    TextView methodTextView; //点检方法
    @Bind(R.id.rule_text_id)
    TextView ruleTextView; //基准
    @Bind(R.id.solve_text_id)
    TextView solveTextView; //异常处置
    @Bind(R.id.hours_text_id)
    TextView hoursTextView; //点检工时
    @Bind(R.id.abc_text_id)
    TextView abcTextView; //重要度
    @Bind(R.id.cycle_text_id)
    TextView cycleTextView; //周期
    @Bind(R.id.n_result_text_id)
    TextView n_resultTextView; //结果
    @Bind(R.id.n_responsor_text_id)
    TextView n_responsorTextView; //实施责任人
    @Bind(R.id.n_members_text_id)
    TextView n_membersTextView; //实施人员

    private WOTASK wotask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wotask_details);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        wotask = (WOTASK) getIntent().getSerializableExtra("wotask");
    }

    @Override
    protected void findViewById() {


    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.wotask_text);
        if (wotask != null) {
            wosequenceTextView.setText(wotask.getWOSEQUENCE());
            assetnumTextView.setText(wotask.getASSETNUM());
            sbmcTextView.setText(wotask.getASSET_DESCRIPTION());
            itemTextView.setText(wotask.getITEM());
            positionTextView.setText(wotask.getPOSITION());
            typeTextView.setText(wotask.getREFBOOKLINE_TYPE());
            methodTextView.setText(wotask.getREFBOOKLINE_METHOD());
            ruleTextView.setText(wotask.getWPLINE_RULE());
            solveTextView.setText(wotask.getWPLINE_SOLVE());
            hoursTextView.setText(wotask.getREFBOOKLINE_HOURS());
            abcTextView.setText(wotask.getREFBOOKLINE_ABC());
            cycleTextView.setText(wotask.getREFBOOKLINE_CYCLE());
            n_resultTextView.setText(wotask.getN_RESULT());
            n_responsorTextView.setText(wotask.getRESPONSOR_DISPLAYNAME());
            n_membersTextView.setText(wotask.getN_MEMBERS());


        }

    }




}
