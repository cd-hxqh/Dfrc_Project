package com.dfrc.hxqh.dfrc_project.view.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.api.HttpManager;
import com.dfrc.hxqh.dfrc_project.api.HttpRequestHandler;
import com.dfrc.hxqh.dfrc_project.api.JsonUtils;
import com.dfrc.hxqh.dfrc_project.bean.Results;
import com.dfrc.hxqh.dfrc_project.constants.Constants;
import com.dfrc.hxqh.dfrc_project.dialog.FlippingLoadingDialog;
import com.dfrc.hxqh.dfrc_project.model.ALNDOMAIN;
import com.dfrc.hxqh.dfrc_project.model.ASSET;
import com.dfrc.hxqh.dfrc_project.model.N_PROBLEM;
import com.dfrc.hxqh.dfrc_project.model.PERSON;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.view.widght.ShareBottomDialog;
import com.dfrc.hxqh.dfrc_project.webserviceclient.AndroidClientService;
import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnOperItemClickL;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 问题点管理新建
 */
public class N_ProblemAddActivity extends BaseActivity {
    private static String TAG = "N_ProblemDetailsActivity";

    public static final int RESPONSOR_REQUESTCODE = 1000;  //担当

    public static final int CONFIRMBY_REQUESTCODE = 1002;  //确认人


    @OnClick(R.id.title_back_id) //返回按钮
    public void setBackOnClickListener() {
        finish();
    }

    @Bind(R.id.title_name)
    TextView titleTextView; //标题

    @Bind(R.id.sbmittext_id)  //保存
            ImageButton sbmitBtn;


    @Bind(R.id.prodesc_text_id)
    EditText prodescTextView; //问题点描述
    @Bind(R.id.bz_text_id)
    TextView bzTextView; //班组
    @Bind(R.id.finddate_text_id)
    TextView finddateTextView; //发现日期
    @Bind(R.id.responsor_text_id)
    TextView responsorTextView; //担当
    @Bind(R.id.ddname_text_id)
    TextView responsorNameTextView; //担当名称
    @Bind(R.id.pl_text_id)
    EditText plTextView; //生产线
    @Bind(R.id.position_text_id)
    EditText positionTextView; //部位
    @Bind(R.id.assetnum_text_id)
    TextView assetnumTextView; //设备编号
    @Bind(R.id.sbmc_text_id)
    TextView sbmsTextView; //设备描述
    @Bind(R.id.reason_text_id)
    EditText reasonTextView; //原因
    @Bind(R.id.dc_text_id)
    EditText dcTextView; //对策
    @Bind(R.id.finishdate_text_id)
    TextView finishdateTextView; //完成日期
    @Bind(R.id.jz_text_id)
    TextView jzTextView; //进展
    @Bind(R.id.abc_text_id)
    TextView abcTextView; //重要度
    @Bind(R.id.confirmby_text_id)
    TextView confirmbyTextView; //确认人
    @Bind(R.id.result_text_id)
    TextView resultTextView; //整改结果


    /**
     * 时间选择器
     **/
    private DatePickerDialog datePickerDialog;
    StringBuffer sb;
    private int layoutnum;


    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;


    private int dateMark; //日期选择

    //进展
    private String[] jinzhanTypes = null;


    protected FlippingLoadingDialog mLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_problem_add);
        ButterKnife.bind(this);
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();

    }


    @Override
    protected void findViewById() {
        setDataListener();

    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.wtdgl_text);
        sbmitBtn.setVisibility(View.VISIBLE);
        bzTextView.setText(AccountUtils.getCrewid(N_ProblemAddActivity.this));
        finddateTextView.setOnClickListener(new MydateListener());
        finishdateTextView.setOnClickListener(new MydateListener());

    }


    /**
     * 日期选择器
     **/
    private class MydateListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.finddate_text_id) {//发现日期
                dateMark = 1;
            } else if (view.getId() == R.id.finishdate_text_id) {//完成日期
                dateMark = 2;
            }

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
            if (dateMark == 1) {
                finddateTextView.setText(sb.toString());
            } else if (dateMark == 2) {
                finishdateTextView.setText(sb.toString());
            }
        }
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


    //担当
    @OnClick(R.id.responsor_text_id)
    void setResponsorTextViewOnClickListener() {
        Intent intent = new Intent(N_ProblemAddActivity.this, PersonActivity.class);
        intent.putExtra("crewid", AccountUtils.getCrewid(this));
        startActivityForResult(intent, RESPONSOR_REQUESTCODE);
    }


    //设备
    @OnClick(R.id.assetnum_text_id)
    void setAssetnumTextViewOnClickListener() {
        Intent intent = new Intent(N_ProblemAddActivity.this, AssetChooseActivity.class);
        intent.putExtra("crewid", AccountUtils.getCrewid(this));
        startActivityForResult(intent, AssetChooseActivity.ASSET_CODE);
    }

    //进展
    @OnClick(R.id.jz_text_id)
    void setJzTextViewOnClickListener() {
        getOptionsValue("进展", HttpManager.getALNDOMAINURL(Constants.WTDSTATUS), jzTextView);
    }

    //重要度
    @OnClick(R.id.abc_text_id)
    void setAbcTextViewOnClickListener() {
        getOptionsValue("重要度", HttpManager.getALNDOMAINURL(Constants.ABCTYPE), abcTextView);
    }

    //确认人
    @OnClick(R.id.confirmby_text_id)
    void setConfirmbyTextViewOnClickListeber() {
        Intent intent = new Intent(N_ProblemAddActivity.this, PersonActivity.class);
        intent.putExtra("crewid", AccountUtils.getCrewid(this));
        startActivityForResult(intent, CONFIRMBY_REQUESTCODE);
    }

    //整改结果
    @OnClick(R.id.result_text_id)
    void setResultTextViewOnClickListener() {
        getOptionsValue("整改结果", HttpManager.getALNDOMAINURL(Constants.N_PRORESULT), resultTextView);
    }


    //修改提交
    @OnClick(R.id.sbmittext_id)
    void setSbmitBtnOnClickListener() {
        getLoadingDialog("正在提交").show();
        saveAsyncTask();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESPONSOR_REQUESTCODE:
                if (requestCode == RESPONSOR_REQUESTCODE) {
                    PERSON person = (PERSON) data.getSerializableExtra("person");
                    responsorTextView.setText(person.getPERSONID());
                    responsorNameTextView.setText(person.getDISPLAYNAME());
                } else if (requestCode == CONFIRMBY_REQUESTCODE) {
                    PERSON person1 = (PERSON) data.getSerializableExtra("person");
                    confirmbyTextView.setText(person1.getPERSONID());
                }
                break;
            case AssetChooseActivity.ASSET_CODE:
                ASSET asset = (ASSET) data.getSerializableExtra("asset");
                assetnumTextView.setText(asset.getASSETNUM());
                sbmsTextView.setText(asset.getDESCRIPTION());
                break;
        }
    }


    //获取进展
    private void getOptionsValue(final String title, String url, final TextView textview) {
        {
            HttpManager.getData(this, url, new HttpRequestHandler<Results>() {


                @Override
                public void onSuccess(Results data) {

                }

                @Override
                public void onSuccess(Results data, int totalPages, int currentPage) {
                    ArrayList<ALNDOMAIN> item = JsonUtils.parsingALNDOMAIN(data.getResultlist());
                    if (item == null || item.isEmpty()) {
                        MessageUtils.showMiddleToast(N_ProblemAddActivity.this, getString(R.string.get_type_text));
                    } else {
                        jinzhanTypes = new String[item.size()];
                        for (int i = 0; i < item.size(); i++) {
                            jinzhanTypes[i] = item.get(i).getDESCRIPTION();
                        }
                        if (jinzhanTypes != null && jinzhanTypes.length != 0) {
                            showShareBottomDialog(title, jinzhanTypes, textview);
                        } else {
                            MessageUtils.showMiddleToast(N_ProblemAddActivity.this, getString(R.string.get_type_text));
                        }

                    }

                }

                @Override
                public void onFailure(String error) {

                }
            });

        }
    }


    /**
     * 显示选项框
     **/
    private void showShareBottomDialog(String title, final String[] typesitem, final TextView textview) {

        final ShareBottomDialog dialog = new ShareBottomDialog(N_ProblemAddActivity.this, jinzhanTypes, null);


        dialog.title(title)//
                .titleTextSize_SP(14.5f)//
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageUtils.showMiddleToast(N_ProblemAddActivity.this, typesitem[position]);
                textview.setText(typesitem[position]);
                dialog.dismiss();
            }
        });
    }


    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }


    /**
     * 提交数据*
     */
    private void saveAsyncTask() {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return AndroidClientService.CreateProblem(N_ProblemAddActivity.this, getN_problemInfo());
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(N_ProblemAddActivity.this, s);
                finish();
            }
        }.execute();


    }


    //封装修改的数据
    private N_PROBLEM getN_problemInfo() {
        N_PROBLEM n_problem = new N_PROBLEM();
        String prodesc = prodescTextView.getText().toString(); //问题点描述
        String crewid = bzTextView.getText().toString(); //班组
        String finddate = finddateTextView.getText().toString(); //发现日期
        String responsor = responsorTextView.getText().toString(); //担当
        String pl = plTextView.getText().toString(); //生产线
        String position = positionTextView.getText().toString(); //部位
        String assetnum = assetnumTextView.getText().toString(); //设备编号
        String reason = reasonTextView.getText().toString(); //原因
        String solve = dcTextView.getText().toString(); //对策
        String finishdate = finishdateTextView.getText().toString(); //完成日期
        String status = jzTextView.getText().toString(); //进展
        String abc = abcTextView.getText().toString(); //重要度
        String confirmby = confirmbyTextView.getText().toString(); //确认人
        String result = resultTextView.getText().toString(); //整改结果
        n_problem.setPRODESC(prodesc);
        n_problem.setCREWID(crewid);
        n_problem.setFINDDATE(finddate);
        n_problem.setRESPONSOR(responsor);
        n_problem.setPL(pl);
        n_problem.setPOSITION(position);
        n_problem.setASSETNUM(assetnum);
        n_problem.setREASON(reason);
        n_problem.setSOLVE(solve);
        n_problem.setFINISHDATE(finishdate);
        n_problem.setSTATUS(status);
        n_problem.setABC(abc);
        n_problem.setCONFIRMBY(confirmby);
        n_problem.setRESULT(result);

        return n_problem;
    }
}