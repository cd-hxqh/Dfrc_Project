package com.dfrc.hxqh.dfrc_project.webserviceclient;


import android.content.Context;
import android.util.Log;

import com.dfrc.hxqh.dfrc_project.constants.Constants;
import com.dfrc.hxqh.dfrc_project.model.N_PROBLEM;
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
        String ip_adress = AccountUtils.getIpAddress(context) + Constants.LoginwebserviceURL;
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


    /**
     * 定期检查单OK
     */
    public static String MaintWOIsOk(final Context cxt, String wosequence, String n_result, String n_note, String n_members, String wonum) {

        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.LoginwebserviceURL;

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "dflserviceMaintWOIsOk");
        soapReq.addProperty("WOSEQUENCE", wosequence);
        soapReq.addProperty("N_RESULT", n_result);
        soapReq.addProperty("N_NOTE", n_note);
        soapReq.addProperty("N_MEMBERS", n_members);
        soapReq.addProperty("WONUM", wonum);
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
    public static String MaintWOIsNo(final Context cxt, String personid, String wosequence, String n_result, String n_note, String n_members, String wonum, String assetnum, String siteid, String crewid, String responsor, String position, String reason, String solve, String finishdate, String prodesc) {

        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.LoginwebserviceURL;

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "dflserviceMaintWOIsNo");
        soapReq.addProperty("PERSONID", personid); //登录人ID
        soapReq.addProperty("WOSEQUENCE", wosequence);//点检项序号
        soapReq.addProperty("N_RESULT", n_result);//结果
        soapReq.addProperty("N_NOTE", n_note);// 预知项目结果
        soapReq.addProperty("N_MEMBERS", n_members);//实施人
        soapReq.addProperty("WONUM", wonum);//定期点检工单号
        soapReq.addProperty("ASSETNUM", assetnum);//设备编号
        soapReq.addProperty("SITEID", siteid);//站点
        soapReq.addProperty("CREWID", crewid); //班组
        soapReq.addProperty("RESPONSOR", responsor);//问题点担当
        soapReq.addProperty("POSITION", position);//部位
        soapReq.addProperty("REASON", reason); //原因
        soapReq.addProperty("SOLVE", solve); //解决办法
        soapReq.addProperty("FINISHDATE", finishdate);//完成日期
        soapReq.addProperty("PRODESC", prodesc);//问题点录入
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
     * 通用修改
     */
    public static String UpdateMbo(final Context cxt, String json, String mboObjectName, String mboKey, String mboKeyValue) {

        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.MOBILESERVICEURL;

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
            Log.i(TAG,"obj="+obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }


    /**
     * 定期检查单问题点
     */
    public static String MaintWOPro(final Context cxt, String personid, String wosequence, String n_result, String n_note, String n_members, String wonum, String assetnum, String siteid, String crewid, String responsor, String position, String reason, String solve, String finishdate, String prodesc) {

        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.LoginwebserviceURL;

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "dflserviceMaintWOIsNo");
        soapReq.addProperty("PERSONID", personid); //登录人ID
        soapReq.addProperty("WOSEQUENCE", wosequence);//点检项序号
        soapReq.addProperty("N_RESULT", n_result);//结果
        soapReq.addProperty("N_NOTE", n_note);// 预知项目结果
        soapReq.addProperty("N_MEMBERS", n_members);//实施人
        soapReq.addProperty("WONUM", wonum);//定期点检工单号
        soapReq.addProperty("ASSETNUM", assetnum);//设备编号
        soapReq.addProperty("SITEID", siteid);//站点
        soapReq.addProperty("CREWID", crewid); //班组
        soapReq.addProperty("RESPONSOR", responsor);//问题点担当
        soapReq.addProperty("POSITION", position);//部位
        soapReq.addProperty("REASON", reason); //原因
        soapReq.addProperty("SOLVE", solve); //解决办法
        soapReq.addProperty("FINISHDATE", finishdate);//完成日期
        soapReq.addProperty("PRODESC", prodesc);//问题点录入
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
     * 问题点新增
     */
    public static String CreateProblem(final Context cxt, N_PROBLEM n_problem) {

        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.LoginwebserviceURL;

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
}
