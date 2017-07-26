package com.yjdj.view.core.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hsqldb.lib.HashMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.QueryPojo2;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.ExcelExportUtil12;
import com.yjdj.view.core.util.ExcelExportUtil2227;
import com.yjdj.view.core.util.FileUtils;

/**
 * Created by zcyj on 1816/10/24.
 * @param <K>
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class KPI2227Test {
	
	@Autowired
	private IReportServiceKPI2227 ireportservicekpi2227;

    @Test   //质量损失 +营业收入的截止时间
    public void getDeadline() throws Exception{
        String json = ireportservicekpi2227.getDeadline();
        System.out.println(json+"  json的值");
    }
	
    @Test  //厂内外一年中每个月的损失
    public void getPlantAccount() throws Exception{
    	
        QueryBean queryBean = new QueryBean();
        queryBean.setDateYearMonth("201704");
        String PlantAccount = ireportservicekpi2227.getPlantAccount(queryBean);
        System.out.println(PlantAccount+"  PlantAccount数值");
    }
	
    
    @Test  //质量分布中的比率数
    public void getQualiDetail() throws Exception{
    	//fs18的条件都不是必须的
        QueryBean queryBean = new QueryBean();
        queryBean.setDateYearMonth("201611");
        String qualidetail = ireportservicekpi2227.getQualiDetail(queryBean);
        System.out.println(qualidetail+"  qualidetail数值");
    }
    
             
    @Test   // 厂内外  各质量损失类别下钻至质量损失原因 
    public void getCauseByType() throws Exception{
        QueryBean queryBean = new QueryBean();
        queryBean.setDateYearMonth("201611");
        queryBean.setProductType("1");
        String causeByType = ireportservicekpi2227.getCauseByType(queryBean);
        System.out.println(causeByType+"  causeByType的值");
    }
    
        
    @Test   // 厂内外  各质量损失类别下钻至责任单位
    public void getDutyunitByCause() throws Exception{
        QueryBean queryBean = new QueryBean();
        queryBean.setDateYearMonth("201611");
        queryBean.setProductType("E");
        String dutyunitByCause = ireportservicekpi2227.getDutyunitByCause(queryBean);
        System.out.println(dutyunitByCause+"  dutyunitByCause的值");
    }
    
    
    @Test   // 厂内外  各质量损失类别下钻至责任单位
    public void getProjecttypes() throws Exception{
      
        String projecttypes = ireportservicekpi2227.getProjecttypes();
        System.out.println(projecttypes+"  projecttypes的值");
    }
    
    //按型号统计(数量) + 按故障类型统计(台次)   
    @Test   
    public void getFaultCount() throws Exception{
        QueryBean queryBean = new QueryBean();
        queryBean.setDateYearMonth("201612");
        queryBean.setProductType("铁路电机");
        String faultCount = ireportservicekpi2227.getFaultCount(queryBean);
        System.out.println(faultCount+"  faultCount的值");
    }
    
    @Test  //产品故障统计  故障型号下钻
    public void getLayerByModel() throws Exception{
        QueryBean queryBean = new QueryBean();
        
        queryBean.setDateYearMonth("201611");
        queryBean.setProductType("风电产品");
        queryBean.setStartTime("YJ93C");
        
        String layerByModel = ireportservicekpi2227.getLayerByModel(queryBean);
        System.out.println(layerByModel+"  layerByModel的值");
    }
    
    
    
    @Test  //产品故障统计  故障类型下钻
    public void getLayerByType() throws Exception{
        QueryBean queryBean = new QueryBean();
        queryBean.setDateYearMonth("201611");
        queryBean.setProductType("风电产品");
        queryBean.setStartTime("速传故障");
        
        String layerByType = ireportservicekpi2227.getLayerByType(queryBean);
        System.out.println(layerByType+"  layerByType的值");
    }
    
    
    @Test  //科学计数法
    public void getScienNum() throws Exception{
//        DecimalFormat decimalFormat = new  DecimalFormat("0000.00");
//        System.out.println(decimalFormat.format(3.4924596548080444e-10)+"!!!!1");
    	BigDecimal bigDecimal = new BigDecimal("2.0161111E7");
    	System.out.println(bigDecimal.toPlainString()+"!!!");
        
    }
    
    
    
    //导入文件
    @Test
    public void importData() throws DataAccessException, org.apache.poi.openxml4j.exceptions.InvalidFormatException, ParseException, Exception, IllegalAccessException {

    		String filePath = "C:\\Users\\Administrator\\Desktop\\最新FS功能包\\KPI\\质量模板改之后的"
    					+ "\\导入成功的模板\\厂内产品质量信息表-导入模板-20170321.xls";
//    		String filePath = "C:\\Users\\Administrator\\Desktop\\最新FS功能包\\KPI\\质量模板改之后的"
//    				+ "\\导入成功的模板\\厂内产品质量信息表-2016年数据.xlsx";
        
        try {
            String result = (String) ireportservicekpi2227.importData(filePath);
            if(result != null || !"".equals(result)){
                System.out.println("result++++++++++++"+result);
            }
        } catch (InvalidFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
     
    //条件查询  项目类型
    @Test
    public void getProjectype() throws IOException {
        Object projecttype = ireportservicekpi2227.getCode("THM_KPI2227_04", "1", null,1);
        System.out.println(projecttype+" resultJson!!!!");	
    }
    
    
    //条件查询  质量损失类别
    @Test
    public void getCategorycode() throws IOException {
        Object categorycode = ireportservicekpi2227.getCode("THM_KPI2227_05", "1", null,2);
        System.out.println(categorycode+" resultJson!!!!");	
    }
    
    //条件查询  质量损失原因
    @Test
    public void getReasoncode() throws IOException {
        Object reasoncode = ireportservicekpi2227.getCode("THM_KPI2227_06", "A", null,3);
        System.out.println(reasoncode+" resultJson!!!!");	
    }
    
 
    
   //查询
    @Test
    public void selectResult() throws IOException {
    	QueryPojo2 queryPojo = new QueryPojo2();
        Object resultJson = ireportservicekpi2227.getResult(queryPojo);
        System.out.println(resultJson);
    }
    
    //导出查询的结果集
    @Test
    public void downloadResult() throws IOException {
    	QueryPojo2 queryPojo = new QueryPojo2();
        queryPojo.setExport(true);
        Object resultJson = ireportservicekpi2227.getResult(queryPojo);
        if (resultJson != null && queryPojo.isExport()) {
            ExcelExportUtil2227 ee = (ExcelExportUtil2227) resultJson;
            ee.writeFile("C://Users//Administrator//Desktop//888.xlsx");
            ee.dispose();
            System.out.println("厂内Export success.");
        }
    }
    
    
    //导出模板测试
    @Test
    public void downloadTemplate() throws IOException {
        Object resultJson = ireportservicekpi2227.downloadTemplate();
        if (resultJson != null) {
            ExcelExportUtil2227 ee = (ExcelExportUtil2227) resultJson;
//            ee.writeFile("C://Users//Administrator//Desktop//厂内质量信息导出表.xlsx");
            ee.writeFile("C://Users//Administrator//Desktop//temp//厂内质量信息导出表.xlsx");
            ee.dispose();
            System.out.println("模板Export success.");
        }

    }
    
    
    public static void main(String[] args) {
           
//    	System.out.println("201601.00".substring(0, 6));
//    	验证非零的负整数：^\\-[1-9][0-9]*$
//    	验证非负整数（正整数 + 0） ^\\d+$
//    	System.out.println(checkDateFormat(1,"1","20170101" ));;
//    	System.out.println(isMatches("2"));
//    	  DecimalFormat decimalFormat = new  DecimalFormat("0000.00");
//        System.out.println(decimalFormat.format(3.4924596548080444e-10)+"!!!!1");
    	BigDecimal bigDecimal = new BigDecimal("2.016111111E7");    //2.0161111E7  20161111
    	System.out.println(bigDecimal.toPlainString()+"!!!");
    	
        
    }
    
    

    
        
   public static boolean isMatches(String number){
    	  boolean flag=false;
    	  try{
    	   String regex="^\\d+$";  //  ([A-Fa-f]([\d]|1[01]))|(([\d]|1[01])[A-Fa-f])
    	   Pattern p=Pattern.compile(regex);
    	   Matcher m=p.matcher(number);
    	   if(m.matches()){
    	     System.out.println("succes+----符合");
    	    return true;
    	   }else{
    	    System.out.println("false----不符合");
    	   }
    	  }catch(Exception e){
    	   e.printStackTrace();
    	  }
    	  return flag;
    	 }
    	
   }  




