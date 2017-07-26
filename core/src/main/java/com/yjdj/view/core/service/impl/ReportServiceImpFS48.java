package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import com.yjdj.view.core.mapper.IReportMapperFS48;
import com.yjdj.view.core.service.IReportServiceFS48;

/**
 * Created by liwuhua on 17/03/30.
 */

@Service
@TransactionConfiguration(defaultRollback = false)
@SuppressWarnings("all")
public class ReportServiceImpFS48 implements IReportServiceFS48{

    @Autowired
    private IReportMapperFS48 ireportmapperfs48;
    
    @Autowired
    private GenericMapper genericMapper;
    private final Logger log = Logger.getLogger(ReportServiceImpFS48.class);


	@Override
	public String getStations() throws JsonProcessingException, IOException, DataAccessException {
		 ObjectMapper mapper = new ObjectMapper();
		 String resultJson="{}";
		 List<String> stations = ireportmapperfs48.getStations();
		 HashMap resultMap=new HashMap<>();  
    	 resultMap.put("stations", stations);
	     resultJson = mapper.writeValueAsString(resultMap);
		
		return resultJson;
	}
    
    
    
    @Override
    public String selecProductAttachOrSparepart(QueryBean queryBean) throws JsonProcessingException,DataAccessException{
        ObjectMapper mapper = new ObjectMapper();
        
        //时间过滤
       	String startMonth = queryBean.getStartTime();  
       	//startMonth="000000";
       	String endMonth = queryBean.getEndTime();  
       	//endMonth = "201705";
        String stationId = queryBean.getYlzd(); //服务站点
        String startitem = queryBean.getStartitem()+"";
        String pageitem  = queryBean.getPageitem()+"";
        String bsString = queryBean.getProductType(); //查询标识
        
        String resultJson="1".equals(bsString)?"{\"matnrpullcompar\":[]}":"{\"matnrpullcompar\":[]}";

 
            // 报告日期 DELIDATE  基本开始日期  endTime  startTime   必选的  其他不适必选的  StringUtils.isNotBlank(startMonth)&&StringUtils.isNotBlank(endMonth)&&
        	if(StringUtils.isNotBlank(stationId)){
        		HashMap resultMap=new HashMap<>(); 
        		
        		if(StringUtils.isNotEmpty(bsString)&&"1".equals(bsString)){
        			//产品配属
        			//List<Map> productAttach = ireportmapperfs48.selecProductAttach(startMonth,endMonth,stationId,startitem,pageitem);
        			//修改   不需要时间查询   只展示最终的站点信息    ------37服务站
        			List<Map> productAttach = ireportmapperfs48.newSelecProductAttach(stationId,startitem,pageitem);
        			
        			resultMap.put("productattach", productAttach);
         	        resultJson = mapper.writeValueAsString(resultMap);
        		}else if(StringUtils.isNotEmpty(bsString)&&"2".equals(bsString)){
        			//所有的段所 控制顺序用
        			List<LinkedHashMap> duansuos = ireportmapperfs48.getDuansuos(stationId);
        			//List<Map> spareparts = ireportmapperfs48.selecSparepart(startMonth,endMonth,stationId,startitem,pageitem);
        			List<Map> spareparts = ireportmapperfs48.newSelecSparepart(stationId,startitem,pageitem);
        			List<Object> spareLists = new ArrayList<>();//最终的所有的备品配件
        			   //获得一个产品型号
        	        if(CollectionUtils.isNotEmpty(spareparts) ){
        				for(int i=0;i<spareparts.size();i++){
        					String aname=(String) spareparts.get(i).get("pname");
        					String amodel=(String) spareparts.get(i).get("pmodel");
        					String locodepos=(String) spareparts.get(i).get("locodepos");
        					String counts=(String) spareparts.get(i).get("counts"); 
        					List<String> locodepList = Arrays.asList(locodepos.split(",")); //所有的段所名称
        					List<String> countList = Arrays.asList(counts.split(",")); //所有的段所数量
        					List<Object> singleSpare = new ArrayList<>();//最终的所有的备品配件
        					singleSpare.add(aname); 
        					singleSpare.add(amodel); 
        					//一个产品型号中  遍历所有的段所 得到对应的数量
        					int duanindex=1;
        					int amount=0;
        					/*for(LinkedHashMap duansuo:duansuos){
        						String  duansuoname= duansuo.get("locomotivedepot")+""; 	
        					    if(locodepList.contains(duansuoname)){
        					    	int  index1=locodepList.indexOf(duansuoname);
        					    	singleSpare.add(countList.get(locodepList.indexOf(duansuoname)));
        					    	amount=amount+Integer.valueOf(countList.get(locodepList.indexOf(duansuoname)));
        					    }else{
        					    	singleSpare.add("");
        					    }
        					if(duanindex==duansuos.size()){
        						singleSpare.add(amount);
        					    spareLists.add(singleSpare);	
        					}
        					duanindex++;
        				    }*/
        					for(LinkedHashMap duansuo:duansuos){
        						String  duansuoname= duansuo.get("locomotivedepot")+""; 	
        					    if(locodepList.contains(duansuoname)){
        					    	int  index1=locodepList.indexOf(duansuoname);
        					    	//判断countList是不是为空
        					    	if(countList.size() == 0){
        					    		singleSpare.add(""); // 第一步  发现countList数组为空时  ，把所对应的内容设置为空
        					    	}else{
        					    		//不为空   集合长度不一致，导致越界问题
        					    		int bs = locodepList.indexOf(duansuoname); //段所的下标
        					    		if(countList.size()-1 < bs){   //段所对应的数量的下标不足
        					    			singleSpare.add("");
        					    			amount=amount+Integer.valueOf("0");
        					    		}else{
        					    			String inp = countList.get(locodepList.indexOf(duansuoname));
        					    			singleSpare.add(inp);
        					    			if(inp.equals("")){
        					    				amount=amount+Integer.valueOf("0");
        					    			}else{
        					    				amount=amount+Integer.valueOf(countList.get(locodepList.indexOf(duansuoname)));
        					    			}
        					    		}
            					    	
        					    	}
        					    }else{
        					    	singleSpare.add("");
        					    }
        					if(duanindex==duansuos.size()){
        						singleSpare.add(amount);
        					    spareLists.add(singleSpare);	
        					}
        					duanindex++;
        				}

        					
        			}
        	        	
        	        }
        			resultMap.put("headers", duansuos); 
     				resultMap.put("spareLists", spareLists);
     	            resultJson = mapper.writeValueAsString(resultMap);
        		}
        	}

        	return resultJson;
    }

}
