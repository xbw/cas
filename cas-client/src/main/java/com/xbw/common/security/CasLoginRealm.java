package com.xbw.common.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasAuthenticationException;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CasLoginRealm extends CasRealm {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		CasToken casToken = (CasToken) token;
		if (token == null) {
			return null;
		}

		// 获取ticket
		String ticket = (String) casToken.getCredentials();
		if (!org.apache.shiro.util.StringUtils.hasText(ticket)) {
			return null;
		}
		System.out.println("ticket>>" + ticket);
		TicketValidator ticketValidator = ensureTicketValidator();
		try {
			// 回传ticket到服务端验证，验证通过就进入下一行，可以获取登录后的相关信息，否则直接抛异常，即验证不通过
			Assertion casAssertion = ticketValidator.validate(ticket, getCasService());
			AttributePrincipal casPrincipal = casAssertion.getPrincipal();
			String userName = casPrincipal.getName();
			if (userName != null) {
				Principal p = new Principal("1", userName, userName);
				PrincipalCollection principalCollection = new SimplePrincipalCollection(p, getName());
				return new SimpleAuthenticationInfo(principalCollection, ticket);
			} else {
				return null;
			}
		} catch (TicketValidationException e) {
			logger.error("票据认证失败", e);
			throw new CasAuthenticationException("Unable to validate ticket [" + ticket + "]", e);
		}
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) getAvailablePrincipal(principals);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 添加用户权限
		info.addStringPermission("user");
		return info;
	}

}