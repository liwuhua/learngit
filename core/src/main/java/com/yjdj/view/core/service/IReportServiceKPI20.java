package com.yjdj.view.core.service;

import java.io.IOException;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IReportServiceKPI20 {
	
	
	public String getLedgercheck(String yearMonth) throws JsonProcessingException, IOException ,DataAccessException;
	
	
}
