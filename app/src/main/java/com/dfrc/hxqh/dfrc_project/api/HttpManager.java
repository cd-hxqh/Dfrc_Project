package com.dfrc.hxqh.dfrc_project.api;


import android.content.Context;
import android.util.Log;

import com.dfrc.hxqh.dfrc_project.R;
import com.dfrc.hxqh.dfrc_project.bean.LoginResults;
import com.dfrc.hxqh.dfrc_project.bean.Results;
import com.dfrc.hxqh.dfrc_project.constants.Constants;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;


/**
 * Created by apple on 15/5/27.
 */
public class HttpManager {

    private static AsyncHttpClient sClient = null;
    private static final String TAG = "HttpManager";



    /**
     * 设置获取设备
     */
    public static String getASSETURL(String vaule, int curpage, int showcount) {
        if (vaule.equals("")){
            return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ASSETNUM DESC','condition':{'STATUS':'=活动'}}";

        }
        else {
            return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ASSETNUM DESC','condition':{'STATUS':'=活动'},'sinorsearch':{'ASSETNUM':'" + vaule + "','DESCRIPTION':'" + vaule + "'}}";
        }
    }


    /**
     * 设置根据设备编号获取备件
     */
    public static String getSPAREPARTURL(String vaule,String assetnum, int curpage, int showcount) {
        if (vaule.equals("")){
            return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.SPAREPART_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'ASSETNUM':'="+assetnum+"'}}";

        }
        else {
            return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.SPAREPART_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'ASSETNUM':'="+assetnum+"'},'sinorsearch':{'ITEMNUM':'" + vaule + "'}}";
        }
    }



    /**
     * 设置定期点检工单
     */
    public static String getWORKORDERURL(String vaule, int curpage, int showcount) {
        if (vaule.equals("")){
            return "{'appid':'" + Constants.N_MATWO_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WONUM DESC','condition':{'WORKTYPE':'=MAINT','PARENT':''}}";

        }
        else {
            return "{'appid':'" + Constants.N_MATWO_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WONUM DESC','condition':{'WORKTYPE':'=MAINT','PARENT':''},'sinorsearch':{'WONUM':'" + vaule + "','DESCRIPTION':'" + vaule + "'}}";
        }
    }



    /**
     * 设置定期点检工单明细行
     */
    public static String getWOTASKURL(String vaule,String wonum, int curpage, int showcount) {
        if (vaule.equals("")){
            return "{'appid':'" + Constants.N_MATWO_APPID + "','objectname':'" + Constants.WOTASK_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WOSEQUENCE DESC','condition':{'WONUM':'="+wonum+"'}}";

        }
        else {
            return "{'appid':'" + Constants.N_MATWO_APPID + "','objectname':'" + Constants.WOTASK_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WOSEQUENCE DESC','condition':{'WONUM':'="+wonum+"'},'sinorsearch':{'WOSEQUENCE':'" + vaule + "','ASSETNUM':'" + vaule + "'}}";
        }
    }

    /**
     * 设置问题点管理
     */
    public static String getN_PROBLEMURL(String vaule, int curpage, int showcount) {
        if (vaule.equals("")){
            return "{'appid':'" + Constants.N_PROB2_APPID + "','objectname':'" + Constants.N_PROBLEM_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'N_PROBLEMNUM DESC'}";

        }
        else {
            return "{'appid':'" + Constants.N_PROB2_APPID + "','objectname':'" + Constants.N_PROBLEM_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'N_PROBLEMNUM DESC','sinorsearch':{'N_PROBLEMNUM':'" + vaule + "','PRODESC':'" + vaule + "'}}";
        }
    }


    /**
     * 使用用户名密码登录
     *
     * @param cxt
     * @param username 用户名
     * @param password 密码
     * @param imei     密码
     * @param handler  返回结果处理
     */
    public static void loginWithUsername(final Context cxt, final String username, final String password, String imei,
                                         final HttpRequestHandler<String> handler) {
        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.SIGN_IN_URL;
        Log.i(TAG, "ip_adress=" + ip_adress);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("loginid", username);
        params.put("password", password);
        params.put("imei", imei);
        client.post(ip_adress, params, new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.i(TAG, "SstatusCode=" + statusCode + "responseString=" + responseString);
                SafeHandler.onFailure(handler, IMErrorType.errorMessage(cxt, IMErrorType.ErrorLoginFailure));
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.i(TAG, "SstatusCode=" + statusCode + "responseString=" + responseString);
                if (statusCode == 200) {
                    LoginResults loginResults = JsonUtils.parsingAuthStr(cxt, responseString);
                    if (loginResults != null) {
                        if (loginResults.getErrcode().equals(Constants.LOGINSUCCESS) || loginResults.getErrcode().equals(Constants.CHANGEIMEI)) {
                            SafeHandler.onSuccess(handler, loginResults.getResult());
                        } else if (loginResults.getErrcode().equals(Constants.USERNAMEERROR)) {
                            SafeHandler.onFailure(handler, loginResults.getErrmsg());
                        }
                    }

                }
            }

        });


    }


    /**
     * 不分页获取信息方法*
     */
    public static void getData(final Context cxt, String data, final HttpRequestHandler<Results> handler) {
        Log.i(TAG, "data=" + data);
        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.BASE_URL;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("data", data);
        client.get(ip_adress, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {

                Results result = JsonUtils.parsingResults1(cxt, responseString);

                SafeHandler.onSuccess(handler, result, result.getCurpage(), result.getShowcount());
            }

        });
    }


    /**
     * 解析返回的结果--分页*
     */
    public static void getDataPagingInfo(final Context cxt, String data, final HttpRequestHandler<Results> handler) {
        Log.i(TAG, "data=" + data);
        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.BASE_URL;

        Log.i(TAG, "ip_adress=" + ip_adress);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("data", data);
        client.get(ip_adress, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }


            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Results result = JsonUtils.parsingResults(cxt, responseString);

                SafeHandler.onSuccess(handler, result, result.getCurpage(), result.getShowcount());
            }

        });
    }


    /**
     * 生成物资编码*
     *
     * @ cxt 上下问
     * useruid 用户唯一ID
     * itemreqid 编码申请单唯一标识
     */
    public static void setItemNumber(final Context cxt, final String useruid, final String itemreqid,
                                     final HttpRequestHandler<String> handler) {

        Log.i(TAG, "itemreqid=" + itemreqid);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("useruid", useruid);
        params.put("itemreqid", itemreqid);
        client.post(Constants.ITEM_GENERATE_URL, params, new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, IMErrorType.errorMessage(cxt, IMErrorType.ErrorLoginFailure));
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.i(TAG, "SstatusCode=" + statusCode + "responseString=" + responseString);
            }

        });


    }


    /**
     * 发送工作流
     *
     * @cxt 上下文
     * @ownertable 工作流对应的主表名称
     * @ownerid 工作流对应的主表主键
     * @processname 工作流名称
     * @useruid 当前登录人的唯一标识
     */
    public static void startFlow(final Context cxt, final String ownertable, final String ownerid, final String processname, final String useruid,
                                 final HttpRequestHandler<String> handler) {


        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("ownertable", ownertable);
        params.put("ownerid", ownerid);
        params.put("processname", processname);
        params.put("useruid", useruid);
        client.post(Constants.START_FLOW_URL, params, new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, IMErrorType.errorMessage(cxt, IMErrorType.ErrorLoginFailure));
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.i(TAG, "SstatusCode=" + statusCode + "responseString=" + responseString);

            }

        });


    }

    /**
     * 审批工作流
     *
     * @ownertable 工作流对应的主表名称
     * @ownerid 工作流对应的主表主键
     * @memo 审批意见
     * @selectWhat 是否接受：true/false
     * useruid 当前登录人的唯一标识
     */
    public static void approvalFlow(final Context cxt, final String ownertable, final String ownerid, final String memo, final String selectWhat, final String useruid,
                                    final HttpRequestHandler<String> handler) {


        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("ownertable", ownertable);
        params.put("ownerid", ownerid);
        params.put("memo", memo);
        params.put("selectWhat", selectWhat);
        params.put("useruid", useruid);
        client.post(Constants.APPROVAL_FLOW_URL, params, new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, IMErrorType.errorMessage(cxt, IMErrorType.ErrorLoginFailure));
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.i(TAG, "SstatusCode=" + statusCode + "responseString=" + responseString);
            }

        });


    }


}
