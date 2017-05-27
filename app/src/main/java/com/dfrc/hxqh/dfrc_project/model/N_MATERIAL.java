package com.dfrc.hxqh.dfrc_project.model;

/**
 * Created by think on 2016/7/1.
 * 申请领用物料明细
 */
public class N_MATERIAL extends Entity {
    public String N_MATERIALID;//ID
    public String DESCRIPTION;//描述
    public String DESCRIPTION1;//存放位置
    public String ITEMNUM;//物料编码
    public String LINECOST;//行成本
    public String N_REASON;//领用原因
    public String N_SAP3;//实际数量
    public String UNITCOST;//单价
    public String CURBAL;//当前数量
    public String WONUM;//工单号
    public String SITEID;//站点
    public String STATUS;//状态
    public String N_SAP1;//发货仓库
    public String N_SAP5;//申请数量
    public String ITEM_DESCRIPTION;//描述
    public String TOBIN;//分库存放货柜
    public String FROMBIN;//总库存放货柜


    public String getCURBAL() {
        return CURBAL;
    }

    public void setCURBAL(String CURBAL) {
        this.CURBAL = CURBAL;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getDESCRIPTION1() {
        return DESCRIPTION1;
    }

    public void setDESCRIPTION1(String DESCRIPTION1) {
        this.DESCRIPTION1 = DESCRIPTION1;
    }

    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }

    public String getLINECOST() {
        return LINECOST;
    }

    public void setLINECOST(String LINECOST) {
        this.LINECOST = LINECOST;
    }

    public String getN_REASON() {
        return N_REASON;
    }

    public void setN_REASON(String n_REASON) {
        N_REASON = n_REASON;
    }

    public String getN_SAP3() {
        return N_SAP3;
    }

    public void setN_SAP3(String n_SAP3) {
        N_SAP3 = n_SAP3;
    }

    public String getUNITCOST() {
        return UNITCOST;
    }

    public void setUNITCOST(String UNITCOST) {
        this.UNITCOST = UNITCOST;
    }

    public String getWONUM() {
        return WONUM;
    }

    public void setWONUM(String WONUM) {
        this.WONUM = WONUM;
    }

    public String getSITEID() {
        return SITEID;
    }

    public void setSITEID(String SITEID) {
        this.SITEID = SITEID;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getN_SAP1() {
        return N_SAP1;
    }

    public void setN_SAP1(String n_SAP1) {
        N_SAP1 = n_SAP1;
    }

    public String getN_SAP5() {
        return N_SAP5;
    }

    public void setN_SAP5(String n_SAP5) {
        N_SAP5 = n_SAP5;
    }

    public String getITEM_DESCRIPTION() {
        return ITEM_DESCRIPTION;
    }

    public void setITEM_DESCRIPTION(String ITEM_DESCRIPTION) {
        this.ITEM_DESCRIPTION = ITEM_DESCRIPTION;
    }

    public String getTOBIN() {
        return TOBIN;
    }

    public void setTOBIN(String TOBIN) {
        this.TOBIN = TOBIN;
    }

    public String getFROMBIN() {
        return FROMBIN;
    }

    public void setFROMBIN(String FROMBIN) {
        this.FROMBIN = FROMBIN;
    }

    public String getN_MATERIALID() {
        return N_MATERIALID;
    }

    public void setN_MATERIALID(String n_MATERIALID) {
        N_MATERIALID = n_MATERIALID;
    }
}
