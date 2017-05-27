package com.dfrc.hxqh.dfrc_project.model;

/**
 * Created by think on 2016/7/1.
 * 定期点检工单
 */
public class WORKORDER extends Entity {
    public String CHANGEBY;//变更人
    public String CREWID;//班组
    public String DESCRIPTION;//描述
    public String MONTH;//月度
    public String N_WPNUM;//周计划编号
    public String STATUS;//状态
    public String WONUM;//编号
    public String YEAR;//年度
    public String N_QTYOPEN;//未完成
    public String N_QTYCOMP;//已完成
    public String SITEID;//地点

    public String getSITEID() {
        return SITEID;
    }

    public void setSITEID(String SITEID) {
        this.SITEID = SITEID;
    }

    public String getCHANGEBY() {
        return CHANGEBY;
    }

    public void setCHANGEBY(String CHANGEBY) {
        this.CHANGEBY = CHANGEBY;
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

    public String getMONTH() {
        return MONTH;
    }

    public void setMONTH(String MONTH) {
        this.MONTH = MONTH;
    }

    public String getN_WPNUM() {
        return N_WPNUM;
    }

    public void setN_WPNUM(String n_WPNUM) {
        N_WPNUM = n_WPNUM;
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

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
    }

    public String getN_QTYOPEN() {
        return N_QTYOPEN;
    }

    public void setN_QTYOPEN(String n_QTYOPEN) {
        N_QTYOPEN = n_QTYOPEN;
    }

    public String getN_QTYCOMP() {
        return N_QTYCOMP;
    }

    public void setN_QTYCOMP(String n_QTYCOMP) {
        N_QTYCOMP = n_QTYCOMP;
    }
}