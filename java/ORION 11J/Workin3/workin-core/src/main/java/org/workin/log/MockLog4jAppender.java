package org.workin.log;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Store Logger appender in a list, which will be used for testing logging output.
 * Before the test, you need to invoke addToLogger method to add the specific appender to the listening logger.
 * 
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
public class MockLog4jAppender extends AppenderSkeleton {

	private List<LoggingEvent> logs = new ArrayList<LoggingEvent>();

	/**
	 * @return Retrieve the first logging event appended.
	 */
	public LoggingEvent getFirstLog() {
		if (logs.isEmpty()) {
			return null;
		}
		return logs.get(0);
	}

	/**
	 * @return Retrieve the last logging event appended.
	 */
	public LoggingEvent getLastLog() {
		if (logs.isEmpty()) {
			return null;
		}
		return logs.get(logs.size() - 1);
	}

	/**
	 * @return Retrieve all the logging events appended.
	 */
	public List<LoggingEvent> getAllLogs() {
		return logs;
	}

	/**
	 * Clear all the logs appended.
	 */
	public void clearLogs() {
		logs.clear();
	}

	/**
	 * Add the specific appender to listening Logger
	 * @param loggerName
	 */
	public void addToLogger(String loggerName) {
		Logger logger = Logger.getLogger(loggerName);
		logger.addAppender(this);
	}

	/**
	 * Add the specific appender to listening Logger
	 * 
	 * @param loggerClass
	 */
	public void addToLogger(Class<?> loggerClass) {
		Logger logger = Logger.getLogger(loggerClass);
		logger.addAppender(this);
	}

	/**
	 * Remove the specific appender from listening Logger
	 * @param loggerName
	 */
	public void removeFromLogger(String loggerName) {
		Logger logger = Logger.getLogger(loggerName);
		logger.removeAppender(this);
	}

	/**
	 * Remove the specific appender from listening Logger
	 * 
	 * @param loggerClass
	 */
	public void removeFromLogger(Class<?> loggerClass) {
		Logger logger = Logger.getLogger(loggerClass);
		logger.removeAppender(this);
	}

	/* 
	 * Implements append method for AppenderSkeleton.class, will add the log event to internal list.
	 * @see org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent)
	 */
	@Override
	protected void append(LoggingEvent event) {
		logs.add(event);
	}

	/**
	 * @see AppenderSkeleton#close()
	 */
	@Override
	public void close() {
	}

	/**
	 * @see AppenderSkeleton#requiresLayout()
	 */
	@Override
	public boolean requiresLayout() {
		return false;
	}
}
