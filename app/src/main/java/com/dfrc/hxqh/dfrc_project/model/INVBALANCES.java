package com.dfrc.hxqh.dfrc_project.model;

/**
 * Created by think on 2016/7/1.
 * 库存
 */
public class INVBALANCES extends Entity {
    public String BINNUM;//货柜
    public String CURBAL;//当前余量
    public String ITEMNUM;//物料编号
    public String ITEMNUMNAME;//物料名称
    public String LOCATION;//库房
    public String LOCATION2;//库房描述
    public String SITEID;//位置
    public String INVENTORY_ISSUEUNIT;//发放单位
    public String INVENTORY_UDUNITCOST;//单价
    public String DESCRIPTION;//描述
    public String LOCATION_DESCRIPTION;//库房描述
    public String N_BRAND;//班组
    public String ORDERUNIT;//单位成本
    public String UDUNITCOST;//行成本


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

    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }

    public String getITEMNUMNAME() {
        return ITEMNUMNAME;
    }

    public void setITEMNUMNAME(String ITEMNUMNAME) {
        this.ITEMNUMNAME = ITEMNUMNAME;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getLOCATION2() {
        return LOCATION2;
    }

    public void setLOCATION2(String LOCATION2) {
        this.LOCATION2 = LOCATION2;
    }

    public String getSITEID() {
        return SITEID;
    }

    public void setSITEID(String SITEID) {
        this.SITEID = SITEID;
    }

    public String getINVENTORY_ISSUEUNIT() {
        return INVENTORY_ISSUEUNIT;
    }

    public void setINVENTORY_ISSUEUNIT(String INVENTORY_ISSUEUNIT) {
        this.INVENTORY_ISSUEUNIT = INVENTORY_ISSUEUNIT;
    }

    public String getINVENTORY_UDUNITCOST() {
        return INVENTORY_UDUNITCOST;
    }

    public void setINVENTORY_UDUNITCOST(String INVENTORY_UDUNITCOST) {
        this.INVENTORY_UDUNITCOST = INVENTORY_UDUNITCOST;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getLOCATION_DESCRIPTION() {
        return LOCATION_DESCRIPTION;
    }

    public void setLOCATION_DESCRIPTION(String LOCATION_DESCRIPTION) {
        this.LOCATION_DESCRIPTION = LOCATION_DESCRIPTION;
    }

    public String getN_BRAND() {
        return N_BRAND;
    }

    public void setN_BRAND(String n_BRAND) {
        N_BRAND = n_BRAND;
    }

    public String getORDERUNIT() {
        return ORDERUNIT;
    }

    public void setORDERUNIT(String ORDERUNIT) {
        this.ORDERUNIT = ORDERUNIT;
    }

    public String getUDUNITCOST() {
        return UDUNITCOST;
    }

    public void setUDUNITCOST(String UDUNITCOST) {
        this.UDUNITCOST = UDUNITCOST;
    }
}
