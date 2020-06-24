package com.icat.common;

/**
 * author:icat blog:https://blog.techauch.com 返回结果集
 */
public class R<T> {

	private String message;
	private int retCode;
	private T data;

	private R(T data) {
		this.retCode = 0;
		this.message = "OK";
		this.data = data;
	}

	private R(int retCode, String message) {
		this.retCode = retCode;
		this.message = message;
	}

	/**
	 * 成功时候的调用
	 * 
	 */
	public static <T> R<T> success(T data) {
		return new R<T>(data);
	}

	/**
	 * 成功，不需要传入参数
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T> R<T> success() {
		return (R<T>) success("it's ok");
	}

	/**
	 * 失败时候的调用
	 * 
	 */
	public static <T> R<T> error(int retCode, String message) {
		return new R<T>(retCode, message);
	}

	/**
	 * 失败时候的调用,扩展消息参数
	 * 
	 */
	public static <T> R<T> error(int retCode, String message, String msg) {
		message = (message + "--" + msg);
		return new R<T>(retCode, message);
	}

	public T getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

	public int getRetCode() {
		return retCode;
	}

}
