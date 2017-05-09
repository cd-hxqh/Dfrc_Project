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
    public static String getASSETURL(String vaule, String siteid, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ASSETNUM DESC','condition':{'STATUS':'=活动','SITEID':'=" + siteid + "'}}";

        } else {
            return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ASSETNUM DESC','condition':{'STATUS':'=活动','SITEID':'=" + siteid + "'},'sinorsearch':{'ASSETNUM':'" + vaule + "','DESCRIPTION':'" + vaule + "'}}";
        }
    }

    /**
     * 根据班组获取设备
     */
    public static String getASSETURL1(String vaule, String n_crewid, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ASSETNUM DESC','condition':{'N_CREWID':'=" + n_crewid + "'}}";

        } else {
            return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ASSETNUM DESC','condition':{'N_CREWID':'=" + n_crewid + "'},'sinorsearch':{'ASSETNUM':'" + vaule + "','DESCRIPTION':'" + vaule + "'}}";
        }
    }

    /**
     * 根据编号获取设备
     */
    public static String getASSETByNuMURL(String assetNum, String vaule, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ASSETNUM DESC','condition':{'ASSETNUM':'=" + assetNum + "'}}";

        } else {
            return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ASSETNUM DESC','condition':{'ASSETNUM':'=" + assetNum + "'},'sinorsearch':{'ASSETNUM':'" + vaule + "','DESCRIPTION':'" + vaule + "'}}";
        }
    }

    /**
     * 设置根据资产编号获取问题点
     */
    public static String getN_PROBLEMURL(String assetnum,String vaule, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.N_PROBLEM_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'N_PROBLEMNUM DESC','condition':{'ASSETNUM':'=" + assetnum + "'}}";

        } else {
            return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.N_PROBLEM_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'N_PROBLEMNUM DESC','condition':{'ASSETNUM':'=" + assetnum + "'}','sinorsearch':{'N_PROBLEMNUM':'" + vaule + "','PRODESC':'" + vaule + "'}}";
        }
    }


    /**
     * 设置根据资产编号获取故障工单
     */
    public static String getGZWORKORDERURL(String assetnum, String vaule, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WONUM DESC','condition':{'WORKTYPE':'=FM','ASSETNUM':'=" + assetnum + "'}}";

        } else {
            return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WONUM DESC','condition':{'WORKTYPE':'=FM','ASSETNUM':'=" + assetnum + "'},'sinorsearch':{'WONUM':'" + vaule + "','DESCRIPTION':'" + vaule + "'}}";
        }
    }


    /**
     * 设置根据设备编号获取备件
     */
    public static String getSPAREPARTURL(String vaule, String assetnum, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.SPAREPART_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'ASSETNUM':'=" + assetnum + "'}}";

        } else {
            return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.SPAREPART_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'ASSETNUM':'=" + assetnum + "'},'sinorsearch':{'ITEMNUM':'" + vaule + "'}}";
        }
    }






    /**
     * 设置定期点检工单
     */
    public static String getWORKORDERURL(String vaule, String crewid, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.N_MATWO_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WONUM DESC','condition':{'WORKTYPE':'=MAINT','WKTYPE':'=MAINT','CREWID':'=" + crewid + "'}}";

        } else {
            return "{'appid':'" + Constants.N_MATWO_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WONUM DESC','condition':{'WORKTYPE':'=MAINT','WKTYPE':'=MAINT','CREWID':'=" + crewid + "'},'sinorsearch':{'WONUM':'" + vaule + "','DESCRIPTION':'" + vaule + "'}}";
        }
    }


    /**
     * 设置定期点检工单明细行
     */
    public static String getWOTASKURL(String vaule, String wonum, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.N_MATWO_APPID + "','objectname':'" + Constants.WOTASK_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WOSEQUENCE DESC','condition':{'WONUM':'=" + wonum + "'}}";

        } else {
            return "{'appid':'" + Constants.N_MATWO_APPID + "','objectname':'" + Constants.WOTASK_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WOSEQUENCE DESC','condition':{'WONUM':'=" + wonum + "'},'sinorsearch':{'WOSEQUENCE':'" + vaule + "','ASSETNUM':'" + vaule + "'}}";
        }
    }

    /**
     * 设置定期点检工单明细行
     */
    public static String getWOTASKURL(String vaule, String wonum, String assetnum, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.N_MATWO_APPID + "','objectname':'" + Constants.WOTASK_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WOSEQUENCE DESC','condition':{'WONUM':'=" + wonum + "','ASSETNUM':'=" + assetnum + "'}}";

        } else {
            return "{'appid':'" + Constants.N_MATWO_APPID + "','objectname':'" + Constants.WOTASK_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WOSEQUENCE DESC','condition':{'WONUM':'=" + wonum + "','ASSETNUM':'=" + assetnum + "'},'sinorsearch':{'WOSEQUENCE':'" + vaule + "','ASSETNUM':'" + vaule + "'}}";
        }
    }

    /**
     * 设置问题点管理
     */
    public static String getN_PROBLEMURL(String vaule, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.N_PROB2_APPID + "','objectname':'" + Constants.N_PROBLEM_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'N_PROBLEMNUM DESC'}";

        } else {
            return "{'appid':'" + Constants.N_PROB2_APPID + "','objectname':'" + Constants.N_PROBLEM_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'N_PROBLEMNUM DESC','sinorsearch':{'N_PROBLEMNUM':'" + vaule + "','PRODESC':'" + vaule + "'}}";
        }
    }


    /**
     * 设置获取人员
     */
    public static String getPERSIONURL(String vaule, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.PERSON_APPID + "','objectname':'" + Constants.PERSON_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'PERSONID DESC'}";

        } else {
            return "{'appid':'" + Constants.PERSON_APPID + "','objectname':'" + Constants.PERSON_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'PERSONID DESC','sinorsearch':{'PERSONID':'" + vaule + "','DISPLAYNAME':'" + vaule + "'}}";
        }
    }

    /**
     * 设置根据班组获取人员
     */
    public static String getPERSIONURL(String vaule, String crewid, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.PERSON_APPID + "','objectname':'" + Constants.PERSON_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'PERSONID DESC','condition':{'N_CREWID':'=" + crewid + "'}}";

        } else {
            return "{'appid':'" + Constants.PERSON_APPID + "','objectname':'" + Constants.PERSON_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'PERSONID DESC','condition':{'N_CREWID':'=" + crewid + "'},'sinorsearch':{'PERSONID':'" + vaule + "','DISPLAYNAME':'" + vaule + "'}}";
        }
    }

    /**
     * 设置获取人员
     */
    public static String getPERSIONByIDURL(String personid, int curpage, int showcount) {
        return "{'appid':'" + Constants.PERSON_APPID + "','objectname':'" + Constants.PERSON_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'PERSONID DESC','condition':{'PERSONID':'=" + personid + "'}}";


    }


    /**
     * 设置获取库存
     */
    public static String getINVETORYURL(String vaule, String siteid, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.INVENTOR_APPID + "','objectname':'" + Constants.INVENTORY_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'STATUS':'=活动','SITEID':'=" + siteid + "'}}";

        } else {
            return "{'appid':'" + Constants.INVENTOR_APPID + "','objectname':'" + Constants.INVENTORY_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'STATUS':'=活动','SITEID':'=" + siteid + "'},'sinorsearch':{'ITEMNUM':'" + vaule + "'}}";
        }
    }

    /**
     * 设置根据备件获取库存
     */
    public static String getINVETORYIDURL(String itemnum, String vaule, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.INVENTOR_APPID + "','objectname':'" + Constants.INVENTORY_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'ITEMNUM':'=" + itemnum + "'}}";

        } else {
            return "{'appid':'" + Constants.INVENTOR_APPID + "','objectname':'" + Constants.INVENTORY_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'ITEMNUM':'=" + itemnum + "'},'sinorsearch':{'ITEMNUM':'" + vaule + "'}}";
        }

    }

    /**
     * 设置根据备件编号获取库存余量
     */
    public static String getINVBALANCESURL(String itemnum, String vaule, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.INVENTOR_APPID + "','objectname':'" + Constants.INVBALANCES_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'ITEMNUM':'=" + itemnum + "'}}";

        } else {
            return "{'appid':'" + Constants.INVENTOR_APPID + "','objectname':'" + Constants.INVBALANCES_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'ITEMNUM':'=" + itemnum + "'},'sinorsearch':{'ITEMNUM':'" + vaule + "'}}";
        }

    }


    /**
     * 获取总库领料单
     */
    public static String getN_WORKORURL(String vaule, String siteid, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.N_WORKOR2 + "','objectname':'" + Constants.WORKORDER_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WONUM DESC','condition':{'SITEID':'=" + siteid + "'}}";

        } else {
            return "{'appid':'" + Constants.N_WORKOR2 + "','objectname':'" + Constants.WORKORDER_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WONUM DESC','condition':{'SITEID':'=" + siteid + "'},'sinorsearch':{'WONUM':'" + vaule + "','DESCRIPTION':'" + vaule + "'}}";
        }
    }

    /**
     * 获取总库领料单
     */
    public static String getN_MATERIAL(String vaule, String wonum, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.N_WORKOR2 + "','objectname':'" + Constants.N_MATERIAL + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'WONUM':'=" + wonum + "'}}";

        } else {
            return "{'appid':'" + Constants.N_WORKOR2 + "','objectname':'" + Constants.N_MATERIAL + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'WONUM':'=" + wonum + "'},'sinorsearch':{'ITEMNUM':'" + vaule + "','DESCRIPTION':'" + vaule + "'}}";
        }
    }



    /**
     * 设置根据备件编号获取库存余量
     */
    public static String getINVBALANCESURL1(String location, String vaule, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.INVBALANCES_NAME + "','objectname':'" + Constants.INVBALANCES_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'LOCATION':'=" + location + "'}}";

        } else {
            return "{'appid':'" + Constants.INVBALANCES_NAME + "','objectname':'" + Constants.INVBALANCES_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'LOCATION':'=" + location + "'},'sinorsearch':{'ITEMNUM':'" + vaule +"',''DESCRIPTION':'" + vaule + "'}}";
        }

    }

    /**
     * 获取分库领料单
     */
    public static String getN_WORKORDEURL(String vaule, String siteid, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.N_WORKORDE + "','objectname':'" + Constants.WORKORDER_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WONUM DESC','condition':{'SITEID':'=" + siteid + "'}}";

        } else {
            return "{'appid':'" + Constants.N_WORKORDE + "','objectname':'" + Constants.WORKORDER_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WONUM DESC','condition':{'SITEID':'=" + siteid + "'},'sinorsearch':{'WONUM':'" + vaule + "','DESCRIPTION':'" + vaule + "'}}";
        }
    }


    /**
     * 获取分库领料单行
     */
    public static String getMATUSETRANSURL(String vaule, String wonum, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.N_WORKORDE + "','objectname':'" + Constants.MATUSETRANS + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'REFWO':'=" + wonum + "'}}";

        } else {
            return "{'appid':'" + Constants.N_WORKORDE + "','objectname':'" + Constants.MATUSETRANS + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'REFWO':'=" + wonum + "'},'sinorsearch':{'ITEMNUM':'" + vaule + "','DESCRIPTION':'" + vaule + "'}}";
        }
    }


    /**
     * 获取采购接收
     */
    public static String getPOURL(String vaule, String siteid, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.RECEIPTS_APPID + "','objectname':'" + Constants.PO_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'PONUM DESC','condition':{'SITEID':'=" + siteid + "'}}";

        } else {
            return "{'appid':'" + Constants.RECEIPTS_APPID + "','objectname':'" + Constants.PO_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'PONUM DESC','condition':{'SITEID':'=" + siteid + "'},'sinorsearch':{'PONUM':'" + vaule + "','DESCRIPTION':'" + vaule + "'}}";
        }
    }

    /**
     * 获取采购单行
     */
    public static String getPOLINEURL(String vaule, String ponum, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.RECEIPTS_APPID + "','objectname':'" + Constants.POLINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'POLINENUM ASC','condition':{'PONUM':'=" + ponum + "'}}";

        } else {
            return "{'appid':'" + Constants.RECEIPTS_APPID + "','objectname':'" + Constants.POLINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'POLINENUM ASC','condition':{'PONUM':'=" + ponum + "'},'sinorsearch':{'POLINENUM':'=" + vaule+"','ITEMNUM':'" + vaule + "','DESCRIPTION':'" + vaule + "'}}";
        }
    }

    /**
     * 获取物料接收
     */
    public static String getMATUSETRANS1URL(String vaule, String ponum, int curpage, int showcount) {
        if (vaule.equals("")) {
            return "{'appid':'" + Constants.RECEIPTS_APPID + "','objectname':'" + Constants.MATRECTRANS_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'POLINENUM ASC','condition':{'PONUM':'=" + ponum + "'}}";

        } else {
            return "{'appid':'" + Constants.RECEIPTS_APPID + "','objectname':'" + Constants.MATRECTRANS_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'POLINENUM ASC','condition':{'PONUM':'=" + ponum + "'},'sinorsearch':{'POLINENUM':'=" + vaule+"','ITEMNUM':'" + vaule + "','DESCRIPTION':'" + vaule + "'}}";
        }
    }


    /**
     * 获取选项值
     */
    public static String getALNDOMAINURL(String domainid) {

        return "{'appid':'" + Constants.ALNDOMAIN_APPID + "','objectname':'" + Constants.ALNDOMAIN_NAME + "','option':'read','condition':{'DOMAINID':'=" + domainid + "'}}";


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
