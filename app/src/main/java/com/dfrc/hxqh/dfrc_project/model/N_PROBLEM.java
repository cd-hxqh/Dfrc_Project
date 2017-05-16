package com.dfrc.hxqh.dfrc_project.model;

/**
 * Created by think on 2016/7/1.
 * 问题点管理
 */
public class N_PROBLEM extends Entity {
    public String N_PROBLEMID;//主键ID
    public String ABC;//重要度
    public String ASSETNUM;//设备编号
    public String ASSET_DESCRIPTION;//设备名称
    public String CONFIRMBY;//确认人
    public String CONFIRMBY_DISPLAYNAME;//确认人名称
    public String CREWID;//班组
    public String FINDDATE;//发现日期
    public String FINISHDATE;//完成日期
    public String N_PROBLEMNUM;//问题点编号
    public String PL;//生产线
    public String POSITION;//部位
    public String PRODESC;//问题点描述
    public String REASON;//原因
    public String RESPONSOR;//担当
    public String DISPLAYNAME;//担当名称
    public String RESULT;//整改结果
    public String SOLVE;//对策
    public String STATUS;//进展
    public String WONUM;//工单
    public String WORKORDER_DESCRIPTION;//工单描述

    public String getN_PROBLEMID() {
        return N_PROBLEMID;
    }

    public void setN_PROBLEMID(String n_PROBLEMID) {
        N_PROBLEMID = n_PROBLEMID;
    }

    public String getABC() {
        return ABC;
    }

    public void setABC(String ABC) {
        this.ABC = ABC;
    }

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

    public String getCONFIRMBY() {
        return CONFIRMBY;
    }

    public void setCONFIRMBY(String CONFIRMBY) {
        this.CONFIRMBY = CONFIRMBY;
    }

    public String getCONFIRMBY_DISPLAYNAME() {
        return CONFIRMBY_DISPLAYNAME;
    }

    public void setCONFIRMBY_DISPLAYNAME(String CONFIRMBY_DISPLAYNAME) {
        this.CONFIRMBY_DISPLAYNAME = CONFIRMBY_DISPLAYNAME;
    }

    public String getCREWID() {
        return CREWID;
    }

    public void setCREWID(String CREWID) {
        this.CREWID = CREWID;
    }

    public String getFINDDATE() {
        return FINDDATE;
    }

    public void setFINDDATE(String FINDDATE) {
        this.FINDDATE = FINDDATE;
    }

    public String getFINISHDATE() {
        return FINISHDATE;
    }

    public void setFINISHDATE(String FINISHDATE) {
        this.FINISHDATE = FINISHDATE;
    }

    public String getN_PROBLEMNUM() {
        return N_PROBLEMNUM;
    }

    public void setN_PROBLEMNUM(String n_PROBLEMNUM) {
        N_PROBLEMNUM = n_PROBLEMNUM;
    }

    public String getPL() {
        return PL;
    }

    public void setPL(String PL) {
        this.PL = PL;
    }

    public String getPOSITION() {
        return POSITION;
    }

    public void setPOSITION(String POSITION) {
        this.POSITION = POSITION;
    }

    public String getPRODESC() {
        return PRODESC;
    }

    public void setPRODESC(String PRODESC) {
        this.PRODESC = PRODESC;
    }

    public String getREASON() {
        return REASON;
    }

    public void setREASON(String REASON) {
        this.REASON = REASON;
    }

    public String getRESPONSOR() {
        return RESPONSOR;
    }

    public void setRESPONSOR(String RESPONSOR) {
        this.RESPONSOR = RESPONSOR;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public String getSOLVE() {
        return SOLVE;
    }

    public void setSOLVE(String SOLVE) {
        this.SOLVE = SOLVE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getWONUM() {
        return WONUM;
    }

    public void setWONUM(String WONUM) {
        this.WONUM = WONUM;
    }

    public String getWORKORDER_DESCRIPTION() {
        return WORKORDER_DESCRIPTION;
    }

    public void setWORKORDER_DESCRIPTION(String WORKORDER_DESCRIPTION) {
        this.WORKORDER_DESCRIPTION = WORKORDER_DESCRIPTION;
    }

    public String getDISPLAYNAME() {
        return DISPLAYNAME;
    }

    public void setDISPLAYNAME(String DISPLAYNAME) {
        this.DISPLAYNAME = DISPLAYNAME;
    }
}