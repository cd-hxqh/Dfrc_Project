package com.dfrc.hxqh.dfrc_project.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by think on 2016/7/1.
 * 用户权限
 */
@DatabaseTable(tableName = "USERPERMISSIONS")
public class USERPERMISSIONS extends Entity{
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(columnName = "PERSONID")
    public String PERSONID;//用户PERSIONID
    @DatabaseField(columnName = "APPID")
    public String APPID;//权限

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPERSONID() {
        return PERSONID;
    }

    public void setPERSONID(String PERSONID) {
        this.PERSONID = PERSONID;
    }

    public String getAPPID() {
        return APPID;
    }

    public void setAPPID(String APPID) {
        this.APPID = APPID;
    }
}
