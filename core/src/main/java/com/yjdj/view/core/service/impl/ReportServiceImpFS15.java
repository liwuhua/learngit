package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yjdj.view.core.entity.mybeans.InsertFieldTHM_FS15;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_IMP_FS16;

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
import com.yjdj.view.core.entity.mybeans.THM_IMP_FS15;
import com.yjdj.view.core.mapper.IReportMapperFS15;
import com.yjdj.view.core.service.IReportServiceFS15;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.ExcelImportUtil;
import com.yjdj.view.core.util.FileUtils;
import com.yjdj.view.core.util.StringUtil;

import java.util.Objects;

import org.springframework.util.StringUtils;

/**
 * create on 2016/11/04
 *
 * @author yangzhijie
 *
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS15 implements IReportServiceFS15 {

    private final Logger log = Logger.getLogger(ReportServiceImpFS15.class);
    
    @Autowired
    private IReportMapperFS15 iReportMapperFS15;
    private String dateYearMonth = null;
    private List<String> compCodeValue = null;
    private List<String> title = null;
    private List<String> secondTitle = null;
    private boolean isExport = false;
    private List<List<Object>> resultDataRowList = null;
    private List<String> headerList = null;
    private List<THM_IMP_FS15> dataList = null;
    private ExcelExportUtil ee = null;
    private List<Map<String, String>> resultCompList = null;
    private List<Map<String, String>> getCompList = null;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");//格式化设置

    @Override
    public Object getReportJson(QueryBean queryBean) throws JsonProcessingException, DataAccessException, IOException {
        Object mapList = null;
        //初始化数据
        init(queryBean);
        //第一步
        mapList = getMapListByThmImpFs15(dateYearMonth, compCodeValue);

        return mapList;
    }

    /**
     * 初始化数据
     *
     * @param queryBean
     */
    private void init(QueryBean queryBean) {
        dateYearMonth = queryBean.getDateYearMonth();
        compCodeValue = queryBean.getCompCodeValue();
        isExport = queryBean.isExport();
        initHead();
    }
    private void initHead() {
        headerList = Lists.newArrayList();
        headerList.add("项目");
        headerList.add("行次");
        headerList.add("期末金额");
        headerList.add("年初金额");
        headerList.add("项目");
        headerList.add("行次");
        headerList.add("期末金额");
        headerList.add("年初金额");
    }

    /**
     *
     * @param dateYearMonth
     * @param compCodeValue
     * @return
     * @throws IOException
     */
    public Object getMapListByThmImpFs15(String dateYearMonth, List<String> compCodeValue) throws JsonProcessingException, DataAccessException, IOException {
        // TODO Auto-generated method stub
        String json = null;
      //取表格列数
        initHead();
        int headerLength = headerList.size();
        //下载模板
        if(compCodeValue.size() > 0){
        	if(compCodeValue.get(0).length() > 4){
        		//导出数据
                title = new ArrayList<String>();
                title.add("");
                if (title.size() != 0 && title != null) {
                    secondTitle = new ArrayList<String>();
                    secondTitle.add("编制单位: " + "中车永济电机有限公司");
                    for (int i = 0; i < headerLength - 2; i++) {
                        secondTitle.add("");
                    }
                    secondTitle.add(dateYearMonth.substring(0, 4) + "年" + dateYearMonth.substring(4, 6) + "月");
                }
//                Map<String, String> map = iReportMapperFS15.selectMaxYear();
//                dateYearMonth = map.get("YEARS");
                Map<String, String> companys = new HashMap<String, String>();
                companys.put("5003", "中车永济电机有限公司");
//                List<THM_IMP_FS15> listField = iReportMapperFS15.selectField(dateYearMonth.substring(0, 4));
                List<THM_IMP_FS15> listField = iReportMapperFS15.selectDataFS15(dateYearMonth, compCodeValue.get(0).substring(0,4));
                resultDataRowList = export(listField);
                ee = new ExcelExportUtil("资产负债表", secondTitle, headerList, "（合并）");
                List<List<Object>> dataLists = Lists.newArrayList();
                for (int j = 0; j < resultDataRowList.size(); j++) {
                	dataLists.add(resultDataRowList.get(j));
                }
                
                for (int i = 0; i < resultDataRowList.size(); i++) {
                    Row row = ee.addRow();
                    for (int j = 0; j < dataLists.get(i).size(); j++) {
                    	String val = StringUtil.nullToStr(dataLists.get(i).get(j));
                    	try{
                    		if(j == 2 || j == 3 || j == 6 || j ==7){
//                    			if(Integer.valueOf(num) == 1 || Integer.valueOf(num) == 23
//                    				|| Integer.valueOf(num) == 55 || Integer.valueOf(num) == 82){
//                    				ee.addCell(row, row.getRowNum(), j, "——", 3, true, String.class);
//                    			}else{
//                    				ee.addCell(row, row.getRowNum(), j, "", 3, true, String.class);
//                    			}
                    			ee.addCell(row, row.getRowNum(), j, "", 3, true, String.class);
                    		}else if(j == 1 || j == 5){
                    			ee.addCell(row, row.getRowNum(), j, val, 2, true, String.class);
                    			
                    		}else{
                    			ee.addCell(row, row.getRowNum(), j, val.replaceAll("&nbsp;", " "), 1, true, String.class);
                    		}
                        }catch(java.lang.IllegalArgumentException e){
                        	e.printStackTrace();
                        }
                    }
                }
                return ee;
        	}
        }
        //取出公司编制单位
        resultCompList = iReportMapperFS15.selectCompany(compCodeValue);
        Map<String, String> company = new HashMap<String, String>();
        for (int i = 0; i < resultCompList.size(); i++) {
            company.put((String) resultCompList.get(i).get("COMP_CODE"), resultCompList.get(i).get("TXTMD"));
        }
//        dataList = iReportMapperFS15.selectData(dateYearMonth, compCodeValue);
        dataList = iReportMapperFS15.selectDataFS15(dateYearMonth, compCodeValue.get(0));
        
        if (!isExport) {
        	int count =0 ;
        	List<THM_IMP_FS15> tmpImp = new ArrayList<THM_IMP_FS15>();
        	for (THM_IMP_FS15 valueOne : dataList) {
        		THM_IMP_FS15 imp = new THM_IMP_FS15();
        		if(count == dataList.size()/2 || count == 0){
        			imp.setItemname(headerList.get(0));
        			imp.setItemno(headerList.get(1));
        			imp.setFinalmoney(headerList.get(2));
        			imp.setStartmoney(headerList.get(3));
        			tmpImp.add(imp);
        			imp = new THM_IMP_FS15();
        		}
    			imp.setItemname(valueOne.getItemname());
    			imp.setItemno(valueOne.getItemno());
    			imp.setFinalmoney(valueOne.getFinalmoney());
    			imp.setStartmoney(valueOne.getStartmoney());
    			tmpImp.add(imp);
    			imp = new THM_IMP_FS15();
    			count ++;
        	}
        	
        	int size = tmpImp.size();
            if(size%2 != 0){
            	THM_IMP_FS15 imp = new THM_IMP_FS15();
            	imp.setItemno(String.valueOf(size+1));
            	imp.setItemname("");
            	imp.setStartmoney("");
            	imp.setFinalmoney("");
            	tmpImp.add(imp);
            }
            
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(tmpImp);
            log.info("json+++++++++++++++"+json);
        } else {
            //导出数据
        	
            title = new ArrayList<String>();
            for (int i = 0; i < compCodeValue.size(); i++) {
                title.add(company.get(compCodeValue.get(i)));
            }
            if (title.size() != 0 && title != null) {
                secondTitle = new ArrayList<String>();
                String str = " ";
                for (int i = 0; i < title.size(); i++) {
                    str = str + title.get(i).toString() + " ";
                }
                secondTitle.add("编制单位: " + str);
                for (int i = 0; i < headerLength - 2; i++) {
                    secondTitle.add("");
                }
                secondTitle.add(dateYearMonth.substring(0, 4) + "年" + dateYearMonth.substring(4, 6) + "月");
            }
            resultDataRowList = export(dataList);
            ee = new ExcelExportUtil("资产负债表", secondTitle, headerList, "(合并)");
            List<List<Object>> dataList = Lists.newArrayList();
            for (int j = 0; j < resultDataRowList.size(); j++) {
                dataList.add(resultDataRowList.get(j));
            }
            for (int i = 0; i < resultDataRowList.size(); i++) {
                Row row = ee.addRow();
                for (int j = 0; j < dataList.get(i).size(); j++) {
                    String val = StringUtil.nullToStr(dataList.get(i).get(j));
                    if ((j >= 2 && j <= 3) || (j >= 6 && j <= 7)) {//数值处理
                        try{
                            ee.addCell(row, row.getRowNum(), j, decimalFormat.format(val), 3, true, Double.class);
                        }catch(java.lang.IllegalArgumentException e){
                            ee.addCell(row, row.getRowNum(), j, val, 3, true, Double.class);
                        }
                    } else if (j == 0 || j == 4) {//项目处理
                        /*String itemRn = StringUtil.nullToStr(dataList.get(i).get(j+1));
                        if ("22".equals(itemRn)
                                || "47".equals(itemRn)
                                || "51".equals(itemRn)
                                || "78".equals(itemRn)
                                || "88".equals(itemRn)
                                || "89".equals(itemRn)
                                || "101".equals(itemRn)
                                || "102".equals(itemRn)) {
                            ee.addCell(row, row.getRowNum(), j, val.replaceAll("&nbsp;", " "), 4, true, String.class);
                        } else {
                            ee.addCell(row, row.getRowNum(), j, val.replaceAll("&nbsp;", " "), 1, true, String.class);
                        }*/
                        ee.addCell(row, row.getRowNum(), j, val.replaceAll("&nbsp;", " "), 1, true, String.class);
                    } else {//行次
                        ee.addCell(row, row.getRowNum(), j, val, 2, true, String.class);
                    }
                }
            }
            return ee;
        }
        return json;
    }

    /**
     * 导出数据
     *
     * @param dataList
     * @throws IOException
     */
    private List<List<Object>> export(List<THM_IMP_FS15> data) throws IOException {
        // TODO Auto-generated method stub
        //
        String itemname = null;
        String regEx_special = "\\&[a-zA-Z]{1,10};";
        Pattern pattern = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
        List<List<Object>> dataRowList = Lists.newArrayList();
        List<Object> list = new ArrayList<Object>();
        Matcher matcher = null;
        NumberFormat decimalFormat = new DecimalFormat("###,###.##");
        int size = data.size();
        if(size%2 != 0){
        	THM_IMP_FS15 imp = new THM_IMP_FS15();
        	imp.setItemno(String.valueOf(size+1));
        	imp.setItemname("");
        	imp.setFinalmoney("");
        	imp.setStartmoney("");
        	data.add(imp);
        }
        size = data.size();
        
        for (THM_IMP_FS15 valueOne : data) {
            for (THM_IMP_FS15 valueTwo : data) {
                int step = Integer.parseInt(valueTwo.getItemno()) - Integer.parseInt(valueOne.getItemno());
                if (step == data.size()/2) {
                    itemname = valueOne.getItemname();
                    matcher = pattern.matcher(itemname);
                    itemname = matcher.replaceAll(" "); // 过滤标签
                    list.add(itemname);
                    list.add(Integer.parseInt(valueOne.getItemno()) + ""); //行次
                	if("——".equals(valueOne.getStartmoney()) || "——".equals(valueOne.getFinalmoney())){
                		list.add(valueOne.getFinalmoney()); //期末金额
                		list.add(valueOne.getStartmoney()); //年初金额
                	}else if("".equals(valueOne.getStartmoney()) || " ".equals(valueOne.getStartmoney()) || "".equals(valueOne.getFinalmoney()) || " ".equals(valueOne.getFinalmoney())){
                		list.add(""); //期末金额
                		list.add(""); //年初金额
                	}else{
                		if(valueOne.getFinalmoney() == null){
                        	list.add(valueOne.getFinalmoney()); //期末金额
                		}
                		if(valueOne.getStartmoney() == null){
                			list.add(valueOne.getStartmoney()); //年初金额
                		}
                		if(valueOne.getStartmoney() != null && valueOne.getFinalmoney() != null){
//                        	list.add(decimalFormat.format(Double.valueOf(valueOne.getFinalmoney()))); //期末金额
//                        	list.add(decimalFormat.format(Double.valueOf(valueOne.getStartmoney()))); //年初金额
                			if(!FileUtils.isNumeric(valueOne.getFinalmoney().replaceAll(",", "").replace("，", ""))){
                				list.add(valueOne.getFinalmoney()); //
                			}else{
                				list.add(decimalFormat.format(Double.valueOf(valueOne.getFinalmoney()))); //期末金额
                			}
                			if(!FileUtils.isNumeric(valueOne.getStartmoney().replaceAll(",", "").replace("，", ""))){
                				list.add(valueOne.getStartmoney()); //
                			}else{
                				list.add(decimalFormat.format(Double.valueOf(valueOne.getStartmoney()))); //年初金额
                			}
                        	
                		}
                	}
                    
                    itemname = valueTwo.getItemname();
                    matcher = pattern.matcher(itemname);
                    itemname = matcher.replaceAll(" "); // 过滤标签
                    list.add(itemname);
                    list.add(Integer.parseInt(valueTwo.getItemno()) + ""); //行次
                    if("——".equals(valueTwo.getStartmoney()) || "——".equals(valueTwo.getFinalmoney())){
                		list.add(valueTwo.getFinalmoney()); //期末金额
                		list.add(valueTwo.getStartmoney()); //年初金额
                	}else if("".equals(valueTwo.getStartmoney()) || " ".equals(valueTwo.getStartmoney()) || "".equals(valueTwo.getFinalmoney()) || " ".equals(valueTwo.getFinalmoney())){
                		list.add(""); //期末金额
                		list.add(""); //年初金额
                	}else{
                		if(valueTwo.getFinalmoney() == null){
                        	list.add(valueTwo.getFinalmoney()); //期末金额
                		}
                		if(valueTwo.getStartmoney() == null){
                			list.add(valueTwo.getStartmoney()); //年初金额
                		}
                		if(valueTwo.getStartmoney() != null && valueTwo.getFinalmoney() != null){
//                			list.add(decimalFormat.format(Double.valueOf(valueTwo.getFinalmoney()))); //期末金额
//                        	list.add(decimalFormat.format(Double.valueOf(valueTwo.getStartmoney()))); //年初金额
                			if(!FileUtils.isNumeric(valueTwo.getFinalmoney().replaceAll(",", "").replace("，", ""))){
                				list.add(valueTwo.getFinalmoney()); //
                			}else{
                				list.add(decimalFormat.format(Double.valueOf(valueTwo.getFinalmoney()))); //期末金额
                			}
                			if(!FileUtils.isNumeric(valueTwo.getStartmoney().replaceAll(",", "").replace("，", ""))){
                				list.add(valueTwo.getStartmoney()); //
                			}else{
                				list.add(decimalFormat.format(Double.valueOf(valueTwo.getStartmoney()))); //年初金额
                			}
                		}
                	}
                    
                    dataRowList.add(list);
                    list = new ArrayList<Object>();
                }
            }

        }
        return dataRowList;
    }

    @Override
    public Object importData(String filePath) throws JsonProcessingException,
            IOException, DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException {
        // TODO Auto-generated method stub
        String yearMonth = null;
        Matcher matcher = null;
        String json = null;
        Map<String, Object> listMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        //导入数据
        ExcelImportUtil<?> ei = new ExcelImportUtil(filePath, 3);
        headerList = Lists.newArrayList();
        headerList.add("项目");
        headerList.add("行次");
        headerList.add("期末金额");
        headerList.add("年初金额");
        headerList.add("项目");
        headerList.add("行次");
        headerList.add("期末金额");
        headerList.add("年初金额");
        json = ei.checkHeadList(headerList,listMap);
        if(null!=json){
            return json;
        }
        
        String company = ei.getCellValue(2, 0).toString();
        String time = ei.getCellValue(2, ei.getLastCellNum()-1).toString();
        Pattern p = Pattern.compile("[^0-9]");
        matcher = p.matcher(time);
        if (company.indexOf(":") > 0) {
            company = company.substring(company.indexOf(":") + 1, company.length()).trim();
        } else if (company.indexOf("：") > 0) {
            company = company.substring(company.indexOf("：") + 1, company.length()).trim();
        }
        
        if (company.trim().length() == 0) {
            listMap.put("ERRORCOMPANEY", "未填写编制单位!");
        }
        getCompList = iReportMapperFS15.selectCompanyCode(company.trim());
        if(getCompList != null){
            if (getCompList.size() <= 0) {
                listMap.put("ERRORCOMPANEY", "编制单位填写错误，没有该编制单位!");
            }else{
            	company = getCompList.get(0).get("COMP_CODE");
            }
            yearMonth = matcher.replaceAll("").trim();
            if (!FileUtils.isValidDate(yearMonth)) {
                listMap.put("ERRORTIME", "日期填写错误!");
            }
        }else{
        	listMap.put("ERRORCOMPANEY", "编制单位填写错误，没有该编制单位!");
        }
        
        //如果验证数据没有问题，则返回所有的数据，标志位为1，若是数据有问题，则返回不正确数据，标准为0
        List<InsertFieldTHM_FS15> list = ei.getDataList(InsertFieldTHM_FS15.class, "FS15");
        
        List<InsertFieldTHM_FS15> tmpDataList = new ArrayList<InsertFieldTHM_FS15>();
        InsertFieldTHM_FS15 tmp = new InsertFieldTHM_FS15();

        //取表头
//    	tmp.setProjectOne(ei.getRow(ei.getHeaderNum()).getCell(0).getStringCellValue());
//        tmp.setLineNumOne(ei.getRow(ei.getHeaderNum()).getCell(1).getStringCellValue());
//        tmp.setEndAccountOne(ei.getRow(ei.getHeaderNum()).getCell(2).getStringCellValue());
//        tmp.setStartAccountOne(ei.getRow(ei.getHeaderNum()).getCell(3).getStringCellValue());
//        tmp.setYearMonth(yearMonth);
//        tmp.setCompanyCode(company);
//        tmpDataList.add(tmp);
//        tmp = new InsertFieldTHM_FS15();
        
        //导入数据处理
        for (int i = 0; i < list.size(); i++) {
            if (!StringUtil.isEmpty(list.get(i).getLineNumOne())) {
            	tmp.setProjectOne(list.get(i).getProjectOne());
                tmp.setLineNumOne(list.get(i).getLineNumOne());
                tmp.setEndAccountOne(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""));
                tmp.setStartAccountOne(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""));
                tmp.setYearMonth(yearMonth);
                tmp.setCompanyCode(company);
                tmpDataList.add(tmp);
                tmp = new InsertFieldTHM_FS15();
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (!StringUtil.isEmpty(list.get(i).getLineNumTwo())) {
            	tmp.setProjectOne(list.get(i).getProjectTwo());
                tmp.setLineNumOne(list.get(i).getLineNumTwo());
                tmp.setEndAccountOne(list.get(i).getEndAccountTwo().replaceAll(",", "").replace("，", ""));
                tmp.setStartAccountOne(list.get(i).getStartAccountTwo().replaceAll(",", "").replace("，", ""));
                tmp.setYearMonth(yearMonth);
                tmp.setCompanyCode(company);
                tmpDataList.add(tmp);
                tmp = new InsertFieldTHM_FS15();
            }
        }

        //校验数据是否准确，加和逻辑，分项与统计是否匹配
//        List<String> valids = checkData(tmpDataList, yearMonth);
//        if(valids.size() > 0){
//        	listMap.put("ERRORDATA", valids);
//        	errorMap.put("ERROR", listMap);
//        	return mapper.writeValueAsString(errorMap);
//        }
        
        iReportMapperFS15.saveOrUpdateDataFS15(yearMonth, company, tmpDataList);
        listMap.put("SUCCESSES", "导入成功!");
        json = mapper.writeValueAsString(listMap);
        return json;
    }

    /**
     * 校验数据分项与统计是否匹配
     * @param list
     * @return 
     */
    private List<String> checkData(List<InsertFieldTHM_FS15> list, String yearMonth) throws JsonProcessingException {
        List<String> valids = new ArrayList<String>();
        int num1 = 5;
        int num2 = 5;
        int columnstartAccountOne = 3;
        int columnstarAccountTwo = 4;
        int columnendAccountOne = 7;
        int columnendAccountTwo = 8;
        Map<String, String> map = iReportMapperFS15.selectMaxYear();
        dateYearMonth = map.get("YEARS");
        if(valids.size() == 0){
        	if("2015".equals(dateYearMonth)){
        		for(int i=0; i<list.size(); i++){
                	if(list.get(i).getLineNumOne() != null && !"".equals(list.get(i).getLineNumOne())){
                		if(Integer.parseInt(list.get(i).getLineNumOne()) >=1 && Integer.parseInt(list.get(i).getLineNumOne()) <= 51){
                			if(Integer.parseInt(list.get(i).getLineNumOne()) != 1 && Integer.parseInt(list.get(i).getLineNumOne()) != 23
                        			&& Integer.parseInt(list.get(i).getLineNumOne()) != 48 && Integer.parseInt(list.get(i).getLineNumOne()) != 49
                        			&& Integer.parseInt(list.get(i).getLineNumOne()) != 50){
                        		if(!FileUtils.isNumeric(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""))){
                        			valids.add("第 "+num1+" 行第 "+columnstarAccountTwo+" 列数据格式错误!");
                            	}else if(!FileUtils.isNumeric(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""))){
                        			valids.add("第 "+num1+" 行第 "+columnstartAccountOne+" 列数据格式错误!");
                            	}
                        		
                        	}
                			num1 ++;
                		}
            			if(Integer.parseInt(list.get(i).getLineNumOne()) >= 52 && Integer.parseInt(list.get(i).getLineNumOne()) <= 102){
                			if(Integer.parseInt(list.get(i).getLineNumOne()) != 52 && Integer.parseInt(list.get(i).getLineNumOne()) != 79
                        			&& Integer.parseInt(list.get(i).getLineNumOne()) != 90){
                        		if(!FileUtils.isNumeric(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""))){
                        			valids.add("第 "+num2+" 行第 "+columnendAccountTwo+" 列数据格式错误!");
                            	}else if(!FileUtils.isNumeric(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""))){
                        			valids.add("第 "+num2+" 行第 "+columnendAccountOne+" 列数据格式错误!");
                            	}
                        	}
                			num2 ++;
                		}
                	}
                }
        		valids.addAll(check2015(list));
        	}else if("2016".equals(dateYearMonth)){
        		for(int i=0; i<list.size(); i++){
                	if(list.get(i).getLineNumOne() != null && !"".equals(list.get(i).getLineNumOne())){
                		if(Integer.parseInt(list.get(i).getLineNumOne()) >=1 && Integer.parseInt(list.get(i).getLineNumOne()) <= 54){
                			if(Integer.parseInt(list.get(i).getLineNumOne()) != 1 && Integer.parseInt(list.get(i).getLineNumOne()) != 23
                        			&& Integer.parseInt(list.get(i).getLineNumOne()) != 48 && Integer.parseInt(list.get(i).getLineNumOne()) != 49
                        			&& Integer.parseInt(list.get(i).getLineNumOne()) != 50){
                        		if(!FileUtils.isNumeric(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""))){
                        			valids.add("第 "+num1+" 行第 "+columnstarAccountTwo+" 列数据格式错误!");
                            	}else if(!FileUtils.isNumeric(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""))){
                        			valids.add("第 "+num1+" 行第 "+columnstartAccountOne+" 列数据格式错误!");
                            	}
                        		
                        	}
                			num1 ++;
                		}
            			if(Integer.parseInt(list.get(i).getLineNumOne()) >= 55 && Integer.parseInt(list.get(i).getLineNumOne()) <= 108){
                			if(Integer.parseInt(list.get(i).getLineNumOne()) != 52 && Integer.parseInt(list.get(i).getLineNumOne()) != 79
                        			&& Integer.parseInt(list.get(i).getLineNumOne()) != 90){
                        		if(!FileUtils.isNumeric(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""))){
                        			valids.add("第 "+num2+" 行第 "+columnendAccountTwo+" 列数据格式错误!");
                            	}else if(!FileUtils.isNumeric(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""))){
                        			valids.add("第 "+num2+" 行第 "+columnendAccountOne+" 列数据格式错误!");
                            	}
                        	}
                			num2 ++;
                		}
                	}
                	
                }
        		valids.addAll(check2016(list));
        	}
        }
        return valids;
    }
    //2015年数据校验
    public List<String> check2015(List<InsertFieldTHM_FS15> list){
    	List<String> valids = new ArrayList<String>();
    	//8小于等于7 年初金额
        Double item8_startAccountOne = StringUtils.isEmpty(list.get(7).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(7).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item7_startAccountOne = StringUtils.isEmpty(list.get(6).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(6).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(item8_startAccountOne > item7_startAccountOne){
            valids.add("行次8年初金额 大于 行次7年初金额");
        }
    	//8小于等于7 期末余额
        Double item8_endAccountOne = StringUtils.isEmpty(list.get(7).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(7).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item7_endAccountOne = StringUtils.isEmpty(list.get(6).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(6).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        if(item8_endAccountOne > item7_endAccountOne){
            valids.add("行次8期末余额 大于 行次7期末余额");
        }
        
        //18加19小于等于17 期末余额
        Double item18_endAccountOne = StringUtils.isEmpty(list.get(17).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(17).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item19_endAccountOne = StringUtils.isEmpty(list.get(18).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(18).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item17_endAccountOne = StringUtils.isEmpty(list.get(16).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(16).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item18_endAccountOne + item19_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item17_endAccountOne){
            valids.add("行次18与行次19期末余额之和 不等于 行次17期末余额");
        }
        //18加19小于等于17 年初金额
        Double item18_startAccountOne = StringUtils.isEmpty(list.get(17).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(17).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item19_startAccountOne = StringUtils.isEmpty(list.get(18).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(18).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item17_startAccountOne = StringUtils.isEmpty(list.get(16).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(16).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item18_startAccountOne + item19_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item17_startAccountOne){
            valids.add("行次18与行次19年初金额之和 不等于 行次17年初金额");
        }
        
        //2+3+4+5+6+7+9+…17+20+21 = 22 期末余额
        Double item22_endAccountOne = StringUtils.isEmpty(list.get(21).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(21).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item2_21_endAccountOne = 0d;
        for(int i=1;i<21;i++){
        	if(i != 7 && i != 17 && i != 18){
        		item2_21_endAccountOne += StringUtils.isEmpty(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        	}
        }
        if(!Objects.equals(item22_endAccountOne, new BigDecimal(item2_21_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())){
            valids.add("行次2到行次21 期末余额之和 不等于 行次22期末余额");
        }
        //2+3+4+5+6+7+9+…17+20+21 = 22 年初金额
        Double item22_startAccountOne = StringUtils.isEmpty(list.get(21).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(21).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item2_21_startAccountOne = 0d;
        for(int i=1;i<21;i++){
        	if(i != 7 && i != 17 && i != 18){
        		item2_21_startAccountOne += StringUtils.isEmpty(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        	}
        	
        }
        if(!Objects.equals(item22_startAccountOne, new BigDecimal(item2_21_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())){
            valids.add("行次2到行次21 年初金额之和 不等于 行次22年初金额");
        }
        
        //30-31 = 32 期末余额
        Double item31_endAccountOne = StringUtils.isEmpty(list.get(30).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(30).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item30_endAccountOne = StringUtils.isEmpty(list.get(29).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(29).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item32_endAccountOne = StringUtils.isEmpty(list.get(31).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(31).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item30_endAccountOne - item31_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item32_endAccountOne){
            valids.add("行次30与行次31期末余额之差 不等于 行次32期末余额");
        }
        //30-31 = 32 年初金额
        Double item31_startAccountOne = StringUtils.isEmpty(list.get(30).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(30).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item30_startAccountOne = StringUtils.isEmpty(list.get(29).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(29).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item32_startAccountOne = StringUtils.isEmpty(list.get(31).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(31).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item30_startAccountOne - item31_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item32_startAccountOne){
            valids.add("行次30与行次31年初金额之差 不等于 行次32年初金额");
        }
        
        //32-33 = 34 期末余额
        Double item33_endAccountOne = StringUtils.isEmpty(list.get(32).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(32).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item34_endAccountOne = StringUtils.isEmpty(list.get(33).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(33).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item32_endAccountOne - item33_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item34_endAccountOne){
            valids.add("行次32与行次33期末余额之差 不等于 行次34期末余额");
        }
        //32-33 = 34 年初金额
        Double item33_startAccountOne = StringUtils.isEmpty(list.get(32).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(32).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item34_startAccountOne = StringUtils.isEmpty(list.get(33).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(33).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item32_startAccountOne - item33_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item34_startAccountOne){
            valids.add("行次32与行次33年初金额之差 不等于 行次34年初金额");
        }
        
        //46小于等于45 期末余额
        Double item46_endAccountOne = StringUtils.isEmpty(list.get(45).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(45).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item45_endAccountOne = StringUtils.isEmpty(list.get(44).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(44).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        if(item46_endAccountOne > item45_endAccountOne){
            valids.add("行次46期末余额 大于 行次45期末余额");
        }
        //46小于等于45 年初金额
        Double item46_startAccountOne = StringUtils.isEmpty(list.get(45).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(45).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item45_startAccountOne = StringUtils.isEmpty(list.get(44).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(44).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(item46_startAccountOne > item45_startAccountOne){
            valids.add("行次46年初金额 大于 行次45年初金额");
        }
        
        //24…29+34+…45=47 期末余额
        Double item24_45_endAccountOne = 0d;
        Double item47_endAccountOne = StringUtils.isEmpty(list.get(46).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(46).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        for(int i=23;i<29;i++){
            item24_45_endAccountOne += StringUtils.isEmpty(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        }
        for(int i=33;i<45;i++){
            item24_45_endAccountOne += StringUtils.isEmpty(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        }
        if(!Objects.equals(item47_endAccountOne, new BigDecimal(item24_45_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())){
            valids.add("行次24到行次29与行次34到行次45 期末余额之和 不等于 行次47期末余额");
        }
        //24…29+34+…45=47 年初金额
        Double item24_45_startAccountOne = 0d;
        Double item47_startAccountOne = StringUtils.isEmpty(list.get(46).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(46).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        for(int i=23;i<29;i++){
            item24_45_startAccountOne += StringUtils.isEmpty(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        }
        for(int i=33;i<45;i++){
            item24_45_startAccountOne += StringUtils.isEmpty(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        }
        if(!Objects.equals(item47_startAccountOne, new BigDecimal(item24_45_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())){
            valids.add("行次24到行次29与行次34到行次45 年初金额之和 不等于 行次47年初金额");
        }
        
        //64加65小于等于63 期末余额
        Double item64_endAccountOne = StringUtils.isEmpty(list.get(63).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(63).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item65_endAccountOne = StringUtils.isEmpty(list.get(64).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(64).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item63_endAccountOne = StringUtils.isEmpty(list.get(62).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(62).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item64_endAccountOne + item65_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() > item63_endAccountOne){
            valids.add("行次64与行次65期末余额之和 大于 行次63期末余额");
        }
        //64加65小于等于63 年初金额
        Double item64_startAccountOne = StringUtils.isEmpty(list.get(63).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(63).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item65_startAccountOne = StringUtils.isEmpty(list.get(64).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(64).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item63_startAccountOne = StringUtils.isEmpty(list.get(62).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(62).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item64_startAccountOne + item65_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() > item63_startAccountOne){
            valids.add("行次64与行次65年初金额之和 大于 行次63年初金额");
        }
        
        //68小于等于67 期末余额
        Double item68_endAccountOne = StringUtils.isEmpty(list.get(67).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(67).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item67_endAccountOne = StringUtils.isEmpty(list.get(66).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(66).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        if(item68_endAccountOne > item67_endAccountOne){
            valids.add("行次68期末余额 大于 行次67期末余额");
        }
        //68小于等于67 年初金额
        Double item68_startAccountOne = StringUtils.isEmpty(list.get(67).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(67).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item67_startAccountOne = StringUtils.isEmpty(list.get(66).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(66).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(item68_startAccountOne > item67_startAccountOne){
            valids.add("行次68年初金额 大于 行次67年初金额");
        }
        
        //53…63+67+69….77=78 期末余额
        Double item53_77_endAccountOne = 0d;
        Double item78_endAccountOne = StringUtils.isEmpty(list.get(77).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(77).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        for(int i=52;i<63;i++){
        	item53_77_endAccountOne += StringUtils.isEmpty(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""));
            
        }
        for(int i=66;i<77;i++){
        	if(i != 67 ){
        		item53_77_endAccountOne += StringUtils.isEmpty(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        	}
            
        }
        if(!Objects.equals(new BigDecimal(item53_77_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), item78_endAccountOne)){
            valids.add("行次53到行次63与行次69到行次77 期末余额之和 不等于 行次78期末余额");
        }
        //53…63+67+69….77=78 年初金额
        Double item53_77_startAccountOne = 0d;
        Double item78_startAccountOne = StringUtils.isEmpty(list.get(77).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(77).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        for(int i=52;i<63;i++){
        		item53_77_startAccountOne += StringUtils.isEmpty(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""));
            
        }
        for(int i=66;i<77;i++){
        	if(i != 67 ){
        		item53_77_startAccountOne += StringUtils.isEmpty(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        	}
            
        }
        if(!Objects.equals(new BigDecimal(item53_77_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), item78_startAccountOne)){
            valids.add("行次53到行次63与行次69到行次77 年初金额之和 不等于 行次78年初金额");
        }
        
        //87小于等于86 期末余额
        Double item87_endAccountOne = StringUtils.isEmpty(list.get(86).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(86).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item86_endAccountOne = StringUtils.isEmpty(list.get(85).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(85).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        if(item87_endAccountOne > item86_endAccountOne){
            valids.add("行次87期末余额 大于 行次86期末余额");
        }
        //87小于等于86 年初金额
        Double item87_startAccountOne = StringUtils.isEmpty(list.get(86).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(86).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item86_startAccountOne = StringUtils.isEmpty(list.get(85).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(85).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(item87_startAccountOne > item86_startAccountOne){
            valids.add("行次87年初金额大于 行次86年初金额");
        }
        
        //80…86的和=88 期末余额
        Double item80_86_endAccountOne = 0d;
        Double item88_endAccountOne = StringUtils.isEmpty(list.get(87).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(87).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        for(int i=79;i<86;i++){
            item80_86_endAccountOne += StringUtils.isEmpty(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        }
        if(!Objects.equals(new BigDecimal(item80_86_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), item88_endAccountOne)){
            valids.add("行次80到行次86期末余额之和 不等于 行次88期末余额");
        }
        //80…86的和=88 年初金额
        Double item80_86_startAccountOne = 0d;
        Double item88_startAccountOne = StringUtils.isEmpty(list.get(87).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(87).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        for(int i=79;i<86;i++){
            item80_86_startAccountOne += StringUtils.isEmpty(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        }
        if(!Objects.equals(new BigDecimal(item80_86_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), item88_startAccountOne)){
            valids.add("行次80到行次86年初金额之和 不等于 行次88年初金额");
        }
        
        //78+88=89 期末余额
        Double item89_endAccountOne = StringUtils.isEmpty(list.get(88).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(88).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item78_endAccountOne + item88_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item89_endAccountOne){
            valids.add("行次78与行次88期末余额之和 不等于 行次89期末余额");
        }
         //78+88=89 年初金额
        Double item89_startAccountOne = StringUtils.isEmpty(list.get(88).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(88).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item78_startAccountOne + item88_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item89_startAccountOne){
            valids.add("行次78与行次88年初金额之和 不等于 行次89年初金额");
        }
        
        //91+92-93+94+95+96+97+98=99 期末余额
        Double item91_98_endAccountOne = 0d;
        Double item99_endAccountOne = StringUtils.isEmpty(list.get(98).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(98).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        for(int i=90;i<98;i++){
            if(i==92){
                item91_98_endAccountOne -= StringUtils.isEmpty(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""));
            }else{
                item91_98_endAccountOne += StringUtils.isEmpty(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""));
            }
        }
        if(!Objects.equals(new BigDecimal(item91_98_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), item99_endAccountOne)){
            valids.add("行次91+92-93+94+95+96+97+98期末余额之和 不等于 行次99期末余额");
        }
        //91+92-93+94+95+96+97+98=99 年初金额
        Double item91_98_startAccountOne = 0d;
        Double item99_startAccountOne = StringUtils.isEmpty(list.get(98).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(98).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        for(int i=90;i<98;i++){
            if(i==92){
                item91_98_startAccountOne -= StringUtils.isEmpty(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""));
            }else{
                item91_98_startAccountOne += StringUtils.isEmpty(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""));
            }
        }
        if(!Objects.equals(new BigDecimal(item91_98_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), item99_startAccountOne)){
            valids.add("行次91+92-93+94+95+96+97+98年初金额之和 不等于 行次99年初金额");
        }
        
        //99+100=101 期末余额
        Double item100_endAccountOne = StringUtils.isEmpty(list.get(99).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(99).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item101_endAccountOne = StringUtils.isEmpty(list.get(100).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(100).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item99_endAccountOne + item100_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item101_endAccountOne){
            valids.add("行次99与行次100期末余额之和 不等于 行次101期末余额");
        }
        //99+100=101 年初金额
        Double item100_startAccountOne = StringUtils.isEmpty(list.get(99).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(99).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item101_startAccountOne = StringUtils.isEmpty(list.get(100).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(100).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item99_startAccountOne + item100_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item101_startAccountOne){
            valids.add("行次99与行次100年初金额之和 不等于 行次101年初金额");
        }
        
        //99+100=101 期末余额
        Double item51_endAccountOne = StringUtils.isEmpty(list.get(50).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(50).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item102_endAccountOne = StringUtils.isEmpty(list.get(101).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(101).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item22_endAccountOne + item47_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != new BigDecimal(item101_endAccountOne + item89_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() 
                && !Objects.equals(item51_endAccountOne, item102_endAccountOne) 
                && item22_endAccountOne + item47_endAccountOne != item51_endAccountOne){
            valids.add("行次22与行次47期末余额之和 不等于 行次51期末余额 且不等于 行次102期末余额");
        }
        //99+100=101 年初金额
        Double item51_startAccountOne = StringUtils.isEmpty(list.get(50).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(50).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item102_startAccountOne = StringUtils.isEmpty(list.get(101).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(101).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item22_startAccountOne + item47_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != new BigDecimal(item101_startAccountOne + item89_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() 
                && !Objects.equals(item51_startAccountOne, item102_startAccountOne) 
                && new BigDecimal(item22_startAccountOne + item47_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item51_startAccountOne){
            valids.add("行次22与行次47年初金额之和 不等于 行次51年初金额 且不等于 行次102年初金额");
        }
        //验证行次1、23、48、49、50、52、79数据为空，不用导入导出
        if(!StringUtils.isEmpty(list.get(0).getStartAccountOne().trim()) || !StringUtils.isEmpty(list.get(0).getEndAccountOne().trim())){
        	valids.add("行次1  应为空");
        }else if(!StringUtils.isEmpty(list.get(22).getStartAccountOne().trim()) || !StringUtils.isEmpty(list.get(22).getEndAccountOne().trim())){
        	valids.add("行次23 应为空");
        }else if(!StringUtils.isEmpty(list.get(47).getStartAccountOne().trim()) || !StringUtils.isEmpty(list.get(47).getEndAccountOne().trim())){
        	valids.add("行次48  应为空");
        }else if(!StringUtils.isEmpty(list.get(48).getStartAccountOne().trim()) || !StringUtils.isEmpty(list.get(48).getEndAccountOne().trim())){
        	valids.add("行次49 应为空");
        }else if(!StringUtils.isEmpty(list.get(49).getStartAccountOne().trim()) || !StringUtils.isEmpty(list.get(49).getEndAccountOne().trim())){
        	valids.add("行次50 应为空");
        }else if(!StringUtils.isEmpty(list.get(51).getStartAccountOne().trim()) || !StringUtils.isEmpty(list.get(51).getEndAccountOne().trim())){
        	valids.add("行次52 应为空");
        }else if(!StringUtils.isEmpty(list.get(78).getStartAccountOne().trim()) || !StringUtils.isEmpty(list.get(78).getEndAccountOne().trim())){
        	valids.add("行次79 应为空");
        }
        return valids;
    }
    
    //2016年数据校验
    public List<String> check2016(List<InsertFieldTHM_FS15> list){
    	List<String> valids = new ArrayList<String>();
    	//8小于等于7 年初金额
        Double item8_startAccountOne = StringUtils.isEmpty(list.get(7).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(7).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item7_startAccountOne = StringUtils.isEmpty(list.get(6).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(6).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(item8_startAccountOne > item7_startAccountOne){
            valids.add("行次8年初金额 大于 行次7年初金额");
        }
    	//8小于等于7 期末余额
        Double item8_endAccountOne = StringUtils.isEmpty(list.get(7).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(7).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item7_endAccountOne = StringUtils.isEmpty(list.get(6).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(6).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        if(item8_endAccountOne > item7_endAccountOne){
            valids.add("行次8期末余额 大于 行次7期末余额");
        }
        
        //18加19小于等于17 期末余额
        Double item18_endAccountOne = StringUtils.isEmpty(list.get(17).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(17).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item19_endAccountOne = StringUtils.isEmpty(list.get(18).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(18).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item17_endAccountOne = StringUtils.isEmpty(list.get(16).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(16).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item18_endAccountOne + item19_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item17_endAccountOne){
            valids.add("行次18与行次19期末余额之和 不等于 行次17期末余额");
        }
        //18加19小于等于17 年初金额
        Double item18_startAccountOne = StringUtils.isEmpty(list.get(17).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(17).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item19_startAccountOne = StringUtils.isEmpty(list.get(18).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(18).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item17_startAccountOne = StringUtils.isEmpty(list.get(16).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(16).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item18_startAccountOne + item19_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item17_startAccountOne){
            valids.add("行次18与行次19年初金额之和 不等于 行次17年初金额");
        }
        
        //2+3+4+5+6+7+9+…17+20+21 = 22 期末余额
        Double item22_endAccountOne = StringUtils.isEmpty(list.get(21).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(21).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item2_21_endAccountOne = 0d;
        for(int i=1;i<21;i++){
        	if(i != 7 && i != 17 && i != 18){
        		item2_21_endAccountOne += StringUtils.isEmpty(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        	}
        }
        if(!Objects.equals(item22_endAccountOne, new BigDecimal(item2_21_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())){
            valids.add("行次2到行次21 期末余额之和 不等于 行次22期末余额");
        }
        //2+3+4+5+6+7+9+…17+20+21 = 22 年初金额
        Double item22_startAccountOne = StringUtils.isEmpty(list.get(21).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(21).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item2_21_startAccountOne = 0d;
        for(int i=1;i<21;i++){
        	if(i != 7 && i != 17 && i != 18){
        		item2_21_startAccountOne += StringUtils.isEmpty(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        	}
        	
        }
        if(!Objects.equals(item22_startAccountOne, new BigDecimal(item2_21_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())){
            valids.add("行次2到行次21 年初金额之和 不等于 行次22年初金额");
        }
        
        //30-31 = 32 期末余额
        Double item31_endAccountOne = StringUtils.isEmpty(list.get(30).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(30).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item30_endAccountOne = StringUtils.isEmpty(list.get(29).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(29).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item32_endAccountOne = StringUtils.isEmpty(list.get(31).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(31).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item30_endAccountOne - item31_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item32_endAccountOne){
            valids.add("行次30与行次31期末余额之差 不等于 行次32期末余额");
        }
        //30-31 = 32 年初金额
        Double item31_startAccountOne = StringUtils.isEmpty(list.get(30).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(30).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item30_startAccountOne = StringUtils.isEmpty(list.get(29).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(29).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item32_startAccountOne = StringUtils.isEmpty(list.get(31).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(31).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item30_startAccountOne - item31_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item32_startAccountOne){
            valids.add("行次30与行次31年初金额之差 不等于 行次32年初金额");
        }
        
        //32-33 = 34 期末余额
        Double item33_endAccountOne = StringUtils.isEmpty(list.get(32).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(32).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item34_endAccountOne = StringUtils.isEmpty(list.get(33).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(33).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item32_endAccountOne - item33_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item34_endAccountOne){
            valids.add("行次32与行次33期末余额之差 不等于 行次34期末余额");
        }
        //32-33 = 34 年初金额
        Double item33_startAccountOne = StringUtils.isEmpty(list.get(32).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(32).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item34_startAccountOne = StringUtils.isEmpty(list.get(33).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(33).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item32_startAccountOne - item33_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item34_startAccountOne){
            valids.add("行次32与行次33年初金额之差 不等于 行次34年初金额");
        }
        
        //46小于等于45 期末余额
        Double item46_endAccountOne = StringUtils.isEmpty(list.get(45).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(45).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item45_endAccountOne = StringUtils.isEmpty(list.get(44).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(44).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        if(item46_endAccountOne > item45_endAccountOne){
            valids.add("行次46期末余额 大于 行次45期末余额");
        }
        //46小于等于45 年初金额
        Double item46_startAccountOne = StringUtils.isEmpty(list.get(45).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(45).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item45_startAccountOne = StringUtils.isEmpty(list.get(44).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(44).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(item46_startAccountOne > item45_startAccountOne){
            valids.add("行次46年初金额 大于 行次45年初金额");
        }
        
        //24…29+34+…45=47 期末余额
        Double item24_45_endAccountOne = 0d;
        Double item47_endAccountOne = StringUtils.isEmpty(list.get(46).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(46).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        for(int i=23;i<29;i++){
            item24_45_endAccountOne += StringUtils.isEmpty(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        }
        for(int i=33;i<45;i++){
            item24_45_endAccountOne += StringUtils.isEmpty(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        }
        if(!Objects.equals(item47_endAccountOne, new BigDecimal(item24_45_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())){
            valids.add("行次24到行次29与行次34到行次45 期末余额之和 不等于 行次47期末余额");
        }
        //24…29+34+…45=47 年初金额
        Double item24_45_startAccountOne = 0d;
        Double item47_startAccountOne = StringUtils.isEmpty(list.get(46).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(46).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        for(int i=23;i<29;i++){
            item24_45_startAccountOne += StringUtils.isEmpty(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        }
        for(int i=33;i<45;i++){
            item24_45_startAccountOne += StringUtils.isEmpty(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        }
        if(!Objects.equals(item47_startAccountOne, new BigDecimal(item24_45_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())){
            valids.add("行次24到行次29与行次34到行次45 年初金额之和 不等于 行次47年初金额");
        }
        
        //67加68小于等于66 期末余额
        Double item67_endAccountOne = StringUtils.isEmpty(list.get(66).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(66).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item68_endAccountOne = StringUtils.isEmpty(list.get(67).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(67).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item66_endAccountOne = StringUtils.isEmpty(list.get(65).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(65).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item67_endAccountOne + item68_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() > item66_endAccountOne){
            valids.add("行次67与行次68期末余额之和 大于 行次66期末余额");
        }
        //67加68小于等于66 年初金额
        Double item67_startAccountOne = StringUtils.isEmpty(list.get(66).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(66).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item68_startAccountOne = StringUtils.isEmpty(list.get(67).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(67).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item66_startAccountOne = StringUtils.isEmpty(list.get(65).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(65).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item67_startAccountOne + item68_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() > item66_startAccountOne){
            valids.add("行次64与行次65年初金额之和 大于 行次63年初金额");
        }
        
        //71小于等于70 期末余额
        Double item71_endAccountOne = StringUtils.isEmpty(list.get(70).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(70).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item70_endAccountOne = StringUtils.isEmpty(list.get(69).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(69).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        if(item71_endAccountOne > item70_endAccountOne){
            valids.add("行次71期末余额 大于 行次70期末余额");
        }
        //71小于等于70 年初金额
        Double item71_startAccountOne = StringUtils.isEmpty(list.get(70).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(70).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item70_startAccountOne = StringUtils.isEmpty(list.get(69).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(69).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(item71_startAccountOne > item70_startAccountOne){
            valids.add("行次71年初金额 大于 行次70年初金额");
        }
        
        //56…66+70+72….80=81 期末余额
        Double item56_66_endAccountOne = 0d;
        Double item70_80_endAccountOne = 0d;
        Double item81_endAccountOne = StringUtils.isEmpty(list.get(80).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(80).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        for(int i=56;i<67;i++){
        	item56_66_endAccountOne += StringUtils.isEmpty(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""));
            
        }
        for(int i=70;i<81;i++){
        	if(i != 71 ){
        		item70_80_endAccountOne += StringUtils.isEmpty(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        	}
            
        }
        if(!Objects.equals(new BigDecimal(item70_80_endAccountOne + item56_66_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), item81_endAccountOne)){
            valids.add("行次56到行次66与行次70到行次80以及70行次 期末余额之和 不等于 行次81期末余额");
        }
        //56…66+70+72….80=81 年初金额
        Double item56_66_startAccountOne = 0d;
        Double item70_80_startAccountOne = 0d;
        Double item81_startAccountOne = StringUtils.isEmpty(list.get(80).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(80).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        for(int i=56;i<67;i++){
        	item56_66_startAccountOne += StringUtils.isEmpty(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        }
        for(int i=70;i<81;i++){
        	if(i != 71 ){
        		item70_80_startAccountOne += StringUtils.isEmpty(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        	}
            
        }
        if(!Objects.equals(new BigDecimal(item70_80_startAccountOne + item56_66_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), item81_startAccountOne)){
            valids.add("行次56到行次66与行次70到行次80以及70行次 年初金额之和 不等于 行次81年初金额");
        }
        
        //92小于等于91 期末余额
        Double item92_endAccountOne = StringUtils.isEmpty(list.get(91).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(91).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item91_endAccountOne = StringUtils.isEmpty(list.get(90).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(90).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        if(item92_endAccountOne > item91_endAccountOne){
            valids.add("行次92期末余额 大于 行次91期末余额");
        }
        //92小于等于91 年初金额
        Double item92_startAccountOne = StringUtils.isEmpty(list.get(91).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(91).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item91_startAccountOne = StringUtils.isEmpty(list.get(90).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(90).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(item92_startAccountOne > item91_startAccountOne){
            valids.add("行次92年初金额大于 行次91年初金额");
        }
        
        //83…92的和=93 期末余额
        Double item83_92_endAccountOne = 0d;
        Double item93_endAccountOne = StringUtils.isEmpty(list.get(92).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(92).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        for(int i=83;i<93;i++){
            item83_92_endAccountOne += StringUtils.isEmpty(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        }
        if(!Objects.equals(new BigDecimal(item83_92_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), item93_endAccountOne)){
            valids.add("行次83到行次92期末余额之和 不等于 行次93期末余额");
        }
        //83…92的和=93 年初金额
        Double item83_92_startAccountOne = 0d;
        Double item93_startAccountOne = StringUtils.isEmpty(list.get(92).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(92).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        for(int i=83;i<93;i++){
            item83_92_startAccountOne += StringUtils.isEmpty(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        }
        if(!Objects.equals(new BigDecimal(item83_92_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), item93_startAccountOne)){
            valids.add("行次83到行次92年初金额之和 不等于 行次93年初金额");
        }
        
        //81+93=94 期末余额
        Double item94_endAccountOne = StringUtils.isEmpty(list.get(93).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(93).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item81_endAccountOne + item93_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item94_endAccountOne){
            valids.add("行次81与行次93期末余额之和 不等于 行次94期末余额");
        }
        //81+93=94 期末余额
        if(new BigDecimal(item81_startAccountOne + item93_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item93_startAccountOne){
            valids.add("行次81与行次93年初金额之和 不等于 行次94年初金额");
        }
        
        //96+97+98+99+101+102+103+104=105 期末余额
        Double item96_104_endAccountOne = 0d;
        Double item105_endAccountOne = StringUtils.isEmpty(list.get(104).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(104).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        for(int i=96;i<105;i++){
            if(i != 100){
                item96_104_endAccountOne += StringUtils.isEmpty(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getEndAccountOne().replaceAll(",", "").replace("，", ""));
            }
        }
        if(!Objects.equals(new BigDecimal(item96_104_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), item105_endAccountOne)){
            valids.add("行次96+97+98+99+101+102+103+104期末余额之和 不等于 行次105期末余额");
        }
        //96+97+98+99+101+102+103+104 = 105 年初金额
        Double item96_104_startAccountOne = 0d;
        Double item105_startAccountOne = StringUtils.isEmpty(list.get(104).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(104).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        for(int i=96;i<105;i++){
            if(i != 100){
                item96_104_startAccountOne += StringUtils.isEmpty(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getStartAccountOne().replaceAll(",", "").replace("，", ""));
            }
        }
        if(!Objects.equals(new BigDecimal(item96_104_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), item105_startAccountOne)){
            valids.add("行次96+97+98+99+101+102+103+104年初金额之和 不等于 行次105年初金额");
        }
        
        //105+106=107 期末余额
        Double item106_endAccountOne = StringUtils.isEmpty(list.get(105).getEndAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(105).getEndAccountOne().replaceAll(",", "").replace("，", ""));
        Double item107_endAccountOne = StringUtils.isEmpty(list.get(106).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(106).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item105_endAccountOne + item106_endAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item107_endAccountOne){
            valids.add("行次105与行次106期末余额之和 不等于 行次107期末余额");
        }
        //105+106=107 年初金额
        Double item107_startAccountOne = StringUtils.isEmpty(list.get(106).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(106).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        Double item106_startAccountOne = StringUtils.isEmpty(list.get(105).getStartAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(105).getStartAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item105_startAccountOne + item106_startAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item107_startAccountOne){
            valids.add("行次105与行次106年初金额之和 不等于 行次107年初金额");
        }
        
        //验证行次48、49、50、51,52、53数据为空，不用导入导出
        if(!StringUtils.isEmpty(list.get(47).getStartAccountOne().trim()) || !StringUtils.isEmpty(list.get(47).getEndAccountOne().trim())){
        	valids.add("行次48  应为空");
        }else if(!StringUtils.isEmpty(list.get(48).getStartAccountOne().trim()) || !StringUtils.isEmpty(list.get(48).getEndAccountOne().trim())){
        	valids.add("行次49 应为空");
        }else if(!StringUtils.isEmpty(list.get(49).getStartAccountOne().trim()) || !StringUtils.isEmpty(list.get(49).getEndAccountOne().trim())){
        	valids.add("行次50  应为空");
        }else if(!StringUtils.isEmpty(list.get(50).getStartAccountOne().trim()) || !StringUtils.isEmpty(list.get(50).getEndAccountOne().trim())){
        	valids.add("行次51 应为空");
        }else if(!StringUtils.isEmpty(list.get(51).getStartAccountOne().trim()) || !StringUtils.isEmpty(list.get(51).getEndAccountOne().trim())){
        	valids.add("行次52 应为空");
        }else if(!StringUtils.isEmpty(list.get(52).getStartAccountOne().trim()) || !StringUtils.isEmpty(list.get(52).getEndAccountOne().trim())){
        	valids.add("行次53 应为空");
        }
        //1  23   55   82  95 只能为 --
        if("——".equals(list.get(0).getStartAccountOne().trim()) || "——".equals(list.get(0).getEndAccountOne().trim())){
        	valids.add("行次1 应为 ——");
        }else if("——".equals(list.get(22).getStartAccountOne().trim()) || "——".equals(list.get(22).getEndAccountOne().trim())){
        	valids.add("行次23 应为 ——");
        }else if("——".equals(list.get(54).getStartAccountOne().trim()) || "——".equals(list.get(54).getEndAccountOne().trim())){
        	valids.add("行次55应为 ——");
        }else if("——".equals(list.get(81).getStartAccountOne().trim()) || "——".equals(list.get(81).getEndAccountOne().trim())){
        	valids.add("行次82应为 ——");
        }else if("——".equals(list.get(94).getStartAccountOne().trim()) || "——".equals(list.get(94).getEndAccountOne().trim())){
        	valids.add("行次95应为 ——");
        }
        
        return valids;
    }
}
