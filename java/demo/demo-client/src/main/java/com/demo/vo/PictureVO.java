package com.demo.vo;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class PictureVO {
    @JsonProperty("1")
    private String  userId;

    @JsonProperty("2")
    private String  picName;

    @JsonProperty("3")
    private Integer picType;

    @JsonProperty("4")
    private String  picDesc;

    @JsonProperty("5")
    private String  sodDesc;

    @JsonProperty("6")
    private Long    uploadDt;

    @JsonProperty("7")
    private Long    createDt;

    @JsonProperty("8")
    private String  userName;

    @JsonProperty("9")
    private String  picId;

    @JsonProperty("10")
    private Long    timeline;

    @JsonProperty("11")
    private Double  lat;

    @JsonProperty("12")
    private Double  lon;


    private String clh;

    private Integer xc;

    private Integer tw;

    private Integer bw;

    private Integer gzfj;

    private Integer gzfl;

    private String gznr;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
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
        this.picDesc = picDesc;
    }

    public String getSodDesc() {
        return sodDesc;
    }

    public void setSodDesc(String sodDesc) {
        this.sodDesc = sodDesc;
    }

    public Long getUploadDt() {
        return uploadDt;
    }

    public void setUploadDt(Long uploadDt) {
        this.uploadDt = uploadDt;
    }

    public Long getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Long createDt) {
        this.createDt = createDt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public Long getTimeline() {
        return timeline;
    }

    public void setTimeline(Long timeline) {
        this.timeline = timeline;
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

    @Override
    public String toString() {
        return "PictureVO{" + "userId='" + userId + '\'' + ", picName='" + picName + '\'' + ", picType=" + picType
                + ", picDesc='" + picDesc + '\'' + ", sodDesc='" + sodDesc + '\'' + ", uploadDt=" + uploadDt
                + ", createDt=" + createDt + ", userName='" + userName + '\'' + ", picId='" + picId + '\''
                + ", timeline=" + timeline + ", lat=" + lat + ", lon=" + lon + '}';
    }
}
