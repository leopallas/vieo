package com.demo.web.dao;

import com.demo.web.domain.Location;

public interface LocationMapper {
    int deleteByPrimaryKey(String locId);

    int insert(Location record);

    int insertSelective(Location record);

    Location selectByPrimaryKey(String locId);

    int updateByPrimaryKeySelective(Location record);

    int updateByPrimaryKey(Location record);
}
