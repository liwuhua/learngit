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
public class KPI1516Test {

	@Autowired
	private IReportServiceKPI1516 iReportServiceKPI1516;
	@Test
	public void getRate(){
			String result="";
			result = iReportServiceKPI1516.getMTMandOIPM("201703");
			
			//result = iReportServiceKPI1516.getFirstDetail("201612");
//			result = iReportServiceKPI1516.getVClassShopDetail("201601");
			//result = iReportServiceKPI1516.getSecondWorkShopDetail("201601");
//			result = iReportServiceKPI1516.getOrderDetail("201702");
			System.out.println("result  :  "+result);
	}
}
