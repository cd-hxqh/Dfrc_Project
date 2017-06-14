package com.dfrc.hxqh.dfrc_project.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by think on 2016/7/1.
 * 定期点检工单
 */
@DatabaseTable(tableName = "WORKORDER")
public class WORKORDER extends Entity {
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(columnName = "CHANGEBY")
    public String CHANGEBY;//变更人
    @DatabaseField(columnName = "CREWID")
    public String CREWID;//班组
    @DatabaseField(columnName = "DESCRIPTION")
    public String DESCRIPTION;//描述
    @DatabaseField(columnName = "MONTH")
    public String MONTH;//月度
    @DatabaseField(columnName = "N_WPNUM")
    public String N_WPNUM;//周计划编号
    @DatabaseField(columnName = "STATUS")
    public String STATUS;//状态
    @DatabaseField(columnName = "WONUM")
    public String WONUM;//编号
    @DatabaseField(columnName = "YEAR")
    public String YEAR;//年度
    @DatabaseField(columnName = "N_QTYOPEN")
    public String N_QTYOPEN;//未完成
    @DatabaseField(columnName = "N_QTYCOMP")
    public String N_QTYCOMP;//已完成
    @DatabaseField(columnName = "SITEID")
    public String SITEID;//地点
    @DatabaseField(columnName = "WORKTYPE")
    public String WORKTYPE;//类型
    @DatabaseField(columnName = "WKTYPE")
    public String WKTYPE;//类型
    @DatabaseField(columnName = "DOWNSTATUS")
    public String DOWNSTATUS;//下载状态

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getWORKTYPE() {
        return WORKTYPE;
    }

    public void setWORKTYPE(String WORKTYPE) {
        this.WORKTYPE = WORKTYPE;
    }

    public String getWKTYPE() {
        return WKTYPE;
    }

    public void setWKTYPE(String WKTYPE) {
        this.WKTYPE = WKTYPE;
    }

    public String getDOWNSTATUS() {
        return DOWNSTATUS;
    }

    public void setDOWNSTATUS(String DOWNSTATUS) {
        this.DOWNSTATUS = DOWNSTATUS;
    }
}