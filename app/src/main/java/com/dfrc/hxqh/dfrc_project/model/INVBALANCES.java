package com.dfrc.hxqh.dfrc_project.model;

/**
 * Created by think on 2016/7/1.
 * 库存余量
 */
public class INVBALANCES extends Entity {
    public String BINNUM;//货柜
    public String CURBAL;//
    public String CURBALTOTAL;//
    public String DESCRIPTION;//描述
    public String ITEMNUM;//项目
    public String LOCATION_DESCRIPTION;//位置描述
    public String N_BRAND;//
    public String ORDERUNIT;//
    public String UDUNITCOST;//

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

    public String getCURBALTOTAL() {
        return CURBALTOTAL;
    }

    public void setCURBALTOTAL(String CURBALTOTAL) {
        this.CURBALTOTAL = CURBALTOTAL;
    }

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
