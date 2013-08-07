package com.demo.web.dao;

import com.demo.web.domain.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String usrId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String usrId);

    User selectBySecretToken(String secretToken);

    List<User> selectAll();

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
