package com.elegoninfotech.orion.orm;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.workin.security.springsecurity.SpringSecurityUtils;

import com.elegoninfotech.orion.orm.domain.AuditableEntity;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
public class AuditListener implements SaveOrUpdateEventListener {

	private static final long serialVersionUID = -3086602821818215918L;
	protected static Logger logger = LoggerFactory.getLogger(AuditListener.class);

	/* 
	 * @see org.hibernate.event.SaveOrUpdateEventListener#onSaveOrUpdate(org.hibernate.event.SaveOrUpdateEvent)
	 */
	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
		Object object = event.getObject();

		//If object is a sub-class of AuditableEntity, add audit informations.
		if (object instanceof AuditableEntity) {
			AuditableEntity entity = (AuditableEntity) object;
			String loginName = SpringSecurityUtils.getCurrentUserName();

			if (StringUtils.hasText(entity.getId())) {
				//modify existing object
				entity.setLastModifyTime(new Date());
				entity.setLastModifyBy(loginName);

				logger.info("User {} modified a(n) {}(ID:{}) at {}.", new Object[] { loginName, event.getEntityName(),
						entity.getId(), new Date() });
			} else {
				//create new object
				entity.setCreateTime(new Date());
				entity.setCreateBy(loginName);

				logger.info("User {} created a new {}(ID:{}) at {}.", new Object[] { loginName, event.getEntityName(),
						entity.getId(), new Date() });
			}
		}
	}

}
