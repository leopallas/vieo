package com.test.performance.mvc.domain;

import java.util.Date;

public class TestMongoDB2 {
    private String id;

    private String  objID;

    private boolean active;

    private String  desc;

    private Date    createTime;

    private Date    updateTime;

    public TestMongoDB2(String id, String objID, boolean active, String desc, Date createTime, Date updateTime) {
        this.id = id;
        this.objID = objID;
        this.active = active;
        this.desc = desc;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getObjID() {
        return objID;
    }

    public boolean isActive() {
        return active;
    }

    public String getDesc() {
        return desc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getId() {
        return id;
    }
}
