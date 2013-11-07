package org.workin.unit.orm.mybatis;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.workin.test.utils.DbUnitUtils;
import org.workin.unit.orm.domain.entity.User;

@ContextConfiguration("classpath:applicationContext.xml")
public class UserMapperTest extends AbstractTransactionalJUnit4SpringContextTests {
	protected DataSource dataSource;

	@Autowired
	UserMapper userMapper;

	@Override
	@Autowired
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
		this.dataSource = dataSource;
	}

	@Before
	public void loadTestData() throws Exception {
		//simpleJdbcTemplate.update("DROP all objects");

		executeSqlScript("classpath:/schema/mysql/schema.sql", false);
		DbUnitUtils.loadData(dataSource, "/test-data.xml");
	}

	@Test
	public void testFindUserByLoginName() {
		assertNotNull(userMapper);
		User user = userMapper.findUserByLoginName("admin");
		assertNotNull(user);
		assertEquals(1L, user.getId());
	}

	@Test
	public void testFindUserById() {
		assertNotNull(userMapper);
		User user = userMapper.findUserById(1L);
		assertNotNull(user);
		assertEquals("admin", user.getLoginName());

	}

}
