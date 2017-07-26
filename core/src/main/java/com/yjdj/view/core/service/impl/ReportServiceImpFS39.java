package com.yjdj.view.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.mapper.IReportMapperFS39;
import com.yjdj.view.core.service.IReportServiceFS39;
import com.yjdj.view.core.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS39 implements IReportServiceFS39 {

	@Autowired
	private IReportMapperFS39 iReportMapperFS39;

	private Map<String, Object> pcfhMap = null;

	@Override
	public Object getGroes(String groes) throws JsonProcessingException, DataAccessException {
		ObjectMapper mapper = new ObjectMapper();
		List<String> mapList = iReportMapperFS39.getGroes(groes);
		return mapper.writeValueAsString(mapList);
	}

	@Override
	public Object getLtxa1(String ltxa1) throws JsonProcessingException, DataAccessException {
		ObjectMapper mapper = new ObjectMapper();
		List<String> mapList = iReportMapperFS39.getLtxa1(ltxa1);
		return mapper.writeValueAsString(mapList);
	}

	@Override
	public Object getKdauf(String kdauf) throws JsonProcessingException, DataAccessException {
		ObjectMapper mapper = new ObjectMapper();
		List<String> mapList = iReportMapperFS39.getKdauf(kdauf);
		return mapper.writeValueAsString(mapList);
	}

	@Override
	public Object getKdpos(String kdpos) throws JsonProcessingException, DataAccessException {
		ObjectMapper mapper = new ObjectMapper();
		List<String> mapList = iReportMapperFS39.getKdpos(kdpos);
		return mapper.writeValueAsString(mapList);
	}

	@Override
	public Object getArbpl(String arbpl,String ktext) throws JsonProcessingException, DataAccessException {
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String,Object>> mapList = iReportMapperFS39.getArbpl(arbpl,ktext);
		return mapper.writeValueAsString(mapList);
	}

	@Override
	public Object getXqdw(String xqdw) throws JsonProcessingException, DataAccessException {
		ObjectMapper mapper = new ObjectMapper();
		List<String> mapList = iReportMapperFS39.getXqdw(xqdw);
		return mapper.writeValueAsString(mapList);
	}

	@Override
	public Object getAuart(String auart) throws JsonProcessingException, DataAccessException {
		ObjectMapper mapper = new ObjectMapper();
		List<String> mapList = iReportMapperFS39.getAuart(auart);
		return mapper.writeValueAsString(mapList);
	}

	@Override
	public String getDataFS39(QueryBean queryBean) throws JsonProcessingException, DataAccessException, ParseException {
		Map<String, Object> reMap = new HashMap<String, Object>();
		List<Map<String, Object>> dataMapList = new ArrayList<Map<String, Object>>();
		pcfhMap = new HashMap<String, Object>();

		List<Map<String, Object>> mapList = null;

		if(queryBean.getKdaufValue()==null || queryBean.getKdaufValue().isEmpty()){
			List<String> kdfsValue = iReportMapperFS39.getKdfs(queryBean.getGroesValue(), queryBean.getLtxa1Value(), queryBean.getMatnrValue(), queryBean.getMatnrInterval(),
					queryBean.getDwerkValue(), queryBean.getArbplValue(), queryBean.getXqdwValue(), queryBean.getAuartValue());

			mapList = iReportMapperFS39.getDateFS3902(kdfsValue, queryBean.getLtxa1Value(), queryBean.getMatnrValue(), queryBean.getMatnrInterval(),
					queryBean.getKdaufValue(), queryBean.getKdposValue(), queryBean.getDwerkValue(), queryBean.getArbplValue(),
					queryBean.getXqdwValue(), queryBean.getAuartValue(),queryBean.getStartTime(),queryBean.getEndTime());
		} else {
			mapList = iReportMapperFS39.getDateFS39(queryBean.getGroesValue(), queryBean.getLtxa1Value(), queryBean.getMatnrValue(), queryBean.getMatnrInterval(),
					queryBean.getKdaufValue(), queryBean.getKdposValue(), queryBean.getDwerkValue(), queryBean.getArbplValue(),
					queryBean.getXqdwValue(), queryBean.getAuartValue(),queryBean.getStartTime(),queryBean.getEndTime());
		}

		if(CollectionUtils.isNotEmpty(mapList)){
			for(Map<String, Object> map : mapList){
				map = getMapByDGS(map,queryBean,pcfhMap);
				if(map.containsKey("DQJH") || map.containsKey("DQCC")){
					dataMapList.add(map);
				}
			}
		}

		reMap.putAll(pcfhMap);
		reMap.put("data",dataMapList);

		ObjectMapper mapper = new ObjectMapper();

		if(CollectionUtils.isNotEmpty(dataMapList)){
			return mapper.writeValueAsString(reMap);
		}else {
			return "{}";
		}
	}

	private Map<String, Object> getMapByDGS(Map<String, Object> map,QueryBean queryBean,Map<String, Object> pcfhMap) throws ParseException {
		String dgs = map.get("DGS").toString();

		List<Float> plList = new ArrayList<Float>();

		String[] ss = dgs.split("@");
		for(String str : ss){
			String[] dt = str.split(":");
			//统计批量书记,当期计划,当期产出
			sumDqsl(map,dt[0],dt[1],dt[2],queryBean.getStartTime(),queryBean.getEndTime(),plList,pcfhMap);
		}

		if(CollectionUtils.isNotEmpty(plList)){
			Float pl = getPlByList(plList);
			map.put("PL",pl);
		}

		map.remove("DGS");

		return map;
	}

	private void sumDqsl(Map<String, Object> map,String dtsl,String time,
						 String sttyp,String startTime,String endTime,List<Float> plList,Map<String, Object> pcfhMap) throws ParseException {
		Long date = DateUtils.getDateByStr(time).getTime();
		Long date1 = DateUtils.getDateByStr(startTime).getTime();
		Long date2 = DateUtils.getDateByStr(endTime).getTime();
		if(date>=date1 && date<=date2){
			//日期比较
			compareDateByMap(map,time);
			compareDateByMap(pcfhMap,time);


			float sl = Float.parseFloat(dtsl);
			if("a".equals(sttyp)){
				upMap(map,sl,"DQJH");

				map.put(time+"JH",dtsl);
				//统计计划数量
				plList.add(Float.parseFloat(dtsl));
			}else {
				upMap(map,sl,"DQCC");

				map.put(time+"CC",dtsl);
			}
			//日排产
			upMap(pcfhMap,Float.parseFloat(dtsl),time);
		}
	}

	//获取批量
	private Float getPlByList(List<Float> list){
		Set<Float> set = new HashSet<Float>(list);
		Map<Integer,List<Float>> map = new HashMap<Integer,List<Float>>();
		int max = Integer.MIN_VALUE;
		for(Float str : set){
			int num = Collections.frequency(list,str);
			max = max > num ? max : num;

			List<Float> ll = map.get(num);
			if(CollectionUtils.isEmpty(ll)){
				ll = new ArrayList<Float>();
			}
			ll.add(str);
			map.put(Collections.frequency(list,str), ll);
		}

		List<Float> ll = map.get(max);
		//倒排
		Collections.reverse(ll);

		return ll.get(0);
	}


	//收集日排产
	private void upMap(Map<String, Object> pcfhMap,float dtsl,String time){
		Float num = (Float) pcfhMap.get(time);
		pcfhMap.put(time,null==num? dtsl : num+dtsl);
	}

	//日期比较
	private void compareDateByMap(Map<String, Object> pcfhMap,String time) throws ParseException {
		String minTime = (String) pcfhMap.get("minTime");
		String maxTime = (String) pcfhMap.get("maxTime");
		Long dateTime = DateUtils.getDateByStr(time).getTime();

		if(null == minTime){
			pcfhMap.put("minTime",time);
		}else{
			Long minDateTime = DateUtils.getDateByStr(minTime).getTime();
			pcfhMap.put("minTime",minDateTime < dateTime ? minTime : time);
		}

		if(null == maxTime){
			pcfhMap.put("maxTime",time);
		}else{
			Long maxDateTime = DateUtils.getDateByStr(maxTime).getTime();
			pcfhMap.put("maxTime",maxDateTime > dateTime ? maxTime : time);
		}
	}
}
