package com.icat.common.util.excel;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

public class ExcelData<T> implements Serializable {
	private static final long serialVersionUID = 4444017239100620999L;

	// 表头
	private List<String> titles;

	// 数据
	private List<List<T>> rows;

	// 页签名称
	private String name;

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public List<List<T>> getRows() {
		return rows;
	}

	public void setRows(List<List<T>> rows) {
		this.rows = rows;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
