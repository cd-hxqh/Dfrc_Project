package com.dfrc.hxqh.dfrc_project.view.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.INVBALANCES;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.webserviceclient.AndroidClientService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 库位新增
 */
public class BinnumAddActivity extends BaseActivity {
    private static String TAG = "BinnumAddActivity";


    @Bind(R.id.title_back_id)
    ImageView backImageView; //返回按钮

    @Bind(R.id.title_name)
    TextView titleTextView; //标题

    //显示项目
    @Bind(R.id.hg_text_id)
    TextView binnumTextView; //货柜
    @Bind(R.id.curbal_text_id)
    TextView curbalTextView; //当前余量


    private INVBALANCES invbalances;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binnum_add);
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
        titleTextView.setText(R.string.kwxz_text);

    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageViewOnClickListener() {
        finish();
    }

    //保存
    @OnClick(R.id.save_btn_id)
    void setSaveBtnOnClickListener() {
        getLoadingDialog("正在提交...").show();
        startAsyncTask();
    }


    /**
     * 提交数据*
     */
    private void startAsyncTask() {
        final String binnum = binnumTextView.getText().toString();
        final String qty = curbalTextView.getText().toString();

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return AndroidClientService.INV05InvAdd(BinnumAddActivity.this, AccountUtils.getloginUserName(BinnumAddActivity.this), invbalances.getITEMNUM(), qty, invbalances.getLOCATION(), binnum, invbalances.getSITEID());
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(BinnumAddActivity.this, s);
                finish();


            }
        }.execute();


    }
}
