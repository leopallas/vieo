/**
 * 
 */
package net.sf.log4jdbc;

import java.sql.Connection;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
public class DataSourceSpyInterceptor implements MethodInterceptor {
	private RdbmsSpecifics rdbmsSpecifics = null;

	private RdbmsSpecifics getRdbmsSpecifics(Connection conn) {
		if (rdbmsSpecifics == null) {
			rdbmsSpecifics = DriverSpy.getRdbmsSpecifics(conn);
		}
		return rdbmsSpecifics;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object result = invocation.proceed();
		if (SpyLogFactory.getSpyLogDelegator().isJdbcLoggingEnabled()) {
			if (result instanceof Connection) {
				Connection conn = (Connection) result;
				return new ConnectionSpy(conn, getRdbmsSpecifics(conn));
			}
		}
		return result;
	}
}
