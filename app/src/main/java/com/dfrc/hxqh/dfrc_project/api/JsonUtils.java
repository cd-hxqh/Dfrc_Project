package com.dfrc.hxqh.dfrc_project.api;

import android.content.Context;
import android.util.Log;

import com.dfrc.hxqh.dfrc_project.bean.LoginResults;
import com.dfrc.hxqh.dfrc_project.bean.Results;
import com.dfrc.hxqh.dfrc_project.constants.Constants;
import com.dfrc.hxqh.dfrc_project.model.ASSET;
import com.dfrc.hxqh.dfrc_project.model.N_PROBLEM;
import com.dfrc.hxqh.dfrc_project.model.SPAREPART;
import com.dfrc.hxqh.dfrc_project.model.WORKORDER;
import com.dfrc.hxqh.dfrc_project.model.WOTASK;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Json数据解析类
 */
public class JsonUtils {
    private static final String TAG = "JsonUtils";


    /**
     * 解析登录信息*
     */
    public static LoginResults parsingAuthStr(final Context cxt, String data) {
        Log.i(TAG, "data=" + data);
        LoginResults loginResults = new LoginResults();
        try {
            JSONObject json = new JSONObject(data);
            String errcode = json.getString("errcode");
            String errmsg = json.getString("errmsg");
            loginResults.setErrcode(errcode);
            loginResults.setErrmsg(errmsg);
            if (errcode.equals(Constants.LOGINSUCCESS) || errcode.equals(Constants.CHANGEIMEI)) {
                loginResults.setResult(json.getString("result"));
            }


            return loginResults;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 分页解析返回的结果*
     */
    public static Results parsingResults(Context ctx, String data) {

        String result = null;
        Results results = null;
        try {
            JSONObject json = new JSONObject(data);
            String jsonString = json.getString("errcode");
            if (jsonString.equals(Constants.GETDATASUCCESS)) {
                result = json.getString("result");
                JSONObject rJson = new JSONObject(result);
                String curpage = rJson.getString("curpage");
                String totalresult = rJson.getString("totalresult");
                String resultlist = rJson.getString("resultlist");
                String totalpage = rJson.getString("totalpage");
                String showcount = rJson.getString("showcount");
                results = new Results();
                results.setCurpage(Integer.valueOf(curpage));
                results.setTotalresult(totalresult);
                results.setResultlist(resultlist);
                results.setTotalpage(totalpage);
                results.setShowcount(Integer.valueOf(showcount));
            }

            return results;


        } catch (JSONException e) {
            e.printStackTrace();
            return results;
        }

    }

    /**
     * 不分页解析返回的结果*
     */
    public static Results parsingResults1(Context ctx, String data) {

        String result = null;
        Results results = null;
        try {
            JSONObject json = new JSONObject(data);
            String jsonString = json.getString("errcode");
            if (jsonString.equals(Constants.GETDATASUCCESS)) {
                result = json.getString("result");
                Log.i(TAG, "result=" + result);
                results = new Results();
                results.setResultlist(result);
            }

            return results;


        } catch (JSONException e) {
            e.printStackTrace();
            return results;
        }

    }

    /**
     * 设备
     */
    public static ArrayList<ASSET> parsingASSET(String data) {
        ArrayList<ASSET> list = null;
        ASSET asset = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<ASSET>();
            for (int i = 0; i < jsonArray.length(); i++) {
                asset = new ASSET();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = asset.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = asset.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(asset);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = asset.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(asset, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(asset);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
    /**
     * 备件
     */
    public static ArrayList<SPAREPART> parsingSPAREPART(String data) {
        ArrayList<SPAREPART> list = null;
        SPAREPART sparepart = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<SPAREPART>();
            for (int i = 0; i < jsonArray.length(); i++) {
                sparepart = new SPAREPART();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = sparepart.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = sparepart.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(sparepart);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = sparepart.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(sparepart, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(sparepart);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 定期点检工单
     */
    public static ArrayList<WORKORDER> parsingWORKORDER(String data) {
        ArrayList<WORKORDER> list = null;
        WORKORDER workorder = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<WORKORDER>();
            for (int i = 0; i < jsonArray.length(); i++) {
                workorder = new WORKORDER();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = workorder.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = workorder.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(workorder);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = workorder.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(workorder, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(workorder);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 定期点检工单明细行
     */
    public static ArrayList<WOTASK> parsingWOTASK(String data) {
        ArrayList<WOTASK> list = null;
        WOTASK wotask = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<WOTASK>();
            for (int i = 0; i < jsonArray.length(); i++) {
                wotask = new WOTASK();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = wotask.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = wotask.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(wotask);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = wotask.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(wotask, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(wotask);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }




    /**
     * 问题点管理
     */
    public static ArrayList<N_PROBLEM> parsingN_PROBLEM(String data) {
        ArrayList<N_PROBLEM> list = null;
        N_PROBLEM n_problem = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_PROBLEM>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_problem = new N_PROBLEM();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_problem.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_problem.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_problem);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_problem.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_problem, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_problem);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }




}