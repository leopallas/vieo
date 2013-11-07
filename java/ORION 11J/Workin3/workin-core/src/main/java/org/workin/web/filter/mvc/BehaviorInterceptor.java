/**
 * 
 */
package org.workin.web.filter.mvc;

import java.util.Collection;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.collect.Sets;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
public class BehaviorInterceptor extends HandlerInterceptorAdapter {
	private transient static final Logger logger = LoggerFactory.getLogger(BehaviorInterceptor.class);

	// User define this parameter, for ignore request URI in this intercept action.
	private Set<Pattern> ignoreRequestURIs = Sets.newHashSet();

	// User define this parameter, for allowed request URI in this intercept action.
	private Set<Pattern> allowedRequestURIs = Sets.newHashSet();

	public Set<Pattern> getIgnoreRequestURIs() {
		return ignoreRequestURIs;
	}

	public void setIgnoreRequestURIs(Set<Pattern> ignoreRequestURIs) {
		this.ignoreRequestURIs = ignoreRequestURIs;
	}

	public Set<Pattern> getAllowedRequestURIs() {
		return allowedRequestURIs;
	}

	public void setAllowedRequestURIs(Set<Pattern> allowedRequestURIs) {
		this.allowedRequestURIs = allowedRequestURIs;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException {

		String requestUri = request.getRequestURI();

		if (!ignoreRequestURIs.isEmpty() && !allowedRequestURIs.isEmpty()) {
			throw new IllegalStateException(
					"'ignoreRequestURIs' and 'allowedRequestURIs' could not be both configured.");
		}

		if (!matchRequestURIs(ignoreRequestURIs, requestUri, false)
				|| matchRequestURIs(allowedRequestURIs, requestUri, true)) {
			logger.info("Traced HTTP request with URI '{}'.", requestUri);
		}
		return true;
	}

	/**
	 * 
	 * Is matched request URIs?.
	 * 
	 * @param pattenCollection
	 * @param requestURI
	 * @param isAllow
	 * 
	 * @return 
	 */
	protected static final boolean matchRequestURIs(Collection<Pattern> pattenCollection, String requestURI,
			Boolean isAllow) {
		if (!StringUtils.hasText(requestURI) || pattenCollection.isEmpty()) {
			return false;
		}
		for (Pattern patten : pattenCollection) {
			if (patten.matcher(requestURI).matches()) {
				return isAllow ? true : false;
			}
		}
		return false;
	}
}
