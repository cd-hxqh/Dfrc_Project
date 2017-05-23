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
import com.dfrc.hxqh.dfrc_project.model.INVENTORY;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 库存详情
 */
public class InventoryDetailsActivity extends BaseActivity {
    private static String TAG = "InventoryDetailsActivity";


    @Bind(R.id.title_back_id)
    ImageView backImageView; //返回按钮

    @Bind(R.id.title_name)
    TextView titleTextView; //标题
    @Bind(R.id.title_add)
    ImageView menuImageView; //菜单
    PopupWindow popupWindow;
    @Bind(R.id.item_text_id)
    TextView itemTextView; //项目
    @Bind(R.id.description_text_id)
    TextView descriptionTextView; //描述
    @Bind(R.id.location_text_id)
    TextView locationTextView; //库房
    @Bind(R.id.loc_desc_text_id)
    TextView locdescTextView; //库房描述
    @Bind(R.id.binnum_text_id)
    TextView binnumTextView; //存放位置
    @Bind(R.id.udunitcost_text_id)
    TextView udunitcostTextView; //单价
    @Bind(R.id.issueunit_text_id)
    TextView issueunitTextView; //发放单位
    @Bind(R.id.status_text_id)
    TextView statusTextView; //状态
    @Bind(R.id.siteid_text_id)
    TextView siteidTextView; //站点
    @Bind(R.id.curbaltotal_text_id)
    TextView curbaltotalTextView; //当前余量
    @Bind(R.id.avblbalance_text_id)
    TextView avblbalanceTextView; //可用量


    private INVENTORY inventory;

    LinearLayout invbalancesLinearLayout; //库存余量


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_details);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        inventory = (INVENTORY) getIntent().getSerializableExtra("inventory");
    }

    @Override
    protected void findViewById() {


    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.kcxq_text);
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setImageResource(R.mipmap.ic_more);
        if (inventory != null) {
            itemTextView.setText(inventory.getITEMNUM());
            descriptionTextView.setText(inventory.getDESCRIPTION());
            locationTextView.setText(inventory.getLOCATION());
            locdescTextView.setText(inventory.getLOCATIONS_DESCRIPTION());
            binnumTextView.setText(inventory.getBINNUM());
            udunitcostTextView.setText(inventory.getUDUNITCOST());
            issueunitTextView.setText(inventory.getISSUEUNIT());
            statusTextView.setText(inventory.getSTATUS());
            siteidTextView.setText(inventory.getSITEID());
            curbaltotalTextView.setText(inventory.getCURBALTOTAL());
            avblbalanceTextView.setText(inventory.getAVBLBALANCE());

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
        View contentView = LayoutInflater.from(InventoryDetailsActivity.this).inflate(
                R.layout.inventory_popup_window, null);


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
        invbalancesLinearLayout = (LinearLayout) contentView.findViewById(R.id.kcyl_linearlayout_id);
        invbalancesLinearLayout.setOnClickListener(kcylOnClickListener);

    }

    private View.OnClickListener kcylOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(InventoryDetailsActivity.this, InvbalancesActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("itemnum", inventory.getITEMNUM());
            intent.putExtras(bundle);
            startActivityForResult(intent, 1000);
            popupWindow.dismiss();
        }
    };

}
