/**
 * 
 */
package org.workin.unit.orm.jpa;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.workin.orm.Page;
import org.workin.orm.jpa.JpaPersistence;
import org.workin.test.utils.DbUnitUtils;
import org.workin.unit.orm.domain.entity.User;
import org.workin.unit.orm.mybatis.UserMapper;
import org.workin.utils.orm.PersistenceUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 * 
 */
@ContextConfiguration("classpath:applicationContext.xml")
public class JpaPersistenceTestCase extends AbstractTransactionalJUnit4SpringContextTests {
	protected DataSource dataSource;

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JpaPersistence jpaPersistence;

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

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#findById(java.lang.Class, java.lang.Object)}.
	 */
	@Test
	public void testFindById() {
		User user = jpaPersistence.findById(User.class, 1L);
		assertNotNull(user);
		assertEquals(1L, user.getId());
		assertEquals("admin", user.getLoginName());
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#getReference(java.lang.Class, java.lang.Object)}.
	 */
	@Test
	public void testGetReference() {
		User user = jpaPersistence.getReference(User.class, 1L);
		assertNotNull(user);
		assertEquals(1L, user.getId());
		assertEquals("admin", user.getLoginName());
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#merge(java.lang.Object)}.
	 */
	@Test
	public void testMerge() {
		User user = new User();
		user.setLoginName("testUser1");
		user.setPassword("tesPassword");
		user.setName("test user");
		user = jpaPersistence.merge(user);
		assertNotNull(user);
		assertEquals("testUser1", user.getLoginName());
		user = jpaPersistence.getReference(User.class, 2L);
		user.setLoginName("mergedUser");
		user = jpaPersistence.merge(user);
		assertNotNull(user);
		assertEquals(2L, user.getId());
		assertEquals("mergedUser", user.getLoginName());
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#persist(java.lang.Object)}.
	 */
	@Test
	public void testPersist() {
		User user = new User();
		user.setLoginName("testUser2");
		user.setPassword("tesPassword");
		user.setName("test user");
		user = jpaPersistence.merge(user);
		assertNotNull(user);
		assertEquals("testUser2", user.getLoginName());
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#remove(java.lang.Class, java.lang.Object)}.
	 */
	@Test
	@ExpectedException(EmptyResultDataAccessException.class)
	public void testRemoveClassOfTPK() {
		jpaPersistence.remove(User.class, 3L);
		jpaPersistence.findUniqueByProperty(User.class, "id", 3L);
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#remove(java.lang.Object)}.
	 */
	@Test
	@ExpectedException(EmptyResultDataAccessException.class)
	public void testRemoveT() {
		User user = jpaPersistence.getReference(User.class, 4L);
		jpaPersistence.remove(user);
		jpaPersistence.findUniqueByProperty(User.class, "id", 4L);
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#findByNamedQuery(java.lang.String)}.
	 */
	@Test
	public void testFindByNamedQueryString() {
		List<User> userList = jpaPersistence.findByNamedQuery("findAllUser");
		assertNotNull(userList);
		assertEquals(6, userList.size());
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#findByNamedQuery(java.lang.String, java.util.Map)}
	 * .
	 */
	@Test
	public void testFindByNamedQueryStringMapOfStringQ() {
		Map<String, Object> params = Maps.newHashMap();
		params.put("name", "Jack");
		List<User> userList = jpaPersistence.findByNamedQuery("findUserByName", params);
		assertNotNull(userList);
		assertEquals(1, userList.size());
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#findByNamedQuery(java.lang.String, java.lang.Object[])}
	 * .
	 */
	@Test
	public void testFindByNamedQueryStringObjectArray() {
		List<User> userList = jpaPersistence.findByNamedQuery("findUserById", 2L);
		assertNotNull(userList);
		assertEquals(1, userList.size());
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#findByQuery(java.lang.String)}
	 * .
	 */
	@Test
	public void testFindByQueryString() {
		String queryString = PersistenceUtils.buildQueryString(User.class, false).toString();
		List<User> userList = jpaPersistence.findByQuery(queryString);
		assertNotNull(userList);
		assertEquals(6, userList.size());
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#findByQuery(java.lang.String, java.util.Map)}
	 * .
	 */
	@Test
	public void testFindByQueryStringMapOfStringQ() {
		Map<String, Object> params = Maps.newHashMap();
		params.put("name", "Jack");
		String queryString = PersistenceUtils.buildQueryString(User.class, false, params).toString();
		List<User> userList = jpaPersistence.findByQuery(queryString, params);
		assertNotNull(userList);
		assertEquals(1, userList.size());
		params.put("loginName", "user3");// actually loginName is 'user2'
		queryString = PersistenceUtils.buildQueryString(User.class, false, params).toString();
		userList = jpaPersistence.findByQuery(queryString, params);
		assertEquals(0, userList.size());
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#findByQuery(java.lang.String, java.lang.Object[])}
	 * .
	 */
	@Test
	public void testFindByQueryStringObjectArray() {
		String queryString = PersistenceUtils.buildQueryString(User.class, false, "name").toString();
		List<User> userList = jpaPersistence.findByQuery(queryString, "Jack");
		assertNotNull(userList);
		assertEquals(1, userList.size());
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#batchPersist(java.util.List)}
	 * .
	 */
	@Test
	public void testBatchPersist() {
		List<User> userList = createUserList(3000);
		jpaPersistence.batchPersist(userList);
		jpaPersistence.flush();
		int count = jpaPersistence.findAll(User.class).size();
		assertEquals(3006, count);
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#batchMerge(java.util.List)}
	 * .
	 */
	@Test
	public void testBatchMerge() {
		List<User> userList = jpaPersistence.findByProperty(User.class, "name", "Sawyer");
		User user = createUser();
		userList.add(user);
		jpaPersistence.batchMerge(userList);
		int count = jpaPersistence.findAll(User.class).size();
		assertEquals(7, count);
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#batchRemove(java.util.List)}
	 * .
	 */
	@Test
	public void testBatchRemove() {
		List<User> userList = jpaPersistence.findByProperty(User.class, "name", "Sawyer");
		jpaPersistence.batchRemove(userList);
		int count = jpaPersistence.findAll(User.class).size();
		assertEquals(4, count);
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#countByProperty(java.lang.Class, java.lang.String, java.lang.Object)}
	 * .
	 */
	@Test
	public void testCountByProperty() {
		long count = jpaPersistence.countByProperty(User.class, "name", "Sawyer");
		assertEquals(2, count);
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#countByProperties(java.lang.Class, java.util.Map)}
	 * .
	 */
	@Test
	public void testCountByProperties() {
		Map<String, Object> params = Maps.newHashMap();
		params.put("name", "Jack");
		params.put("loginName", "user2");
		long count = jpaPersistence.countByProperties(User.class, params);
		assertEquals(1, count);

	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#countByQuery(java.lang.String, java.lang.Object[])}
	 * .
	 */
	@Test
	public void testCountByQueryStringObjectArray() {
		String queryString = PersistenceUtils.buildQueryString(User.class, false, "name").toString();
		long count = jpaPersistence.countByQuery(queryString, "Jack");
		assertEquals(1, count);
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#countByQuery(java.lang.String, java.util.Map)}
	 * .
	 */
	@Test
	public void testCountByQueryStringMapOfStringQ() {
		Map<String, Object> params = Maps.newHashMap();
		params.put("name", "Jack");
		String queryString = PersistenceUtils.buildQueryString(User.class, false, params).toString();
		long count = jpaPersistence.countByQuery(queryString, params);
		assertEquals(1, count);
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#findAll(java.lang.Class)}
	 * .
	 */
	@Test
	public void testFindAll() {
		List<User> userList = jpaPersistence.findAll(User.class);
		assertEquals(6, userList.size());
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#findUniqueByNamedQuery(java.lang.String, java.lang.Object[])}
	 * .
	 */
	@Test
	public void testFindUniqueByNamedOfQueryStringObjectArray() {
		User user = (User) jpaPersistence.findUniqueByNamedQuery("findUserByNameAndPasswd", "Jack", "user2");
		assertEquals(3, user.getId());
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#findUniqueByNamedQuery(java.lang.String, java.util.Map)}
	 * .
	 */
	@Test
	public void testFindUniqueByNamedOfQueryStringMapOfStringQ() {
		Map<String, Object> params = Maps.newHashMap();
		params.put("name", "Jack");
		User user = (User) jpaPersistence.findUniqueByNamedQuery("findUserByName", params);
		assertEquals(3, user.getId());
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#findUniqueByProperty(java.lang.Class, java.lang.String, java.lang.Object)}
	 * .
	 */
	@Test
	public void testFindUniqueByProperty() {
		User user = jpaPersistence.findUniqueByProperty(User.class, "name", "Jack");
		assertEquals(3, user.getId());
	}

	/**
	 * Test method for
	 * {@link org.workin.orm.jpa.JpaPersistenceImpl#findUniqueByProperties(java.lang.Class, java.util.Map)}
	 * .
	 */
	@Test
	@ExpectedException(EmptyResultDataAccessException.class)
	public void testFindUniqueByProperties() {
		Map<String, Object> params = Maps.newHashMap();
		params.put("name", "Jack");
		params.put("loginName", "user2");
		User user = jpaPersistence.findUniqueByProperties(User.class, params);
		assertEquals(3, user.getId());
		params.put("loginName", "user4");
		user = jpaPersistence.findUniqueByProperties(User.class, params);
		//assertNull(user);
	}

	/**
	* Test method for {@link org.workin.orm.jpa.JpaPersistenceImpl#findPageByQuery(org.workin.orm.Page, java.lang.String, java.lang.Object[])}.
	*/
	@Test
	public void testFindPageByQueryPageOfTStringObjectArray() {
		Page<User> page = new Page<User>(5);
		page.setOrderBy("loginName");
		page.setOrder(Page.DESC);
		String queryString = PersistenceUtils.buildQueryString(User.class, false, "name").toString();
		page = jpaPersistence.findPageByQuery(page, queryString, "Sawyer");
		assertNotNull(page);
		assertEquals(2, page.getTotalCount());
		//		List<User> resultList = page.getResult();
		//		User user = resultList.get(1);
		//		assertEquals("user4", user.getLoginName());
	}

	/**
	 * Test method for {@link org.workin.orm.jpa.JpaPersistenceImpl#findPageByQuery(org.workin.orm.Page, java.lang.String, java.util.Map)}.
	 */
	@Test
	public void testFindPageByQueryPageOfTStringMapOfStringQ() {
		Page<User> page = new Page<User>(5);
		Map<String, Object> params = Maps.newHashMap();
		params.put("name", "Jack");
		String queryString = PersistenceUtils.buildQueryString(User.class, false, params).toString();
		page = jpaPersistence.findPageByQuery(page, queryString, params);
		assertNotNull(page);
		assertEquals(1, page.getTotalCount());
		params.put("loginName", "user3");// actually loginName is 'user2'
		queryString = PersistenceUtils.buildQueryString(User.class, false, params).toString();
		page = jpaPersistence.findPageByQuery(page, queryString, params);
		assertEquals(0, page.getTotalCount());
	}

	/**
	 * Test method for {@link org.workin.orm.jpa.JpaPersistenceImpl#findPageByNamedQuery(org.workin.orm.Page, java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testFindPageByNamedQueryPageOfTStringObjectArray() {
		Page<User> page = new Page<User>(5);

		page = jpaPersistence.findPageByNamedQuery(page, "findUserById", 2L);
		assertNotNull(page);
		assertEquals(-1, page.getTotalCount());// autoCount is not available here
		assertEquals(-1, page.getTotalPages());
		assertEquals(1, page.getResult().size());
	}

	/**
	 * Test method for {@link org.workin.orm.jpa.JpaPersistenceImpl#findPageByNamedQuery(org.workin.orm.Page, java.lang.String, java.util.Map)}.
	 */
	@Test
	public void testFindPageByNamedQueryPageOfTStringMapOfStringQ() {
		Page<User> page = new Page<User>(5);

		Map<String, Object> params = Maps.newHashMap();
		params.put("name", "Jack");
		page = jpaPersistence.findPageByNamedQuery(page, "findUserByName", params);
		assertNotNull(page);
		assertEquals(-1, page.getTotalCount());// autoCount is not available here
		assertEquals(-1, page.getTotalPages());
		assertEquals(1, page.getResult().size());
	}

	/**
	 * Test method for {@link org.workin.orm.jpa.JpaPersistenceImpl#findByProperty(java.lang.Class, java.lang.String, java.lang.Object)}.
	 */
	@Test
	public void testFindByProperty() {
		List<User> userList = jpaPersistence.findByProperty(User.class, "name", "Sawyer");
		assertNotNull(userList);
		assertEquals(2, userList.size());
	}

	/**
	 * Test method for {@link org.workin.orm.jpa.JpaPersistenceImpl#findByProperties(java.lang.Class, java.util.Map)}.
	 */
	@Test
	public void testFindByProperties() {
		Map<String, Object> params = Maps.newHashMap();
		params.put("name", "Sawyer");
		List<User> userList = jpaPersistence.findByProperties(User.class, params);
		assertNotNull(userList);
		assertEquals(2, userList.size());
	}

	/**
	 * Test method for {@link org.workin.orm.jpa.JpaPersistenceImpl#findPageByProperty(org.workin.orm.Page, java.lang.Class, java.lang.String, java.lang.Object)}.
	 */
	@Test
	public void testFindPageByProperty() {
		Page<User> page = new Page<User>(5);
		page = jpaPersistence.findPageByProperty(page, User.class, "name", "Sawyer");
		assertNotNull(page);
		assertEquals(2, page.getTotalCount());
	}

	/**
	 * Test method for {@link org.workin.orm.jpa.JpaPersistenceImpl#findPageByProperties(org.workin.orm.Page, java.lang.Class, java.util.Map)}.
	 */
	@Test
	public void testFindPageByProperties() {
		Page<User> page = new Page<User>(5);
		Map<String, Object> params = Maps.newHashMap();
		params.put("name", "Sawyer");
		page = jpaPersistence.findPageByProperties(page, User.class, params);
		assertNotNull(page);
		assertEquals(2, page.getTotalCount());
	}

	@Test
	public void testJpaAndMyBatis() {
		User user = createUser();
		userMapper.insertUser(user);
		User selUser = jpaPersistence.findUniqueByProperty(User.class, "loginName", user.getLoginName());
		assertNotNull(selUser);
		jpaPersistence.remove(selUser);
		jpaPersistence.flush();
		user = userMapper.findUserByLoginName(selUser.getLoginName());
		assertNull(user);
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

	private List<User> createUserList(int size) {
		List<User> userList = Lists.newArrayList();
		for (int i = 0; i < size; i++) {
			userList.add(createUser());
		}
		return userList;
	}
}
