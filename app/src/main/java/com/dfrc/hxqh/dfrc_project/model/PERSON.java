package com.dfrc.hxqh.dfrc_project.model;

/**
 * Created by think on 2016/7/1.
 * 设备
 */
public class PERSON extends Entity {
    public String PERSONID;//
    public String N_CREWID;//
    public String DISPLAYNAME;//

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
}
