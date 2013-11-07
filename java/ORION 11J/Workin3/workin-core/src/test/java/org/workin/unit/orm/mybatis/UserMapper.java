package org.workin.unit.orm.mybatis;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import org.workin.orm.mybatis.MyBatisMapper;
import org.workin.unit.orm.domain.entity.User;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
@Service(value = "userMapper")
public interface UserMapper extends MyBatisMapper {

	@Select("SELECT * FROM user_table WHERE LOGIN_NAME = #{loginName}")
	public User findUserByLoginName(@Param("loginName") String loginName);

	public User findUserById(long id);

	public void insertUser(User user);
}
