package com.xbw.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xbw.common.security.Principal;

//@Controller
public class ShiroLoginController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public String login(HttpServletRequest request) throws Exception {
		logger.info(new Exception().getStackTrace()[0].getMethodName() + ">>RequestMethod.GET");
		Subject subject = SecurityUtils.getSubject();
		Principal principal = (Principal) subject.getPrincipal();

		// 如果已经登录，则跳转到管理首页
		if (principal != null) {
			return "success";
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, String userName, String password) throws Exception {
		logger.info(new Exception().getStackTrace()[0].getMethodName() + ">>RequestMethod.POST");
		Subject subject = SecurityUtils.getSubject();
		Principal principal = (Principal) subject.getPrincipal();
		// 如果已经登录，则跳转到管理首页
		if (principal != null) {
			return "success";
		}
		// 登录认证令牌（用户名密码令牌）
		subject.login(new UsernamePasswordToken(userName, password));

		principal = (Principal) subject.getPrincipal();
		logger.info(new Exception().getStackTrace()[0].getMethodName() + ">>>principal>>>" + principal.getUserName());
		HttpSession session = request.getSession();
		session.setAttribute("userName", userName);
		return "success";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, String userName, String password) throws Exception {
		logger.info(new Exception().getStackTrace()[0].getMethodName());
		HttpSession session = request.getSession();
		session.invalidate();
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/";
	}
}
