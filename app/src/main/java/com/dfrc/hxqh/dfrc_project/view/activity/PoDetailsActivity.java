package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
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

    @Bind(R.id.title_name)
    TextView titleTextView; //标题
    @Bind(R.id.ponum_text_id)
    TextView ponumTextView; //订单号
    @Bind(R.id.description_text_id)
    TextView descriptionTextView; //描述
    @Bind(R.id.vendor_text_id)
    TextView vendorTextView; //供应商
    @Bind(R.id.vendorname_text_id)
    TextView vendornameTextView; //供应商名称
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
        if (po != null) {
            ponumTextView.setText(po.getPONUM());
            descriptionTextView.setText(po.getDESCRIPTION());
            vendorTextView.setText(po.getVENDOR());
            vendornameTextView.setText(po.getVENDORNAME());
            orderdateTextView.setText(po.getORDERDATE());
            receiptsTextView.setText(po.getRECEIPTS());
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

    //采购单行
    @OnClick(R.id.poline_text_id)
    void setPolineOnClickListener() {
        Intent intent = new Intent(PoDetailsActivity.this, PoLineActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ponum", po.getPONUM());
        intent.putExtras(bundle);
        startActivityForResult(intent, 1000);
    }

    //物料接收
    @OnClick(R.id.wljs_text_id)
    void setWljsOnClickListener() {
        Intent intent = new Intent(PoDetailsActivity.this, WljsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ponum", po.getPONUM());
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }


}
