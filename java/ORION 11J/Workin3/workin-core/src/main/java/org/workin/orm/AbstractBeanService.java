/**
 * 
 */
package org.workin.orm;

import org.perf4j.aop.Profiled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
public class AbstractBeanService implements CrudService, BeanService {

	@Autowired
	private PersistenceProvider persistenceProvider;

	@Override
	public PersistenceProvider getPersistenceProvider() {
		return this.persistenceProvider;
	}

	@Override
	@Transactional
	@Profiled
	public <T> T merge(T entity) {
		return this.persistenceProvider.merge(entity);
	}

	@Override
	@Transactional
	@Profiled
	public <T> T save(T entity) {
		return this.persistenceProvider.persist(entity);
	}

	@Override
	@Transactional
	@Profiled
	public <T, PK> void remove(Class<T> entityClass, PK id) {
		this.persistenceProvider.remove(entityClass, id);
	}

	@Override
	@Transactional
	@Profiled
	public <T> void remove(T entity) {
		this.persistenceProvider.remove(entity);
	}

	@Override
	public <T, PK> T findById(Class<T> entityClass, PK id) {
		return this.persistenceProvider.findById(entityClass, id);
	}

}
