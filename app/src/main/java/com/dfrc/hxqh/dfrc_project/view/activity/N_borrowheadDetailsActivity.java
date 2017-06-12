package com.dfrc.hxqh.dfrc_project.view.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.N_BORROWHEAD;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 借用详情
 */
public class N_borrowheadDetailsActivity extends BaseActivity {
    private static String TAG = "N_borrowheadDetailsActivity";


    @Bind(R.id.title_back_id)
    ImageView backImageView; //返回按钮

    @Bind(R.id.title_name)
    TextView titleTextView; //标题
    @Bind(R.id.n_borrowheadnum_text_id)
    TextView itemTextView; //编号
    @Bind(R.id.description_text_id)
    TextView descriptionTextView; //描述
    @Bind(R.id.createdby_id)
    TextView createdbyTextView; //借用人
    @Bind(R.id.bz_text_id)
    TextView crewidTextView; //班组
    @Bind(R.id.werks_text_id)
    TextView werksTextView; //借方工厂代码
    @Bind(R.id.location_text_id)
    TextView locationTextView; //借方仓库代码
    @Bind(R.id.siteid_text_id)
    TextView siteidTextView; //借方地点
    @Bind(R.id.createddate_text_id)
    TextView createddateTextView; //申请时间
    @Bind(R.id.n_werks_text_id)
    TextView n_werksTextView; //被借方工厂代码
    @Bind(R.id.n_location_text_id)
    TextView n_locationTextView; //被借方仓库代码
    @Bind(R.id.n_stieid_text_id)
    TextView n_stieidTextView; //被借方地点
    @Bind(R.id.status_text_id)
    TextView statusTextView; //状态


    private N_BORROWHEAD n_borrowhead;

    LinearLayout invbalancesLinearLayout; //库存余量


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_borrowhead_details);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        n_borrowhead = (N_BORROWHEAD) getIntent().getSerializableExtra("n_borrowhead");
    }

    @Override
    protected void findViewById() {


    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.jyxq_text);
        if (n_borrowhead != null) {
            itemTextView.setText(n_borrowhead.getN_BORROWHEADNUM());
            descriptionTextView.setText(n_borrowhead.getDESCRIPTION());
            createdbyTextView.setText(n_borrowhead.getCREATEDBY());
            crewidTextView.setText(n_borrowhead.getCREWID());
            werksTextView.setText(n_borrowhead.getWERKS());
            locationTextView.setText(n_borrowhead.getLOCATION());
            siteidTextView.setText(n_borrowhead.getSITEID());
            createddateTextView.setText(n_borrowhead.getCREATEDDATE());
            n_werksTextView.setText(n_borrowhead.getN_WERKS());
            n_locationTextView.setText(n_borrowhead.getN_LOCATION());
            n_stieidTextView.setText(n_borrowhead.getN_STIEID());
            statusTextView.setText(n_borrowhead.getSTATUS());

        }

    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageViewOnClickListener() {
        finish();
    }


}
