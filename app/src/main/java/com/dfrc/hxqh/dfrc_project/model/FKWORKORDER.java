package com.dfrc.hxqh.dfrc_project.model;

/**
 * Created by think on 2016/7/1.
 * 分库领料单
 */
public class FKWORKORDER extends Entity {
    public String DESCRIPTION;//描述
    public String REPORTDATE;//报告日期
    public String REPORTEDBY;//提报人
    public String REPORTEDBYNAME;//提报人名称
    public String SITEID;//地点
    public String STATUS;//状态
    public String STATUSDATE;//状态日期
    public String WONUM;//分库领料单
    public String CREWID;//班组


    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getREPORTDATE() {
        return REPORTDATE;
    }

    public void setREPORTDATE(String REPORTDATE) {
        this.REPORTDATE = REPORTDATE;
    }

    public String getREPORTEDBY() {
        return REPORTEDBY;
    }

    public void setREPORTEDBY(String REPORTEDBY) {
        this.REPORTEDBY = REPORTEDBY;
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

    public String getWONUM() {
        return WONUM;
    }

    public void setWONUM(String WONUM) {
        this.WONUM = WONUM;
    }

    public String getCREWID() {
        return CREWID;
    }

    public void setCREWID(String CREWID) {
        this.CREWID = CREWID;
    }
}