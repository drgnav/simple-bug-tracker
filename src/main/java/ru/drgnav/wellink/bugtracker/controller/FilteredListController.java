package ru.drgnav.wellink.bugtracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ru.drgnav.wellink.bugtracker.util.BugTrackerUtils;

public abstract class FilteredListController {
	protected int pageSize = 25;
	protected String headerStringKey;
	
	@Autowired
	protected BugTrackerUtils utils;
	
	public abstract void search();
	public abstract void clearFilter();
	public abstract void addItem();
	public abstract List<?> getItems();
	public abstract int getRowsCount();
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int updatePageSize(int pageSize) {
		return this.pageSize = pageSize;
	}
	
	public String getHeaderString() {
		return utils.getMessage(headerStringKey);
	}


}
