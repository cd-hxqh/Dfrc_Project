package com.dfrc.hxqh.dfrc_project.view.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.dialog.FlippingLoadingDialog;
import com.dfrc.hxqh.dfrc_project.model.MATRECTRANS;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.webserviceclient.AndroidClientService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 物料接收新建行
 */
public class Matrectrans_AddActivity extends BaseActivity {

    private static final String TAG = "Matrectrans_AddActivity";

    @OnClick(R.id.title_back_id)
    public void setBackOnClickLIstener() {
        finish();
    }

    @Bind(R.id.title_name)
    TextView titleTextView; //标题
    @Bind(R.id.sbmittext_id)
    ImageButton sbmitImageButton; //提交


    @Bind(R.id.polinenum_text_id)
    TextView polinenumTextView; //行
    @Bind(R.id.receiptquantity_text_id)
    TextView receiptquantityView; //数量
    @Bind(R.id.binnum_text_id)
    TextView binnumTextView; //货柜
    @Bind(R.id.enterby_text_id)
    TextView enterbyTextView; //输入人

    private String ponum; //采购单号
    protected FlippingLoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrectrans_add);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();

    }


    private void geiIntentData() {
        ponum = getIntent().getExtras().getString("ponum");
    }

    @Override
    protected void findViewById() {


    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.add_text);
        sbmitImageButton.setVisibility(View.VISIBLE);
        enterbyTextView.setText(AccountUtils.getloginUserName(Matrectrans_AddActivity.this));

    }


    @OnClick(R.id.sbmittext_id)
    void setSbmitImageButtonOnClickListener() {
        if (polinenumTextView.getText().toString().equals("")) {
            polinenumTextView.setError("*必填");
        } else {
            getLoadingDialog("正在提交...").show();
            startAsyncTask();
        }
    }


    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }


    /**
     * 提交数据*
     */
    private void startAsyncTask() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return AndroidClientService.INV02RecByPOLine(Matrectrans_AddActivity.this, getMATRECTRANS());
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.i(TAG, "s=" + s);
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(Matrectrans_AddActivity.this, s);
                finish();

            }
        }.execute();


    }


    /**
     * 封装新增的信息
     **/
    private MATRECTRANS getMATRECTRANS() {
        MATRECTRANS matrectrans = new MATRECTRANS();
        String polinenum = polinenumTextView.getText().toString();
        String qty = receiptquantityView.getText().toString();
        String binnum = binnumTextView.getText().toString();
        matrectrans.setPOLINENUM(polinenum);
        matrectrans.setRECEIPTQUANTITY(qty);
        matrectrans.setBINNUM(binnum);
        matrectrans.setPONUM(ponum);
        matrectrans.setENTERBY(AccountUtils.getloginUserName(Matrectrans_AddActivity.this));
        return matrectrans;
    }

}
