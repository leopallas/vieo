/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: SpringSecurityUtils.java 1185 2010-08-29 15:56:19Z calvinxiu $
 */
package org.workin.security.springsecurity;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * SpringSecurity tools which only support SpringSecurity 3.0.x.
 * 
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
public class SpringSecurityUtils {
	/**
	 * Return current user as User.class or child class of User.class for SpringSecurity, default it'll return null if user is not login.
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends User> T getCurrentUser() {
		Authentication authentication = getAuthentication();

		if (authentication == null) {
			return null;
		}

		Object principal = authentication.getPrincipal();
		if (!(principal instanceof User)) {
			return null;
		}

		return (T) principal;
	}

	/**
	 * Get user name for current login user, default return empty if user is not login.
	 * @return
	 */
	public static String getCurrentUserName() {
		Authentication authentication = getAuthentication();

		if (authentication == null || authentication.getPrincipal() == null) {
			return "";
		}

		return authentication.getName();
	}

	/**
	 * Return login IP for current login user, default return empty if user is not login.
	 * @return
	 */
	public static String getCurrentUserIp() {
		Authentication authentication = getAuthentication();

		if (authentication == null) {
			return "";
		}

		Object details = authentication.getDetails();
		if (!(details instanceof WebAuthenticationDetails)) {
			return "";
		}

		WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
		return webDetails.getRemoteAddress();
	}

	/**
	 * Check if user has any of the roles, then return true if any one matched.
	 * @param roles
	 * @return
	 */
	public static boolean hasAnyRole(String... roles) {
		Authentication authentication = getAuthentication();

		if (authentication == null) {
			return false;
		}

		Collection<? extends GrantedAuthority> grantedAuthorityList = authentication.getAuthorities();
		for (String role : roles) {
			for (GrantedAuthority authority : grantedAuthorityList) {
				if (role.equals(authority.getAuthority())) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Save UserDetails to Security Context.
	 * @param userDetails user informations initialized.
	 * @param request used for retrieving user IP address, which can be set as null.
	 */
	public static void saveUserDetailsToContext(UserDetails userDetails, HttpServletRequest request) {
		PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(userDetails,
				userDetails.getPassword(), userDetails.getAuthorities());

		if (request != null) {
			authentication.setDetails(new WebAuthenticationDetails(request));
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	/**
	 * Get Authentication, and default return null if SecurityContext is empty.
	 * @return
	 */
	private static Authentication getAuthentication() {
		SecurityContext context = SecurityContextHolder.getContext();

		if (context == null) {
			return null;
		}

		return context.getAuthentication();
	}
}
