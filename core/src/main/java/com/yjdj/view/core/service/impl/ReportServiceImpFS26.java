package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yjdj.view.core.entity.mybeans.InsertFieldTHM_FS24;
import com.yjdj.view.core.entity.mybeans.MdmTcompCode;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS24_01;
import com.yjdj.view.core.entity.mybeans.THM_FS24_Result;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.mapper.GenericMapper;
import com.yjdj.view.core.mapper.IReportMapperFS26;
import com.yjdj.view.core.service.IReportServiceFS26;
import com.yjdj.view.core.util.ExcelExportUtil24;
import com.yjdj.view.core.util.ExcelImportUtil;
import com.yjdj.view.core.util.StringUtil;
/**
 * create on 2016/12/07
 * @author liuchengli
 *
 */

@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS26 implements IReportServiceFS26{

	private final Logger log = Logger.getLogger(ReportServiceImpFS26.class);

	
    @Autowired
    private IReportMapperFS26 iReportMapperFS26;
    
    @Autowired
    private GenericMapper genericMapper;
    
	/**
	 * 比较器，排序用
	 */
	private Comparator<THM_FS24_01> compare1 = new Comparator<THM_FS24_01>() {
		@Override
		public int compare(THM_FS24_01 th1, THM_FS24_01 th2) {
			// TODO Auto-generated method stub
			return th1.getId()-th2.getId();
		}
	};
	
	/**
     * 重新排序，对与一级标题，其没有公司代码，排在前面
     * 
     */
 
	private Comparator<THM_FS24_01> compareBurks = new Comparator<THM_FS24_01>() {
		@Override
		public int compare(THM_FS24_01 th1, THM_FS24_01 th2) {
			
			if(th1.getId()==th2.getId()){
				try {
					if(th1.getRbukrs()==null&&th2.getRbukrs()!=null){
						return -1;
					  }else if(th1.getRbukrs()!=null&&th2.getRbukrs()==null){
						return 1;
					}else{
						return Integer.valueOf(th1.getRbukrs())-Integer.valueOf(th2.getRbukrs());
					}
					
				} catch (Exception e) {
					return 1;
				}
				
			}else{
				return th1.getId()-th2.getId();	
			}
			
		}
	};

	@Override
	public void init() {
    	Date dt = new Date();
    	String date = new SimpleDateFormat("yyyy-MM-dd").format(dt);
    	HashMap<String,String> map = new HashMap<String,String>();
    	map.put("start_date",date);
		iReportMapperFS26.init(map);	
	}
	
	
	@Override
	public List<THM_FS24_01> listMerge(List<THM_FS24_01> list) {
		if(list!=null&&list.size()>0){
			
			
			THM_FS24_01 th7=add(returnSubList(list, 8, 10),7,"销售服务费","*307****");
			list.add(th7);
			THM_FS24_01 th11=add(returnSubList(list, 12, 13),11,"预计产品质量损失","*308****");
			list.add(th11);
			
			THM_FS24_01 th29=add(returnSubList(list, 30, 31),29,"劳动保护费","*342****");
			list.add(th29);
			/**14 包含29的合计*/
			THM_FS24_01 th14=add(returnSubList(list, 15, 29),14,"职工薪酬","-");
			list.add(th14);
			THM_FS24_01 th34=add(returnSubList(list, 35, 36),34,"差旅费","-");
			list.add(th34);
			THM_FS24_01 th37=add(returnSubList(list, 38, 39),37,"办公费","*310****");
			list.add(th37);
			
	    	list.sort(compare1);
	    	
	    	/**
	    	 * 合计 1+2+3+4+5+6+7+11+14+32+33+34+37+40+41
	    	 */
	    	List<THM_FS24_01> sublist43=returnSubList(list, 43, 43);
	    	
	 

	    	THM_FS24_01 th43 = add(sublist43,43,"合计","-");
	    	list.add(th43);
	    	list.sort(compare1);
	    	return list;
		}
		return null;
	}
    
    
	/**
	 * 一级明细查询
	 */
	@Override
	public List<THM_FS24_01> getFirstDetail(QueryBean queryBean) {
		init();
		if((StringUtils.isNotBlank(queryBean.getDateYearMonthStart()) 
			&& StringUtils.isNotBlank(queryBean.getDateYearMonthEnd()))){
        	//获取时间必选
        	String startTime = queryBean.getDateYearMonthStart();
            String endTime = queryBean.getDateYearMonthEnd();
            
            //时间切分年份
            String curr_year = startTime.substring(0,4);
            String last_year = String .valueOf((Integer.valueOf(curr_year)-1));
            
            //时间切分月份，计算本期计划
            String month = endTime.substring(4,6);
            
            
            //区间类型
            int r=0;
            //完整季度
            String jiduTime=null;
            //系数
            int adj=1;
            
            /**
             * 调整区间
             */
            String adjStart=null;
            String adjEnd=null;
            
            /**
             * 计算本年计划时,使用当前月份的季度月份
             */
            String yearPlanEnd=null;
            
            /**
             * 计算本年计划时，出现有本年计划，二没有本期发生额，而导致没有查出该条数据
             */
            String yplan=null;
            
            /**
             * 按季度切分，1,2两个月单独算，它们没有季度计算所以 planEndTime=null,
             * 如果是完整的季度查询，则不切分
             */
            if(month.equals("01")||month.equals("04")||month.equals("07")||month.equals("10")){
            	r=1;
            	if(month.equals("01")){
            		jiduTime="01";
            		adjStart="01";
            		adjEnd="01";
            		yearPlanEnd=curr_year+"03";
            		yplan=yearPlanEnd;
            		r=1;
            		adj=1;
            	} else if(month.equals("04")){
            		jiduTime="03";
            		adjStart="04";
            		adjEnd="04";
            		yearPlanEnd=curr_year+"06";
            		yplan=yearPlanEnd;
            		r=2;
            		adj=1;
            	}else if(month.equals("07")){
            		jiduTime="06";
            		adjStart="07";
            		adjEnd="07";
            		yearPlanEnd=curr_year+"09";
            		yplan=yearPlanEnd;
            		r=2;
            		adj=1;
            	}else if(month.equals("10")){
            		jiduTime="09";
            		adjStart="10";
            		adjEnd="10";
            		yearPlanEnd=curr_year+"12";
            		yplan=yearPlanEnd;
            		r=2;
            		adj=1;
            	}
            }else if(month.equals("02")||month.equals("05")||month.equals("08")||month.equals("11")){

            	if(month.equals("02")){
            		jiduTime="02";
            		adjStart="01";
            		adjEnd="02";
            		yearPlanEnd=curr_year+"03";
            		yplan=yearPlanEnd;
            		r=1;
            		adj=2;
            	}else if(month.equals("05")){
            		jiduTime="03";
            		adjStart="04";
            		adjEnd="05";
            		yearPlanEnd=curr_year+"06";
            		yplan=yearPlanEnd;
            		r=2;
            		adj=2;
            	}else if(month.equals("08")){
            		jiduTime="06";
            		adjStart="07";
            		adjEnd="08";
            		yearPlanEnd=curr_year+"09";
            		yplan=yearPlanEnd;
            		r=2;
            		adj=2;
            	}else if(month.equals("11")){
            		jiduTime="09";
            		adjStart="10";
            		adjEnd="11";
            		yearPlanEnd=curr_year+"12";
            		yplan=yearPlanEnd;
            		r=2;
            		adj=2;
            	}
            	
            }else{
        		jiduTime=month;
        		yearPlanEnd=curr_year+month;
        	}
            
            //公司代码
         	List<String> compValues  = queryBean.getCompCodeValue(); 
         	//查询分页起始位置
         	int offset = queryBean.getStartitem();
         	int pageitem = queryBean.getPageitem();
         	
            //多值 转换成字符串  ,号隔开
            String p_bukrs =(compValues==null||compValues.size()==0)?"" :compValues.toString().replace("[", "\'").replace("]", "\'").replace(", ",",");
            
            
            HashMap<String,String> paraMap = new HashMap<>();
            
        	paraMap.put("start_date", startTime);
        	paraMap.put("end_date"  , endTime);
        	paraMap.put("curr_year"  , curr_year);
        	paraMap.put("last_year"  , last_year);
        	paraMap.put("compCodeValue", p_bukrs);
        	
    		paraMap.put("jiduTime", jiduTime);
	        paraMap.put("r",r+"");
	        paraMap.put("adj",adj+"");
	        paraMap.put("adjStart",adjStart);
	        paraMap.put("adjEnd",adjEnd);
	        paraMap.put("yearPlanEnd",yearPlanEnd);
	        paraMap.put("yplan",yplan);
	        
        	
        	List<THM_FS24_01> list =  iReportMapperFS26.getFirstDetail(paraMap);
        	
        	list=listMerge(list);
 	        return list;
			
		}
		
		return null;
	}
    /**
     * 二级明细查询
     */
	@Override
	public List<THM_FS24_01> getSecondDetail(QueryBean queryBean) {
		  init();
		  ObjectMapper mapper = new ObjectMapper();
	        String resultJson = "";
			if((StringUtils.isNotBlank(queryBean.getDateYearMonthStart()) 
				&& StringUtils.isNotBlank(queryBean.getDateYearMonthEnd()))){
	        	//获取时间必选
	        	String startTime = queryBean.getDateYearMonthStart();
	            String endTime = queryBean.getDateYearMonthEnd();
	            
	            //时间切分年份
	            String curr_year = startTime.substring(0,4);
	            String last_year = String .valueOf((Integer.valueOf(curr_year)-1));
	            
	            //时间切分月份，计算本期计划
	            String month = endTime.substring(4,6);
	            
	            
	            //区间类型
	            int r=0;
	            //完整季度
	            String jiduTime=null;
	            //系数
	            int adj=1;
	            /**
	             * 调整区间
	             */
	            String adjStart=null;
	            String adjEnd=null;
	            
	            /**
	             * 计算本年计划时,使用当前月份的季度月份
	             */
	            String yearPlanEnd=null;
	            
	            /**
	             * 计算本年计划时，出现有本年计划，二没有本期发生额，而导致没有查出该条数据
	             */
	            String yplan=null;
	            
	            /**
	             * 按季度切分，1,2两个月单独算，它们没有季度计算所以 planEndTime=null,
	             * 如果是完整的季度查询，则不切分
	             */
	            if(month.equals("01")||month.equals("04")||month.equals("07")||month.equals("10")){
	            	r=1;
	            	if(month.equals("01")){
	            		jiduTime="01";
	            		adjStart="01";
	            		adjEnd="01";
	            		yearPlanEnd=curr_year+"03";
	            		yplan=yearPlanEnd;
	            		r=1;
	            		adj=1;
	            	} else if(month.equals("04")){
	            		jiduTime="03";
	            		adjStart="04";
	            		adjEnd="04";
	            		yearPlanEnd=curr_year+"06";
	            		yplan=yearPlanEnd;
	            		r=2;
	            		adj=1;
	            	}else if(month.equals("07")){
	            		jiduTime="06";
	            		adjStart="07";
	            		adjEnd="07";
	            		yearPlanEnd=curr_year+"09";
	            		yplan=yearPlanEnd;
	            		r=2;
	            		adj=1;
	            	}else if(month.equals("10")){
	            		jiduTime="09";
	            		adjStart="10";
	            		adjEnd="10";
	            		yearPlanEnd=curr_year+"12";
	            		yplan=yearPlanEnd;
	            		r=2;
	            		adj=1;
	            	}
	            }else if(month.equals("02")||month.equals("05")||month.equals("08")||month.equals("11")){

	            	if(month.equals("02")){
	            		jiduTime="02";
	            		adjStart="01";
	            		adjEnd="02";
	            		yearPlanEnd=curr_year+"03";
	            		yplan=yearPlanEnd;
	            		r=1;
	            		adj=2;
	            	}else if(month.equals("05")){
	            		jiduTime="03";
	            		adjStart="04";
	            		adjEnd="05";
	            		yearPlanEnd=curr_year+"06";
	            		yplan=yearPlanEnd;
	            		r=2;
	            		adj=2;
	            	}else if(month.equals("08")){
	            		jiduTime="06";
	            		adjStart="07";
	            		adjEnd="08";
	            		yearPlanEnd=curr_year+"09";
	            		yplan=yearPlanEnd;
	            		r=2;
	            		adj=2;
	            	}else if(month.equals("11")){
	            		jiduTime="09";
	            		adjStart="10";
	            		adjEnd="11";
	            		yearPlanEnd=curr_year+"12";
	            		yplan=yearPlanEnd;
	            		r=2;
	            		adj=2;
	            	}
	            	
	            }else{
	        		jiduTime=month;
	        		yearPlanEnd=curr_year+month;
	        	}
	            
	            //公司代码
	         	List<String> compValues  = queryBean.getCompCodeValue(); 
	         	//查询分页起始位置
	         	int offset = queryBean.getStartitem();
	         	int pageitem = queryBean.getPageitem();
	         	
	            //多值 转换成字符串  ,号隔开
	            String p_bukrs =(compValues==null||compValues.size()==0)?"" :compValues.toString().replace("[", "\'").replace("]", "\'").replace(", ",",");
	            
	            
	            HashMap<String,String> paraMap = new HashMap<>();
	            
	        	paraMap.put("start_date", startTime);
	        	paraMap.put("end_date"  , endTime);
	        	paraMap.put("curr_year"  , curr_year);
	        	paraMap.put("last_year"  , last_year);
	        	paraMap.put("compCodeValue", p_bukrs);
	        	
	    		paraMap.put("jiduTime", jiduTime);
		        paraMap.put("r",r+"");
		        paraMap.put("adj",adj+"");
		        paraMap.put("adjStart",adjStart);
		        paraMap.put("adjEnd",adjEnd);
		        paraMap.put("yearPlanEnd",yearPlanEnd);
		        paraMap.put("yplan",yplan);
		        
	        	List<THM_FS24_01> list =  iReportMapperFS26.getSecondDetail(paraMap);
	        	

	        	list=listMerge(list);
	        	
	        	/**
	        	 * 
	        	 * 将每一个一级类型的下的按照公司代码区分的二级明细解析出来
	        	 * */ 
	        
	        	list.addAll(generateSecondDetail(list));
	        	
				list.sort(compareBurks);
	 	        return list;
	 	        
			}
			
			return null;
	}
	
	 /**
     * 返回合计项的所有子项
     */
	@Override
	public List<THM_FS24_01> returnSubList(List<THM_FS24_01> list, int startId,
			int endId) {
		List<THM_FS24_01> sub = new ArrayList<THM_FS24_01>();
		if(startId!=43){
			for (THM_FS24_01 thm : list) {
				if(thm.getId()>=startId&&thm.getId()<=endId){
					sub.add(thm);
				}
			}
			
		}else{
			 new ArrayList<Integer>();
			 List<Integer> th43 = Arrays.asList(1,2,3,4,5,6,7,11,14,32,33,34,37,40,41,42);
			 for (THM_FS24_01 thm : list) {
				if(th43.indexOf(thm.getId())!=-1){
					sub.add(thm);
				}
			}
			
		}

		return sub;
	}

	@Override
	public THM_FS24_01 add(List<THM_FS24_01> list,int id,String name,String racct) {
		THM_FS24_01 th = new THM_FS24_01();
		
		BigDecimal last_year_cost = new BigDecimal(0.00);
		BigDecimal year_plan = new BigDecimal(0.00);
		BigDecimal period_plan = new BigDecimal(0.00);
		BigDecimal period_cost = new BigDecimal(0.00);
		BigDecimal exe_rate = new BigDecimal(0.00);
        /**
         * 会计科目没有计划值和执行率
         */
		if(id==14||id==34){
			for (THM_FS24_01 thm : list) {
				last_year_cost=last_year_cost.add(new BigDecimal(thm.getLast_year_cost()));
				year_plan=year_plan.add(new BigDecimal(thm.getYear_plan()));
				period_plan=period_plan.add(new BigDecimal(thm.getPeriod_plan()));
				period_cost=period_cost.add(new BigDecimal(thm.getPeriod_cost()));
			}
			th.setId(id);
			th.setItem_column(name);
			th.setInner_order(" ");
			th.setTxtmd(name);
			th.setLast_year_cost(last_year_cost.doubleValue());
			th.setYear_plan(year_plan.doubleValue());
			th.setPeriod_cost(period_cost.doubleValue());
			th.setPeriod_plan(period_plan.doubleValue());
		}else if(id==43){
			th.setId(id);
			th.setItem_column(name);
			th.setInner_order("-");
			th.setTxtmd("-");
			/**
			 * 计算总的执行率时，排除会计科目的数据
			 */
			BigDecimal exe_year_plan = new BigDecimal(0.00);
			BigDecimal exe_peroid_cost = new BigDecimal(0.00);
			for (THM_FS24_01 thm : list) {
				last_year_cost=last_year_cost.add(new BigDecimal(thm.getLast_year_cost()));
				year_plan=year_plan.add(new BigDecimal(thm.getYear_plan()));
				period_plan=period_plan.add(new BigDecimal(thm.getPeriod_plan()));
				period_cost=period_cost.add(new BigDecimal(thm.getPeriod_cost()));
				/**
				 * 14 是会计科目各自的总计项
				 */
				if(thm.getId()!=14&&thm.getId()!=33&&thm.getId()!=34&&thm.getId()!=40){
					exe_year_plan=exe_year_plan.add(new BigDecimal(thm.getYear_plan()));
					exe_peroid_cost=exe_peroid_cost.add(new BigDecimal(thm.getPeriod_cost()));
				}
				
			}
			th.setLast_year_cost(last_year_cost.doubleValue());
			th.setYear_plan(year_plan.doubleValue());
			th.setPeriod_plan(period_plan.doubleValue());
			th.setPeriod_cost(period_cost.doubleValue());
			
			if(!exe_year_plan.toString().equals("0")){
				exe_rate= new BigDecimal(exe_peroid_cost.doubleValue()/exe_year_plan.doubleValue());
			}
			th.setExe_rate(exe_rate.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()); 
			
		}else{
			for (THM_FS24_01 thm : list) {
				last_year_cost=last_year_cost.add(new BigDecimal(thm.getLast_year_cost()));
				year_plan=year_plan.add(new BigDecimal(thm.getYear_plan()));
				period_plan=period_plan.add(new BigDecimal(thm.getPeriod_plan()));
				period_cost=period_cost.add(new BigDecimal(thm.getPeriod_cost()));
			}
			th.setId(id);
			th.setItem_column(name);
			th.setInner_order(" ");
			th.setRacct(racct);
			th.setTxtmd(name);
			th.setLast_year_cost(last_year_cost.doubleValue());
			th.setYear_plan(year_plan.doubleValue());
			th.setPeriod_plan(period_plan.doubleValue());
			th.setPeriod_cost(period_cost.doubleValue());
			if(!year_plan.toString().equals("0")){
				exe_rate= new BigDecimal(period_cost.doubleValue()/year_plan.doubleValue());
			}
			th.setExe_rate(exe_rate.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()); 
			
		}
		return th;
	}
   
	/**
	 * 1,34,57 这些行次没有公司明细
	 * 
	 *   GROUP_CONCAT(concat(dt.rbukrs,'#',IFNULL(dt.TXTMD,' '),
		 '#',IFNULL(CAST(dt.balance as char),' '),
		 '#', IFNULL(CAST(dt.wtjhv_y as char),' '),
		 '#',IFNULL(CAST(dt.wtjhv_p as char),' '),
		 '#', IFNULL(CAST(dt.balance_p as char),' '),
		 '#',IFNULL(CAST(dt.ratio as char), ' '))) 
		 as detail
	 *  解析detail
	 * 
	 */
	@Override
	public List<THM_FS24_01> generateSecondDetail(List<THM_FS24_01> list) {
		List<THM_FS24_01> burksList = new ArrayList<THM_FS24_01>(); 
		for (THM_FS24_01 thm: list) {
		  if(thm.getId()!=14&&thm.getId()!=34&&thm.getId()!=43){
				String details = thm.getDetail();
			   if(details!=null&&!details.equals("")){
					String[] bukrsDetail = details.split(",");
					String racct = thm.getRacct();
					int id = thm.getId();
				
					for (String bd : bukrsDetail) {
						THM_FS24_01 burks = new THM_FS24_01();
						String[] bdArr=bd.split("#");
						if(bdArr.length==8){
							burks.setId(id);
							burks.setRacct(racct);
							/**公司代码，第一列描述时用公司代码描述，rbukrs**/
							burks.setItem_column(bdArr[0]);
							/**公司代码，用于比较**/
							burks.setRbukrs(bdArr[0]);
							/**描述,TXTMD**/
							burks.setTxtmd(bdArr[1]);
							/**内部订单号**/
							burks.setInner_order(bdArr[2]);
							
							/**上年发生额**/
							burks.setLast_year_cost(Double.valueOf(bdArr[3]));
						    /**本年计划**/
							burks.setYear_plan(Double.valueOf(bdArr[4]));
							/**本期计划**/
							burks.setPeriod_plan(Double.valueOf(bdArr[5]));
							/**本期发生额**/
							burks.setPeriod_cost(Double.valueOf(bdArr[6]));
							/**执行率**/
							burks.setExe_rate(Double.valueOf(bdArr[7]));
							burksList.add(burks);
							
						}

				    }
			
			   }
		   }
		}
		
		return burksList;
	}
    
	
	
	
	/**
	 * 查询导入表的明细数据
	 */
	@Override
	public List<THM_FS24_01> getDetailImp(QueryBean queryBean,int flag) {
		
				if(flag==1){
					return getImpFirst(queryBean);
				}else{
					return getImpSecond(queryBean);
				}
		
	}

	@Override
	public List<THM_FS24_Result> getFinalDetail(QueryBean queryBean, int flag) {
		
		//获取时间必选
    	String startTime = queryBean.getDateYearMonthStart();
        String endTime = queryBean.getDateYearMonthEnd();
        
        //公司代码
     	List<String> compValues  = queryBean.getCompCodeValue(); 
        //多值 转换成字符串  ,号隔开
        String p_bukrs =(compValues==null||compValues.size()==0)?"" :compValues.toString().replace("[", "\'").replace("]", "\'").replace(", ",",");
        
        HashMap<String,String> paraMap = new HashMap<>();
        
    	paraMap.put("startdate", startTime);
    	paraMap.put("enddate"  , endTime);
    	paraMap.put("compCodeValue", p_bukrs);
    	
    	int count = iReportMapperFS26.hiveOrImp(paraMap);
    	
		List<THM_FS24_01> reList=null;
		if(count>0){
			reList=getDetailImp(queryBean,flag);;
		}else{
			if(flag==1){
				reList=getFirstDetail(queryBean);	
			}else{
				reList=getSecondDetail(queryBean);
			}
		}

		return convertResult(reList);

	}

	@Override
	public List<THM_FS24_Result> convertResult(List<THM_FS24_01> list) {
		List<THM_FS24_Result> result = new ArrayList<THM_FS24_Result>();
		if(list!=null&&list.size()>0){
		 for (THM_FS24_01 thm : list) {
			THM_FS24_Result re = new THM_FS24_Result();
			re.setId(thm.getId());
			re.setItem_column(thm.getItem_column());
			re.setLast_year_cost(thm.getLast_year_cost());
			re.setPeriod_cost(thm.getPeriod_cost());
			re.setPeriod_plan(thm.getPeriod_plan());
			re.setYear_plan(thm.getYear_plan());
			re.setRacct(thm.getRacct());
			
			if(thm.getInner_order()!=null&&!thm.getInner_order().equals("")){
				re.setRacct(thm.getInner_order());
			}else{
				re.setRacct(re.getRacct());
			}
			if(thm.getTxtmd()!=null&&!thm.getTxtmd().equals("")){
				re.setTxtmd(thm.getTxtmd());
			}else{
				re.setTxtmd(thm.getItem_column());
			}
			re.setExe_rate(thm.getExe_rate());
			result.add(re);
		 }
		}else{
			
		}
		return result;
	}

	@Override
	public String returnFinalResult(QueryBean queryBean, int flag) {
		ObjectMapper mapper = new ObjectMapper();
        String resultJson = "";
		HashMap resultMap=new HashMap<>(); //结果集  转换成json格式 
        resultMap.put("list",getFinalDetail(queryBean, flag));
        String secondTitle=getComName(queryBean.getCompCodeValue());
		resultMap.put("BZDW",secondTitle);
		 try {
			resultJson = mapper.writeValueAsString(resultMap);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultJson;
	}

	@Override
	public List<List<Object>> export(QueryBean queryBean, int flag) {
		List<THM_FS24_Result> reList =  getFinalDetail(queryBean, flag);
		List<List<Object>> dataRowList = Lists.newArrayList();
		List<Object> oneRow =null;
		if(flag==1){
			for (THM_FS24_Result re : reList) {
				oneRow = new ArrayList<Object>();
				oneRow.add(re.getItem_column());
				oneRow.add(re.getId());
				oneRow.add(re.getRacct());
				oneRow.add(re.getTxtmd());
				Object a = ((re.getLast_year_cost()+"").equals("0.0")?"":(re.getLast_year_cost()));
				oneRow.add(a);
				Object b = ((re.getYear_plan()+"").equals("0.0")?"":(re.getYear_plan()));
				oneRow.add(b);
				Object c = ((re.getPeriod_plan()+"").equals("0.0")?"":(re.getPeriod_plan()));
				oneRow.add(c);
				Object d = ((re.getPeriod_cost()+"").equals("0.0")?"":(re.getPeriod_cost()));
				oneRow.add(d);
				Object e = ((re.getExe_rate()+"").equals("0.0")?"":(re.getExe_rate()));
				oneRow.add(e);
				
				dataRowList.add(oneRow);
			}
		}else{
			for (THM_FS24_Result re : reList) {
				oneRow = new ArrayList<Object>();
				oneRow.add(re.getItem_column());
				oneRow.add(re.getId());
			    oneRow.add(re.getRacct());
				
				oneRow.add(re.getTxtmd());
				Object a = ((re.getLast_year_cost()+"").equals("0.0")?"":(re.getLast_year_cost()));
				oneRow.add(a);
				Object b = ((re.getYear_plan()+"").equals("0.0")?"":(re.getYear_plan()));
				oneRow.add(b);
				Object c = ((re.getPeriod_plan()+"").equals("0.0")?"":(re.getPeriod_plan()));
				oneRow.add(c);
				Object d = ((re.getPeriod_cost()+"").equals("0.0")?"":(re.getPeriod_cost()));
				oneRow.add(d);
				Object e = ((re.getExe_rate()+"").equals("0.0")?"":(re.getExe_rate()));
				oneRow.add(e);
				
				dataRowList.add(oneRow);
			
		 }
		
	  }
		return dataRowList;
  }

	@Override
	public ExcelExportUtil24 exportExcel(QueryBean queryBean, int flag) {
		String title="销售费用统计分析表";
    	
    	
    	String secondTitle="编制单位:-";
    	
    	/**
    	 * 编制单位
    	 */
    	List<String> compValues  = queryBean.getCompCodeValue();
    	secondTitle = getComName(compValues);
    	
        List<String> headerList = Lists.newArrayList();
        headerList.add("项目");
        headerList.add("行次");
        headerList.add("内部订单");
        headerList.add("描述");
        headerList.add("上年发生额");
        headerList.add("本年计划");
        headerList.add("本期计划");
        headerList.add("本期发生额");
        headerList.add("执行率");
        
        secondTitle=secondTitle+"###"+queryBean.getDateYearMonthStart()+"-"+queryBean.getDateYearMonthEnd();
        
        List<List<Object>> dataList = export(queryBean,flag);

        ExcelExportUtil24 ee = new ExcelExportUtil24(title, secondTitle, headerList);

         ee=ee.getFs24ExcelReport(dataList, ee,2);
		return ee;
	}

	@Override
	public Object importData(String filePath,List<String> compValues) throws JsonProcessingException,
			IOException, DataAccessException, InvalidFormatException,
			InstantiationException, IllegalAccessException {
        String yearMonth = null;
        Matcher matcher = null;
        String json = null;
        Map<String, Object> listMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        List<String> headerList = Lists.newArrayList();
        headerList.add("项目");
        headerList.add("行次");
        headerList.add("内部订单");
        headerList.add("描述");
        headerList.add("上年发生额");
        headerList.add("本年计划");
        headerList.add("本期计划");
        headerList.add("本期发生额");
        headerList.add("执行率");
        //导入数据
        ExcelImportUtil ei = new ExcelImportUtil(filePath, 2);
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
        
        /**
         * 检查文件头是否一致
         */
        json = ei.checkHeadList(headerList,listMap);
        if(null!=json){
            return json;
        }
        //如果验证数据没有问题，则返回所有的数据，标志位为1，若是数据有问题，则返回不正确数据，标准为0
        List<InsertFieldTHM_FS24> list = ei.getDataList(InsertFieldTHM_FS24.class, "FS24");
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
        
        List<InsertFieldTHM_FS24> tmpDataList = new ArrayList<InsertFieldTHM_FS24>();
        InsertFieldTHM_FS24 tmp = new InsertFieldTHM_FS24();
        
        
        /**
         * 获取所有一级明细的列名
         */
        List<Map<String,String>> fieldNames=iReportMapperFS26.getFieldName();
        
        String fname=fieldNames.get(0).get("fieldName");
       
        //导入数据处理
        for (int i = 0; i < list.size(); i++) {
            if (!StringUtil.isEmpty(list.get(i).getItem_column())
            		&&!fname.contains(list.get(i).getItem_column())) {
            	tmp.setItem_column(list.get(i).getItem_column());
            	tmp.setId(list.get(i).getId());
            	tmp.setRacct(list.get(i).getRacct());
            	tmp.setTxtmd(list.get(i).getTxtmd());
            	tmp.setLast_year_cost(list.get(i).getLast_year_cost());
            	tmp.setYear_plan(list.get(i).getYear_plan());
            	tmp.setPeriod_cost(list.get(i).getPeriod_cost());
            	tmp.setPeriod_plan(list.get(i).getPeriod_plan());
            	tmp.setExe_rate(list.get(i).getExe_rate());
            	
            	tmp.setStartdate(startdate);
            	tmp.setEnddate(enddate);
                tmpDataList.add(tmp);
                tmp = new InsertFieldTHM_FS24();
            }
        }

        /**
         * 导入的时候需要逐条验证，不能批量导入，需要根据公司代码，时间，id验证
         */
        HashMap<String,String> map = null;
        List<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
        Pattern pattern = Pattern.compile("^\\w+$"); 
        for (InsertFieldTHM_FS24 thm : tmpDataList) {
        	map = new HashMap<String,String>();
        	map.put("item_column",thm.getItem_column());
        	map.put("id",thm.getId()+"");
        	map.put("racct",thm.getRacct());
        	map.put("txtmd",thm.getTxtmd());
        	map.put("last_year_cost",thm.getLast_year_cost()+"");
        	map.put("year_plan",thm.getYear_plan()+"");
        	map.put("period_plan",thm.getPeriod_plan()+"");
        	map.put("period_cost",thm.getPeriod_cost()+"");
        	map.put("exe_rate",thm.getExe_rate()+"");
        	map.put("startdate",thm.getStartdate());
        	map.put("enddate",thm.getEnddate());
        	
         	/**
        	 * 公司上传权限验证
        	 */
            if(pattern.matcher(thm.getItem_column()).matches()){
            	if(compValues.indexOf(thm.getItem_column())==-1){
    	        	listMap.put("ERROR", "当前用户没有  ："+thm.getItem_column()+" 没有导入权限！");
    	  	        json = mapper.writeValueAsString(listMap);
    	  	        return json;
    	        }
            }
            data.add(map);
        	
		}
        
        for (HashMap<String, String> thm : data) {
        	 int count = iReportMapperFS26.insertOrUpdate(thm);
         	/**
         	 * 区分会计科目和内部订单
         	 * 
         	 */
        	 thm.put("count", count+"");
         	List<Integer> distinct=Arrays.asList(15,16,17,18,19,20,21,22,23,24,25,26,27,28,33,35,36,40);
         	int id = Integer.valueOf(thm.get("id"));
         	if(distinct.indexOf(id)!=-1){
         		thm.put("updateFlag",0+"");
         	}else{
         		thm.put("updateFlag",1+"");
         	}
         	iReportMapperFS26.saveOrUpdateData(thm);
		}
       
    	
        listMap.put("SUCCESSES", "导入成功!");
        json = mapper.writeValueAsString(listMap);
        return json;
	}

	@Override
	public List<THM_FS24_01> getImpFirst(QueryBean queryBean) {
		if((StringUtils.isNotBlank(queryBean.getDateYearMonthStart()) 
				&& StringUtils.isNotBlank(queryBean.getDateYearMonthEnd()))){
	        	//获取时间必选
	        	String startTime = queryBean.getDateYearMonthStart();
	            String endTime = queryBean.getDateYearMonthEnd();
	            
	            //公司代码
	         	List<String> compValues  = queryBean.getCompCodeValue(); 
	            //多值 转换成字符串  ,号隔开
	            String p_bukrs =(compValues==null||compValues.size()==0)?"" :compValues.toString().replace("[", "\'").replace("]", "\'").replace(", ",",");
	            
	            HashMap<String,String> paraMap = new HashMap<>();
	            
	        	paraMap.put("startdate", startTime);
	        	paraMap.put("enddate"  , endTime);
	        	paraMap.put("compCodeValue", p_bukrs);
	        	
	        	List<THM_FS24_01> list =  iReportMapperFS26.getFirstDetailImp(paraMap);
	        	list=listMerge(list);
	 	        return list;
				
			}
		
		return null;
	}

	@Override
	public List<THM_FS24_01> getImpSecond(QueryBean queryBean) {
		if((StringUtils.isNotBlank(queryBean.getDateYearMonthStart()) 
				&& StringUtils.isNotBlank(queryBean.getDateYearMonthEnd()))){
	        	//获取时间必选
	        	String startTime = queryBean.getDateYearMonthStart();
	            String endTime = queryBean.getDateYearMonthEnd();
	            
	            //公司代码
	         	List<String> compValues  = queryBean.getCompCodeValue(); 
	            //多值 转换成字符串  ,号隔开
	            String p_bukrs =(compValues==null||compValues.size()==0)?"" :compValues.toString().replace("[", "\'").replace("]", "\'").replace(", ",",");
	            
	            HashMap<String,String> paraMap = new HashMap<>();
	            
	        	paraMap.put("startdate", startTime);
	        	paraMap.put("enddate"  , endTime);
	        	paraMap.put("compCodeValue", p_bukrs);
	        	
	        	/**
	        	 * 查出所有的一级明细，与二级明细合并
	        	 */
	        	List<THM_FS24_01> list =  iReportMapperFS26.getFirstDetailImp(paraMap);
	        	list=listMerge(list);
	        	
	        	List<THM_FS24_01>  bukrsDetail = iReportMapperFS26.getSecondDetailImp(paraMap);
	        	if(list!=null&&list.size()>0&&bukrsDetail!=null){
	        		list.addAll(bukrsDetail);
		        	
		        	list.sort(compareBurks);
		 	        return list;
	        	}
	        	
	        
			}
		return null;
	}

	@Override
	public String getComName(List<String> compValues) {
		String secondTitle="编制单位:-";
		List<String> compValuesTemp = new ArrayList<String>();
		if(compValues!=null&&compValues.size()>0){
			String[] compStr = compValues.toString().replace("[", "\'").replace("]", "\'").replace(", ",",").split(",");
			for (int i = 0; i < compStr.length; i++) {
				compValuesTemp.add(compStr[i]);
			}
		}else{
			return secondTitle;
		}
    	List<MdmTcompCode> compCodeList= genericMapper.getAllTcompCode24("THM_FS24_01",null,null);
   	 
    	
    	if((compValuesTemp!=null&&compValuesTemp.size()==0)||(compValuesTemp.size()==compCodeList.size())){
    		   secondTitle="编制单位:中车永济电机有限公司合计";
    	}else if(compValuesTemp.size()>1&&compValuesTemp.size()<compCodeList.size()){
    		   secondTitle="编制单位:-";
    	}else if(compValuesTemp.size()==1){
        	 for (int i = 0; i < compCodeList.size(); i++) {
        		 MdmTcompCode comp= compCodeList.get(i);
        		 
        		 
        		 if(("'"+comp.getComp_code()+"'").equals(compValuesTemp.get(0))){
        			 secondTitle="编制单位:"+comp.getTxtmd(); 
        		 }
			 }

    	}
		return secondTitle;
	}
  
	
}