package com.elegoninfotech.orion.filter;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.workin.queue.QueuesHolder;
import org.workin.web.filter.LoggerMDCFilter;

import com.elegoninfotech.orion.orm.domain.entity.ActivityLog;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
public class LoggerDBFilter extends LoggerMDCFilter {
	@Autowired
	private StoreLogConsumer consumer;
	public static final String LOGGER_DB_QUEUENAME = "stroeLog";

	public void addActivityToQueue(ActivityLog log) {
		BlockingQueue queue = QueuesHolder.getQueue(LOGGER_DB_QUEUENAME);
		queue.offer(log);
		consumer.setQueueName(LOGGER_DB_QUEUENAME);
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		ActivityLog activityLog = new ActivityLog();
		activityLog.setRemoteAddr(getRemoteIpAddress(request));
		activityLog.setRequestTime(new Date());
		activityLog.setQueryString(request.getQueryString());
		activityLog.setRequestURI(request.getRequestURI());
		activityLog.setOperateUser("demoUser");

		super.doFilterInternal(request, response, chain);

		activityLog.setResponseTime(new Date());
		addActivityToQueue(activityLog);
	}

}
