package com.excel.demo.bean;

public class DemoException extends Exception {
	private static final long serialVersionUID = 1L;

	private String errorCode;
	private String errorMsg;
	public DemoException(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
}
