package com.yjdj.view.core.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.util.ExcelExportUtil54;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.eclipse.jetty.util.log.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * create on 2017/3/30
 * @author yangzhijie
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS54Test {

    @Autowired
    private IReportServiceFS54 IReportServiceFS54;

    @Test
    public void getReportJson(){
    	
    	boolean isExport = false;
//        List<String> cpxh = new ArrayList<String>();
//        cpxh.add("2Q213");
//        cpxh.add("92A");
    	
      List<String> traintype = new ArrayList<String>();
      traintype.add("HXD2");
      traintype.add("CRH5A");
    	
    	List<String> matnrValue = new ArrayList<String>();
    	matnrValue.add("CNR0000021930");
        
//    	List<String> matnrInterval = new ArrayList<String>();
//    	matnrInterval.add("5020000565");
    	
//        List<String> fwz = new ArrayList<String>();
//        fwz.add("沈阳服务站");
//        fwz.add("大连服务站");
    	
    	String yearMonth = "201705";
        
        QueryBean queryBean = new QueryBean();
        queryBean.setMatnrValue(matnrValue);
//        queryBean.setMatnrValue(matnrInterval);
        queryBean.setExport(isExport);
        queryBean.setDateYearMonth(yearMonth);
//        queryBean.setCpxh(cpxh);
//        queryBean.setFwz(fwz);
        
        if(isExport){
        	try {
            	Log.info("queryBean ++++++++"+queryBean.toString());
                Object resultJson = IReportServiceFS54.getReportJson(queryBean);
                System.out.println("resultJson++++++++++++++++"+resultJson.toString());
            	ExcelExportUtil54 ee = (ExcelExportUtil54) resultJson;
        		ee.writeFile("d:/export54.xlsx");
        		ee.dispose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
        	try {
            	Log.info("queryBean ++++++++"+queryBean.toString());
                Object resultJson = IReportServiceFS54.getReportJson(queryBean);
                System.out.println("resultJson++++++++++++++++"+resultJson.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void importTest() throws DataAccessException, JsonProcessingException, InvalidFormatException, InstantiationException, IllegalAccessException, IOException{
    	String filePath = "D://aaa.xlsx";
    	@SuppressWarnings("unused")
		Object result = IReportServiceFS54.importData(filePath);
    	System.out.println("result+++++++++++++++"+result.toString());    	
    }
    
    
    @Test
    public void selectFwzTest() throws DataAccessException, JsonProcessingException, InvalidFormatException, InstantiationException, IllegalAccessException, IOException{
    	String fwzLike = "";
    	String fwzIdLike = "";
    	List<Map<String,String>> fwzList = new ArrayList<Map<String,String>>();
    	fwzList = IReportServiceFS54.selectFwz(fwzLike,fwzIdLike);
    	System.out.println("result+++++++++++++++"+fwzList);    	
    }
    
    @Test
    public void selectCxTest() throws DataAccessException, JsonProcessingException, InvalidFormatException, InstantiationException, IllegalAccessException, IOException{
    	String cxLike = "";
    	String cxIdLike = "";
    	List<Map<String,String>> cxList = new ArrayList<Map<String,String>>();
    	cxList = IReportServiceFS54.selectCx(cxLike,cxIdLike);
    	System.out.println("result+++++++++++++++"+cxList);    	
    }
    
    @Test
    public void selectMaterialInListTest() throws DataAccessException, JsonProcessingException, InvalidFormatException, InstantiationException, IllegalAccessException, IOException{
    	List<String> temp = new ArrayList<String>();
    	List<String> result = new ArrayList<String>();
    	temp.add("5020000565");
    	temp.add("5020000566");
    	temp.add("50200000565");
    	result = IReportServiceFS54.selectMaterialInList(temp);
    	System.out.println("result+++++++++++++++"+result);    	
    }
}
