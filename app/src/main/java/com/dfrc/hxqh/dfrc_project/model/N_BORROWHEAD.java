package com.dfrc.hxqh.dfrc_project.model;

/**
 * Created by think on 2016/7/1.
 * 备件借用
 */
public class N_BORROWHEAD extends Entity {
    public String CREATEDBY;//申请人
    public String CREATEDDATE;//申请时间
    public String CREWID;//班组
    public String DESCRIPTION;//描述
    public String LOCATION;//位置
    public String N_BORROWHEADNUM;//借用单号
    public String N_LOCATION;//借出仓库代码
    public String N_STIEID;//借出地点
    public String N_WERKS;//借出工厂代
    public String SITEID;//地点
    public String STATUS;//状态
    public String WERKS;//领用工厂代码

    public String getCREATEDBY() {
        return CREATEDBY;
    }

    public void setCREATEDBY(String CREATEDBY) {
        this.CREATEDBY = CREATEDBY;
    }

    public String getCREATEDDATE() {
        return CREATEDDATE;
    }

    public void setCREATEDDATE(String CREATEDDATE) {
        this.CREATEDDATE = CREATEDDATE;
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

    public String getN_BORROWHEADNUM() {
        return N_BORROWHEADNUM;
    }

    public void setN_BORROWHEADNUM(String n_BORROWHEADNUM) {
        N_BORROWHEADNUM = n_BORROWHEADNUM;
    }

    public String getN_LOCATION() {
        return N_LOCATION;
    }

    public void setN_LOCATION(String n_LOCATION) {
        N_LOCATION = n_LOCATION;
    }

    public String getN_STIEID() {
        return N_STIEID;
    }

    public void setN_STIEID(String n_STIEID) {
        N_STIEID = n_STIEID;
    }

    public String getN_WERKS() {
        return N_WERKS;
    }

    public void setN_WERKS(String n_WERKS) {
        N_WERKS = n_WERKS;
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

    public String getWERKS() {
        return WERKS;
    }

    public void setWERKS(String WERKS) {
        this.WERKS = WERKS;
    }
}