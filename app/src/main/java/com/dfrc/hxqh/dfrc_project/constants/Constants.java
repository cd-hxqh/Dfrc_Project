package com.dfrc.hxqh.dfrc_project.constants;

/**
 * Created by apple on 15/10/8.
 * 静态常量类
 */
public class Constants {

    /**
     * 基础接口*
     */

    /**
     * 默认 外网*
     */
    public static final String HTTP_API_IP = "http://172.25.185.33:7001";
    //本地
//    public static final String HTTP_API_IP = "http://192.168.101.164:7001";


    //登陆URL
    public static final String SIGN_IN_URL ="/maximo/mobile/system/login";

    //粮情检查单webservice上传接口
    public static final String lqwebserviceURL = "/meaweb/services/N_GRAINS";

    //扦样单webservice上传接口
    public static final String qywebserviceURL = "/meaweb/services/N_SAMPLE";

    //货运预报webservice上传接口
    public static final String carwebserviceURL = "/meaweb/services/N_CAR";

    //扦样单webservice获取车辆信息接口
    public static final String qywebservice1URL = "/meaweb/services/N_SAMPLE1";

    //缺陷工单,快速上报webservice新增接口
    public static final String WorkOrderwebserviceURL = "/meaweb/services/MOBILEROUTES";

    //获取权限接口
    public static final String LoginwebserviceURL = "/meaweb/services/DFLSERVICE";

    //移动设备查询
    public static final String MobileassteURL = "/meaweb/services/MOBILEASSTE";

    //工具设备
    public static final String MOBILETOOLSURL = "/meaweb/services/MOBILETOOLS";

    //通用接口查询
    public static final String BASE_URL =  "/maximo/mobile/common/api";



    //生成物资编码接口
    public static final String ITEM_GENERATE_URL ="/maximo/mobile/itemreq/createCode";
    //发送工作流接口

    public static final String START_FLOW_URL = "/maximo/mobile/wf/start";
    //审批工作流接口
    public static final String APPROVAL_FLOW_URL ="/maximo/mobile/wf/approve";


    /**
     * 图片*
     */
    public static final String WORK_FLOW_URL = "/meaweb/services/WFSERVICE";

    /**
     * ------------------数据库表名配置－－开始*
     */

    //设备appid
    public static final String ASSET_APPID = "ASSET" ;

    //设备的表名
    public static final String ASSET_NAME = "ASSET";
    //备件的表名
    public static final String SPAREPART_NAME = "SPAREPART";

    //库存appid
    public static final String INVENTOR_APPID = "INVENTOR" ;

    //库存的表名
    public static final String INVENTORY_NAME = "INVENTORY";

    //位置appid
    public static final String LOCATION_APPID = "LOCATION" ;

    //位置的表名
    public static final String LOCATIONS_NAME = "LOCATIONS";

    //问题点管理appid
    public static final String N_PROB2_APPID = "N_PROB2" ;

    //问题点管理的表名
    public static final String N_PROBLEM_NAME = "N_PROBLEM";

    //定期点检工单appid
    public static final String N_MATWO_APPID = "N_MATWO" ;

    //定期点检工单的表名
    public static final String WORKORDER_NAME = "WORKORDER";
    //定期点检工单明细行的表名
    public static final String WOTASK_NAME = "WOTASK";

    //采购接收appid
    public static final String RECEIPTS_APPID = "RECEIPTS" ;

    //采购接收的表名
    public static final String PO_NAME = "PO";





    /**
     * 用户登录表识--开始*
     */
    public static final String LOGINSUCCESS = "USER-S-101"; //登录成功

    public static final String CHANGEIMEI = "USER-S-104"; //登录成功,检测到用户更换手机登录

    public static final String USERNAMEERROR = "USER-E-100";//用户名密码错误

    public static final String GETDATASUCCESS = "GLOBAL-S-0";//获取数据成功


    /**
     * 入库管理的发放与接收*
     */
    public static final String RECEIPT = "RECEIPT";//接收
    public static final String RETURN = "RETURN";//退货




    /**选项值**/
    public static final String AREA = "AREA";//区域
    public static final String FRVIEW = "FRVIEW";//处理意见

    public static final String SMJBCL = "SMJBCL";//虫类
    public static final String CHLY = "CHLY";//害虫来源
    public static final String CHVIEW = "CHVIEW";//虫害处理意见
    public static final String CFXN = "CFXN";//仓房性能
    public static final String N_TYPES = "N_TYPES";//扦样类型
    public static final String WORKTYPE = "WORKTYPE1";//作业性质
    public static final String GRAINJCAREA = "GRAINJCAREA";//面积
    public static final String JLZJ = "JLZJ";//状况
    public static final String THINGS = "THINGS";//处理结果
    public static final String JLSTATUS = "JLSTATUS";//结露面积
    public static final String JLVIEW = "JLVIEW";//结露处理意见

    public static final String CFLY = "CFLY";//生霉位置
    public static final String SMCD = "SMCD";//生霉程度
    public static final String XZYN = "XZYN";//是否熏蒸
    public static final String MEDTYPE = "MEDTYPE";//施药方法
    public static final String CLOSETYPE = "CLOSETYPE";//密闭方法
    public static final String MOUSETYPE = "MOUSETYPE";//鼠害种类
    public static final String MOUSEAREA = "MOUSEAREA";//鼠害区域
    public static final String SHJYFZ = "SHJYFZ";//鼠害处理意见
    public static final String SHWLFZ = "SHWLFZ";//治理措施
    public static final String CROPSTYPE = "CROPSTYPE";//粮食品种
    public static final String CONTENT = "CONTENT";//作业内容
    public static final String GRAINSWS = "GRAINSWS";//一般，良好，较差
    public static final String MHQVIEW = "MHQVIEW";//灭火器问题描述
    public static final String THINGSS = "THINGSS";//灭火器处理结果
    public static final String XFSVIEW = "XFSVIEW";//消防栓问题描述
    public static final String PDXVIEW = "PDXVIEW";//配电箱问题描述
    public static final String GBJVIEW = "GBJVIEW";//刮板机问题描述
    public static final String ZLVIEW = "DGFJVIEW";//轴流风机问题描述
    public static final String DTVIEW = "DTVIEW";//电梯问题描述
    public static final String LIGHTVIEW = "LIGHTVIEW";//照明问题描述
    public static final String CVIEW = "CVIEW";//窗户问题描述
    public static final String XCWSVIEW = "XCVIEW";//现场卫生问题描述
    public static final String CNWSVIEW = "XCVIEW";//仓内卫生问题描述
    public static final String TLWSVIEW = "TLWSVIEW";//通廊卫生问题描述
    public static final String DGWSVIEW = "DGWSVIEW";//地坑卫生问题描述
    public static final String CFLYVIEW = "CFLYVIEW";//仓房漏雨问题描述
    public static final String DGFJVIEW = "DGFJVIEW";//仓房漏雨问题描述
    public static final String LXFJVIEW = "LXFJVIEW";//离心风机问题描述

}
