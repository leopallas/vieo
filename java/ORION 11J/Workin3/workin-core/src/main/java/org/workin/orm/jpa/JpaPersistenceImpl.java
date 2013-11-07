package org.workin.orm.jpa;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.workin.orm.Page;
import org.workin.utils.orm.PersistenceUtils;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 * 
 */
@SuppressWarnings("unchecked")
public class JpaPersistenceImpl extends JpaDaoSupport implements JpaPersistence {

	@Override
	public <T> boolean contains(T entity) {
		return this.getJpaTemplate().contains(entity);
	}

	@Override
	public <T, PK> T findById(Class<T> entityClass, PK id) {
		return this.getJpaTemplate().find(entityClass, id);
	}

	@Override
	public <T, PK> T getReference(Class<T> entityClass, PK id) {
		return this.getJpaTemplate().getReference(entityClass, id);
	}

	@Override
	public <T> T merge(T entity) {
		return this.getJpaTemplate().merge(entity);
	}

	@Override
	public <T> T persist(T entity) {
		this.getJpaTemplate().persist(entity);
		return entity;
	}

	@Override
	public <T> void refresh(T entity) {
		this.getJpaTemplate().refresh(entity);
	}

	@Override
	public <T, PK> void remove(Class<T> entityClass, PK id) {
		this.getJpaTemplate().remove(this.getReference(entityClass, id));
	}

	@Override
	public <T> void remove(T entity) {
		this.getJpaTemplate().remove(entity);
	}

	@Override
	public <X> List<X> findByNamedQuery(String queryName) {
		return this.getJpaTemplate().findByNamedQuery(queryName);
	}

	@Override
	public <X> List<X> findByNamedQuery(String queryName, Map<String, ?> params) {
		return this.getJpaTemplate().findByNamedQueryAndNamedParams(queryName, params);
	}

	@Override
	public <X> List<X> findByNamedQuery(String queryName, Object... values) {
		return this.getJpaTemplate().findByNamedQuery(queryName, values);
	}

	@Override
	public <X> List<X> findByQuery(String queryString) {
		return this.getJpaTemplate().find(queryString);
	}

	@Override
	public <X> List<X> findByQuery(String queryString, Map<String, ?> params) {
		return this.getJpaTemplate().findByNamedParams(queryString, params);
	}

	@Override
	public <X> List<X> findByQuery(String queryString, Object... values) {
		return this.getJpaTemplate().find(queryString, values);
	}

	@Override
	public void flush() {
		this.getJpaTemplate().flush();
	}

	@Override
	public void batchPersist(final List<?> entityList) {
		// Do nothing if entity list is empty
		if (CollectionUtils.isEmpty(entityList)) {
			logger.debug("Persist list is empty, no entity has been persisted.");
			return;
		}
		this.getJpaTemplate().execute(new JpaCallback<Object>() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				int max = entityList.size();
				for (int i = 0; i < max; i++) {
					em.persist(entityList.get(i));
					if ((i != 0 && i % DEFAULT_BATCH_SIZE == 0) || i == max - 1) {
						em.flush();
					}
				}
				logger.debug("{} entities has been persisted.", max);
				return null;
			}

		});
	}

	@Override
	public void batchMerge(final List<?> entityList) {
		// Do nothing if entity list is empty
		if (CollectionUtils.isEmpty(entityList)) {
			logger.debug("Merge list is empty, no entity has been merged.");
			return;
		}
		this.getJpaTemplate().execute(new JpaCallback<Object>() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				int max = entityList.size();
				for (int i = 0; i < max; i++) {
					em.merge(entityList.get(i));
					if ((i != 0 && i % DEFAULT_BATCH_SIZE == 0) || i == max - 1) {
						em.flush();
					}
				}
				logger.debug("{} entities has been merged.", max);
				return null;
			}

		});
	}

	@Override
	public void batchRemove(final List<?> entityList) {
		// Do nothing if entity list is empty
		if (CollectionUtils.isEmpty(entityList)) {
			logger.debug("Remove list is empty, no entity has been removed.");
			return;
		}
		this.getJpaTemplate().execute(new JpaCallback<Object>() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				int max = entityList.size();
				for (int i = 0; i < max; i++) {
					em.refresh(entityList.get(i));
					em.remove(entityList.get(i));
					if ((i != 0 && i % DEFAULT_BATCH_SIZE == 0) || i == max - 1) {
						em.flush();
					}
				}
				logger.debug("{} entities has been removed.", max);
				return null;
			}

		});
	}

	@Override
	public long countByProperty(final Class<?> entityClass, final String propertyName, final Object value) {
		return (Long) getJpaTemplate().execute(new JpaCallback<Object>() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String queryString = PersistenceUtils.buildQueryString(entityClass, true, propertyName);
				Query queryObject = em.createQuery(queryString);
				prepareQuery(queryObject);
				queryObject.setParameter(1, value);
				return queryObject.getSingleResult();
			}
		});
	}

	@Override
	public long countByProperties(final Class<?> entityClass, final Map<String, ?> params) {
		return (Long) getJpaTemplate().execute(new JpaCallback<Object>() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String queryString = PersistenceUtils.buildQueryString(entityClass, true, params);
				Query queryObject = em.createQuery(queryString);
				prepareQuery(queryObject);
				if (!CollectionUtils.isEmpty(params)) {
					for (Map.Entry<String, ?> entry : params.entrySet()) {
						queryObject.setParameter(entry.getKey(), entry.getValue());
					}
				}
				return queryObject.getSingleResult();
			}
		});
	}

	@Override
	public long countByQuery(final String queryString) {
		return (Long) getJpaTemplate().execute(new JpaCallback<Object>() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String fromHql = queryString;
				// exclude order by, due to select and order by clause would
				// affect count query.
				fromHql = "FROM " + StringUtils.substringAfter(fromHql, "FROM");
				fromHql = StringUtils.substringBefore(fromHql, "ORDER BY");

				String countHql = "SELECT COUNT(*) " + fromHql;
				logger.debug("Persistence automatic build count of Query: {}", countHql);

				Query queryObject = em.createQuery(countHql);
				prepareQuery(queryObject);
				return queryObject.getSingleResult();
			}
		});
	}

	@Override
	public long countByQuery(final String queryString, final Object... values) {
		return (Long) getJpaTemplate().execute(new JpaCallback<Object>() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String fromHql = queryString;
				// exclude order by, due to select and order by clause would
				// affect count query.
				fromHql = "FROM " + StringUtils.substringAfter(fromHql, "FROM");
				fromHql = StringUtils.substringBefore(fromHql, "ORDER BY");

				String countHql = "SELECT COUNT(*) " + fromHql;
				logger.debug("Persistence automatic build count of Query: {}", countHql);

				Query queryObject = em.createQuery(countHql);
				prepareQuery(queryObject);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						queryObject.setParameter(i + 1, values[i]);
					}
				}
				return queryObject.getSingleResult();
			}
		});
	}

	@Override
	public long countByQuery(final String queryString, final Map<String, ?> params) {
		return (Long) getJpaTemplate().execute(new JpaCallback<Object>() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String fromHql = queryString;
				// exclude order by, due to select and order by clause would
				// affect count query.
				fromHql = "FROM " + StringUtils.substringAfter(fromHql, "FROM");
				fromHql = StringUtils.substringBefore(fromHql, "ORDER BY");

				String countHql = "SELECT COUNT(*) " + fromHql;
				logger.debug("Persistence automatic build count of Query: {}", countHql);

				Query queryObject = em.createQuery(countHql);
				prepareQuery(queryObject);
				if (!CollectionUtils.isEmpty(params)) {
					for (Map.Entry<String, ?> entry : params.entrySet()) {
						queryObject.setParameter(entry.getKey(), entry.getValue());
					}
				}
				return queryObject.getSingleResult();
			}
		});
	}

	@Override
	public <X, T> List<X> findAll(Class<T> entityClass) {
		return this.getJpaTemplate().find(PersistenceUtils.buildQueryString(entityClass, false).toString());
	}

	@Override
	public Object findUniqueByNamedQuery(final String queryName, final Object... values) {
		return this.getJpaTemplate().execute(new JpaCallback<Object>() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query queryObject = em.createNamedQuery(queryName);
				prepareQuery(queryObject);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						queryObject.setParameter(i + 1, values[i]);
					}
				}
				return queryObject.getSingleResult();
			}
		});
	}

	@Override
	public Object findUniqueByNamedQuery(final String queryName, final Map<String, ?> params) {
		return this.getJpaTemplate().execute(new JpaCallback<Object>() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query queryObject = em.createNamedQuery(queryName);
				if (!CollectionUtils.isEmpty(params)) {
					for (Map.Entry<String, ?> entry : params.entrySet()) {
						queryObject.setParameter(entry.getKey(), entry.getValue());
					}
				}
				return queryObject.getSingleResult();
			}
		});
	}

	@Override
	public <T> List<T> findByProperty(final Class<T> entityClass, final String propertyName, final Object value) {
		return this.getJpaTemplate().execute(new JpaCallback<List<T>>() {
			@Override
			public List<T> doInJpa(EntityManager em) throws PersistenceException {
				Query queryObject = em.createQuery(PersistenceUtils.buildQueryString(entityClass, false, propertyName));
				prepareQuery(queryObject);
				queryObject.setParameter(1, value);
				return queryObject.getResultList();
			}
		});
	}

	@Override
	public <T> List<T> findByProperties(final Class<T> entityClass, final Map<String, ?> params) {
		return this.getJpaTemplate().execute(new JpaCallback<List<T>>() {
			@Override
			public List<T> doInJpa(EntityManager em) throws PersistenceException {
				Query queryObject = em.createQuery(PersistenceUtils.buildQueryString(entityClass, false, params));
				prepareQuery(queryObject);
				for (Map.Entry<String, ?> entry : params.entrySet()) {
					queryObject.setParameter(entry.getKey(), entry.getValue());
				}
				return queryObject.getResultList();
			}
		});
	}

	@Override
	public <T> T findUniqueByProperty(final Class<T> entityClass, final String propertyName, final Object value) {
		return this.getJpaTemplate().execute(new JpaCallback<T>() {
			@Override
			public T doInJpa(EntityManager em) throws PersistenceException {
				Query queryObject = em.createQuery(PersistenceUtils.buildQueryString(entityClass, false, propertyName));
				prepareQuery(queryObject);
				queryObject.setParameter(1, value);
				return (T) queryObject.getSingleResult();
			}
		});
	}

	@Override
	public <T> T findUniqueByProperties(final Class<T> entityClass, final Map<String, ?> params) {
		return this.getJpaTemplate().execute(new JpaCallback<T>() {
			@Override
			public T doInJpa(EntityManager em) throws PersistenceException {
				Query queryObject = em.createQuery(PersistenceUtils.buildQueryString(entityClass, false, params));
				prepareQuery(queryObject);
				for (Map.Entry<String, ?> entry : params.entrySet()) {
					queryObject.setParameter(entry.getKey(), entry.getValue());
				}
				return (T) queryObject.getSingleResult();
			}
		});
	}

	@Override
	public <T> Page<T> findPageByQuery(final Page<T> page, final String queryString, final Object... values) {
		Assert.notNull(page, "page could not be null.");

		return this.getJpaTemplate().execute(new JpaCallback<Page<T>>() {
			@Override
			public Page<T> doInJpa(EntityManager em) throws PersistenceException {
				Query queryObject = em.createQuery(queryString);
				prepareQuery(queryObject);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						queryObject.setParameter(i + 1, values[i]);
					}
				}
				if (page.isAutoCount()) {
					long totalCount = countByQuery(queryString, values);
					page.setTotalCount(totalCount);
				}
				setPageParameterToQuery(queryObject, page);
				page.setResult(queryObject.getResultList());
				return page;
			}
		});
	}

	@Override
	public <T> Page<T> findPageByQuery(final Page<T> page, final String queryString, final Map<String, ?> params) {
		Assert.notNull(page, "page could not be null.");

		return this.getJpaTemplate().execute(new JpaCallback<Page<T>>() {
			@Override
			public Page<T> doInJpa(EntityManager em) throws PersistenceException {
				Query queryObject = em.createQuery(queryString);
				prepareQuery(queryObject);
				for (Map.Entry<String, ?> entry : params.entrySet()) {
					queryObject.setParameter(entry.getKey(), entry.getValue());
				}
				if (page.isAutoCount()) {
					long totalCount = countByQuery(queryString, params);
					page.setTotalCount(totalCount);
				}
				setPageParameterToQuery(queryObject, page);
				page.setResult(queryObject.getResultList());
				return page;
			}
		});
	}

	@Override
	public <T> Page<T> findPageByNamedQuery(final Page<T> page, final String queryName, final Object... values) {
		Assert.notNull(page, "page could not be null.");

		return this.getJpaTemplate().execute(new JpaCallback<Page<T>>() {
			@Override
			public Page<T> doInJpa(EntityManager em) throws PersistenceException {
				Query queryObject = em.createNamedQuery(queryName);
				prepareQuery(queryObject);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						queryObject.setParameter(i + 1, values[i]);
					}
				}
				// autoCount is not available here
				page.setAutoCount(false);
				/*if (page.isAutoCount()) {
					long totalCount = countByQuery(queryName, values);
					page.setTotalCount(totalCount);
				}*/
				setPageParameterToQuery(queryObject, page);
				page.setResult(queryObject.getResultList());
				return page;
			}
		});
	}

	@Override
	public <T> Page<T> findPageByNamedQuery(final Page<T> page, final String queryName, final Map<String, ?> params) {
		Assert.notNull(page, "page could not be null.");

		return this.getJpaTemplate().execute(new JpaCallback<Page<T>>() {
			@Override
			public Page<T> doInJpa(EntityManager em) throws PersistenceException {
				Query queryObject = em.createNamedQuery(queryName);
				prepareQuery(queryObject);
				for (Map.Entry<String, ?> entry : params.entrySet()) {
					queryObject.setParameter(entry.getKey(), entry.getValue());
				}
				// autoCount is not available here
				page.setAutoCount(false);
				/*if (page.isAutoCount()) {
					long totalCount = countByQuery(queryName, params);
					page.setTotalCount(totalCount);
				}*/
				setPageParameterToQuery(queryObject, page);
				page.setResult(queryObject.getResultList());
				return page;
			}
		});
	}

	@Override
	public <T> Page<T> findPageByProperty(final Page<T> page, final Class<T> entityClass, final String propertyName,
			final Object value) {
		Assert.notNull(page, "page could not be null.");

		return this.getJpaTemplate().execute(new JpaCallback<Page<T>>() {
			@Override
			public Page<T> doInJpa(EntityManager em) throws PersistenceException {
				Query queryObject = em.createQuery(PersistenceUtils.buildQueryString(entityClass, false, propertyName));
				prepareQuery(queryObject);
				queryObject.setParameter(1, value);
				if (page.isAutoCount()) {
					long totalCount = countByProperty(entityClass, propertyName, value);
					page.setTotalCount(totalCount);
				}
				setPageParameterToQuery(queryObject, page);
				page.setResult(queryObject.getResultList());
				return page;
			}
		});
	}

	@Override
	public <T> Page<T> findPageByProperties(final Page<T> page, final Class<T> entityClass, final Map<String, ?> params) {
		Assert.notNull(page, "page could not be null.");

		return this.getJpaTemplate().execute(new JpaCallback<Page<T>>() {
			@Override
			public Page<T> doInJpa(EntityManager em) throws PersistenceException {
				Query queryObject = em.createQuery(PersistenceUtils.buildQueryString(entityClass, false, params));
				prepareQuery(queryObject);
				for (Map.Entry<String, ?> entry : params.entrySet()) {
					queryObject.setParameter(entry.getKey(), entry.getValue());
				}
				if (page.isAutoCount()) {
					long totalCount = countByProperties(entityClass, params);
					page.setTotalCount(totalCount);
				}
				setPageParameterToQuery(queryObject, page);
				page.setResult(queryObject.getResultList());
				return page;
			}
		});
	}

	protected void prepareQuery(Query query) {
		getJpaTemplate().prepareQuery(query);
	}

	protected <T> Query setPageParameterToQuery(final Query query, final Page<T> page) {

		Assert.isTrue(page.getPageSize() > 0, "Page Size must be larger than zero");

		//firstResult will start with 0 in hibernate.
		query.setFirstResult(page.getFirst() - 1);
		query.setMaxResults(page.getPageSize());
		return query;
	}

	public static final transient Logger logger = LoggerFactory.getLogger(JpaPersistenceImpl.class);

	// Define batch objects record size.
	public static final int DEFAULT_BATCH_SIZE = 1000;

}
