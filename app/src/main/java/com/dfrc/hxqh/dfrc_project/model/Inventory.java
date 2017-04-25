package com.dfrc.hxqh.dfrc_project.model;

/**
 * Created by think on 2016/7/1.
 * 库存
 */
public class INVENTORY extends Entity{
    public String AVBLBALANCE;//当前余量
    public String BINNUM;//存放位置
    public String CURBALTOTAL;//当前余量
    public String ISSUEUNIT;//发放单位
    public String ITEMNUM;//备件
    public String DESCRIPTION;//备件名称
    public String LOCATION;//库房
    public String LOCATIONS_DESCRIPTION;//库房名称
    public String SITEID;//地点
    public String STATUS;//状态
    public String UDUNITCOST;//存放位置

    public String getAVBLBALANCE() {
        return AVBLBALANCE;
    }

    public void setAVBLBALANCE(String AVBLBALANCE) {
        this.AVBLBALANCE = AVBLBALANCE;
    }

    public String getBINNUM() {
        return BINNUM;
    }

    public void setBINNUM(String BINNUM) {
        this.BINNUM = BINNUM;
    }

    public String getCURBALTOTAL() {
        return CURBALTOTAL;
    }

    public void setCURBALTOTAL(String CURBALTOTAL) {
        this.CURBALTOTAL = CURBALTOTAL;
    }

    public String getISSUEUNIT() {
        return ISSUEUNIT;
    }

    public void setISSUEUNIT(String ISSUEUNIT) {
        this.ISSUEUNIT = ISSUEUNIT;
    }

    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getLOCATIONS_DESCRIPTION() {
        return LOCATIONS_DESCRIPTION;
    }

    public void setLOCATIONS_DESCRIPTION(String LOCATIONS_DESCRIPTION) {
        this.LOCATIONS_DESCRIPTION = LOCATIONS_DESCRIPTION;
    }

    public String getSITEID() {
        return SITEID;
    }

    public void setSITEID(String SITEID) {
        this.SITEID = SITEID;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getUDUNITCOST() {
        return UDUNITCOST;
    }

    public void setUDUNITCOST(String UDUNITCOST) {
        this.UDUNITCOST = UDUNITCOST;
    }
}
