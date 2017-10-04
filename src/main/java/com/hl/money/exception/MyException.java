package com.hl.money.exception;

public class MyException extends RuntimeException {
	private static final long serialVersionUID = -3854489962316312793L;

	private Integer code;

	private String message;

	public MyException() {
		super();
	}

	public MyException(final Integer code) {
		this.code = code;
	}

	public MyException(String message) {
		super();
		this.message = message;
	}

	public MyException(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return this.code;
	}

	public void setCode(final Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
