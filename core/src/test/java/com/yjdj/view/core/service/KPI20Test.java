package com.yjdj.view.core.service;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class KPI20Test {

	@Autowired
	private IReportServiceKPI20 iReportServiceKPI20;
	
	
	@Test
	public void getLedgercheck() throws DataAccessException, JsonProcessingException, IOException{
		String ledgercheck = iReportServiceKPI20.getLedgercheck("201604");
		 System.out.println(ledgercheck+" ledgercheck");
 
	}
}
