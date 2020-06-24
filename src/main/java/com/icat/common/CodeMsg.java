package com.icat.common;

/**
 * author:icat  
 * blog:https://blog.techauch.com
 * 操作结果消息类
 */
public class CodeMsg {
	
	private int retCode;
	private String message;
	public static CodeMsg SUCCESS = new CodeMsg(0, "操作成功");
	public static CodeMsg ERROR = new CodeMsg(-1, "操作失败");
	public static CodeMsg ERROR_ADD = new CodeMsg(1, "添加失败");
	public static CodeMsg ERROR_DEL = new CodeMsg(2, "删除失败");
	public static CodeMsg ERROR_UPDATE = new CodeMsg(3, "修改失败");

	public static CodeMsg SERVER_EXCEPTION = new CodeMsg(500, "服务端异常");
	public static CodeMsg PARAMETER_ISNULL = new CodeMsg(501, "参数非法");

	public CodeMsg(int retCode, String message) {
		this.retCode = retCode;
		this.message = message;
	}

	public int getRetCode() {
		return retCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
