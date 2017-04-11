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
import com.dfrc.hxqh.dfrc_project.model.WORKORDER;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 工单详情
 */
public class WorkOrderDetailsActivity extends BaseActivity {
    private static String TAG = "WorkOrderDetailsActivity";


    @Bind(R.id.title_back_id)
    ImageView backImageView; //返回按钮

    @Bind(R.id.title_name)
    TextView titleTextView; //标题
    @Bind(R.id.title_add)
    ImageView menuImageView; //菜单
    PopupWindow popupWindow;
    @Bind(R.id.wonum_text_id)
    TextView wonumTextView; //工单
    @Bind(R.id.description_text_id)
    TextView descriptionTextView; //描述
    @Bind(R.id.bz_text_id)
    TextView bzTextView; //班组
    @Bind(R.id.year_text_id)
    TextView yearTextView; //年度
    @Bind(R.id.month_text_id)
    TextView monthTextView; //月度
    @Bind(R.id.n_wpnum_text_id)
    TextView n_wpnumTextView; //周计划编号
    @Bind(R.id.status_text_id)
    TextView statusTextView; //状态
    @Bind(R.id.changeby_text_id)
    TextView changebyTextView; //变更人

    private WORKORDER workorder;

    LinearLayout mxhLinearLayout; //点检明细行点检明细行


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workorder_details);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        workorder = (WORKORDER) getIntent().getSerializableExtra("workorder");
    }

    @Override
    protected void findViewById() {


    }


    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.ddjgd_text);
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setImageResource(R.mipmap.ic_more);
        menuImageView.setOnClickListener(menuImageViewOnClickListener);
        if (workorder != null) {
            wonumTextView.setText(workorder.getWONUM());
            descriptionTextView.setText(workorder.getDESCRIPTION());
            bzTextView.setText(workorder.getCREWID());
            yearTextView.setText(workorder.getYEAR());
            monthTextView.setText(workorder.getMONTH());
            n_wpnumTextView.setText(workorder.getN_WPNUM());
            statusTextView.setText(workorder.getSTATUS());
            changebyTextView.setText(workorder.getCHANGEBY());

        }

    }


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener menuImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showPopupWindow(menuImageView);
        }
    };


    /**
     * 初始化showPopupWindow*
     */
    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(WorkOrderDetailsActivity.this).inflate(
                R.layout.workorder_popup_window, null);


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
        mxhLinearLayout = (LinearLayout) contentView.findViewById(R.id.beijian_linearlayout_id);
        mxhLinearLayout.setOnClickListener(beijianOnClickListener);

    }

    private View.OnClickListener beijianOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(WorkOrderDetailsActivity.this, WotaskActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("wonum", workorder.getWONUM());
            intent.putExtras(bundle);
            startActivityForResult(intent, 1000);
            popupWindow.dismiss();
        }
    };

}
