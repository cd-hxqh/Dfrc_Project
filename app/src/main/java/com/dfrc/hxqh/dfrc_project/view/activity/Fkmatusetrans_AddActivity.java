package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.api.HttpManager;
import com.dfrc.hxqh.dfrc_project.api.HttpRequestHandler;
import com.dfrc.hxqh.dfrc_project.api.JsonUtils;
import com.dfrc.hxqh.dfrc_project.bean.Results;
import com.dfrc.hxqh.dfrc_project.dialog.FlippingLoadingDialog;
import com.dfrc.hxqh.dfrc_project.model.FKWORKORDER;
import com.dfrc.hxqh.dfrc_project.model.INVBALANCES;
import com.dfrc.hxqh.dfrc_project.model.LOCATIONS;
import com.dfrc.hxqh.dfrc_project.model.MATUSETRANS;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.view.widght.ShareBottomDialog;
import com.dfrc.hxqh.dfrc_project.webserviceclient.AndroidClientService;
import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnOperItemClickL;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 分库领料行新建
 */
public class Fkmatusetrans_AddActivity extends BaseActivity {

    private static final String TAG = "Fkmatusetrans_AddActivity";

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
    @Bind(R.id.issuetype_text_id)
    TextView issuetypeTextView; //交易类型
    @Bind(R.id.location_text_id)
    TextView locationTextView; //库房
    @Bind(R.id.hg_text_id)
    TextView hgTextView; //货柜
    @Bind(R.id.positivequantity_text_id)
    TextView positivequantityTextView; //申请数量
    @Bind(R.id.enterby_text_id)
    TextView enterbyTextView; //领用人
    @Bind(R.id.n_usearea_text_id)
    TextView n_useareaTextView; //使用区域

    private FKWORKORDER fkworkorder;
    protected FlippingLoadingDialog mLoadingDialog;

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;

    private String[] issuety = {"发放", "退回"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fkmatusetrans_add);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }


    private void geiIntentData() {
        fkworkorder = (FKWORKORDER) getIntent().getSerializableExtra("fkworkorder");
    }

    @Override
    protected void findViewById() {


    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.add_text);
        sbmitImageButton.setVisibility(View.VISIBLE);
        issuetypeTextView.setText(issuety[0]);
        enterbyTextView.setText(AccountUtils.getloginUserName(Fkmatusetrans_AddActivity.this));
        getLocation(HttpManager.getLOCATIONURL(fkworkorder.getCREWID(), fkworkorder.getSITEID()), locationTextView);
    }

    @OnClick(R.id.itemnum_text_id)
        //物料编码
    void setItemnumTextViewOnClickListener() {
        Intent intent = new Intent(Fkmatusetrans_AddActivity.this, InvbalancesChooseActivity.class);
        intent.putExtra("location", locationTextView.getText().toString());
        startActivityForResult(intent, INVBALANCES_CODE);
    }

    @OnClick(R.id.issuetype_text_id)
        //交易类型
    void setIssuetypeTextViewOnClickListener() {
        getISSUETYPEValue("交易类型", issuetypeTextView);
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
                hgTextView.setText(invbalances.getBINNUM());
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
                return AndroidClientService.AddN_WORKORDLine(Fkmatusetrans_AddActivity.this, getMATUSETRANS());
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(Fkmatusetrans_AddActivity.this, s);
                finish();


            }
        }.execute();


    }


    /**
     * 封装新增的信息
     **/
    private MATUSETRANS getMATUSETRANS() {
        MATUSETRANS matusetrans = new MATUSETRANS();
        String itemnum = itemnumTextView.getText().toString();
        String issuetype = issuetypeTextView.getText().toString();
        String location = locationTextView.getText().toString();
        String hg = hgTextView.getText().toString();
        String positivequantity = positivequantityTextView.getText().toString();
        String enterby = enterbyTextView.getText().toString();
        String n_usearea = n_useareaTextView.getText().toString();
        matusetrans.setITEMNUM(itemnum);
        matusetrans.setISSUETYPE(issuetype);
        matusetrans.setSTORELOC(location);
        matusetrans.setBINNUM(hg);
        matusetrans.setPOSITIVEQUANTITY(positivequantity);
        matusetrans.setENTERBY(enterby);
        matusetrans.setN_USEAREA(n_usearea);
        matusetrans.setWONUM(fkworkorder.getWONUM());
        matusetrans.setSTATUS(fkworkorder.getSTATUS());
        matusetrans.setSITEID(fkworkorder.getSITEID());

        return matusetrans;
    }


    //获取库房
    private void getLocation(String url, final TextView textview) {
        {
            HttpManager.getData(this, url, new HttpRequestHandler<Results>() {


                @Override
                public void onSuccess(Results data) {

                }

                @Override
                public void onSuccess(Results data, int totalPages, int currentPage) {
                    ArrayList<LOCATIONS> item = JsonUtils.parsingLOCATION(data.getResultlist());
                    if (item == null || item.isEmpty()) {
                        MessageUtils.showMiddleToast(Fkmatusetrans_AddActivity.this, getString(R.string.get_type_text));
                    } else {
                        LOCATIONS locations = item.get(0);
                        textview.setText(locations.getLOCATION());
                    }

                }

                @Override
                public void onFailure(String error) {

                }
            });

        }
    }

    //获取交易类型
    private void getISSUETYPEValue(final String title, final TextView textview) {

        showShareBottomDialog(title, issuety, textview);

    }

    /**
     * 显示选项框
     **/
    private void showShareBottomDialog(String title, final String[] typesitem, final TextView textview) {

        final ShareBottomDialog dialog = new ShareBottomDialog(Fkmatusetrans_AddActivity.this, typesitem, null);


        dialog.title(title)//
                .titleTextSize_SP(14.5f)//
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageUtils.showMiddleToast(Fkmatusetrans_AddActivity.this, typesitem[position]);
                textview.setText(typesitem[position]);
                dialog.dismiss();
            }
        });
    }

}
