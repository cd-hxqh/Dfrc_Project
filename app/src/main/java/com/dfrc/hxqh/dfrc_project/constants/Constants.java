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
    public static final String HTTP_API_IP = "http://172.25.185.32:7001";

    /**正式**/

    public static final String HTTPZS_API_IP = "http://172.28.16.96";

    //神经病地址
    public static final String HTTPCES_API_IP = "http://172.28.16.58:9083";


    //登陆URL
    public static final String SIGN_IN_URL = "/maximo/mobile/system/login";


    //全都是一个接口
    public static final String LoginwebserviceURL = "/meaweb/services/DFLSERVICE";

    //全都是一个接口
    public static final String MOBILESERVICEURL = "/meaweb/services/MOBILESERVICE";


    //通用接口查询
    public static final String BASE_URL = "/maximo/mobile/common/api";


    //生成物资编码接口
    public static final String ITEM_GENERATE_URL = "/maximo/mobile/itemreq/createCode";
    //发送工作流接口

    public static final String START_FLOW_URL = "/maximo/mobile/wf/start";
    //审批工作流接口
    public static final String APPROVAL_FLOW_URL = "/maximo/mobile/wf/approve";


    /**
     * 图片*
     */
    public static final String WORK_FLOW_URL = "/meaweb/services/WFSERVICE";

    /**
     * ------------------数据库表名配置－－开始*
     */

    //设备appid
    public static final String ASSET_APPID = "ASSET";

    //设备的表名
    public static final String ASSET_NAME = "ASSET";
    //备件的表名
    public static final String SPAREPART_NAME = "SPAREPART";

    //库存appid
    public static final String INVENTOR_APPID = "INVENTOR";

    //库存的表名
    public static final String INVENTORY_NAME = "INVENTORY";

    //库存余量的表名
    public static final String INVBALANCES_NAME = "INVBALANCES";

    //位置appid
    public static final String LOCATION_APPID = "LOCATION";

    //位置的表名
    public static final String LOCATIONS_NAME = "LOCATIONS";

    //问题点管理appid
    public static final String N_PROB2_APPID = "N_PROB2";

    //问题点管理的表名
    public static final String N_PROBLEM_NAME = "N_PROBLEM";

    //定期点检工单appid
    public static final String N_MATWO_APPID = "N_MATWO";

    //定期点检工单的表名
    public static final String WORKORDER_NAME = "WORKORDER";
    //定期点检工单明细行的表名
    public static final String WOTASK_NAME = "WOTASK";

    //采购接收appid
    public static final String RECEIPTS_APPID = "N_PO";

    //采购接收的表名
    public static final String PO_NAME = "PO";
    //采购单行的表名
    public static final String POLINE_NAME = "POLINE";
    //物料接收的表名
    public static final String MATRECTRANS_NAME = "MATRECTRANS";
    //物料退回的表名
    public static final String UDCANRTN_NAME = "UDCANRTN";

    //人员appid
    public static final String PERSON_APPID = "PERSON";

    //人员的表名
    public static final String PERSON_NAME = "PERSON";

    //总库领料单appid
    public static final String N_WORKOR2 = "N_WORKOR2";
    //总库领料单明细行的表名
    public static final String N_MATERIAL = "N_MATERIAL";

    //分库领料单appid
    public static final String N_WORKORDE = "N_WORKORDE";
    //分库库领料单明细行的表名
    public static final String MATUSETRANS = "MATUSETRANS";
    //备件借用appid
    public static final String N_BORROW_APPID = "N_BORROW";
    //备件借用的表名
    public static final String N_BORROWHEAD_NAME = "N_BORROWHEAD";

    //ALNDOMAINappid
    public static final String ALNDOMAIN_APPID = "ALNDOMAIN";
    //ALNDOMAIN的表名
    public static final String ALNDOMAIN_NAME = "ALNDOMAIN";

    /**
     * 附件appid
     **/
    public static final String DOCLINKS_APPID = "N_PROB2";
    /**
     * 附件表名
     **/
    public static final String DOCLINKS_NAME = "DOCLINKS";


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


    //进展
    public static final String WTDSTATUS = "WTDSTATUS";//进展
    public static final String ABCTYPE = "N_ABC";//重要度
    public static final String N_PRORESULT = "N_PRORESULT";//整改结果


    /**设置数据库参数-开始**/
    /**
     * 数据库路径
     */
    public static final String PATH_DB = android.os.Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + "/Android/data/";
    /**
     * 无SD卡的数据库路径
     */
    public static final String NOT_SDCARD_PATH_DB = "/data/data/";
    /**
     * 数据库名称 *
     */
    public static final String TB_NAME = "sqlite-dfrc.db";

    /**
     * 数据库版本
     **/
    public static final int DATABASE_VERSION = 20;


}
