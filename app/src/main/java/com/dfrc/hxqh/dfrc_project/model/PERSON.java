package com.dfrc.hxqh.dfrc_project.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by think on 2016/7/1.
 * 设备
 */
@DatabaseTable(tableName = "PERSON")
public class PERSON extends Entity {
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(columnName = "PERSONID")
    public String PERSONID;//PersonId
    @DatabaseField(columnName = "N_CREWID")
    public String N_CREWID;//班组
    @DatabaseField(columnName = "DISPLAYNAME")
    public String DISPLAYNAME;// 名称
    @DatabaseField(columnName = "JOBCODE")
    public String JOBCODE;//职务
    @DatabaseField(columnName = "N_SECTION")
    public String N_SECTION;//车间
    @DatabaseField(columnName = "LOCATIONSITE")
    public String LOCATIONSITE;//站点


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

    public String getN_CREWID() {
        return N_CREWID;
    }

    public void setN_CREWID(String n_CREWID) {
        N_CREWID = n_CREWID;
    }

    public String getDISPLAYNAME() {
        return DISPLAYNAME;
    }

    public void setDISPLAYNAME(String DISPLAYNAME) {
        this.DISPLAYNAME = DISPLAYNAME;
    }

    public String getJOBCODE() {
        return JOBCODE;
    }

    public void setJOBCODE(String JOBCODE) {
        this.JOBCODE = JOBCODE;
    }

    public String getN_SECTION() {
        return N_SECTION;
    }

    public void setN_SECTION(String n_SECTION) {
        N_SECTION = n_SECTION;
    }

    public String getLOCATIONSITE() {
        return LOCATIONSITE;
    }

    public void setLOCATIONSITE(String LOCATIONSITE) {
        this.LOCATIONSITE = LOCATIONSITE;
    }
}
