package com.dfrc.hxqh.dfrc_project.model;

/**
 * Created by think on 2016/7/1.
 * 采购单行
 */
public class POLINE extends Entity {
    public String DESCRIPTION;//描述
    public String ITEMNUM;//项目
    public String MANUFACTURER;//制造商
    public String ORDERQTY;//数量
    public String ORDERUNIT;//订购单位
    public String POLINENUM;//行
    public String STORELOC;//库存地点
    public String UNITCOST;//单位成本
    public String VENDELIVERYDATE;//供应商日期


    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }

    public String getMANUFACTURER() {
        return MANUFACTURER;
    }

    public void setMANUFACTURER(String MANUFACTURER) {
        this.MANUFACTURER = MANUFACTURER;
    }

    public String getORDERQTY() {
        return ORDERQTY;
    }

    public void setORDERQTY(String ORDERQTY) {
        this.ORDERQTY = ORDERQTY;
    }

    public String getORDERUNIT() {
        return ORDERUNIT;
    }

    public void setORDERUNIT(String ORDERUNIT) {
        this.ORDERUNIT = ORDERUNIT;
    }

    public String getPOLINENUM() {
        return POLINENUM;
    }

    public void setPOLINENUM(String POLINENUM) {
        this.POLINENUM = POLINENUM;
    }

    public String getSTORELOC() {
        return STORELOC;
    }

    public void setSTORELOC(String STORELOC) {
        this.STORELOC = STORELOC;
    }

    public String getUNITCOST() {
        return UNITCOST;
    }

    public void setUNITCOST(String UNITCOST) {
        this.UNITCOST = UNITCOST;
    }

    public String getVENDELIVERYDATE() {
        return VENDELIVERYDATE;
    }

    public void setVENDELIVERYDATE(String VENDELIVERYDATE) {
        this.VENDELIVERYDATE = VENDELIVERYDATE;
    }
}