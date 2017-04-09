package com.dfrc.hxqh.dfrc_project.webserviceclient;


import android.content.Context;
import android.util.Log;

import com.dfrc.hxqh.dfrc_project.constants.Constants;
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
     * 粮情检查单新增及修改
     */
    public static String addAndUpdateN_grainjc(final Context cxt, String json) {

        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.lqwebserviceURL;

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "n_grainsaddAccount");
        soapReq.addProperty("json", json);//封装数据
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


}
