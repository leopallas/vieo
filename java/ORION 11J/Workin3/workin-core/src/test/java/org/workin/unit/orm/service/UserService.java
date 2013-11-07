/**
 * 
 */
package org.workin.unit.orm.service;

import org.workin.orm.CrudService;
import org.workin.unit.orm.domain.entity.User;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
public interface UserService extends CrudService {
	public User findUserByLoginName(String loginName);

	public User insertUserByMyBatis(User user);
}
