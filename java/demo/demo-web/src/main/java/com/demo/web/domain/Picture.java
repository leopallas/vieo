package com.demo.web.domain;

import java.util.Date;

public class Picture {
    private String picId;

    private String usrId;

    private String picName;

    private Integer picType;

    private String picDesc;

    private String sodDesc;

    private Date createDt;

    private Date uploadDt;

    private Double lat;

    private Double lon;

    private String clh;

    private Integer xc;

    private Integer tw;

    private Integer bw;

    private Integer gzfj;

    private Integer gzfl;

    private String gznr;

    private String  usrName;

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId == null ? null : picId.trim();
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId == null ? null : usrId.trim();
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName == null ? null : picName.trim();
    }

    public Integer getPicType() {
        return picType;
    }

    public void setPicType(Integer picType) {
        this.picType = picType;
    }

    public String getPicDesc() {
        return picDesc;
    }

    public void setPicDesc(String picDesc) {
        this.picDesc = picDesc == null ? null : picDesc.trim();
    }

    public String getSodDesc() {
        return sodDesc;
    }

    public void setSodDesc(String sodDesc) {
        this.sodDesc = sodDesc == null ? null : sodDesc.trim();
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getUploadDt() {
        return uploadDt;
    }

    public void setUploadDt(Date uploadDt) {
        this.uploadDt = uploadDt;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getClh() {
        return clh;
    }

    public void setClh(String clh) {
        this.clh = clh == null ? null : clh.trim();
    }

    public Integer getXc() {
        return xc;
    }

    public void setXc(Integer xc) {
        this.xc = xc;
    }

    public Integer getTw() {
        return tw;
    }

    public void setTw(Integer tw) {
        this.tw = tw;
    }

    public Integer getBw() {
        return bw;
    }

    public void setBw(Integer bw) {
        this.bw = bw;
    }

    public Integer getGzfj() {
        return gzfj;
    }

    public void setGzfj(Integer gzfj) {
        this.gzfj = gzfj;
    }

    public Integer getGzfl() {
        return gzfl;
    }

    public void setGzfl(Integer gzfl) {
        this.gzfl = gzfl;
    }

    public String getGznr() {
        return gznr;
    }

    public void setGznr(String gznr) {
        this.gznr = gznr == null ? null : gznr.trim();
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }
}