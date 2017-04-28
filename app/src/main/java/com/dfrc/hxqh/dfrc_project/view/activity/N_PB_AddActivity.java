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
import com.dfrc.hxqh.dfrc_project.dialog.FlippingLoadingDialog;
import com.dfrc.hxqh.dfrc_project.model.PERSON;
import com.dfrc.hxqh.dfrc_project.model.WOTASK;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.webserviceclient.AndroidClientService;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 问题点录入
 */
public class N_PB_AddActivity extends BaseActivity {


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

    protected FlippingLoadingDialog mLoadingDialog;

    /**
     * 时间选择器
     **/
    private DatePickerDialog datePickerDialog;
    StringBuffer sb;
    private int layoutnum;


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
        setDataListener();
        n_finishdateTextView.setOnClickListener(new MydateListener());

    }


    @OnClick(R.id.sbmittext_id)
    void setSbmitImageButtonOnClickListener() {
        getLoadingDialog("正在提交...").show();
        startAsyncTask();
    }


    @OnClick(R.id.n_improver_text_id)
    void setN_prodescTextViewOnClickListener() {
        Intent intent = new Intent(N_PB_AddActivity.this, PersonActivity.class);
        intent.putExtra("crewid", "");
        startActivityForResult(intent, 0);
    }

    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }


    /**
     * 提交数据*
     */
    private void startAsyncTask() {
        final String improver = improverTextView.getText().toString();
        final String reason = reasonTextView.getText().toString();
        final String solve = solveTextView.getText().toString();
        final String finishdate = n_finishdateTextView.getText().toString();
        final String prodesc = n_prodescTextView.getText().toString();
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                if (code == WotaskDetailsActivity.NO_CODE) {
                    return AndroidClientService.MaintWOIsNo(N_PB_AddActivity.this, AccountUtils.getloginUserName(N_PB_AddActivity.this), wotask.getWOSEQUENCE(), wotask.getN_RESULT(), wotask.getN_NOTE(), wotask.getN_MEMBERS(), wonum, wotask.getASSETNUM(), AccountUtils.getloginSite(N_PB_AddActivity.this), AccountUtils.getCrewid(N_PB_AddActivity.this), improver, wotask.getPOSITION(), reason, solve, finishdate, prodesc);
                } else if (code == WotaskDetailsActivity.WT_CODE) {
                    return AndroidClientService.MaintWOPro(N_PB_AddActivity.this, AccountUtils.getloginUserName(N_PB_AddActivity.this), wotask.getWOSEQUENCE(), wotask.getN_RESULT(), wotask.getN_NOTE(), wotask.getN_MEMBERS(), wonum, wotask.getASSETNUM(), AccountUtils.getloginSite(N_PB_AddActivity.this), AccountUtils.getCrewid(N_PB_AddActivity.this), improver, wotask.getPOSITION(), reason, solve, finishdate, prodesc);

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
