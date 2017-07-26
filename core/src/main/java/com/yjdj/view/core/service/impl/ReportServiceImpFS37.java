package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.entity.mybeans.THM_FS37;
import com.yjdj.view.core.mapper.IReportMapperFS37;
import com.yjdj.view.core.service.IReportServiceFS37;

/**
 * Created by zhangwenguo on 2017.03.28
 * 
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS37 implements IReportServiceFS37 {

	private final Logger log = Logger.getLogger(ReportServiceImpFS37.class);
	@Autowired
	private IReportMapperFS37 iReportMapperFS37;
	@Override
	public Object getReportJson(String matnr, String werks) throws JsonProcessingException, IOException,
			DataAccessException {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
		
		iReportMapperFS37.getData(matnr, werks, df.format(new Date())); //请求数据
		List<THM_FS37> resultData = iReportMapperFS37.selectData(); //查询数据
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("DATA", resultData);
		String json = mapper.writeValueAsString(map);
		log.info("+++++++++"+json);
		
		return json;
	}


}
