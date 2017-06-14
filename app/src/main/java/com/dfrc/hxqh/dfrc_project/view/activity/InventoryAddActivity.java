package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.model.INVBALANCES;
import com.dfrc.hxqh.dfrc_project.model.LOCATIONS;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.webserviceclient.AndroidClientService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 库位转移
 */
public class InventoryAddActivity extends BaseActivity {
    private static String TAG = "InventoryAddActivity";

    public static final int YHG_REQUESTCODE = 1007;//原货柜
    public static final int MBHG_REQUESTCODE = 1008;//目标货柜
    @Bind(R.id.title_back_id)
    ImageView backImageView; //返回按钮

    @Bind(R.id.title_name)
    TextView titleTextView; //标题

    //显示项目
    @Bind(R.id.item_text_id)
    TextView itemTextView; //备件编码
    @Bind(R.id.description_text_id)
    TextView descriptionTextView; //描述
    @Bind(R.id.quantity_text_id)
    EditText quantityEditText; //数量
    @Bind(R.id.location_text_id)
    TextView locationTextView; //库房
    @Bind(R.id.siteid_text_id)
    TextView siteidTextView; //目标地点
    @Bind(R.id.mblocation_text_id)
    TextView mblocationTextView; //目标库房
    @Bind(R.id.binnum_text_id)
    TextView binnumTextView; //原货柜
    @Bind(R.id.mbinnum_text_id)
    TextView mbinnumTextView; //目标货柜
    @Bind(R.id.issueunit_text_id)
    TextView issueunitTextView; //单位成本


    private INVBALANCES invbalances;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_add);
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
        titleTextView.setText(R.string.kwzt_text);
        if (invbalances != null) {
            itemTextView.setText(invbalances.getITEMNUM());
            descriptionTextView.setText(invbalances.getITEMNUMNAME());
            quantityEditText.setText(invbalances.getCURBAL());
            locationTextView.setText(invbalances.getLOCATION());
            siteidTextView.setText(invbalances.getSITEID());
            mblocationTextView.setText(invbalances.getLOCATION());
            binnumTextView.setText(invbalances.getBINNUM());
            issueunitTextView.setText(invbalances.getINVENTORY_UDUNITCOST());

        }

    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageViewOnClickListener() {
        finish();
    }

    //目标库房
    @OnClick(R.id.mblocation_text_id)
    void setMblocationTextViewOnClickListener() {
        Intent intent = getIntent();
        intent.setClass(InventoryAddActivity.this, LocationChooseActivity.class);
        startActivityForResult(intent, 0);
    }
    //原货柜
    @OnClick(R.id.binnum_text_id)
    void setBinnumTextViewOnClickListener() {
        Intent intent = getIntent();
        intent.setClass(InventoryAddActivity.this, HgChooseActivity.class);
        intent.putExtra("hgbs",YHG_REQUESTCODE);
        startActivityForResult(intent, YHG_REQUESTCODE);
    }
    //目标货柜
    @OnClick(R.id.mbinnum_text_id)
    void setMbbinnumTextViewOnClickListener() {
        Intent intent = getIntent();
        intent.setClass(InventoryAddActivity.this, HgChooseActivity.class);
        intent.putExtra("mbkf",mblocationTextView.getText().toString());
        intent.putExtra("hgbs",MBHG_REQUESTCODE);
        startActivityForResult(intent, MBHG_REQUESTCODE);
    }

    //保存
    @OnClick(R.id.save_btn_id)
    void setSaveBtnOnClickListener() {
        getLoadingDialog("正在提交...").show();
        startAsyncTask();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case LocationChooseActivity.LOCATONS_RESULTCODE:
                LOCATIONS locations = (LOCATIONS) data.getSerializableExtra("locations");
                mblocationTextView.setText(locations.getLOCATION());
                break;
            case HgChooseActivity.HG_RESULTCODE:
                INVBALANCES invbalances = (INVBALANCES) data.getSerializableExtra("invbalances");
                if(requestCode==YHG_REQUESTCODE){
                    binnumTextView.setText(invbalances.getBINNUM());
                }else if(requestCode==MBHG_REQUESTCODE){
                    mbinnumTextView.setText(invbalances.getBINNUM());
                }

                break;
        }
    }



    /**
     * 提交数据*
     */
    private void startAsyncTask() {
        final String qty = quantityEditText.getText().toString(); //数量
        final String mblocation = mblocationTextView.getText().toString(); //目标库房
        final String mbinnum = mbinnumTextView.getText().toString(); //目标货柜

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return AndroidClientService.INV05Invtrans(InventoryAddActivity.this, AccountUtils.getloginUserName(InventoryAddActivity.this), invbalances.getITEMNUM(), qty, invbalances.getLOCATION(), invbalances.getBINNUM(), mblocation, mbinnum);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(InventoryAddActivity.this, s);
                finish();

            }
        }.execute();


    }

}
