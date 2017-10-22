package com.hl.money.controller;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hl.money.domain.Nodes;
import com.hl.money.entity.Bonus;
import com.hl.money.entity.Result;
import com.hl.money.entity.User;
import com.hl.money.service.BonusService;
import com.hl.money.service.UserNodeService;
import com.hl.money.service.UserService;
import com.hl.money.utils.PageUtil;

@RestController
@ResponseBody
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserNodeService userNodeService;

	@Autowired
	private BonusService bonusService;

	@RequestMapping("/toRegister")
	public ModelAndView toRegister() {
		final ModelAndView mv = new ModelAndView();
		mv.setViewName("register");
		return mv;
	}

	@RequestMapping("/toRegisterSuccess/{userId}")
	public ModelAndView toRegisterSuccess(@PathVariable final int userId) {
		final ModelAndView mv = new ModelAndView();
		mv.setViewName("registerSuccess");
		mv.addObject("userId", userId);
		return mv;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView userLogin(final HttpServletRequest request, final User u) {
		final ModelAndView mv = new ModelAndView();
		final User user = this.userService.getUserByIdPassword(u);
		if (null == user) {
			mv.setViewName("index");
			mv.addObject("msg", "用户不存在或密码错误");
		} else {
			mv.setViewName("userMain");
			mv.addObject("user", user);
			request.getSession().setAttribute("name", String.valueOf(user.getUserId()));
			request.getSession().setAttribute("userType", "user");
		}
		return mv;
	}

	@RequestMapping(value = "/checkUser/{userId}/{password}", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> checkUserExist(@PathVariable final int userId, @PathVariable final String password) {
		final Result<String> result = new Result<>();
		final User u = new User();
		u.setUserId(userId);
		u.setPassword(password);
		final User user = this.userService.getUserByIdPassword(u);
		if (null == user) {
			result.setCode(HttpStatus.NO_CONTENT.value());
			result.setMsg("用户或密码错误");
		} else {
			result.setCode(HttpStatus.ACCEPTED.value());
			result.setMsg("登陆成功");
		}
		return result;
	}

	@RequestMapping("/create")
	public ModelAndView createUser(final User user) {

		// 校验此用户是否注册
		final ModelAndView mv = new ModelAndView();
		final List<User> listUserByName = this.userService.getUsersByUserName(user.getUserName());
		if (!CollectionUtils.isEmpty(listUserByName)) {
			mv.addObject("msg", "请人已注册，请换一个姓名，例如张三1，张三2...");
			mv.setViewName("register");
		} else {
			final User u = this.userService.createUser(user);
			mv.setViewName("redirect:toRegisterSuccess/" + u.getUserId() + "");
		}
		return mv;
	}

	@RequestMapping(value = "/checkUser/{recommendId}", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> checkUserId(@PathVariable final String recommendId) {
		final Result<String> result = new Result<>();
		final List<User> userList = this.userService.checkUser(Integer.valueOf(recommendId));
		if (CollectionUtils.isEmpty(userList)) {
			result.setCode(HttpStatus.NO_CONTENT.value());
			result.setMsg("用户不存在或未审核通过");
		} else {
			result.setCode(HttpStatus.ACCEPTED.value());
			result.setMsg("用户已存在");
		}
		return result;
	}

	@RequestMapping("/all")
	public ModelAndView toAllNode() {
		// 校验此用户是否注册
		final ModelAndView mv = new ModelAndView();
		mv.setViewName("/userNodes");
		return mv;
	}

	@RequestMapping("/getAllNodes")
	public Nodes getAllNodes(final HttpServletRequest request) {
		final String userId = String.valueOf(request.getSession().getAttribute("name"));
		final Nodes nodes = this.userNodeService.getNodes(Integer.valueOf(userId));
		return nodes;
	}

	@RequestMapping("/awardhistroy")
	public ModelAndView getAwardHistroy(final HttpServletRequest request) {
		final ModelAndView mv = new ModelAndView();

		final String currentPageStr = request.getParameter("page");
		int currentPage = 1;
		final int perpage = 10;
		if (!StringUtils.isEmpty(currentPageStr)) {
			currentPage = Integer.parseInt(currentPageStr);
		}
		// 分页请求数据URL地址
		final String url = "/user/awardhistroy?";
		final String userId = String.valueOf(request.getSession().getAttribute("name"));

		final Page<Bonus> listBonus = this.bonusService.getBonusById(Integer.valueOf(userId), currentPage, 10);
		final Long total = listBonus.getTotalElements();
		final String pageHtml = PageUtil.getBackPageHtml(currentPage, perpage, total.intValue(), url);
		mv.setViewName("/userAward");
		mv.addObject("bonus", listBonus);
		mv.addObject("pageHtml", pageHtml);

		return mv;
	}

	@RequestMapping("/recommend")
	public ModelAndView getRecommendByUserId(final HttpServletRequest request) {
		final ModelAndView mv = new ModelAndView();

		final String currentPageStr = request.getParameter("page");
		int currentPage = 1;
		final int perpage = 10;
		if (!StringUtils.isEmpty(currentPageStr)) {
			currentPage = Integer.parseInt(currentPageStr);
		}
		// 分页请求数据URL地址
		final String url = "/user/recommend?";
		String userId = String.valueOf(request.getSession().getAttribute("name"));

		final Page<User> listUsers = this.userService.getUsersByRecommendId(Integer.valueOf(userId), currentPage, 10);
		final Long total = listUsers.getTotalElements();
		final String pageHtml = PageUtil.getBackPageHtml(currentPage, perpage, total.intValue(), url);
		mv.setViewName("/userRecommend");
		mv.addObject("users", listUsers);
		mv.addObject("pageHtml", pageHtml);

		return mv;
	}

	@RequestMapping("/logout")
	public ModelAndView logout(final HttpServletRequest request) {
		final ModelAndView mv = new ModelAndView();
		final Enumeration em = request.getSession().getAttributeNames();
		while (em.hasMoreElements()) {
			request.getSession().removeAttribute(em.nextElement().toString());
		}
		mv.setViewName("redirect:/");
		return mv;
	}
}
