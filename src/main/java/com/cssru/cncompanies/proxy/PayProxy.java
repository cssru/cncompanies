package com.cssru.cncompanies.proxy;

public class PayProxy {
	private Long userId;
	private Integer payValue;

	public PayProxy() {
		userId = 0L;
		payValue = 0;
	}

	public PayProxy(Long userId, Integer payValue) {
		this.userId = userId;
		this.payValue = payValue;
	}

	// getters
	public Long getUserId() {
		return userId;
	}

	public Integer getPayValue() {
		return payValue;
	}

	// setters
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setPayValue(Integer payValue) {
		this.payValue = payValue;
	}
}
