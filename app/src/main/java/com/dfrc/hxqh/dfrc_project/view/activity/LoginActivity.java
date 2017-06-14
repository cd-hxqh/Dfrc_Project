package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.EditText;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.api.HttpManager;
import com.dfrc.hxqh.dfrc_project.api.HttpRequestHandler;
import com.dfrc.hxqh.dfrc_project.api.JsonUtils;
import com.dfrc.hxqh.dfrc_project.bean.Results;
import com.dfrc.hxqh.dfrc_project.constants.Constants;
import com.dfrc.hxqh.dfrc_project.model.PERSON;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.webserviceclient.AndroidClientService;
import com.mpt.hxqh.dfrc_project.AppManager;
import com.pgyersdk.update.PgyUpdateManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * d登陆界面
 **/

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    @Bind(R.id.login_username_edit)
    EditText mUsername;
    @Bind(R.id.login_password_edit)
    EditText mPassword;


    private boolean isRemember; //是否记住密码

    String imei; //imei

    private long exitTime = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        PgyUpdateManager.register(LoginActivity.this, getString(R.string.file_provider));
        imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
                .getDeviceId();

        if (AccountUtils.getIpAddress(LoginActivity.this).equals("")) {
            AccountUtils.setIpAddress(LoginActivity.this, Constants.HTTP_API_IP);
        }
        findViewById();
        initView();


    }

    @Override
    protected void findViewById() {
        boolean isChecked = AccountUtils.getIsChecked(LoginActivity.this);
        if (isChecked) {
            mUsername.setText(AccountUtils.getUserName(LoginActivity.this));
            mPassword.setText(AccountUtils.getUserPassword(LoginActivity.this));
        }

    }

    //记住密码
    @OnCheckedChanged(R.id.checkBox)
    void onChecked(boolean isChecked) {
        isRemember = isChecked;
    }


    @Override
    protected void initView() {

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

    //登陆按钮
    @OnClick(R.id.btn_login)
    void setOnClick() {
        if (mUsername.getText().length() == 0) {
            mUsername.setError(getString(R.string.login_error_empty_user));
            mUsername.requestFocus();
        } else if (mPassword.getText().length() == 0) {
            mPassword.setError(getString(R.string.login_error_empty_passwd));
            mPassword.requestFocus();
        } else {
            login();
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
                            getPerson(mUsername.getText().toString());
                            getUserApp(mUsername.getText().toString());
                        }
                    }

                    @Override
                    public void onSuccess(String data, int totalPages, int currentPage) {
                        if (data != null) {
                            getPerson(mUsername.getText().toString());
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

    /**
     * 获取Person*
     */
    private void getPerson(final String username) {
        HttpManager.getDataPagingInfo(LoginActivity.this, HttpManager.getPERSIONByIDURL(username, 1, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<PERSON> item = JsonUtils.parsingPERSON(results.getResultlist());
                if (item == null || item.isEmpty()) {
                } else {

                    Log.i(TAG, "BZ=" + item.get(0).getN_CREWID());
                    AccountUtils.setPerson(LoginActivity.this, item.get(0));
                }
            }

            @Override
            public void onFailure(String error) {
            }
        });
    }

}
