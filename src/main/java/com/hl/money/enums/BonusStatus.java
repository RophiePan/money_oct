package com.hl.money.enums;

public enum BonusStatus {
	UNDELIVER(0), DELIVER(1);
	private int value;

	private BonusStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
