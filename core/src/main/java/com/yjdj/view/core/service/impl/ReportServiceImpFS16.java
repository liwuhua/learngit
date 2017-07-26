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

import com.yjdj.view.core.entity.mybeans.InsertFieldTHM_FS16;
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
import com.yjdj.view.core.mapper.IReportMapperFS16;
import com.yjdj.view.core.service.IReportServiceFS16;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.ExcelImportUtil;
import com.yjdj.view.core.util.FileUtils;
import com.yjdj.view.core.util.StringUtil;

import org.springframework.util.StringUtils;

/**
 * create on 2016/11/10
 *
 * @author yangzhijie
 *
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS16 implements IReportServiceFS16 {

    private final Logger log = Logger.getLogger(ReportServiceImpFS16.class);

    @Autowired
    private IReportMapperFS16 iReportMapperFS16;
    private String dateYearMonth = null;
    private List<String> compCodeValue = null;
    private List<String> title = null;
    private List<String> secondTitle = null;
    private boolean isExport = false;
    private List<List<Object>> resultDataRowList = null;
    private List<String> headerList = null;
    private List<THM_IMP_FS16> dataList = null;
    private ExcelExportUtil ee = null;
    private List<Map<String, String>> resultCompList = null;
    private List<Map<String, String>> getCompList = null;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");//格式化设置

    @Override
    public Object getReportJson(QueryBean queryBean) throws JsonProcessingException, DataAccessException, IOException {
        //初始化数据
        init(queryBean);
        //第一步
        return getMapListByThmImpFs16(dateYearMonth, compCodeValue);
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
        headerList = Lists.newArrayList();
        //本月金额	本年累计	上年同期累计
        initHead(dateYearMonth);

    }
    
    private void initHead(String dateYearMonth) {
        headerList = Lists.newArrayList();
        //本月金额	本年累计	上年同期累计
        if(!"201606".equals(dateYearMonth) && !"201612".equals(dateYearMonth)){
            headerList.add("项目");
            headerList.add("行次");
            headerList.add("本月金额");
            headerList.add("本年累计");
            headerList.add("上年同期累计");
            headerList.add("项目");
            headerList.add("行次");
            headerList.add("本月金额");
            headerList.add("本年累计");
            headerList.add("上年同期累计");
        }else {
        	//本期金额	上期金额
            headerList.add("项目");
            headerList.add("行次");
            headerList.add("本期金额");
            headerList.add("上期金额");
            headerList.add("上年同期累计");
            headerList.add("项目");
            headerList.add("行次");
            headerList.add("本期金额");
            headerList.add("上期金额");
            headerList.add("上年同期累计");
        }

    }
    
    
    public Object getMapListByThmImpFs16(String dateYearMonth, List<String> compCodeValue) throws JsonProcessingException, DataAccessException, IOException {
        // TODO Auto-generated method stub
        String json = null;
        initHead(dateYearMonth);
      //取表格列数
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
//                Map<String, String> map = iReportMapperFS16.selectMaxYear();
//                dateYearMonth = map.get("YEARS");
                
                Map<String, String> companys = new HashMap<String, String>();
                companys.put("5003", "中车永济电机有限公司");
                
//                if("201606".equals(dateYearMonth) || "201612".equals(dateYearMonth)){
//                	dateYearMonth = "2016-0612";
//                	listField = iReportMapperFS16.selectField(dateYearMonth);
//                }else {
//                	listField = iReportMapperFS16.selectField(dateYearMonth.substring(0, 4));
//                }
                List<THM_IMP_FS16> listField = iReportMapperFS16.selectDataFS16(dateYearMonth, compCodeValue.get(0).substring(0,4));
                resultDataRowList = export(listField);
                ee = new ExcelExportUtil("合并利润表", secondTitle, headerList, "（合并）");
                List<List<Object>> dataLists = Lists.newArrayList();
                for (int j = 0; j < resultDataRowList.size(); j++) {
                	dataLists.add(resultDataRowList.get(j));
                }
                
                for (int i = 0; i < resultDataRowList.size(); i++) {
                    Row row = ee.addRow();
                    for (int j = 0; j < dataLists.get(i).size(); j++) {
                    	String val = StringUtil.nullToStr(dataLists.get(i).get(j));
                    	try{
                    		if(j == 2 || j == 3 || j == 4 || j == 7 || j == 8 || j == 9){
                    			ee.addCell(row, row.getRowNum(), j, "", 3, true, String.class);
                    		}else if(j == 1 || j == 6){
                    			ee.addCell(row, row.getRowNum(), j, val.replaceAll("&nbsp;", " "), 2, true, String.class);
                    		}else{
                    			String itemRn = StringUtil.nullToStr(dataLists.get(i).get(j+1));
                    			if ("1".equals(itemRn)
                                        || "8".equals(itemRn)
                                        || "28".equals(itemRn)
                                        || "34".equals(itemRn)
                                        || "36".equals(itemRn)
                                        || "39".equals(itemRn)
                                        || "52".equals(itemRn)
                                        || "55".equals(itemRn)) {
                                    ee.addCell(row, row.getRowNum(), j, val.replaceAll("&nbsp;", " "), 4, true, String.class);
                                } else {
                                    ee.addCell(row, row.getRowNum(), j, val.replaceAll("&nbsp;", " "), 1, true, String.class);
                                }
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
        resultCompList = iReportMapperFS16.selectCompany(compCodeValue);
        Map<String, String> company = new HashMap<String, String>();
        for (int i = 0; i < resultCompList.size(); i++) {
            company.put((String) resultCompList.get(i).get("COMP_CODE"), resultCompList.get(i).get("TXTMD"));
        }

//        dataList = iReportMapperFS16.selectData(dateYearMonth, compCodeValue);
        dataList = iReportMapperFS16.selectDataFS16(dateYearMonth, compCodeValue.get(0));
        
        if (!isExport) {
        	int count =0 ;
        	List<THM_IMP_FS16> tmpImp = new ArrayList<THM_IMP_FS16>();
        	for (THM_IMP_FS16 valueOne : dataList) {
        		THM_IMP_FS16 imp = new THM_IMP_FS16();
        		if(count == dataList.size()/2 || count == 0){
        			imp.setItemName(headerList.get(0));
        			imp.setItemNo(headerList.get(1));
        			imp.setThisYearSum(headerList.get(2));
        			imp.setMonthMoney(headerList.get(3));
        			imp.setLastYearSum(headerList.get(4));
        			tmpImp.add(imp);
        			imp = new THM_IMP_FS16();
        		}
    			imp.setItemName(valueOne.getItemName());
    			imp.setItemNo(valueOne.getItemNo());
    			imp.setMonthMoney(valueOne.getMonthMoney());
    			imp.setThisYearSum(valueOne.getThisYearSum());
    			imp.setLastYearSum(valueOne.getLastYearSum());
    			tmpImp.add(imp);
    			imp = new THM_IMP_FS16();
    			count ++;
        	}
            int size = tmpImp.size();
            if(size%2 != 0){
            	THM_IMP_FS16 imp = new THM_IMP_FS16();
            	imp.setItemNo(String.valueOf(size+1));
            	imp.setItemName("");
            	imp.setMonthMoney("");
            	imp.setThisYearSum("");
            	imp.setLastYearSum("");
            	tmpImp.add(imp);
            }
            
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(tmpImp);
            log.debug("FS16 json++++++++++ " + json);
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
            ee = new ExcelExportUtil("利润表", secondTitle, headerList, "(合并)");
            List<List<Object>> dataList = Lists.newArrayList();
            for (int j = 0; j < resultDataRowList.size(); j++) {
                dataList.add(resultDataRowList.get(j));
            }
            for (int i = 0; i < resultDataRowList.size(); i++) {
                Row row = ee.addRow();
                for (int j = 0; j < dataList.get(i).size(); j++) {
                    String val = StringUtil.nullToStr(dataList.get(i).get(j));
                    if (j == 2 || j == 3 || j == 4 || j == 7 || j == 8 || j == 9) {
                        try {
                            ee.addCell(row, row.getRowNum(), j, decimalFormat.format(val), 3, true, Double.class);
                        } catch (java.lang.IllegalArgumentException e) {
                            ee.addCell(row, row.getRowNum(), j, val, 3, true, Double.class);
                        }
                    } else if (j == 0 || j == 5) {//项目处理
                        String itemRn = StringUtil.nullToStr(dataList.get(i).get(j + 1));
                        if ("1".equals(itemRn)
                                || "8".equals(itemRn)
                                || "28".equals(itemRn)
                                || "34".equals(itemRn)
                                || "36".equals(itemRn)
                                || "39".equals(itemRn)
                                || "52".equals(itemRn)
                                || "55".equals(itemRn)) {
                            ee.addCell(row, row.getRowNum(), j, val.replaceAll("&nbsp;", " "), 4, true, String.class);
                        } else {
                            ee.addCell(row, row.getRowNum(), j, val.replaceAll("&nbsp;", " "), 1, true, String.class);
                        }
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
    private List<List<Object>> export(List<THM_IMP_FS16> data) throws IOException {
        // TODO Auto-generated method stub
        //
        String itemname = null;
        String regEx_special = "\\&[a-zA-Z]{1,10};";
        Pattern pattern = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
        List<List<Object>> dataRowList = Lists.newArrayList();
        List<Object> list = new ArrayList<Object>();
        NumberFormat decimalFormat = new DecimalFormat("###,###.##");
        Matcher matcher = null;
        /*for (THM_IMP_FS16 value : data) {
            itemname = value.getItemName();
            Matcher matcher = pattern.matcher(itemname);
            itemname = matcher.replaceAll(" "); // 过滤script标签
            list.add(itemname);
            list.add(Integer.parseInt(value.getItemNo()) + ""); //行次
        	if("——".equals(value.getMonthMoney()) || "——".equals(value.getThisYearSum()) || "——".equals(value.getLastYearSum())){
        		list.add(value.getMonthMoney()); //本月金额
        		list.add(value.getThisYearSum()); //本年累计
        		list.add(value.getLastYearSum()); //本年累计
        	}else if("".equals(value.getMonthMoney()) || " ".equals(value.getMonthMoney()) || "".equals(value.getThisYearSum()) || " ".equals(value.getThisYearSum())){
        		list.add(""); //期末金额
        		list.add(""); //年初金额
        		list.add(""); //上年同期累计
        	}else{
        		if(value.getMonthMoney() == null){
                	list.add(value.getMonthMoney()); 
        		}
        		if(value.getThisYearSum() == null){
        			list.add(value.getThisYearSum()); //
        		}
        		if(value.getThisYearSum() == null){
        			list.add(value.getThisYearSum()); 
        		}
        		if(value.getMonthMoney() != null && value.getThisYearSum() != null && value.getLastYearSum() != null){
            		list.add(decimalFormat.format(Double.valueOf(value.getMonthMoney()))); //本月金额
                    list.add(decimalFormat.format(Double.valueOf(value.getThisYearSum()))); //本年累计
                    list.add(decimalFormat.format(Double.valueOf(value.getLastYearSum()))); //上年同期累计
        		}

        	}
            
            dataRowList.add(list);
            list = new ArrayList<Object>();
        }*/
        int size = data.size();
        if(size%2 != 0){
        	THM_IMP_FS16 imp = new THM_IMP_FS16();
        	imp.setItemNo(String.valueOf(size+1));
        	imp.setItemName("");
        	imp.setMonthMoney("");
        	imp.setThisYearSum("");
        	imp.setLastYearSum("");
        	data.add(imp);
        }
        size = data.size();
        for (THM_IMP_FS16 valueOne : data) {
            for (THM_IMP_FS16 valueTwo : data) {
                int step = Integer.parseInt(valueTwo.getItemNo()) - Integer.parseInt(valueOne.getItemNo());
                
                if (step == size/2) {
                    itemname = valueOne.getItemName();
                    matcher = pattern.matcher(itemname);
                    itemname = matcher.replaceAll(" "); // 过滤标签
                    list.add(itemname);
                    list.add(Integer.parseInt(valueOne.getItemNo()) + ""); //行次
                    
                    if("——".equals(valueOne.getMonthMoney()) || "——".equals(valueOne.getThisYearSum()) || "——".equals(valueOne.getLastYearSum())){
                		list.add(valueOne.getMonthMoney()); //本月金额
                		list.add(valueOne.getThisYearSum()); //本年累计
                		list.add(valueOne.getLastYearSum()); //本年累计
                	}else if("".equals(valueOne.getMonthMoney()) || " ".equals(valueOne.getMonthMoney()) || "".equals(valueOne.getThisYearSum()) || " ".equals(valueOne.getThisYearSum())){
                		list.add(""); //期末金额
                		list.add(""); //年初金额
                		list.add(""); //上年同期累计
                	}else{
                		if(valueOne.getMonthMoney() == null){
                        	list.add(valueOne.getMonthMoney()); 
                		}
                		if(valueOne.getThisYearSum() == null){
                			list.add(valueOne.getThisYearSum()); //
                		}
                		if(valueOne.getThisYearSum() == null){
                			list.add(valueOne.getThisYearSum()); 
                		}
                		if(valueOne.getMonthMoney() != null && valueOne.getThisYearSum() != null && valueOne.getLastYearSum() != null){
//                    		list.add(decimalFormat.format(Double.valueOf(valueOne.getMonthMoney()))); //本月金额
//                            list.add(decimalFormat.format(Double.valueOf(valueOne.getThisYearSum()))); //本年累计
                    		if(!FileUtils.isNumeric(valueOne.getMonthMoney().replaceAll(",", "").replace("，", ""))){
                				list.add(valueOne.getMonthMoney()); //
                			}else{
                				list.add(decimalFormat.format(Double.valueOf(valueOne.getMonthMoney()))); //本月金额
                			}
                    		if(!FileUtils.isNumeric(valueOne.getThisYearSum().replaceAll(",", "").replace("，", ""))){
                				list.add(valueOne.getThisYearSum()); //
                			}else{
                				list.add(decimalFormat.format(Double.valueOf(valueOne.getThisYearSum()))); //本年累计
                			}
                    		
                            if("".equals(valueOne.getLastYearSum()) || " ".equals(valueOne.getLastYearSum())){
                            	list.add(""); //上年同期累计
                            }else{
//                            	list.add(decimalFormat.format(Double.valueOf(valueOne.getLastYearSum()))); //上年同期累计
                            	if(!FileUtils.isNumeric(valueOne.getLastYearSum().replaceAll(",", "").replace("，", ""))){
                    				list.add(valueOne.getLastYearSum()); //
                    			}else{
                    				list.add(decimalFormat.format(Double.valueOf(valueOne.getLastYearSum()))); //上年同期累计
                    			}
                            	
//                            	list.add(decimalFormat.format(valueOne.getLastYearSum())); //上年同期累计
                            }
                            
                		}

                	}
                    
                    itemname = valueTwo.getItemName();
                    matcher = pattern.matcher(itemname);
                    itemname = matcher.replaceAll(" "); // 过滤标签
                    list.add(itemname);
                    list.add(Integer.parseInt(valueTwo.getItemNo()) + ""); //行次
                    if("——".equals(valueTwo.getMonthMoney()) || "——".equals(valueTwo.getThisYearSum()) || "——".equals(valueTwo.getLastYearSum())){
                		list.add(valueTwo.getMonthMoney()); //本月金额
                		list.add(valueTwo.getThisYearSum()); //本年累计
                		list.add(valueTwo.getLastYearSum()); //本年累计
                	}else if("".equals(valueTwo.getMonthMoney()) || " ".equals(valueTwo.getMonthMoney()) || "".equals(valueTwo.getThisYearSum()) || " ".equals(valueTwo.getThisYearSum())){
                		list.add(""); //期末金额
                		list.add(""); //年初金额
                		list.add(""); //上年同期累计
                	}else{
                		if(valueTwo.getMonthMoney() == null){
                        	list.add(valueTwo.getMonthMoney()); 
                		}
                		if(valueTwo.getThisYearSum() == null){
                			list.add(valueTwo.getThisYearSum()); //
                		}
                		if(valueTwo.getThisYearSum() == null){
                			list.add(valueTwo.getThisYearSum()); 
                		}
                		if(valueTwo.getMonthMoney() != null && valueTwo.getThisYearSum() != null && valueTwo.getLastYearSum() != null){
//                    		list.add(decimalFormat.format(Double.valueOf(valueTwo.getMonthMoney()))); //本月金额
//                          list.add(decimalFormat.format(Double.valueOf(valueTwo.getThisYearSum()))); //本年累计
                			if(!FileUtils.isNumeric(valueTwo.getMonthMoney().replaceAll(",", "").replace("，", ""))){
                				list.add(valueTwo.getMonthMoney()); //
                			}else{
                				list.add(decimalFormat.format(Double.valueOf(valueTwo.getMonthMoney()))); //本月金额
                			}
                			if(!FileUtils.isNumeric(valueTwo.getThisYearSum().replaceAll(",", "").replace("，", ""))){
                				list.add(valueTwo.getThisYearSum()); //
                			}else{
                				list.add(decimalFormat.format(Double.valueOf(valueTwo.getThisYearSum()))); //本年累计
                			}
                            if("".equals(valueOne.getLastYearSum()) || " ".equals(valueOne.getLastYearSum())){
                            	list.add(""); //上年同期累计
                            }else{
                            	if(!FileUtils.isNumeric(valueTwo.getLastYearSum().replaceAll(",", "").replace("，", ""))){
                    				list.add(valueTwo.getLastYearSum()); //
                    			}else{
                    				list.add(decimalFormat.format(Double.valueOf(valueTwo.getLastYearSum()))); //上年同期累计
                    			}
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
        String json = null;
        String yearMonth = null;
        Matcher matcher = null;
        Map<String, Object> listMap = new HashMap<String, Object>();
        Map<String, Object> errorMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        //导入数据
        ExcelImportUtil ei = new ExcelImportUtil(filePath, 3);
        headerList = Lists.newArrayList();
        
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
        getCompList = iReportMapperFS16.selectCompanyCode(company.trim());
        if(getCompList != null){
        	if (getCompList.size() <= 0) {
                listMap.put("ERRORCOMPANEY", "编制单位填写错误，没有该编制单位!");
            }else{
            	company = getCompList.get(0).get("COMP_CODE");
            }
            yearMonth = matcher.replaceAll("").trim();
            if (!FileUtils.isValidDate(yearMonth)) {
                listMap.put("ERRORTIME", "时间填写错误!");
            }
        }else{
        	listMap.put("ERRORCOMPANEY", "编制单位填写错误，没有该编制单位!");
        }
        initHead(yearMonth);
        json = ei.checkHeadList(headerList, listMap);
        if (null != json) {
            return json;
        }
        
        //如果验证数据没有问题，则返回所有的数据，标志位为1，若是数据有问题，则返回不正确数据，标准为0
        List<InsertFieldTHM_FS16> list = ei.getDataList(InsertFieldTHM_FS16.class, "FS16");
        
        //校验数据是否准确，加和逻辑，分项与统计是否匹配
//        List<String> valids = checkData(list, yearMonth);
//        if(valids.size() > 0){
//        	listMap.put("ERRORDATA", valids);
//        	errorMap.put("ERROR", listMap);
//        	return mapper.writeValueAsString(errorMap);
//        }
        List<InsertFieldTHM_FS16> tmpDataList = new ArrayList<InsertFieldTHM_FS16>();
        InsertFieldTHM_FS16 tmp = new InsertFieldTHM_FS16();
        
        //取表头
//    	tmp.setItemNameOne(ei.getRow(ei.getHeaderNum()).getCell(0).getStringCellValue());
//        tmp.setItemNoOne(ei.getRow(ei.getHeaderNum()).getCell(1).getStringCellValue());
//        tmp.setMonthMoneyOne(ei.getRow(ei.getHeaderNum()).getCell(2).getStringCellValue());
//        tmp.setThisYearSumOne(ei.getRow(ei.getHeaderNum()).getCell(3).getStringCellValue());
//        tmp.setLastYearSumOne(ei.getRow(ei.getHeaderNum()).getCell(3).getStringCellValue());
//        tmp.setYearMonth(yearMonth);
//        tmp.setCompanyCode(company);
//        tmpDataList.add(tmp);
//        tmp = new InsertFieldTHM_FS16();

        //导入数据处理
        for (int i = 0; i < list.size(); i++) {
            if (!StringUtil.isEmpty(list.get(i).getItemNoOne())) {
            	tmp.setItemNameOne(list.get(i).getItemNameOne());
                tmp.setItemNoOne(list.get(i).getItemNoOne());
                tmp.setMonthMoneyOne(list.get(i).getMonthMoneyOne().replaceAll(",", "").replace("，", ""));
                tmp.setThisYearSumOne(list.get(i).getThisYearSumOne().replaceAll(",", "").replace("，", ""));
                tmp.setLastYearSumOne(list.get(i).getLastYearSumOne().replaceAll(",", "").replace("，", ""));
                tmp.setYearMonth(yearMonth);
                tmp.setCompanyCode(company);
                tmpDataList.add(tmp);
                tmp = new InsertFieldTHM_FS16();
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (!StringUtil.isEmpty(list.get(i).getItemNoTwo())) {
            	tmp.setItemNameOne(list.get(i).getItemNameTwo());
                tmp.setItemNoOne(list.get(i).getItemNoTwo());
                tmp.setMonthMoneyOne(list.get(i).getMonthMoneyTwo().replaceAll(",", "").replace("，", ""));
                tmp.setThisYearSumOne(list.get(i).getThisYearSumTwo().replaceAll(",", "").replace("，", ""));
                tmp.setLastYearSumOne(list.get(i).getLastYearSumTwo().replaceAll(",", "").replace("，", ""));
                tmp.setYearMonth(yearMonth);
                tmp.setCompanyCode(company);
                tmpDataList.add(tmp);
                tmp = new InsertFieldTHM_FS16();
            }
        }
//        iReportMapperFS16.saveOrUpdateData(yearMonth, company, tmpDataList);
        iReportMapperFS16.saveOrUpdateDataFS16(yearMonth, company, tmpDataList);
        listMap.put("SUCCESSES", "导入成功!");
        json = mapper.writeValueAsString(listMap);
        
        return json;
    }

    /**
     * 校验数据分项与统计是否匹配
     *
     * @param list
     * @return
     */
/*    private List<String> checkData(List<InsertFieldTHM_FS16> list, String yearMonth) throws JsonProcessingException {
        List<String> valids = new ArrayList<String>();
        int num = 5;
        int columnsMonthMoney = 3;
        int columnsThisYearSum = 4;
        int columnsLastYearSum = 5;
        Map<String, String> map = iReportMapperFS16.selectMaxYear();
        dateYearMonth = map.get("YEARS");
        if(valids.size() == 0){
        	if("2015".equals(dateYearMonth)){
        		for(int i=0; i<list.size(); i++){
                	if(list.get(i).getItemNo() != null && !"".equals(list.get(i).getItemNo())){
                		if(Integer.parseInt(list.get(i).getItemNo()) >=1 && Integer.parseInt(list.get(i).getItemNo()) <= 45){
                    		if(!FileUtils.isNumeric(list.get(i).getMonthMoney().replaceAll(",", "").replace("，", ""))){
                    			valids.add("第 "+num+" 行第 "+columnsMonthMoney+" 列数据格式错误!");
                        	}else if(!FileUtils.isNumeric(list.get(i).getThisYearSum().replaceAll(",", "").replace("，", ""))){
                    			valids.add("第 "+num+" 行第 "+columnsThisYearSum+" 列数据格式错误!");
                        	}else if(!FileUtils.isNumeric(list.get(i).getLastYearSum().replaceAll(",", "").replace("，", ""))){
                    			valids.add("第 "+num+" 行第 "+columnsLastYearSum+" 列数据格式错误!");
                        	}
                			num ++;
                		}
                	}
                }
        		valids.addAll(check2015(list));
        	}
        }else if("2016".equals(dateYearMonth)){
    		for(int i=0; i<list.size(); i++){
            	if(list.get(i).getItemNo() != null && !"".equals(list.get(i).getItemNo())){
            		if(Integer.parseInt(list.get(i).getItemNo()) >=1 && Integer.parseInt(list.get(i).getItemNo()) <= 57){
                		if(!FileUtils.isNumeric(list.get(i).getMonthMoney().replaceAll(",", "").replace("，", ""))){
                			valids.add("第 "+num+" 行第 "+columnsMonthMoney+" 列数据格式错误!");
                    	}else if(!FileUtils.isNumeric(list.get(i).getThisYearSum().replaceAll(",", "").replace("，", ""))){
                			valids.add("第 "+num+" 行第 "+columnsThisYearSum+" 列数据格式错误!");
                    	}else if(!FileUtils.isNumeric(list.get(i).getLastYearSum().replaceAll(",", "").replace("，", ""))){
                			valids.add("第 "+num+" 行第 "+columnsLastYearSum+" 列数据格式错误!");
                    	}
            			num ++;
            		}
            	}
            }
    		valids.addAll(check2016(list));
        }
        return valids;
    }
    
    //2015年数据校验
    public List<String> check2015(List<InsertFieldTHM_FS16> list){
    	List<String> valids = new ArrayList<String>();
    	//2+5+6+7 = 1 本月金额
        Double item1_monthMoney = StringUtils.isEmpty(list.get(0).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(0).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item2_monthMoney = StringUtils.isEmpty(list.get(1).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(1).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item5_7_monthMoney = 0d;
        for (int i = 4; i < 7; i++) {
            item5_7_monthMoney += StringUtils.isEmpty(list.get(i).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(i).getMonthMoney().replaceAll(",", "").replace("，", ""));
        }
        if (item1_monthMoney != new BigDecimal(item5_7_monthMoney + item2_monthMoney).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) {
            valids.add("行次2+5+6+7 本月金额之和 不等于 行次1本月金额");
        }
        //2+5+6+7 = 1 本年累计
        Double item1_thisYearSum = StringUtils.isEmpty(list.get(0).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(0).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item2_thisYearSum = StringUtils.isEmpty(list.get(1).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(1).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item5_7_thisYearSum = 0d;
        for (int i = 4; i < 7; i++) {
            item5_7_thisYearSum += StringUtils.isEmpty(list.get(i).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(i).getThisYearSum().replaceAll(",", "").replace("，", ""));
        }
        if (item1_thisYearSum != new BigDecimal(item5_7_thisYearSum + item2_thisYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) {
            valids.add("行次2+5+6+7 本年累计之和 不等于 行次1本年累计");
        }
        //2+5+6+7 = 1 上年同期累计
        Double item1_lastYearSum = StringUtils.isEmpty(list.get(0).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(0).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item2_lastYearSum = StringUtils.isEmpty(list.get(1).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(1).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item5_7_lastYearSum = 0d;
        for (int i = 4; i < 7; i++) {
            item5_7_lastYearSum += StringUtils.isEmpty(list.get(i).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(i).getLastYearSum().replaceAll(",", "").replace("，", ""));
        }
        if (item1_lastYearSum != new BigDecimal(item5_7_lastYearSum + item2_lastYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) {
            valids.add("行次2+5+6+7 上年同期累计之和 不等于 行次1上年同期累计");
        }
        
        //3+4=2 本月金额
        Double item3_monthMoney = StringUtils.isEmpty(list.get(2).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(2).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item4_monthMoney = StringUtils.isEmpty(list.get(3).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(3).getMonthMoney().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item3_monthMoney + item4_monthMoney).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item2_monthMoney) {
            valids.add("行次3与行次4本月金额之和 不等于 行次2本月金额");
        }
        //3+4=2 本年累计
        Double item3_thisYearSum = StringUtils.isEmpty(list.get(2).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(2).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item4_thisYearSum = StringUtils.isEmpty(list.get(3).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(3).getThisYearSum().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item3_thisYearSum + item4_thisYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item2_thisYearSum) {
            valids.add("行次3与行次4本年累计之和 不等于 行次2本年累计");
        }
        //3+4=2 上年同期累计
        Double item3_lastYearSum = StringUtils.isEmpty(list.get(2).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(2).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item4_lastYearSum = StringUtils.isEmpty(list.get(3).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(3).getLastYearSum().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item3_lastYearSum + item4_lastYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item2_lastYearSum) {
            valids.add("行次3与行次4上年同期累计之和 不等于 行次2上年同期累计");
        }
        
        //9+12…23=8 本月金额
        Double item12_23_monthMoney = 0d;
        Double item8_monthMoney = StringUtils.isEmpty(list.get(7).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(7).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item9_monthMoney = StringUtils.isEmpty(list.get(8).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(8).getMonthMoney().replaceAll(",", "").replace("，", ""));
        for (int i = 11; i < 23; i++) {
            item12_23_monthMoney += StringUtils.isEmpty(list.get(i).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(i).getMonthMoney().replaceAll(",", "").replace("，", ""));
        }
        if (item8_monthMoney != new BigDecimal(item9_monthMoney + item12_23_monthMoney).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) {
            valids.add("行次9+12…23 本月金额之和 不等于 行次8本月金额");
        }
        //9+12…23=8 本年累计
        Double item12_23_thisYearSum= 0d;
        Double item8_thisYearSum = StringUtils.isEmpty(list.get(7).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(7).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item9_thisYearSum = StringUtils.isEmpty(list.get(8).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(8).getThisYearSum().replaceAll(",", "").replace("，", ""));
        for (int i = 11; i < 23; i++) {
            item12_23_thisYearSum += StringUtils.isEmpty(list.get(i).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(i).getThisYearSum().replaceAll(",", "").replace("，", ""));
        }
        if (item8_thisYearSum != new BigDecimal(item9_thisYearSum + item12_23_thisYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) {
            valids.add("行次9+12…23 本年累计之和 不等于 行次8本年累计");
        }
        //9+12…23=8 上年同期累计
        Double item12_23_lastYearSum = 0d;
        Double item8_lastYearSum = StringUtils.isEmpty(list.get(7).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(7).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item9_lastYearSum = StringUtils.isEmpty(list.get(8).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(8).getLastYearSum().replaceAll(",", "").replace("，", ""));
        for (int i = 11; i < 23; i++) {
            item12_23_lastYearSum += StringUtils.isEmpty(list.get(i).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(i).getLastYearSum().replaceAll(",", "").replace("，", ""));
        }
        if (item8_lastYearSum != new BigDecimal(item9_lastYearSum + item12_23_lastYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) {
            valids.add("行次9+12…23 上年同期累计之和 不等于 行次8上年同期累计");
        }
        
        //10+11 本月金额
        Double item10_monthMoney = StringUtils.isEmpty(list.get(9).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(9).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item11_monthMoney = StringUtils.isEmpty(list.get(10).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(10).getMonthMoney().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item10_monthMoney + item11_monthMoney).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item9_monthMoney) {
            valids.add("行次10与行次11本月金额之和 不等于 行次9本月金额");
        }
        //10+11 本年累计
        Double item10_thisYearSum = StringUtils.isEmpty(list.get(9).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(9).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item11_thisYearSum = StringUtils.isEmpty(list.get(10).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(10).getThisYearSum().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item10_thisYearSum + item11_thisYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item9_thisYearSum) {
            valids.add("行次10与行次11本年累计之和 不等于 行次9本年累计");
        }
        //10+11 上年同期累计
        Double item10_lastYearSum = StringUtils.isEmpty(list.get(9).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(9).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item11_lastYearSum = StringUtils.isEmpty(list.get(10).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(10).getLastYearSum().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item10_lastYearSum + item11_lastYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item9_lastYearSum) {
            valids.add("行次10与行次11上年同期累计之和 不等于 行次9上年同期累计");
        }
        
        //26小于等于25
        Double item26_monthMoney = StringUtils.isEmpty(list.get(25).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(25).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item25_monthMoney = StringUtils.isEmpty(list.get(24).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(24).getMonthMoney().replaceAll(",", "").replace("，", ""));
        if (item26_monthMoney > item25_monthMoney) {
            valids.add("行次26本月金额 大于 行次25本月金额");
        }
        //26小于等于25 本年累计
        Double item26_thisYearSum = StringUtils.isEmpty(list.get(25).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(25).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item25_thisYearSum = StringUtils.isEmpty(list.get(24).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(24).getThisYearSum().replaceAll(",", "").replace("，", ""));
        if (item26_thisYearSum > item25_thisYearSum) {
            valids.add("行次26本年累计 大于 行次25本年累计");
        }
        //26小于等于25 上年同期累计
        Double item26_lastYearSum = StringUtils.isEmpty(list.get(25).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(25).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item25_lastYearSum = StringUtils.isEmpty(list.get(24).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(24).getLastYearSum().replaceAll(",", "").replace("，", ""));
        if (item26_lastYearSum > item25_lastYearSum) {
            valids.add("行次26上年同期累计 大于 行次25上年同期累计");
        }
        
        //1减8+24+25+27=28 本月金额
        Double item24_monthMoney = StringUtils.isEmpty(list.get(23).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(23).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item27_monthMoney = StringUtils.isEmpty(list.get(26).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(26).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item28_monthMoney = StringUtils.isEmpty(list.get(27).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(27).getMonthMoney().replaceAll(",", "").replace("，", ""));
        if ((new BigDecimal(item1_monthMoney - item8_monthMoney + item24_monthMoney + item25_monthMoney +item27_monthMoney).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) != item28_monthMoney) {
            valids.add("行次1-8+24+25+27本月金额之和 不等于 行次28本月金额");
        }
        //1减8+24+25+27=28 本年累计
        Double item24_thisYearSum = StringUtils.isEmpty(list.get(23).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(23).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item27_thisYearSum = StringUtils.isEmpty(list.get(26).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(26).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item28_thisYearSum = StringUtils.isEmpty(list.get(27).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(27).getThisYearSum().replaceAll(",", "").replace("，", ""));
        if ((new BigDecimal(item1_thisYearSum - item8_thisYearSum + item24_thisYearSum + item25_thisYearSum +item27_thisYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) != item28_thisYearSum) {
            valids.add("行次1-8+24+25+27本年累计之和 不等于 行次28本年累计");
        }
        //1减8+24+25+27=28 上年同期累计
        Double item24_lastYearSum = StringUtils.isEmpty(list.get(23).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(23).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item27_lastYearSum = StringUtils.isEmpty(list.get(26).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(26).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item28_lastYearSum = StringUtils.isEmpty(list.get(27).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(27).getLastYearSum().replaceAll(",", "").replace("，", ""));
        if ((new BigDecimal(item1_lastYearSum - item8_lastYearSum + item24_lastYearSum + item25_lastYearSum +item27_lastYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) != item28_lastYearSum) {
            valids.add("行次1-8+24+25+27上年同期累计之和 不等于 行次28上年同期累计");
        }
        
        //30加31小于等于29 本月金额
        Double item30_monthMoney = StringUtils.isEmpty(list.get(29).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(29).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item29_monthMoney = StringUtils.isEmpty(list.get(28).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(28).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item31_monthMoney = StringUtils.isEmpty(list.get(30).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(30).getMonthMoney().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item30_monthMoney + item31_monthMoney).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item29_monthMoney) {
            valids.add("行次30与行次31本月金额之和 不等于 行次29本月金额");
        }
        //30加31小于等于29 本年累计
        Double item30_thisYearSum = StringUtils.isEmpty(list.get(29).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(29).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item29_thisYearSum = StringUtils.isEmpty(list.get(28).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(28).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item31_thisYearSum = StringUtils.isEmpty(list.get(30).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(30).getThisYearSum().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item30_thisYearSum + item31_thisYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item29_thisYearSum) {
            valids.add("行次30与行次31本年累计之和 不等于 行次29本年累计");
        }
        //30加31小于等于29 上年同期累计
        Double item30_lastYearSum = StringUtils.isEmpty(list.get(29).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(29).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item29_lastYearSum = StringUtils.isEmpty(list.get(28).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(28).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item31_lastYearSum = StringUtils.isEmpty(list.get(30).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(30).getLastYearSum().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item30_lastYearSum + item31_lastYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item29_lastYearSum) {
            valids.add("行次30与行次31上年同期累计之和 不等于 行次29上年同期累计");
        }
        
        //33小于等于32 本月金额
        Double item33_monthMoney = StringUtils.isEmpty(list.get(32).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(32).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item32_monthMoney = StringUtils.isEmpty(list.get(31).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(31).getMonthMoney().replaceAll(",", "").replace("，", ""));
        if (item33_monthMoney > item32_monthMoney) {
            valids.add("行次33本月金额 大于 行次32本月金额");
        }
        //33小于等于32 本年累计
        Double item33_thisYearSum = StringUtils.isEmpty(list.get(32).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(32).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item32_thisYearSum = StringUtils.isEmpty(list.get(31).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(31).getThisYearSum().replaceAll(",", "").replace("，", ""));
        if (item33_thisYearSum > item32_thisYearSum) {
            valids.add("行次33本年累计 大于 行次32本年累计");
        }
        //33小于等于32 上年同期累计
        Double item33_lastYearSum = StringUtils.isEmpty(list.get(32).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(32).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item32_lastYearSum = StringUtils.isEmpty(list.get(31).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(31).getLastYearSum().replaceAll(",", "").replace("，", ""));
        if (item33_lastYearSum > item32_lastYearSum) {
            valids.add("行次33上年同期累计 大于 行次32上年同期累计");
        }

        //28+29-32=34 本月金额
        Double item34_monthMoney = StringUtils.isEmpty(list.get(33).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(33).getMonthMoney().replaceAll(",", "").replace("，", ""));
        if ((new BigDecimal(item28_monthMoney + item29_monthMoney - item32_monthMoney).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) != item34_monthMoney) {
            valids.add("行次28+29-32本月金额之和 不等于 行次34本月金额");
        }
        //28+29-32=34 本年累计
        Double item34_thisYearSum = StringUtils.isEmpty(list.get(33).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(33).getThisYearSum().replaceAll(",", "").replace("，", ""));
        if ((new BigDecimal(item28_thisYearSum + item29_thisYearSum - item32_thisYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) != item34_thisYearSum) {
            valids.add("行次28+29-32本年累计之和 不等于 行次34本年累计");
        }
        //28+29-32=34 上年同期累计
        Double item34_lastYearSum = StringUtils.isEmpty(list.get(33).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(33).getLastYearSum().replaceAll(",", "").replace("，", ""));
        if ((new BigDecimal(item28_lastYearSum + item29_lastYearSum - item32_lastYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) != item34_lastYearSum) {
            valids.add("行次28+29-32上年同期累计之和 不等于 行次34上年同期累计");
        }
        
        //34减去35=36 本月金额
        Double item35_monthMoney = StringUtils.isEmpty(list.get(34).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(34).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item36_monthMoney = StringUtils.isEmpty(list.get(35).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(35).getMonthMoney().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item34_monthMoney - item35_monthMoney).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item36_monthMoney) {
            valids.add("行次34与行次35本月金额之差 不等于 行次36本月金额");
        }
        //34减去35=36 本年累计
        Double item35_thisYearSum = StringUtils.isEmpty(list.get(34).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(34).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item36_thisYearSum = StringUtils.isEmpty(list.get(35).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(35).getThisYearSum().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item34_thisYearSum - item35_thisYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item36_thisYearSum) {
            valids.add("行次34与行次35本年累计之差 不等于 行次36本年累计");
        }
        //34减去35=36 上年同期累计
        Double item35_lastYearSum = StringUtils.isEmpty(list.get(34).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(34).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item36_lastYearSum = StringUtils.isEmpty(list.get(35).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(35).getLastYearSum().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item34_lastYearSum - item35_lastYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item36_lastYearSum) {
            valids.add("行次34与行次35上年同期累计之差 不等于 行次36上年同期累计");
        }
        
        //36减去37=38 本月金额
        Double item37_monthMoney = StringUtils.isEmpty(list.get(36).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(36).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item38_monthMoney = StringUtils.isEmpty(list.get(37).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(37).getMonthMoney().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item36_monthMoney - item37_monthMoney).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item38_monthMoney) {
            valids.add("行次36与行次37本月金额之差 不等于 行次38本月金额");
        }
        //36减去37=38 本年累计
        Double item37_thisYearSum = StringUtils.isEmpty(list.get(36).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(36).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item38_thisYearSum = StringUtils.isEmpty(list.get(37).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(37).getThisYearSum().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item36_thisYearSum - item37_thisYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item38_thisYearSum) {
            valids.add("行次36与行次37本年累计之差 不等于 行次38本年累计");
        }
        //36减去37=38 上年同期累计
        Double item37_lastYearSum = StringUtils.isEmpty(list.get(36).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(36).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item38_lastYearSum = StringUtils.isEmpty(list.get(37).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(37).getLastYearSum().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item36_lastYearSum - item37_lastYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item38_lastYearSum) {
            valids.add("行次36与行次37上年同期累计之差 不等于 行次38上年同期累计");
        }
        return valids;
    }
    //2016年数据校验
    public List<String> check2016(List<InsertFieldTHM_FS16> list){
    	List<String> valids = new ArrayList<String>();
    	//1至38行次校验,与2015一致
    	valids.addAll(check2015(list));
        //40+51=39 本月金额
        Double item40_monthMoney = StringUtils.isEmpty(list.get(39).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(39).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item51_monthMoney = StringUtils.isEmpty(list.get(50).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(50).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item39_monthMoney = StringUtils.isEmpty(list.get(38).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(38).getMonthMoney().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item40_monthMoney + item51_monthMoney).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item39_monthMoney) {
            valids.add("行次40与行次51本月金额之和  不等于 行次39本月金额");
        }
        //40+51=39 本年累计
        Double item40_thisYearSum = StringUtils.isEmpty(list.get(39).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(39).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item51_thisYearSum = StringUtils.isEmpty(list.get(50).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(50).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item39_thisYearSum = StringUtils.isEmpty(list.get(38).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(38).getThisYearSum().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item40_thisYearSum + item51_thisYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item39_thisYearSum) {
            valids.add("行次40与行次51本年累计之和  不等于 行次39本年累计");
        }
        //40+51=39 上年同期累计
        Double item40_lastYearSum = StringUtils.isEmpty(list.get(39).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(39).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item51_lastYearSum = StringUtils.isEmpty(list.get(50).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(50).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item39_lastYearSum = StringUtils.isEmpty(list.get(38).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(38).getLastYearSum().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item40_lastYearSum + item51_lastYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item39_lastYearSum) {
            valids.add("行次40与行次51上年同期累计之和 不等于 行次39上年同期累计");
        }

        //41+44=40 本月金额
        Double item41_monthMoney = StringUtils.isEmpty(list.get(40).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(40).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item44_monthMoney = StringUtils.isEmpty(list.get(43).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(43).getMonthMoney().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item41_monthMoney + item44_monthMoney).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item40_monthMoney) {
            valids.add("行次41与行次44本月金额之和  不等于 行次40本月金额");
        }
        //41+44=40 本年累计
        Double item41_thisYearSum = StringUtils.isEmpty(list.get(40).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(40).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item44_thisYearSum = StringUtils.isEmpty(list.get(43).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(43).getThisYearSum().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item41_thisYearSum + item44_thisYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item40_thisYearSum) {
            valids.add("行次41与行次44本年累计之和  不等于 行次40本年累计");
        }
        //41+44=40 上年同期累计
        Double item41_lastYearSum = StringUtils.isEmpty(list.get(40).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(40).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item44_lastYearSum = StringUtils.isEmpty(list.get(43).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(43).getLastYearSum().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item41_lastYearSum + item44_lastYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item40_lastYearSum) {
            valids.add("行次41与行次44上年同期累计之和  不等于 行次40上年同期累计");
        }
        
        //42+43=41 本月金额
        Double item42_monthMoney = StringUtils.isEmpty(list.get(41).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(41).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item43_monthMoney = StringUtils.isEmpty(list.get(42).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(42).getMonthMoney().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item42_monthMoney + item43_monthMoney).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item41_monthMoney) {
            valids.add("行次42与行次43本月金额之和  不等于 行次41本月金额");
        }
        //42+43=41 本年累计
        Double item42_thisYearSum = StringUtils.isEmpty(list.get(41).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(41).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item43_thisYearSum = StringUtils.isEmpty(list.get(42).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(42).getThisYearSum().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item42_thisYearSum + item43_thisYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item41_thisYearSum) {
            valids.add("行次42与行次43本年累计之和  不等于 行次41本年累计");
        }
        //42+43=41 上年同期累计
        Double item42_lastYearSum = StringUtils.isEmpty(list.get(41).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(41).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item43_lastYearSum = StringUtils.isEmpty(list.get(42).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(42).getLastYearSum().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item42_lastYearSum + item43_lastYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item41_lastYearSum) {
            valids.add("行次41与行次43上年同期累计之和  不等于 行次41上年同期累计");
        }
        
        //45+46+47+48+49+50=44 本月金额
        Double item45_monthMoney = StringUtils.isEmpty(list.get(44).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(44).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item46_monthMoney = StringUtils.isEmpty(list.get(45).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(45).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item47_monthMoney = StringUtils.isEmpty(list.get(46).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(46).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item48_monthMoney = StringUtils.isEmpty(list.get(47).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(47).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item49_monthMoney = StringUtils.isEmpty(list.get(48).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(48).getMonthMoney().replaceAll(",", "").replace("，", ""));
        Double item50_monthMoney = StringUtils.isEmpty(list.get(49).getMonthMoney().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(49).getMonthMoney().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item45_monthMoney + item46_monthMoney + item47_monthMoney + item48_monthMoney + item49_monthMoney + item50_monthMoney).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item44_monthMoney) {
            valids.add("行次45与行次46与行次47与行次48与行次49与行次50本月金额之和  不等于 行次44本月金额");
        }
        //45+46+47+48+49+50=44 本年累计
        Double item45_thisYearSum = StringUtils.isEmpty(list.get(44).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(44).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item46_thisYearSum = StringUtils.isEmpty(list.get(45).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(45).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item47_thisYearSum = StringUtils.isEmpty(list.get(46).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(46).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item48_thisYearSum = StringUtils.isEmpty(list.get(47).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(47).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item49_thisYearSum = StringUtils.isEmpty(list.get(48).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(48).getThisYearSum().replaceAll(",", "").replace("，", ""));
        Double item50_thisYearSum = StringUtils.isEmpty(list.get(49).getThisYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(49).getThisYearSum().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item45_thisYearSum + item46_thisYearSum + item47_thisYearSum + item48_thisYearSum + item49_thisYearSum + item50_thisYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item44_thisYearSum) {
            valids.add("行次45与行次46与行次47与行次48与行次49与行次50本年累计之和  不等于 行次44本年累计");
        }
        //45+46+47+48+49+50=44 上年同期累计
        Double item45_lastYearSum = StringUtils.isEmpty(list.get(44).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(44).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item46_lastYearSum = StringUtils.isEmpty(list.get(45).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(45).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item47_lastYearSum = StringUtils.isEmpty(list.get(46).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(46).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item48_lastYearSum = StringUtils.isEmpty(list.get(47).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(47).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item49_lastYearSum = StringUtils.isEmpty(list.get(48).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(48).getLastYearSum().replaceAll(",", "").replace("，", ""));
        Double item50_lastYearSum = StringUtils.isEmpty(list.get(49).getLastYearSum().replaceAll(",", "").replace("，", "")) ? 0d : Double.valueOf(list.get(49).getLastYearSum().replaceAll(",", "").replace("，", ""));
        if (new BigDecimal(item45_lastYearSum + item46_lastYearSum + item47_lastYearSum + item48_lastYearSum + item49_lastYearSum +item50_lastYearSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item44_lastYearSum) {
            valids.add("行次45与行次46与行次47与行次48与行次49与行次50上年同期累计之和  不等于 行次44上年同期累计");
        }
        //55 行次
        if("——".equals(list.get(54).getThisYearSum().trim()) || "——".equals(list.get(54).getLastYearSum().trim()) || "——".equals(list.get(54).getMonthMoney().trim())){
        	valids.add("行次55 应为 ——");
        }
        return valids;
    }*/

}
