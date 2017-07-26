package com.yjdj.view.core.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.util.ExcelExportUtil05;

/**
 * create on 2017/03/28
 * @author zhangwenguo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS37Test {

    @Autowired
    private IReportServiceFS37 iReportServiceFS37;
    
    @Test
    public void getReportJson(){
    	String matnr = null;
    	String werks = null;
    	String dateTime = null;
    	//CALL FS37("000000Z500000189039","1000","20161010");
    	matnr = "000000Z500000189039";
    	werks = "1000";
    	dateTime = "20161010";
    	
    	matnr = matnr.replace("0", " ");
    	
    	try {
			Object resultJson = iReportServiceFS37.getReportJson(matnr, werks);
			System.out.println(resultJson);
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
