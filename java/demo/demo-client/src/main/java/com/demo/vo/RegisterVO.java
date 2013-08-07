package com.demo.vo;

import org.codehaus.jackson.annotate.JsonProperty;

public class RegisterVO {
    @JsonProperty("1")
    private String serialNO;

    @JsonProperty("2")
    private String barcode;

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setSerialNO(String serialNO) {
        this.serialNO = serialNO;
    }

    public String getSerialNO() {
        return serialNO;
    }
}
