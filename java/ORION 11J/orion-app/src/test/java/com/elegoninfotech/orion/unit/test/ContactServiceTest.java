package com.elegoninfotech.orion.unit.test;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.elegoninfotech.orion.orm.domain.entity.Contact;
import com.elegoninfotech.orion.test.service.ContactService;

@ContextConfiguration("classpath:applicationContext.xml")
public class ContactServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	ContactService contactService;

	@Test
	public void testAddContact() {
		Contact contact = new Contact();
		contact.setEmail("email" + UUID.randomUUID().toString());
		contact.setFirstname("firstName");
		contact.setLastname("lastName");
		contact.setTelephone("telephone");
		contactService.addContact(contact);
		assertNotNull(contactService.listContact());
	}

	@Test
	public void testListContact() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveContact() {
		fail("Not yet implemented");
	}

}
