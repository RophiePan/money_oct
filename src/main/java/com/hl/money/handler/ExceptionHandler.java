package com.hl.money.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.hl.money.exception.MyException;

@ControllerAdvice
public class ExceptionHandler {
	private final static Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ModelAndView handle(final Exception e) {
		ExceptionHandler.logger.error("System Error", e);
		final ModelAndView mv = new ModelAndView();
		mv.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

		if (e instanceof MyException) {
			MyException myException = (MyException) e;
			mv.addObject("error", myException);
		}
		return mv;
	}
}
