package com.demo.db;

public class AdminVO {

    private String id;
    private String name;
    private int admType;

    public int getAdmType() {
        return admType;
    }

    public void setAdmType(int admType) {
        this.admType = admType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
