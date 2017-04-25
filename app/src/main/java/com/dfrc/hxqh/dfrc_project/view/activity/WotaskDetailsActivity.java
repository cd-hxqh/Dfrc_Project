package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.constants.Constants;
import com.dfrc.hxqh.dfrc_project.dialog.FlippingLoadingDialog;
import com.dfrc.hxqh.dfrc_project.model.PERSON;
import com.dfrc.hxqh.dfrc_project.model.WOTASK;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.webserviceclient.AndroidClientService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 点击明细行详情
 */
public class WotaskDetailsActivity extends BaseActivity {

    public static final int NO_CODE = 100;
    public static final int WT_CODE = 101;

    @OnClick(R.id.title_back_id)
    public void setBackOnClickLIstener() {
        finish();
    }

    @Bind(R.id.title_name)
    TextView titleTextView; //标题
    @Bind(R.id.title_add)
    ImageView menuImageView; //菜单
    PopupWindow popupWindow;
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
    EditText n_resultTextView; //结果
    @Bind(R.id.n_note_text_id)
    EditText n_noteTextView; //预知项目结果
    @Bind(R.id.n_responsor_text_id)
    TextView n_responsorTextView; //实施责任人
    @Bind(R.id.n_members_text_id)
    TextView n_membersTextView; //实施人员

    private WOTASK wotask;
    private String wonum;

    private LinearLayout saveLinearLayout; //保存
    private LinearLayout okLinearLayout; //OK
    private LinearLayout noLinearLayout; //NO
    private LinearLayout n_pbLinearLayout; //问题点

    protected FlippingLoadingDialog mLoadingDialog;


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
        wonum = getIntent().getExtras().getString("wonum");
    }

    @Override
    protected void findViewById() {


    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.wotask_text);
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setImageResource(R.mipmap.ic_more);
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

    //菜单事件
    @OnClick(R.id.title_add)
    void setMenuImageViewOnClickListener() {
        showPopupWindow(menuImageView);
    }

    //实施人员
    @OnClick(R.id.n_members_text_id)
    void setN_membersTextViewOnClickListener() {
        Intent intent = new Intent(WotaskDetailsActivity.this, PersonActivity.class);
        startActivityForResult(intent, 0);
    }

    /**
     * 初始化showPopupWindow*
     */
    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(WotaskDetailsActivity.this).inflate(
                R.layout.wotask_popup_window, null);


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
        saveLinearLayout = (LinearLayout) contentView.findViewById(R.id.save_linearlayout_id);
        okLinearLayout = (LinearLayout) contentView.findViewById(R.id.ok_linearlayout_id);
        noLinearLayout = (LinearLayout) contentView.findViewById(R.id.no_linearlayout_id);
        n_pbLinearLayout = (LinearLayout) contentView.findViewById(R.id.n_pb_linearlayout_id);
        saveLinearLayout.setOnClickListener(saveLinearLayoutOnClickListener);
        okLinearLayout.setOnClickListener(okLinearLayoutOnClickListener);
        noLinearLayout.setOnClickListener(noLinearLayoutOnClickListener);
        n_pbLinearLayout.setOnClickListener(n_pbLinearLayoutOnClickListener);

    }

    private View.OnClickListener saveLinearLayoutOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getLoadingDialog("正在提交数据...").show();
            saveAsyncTask();
            popupWindow.dismiss();
        }
    };
    private View.OnClickListener okLinearLayoutOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getLoadingDialog("正在提交数据...").show();
            startAsyncTask();
            popupWindow.dismiss();
        }
    };
    private View.OnClickListener noLinearLayoutOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            intent.setClass(WotaskDetailsActivity.this, N_PB_AddActivity.class);
            intent.putExtra("code", NO_CODE);
            startActivityForResult(intent, 0);
            popupWindow.dismiss();
        }
    };
    private View.OnClickListener n_pbLinearLayoutOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            intent.setClass(WotaskDetailsActivity.this, N_PB_AddActivity.class);
            intent.putExtra("code", WT_CODE);
            startActivityForResult(intent, 0);
            popupWindow.dismiss();
        }
    };

    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }


    /**
     * 修改*
     */
    private void saveAsyncTask() {

        final String n_result = n_resultTextView.getText().toString();
        final String n_note = n_noteTextView.getText().toString();
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return AndroidClientService.UpdateMbo(WotaskDetailsActivity.this, "", Constants.WORKORDER_NAME, "1", "WONUM");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(WotaskDetailsActivity.this, s);


            }
        }.execute();


    }

    /**
     * 提交数据*
     */
    private void startAsyncTask() {

        final String n_result = n_resultTextView.getText().toString();
        final String n_note = n_noteTextView.getText().toString();
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return AndroidClientService.MaintWOIsOk(WotaskDetailsActivity.this, wotask.getWOSEQUENCE(), n_result, n_note, AccountUtils.getloginUserName(WotaskDetailsActivity.this), wonum);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(WotaskDetailsActivity.this, s);


            }
        }.execute();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1000:
                PERSON person = (PERSON) data.getSerializableExtra("person");
                n_membersTextView.setText(person.getPERSONID());
                break;
        }
    }


}
