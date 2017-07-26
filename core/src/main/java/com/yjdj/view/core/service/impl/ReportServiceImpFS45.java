package com.yjdj.view.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.yjdj.view.core.mapper.IReportMapperFS45;
import com.yjdj.view.core.service.IReportServiceFS45;
import com.yjdj.view.core.web.util.dwz.AjaxObject;

/**
 * Created by liwuhua on 16/11/28.
 */

@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS45 implements IReportServiceFS45{

    @Autowired
    private IReportMapperFS45 ireportmapperfs45;
    
    @Autowired
    private GenericMapper genericMapper;
    private final Logger log = Logger.getLogger(ReportServiceImpFS45.class);

    
    public String getListFs45(QueryBean queryBean,List<String> plantList) throws JsonProcessingException,DataAccessException{
        ObjectMapper mapper = new ObjectMapper();
        String resultJson = "{\"matnrpullcompar\":[]}";
		/*  报告日期	单值		必选 DELIDATE
		        工厂	多值		可选 WERKS
		        工作中心	多值		可选  ZHWCX
		        组件采购类型	多值		可选  COMPPURTYPE
		        采购组	多值+排除按钮	排除值	可选  EKGRP
		        供应商编码	多值+排除按钮	排除值	可选   LIFNR
		        配送方式	多值+排除按钮	排除值ZTMM_PSMAP- ZPSFS	可选  ZPSFS  */
        
        String delidate = queryBean.getStartTimeThree(); // DELIDATE = 查询条件中的报告日期
     	List<String> werksValue = queryBean.getWerksValue(); //and WERKS  in 工厂多值
     	if(werksValue==null){
     		werksValue=new ArrayList<>();
     	}
     	
        // 此处需做判断  空值情况下  如果有权限工厂那么查询的是权限范围内所有工厂    如果没有权限工厂  那么返回没有权限
     	if(CollectionUtils.isEmpty(werksValue)&&CollectionUtils.isEmpty(plantList)){
     	     //权限中没有工厂
     		return AjaxObject.newError("没有分配权限工厂").setCallbackType("").toString();
     	}else if(CollectionUtils.isEmpty(werksValue)&&(!CollectionUtils.isEmpty(plantList))){
             //将查询条件中的工厂置为查询出所有的权限工厂
     		werksValue=new ArrayList<String>();
     		werksValue.addAll(plantList);
     	}
     	
    	List<String> zhwcxValue = queryBean.getVendorValue(); //   工作中心
        List<String> comppurtypeValue = queryBean.getDauatValue(); // AND COMPPURTYPE IN 组件采购类型
        List<String> ekgrpValue = queryBean.getPurGroupValue(); // 采购组
        List<String> lifnrValue = queryBean.getVendorValue(); // 供应商编码
    	List<String> zpsfsValue = queryBean.getTwoValue();  // 配送方式
        
        boolean isExport = queryBean.isExport();  //是否是导出
        boolean isExclu1 = queryBean.isExclu1();  //isExclu1   and EKGRP in   采购组        排他的标识
        boolean isExclu2 = queryBean.isExclu2();   //isExclu2   and LIFNR in 供应商编码   排他的标识
        boolean isExclu3 = queryBean.isShow();    //isExclu3   and ZPSFS  in  配送方式  排他的标识

        Integer startitem = queryBean.getStartitem();
        Integer pageitem  = queryBean.getPageitem();
     	 
            HashMap<String, Object> paraMap = new HashMap<>();
        	paraMap.put("delidate", delidate);
        	paraMap.put("werksValue", werksValue);
        	paraMap.put("zhwcxValue", zhwcxValue);
            paraMap.put("isExport", isExport);
        	paraMap.put("isExclu1",isExclu1);
        	paraMap.put("isExclu2", isExclu2);
        	paraMap.put("isExclu3", isExclu3);
        	paraMap.put("comppurtypeValue", comppurtypeValue);
        	paraMap.put("lifnrValue", lifnrValue);
        	paraMap.put("zpsfsValue", zpsfsValue);
        	paraMap.put("ekgrpValue", ekgrpValue);
        	paraMap.put("startitem", startitem);
        	paraMap.put("pageitem", pageitem);
        	
       // 报告日期 DELIDATE  基本开始日期  endTime  startTime   必选的  其他不适必选的
        	if(StringUtils.isNotBlank(delidate)){
        		
        		HashMap resultMap=new HashMap<>(); 
 				 List<HashMap> MatnrpullCompar = ireportmapperfs45.getOrderShortsum(paraMap);
 				resultMap.put("matnrpullcompar", MatnrpullCompar);
 	            resultJson = mapper.writeValueAsString(resultMap);
        	}

        	return resultJson;
    }


}
