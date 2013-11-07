/**
 * 
 */
package com.elegoninfotech.orion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
@Controller
public class HelloWorldController {

	@RequestMapping("/hello")
	public ModelAndView helloWorld() {
		String message = "Hello World, Spring 3.0!";
		return new ModelAndView("hello", "message", message);
	}
}
