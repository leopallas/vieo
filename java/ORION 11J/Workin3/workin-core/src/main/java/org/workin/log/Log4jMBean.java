package org.workin.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * Dynamically configure Logger level based on JMX.
 * 
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
@ManagedResource(objectName = Log4jMBean.LOG4J_MBEAN_NAME, description = "Log4j Management Bean")
public class Log4jMBean {

	/**
	 * Registered name of Log4jMbean
	 */
	public static final String LOG4J_MBEAN_NAME = "Log4j:name=log4j";

	private static org.slf4j.Logger mbeanLogger = LoggerFactory.getLogger(Log4jMBean.class);

	/**
	 * @return Retrieve logging level of the Root Logger 
	 */
	@ManagedAttribute(description = "Logging level of the root logger")
	public String getRootLoggerLevel() {
		Logger root = Logger.getRootLogger();
		return root.getLevel().toString();
	}

	/**
	 * Set logging level of Root Logger. Default set to DEBUG if logging name is error.
	 * @param newLevel
	 */
	@ManagedAttribute(description = "Logging level of the root logger")
	public void setRootLoggerLevel(String newLevel) {
		Logger root = Logger.getRootLogger();
		Level level = Level.toLevel(newLevel);
		root.setLevel(level);
		mbeanLogger.info("Set logging level of Root Logger to {}", newLevel);
	}

	/**
	 * Retrieve Logging level of the Logger
	 * @param loggerName
	 * @return
	 */
	@ManagedOperation(description = "Get logging level of the logger")
	@ManagedOperationParameters({ @ManagedOperationParameter(name = "loggerName", description = "Logger name") })
	public String getLoggerLevel(String loggerName) {
		Logger logger = Logger.getLogger(loggerName);
		return logger.getEffectiveLevel().toString();
	}

	/**
	 * Set logging level of the specific Logger. Default set to DEBUG if logging name is error.
	 * @param loggerName
	 * @param newLevel
	 */
	@ManagedOperation(description = "Set new logging level to the logger")
	@ManagedOperationParameters({ @ManagedOperationParameter(name = "loggerName", description = "Logger name"),
			@ManagedOperationParameter(name = "newlevel", description = "New level") })
	public void setLoggerLevel(String loggerName, String newLevel) {
		Logger logger = Logger.getLogger(loggerName);
		Level level = Level.toLevel(newLevel);
		logger.setLevel(level);
		mbeanLogger.info("Set logging level of {} to {}", loggerName, newLevel);
	}
}
