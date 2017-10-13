package com.hl.money.controller;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hl.money.entity.Result;
import com.hl.money.entity.User;
import com.hl.money.service.UserService;

@RestController
@ResponseBody
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

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
	public ModelAndView userLogin(HttpServletRequest request, final User u) {
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
			result.setMsg("用户不存在");
		} else {
			result.setCode(HttpStatus.ACCEPTED.value());
			result.setMsg("用户已存在");
		}
		return result;
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
