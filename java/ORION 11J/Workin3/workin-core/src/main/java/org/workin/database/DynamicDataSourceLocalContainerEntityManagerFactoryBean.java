/**
 * 
 */
package org.workin.database;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
public class DynamicDataSourceLocalContainerEntityManagerFactoryBean extends LocalContainerEntityManagerFactoryBean {

	@Autowired
	private DynamicDataSource dataSource;

	@Override
	public DataSource getDataSource() {
		if (dataSource != null) {
			return dataSource.determineTargetDataSource();
		}
		return super.getDataSource();
	}
}
