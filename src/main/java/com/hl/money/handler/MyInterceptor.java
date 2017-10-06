package com.hl.money.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.devtools.remote.server.Handler;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor implements HandlerInterceptor, Handler {

	@Override
	public void handle(ServerHttpRequest arg0, ServerHttpResponse arg1) throws IOException {

	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String url = request.getRequestURL().toString();
		if (url.contains("user/toRegister") || url.contains("user/checkUser") || (url.contains("login"))) {
			return true;
		}
		if (url.endsWith("/")) {
			return true;
		}
		String username = (String) request.getSession().getAttribute("name");
		if ("".equals(username) || null == username) {
			response.sendRedirect("/");
			return false;
		}
		return true;
	}

}
