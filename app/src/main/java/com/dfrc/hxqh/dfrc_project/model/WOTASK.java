package com.dfrc.hxqh.dfrc_project.model;

/**
 * Created by think on 2016/7/1.
 * 点检明细行
 */
public class WOTASK extends Entity {
    public String ASSETNUM;//设备编号
    public String ASSET_DESCRIPTION;//设备名称
    public String ITEM;//项目
    public String N_MEMBERS;//实施人员
    public String N_NOTE;//预知项目结果
    public String N_RESPONSOR;//实施责任人
    public String N_RESULT;//结果
    public String POSITION;//部位
    public String REFBOOKLINE_ABC;//重要度
    public String REFBOOKLINE_CYCLE;//周期(月)
    public String REFBOOKLINE_HOURS;//点检工时
    public String REFBOOKLINE_METHOD;//点检方法
    public String REFBOOKLINE_TYPE;//类别
    public String RESPONSOR_DISPLAYNAME;//
    public String WOSEQUENCE;//序号
    public String WPLINE_RULE;//基准
    public String WPLINE_SOLVE;//异常处理

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
}