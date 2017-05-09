package com.dfrc.hxqh.dfrc_project.model;

/**
 * Created by think on 2016/7/1.
 * 分库领用明细
 */
public class MATRECTRANS extends Entity{
    public String DESCRIPTION;//物料名称
    public String ISSUETYPE;//交易类型
    public String ITEMNUM;//物料编码
    public String ACTUALDATE;//实际日期
    public String POLINENUM;//PO行
    public String RECEIPTQUANTITY;//数量
    public String STATUS;//状态
    public String ENTERBY;//输入人
    public String PONUM;//采购单号
    public String BINNUM;//货柜


    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
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

    public String getACTUALDATE() {
        return ACTUALDATE;
    }

    public void setACTUALDATE(String ACTUALDATE) {
        this.ACTUALDATE = ACTUALDATE;
    }

    public String getPOLINENUM() {
        return POLINENUM;
    }

    public void setPOLINENUM(String POLINENUM) {
        this.POLINENUM = POLINENUM;
    }

    public String getRECEIPTQUANTITY() {
        return RECEIPTQUANTITY;
    }

    public void setRECEIPTQUANTITY(String RECEIPTQUANTITY) {
        this.RECEIPTQUANTITY = RECEIPTQUANTITY;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getENTERBY() {
        return ENTERBY;
    }

    public void setENTERBY(String ENTERBY) {
        this.ENTERBY = ENTERBY;
    }

    public String getPONUM() {
        return PONUM;
    }

    public void setPONUM(String PONUM) {
        this.PONUM = PONUM;
    }

    public String getBINNUM() {
        return BINNUM;
    }

    public void setBINNUM(String BINNUM) {
        this.BINNUM = BINNUM;
    }
}
