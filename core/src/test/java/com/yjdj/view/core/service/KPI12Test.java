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
public class KPI12Test {

	@Autowired
	private IReportServiceKPI12 iReportServiceKPI12;
	@Test
	public void getRate(){
		String label = "100";
		try {
			
//			Object result10 = iReportServiceKPI12.getProOTDeliverRateAndProCompCount("201301");
//			System.out.println("result+++++++"+result10);
//			Object result11 = iReportServiceKPI12.getProOTDeliverGoDown("201701");
//			System.out.println("result+++++++"+result11);
			
			
//			Object result11 = iReportServiceKPI12.getProUnCompSumByBukrsGoDown("20010101","5003");
//			System.out.println("result+++++++"+result11);
			
			Object result11 = iReportServiceKPI12.getProCompSumGoDown("201612");
			System.out.println("result+++++++"+result11);
			
//			Object result13 = iReportServiceKPI12.getProCompSumByBukrsGoDown("201703","5003");
//			System.out.println("result+++++++"+result13);
			
//			Object result12 = iReportServiceKPI12.getProOTDeliverByBukrsGoDown("201703","5003");
//			System.out.println("result+++++++"+result12);	
			
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
