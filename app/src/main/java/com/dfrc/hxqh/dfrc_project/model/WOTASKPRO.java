package com.dfrc.hxqh.dfrc_project.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by think on 2016/7/1.
 * WoTaskPRO的问题点
 */
@DatabaseTable(tableName = "WOTASKPRO")
public class WOTASKPRO extends Entity {
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(columnName = "PERSONID")
    public String PERSONID;//登录人ID
    @DatabaseField(columnName = "WOSEQUENCE")
    public String WOSEQUENCE;//点检项序号
    @DatabaseField(columnName = "N_RESULT")
    public String N_RESULT;//结果
    @DatabaseField(columnName = "N_NOTE")
    public String N_NOTE;//预知项目结果
    @DatabaseField(columnName = "N_MEMBERS")
    public String N_MEMBERS;//实施人
    @DatabaseField(columnName = "WONUM")
    public String WONUM;//定期点检工单号
    @DatabaseField(columnName = "ASSETNUM")
    public String ASSETNUM;//设备编号
    @DatabaseField(columnName = "SITEID")
    public String SITEID;//站点
    @DatabaseField(columnName = "CREWID")
    public String CREWID;//班组
    @DatabaseField(columnName = "RESPONSOR")
    public String RESPONSOR;//问题点担当
    @DatabaseField(columnName = "POSITION")
    public String POSITION;//部位
    @DatabaseField(columnName = "REASON")
    public String REASON;//原因
    @DatabaseField(columnName = "SOLVE")
    public String SOLVE;//解决办法
    @DatabaseField(columnName = "FINISHDATE")
    public String FINISHDATE;//完成日期
    @DatabaseField(columnName = "PRODESC")
    public String PRODESC;//问题点录入

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPERSONID() {
        return PERSONID;
    }

    public void setPERSONID(String PERSONID) {
        this.PERSONID = PERSONID;
    }

    public String getWOSEQUENCE() {
        return WOSEQUENCE;
    }

    public void setWOSEQUENCE(String WOSEQUENCE) {
        this.WOSEQUENCE = WOSEQUENCE;
    }

    public String getN_RESULT() {
        return N_RESULT;
    }

    public void setN_RESULT(String n_RESULT) {
        N_RESULT = n_RESULT;
    }

    public String getN_NOTE() {
        return N_NOTE;
    }

    public void setN_NOTE(String n_NOTE) {
        N_NOTE = n_NOTE;
    }

    public String getN_MEMBERS() {
        return N_MEMBERS;
    }

    public void setN_MEMBERS(String n_MEMBERS) {
        N_MEMBERS = n_MEMBERS;
    }

    public String getWONUM() {
        return WONUM;
    }

    public void setWONUM(String WONUM) {
        this.WONUM = WONUM;
    }

    public String getASSETNUM() {
        return ASSETNUM;
    }

    public void setASSETNUM(String ASSETNUM) {
        this.ASSETNUM = ASSETNUM;
    }

    public String getSITEID() {
        return SITEID;
    }

    public void setSITEID(String SITEID) {
        this.SITEID = SITEID;
    }

    public String getCREWID() {
        return CREWID;
    }

    public void setCREWID(String CREWID) {
        this.CREWID = CREWID;
    }

    public String getRESPONSOR() {
        return RESPONSOR;
    }

    public void setRESPONSOR(String RESPONSOR) {
        this.RESPONSOR = RESPONSOR;
    }

    public String getPOSITION() {
        return POSITION;
    }

    public void setPOSITION(String POSITION) {
        this.POSITION = POSITION;
    }

    public String getREASON() {
        return REASON;
    }

    public void setREASON(String REASON) {
        this.REASON = REASON;
    }

    public String getSOLVE() {
        return SOLVE;
    }

    public void setSOLVE(String SOLVE) {
        this.SOLVE = SOLVE;
    }

    public String getFINISHDATE() {
        return FINISHDATE;
    }

    public void setFINISHDATE(String FINISHDATE) {
        this.FINISHDATE = FINISHDATE;
    }

    public String getPRODESC() {
        return PRODESC;
    }

    public void setPRODESC(String PRODESC) {
        this.PRODESC = PRODESC;
    }
}