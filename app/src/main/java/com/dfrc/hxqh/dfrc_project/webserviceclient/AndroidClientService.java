package com.dfrc.hxqh.dfrc_project.webserviceclient;


import android.content.Context;
import android.util.Log;

import com.dfrc.hxqh.dfrc_project.constants.Constants;
import com.dfrc.hxqh.dfrc_project.model.MATRECTRANS;
import com.dfrc.hxqh.dfrc_project.model.MATUSETRANS;
import com.dfrc.hxqh.dfrc_project.model.N_MATERIAL;
import com.dfrc.hxqh.dfrc_project.model.N_PROBLEM;
import com.dfrc.hxqh.dfrc_project.model.UDCANRTN;
import com.dfrc.hxqh.dfrc_project.model.WOTASKNG;
import com.dfrc.hxqh.dfrc_project.model.WOTASKOK;
import com.dfrc.hxqh.dfrc_project.model.WOTASKPRO;
import com.dfrc.hxqh.dfrc_project.until.AccountUtils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by think on 2015/8/11.
 * webservice方法
 */
public class AndroidClientService {
    private static final String TAG = "AndroidClientService";
    public static String NAMESPACE = "http://www.ibm.com/maximo";
    public String url = null;
    public static int timeOut = 1200000;


    /**
     * 获取权限
     */
    public static String mobilelogin_getUserApp(Context context, String username) {
        String ip_adress = AccountUtils.getIpAddress(context);
        if (ip_adress.equals(Constants.HTTPZS_API_IP)) {
            ip_adress = Constants.HTTPCES_API_IP + Constants.LoginwebserviceURL;
//            ip_adress = Constants.HTTP_API_IP + Constants.LoginwebserviceURL;
        } else {
            ip_adress = AccountUtils.getIpAddress(context) + Constants.LoginwebserviceURL;
        }
        Log.i(TAG, "ip_adress=" + ip_adress);
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "dflservicegetUserApp");
        soapReq.addProperty("username", username);//用户名
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException | XmlPullParserException e) {
            Log.i(TAG,"rrr");
            return null;
        }
        String obj = null;
        String webResult = null;
        try {
            webResult = soapEnvelope.getResponse().toString();
            Log.i(TAG, "webResult=" + webResult);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
            return null;
        }
        return webResult;
    }


    /**
     * 定期检查单OK
     */
    public static String MaintWOIsOk(final Context context, WOTASKOK wotaskok) {

        String ip_adress = AccountUtils.getIpAddress(context);
        if (ip_adress.equals(Constants.HTTPZS_API_IP)) {
            ip_adress = Constants.HTTPCES_API_IP + Constants.LoginwebserviceURL;
        } else {
            ip_adress = AccountUtils.getIpAddress(context) + Constants.LoginwebserviceURL;
        }

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "dflserviceMaintWOIsOk");
        soapReq.addProperty("WOSEQUENCE", wotaskok.getWOSEQUENCE());
        soapReq.addProperty("N_RESULT", wotaskok.getN_RESULT());
        soapReq.addProperty("N_NOTE", wotaskok.getN_NOTE());
        soapReq.addProperty("N_MEMBERS", wotaskok.getN_MEMBERS());
        soapReq.addProperty("WONUM", wotaskok.getWONUM());
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }


    /**
     * 定期检查单NO
     */
    public static String MaintWOIsNo(final Context context, WOTASKNG wotaskng) {

        String ip_adress = AccountUtils.getIpAddress(context);
        if (ip_adress.equals(Constants.HTTPZS_API_IP)) {
            ip_adress = Constants.HTTPCES_API_IP + Constants.LoginwebserviceURL;
        } else {
            ip_adress = AccountUtils.getIpAddress(context) + Constants.LoginwebserviceURL;
        }

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "dflserviceMaintWOIsNo");
        soapReq.addProperty("PERSONID", wotaskng.getPERSONID()); //登录人ID
        soapReq.addProperty("WOSEQUENCE", wotaskng.getWOSEQUENCE());//点检项序号
        soapReq.addProperty("N_RESULT", wotaskng.getN_RESULT());//结果
        soapReq.addProperty("N_NOTE", wotaskng.getN_NOTE());// 预知项目结果
        soapReq.addProperty("N_MEMBERS", wotaskng.getN_MEMBERS());//实施人
        soapReq.addProperty("WONUM", wotaskng.getWONUM());//定期点检工单号
        soapReq.addProperty("ASSETNUM", wotaskng.getASSETNUM());//设备编号
        soapReq.addProperty("SITEID", wotaskng.getSITEID());//站点
        soapReq.addProperty("CREWID", wotaskng.getCREWID()); //班组
        soapReq.addProperty("RESPONSOR", wotaskng.getRESPONSOR());//问题点担当
        soapReq.addProperty("POSITION", wotaskng.getPOSITION());//部位
        soapReq.addProperty("REASON", wotaskng.getREASON()); //原因
        soapReq.addProperty("SOLVE", wotaskng.getSOLVE()); //解决办法
        soapReq.addProperty("FINISHDATE", wotaskng.getFINISHDATE());//完成日期
        soapReq.addProperty("PRODESC", wotaskng.getPRODESC());//问题点录入
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }

    /**
     * 总库领料单确认发放
     */
    public static String INV03Issue(final Context context, String userid, String wonum, String itemnum, String n_sap1, String crewid, String siteid) {
        Log.i(TAG, "userid=" + userid + ",wonum=" + wonum + ",itemnum=" + itemnum + ",n_sap1=" + n_sap1 + ",crewid=" + crewid + ",siteid=" + siteid);
        String ip_adress = AccountUtils.getIpAddress(context);
        if (ip_adress.equals(Constants.HTTPZS_API_IP)) {
            ip_adress = Constants.HTTPCES_API_IP + Constants.LoginwebserviceURL;
        } else {
            ip_adress = AccountUtils.getIpAddress(context) + Constants.LoginwebserviceURL;
        }
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "dflserviceINV03Issue");
        soapReq.addProperty("userid", userid);
        soapReq.addProperty("wonum", wonum);
        soapReq.addProperty("itemnum", itemnum);
        soapReq.addProperty("n_sap1", n_sap1);
        soapReq.addProperty("crewid", crewid);
        soapReq.addProperty("siteid", siteid);
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            Log.i(TAG, "obj=" + obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }


    /**
     * 通用修改
     */
    public static String UpdateMbo(final Context context, String json, String mboObjectName, String mboKey, String mboKeyValue) {

        String ip_adress = AccountUtils.getIpAddress(context);

        if (ip_adress.equals(Constants.HTTPZS_API_IP)) {
            ip_adress = Constants.HTTPCES_API_IP + Constants.MOBILESERVICEURL;
        } else {
            ip_adress = AccountUtils.getIpAddress(context) + Constants.MOBILESERVICEURL;
        }


        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mobileserviceUpdateMbo");
        soapReq.addProperty("json", json); //json
        soapReq.addProperty("mboObjectName", mboObjectName); //mboObjectName
        soapReq.addProperty("mboKey", mboKey); //mboKey
        soapReq.addProperty("mboKeyValue", mboKeyValue); //mboKeyValue
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            Log.i(TAG, "obj=" + obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }


    /**
     * 定期检查单问题点
     */
    public static String MaintWOPro(final Context cxt, WOTASKPRO wotaskpro) {


        String ip_adress = AccountUtils.getIpAddress(cxt);
        if (ip_adress.equals(Constants.HTTPZS_API_IP)) {
            ip_adress = Constants.HTTPCES_API_IP + Constants.LoginwebserviceURL;
        } else {
            ip_adress = AccountUtils.getIpAddress(cxt) + Constants.LoginwebserviceURL;
        }

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "dflserviceMaintWOPro");
        soapReq.addProperty("PERSONID", wotaskpro.getPERSONID()); //登录人ID
        soapReq.addProperty("WOSEQUENCE", wotaskpro.getWOSEQUENCE());//点检项序号
        soapReq.addProperty("N_RESULT", wotaskpro.getN_RESULT());//结果
        soapReq.addProperty("N_NOTE", wotaskpro.getN_NOTE());// 预知项目结果
        soapReq.addProperty("N_MEMBERS", wotaskpro.getN_MEMBERS());//实施人
        soapReq.addProperty("WONUM", wotaskpro.getWONUM());//定期点检工单号
        soapReq.addProperty("ASSETNUM", wotaskpro.getASSETNUM());//设备编号
        soapReq.addProperty("SITEID", wotaskpro.getSITEID());//站点
        soapReq.addProperty("CREWID", wotaskpro.getCREWID()); //班组
        soapReq.addProperty("RESPONSOR", wotaskpro.getRESPONSOR());//问题点担当
        soapReq.addProperty("POSITION", wotaskpro.getPOSITION());//部位
        soapReq.addProperty("REASON", wotaskpro.getREASON()); //原因
        soapReq.addProperty("SOLVE", wotaskpro.getSOLVE()); //解决办法
        soapReq.addProperty("FINISHDATE", wotaskpro.getFINISHDATE());//完成日期
        soapReq.addProperty("PRODESC", wotaskpro.getPRODESC());//问题点录入
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            Log.i(TAG, "问题点:=" + obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }


    /**
     * 问题点新增
     */
    public static String CreateProblem(final Context cxt, N_PROBLEM n_problem) {


        String ip_adress = AccountUtils.getIpAddress(cxt);
        if (ip_adress.equals(Constants.HTTPZS_API_IP)) {
            ip_adress = Constants.HTTPCES_API_IP + Constants.LoginwebserviceURL;
        } else {
            ip_adress = AccountUtils.getIpAddress(cxt) + Constants.LoginwebserviceURL;
        }


        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "dflserviceCreateProblem");
        soapReq.addProperty("PRODESC", n_problem.getPRODESC()); //问题点描述
        soapReq.addProperty("CREWID", n_problem.getCREWID());//班组
        soapReq.addProperty("FINDDATE", n_problem.getFINDDATE());//发现日期
        soapReq.addProperty("RESPONSOR", n_problem.getRESPONSOR());// 担当
        soapReq.addProperty("PL", n_problem.getPL());//生产线
        soapReq.addProperty("POSITION", n_problem.getPOSITION());//部位
        soapReq.addProperty("ASSETNUM", n_problem.getASSETNUM());//设备编号
        soapReq.addProperty("REASON", n_problem.getREASON());//原因
        soapReq.addProperty("SOLVE", n_problem.getSOLVE()); //对策
        soapReq.addProperty("FINISHDATE", n_problem.getFINISHDATE());//完成日期
        soapReq.addProperty("STATUS", n_problem.getSTATUS());//进展
        soapReq.addProperty("ABC", n_problem.getABC()); //重要度
        soapReq.addProperty("CONFIRMBY", n_problem.getCONFIRMBY()); //确认人
        soapReq.addProperty("RESULT", n_problem.getRESULT());//整改结果
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }


    /**
     * 物料接收新增
     */
    public static String INV02RecByPOLine(final Context cxt, MATRECTRANS matrectrans) {


        String ip_adress = AccountUtils.getIpAddress(cxt);
        if (ip_adress.equals(Constants.HTTPZS_API_IP)) {
            ip_adress = Constants.HTTPCES_API_IP + Constants.LoginwebserviceURL;
        } else {
            ip_adress = AccountUtils.getIpAddress(cxt) + Constants.LoginwebserviceURL;
        }

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "dflserviceINV02RecByPOLine");
        soapReq.addProperty("userid", matrectrans.getENTERBY()); //输入人
        soapReq.addProperty("ponum", matrectrans.getPONUM());//采购单号
        soapReq.addProperty("polinenum", matrectrans.getPOLINENUM());//采购单行号
        soapReq.addProperty("qty", matrectrans.getRECEIPTQUANTITY());// 数量
        soapReq.addProperty("binnum", matrectrans.getBINNUM());//货柜
        soapReq.addProperty("issuetype", "接收");//交易类型
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }

    /**
     * 物料退回新增
     */
    public static String INV02RecByPOLine1(final Context cxt, UDCANRTN matrectrans) {

        String ip_adress = AccountUtils.getIpAddress(cxt);
        if (ip_adress.equals(Constants.HTTPZS_API_IP)) {
            ip_adress = Constants.HTTPCES_API_IP + Constants.LoginwebserviceURL;
        } else {
            ip_adress = AccountUtils.getIpAddress(cxt) + Constants.LoginwebserviceURL;
        }


        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "dflserviceINV02RecByPOLine");
        soapReq.addProperty("userid", matrectrans.getENTERBY()); //输入人
        soapReq.addProperty("ponum", matrectrans.getPONUM());//采购单号
        soapReq.addProperty("polinenum", matrectrans.getPOLINENUM());//采购单行号
        soapReq.addProperty("qty", matrectrans.getQUANTITY());// 数量
        soapReq.addProperty("binnum", matrectrans.getTOBIN());//货柜
        soapReq.addProperty("issuetype", "接收");//交易类型
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }


    /**
     * 总库领料单物料新增
     */
    public static String AddN_WORKOR2Line(final Context cxt, N_MATERIAL n_material) {

        String ip_adress = AccountUtils.getIpAddress(cxt);
        if (ip_adress.equals(Constants.HTTPZS_API_IP)) {
            ip_adress = Constants.HTTPCES_API_IP + Constants.LoginwebserviceURL;
        } else {
            ip_adress = AccountUtils.getIpAddress(cxt) + Constants.LoginwebserviceURL;
        }


        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "dflserviceAddN_WORKOR2Line");

        soapReq.addProperty("WONUM", n_material.getWONUM()); //工单号
        soapReq.addProperty("SITEID", n_material.getSITEID());//站点
        soapReq.addProperty("STATUS", n_material.getSTATUS());//状态
        soapReq.addProperty("N_SAP1", n_material.getN_SAP1());// 数量
        soapReq.addProperty("ITEMNUM", n_material.getITEMNUM());//物料号
        soapReq.addProperty("N_REASON", n_material.getN_REASON());//领料原因
        soapReq.addProperty("N_SAP5", n_material.getN_SAP5());//申请数量
        soapReq.addProperty("TOBIN", n_material.getTOBIN());//分库
        soapReq.addProperty("FROMBIN", n_material.getFROMBIN());//总库
        soapReq.addProperty("SOURCE", n_material.getSOURCE());//来源
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }

    /**
     * 分库库领料单物料新增
     */
    public static String AddN_WORKORDLine(final Context cxt, MATUSETRANS matusetrans) {
        String ip_adress = AccountUtils.getIpAddress(cxt);
        if (ip_adress.equals(Constants.HTTPZS_API_IP)) {
            ip_adress = Constants.HTTPCES_API_IP + Constants.LoginwebserviceURL;
        } else {
            ip_adress = AccountUtils.getIpAddress(cxt) + Constants.LoginwebserviceURL;
        }

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "dflserviceAddN_WORKORDLine");

        soapReq.addProperty("WONUM", matusetrans.getWONUM()); //工单号
        soapReq.addProperty("SITEID", matusetrans.getSITEID());//站点
        soapReq.addProperty("STATUS", matusetrans.getSTATUS());//状态
        soapReq.addProperty("ITEMNUM", matusetrans.getITEMNUM());// 物料号
        soapReq.addProperty("ISSUETYPE", matusetrans.getISSUETYPE());//交易类型
        soapReq.addProperty("STORELOC", matusetrans.getSTORELOC());//库房
        soapReq.addProperty("BINNUM", matusetrans.getBINNUM());//货架
        soapReq.addProperty("POSITIVEQUANTITY", matusetrans.getPOSITIVEQUANTITY());//申请数量
        soapReq.addProperty("ENTERBY", matusetrans.getENTERBY());//领用人
        soapReq.addProperty("N_USEAREA", matusetrans.getN_USEAREA());//使用区域
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }


    /**
     * 获取主菜单的点击工单
     */
    public static String searchMaint2(final Context cxt, String key, String keyvalue, String type) {
        Log.i(TAG, "key=" + key + ",keyvalue=" + keyvalue + ",type=" + type);

        String ip_adress = AccountUtils.getIpAddress(cxt);

        if (ip_adress.equals(Constants.HTTPZS_API_IP)) {
            ip_adress = Constants.HTTPCES_API_IP + Constants.LoginwebserviceURL;
//            ip_adress = Constants.HTTP_API_IP + Constants.LoginwebserviceURL;
        } else {
            ip_adress = AccountUtils.getIpAddress(cxt) + Constants.LoginwebserviceURL;
        }

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "dflservicesearchMaint2");

        soapReq.addProperty("KEY", key); //key
        soapReq.addProperty("KEYVALUE", keyvalue);//keyvalue
        soapReq.addProperty("TYPE", type);//type
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            Log.i(TAG, "obj=" + obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }


    /**
     * 库房新增
     */
    public static String INV05InvAdd(final Context cxt, String userid, String itemnum, String qty, String storeroom, String binnum, String siteid) {
        String ip_adress = AccountUtils.getIpAddress(cxt);
        if (ip_adress.equals(Constants.HTTPZS_API_IP)) {
            ip_adress = Constants.HTTPCES_API_IP + Constants.LoginwebserviceURL;
        } else {
            ip_adress = AccountUtils.getIpAddress(cxt) + Constants.LoginwebserviceURL;
        }

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "dflserviceINV05InvAdd");

        soapReq.addProperty("USERID", userid); //userid
        soapReq.addProperty("ITEMNUM", itemnum);//itemnum
        soapReq.addProperty("QTY", qty);//qty
        soapReq.addProperty("STOREROOM", storeroom);//storeroom
        soapReq.addProperty("BINNUM", binnum);//binnum
        soapReq.addProperty("SITEID", siteid);//siteid
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            Log.i(TAG, "obj=" + obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }

    /**
     * 库位转移
     *
     * @userid 用户ID
     * @itemnum 项目
     * @qty 数量
     * @storeroom1 库房
     * @binnum1 原货柜
     * @storeroom2 目标库房
     * @binnum2 目标货柜
     */
    public static String INV05Invtrans(final Context cxt, String userid, String itemnum, String qty, String storeroom1, String binnum1, String storeroom2, String binnum2) {
        String ip_adress = AccountUtils.getIpAddress(cxt);
        if (ip_adress.equals(Constants.HTTPZS_API_IP)) {
            ip_adress = Constants.HTTPCES_API_IP + Constants.LoginwebserviceURL;
        } else {
            ip_adress = AccountUtils.getIpAddress(cxt) + Constants.LoginwebserviceURL;
        }

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "dflserviceINV05Invtrans");

        soapReq.addProperty("userid", userid); //userid
        soapReq.addProperty("itemnum", itemnum);//itemnum
        soapReq.addProperty("qty", qty);//qty
        soapReq.addProperty("storeroom1", storeroom1);//storeroom
        soapReq.addProperty("binnum1", binnum1);//binnum
        soapReq.addProperty("storeroom2", storeroom2);//siteid
        soapReq.addProperty("binnum2", binnum2);//siteid
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            Log.i(TAG, "obj=" + obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }


    /**
     * 通过webservice实现图片上传
     *
     * @param imageBuffer
     */
    /**
     * 通用修改
     */
    public static String connectWebService(Context context, String filename, String image, String ownertable, String ownerid, String url) {
        String ip_adress = AccountUtils.getIpAddress(context);
        if (ip_adress.equals(Constants.HTTPZS_API_IP)) {
            ip_adress = Constants.HTTPCES_API_IP;
        } else {
            ip_adress = AccountUtils.getIpAddress(context);
        }
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mobileserviceuploadImage");
        soapReq.addProperty("filename", filename);//文件名
        soapReq.addProperty("image", image);//图片Json
        soapReq.addProperty("ownertable", ownertable);//表名
        soapReq.addProperty("ownerid", ownerid);//表主键值
        soapEnvelope.setOutputSoapObject(soapReq);

        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress+ url, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException | XmlPullParserException e) {
            return null;
        }
        String obj = null;
        String webResult = null;
        try {
            webResult = soapEnvelope.getResponse().toString();
            Log.i(TAG, "webResult=" + webResult);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

}
