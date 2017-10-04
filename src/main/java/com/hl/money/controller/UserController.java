package com.hl.money.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView userLogin(final User u) {
		final ModelAndView mv = new ModelAndView();
		final User user = this.userService.getUserByIdPassword(u);
		if (null == user) {
			mv.setViewName("index");
			mv.addObject("msg", "用户不存在或密码错误");
		} else {
			mv.setViewName("userMain");
			mv.addObject("user", user);
		}
		return mv;
	}

	@RequestMapping("/create")
	public ModelAndView createUser(final User user) {
		final ModelAndView mv = new ModelAndView();
		User u = this.userService.createUser(user);
		mv.setViewName("registSuccess");
		mv.addObject("userId", u.getUserId());
		return mv;
	}

	@RequestMapping(value = "/checkUser/{recommendId}", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> checkUserId(@PathVariable final String recommendId) {
		final Result<String> result = new Result<>();
		final int i = this.userService.checkUser(Integer.valueOf(recommendId));
		if (i == 0) {
			result.setCode(HttpStatus.NO_CONTENT.value());
			result.setMsg("用户不存在");
		} else {
			result.setCode(HttpStatus.ACCEPTED.value());
			result.setMsg("用户已存在");
		}
		return result;
	}

}
