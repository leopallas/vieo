/**
 * 
 */
package com.elegoninfotech.orion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.elegoninfotech.orion.orm.domain.entity.Contact;
import com.elegoninfotech.orion.test.service.ContactService;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */

@Controller
@RequestMapping("/contact")
public class ContactController {
	@Autowired
	private ContactService contactService;

	private final String LIST_ACTION = "redirect:/contact";

	@RequestMapping
	public String list(ModelMap model) {
		model.put("contact", new Contact());
		model.put("contactList", contactService.listContact());

		return "contact";
	}

	@RequestMapping(value = "/{contactId}", method = RequestMethod.GET)
	public String show(ModelMap model, @PathVariable("contactId") Integer contactId) {
		model.put("contact", contactService.findById(Contact.class, contactId));
		return "/contact-edit";
	}

	@RequestMapping(value = "/new")
	public String _new(@ModelAttribute("contact") Contact contact) {

		contactService.addContact(contact);

		return "/contact-edit";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String add(@ModelAttribute("contact") Contact contact) {

		contactService.addContact(contact);

		return LIST_ACTION;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@ModelAttribute("contact") Contact contact) {
		contactService.addContact(contact);

		return LIST_ACTION;
	}

	@RequestMapping(value = "/{contactId}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("contactId") Integer contactId) {

		contactService.removeContact(contactId);

		return LIST_ACTION;
	}
}
