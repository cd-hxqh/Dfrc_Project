package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.api.HttpManager;
import com.dfrc.hxqh.dfrc_project.api.HttpRequestHandler;
import com.dfrc.hxqh.dfrc_project.constants.Constants;
import com.dfrc.hxqh.dfrc_project.dialog.FlippingLoadingDialog;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.webserviceclient.AndroidClientService;
import com.mpt.hxqh.dfrc_project.AppManager;
import com.tencent.bugly.Bugly;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * d登陆界面
 **/

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private EditText mUsername;
    private EditText mPassword;

    private Button loginBtn;

    private CheckBox checkBox; //记住密码


    private boolean isRemember; //是否记住密码

    String userName; //用户名
    String userPassWorld; //密码
    String imei; //imei

    private long exitTime = 0;

    protected FlippingLoadingDialog mLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bugly.init(getApplicationContext(), "2d01706f1a", true);

        imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
                .getDeviceId();

        if (AccountUtils.getIpAddress(LoginActivity.this).equals("")) {
            AccountUtils.setIpAddress(LoginActivity.this, Constants.HTTP_API_IP);
        }
        findViewById();
        initView();
        setEvent();


    }

    @Override
    protected void findViewById() {

        mUsername = (EditText) findViewById(R.id.login_username_edit);
        mPassword = (EditText) findViewById(R.id.login_password_edit);

        loginBtn = (Button) findViewById(R.id.btn_login);

        boolean isChecked = AccountUtils.getIsChecked(LoginActivity.this);
        if (isChecked) {
            mUsername.setText(AccountUtils.getUserName(LoginActivity.this));
            mPassword.setText(AccountUtils.getUserPassword(LoginActivity.this));
        }


        checkBox = (CheckBox) findViewById(R.id.checkBox);

    }

    /**
     * 设置事件监听*
     */
    private void setEvent() {
        checkBox.setOnCheckedChangeListener(cheBoxOnCheckedChangListener);
    }


    private CompoundButton.OnCheckedChangeListener cheBoxOnCheckedChangListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            isRemember = isChecked;
        }
    };


    @Override
    protected void initView() {
        loginBtn.setOnClickListener(this);

        if (AccountUtils.getIpAddress(LoginActivity.this) == null
                || AccountUtils.getIpAddress(LoginActivity.this).equals("")) {//初始化地址
            AccountUtils.setIpAddress(LoginActivity.this, Constants.HTTP_API_IP);
        }

    }


    /**
     * 跳转至主界面*
     */
    private void startIntent(ArrayList<String> list) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.putExtra("appidArray", list);
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (mUsername.getText().length() == 0) {
                    mUsername.setError(getString(R.string.login_error_empty_user));
                    mUsername.requestFocus();
                } else if (mPassword.getText().length() == 0) {
                    mPassword.setError(getString(R.string.login_error_empty_passwd));
                    mPassword.requestFocus();
                } else {
                    login();
                }
                break;

        }
    }


    /**
     * 登陆*
     */
    private void login() {
        getLoadingDialog("正在登陆...").show();

        HttpManager.loginWithUsername(LoginActivity.this,
                mUsername.getText().toString(),
                mPassword.getText().toString(), imei,
                new HttpRequestHandler<String>() {
                    @Override
                    public void onSuccess(String data) {

                        getLoadingDialog("正在登陆...").dismiss();
                        if (data != null) {
                            getBaseApplication().setUsername(mUsername.getText().toString());
                            if (isRemember) {
                                AccountUtils.setChecked(LoginActivity.this, isRemember);
                                //记住密码
                                AccountUtils.setUserNameAndPassWord(LoginActivity.this, mUsername.getText().toString(), mPassword.getText().toString());
                            }
                            try {//保存登录返回信息
                                JSONObject object = new JSONObject(data);
                                JSONObject LoginDetails = object.getJSONObject("userLoginDetails");
                                AccountUtils.setLoginDetails(LoginActivity.this, LoginDetails.getString("insertOrg"), LoginDetails.getString("insertSite"),
                                        LoginDetails.getString("personId"), object.getString("userName"), LoginDetails.getString("displayName"), LoginDetails.getString("loginUserName"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            getUserApp(mUsername.getText().toString());
                        }
                    }

                    @Override
                    public void onSuccess(String data, int totalPages, int currentPage) {
                        if (data != null) {

                            getUserApp(mUsername.getText().toString());

                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        MessageUtils.showErrorMessage(LoginActivity.this, error);
                        getLoadingDialog("正在登陆...").dismiss();
                    }
                });
    }


    @Override
    public void onBackPressed() {


        if ((System.currentTimeMillis() - exitTime) > 2000) {
            MessageUtils.showMiddleToast(this, getString(R.string.tccx_text));
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.AppExit(LoginActivity.this);
        }
    }

    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }

    private boolean isJsonArrary(String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }


    /**
     * 获取用户名显示权限数据*
     */
    private void getUserApp(final String username) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return AndroidClientService.mobilelogin_getUserApp(LoginActivity.this, username);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (isJsonArrary(s)) {
                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        JSONObject jsonObject;
                        ArrayList<String> arrayList = new ArrayList<String>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            if (jsonObject.has("appid")) {
                                arrayList.add(jsonObject.getString("appid"));
                            }
                        }
                        MessageUtils.showMiddleToast(LoginActivity.this, getString(R.string.login_successful_hint));
                        startIntent(arrayList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    MessageUtils.showErrorMessage(LoginActivity.this, "获取权限失败");
                }
            }
        }.execute();
    }

}
