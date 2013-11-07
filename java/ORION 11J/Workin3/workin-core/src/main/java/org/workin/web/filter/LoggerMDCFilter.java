package org.workin.web.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.workin.log.LogTraceUtils;
import org.workin.security.springsecurity.SpringSecurityUtils;

/**
 * 
 * 存放在MDC中的数据，log4j可以直接引用并作为日志信息打印出来.
 * 
 * <pre>
 * 示例使用:
 * log4j.appender.stdout.layout.conversionPattern=%d [%X{loginUserId}/%X{req.remoteAddr}/%X{traceId} - %X{req.requestURI}?%X{req.queryString}] %-5p %c{2} - %m%n
 * </pre>
 * 
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
public class LoggerMDCFilter extends OncePerRequestFilter {

	public String getUserName() {
		return "";
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		try {
			MDC.put("loginUserId", getUserName());
			MDC.put("req.requestURI", request.getRequestURI());
			MDC.put("req.queryString", request.getQueryString());
			MDC.put("req.requestURIWithQueryString", request.getRequestURI()
					+ (request.getQueryString() == null ? "" : "?" + request.getQueryString()));
			MDC.put("req.remoteAddr", getRemoteIpAddress(request));

			//为每一个请求创建一个traceId，方便查找日志时可以根据ID查找出一个HTTP请求所有相关日志
			// LogTraceUtils完成的功能是: MDC.put("traceId",StringUtils.remove(UUID.randomUUID().toString(),"-"))
			LogTraceUtils.beginTrace();
			chain.doFilter(request, response);
		} finally {
			clearMDC();
		}
	}

	/**
	 * 
	 * If you need get IP address form other way,u need Override this method only.
	 * 
	 * Get remote IP address
	 * 
	 * @param request
	 * @return
	 * 
	 */
	public String getRemoteIpAddress(final HttpServletRequest request) {
		String remoteIp = SpringSecurityUtils.getCurrentUserIp();

		if (StringUtils.hasText(remoteIp)) {
			return remoteIp;
		}

		remoteIp = request.getHeader("x-forwarded-for");

		if (!StringUtils.hasText(remoteIp) || "unknown".equalsIgnoreCase(remoteIp)) {
			remoteIp = request.getHeader("Proxy-Client-IP");
		}
		if (!StringUtils.hasText(remoteIp) || "unknown".equalsIgnoreCase(remoteIp)) {
			remoteIp = request.getHeader("WL-Proxy-Client-IP");
		}
		if (!StringUtils.hasText(remoteIp) || "unknown".equalsIgnoreCase(remoteIp)) {
			remoteIp = request.getRemoteAddr();
		}
		return remoteIp;
	}

	private static void clearMDC() {
		Map map = MDC.getContext();
		if (map != null) {
			map.clear();
		}
	}

}