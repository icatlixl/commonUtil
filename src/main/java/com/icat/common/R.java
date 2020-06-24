package com.icat.common;

/**
 * author:icat  
 * blog:https://blog.techauch.com
 * 返回结果集
 */
public class R<T> {

	private String message;
	private int retCode;
	private T data;

	private R(T data) {
		this.retCode = 0;
		this.message = CodeMsg.SUCCESS.getMessage();
		this.data = data;
	}

	private R(CodeMsg cm) {
		if (cm == null) {
			return;
		}
		this.retCode = cm.getRetCode();
		this.message = cm.getMessage();
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
		return (R<T>) success("");
	}

	/**
	 * 添加失败
	 */
	public static <T> R<T> errorAdd() {
		return new R<T>(CodeMsg.ERROR_ADD);
	}

	/**
	 * 删除失败
	 */
	public static <T> R<T> errorDel() {
		return new R<T>(CodeMsg.ERROR_DEL);
	}

	/**
	 * 修改失败
	 */
	public static <T> R<T> errorUpdate() {
		return new R<T>(CodeMsg.ERROR_UPDATE);
	}

	/**
	 * 操作失败
	 */
	public static <T> R<T> error() {
		return new R<T>(CodeMsg.ERROR);
	}

	/**
	 * 失败时候的调用
	 * 
	 */
	public static <T> R<T> error(CodeMsg cm) {
		return new R<T>(cm);
	}

	/**
	 * 失败时候的调用,扩展消息参数
	 * 
	 */
	public static <T> R<T> error(CodeMsg cm, String msg) {
		cm.setMessage(cm.getMessage() + "--" + msg);
		return new R<T>(cm);
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
