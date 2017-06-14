package com.dfrc.hxqh.dfrc_project.view.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.dfrc.hxqh.dfrc_project.BaseApplication;
import com.dfrc.hxqh.dfrc_project.dialog.FlippingLoadingDialog;
import com.mpt.hxqh.dfrc_project.AppManager;

import butterknife.ButterKnife;


public abstract class BaseActivity extends ActionBarActivity {

    public FlippingLoadingDialog mLoadingDialog;

    protected BaseApplication baseApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        baseApplication = (BaseApplication) getApplication();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        //结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }



    public FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }



    public void colseProgressBar() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    /**
     *
     */
    protected BaseApplication getBaseApplication(){
        return baseApplication;
    }

    /**
     * 绑定控件id
     */
    protected abstract void findViewById();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    //解析设备编号
    public String parsingResult(String result) {
        String[] s = result.split("\\n");
        String assetNum = s[0].substring(s[0].indexOf("：") + 1);
        return assetNum;
    }

}
