package com.yjdj.view.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.mapper.IReportMapperFS13;
import com.yjdj.view.core.service.IReportServiceFS13;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by liwuhua on 16/11/7.
 */

@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS13 implements IReportServiceFS13 {

	@Autowired
	private IReportMapperFS13 iReportMapperFS13;

	@Override
	public Object getLgpbe(String lgpbe) throws JsonProcessingException, DataAccessException {
		ObjectMapper mapper = new ObjectMapper();
		List<String> mapList = iReportMapperFS13.getLgpbe(lgpbe);
		return mapper.writeValueAsString(mapList);
	}

	@Override
	public Object getData(QueryBean queryBean) throws JsonProcessingException, DataAccessException {
		ObjectMapper mapper = new ObjectMapper();

		//判断日期
		boolean b = isMonth(queryBean.getDateTime());
		String blog = "0";
		if(b){
			blog = "1";
		}

		List<Map<String,Object>> mapList = iReportMapperFS13.getData(queryBean.getPlantValue(),queryBean.getMatnrValue(),queryBean.getMatnrIntervalMap(),
				queryBean.getLgortValue(),queryBean.getLgpbeValue(),queryBean.getVendorValue(),queryBean.getVendorTypeValue(),queryBean.getDateTime(),
				queryBean.getStartTime(),queryBean.getEndTime(),blog,queryBean.getStartitem(),queryBean.getPageitem());

		Map<String,Object> map = iReportMapperFS13.getSumData(queryBean.getPlantValue(),queryBean.getMatnrValue(),queryBean.getMatnrIntervalMap(),
				queryBean.getLgortValue(),queryBean.getLgpbeValue(),queryBean.getVendorValue(),queryBean.getVendorTypeValue(),queryBean.getDateTime(),
				queryBean.getStartTime(),queryBean.getEndTime(),blog);
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
