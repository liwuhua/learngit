package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.InsertFieldTHM_FS22;
import com.yjdj.view.core.entity.mybeans.InsertFieldTHM_FS24;
import com.yjdj.view.core.entity.mybeans.MdmSecondCost;
import com.yjdj.view.core.entity.mybeans.MdmTcompCode;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS22_01;
import com.yjdj.view.core.entity.mybeans.THM_FS22_02;
import com.yjdj.view.core.mapper.GenericMapper;
import com.yjdj.view.core.mapper.IReportMapperFS22;
import com.yjdj.view.core.service.IReportServiceFS22;
import com.yjdj.view.core.util.ExcelExportUtil22;
import com.yjdj.view.core.util.ExcelExportUtil24;
import com.yjdj.view.core.util.ExcelImportUtil;
import com.yjdj.view.core.util.StringUtil;

/**
 * Created by liuchengli on 16/11/27.
 * 
 * 
 * 
 * 
 * private List<String> dauatValue;      //订单类型多值
 * private List<String> compCodeValue;  //公司代码编号多值
 * 
 * 
 * private String dateYearMonthStart; //年月区间上行
   private String dateYearMonthEnd;    //年月区间下行
 */

@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS22 implements IReportServiceFS22{

    @Autowired
    private IReportMapperFS22 ireportmapperfs22;
    
    @Autowired
    private GenericMapper genericMapper;
    private final Logger log = Logger.getLogger(ReportServiceImpFS22.class);

       
    
      @Override
      public List<THM_FS22_01> getListFsFirstDetail(QueryBean queryBean) throws IOException, ParseException {
    	     List<THM_FS22_01>  firstDetail = null;
	        //时间必选  
	        if((StringUtils.isNotBlank(queryBean.getDateYearMonthStart()) && StringUtils.isNotBlank(queryBean.getDateYearMonthEnd()))){
	        	
	        	//获取时间必选
	        	String startTime = queryBean.getDateYearMonthStart();
	            String endTime = queryBean.getDateYearMonthEnd();
	            
	            //公司代码
	         	List<String> compValues  = queryBean.getCompCodeValue(); 
	         	
	         	//订单类型
	         	String order_type = "'Z002'";
	            
	         	//项目编号
	         	List<String> inner_order  = queryBean.getOrderID(); 
	         	
	         	//查询分页起始位置
	         	int offset = queryBean.getStartitem();
	         	int pageitem = queryBean.getPageitem();
	         	
	         	
	            //多值 转换成字符串  ,号隔开
	            String p_bukrs =(compValues==null||compValues.size()==0)?"''" :compValues.toString().replace("[", "\'").replace("]", "\'").replace(", ",",");
	        	
	          //多值 转换成字符串  ,号隔开
	            String p_inner_order =(inner_order==null||inner_order.size()==0)?"''" :inner_order.toString().replace("[", "\'").replace("]", "\'").replace(", ",",");
	            
	            HashMap<String,String> paraMap = new HashMap<>();
	        	
	        	paraMap.put("start_date", startTime);
	        	paraMap.put("end_date"  , endTime);
	        	paraMap.put("compCodeValue"   , p_bukrs);
	        	paraMap.put("order_type"  , order_type);
	        	paraMap.put("inner_order"  , p_inner_order);
	        	paraMap.put("offset"  ,offset+"");
	        	paraMap.put("rowcount"  ,pageitem+"");
	        	
	        	
	            HashMap resultMap=new HashMap<>(); //结果集  转换成json格式 
				firstDetail = ireportmapperfs22.getYanFaCost(paraMap);

	        }
	        
	        return firstDetail;
      }
        @Override
	    public String getListFs22(QueryBean queryBean) throws IOException, ParseException {
	        ObjectMapper mapper = new ObjectMapper();
	        String resultJson = "";
            HashMap resultMap=new HashMap<>(); //结果集  转换成json格式 
			List<THM_FS22_01> periodexpense = getListFsFirstDetail(queryBean);
			resultMap.put("periodexpense", periodexpense);
			
			String secondTitle=getComName(queryBean.getCompCodeValue());
			resultMap.put("BZDW",secondTitle);
            resultJson = mapper.writeValueAsString(resultMap);
	        return resultJson;
	    }

     /**
      * 研发费用二级明细，每次返回一条记录
      * 
      */
		@Override
		public String getListFsDetail22(QueryBean queryBean)
				throws IOException, ParseException {
			
			ObjectMapper mapper = new ObjectMapper();
	        String resultJson = null;
	    //时间必选  
	    if((StringUtils.isNotBlank(queryBean.getDateYearMonthStart()) && StringUtils.isNotBlank(queryBean.getDateYearMonthEnd()))){
	        	
	        	//获取时间必选
	        	String startTime = queryBean.getDateYearMonthStart();
	            String endTime = queryBean.getDateYearMonthEnd();
	            
	            //公司代码
	         	List<String> compValues  = queryBean.getCompCodeValue(); 
	         	
	         	//项目编号
	         	List<String> inner_order  = queryBean.getOrderID(); 
	         	

	        
	            HashMap<String,String> paraMap = new HashMap<>();
	        	
	        	paraMap.put("start_date", startTime);
	        	paraMap.put("end_date"  , endTime);
	        	paraMap.put("bukrs"  , compValues.get(0));
	        	paraMap.put("aufnr"  , inner_order.get(0));
	        	List<MdmSecondCost> codeList= getListFsSecondCode();
				THM_FS22_02 secondDetailObj = ireportmapperfs22.getYanFaCostDetail(paraMap);
		        
				//8100000026#727.61,5000010100#677.97
				 StringBuffer sb = new StringBuffer();
				if(secondDetailObj!=null){
					
						String secondDetail = secondDetailObj.getAll_detail();
						String[] arrayDetail = secondDetail.split(",");
						Map<String,String> codeAndCost=new HashMap<String,String>();
						for (String codeCost : arrayDetail) {
							String[] arr=codeCost.split("#");
							if(arr.length==2){
								codeAndCost.put(arr[0], arr[1]);
							}
							
						}
						
					        
						   BigDecimal yanfaDN = new BigDecimal("0.00");
						 
					        for (int i = 0; i < codeList.size(); i++) {
					        		
					        		if(codeAndCost.containsKey(codeList.get(i).getId())){
						        		if(i==(codeList.size()-1)){
								        sb.append("\""+codeList.get(i).getId()+"\""+":");
								        sb.append(codeAndCost.get(codeList.get(i).getId())+"");
						        		}else{
						        	    sb.append("\""+codeList.get(i).getId()+"\""+":");
										sb.append(codeAndCost.get(codeList.get(i).getId())+",");
						        		}
						        	}else{
						        		if(i==(codeList.size()-1)){
						        			sb.append("\""+codeList.get(i).getId()+"\""+":");
									        sb.append("0.00");
						        		}else{
						        			sb.append("\""+codeList.get(i).getId()+"\""+":");
									        sb.append("0.00,");
						        		}
						        		
						        	}
					        		
					        	
						    }
					        
					       resultJson=sb.toString();
				 /**如果在当前查询时间区间内没有发生额，就没有数据，就会发生没有该条记录，所以，应该补全记录*/ 
				 }else{
					 BigDecimal yanfaDN = new BigDecimal("0.00");
					 for (int i = 0; i < codeList.size(); i++) {
		        	
			        		 if(i==(codeList.size()-1)){
			        			sb.append("\""+codeList.get(i).getId()+"\""+":");
						        sb.append("0.00");
			        		  }else{
			        			sb.append("\""+codeList.get(i).getId()+"\""+":");
						        sb.append("0.00,");
			        		  }
					        		
				        	
				        	
					    }
					   
					 resultJson=sb.toString();
				}
				
				
			        
	        }
	        
	        return resultJson;
			
		}
   
		
   /**
    * 查询二级费用项代码
    */
	@Override
	public List<MdmSecondCost> getListFsSecondCode(){
		List<String> codeList=new ArrayList<String>();
		List<MdmSecondCost> periodexpense = ireportmapperfs22.getSecondCostCode();
		return periodexpense;
	}
	
	/**
	 * 
	 */
	@Override
	public String getListFsSecondDetail22(QueryBean queryBean)
			throws IOException, ParseException {
		QueryBean qb = new QueryBean(); 
		List<THM_FS22_01> queryList = getListFsFirstDetail(queryBean);
		
		StringBuffer sb = new StringBuffer();
		   
	       sb.append("{"+"\""+"costDetail"+"\""+":");
	       sb.append("[");
		   String start_date = queryBean.getDateYearMonthStart();
		   String end_date = queryBean.getDateYearMonthEnd();
		 
	       for (int i = 0; i < queryList.size(); i++) {
	    	   
	    	   THM_FS22_01 para = queryList.get(i);
                  
				   //查询条件设置 
				   qb.setOrderID(Arrays.asList(para.getAufnr()));
				   qb.setDateYearMonthStart(start_date);
				   qb.setDateYearMonthEnd(end_date);
				   qb.setCompCodeValue(Arrays.asList(para.getBukrs()));
				   String detail= getListFsDetail22(qb);
				   
				   if(detail!=null&&!detail.equals("")){
				       sb.append("{");
				       sb.append("\""+"aufnr"+"\""+":");
				       sb.append("\""+para.getAufnr()+"\""+",");
				       
				       sb.append("\""+"ktext"+"\""+":");
				       sb.append("\""+para.getKtext()+"\""+",");
				       
				       sb.append("\""+"all_buget"+"\""+":");
				       sb.append(para.getAll_buget()+",");
				       
				       sb.append("\""+"bef_years_cost"+"\""+":");
				       sb.append(para.getBef_years_cost()+",");
				       
				       sb.append("\""+"wtjhv"+"\""+":");
				       sb.append(para.getWtjhv()+",");
				       
				       sb.append("\""+"curr_year_cost"+"\""+":");
				       sb.append(para.getCurr_year_cost()+",");
				       
				       sb.append("\""+"peroid_cost"+"\""+":");
				       sb.append(para.getPeroid_cost()+",");
				       
				       sb.append("\""+"qita"+"\""+":");
				       sb.append(para.getQita()+",");
				       
				       sb.append(detail);
				        
					   sb.append("}");
				      
				       if((i!=queryList.size()-1)){
				    	   sb.append(","); 
				       }
					   
				   }

			
		 }  
	       
	    sb.append("]");
		sb.append("}");
		
		String sbReturn=sb.toString().replace(",},]}", "}]}");
		       sbReturn=sbReturn.replace(",}]}", "}]}");
		       sbReturn=sbReturn.replace("},]}", "}]}");
		   
		return sbReturn;
	}
	
	@Override
	public List<List<Object>> getListFsSecondDetailExport(QueryBean queryBean) throws IOException, ParseException {
		QueryBean qb = new QueryBean(); 
		   List<THM_FS22_01> queryList = getListFsFirstDetail(queryBean);
		   String start_date = queryBean.getDateYearMonthStart();
		   String end_date = queryBean.getDateYearMonthEnd();
		   
		   List<List<Object>> secExp=new ArrayList<List<Object>>();
		      for (int i = 0; i < queryList.size(); i++) {
		    	   
		    	       THM_FS22_01 para = queryList.get(i);
					   //查询条件设置 
					   qb.setOrderID(Arrays.asList(para.getAufnr()));
					   qb.setDateYearMonthStart(start_date);
					   qb.setDateYearMonthEnd(end_date);
					   qb.setCompCodeValue(Arrays.asList(para.getBukrs()));
					   String detail= getListFsDetail22(qb);
					   
					   List<Object> item = new ArrayList<Object>();
					   if(detail!=null&&!detail.equals("")){
						   item.add(para.getAufnr());
					       item.add(para.getKtext());
					       
					       item.add(para.getAll_buget());
					       item.add(para.getBef_years_cost());
					       
					       item.add(para.getWtjhv());
					       
					       item.add(para.getCurr_year_cost());
					       
					       item.add(para.getPeroid_cost());
					       
					       
					       
					       /**
					        * 解析明细，最后一项“其他”有部分值可以显示，需要减去这些
					        * 5000769900,5000650000,5000330000,5000160212,5000160700
					        */
					       double temp=0.00;
					       String[] arr=detail.split(",");
					       String qt="\"5000769900\",\"5000650000\",\"5000330000\",\"5000160212\",\"5000160700\"";
					       for (String strDetail : arr) {
							    String[] strValue=strDetail.split(":");
							    item.add(strValue[1]);
							    if(qt.contains(strValue[0])){
							    	temp+=Double.valueOf(strValue[1]);
							    }
						    }
					       
					       item.add(Double.valueOf(para.getQita()-temp));
					       secExp.add(item);
					   }
					   

			 }  
		     
		  return secExp;
	}
	/**
	 * 
	 */
	@Override
	public List<List<Object>> exportFS22(int flag, QueryBean queryBean) {
		List<List<Object>> exportList = new ArrayList<List<Object>>();
		if(flag==1){
			try {
				List<THM_FS22_01> list = getListFsFirstDetail(queryBean);
				//添加合计
				list.add((THM_FS22_01)sumFirstAll(list,2));
				for (THM_FS22_01 thm : list) {
					List<Object> oneRecord = new ArrayList<Object>();
					oneRecord.add(thm.getAufnr());
					oneRecord.add(thm.getKtext());
					oneRecord.add(thm.getAll_buget());
					oneRecord.add(thm.getBef_years_cost());
					oneRecord.add(thm.getWtjhv());
					oneRecord.add(thm.getCurr_year_cost());
					oneRecord.add(thm.getPeroid_cost());
					oneRecord.add(thm.getCailiao());
					oneRecord.add(thm.getRanliao());
					oneRecord.add(thm.getDongli());
					oneRecord.add(thm.getRengong());
					oneRecord.add(thm.getZhejiu());
					oneRecord.add(thm.getWuxingzichan());
					oneRecord.add(thm.getGongju());
					oneRecord.add(thm.getShiyan());
					oneRecord.add(thm.getJianding());
					oneRecord.add(thm.getZhuanli());
					oneRecord.add(thm.getJishu());
					oneRecord.add(thm.getZhuanrang());
					oneRecord.add(thm.getZiliao());
					oneRecord.add(thm.getChailv());
					oneRecord.add(thm.getBangong());
					oneRecord.add(thm.getXiuli());
					oneRecord.add(thm.getQita());
					
					exportList.add(oneRecord);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				List<List<Object>> secExp = getListFsSecondDetailExport(queryBean);
				secExp.add((List<Object>)sumSecondAll(secExp,2));
				exportList=secExp;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		}
		return exportList;
	}
	@Override
	public String getInnerOrder(String aufnr) {
		 HashMap<String,String> paraMap = new HashMap<>();
 	     paraMap.put("aufnr", aufnr);
 	     
 	     List<String> innerOrder = ireportmapperfs22.getInnerOrder(paraMap);
 	     
 	    StringBuffer sb = new StringBuffer();
		   
	       sb.append("[");
	       for (int i = 0; i < innerOrder.size(); i++) {
	    	       sb.append("\"");
			       sb.append(innerOrder.get(i));
			       sb.append("\"");
			       if((i!=innerOrder.size()-1)){
			    	   sb.append(","); 
			       }
			
		 }
	       
		    sb.append("]");
	
		return sb.toString();
	}
	
	@Override
	public ExcelExportUtil22 exportExcel(QueryBean queryBean, int flag) {
		String title="研发费用明细表";
    	
    	String secondTitle="编制单位:-";
    	//不分页
    	queryBean.setStartitem(-1);
    	/**
    	 * 编制单位
    	 */
    	List<String> compValues  = queryBean.getCompCodeValue();
    	secondTitle=getComName(compValues);
          
       List<String> headerList2 = Lists.newArrayList();
       List<String> headerList1 = Lists.newArrayList();
       List<MdmSecondCost> codeList=getListFsSecondCode();
       headerList2.add("项目号");
       headerList2.add("项目描述");
       headerList2.add("项目总预算");
       headerList2.add("以前年度累计发生额");
       headerList2.add("当年项目预算");
       headerList2.add("当年累计发生额");
       headerList2.add("当期发生额");
           
           
           /**
            * 
            * 一级明细导出
            */
           if(flag==1){
               headerList1.add("研发项目");
               for (int i = 0; i < 6; i++) {
               	headerList1.add("");
       		   }
			    headerList1.add("材料费");
			    headerList1.add("燃料费");
			    headerList1.add("动力费用");
			    headerList1.add("人工工资");
			    headerList1.add("折旧费");
			    headerList1.add("无形资产摊销");
			    headerList1.add("工卡模具费");
			    headerList1.add("试验检验费"); 
			    headerList1.add("研发成果论证、鉴定、评审、验收费用");
			    headerList1.add("专利费");
			    headerList1.add("技术开发费");
			    headerList1.add("技术转让费");
			    headerList1.add("技术资料费用");
			    headerList1.add("差旅费");
			    headerList1.add("办公费");
			    headerList1.add("修理费");
			    headerList1.add("其他");
               
                for (int i = 0; i < headerList1.size()-8; i++) {
            	   headerList2.add("");
			   }
           }else{
			   if(codeList!=null){
		    	  for (MdmSecondCost mdm : codeList) {
		        		if(mdm.getColumn_ex()==19){
		        			continue;
		        		}else if(mdm.getColumn_ex()==20){
		        		 headerList2.add("研发动能分摊");
		        		}
		               //二级明细项
		        	   headerList2.add(mdm.getDescription());
				 }
			  }
			 //二级列表末尾，其他
			 if(flag==2){
				 headerList2.add("成本费用-其他");   
			 }
		    
		    headerList1.add("研发项目");
		    for (int i = 0; i < 6; i++) {
		    	headerList1.add("");
			}
		    headerList1.add("材料费");
		    for (int i = 0; i < 9; i++) {
		    	headerList1.add("");
			}
		    headerList1.add("燃料费");
		    headerList1.add("动力费用");
		    for (int i = 0; i < 5; i++) {
		    	headerList1.add("");
			}
		    headerList1.add("人工工资");
		    headerList1.add("折旧费");
		    for (int i = 0; i < 2; i++) {
		    	headerList1.add("");
			}
		    headerList1.add("无形资产摊销");
		    headerList1.add("");
		    headerList1.add("工卡模具费");
		    for (int i = 0; i < 2; i++) {
		    	headerList1.add("");
			}
		    headerList1.add("试验检验费"); 
		    headerList1.add("研发成果论证、鉴定、评审、验收费用");
		    headerList1.add("专利费");
		    headerList1.add("技术开发费");
		    headerList1.add("技术转让费");
		    headerList1.add("技术资料费用");
		    headerList1.add("");
		    headerList1.add("差旅费");
		    headerList1.add("");
		    headerList1.add("办公费");
		    for (int i = 0; i < 9; i++) {
		    	headerList1.add("");
			}
		    headerList1.add("修理费");
		    for (int i = 0; i < 8; i++) {
		    	headerList1.add("");
			}
		    headerList1.add("其他");
		    for (int i = 0; i < 5; i++) {
		    	headerList1.add("");
			}
       }
        secondTitle=secondTitle+"###"+queryBean.getDateYearMonthStart()+"-"+queryBean.getDateYearMonthEnd();
        
        List<List<Object>> dataList = exportFS22(flag, queryBean);

        ExcelExportUtil22 ee = new ExcelExportUtil22(title, secondTitle, headerList1,headerList2,flag);

        ee=ee.getFs24ExcelReport(dataList, ee);
		return ee;
	}
	
	
	/**
	 * 数据导入
	 * @throws InvalidFormatException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Override
	public Object importData(String filePath,List<String> codeList) throws JsonProcessingException,
			IOException, InvalidFormatException, InstantiationException,
			IllegalAccessException {
		
	        String yearMonth = null;
	        Matcher matcher = null;
	        String json = null;
	        Map<String, Object> listMap = new HashMap<String, Object>();
	        ObjectMapper mapper = new ObjectMapper();
	        List<String> headerList = Lists.newArrayList();
	        headerList.add("项目号");
	        headerList.add("项目描述");
	        headerList.add("项目总预算");
	        headerList.add("以前年度累计发生额");
	        headerList.add("当年项目预算");
	        //导入数据
	        ExcelImportUtil ei = new ExcelImportUtil(filePath, 3);
	        String time = ei.getCellValue(1,1).toString();
	        String startdate = "";
	        String enddate="";
	        
	        if (time==null||time.trim().equals("")) {
	            listMap.put("ERRORTIME", "日期填写错误!");
	            json = mapper.writeValueAsString(listMap);
	            return json;
	        }else{
	        	String[] array = time.split("-");
	        	if(array.length!=2){
	        		  listMap.put("ERRORTIME", "日期填写错误!");
	                  json = mapper.writeValueAsString(listMap);
	                  return json;
	        	}else{
	        		startdate=array[0];
	        		enddate=array[1];
	        	}
	        	
	        }
	        
	        //如果验证数据没有问题，则返回所有的数据，标志位为1，若是数据有问题，则返回不正确数据，标准为0
	        List<InsertFieldTHM_FS22> list = ei.getDataList(InsertFieldTHM_FS22.class, "FS22");
	        if ("ERROR".equals(list.get(list.size() - 1))) {
	            for (int i = 0; i < list.size() - 1; i++) {
	                listMap.put("ERROR" + i, list.get(i));
	            }
	            //如果返回的结果存在不合理的数据，那么将数据结果以json格式返回
	            Map<String, Object> map = new HashMap<String, Object>();
	            map.put("ERROR", listMap);
	            if (listMap.size() > 0) {
	                json = mapper.writeValueAsString(map);
	                return json;
	            }
	        }
	        
	        HashMap<String,String> map = null;
	        List<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
	        for (InsertFieldTHM_FS22 insert: list) {
	          map = new HashMap<String,String>();
	          map.put("aufnr",insert.getAufnr());
	          map.put("impdate",enddate);
	          map.put("curr_year_buget",insert.getWtjhv()+"");
	          map.put("before_year_cost",insert.getBef_years_cost()+"");
	          /**
	           * 判断当前项目对应的公司代码是否在该登录用户的权限之内，如果不在，返回错误提示
	           */
	          String bukrs = ireportmapperfs22.getAuth(map);
	          if(codeList.indexOf(bukrs)==-1&&bukrs!=null&&!insert.getAufnr().equals("合计")){
	        	listMap.put("ERROR", "项目号 ："+insert.getAufnr()+" 没有导入权限！");
	  	        json = mapper.writeValueAsString(listMap);
	  	        return json;
	          }else{
	        	  data.add(map);
	        	  
	          }
	          	
			}
	        for (HashMap<String, String> hash : data) {
	        	ireportmapperfs22.saveOrUpdateData(hash);
			}
	        
	        listMap.put("SUCCESSES", "导入成功!");
	        json = mapper.writeValueAsString(listMap);
	        return json;
		
		
	}
	@Override
	public Object sumFirstAll(List<THM_FS22_01> list,int flag) {  
		ObjectMapper mapper = new ObjectMapper();
        String resultJson = "";
        
	   THM_FS22_01 sum = new THM_FS22_01();
	   sum.setAufnr("合计");
	   sum.setKtext("-");
	   double all_buget=0.00;
	   double bef_years_cost=0.00;
	   double wtjhv=0.00;
	   double peroid_cost=0.00;
	   double curr_year_cost=0.00;
	   double cailiao=0.00;
	   double ranliao=0.00;
	   double dongli=0.00;
	   double rengong=0.00;
	   double zhejiu=0.00;
	   double wuxingzichan=0.00;
	   double gongju=0.00;
	   double shiyan=0.00;
	   double jianding=0.00;
	   double zhuanli=0.00;
	   double jishu=0.00;
	   double zhuanrang=0.00;
	   double ziliao=0.00;
	   double chailv=0.00;
	   double bangong=0.00;
	   double xiuli=0.00;
	   double qita=0.00;
		
	   for (THM_FS22_01 thm: list) {
		   all_buget=all_buget+thm.getAll_buget();
		   bef_years_cost=bef_years_cost+thm.getBef_years_cost();
		   wtjhv=wtjhv+thm.getWtjhv();
		   peroid_cost=peroid_cost+thm.getPeroid_cost();
		   curr_year_cost=curr_year_cost+thm.getCurr_year_cost();
		   cailiao=cailiao+thm.getCailiao();
		   ranliao=ranliao+thm.getRanliao();
		   dongli=dongli+thm.getDongli();
		   rengong=rengong+thm.getRengong();
		   zhejiu=zhejiu+thm.getZhejiu();
		   wuxingzichan=wuxingzichan+thm.getWuxingzichan();
		   gongju=gongju+thm.getGongju();
		   shiyan=shiyan+thm.getShiyan();
		   jianding=jianding+thm.getJianding();
		   zhuanli=zhuanli+thm.getZhuanli();
		   jishu=jishu+thm.getJishu();
		   zhuanrang=zhuanrang+thm.getZhuanrang();
		   ziliao=ziliao+thm.getZiliao();
		   chailv=chailv+thm.getChailv();
		   bangong=bangong+thm.getBangong();
		   xiuli=xiuli+thm.getXiuli();
		   qita=qita+thm.getQita();
	   }
	   sum.setAll_buget(all_buget);
	   sum.setBef_years_cost(bef_years_cost);
	   sum.setWtjhv(wtjhv);
	   sum.setPeroid_cost(peroid_cost);
	   sum.setCurr_year_cost(curr_year_cost);
	   sum.setCailiao(cailiao);
	   sum.setRanliao(ranliao);
	   sum.setDongli(dongli);
	   sum.setRengong(rengong);
	   sum.setZhejiu(zhejiu);
	   sum.setWuxingzichan(wuxingzichan);
	   sum.setGongju(gongju);
	   sum.setShiyan(shiyan);
	   sum.setJianding(jianding);
	   sum.setZhuanli(zhuanli);
	   sum.setJishu(jishu);
	   sum.setZhuanrang(zhuanrang);
	   sum.setZiliao(ziliao);
	   sum.setChailv(chailv);
	   sum.setBangong(bangong);
	   sum.setXiuli(xiuli);
	   sum.setQita(qita);
	   
	   try {
		resultJson = mapper.writeValueAsString(sum);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   if(flag==1){
		   return resultJson;
	   }else{
		   return sum;
	   }
		
	}
	@Override
	public Object  sumSecondAll(List<List<Object>> list,int flag) {
		ObjectMapper mapper = new ObjectMapper();
        String resultJson = null;
		//初始化一个map用于累加
		Map<String,Double> map = new LinkedHashMap<String,Double>();
		
		List<Object> listre = new ArrayList<Object>();
		
		int size=0;
		if(list!=null&&list.size()>0){
			List<Object> objList = list.get(0);
			size = objList.size();
			for (int i = 2; i < objList.size(); i++) {
				map.put(i+"",0.00);
			}
		}
		/**
		 * 累加
		 */
		if(map.size()>0){
			 for (List<Object> thm2 : list) {
				for (int i = 2; i < thm2.size(); i++) {
					
					double val=map.get(i+"");
					double newVal=val+Double.valueOf(thm2.get(i).toString());
					map.put(i+"",newVal);
			  }
			
		  }
		
	  }
	  try {
		resultJson = 	mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	  
	  if(flag==1){
		  return resultJson; 
	  }else{
		  listre.add("合计");
		  listre.add("-");
		  
		 
		  for (int i = 0; i < map.size(); i++) {
			  
			  listre.add(map.get((i+2)+""));
		  }
		  
		  return listre;
	  }
	  
  }
	
	
	
	@Override
	public String returnSum(QueryBean queryBean, int flag) {
		queryBean.setStartitem(-1);
		if(flag==1){
			try {
				return (String)sumFirstAll(getListFsFirstDetail(queryBean),1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			
			try {
				return (String)sumSecondAll(getListFsSecondDetailExport(queryBean),1);
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return "";
	}
	@Override
	public String getComName(List<String> compValues) {
		String secondTitle="编制单位:-";
    	List<MdmTcompCode> compCodeList= genericMapper.getAllTcompCode("THM_FS22_01",null,null);
   	    
    	
    	if(compValues==null||(compValues!=null&&compValues.size()==0)||(compValues.size()==compCodeList.size())){
    		   secondTitle="编制单位:中车永济电机有限公司合计";
    	}else if(compValues.size()>1&&compValues.size()<compCodeList.size()){
    		   secondTitle="编制单位:-";
    	}else if(compValues.size()==1){
        	List<MdmTcompCode> compCodeListOne= 
        			genericMapper.getAllTcompCode("THM_FS22_01",compValues.get(0),null);
        	if(compCodeListOne!=null&&compCodeListOne.size()>0){
        	   secondTitle="编制单位:"+compCodeListOne.get(0).getTxtmd();
        	}
    		
    	}
		return secondTitle;
	}

}
