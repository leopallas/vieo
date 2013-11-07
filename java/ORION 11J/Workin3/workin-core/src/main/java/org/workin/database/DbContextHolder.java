package org.workin.database;


/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
public class DbContextHolder {

	private static final ThreadLocal<Object> contextHolder = new ThreadLocal<Object>();

	public static void setDbType(Object dbType) {
		contextHolder.set(dbType);
	}

	public static String getDbType() {
		return (String) contextHolder.get();
	}

	public static void clearDbType() {
		contextHolder.remove();
	}

}
