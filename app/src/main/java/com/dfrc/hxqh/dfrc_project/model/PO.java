package com.dfrc.hxqh.dfrc_project.model;

/**
 * Created by think on 2016/7/1.
 * 采购接收
 */
public class PO extends Entity {
    public String DESCRIPTION;//描述
    public String ORDERDATE;//订购日期
    public String PONUM;//采购订单号
    public String RECEIPTS;//接收
    public String SHIPTOATTN;//接收人
    public String STATUS;//状态
    public String SHIPTOPERSON_DISPLAYNAME;//接收人名称
    public String SITEID;//地点

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getORDERDATE() {
        return ORDERDATE;
    }

    public void setORDERDATE(String ORDERDATE) {
        this.ORDERDATE = ORDERDATE;
    }

    public String getPONUM() {
        return PONUM;
    }

    public void setPONUM(String PONUM) {
        this.PONUM = PONUM;
    }

    public String getRECEIPTS() {
        return RECEIPTS;
    }

    public void setRECEIPTS(String RECEIPTS) {
        this.RECEIPTS = RECEIPTS;
    }

    public String getSHIPTOATTN() {
        return SHIPTOATTN;
    }

    public void setSHIPTOATTN(String SHIPTOATTN) {
        this.SHIPTOATTN = SHIPTOATTN;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getSHIPTOPERSON_DISPLAYNAME() {
        return SHIPTOPERSON_DISPLAYNAME;
    }

    public void setSHIPTOPERSON_DISPLAYNAME(String SHIPTOPERSON_DISPLAYNAME) {
        this.SHIPTOPERSON_DISPLAYNAME = SHIPTOPERSON_DISPLAYNAME;
    }

    public String getSITEID() {
        return SITEID;
    }

    public void setSITEID(String SITEID) {
        this.SITEID = SITEID;
    }
}