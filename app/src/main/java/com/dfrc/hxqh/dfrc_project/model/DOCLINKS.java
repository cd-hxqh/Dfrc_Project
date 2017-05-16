package com.dfrc.hxqh.dfrc_project.model;

/**
 * Created by think on 2016/7/1.
 * 图片信息类
 */
public class DOCLINKS extends Entity {
    public String DOCINFOID;//
    public String DOCTYPE;//文件类型
    public String OWNERID;//编号Id
    public String OWNERTABLE;//表名
    public String URLNAME;//地址
    public String DOCUMENT;//

    public String getDOCINFOID() {
        return DOCINFOID;
    }

    public void setDOCINFOID(String DOCINFOID) {
        this.DOCINFOID = DOCINFOID;
    }

    public String getDOCTYPE() {
        return DOCTYPE;
    }

    public void setDOCTYPE(String DOCTYPE) {
        this.DOCTYPE = DOCTYPE;
    }

    public String getOWNERID() {
        return OWNERID;
    }

    public void setOWNERID(String OWNERID) {
        this.OWNERID = OWNERID;
    }

    public String getOWNERTABLE() {
        return OWNERTABLE;
    }

    public void setOWNERTABLE(String OWNERTABLE) {
        this.OWNERTABLE = OWNERTABLE;
    }

    public String getURLNAME() {
        return URLNAME;
    }

    public void setURLNAME(String URLNAME) {
        this.URLNAME = URLNAME;
    }

    public String getDOCUMENT() {
        return DOCUMENT;
    }

    public void setDOCUMENT(String DOCUMENT) {
        this.DOCUMENT = DOCUMENT;
    }
}