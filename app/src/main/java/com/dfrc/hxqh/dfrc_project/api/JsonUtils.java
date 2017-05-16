package com.dfrc.hxqh.dfrc_project.api;

import android.content.Context;
import android.util.Log;

import com.dfrc.hxqh.dfrc_project.bean.LoginResults;
import com.dfrc.hxqh.dfrc_project.bean.Results;
import com.dfrc.hxqh.dfrc_project.constants.Constants;
import com.dfrc.hxqh.dfrc_project.model.ALNDOMAIN;
import com.dfrc.hxqh.dfrc_project.model.ASSET;
import com.dfrc.hxqh.dfrc_project.model.DOCLINKS;
import com.dfrc.hxqh.dfrc_project.model.FKWORKORDER;
import com.dfrc.hxqh.dfrc_project.model.GZWORKORDER;
import com.dfrc.hxqh.dfrc_project.model.INVBALANCES;
import com.dfrc.hxqh.dfrc_project.model.INVENTORY;
import com.dfrc.hxqh.dfrc_project.model.LOCATIONS;
import com.dfrc.hxqh.dfrc_project.model.MATRECTRANS;
import com.dfrc.hxqh.dfrc_project.model.MATUSETRANS;
import com.dfrc.hxqh.dfrc_project.model.N_BORROWHEAD;
import com.dfrc.hxqh.dfrc_project.model.N_MATERIAL;
import com.dfrc.hxqh.dfrc_project.model.N_PROBLEM;
import com.dfrc.hxqh.dfrc_project.model.PERSON;
import com.dfrc.hxqh.dfrc_project.model.PO;
import com.dfrc.hxqh.dfrc_project.model.POLINE;
import com.dfrc.hxqh.dfrc_project.model.SPAREPART;
import com.dfrc.hxqh.dfrc_project.model.UDCANRTN;
import com.dfrc.hxqh.dfrc_project.model.WORKORDER;
import com.dfrc.hxqh.dfrc_project.model.WOTASK;
import com.dfrc.hxqh.dfrc_project.model.ZKWORKORDER;

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
     * 故障工单
     */
    public static ArrayList<GZWORKORDER> parsingGZWORKORDER(String data) {
        ArrayList<GZWORKORDER> list = null;
        GZWORKORDER gzworkorder = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<GZWORKORDER>();
            for (int i = 0; i < jsonArray.length(); i++) {
                gzworkorder = new GZWORKORDER();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = gzworkorder.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = gzworkorder.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(gzworkorder);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = gzworkorder.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(gzworkorder, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(gzworkorder);
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


    /**
     * 人员
     */
    public static ArrayList<PERSON> parsingPERSON(String data) {
        ArrayList<PERSON> list = null;
        PERSON person = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<PERSON>();
            for (int i = 0; i < jsonArray.length(); i++) {
                person = new PERSON();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = person.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = person.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(person);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = person.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(person, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(person);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 库存查询
     */
    public static ArrayList<INVENTORY> parsingINVENTORY(String data) {
        ArrayList<INVENTORY> list = null;
        INVENTORY inventory = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<INVENTORY>();
            for (int i = 0; i < jsonArray.length(); i++) {
                inventory = new INVENTORY();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = inventory.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = inventory.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(inventory);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = inventory.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(inventory, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(inventory);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 库存余量
     */
    public static ArrayList<INVBALANCES> parsingINVBALANCES(String data) {
        ArrayList<INVBALANCES> list = null;
        INVBALANCES invbalances = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<INVBALANCES>();
            for (int i = 0; i < jsonArray.length(); i++) {
                invbalances = new INVBALANCES();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = invbalances.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = invbalances.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(invbalances);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = invbalances.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(invbalances, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(invbalances);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 总库领料单
     */
    public static ArrayList<ZKWORKORDER> parsingZKWORKORDER(String data) {
        ArrayList<ZKWORKORDER> list = null;
        ZKWORKORDER zkworkorder = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<ZKWORKORDER>();
            for (int i = 0; i < jsonArray.length(); i++) {
                zkworkorder = new ZKWORKORDER();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = zkworkorder.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = zkworkorder.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(zkworkorder);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = zkworkorder.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(zkworkorder, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(zkworkorder);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 总库领料单明细行
     */
    public static ArrayList<N_MATERIAL> parsingN_MATERIAL(String data) {
        ArrayList<N_MATERIAL> list = null;
        N_MATERIAL n_material = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_MATERIAL>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_material = new N_MATERIAL();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_material.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_material.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_material);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_material.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_material, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_material);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 分库领料单
     */
    public static ArrayList<FKWORKORDER> parsingFKWORKORDER(String data) {
        ArrayList<FKWORKORDER> list = null;
        FKWORKORDER fkworkorder = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<FKWORKORDER>();
            for (int i = 0; i < jsonArray.length(); i++) {
                fkworkorder = new FKWORKORDER();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = fkworkorder.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = fkworkorder.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(fkworkorder);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = fkworkorder.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(fkworkorder, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(fkworkorder);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 分库领料单行
     */
    public static ArrayList<MATUSETRANS> parsingMATUSETRANS(String data) {
        ArrayList<MATUSETRANS> list = null;
        MATUSETRANS matusetrans = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<MATUSETRANS>();
            for (int i = 0; i < jsonArray.length(); i++) {
                matusetrans = new MATUSETRANS();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = matusetrans.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = matusetrans.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(matusetrans);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = matusetrans.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(matusetrans, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(matusetrans);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 采购接收
     */
    public static ArrayList<PO> parsingPO(String data) {
        ArrayList<PO> list = null;
        PO po = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<PO>();
            for (int i = 0; i < jsonArray.length(); i++) {
                po = new PO();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = po.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = po.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(po);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = po.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(po, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(po);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }



    /**
     * 采购单行
     */
    public static ArrayList<POLINE> parsingPOLINE(String data) {
        ArrayList<POLINE> list = null;
        POLINE poline = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<POLINE>();
            for (int i = 0; i < jsonArray.length(); i++) {
                poline = new POLINE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = poline.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = poline.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(poline);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = poline.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(poline, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(poline);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
    /**
     * 物料接收
     */
    public static ArrayList<MATRECTRANS> parsingMATRECTRANS(String data) {
        ArrayList<MATRECTRANS> list = null;
        MATRECTRANS matrectrans = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<MATRECTRANS>();
            for (int i = 0; i < jsonArray.length(); i++) {
                matrectrans = new MATRECTRANS();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = matrectrans.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = matrectrans.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(matrectrans);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = matrectrans.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(matrectrans, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(matrectrans);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
    /**
     * 物料退回
     */
    public static ArrayList<UDCANRTN> parsingUDCANRTN(String data) {
        ArrayList<UDCANRTN> list = null;
        UDCANRTN udcanrtn = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<UDCANRTN>();
            for (int i = 0; i < jsonArray.length(); i++) {
                udcanrtn = new UDCANRTN();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = udcanrtn.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = udcanrtn.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(udcanrtn);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = udcanrtn.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(udcanrtn, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(udcanrtn);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }



    /**
     * 备件借用
     */
    public static ArrayList<N_BORROWHEAD> parsingN_BORROWHEAD(String data) {
        ArrayList<N_BORROWHEAD> list = null;
        N_BORROWHEAD n_borrowhead = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_BORROWHEAD>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_borrowhead = new N_BORROWHEAD();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_borrowhead.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_borrowhead.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_borrowhead);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_borrowhead.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_borrowhead, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_borrowhead);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }



    /**
     * ALNDOMAIN
     */
    public static ArrayList<ALNDOMAIN> parsingALNDOMAIN(String data) {
        ArrayList<ALNDOMAIN> list = null;
        ALNDOMAIN alndomain = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<ALNDOMAIN>();
            for (int i = 0; i < jsonArray.length(); i++) {
                alndomain = new ALNDOMAIN();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = alndomain.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = alndomain.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(alndomain);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = alndomain.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(alndomain, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(alndomain);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
    /**
     * 位置
     */
    public static ArrayList<LOCATIONS> parsingLOCATION(String data) {
        ArrayList<LOCATIONS> list = null;
        LOCATIONS locations = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<LOCATIONS>();
            for (int i = 0; i < jsonArray.length(); i++) {
                locations = new LOCATIONS();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = locations.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = locations.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(locations);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = locations.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(locations, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(locations);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 图片附件
     */
    public static ArrayList<DOCLINKS> parsingDOCLINKS(String data) {
        ArrayList<DOCLINKS> list = null;
        DOCLINKS doclinks = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<DOCLINKS>();
            for (int i = 0; i < jsonArray.length(); i++) {
                doclinks = new DOCLINKS();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = doclinks.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = doclinks.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(doclinks);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = doclinks.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(doclinks, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(doclinks);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    ////////////////封装json

    /**
     * 封装WOTASK的json
     */
    public static String potoWOTASK(String n_result, String n_note, String n_members) {

        JSONObject json = new JSONObject();

        try {
            json.put("N_RESULT", n_result);  //结果
            json.put("N_NOTE", n_note); //预知项目结果
            json.put("N_MEMBERS", n_members); //实施人员
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("", "");
            jsonArray.put(jsonObject);
            json.put("relationShip", jsonArray);
        } catch (JSONException e) {
            return null;
        }

        Log.i(TAG, "json=" + json);
        return json.toString();

    }


    /**
     * 封装N_PROBLEM的json
     */
    public static String potoN_PROBLEM(N_PROBLEM n_problem) {

        JSONObject json = new JSONObject();

        try {
            json.put("PRODESC", n_problem.getPRODESC());  //问题点描述
            json.put("FINDDATE", n_problem.getFINDDATE()); //发现日期
            json.put("RESPONSOR", n_problem.getRESPONSOR()); //担当
            json.put("PL", n_problem.getPL()); //生产线
            json.put("POSITION", n_problem.getPOSITION()); //部位
            json.put("ASSETNUM", n_problem.getASSETNUM()); //设备编号
            json.put("REASON", n_problem.getREASON()); //原因
            json.put("SOLVE", n_problem.getSOLVE()); //对策
            json.put("FINISHDATE", n_problem.getFINISHDATE()); //完成日期
            json.put("STATUS", n_problem.getSTATUS()); //进展
            json.put("ABC", n_problem.getABC()); //重要度
            json.put("CONFIRMBY", n_problem.getCONFIRMBY()); //确认人
            json.put("RESULT", n_problem.getRESULT()); //整改结果
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("", "");
            jsonArray.put(jsonObject);
            json.put("relationShip", jsonArray);
        } catch (JSONException e) {
            return null;
        }

        Log.i(TAG, "json=" + json);
        return json.toString();

    }






    //解析问题点修改的返回值
    public static String showResults(String json) {
        String success = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.has("success")) {
                success = jsonObject.getString("success");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return success;
    }

}