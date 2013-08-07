package com.demo.vo;

/**
 * Created with IntelliJ IDEA. User: xw Date: 4/7/13 Time: 3:20 PM To change
 * this template use File | Settings | File Templates.
 */
public class LocationVO {

    private double lat;

    private double lon;

    private int    source; // 0 GPS, 1 WIFI.

    public LocationVO() {

    }

    public LocationVO(double lat, double lon, int source) {
        this.lat = lat;
        this.lon = lon;
        this.source = source;
    }

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

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }
}
