package com.demo.db;

import java.util.Date;

public class PictureVO {

    private String usrId;
    private String picId;
    private String picName;
    private String picDesc;
    private String sodDesc;
    private int    picType;
    private Date   uploadDt;
    private Date   createDt;
    private double lat ;
    private double lon ;
    private String  clh;
    private Integer xc;
    private Integer tw;
    private Integer bw;
    private Integer gzfj;
    private Integer gzfl;
    private String  gznr;
    private String  usrName;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getPicDesc() {
        return picDesc;
    }

    public void setPicDesc(String picDesc) {
        this.picDesc = picDesc;
    }

    public String getSodDesc() {
        return sodDesc;
    }

    public void setSodDesc(String sodDesc) {
        this.sodDesc = sodDesc;
    }

    public int getPicType() {
        return picType;
    }

    public void setPicType(int picType) {
        this.picType = picType;
    }

    public Date getUploadDt() {
        return uploadDt;
    }

    public void setUploadDt(Date uploadDt) {
        this.uploadDt = uploadDt;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getClh() {
        return clh;
    }

    public void setClh(String clh) {
        this.clh = clh;
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
        this.gznr = gznr;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }
}
