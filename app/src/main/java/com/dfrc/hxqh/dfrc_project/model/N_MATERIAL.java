package com.dfrc.hxqh.dfrc_project.model;

/**
 * Created by think on 2016/7/1.
 * 申请领用物料明细
 */
public class N_MATERIAL extends Entity {
    public String DESCRIPTION;//描述
    public String DESCRIPTION1;//存放位置
    public String ITEMNUM;//物料编码
    public String LINECOST;//行成本
    public String N_REASON;//领用原因
    public String N_SAP3;//申请数量
    public String UNITCOST;//单价
    public String CURBAL;//当前数量


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

    public String getDESCRIPTION1() {
        return DESCRIPTION1;
    }

    public void setDESCRIPTION1(String DESCRIPTION1) {
        this.DESCRIPTION1 = DESCRIPTION1;
    }

    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }

    public String getLINECOST() {
        return LINECOST;
    }

    public void setLINECOST(String LINECOST) {
        this.LINECOST = LINECOST;
    }

    public String getN_REASON() {
        return N_REASON;
    }

    public void setN_REASON(String n_REASON) {
        N_REASON = n_REASON;
    }

    public String getN_SAP3() {
        return N_SAP3;
    }

    public void setN_SAP3(String n_SAP3) {
        N_SAP3 = n_SAP3;
    }

    public String getUNITCOST() {
        return UNITCOST;
    }

    public void setUNITCOST(String UNITCOST) {
        this.UNITCOST = UNITCOST;
    }
}
