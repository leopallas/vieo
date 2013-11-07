/**
 * 
 */
package org.workin.orm;

import java.util.List;
import java.util.Map;

import org.workin.orm.jpa.JpaPersistence;

/**
 * @author <a href="mailto:leopallas@gmail.com">Leo Xu</a>
 *
 */
public class PersistenceProvider {

	// Handle service with JPA
	private JpaPersistence jpaPersistence;

	/**
	 * @param jpaPersistence
	 */
	public void setJpaPersistence(JpaPersistence jpaPersistence) {
		this.jpaPersistence = jpaPersistence;
	}

	/**
	 * @param <T>
	 * @param entity
	 * @return
	 */
	public <T> boolean contains(final T entity) {
		return jpaPersistence.contains(entity);
	}

	/**
	 * @param <X>
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	public <X, T> List<X> findAll(final Class<T> entityClass) {
		return jpaPersistence.findAll(entityClass);
	}

	/**
	 * @param <T>
	 * @param <PK>
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public <T, PK> T findById(final Class<T> entityClass, PK id) {
		return jpaPersistence.findById(entityClass, id);
	}

	/**
	 * @param <X>
	 * @param queryName
	 * @return
	 */
	public <X> List<X> findByNamedQuery(final String queryName) {
		return jpaPersistence.findByNamedQuery(queryName);
	}

	/**
	 * @param <X>
	 * @param queryName
	 * @param params
	 * @return
	 */
	public <X> List<X> findByNamedQuery(final String queryName, final Map<String, ?> params) {
		return jpaPersistence.findByNamedQuery(queryName, params);
	}

	/**
	 * @param <X>
	 * @param queryName
	 * @param values
	 * @return
	 */
	public <X> List<X> findByNamedQuery(final String queryName, final Object... values) {
		return jpaPersistence.findByNamedQuery(queryName, values);
	}

	/**
	 * @param <X>
	 * @param queryString
	 * @return
	 */
	public <X> List<X> findByQuery(final String queryString) {
		return jpaPersistence.findByQuery(queryString);
	}

	/**
	 * @param <X>
	 * @param queryString
	 * @param params
	 * @return
	 */
	public <X> List<X> findByQuery(final String queryString, final Map<String, ?> params) {
		return jpaPersistence.findByQuery(queryString, params);
	}

	/**
	 * @param <X>
	 * @param queryString
	 * @param values
	 * @return
	 */
	public <X> List<X> findByQuery(final String queryString, final Object... values) {
		return jpaPersistence.findByQuery(queryString, values);
	}

	/**
	 * 
	 */
	public void flush() {
		jpaPersistence.flush();
	}

	/**
	 * @param <T>
	 * @param <PK>
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public <T, PK> T getReference(final Class<T> entityClass, PK id) {
		return jpaPersistence.getReference(entityClass, id);
	}

	/**
	 * @param <T>
	 * @param entity
	 * @return
	 */
	public <T> T merge(final T entity) {
		return jpaPersistence.merge(entity);
	}

	/**
	 * @param <T>
	 * @param entity
	 * @return
	 */
	public <T> T persist(final T entity) {
		return jpaPersistence.persist(entity);
	}

	/**
	 * @param <T>
	 * @param entity
	 */
	public <T> void refresh(final T entity) {
		jpaPersistence.refresh(entity);
	}

	/**
	 * @param <T>
	 * @param <PK>
	 * @param entityClass
	 * @param id
	 */
	public <T, PK> void remove(final Class<T> entityClass, PK id) {
		jpaPersistence.remove(entityClass, id);
	}

	/**
	 * @param <T>
	 * @param entity
	 */
	public <T> void remove(final T entity) {
		jpaPersistence.remove(entity);
	}

	/**
	 * @param entityList
	 */
	public void batchPersist(final List<?> entityList) {
		jpaPersistence.batchPersist(entityList);
	}

	/**
	 * @param entityList
	 */
	public void batchMerge(final List<?> entityList) {
		jpaPersistence.batchMerge(entityList);
	}

	/**
	 * @param entityList
	 */
	public void batchRemove(final List<?> entityList) {
		jpaPersistence.batchRemove(entityList);
	}

	/**
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public long countByProperty(final Class<?> entityClass, final String propertyName, final Object value) {
		return jpaPersistence.countByProperty(entityClass, propertyName, value);
	}

	/**
	 * @param entityClass
	 * @param params
	 * @return
	 */
	public long countByProperties(final Class<?> entityClass, final Map<String, ?> params) {
		return jpaPersistence.countByProperties(entityClass, params);
	}

	/**
	 * @param queryString
	 * @return
	 */
	public long countByQuery(final String queryString) {
		return jpaPersistence.countByQuery(queryString);
	}

	/**
	 * @param queryString
	 * @param values
	 * @return
	 */
	public long countByQuery(final String queryString, final Object... values) {
		return jpaPersistence.countByQuery(queryString, values);
	}

	/**
	 * @param queryString
	 * @param params
	 * @return
	 */
	public long countByQuery(final String queryString, final Map<String, ?> params) {
		return jpaPersistence.countByQuery(queryString, params);
	}

	/**
	 * @param queryName
	 * @param values
	 * @return
	 */
	public Object findUniqueByNamedQuery(final String queryName, final Object... values) {
		return jpaPersistence.findUniqueByNamedQuery(queryName, values);
	}

	/**
	 * @param queryName
	 * @param params
	 * @return
	 */
	public Object findUniqueByNamedQuery(final String queryName, final Map<String, ?> params) {
		return jpaPersistence.findUniqueByNamedQuery(queryName, params);
	}

	/**
	 * @param <T>
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public <T> List<T> findByProperty(final Class<T> entityClass, final String propertyName, final Object value) {
		return jpaPersistence.findByProperty(entityClass, propertyName, value);
	}

	/**
	 * @param <T>
	 * @param entityClass
	 * @param params
	 * @return
	 */
	public <T> List<T> findByProperties(final Class<T> entityClass, final Map<String, ?> params) {
		return jpaPersistence.findByProperties(entityClass, params);
	}

	/**
	 * @param <T>
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public <T> T findUniqueByProperty(final Class<T> entityClass, final String propertyName, final Object value) {
		return jpaPersistence.findUniqueByProperty(entityClass, propertyName, value);
	}

	/**
	 * @param <T>
	 * @param entityClass
	 * @param params
	 * @return
	 */
	public <T> T findUniqueByProperties(final Class<T> entityClass, final Map<String, ?> params) {
		return jpaPersistence.findUniqueByProperties(entityClass, params);
	}

	/**
	 * @param <T>
	 * @param page
	 * @param queryString
	 * @param values
	 * @return
	 */
	public <T> Page<T> findPageByQuery(final Page<T> page, final String queryString, final Object... values) {
		return jpaPersistence.findPageByQuery(page, queryString, values);
	}

	/**
	 * @param <T>
	 * @param page
	 * @param queryString
	 * @param params
	 * @return
	 */
	public <T> Page<T> findPageByQuery(final Page<T> page, final String queryString, final Map<String, ?> params) {
		return jpaPersistence.findPageByQuery(page, queryString, params);
	}

	/**
	 * @param <T>
	 * @param page Variable 'autoCount' for page is not available here
	 * @param queryName
	 * @param values
	 * @return
	 */
	public <T> Page<T> findPageByNamedQuery(final Page<T> page, final String queryName, final Object... values) {
		return jpaPersistence.findPageByNamedQuery(page, queryName, values);
	}

	/**
	 * 
	 * @param <T>
	 * @param page Variable 'autoCount' for page is not available here
	 * @param queryName
	 * @param params
	 * @return
	 */
	public <T> Page<T> findPageByNamedQuery(final Page<T> page, final String queryName, final Map<String, ?> params) {
		return jpaPersistence.findPageByNamedQuery(page, queryName, params);
	}

	/**
	 * @param <T>
	 * @param page
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public <T> Page<T> findPageByProperty(final Page<T> page, final Class<T> entityClass, final String propertyName,
			final Object value) {
		return jpaPersistence.findPageByProperty(page, entityClass, propertyName, value);
	}

	/**
	 * @param <T>
	 * @param page
	 * @param entityClass
	 * @param params
	 * @return
	 */
	public <T> Page<T> findPageByProperties(final Page<T> page, final Class<T> entityClass, final Map<String, ?> params) {
		return jpaPersistence.findPageByProperties(page, entityClass, params);
	}

}
