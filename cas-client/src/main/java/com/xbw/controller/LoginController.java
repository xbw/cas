package com.xbw.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@Controller
public class LoginController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public String login(HttpServletRequest request) throws Exception {
		logger.info(new Exception().getStackTrace()[0].getMethodName());

		HttpSession session = request.getSession();
		String sName = (String) session.getAttribute("userName");
		if (sName == null) {
			return "index";
		} else {
			return "success";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, String userName, String password) throws Exception {
		logger.info(new Exception().getStackTrace()[0].getMethodName());
		HttpSession session = request.getSession();
		String sName = (String) session.getAttribute("userName");
		if (sName == null || !userName.equals(sName)) {
			session.setAttribute("userName", userName);
		}
		return "success";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, String userName, String password) throws Exception {
		logger.info(new Exception().getStackTrace()[0].getMethodName());
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}
}
