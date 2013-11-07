/**
 * 
 */
package com.elegoninfotech.orion.test.service;

import java.util.List;

import org.workin.orm.CrudService;

import com.elegoninfotech.orion.orm.domain.entity.Contact;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
public interface ContactService extends CrudService {
	public void addContact(Contact contact);

	public List<Contact> listContact();

	public void removeContact(Integer id);
}
