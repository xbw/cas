package com.xbw.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;

import com.xbw.common.security.Principal;

/**
 * 该过滤器用户从CAS认证服务器中获取登录用户用户名，并填充必要的Session.
 * 
 */
@WebFilter(urlPatterns = { "/*" })
public class CasSetUserFilter implements Filter {
	private static final Logger logger = Logger.getLogger(CasSetUserFilter.class);

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();

		// 如果session中没有用户信息，则填充用户信息
		if (session.getAttribute("userName") == null) {
			String userName = getUserName(request);
			if (userName == null) {
				Subject subject = SecurityUtils.getSubject();
				Principal principal = (Principal) subject.getPrincipal();
				userName = principal.getUserName();
			}
			try {
				// 根据单点登录的账户的用户名，从数据库用户表查找用户信息， 填充到session中
				session.setAttribute("userName", userName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		chain.doFilter(request, response);
	}

	private String getUserName(ServletRequest request) {
		HttpSession session = ((HttpServletRequest) request).getSession();
		String principal = ((HttpServletRequest) request).getRemoteUser();
		logger.info("getRemoteUser>>" + principal);
		String userName = null;
		// 如果session中没有用户信息，则填充用户信息
		if (session.getAttribute("userName") == null) {
			// 从Cas服务器获取登录账户的用户名

			Assertion assertion = AssertionHolder.getAssertion();
			if (assertion == null) {
				return userName;
			}
			userName = assertion.getPrincipal().getName();
			logger.info("AssertionHolder.getAssertion()>>session id>>" + session.getId() + ", userName>>" + userName);
			assertion = session != null ? (Assertion) session.getAttribute("_const_cas_assertion_") : null;
			if (assertion == null) {
				return userName;
			}
			userName = assertion.getPrincipal().getName();
			logger.info("session.getAttribute(\"_const_cas_assertion_\")>>session id>>" + session.getId()
					+ ", userName>>" + userName);
		}
		return userName;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}
}