package com.dfrc.hxqh.dfrc_project.model;

/**
 * Created by think on 2016/7/1.
 * ALNDOMAIN
 */
public class ALNDOMAIN extends Entity{
    public String DESCRIPTION;//
    public String DOMAINID;//
    public String VALUE;//

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getDOMAINID() {
        return DOMAINID;
    }

    public void setDOMAINID(String DOMAINID) {
        this.DOMAINID = DOMAINID;
    }

    public String getVALUE() {
        return VALUE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }
}
