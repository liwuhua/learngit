package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS12;
import com.yjdj.view.core.mapper.GenericMapper;
import com.yjdj.view.core.mapper.IReportMapperFS12;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS12;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.ExcelExportUtil12;
import com.yjdj.view.core.util.ExcelImportUtil;
import com.yjdj.view.core.util.FileUtils;

/**
 * Created by liwuhua on 16/11/7.
 */

@Service
@TransactionConfiguration(defaultRollback = false)
@SuppressWarnings("all")
public class ReportServiceImpFS12 implements IReportServiceFS12{

    @Autowired
    private IReportMapperFS12 ireportmapperfs12;
    
    @Autowired
    private GenericMapper genericMapper;
    
    @Autowired
    private GenericService genericService;
    
    private final Logger log = Logger.getLogger(ReportServiceImpFS12.class);
    
    private ExcelExportUtil12 ee = null;

    
    @Override
    //通过判断是否是是导出 如果是导出那么  需要返回的是别的类型的结果 list
    public Object getListFs12(QueryBean queryBean,List<String> compCodeList) throws JsonProcessingException,DataAccessException{
	    ObjectMapper mapper = new ObjectMapper();
        String resultJson = null;
        //获取权限内所有的公司代码
//        List<MdmTcompCode> tcompCodeList = genericMapper.getListTcompCodeByList(compCodeList.toString().replace("[","\'").replace("]","\'").replace(" ",""));
        String json = null;
        //根据传入的公司代码  来判断编制单位  
        List<String> codeValue = queryBean.getCompCodeValue();
        boolean b = genericService.isBool(codeValue,compCodeList,queryBean);
          
        //时间必选  公司代码若不选默认 5*开头    +默认用户输入的公司代码 不需要做校验 若不正确那么不给予查询     
        if((StringUtils.isNotBlank(queryBean.getStartTime()) && StringUtils.isNotBlank(queryBean.getEndTime()))){
        	
        	//获取时间必选
        	String startTime = queryBean.getStartTime();
            String endTime = queryBean.getEndTime();
         	List<String> bukrsValue  = queryBean.getCompCodeValue();  
            
            String startYear = startTime.substring(0, 4);
            String endYear = endTime.substring(0, 4);
            String startMonth = startTime.substring(4, 6);
            String endMonth = endTime.substring(4, 6);
            
            String p_startfiscper=startYear+"0"+startMonth;   
            String p_endfiscper = endYear+"0"+endMonth; 
            
            //多值 转换成字符串  ,号隔开
            String p_bukrs =(bukrsValue==null||bukrsValue.size()==0)?"''" :bukrsValue.toString().replace("[", "\'").replace("]", "\'").replace(", ", ",");
        	HashMap<String, Object> paraMap = new HashMap<>();
        	paraMap.put("p_startfiscper", p_startfiscper);
        	paraMap.put("p_endfiscper"  , p_endfiscper);
        	paraMap.put("p_bukrs"       , p_bukrs);
            HashMap resultMap=new HashMap<>(); 
            
            List<THM_FS12> periodexpense = ireportmapperfs12.getPeriodexpense(paraMap);
            List<THM_FS12> periomodify = new ArrayList<>();
            
            HashMap<String, Object> countMap = new HashMap<>();
            countMap.put("bukrsValue",     bukrsValue);
            countMap.put("p_startfiscper", p_startfiscper);
            countMap.put("p_endfiscper",   p_endfiscper);
            
            //以下两个map是结合导入表以后  计算65和85行得出的今年去年的值
            Map currlastCount65 = ireportmapperfs12.getCurrlastCount65(countMap);
            Map currlastCount85 = ireportmapperfs12.getCurrlastCount85(countMap);
           
            double curryearcount65=0.00;
            double lastyearcount65=0.00;
            double curryearcount85=0.00;
            double lastyearcount85=0.00;
            if(currlastCount65!=null&&!currlastCount65.isEmpty()){
                //65行有值的情况下 去年的值和今年值
                 curryearcount65=Double.parseDouble(currlastCount65.get("curryearcount")+"");
                 lastyearcount65=Double.parseDouble(currlastCount65.get("lastyearcount")+"");
          }
            
            if(currlastCount85!=null&&!currlastCount85.isEmpty()){
            	 //85行有值的情况下 //85行有值的情况下 去年的值和今年值
                 curryearcount85=Double.parseDouble(currlastCount85.get("curryearcount")+"");
                 lastyearcount85=Double.parseDouble(currlastCount85.get("lastyearcount")+"");
            }
           
             //当两者全部为null时 才不更新 其他情况 更新   65和41关联    85和42
             if(currlastCount65!=null&&(!currlastCount65.isEmpty())
            		||currlastCount85!=null&&(!currlastCount85.isEmpty())){         
                
                 //如果结果中没有值  那么直接就是调用函数   不修改值
            	for (THM_FS12 thm : periodexpense) {
					if ("42".equals(thm.getRownum())) {
					     if(currlastCount65!=null&&(!currlastCount65.isEmpty())){
			   		            
					    	 double   mysqlCurr65=Double.parseDouble(periodexpense.get(64).getCurryearcount());
					    	 double   mysqlLastyear65 =Double.parseDouble(periodexpense.get(64).getLastyearcount());
					    	 double   mysqlCurr85=Double.parseDouble(periodexpense.get(84).getCurryearcount());
					    	 double   mysqlLastyear85 =Double.parseDouble(periodexpense.get(84).getLastyearcount());
					    	 double  mysqlcurr42 = Double.parseDouble(thm.getCurryearcount());
					         double  mysqllast42 = Double.parseDouble(thm.getLastyearcount());
			            
//						 本年累计数 更新			mysqlcurr42是查询后的值      curryearcount65是结合导入表的值    mysqlCurr65 是查询后的值
						String fiCurryearcount42 = mysqlcurr42 +(curryearcount85 - mysqlCurr85)+ (curryearcount65 - mysqlCurr65) + "";
						String fiLastyearcount42 = mysqllast42 + (lastyearcount85 - mysqlLastyear85)+ (lastyearcount65 - mysqlLastyear65) + "";
						THM_FS12 thm1 = new THM_FS12();
						thm1.setRownum("42");
						thm1.setProname("<B>二、管理费用</B>"); 
						thm1.setCurryearcount(fiCurryearcount42);
						thm1.setLastyearcount(fiLastyearcount42);
						periomodify.add(41,thm1);
						 continue;  
					   }
					 }else if ("65".equals(thm.getRownum())) {
							
					    	THM_FS12 thm2 = new THM_FS12();
							thm2.setRownum("65");
							thm2.setProname("&nbsp;3.折旧费");
							thm2.setCurryearcount(currlastCount65.get("curryearcount")+"");
							thm2.setLastyearcount(currlastCount65.get("lastyearcount")+"");
							periomodify.add(64,thm2);
							continue;  //periomodify.add(thm);
					} else if ("85".equals(thm.getRownum())) {
						
						THM_FS12 thm2 = new THM_FS12();
						thm2.setRownum("85");
						thm2.setProname("&nbsp;18.研究与开发费用");
						thm2.setCurryearcount(currlastCount85.get("curryearcount")+"");
						thm2.setLastyearcount(currlastCount85.get("lastyearcount")+"");
						periomodify.add(84, thm2);
						continue;  //不调用  periomodify.add(thm);
					}	    
					 periomodify.add(thm);
				}
            }
            
            List<Object> resultList = new ArrayList<>();
		
            if((currlastCount65!=null&&(!currlastCount65.isEmpty()))||(currlastCount85!=null
            		&&(!currlastCount85.isEmpty()))){
            	resultList.add(periomodify);
            	
            }else{
            	resultList.add(periodexpense);
            
            }
			
            if(b){
            	resultList.add("中车永济电机有限公司合计");
            	resultMap.put("BZDW","中车永济电机有限公司合计");
            }else{
                if(queryBean.getCompCodeValue().size() > 1){
                	resultList.add("");
                	resultMap.put("BZDW","");
                }else if(queryBean.getCompCodeValue().size() == 1){
                    String txtmd = genericMapper.getTcompCode(queryBean.getCompCodeValue().get(0)).getTxtmd();
                    resultList.add(txtmd);
                    resultMap.put("BZDW",txtmd);
                }
            }
			if(queryBean.isExport()){
				return resultList;  //导出时  不用json格式的数据
			}
			
		     //65或85时 两者其一部位空时 添加 
			   if((currlastCount65!=null&&(!currlastCount65.isEmpty()))||
	            	(currlastCount85!=null&&(!currlastCount85.isEmpty()))){
				     resultMap.put("periodexpense", periomodify);
	            }else{
	            	resultMap.put("periodexpense", periodexpense);
	           }
			
            resultJson = mapper.writeValueAsString(resultMap);
        }
           
        return resultJson;
    }


    @Override
    public ExcelExportUtil12 getexportExcel(QueryBean queryBean,List<String> compCodeList) throws JsonProcessingException,DataAccessException{

        List<String> headerList = Lists.newArrayList();
        headerList.add("项目");
        headerList.add("行次");
        headerList.add("本年累计数");
        headerList.add("上年累计数");
        
        headerList.add("项目");
        headerList.add("行次");
        headerList.add("本年累计数");
        headerList.add("上年累计数");   
        
        headerList.add("项目");
        headerList.add("行次");
        headerList.add("本年累计数");
        headerList.add("上年累计数");
        
        List<Object> dataRowList = Lists.newArrayList();
        List<List<Object>> dataList = Lists.newArrayList();
     	NumberFormat decimalFormat = new DecimalFormat("###,###.##");
    

//        List<THM_FS12> periodexpense = (List<THM_FS12>)getListFs12(queryBean,compCodeList);
        List<THM_FS12> periodexpense = (List<THM_FS12>)((List<Object>) getListFs12(queryBean,compCodeList)).get(0);
        
        if (periodexpense != null) {
            if (periodexpense.isEmpty()) {
                //查询数据为空的时候的处理
                for (int i = 1; i <= 1000000; i++) {
                    dataList.add(dataRowList);
                }

            } else {
                //查询数据不为空的时候的处理	
                for (int h = 1; h <= 1000000; h++) {
                    if (h == 42) {
                        break;
                    }
                    THM_FS12 no1 = periodexpense.get(h-1); 
                    dataRowList.add(no1.getProname().replace("&nbsp;", "  ").replace("<B>", "").replace("</B>", ""));  //<B>一、营业费用</B> h=1
                    dataRowList.add(no1.getRownum()); 
                    dataRowList.add(decimalFormat.format(Double.parseDouble(no1.getCurryearcount())));
                    dataRowList.add(decimalFormat.format(Double.parseDouble(no1.getLastyearcount())));
                    
                    
                    THM_FS12 no2 = periodexpense.get(h+40); 
                    dataRowList.add(no2.getProname().replace("&nbsp;", "  ").replace("<B>", "").replace("</B>", ""));
                    dataRowList.add(no2.getRownum());
                    dataRowList.add(decimalFormat.format(Double.parseDouble(no2.getCurryearcount())));
                    dataRowList.add(decimalFormat.format(Double.parseDouble(no2.getLastyearcount())));
                    
                    THM_FS12 no3 = periodexpense.get(h+81); 
                    dataRowList.add(no3.getProname().replace("&nbsp;", "  ").replace("<B>", "").replace("</B>", ""));//---&nbsp;
                    dataRowList.add(no3.getRownum());
                    dataRowList.add(decimalFormat.format(Double.parseDouble(no3.getCurryearcount())));
                    dataRowList.add(decimalFormat.format(Double.parseDouble(no3.getLastyearcount())));
                    
                    dataList.add(dataRowList);
                    dataRowList = new ArrayList<>();
                }
            }  
               
            List<String> secondTitleList = new ArrayList<>();
            String secondTitle = queryBean.getStartTime().substring(0, 4) + "年"+ queryBean.getStartTime().substring(4, 6) + "月— "
            + queryBean.getEndTime().substring(0, 4) + "年" + queryBean.getEndTime().substring(4, 6)+ "月" ;
            String BZDW = (String)((List<Object>) getListFs12(queryBean,compCodeList)).get(1);
            secondTitleList.add("编制单位: "+BZDW);
            for(int i=0; i<headerList.size()-7; i++){
            	secondTitleList.add("");
			}
            secondTitleList.add(secondTitle);
            ee = new ExcelExportUtil12("费用期间明细表", secondTitleList, headerList);

            for (int i = 0; i < 41; i++) {
                Row row = ee.addRow();
                for (int j = 0; j < dataList.get(i).size(); j++) {
                	//i是行数    j是列数的数据
//                	j是 0 1 4 5  8 9 
                	if(j==0||j==1||j==4||j==5||j==8||j==9 ){
                		if((j==0&&i==0)||(j==4&&i==0)||(j==8&&i==27)){
                			ee.addCell(row, row.getRowNum(), j, dataList.get(i).get(j), 4, true, String.class);
                		}else{
                			ee.addCell(row, row.getRowNum(), j, dataList.get(i).get(j), 2, true, String.class);
                		}
                		
                	}else {
                		
                		ee.addCell(row, row.getRowNum(), j, dataList.get(i).get(j), 1, true, String.class);
					}
                	
                	
                	

//                	if(j==1||j==2||j==3||j==5||j==6||j==7||j==9||j==10||j==11){
//                	 ee.addCell(row, row.getRowNum(), j, dataList.get(i).get(j), 2, true, String.class);
//                	 //加粗
////                	 ee.addCell(row, row.getRowNum(), j, val.replaceAll("&nbsp;", " "), 4, true, String.class);
//                	}else{
//                	 ee.addCell(row, row.getRowNum(), j, dataList.get(i).get(j), 1, true, String.class);
//                   }
                }
            }
        }

        return ee;
    }


    
    /*
     * 一次导入一个单位   一个月  起始月和结束月必须一致  灰色部分才能允许导入数据  
     * 
     */
	@Override
	public Object importData(String filePath, List<String> compCodeList) throws JsonProcessingException, IOException,
			DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException {

		String json = null;
		String startYearMonth = null;
		String endYearMonth = null;
		Matcher matcher1 = null;
		Matcher matcher2 = null;
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		ExcelImportUtil ei = new ExcelImportUtil(filePath, 2);
		List<String> headerList = Lists.newArrayList();
		headerList.add("项目");
		headerList.add("行次");
		headerList.add("本年累计数");
		headerList.add("上年累计数");
		headerList.add("项目");
		headerList.add("行次");
		headerList.add("本年累计数");
		headerList.add("上年累计数");

		headerList.add("项目");
		headerList.add("行次");
		headerList.add("本年累计数");
		headerList.add("上年累计数");
		json = ei.checkHeadList(headerList, listMap);
		if (null != json) {
			return json;
		}
		String company = ei.getCellValue(1, 0).toString();
		String regex = ei.getCellValue(1, 6).toString().substring(8, ei.getCellValue(1, 6).toString().length()-8); //年月分割符
		String[] timeInterval = ei.getCellValue(1, 6).toString().split(regex);
		String startTime = timeInterval[0];
		String endTime   = timeInterval[1];
	
		Pattern p = Pattern.compile("[^0-9]");
		matcher1 = p.matcher(startTime);
		matcher2 = p.matcher(endTime);
		if (company.indexOf(":") > 0) {
			company = company.substring(company.indexOf(":") + 1, company.length());
		} else if (company.indexOf("：") > 0) {
			company = company.substring(company.indexOf("：") + 1, company.length());
		}
		if (company.trim().length() == 0) {
			listMap.put("ERRORCOMPANEY", "未填写编制单位!");
		}
		// 去域中 查询有没有这个公司 compCodeList 权限公司代码
		List<Map<String, String>> getCompList = ireportmapperfs12.selectComCodeBytxtmd(company.trim());
		if (getCompList.size() <= 0) {
			listMap.put("ERRORCOMPANEY", "编制单位填写错误，没有该编制单位!");
		}

		// 权限代码不为空 遍历权限内的公司代码 后 和文件中的公司代码比较 查看是否在权限内
		if (compCodeList.size() == 0 || compCodeList == null) {
			listMap.put("ERRORCOMPANEY", "权限配置错误,没有配置公司查询权限");

		} else {
			if (getCompList.size()>0 && !compCodeList.contains(getCompList.get(0).get("COMP_CODE"))) {
				// 权限公司代码不包含 文件中的公司代码
				listMap.put("ERRORCOMPANEY", "模板错误,编制单位不在权限范围内");
			}
		}
		if(getCompList.size()>0){
			company = getCompList.get(0).get("COMP_CODE"); 
		}
		
		startYearMonth = matcher1.replaceAll("").trim();
		endYearMonth = matcher2.replaceAll("").trim();
		if ((!FileUtils.isValidDate(startYearMonth)) || !FileUtils.isValidDate(endYearMonth)) { 
			listMap.put("ERRORTIME", "时间填写错误!");
		} else if (Integer.parseInt(startYearMonth) != Integer.parseInt(endYearMonth)) {
			listMap.put("ERRORTIME", "起始结束时间必须一致!");
		}


		String lastCell65 = ((String)ei.getCellValue(26, 6)).trim().replaceAll(",", "").replace("，", ""); // 获取指定行的值 必须是数值型  可为空  65
		String thisCell65 = ((String)ei.getCellValue(26, 7)).trim().replaceAll(",", "").replace("，", ""); // 获取指定行的值
		String lastCell85 = ((String)ei.getCellValue(5, 10)).trim().replaceAll(",", "").replace("，", ""); // 获取指定行的值 85
		String thisCell85 = ((String)ei.getCellValue(5, 11)).trim().replaceAll(",", "").replace("，", ""); // 获取指定行的值

		if ((!lastCell65.matches("^(\\-|\\+)?\\d+(\\.\\d+)?$"))&&StringUtils.isNotEmpty(lastCell65)) {
			listMap.put("ERRORTIME", "第65行次的本年累计数不是数值型!");
		} else if ((!thisCell65.matches("^(\\-|\\+)?\\d+(\\.\\d+)?$"))&&StringUtils.isNotEmpty(thisCell65)) {
			listMap.put("ERRORTIME", "第65行次的上年累计数不是数值型!");
		} else if ((!lastCell85.matches("^(\\-|\\+)?\\d+(\\.\\d+)?$"))&&StringUtils.isNotEmpty(lastCell85)) {
			listMap.put("ERRORTIME", "第85行次的本年累计数不是数值型!");
		} else if ((!thisCell85.matches("^(\\-|\\+)?\\d+(\\.\\d+)?$"))&&StringUtils.isNotEmpty(thisCell85)) {
			listMap.put("ERRORTIME", "第85行次的上年累计数不是数值型!");
		}

		if(listMap.size() > 0){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("ERROR", listMap);
			json = mapper.writeValueAsString(map);
			return json;
		}
		// 1)先去导入结果表中查询数据(月区间+ 公司代码(只能一个)) 看看有没有 有的话 update更新输入的四个字段和 大条目值
		// 2) 导入表中没有数值的话 那么插入数据
		// 文件中输入的值都是符合规定的 先可调用存储过程查询 然后查询中间表 然后将四个字段 取输入值和实际结果的差值 然后结果 对应数值相加减
//		String p_startfiscper = startYearMonth.substring(0, 4) + "0" + startYearMonth.substring(4, 6);
//		String p_endfiscper = endYearMonth.substring(0, 4) + "0" + endYearMonth.substring(4, 6);
//		String string = getCompList.get(0).get("COMP_CODE");

		// {p_startfiscper=2016010, p_endfiscper=2016012, p_bukrs=''}
//		HashMap paraMap = new HashMap<>();
//		paraMap.put("p_startfiscper", p_startfiscper);
//		paraMap.put("p_endfiscper", p_endfiscper);
//		paraMap.put("p_bukrs", "\'" + getCompList.get(0).get("COMP_CODE") + "\'");
//		List<THM_FS12> periodexpense = ireportmapperfs12.getPeriodexpense(paraMap);
//		List<InsertFieldTHM_FS12> tmpDataList = new ArrayList<>();

//		if (periodexpense.size() == 0 || periodexpense == null) {
//			// 说明没有这个公司的记录数
//			listMap.put("ERRORTIME", "无此公司的其他项记录!");
//			json = mapper.writeValueAsString(listMap);
//			return json;
//		} else {
			// 这个因为导入表中没有记录数 所以需要查询后 导入到 导入表中
			// 遍历periodexpense 元素 先可调用存储过程查询 然后查询中间表 然后将四个字段 取输入值和实际结果的差值 然后结果
			// 对应数值相加减
			// 将查询的到的数据 封装到 tmpDataList 中
//			for (THM_FS12 thm : periodexpense) {
				// 先65行 在85行 算差值
//				if (thm.getRownum() == "42") {
					// 数据库中65行的值 计算更新42行差值使用
//					int mysqlCurr65 = Integer.parseInt(periodexpense.get(64).getCurryearcount());
//					int mysqlLastyear65 = Integer.parseInt(periodexpense.get(64).getLastyearcount());
//					InsertFieldTHM_FS12 insertfield = new InsertFieldTHM_FS12();
//					insertfield.setRownum("65");
//					insertfield.setCurryearcount(thisCell65);
//					insertfield.setLastyearcount(lastCell65);
//					insertfield.setStartYearMonth(startYearMonth);
//					insertfield.setEndYearMonth(endYearMonth);
//					insertfield.setComcode(getCompList.get(0).get("COMP_CODE"));
//					tmpDataList.add(insertfield);

					// 数据库中第42行数据 本年的
//					int mysqlcurr42 = Integer.parseInt(thm.getCurryearcount());
//					int intThisCell65 = Integer.parseInt(thisCell65);

					// 数据库中第42行数据 去年的
//					int mysqllast42 = Integer.parseInt(thm.getLastyearcount());
//					int intLastCell65 = Integer.parseInt(lastCell65);

					// 本年累计数 更新
//					String fiCurryearcount42 = mysqlcurr42 + (intThisCell65 - mysqlCurr65) + "";
//					String fiLastyearcount42 = mysqllast42 + (intLastCell65 - mysqlLastyear65) + "";
//
//					InsertFieldTHM_FS12 insertfield42 = new InsertFieldTHM_FS12();
//					insertfield.setRownum("42");
//					insertfield42.setCurryearcount(fiCurryearcount42);
//					insertfield42.setLastyearcount(fiLastyearcount42);
//					insertfield42.setStartYearMonth(startYearMonth);
//					insertfield42.setEndYearMonth(endYearMonth);
//					insertfield42.setComcode(getCompList.get(0).get("COMP_CODE"));
//					tmpDataList.add(insertfield42);
//					periodexpense.remove(64); // 能否删除第65行??
//					System.out.println(periodexpense);

//				} else if (thm.getRownum() == "83") {
					// lastCell85 thisCell85
					// 通过 85 计算更新83
					// 先65行 在85行 算差值

//					// 数据库中85行的值 计算更新83行差值使用
//					int mysqlCurr85 = Integer.parseInt(periodexpense.get(84).getCurryearcount());
//					int mysqlLast85 = Integer.parseInt(periodexpense.get(84).getLastyearcount());
//
//					InsertFieldTHM_FS12 insertfield = new InsertFieldTHM_FS12();
//					insertfield.setRownum("85");
//					insertfield.setCurryearcount(thisCell85);
//					insertfield.setLastyearcount(lastCell85);
//					insertfield.setStartYearMonth(startYearMonth);
//					insertfield.setEndYearMonth(endYearMonth);
//					insertfield.setComcode(getCompList.get(0).get("COMP_CODE"));
//					tmpDataList.add(insertfield);
//
//					// 数据库中第83行数据 本年的 当前83行
//					int mysqlcurr83 = Integer.parseInt(thm.getCurryearcount());
//					int mysqllast83 = Integer.parseInt(thm.getLastyearcount());
//
//					// 输入的85行
//					int intThisCell85 = Integer.parseInt(thisCell85);
//					int intLastCell85 = Integer.parseInt(lastCell85);
//
//					// 本年累计数 更新
//					String fiCurryearcount83 = mysqlcurr83 + (intThisCell85 - mysqlCurr85) + "";
//					String fiLastyearcount83 = mysqllast83 + (intLastCell85 - mysqlLast85) + "";
//
//					InsertFieldTHM_FS12 insertfield83 = new InsertFieldTHM_FS12();
//					insertfield.setRownum("83");
//					insertfield83.setCurryearcount(fiCurryearcount83);
//					insertfield83.setLastyearcount(fiLastyearcount83);
//					insertfield83.setStartYearMonth(startYearMonth);
//					insertfield83.setEndYearMonth(endYearMonth);
//					insertfield83.setComcode(getCompList.get(0).get("COMP_CODE"));
//					tmpDataList.add(insertfield83);
//					periodexpense.remove(84); // 能否删除第85行??
//					System.out.println(periodexpense);
//				}
				// 将 periodexpense 复制到 tmpDataList
//				InsertFieldTHM_FS12 insertfield = new InsertFieldTHM_FS12();
//				insertfield.setRownum(thm.getRownum().replace("&nbsp;", "  ").replace("<B>", "").replace("</B>", ""));
//				insertfield.setCurryearcount(thm.getCurryearcount());
//				insertfield.setLastyearcount(thm.getLastyearcount());
//				insertfield.setStartYearMonth(startYearMonth);
//				insertfield.setEndYearMonth(endYearMonth);
//				insertfield.setComcode(getCompList.get(0).get("COMP_CODE"));
//				tmpDataList.add(insertfield);
//			}
//		     rownum  lastCell65    thisCell65(lastCell85,thisCell85)  getCompList.get(0).get("COMP_CODE")

//			 Map<Object,Object> map = new HashMap<>();
//			 map.put("lastCell65",lastCell65);           
//			 map.put("thisCell65", thisCell65);
//			 map.put("lastCell85",lastCell85);
//			 map.put("thisCell85",thisCell85);
		
		
             THM_FS12 thm_FS1 = new THM_FS12();
             thm_FS1.setCurryearcount(lastCell65);
             thm_FS1.setLastyearcount(thisCell65);
             thm_FS1.setRownum("65");
             
             THM_FS12 thm_FS2 = new THM_FS12();
             thm_FS2.setCurryearcount(lastCell85);
             thm_FS2.setLastyearcount(thisCell85);
             thm_FS2.setRownum("85");
             List<THM_FS12> tmpDataList = new ArrayList<>();     
             tmpDataList.add(thm_FS1);
             tmpDataList.add(thm_FS2);
	   
			ireportmapperfs12.saveOrUpdateData(startYearMonth, endYearMonth, getCompList.get(0).get("COMP_CODE"), tmpDataList);
			listMap.put("SUCCESSES", "导入成功!");
			json = mapper.writeValueAsString(listMap);
			return json;

		}
	}
