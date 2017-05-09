package com.dfrc.hxqh.dfrc_project.model;

/**
 * Created by think on 2016/7/1.
 * 分库领用明细
 */
public class MATUSETRANS extends Entity{
    public String BINNUM;//货柜
    public String CURBAL;//库存余量
    public String DESCRIPTION;//物料名称
    public String DISPLAYNAME;//领用人名称
    public String ENTERBY;//领用人
    public String ISSUETYPE;//交易类型
    public String ITEMNUM;//物料编码
    public String N_USEAREA;//使用区域
    public String POSITIVEQUANTITY;//数量
    public String STORELOC;//库房

    public String getBINNUM() {
        return BINNUM;
    }

    public void setBINNUM(String BINNUM) {
        this.BINNUM = BINNUM;
    }

    public String getCURBAL() {
        return CURBAL;
    }

    public void setCURBAL(String CURBAL) {
        this.CURBAL = CURBAL;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getDISPLAYNAME() {
        return DISPLAYNAME;
    }

    public void setDISPLAYNAME(String DISPLAYNAME) {
        this.DISPLAYNAME = DISPLAYNAME;
    }

    public String getENTERBY() {
        return ENTERBY;
    }

    public void setENTERBY(String ENTERBY) {
        this.ENTERBY = ENTERBY;
    }

    public String getISSUETYPE() {
        return ISSUETYPE;
    }

    public void setISSUETYPE(String ISSUETYPE) {
        this.ISSUETYPE = ISSUETYPE;
    }

    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }

    public String getN_USEAREA() {
        return N_USEAREA;
    }

    public void setN_USEAREA(String n_USEAREA) {
        N_USEAREA = n_USEAREA;
    }

    public String getPOSITIVEQUANTITY() {
        return POSITIVEQUANTITY;
    }

    public void setPOSITIVEQUANTITY(String POSITIVEQUANTITY) {
        this.POSITIVEQUANTITY = POSITIVEQUANTITY;
    }

    public String getSTORELOC() {
        return STORELOC;
    }

    public void setSTORELOC(String STORELOC) {
        this.STORELOC = STORELOC;
    }

}
