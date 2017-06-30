package com.dfrc.hxqh.dfrc_project.view.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.dao.WoTaskNgDao;
import com.dfrc.hxqh.dfrc_project.dao.WoTaskProDao;
import com.dfrc.hxqh.dfrc_project.model.PERSON;
import com.dfrc.hxqh.dfrc_project.model.WOTASK;
import com.dfrc.hxqh.dfrc_project.model.WOTASKNG;
import com.dfrc.hxqh.dfrc_project.model.WOTASKPRO;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.until.NetWorkHelper;
import com.dfrc.hxqh.dfrc_project.webserviceclient.AndroidClientService;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 问题点录入
 */
public class N_PB_AddActivity extends BaseActivity {

    private static final String TAG = "N_PB_AddActivity";

    @OnClick(R.id.title_back_id)
    public void setBackOnClickLIstener() {
        finish();
    }

    @Bind(R.id.title_name)
    TextView titleTextView; //标题
    @Bind(R.id.sbmittext_id)
    ImageButton sbmitImageButton; //提交


    @Bind(R.id.n_prodesc_text_id)
    TextView n_prodescTextView; //问题点录入
    @Bind(R.id.n_solve_text_id)
    TextView solveTextView; //解决办法
    @Bind(R.id.reason_text_id)
    TextView reasonTextView; //原因
    @Bind(R.id.n_improver_text_id)
    TextView improverTextView; //改善担当
    @Bind(R.id.n_finishdate_text_id)
    TextView n_finishdateTextView; //完成日期

    private WOTASK wotask;
    private String wonum;
    private int code;
    private String crewid; //班组
    private String siteid; //站点


    /**
     * 时间选择器
     **/
    private DatePickerDialog datePickerDialog;
    StringBuffer sb;
    private int layoutnum;

    private WOTASKNG wotaskng; //NG
    private WOTASKPRO wotaskpro; //问题点


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_pb_add);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();

    }


    private void geiIntentData() {
        wotask = (WOTASK) getIntent().getSerializableExtra("wotask");
        wonum = getIntent().getExtras().getString("wonum");
        code = getIntent().getExtras().getInt("code");

    }

    @Override
    protected void findViewById() {


    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.wtdll_text);
        sbmitImageButton.setVisibility(View.VISIBLE);
        improverTextView.setText(AccountUtils.getloginUserName(N_PB_AddActivity.this));
        setDataListener();
        n_finishdateTextView.setOnClickListener(new MydateListener());
        if (code == WotaskDetailsActivity.NO_CODE) {
            wotaskng = isWoTaskNg();
            if (null != wotaskng) {
                n_prodescTextView.setText(wotaskng.getPRODESC());
                solveTextView.setText(wotaskng.getSOLVE());
                reasonTextView.setText(wotaskng.getREASON());
                improverTextView.setText(wotaskng.getRESPONSOR());
                n_finishdateTextView.setText(wotaskng.getFINISHDATE());
            }
        } else if (code == WotaskDetailsActivity.WT_CODE) {
            wotaskpro = isWoTaskPro();
            if (null != wotaskpro) {
                n_prodescTextView.setText(wotaskpro.getPRODESC());
                solveTextView.setText(wotaskpro.getSOLVE());
                reasonTextView.setText(wotaskpro.getREASON());
                improverTextView.setText(wotaskpro.getRESPONSOR());
                n_finishdateTextView.setText(wotaskpro.getFINISHDATE());
            }
        }


    }


    @OnClick(R.id.sbmittext_id)
    void setSbmitImageButtonOnClickListener() {
        getLoadingDialog("正在保存...").show();


        if (code == WotaskDetailsActivity.NO_CODE) {
            if (NetWorkHelper.isWifi(this) && !AccountUtils.getIsOffLine(this)) {
                startAsyncTask();
            } else {
                saveWoTaskNG();
            }
        } else if (code == WotaskDetailsActivity.WT_CODE) {
            if (NetWorkHelper.isWifi(this) && !AccountUtils.getIsOffLine(this)) {
                startAsyncTask();
            } else {
                saveWoTaskPRO();
            }
        }


    }

    //改善担当
    @OnClick(R.id.n_improver_text_id)
    void setN_prodescTextViewOnClickListener() {
        Intent intent = getIntent();
        intent.setClass(N_PB_AddActivity.this, PersonActivity.class);
        startActivityForResult(intent, 0);
    }


    /**
     * 提交数据*
     */
    private void startAsyncTask() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                if (code == WotaskDetailsActivity.NO_CODE) {
                    return AndroidClientService.MaintWOIsNo(N_PB_AddActivity.this, onLineWotaskNg());
                } else if (code == WotaskDetailsActivity.WT_CODE) {
                    return AndroidClientService.MaintWOPro(N_PB_AddActivity.this, onLineWotaskPro());

                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(N_PB_AddActivity.this, s);


            }
        }.execute();


    }


    /**
     * 离线保存NG
     **/

    private void saveWoTaskNG() {
        if (null == wotaskng) {
            wotaskng = new WOTASKNG();
            wotaskng.setPERSONID(AccountUtils.getloginUserName(N_PB_AddActivity.this));
            wotaskng.setWOSEQUENCE(wotask.getWOSEQUENCE());
            wotaskng.setN_RESULT("NG");
            wotaskng.setN_NOTE(wotask.getN_NOTE());
            wotaskng.setN_MEMBERS(wotask.getN_MEMBERS());
            wotaskng.setWONUM(wotask.getWONUM());
            wotaskng.setASSETNUM(wotask.getASSETNUM());
            wotaskng.setSITEID(AccountUtils.getloginSite(N_PB_AddActivity.this));
            wotaskng.setCREWID(AccountUtils.getCrewid(N_PB_AddActivity.this));
            wotaskng.setCREWID(AccountUtils.getCrewid(N_PB_AddActivity.this));
            wotaskng.setPOSITION(wotask.getPOSITION());

            String improver = improverTextView.getText().toString();
            String reason = reasonTextView.getText().toString();
            String solve = solveTextView.getText().toString();
            String finishdate = n_finishdateTextView.getText().toString();
            String prodesc = n_prodescTextView.getText().toString();

            wotaskng.setRESPONSOR(improver);
            wotaskng.setREASON(reason);
            wotaskng.setSOLVE(solve);
            wotaskng.setFINISHDATE(finishdate);
            wotaskng.setPRODESC(prodesc);

            new WoTaskNgDao(N_PB_AddActivity.this).create(wotaskng);
        } else {
            String improver = improverTextView.getText().toString();
            String reason = reasonTextView.getText().toString();
            String solve = solveTextView.getText().toString();
            String finishdate = n_finishdateTextView.getText().toString();
            String prodesc = n_prodescTextView.getText().toString();
            wotaskng.setRESPONSOR(improver);
            wotaskng.setREASON(reason);
            wotaskng.setSOLVE(solve);
            wotaskng.setFINISHDATE(finishdate);
            wotaskng.setPRODESC(prodesc);
            new WoTaskNgDao(N_PB_AddActivity.this).update(wotaskng);
        }
        mLoadingDialog.dismiss();
        MessageUtils.showMiddleToast(N_PB_AddActivity.this, getString(R.string.data_success_text));
        finish();


    }

    /**
     * 在线保存NG
     **/
    private WOTASKNG onLineWotaskNg() {
        wotaskng = new WOTASKNG();
        wotaskng.setPERSONID(AccountUtils.getloginUserName(N_PB_AddActivity.this));
        wotaskng.setWOSEQUENCE(wotask.getWOSEQUENCE());
        wotaskng.setN_RESULT("NG");
        wotaskng.setN_NOTE(wotask.getN_NOTE());
        wotaskng.setN_MEMBERS(wotask.getN_MEMBERS());
        wotaskng.setWONUM(wotask.getWONUM());
        wotaskng.setASSETNUM(wotask.getASSETNUM());
        wotaskng.setSITEID(AccountUtils.getloginSite(N_PB_AddActivity.this));
        wotaskng.setCREWID(AccountUtils.getCrewid(N_PB_AddActivity.this));
        wotaskng.setCREWID(AccountUtils.getCrewid(N_PB_AddActivity.this));
        wotaskng.setPOSITION(wotask.getPOSITION());

        String improver = improverTextView.getText().toString();
        String reason = reasonTextView.getText().toString();
        String solve = solveTextView.getText().toString();
        String finishdate = n_finishdateTextView.getText().toString();
        String prodesc = n_prodescTextView.getText().toString();

        wotaskng.setRESPONSOR(improver);
        wotaskng.setREASON(reason);
        wotaskng.setSOLVE(solve);
        wotaskng.setFINISHDATE(finishdate);
        wotaskng.setPRODESC(prodesc);
        return wotaskng;
    }


    /**
     * 离线 保存PRO
     **/

    private void saveWoTaskPRO() {
        if (null == wotaskpro) {
            wotaskpro = new WOTASKPRO();
            wotaskpro.setPERSONID(AccountUtils.getloginUserName(N_PB_AddActivity.this));
            wotaskpro.setWOSEQUENCE(wotask.getWOSEQUENCE());
            wotaskpro.setN_RESULT(wotask.getN_RESULT());
            wotaskpro.setN_NOTE(wotask.getN_NOTE());
            wotaskpro.setN_MEMBERS(wotask.getN_MEMBERS());
            wotaskpro.setWONUM(wotask.getWONUM());
            wotaskpro.setASSETNUM(wotask.getASSETNUM());
            wotaskpro.setSITEID(AccountUtils.getloginSite(N_PB_AddActivity.this));
            wotaskpro.setCREWID(AccountUtils.getCrewid(N_PB_AddActivity.this));
            wotaskpro.setCREWID(AccountUtils.getCrewid(N_PB_AddActivity.this));
            wotaskpro.setPOSITION(wotask.getPOSITION());


            String improver = improverTextView.getText().toString();
            String reason = reasonTextView.getText().toString();
            String solve = solveTextView.getText().toString();
            String finishdate = n_finishdateTextView.getText().toString();
            String prodesc = n_prodescTextView.getText().toString();

            wotaskpro.setRESPONSOR(improver);
            wotaskpro.setREASON(reason);
            wotaskpro.setSOLVE(solve);
            wotaskpro.setFINISHDATE(finishdate);
            wotaskpro.setPRODESC(prodesc);

            new WoTaskProDao(N_PB_AddActivity.this).create(wotaskpro);
        } else {
            String improver = improverTextView.getText().toString();
            String reason = reasonTextView.getText().toString();
            String solve = solveTextView.getText().toString();
            String finishdate = n_finishdateTextView.getText().toString();
            String prodesc = n_prodescTextView.getText().toString();
            wotaskpro.setRESPONSOR(improver);
            wotaskpro.setREASON(reason);
            wotaskpro.setSOLVE(solve);
            wotaskpro.setFINISHDATE(finishdate);
            wotaskpro.setPRODESC(prodesc);
            new WoTaskProDao(N_PB_AddActivity.this).update(wotaskpro);
        }
        mLoadingDialog.dismiss();
        MessageUtils.showMiddleToast(N_PB_AddActivity.this, getString(R.string.data_success_text));
        finish();


    }


    /**
     * 在线保存NG
     **/
    private WOTASKPRO onLineWotaskPro() {
        wotaskpro = new WOTASKPRO();
        wotaskpro.setPERSONID(AccountUtils.getloginUserName(N_PB_AddActivity.this));
        wotaskpro.setWOSEQUENCE(wotask.getWOSEQUENCE());
        wotaskpro.setN_RESULT(wotask.getN_RESULT());
        wotaskpro.setN_NOTE(wotask.getN_NOTE());
        wotaskpro.setN_MEMBERS(wotask.getN_MEMBERS());
        wotaskpro.setWONUM(wotask.getWONUM());
        wotaskpro.setASSETNUM(wotask.getASSETNUM());
        wotaskpro.setSITEID(AccountUtils.getloginSite(N_PB_AddActivity.this));
        wotaskpro.setCREWID(AccountUtils.getCrewid(N_PB_AddActivity.this));
        wotaskpro.setCREWID(AccountUtils.getCrewid(N_PB_AddActivity.this));
        wotaskpro.setPOSITION(wotask.getPOSITION());


        String improver = improverTextView.getText().toString();
        String reason = reasonTextView.getText().toString();
        String solve = solveTextView.getText().toString();
        String finishdate = n_finishdateTextView.getText().toString();
        String prodesc = n_prodescTextView.getText().toString();

        wotaskpro.setRESPONSOR(improver);
        wotaskpro.setREASON(reason);
        wotaskpro.setSOLVE(solve);
        wotaskpro.setFINISHDATE(finishdate);
        wotaskpro.setPRODESC(prodesc);

        return wotaskpro;
    }


    /**
     * 判断该任务是否已操作了NG选项
     **/
    private WOTASKNG isWoTaskNg() {
        return new WoTaskNgDao(N_PB_AddActivity.this).findByWosequence(wotask.getWOSEQUENCE());
    }

    /**
     * 判断该任务是否已操作了PRO选项
     **/

    private WOTASKPRO isWoTaskPro() {
        return new WoTaskProDao(N_PB_AddActivity.this).findByWosequence(wotask.getWOSEQUENCE());

    }


    /**
     * 设置时间选择器*
     */
    private void setDataListener() {

        final Calendar objTime = Calendar.getInstance();
        int iYear = objTime.get(Calendar.YEAR);
        int iMonth = objTime.get(Calendar.MONTH);
        int iDay = objTime.get(Calendar.DAY_OF_MONTH);
        int hour = objTime.get(Calendar.HOUR_OF_DAY);

        int minute = objTime.get(Calendar.MINUTE);


        datePickerDialog = new DatePickerDialog(this, new datelistener(), iYear, iMonth, iDay);
    }

    /**
     * 日期选择器
     **/
    private class MydateListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            layoutnum = 0;
            sb = new StringBuffer();
            layoutnum = view.getId();
            datePickerDialog.show();
        }
    }


    private class datelistener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            sb = new StringBuffer();
            monthOfYear = monthOfYear + 1;
            if (dayOfMonth < 10) {
                sb.append(year + "-" + monthOfYear + "-" + "0" + dayOfMonth);
            } else {
                sb.append(year + "-" + monthOfYear + "-" + dayOfMonth);
            }

            n_finishdateTextView.setText(sb.toString());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1000:
                PERSON person = (PERSON) data.getSerializableExtra("person");
                improverTextView.setText(person.getPERSONID());
                break;
        }
    }
}
