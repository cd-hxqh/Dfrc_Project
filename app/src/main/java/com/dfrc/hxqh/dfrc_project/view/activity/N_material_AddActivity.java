package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.dialog.FlippingLoadingDialog;
import com.dfrc.hxqh.dfrc_project.model.INVBALANCES;
import com.dfrc.hxqh.dfrc_project.model.N_MATERIAL;
import com.dfrc.hxqh.dfrc_project.model.ZKWORKORDER;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.webserviceclient.AndroidClientService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;


/**
 * 总库领料行新建
 */
public class N_material_AddActivity extends BaseActivity {

    private static final String TAG = "N_material_AddActivity";

    public static final int INVBALANCES_CODE = 1003;

    @OnClick(R.id.title_back_id)
    public void setBackOnClickLIstener() {
        finish();
    }

    @Bind(R.id.title_name)
    TextView titleTextView; //标题
    @Bind(R.id.sbmittext_id)
    ImageButton sbmitImageButton; //提交


    @Bind(R.id.itemnum_text_id)
    TextView itemnumTextView; //物料号
    @Bind(R.id.desc_text_id)
    TextView descTextView; //描述
    @Bind(R.id.n_reason_text_id)
    TextView n_reasonTextView; //领用原因
    @Bind(R.id.n_sap5_text_id)
    EditText n_sap5TextView; //申请数量
    @Bind(R.id.n_sap3_text_id)
    EditText n_sap3TextView; //实际发放数量


    private ZKWORKORDER zkworkorder;
    protected FlippingLoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_material_add);
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
        titleTextView.setText(R.string.add_text);
        sbmitImageButton.setVisibility(View.VISIBLE);


    }

    @OnTextChanged(value = R.id.n_sap5_text_id, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @OnTextChanged(value = R.id.n_sap5_text_id, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.i(TAG, "s=" + s);
//        n_sap3TextView.setText(s);
    }

    @OnTextChanged(value = R.id.n_sap5_text_id, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged(Editable s) {
        Log.i(TAG, "ssss=" + s.toString());
        n_sap3TextView.setText(s);
    }

    @OnClick(R.id.itemnum_text_id)
    void setItemnumTextViewOnClickListener() {
        Intent intent = new Intent(N_material_AddActivity.this, InvbalancesChooseActivity.class);
        intent.putExtra("location", zkworkorder.getN_SAP1());
        startActivityForResult(intent, INVBALANCES_CODE);
    }


    @OnClick(R.id.sbmittext_id)
    void setSbmitImageButtonOnClickListener() {
        getLoadingDialog("正在提交...").show();
        startAsyncTask();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case INVBALANCES_CODE:
                INVBALANCES invbalances = (INVBALANCES) data.getSerializableExtra("invbalances");
                itemnumTextView.setText(invbalances.getITEMNUM());
                descTextView.setText(invbalances.getDESCRIPTION());
                break;
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
                return AndroidClientService.AddN_WORKOR2Line(N_material_AddActivity.this, getN_MATERIAL());
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.i(TAG, "s=" + s);
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(N_material_AddActivity.this, s);


            }
        }.execute();


    }


    /**
     * 封装新增的信息
     **/
    private N_MATERIAL getN_MATERIAL() {
        N_MATERIAL n_material = new N_MATERIAL();
        String itemnum = itemnumTextView.getText().toString();
        String n_reason = n_reasonTextView.getText().toString();
        String n_sap3 = n_sap3TextView.getText().toString();
        String n_sap5 = n_sap5TextView.getText().toString();
        n_material.setITEMNUM(itemnum);
        n_material.setN_REASON(n_reason);
        n_material.setN_SAP3(n_sap3);
        n_material.setN_SAP5(n_sap5);
        n_material.setN_SAP1(zkworkorder.getN_SAP1());
        n_material.setWONUM(zkworkorder.getWONUM());
        n_material.setSTATUS(zkworkorder.getSTATUS());
        n_material.setSITEID(zkworkorder.getSITEID());

        return n_material;
    }

}
