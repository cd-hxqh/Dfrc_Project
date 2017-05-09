package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.ZKWORKORDER;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 总库领料单详情
 */
public class ZkworkorderDetailsActivity extends BaseActivity {
    private static String TAG = "ZkworkorderDetailsActivity";


    @Bind(R.id.title_back_id)
    ImageView backImageView; //返回按钮

    @Bind(R.id.title_name)
    TextView titleTextView; //标题
    @Bind(R.id.title_add)
    ImageView menuImageView; //菜单
    PopupWindow popupWindow;
    @Bind(R.id.wonum_text_id)
    TextView wonumTextView; //领料单号
    @Bind(R.id.description_text_id)
    TextView descriptionTextView; //描述
    @Bind(R.id.tbr_text_id)
    TextView tbrTextView; //提报人
    @Bind(R.id.n_sap1_text_id)
    TextView n_sap1TextView; //发货仓库
    @Bind(R.id.status_text_id)
    TextView statusTextView; //状态
    @Bind(R.id.reportdate_text_id)
    TextView reportdateTextView; //报告日期
    @Bind(R.id.statusdate_text_id)
    TextView statusdateTextView; //状态日期
    @Bind(R.id.totalcost_text_id)
    TextView totalcostTextView; //领料单合计
    @Bind(R.id.siteid_text_id)
    TextView siteidTextView; //地点


    private ZKWORKORDER zkworkorder;

    LinearLayout n_materialLinearLayout; //申请领用物料明细


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zkworkorder_details);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        zkworkorder = (ZKWORKORDER) getIntent().getSerializableExtra("zkworkorder");
    }

    @Override
    protected void findViewById() {


    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.zklld_text);
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setImageResource(R.mipmap.ic_more);
        if (zkworkorder != null) {
            wonumTextView.setText(zkworkorder.getWONUM());
            descriptionTextView.setText(zkworkorder.getDESCRIPTION());
            tbrTextView.setText(zkworkorder.getREPORTEDBYNAME());
            n_sap1TextView.setText(zkworkorder.getN_SAP1());
            statusTextView.setText(zkworkorder.getSTATUS());
            reportdateTextView.setText(zkworkorder.getREPORTDATE());
            statusdateTextView.setText(zkworkorder.getSTATUSDATE());
            totalcostTextView.setText(zkworkorder.getTOTALCOST());
            siteidTextView.setText(zkworkorder.getSITEID());

        }

    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageViewOnClickListener() {
        finish();
    }

    //菜单事件
    @OnClick(R.id.title_add)
    void setMenuImageViewOnClickListener() {
        showPopupWindow(menuImageView);
    }


    /**
     * 初始化showPopupWindow*
     */
    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(ZkworkorderDetailsActivity.this).inflate(
                R.layout.zkworkorder_popup_window, null);


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
        n_materialLinearLayout = (LinearLayout) contentView.findViewById(R.id.sqlywlmx_linearlayout_id);
        n_materialLinearLayout.setOnClickListener(kcylOnClickListener);

    }

    private View.OnClickListener kcylOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ZkworkorderDetailsActivity.this, N_materialActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("wonum", zkworkorder.getWONUM());
            bundle.putSerializable("zkworkorder", zkworkorder);
            intent.putExtras(bundle);
            startActivityForResult(intent, 1000);
            popupWindow.dismiss();
        }
    };

}
