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
import com.dfrc.hxqh.dfrc_project.model.PO;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 采购订单详情
 */
public class PoDetailsActivity extends BaseActivity {
    private static String TAG = "PoDetailsActivity";


    @Bind(R.id.title_back_id)
    ImageView backImageView; //返回按钮
    @Bind(R.id.title_add)
    ImageView menuImageView; //菜单
    PopupWindow popupWindow;

    @Bind(R.id.title_name)
    TextView titleTextView; //标题
    @Bind(R.id.ponum_text_id)
    TextView ponumTextView; //订单号
    @Bind(R.id.description_text_id)
    TextView descriptionTextView; //描述
    @Bind(R.id.orderdate_text_id)
    TextView orderdateTextView; //订购日期
    @Bind(R.id.receipts_text_id)
    TextView receiptsTextView; //接收
    @Bind(R.id.shiptoattn_text_id)
    TextView shiptoattnTextView; //接收人
    @Bind(R.id.status_text_id)
    TextView statusTextView; //状态
    @Bind(R.id.siteid_text_id)
    TextView siteidTextView; //地点


    private PO po;

    private LinearLayout polineLinearLayout; //采购单行

    private LinearLayout matrectransLinearLayout; //物料接收

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_details);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        po = (PO) getIntent().getSerializableExtra("po");
    }

    @Override
    protected void findViewById() {


    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.po_derail_text);
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setImageResource(R.mipmap.ic_more);
        if (po != null) {
            ponumTextView.setText(po.getPONUM());
            descriptionTextView.setText(po.getDESCRIPTION());
            orderdateTextView.setText(po.getORDERDATE());
            receiptsTextView.setText(po.getSTATUS());
            shiptoattnTextView.setText(po.getSHIPTOPERSON_DISPLAYNAME());
            statusTextView.setText(po.getSTATUS());
            siteidTextView.setText(po.getSITEID());

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
        View contentView = LayoutInflater.from(PoDetailsActivity.this).inflate(
                R.layout.po_popup_window, null);


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
        polineLinearLayout = (LinearLayout) contentView.findViewById(R.id.poline_linearlayout_id);
        matrectransLinearLayout = (LinearLayout) contentView.findViewById(R.id.matrectrans_linearlayout_id);
        polineLinearLayout.setOnClickListener(polineOnClickListener);
        matrectransLinearLayout.setOnClickListener(matrectransOnClickListener);

    }

    private View.OnClickListener polineOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(PoDetailsActivity.this, PoLineActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("ponum", po.getPONUM());
            intent.putExtras(bundle);
            startActivityForResult(intent, 1000);
            popupWindow.dismiss();
        }
    };
    private View.OnClickListener matrectransOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(PoDetailsActivity.this, WljsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("ponum", po.getPONUM());
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
            popupWindow.dismiss();
        }
    };

}
