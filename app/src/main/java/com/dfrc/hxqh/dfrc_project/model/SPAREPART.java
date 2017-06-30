package com.dfrc.hxqh.dfrc_project.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by think on 2016/7/1.
 * 设备
 */
@DatabaseTable(tableName = "SPAREPART")
public class SPAREPART extends Entity {
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(columnName = "ITEMNUM")
    public String ITEMNUM;//物料编码
    @DatabaseField(columnName = "ITEMNUM_DESCRIPTION")
    public String ITEMNUM_DESCRIPTION;//物料名称
    @DatabaseField(columnName = "N_BRAND")
    public String N_BRAND;//品牌
    @DatabaseField(columnName = "N_LOCATION")
    public String N_LOCATION;//安装部位
    @DatabaseField(columnName = "N_MODELNUM")
    public String N_MODELNUM;//规格型号
    @DatabaseField(columnName = "QUANTITY")
    public String QUANTITY;//数量
    @DatabaseField(columnName = "ASSETNUM")
    public String ASSETNUM;//资产编号

    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }

    public String getITEMNUM_DESCRIPTION() {
        return ITEMNUM_DESCRIPTION;
    }

    public void setITEMNUM_DESCRIPTION(String ITEMNUM_DESCRIPTION) {
        this.ITEMNUM_DESCRIPTION = ITEMNUM_DESCRIPTION;
    }

    public String getN_BRAND() {
        return N_BRAND;
    }

    public void setN_BRAND(String n_BRAND) {
        N_BRAND = n_BRAND;
    }

    public String getN_LOCATION() {
        return N_LOCATION;
    }

    public void setN_LOCATION(String n_LOCATION) {
        N_LOCATION = n_LOCATION;
    }

    public String getN_MODELNUM() {
        return N_MODELNUM;
    }

    public void setN_MODELNUM(String n_MODELNUM) {
        N_MODELNUM = n_MODELNUM;
    }

    public String getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(String QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getASSETNUM() {
        return ASSETNUM;
    }

    public void setASSETNUM(String ASSETNUM) {
        this.ASSETNUM = ASSETNUM;
    }
}