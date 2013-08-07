package com.demo.web.dao;

import com.demo.web.domain.Device;

public interface DeviceMapper {
    int insert(Device record);

    int insertSelective(Device record);

    Device selectByBarCode(String barCode);

    Device selectBySerialNo(String serialNo);

    Device selectByUsrId(String usrId);

    int updateByBarCode(Device record);
}
