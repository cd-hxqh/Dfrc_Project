package com.dfrc.hxqh.dfrc_project.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by think on 2016/7/1.
 * WoTaskOK的结果集
 */
@DatabaseTable(tableName = "WOTASKOK")
public class WOTASKOK extends Entity {
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(columnName = "WOSEQUENCE")
    public String WOSEQUENCE;//序号
    @DatabaseField(columnName = "N_RESULT")
    public String N_RESULT;//结果
    @DatabaseField(columnName = "N_NOTE")
    public String N_NOTE;//预知项目结果
    @DatabaseField(columnName = "N_MEMBERS")
    public String N_MEMBERS;//实施人员
    @DatabaseField(columnName = "WONUM")
    public String WONUM;//编号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}