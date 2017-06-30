package com.dfrc.hxqh.dfrc_project.model;

/**
 * Created by think on 2016/7/1.
 * 采购订单退库
 */
public class UDCANRTN extends Entity {
    public String ITEMNUM;//项目
    public String DESCRIPTION;//描述
    public String POLINENUM;//采购单行
    public String PONUM;//采购单号
    public String QUANTITY;//数量
    public String RECEIVEDUNIT;//订购单位
    public String TOBIN;//货柜
    public String TOSTORELOC;//目标库房
    public String ENTERBY;//输入人


    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }

    public String getPOLINENUM() {
        return POLINENUM;
    }

    public void setPOLINENUM(String POLINENUM) {
        this.POLINENUM = POLINENUM;
    }

    public String getPONUM() {
        return PONUM;
    }

    public void setPONUM(String PONUM) {
        this.PONUM = PONUM;
    }

    public String getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(String QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public String getRECEIVEDUNIT() {
        return RECEIVEDUNIT;
    }

    public void setRECEIVEDUNIT(String RECEIVEDUNIT) {
        this.RECEIVEDUNIT = RECEIVEDUNIT;
    }

    public String getTOBIN() {
        return TOBIN;
    }

    public void setTOBIN(String TOBIN) {
        this.TOBIN = TOBIN;
    }

    public String getTOSTORELOC() {
        return TOSTORELOC;
    }

    public void setTOSTORELOC(String TOSTORELOC) {
        this.TOSTORELOC = TOSTORELOC;
    }

    public String getENTERBY() {
        return ENTERBY;
    }

    public void setENTERBY(String ENTERBY) {
        this.ENTERBY = ENTERBY;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }
}