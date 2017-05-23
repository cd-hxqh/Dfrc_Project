package com.dfrc.hxqh.dfrc_project.webserviceclient;


import android.content.Context;
import android.util.Log;

import com.dfrc.hxqh.dfrc_project.constants.Constants;
import com.dfrc.hxqh.dfrc_project.model.MATRECTRANS;
import com.dfrc.hxqh.dfrc_project.model.MATUSETRANS;
import com.dfrc.hxqh.dfrc_project.model.N_MATERIAL;
import com.dfrc.hxqh.dfrc_project.model.N_PROBLEM;
import com.dfrc.hxqh.dfrc_project.model.UDCANRTN;
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
            Log.i(TAG, "obj=" + obj);
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


    /**
     * 物料接收新增
     */
    public static String INV02RecByPOLine(final Context cxt, MATRECTRANS matrectrans) {

        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.LoginwebserviceURL;
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
        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.LoginwebserviceURL;

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

        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.LoginwebserviceURL;

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "dflserviceAddN_WORKOR2Line");

        soapReq.addProperty("WONUM", n_material.getWONUM()); //工单号
        soapReq.addProperty("SITEID", n_material.getSITEID());//站点
        soapReq.addProperty("STATUS", n_material.getSTATUS());//状态
        soapReq.addProperty("N_SAP1", n_material.getN_SAP1());// 数量
        soapReq.addProperty("ITEMNUM", n_material.getITEMNUM());//物料号
        soapReq.addProperty("REASON", n_material.getN_REASON());//领料原因
        soapReq.addProperty("N_SAP3", n_material.getN_SAP3());//实际发放数量
        soapReq.addProperty("N_SAP5", n_material.getN_SAP5());//申请数量
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
        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.LoginwebserviceURL;
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
    public static String searchMaint2(final Context cxt, String key, String keyvalue) {
        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.LoginwebserviceURL;
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "dflservicesearchMaint2");

        soapReq.addProperty("KEY", key); //key
        soapReq.addProperty("KEYVALUE", keyvalue);//keyvalue
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
            Log.i(TAG, "值:" + obj);
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

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mobileserviceuploadImage");
        soapReq.addProperty("filename", filename);//文件名
        soapReq.addProperty("image", image);//图片Json
        soapReq.addProperty("ownertable", ownertable);//表名
        soapReq.addProperty("ownerid", ownerid);//表主键值
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
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
