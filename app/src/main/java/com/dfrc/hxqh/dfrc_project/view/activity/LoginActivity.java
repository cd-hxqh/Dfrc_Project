package com.dfrc.hxqh.dfrc_project.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.api.HttpManager;
import com.dfrc.hxqh.dfrc_project.api.HttpRequestHandler;
import com.dfrc.hxqh.dfrc_project.api.JsonUtils;
import com.dfrc.hxqh.dfrc_project.bean.Results;
import com.dfrc.hxqh.dfrc_project.constants.Constants;
import com.dfrc.hxqh.dfrc_project.dao.UserDao;
import com.dfrc.hxqh.dfrc_project.dao.UserPermissionsDao;
import com.dfrc.hxqh.dfrc_project.model.PERSON;
import com.dfrc.hxqh.dfrc_project.model.USER;
import com.dfrc.hxqh.dfrc_project.model.USERPERMISSIONS;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.dfrc.hxqh.dfrc_project.until.MessageUtils;
import com.dfrc.hxqh.dfrc_project.until.NetWorkHelper;
import com.dfrc.hxqh.dfrc_project.webserviceclient.AndroidClientService;
import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.NormalListDialog;
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
    private static final String TAG = "LoginActivity1";
    @Bind(R.id.login_username_edit)
    EditText mUsername;
    @Bind(R.id.login_password_edit)
    EditText mPassword;

    @Bind(R.id.checkBox)
    CheckBox isCheckBox;
    private boolean isRemember; //是否记住密码

    @Bind(R.id.ip_address_id)
    TextView adressText; //服务器地址

    String imei; //imei

    private long exitTime = 0;

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private UserDao userDao; //用户DAO
    private UserPermissionsDao userpermissionsdao; //用户权限DAO


    private ArrayList<DialogMenuItem> mMenuItems = new ArrayList<>();
    private String[] idadresss;
    private String adress;

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
        initDAO();
        findViewById();
        initView();
        if (NetWorkHelper.isNetwork(this)) {
            MessageUtils.showMiddleToast(this, "网络连接不可用");
        }

        adress = AccountUtils.getIpAddress(LoginActivity.this);
        addIpData();

    }

    /**
     * 初始化用户DAO
     **/
    private void initDAO() {
        userDao = new UserDao(this);
        userpermissionsdao = new UserPermissionsDao(this);
    }

    @Override
    protected void findViewById() {
        boolean isChecked = AccountUtils.getIsChecked(LoginActivity.this);
        Log.i(TAG, "isChecked" + isChecked);
        isCheckBox.setChecked(isChecked);
        if (isChecked) {
            mUsername.setText(AccountUtils.getUserName(LoginActivity.this));
            mPassword.setText(AccountUtils.getUserPassword(LoginActivity.this));
        } else {
            mUsername.setText(AccountUtils.getUserName(LoginActivity.this));
            mPassword.setText("");
        }

    }

    //记住密码
    @OnCheckedChanged(R.id.checkBox)
    void onChecked(boolean isChecked) {
        isRemember = isChecked;
    }


    //服务器地址
    @OnClick(R.id.ip_address_id)
    void setAdressTextOnClickListener() {
        mMenuItems = new ArrayList<>();
        addIpData();
        NormalListDialog();
    }


    /**
     * 设置服务端地址*
     */
    private void addIpData() {
        String[] inspotypes = getResources().getStringArray(R.array.address_text);
        idadresss = getResources().getStringArray(R.array.address_text);

        for (int i = 0; i < inspotypes.length; i++) {
            if (adress != null && adress.equals(inspotypes[i].split(" ")[1])) {
                mMenuItems.add(new DialogMenuItem("√  " + inspotypes[i], 0));
            } else {
                mMenuItems.add(new DialogMenuItem("    " + inspotypes[i], 0));
            }
        }
    }


    private void NormalListDialog() {
        final NormalListDialog dialog = new NormalListDialog(LoginActivity.this, mMenuItems);
        dialog.title("请选择")//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "ip=" + idadresss[position]);
                String ip = idadresss[position];
                if (ip.contains("√")) {
                    ip.replace("√", "");
                }
                AccountUtils.setIpAddress(LoginActivity.this, getResources().getStringArray(R.array.address_text)[position].split(" ")[1].trim());
                adress = getResources().getStringArray(R.array.address_text)[position].split(" ")[1].trim();
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void initView() {

//        if (AccountUtils.getIpAddress(LoginActivity.this) == null
//                || AccountUtils.getIpAddress(LoginActivity.this).equals("")) {//初始化地址
//            AccountUtils.setIpAddress(LoginActivity.this, Constants.HTTP_API_IP);
//        }

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();

    }


    /**
     * 跳转至主界面*
     */
    private void startIntent() {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
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
        } else if (NetWorkHelper.isNetwork(LoginActivity.this)) {
            LoginOffline();
        } else {
            login();
        }
    }

    /**
     * 离线登录*
     */
    private void LoginOffline() {
        final NormalDialog dialog = new NormalDialog(LoginActivity.this);
        dialog.content("确定离线登录吗?")//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                        //界面输入的用户名和密码
                        String username = mUsername.getText().toString();
                        String password = mPassword.getText().toString();
                        AccountUtils.setChecked(LoginActivity.this, isRemember);
                        AccountUtils.setIsOffLine(LoginActivity.this, true);
                        if (userDao.isLoginSccessful(username, password)) {
                            getBaseApplication().setUsername(mUsername.getText().toString());
                            //记住密码
                            AccountUtils.setUserNameAndPassWord(LoginActivity.this, mUsername.getText().toString(), mPassword.getText().toString());
                            MessageUtils.showMiddleToast(LoginActivity.this, "离线登录成功");
                            startIntent();
                        } else {
                            MessageUtils.showMiddleToast(LoginActivity.this, "用户名或密码错误");
                        }

                    }
                });
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
                            AccountUtils.setIsOffLine(LoginActivity.this, false);
                            AccountUtils.setChecked(LoginActivity.this, isRemember);
                            if (isRemember) {
                                //记住密码
                                AccountUtils.setUserNameAndPassWord(LoginActivity.this, mUsername.getText().toString(), mPassword.getText().toString());
                            }
                            //记住密码
                            AccountUtils.setUserNameAndPassWord(LoginActivity.this, mUsername.getText().toString(), mPassword.getText().toString());

                            try {//保存登录返回信息
                                JSONObject object = new JSONObject(data);
                                JSONObject LoginDetails = object.getJSONObject("userLoginDetails");
                                AccountUtils.setLoginDetails(LoginActivity.this, LoginDetails.getString("insertOrg"), LoginDetails.getString("insertSite"),
                                        LoginDetails.getString("personId"), object.getString("userName"), LoginDetails.getString("displayName"), LoginDetails.getString("loginUserName"));

                                USER user = null;
                                //根据USERNAM查询是否有该用户
                                if (isShow(mUsername.getText().toString())) {
                                    user = userDao.findByUserName(mUsername.getText().toString());
                                    user.setUSERNAME(mUsername.getText().toString());
                                    user.setPASSWORD(mPassword.getText().toString());
                                    user.setDISPLAYNAME(LoginDetails.getString("displayName"));
                                    user.setSITE(LoginDetails.getString("insertSite"));
                                    user.setPERSONID(LoginDetails.getString("personId"));
                                    userDao.update(user);
                                } else {
                                    //保存用户信息
                                    user = new USER();
                                    user.setUSERNAME(mUsername.getText().toString());
                                    user.setPASSWORD(mPassword.getText().toString());
                                    user.setDISPLAYNAME(LoginDetails.getString("displayName"));
                                    user.setSITE(LoginDetails.getString("insertSite"));
                                    user.setPERSONID(LoginDetails.getString("personId"));
                                    userDao.create(user);
                                }
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
                        mLoadingDialog.dismiss();
                    }
                });
    }

    /**
     * 判断本地是否保存该用户
     **/
    private boolean isShow(String username) {
        return userDao.isexitByNum(username);
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
                                String appid = jsonObject.getString("appid");
                                Log.i(TAG, "appid=" + appid);
                                if (!userpermissionsdao.isexitByNum(username, appid)) {
                                    USERPERMISSIONS userpermissions = new USERPERMISSIONS();
                                    userpermissions.setPERSONID(username);
                                    userpermissions.setAPPID(appid);
                                    userpermissionsdao.create(userpermissions);
                                }
                            }
                        }
                        MessageUtils.showMiddleToast(LoginActivity.this, getString(R.string.login_successful_hint));
                        startIntent();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        MessageUtils.showMiddleToast(LoginActivity.this, "获取权限失败");
                    }
                } else {
                    MessageUtils.showMiddleToast(LoginActivity.this, "获取权限失败");
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
                Log.i(TAG,"sssssssssss="+results.getResultlist());
                ArrayList<PERSON> item = JsonUtils.parsingPERSON(results.getResultlist());
                if (item == null || item.isEmpty()) {
                } else {
                    AccountUtils.setPerson(LoginActivity.this, item.get(0));
                }
            }

            @Override
            public void onFailure(String error) {
                Log.i(TAG,"eeeeee");
            }
        });
    }

}
