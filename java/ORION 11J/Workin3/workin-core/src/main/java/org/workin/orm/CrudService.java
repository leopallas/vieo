/**
 * 
 */
package org.workin.orm;


/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
public interface CrudService {

	public <T> T merge(final T entity);

	public <T> T save(final T entity);

	public <T, PK> void remove(final Class<T> entityClass, PK id);

	public <T> void remove(final T entity);

	public <T, PK> T findById(final Class<T> entityClass, PK id);

}
