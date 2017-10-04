package com.hl.money.entity;

public class Result<T> {

	/** 错误码. */
	private Integer code;

	/** 提示信息. */
	private String msg;

	/** 具体的内容. */
	private T data;

	public Integer getCode() {
		return this.code;
	}

	public void setCode(final Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(final String msg) {
		this.msg = msg;
	}

	public T getData() {
		return this.data;
	}

	public void setData(final T data) {
		this.data = data;
	}
}
