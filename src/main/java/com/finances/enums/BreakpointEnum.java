package com.finances.enums;

public enum BreakpointEnum {

	MONTHLY(1), WEEKLY(2);

	private int id;

	BreakpointEnum(int id) {
		setId(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
