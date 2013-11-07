/**
 * 
 */
package org.workin.orm.jpa;

import java.util.List;
import java.util.Map;

import org.workin.orm.Page;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 * @param <PK>
 *
 */
public interface JpaPersistence {

	public <T> boolean contains(final T entity);

	public <X, T> List<X> findAll(final Class<T> entityClass);

	public <T, PK> T findById(final Class<T> entityClass, PK id);

	public <X> List<X> findByNamedQuery(final String queryName);

	public <X> List<X> findByNamedQuery(final String queryName, final Map<String, ?> params);

	public <X> List<X> findByNamedQuery(final String queryName, final Object... values);

	public <X> List<X> findByQuery(final String queryString);

	public <X> List<X> findByQuery(final String queryString, final Map<String, ?> params);

	public <X> List<X> findByQuery(final String queryString, final Object... values);

	public void flush();

	public <T, PK> T getReference(final Class<T> entityClass, PK id);

	public <T> T merge(final T entity);

	public <T> T persist(final T entity);

	public <T> void refresh(final T entity);

	public <T, PK> void remove(final Class<T> entityClass, PK id);

	public <T> void remove(final T entity);

	// add extended methods implement
	public void batchPersist(final List<?> entityList);

	public void batchMerge(final List<?> entityList);

	public void batchRemove(final List<?> entityList);

	public long countByProperty(final Class<?> entityClass, final String propertyName, final Object value);

	public long countByProperties(final Class<?> entityClass, final Map<String, ?> params);

	public long countByQuery(final String queryString);

	public long countByQuery(final String queryString, final Object... values);

	public long countByQuery(final String queryString, final Map<String, ?> params);

	public Object findUniqueByNamedQuery(final String queryName, final Object... values);

	public Object findUniqueByNamedQuery(final String queryName, final Map<String, ?> params);

	public <T> List<T> findByProperty(final Class<T> entityClass, final String propertyName, final Object value);

	public <T> List<T> findByProperties(final Class<T> entityClass, final Map<String, ?> params);

	public <T> T findUniqueByProperty(final Class<T> entityClass, final String propertyName, final Object value);

	public <T> T findUniqueByProperties(final Class<T> entityClass, final Map<String, ?> params);

	public <T> Page<T> findPageByQuery(final Page<T> page, final String queryString, final Object... values);

	public <T> Page<T> findPageByQuery(final Page<T> page, final String queryString, final Map<String, ?> params);

	public <T> Page<T> findPageByNamedQuery(final Page<T> page, final String queryName, final Object... values);

	public <T> Page<T> findPageByNamedQuery(final Page<T> page, final String queryName, final Map<String, ?> params);

	public <T> Page<T> findPageByProperty(final Page<T> page, final Class<T> entityClass, final String propertyName,
			final Object value);

	public <T> Page<T> findPageByProperties(final Page<T> page, final Class<T> entityClass, final Map<String, ?> params);

}
