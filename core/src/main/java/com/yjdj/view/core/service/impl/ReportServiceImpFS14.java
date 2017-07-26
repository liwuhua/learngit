package com.yjdj.view.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.mapper.IReportMapperFS13;
import com.yjdj.view.core.mapper.IReportMapperFS14;
import com.yjdj.view.core.service.IReportServiceFS13;
import com.yjdj.view.core.service.IReportServiceFS14;
import com.yjdj.view.core.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by liwuhua on 16/11/7.
 */

@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS14 implements IReportServiceFS14 {

	@Autowired
	private IReportMapperFS14 iReportMapperFS14;

	@Override
	public Object getSttyp(String sttyp) throws JsonProcessingException, DataAccessException {
		ObjectMapper mapper = new ObjectMapper();
		List<String> mapList = iReportMapperFS14.getSttyp(sttyp);
		return mapper.writeValueAsString(mapList);
	}

	@Override
	public Object getSobkz(String sobkz) throws JsonProcessingException, DataAccessException {
		ObjectMapper mapper = new ObjectMapper();
		List<String> mapList = iReportMapperFS14.getSobkz(sobkz);
		return mapper.writeValueAsString(mapList);
	}

	@Override
	public Object getLgpbe(String lgpbe) throws JsonProcessingException, DataAccessException {
		ObjectMapper mapper = new ObjectMapper();
		List<String> mapList = iReportMapperFS14.getLgpbe(lgpbe);
		return mapper.writeValueAsString(mapList);
	}

	@Override
	public Object getData(QueryBean queryBean) throws JsonProcessingException, DataAccessException, ParseException {
		ObjectMapper mapper = new ObjectMapper();

		//判断日期
		boolean b = isMonth(queryBean.getDateTime());
		String blog = "0";
		if(b){
			blog = "1";
		}

		//根据天数获取无动态日期
		String wdtDateTime = null;
		if(null != queryBean.getDayNum()){
			wdtDateTime = DateUtils.getDayBeforeStr(queryBean.getDateTime(),-queryBean.getDayNum());
		}

		List<Map<String,Object>> mapList = null;
		Map<String,Object> map = null;
		if("a".equals(queryBean.getYlzd())){
			mapList = iReportMapperFS14.getDataFir(queryBean.getPlantValue(),queryBean.getMatnrValue(),queryBean.getMatnrIntervalMap(),queryBean.getLgortValue(),queryBean.getLgpbeValue(),
					queryBean.getSobkzValue(),queryBean.getSttypValue(),queryBean.getMatlGroupValue(),queryBean.getValClassValue(),queryBean.getDateTime(),
					wdtDateTime,blog,queryBean.getStartitem(),queryBean.getPageitem());

			map = iReportMapperFS14.getSumDataFir(queryBean.getPlantValue(),queryBean.getMatnrValue(),queryBean.getMatnrIntervalMap(),queryBean.getLgortValue(),queryBean.getLgpbeValue(),
					queryBean.getSobkzValue(),queryBean.getSttypValue(),queryBean.getMatlGroupValue(),queryBean.getValClassValue(),queryBean.getDateTime(),
					wdtDateTime,blog);
		}else if("b".equals(queryBean.getYlzd())){
			mapList = iReportMapperFS14.getDataSec(queryBean.getPlantValue(),queryBean.getMatnrValue(),queryBean.getMatnrIntervalMap(),queryBean.getLgortValue(),queryBean.getLgpbeValue(),
					queryBean.getSobkzValue(),queryBean.getSttypValue(),queryBean.getMatlGroupValue(),queryBean.getValClassValue(),queryBean.getDateTime(),
					wdtDateTime,blog,queryBean.getStartitem(),queryBean.getPageitem());

			map = iReportMapperFS14.getSumDataSec(queryBean.getPlantValue(),queryBean.getMatnrValue(),queryBean.getMatnrIntervalMap(),queryBean.getLgortValue(),queryBean.getLgpbeValue(),
					queryBean.getSobkzValue(),queryBean.getSttypValue(),queryBean.getMatlGroupValue(),queryBean.getValClassValue(),queryBean.getDateTime(),
					wdtDateTime,blog);
		}

		if(null != map){
			map.put("data",mapList);
		}

		return mapper.writeValueAsString(map);
	}

	/**
	 * 判断月份是否为当月
	 * @param dateTime
	 * @return
     */
	private boolean isMonth(String dateTime){
		boolean b = false;

		String month = dateTime.substring(0,6);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String now = sdf.format(new Date());
		if(month.equals(now)){
			b = true;
		}else {
			b = false;
		}

		return b;
	}
}
