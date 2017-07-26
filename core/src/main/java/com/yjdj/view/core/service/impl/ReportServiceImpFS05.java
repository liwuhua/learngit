package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.yjdj.view.core.entity.mybeans.InsertFieldTHM_FS05;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS05;
import com.yjdj.view.core.mapper.IReportMapperFS05;
import com.yjdj.view.core.service.IReportServiceFS05;
import com.yjdj.view.core.util.ExcelExportUtil05;
import com.yjdj.view.core.util.ExcelImportUtil;
import com.yjdj.view.core.util.FileUtils;

/**
 * Created by zhangwenguo on 2016.11.28
 * 
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS05 implements IReportServiceFS05 {

	private final Logger log = Logger.getLogger(ReportServiceImpFS01.class);
	@Autowired
	private IReportMapperFS05 iReportMapperFS05;
	private List<String> compCodeValue = null; //公司代码
	private String compCode = null;
	private String startTime = null; //开始时间
	private String endTime = null; //结束时间
	private Boolean isExport = false; //是否是导出
	private List<String> headerList = null;
	private List<String> allCompCode = null;
	private String startTmpTime = null;
	private String endTmpTime = null;
	
	@Override
	public Object getReportJson(QueryBean queryBean, List<String> checkComp) throws IOException {
		// TODO Auto-generated method stub
		init(queryBean, checkComp);
		ObjectMapper mapper = new ObjectMapper();
		List<String> listComp = new ArrayList<String>();
		Map<String,Object> errorComp = new HashMap<String, Object>();
		for(int i=0; i<compCodeValue.size(); i++){
			if(!checkComp.contains(compCodeValue.get(i))){
				if(!"India".equalsIgnoreCase(compCodeValue.get(i)) && !"SouthAfrica".equalsIgnoreCase(compCodeValue.get(i))){
					Map<String,Object> map = iReportMapperFS05.getCompTxtmdByCode(compCodeValue.get(i));
					if(map != null){
						listComp.add("没有权限导入 "+map.get("TXTMD"));
					}
				}else{
					Map<String,Object> map = iReportMapperFS05.getCompTxtmdNFAndYDByCode(compCodeValue.get(i));
					if(map != null){
						listComp.add("没有权限导入 "+ map.get("TXTMD"));
					}
				}
			}
		}
		if(listComp.size() > 0){
			errorComp.put("ERRORCOMP", listComp);
			String json = mapper.writeValueAsString(errorComp);
			return json;
		}
		List<THM_FS05> resultData = iReportMapperFS05.getTaxData(startTime, endTime, compCode);
		List<Object> listMap = handleData(resultData,isExport);
		if(!isExport){
			int num = 1;
			Map<String,Object> mapCompList = new HashMap<String, Object>();
			Map<String,Object> resultMap = new HashMap<String, Object>();
			Map<String,Object> mapComp = new HashMap<String, Object>();
			List<Object> resultList = new ArrayList<Object>(); 
			Map<String,Object> map = null;
			for(Object value:listMap){
				@SuppressWarnings("unchecked")
				Map<String,Object> map1 = (Map<String, Object>) value;
				@SuppressWarnings("unchecked")
				Map<String,Object> map2 = (Map<String, Object>) map1.get(String.valueOf(num));
				resultList.add(map2);
				num ++;
			}
			resultMap.put("data", resultList);
			for(int i=0; i<compCodeValue.size(); i++){
				if("India".equals(compCodeValue.get(i)) || "SouthAfrica".equals(compCodeValue.get(i))){
					map = iReportMapperFS05.getCompTxtmdNFAndYDByCode(compCodeValue.get(i));
				}else{
					map = iReportMapperFS05.getCompTxtmdByCode(compCodeValue.get(i));
				}
				String txtmd = String.valueOf(map.get("TXTMD"));
				mapCompList.put(compCodeValue.get(i), txtmd);
			}
			resultMap.put("header", mapCompList);
			if(compCodeValue.size() == allCompCode.size()){
				mapComp.put("company", "中车永济电机有限公司合计");
			}else if(compCodeValue.size() == 1){
				map = iReportMapperFS05.getCompTxtmdByCode(compCodeValue.get(0));
				if(map == null){
					map = iReportMapperFS05.getCompTxtmdNFAndYDByCode(compCodeValue.get(0));
					if(map != null){
						mapComp.put("company", String.valueOf(map.get("TXTMD")));
					}
				}else{
					mapComp.put("company", String.valueOf(map.get("TXTMD")));
				}
				
			}else{
				mapComp.put("company", "");
			}
			resultMap.put("company", mapComp);
			String json = mapper.writeValueAsString(resultMap);
			log.info("json++++++++++++"+json);
			return json;
		}
		return export(listMap);
	}
	/**
	 * 数据初始化
	 * @param queryBean
	 */
	private void init(QueryBean queryBean, List<String> checkComp){
		if(queryBean != null){
			compCodeValue = queryBean.getCompCodeValue();
			isExport = queryBean.isExport();
			startTime = queryBean.getStartTime();
			endTime = queryBean.getEndTime();
		}
		StringBuffer sb = new StringBuffer();
		allCompCode = new ArrayList<String>();
		allCompCode.add("5003"); //永济公司
		allCompCode.add("5004"); //西安永电
		allCompCode.add("5005"); //西安捷力
		allCompCode.add("5006"); //修配公司
		allCompCode.add("5025"); //西安永电金风
		allCompCode.add("5142"); //托克逊公司
		allCompCode.add("India"); //印度公司
		allCompCode.add("SouthAfrica"); //南非公司
		if(compCodeValue == null || compCodeValue.size() == 0){
			compCodeValue = checkComp;
		}
		if(compCodeValue != null && compCodeValue.size() != 0){
			for(int i=0; i<compCodeValue.size(); i++){
				if("India".equals(compCodeValue.get(i)) || "SouthAfrica".equals(compCodeValue.get(i))){
					sb.append("'"+compCodeValue.get(i)+"'").append(",");
				}else{
					sb.append(compCodeValue.get(i)).append(",");
				}
			}
			String codeStr = sb.toString();
			compCode = codeStr.substring(0, codeStr.length()-1);
		}
		if(startTime != null && !"".equals(startTime)){
			startTime = startTime.substring(0, 4)+"0"+startTime.substring(4,6);
		}
		if(endTime != null && !"".equals(endTime)){
			endTime = endTime.substring(0, 4)+"0"+endTime.substring(4,6);
		}
		//初始化公司
		if(isExport){
			startTmpTime = queryBean.getStartTime();
			endTmpTime = queryBean.getEndTime();
			headerList = new ArrayList<String>();
			headerList.add("项目");
			headerList.add("行次");
			headerList.add("中车永济电机有限公司");
			headerList.add("西安中车永电电气有限公司");
			headerList.add("西安中车永电捷力风能有限公司");
			headerList.add("永济中车电机电器修配有限公司");
			headerList.add("西安中车永电金风科技有限公司");
			headerList.add("托克逊中车永电能源装备有限公司");
			headerList.add("印度中车先锋电气有限公司");
			headerList.add("南非项目（公司）");
			headerList.add("合计");
		}
		
	}
	/**
	 * 数据格式转换
	 * @param resultData
	 * @return
	 */
	private List<Object> handleData(List<THM_FS05> resultData, boolean isExport){
		String[] rbukrs = null;
		String[] total = null;
		int pace1 = 1;
		int pace2 = 1;
		int pace3 = 1;
		double sum = 0.00;
		double toTalColumn77 = 0.00;
		double toTalColumn78 = 0.00;
		double toTalColumn77YJ = 0.00;
		double toTalColumn77XP = 0.00;
		double toTalColumn77JL = 0.00;
		double toTalColumn77XADQ = 0.00;
		double toTalColumn77JF = 0.00;
		double toTalColumn77TKX = 0.00;
		double toTalColumn77YD = 0.00;
		double toTalColumn77NF = 0.00;
		double toTalColumn78YJ = 0.00;
		double toTalColumn78XP = 0.00;
		double toTalColumn78JL = 0.00;
		double toTalColumn78XADQ = 0.00;
		double toTalColumn78JF = 0.00;
		double toTalColumn78TKX = 0.00;
		double toTalColumn78YD = 0.00;
		double toTalColumn78NF = 0.00;		
		List<String> sumColumn77List = new ArrayList<String>();
		List<String> sumColumn78List = new ArrayList<String>();
		
		List<Object> resultList = new ArrayList<Object>();
		List<Object> rbukrsList = new ArrayList<Object>();
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> resultMap = new HashMap<String, Object>();
		NumberFormat decimalFormat = new DecimalFormat("###,###.##");
		
		for(THM_FS05 value:resultData){
			map.put("ITEM", value.getItem());
			map.put("ITEM_COLUMN", value.getItem_column());
			if(value.getRbukrs() != null && value.getTotal() != null){
				rbukrs = value.getRbukrs().split(",");
				for(int i=0; i<rbukrs.length; i++){
					rbukrsList.add(rbukrs[i]);
				}
				total = value.getTotal().split(",");
				for(int i=0; i<rbukrs.length; i++){
					if(i <= total.length-1){
						if(total[i] != null && !"".equals(total[i].trim())){
							sum += Double.parseDouble(total[i]);
							map.put(rbukrs[i], decimalFormat.format(Double.parseDouble(total[i])));
						}
					}
					
				}
				//77=3+8+12+13+16+21+26+31+41  
				if(Integer.parseInt(value.getItem_column()) == 3 || Integer.parseInt(value.getItem_column()) == 8
						|| Integer.parseInt(value.getItem_column()) == 12 || Integer.parseInt(value.getItem_column()) == 13
						|| Integer.parseInt(value.getItem_column()) == 16 || Integer.parseInt(value.getItem_column()) == 21
						|| Integer.parseInt(value.getItem_column()) == 26 || Integer.parseInt(value.getItem_column()) == 31
						|| Integer.parseInt(value.getItem_column()) == 41){
					
					for(int i=0; i<rbukrs.length; i++){
						if(i <= total.length-1){
							if("".equals(total[i].trim())){
								continue;
							}
							if("5003".equals(rbukrs[i])){
								toTalColumn77YJ += Double.parseDouble(total[i]);
							}else if("5004".equals(rbukrs[i])){
								toTalColumn77XADQ += Double.parseDouble(total[i]);
							}else if("5005".equals(rbukrs[i])){
								toTalColumn77JL += Double.parseDouble(total[i]);
							}else if("5006".equals(rbukrs[i])){
								toTalColumn77XP += Double.parseDouble(total[i]);
							}else if("5025".equals(rbukrs[i])){
								toTalColumn77JF += Double.parseDouble(total[i]);
							}else if("5142".equals(rbukrs[i])){
								toTalColumn77TKX += Double.parseDouble(total[i]);
							}else if("India".equals(rbukrs[i])){
								toTalColumn77YD += Double.parseDouble(total[i]);
							}else if("SouthAfrica".equals(rbukrs[i])){
								toTalColumn77NF += Double.parseDouble(total[i]);
							}
						}
					}
				}
				//78=4+9+12+13+17+22+27+32+42
				if(Integer.parseInt(value.getItem_column()) == 4 || Integer.parseInt(value.getItem_column()) == 9
						|| Integer.parseInt(value.getItem_column()) == 12 || Integer.parseInt(value.getItem_column()) == 13
						|| Integer.parseInt(value.getItem_column()) == 17 || Integer.parseInt(value.getItem_column()) == 22
						|| Integer.parseInt(value.getItem_column()) == 27 || Integer.parseInt(value.getItem_column()) == 32
						|| Integer.parseInt(value.getItem_column()) == 42){
					for(int i=0; i<rbukrs.length; i++){
						if(i <= total.length-1){
							if(total[i] != null && "".equals(total[i].trim())){
								continue;
							}
							if("5003".equals(rbukrs[i])){
								toTalColumn78YJ += Double.parseDouble(total[i]);
							}else if("5004".equals(rbukrs[i])){
								toTalColumn78XADQ += Double.parseDouble(total[i]);
							}else if("5005".equals(rbukrs[i])){
								toTalColumn78JL += Double.parseDouble(total[i]);
							}else if("5006".equals(rbukrs[i])){
								toTalColumn78XP += Double.parseDouble(total[i]);
							}else if("5025".equals(rbukrs[i])){
								toTalColumn78JF += Double.parseDouble(total[i]);
							}else if("5142".equals(rbukrs[i])){
								toTalColumn78TKX += Double.parseDouble(total[i]);
							}else if("India".equals(rbukrs[i])){
								toTalColumn78YD += Double.parseDouble(total[i]);
							}else if("SouthAfrica".equals(rbukrs[i])){
								toTalColumn78NF += Double.parseDouble(total[i]);
							}
						}
						
					}
				}
				//无数据公司处理
				if(isExport){
					if(allCompCode.size() != rbukrsList.size()){
						for(int i=0; i<allCompCode.size(); i++){
							if(!rbukrsList.contains(allCompCode.get(i))){
								if(compCodeValue.contains(allCompCode.get(i))){
									map.put(allCompCode.get(i), decimalFormat.format(0));
								}else{
									map.put(allCompCode.get(i), "");
								}
							}
						}
					}
				}else{
					if(compCodeValue.size() != rbukrsList.size()){
						for(int i=0; i<allCompCode.size(); i++){
							if(!rbukrsList.contains(allCompCode.get(i))){
								map.put(allCompCode.get(i), decimalFormat.format(0));
							}
						}
					}
				}
				map.put("SUM", decimalFormat.format(sum));
				rbukrsList = new ArrayList<Object>();
			}else{
				if(Integer.parseInt(value.getItem_column()) != 12 && Integer.parseInt(value.getItem_column()) != 13
						&& Integer.parseInt(value.getItem_column()) != 75 && Integer.parseInt(value.getItem_column()) != 76
						&& Integer.parseInt(value.getItem_column()) != 79 && Integer.parseInt(value.getItem_column()) != 80){
					if(Integer.parseInt(value.getItem_column()) == pace2){
						for(int i=0; i<compCodeValue.size(); i++){
							if(value.getRbukrs() == null && value.getTotal() == null){
								map.put(compCodeValue.get(i), value.getLabel());
							}
						}
						if(pace2 == 11){
							pace2 += 3;
						}else{
							pace2 += 5;
						}
					}else{
						for(int i=0; i<compCodeValue.size(); i++){
							if(value.getRbukrs() == null && value.getTotal() == null){
								map.put(compCodeValue.get(i), decimalFormat.format(0));
							}
						}
					}
					//无数据公司处理
					
					if(isExport){
						//1,6,11,14,19,24,29,34,39,44,49,54,59,64,69,74
						if(Integer.parseInt(value.getItem_column()) == pace1){
							if(allCompCode.size() != compCodeValue.size()){
								for(int i=0; i<allCompCode.size(); i++){
									if(!compCodeValue.contains(allCompCode.get(i))){
										map.put(allCompCode.get(i), value.getLabel());
									}
								}
							}
							if(pace1 == 11){
								pace1 += 3;
							}else{
								pace1 += 5;
							}
						}else{
							if(allCompCode.size() != compCodeValue.size()){
								for(int i=0; i<allCompCode.size(); i++){
									if(!compCodeValue.contains(allCompCode.get(i))){
										map.put(allCompCode.get(i), "");
									}
								}
							}
						}
					}
					if(Integer.parseInt(value.getItem_column()) == pace3){
						if(value.getRbukrs() == null && value.getTotal() == null){
							map.put("SUM", value.getLabel());
						}
						if(pace3 == 11){
							pace3 += 3;
						}else{
							pace3 += 5;
						}
					}else{
						map.put("SUM", 0.00);
					}
				}else{
					if(Integer.parseInt(value.getItem_column()) == 12 || Integer.parseInt(value.getItem_column()) == 13){
						if(rbukrs != null){
							for(int i=0; i<rbukrs.length; i++){
								sumColumn77List.add("0");
							}
						}
					}
					if(Integer.parseInt(value.getItem_column()) == 12 || Integer.parseInt(value.getItem_column()) == 13){
						if(rbukrs != null){
							for(int i=0; i<rbukrs.length; i++){
								sumColumn78List.add("0");
							}
						}
					}
					for(int i=0; i<compCodeValue.size(); i++){
						map.put(compCodeValue.get(i), decimalFormat.format(0));
					}
					//无数据公司处理
					if(isExport){
						if(allCompCode.size() != compCodeValue.size()){
							for(int i=0; i<allCompCode.size(); i++){
								if(!compCodeValue.contains(allCompCode.get(i))){
									map.put(allCompCode.get(i), "");
								}
							}
						}
					}
					map.put("SUM", decimalFormat.format(0));
				}
			}
			sum = 0;
			resultMap.put(value.getItem_column(), map);
			resultList.add(resultMap);
			map = new HashMap<String, Object>();
			resultMap = new HashMap<String, Object>();
		}
		
		for(Object val:resultList){
			@SuppressWarnings("unchecked")
			Map<String, Object> mapValue = (Map<String, Object>) val;
			if(mapValue.get("77") != null){
				@SuppressWarnings("unchecked")
				Map<String, Object> map1 = (Map<String, Object>) mapValue.get("77");
				map1.put("5003", decimalFormat.format(toTalColumn77YJ));
				map1.put("5004", decimalFormat.format(toTalColumn77XADQ));
				map1.put("5005", decimalFormat.format(toTalColumn77JL));
				map1.put("5006", decimalFormat.format(toTalColumn77XP));
				map1.put("5025", decimalFormat.format(toTalColumn77JF));
				map1.put("5142", decimalFormat.format(toTalColumn77TKX));
				map1.put("India", decimalFormat.format(toTalColumn77YD));
				map1.put("SouthAfrica", decimalFormat.format(toTalColumn77NF));
				toTalColumn77 = toTalColumn77YJ+toTalColumn77XP+toTalColumn77JL+
						toTalColumn77XADQ+toTalColumn77JF+toTalColumn77TKX+toTalColumn77YD+toTalColumn77NF;
				map1.put("SUM", decimalFormat.format(toTalColumn77));
			}
			if(mapValue.get("78") != null){
				@SuppressWarnings("unchecked")
				Map<String, Object> map2 = (Map<String, Object>) mapValue.get("78");
				map2.put("5003", decimalFormat.format(toTalColumn78YJ));
				map2.put("5004", decimalFormat.format(toTalColumn78XADQ));
				map2.put("5005", decimalFormat.format(toTalColumn78JL));
				map2.put("5006", decimalFormat.format(toTalColumn78XP));
				map2.put("5025", decimalFormat.format(toTalColumn78JF));
				map2.put("5142", decimalFormat.format(toTalColumn78TKX));
				map2.put("India", decimalFormat.format(toTalColumn78YD));
				map2.put("SouthAfrica", decimalFormat.format(toTalColumn78NF));
				toTalColumn78 = toTalColumn78YJ+toTalColumn78XP+toTalColumn78JL+
						toTalColumn78XADQ+toTalColumn78JF+toTalColumn78TKX+toTalColumn78YD+toTalColumn78NF;
				map2.put("SUM", decimalFormat.format(toTalColumn78));
			}
		}
		log.info(resultList.toString());
		return resultList;
	}
	
	/**
	 * 导出数据
	 * @param listMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ExcelExportUtil05 export(List<Object> listMap){
		Map<String, Object> map1 = null;
		Map<String, Object> map2 = null;
		int num = 0;
		int step = 0;
		String item = "";
		String itemColumn = "";
		String yongJiComp = "";
		String xiAnYongjiComp = "";
		String xiAnJieLiComp = "";
		String xiuPeiComp = "";
		String xiAnYongDianJinFengComp = "";
		String tuoKeXunComp = "";
		String yinDuComp = "";
		String nanFeiComp = "";
		String sum = "";
        List<List<Object>> dataRowList = Lists.newArrayList();
        List<Object> list = new ArrayList<Object>();
        String regEx_special = "\\&[a-zA-Z]{1,10};";
        Pattern pattern = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
        
        for (Object value : listMap) {
        	num ++;
        	map1 = (Map<String, Object>) value;
        	map2 = (Map<String, Object>) map1.get(String.valueOf(num));
        	if(map2.get(allCompCode.get(step)) != null){
        		yongJiComp = String.valueOf(map2.get(allCompCode.get(step)));
        	}
        	if(map2.get(allCompCode.get(++step)) != null){
        		xiAnYongjiComp = String.valueOf(map2.get(allCompCode.get(step)));
        	}
        	if(map2.get(allCompCode.get(++step)) != null){
        		xiAnJieLiComp = String.valueOf(map2.get(allCompCode.get(step)));
        	}
        	if(map2.get(allCompCode.get(++step)) != null){
        		xiuPeiComp = String.valueOf(map2.get(allCompCode.get(step)));
        	}
        	if(map2.get(allCompCode.get(++step)) != null){
        		xiAnYongDianJinFengComp = String.valueOf(map2.get(allCompCode.get(step)));
        	}
        	if(map2.get(allCompCode.get(++step)) != null){
        		tuoKeXunComp = String.valueOf(map2.get(allCompCode.get(step)));
        	}
        	if(map2.get(allCompCode.get(++step)) != null){
        		yinDuComp = String.valueOf(map2.get(allCompCode.get(step)));
        	}
        	if(map2.get(allCompCode.get(++step)) != null){
        		nanFeiComp = String.valueOf(map2.get(allCompCode.get(step)));
        	}
        	if(map2.get("ITEM") != null){
        		item = String.valueOf(map2.get("ITEM"));
        	}
        	if(map2.get("ITEM_COLUMN") != null){
        		itemColumn = String.valueOf(map2.get("ITEM_COLUMN"));
        	}
        	if(map2.get("SUM") != null){
        		sum = String.valueOf(map2.get("SUM"));
        	}
        	step = 0;
        	Matcher matcher = pattern.matcher(item);
        	item = matcher.replaceAll(" "); // 过滤标签
        	list.add(item);
        	list.add(itemColumn);
        	list.add(yongJiComp);
        	list.add(xiAnYongjiComp);
        	list.add(xiAnJieLiComp);
        	list.add(xiuPeiComp);
        	list.add(xiAnYongDianJinFengComp);
        	list.add(tuoKeXunComp);
        	list.add(yinDuComp);
        	list.add(nanFeiComp);
        	list.add(sum);
        	dataRowList.add(list);
        	list = new ArrayList<Object>();
        	
        }
        
        List<String> secondTitle = new ArrayList<String>();
		if(compCodeValue.size() == allCompCode.size()){
			secondTitle.add("编制单位："+"中车永济电机有限公司合计");
		}else if(compCodeValue.size() == 1){
			if("India".equals(compCodeValue.get(0)) || "SouthAfrica".equals(compCodeValue.get(0))){
				secondTitle.add("编制单位："+getCompTxtmdNFAndYDByCode(compCodeValue.get(0)));
			}else{
				secondTitle.add("编制单位："+getCompTxtmdByCode(compCodeValue.get(0)));
			}
			
		}else{
			secondTitle.add("编制单位：");
		}
		for(int i=0; i<allCompCode.size()/2; i++){
			secondTitle.add("");
		}
		secondTitle.add(startTmpTime.substring(0, 4)+"年 "+startTmpTime.substring(4,6)+"月"+ " - "+endTmpTime.substring(0,4)+"年 "+endTmpTime.substring(4,6)+"月");
        ExcelExportUtil05 ee = new ExcelExportUtil05("应上交应弥补款项表", secondTitle, headerList);
        List<List<Object>> dataList = Lists.newArrayList();
        for (int j = 0; j < dataRowList.size(); j++) {
            dataList.add(dataRowList.get(j));
        }
        for (int i = 0; i < dataRowList.size(); i++) {
        	Row row = ee.addRow();
            for (int j = 0; j < dataList.get(i).size(); j++) {
                ee.addCell(row, j, dataList.get(i).get(j));
            }
        }
		
		return ee;
	}
	/**
	 * 返回公司描述
	 * @param compCode
	 * @return
	 */
	private String getCompTxtmdByCode(String compCode){
		Map<String,Object> map = iReportMapperFS05.getCompTxtmdByCode(compCode);
		String txtmd = String.valueOf(map.get("TXTMD"));
		return txtmd;
	}
	/**
	 * 返回公司描述
	 * @param compCode
	 * @return
	 */
	private String getCompTxtmdNFAndYDByCode(String compCode){
		Map<String,Object> map = iReportMapperFS05.getCompTxtmdNFAndYDByCode(compCode);
		String txtmd = String.valueOf(map.get("TXTMD"));
		return txtmd;
	}
	/**
	 * 查询当前公司是否存在
	 * @param compTxtmd
	 * @return
	 */
	private Map<String,String> getCompTxtmdByTxtmd(String compTxtmd){
		Map<String,String> map = iReportMapperFS05.getCompTxtmdByTxtmd(compTxtmd);
		return map;
	}
	/**
	 * 返回公司描述
	 * @param compTxtmd
	 * @return
	 */
	private Map<String,String> getCompTxtmdNFAndYDByTxtmd(String compTxtmd){
		Map<String,String> map = iReportMapperFS05.getCompTxtmdNFAndYDByTxtmd(compTxtmd);
		return map;
	}
	/**
	 * 数据导入
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ParseException 
	 */
	@Override
	public Object importData(String filePath, List<String>compCodeCheck) throws JsonProcessingException,
			IOException, DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException{
		// TODO Auto-generated method stub
        //导入数据
		int pace = 1;
		int step = 0;
		boolean flags = false;
		Matcher matcher = null;
		String fiscper = null; //区间
		String yearMonth = null; //年月
        Map<String, Object> listMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> errorMap = new HashMap<String, Object>();
		ExcelImportUtil ei = new ExcelImportUtil(filePath, 2);
        String time = ei.getCellValue(1, 5).toString(); //时间
        String[] yearMonths = time.split("-");
        if(yearMonths.length != 2){
            listMap.put("ERRORTIME", "日期填写错误!");
        }else{
            Pattern p = Pattern.compile("[^0-9]");
            for(int i=0; i<yearMonths.length; i++){
                matcher = p.matcher(yearMonths[i]);
                yearMonth = matcher.replaceAll("").trim();
                if (!FileUtils.isValidDate(yearMonth)) {
                    listMap.put("ERRORTIME", "日期填写错误!");
                }
            }
            if(!yearMonths[0].trim().equals(yearMonths[1].trim())){
                listMap.put("ERRORTIME", "不能跨月导入,请检查月份!");
            }
        }
        if(listMap.size() == 0){
        	fiscper = yearMonth.substring(0, 4)+"0"+yearMonth.substring(4, yearMonth.length());
        
        }
        List<String> listCompCode = new ArrayList<String>();
        List<String> listAllCompCode = new ArrayList<String>();
        List<String> listYDandND = new ArrayList<String>();
		listCompCode.add("5003"); //永济公司
		listCompCode.add("5004"); //西安永电
		listCompCode.add("5005"); //西安捷力
		listCompCode.add("5006"); //修配公司
		listCompCode.add("5025"); //西安永电金风
		listCompCode.add("5142"); //托克逊公司
		listYDandND.add("India"); //印度公司
		listYDandND.add("SouthAfrica"); //南非公司
		listAllCompCode.addAll(listCompCode);
		listAllCompCode.addAll(listYDandND);
		headerList = new ArrayList<String>();
		headerList.add("项目");
        headerList.add("行次");
		for(int i=0; i<listAllCompCode.size(); i++){
			if("India".equals(listAllCompCode.get(i)) || "SouthAfrica".equals(listAllCompCode.get(i))){
				headerList.add(getCompTxtmdNFAndYDByCode(listAllCompCode.get(i)));
			}else{
				headerList.add(getCompTxtmdByCode(listAllCompCode.get(i)));
			}
		}
		headerList.add("合计");
        String json = ei.checkHeadList(headerList, listMap);
        if (null != json) {
            return json;
        }
        
        List<InsertFieldTHM_FS05> list = ei.getDataList(InsertFieldTHM_FS05.class, "FS05");
        //校验数据是否准确
        
        List<String> valids = checkData(list);
        if(valids.size() > 0){
        	listMap.put("ERRORDATA", valids);
        }
        if(listMap.size() > 0){
        	errorMap.put("ERROR", listMap);
        	return mapper.writeValueAsString(errorMap);
        }
        
        List<InsertFieldTHM_FS05> dataList = new ArrayList<InsertFieldTHM_FS05>();
        InsertFieldTHM_FS05 insertValue = new InsertFieldTHM_FS05();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());
        List<String> listTotal = new ArrayList<String>();
        boolean flag5003 = true;
        boolean flag5004 = true;
        boolean flag5005 = true;
        boolean flag5006 = true;
        boolean flag5025 = true;
        boolean flag5142 = true;
        boolean flagIndia = true;
        boolean flagSouthAfrica = true;
        List<String> compList = new ArrayList<String>();
        for(InsertFieldTHM_FS05 value:list){
			if(Integer.parseInt(value.getLineNum()) == 77){
        		break;
        	}
        	if(Integer.parseInt(value.getLineNum()) == 12 || Integer.parseInt(value.getLineNum()) == 13
        			|| Integer.parseInt(value.getLineNum()) == 75 || Integer.parseInt(value.getLineNum()) == 76){
        		listTotal = new ArrayList<String>();
        		if(compCodeCheck.contains("5003")){
        			listTotal.add(value.getYongjiCompany().replaceAll(",", "").replace("，", ""));
        			compList.add("5003");
        		}else if(value.getYongjiCompany() != null && flag5003 && !"".equals(value.getYongjiCompany().trim())){
        			flag5003 = false;
        			valids.add("没有权限导入 中车永济电机有限公司");
        		}
        		if(compCodeCheck.contains("5004")){
        			listTotal.add(value.getXianyongdianCompany().replaceAll(",", "").replace("，", ""));
        			compList.add("5004");
        		}else if(value.getXianyongdianCompany() != null && flag5004 && !"".equals(value.getXianyongdianCompany().trim())){
        			flag5004 = false;
        			valids.add("没有权限导入 西安中车永电电气有限公司");
        		}
        		if(compCodeCheck.contains("5005")){
        			listTotal.add(value.getXianjieliCompany().replaceAll(",", "").replace("，", ""));
        			compList.add("5005");
        		}else if(value.getXianjieliCompany() !=null && flag5005 && !"".equals(value.getXianjieliCompany().trim())){
        			flag5005 = false;
        			valids.add("没有权限导入 西安中车永电捷力风能有限公司");
        		}
        		if(compCodeCheck.contains("5006")){
        			listTotal.add(value.getXiupeiCompany().replaceAll(",", "").replace("，", ""));
        			compList.add("5006");
        		}else if(value.getXiupeiCompany().trim() != null && flag5006 && !"".equals(value.getXiupeiCompany().trim())){
        			flag5006 = false;
        			valids.add("没有权限导入 永济中车电机电器修配有限公司");
        		}
        		if(compCodeCheck.contains("5025")){
        			listTotal.add(value.getXianjinfengCompany().replaceAll(",", "").replace("，", ""));
        			compList.add("5025");
        		}else if(value.getXianjinfengCompany() != null && flag5025 && !"".equals(value.getXianjinfengCompany().trim())){
        			flag5025 = false;
        			valids.add("没有权限导入 西安中车永电金风科技有限公司");
        		}
        		if(compCodeCheck.contains("5142")){
        			listTotal.add(value.getTuokexunCompany().replaceAll(",", "").replace("，", ""));
        			compList.add("5142");
        		}else if(value.getTuokexunCompany() != null && flag5142 && !"".equals(value.getTuokexunCompany().trim())){
        			flag5142 = false;
        			valids.add("没有权限导入 托克逊中车永电能源装备有限公司");
        		}
        		if(compCodeCheck.contains("India")){
        			listTotal.add(value.getYinduCompany().replaceAll(",", "").replace("，", ""));
        			compList.add("India");
        		}else if(value.getYinduCompany() != null && flagIndia && !"".equals(value.getYinduCompany().trim())){
        			flagIndia = false;
        			valids.add("没有权限导入 印度中车先锋电气有限公司");
        		}
        		if(compCodeCheck.contains("SouthAfrica")){
        			listTotal.add(value.getNanfeiCompany().replaceAll(",", "").replace("，", ""));
        			compList.add("SouthAfrica");
        		}else if(value.getNanfeiCompany() != null && flagSouthAfrica && !"".equals(value.getNanfeiCompany().trim())){
        			flagSouthAfrica = false;
        			valids.add("没有权限导入 南非项目（公司）");
        		}
//        		listTotal.add(value.getXianjieliCompany().replaceAll(",", "").replace("，", ""));
//        		listTotal.add(value.getXiupeiCompany().replaceAll(",", "").replace("，", ""));
//        		listTotal.add(value.getXianjinfengCompany().replaceAll(",", "").replace("，", ""));
//        		listTotal.add(value.getTuokexunCompany().replaceAll(",", "").replace("，", ""));
//        		listTotal.add(value.getYinduCompany().replaceAll(",", "").replace("，", ""));
//        		listTotal.add(value.getNanfeiCompany().replaceAll(",", "").replace("，", ""));
        		for(int i=0; i<listTotal.size(); i++){
        			insertValue.setRBUKRS(compList.get(i));
            		insertValue.setLineNum(value.getLineNum());
            		insertValue.setTOTAL(listTotal.get(i));
            		insertValue.setFISCPER(fiscper);
            		insertValue.setCREATE_TIME(date);
            		dataList.add(insertValue);
            		insertValue = new InsertFieldTHM_FS05();
        		}
        		listTotal = new ArrayList<String>();
        		if(Integer.parseInt(value.getLineNum()) == 13){
        			pace += 3;
        		}
        	}else{
        		//1,6,11,14,19,24,29,34,39,44,49,54,59,64,69,74
        		if(Integer.parseInt(value.getLineNum()) != pace){
        			if(compCodeCheck.contains("India")){
            			listTotal.add(value.getYinduCompany().replaceAll(",", "").replace("，", ""));
            		}else if(value.getYinduCompany() != null && !"".equals(value.getYinduCompany().trim())){
            			valids.add("没有权限导入 印度中车先锋电气有限公司");
            		}
            		if(compCodeCheck.contains("SouthAfrica")){
            			listTotal.add(value.getNanfeiCompany().replaceAll(",", "").replace("，", ""));
            		}else if(value.getNanfeiCompany() != null && !"".equals(value.getNanfeiCompany().trim())){
            			valids.add("没有权限导入 南非项目（公司）");
            		}
//        			listTotal.add(value.getYinduCompany().replaceAll(",", "").replace("，", ""));
//            		listTotal.add(value.getNanfeiCompany().replaceAll(",", "").replace("，", ""));
            		for(int i=0; i<listTotal.size(); i++){
            			insertValue.setRBUKRS(listYDandND.get(i));
                		insertValue.setLineNum(value.getLineNum());
                		insertValue.setTOTAL(listTotal.get(i));
                		insertValue.setFISCPER(fiscper);
                		insertValue.setCREATE_TIME(date);
                		dataList.add(insertValue);
                		insertValue = new InsertFieldTHM_FS05();
            		}
        		}
        		if(Integer.parseInt(value.getLineNum()) != pace){
        			step++;
        		}
				if(step == 4 || pace == 11){
					if(pace == 11){
						if(flags){
							pace += 3;
						}else{
							flags = true;
							continue;
						}
					}else{
						pace += 5;
					}
					step = 0;
				}
				
				listTotal = new ArrayList<String>();
        	}
        }
        if(valids.size() >0 ){
        	listMap.put("ERRORDATA", valids);
        	return mapper.writeValueAsString(listMap);
        }
        iReportMapperFS05.saveOrUpdateData(dataList,fiscper,"");
        listMap.put("SUCCESSES", "导入成功!");
        return mapper.writeValueAsString(listMap);
	}
	
	/**
	 * 数据校验
	 * @param list
	 * @return
	 * @throws ParseException 
	 */
	private List<String> checkData(List<InsertFieldTHM_FS05> list){
		int position = 2;
		int columnNum9 = 9;
		int columnNum10 = 10;
		int rowNum15 = 15;
		int rowNum16 = 16;
		int rowNum78 = 78;
		int rowNum79 = 79;
		int rowNum = 0;
		int columnNum15 = 3;
		int columnNum16 = 3;
		int columnNum78 = 3;
		int columnNum79 = 3;
		int pace = 1;
		int step = 0;
		int count = 2;
		int num = 4;
		boolean flag = true;
		Map<String, String> getCompTxtmdXAJL = null;
		Map<String, String> getCompTxtmdXAYDJF = null;
		List<String> valids = new ArrayList<String>();
		for(InsertFieldTHM_FS05 value:list){
			//检查 所有行次 是否为正，且为数据
			if(!FileUtils.isNumeric(value.getLineNum())){
				valids.add("第 "+num+" 行第 2 列数据格式错误!");
			}else if(value.getLineNum().contains(".")){
				valids.add("第 "+num+" 行第 2 列数据格式错误!");
			}else if(Integer.parseInt(value.getLineNum()) <= 0){
				valids.add("第 "+num+" 行第 2 列数据格式错误!");
			}
			num++;
		}
		for(InsertFieldTHM_FS05 value:list){
			if(flag){
				Map<String, String> getCompTxtmdYJ = getCompTxtmdByTxtmd(headerList.get(count)); //永济公司
				Map<String, String> getCompTxtmdXP = getCompTxtmdByTxtmd(headerList.get(++count)); //修配公司
				String[] Xianjieli = headerList.get(++count).split("、");
				for(int i=0; i<Xianjieli.length; i++){
					getCompTxtmdXAJL = getCompTxtmdByTxtmd(Xianjieli[i]); //西安捷力
					break;
				}
				Map<String, String> getCompTxtmdXAYD = getCompTxtmdByTxtmd(headerList.get(++count)); //西安永电
				String[] Xianjinfeng = headerList.get(++count).split("、");
				for(int i=0; i<Xianjinfeng.length; i++){
					getCompTxtmdXAYDJF = getCompTxtmdByTxtmd(Xianjinfeng[i]); //西安永电金风
					if(getCompTxtmdXAYDJF == null){
						break;
					}
				}
				Map<String, String> getCompTxtmdTKX = getCompTxtmdByTxtmd(headerList.get(++count)); //托克逊公司
				Map<String, String> getCompTxtmdYD = getCompTxtmdNFAndYDByTxtmd(headerList.get(++count)); //印度公司
				Map<String, String> getCompTxtmdNF = getCompTxtmdNFAndYDByTxtmd(headerList.get(++count)); //南非公司
				if(getCompTxtmdYJ ==null){
					valids.add("第3行第3列错误，没有该公司!");
				}else if(getCompTxtmdXP == null){
					valids.add("第3行第4列错误，没有该公司!");
				}else if(getCompTxtmdXAJL == null){
					valids.add("第3行第5列错误，没有该公司!");
				}else if(getCompTxtmdXAYD == null){
					valids.add("第3行第6列错误，没有该公司!");
				}else if(getCompTxtmdXAYDJF == null){
					valids.add("第3行第7列错误，没有该公司!");
				}else if(getCompTxtmdTKX == null){
					valids.add("第3行第8列错误，没有该公司!");
				}else if(getCompTxtmdYD == null){
					valids.add("第3行第9列错误，没有该公司!");
				}else if(getCompTxtmdNF == null){
					valids.add("第3行第10列错误，没有该公司!");
				}
				flag = false;
				continue;
			}

			if(Integer.parseInt(value.getLineNum()) == 12){
				//永济公司	修配公司	西安捷力	西安永电	西安永电金风	托克逊公司	印度公司	南非公司
				if(!FileUtils.isNumeric(value.getYongjiCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum15+"行第"+columnNum15+"列数据格式错误!");
				}
				columnNum15++;
				if(!FileUtils.isNumeric(value.getXiupeiCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum15+"行第"+columnNum15+"列数据格式错误!");
				}
				columnNum15++;
				if(!FileUtils.isNumeric(value.getXianjieliCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum15+"行第"+columnNum15+"列数据格式错误!");
				}
				columnNum15++;
				if(!FileUtils.isNumeric(value.getXianyongdianCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum15+"行第"+columnNum15+"列数据格式错误!");
				} 
				columnNum15++;
				if(!FileUtils.isNumeric(value.getXianjinfengCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum15+"行第"+columnNum15+"列数据格式错误!");
				}
				columnNum15++;
				if(!FileUtils.isNumeric(value.getTuokexunCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum15+"行第"+columnNum15+"列数据格式错误!");
				}
				columnNum15++;
				if(!FileUtils.isNumeric(value.getYinduCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum15+"行第"+columnNum15+"列数据格式错误!");
				}
				if(!FileUtils.isNumeric(value.getNanfeiCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum15+"行第"+columnNum15+"列数据格式错误!");
				}
				columnNum15++;
			}else if(Integer.parseInt(value.getLineNum()) == 13){
				if(!FileUtils.isNumeric(value.getYongjiCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum16+"行第"+columnNum16+"列数据格式错误!");
				}
				columnNum16++;
				if(!FileUtils.isNumeric(value.getXiupeiCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum16+"行第"+columnNum16+"列数据格式错误!");
				}
				columnNum16++;
				if(!FileUtils.isNumeric(value.getXianjieliCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum16+"行第"+columnNum16+"列数据格式错误!");
				}
				columnNum16++;
				if(!FileUtils.isNumeric(value.getXianyongdianCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum16+"行第"+columnNum16+"列数据格式错误!");
				}
				columnNum16++;
				if(!FileUtils.isNumeric(value.getXianjinfengCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum16+"行第"+columnNum16+"列数据格式错误!");
				}
				columnNum16++;
				if(!FileUtils.isNumeric(value.getTuokexunCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum16+"行第"+columnNum16+"列数据格式错误!");
				}
				columnNum16++;
				if(!FileUtils.isNumeric(value.getYinduCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum16+"行第"+columnNum16+"列数据格式错误!");
				}
				columnNum16++;
				if(!FileUtils.isNumeric(value.getNanfeiCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum16+"行第"+columnNum16+"列数据格式错误!");
				}
				columnNum16++;
			}else if(Integer.parseInt(value.getLineNum()) == 75){
				if(!FileUtils.isNumeric(value.getYongjiCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum78+"行第"+columnNum78+"列数据格式错误!");
				}
				columnNum78++;
				if(!FileUtils.isNumeric(value.getXiupeiCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum78+"行第"+columnNum78+"列数据格式错误!");
				}
				columnNum78++;
				if(!FileUtils.isNumeric(value.getXianjieliCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum78+"行第"+columnNum78+"列数据格式错误!");
				}
				columnNum78++;
				if(!FileUtils.isNumeric(value.getXianyongdianCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum78+"行第"+columnNum78+"列数据格式错误!");
				}
				columnNum78++;
				if(!FileUtils.isNumeric(value.getXianjinfengCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum78+"行第"+columnNum78+"列数据格式错误!");
				}
				columnNum78++;
				if(!FileUtils.isNumeric(value.getTuokexunCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum78+"行第"+columnNum78+"列数据格式错误!");
				}
				columnNum78++;
				if(!FileUtils.isNumeric(value.getYinduCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum78+"行第"+columnNum78+"列数据格式错误!");
				}
				columnNum78++;
				if(!FileUtils.isNumeric(value.getNanfeiCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum78+"行第"+columnNum78+"列数据格式错误!");
				}
				columnNum78++;
			}else if(Integer.parseInt(value.getLineNum()) == 76){
				if(!FileUtils.isNumeric(value.getYongjiCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum79+"行第"+columnNum79+"列数据格式错误!");
				}
				columnNum79++;
				if(!FileUtils.isNumeric(value.getXiupeiCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum79+"行第"+columnNum79+"列数据格式错误!");
				}
				columnNum79++;
				if(!FileUtils.isNumeric(value.getXianjieliCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum79+"行第"+columnNum79+"列数据格式错误!");
				}
				columnNum79++;
				if(!FileUtils.isNumeric(value.getXianyongdianCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum79+"行第"+columnNum79+"列数据格式错误!");
				}
				columnNum79++;
				if(!FileUtils.isNumeric(value.getXianjinfengCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum79+"行第"+columnNum79+"列数据格式错误!");
				}
				columnNum79++;
				if(!FileUtils.isNumeric(value.getTuokexunCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum79+"行第"+columnNum79+"列数据格式错误!");
				}
				columnNum79++;
				if(!FileUtils.isNumeric(value.getYinduCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum79+"行第"+columnNum79+"列数据格式错误!");
				}
				columnNum79++;
				if(!FileUtils.isNumeric(value.getNanfeiCompany().replaceAll(",", "").replace("，", ""))){
					valids.add("第"+rowNum79+"行第"+columnNum79+"列数据格式错误!");
				}
				columnNum79++;
			}else{
				//1,6,11,14,19,24,29,34,39,44,49,54,59,64,69,74
				if(Integer.parseInt(value.getLineNum()) != pace){
					if(Integer.parseInt(value.getLineNum()) == position){
						if(!FileUtils.isNumeric(value.getYinduCompany().replaceAll(",", "").replace("，", ""))){
							rowNum = position+3;
							valids.add("第"+rowNum+"行第"+columnNum9+"列数据格式错误!");
						}else if(!FileUtils.isNumeric(value.getNanfeiCompany().replaceAll(",", "").replace("，", ""))){
							valids.add("第"+rowNum+"行第"+columnNum10+"列数据格式错误!");
						}
						position++;
					}
					step++;
					if(step == 4){
						if(pace == 11){
							pace += 3;
						}else{
							pace += 5;
						}
						step = 1;
					}
					
				}
			}
		}
		
		return valids;
	}
	
}
