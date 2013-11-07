/**
 * 
 */
package org.workin.unit.orm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.workin.orm.AbstractBeanService;
import org.workin.unit.orm.domain.entity.User;
import org.workin.unit.orm.mybatis.UserMapper;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
@Service(value = "userService")
public class UserServiceImpl extends AbstractBeanService implements UserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public User findUserByLoginName(String loginName) {
		return super.getPersistenceProvider().findUniqueByProperty(User.class, "loginName", loginName);
	}

	@Override
	public User insertUserByMyBatis(User user) {
		userMapper.insertUser(user);
		return user;
	}

}
