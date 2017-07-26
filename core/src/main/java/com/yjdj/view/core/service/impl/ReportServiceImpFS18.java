package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.mapper.GenericMapper;
import com.yjdj.view.core.mapper.IReportMapperFS18;
import com.yjdj.view.core.service.IReportServiceFS18;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * Created by liwuhua on 16/11/7.
 */

@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS18 implements IReportServiceFS18{

    @Autowired
    private IReportMapperFS18 ireportmapperfs18;
    
    @Autowired
    private GenericMapper genericMapper;
    private final Logger log = Logger.getLogger(ReportServiceImpFS18.class);

    
	    @Override
	    public String getListFs18(QueryBean queryBean,List<String> plantList) throws JsonProcessingException,DataAccessException{
	        ObjectMapper mapper = new ObjectMapper();
	        String resultJson = "{\"msg\":\"预警状态必选一种\"}";
       	     
	        //只有显示标识是必选的
	     	 List<String> vbelnValue = queryBean.getVbelnValue();
             List<String> matnrInterval = queryBean.getMatnrInterval();
             List<String> werksValue = queryBean.getWerksValue();  // 销售订单工厂
           
          	// 此处需做判断  空值情况下  如果有权限工厂那么查询的是权限范围内所有工厂    如果没有权限工厂  那么返回没有权限
 	     	if(CollectionUtils.isEmpty(werksValue)&&CollectionUtils.isEmpty(plantList)){
 	     	     //权限中没有工厂
 	     		return AjaxObject.newError("没有分配权限销售订单工厂").setCallbackType("").toString();
 	     	}else if(CollectionUtils.isEmpty(werksValue)&&(!CollectionUtils.isEmpty(plantList))){
                  //将查询条件中的工厂置为查询出所有的权限工厂
 	     		werksValue.clear();
 	     		werksValue.addAll(plantList);
 	     	}
 	     	
 	         List<String> dwerkValue = queryBean.getDwerkValue(); //生产工厂
 	      	// 此处需做判断  空值情况下  如果有权限工厂那么查询的是权限范围内所有工厂    如果没有权限工厂  那么返回没有权限
 	     	if(CollectionUtils.isEmpty(dwerkValue)&&CollectionUtils.isEmpty(plantList)){
 	     	     //权限中没有工厂
 	     		return AjaxObject.newError("没有分配权限生产工厂").setCallbackType("").toString();
 	     	}else if(CollectionUtils.isEmpty(dwerkValue)&&(!CollectionUtils.isEmpty(plantList))){
                  //将查询条件中的工厂置为查询出所有的权限工厂
 	     		dwerkValue.clear();
 	     		dwerkValue.addAll(plantList);
 	     	}
             
             
             
             List<String> dauatValue = queryBean.getDauatValue();
             List<String> showmarks = queryBean.getTwoValue(); //显示标识
             
             
        	String startTime = queryBean.getStartTime();   // AND EDATU >= 起始销售订单交货日期查询条件
            String endTime = queryBean.getEndTime();        //AND EDATU <= 截止销售订单交货日期查询条件
            String startTimeTwo = queryBean.getStartTimeTwo();//AND GLTRP >= 起始计划完成日期查询条件
            String endTimeTwo = queryBean.getEndTimeTwo();//AND GLTRP <= 截止计划完成日期查询条件
            String startTimeThree = queryBean.getStartTimeThree();// and ORDERWAREEDATE >= 起始生产订单收货入库日期查询条件   
            String endTimeThree = queryBean.getEndTimeThree(); //and ORDERWAREEDATE <= 截止生产订单收货入库日期查询条件
            int startitem = queryBean.getStartitem();
            int pageitem  = queryBean.getPageitem();

             if(showmarks!=null&&showmarks.size()!=0){
            	 if((!showmarks.contains("0"))||(!showmarks.contains("1")||(!showmarks.contains("2")))){
            		 resultJson = "{\"msg\":\"预警状态不在范围内\"}";
            	 }
             
	            HashMap<String, Object> paraMap = new HashMap<>();
	        	paraMap.put("vbelnValue", vbelnValue);
	        	paraMap.put("matnrInterval", matnrInterval);
	        	paraMap.put("werksValue", werksValue);
	        	paraMap.put("dwerkValue", dwerkValue);
	        	paraMap.put("dauatValue", dauatValue);
	        	paraMap.put("showmarks", showmarks);
	        	paraMap.put("startTime", startTime);
	        	paraMap.put("endTime", endTime);
	        	paraMap.put("startTimeTwo",startTimeTwo);
	        	paraMap.put("endTimeTwo", endTimeTwo);
	        	paraMap.put("startTimeThree",startTimeThree);
	        	paraMap.put("endTimeThree", endTimeThree);
	        	paraMap.put("startitem", startitem);
	        	paraMap.put("pageitem", pageitem);
	        	
	        	
	            HashMap resultMap=new HashMap<>(); //结果集  转换成json格式 
	            HashMap resultMap1=new HashMap<>(); //结果集  转换成json格式 
				List<Map> marorderdeliver = ireportmapperfs18.getMarorderdeliver(paraMap);
				resultMap.put("marorderdeliver", marorderdeliver);
	            resultJson = mapper.writeValueAsString(resultMap);
                	            
	              return resultJson;
             }
                  return resultJson;
	    }


}
