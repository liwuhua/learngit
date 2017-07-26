package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.util.ArrayList;
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
import com.yjdj.view.core.entity.mybeans.THM_KIP12;
import com.yjdj.view.core.mapper.IReportMapperKPI12;
import com.yjdj.view.core.service.IReportServiceKPI12;

/**
 * KPI-生产管理目标
 * @author 
 * create by 20161226
 * 
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpKPI12 implements IReportServiceKPI12{

	private final Logger log = Logger.getLogger(ReportServiceImpKPI12.class);
	
	@Autowired
	private IReportMapperKPI12 iReportMapperKPI12;
	/**
	 * 产品订单正点交付率,产品完成总数,产品累计未完成数据
	 */
	@Override
	public Object getProOTDeliverRateAndProCompCount(String yearMonth)
			throws JsonProcessingException, IOException, DataAccessException {
		// TODO Auto-generated method stub
		Map<String,Object> resultMapRate = new HashMap<String, Object>();
		List<Object> resultRate = new ArrayList<Object>();
		//产品订单正点交付率
		List<THM_KIP12> listRate = iReportMapperKPI12.selectProOTDeliverRate(yearMonth);
		for(THM_KIP12 value:listRate){
			//on_time_rate
			resultMapRate.put("on_time_rate", value.getOn_time_rate());
			resultRate.add(resultMapRate);
			resultMapRate = new HashMap<String, Object>();
		}
		
		
		Map<String,Object> resultMapCompCount = new HashMap<String, Object>();
		List<Object> resultCompCount = new ArrayList<Object>();
		//产品完成总数
		List<THM_KIP12> listCompCount = iReportMapperKPI12.selectProCompCount(yearMonth);
		for(THM_KIP12 value:listCompCount){
			//tiqian_complete_sum,dangqi_complete_sum,yanqi_complete_sum,product_complete_sum
			resultMapCompCount.put("tiqian_complete_sum", value.getTiqian_complete_sum());
			resultMapCompCount.put("dangqi_complete_sum", value.getDangqi_complete_sum());
			resultMapCompCount.put("yanqi_complete_sum", value.getYanqi_complete_sum());
			resultMapCompCount.put("product_complete_sum", value.getProduct_complete_sum());
			
			resultCompCount.add(resultMapCompCount);
			resultMapCompCount = new HashMap<String, Object>();
		}
		
		
		Map<String,Object> resultMapUnCompCount = new HashMap<String, Object>();
		List<Object> resultUnCompCount = new ArrayList<Object>();
		//产品累计未完成数据
		List<THM_KIP12> listUnCompCount = iReportMapperKPI12.selectProUnCompSum(yearMonth);
		for(THM_KIP12 value:listUnCompCount){
			//qianqi_uncomplete_sum,dangqi_uncomplete_sum,product_uncomplete_sum
			resultMapUnCompCount.put("qianqi_uncomplete_sum", value.getQianqi_uncomplete_sum());
			resultMapUnCompCount.put("dangqi_uncomplete_sum", value.getDangqi_uncomplete_sum());
			resultMapUnCompCount.put("product_uncomplete_sum", value.getProduct_uncomplete_sum());
			
			resultUnCompCount.add(resultMapUnCompCount);
			resultMapUnCompCount = new HashMap<String, Object>();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> mapTmp = new HashMap<String, Object>();
		mapTmp.put("COMPCOUNT", resultCompCount);
		mapTmp.put("UNCOMPCOUNT", resultUnCompCount);
		mapTmp.put("ONTIMERATE", resultRate);
		map.put("DATA", mapTmp);
		String json = mapper.writeValueAsString(map);
		log.info("+++++++++"+json);
		
		return json;
	}

	/**
	 * 产品正点交付钻取
	 */
	@Override
	public Object getProOTDeliverGoDown(String yearMonth)
			throws JsonProcessingException, IOException, DataAccessException {
		// TODO Auto-generated method stub
		Map<String,Object> resultMapDeliver = new HashMap<String, Object>();
		List<Object> resultDeliver = new ArrayList<Object>();
		String bukrs = null;
		//产品订单正点交付率
		List<THM_KIP12> listDeliver = iReportMapperKPI12.selectProOTDeliverGoDown(yearMonth);
		List<THM_KIP12> listDeliverSum = iReportMapperKPI12.selectProOTDeliverGoDownSum(yearMonth);
		
		for(THM_KIP12 value:listDeliver){
			//arktx,plan_menge,on_time_menge,on_time_rate
			resultMapDeliver.put("arktx", value.getArktx());
			resultMapDeliver.put("plan_menge", value.getPlan_menge());
			resultMapDeliver.put("on_time_menge", value.getOn_time_menge());
			resultMapDeliver.put("on_time_rate", value.getOn_time_rate());
			resultDeliver.add(resultMapDeliver);
			resultMapDeliver = new HashMap<String, Object>();
		}
		if(listDeliverSum.get(0) != null){
			resultMapDeliver.put("arktx", "合计");
			resultMapDeliver.put("plan_menge", listDeliverSum.get(0).getPlan_menge());
			resultMapDeliver.put("on_time_menge", listDeliverSum.get(0).getOn_time_menge());
			resultMapDeliver.put("on_time_rate", listDeliverSum.get(0).getOn_time_rate());
			resultDeliver.add(resultMapDeliver);
		}
		
		//查询公司
		List<THM_KIP12> listCompany = iReportMapperKPI12.selectProOTDeliverCompany(yearMonth);
		List<Object> resultCompany = new ArrayList<Object>();
		if(listCompany.size() > 0){
			bukrs = listCompany.get(0).getBukrs();
			Map<String,Object> resultMapCompany = new HashMap<String, Object>();
			for(THM_KIP12 value:listCompany){
				//bukrs,txtmd
				resultMapCompany.put("bukrs", value.getBukrs());
				resultMapCompany.put("txtmd", value.getTxtmd());
				resultCompany.add(resultMapCompany);
				resultMapCompany = new HashMap<String, Object>();
			}
		}
		//公司下产品订单正点交付率
		List<THM_KIP12> listDeliverByBukrs = iReportMapperKPI12.selectProOTRateDeliverByBukrsGoDown(bukrs, yearMonth);
		List<THM_KIP12> listDeliverByBukrsSum = iReportMapperKPI12.selectProOTRateDeliverByBukrsGoDownSum(bukrs, yearMonth);
		Map<String,Object> resultMapDeliverByBukrs = new HashMap<String, Object>();
		List<Object> resultDeliverByBukrs = new ArrayList<Object>();
		for(THM_KIP12 value:listDeliverByBukrs){
			//txtmd,arktx,plan_menge,on_time_menge,on_time_rate
			resultMapDeliverByBukrs.put("txtmd", value.getTxtmd());
			resultMapDeliverByBukrs.put("arktx", value.getArktx());
			resultMapDeliverByBukrs.put("plan_menge", value.getPlan_menge());
			resultMapDeliverByBukrs.put("on_time_menge", value.getOn_time_menge());
			resultMapDeliverByBukrs.put("on_time_rate", value.getOn_time_rate());
			
			resultDeliverByBukrs.add(resultMapDeliverByBukrs);
			resultMapDeliverByBukrs = new HashMap<String, Object>();
		}
		if(listDeliverByBukrsSum.get(0) != null){
			resultMapDeliverByBukrs.put("arktx", "合计");
			resultMapDeliverByBukrs.put("plan_menge", listDeliverByBukrsSum.get(0).getPlan_menge());
			resultMapDeliverByBukrs.put("on_time_menge", listDeliverByBukrsSum.get(0).getOn_time_menge());
			resultMapDeliverByBukrs.put("on_time_rate", listDeliverByBukrsSum.get(0).getOn_time_rate());
			resultDeliverByBukrs.add(resultMapDeliverByBukrs);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> mapTmp = new HashMap<String, Object>();
		mapTmp.put("COMPANY", resultCompany);
		mapTmp.put("ONTIMEDELIVER", resultDeliver);
		mapTmp.put("ONTIMEDELIVERBYBUKRS", resultDeliverByBukrs);
		map.put("DATA", mapTmp);
		String json = mapper.writeValueAsString(map);
		log.info("+++++++++"+json);
		
		return json;
	}
	
	@Override
	public Object getProOTDeliverByBukrsGoDown(String yearMonth, String bukrs)
			throws JsonProcessingException, IOException, DataAccessException {
		// TODO Auto-generated method stub
		Map<String,Object> resultMapDeliverByBukrs = new HashMap<String, Object>();
		List<Object> resultDeliverByBukrs = new ArrayList<Object>();
		//公司下产品订单正点交付率
		List<THM_KIP12> listDeliverByBukrs = iReportMapperKPI12.selectProOTRateDeliverByBukrsGoDown(bukrs, yearMonth);
		List<THM_KIP12> listDeliverByBukrsSum = iReportMapperKPI12.selectProOTRateDeliverByBukrsGoDownSum(bukrs, yearMonth);
		for(THM_KIP12 value:listDeliverByBukrs){
			//txtmd,arktx,plan_menge,on_time_menge,on_time_rate
			resultMapDeliverByBukrs.put("txtmd", value.getTxtmd());
			resultMapDeliverByBukrs.put("arktx", value.getArktx());
			resultMapDeliverByBukrs.put("plan_menge", value.getPlan_menge());
			resultMapDeliverByBukrs.put("on_time_menge", value.getOn_time_menge());
			resultMapDeliverByBukrs.put("on_time_rate", value.getOn_time_rate());
			
			resultDeliverByBukrs.add(resultMapDeliverByBukrs);
			resultMapDeliverByBukrs = new HashMap<String, Object>();
		}
		if(listDeliverByBukrsSum.get(0) != null){
			resultMapDeliverByBukrs.put("arktx", "合计");
			resultMapDeliverByBukrs.put("on_time_menge", listDeliverByBukrsSum.get(0).getOn_time_menge());
			resultMapDeliverByBukrs.put("plan_menge", listDeliverByBukrsSum.get(0).getPlan_menge());
			resultMapDeliverByBukrs.put("on_time_rate", listDeliverByBukrsSum.get(0).getOn_time_rate());
			resultDeliverByBukrs.add(resultMapDeliverByBukrs);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> mapTmp = new HashMap<String, Object>();
		mapTmp.put("ONTIMEDELIVERBYBUKRS", resultDeliverByBukrs);
		map.put("DATA", mapTmp);
		String json = mapper.writeValueAsString(map);
		log.info("+++++++++"+json);
		
		return json;
	}

	@Override
	public Object getProUnCompSumGoDown(String yearMonth)
			throws JsonProcessingException, IOException, DataAccessException {
		// TODO Auto-generated method stub
		String bukrs = "";
		Map<String,Object> resultMapUnCompSumGoDown = new HashMap<String, Object>();
		List<Object> resultUnCompSumGoDown = new ArrayList<Object>();
		//
		List<THM_KIP12> listUnCompSumGoDown = iReportMapperKPI12.selectProUnCompSumGoDown(yearMonth);
		for(THM_KIP12 value:listUnCompSumGoDown){
			//arktx,uncomplete_sum
			resultMapUnCompSumGoDown.put("arktx", value.getArktx());
			resultMapUnCompSumGoDown.put("uncomplete_sum", value.getUncomplete_sum());
			resultUnCompSumGoDown.add(resultMapUnCompSumGoDown);
			resultMapUnCompSumGoDown = new HashMap<String, Object>();
		}
		
		//查询公司
		List<THM_KIP12> listCompany = iReportMapperKPI12.selectProUnCompCompany(yearMonth);
		List<Object> resultCompany = new ArrayList<Object>();
		if(listCompany.size() > 0){
			bukrs = listCompany.get(0).getBukrs();
			Map<String,Object> resultMapCompany = new HashMap<String, Object>();
			for(THM_KIP12 value:listCompany){
				//bukrs,txtmd
				resultMapCompany.put("bukrs", value.getBukrs());
				resultMapCompany.put("txtmd", value.getTxtmd());
				resultCompany.add(resultMapCompany);
				resultMapCompany = new HashMap<String, Object>();
			}
		}
		//公司下产品累计未完成总数钻取
		List<THM_KIP12> listUnCompByBukrs = iReportMapperKPI12.selectProUnCompSumByBukrsGoDown(bukrs, yearMonth);
		
		Map<String,Object> resultMapUnCompByBukrs = new HashMap<String, Object>();
		List<Object> resultUnCompByBukrs = new ArrayList<Object>();
		for(THM_KIP12 value:listUnCompByBukrs){
			//bukrs,txtmd
			resultMapUnCompByBukrs.put("arktx", value.getArktx());
			resultMapUnCompByBukrs.put("uncomplete_sum", value.getUncomplete_sum());
			resultUnCompByBukrs.add(resultMapUnCompByBukrs);
			resultMapUnCompByBukrs = new HashMap<String, Object>();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> mapTmp = new HashMap<String, Object>();
		mapTmp.put("COMPANY", resultCompany);
		mapTmp.put("UNCOMPSUMGODOWN", resultUnCompSumGoDown);
		mapTmp.put("UNCOMPSUMBYBUKRSGODOWN", resultUnCompByBukrs);
		map.put("DATA", mapTmp);
		String json = mapper.writeValueAsString(map);
		log.info("+++++++++"+json);
		
		return json;
	}

	@Override
	public Object getProUnCompSumByBukrsGoDown(String yearMonth, String bukrs)
			throws JsonProcessingException, IOException, DataAccessException {
		// TODO Auto-generated method stub
		Map<String,Object> resultMapUnCompSumByBukrsGoDown = new HashMap<String, Object>();
		List<Object> resultUnCompSumByBukrsGoDown = new ArrayList<Object>();
		//
		List<THM_KIP12> listUnCompSumGoDown = iReportMapperKPI12.selectProUnCompSumByBukrsGoDown(bukrs, yearMonth);
		for(THM_KIP12 value:listUnCompSumGoDown){
			//arktx,uncomplete_sum 
			resultMapUnCompSumByBukrsGoDown.put("arktx", value.getArktx());
			resultMapUnCompSumByBukrsGoDown.put("uncomplete_sum", value.getUncomplete_sum());
			resultUnCompSumByBukrsGoDown.add(resultMapUnCompSumByBukrsGoDown);
			resultMapUnCompSumByBukrsGoDown = new HashMap<String, Object>();
		}
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("UNCOMPSUMBYBUKRSGODOWN", resultUnCompSumByBukrsGoDown);
		String json = mapper.writeValueAsString(map);
		log.info("+++++++++"+json);
		
		return json;
	}
	
	@Override
	public Object getProCompSumGoDown(String yearMonth)
			throws JsonProcessingException, IOException, DataAccessException {
		// TODO Auto-generated method stub
		Map<String,Object> resultMapCompSumGoDown = new HashMap<String, Object>();
		Map<String,Object> resultMapCompSumGoDownSum = new HashMap<String, Object>();
		List<Object> resultCompSumGoDown = new ArrayList<Object>();
		String bukrs = "";
		List<THM_KIP12> listCompSumGoDown = iReportMapperKPI12.selectProCompSumGoDown(yearMonth);
		//总的合计
		List<THM_KIP12> listCompSumGoDownSum = iReportMapperKPI12.selectProCompSumGoDownSum(yearMonth);
		for(THM_KIP12 value:listCompSumGoDown){
			//arktx,uncomplete_sum
			resultMapCompSumGoDown.put("arktx", value.getArktx());
			resultMapCompSumGoDown.put("complete_sum", value.getComplete_sum());
			resultCompSumGoDown.add(resultMapCompSumGoDown);
			resultMapCompSumGoDown = new HashMap<String, Object>();
		}
		if(listCompSumGoDownSum.get(0) != null){
			resultMapCompSumGoDownSum.put("arktx", listCompSumGoDownSum.get(0).getArktx());
			resultMapCompSumGoDownSum.put("complete_sum", listCompSumGoDownSum.get(0).getComplete_sum());
			resultCompSumGoDown.add(resultMapCompSumGoDownSum);
		}
		
		//查询公司
		List<THM_KIP12> listCompany = iReportMapperKPI12.selectProCompCompany(yearMonth);
		List<Object> resultCompany = new ArrayList<Object>();
		if(listCompany.size() > 0){
			bukrs = listCompany.get(0).getBukrs();
			Map<String,Object> resultMapCompany = new HashMap<String, Object>();
			for(THM_KIP12 value:listCompany){
				//bukrs,txtmd
				resultMapCompany.put("bukrs", value.getBukrs());
				resultMapCompany.put("txtmd", value.getTxtmd());
				resultCompany.add(resultMapCompany);
				resultMapCompany = new HashMap<String, Object>();
			}
		}
		
		//公司下产品完成总数钻取
		List<THM_KIP12> listCompSumByBukrs = iReportMapperKPI12.selectProCompSumByBukrsGoDown(bukrs, yearMonth);
		//第一个公司合计
		List<THM_KIP12> listCompSumByBukrsSum = iReportMapperKPI12.selectProCompSumByBukrsGoDownSum(bukrs, yearMonth);
		
		Map<String,Object> resultMapCompSumByBukrs = new HashMap<String, Object>();
		List<Object> resultCompSumByBukrs = new ArrayList<Object>();
		for(THM_KIP12 value:listCompSumByBukrs){
			//bukrs,txtmd
			resultMapCompSumByBukrs.put("arktx", value.getArktx());
			resultMapCompSumByBukrs.put("complete_sum", value.getComplete_sum());
			resultCompSumByBukrs.add(resultMapCompSumByBukrs);
			resultMapCompSumByBukrs = new HashMap<String, Object>();
		}
		if(listCompSumByBukrsSum.get(0) != null){
			resultMapCompSumGoDown.put("arktx", listCompSumByBukrsSum.get(0).getArktx());
			resultMapCompSumGoDown.put("complete_sum", listCompSumByBukrsSum.get(0).getComplete_sum());
			resultCompSumByBukrs.add(resultMapCompSumGoDown);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> mapTmp = new HashMap<String, Object>();
		mapTmp.put("COMPANY", resultCompany);
		mapTmp.put("COMPSUMGODOWN", resultCompSumGoDown);
		mapTmp.put("COMPSUMBYBUKRSGODOWN", resultCompSumByBukrs);
		map.put("DATA", mapTmp);
		String json = mapper.writeValueAsString(map);
		log.info("+++++++++"+json);
		
		return json;
	}

	@Override
	public Object getProCompSumByBukrsGoDown(String yearMonth, String bukrs)
			throws JsonProcessingException, IOException, DataAccessException {
		// TODO Auto-generated method stub
		Map<String,Object> resultMapCompSumByBukrsGoDown = new HashMap<String, Object>();
		List<Object> resultCompSumByBukrsGoDown = new ArrayList<Object>();
		//
		List<THM_KIP12> listCompSumGoDown = iReportMapperKPI12.selectProCompSumByBukrsGoDown(bukrs, yearMonth);
		List<THM_KIP12> listCompSumGoDownSum = iReportMapperKPI12.selectProCompSumByBukrsGoDownSum(bukrs, yearMonth);
		for(THM_KIP12 value:listCompSumGoDown){
			//arktx,uncomplete_sum 
			resultMapCompSumByBukrsGoDown.put("arktx", value.getArktx());
			resultMapCompSumByBukrsGoDown.put("complete_sum", value.getComplete_sum());
			resultCompSumByBukrsGoDown.add(resultMapCompSumByBukrsGoDown);
			resultMapCompSumByBukrsGoDown = new HashMap<String, Object>();
		}
		if(listCompSumGoDownSum.get(0) != null){
			resultMapCompSumByBukrsGoDown.put("arktx", listCompSumGoDownSum.get(0).getArktx());
			resultMapCompSumByBukrsGoDown.put("complete_sum", listCompSumGoDownSum.get(0).getComplete_sum());
			resultCompSumByBukrsGoDown.add(resultMapCompSumByBukrsGoDown);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("COMPSUMBYBUKRSGODOWN", resultCompSumByBukrsGoDown);
		String json = mapper.writeValueAsString(map);
		log.info("+++++++++"+json);
		
		return json;
	}
	

}
