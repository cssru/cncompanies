package com.cssru.cncompanies.secure;

public class BadLoginException extends Exception {
	public static final int ACCESS_DENIED = 1;
	public static final int LOGIN_LOCKED = 2;
	public static final int LOGIN_EXPIRED = 3;
	public static final int LOGIN_NOT_CONFIRMED = 4;
	
	private static final long serialVersionUID = 1L;
	private int reason;
	public BadLoginException(int reason, String message) {
		super(message);
		this.reason = reason;
	}
	
	public int getReason() {
		return reason;
	}
}
