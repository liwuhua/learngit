package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.InsertFieldTHM_FS17;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_IMP_FS17;
import com.yjdj.view.core.mapper.IReportMapperFS17;
import com.yjdj.view.core.service.IReportServiceFS17;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.ExcelImportUtil;
import com.yjdj.view.core.util.FileUtils;
import com.yjdj.view.core.util.StringUtil;

/**
 * create on 2016/11/22
 *
 * @author yangzhijie
 *
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS17 implements IReportServiceFS17 {

    private final Logger log = Logger.getLogger(ReportServiceImpFS17.class);

    @Autowired
    private IReportMapperFS17 iReportMapperFS17;
    private String dateYearMonth = null;
    private List<String> compCodeValue = null;
    private List<String> title = null;
    private List<String> secondTitle = null;
    private boolean isExport = false;
    private List<List<Object>> resultDataRowList = null;
    private List<String> headerList = null;
    private List<THM_IMP_FS17> dataList = null;
    private ExcelExportUtil ee = null;
    private List<Map<String, String>> resultCompList = null;
    private List<Map<String, String>> getCompList = null;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");//格式化设置

    @Override
    public Object getReportJson(QueryBean queryBean) throws IOException {
        Object mapList = null;
        init(queryBean);
        //第一步
        mapList = getMapListByThmImpFs17(dateYearMonth, compCodeValue);
        log.debug("+++++++++++++++++++ FS17 getReportJson");
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

    }
    private void initHead() {
        headerList = Lists.newArrayList();
        headerList.add("项目");
        headerList.add("行次");
        headerList.add("本期金额");
        headerList.add("上期金额");
        headerList.add("项目");
        headerList.add("行次");
        headerList.add("本期金额");
        headerList.add("上期金额");
    }

    
    public Object getMapListByThmImpFs17(String dateYearMonth, List<String> compCodeValue) throws IOException {
        // TODO Auto-generated method stub
        String json = null;
        initHead();
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
                };
//                Map<String, String> map = iReportMapperFS17.selectMaxYear();
//                dateYearMonth = map.get("YEARS");
                
                Map<String, String> company = new HashMap<String, String>();
                company.put("5003", "中车永济电机有限公司");
//                List<THM_IMP_FS17> listField = iReportMapperFS17.selectField(dateYearMonth.substring(0, 4));
                List<THM_IMP_FS17> listField = iReportMapperFS17.selectDataFS17(dateYearMonth, compCodeValue.get(0).substring(0,4));
                resultDataRowList = export(listField);
                ee = new ExcelExportUtil("合并现金流量表", secondTitle, headerList, "（合并）");
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
        resultCompList = iReportMapperFS17.selectCompany(compCodeValue);
        Map<String, String> company = new HashMap<String, String>();
        for (int i = 0; i < resultCompList.size(); i++) {
            company.put((String) resultCompList.get(i).get("COMP_CODE"), resultCompList.get(i).get("TXTMD"));
            
        }
//        dataList = iReportMapperFS17.selectData(dateYearMonth, compCodeValue);
        
        dataList = iReportMapperFS17.selectDataFS17(dateYearMonth, compCodeValue.get(0));

        if (!isExport) {
        	int count =0 ;
        	List<THM_IMP_FS17> tmpImp = new ArrayList<THM_IMP_FS17>();
        	for (THM_IMP_FS17 valueOne : dataList) {
        		THM_IMP_FS17 imp = new THM_IMP_FS17();
        		if(count == dataList.size()/2 || count == 0){
        			imp.setItemName(headerList.get(0));
        			imp.setItemNo(headerList.get(1));
        			imp.setCurrentMoney(headerList.get(2));
        			imp.setPreviousMoney(headerList.get(3));
        			tmpImp.add(imp);
        			imp = new THM_IMP_FS17();
        		}
    			imp.setItemName(valueOne.getItemName());
    			imp.setItemNo(valueOne.getItemNo());
    			imp.setCurrentMoney(valueOne.getCurrentMoney());
    			imp.setPreviousMoney(valueOne.getPreviousMoney());
    			tmpImp.add(imp);
    			imp = new THM_IMP_FS17();
    			count ++;
        	}
            int size = tmpImp.size();
            if(size%2 != 0){
            	THM_IMP_FS17 imp = new THM_IMP_FS17();
            	imp.setItemNo(String.valueOf(size+1));
            	imp.setItemName("");
            	imp.setCurrentMoney("");
            	imp.setPreviousMoney("");
            	tmpImp.add(imp);
            }
            
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(tmpImp);
            log.debug("FS17 json++++++++++ " + json);
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
            ee = new ExcelExportUtil("合并现金流量表", secondTitle, headerList, "（合并）");
            List<List<Object>> dataList = Lists.newArrayList();
            for (int j = 0; j < resultDataRowList.size(); j++) {
                dataList.add(resultDataRowList.get(j));
            }
            for (int i = 0; i < resultDataRowList.size(); i++) {
                Row row = ee.addRow();
                for (int j = 0; j < dataList.get(i).size(); j++) {
                    String val = StringUtil.nullToStr(dataList.get(i).get(j));
                    if (j == 2 || j == 3 || j == 6 || j == 7) {
                        try{
                            ee.addCell(row, row.getRowNum(), j, decimalFormat.format(val), 3, true, Double.class);
                        }catch(java.lang.IllegalArgumentException e){
                            ee.addCell(row, row.getRowNum(), j, val, 3, true, Double.class);
                        }
                    } else if (j == 0 || j == 4) {//行项目处理
                        String itemRn = StringUtil.nullToStr(dataList.get(i).get(j + 1));

                        if ("1".equals(itemRn)
                                || "15".equals(itemRn)
                                || "25".equals(itemRn)
                                || "26".equals(itemRn)
                                || "27".equals(itemRn)
                                || "33".equals(itemRn)
                                || "39".equals(itemRn)
                                || "40".equals(itemRn)
                                || "41".equals(itemRn)
                                || "47".equals(itemRn)
                                || "52".equals(itemRn)
                                || "53".equals(itemRn)
                                || "54".equals(itemRn)
                                || "55".equals(itemRn)
                                || "57".equals(itemRn)) {//加粗
                            ee.addCell(row, row.getRowNum(), j, val.replaceAll("&nbsp;", " "), 4, true, String.class);
                        } else {
                            ee.addCell(row, row.getRowNum(), j, val.replaceAll("&nbsp;", " "), 1, true, String.class);
                        }
                    }else {//行次
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
    private List<List<Object>> export(List<THM_IMP_FS17> data) throws IOException {
        // TODO Auto-generated method stub
        //
        String itemname = null;
        Matcher matcher = null;
        String regEx_special = "\\&[a-zA-Z]{1,10};";
        Pattern pattern = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
        List<List<Object>> dataRowList = Lists.newArrayList();
        List<Object> list = new ArrayList<Object>();
        NumberFormat decimalFormat = new DecimalFormat("###,###.##");
        int size = data.size();
        if(size%2 != 0){
        	THM_IMP_FS17 imp = new THM_IMP_FS17();
        	imp.setItemNo(String.valueOf(size+1));
        	imp.setItemName("");
        	imp.setCurrentMoney("");
        	imp.setPreviousMoney("");
        	data.add(imp);
        }
        size = data.size();
        for (THM_IMP_FS17 valueOne : data) {
            for (THM_IMP_FS17 valueTwo : data) {
            	
                int step = Integer.parseInt(valueTwo.getItemNo()) - Integer.parseInt(valueOne.getItemNo());
                if (step == data.size()/2) {
                    itemname = valueOne.getItemName();
                    matcher = pattern.matcher(itemname);
                    itemname = matcher.replaceAll(" "); // 过滤标签
                    list.add(itemname);
                    list.add(valueOne.getItemNo() + ""); //行次
                    if("——".equals(valueOne.getCurrentMoney()) || "——".equals(valueOne.getPreviousMoney())){
                    	list.add(valueOne.getCurrentMoney()); //本期金额
                    	list.add(valueOne.getPreviousMoney()); //上期金额
                	}else if("".equals(valueOne.getCurrentMoney()) || "".equals(valueOne.getPreviousMoney()) || " ".equals(valueOne.getCurrentMoney()) || " ".equals(valueOne.getPreviousMoney())){
                    	list.add(""); //本期金额
                    	list.add(""); //上期金额
                	}else{
                		if((valueOne.getCurrentMoney() == null)){
                        	list.add((valueOne.getCurrentMoney())); //期末金额
                		}
                		if(valueOne.getPreviousMoney() == null){
                			list.add(valueOne.getPreviousMoney()); //年初金额
                		}
                		if(valueOne.getCurrentMoney() != null && valueOne.getPreviousMoney() != null){
                			if(!FileUtils.isNumeric(valueOne.getCurrentMoney().replaceAll(",", "").replace("，", ""))){
                				list.add(valueOne.getCurrentMoney()); //
                			}else{
                				list.add(decimalFormat.format(Double.valueOf(valueOne.getCurrentMoney()))); //
                			}
                			if(!FileUtils.isNumeric(valueOne.getPreviousMoney().replaceAll(",", "").replace("，", ""))){
                				list.add(valueOne.getPreviousMoney()); //
                			}else{
                				list.add(decimalFormat.format(Double.valueOf(valueOne.getPreviousMoney()))); //
                			}
                		}

                	}
                    
                    itemname = valueTwo.getItemName();
                    matcher = pattern.matcher(itemname);
                    itemname = matcher.replaceAll(" "); // 过滤标签
                    list.add(itemname);
                    list.add(valueTwo.getItemNo() + ""); //行次
                    if("——".equals(valueTwo.getCurrentMoney()) || "——".equals(valueTwo.getPreviousMoney())){
                    	list.add(valueTwo.getCurrentMoney()); //本期金额
                    	list.add(valueTwo.getPreviousMoney()); //上期金额
                	}else if("".equals(valueTwo.getCurrentMoney()) || "".equals(valueTwo.getPreviousMoney()) || " ".equals(valueTwo.getCurrentMoney()) || " ".equals(valueTwo.getPreviousMoney())){
                    	list.add(""); //本期金额
                    	list.add(""); //上期金额
                	}else{
                		if((valueTwo.getCurrentMoney() == null)){
                        	list.add((valueTwo.getCurrentMoney())); //期末金额
                		}
                		if(valueTwo.getPreviousMoney() == null){
                			list.add(valueTwo.getPreviousMoney()); //年初金额
                		}
                		if(valueTwo.getCurrentMoney() != null && valueTwo.getPreviousMoney() != null){
                			if(!FileUtils.isNumeric(valueTwo.getCurrentMoney().replaceAll(",", "").replace("，", ""))){
                				list.add(valueTwo.getCurrentMoney()); //
                			}else{
                				list.add(decimalFormat.format(Double.valueOf(valueTwo.getCurrentMoney()))); //
                			}
                			if(!FileUtils.isNumeric(valueTwo.getPreviousMoney().replaceAll(",", "").replace("，", ""))){
                				list.add(valueTwo.getPreviousMoney()); //
                			}else{
                				list.add(decimalFormat.format(Double.valueOf(valueTwo.getPreviousMoney()))); //
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
        ObjectMapper mapper = new ObjectMapper();
        //导入数据
        ExcelImportUtil ei = new ExcelImportUtil(filePath, 3);
        headerList = Lists.newArrayList();
        headerList.add("项目");
        headerList.add("行次");
        headerList.add("本期金额");
        headerList.add("上期金额");
        headerList.add("项目");
        headerList.add("行次");
        headerList.add("本期金额");
        headerList.add("上期金额");
        json = ei.checkHeadList(headerList, listMap);
        if (null != json) {
            return json;
        }
        String company = ei.getCellValue(2, 0).toString();
        String time = ei.getCellValue(2, ei.getLastCellNum()-1).toString();
        Pattern p = Pattern.compile("[^0-9]");
        matcher = p.matcher(time);
        if (company.indexOf(":") > 0) {
            company = company.substring(company.indexOf(":") + 1, company.length());
        } else if (company.indexOf("：") > 0) {
            company = company.substring(company.indexOf("：") + 1, company.length());
        }
        if (company.trim().length() == 0) {
            listMap.put("ERRORCOMPANEY", "未填写编制单位!");
        }
        getCompList = iReportMapperFS17.selectCompanyCode(company.trim());
        if(getCompList != null){
            if (getCompList.size() <= 0) {
                listMap.put("ERRORCOMPANEY", "编制单位填写错误，没有该编制单位!");
            }else{
            	company = getCompList.get(0).get("COMP_CODE");
            }
            yearMonth = matcher.replaceAll("").trim();
            if (!FileUtils.isValidDate(yearMonth)) { //日期校验
                listMap.put("ERRORTIME", "时间填写错误!");
            }
        }else{
        	listMap.put("ERRORCOMPANEY", "编制单位填写错误，没有该编制单位!");
        }
        //如果验证数据没有问题，则返回所有的数据，标志位为1，若是数据有问题，则返回不正确数据，标准为0
        List<InsertFieldTHM_FS17> list = ei.getDataList(InsertFieldTHM_FS17.class, "FS17");
        
        List<InsertFieldTHM_FS17> tmpDataList = new ArrayList<InsertFieldTHM_FS17>();
        InsertFieldTHM_FS17 tmp = new InsertFieldTHM_FS17();
        //取表头
//    	tmp.setProjectOne(ei.getRow(ei.getHeaderNum()).getCell(0).getStringCellValue());
//        tmp.setLineNumOne(ei.getRow(ei.getHeaderNum()).getCell(1).getStringCellValue());
//        tmp.setCurrentAccountOne(ei.getRow(ei.getHeaderNum()).getCell(2).getStringCellValue());
//        tmp.setPreviousAccountOne(ei.getRow(ei.getHeaderNum()).getCell(3).getStringCellValue());
//        tmp.setYearMonth(yearMonth);
//        tmp.setCompanyCode(company);
//        tmpDataList.add(tmp);
//        tmp = new InsertFieldTHM_FS17();
        
        //导入数据处理
        for (int i = 0; i < list.size(); i++) {
            if (!StringUtil.isEmpty(list.get(i).getLineNumOne())) {
            	tmp.setProjectOne(list.get(i).getProjectOne());
                tmp.setLineNumOne(list.get(i).getLineNumOne());
                tmp.setCurrentAccountOne(list.get(i).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
                tmp.setPreviousAccountOne(list.get(i).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
                tmp.setYearMonth(yearMonth);
                tmp.setCompanyCode(company);
                tmpDataList.add(tmp);
                tmp = new InsertFieldTHM_FS17();
            }
        }
        
        for (int j = 0; j < list.size(); j++) {
            if (!StringUtil.isEmpty(list.get(j).getLineNumTwo())) {
            	tmp.setProjectOne(list.get(j).getProjectTwo());
                tmp.setLineNumOne(list.get(j).getLineNumTwo());
                tmp.setCurrentAccountOne(list.get(j).getCurrentAccountTwo().replaceAll(",", "").replace("，", ""));
                tmp.setPreviousAccountOne(list.get(j).getPreviousAccountTwo().replaceAll(",", "").replace("，", ""));
                tmp.setYearMonth(yearMonth);
                tmp.setCompanyCode(company);
                tmpDataList.add(tmp);
                tmp = new InsertFieldTHM_FS17();
            }
        }
        
        //校验数据是否准确，加和逻辑，分项与统计是否匹配
//        List<String> valids = checkData(tmpDataList, yearMonth);
//        if (valids.size() > 0) {
//        	listMap.put("ERRORDATA", valids);
//        	errorMap.put("ERROR", listMap);
//            return mapper.writeValueAsString(errorMap);
//        }
        
//        iReportMapperFS17.saveOrUpdateData(yearMonth, company, tmpDataList);
        iReportMapperFS17.saveOrUpdateDataFS17(yearMonth, company, tmpDataList);
        
        listMap.put("SUCCESSES", "导入成功!");
        json = mapper.writeValueAsString(listMap);

        return json;
    }
    
    /**
     * 校验数据分项与统计是否匹配
     * @param list
     * @return 
     */
    private List<String> checkData(List<InsertFieldTHM_FS17> list, String yearMonth) throws JsonProcessingException {
        List<String> valids = new ArrayList<String>();
        int num1 = 5;
        int num2 = 5;
        int columnCurrentAccountOne = 3;
        int columnPreviousAccountOne = 4;
        int columnCurrentAccountTwo = 7;
        int columnPreviousAccountTwo = 8;
        int size = list.size();
    	Map<String, String> map = iReportMapperFS17.selectMaxYear();
        dateYearMonth = map.get("YEARS");
        if("2015".equals(dateYearMonth) || "2016".equals(dateYearMonth)){
        	for(int i=0; i<list.size(); i++){
            	if(list.get(i).getLineNumOne() != null && !"".equals(list.get(i).getLineNumOne())){
            		if(Integer.parseInt(list.get(i).getLineNumOne()) >=1 && Integer.parseInt(list.get(i).getLineNumOne()) <= size/2){
            			if(Integer.parseInt(list.get(i).getLineNumOne()) != 1 && Integer.parseInt(list.get(i).getLineNumOne()) != 27){
                    		if(!FileUtils.isNumeric(list.get(i).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))){
                    			valids.add("第 "+num1+" 行第 "+columnCurrentAccountOne+" 列数据格式错误!");
                        	}else if(!FileUtils.isNumeric(list.get(i).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))){
                    			valids.add("第 "+num1+" 行第 "+columnPreviousAccountOne+" 列数据格式错误!");
                        	}
                    	}
            			num1 ++;
            		}
        			if(Integer.parseInt(list.get(i).getLineNumOne()) > size/2 && Integer.parseInt(list.get(i).getLineNumOne()) <= size){
            			if(Integer.parseInt(list.get(i).getLineNumOne()) != 41 && Integer.parseInt(list.get(i).getLineNumOne()) != 58){
                    		if(!FileUtils.isNumeric(list.get(i).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))){
                    			valids.add("第 "+num2+" 行第 "+columnCurrentAccountTwo+" 列数据格式错误!");
                        	}else if(!FileUtils.isNumeric(list.get(i).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))){
                    			valids.add("第 "+num2+" 行第 "+columnPreviousAccountTwo+" 列数据格式错误!");
                        	}
                    	}
            			num2 ++;
            		}
            	}
            }
        }
        if(valids.size() == 0){
            //15年与16年的校验都是一样的校验规则
        	if(dateYearMonth.equals(yearMonth.substring(1, 4)) || dateYearMonth.equals(yearMonth.substring(1, 4))){
        		 valids.addAll(check2015(list));
        	}
        }
        
        return valids;
    }

    public List<String> check2015(List<InsertFieldTHM_FS17> list){
    	List<String> valids = new ArrayList<String>();
    	//2+3…14 = 15 本期金额
        Double item15_currentAccountOne = StringUtils.isEmpty(list.get(14).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(14).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        Double item2_14_currentAccountOne = 0d;
        for(int i=1;i<14;i++){
            item2_14_currentAccountOne += StringUtils.isEmpty(list.get(i).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        }
        if(!Objects.equals(new BigDecimal(item2_14_currentAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), item15_currentAccountOne)){
            valids.add("行次2+3…14 本期金额之和 不等于 行次15本期金额");
        }
        //2+3…14 = 15 上期金额
        Double item15_previousAccountOne = StringUtils.isEmpty(list.get(14).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(14).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        Double item2_14_previousAccountOne = 0d;
        for(int i=1;i<14;i++){
            item2_14_previousAccountOne += StringUtils.isEmpty(list.get(i).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        }
        if(!Objects.equals(new BigDecimal(item2_14_previousAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), item15_previousAccountOne)){
            valids.add("行次2+3…14 上期金额之和 不等于 行次15上期金额");
        }
        
        //16+17…24 = 25 本期金额
        Double item25_currentAccountOne = StringUtils.isEmpty(list.get(24).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(24).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        Double item16_24_currentAccountOne = 0d;
        for(int i=15;i<24;i++){
            item16_24_currentAccountOne += StringUtils.isEmpty(list.get(i).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        }
        if(!Objects.equals(new BigDecimal(item16_24_currentAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), item25_currentAccountOne)){
            valids.add("行次16+17…24 本期金额之和 不等于 行次25本期金额");
        }
        //16+17…24 = 25 上期金额
        Double item25_previousAccountOne = StringUtils.isEmpty(list.get(24).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(24).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        Double item16_24_previousAccountOne = 0d;
        for(int i=15;i<24;i++){
            item16_24_previousAccountOne += StringUtils.isEmpty(list.get(i).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        }
        if(!Objects.equals(new BigDecimal(item16_24_previousAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), item25_previousAccountOne)){
            valids.add("行次16+17…24 上期金额之和 不等于 行次25上期金额");
        }
        
        //15-25=26 本期金额
        Double item26_currentAccountOne = StringUtils.isEmpty(list.get(25).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(25).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item15_currentAccountOne - item25_currentAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item26_currentAccountOne){
            valids.add("行次15与行次25 本期金额之差 不等于 行次26本期金额");
        }
        //15-25=26 上期金额
        Double item26_previousAccountOne = StringUtils.isEmpty(list.get(25).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(25).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item15_previousAccountOne - item25_previousAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item26_previousAccountOne){
            valids.add("行次15与行次25 上期金额之差 不等于 行次26上期金额");
        }
        
        //28+29…32 = 33 本期金额
        Double item33_currentAccountOne = StringUtils.isEmpty(list.get(32).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(32).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        Double item28_32_currentAccountOne = 0d;
        for(int i=27;i<32;i++){
            item28_32_currentAccountOne += StringUtils.isEmpty(list.get(i).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        }
        if(!Objects.equals(new BigDecimal(item28_32_currentAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), item33_currentAccountOne)){
            valids.add("行次28+29…32 本期金额之和 不等于 行次33本期金额");
        }
        //28+29…32 = 33 上期金额
        Double item33_previousAccountOne = StringUtils.isEmpty(list.get(32).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(32).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        Double item28_32_previousAccountOne = 0d;
        for(int i=27;i<32;i++){
            item28_32_previousAccountOne += StringUtils.isEmpty(list.get(i).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        }
        if(!Objects.equals(new BigDecimal(item28_32_previousAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), item33_previousAccountOne)){
            valids.add("行次28+29…32 上期金额之和 不等于 行次33上期金额");
        }
        
        //34+35…38 = 39 本期金额
        Double item39_currentAccountOne = StringUtils.isEmpty(list.get(38).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(38).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        Double item34_38_currentAccountOne = 0d;
        for(int i=33;i<38;i++){
            item34_38_currentAccountOne += StringUtils.isEmpty(list.get(i).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        }
        if(!Objects.equals(new BigDecimal(item34_38_currentAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), item39_currentAccountOne)){
            valids.add("行次34+35…38 本期金额之和 不等于 行次39本期金额");
        }
        //34+35…38 = 39 上期金额
        Double item39_previousAccountOne = StringUtils.isEmpty(list.get(38).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(38).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        Double item34_38_previousAccountOne = 0d;
        for(int i=33;i<38;i++){
            item34_38_previousAccountOne += StringUtils.isEmpty(list.get(i).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(i).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        }
        if(!Objects.equals(new BigDecimal(item34_38_previousAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), item39_previousAccountOne)){
            valids.add("行次34+35…38 上期金额之和 不等于 行次39上期金额");
        }

        //33-39=40 本期金额
        Double item40_currentAccountOne = StringUtils.isEmpty(list.get(39).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(39).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item33_currentAccountOne - item39_currentAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item40_currentAccountOne){
            valids.add("行次33与行次39 本期金额之差 不等于 行次40本期金额");
        }
        //33-39=40 上期金额
        Double item40_previousAccountOne = StringUtils.isEmpty(list.get(39).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(39).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        if(new BigDecimal(item33_previousAccountOne - item39_previousAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != item40_previousAccountOne){
            valids.add("行次33与行次39 上期金额之差 不等于 行次40上期金额");
        }
        
        //43小于等于42 本期金额
        Double item43_currentAccountOne = StringUtils.isEmpty(list.get(42).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(42).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        Double item42_currentAccountOne = StringUtils.isEmpty(list.get(41).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(41).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        if(item43_currentAccountOne > item42_currentAccountOne){
            valids.add("行次43本期金额 大于 行次42本期金额");
        }
        //43小于等于42 上期金额
        Double item43_previousAccountOne = StringUtils.isEmpty(list.get(42).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(42).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        Double item42_previousAccountOne = StringUtils.isEmpty(list.get(41).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(41).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        if(item43_previousAccountOne > item42_previousAccountOne){
            valids.add("行次43上期金额 大于 行次42上期金额");
        }
        
        //42+44+45+46 = 47 本期金额
        Double item44_currentAccountOne = StringUtils.isEmpty(list.get(43).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(43).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        Double item45_currentAccountOne = StringUtils.isEmpty(list.get(44).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(44).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        Double item46_currentAccountOne = StringUtils.isEmpty(list.get(45).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(45).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        Double item47_currentAccountOne = StringUtils.isEmpty(list.get(46).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(46).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        if((new BigDecimal(item44_currentAccountOne + item45_currentAccountOne + item46_currentAccountOne + item42_currentAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) != item47_currentAccountOne){
            valids.add("行次42+44+45+46本期金额之和 不等于 行次47本期金额");
        }
        //42+44+45+46 = 47 上期金额
        Double item44_previousAccountOne = StringUtil.isEmpty(list.get(43).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(43).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        Double item45_previousAccountOne = StringUtils.isEmpty(list.get(44).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(44).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        Double item46_previousAccountOne = StringUtils.isEmpty(list.get(45).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(45).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        Double item47_previousAccountOne = StringUtils.isEmpty(list.get(46).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(46).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        if((new BigDecimal(item44_previousAccountOne + item45_previousAccountOne + item46_previousAccountOne + item42_previousAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) != item47_previousAccountOne){
            valids.add("行次42+44+45+46上期金额之和 不等于 行次47上期金额");
        }
        
        //50小于等于49 本期金额
        Double item50_currentAccountOne = StringUtils.isEmpty(list.get(49).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(49).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        Double item49_currentAccountOne = StringUtils.isEmpty(list.get(48).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(48).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        if(item50_currentAccountOne > item49_currentAccountOne){
            valids.add("行次50本期金额 大于 行次49本期金额");
        }
        //50小于等于49 上期金额
        Double item50_previousAccountOne = StringUtils.isEmpty(list.get(49).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(49).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        Double item49_previousAccountOne = StringUtils.isEmpty(list.get(48).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(48).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        if(item50_previousAccountOne > item49_previousAccountOne){
            valids.add("行次50上期金额 大于 行次49上期金额");
        }
        
        //48+49+51 = 52 本期金额
        Double item48_currentAccountOne = StringUtils.isEmpty(list.get(47).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(47).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        Double item51_currentAccountOne = StringUtils.isEmpty(list.get(50).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(50).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        Double item52_currentAccountOne = StringUtils.isEmpty(list.get(51).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(51).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        if((new BigDecimal(item48_currentAccountOne + item49_currentAccountOne + item51_currentAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) != item52_currentAccountOne){
            valids.add("行次48+49+51本期金额之和 不等于 行次52本期金额");
        }
        //48+49+51 = 52 上期金额
        Double item48_previousAccountOne = StringUtils.isEmpty(list.get(47).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(47).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        Double item51_previousAccountOne = StringUtils.isEmpty(list.get(50).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(50).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        Double item52_previousAccountOne = StringUtils.isEmpty(list.get(51).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(51).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        if((new BigDecimal(item48_previousAccountOne + item49_previousAccountOne + item51_previousAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) != item52_previousAccountOne){
            valids.add("行次48+49+51上期金额之和 不等于 行次52上期金额");
        }
        
        //26+40+53+54 = 55 本期金额
        Double item53_currentAccountOne = StringUtils.isEmpty(list.get(52).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(52).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        Double item54_currentAccountOne = StringUtils.isEmpty(list.get(53).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(53).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        Double item55_currentAccountOne = StringUtils.isEmpty(list.get(54).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(54).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        if((new BigDecimal(item26_currentAccountOne + item40_currentAccountOne + item53_currentAccountOne + item54_currentAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) != item55_currentAccountOne){
            valids.add("行次26+40+53+54本期金额之和 不等于 行次55本期金额");
        }
        //26+40+53+54 = 55 上期金额
        Double item53_previousAccountOne = StringUtils.isEmpty(list.get(52).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(52).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        Double item54_previousAccountOne = StringUtils.isEmpty(list.get(53).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(53).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        Double item55_previousAccountOne = StringUtils.isEmpty(list.get(54).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(54).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        if((new BigDecimal(item26_previousAccountOne + item40_previousAccountOne + item53_previousAccountOne + item54_previousAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) != item55_previousAccountOne){
            valids.add("行次26+40+53+54上期金额之和 不等于 行次55上期金额");
        }
        
        //55+56 = 57 本期金额
        Double item56_currentAccountOne = StringUtils.isEmpty(list.get(55).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(55).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        Double item57_currentAccountOne = StringUtils.isEmpty(list.get(56).getCurrentAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(56).getCurrentAccountOne().replaceAll(",", "").replace("，", ""));
        if((new BigDecimal(item55_currentAccountOne + item56_currentAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) != item57_currentAccountOne){
            valids.add("行次55+56本期金额之和 不等于 行次57本期金额");
        }
        //55+56 = 57 上期金额
        Double item56_previousAccountOne = StringUtils.isEmpty(list.get(55).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(55).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        Double item57_previousAccountOne = StringUtils.isEmpty(list.get(56).getPreviousAccountOne().replaceAll(",", "").replace("，", ""))?0d:Double.valueOf(list.get(56).getPreviousAccountOne().replaceAll(",", "").replace("，", ""));
        if((new BigDecimal(item55_previousAccountOne + item56_previousAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) != item57_previousAccountOne){
            valids.add("行次55+56上期金额之和 不等于 行次57上期金额");
        }
        //47-52 = 53 本期金额
        if((new BigDecimal(item47_currentAccountOne - item52_currentAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) != item53_currentAccountOne){
            valids.add("行次47-52上期金额之差 不等于 行次53上期金额");
        }
        //47-52 = 53 上期金额
        if((new BigDecimal(item47_previousAccountOne - item52_previousAccountOne).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) != item53_previousAccountOne){
            valids.add("行次47-52上期金额之差 不等于 行次53上期金额");
        }
        //验证行次58数据为空，不用导入导出
        if(!StringUtils.isEmpty(list.get(57).getPreviousAccountOne().trim()) || !StringUtils.isEmpty(list.get(57).getCurrentAccountOne().trim())){
        	valids.add("行次58  应为空");
        }
        //验证行次1、27、41
        if("——".equals(list.get(0).getPreviousAccountOne().trim().trim()) || "——".equals(list.get(0).getCurrentAccountOne().trim().trim())){
        	valids.add("行次1应为 ——");
        }else if(!StringUtils.isEmpty(list.get(26).getPreviousAccountOne().trim()) || !StringUtils.isEmpty(list.get(26).getCurrentAccountOne().trim())){
        	valids.add("行次27  应为 ——");
        }else if(!StringUtils.isEmpty(list.get(40).getPreviousAccountOne().trim()) || !StringUtils.isEmpty(list.get(40).getCurrentAccountOne().trim())){
        	valids.add("行次41应为 ——");
        }
        return valids;
    }
}
