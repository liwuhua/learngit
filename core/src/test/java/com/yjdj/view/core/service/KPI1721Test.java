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
public class KPI1721Test {

	@Autowired
	private IReportServiceKPI1721 iReportServiceKPI1721;
	@Test
	public void getRate(){
		String label = "100";
		try {
//			Object result1 = iReportServiceKPI1721.getOrdersSumRate("201704");
//			System.out.println("result+++++++"+result1);
			
//			Object result10 = iReportServiceKPI1721.getLifnrRateAndEkgrpRate("201603");
//			System.out.println("result+++++++"+result10);
//			Object result11 = iReportServiceKPI1721.getEkgrpByRate(label,time);
//			System.out.println("result+++++++"+result11);
			
			
			Object result11 = iReportServiceKPI1721.getMaterial("201703");
			System.out.println("result+++++++"+result11);
			
//			Object result12 = iReportServiceKPI1721.getProduct("201611");
//			System.out.println("result+++++++"+result12);
//			
//			Object result12 = iReportServiceKPI1721.getMaterialGoDown("201612");
//			System.out.println("result+++++++"+result12);
			
//			Object result13 = iReportServiceKPI1721.getProductDynamicGoDown("201602");
//			System.out.println("result+++++++"+result13);
			
			
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
