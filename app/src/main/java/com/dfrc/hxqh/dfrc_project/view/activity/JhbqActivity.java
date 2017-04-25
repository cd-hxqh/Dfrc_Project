package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/15.
 * 计划保全
 */

public class JhbqActivity extends BaseActivity {

    @Bind(R.id.title_back_id)
    ImageView backImageView; //返回按钮
    @Bind(R.id.title_name)
    TextView titleTextView; //标题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jhbq);
        ButterKnife.bind(this);
        findViewById();
        initView();
    }


    @Override
    protected void findViewById() {

    }


    @OnClick(R.id.title_back_id)
    public void setBackOnClickListener() {
        finish();
    }

    @OnClick(R.id.workorder_text_id)
    public void setWorkorderOnClickListener() {
        Intent intent = new Intent(this, WorkorderActivity.class);
        startActivityForResult(intent, 0);
    }
    @OnClick(R.id.n_problem_text_id)
    public void setN_problemOnClickListener() {
        Intent intent = new Intent(this, N_problemActivity.class);
        intent.putExtra("assetNum", "");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void initView() {
        titleTextView.setText(R.string.jhbq_text);

    }


}
