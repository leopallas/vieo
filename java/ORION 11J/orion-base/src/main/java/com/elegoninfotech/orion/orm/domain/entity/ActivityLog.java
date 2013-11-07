/**
 * 
 */
package com.elegoninfotech.orion.orm.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.elegoninfotech.orion.orm.domain.SAASEntity;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
@Entity
@Table(name = "ACTIVITY_LOG")
public class ActivityLog extends SAASEntity {

	private static final long serialVersionUID = 2732867856594860270L;

	private String operateUser;
	private Date requestTime;
	private Date responseTime;
	private String remoteAddr;
	private String requestURI;
	private String queryString;

	@Column(name = "OPERATE_USER")
	public String getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "REQUEST_TIME")
	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "RESPONSE_TIME")
	public Date getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}

	@Column(name = "REMOTE_ADDR")
	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	@Column(name = "REQUEST_URI")
	public String getRequestURI() {
		return requestURI;
	}

	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

	@Column(name = "QUERY_STRING")
	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

}
