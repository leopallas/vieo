package com.demo.vo;

public class HeartbeatVO {
    private boolean isUpdatePicture;

    public HeartbeatVO() {

    }

    public HeartbeatVO(boolean isUpdatePicture) {
        this.isUpdatePicture = isUpdatePicture;
    }

    public boolean isUpdatePicture() {
        return isUpdatePicture;
    }

    public void setUpdatePicture(boolean updatePicture) {
        isUpdatePicture = updatePicture;
    }
}
