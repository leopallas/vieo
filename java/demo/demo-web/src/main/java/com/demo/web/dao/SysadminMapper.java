package com.demo.web.dao;

import com.demo.web.domain.Sysadmin;

public interface SysadminMapper {
    int insert(Sysadmin record);

    int insertSelective(Sysadmin record);
}
