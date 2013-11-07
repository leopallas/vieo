/**
 * 
 */
package com.elegoninfotech.orion.test.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.workin.orm.AbstractBeanService;

import com.elegoninfotech.orion.orm.domain.entity.Contact;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
@Transactional
@Service(value = "contactService")
public class ContactServiceImpl extends AbstractBeanService implements ContactService {

	@Transactional
	@Override
	public void addContact(Contact contact) {
		super.getPersistenceProvider().merge(contact);
	}

	@Override
	public List<Contact> listContact() {
		return super.getPersistenceProvider().findAll(Contact.class);
	}

	@Transactional
	@Override
	public void removeContact(Integer id) {
		super.getPersistenceProvider().remove(Contact.class, id);
	}

}
