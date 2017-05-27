package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.INVBALANCES;

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

    //显示项目
    @Bind(R.id.item_text_id)
    TextView itemTextView; //备件编码
    @Bind(R.id.description_text_id)
    TextView descriptionTextView; //描述
    @Bind(R.id.location_text_id)
    TextView locationTextView; //库房
    @Bind(R.id.loc_desc_text_id)
    TextView locdescTextView; //库房描述
    @Bind(R.id.binnum_text_id)
    TextView binnumTextView; //存放位置
    @Bind(R.id.curbal_text_id)
    TextView curbalTextView; //余量
    @Bind(R.id.udunitcost_text_id)
    TextView udunitcostTextView; //单价
    @Bind(R.id.issueunit_text_id)
    TextView issueunitTextView; //发放单位
    @Bind(R.id.siteid_text_id)
    TextView siteidTextView; //站点


    private INVBALANCES invbalances;


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
        invbalances = (INVBALANCES) getIntent().getSerializableExtra("invbalances");
    }

    @Override
    protected void findViewById() {


    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.kcxq_text);
        if (invbalances != null) {
            itemTextView.setText(invbalances.getITEMNUM());
            descriptionTextView.setText(invbalances.getITEMNUMNAME());
            locationTextView.setText(invbalances.getLOCATION());
            locdescTextView.setText(invbalances.getLOCATION2());
            binnumTextView.setText(invbalances.getBINNUM());
            curbalTextView.setText(invbalances.getCURBAL());
            udunitcostTextView.setText(invbalances.getINVENTORY_UDUNITCOST());
            issueunitTextView.setText(invbalances.getINVENTORY_ISSUEUNIT());
            siteidTextView.setText(invbalances.getSITEID());

        }

    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageViewOnClickListener() {
        finish();
    }

    //转移当前项目
    @OnClick(R.id.zydqxm_btn_id)
    void setZydqxmOnClickListener() {
        Intent intent = getIntent();
        intent.setClass(InventoryDetailsActivity.this, InventoryAddActivity.class);
        startActivityForResult(intent, 0);
    }

    //新增货架
    @OnClick(R.id.new_hj_btn_id)
    void setNewHjOnClickListener() {
        Intent intent = getIntent();
        intent.setClass(InventoryDetailsActivity.this, BinnumAddActivity.class);
        startActivityForResult(intent, 0);

    }


}
