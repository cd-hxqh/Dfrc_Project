package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 扫描结果
 */
public class ResultDetailsActivity extends BaseActivity {
    private static String TAG = "ResultDetailsActivity";
    @Bind(R.id.title_name)
    TextView titleTextView; //标题
    @Bind(R.id.result_text_id)
    TextView resultTextView;//结果

    @Bind(R.id.search_btn_id)
    Button searchBtn; //查询资产
    private int mark;
    private String result; //结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_details);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        mark = getIntent().getExtras().getInt("mark");
        result = getIntent().getExtras().getString("result");
        Log.i(TAG, "result=" + result + ",mark=" + mark);
    }

    @Override
    protected void findViewById() {


    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.result_text_id);
        resultTextView.setText(result);

    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageViewOnClickListener() {
        finish();
    }

    //查询资产
    @OnClick(R.id.search_btn_id)
    void setSearchBtnOnClickListener() {
        String assetNum = parsingResult(result);
        if (!assetNum.equals("")) {
            if (mark == AssetActivity.ASSET_CODE) {
                Intent intent = getIntent();
                intent.setClass(ResultDetailsActivity.this, AssetActivity.class);
                intent.putExtra("assetNum", assetNum);
                startActivityForResult(intent, 0);
            } else if (mark == WotaskActivity.WOTASK_CODE) {
                Intent intent = getIntent();
                intent.setClass(ResultDetailsActivity.this, WotaskActivity.class);
                intent.putExtra("assetNum", assetNum);
                startActivityForResult(intent, 0);
            } else if (mark == N_problemActivity.PROBLEM_CODE) {
                Intent intent = getIntent();
                intent.setClass(ResultDetailsActivity.this, N_problemActivity.class);
                intent.putExtra("assetNum", assetNum);
                startActivityForResult(intent, 0);
            }
        } else {
            MessageUtils.showMiddleToast(ResultDetailsActivity.this, getString(R.string.wfcxsb_text));
        }

    }


}
