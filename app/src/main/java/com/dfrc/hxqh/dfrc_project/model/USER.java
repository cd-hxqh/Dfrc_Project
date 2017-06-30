package com.dfrc.hxqh.dfrc_project.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by think on 2016/7/1.
 * 用户表
 */
@DatabaseTable(tableName = "USER")
public class USER extends Entity {
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(columnName = "USERNAME")
    public String USERNAME;//用户名
    @DatabaseField(columnName = "PASSWORD")
    public String PASSWORD;//密码
    @DatabaseField(columnName = "SITE")
    public String SITE;//站点
    @DatabaseField(columnName = "DISPLAYNAME")
    public String DISPLAYNAME;//别名
    @DatabaseField(columnName = "PERSONID")
    public String PERSONID;//用户ID

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getSITE() {
        return SITE;
    }

    public void setSITE(String SITE) {
        this.SITE = SITE;
    }

    public String getDISPLAYNAME() {
        return DISPLAYNAME;
    }

    public void setDISPLAYNAME(String DISPLAYNAME) {
        this.DISPLAYNAME = DISPLAYNAME;
    }

    public String getPERSONID() {
        return PERSONID;
    }

    public void setPERSONID(String PERSONID) {
        this.PERSONID = PERSONID;
    }
}