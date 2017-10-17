package com.hl.money.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hl.money.entity.Admin;
import com.hl.money.entity.User;
import com.hl.money.enums.UserStatus;
import com.hl.money.service.Adminservice;
import com.hl.money.service.UserService;
import com.hl.money.utils.PageUtil;

@RestController
@ResponseBody
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private Adminservice adminService;

	@Autowired
	private UserService userService;

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

	@RequestMapping("/audit")
	public ModelAndView auditUser(final HttpServletRequest request) {
		final ModelAndView mv = new ModelAndView();

		final String currentPageStr = request.getParameter("page");

		int currentPage = 1;
		final int perpage = 10;
		if (!StringUtils.isEmpty(currentPageStr)) {
			currentPage = Integer.parseInt(currentPageStr);
		}
		// 分页请求数据URL地址
		final String url = "/admin/audit?";
		// 取得分页工具条
		final Page<User> users = this.userService.getUsersByUserStatus(UserStatus.UNAUDIT.getValue(), currentPage,
				perpage);
		final Long total = users.getTotalElements();
		final String pageHtml = PageUtil.getBackPageHtml(currentPage, perpage, total.intValue(), url);

		mv.setViewName("/adminAudit");
		mv.addObject("users", users);
		mv.addObject("pageHtml", pageHtml);
		return mv;
	}

	@RequestMapping("/deleteUser")
	public ModelAndView deleteUser(final HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:../audit");
		final String userId = request.getParameter("userId");
		this.userService.deleteUserById(Integer.valueOf(userId));
		return mv;
	}

	@RequestMapping("/auditUserPass")
	public ModelAndView auditUserPass(final HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String userId = request.getParameter("userId");
		this.adminService.auditUserPass(Integer.valueOf(userId));
		mv.setViewName("redirect:/admin/audit");
		return mv;
	}

	@RequestMapping("/logout")
	public ModelAndView logout(final HttpServletRequest request) {
		final ModelAndView mv = new ModelAndView();
		final Enumeration<String> em = request.getSession().getAttributeNames();
		while (em.hasMoreElements()) {
			request.getSession().removeAttribute(em.nextElement().toString());
		}
		mv.setViewName("redirect:/admin/login");
		return mv;
	}

}
