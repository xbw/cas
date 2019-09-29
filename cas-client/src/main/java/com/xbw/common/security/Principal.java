package com.xbw.common.security;

import java.io.Serializable;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * 授权用户信息
 */
public class Principal implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id; // 编号
	private String loginName; // 登录名
	private String userName; // 姓名

	public Principal() {
		super();
	}

	public Principal(String id, String loginName, String userName) {
		super();
		this.id = id;
		this.loginName = loginName;
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取SESSIONID
	 */
	public String getSessionid() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null) {
				session = subject.getSession();
			}
			if (session != null) {
				return (String) session.getId();
			} else {
				return "";
			}
		} catch (InvalidSessionException e) {
			return "";
		}
	}

	@Override
	public String toString() {
		return id;
	}
}