package com.yjdj.view.core.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.log.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS12;
import com.yjdj.view.core.mapper.IReportMapperFS12;
import com.yjdj.view.core.util.ExcelExportUtil12;

/**
 * master分支更改的
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS12Test {

    
    @Autowired
    private IReportServiceFS12 ireportservicefs12;

	@Autowired
	private IReportMapperFS12	ireportmapperfs12;

    @Test
    public void getReportJson(){
    	
        QueryBean queryBean = new QueryBean();
        List<String> arrayList1 = new ArrayList<>();
        arrayList1.add("5003");
        queryBean.setStartTime("201610");
        queryBean.setEndTime("201610");
        queryBean.setExport(true);
        queryBean.setCompCodeValue(arrayList1);
        List<String> arrayList = new ArrayList<>();
        arrayList.add("5003");
        arrayList.add("5004");
        arrayList.add("5005");
        arrayList.add("5006");
        arrayList.add("5025");
        arrayList.add("5142");
        arrayList.add("5026");
        arrayList.add("5500");
        try {
        	Log.info("queryBean ++++++++"+queryBean.toString());
            Object resultJson = ireportservicefs12.getexportExcel(queryBean,arrayList);
            System.out.println("resultJson++++++++++++++="+resultJson.toString());
            ExcelExportUtil12 ee = (ExcelExportUtil12) resultJson;
        	ee.writeFile("C://Users//Administrator//Desktop//export.xlsx");
    		ee.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    @Test
    public void deStatistics() throws Exception, ParseException{
    	ObjectMapper objectMapper = new ObjectMapper();
    	QueryBean bean = new QueryBean();  
    	List<String> compCodeList = new ArrayList<>();
    	String listFs12 = (String) ireportservicefs12.getListFs12(bean,compCodeList);
    	System.out.println(listFs12+"		listFs12  jieguozhi ");
    }
    
    @Test
    public void string() throws Exception, ParseException{
    	String a="";
    	System.out.println(StringUtils.isNotEmpty(a));
    	
    }

	@Test
	public void getLine() throws Exception, ParseException {
		List<Map> selectLine = ireportmapperfs12.selectLine( "122" );
		System.out.println( selectLine );
	}

	@Test
	public void getLine1() throws Exception, ParseException {
		THM_FS12 thm_FS12 = new THM_FS12();
		thm_FS12.setRownum( "5003" );
		List<Map> selectLine = ireportmapperfs12.selectLineByObj( thm_FS12 );
		System.out.println( selectLine );
	}

	@Test
	public void insert() throws Exception, ParseException {
		THM_FS12 thm_FS12 = new THM_FS12();
		thm_FS12.setProname( "5005" );
		// thm_FS12.setRownum( "5005" );
		thm_FS12.setRownum( null );
		thm_FS12.setCurryearcount( "5003" );
		thm_FS12.setLastyearcount( "5003" );
		ireportmapperfs12.insert( thm_FS12 );

	}

	@Test
	public void exists() throws Exception, ParseException {
		System.out.println( ireportmapperfs12.exists( "5003" ) );
	}

	@Test
	public void existsboolean() throws Exception, ParseException {
		System.out.println( ireportmapperfs12.existsboolean( false ) );
	}
  
}
