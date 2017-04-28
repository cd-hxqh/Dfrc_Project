package com.dfrc.hxqh.dfrc_project.view.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.api.HttpManager;
import com.dfrc.hxqh.dfrc_project.api.HttpRequestHandler;
import com.dfrc.hxqh.dfrc_project.api.JsonUtils;
import com.dfrc.hxqh.dfrc_project.bean.Results;
import com.dfrc.hxqh.dfrc_project.constants.Constants;
import com.dfrc.hxqh.dfrc_project.model.ALNDOMAIN;
import com.dfrc.hxqh.dfrc_project.model.ASSET;
import com.dfrc.hxqh.dfrc_project.model.N_PROBLEM;
import com.dfrc.hxqh.dfrc_project.model.PERSON;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.view.widght.ShareBottomDialog;
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
    EditText prodescTextView; //问题点描述
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
    @Bind(R.id.jz_text_id)
    TextView jzTextView; //进展
    @Bind(R.id.abc_text_id)
    TextView abcTextView; //重要度
    @Bind(R.id.confirmby_text_id)
    TextView confirmbyTextView; //确认人
    @Bind(R.id.result_text_id)
    TextView resultTextView; //整改结果

    private N_PROBLEM n_problem;


    /**
     * 时间选择器
     **/
    private DatePickerDialog datePickerDialog;
    StringBuffer sb;
    private int layoutnum;


    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;

    //进展
    private String[] jinzhanTypes = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_problem_details);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();

    }

    private void geiIntentData() {
        n_problem = (N_PROBLEM) getIntent().getSerializableExtra("n_problem");
    }

    @Override
    protected void findViewById() {
        setDataListener();

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
            responsorNameTextView.setText(n_problem.getDISPLAYNAME());
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

        finddateTextView.setOnClickListener(new MydateListener());

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

            finddateTextView.setText(sb.toString());
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
        Intent intent = new Intent(N_ProblemDetailsActivity.this, PersonActivity.class);
        intent.putExtra("crewid", n_problem.getCREWID());
        startActivityForResult(intent, 0);
    }


    //设备
    @OnClick(R.id.assetnum_text_id)
    void setAssetnumTextViewOnClickListener() {
        Intent intent = new Intent(N_ProblemDetailsActivity.this, AssetChooseActivity.class);
        intent.putExtra("crewid", n_problem.getCREWID());
        startActivityForResult(intent, 0);
    }

    //进展
    @OnClick(R.id.jz_text_id)void setJzTextViewOnClickListener(){
        getOptionsValue("进展", HttpManager.getALNDOMAINURL(Constants.WTDSTATUS), jzTextView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1000:
                PERSON person = (PERSON) data.getSerializableExtra("person");
                responsorTextView.setText(person.getPERSONID());
                responsorNameTextView.setText(person.getDISPLAYNAME());
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
                        MessageUtils.showMiddleToast(N_ProblemDetailsActivity.this, getString(R.string.get_type_text));
                    } else {
                        jinzhanTypes = new String[item.size()];
                        for (int i = 0; i < item.size(); i++) {
                            jinzhanTypes[i] = item.get(i).getDESCRIPTION();
                        }
                        if (jinzhanTypes != null && jinzhanTypes.length != 0) {
                            showShareBottomDialog(title, jinzhanTypes, textview);
                        } else {
                            MessageUtils.showMiddleToast(N_ProblemDetailsActivity.this, getString(R.string.get_type_text));
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

        final ShareBottomDialog dialog = new ShareBottomDialog(N_ProblemDetailsActivity.this, jinzhanTypes, null);


        dialog.title(title)//
                .titleTextSize_SP(14.5f)//
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageUtils.showMiddleToast(N_ProblemDetailsActivity.this, typesitem[position]);
                textview.setText(typesitem[position]);
                dialog.dismiss();
            }
        });
    }

}
