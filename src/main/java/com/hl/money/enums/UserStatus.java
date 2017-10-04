package com.hl.money.enums;

public enum UserStatus {
	UNAUDIT(0), AUDITED(1);

	private int value;

	private UserStatus(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
