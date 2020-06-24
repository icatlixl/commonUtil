package com.icat.common.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作类 author:icat blog:https://blog.techauch.com
 *
 */
public class StrKit {
	public static final String ASC = "asc";// 正序
	public static final String DESC = "desc";// 倒序

	/**
	 * 首字母变小写
	 * 
	 */
	public static String firstCharToLowerCase(String str) {
		char firstChar = str.charAt(0);
		if (firstChar >= 'A' && firstChar <= 'Z') {
			char[] arr = str.toCharArray();
			arr[0] += ('a' - 'A');
			return new String(arr);
		}
		return str;
	}

	/**
	 * 是否可转化为数字
	 * 
	 */
	public static boolean isNum(Object o) {
		try {
			new BigDecimal(o.toString());
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 首字母变大写
	 * 
	 */
	public static String firstCharToUpperCase(String str) {
		char firstChar = str.charAt(0);
		if (firstChar >= 'a' && firstChar <= 'z') {
			char[] arr = str.toCharArray();
			arr[0] -= ('a' - 'A');
			return new String(arr);
		}
		return str;
	}

	/**
	 * 字符串为 null 或者为 "" 时返回 true
	 */
	public static boolean isBlank(String str) {
		return str == null || "".equals(str.trim()) ? true : false;
	}

	/**
	 * 字符串不为 null 而且不为 "" 时返回 true
	 */
	public static boolean notBlank(String str) {
		return str == null || "".equals(str.trim()) ? false : true;
	}

	/**
	 * 判断字符串是否为空 字符串不为 null 而且不为 "" 时返回 true
	 */
	public static boolean notBlank(String... strings) {
		if (strings == null)
			return false;
		for (String str : strings)
			if (str == null || "".equals(str.trim()))
				return false;
		return true;
	}

	/**
	 * 判断对象不为空
	 */
	public static boolean notNull(Object... paras) {
		if (paras == null)
			return false;
		for (Object obj : paras)
			if (obj == null)
				return false;
		return true;
	}

	/**
	 * 按字节截取字符串
	 */
	public static String subStringByByte(Object o, int len) {
		if (o == null) {
			return "";
		}
		String str = o.toString();
		String result = null;
		if (str != null) {
			byte[] a = str.getBytes();
			if (a.length <= len) {
				result = str;
			} else if (len > 0) {
				result = new String(a, 0, len);
				int length = result.length();
				if (str.charAt(length - 1) != result.charAt(length - 1)) {
					if (length < 2) {
						result = null;
					} else {
						result = result.substring(0, length - 1);
					}
				}
			}
		}
		return result;
	}

	/**
	 * 逗号表达式_规范
	 */
	public static String commaRect(String commaexpress) {
		commaexpress = FilterNull(commaexpress);
		String[] strlist = commaexpress.split(",");
		StringBuffer result = new StringBuffer();
		for (String str : strlist) {
			if (!("".equals(str.trim())) && !("," + result.toString() + ",").contains("," + str + ",")
					&& !"null".equals(str)) {
				result.append(str.trim() + ",");
			}
		}
		return result.toString().substring(0, (result.length() - 1 > 0) ? result.length() - 1 : 0);
	}

	/**
	 * 逗号表达式_添加
	 */
	public static String commaAdd(String commaexpress, String newelement) {
		return commaRect(FilterNull(commaexpress) + "," + FilterNull(newelement));
	}

	/**
	 * 过滤空NULL
	 */
	public static String FilterNull(Object o) {
		return o != null && !"null".equals(o.toString()) ? o.toString().trim() : "";
	}

	/**
	 * 逗号表达式_删除
	 */
	public static String commaDel(String commaexpress, String delelement) {
		if ((commaexpress == null) || (delelement == null) || (commaexpress.trim().equals(delelement.trim()))) {
			return "";
		}
		String[] deletelist = delelement.split(",");
		String result = commaexpress;
		for (String delstr : deletelist) {
			result = commaDelone(result, delstr);
		}
		return result;
	}

	/**
	 * 逗号表达式_单一删除
	 */
	public static String commaDelone(String commaexpress, String delelement) {
		if ((commaexpress == null) || (delelement == null) || (commaexpress.trim().equals(delelement.trim()))) {
			return "";
		}
		String[] strlist = commaexpress.split(",");
		StringBuffer result = new StringBuffer();
		for (String str : strlist) {
			if ((!str.trim().equals(delelement.trim())) && (!"".equals(str.trim()))) {
				result.append(str.trim() + ",");
			}
		}
		return result.toString().substring(0, result.length() - 1 > 0 ? result.length() - 1 : 0);
	}

	/**
	 * 字符串标签过滤
	 */
	public static String delHTMLTag(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签
		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签
		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签
		return htmlStr.trim(); // 返回文本字符串
	}

	/**
	 * 过滤特殊符号
	 */
	public static String regex(String str) {
		Pattern pattern = Pattern.compile("[0-9-:/ ]");
		Matcher matcher;
		char[] array = str.toCharArray();
		for (int i = 0; i < array.length; i++) {
			matcher = pattern.matcher(String.valueOf(array[i]));
			if (!matcher.matches()) {
				str = str.replace(String.valueOf(array[i]), "");
			}
		}
		return str;
	}

	/**
	 * List<Map>（根据字段排序）
	 */
	public static List<Map<String, Object>> sortByField(List<Map<String, Object>> list, String field, String sortType) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = 0; j < list.size() - 1 - i; j++) {
				Integer temp = Integer.valueOf(list.get(j).get(field).toString());
				Integer temp1 = Integer.valueOf(list.get(j + 1).get(field).toString());
				if (sortType.equals(ASC) ? temp > temp1 : temp < temp1) {
					Map<String, Object> map = list.get(j);
					list.set(j, list.get(j + 1));
					list.set(j + 1, map);
				}
			}
		}
		return list;
	}

	/**
	 * 排序
	 */
	public static List<Integer> sort(List<Integer> list, String sortType) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = 0; j < list.size() - 1 - i; j++) {
				Integer temp = list.get(j);
				Integer temp1 = list.get(j + 1);
				if (sortType.equals(ASC) ? temp > temp1 : temp < temp1) {
					Integer item = list.get(j);
					list.set(j, list.get(j + 1));
					list.set(j + 1, item);
				}
			}
		}
		return list;
	}
}
