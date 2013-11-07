package org.workin.unit.database;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.workin.database.DBType;
import org.workin.database.DbContextHolder;
import org.workin.unit.orm.domain.entity.User;
import org.workin.unit.orm.service.UserService;
import net.sf.cglib.reflect.*;

/**
 * If we need dynamically switch the database, then the transaction should be managed separately. 
 * Otherwise, if we use spring provided transaction, the records will only stored in the default database.
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 * 
 */
@ContextConfiguration("classpath:applicationContext.xml")
public class DynamicDataSourceTestCase extends AbstractJUnit4SpringContextTests {

	@Autowired
	private UserService userService;

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#persist(java.lang.Object)}
	 * .
	 */
	@Test
	//@ExpectedException(EmptyResultDataAccessException.class)
	public void testPersist() {
		User user = createUser();
		user = userService.save(user);
		DbContextHolder.setDbType("ds2");

		User user2 = createUser();
		user2 = userService.save(user2);
		DbContextHolder.clearDbType();
		user = userService.findUserByLoginName(user.getLoginName());
		assertNotNull(user);
		DbContextHolder.setDbType("ds2");
		user = userService.findUserByLoginName(user2.getLoginName());
		assertNotNull(user);
		DbContextHolder.clearDbType();
	}

	@Test
	public void testMyBatisInsert() {
		User user = createUser();
		userService.insertUserByMyBatis(user);
		DbContextHolder.setDbType(DBType.ds2);
		User user2 = createUser();
		userService.insertUserByMyBatis(user2);
		DbContextHolder.clearDbType();
		user = userService.findUserByLoginName(user.getLoginName());
		assertNotNull(user);
		DbContextHolder.setDbType(DBType.ds2);
		user = userService.findUserByLoginName(user2.getLoginName());
		assertNotNull(user);
		DbContextHolder.clearDbType();
	}

	private User createUser() {
		User user = new User();
		long random = Math.round(99999999 * Math.random());
		user.setLoginName("loginName" + random);
		user.setName("Name" + random);
		user.setEmail("email" + random + "@workin.org.cn");
		user.setPassword("password" + random);
		return user;
	}
}
