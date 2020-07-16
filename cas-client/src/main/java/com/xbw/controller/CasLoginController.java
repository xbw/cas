package com.xbw.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xbw.common.security.Principal;

@Controller
public class CasLoginController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequiresPermissions("user")
	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public String login(HttpServletRequest request) throws Exception {
		logger.info(new Exception().getStackTrace()[0].getMethodName() + ">>RequestMethod.GET");
		Subject subject = SecurityUtils.getSubject();
		Principal principal = (Principal) subject.getPrincipal();

		// 如果已经登录，则跳转到管理首页
		if (principal != null) {
			logger.info(
					new Exception().getStackTrace()[0].getMethodName() + ">>>principal>>>" + principal.getUserName());
			HttpSession session = request.getSession();
			session.setAttribute("userName", principal.getUserName());
			return "success";
		} else {
			return "index";
		}
	}
}
