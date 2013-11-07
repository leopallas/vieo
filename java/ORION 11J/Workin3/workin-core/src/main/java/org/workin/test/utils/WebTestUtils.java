package org.workin.test.utils;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.opensymphony.xwork2.ActionContext;

/**
 * Web integrate test tools.
 * 		1. Initialize Spring ApplicationContext into ServletContext
 * 		2. Add MockRequest/MockResponse to ServletActionContext of Struts2
 * 
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
public class WebTestUtils {

	/**
	 * Initialize Spring ApplicationContext into ServletContext
	 * @param servletContext
	 * @param configLocations applicationContext files path.
	 */
	public static void initWebApplicationContext(MockServletContext servletContext, String... configLocations) {
		String configLocationsString = StringUtils.join(configLocations, ",");
		servletContext.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM, configLocationsString);
		new ContextLoader().initWebApplicationContext(servletContext);
	}

	/**
	 * Initialize Spring ApplicationContext into ServletContext
	 * @param servletContext
	 * @param applicationContext Existing ApplicationContext.
	 */
	public static void initWebApplicationContext(MockServletContext servletContext,
			ApplicationContext applicationContext) {
		ConfigurableWebApplicationContext wac = new XmlWebApplicationContext();
		wac.setParent(applicationContext);
		wac.setServletContext(servletContext);
		wac.setConfigLocation("");
		servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, wac);
		wac.refresh();
	}

	/**
	 * Close Spring ApplicationContext from ServletContext.
	 * @param servletContext
	 */
	public static void closeWebApplicationContext(MockServletContext servletContext) {
		new ContextLoader().closeWebApplicationContext(servletContext);
	}

	/**
	 * Set request to ServletActionContext of Struts2, then the test source of struts2 could support ServletActionContext.getRequest() to get MockRequest.
	 * @param request
	 */
	public static void setRequestToStruts2(HttpServletRequest request) {
		initStruts2ActionContext();
		ServletActionContext.setRequest(request);
	}

	/**
	 * Set response to ServletActionContext of Struts2, then the test source of struts2 could support ServletActionContext.getResponse() to get MockResponse.
	 * 
	 * @param response
	 */
	public static void setResponseToStruts2(HttpServletResponse response) {
		initStruts2ActionContext();
		ServletActionContext.setResponse(response);
	}

	/**
	 * Initialize Struts2 ActionContext if it is not initialized.
	 */
	private static void initStruts2ActionContext() {
		if (ActionContext.getContext() == null) {
			ActionContext.setContext(new ActionContext(new HashMap<String, Object>()));
		}
	}
}
