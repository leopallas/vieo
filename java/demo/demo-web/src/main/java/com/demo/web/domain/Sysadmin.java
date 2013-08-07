package com.demo.web.domain;

public class Sysadmin {
    private String adminid;

    private String adminpas;

    private String adminname;

    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid == null ? null : adminid.trim();
    }

    public String getAdminpas() {
        return adminpas;
    }

    public void setAdminpas(String adminpas) {
        this.adminpas = adminpas == null ? null : adminpas.trim();
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname == null ? null : adminname.trim();
    }
}
