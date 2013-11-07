package org.workin.database;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DbContextHolder.getDbType();
	}

	@Override
	public DataSource determineTargetDataSource() {
		return super.determineTargetDataSource();
	}

}
