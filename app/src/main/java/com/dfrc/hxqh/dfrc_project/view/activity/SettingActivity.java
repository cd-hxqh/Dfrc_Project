package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 设置界面
 */
public class SettingActivity extends BaseActivity {
    private static String TAG = "SettingActivity";


    @Bind(R.id.title_back_id)
    ImageView backImageView; //返回按钮

    @Bind(R.id.title_name)
    TextView titleTextView; //标题

    @Bind(R.id.login_status_text_id)
    TextView loginStatus; //登陆状态

    @Bind(R.id.version_text_id)
    TextView versionName; //名称



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        findViewById();
        initView();
        getVerseronInfo();
    }


    @Override
    protected void findViewById() {


    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.settings);
        if(AccountUtils.getIsOffLine(SettingActivity.this)){
            Log.i(TAG,"离线");
            loginStatus.setText(R.string.unline_text);
        }else{
            Log.i(TAG,"在线");
            loginStatus.setText(R.string.online_text);
        }



    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageViewOnClickListener() {
        finish();
    }

    //版本更新
    @OnClick(R.id.version_down_id)
    void setVersonTextViewOnClickListener() {
        PgyUpdateManager.register(SettingActivity.this, getString(R.string.file_provider));

    }


    //注销
    @OnClick(R.id.zx_text_id)
    void setZxTextViewOnClickListener() {
        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
        startActivityForResult(intent, 0);
    }


    //版本更新检查
    private void getVerseronInfo() {
        // 版本检测方式2：带更新回调监听
        PgyUpdateManager.register(SettingActivity.this, getString(R.string.file_provider),
                new UpdateManagerListener() {
                    @Override
                    public void onUpdateAvailable(final String result) {

                        // 将新版本信息封装到AppBean中
                        final AppBean appBean = getAppBeanFromString(result);
                        versionName.setText("新版本:" + appBean.getVersionName());
                    }

                    @Override
                    public void onNoUpdateAvailable() {
                    }
                });
    }
}
