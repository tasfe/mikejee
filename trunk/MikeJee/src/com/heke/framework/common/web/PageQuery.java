/**
 * 
 */
package com.heke.framework.common.web;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author ThinkPad
 * 
 */
public abstract class PageQuery {

	/**
	 * 总纪录数
	 */
	protected int total;

	/**
	 * 当前页
	 */
	protected int page = 1;

	/**
	 * 每页显示数量
	 */
	protected int rows = 20;

	private String sortName;

	private String sortOrder;

	protected List datas;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	@JsonIgnore
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@JsonProperty("rows")
	public List getDatas() {
		return datas;
	}

	public void setDatas(List datas) {
		this.datas = datas;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public void clear() {
		this.total = 0;
		if (this.datas != null) {
			this.datas.clear();
		}
	}

}
