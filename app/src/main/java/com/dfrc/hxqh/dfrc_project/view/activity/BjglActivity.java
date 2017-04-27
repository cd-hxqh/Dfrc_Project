package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/15.
 * 备件管理
 */

public class BjglActivity extends BaseActivity {
    private static final String TAG = "BjglActivity";

    @OnClick(R.id.title_back_id)
    public void setBackOnClickListener() {
        finish();
    }

    @Bind(R.id.title_name)
    TextView titleTextView; //标题


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bjgl);
        ButterKnife.bind(this);
        findViewById();
        initView();
    }


    @Override
    protected void findViewById() {

    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.bjgl_text);

    }


    @OnClick(R.id.cgjs_text_id) //采购接收
    public void setKcjsTextViewOnClickListener() {
        Intent intent = new Intent(this, PoActivity.class);
        startActivityForResult(intent, 0);
    }
    @OnClick(R.id.kccx_text_id) //库存查询
    public void setCgjsTextViewOnClickListener() {
        Intent intent = new Intent(this, Inventoryactivity.class);
        intent.putExtra("assetNum", "");
        startActivityForResult(intent, 0);
    }

    @OnClick(R.id.zklld_text_id) //总库领料单
    public void setZkworkorderOnClickListener() {
        Intent intent = new Intent(this, ZkworkorderActivity.class);
        startActivityForResult(intent, 0);
    }
    @OnClick(R.id.fklld_text_id) //分库领料单
    public void setFkworkorderOnClickListener() {
        Intent intent = new Intent(this, FkworkorderActivity.class);
        startActivityForResult(intent, 0);
    }


}
