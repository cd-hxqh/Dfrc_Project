package com.dfrc.hxqh.dfrc_project.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by think on 2016/7/1.
 * 设备
 */
@DatabaseTable(tableName = "ASSET")
public class ASSET extends Entity{
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(columnName = "ASSETNUM")
    public String ASSETNUM;//设备编码
    @DatabaseField(columnName = "DESCRIPTION")
    public String DESCRIPTION;//设备描述
    @DatabaseField(columnName = "FAMILY_DISPLAYNAME")
    public String FAMILY_DISPLAYNAME;//维护人名称
    @DatabaseField(columnName = "LOCATION")
    public String LOCATION;//位置编码
    @DatabaseField(columnName = "LOCATION_DESCRIPTION")
    public String LOCATION_DESCRIPTION;//位置描述
    @DatabaseField(columnName = "N_ASSETNUM")
    public String N_ASSETNUM;//资产编码
    @DatabaseField(columnName = "N_BONUSCODE")
    public String N_BONUSCODE;//注册代码
    @DatabaseField(columnName = "N_BRAND")
    public String N_BRAND;//品牌
    @DatabaseField(columnName = "N_CARDID")
    public String N_CARDID;//使用证号
    @DatabaseField(columnName = "N_DCEQUIPMENT")
    public String N_DCEQUIPMENT;//数控设备？
    @DatabaseField(columnName = "N_FAMILY")
    public String N_FAMILY;//维护人编码
    @DatabaseField(columnName = "N_IMPORTANCE")
    public String N_IMPORTANCE;//重要程度（A/B/C）
    @DatabaseField(columnName = "N_IPEQUIPMENT")
    public String N_IPEQUIPMENT;//尽快设备？
    @DatabaseField(columnName = "N_MAKERNAME")
    public String N_MAKERNAME;//制造厂商
    @DatabaseField(columnName = "N_MANAGEMENT")
    public String N_MANAGEMENT;//管理部门
    @DatabaseField(columnName = "N_MANTYPE")
    public String N_MANTYPE;//管理类型
    @DatabaseField(columnName = "N_MINITABDATE")
    public String N_MINITABDATE;//启用日期
    @DatabaseField(columnName = "N_MODEL")
    public String N_MODEL;//规格型号
    @DatabaseField(columnName = "N_PCEQUIPMENT")
    public String N_PCEQUIPMENT;//精密设备？
    @DatabaseField(columnName = "N_PROVIDERNAME")
    public String N_PROVIDERNAME;//供应商
    @DatabaseField(columnName = "N_RECORDATE")
    public String N_RECORDATE;//等级日期
    @DatabaseField(columnName = "N_RELEASEDATE")
    public String N_RELEASEDATE;//出厂日期
    @DatabaseField(columnName = "N_SORT")
    public String N_SORT;//设备分类
    @DatabaseField(columnName = "N_SPECIALTYPE")
    public String N_SPECIALTYPE;//设备类型
    @DatabaseField(columnName = "N_USERDEPARTMENT")
    public String N_USERDEPARTMENT;//使用部门
    @DatabaseField(columnName = "N_VIRTUAL")
    public String N_VIRTUAL;//虚拟设备？
    @DatabaseField(columnName = "SITEID")
    public String SITEID;//地点
    @DatabaseField(columnName = "STATUS")
    public String STATUS;//状态
    @DatabaseField(columnName = "N_CREWID")
    public String N_CREWID;//使用班组


    public String getASSETNUM() {
        return ASSETNUM;
    }

    public void setASSETNUM(String ASSETNUM) {
        this.ASSETNUM = ASSETNUM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getFAMILY_DISPLAYNAME() {
        return FAMILY_DISPLAYNAME;
    }

    public void setFAMILY_DISPLAYNAME(String FAMILY_DISPLAYNAME) {
        this.FAMILY_DISPLAYNAME = FAMILY_DISPLAYNAME;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getLOCATION_DESCRIPTION() {
        return LOCATION_DESCRIPTION;
    }

    public void setLOCATION_DESCRIPTION(String LOCATION_DESCRIPTION) {
        this.LOCATION_DESCRIPTION = LOCATION_DESCRIPTION;
    }

    public String getN_ASSETNUM() {
        return N_ASSETNUM;
    }

    public void setN_ASSETNUM(String n_ASSETNUM) {
        N_ASSETNUM = n_ASSETNUM;
    }

    public String getN_BONUSCODE() {
        return N_BONUSCODE;
    }

    public void setN_BONUSCODE(String n_BONUSCODE) {
        N_BONUSCODE = n_BONUSCODE;
    }

    public String getN_BRAND() {
        return N_BRAND;
    }

    public void setN_BRAND(String n_BRAND) {
        N_BRAND = n_BRAND;
    }

    public String getN_CARDID() {
        return N_CARDID;
    }

    public void setN_CARDID(String n_CARDID) {
        N_CARDID = n_CARDID;
    }

    public String getN_DCEQUIPMENT() {
        return N_DCEQUIPMENT;
    }

    public void setN_DCEQUIPMENT(String n_DCEQUIPMENT) {
        N_DCEQUIPMENT = n_DCEQUIPMENT;
    }

    public String getN_FAMILY() {
        return N_FAMILY;
    }

    public void setN_FAMILY(String n_FAMILY) {
        N_FAMILY = n_FAMILY;
    }

    public String getN_IMPORTANCE() {
        return N_IMPORTANCE;
    }

    public void setN_IMPORTANCE(String n_IMPORTANCE) {
        N_IMPORTANCE = n_IMPORTANCE;
    }

    public String getN_IPEQUIPMENT() {
        return N_IPEQUIPMENT;
    }

    public void setN_IPEQUIPMENT(String n_IPEQUIPMENT) {
        N_IPEQUIPMENT = n_IPEQUIPMENT;
    }

    public String getN_MAKERNAME() {
        return N_MAKERNAME;
    }

    public void setN_MAKERNAME(String n_MAKERNAME) {
        N_MAKERNAME = n_MAKERNAME;
    }

    public String getN_MANAGEMENT() {
        return N_MANAGEMENT;
    }

    public void setN_MANAGEMENT(String n_MANAGEMENT) {
        N_MANAGEMENT = n_MANAGEMENT;
    }

    public String getN_MANTYPE() {
        return N_MANTYPE;
    }

    public void setN_MANTYPE(String n_MANTYPE) {
        N_MANTYPE = n_MANTYPE;
    }

    public String getN_MINITABDATE() {
        return N_MINITABDATE;
    }

    public void setN_MINITABDATE(String n_MINITABDATE) {
        N_MINITABDATE = n_MINITABDATE;
    }

    public String getN_MODEL() {
        return N_MODEL;
    }

    public void setN_MODEL(String n_MODEL) {
        N_MODEL = n_MODEL;
    }

    public String getN_PCEQUIPMENT() {
        return N_PCEQUIPMENT;
    }

    public void setN_PCEQUIPMENT(String n_PCEQUIPMENT) {
        N_PCEQUIPMENT = n_PCEQUIPMENT;
    }

    public String getN_PROVIDERNAME() {
        return N_PROVIDERNAME;
    }

    public void setN_PROVIDERNAME(String n_PROVIDERNAME) {
        N_PROVIDERNAME = n_PROVIDERNAME;
    }

    public String getN_RECORDATE() {
        return N_RECORDATE;
    }

    public void setN_RECORDATE(String n_RECORDATE) {
        N_RECORDATE = n_RECORDATE;
    }

    public String getN_RELEASEDATE() {
        return N_RELEASEDATE;
    }

    public void setN_RELEASEDATE(String n_RELEASEDATE) {
        N_RELEASEDATE = n_RELEASEDATE;
    }

    public String getN_SORT() {
        return N_SORT;
    }

    public void setN_SORT(String n_SORT) {
        N_SORT = n_SORT;
    }

    public String getN_SPECIALTYPE() {
        return N_SPECIALTYPE;
    }

    public void setN_SPECIALTYPE(String n_SPECIALTYPE) {
        N_SPECIALTYPE = n_SPECIALTYPE;
    }

    public String getN_USERDEPARTMENT() {
        return N_USERDEPARTMENT;
    }

    public void setN_USERDEPARTMENT(String n_USERDEPARTMENT) {
        N_USERDEPARTMENT = n_USERDEPARTMENT;
    }

    public String getN_VIRTUAL() {
        return N_VIRTUAL;
    }

    public void setN_VIRTUAL(String n_VIRTUAL) {
        N_VIRTUAL = n_VIRTUAL;
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

    public String getN_CREWID() {
        return N_CREWID;
    }

    public void setN_CREWID(String n_CREWID) {
        N_CREWID = n_CREWID;
    }
}
