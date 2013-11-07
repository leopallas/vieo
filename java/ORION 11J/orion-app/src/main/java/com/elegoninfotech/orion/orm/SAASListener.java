/**
 * 
 */
package com.elegoninfotech.orion.orm;

import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;

import com.elegoninfotech.orion.orm.domain.SAASEntity;
import com.elegoninfotech.orion.orm.domain.entity.JmTenant;
import com.elegoninfotech.orion.saas.SAASSupport;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
public class SAASListener extends AuditListener {

	private static final long serialVersionUID = -3355068354131967331L;
	@Autowired
	private SAASSupport sAASSupport;

	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
		Object object = event.getObject();
		super.onSaveOrUpdate(event);
		if (object instanceof SAASEntity) {
			SAASEntity entity = (SAASEntity) object;

			JmTenant tenant = sAASSupport.getUserTenant();
			entity.setTenantCode(tenant.getTenantCode());
			entity.setTenantName(tenant.getTenantName());
			logger.info("Add SAAS support on {}(tenantCode:{}, tenantName:{}) modified a(n) {}(ID:{}) at {}.",
					new Object[] { event.getEntityName(), entity.getTenantCode(), entity.getTenantName() });
		}
	}

}
