package com.dfrc.hxqh.dfrc_project.model;

/**
 * Created by think on 2016/7/1.
 * 总库领料单
 */
public class ZKWORKORDER extends Entity {
    public String ASSETNUM;//设备编号
    public String ASSET_DESCRIPTION;//设备描述
    public String CREWID;//申请班组
    public String DESCRIPTION;//描述
    public String LOCATION;//位置
    public String LOCATIONS_DESCRIPTION;//位置描述
    public String N_SAP1;//发货仓库
    public String N_SITE;//工仓代码
    public String REPORTDATE;//报告日期
    public String REPORTEDBYID;//提报人
    public String REPORTEDBYNAME;//提报人名称
    public String SITEID;//地点
    public String STATUS;//状态
    public String STATUSDATE;//状态日期
    public String TOTALCOST;//总库领料单合计
    public String WONUM;//领料单号
    public String SUMWOBUDGET;//领用年预算
    public String WOBUDGET;//领用月预算
    public String EXPWO;//本月领用支出(实际)
    public String ACTYEAREXPWO;//当年领用支出(实际)
    public String OUTTYPE;//总库移动类型
    public String N_KOSTL;//成本中心


    public String getASSETNUM() {
        return ASSETNUM;
    }

    public void setASSETNUM(String ASSETNUM) {
        this.ASSETNUM = ASSETNUM;
    }

    public String getASSET_DESCRIPTION() {
        return ASSET_DESCRIPTION;
    }

    public void setASSET_DESCRIPTION(String ASSET_DESCRIPTION) {
        this.ASSET_DESCRIPTION = ASSET_DESCRIPTION;
    }

    public String getCREWID() {
        return CREWID;
    }

    public void setCREWID(String CREWID) {
        this.CREWID = CREWID;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getLOCATIONS_DESCRIPTION() {
        return LOCATIONS_DESCRIPTION;
    }

    public void setLOCATIONS_DESCRIPTION(String LOCATIONS_DESCRIPTION) {
        this.LOCATIONS_DESCRIPTION = LOCATIONS_DESCRIPTION;
    }

    public String getN_SAP1() {
        return N_SAP1;
    }

    public void setN_SAP1(String n_SAP1) {
        N_SAP1 = n_SAP1;
    }

    public String getN_SITE() {
        return N_SITE;
    }

    public void setN_SITE(String n_SITE) {
        N_SITE = n_SITE;
    }

    public String getREPORTDATE() {
        return REPORTDATE;
    }

    public void setREPORTDATE(String REPORTDATE) {
        this.REPORTDATE = REPORTDATE;
    }

    public String getREPORTEDBYID() {
        return REPORTEDBYID;
    }

    public void setREPORTEDBYID(String REPORTEDBYID) {
        this.REPORTEDBYID = REPORTEDBYID;
    }

    public String getREPORTEDBYNAME() {
        return REPORTEDBYNAME;
    }

    public void setREPORTEDBYNAME(String REPORTEDBYNAME) {
        this.REPORTEDBYNAME = REPORTEDBYNAME;
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

    public String getSTATUSDATE() {
        return STATUSDATE;
    }

    public void setSTATUSDATE(String STATUSDATE) {
        this.STATUSDATE = STATUSDATE;
    }

    public String getTOTALCOST() {
        return TOTALCOST;
    }

    public void setTOTALCOST(String TOTALCOST) {
        this.TOTALCOST = TOTALCOST;
    }

    public String getWONUM() {
        return WONUM;
    }

    public void setWONUM(String WONUM) {
        this.WONUM = WONUM;
    }

    public String getWOBUDGET() {
        return WOBUDGET;
    }

    public void setWOBUDGET(String WOBUDGET) {
        this.WOBUDGET = WOBUDGET;
    }

    public String getEXPWO() {
        return EXPWO;
    }

    public void setEXPWO(String EXPWO) {
        this.EXPWO = EXPWO;
    }

    public String getACTYEAREXPWO() {
        return ACTYEAREXPWO;
    }

    public void setACTYEAREXPWO(String ACTYEAREXPWO) {
        this.ACTYEAREXPWO = ACTYEAREXPWO;
    }

    public String getSUMWOBUDGET() {
        return SUMWOBUDGET;
    }

    public void setSUMWOBUDGET(String SUMWOBUDGET) {
        this.SUMWOBUDGET = SUMWOBUDGET;
    }

    public String getOUTTYPE() {
        return OUTTYPE;
    }

    public void setOUTTYPE(String OUTTYPE) {
        this.OUTTYPE = OUTTYPE;
    }

    public String getN_KOSTL() {
        return N_KOSTL;
    }

    public void setN_KOSTL(String n_KOSTL) {
        N_KOSTL = n_KOSTL;
    }
}