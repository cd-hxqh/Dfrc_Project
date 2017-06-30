package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.dao.WoTaskDao;
import com.dfrc.hxqh.dfrc_project.dao.WoTaskOKDao;
import com.dfrc.hxqh.dfrc_project.model.PERSON;
import com.dfrc.hxqh.dfrc_project.model.WOTASK;
import com.dfrc.hxqh.dfrc_project.model.WOTASKOK;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 点击明细行详情
 */
public class WotaskDetailsLocationActivity extends BaseActivity {

    public static final int NO_CODE = 100;
    public static final int WT_CODE = 101;

    @OnClick(R.id.title_back_id)
    public void setBackOnClickLIstener() {
        finish();
    }

    @Bind(R.id.title_name)
    TextView titleTextView; //标题
    @Bind(R.id.sbmittext_id)
    ImageButton sbmitBtn; //保存

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
    @Bind(R.id.n_note_text_id)
    EditText n_noteTextView; //预知项目结果
    @Bind(R.id.n_responsor_text_id)
    TextView n_responsorTextView; //实施责任人
    @Bind(R.id.n_members_text_id)
    TextView n_membersTextView; //实施人员

    private WOTASK wotask;
    private String wonum;

    private WoTaskOKDao woTaskOKDao;
    private WoTaskDao woTaskDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wotask_details);
        ButterKnife.bind(this);
        geiIntentData();
        initDAO();
        findViewById();
        initView();

    }

    //初始化DAO
    private void initDAO() {
        woTaskOKDao = new WoTaskOKDao(this);
        woTaskDao = new WoTaskDao(this);
    }

    private void geiIntentData() {
        wotask = (WOTASK) getIntent().getSerializableExtra("wotask");
        wonum = getIntent().getExtras().getString("wonum");
    }

    @Override
    protected void findViewById() {


    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.wotask_text);
        sbmitBtn.setVisibility(View.VISIBLE);
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
            n_noteTextView.setText(wotask.getN_NOTE());
            n_responsorTextView.setText(wotask.getRESPONSOR_DISPLAYNAME());
            n_membersTextView.setText(wotask.getN_MEMBERS());


        }

    }

    //保存
    @OnClick(R.id.sbmittext_id)
    void setSbmitBtnOnClickListener() {
        getLoadingDialog("正在提交数据...").show();
//        saveAsyncTask();
        saveWoTask();
    }

    //OK
    @OnClick(R.id.ok_btn_id)
    void setOkBtnOnClickListener() {
        getLoadingDialog("正在提交数据...").show();
        n_resultTextView.setText("OK");
        OKSubmit();
    }

    //NO
    @OnClick(R.id.no_text_id)
    void setNoBtnOnClickListener() {
        n_resultTextView.setText("NG");
        Intent intent = getIntent();
        intent.setClass(WotaskDetailsLocationActivity.this, N_PB_AddActivity.class);
        intent.putExtra("code", NO_CODE);
        startActivityForResult(intent, 0);
    }

    //问题点
    @OnClick(R.id.n_pb_text_id)
    void setn_pbBtnOnClickListener() {
        Intent intent = getIntent();
        intent.setClass(WotaskDetailsLocationActivity.this, N_PB_AddActivity.class);
        intent.putExtra("code", WT_CODE);
        startActivityForResult(intent, 0);
    }


    //实施人员
    @OnClick(R.id.n_members_text_id)
    void setN_membersTextViewOnClickListener() {
        Intent intent = getIntent();
        intent.setClass(WotaskDetailsLocationActivity.this, PersonActivity.class);
        startActivityForResult(intent, 0);
    }


//    /**
//     * 修改*
//     */
//    private void saveAsyncTask() {
//
//        final String n_result = n_resultTextView.getText().toString();
//        final String n_note = n_noteTextView.getText().toString();
//        final String n_members = n_membersTextView.getText().toString();
//        new AsyncTask<String, String, String>() {
//            @Override
//            protected String doInBackground(String... strings) {
//                return AndroidClientService.UpdateMbo(WotaskDetailsLocationActivity.this, JsonUtils.potoWOTASK(n_result, n_note, n_members), Constants.WOTASK_NAME, "WOTASKID", wotask.getWOTASKID());
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                mLoadingDialog.dismiss();
//                MessageUtils.showMiddleToast(WotaskDetailsLocationActivity.this, s);
//                finish();
//
//
//            }
//        }.execute();
//
//
//    }


    /**
     * 保存数据
     **/
    private void saveWoTask() {
        String n_result = n_resultTextView.getText().toString();
        String n_note = n_noteTextView.getText().toString();
        String n_members = n_membersTextView.getText().toString();
        wotask.setN_RESULT(n_result);
        wotask.setN_NOTE(n_note);
        wotask.setN_MEMBERS(n_members);
        wotask.setUPDATE(1); //修改
        woTaskDao.update(wotask);
        colseProgressBar();
        MessageUtils.showMiddleToast(WotaskDetailsLocationActivity.this, getString(R.string.data_success_text));

    }

    /**
     * OK提交数据
     **/
    private void OKSubmit() {
        String n_result = n_resultTextView.getText().toString();
        String n_note = n_noteTextView.getText().toString();
        WOTASKOK wotaskok = woTaskOKDao.findByWosequence(wotask.getWOSEQUENCE());
        if (null == wotaskok) {
            wotaskok = new WOTASKOK();
            wotaskok.setWONUM(wonum);
            wotaskok.setWOSEQUENCE(wotask.getWOSEQUENCE());
            wotaskok.setN_RESULT(n_result);
            wotaskok.setN_NOTE(n_note);
            wotaskok.setN_MEMBERS(AccountUtils.getloginUserName(WotaskDetailsLocationActivity.this));
            woTaskOKDao.create(wotaskok);
        } else {
            wotaskok.setN_RESULT(n_result);
            wotaskok.setN_NOTE(n_note);
            woTaskOKDao.update(wotaskok);
        }
        colseProgressBar();
        MessageUtils.showMiddleToast(WotaskDetailsLocationActivity.this, getString(R.string.data_success_text));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1000:
                PERSON person = (PERSON) data.getSerializableExtra("person");
                n_membersTextView.setText(person.getDISPLAYNAME());
                break;
        }
    }


}
