package com.hl.money.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hl.money.entity.Admin;
import com.hl.money.service.Adminservice;

@RestController
@ResponseBody
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private Adminservice adminService;

	@RequestMapping("/login")
	public ModelAndView toAdmin() {
		final ModelAndView mv = new ModelAndView();
		mv.setViewName("admin");
		return mv;
	}

	@RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
	public ModelAndView userLogin(final HttpServletRequest request, final Admin a) {
		final ModelAndView mv = new ModelAndView();
		final Admin admin = this.adminService.getUserByIdPassword(a);
		if (null == admin) {
			mv.setViewName("admin");
			mv.addObject("msg", "管理员用户名密码错误!");
		} else {
			request.getSession().setAttribute("name", admin.getAdminName());
			request.getSession().setAttribute("userType", "admin");
			mv.setViewName("redirect:adminMain");
		}
		return mv;
	}

	@RequestMapping("/adminMain")
	public ModelAndView toAdminMain() {
		final ModelAndView mv = new ModelAndView();
		mv.setViewName("/adminMain");
		return mv;
	}

	@RequestMapping("/logout")
	public ModelAndView logout(final HttpServletRequest request) {
		final ModelAndView mv = new ModelAndView();
		final Enumeration em = request.getSession().getAttributeNames();
		while (em.hasMoreElements()) {
			request.getSession().removeAttribute(em.nextElement().toString());
		}
		mv.setViewName("redirect:/admin/login");
		return mv;
	}
}
