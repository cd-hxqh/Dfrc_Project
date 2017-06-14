package com.dfrc.hxqh.dfrc_project.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by think on 2016/7/1.
 * 点检明细行
 */
@DatabaseTable(tableName = "WOTASK")
public class WOTASK extends Entity {
    @DatabaseField(generatedId = true)
    public int id;


    @DatabaseField(columnName = "WOTASKID")
    public String WOTASKID;//任务ID
    @DatabaseField(columnName = "ASSETNUM")
    public String ASSETNUM;//设备编号
    @DatabaseField(columnName = "ASSET_DESCRIPTION")
    public String ASSET_DESCRIPTION;//设备名称
    @DatabaseField(columnName = "ITEM")
    public String ITEM;//项目
    @DatabaseField(columnName = "N_MEMBERS")
    public String N_MEMBERS;//实施人员
    @DatabaseField(columnName = "N_NOTE")
    public String N_NOTE;//预知项目结果
    @DatabaseField(columnName = "N_RESPONSOR")
    public String N_RESPONSOR;//实施责任人
    @DatabaseField(columnName = "N_RESULT")
    public String N_RESULT;//结果
    @DatabaseField(columnName = "POSITION")
    public String POSITION;//部位
    @DatabaseField(columnName = "REFBOOKLINE_ABC")
    public String REFBOOKLINE_ABC;//重要度
    @DatabaseField(columnName = "REFBOOKLINE_CYCLE")
    public String REFBOOKLINE_CYCLE;//周期(月)
    @DatabaseField(columnName = "REFBOOKLINE_HOURS")
    public String REFBOOKLINE_HOURS;//点检工时
    @DatabaseField(columnName = "REFBOOKLINE_METHOD")
    public String REFBOOKLINE_METHOD;//点检方法
    @DatabaseField(columnName = "REFBOOKLINE_TYPE")
    public String REFBOOKLINE_TYPE;//类别
    @DatabaseField(columnName = "RESPONSOR_DISPLAYNAME")
    public String RESPONSOR_DISPLAYNAME;//
    @DatabaseField(columnName = "WOSEQUENCE")
    public String WOSEQUENCE;//序号
    @DatabaseField(columnName = "WPLINE_RULE")
    public String WPLINE_RULE;//基准
    @DatabaseField(columnName = "WPLINE_SOLVE")
    public String WPLINE_SOLVE;//异常处理
    @DatabaseField(columnName = "WONUM")
    public String WONUM;//工单编号
    @DatabaseField(columnName = "UPDATE")
    public int UPDATE;//是否修改 0表示未修改,1表示已修改


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWOTASKID() {
        return WOTASKID;
    }

    public void setWOTASKID(String WOTASKID) {
        this.WOTASKID = WOTASKID;
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

    public String getITEM() {
        return ITEM;
    }

    public void setITEM(String ITEM) {
        this.ITEM = ITEM;
    }

    public String getN_MEMBERS() {
        return N_MEMBERS;
    }

    public void setN_MEMBERS(String n_MEMBERS) {
        N_MEMBERS = n_MEMBERS;
    }

    public String getN_NOTE() {
        return N_NOTE;
    }

    public void setN_NOTE(String n_NOTE) {
        N_NOTE = n_NOTE;
    }

    public String getN_RESPONSOR() {
        return N_RESPONSOR;
    }

    public void setN_RESPONSOR(String n_RESPONSOR) {
        N_RESPONSOR = n_RESPONSOR;
    }

    public String getN_RESULT() {
        return N_RESULT;
    }

    public void setN_RESULT(String n_RESULT) {
        N_RESULT = n_RESULT;
    }

    public String getPOSITION() {
        return POSITION;
    }

    public void setPOSITION(String POSITION) {
        this.POSITION = POSITION;
    }

    public String getREFBOOKLINE_ABC() {
        return REFBOOKLINE_ABC;
    }

    public void setREFBOOKLINE_ABC(String REFBOOKLINE_ABC) {
        this.REFBOOKLINE_ABC = REFBOOKLINE_ABC;
    }

    public String getREFBOOKLINE_CYCLE() {
        return REFBOOKLINE_CYCLE;
    }

    public void setREFBOOKLINE_CYCLE(String REFBOOKLINE_CYCLE) {
        this.REFBOOKLINE_CYCLE = REFBOOKLINE_CYCLE;
    }

    public String getREFBOOKLINE_HOURS() {
        return REFBOOKLINE_HOURS;
    }

    public void setREFBOOKLINE_HOURS(String REFBOOKLINE_HOURS) {
        this.REFBOOKLINE_HOURS = REFBOOKLINE_HOURS;
    }

    public String getREFBOOKLINE_METHOD() {
        return REFBOOKLINE_METHOD;
    }

    public void setREFBOOKLINE_METHOD(String REFBOOKLINE_METHOD) {
        this.REFBOOKLINE_METHOD = REFBOOKLINE_METHOD;
    }

    public String getREFBOOKLINE_TYPE() {
        return REFBOOKLINE_TYPE;
    }

    public void setREFBOOKLINE_TYPE(String REFBOOKLINE_TYPE) {
        this.REFBOOKLINE_TYPE = REFBOOKLINE_TYPE;
    }

    public String getRESPONSOR_DISPLAYNAME() {
        return RESPONSOR_DISPLAYNAME;
    }

    public void setRESPONSOR_DISPLAYNAME(String RESPONSOR_DISPLAYNAME) {
        this.RESPONSOR_DISPLAYNAME = RESPONSOR_DISPLAYNAME;
    }

    public String getWOSEQUENCE() {
        return WOSEQUENCE;
    }

    public void setWOSEQUENCE(String WOSEQUENCE) {
        this.WOSEQUENCE = WOSEQUENCE;
    }

    public String getWPLINE_RULE() {
        return WPLINE_RULE;
    }

    public void setWPLINE_RULE(String WPLINE_RULE) {
        this.WPLINE_RULE = WPLINE_RULE;
    }

    public String getWPLINE_SOLVE() {
        return WPLINE_SOLVE;
    }

    public void setWPLINE_SOLVE(String WPLINE_SOLVE) {
        this.WPLINE_SOLVE = WPLINE_SOLVE;
    }

    public String getWONUM() {
        return WONUM;
    }

    public void setWONUM(String WONUM) {
        this.WONUM = WONUM;
    }

    public int getUPDATE() {
        return UPDATE;
    }

    public void setUPDATE(int UPDATE) {
        this.UPDATE = UPDATE;
    }
}