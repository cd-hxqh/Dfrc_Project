package com.dfrc.hxqh.dfrc_project.model;

/**
 * Created by think on 2016/7/1.
 * 定期点检工单
 */
public class GZWORKORDER extends Entity {
    public String WONUM;//单号
    public String DESCRIPTION;//描述
    public String ESTDUR;//持续时间
    public String N_COUNTERMEASURE;//对策
    public String N_FAILURCAUSE;//故障原因
    public String N_FAILURPROBLEM;//故障现象
    public String N_HAPPENDATE;//发生日期
    public String N_PLSTOPTIME;//生产线停止时间
    public String STATUS;//状态

    public String getWONUM() {
        return WONUM;
    }

    public void setWONUM(String WONUM) {
        this.WONUM = WONUM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getESTDUR() {
        return ESTDUR;
    }

    public void setESTDUR(String ESTDUR) {
        this.ESTDUR = ESTDUR;
    }

    public String getN_COUNTERMEASURE() {
        return N_COUNTERMEASURE;
    }

    public void setN_COUNTERMEASURE(String n_COUNTERMEASURE) {
        N_COUNTERMEASURE = n_COUNTERMEASURE;
    }

    public String getN_FAILURCAUSE() {
        return N_FAILURCAUSE;
    }

    public void setN_FAILURCAUSE(String n_FAILURCAUSE) {
        N_FAILURCAUSE = n_FAILURCAUSE;
    }

    public String getN_FAILURPROBLEM() {
        return N_FAILURPROBLEM;
    }

    public void setN_FAILURPROBLEM(String n_FAILURPROBLEM) {
        N_FAILURPROBLEM = n_FAILURPROBLEM;
    }

    public String getN_HAPPENDATE() {
        return N_HAPPENDATE;
    }

    public void setN_HAPPENDATE(String n_HAPPENDATE) {
        N_HAPPENDATE = n_HAPPENDATE;
    }

    public String getN_PLSTOPTIME() {
        return N_PLSTOPTIME;
    }

    public void setN_PLSTOPTIME(String n_PLSTOPTIME) {
        N_PLSTOPTIME = n_PLSTOPTIME;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }
}