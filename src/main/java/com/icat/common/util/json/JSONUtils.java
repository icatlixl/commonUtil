package com.icat.common.util.json;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json操作类 author:icat blog:https://blog.techauch.com
 */
public class JSONUtils {
	private static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 对象转map
	 */
	public static Map<String, Object> objectToMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (obj == null)
				return null;
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (key.compareToIgnoreCase("class") == 0) {
					continue;
				}
				Method getter = property.getReadMethod();
				Object value = getter != null ? getter.invoke(obj) : null;
				map.put(key, value);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 对象转json字符串
	 * 
	 */
	public static String objectToJson(Object data) {
		try {
			String string = objectMapper.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T jsonToBean(String jsonData, Class<T> beanType) {
		try {
			T t = objectMapper.readValue(jsonData, beanType);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * json转list对象
	 * 
	 */
	public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
		@SuppressWarnings("deprecation")
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, beanType);
		try {
			List<T> list = objectMapper.readValue(jsonData, javaType);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
