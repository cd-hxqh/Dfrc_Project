package com.dfrc.hxqh.dfrc_project.view.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
 * 问题点管理
 */
public class N_ProblemDetailsActivity extends BaseActivity {
    private static String TAG = "N_ProblemDetailsActivity";

    public static final int RESPONSOR_REQUESTCODE = 1000;  //担当

    public static final int CONFIRMBY_REQUESTCODE = 1002;  //确认人


    @OnClick(R.id.title_back_id) //返回按钮
    public void setBackOnClickListener() {
        finish();
    }

    @Bind(R.id.title_name)
    TextView titleTextView; //标题

    @Bind(R.id.title_add)
    ImageView menuImageView; //菜单
    PopupWindow popupWindow;


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

    private N_PROBLEM n_problem;


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


    private LinearLayout saveLinearLayout; //保存
    private LinearLayout imageLinearLayout; //图片


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
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setImageResource(R.mipmap.ic_more);
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
            dcTextView.setText(n_problem.getSOLVE());
            finishdateTextView.setText(n_problem.getFINISHDATE());
            jzTextView.setText(n_problem.getSTATUS());
            abcTextView.setText(n_problem.getABC());
            confirmbyTextView.setText(n_problem.getCONFIRMBY());
            resultTextView.setText(n_problem.getRESULT());

        }

        finddateTextView.setOnClickListener(new MydateListener());
        finishdateTextView.setOnClickListener(new MydateListener());

    }


    //菜单事件
    @OnClick(R.id.title_add)
    void setMenuImageViewOnClickListener() {
        showPopupWindow(menuImageView);
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
        Intent intent = new Intent(N_ProblemDetailsActivity.this, PersonActivity.class);
        intent.putExtra("crewid", n_problem.getCREWID());
        startActivityForResult(intent, RESPONSOR_REQUESTCODE);
    }


    //设备
    @OnClick(R.id.assetnum_text_id)
    void setAssetnumTextViewOnClickListener() {
        Intent intent = new Intent(N_ProblemDetailsActivity.this, AssetChooseActivity.class);
        intent.putExtra("crewid", n_problem.getCREWID());
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
        Intent intent = new Intent(N_ProblemDetailsActivity.this, PersonActivity.class);
        intent.putExtra("crewid", n_problem.getCREWID());
        startActivityForResult(intent, CONFIRMBY_REQUESTCODE);
    }

    //整改结果
    @OnClick(R.id.result_text_id)
    void setResultTextViewOnClickListener() {
        getOptionsValue("整改结果", HttpManager.getALNDOMAINURL(Constants.N_PRORESULT), resultTextView);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESPONSOR_REQUESTCODE:
                PERSON person = (PERSON) data.getSerializableExtra("person");
                if (requestCode == RESPONSOR_REQUESTCODE) {
                    responsorTextView.setText(person.getPERSONID());
                    responsorNameTextView.setText(person.getDISPLAYNAME());
                } else if (requestCode == CONFIRMBY_REQUESTCODE) {
                    confirmbyTextView.setText(person.getPERSONID());
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
                return AndroidClientService.UpdateMbo(N_ProblemDetailsActivity.this, JsonUtils.potoN_PROBLEM(getN_problemInfo(n_problem)), Constants.N_PROBLEM_NAME, "N_PROBLEMNUM", n_problem.getN_PROBLEMNUM());
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();

                MessageUtils.showMiddleToast(N_ProblemDetailsActivity.this, JsonUtils.showResults(s));


            }
        }.execute();


    }


    //封装修改的数据
    private N_PROBLEM getN_problemInfo(N_PROBLEM n_problem1) {
        N_PROBLEM n_problem = new N_PROBLEM();
        String prodesc = prodescTextView.getText().toString(); //问题点描述
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
        if (!prodesc.equals(n_problem1.getPRODESC())) {
            n_problem.setPRODESC(prodesc);
        }
        if (!finddate.equals(n_problem1.getFINDDATE())) {
            n_problem.setFINDDATE(finddate);
        }
        if (!responsor.equals(n_problem1.getRESPONSOR())) {
            n_problem.setRESPONSOR(responsor);
        }
        if (!pl.equals(n_problem1.getPL())) {
            n_problem.setPL(pl);
        }
        if (!position.equals(n_problem1.getPOSITION())) {
            n_problem.setPOSITION(position);
        }
        if (!assetnum.equals(n_problem1.getASSETNUM())) {
            n_problem.setASSETNUM(assetnum);
        }
        if (!reason.equals(n_problem1.getREASON())) {
            n_problem.setREASON(reason);
        }
        if (!solve.equals(n_problem1.getSOLVE())) {
            n_problem.setSOLVE(solve);
        }
        if (!finishdate.equals(n_problem1.getFINISHDATE())) {
            n_problem.setFINISHDATE(finishdate);
        }
        if (!status.equals(n_problem1.getSTATUS())) {
            n_problem.setSTATUS(status);
        }
        if (!abc.equals(n_problem1.getABC())) {
            n_problem.setABC(abc);
        }
        if (!confirmby.equals(n_problem1.getCONFIRMBY())) {
            n_problem.setCONFIRMBY(confirmby);
        }
        if (!result.equals(n_problem1.getRESULT())) {
            n_problem.setRESULT(result);
        }

        return n_problem;
    }

    /**
     * 初始化showPopupWindow*
     */
    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(N_ProblemDetailsActivity.this).inflate(
                R.layout.n_problem1_popup_window, null);


        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.popup_background_mtrl_mult));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);
        saveLinearLayout = (LinearLayout) contentView.findViewById(R.id.add_linearLayout_id);
        imageLinearLayout = (LinearLayout) contentView.findViewById(R.id.image_linearlayout_id);
        saveLinearLayout.setOnClickListener(saveLinearLayoutOnClickListener);
        imageLinearLayout.setOnClickListener(imageLinearLayoutOnClickListener);

    }


    private View.OnClickListener saveLinearLayoutOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getLoadingDialog("正在提交").show();
            saveAsyncTask();
            popupWindow.dismiss();
        }
    };
    private View.OnClickListener imageLinearLayoutOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(N_ProblemDetailsActivity.this, PhotoActivity.class);
            intent.putExtra("ownertable", Constants.N_PROBLEM_NAME);
            intent.putExtra("ownerid", n_problem.getN_PROBLEMID());
            startActivityForResult(intent, 0);
            popupWindow.dismiss();
        }
    };


}