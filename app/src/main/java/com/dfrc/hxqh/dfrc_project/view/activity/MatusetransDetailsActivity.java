package com.dfrc.hxqh.dfrc_project.view.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.MATUSETRANS;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 分库领料单行详情
 */
public class MatusetransDetailsActivity extends BaseActivity {
    private static String TAG = "MatusetransDetailsActivity";


    @Bind(R.id.title_back_id)
    ImageView backImageView; //返回按钮
    @Bind(R.id.title_name)
    TextView titleTextView; //标题

    @Bind(R.id.wlbm_text_id)
    TextView wlbmTextView; //物料编码
    @Bind(R.id.description_text_id)
    TextView descriptionTextView; //描述
    @Bind(R.id.issuetype_text_id)
    TextView issuetypeTextView; //交易类型
    @Bind(R.id.location_text_id)
    TextView locationTextView; //库房
    @Bind(R.id.hg_text_id)
    TextView hgTextView; //货柜
    @Bind(R.id.curbal_text_id)
    TextView curbalTextView; //当前余量
    @Bind(R.id.positivequantity_text_id)
    TextView positivequantityTextView; //申请数量
    @Bind(R.id.enterby_text_id)
    TextView enterbyTextView; //使用人
    @Bind(R.id.n_usearea_text_id)
    TextView n_useareaTextView; //使用区域


    private MATUSETRANS matusetrans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matusetrans_details);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        matusetrans = (MATUSETRANS) getIntent().getSerializableExtra("matusetrans");
    }

    @Override
    protected void findViewById() {


    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.wlmx_text);
        if (matusetrans != null) {
            wlbmTextView.setText(matusetrans.getITEMNUM());
            descriptionTextView.setText(matusetrans.getDESCRIPTION());
            issuetypeTextView.setText(matusetrans.getISSUETYPE());
            locationTextView.setText(matusetrans.getSTORELOC());
            hgTextView.setText(matusetrans.getBINNUM());
            curbalTextView.setText(matusetrans.getCURBAL());
            positivequantityTextView.setText(matusetrans.getPOSITIVEQUANTITY());
            enterbyTextView.setText(matusetrans.getDISPLAYNAME());
            n_useareaTextView.setText(matusetrans.getN_USEAREA());

        }

    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageViewOnClickListener() {
        finish();
    }


}
