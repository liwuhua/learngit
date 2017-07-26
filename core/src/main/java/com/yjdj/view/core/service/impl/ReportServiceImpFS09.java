package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
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
import com.yjdj.view.core.entity.mybeans.THM_FS09;
import com.yjdj.view.core.mapper.GenericMapper;
import com.yjdj.view.core.mapper.IReportMapperFS09;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS09;
import com.yjdj.view.core.util.DateUtils;
import com.yjdj.view.core.util.ExcelExportUtil09;
import com.yjdj.view.core.util.ExcelImportUtil;
import com.yjdj.view.core.util.FileUtils;

/**
 * Created by chengxuan on 2016/11/8.
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS09 implements IReportServiceFS09 {
	@Autowired
	private IReportMapperFS09 iReportMapperFS09;
	@Autowired
	private GenericMapper genericMapper;
	@Autowired
	private GenericService genericService;

	private boolean isExport = false;
	private List<List<Object>> resultDataRowList = null;
	// private Map<String,Object> map;

	@Override
	public Object getListFs09(QueryBean queryBean, List<String> compCodeSelect)
			throws IOException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		// 获取所有的公司代码
		// List<MdmTcompCode> compCodeSelect =
		// genericMapper.getAllTcompCode("THM_FS09_01",null,null);
		String json = null;

		// 判断公司代码
		boolean b = genericService.isBool(queryBean.getCompCodeValue(),
				compCodeSelect, queryBean);

		if (StringUtils.isNotBlank(queryBean.getDateYearMonthStart())
				&& StringUtils.isNotBlank(queryBean.getDateYearMonthEnd())) {
			// Map<String,Object> map = new LinkedHashMap<String,Object>();
			String start1 = DateUtils.formatStringDate(queryBean
					.getDateYearMonthStart());
			String end1 = DateUtils.formatStringDate(queryBean
					.getDateYearMonthEnd());

			String start2 = DateUtils.formatStringDate(DateUtils
					.getLastYM(queryBean.getDateYearMonthStart()));
			String end2 = DateUtils.formatStringDate(DateUtils
					.getLastYM(queryBean.getDateYearMonthEnd()));

			// Map<String,Object> map1 =
			// getMap(queryBean.getCompCodeValue(),start1,end1,b);
			// Map<String,Object> map2 =
			// getMap(queryBean.getCompCodeValue(),start2,end2,b);
			String compCodeValue =  null;
			if(queryBean.getCompCodeValue() != null){
				compCodeValue = queryBean.getCompCodeValue().toString()
						.replace("[", "").replace("]", "").replace(" ", "");
			}
			Map<String, Object> map1 = iReportMapperFS09.test(compCodeValue,
					start1, end1, b, "year");
			Map<String, Object> map2 = iReportMapperFS09.test(compCodeValue,
					start2, end2, b, "lastYear");

			Map<String, Object> map = overMap(map1, map2);

			// 放入前端进行处理
			String bzdw = "BZDW";
			if (b) {
				map.put(bzdw, "中车永济电机有限公司合计");
			} else {
				if (queryBean.getCompCodeValue().size() > 1) {
					map.put(bzdw, "");
				} else if (queryBean.getCompCodeValue().size() == 1) {
					String txtmd = genericMapper.getTcompCode(
							queryBean.getCompCodeValue().get(0)).getTxtmd();
					map.put(bzdw, txtmd);
				}
			}
			// map.put("TY",map1);
			// map.put("LY",map2);
			isExport = queryBean.isExport();
			if (!isExport) {
				json = mapper.writeValueAsString(map);
			} else {
				// 导出数据
				List<List<Object>> dataList = Lists.newArrayList();
				List<Object> dataRowList = new ArrayList<Object>();
				Map<String, String> map3 = iReportMapperFS09.getTxt();

				for (int i = 1; i < 36; i++) {
					if (map.get(String.valueOf(i)) != null) {
						dataRowList.add((map3.get(String.valueOf(i)))
								.replaceAll("&nbsp;", " "));
						dataRowList.add(String.valueOf(i));
						List<Object> temp = (List<Object>) map.get(String.valueOf(i));
						dataRowList.addAll(temp);
						dataList.add(dataRowList);
						dataRowList = new ArrayList<Object>();
					} else {
						dataRowList.add((map3.get(String.valueOf(i)))
								.replaceAll("&nbsp;", " "));
						dataRowList.add(String.valueOf(i));
						dataRowList.add(" ");
						dataRowList.add(" ");
						dataList.add(dataRowList);
						dataRowList = new ArrayList<Object>();
					}
				}

				// while(it.hasNext()){
				// String key = it.next().toString();
				// if(key!=bzdw){
				// dataRowList.add((Object)key);
				// List<Object> temp = (List<Object>)map.get(key);
				// System.out.println(key);
				// System.out.println(temp);
				// dataRowList.addAll(temp);
				// dataList.add(dataRowList);
				// dataRowList = new ArrayList<Object>();
				// }
				// }

				List<String> secondTitle = getSecondTitle(map, start1, end1);
				List<String> headerList = new ArrayList<>();
				headerList = getHeaderList(headerList);
				ExcelExportUtil09 ee = new ExcelExportUtil09("制造费用明细表",
						secondTitle, headerList);

				for (int i = 0; i < dataList.size(); i++) {
					Row row = ee.addRow();
					for (int j = 0; j < dataList.get(i).size(); j++) {
						ee.addCell(row, j, dataList.get(i).get(j));
					}
				}
				return ee;
			}
		}

		return json;
	}

	private Map<String, Object> overMap(Map<String, Object> map1,
			Map<String, Object> map2) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : map1.entrySet()) {
			List<Double> list = new ArrayList<Double>();
			
			list.add(Double.valueOf(entry.getValue().toString()));
			if((map2.get(entry.getKey()))!=null){				
				list.add(Double.valueOf(map2.get(entry.getKey()).toString()));
			}else{
				list.add(0.00);	
			}
			
			map.put(entry.getKey(), list);
		}
		return map;
	}

	// private Map<String,Object> getMap(List<String> compCodeValue,String
	// start,String end,boolean b){
	// Map<String,Object> map = null;
	// map = iReportMapperFS09.getDmbtr(compCodeValue,start,end);
	// if(b){
	// //获取中间值
	// Double temp = iReportMapperFS09.getTemp(start,end);
	// replaceMap("18",temp,map);
	// replaceMap("20",-temp,map);
	// }
	// Double zzgxc =
	// sumMap(map,"3","4","5","6","7","8","9","10","11","12","14","15");
	// map.put("2",zzgxc);
	//
	// Double zzzfy =
	// sumMap(map,"2","18","20","21","22","25","26","27","28","29","30","31","32","33","35");
	// map.put("1",zzzfy);
	//
	// return map;
	// }
	//
	// private Double sumMap(Map<String,Object> map,String... keys){
	// Double sum = 0.0;
	// for(String key : keys){
	// key = key.toUpperCase();
	// if(map.containsKey(key)){
	// sum += (Double) map.get(key);
	// }
	// }
	// return sum;
	// }
	//
	// private void replaceMap(String key,Double temp,Map<String,Object> map){
	// key = key.toUpperCase();
	// map.replace(key,map.get(key),(Double)map.get(key)+temp);
	// }
	/**
	 * list对比
	 * 
	 * @param compCodeValue
	 *            前端传入公司代码
	 * @param compCodeSelect
	 *            全部公司代码
	 * @return
	 */
	// private boolean isBool(List<String> compCodeValue,List<String>
	// compCodeSelect){
	// boolean b = false;
	// if(CollectionUtils.isEmpty(compCodeValue)){ //公司代码为空
	// b = true;
	// }else{
	// Set<String> valueSet = new HashSet<String>(compCodeValue);
	// Set<String> selectSet = new HashSet<String>(compCodeSelect);
	// selectSet.removeAll(valueSet);
	// if(CollectionUtils.isEmpty(selectSet)){//用户全选公司代码
	// b = true;
	// }else{
	// b = false;
	// }
	// }
	// return b;
	// }
	// private boolean isBool(List<String> compCodeValue,List<MdmTcompCode>
	// compCodeSelect,QueryBean queryBean){
	// boolean b = false;
	// if(CollectionUtils.isEmpty(compCodeValue)){
	// b = true;
	// }else if(compCodeValue.size() == compCodeSelect.size()){
	// b = true;
	// }
	// if(b){
	// List<String> list = new ArrayList<String>();
	// for(MdmTcompCode compCode : compCodeSelect){
	// list.add(compCode.getComp_code());
	// }
	// queryBean.setCompCodeValue(list);
	// }
	// return b;
	// }
	/**
	 * 返回导出表的secondTitle
	 * 
	 * @param
	 * @param
	 * @return
	 */
	private List<String> getSecondTitle(Map<String, Object> bzdwmap,
			String start, String end) {
		List<String> secondTitle = new ArrayList<String>();
		String str1 = "编制单位：" + (String) bzdwmap.get("BZDW");
		String str2 = start.substring(0, 4) + start.substring(5, 7) + "—"
				+ end.substring(0, 4) + end.substring(5, 7);
		secondTitle.add(str1);
		secondTitle.add(" ");
		secondTitle.add(" ");
		secondTitle.add(str2);
		return secondTitle;
	}

	/**
	 * 返回导出表的headerList
	 * 
	 * @return
	 */
	private List<String> getHeaderList(List<String> headerList) {
		headerList.add("栏目");
		headerList.add("行次");
		headerList.add("本年数");
		headerList.add("上年数");
		return headerList;
	}

	/*
	 * 一次导入一个单位 一个月 起始月和结束月必须一致 灰色部分才能允许导入数据
	 */
	@Override
	public Object importData(String filePath, List<String> compCodeList)
			throws JsonProcessingException, IOException, DataAccessException,
			InvalidFormatException, InstantiationException,
			IllegalAccessException {

		String json = null;
		String startYearMonth = null;
		String endYearMonth = null;
		Matcher matcher1 = null;
		Matcher matcher2 = null;
		Map<String, Object> listMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		List<String> headerList = Lists.newArrayList();
		headerList.add("栏目");
		headerList.add("行次");
		headerList.add("本年数");
		headerList.add("上年数");
		// 导入数据
		ExcelImportUtil ei = new ExcelImportUtil(filePath, 2);
		json = ei.checkHeadList(headerList, listMap);
		if (null != json) {
			return json;
		}
		String company = ei.getCellValue(1, 0).toString();
		String time = (String) ei.getCellValue(1, 3);
		String regex = time.substring(6, time.length() - 6); // 年月分割符

		String[] timeInterval = ei.getCellValue(1, 3).toString().split(regex);
		String startTime = timeInterval[0];
		String endTime = timeInterval[1];

		Pattern p = Pattern.compile("[^0-9]");
		matcher1 = p.matcher(startTime);
		matcher2 = p.matcher(endTime);
		if (company.indexOf(":") > 0) {
			company = company.substring(company.indexOf(":") + 1,
					company.length());
		} else if (company.indexOf("：") > 0) {
			company = company.substring(company.indexOf("：") + 1,
					company.length());
		}
		if (company.trim().length() == 0) {
			listMap.put("ERRORCOMPANEY", "未填写编制单位!");
		}
		// 去域中 查询有没有这个公司 compCodeList 权限公司代码
		List<Map<String, String>> getCompList = iReportMapperFS09
				.selectComCodeBytxtmd(company.trim());
		if (getCompList.size() <= 0) {
			listMap.put("ERRORCOMPANEY", "编制单位填写错误，没有该编制单位!");
		}

		// 权限代码不为空 遍历权限内的公司代码 后 和文件中的公司代码比较 查看是否在权限内
		if (compCodeList.size() == 0 || compCodeList == null) {
			listMap.put("ERRORCOMPANEY", "查询有误,没有该公司的查询权限");
		} else {
			if (getCompList.size()>0 && !compCodeList.contains(getCompList.get(0).get("COMP_CODE"))) {
				// 权限公司代码不包含 文件中的公司代码
				listMap.put("ERRORCOMPANEY", "模板错误,编制单位不在权限范围内");
			}
		}
		
		if(getCompList.size() != 0){
			company = getCompList.get(0).get("COMP_CODE");
		}
		startYearMonth = matcher1.replaceAll("").trim();
		endYearMonth = matcher2.replaceAll("").trim();
		if ((!FileUtils.isValidDate(startYearMonth))
				|| !FileUtils.isValidDate(endYearMonth)) {
			listMap.put("ERRORTIME", "时间填写错误!");
		} else if (Integer.parseInt(startYearMonth) != Integer
				.parseInt(endYearMonth)) {
			listMap.put("ERRORTIME", "起始结束时间必须一致!");
		}
		String thisCell18 = (String.valueOf(ei.getCellValue(20, 2))).trim().replaceAll(",", "").replace("，", ""); // 获取指定行的值
																		// 必须是数值型
																		// 可为空
																		// 18
		String lastCell18 = (String.valueOf(ei.getCellValue(20, 3))).trim().replaceAll(",", "").replace("，", ""); // 获取指定行的值
		String thisCell20 = (String.valueOf(ei.getCellValue(22, 2))).trim().replaceAll(",", "").replace("，", ""); // 获取指定行的值
																		// 20
		String lastCell20 = (String.valueOf(ei.getCellValue(22, 3))).trim().replaceAll(",", "").replace("，", ""); // 获取指定行的值
		if(!FileUtils.isNumeric(thisCell18.replaceAll(",", "").replace("，", ""))){
			listMap.put("ERRORTIME", "第18行次的本年数不是数值型!");
		}else if(!FileUtils.isNumeric(lastCell18.replaceAll(",", "").replace("，", ""))){
			listMap.put("ERRORTIME", "第18行次的上年数不是数值型!");
		}else if(!FileUtils.isNumeric(thisCell20.replaceAll(",", "").replace("，", ""))){
			listMap.put("ERRORTIME", "第20行次的本年数不是数值型!");
		}else if(!FileUtils.isNumeric(lastCell20.replaceAll(",", "").replace("，", ""))){
			listMap.put("ERRORTIME", "第20行次的上年数不是数值型!");
		}
		
		if(listMap.size() > 0){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ERROR", listMap);
			json = mapper.writeValueAsString(map);
			return json;
		}
		
		THM_FS09 thm_FS1 = new THM_FS09();
		thm_FS1.setThisYearDmbtr(thisCell18);
		thm_FS1.setLastYearDmbtr(lastCell18);
		thm_FS1.setItemNo("18");

		THM_FS09 thm_FS2 = new THM_FS09();
		thm_FS2.setThisYearDmbtr(thisCell20);
		thm_FS2.setLastYearDmbtr(lastCell20);
		thm_FS2.setItemNo("20");

		List<THM_FS09> tmpDataList = new ArrayList<>();
		tmpDataList.add(thm_FS1);
		tmpDataList.add(thm_FS2);

		iReportMapperFS09.saveOrUpdateData(startYearMonth, endYearMonth,
				company, tmpDataList);
		listMap.put("SUCCESSES", "导入成功!");
		json = mapper.writeValueAsString(listMap);
		return json;

	}

}
