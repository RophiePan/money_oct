package com.hl.money.enums;

public enum SubBranch {
	LEFT(1), RIGHT(2);
	private int value;

	private SubBranch(int value) {
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
