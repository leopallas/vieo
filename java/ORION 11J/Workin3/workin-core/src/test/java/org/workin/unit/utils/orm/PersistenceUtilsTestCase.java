/**
 * 
 */
package org.workin.unit.utils.orm;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.unit.orm.domain.entity.User;
import org.workin.utils.orm.PersistenceUtils;

import com.google.common.collect.Maps;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
public class PersistenceUtilsTestCase {

	/**
	 * Test method for {@link org.workin.utils.orm.PersistenceUtils#buildQueryString(java.lang.Class, boolean)}.
	 */
	@Test
	public void testBuildQueryStringClassOfQBoolean() {
		String queryString = PersistenceUtils.buildQueryString(User.class, false).toString();
		assertEquals("SELECT obj FROM org.workin.unit.orm.domain.entity.User obj", queryString);
		queryString = PersistenceUtils.buildQueryString(User.class, true).toString();
		assertEquals("SELECT COUNT(*) as totalCount FROM org.workin.unit.orm.domain.entity.User obj", queryString);
	}

	/**
	 * Test method for {@link org.workin.utils.orm.PersistenceUtils#buildQueryString(java.lang.Class, boolean, java.lang.String[])}.
	 */
	@Test
	public void testBuildQueryStringClassOfQBooleanStringArray() {
		String queryString = PersistenceUtils.buildQueryString(User.class, false, "firstName", "lastName").toString();
		assertEquals("SELECT obj FROM org.workin.unit.orm.domain.entity.User obj WHERE firstName = ? AND lastName = ?",
				queryString);
		queryString = PersistenceUtils.buildQueryString(User.class, true, "firstName", "lastName").toString();
		assertEquals(
				"SELECT COUNT(*) as totalCount FROM org.workin.unit.orm.domain.entity.User obj WHERE firstName = ? AND lastName = ?",
				queryString);
	}

	/**
	 * Test method for {@link org.workin.utils.orm.PersistenceUtils#buildQueryString(java.lang.Class, boolean, java.util.Map)}.
	 */
	@Test
	public void testBuildQueryStringClassOfQBooleanMapOfStringQ() {
		Map<String, Object> params = Maps.newHashMap();
		params.put("firstName", "firstName");
		params.put("lastName", "lastName");
		String queryString = PersistenceUtils.buildQueryString(User.class, false, params).toString();
		assertEquals(
				"SELECT obj FROM org.workin.unit.orm.domain.entity.User obj WHERE lastName = :lastName AND firstName = :firstName",
				queryString);
		queryString = PersistenceUtils.buildQueryString(User.class, true, params).toString();
		assertEquals(
				"SELECT COUNT(*) as totalCount FROM org.workin.unit.orm.domain.entity.User obj WHERE lastName = :lastName AND firstName = :firstName",
				queryString);
	}

	public static final transient Logger logger = LoggerFactory.getLogger(PersistenceUtils.class);

}
