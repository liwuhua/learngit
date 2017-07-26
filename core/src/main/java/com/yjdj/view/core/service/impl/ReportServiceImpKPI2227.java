package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.InsertFieldTHM_KPI2227;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.QueryPojo2;
import com.yjdj.view.core.mapper.IReportMapperKPI0105;
import com.yjdj.view.core.mapper.IReportMapperKPI2227;
import com.yjdj.view.core.service.IReportServiceKPI2227;
import com.yjdj.view.core.util.ExcelExportUtil2227;
import com.yjdj.view.core.util.ExcelImportUtil2227;
import com.yjdj.view.core.util.FileUtils;

import javafx.beans.binding.StringBinding;

/**
 * Created by liwuhua on 16/11/7.
 */
@Service
@TransactionConfiguration(defaultRollback = false)
@SuppressWarnings("all")
public class ReportServiceImpKPI2227 implements IReportServiceKPI2227{


	@Autowired
    private IReportMapperKPI2227 ireportmapperkpi2227;
	
//	@Autowired
//	private IReportMapperKPI0105 ireportmapperkpi01;
    
    private final Logger log = Logger.getLogger(ReportServiceImpKPI2227.class);
    Map<String, Object> listMap = new HashMap<String, Object>();
    Map<String, Object> errorsdataMap = new HashMap<String, Object>();
    ObjectMapper objectMapper = new ObjectMapper();
    int i = 1;
    int j = 1;
    
   
    //列名
    static private String xh       = "序号";  //页面展示时使用的
    static private String time     = "时间";
    static private String yue      = "月份";
    static private String lbbh     = "质量损失类别编号";
    static private String cpxh     = "产品型号";
    static private String lbdm     = "质量损失类别代码";
    static private String lbdmtxt  = "质量损失类别";  //页面展示时使用的
    
    static private String yydm     = "质量损失原因代码";
    static private String yydmtxt  = "质量损失原因"; //页面展示时使用的
    
    static private String fsdw     = "质量问题发生单位";
    static private String fxgx     = "发现工序";
    static private String cpmc     = "产品名称";
    static private String gzsl     = "不合格数量(故障数量)";
    static private String dw       = "单位";  
    static private String wtms     = "质量问题描述";
    static private String djbh     = "不合格品单据编号";
    static private String zt       = "不合格状态";
    static private String gsss     = "工时损失";
    static private String clss     = "材料损失";
    static private String qtss     = "其他损失";
    static private String yjss     = "预计损失金额(元)";
    static private String sjsse    = "实际损失金额(元)";
    static private String pcje     = "赔偿金额(元)";
    static private String zrdw     = "责任单位";
    static private String xmlx     = "项目类型";
    static private String czbs     = "操作标识";
    
//    static private String th    = "图号";
//    static private String jnw    = "境内/境外";
//    static private String xzjx    = "新造/检修";
//    static private String ddh    = "订单号";
//    static private String ylh    = "预留号";
//    static private String isfh    = "合同是否规定赔偿金额、时间和付款方式(是/否)";
//    static private String spqk    = "索赔进展情况";
//    static private String fx  = "分析";
//    static private String cgy    = "采购员";
    
    
	
	@Override
	public String getDeadline(  ) throws JsonProcessingException, IOException, DataAccessException {
		HashMap timeMap = new HashMap<>();
		String json = "{\"deadline\":\"质量损失额截止      年    月;营业收入截止      年    月\"}";
		List<Map> deadLine = ireportmapperkpi2227.getDeadline();
			Map incomeTimeMap = deadLine.get(0);
			Map lossTimeMap = deadLine.get(1);
			if(incomeTimeMap==null&&lossTimeMap!=null){
				String lossTime   = lossTimeMap.get("yymm")+"";
				lossTime="营业收入截止"+lossTime.substring(0,4)+"年"+lossTime.substring(4,6)+"月";
				timeMap.put("deadline", "质量损失额截止      年    月;"+lossTime);
				json= objectMapper.writeValueAsString(timeMap);
				return json;
			}else if(incomeTimeMap!=null&&lossTimeMap==null){
				String incomeTime  = incomeTimeMap.get("yymm")+"";
				incomeTime="质量损失额截止"+incomeTime.substring(0,4)+"年"+incomeTime.substring(4,6)+"月;";
				timeMap.put("deadline", incomeTime+"营业收入截止    年   月");
				json= objectMapper.writeValueAsString(timeMap);
			}
		return json;
	}
    
    
	@Override
	public String getPlantAccount(QueryBean queryBean) throws JsonProcessingException,DataAccessException {
		
		String endYearMonth = queryBean.getDateYearMonth(); 
		String json = null;
		if(StringUtils.isNotEmpty(endYearMonth)){
			String startYearMonth=endYearMonth.substring(0,4)+"01";  // 质量损失 当年累计值
			String startYearMonth1=Integer.parseInt(endYearMonth)-100+"";
			HashMap monthMap = new HashMap<>();
			HashMap currMonthMap = new HashMap<>();
			List<Map> massLossRate = new ArrayList<>();
			
			monthMap.put("startYearMonth", startYearMonth);  
			monthMap.put("startYearMonth1", startYearMonth1);  
			monthMap.put("endYearMonth", endYearMonth);
			
			//查询得到一年每个月的  厂内 场外损失值+场内外和的值
			List<Map> plantAccount = ireportmapperkpi2227.getPlantAccount(monthMap);  
			//获得总质量损失金额
			Map total = ireportmapperkpi2227.getTotal(monthMap);
			double totalAccount=(double)total.get("total");
		
			Map totalMap =new HashMap<>();
			totalMap.put("total", new BigDecimal(totalAccount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		
			
			
			//营业收入数据完整的最后日期
			//如果截止日期  小于查询的日期 那么 给予null值
			String latestDate= ireportmapperkpi2227.getLatestDate(endYearMonth).get(0).get("latestYM").toString();
			
			//存放年月的值13个月
			List<Map> lossMate = new ArrayList<>();
			lossMate.clear();
			for(int i=12; i>=0; i--){ 
				String everyMonth = evaluate(endYearMonth,-i).substring(0, 6); 
				
				if(Integer.valueOf(latestDate)<Integer.valueOf(everyMonth)){
					Map map = new HashMap<>();
					map.put("mate", null);
					map.put("ymonth", everyMonth);
					lossMate.add(map);
					
				}else{
					monthMap.put("everyMonth", everyMonth);
					Map mateOnemonth = ireportmapperkpi2227.getLossMate(monthMap); 
					double mate= mateOnemonth==null?0.00:Double.valueOf(mateOnemonth.get("mate")+"");
					Map map = new HashMap<>();
					map.put("mate", mate);
					map.put("ymonth", everyMonth);
					lossMate.add(map);
				}
			}
			
			List resultList=new ArrayList<>();
			resultList.add(plantAccount);
			resultList.add(totalMap);
			resultList.add(lossMate);
			json = objectMapper.writeValueAsString(resultList);
		}
		return json;
	}

	//日期计算，按月做相减
	private static String evaluate(String yearMonth,int monthdiff) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String last = null;
		if (yearMonth.length() == 6)
			format = new SimpleDateFormat("yyyyMM");
		// 获取当前月第一天：
		Calendar c = Calendar.getInstance();
		Date thisb = null;
		try {
			thisb = format.parse(yearMonth);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(thisb);
		c.add(Calendar.MONTH, monthdiff);
		last = format.format(c.getTime())+"01";
		return last;
	}
	
	
	/**
	 * 
	 * <p>Title: getQualiDetail</p>
	 * <p>Description: </p>
	 * @param queryBean  传一个月份参数即可   饼图
	 * @return  
	 * @throws JsonProcessingException
	 * @throws DataAccessException
	 */
	@Override
	public String getQualiDetail(QueryBean queryBean) throws JsonProcessingException, DataAccessException {

		String dateYearMonth = queryBean.getDateYearMonth(); //通过这个取到场外损失 厂内损失 及损失中 占的比例
		String json=null;
		if(StringUtils.isNotEmpty(dateYearMonth)){
			List<Object> resultList = new ArrayList<>();
			
			HashMap monthyear = new HashMap<>();
			monthyear.put("dateYearMonth", dateYearMonth);
			
			//当年中的 dateYearMonth 月的厂内  场外损失额度 
			Map inplantAccounts = ireportmapperkpi2227.getInplantAccount(monthyear);
			Map outplantAccounts = ireportmapperkpi2227.getOutplantAccount(monthyear);
			List inTotalAccount = new ArrayList<>();
			List outTotalAccount = new ArrayList<>();
			//判端两者是否为空  如果不为空那么取他们的值  为空则值为0
			double intotal=0;
			double outotal=0;
			if(inplantAccounts!=null&&inplantAccounts.size()!=0){
				 intotal = (double)inplantAccounts.get("intotal");
			}
			
			if(outplantAccounts!=null&&outplantAccounts.size()!=0){
				outotal = (double)outplantAccounts.get("outtotal");
			}
			
			//厂内比率  
			inTotalAccount.add(inplantAccounts);  //厂内当月总数
			outTotalAccount.add(outplantAccounts); //厂外当月总数
			if(intotal!=0||outotal!=0){
				inTotalAccount.add(new BigDecimal((intotal/(intotal+outotal))*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+"%");  //厂内当月总数
				outTotalAccount.add( (new BigDecimal((outotal/(intotal+outotal))*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+"%"));
			}else if (intotal==0&&outotal==0){
				inTotalAccount.add("0.00%");
				outTotalAccount.add("0.00%");
			}
			
			//此时inplantAccounts  outplantAccounts 只有一个元素
			//`THM_KPI2227_01`  厂内明细表	
			//`THM_KPI2227_02`	厂外明细表  传一个参数  一个是月份  
			List<Map> inMonthDetail  = ireportmapperkpi2227.getInMonthDetail(monthyear);
			List<Map> OutMonthDetail = ireportmapperkpi2227.getOutMonthDetail(monthyear);
			
			List<Object> inMonth = new  ArrayList<>();
			List<Object> OutMonth = new  ArrayList<>();
			inMonth.add(inTotalAccount);
			inMonth.add(inMonthDetail);
			OutMonth.add(outTotalAccount );
			OutMonth.add( OutMonthDetail);
			
			resultList.add(inMonth); 
			resultList.add(OutMonth); 
			json = objectMapper.writeValueAsString(resultList);
	}
		return json;
	}
	
	//各质量损失类别下钻至质量损失原因    场内外   饼图下钻一层
	@Override 
	public String getCauseByType(QueryBean queryBean) throws JsonProcessingException,DataAccessException {

/*		String dateYearMonth = queryBean.getDateYearMonth(); //指定月
		String categorycode = queryBean.getProductType(); //用这个接收质量损失类别
		String ylzd = queryBean.getYlzd();     //区分场内外
		List<Object> resultList = new ArrayList<>();
		List<Map> inCauseAndAccount=new ArrayList<>();
		List<Map> outCauseAndAccount=new ArrayList<>();
		String json=null;
		if(StringUtils.isNotEmpty(dateYearMonth)&&StringUtils.isNotEmpty(categorycode)&&StringUtils.isNotEmpty(ylzd)){
				
			    HashMap monthAndcause = new HashMap<>();
				monthAndcause.put("dateYearMonth", dateYearMonth);
				monthAndcause.put("categorycode",  categorycode);
				
				if("1".equals(ylzd)){
					 inCauseAndAccount = ireportmapperkpi2227.getIncauseByType(monthAndcause);
					 resultList.add(inCauseAndAccount);
				}else if("2".equals(ylzd)){
					 outCauseAndAccount= ireportmapperkpi2227.getOutcauseByType(monthAndcause);
					 resultList.add(outCauseAndAccount);
				}
				json = objectMapper.writeValueAsString(resultList);
                return json;
		}
		return json;*/
		
		String dateYearMonth = queryBean.getDateYearMonth(); //通过这个取到场外损失 厂内损失 及损失中 占的比例
		String json=null;
		if(StringUtils.isNotEmpty(dateYearMonth)){
			List<Object> resultList = new ArrayList<>();
			
			HashMap monthyear = new HashMap<>();
			monthyear.put("dateYearMonth", dateYearMonth);
			
			//当年中的 dateYearMonth 月的厂内  场外损失额度 
			Map inplantAccounts = ireportmapperkpi2227.getInplantAccount(monthyear);
			Map outplantAccounts = ireportmapperkpi2227.getOutplantAccount(monthyear);
			List inTotalAccount = new ArrayList<>();
			List outTotalAccount = new ArrayList<>();
			//判端两者是否为空  如果不为空那么取他们的值  为空则值为0
			double intotal=0;
			double outotal=0;
			if(inplantAccounts!=null&&inplantAccounts.size()!=0){
				 intotal = (double)inplantAccounts.get("intotal");
			}
			
			if(outplantAccounts!=null&&outplantAccounts.size()!=0){
				outotal = (double)outplantAccounts.get("outtotal");
			}
			
			//厂内比率  
			inTotalAccount.add(inplantAccounts);  //厂内当月总数
			outTotalAccount.add(outplantAccounts); //厂外当月总数
			if(intotal!=0||outotal!=0){
				inTotalAccount.add(new BigDecimal((intotal/(intotal+outotal))*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+"%");  //厂内当月总数
				outTotalAccount.add( (new BigDecimal((outotal/(intotal+outotal))*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+"%"));
			}else if (intotal==0&&outotal==0){
				inTotalAccount.add("0.00%");
				outTotalAccount.add("0.00%");
			}
			
			//此时inplantAccounts  outplantAccounts 只有一个元素
			//`THM_KPI2227_01`  厂内明细表	
			//`THM_KPI2227_02`	厂外明细表  传一个参数  一个是月份  
			List<Map> inMonthDetail  = ireportmapperkpi2227.getInMonthDetail(monthyear);
			List<Map> OutMonthDetail = ireportmapperkpi2227.getOutMonthDetail(monthyear);
				
			List<Object> inMonth = new  ArrayList<>();
			List<Object> OutMonth = new  ArrayList<>();
			inMonth.add(inTotalAccount);
			inMonth.add(inMonthDetail);
			OutMonth.add(outTotalAccount );
			OutMonth.add( OutMonthDetail);
			
			resultList.add(inMonth); 
			resultList.add(OutMonth); 
			json = objectMapper.writeValueAsString(resultList);
		}
		return json;
		
	}
	
	
	    //质量损失原因下钻至责任单位    场内外
		@Override 
		public String getDutyunitByCause(QueryBean queryBean) throws JsonProcessingException,DataAccessException {

			String dateYearMonth = queryBean.getDateYearMonth(); //指定月
			String reasoncode = queryBean.getProductType(); //用这个接收质量损失原因
			String ylzd = queryBean.getYlzd();     //区分场内外
         			
			List<Map> inUnitAndAccount =new ArrayList<>();;
			List<Map> outUnitAndAccount=new ArrayList<>();;
			
			List<Object> resultList = new ArrayList<>();
			String json=null;
			if(StringUtils.isNotEmpty(dateYearMonth)&&StringUtils.isNotEmpty(reasoncode)&&StringUtils.isNotEmpty(ylzd)){
					
				    HashMap monthAndreason = new HashMap<>();
					monthAndreason.put("dateYearMonth", dateYearMonth);
					monthAndreason.put("reasoncode",  reasoncode);
					
					if("1".equals(ylzd)){
						inUnitAndAccount = ireportmapperkpi2227.getInDutyunitByCause(monthAndreason);
						resultList.add(inUnitAndAccount);
					}else if ("2".equals(ylzd)){
						outUnitAndAccount= ireportmapperkpi2227.getOutDutyunitByCause(monthAndreason);
						resultList.add(outUnitAndAccount);
					}
							
				  json = objectMapper.writeValueAsString(resultList);
				  return json;
			}
			
			return json;
		}
		
		
		//产品故障统计 :  按型号统计(数量) + 按故障类型统计(台次)   
		@Override 
		public String getFaultCount(QueryBean queryBean) throws JsonProcessingException,DataAccessException {
			String dateYearMonth = queryBean.getDateYearMonth(); //指定月
			String projetypecode = queryBean.getProductType(); //产品类型编码
			
			List<Object> resultList = new ArrayList<>();
			List<Map> faulttypelist = new ArrayList<>();  //存放 结果值 返回给前端   按错误类型合计的
			List<Map> faultmodelList = new ArrayList<>(); //存放 结果值 返回给前端  按错误型号合计的
			String json="{}";
	        if(StringUtils.isNotEmpty(dateYearMonth)&&StringUtils.isNotEmpty(projetypecode)){
					
				    HashMap monthAndreason = new HashMap<>();
					monthAndreason.put("dateYearMonth", dateYearMonth);
					monthAndreason.put("projetypecode", projetypecode);
					
					List<Map> faultCountByType = ireportmapperkpi2227.getFaultCountByType(monthAndreason);
					List<Map> faultCountByModel = ireportmapperkpi2227.getFaultCountByModel(monthAndreason);
					//遍历faultCountByType  faultCountByModel 比较count  如果数量大于5个的 那么比较大写
					
					//定义一个tmp变量 用来存放 比较值
					double counttmp=0;
					double listi=0;
					
					
					//先取 前五个  第五个和
                    if(faultCountByType!=null&&faultCountByType.size()<=5){
                    	resultList.add(faultCountByType);  //直接 返回结果值
                    }else{
                       	//list数量内  五个以上的元素  开始比较
                    	for (int i=0;i<faultCountByType.size();i++){
                    	    if(i>=5){
		                    	    //从第六个元素开始 和第五个元素   比较
		                    	    //只有相同的情况下才改变	
		                    	if(i==5){
		                    	 	counttmp= (double)faultCountByType.get(4).get("count"); //得到第五个元素
		                    	    listi= (double)faultCountByType.get(i).get("count"); 
		                    	    if(counttmp!=listi){
		                    	       
		                    	       resultList.add(faulttypelist);  //直接 返回结果值
                                       break;		                    	        
		                    	    }else{
		                    	     //如果从第六个和第五个相同 那么继续  改变临时变量	
		                    	     counttmp=(double)faultCountByType.get(i).get("count"); //改变临时变量为第5五个元素的值
		                    	     faulttypelist.add(faultCountByType.get(i));
		                    	     if(i==faultCountByType.size()-1){
		                    	    	 resultList.add(faulttypelist); 
		                    	     }
		                    	    }	
		                    		
		                    	}else{
		                    		//第
		                    		listi=(double)faultCountByType.get(i).get("count"); 
		                    		 if(counttmp!=listi){
		                    			   //两者不相等  结果集不加
//		                    			   faulttypelist.add(faultCountByType.get(i));
			                    	       resultList.add(faulttypelist);  //直接 返回结果值
	                                       break;		                    	        
			                    	    }else{
			                    	     //如果从第六个和第五个相同 那么继续  改变临时变量	
			                    	     counttmp=(double)faultCountByType.get(i).get("count"); //改变临时变量为第5五个元素的值
			                    	     faulttypelist.add(faultCountByType.get(i));
			                    	     if(i==faultCountByType.size()-1){
			                    	    	 resultList.add(faulttypelist); 
			                    	     }
			                    	    }	
		                    	}
		                    	    
                            }else{
                            		//前五个的值 存入到faulttypelist中
                            		faulttypelist.add(faultCountByType.get(i));
                            }
                      }
                    		
                    }
                    	
                    
                    if(faultCountByModel!=null&&faultCountByModel.size()<=5){
                    	resultList.add(faultCountByModel);  //直接 返回结果值
                    }else{
                       	//list数量内  五个以上的元素  开始比较
                    	for (int i=0;i<faultCountByModel.size();i++){
                    	    if(i>=5){
		                    	if(i==5){
		                    	 	counttmp= (double)faultCountByModel.get(4).get("count"); //得到第五个元素
		                    	    listi= (double)faultCountByModel.get(i).get("count"); 
		                    	    if(counttmp!=listi){
		                    	/*    	faultmodelList.add(faultCountByModel.get(0));
		                    	    	faultmodelList.add(faultCountByModel.get(1));
		                    	    	faultmodelList.add(faultCountByModel.get(2));
		                    	    	faultmodelList.add(faultCountByModel.get(3));
		                    	    	faultmodelList.add(faultCountByModel.get(0));*/
		                    	    	
		                    	       resultList.add(faultmodelList);  //直接 返回结果值
                                       break;		                    	        
		                    	    }else{
		                    	     //如果从第六个和第五个相同 那么继续  改变临时变量	
		                    	     counttmp=(double)faultCountByModel.get(i).get("count"); //改变临时变量为第5五个元素的值
		                    	     faultmodelList.add(faultCountByModel.get(i));
		                    	     if(i==faultCountByModel.size()-1){
		                    	    	 resultList.add(faultmodelList); 
		                    	     }
		                    	    }	
		                    		
		                    	}else{
		                    		listi=(double)faultCountByModel.get(i).get("count"); 
		                    		 if(counttmp!=listi){
			                    	       resultList.add(faultmodelList);  //直接 返回结果值
	                                       break;		                    	        
			                    	    }else{
			                    	     counttmp=(double)faultCountByModel.get(i).get("count"); //改变临时变量为第5五个元素的值
			                    	     faultmodelList.add(faultCountByModel.get(i));
			                    	     if(i==faultCountByModel.size()-1){
			                    	    	 resultList.add(faultmodelList); 
			                    	     }
			                    	    }	
		                    	}
                            }else{
                            		//前五个的值 存入到faulttypelist中
                            	  faultmodelList.add(faultCountByModel.get(i));
                            }
                          }
                      }
                    
					json = objectMapper.writeValueAsString(resultList);
			        return json;	
                    }
			 
			          return json;
		}

		    //产品故障统计   对具体型号下钻1层  故障类型  故障数量 
			@Override 
			public String getLayerByModel(QueryBean queryBean) throws JsonProcessingException,DataAccessException {
			    String dateYearMonth = queryBean.getDateYearMonth(); //指定月
				String projetypecode = queryBean.getProductType(); //产品类型编码(风电产品 铁路电机......)
				String productmodel = queryBean.getStartTime(); //产品型号
				
				String json="[]";
				if(StringUtils.isNotEmpty(dateYearMonth)&&StringUtils.isNotEmpty(projetypecode)
						                              &&StringUtils.isNotEmpty(productmodel)){
					
					    HashMap monthAndreason = new HashMap<>();
						monthAndreason.put("dateYearMonth", dateYearMonth);
						monthAndreason.put("projetypecode", projetypecode);
						monthAndreason.put("productmodel", productmodel);
							
							List<Map> layerByModel = ireportmapperkpi2227.getLayerByModel(monthAndreason);
							json = objectMapper.writeValueAsString(layerByModel);
					        return json;
					}
					
					return json;
				}
		
				//产品故障统计  故障类型点击下钻展示格式   产品型号	故障数量
				@Override 
				public String getLayerByType(QueryBean queryBean) throws JsonProcessingException,DataAccessException {
						
					String dateYearMonth = queryBean.getDateYearMonth(); //指定月
					String projetypecode = queryBean.getProductType(); //产品类型编码(风电产品 铁路电机......)
					String faulttype = queryBean.getStartTime(); //故障类型
					
					String json="[]";
					if(StringUtils.isNotEmpty(dateYearMonth)&&StringUtils.isNotEmpty(projetypecode)&&StringUtils.isNotEmpty(faulttype)){
							
						    HashMap paraMap = new HashMap<>();
						    paraMap.put("dateYearMonth", dateYearMonth);
						    paraMap.put("projetypecode", projetypecode);
						    paraMap.put("faulttype", faulttype);
							
							List<Map> laYerByType = ireportmapperkpi2227.getLayerByType(paraMap);
							json = objectMapper.writeValueAsString(laYerByType);
					        return json;
					}
					
					        return json;
				}

				@Override
				public String getProjecttypes() throws JsonProcessingException, DataAccessException {
					       String json="[]";
							List<Map> projecttypes = ireportmapperkpi2227.getProjecttypes();
							json = objectMapper.writeValueAsString(projecttypes);
					        return json;
				
				}
				
				@Override
				public String getCode(String tableName, String categorycode, String categorytxt,Integer tablebs)
						throws JsonProcessingException, DataAccessException {
					String json="[]";
					List<LinkedHashMap> category = ireportmapperkpi2227.getCode(tableName, categorycode, categorytxt,tablebs);			
					json = objectMapper.writeValueAsString(category);
					return json;
				}  
				
					
				
				
				//查询 or 导出结果
				@Override
				public Object getResult(QueryPojo2 queryPojo) throws DataAccessException, IOException {
					// 从数据哭中查询数据  兼职 导出时的功能
					 int flag = 0;//查询flag为0，导出flag为1
				        boolean isExport = queryPojo.isExport();
				        if (isExport) {
				            flag = 1;
				        }
				        
//				    	序号	时间	月份	质量损失类别编号	产品型号	质量损失类别	质量损失原因	质量问题发生单位	
//				    	发现工序	产品名称	不合格数量（故障数量）
//				    	单位	质量问题描述  不合格品单据编号	不合格状态	工时损失	材料损失	
//				    	其他损失	预计损失金额(元)	实际损失金额(元)	赔偿金额(元)	责任单位	项目类型
				        
				        List<String> headerList = new ArrayList<>();
				        headerList.add( xh);   //"序号";
				        headerList.add( time);   //"时间";
				        headerList.add( yue);   //"月份";
				        headerList.add( lbbh);   //"质量损失类别编号";
				        headerList.add( cpxh);   //"产品型号";
				        headerList.add( lbdmtxt);   //"质量损失类别";
				        headerList.add( yydmtxt);   //"质量损失原因";
				        headerList.add( fsdw);   //"质量问题发生单位";
				        headerList.add( fxgx);  // "发现工序";
				        headerList.add( cpmc);   //"产品名称";
				        headerList.add( gzsl);   //"不合格数量(故障数量);";
				        headerList.add( dw);   //"单位";
				        headerList.add( wtms); // "质量问题描述";===
				        headerList.add( djbh); //"不合格品单据编号";
				        headerList.add( zt);   //"不合格状态";
				        headerList.add( gsss);   //"工时损失";
				        headerList.add( clss);   //"材料损失";
				        headerList.add( qtss);   //"其他损失";
				        headerList.add( yjss);   //"预计损失金额(元);";
				        headerList.add( sjsse);   //"实际损失金额(元);";
				        headerList.add( pcje);   //"赔偿金额(元);";
				        headerList.add( zrdw);  // "责任单位";
				        headerList.add( xmlx);   //"项目类型";
				        
				        
//				        headerList.add( th);   //"图号";
//				        headerList.add( jnw);   //"境内/境外";
//				        headerList.add( xzjx);   //"新造/检修";
//				        headerList.add( isfh);   //"合同是否规定赔偿金额、时间和付款方式(是/否);";
//				        headerList.add( spqk);   //"索赔进展情况";
//				        headerList.add( fx);   // "分析";
//				        headerList.add( cgy);   //"采购员";
				        
				        
//				        headerList.add( cpxh);   //"产品型号";
//				        headerList.add( lbdm);   //"质量损失类别代码";
//				        headerList.add( yydm);   //"质量损失原因代码";


//				        headerList.add( ddh);   //"订单号";
//				        headerList.add( ylh);   //"预留号";
//				        headerList.add( xmlx);   //"项目类型";
//				        headerList.add( ddh);   //"订单号";
//				        headerList.add( ylh);   //"预留号";
				        				      
				        //查询
				        List<LinkedHashMap> list_01=ireportmapperkpi2227.getResult(
				                queryPojo.getLbbh(),
				                queryPojo.getLbdm(),
				                queryPojo.getYydm(),
				                queryPojo.getFsdw(),
//				                queryPojo.getProductCode(),
				                queryPojo.getZrdw(),
//				                queryPojo.getJnw(),
//				                queryPojo.getXzjx(),
				                queryPojo.getXmlx(),
				                queryPojo.getStartTime(),
				                queryPojo.getEndTime(),
				                queryPojo.getStartitem(),
				                queryPojo.getPageitem(),
				                flag
				        );
				        if (!isExport) {
				        	ObjectMapper mapper = new ObjectMapper();
				            String resultJson = mapper.writeValueAsString(list_01);
				            return resultJson;
				        } else {
				            List<List<Object>> resultDataRowList = exportExcel(list_01);
				            String secondTitle = null;
				            ExcelExportUtil2227 ee = new ExcelExportUtil2227("厂内产品质量信息表", secondTitle, headerList);
				            List<List<Object>> dataList = Lists.newArrayList();
				            for (int j = 0; j < resultDataRowList.size(); j++) {
				                dataList.add(resultDataRowList.get(j));
				            }
				            for (int i = 0; i < resultDataRowList.size(); i++) {
				                Row row = ee.addRow();
				                for (int j = 0; j < dataList.get(i).size(); j++) {
//				                    ee.addCell(row, j, dataList.get(i).get(j));
				                    ee.addCell(row, row.getRowNum(), j, dataList.get(i).get(j), 1, true, String.class);
				                }
				            }
				            return ee;
				        }
				}
				
				
				
				//导出数据通用 (数据排序)
			    private List<List<Object>> exportExcel(List<LinkedHashMap> list_01) throws IOException {
//			        int num = 1;
			        List<List<Object>> dataRowList = Lists.newArrayList();
			        List<Object> list_ex = new ArrayList<Object>();
			        for (Map value : list_01) {
			        	
			        	
					    list_ex.add(value.get("xh")); 
			            list_ex.add(value.get("date")); 
			        	list_ex.add(value.get("yymm")); 
			        	list_ex.add(value.get("categorynum")); 
			        	list_ex.add(value.get("productmodel")); 
			        	list_ex.add(value.get("categorycode")); 
			        	list_ex.add(value.get("reasoncode")); 
			        	list_ex.add(value.get("occurrunit")); 
			        	list_ex.add(value.get("discoprocess")); 
			        	list_ex.add(value.get("productname")); 
			        	list_ex.add(value.get("faultnum")); 
			        	list_ex.add(value.get("unit")); 
			        	list_ex.add(value.get("qualitydesc"));
			        	list_ex.add(value.get("ducumnum"));
			        	list_ex.add(value.get("noqualistatus"));
			        	list_ex.add(value.get("timeloss"));
			        	list_ex.add(value.get("materialloss"));
			        	list_ex.add(value.get("otherloss"));
			        	list_ex.add(value.get("prelossmonty"));
			        	list_ex.add(value.get("actlossmonty"));
			        	list_ex.add(value.get("payaccou"));
			        	list_ex.add(value.get("dutyunit")); 
			        	list_ex.add(value.get("projetype")); 
			           
//			        	list_ex.add(value.get("date")); 
//			        	list_ex.add(value.get("yymm")); 
//			        	list_ex.add(value.get("categorynum")); 
//			        	list_ex.add(value.get("occurrunit")); 
//			        	list_ex.add(value.get("discoprocess")); 
//			        	list_ex.add(value.get("picturecode")); 
//			        	list_ex.add(value.get("productname")); 
//			        	list_ex.add(value.get("faultnum")); 
//			        	list_ex.add(value.get("qualitydesc"));
//			        	list_ex.add(value.get("payaccou"));
//			        	list_ex.add(value.get("dutyunit")); 
//			        	list_ex.add(value.get("abroadordomesti")); 
//			        	list_ex.add(value.get("newlyorcheck"));
//			        	list_ex.add(value.get("isaccordance")); 
//			        	list_ex.add(value.get("fareconditin")); 
//			        	list_ex.add(value.get("analyzee")); 
//			        	list_ex.add(value.get("purchaser")); 

//			        	list_ex.add(value.get("productmodel")); 						
//			        	list_ex.add(value.get("categorycode"));
//			        	list_ex.add(value.get("reasoncode"));  
//			        	list_ex.add(value.get("company")); 
//			        	list_ex.add(value.get("ducumnum")); 
//			        	list_ex.add(value.get("noqualitatus"));
//			        	list_ex.add(value.get("timeloss"));
//			        	list_ex.add(value.get("materialloss"));
//			        	list_ex.add(value.get("otherloss"));
//			        	list_ex.add(value.get("prelossmonty"));
//			        	list_ex.add(value.get("actlossmonty"));
//			        	list_ex.add(value.get("ordernum"));
//			        	list_ex.add(value.get("obligatenum"));
//			        	list_ex.add(value.get("projetypecode")); 

						dataRowList.add(list_ex);
			            list_ex = new ArrayList<Object>();
			        }

			        return dataRowList;
			    }



				//导入数据
			    @Override
			    public Object importData(String filePath)throws DataAccessException,
			            InvalidFormatException, IOException, InstantiationException, IllegalAccessException {
			        
			    	ExcelImportUtil2227 ei = new ExcelImportUtil2227(filePath, 1);
			        Map<String,Object> errorMap = new HashMap<String, Object>();
			        ObjectMapper mapper = new ObjectMapper();
			        String json = null;
			        List<String> headerList = new ArrayList<>();
			         
			          headerList.add(time);
				  	  headerList.add(yue);
				  	  headerList.add(lbbh);
				  	  headerList.add(cpxh);
				  	  headerList.add(lbdm);
				  	  headerList.add(yydm);
				  	  headerList.add(fsdw);
				  	  headerList.add(fxgx);
//				  	  headerList.add(th);
				  	  headerList.add(cpmc);
				  	  headerList.add(gzsl);
				  	  headerList.add(dw);
				  	  headerList.add(wtms);
				  	  headerList.add(djbh);
				  	  headerList.add(zt);
				  	  headerList.add(gsss);
				  	  headerList.add(clss);
				  	  headerList.add(qtss);
				  	  headerList.add(yjss);
				  	  headerList.add(sjsse);
				  	  headerList.add(pcje);
				  	  headerList.add(zrdw);
//				  	  headerList.add(jnw);
//				  	  headerList.add(xzjx);
//				  	  headerList.add(ddh);
//				  	  headerList.add(ylh);
					  headerList.add(xmlx);
					  headerList.add(czbs);
//				  	  headerList.add(isfh);
//				  	  headerList.add(spqk);
//				  	  headerList.add(fx);
//				  	  headerList.add(cgy);

				  	  
				  	 listMap.clear();
				        //检查列名
			        json = ei.checkHeadList(headerList, listMap);
			        if (null != json) {
			            return json;
			        }
                     
                    //得到excel中  所有的数据  以一行为单位  InsertFieldTHM_KPI2227 就是一行数据
			        @SuppressWarnings("unchecked")
					List<InsertFieldTHM_KPI2227> list = ei.getDataList(InsertFieldTHM_KPI2227.class, "KPI2227");

			        
			       //检查数据
			        listMap.clear();
			        
					List<InsertFieldTHM_KPI2227> resultlist = checkData(list);
			        if(listMap.size() > 0){
			        	errorsdataMap.put("ERRORDATA", listMap);
			            errorMap.put("ERROR", errorsdataMap);
			            return mapper.writeValueAsString(errorMap);
			        }

			        for(InsertFieldTHM_KPI2227 value:list) {
			        	//如果是插入的那么判断一下  数据库中是否存在记录
			        	HashMap czbsMap = new HashMap<>();
			        	String czbs = value.getCzbs().trim();
			        	String lbbhStr = value.getLbbh().trim();
			        	 ireportmapperkpi2227.operateData(value);
			        }
			        
			        listMap.put("SUCCESSES", "导入成功!");
			        return mapper.writeValueAsString(listMap);
			     }
			    
			    
			    private List<InsertFieldTHM_KPI2227> checkData(List<InsertFieldTHM_KPI2227> list) {

 			        String tmp = "";
 			        String tmp1 = "";
 			        Integer codebs=0;
 			        HashMap codeMap = new HashMap<>();
			        DecimalFormat deciformat=new DecimalFormat("0.00");
			        Boolean required;//是否为必填项

			        for(int row = 0; row < list.size(); row++){
			            InsertFieldTHM_KPI2227 value = list.get(row);

			            //判空 时间
			            tmp = value.getTime().trim();
			            required = false;
			            tmp = checkNull(row, time, tmp, required);
			            //格式 时间
			            if(!tmp.equals("")) tmp = checkDateFormat(row, time, tmp);
			            value.setTime(tmp);
			            
			            
			            //判空 月份  需要判断  是否是时间格式的
			            tmp = value.getYue().trim();
			            required = true;
			            tmp = checkNull(row, yue, tmp, required);
			            //格式 月份 年月的
			            if(!tmp.equals("")) tmp1 = checkDateFormat(row, yue, tmp+"01");
			            value.setYue(tmp);
			            

			            //判空 质量损失类别编号   这个做一下正则校验
			            tmp = value.getLbbh().trim();
			            required = true;
			            tmp = checkNull(row, lbbh, tmp, required);
			            value.setLbbh(tmp);
			            
			            
			            //判空 产品型号
			            tmp = value.getCpxh().trim();
			            required = false;
			            tmp = checkNull(row, cpxh, tmp, required);
			            value.setCpxh(tmp);

			            //判空   质量损失类别代码  
			            tmp = value.getLbdm().trim();
			            required = true;
			            tmp = checkNull(row, lbdm, tmp, required);
			            if(!tmp.equals("")) tmp = checkMainData(row,lbdm,tmp,1);
			            value.setLbdm(tmp);
			            

			            //判空 质量损失原因代码
			            tmp = value.getYydm().trim();
			            required = true;
			            tmp = checkNull(row, yydm, tmp, required);
			            if(!tmp.equals("")) tmp = checkMainData(row,yydm,tmp,2);
			            value.setYydm(tmp);
			            
			            
			            //判空  质量问题发生单位
			            tmp = value.getFsdw().trim();
			            required = false;
			            tmp = checkNull(row, fsdw, tmp, required);
			            value.setFsdw(tmp);
			            
			            //判空  发现工序
			            tmp = value.getFxgx().trim();
			            required = false;
			            tmp = checkNull(row, fxgx, tmp, required);
			            value.setFxgx(tmp);
			            

			            //判空 图号
//			            tmp = value.getTh().trim();
//			            required = false;
//			            tmp = checkNull(row, th, tmp, required);
//			            value.setTh(tmp);

			            
			            //判空 产品名称
			            tmp = value.getCpmc().trim();
			            required = false;
			            tmp = checkNull(row, cpmc, tmp, required);
			            value.setCpmc(tmp);
			            
			            //判空 不合格数量(故障数量)正实数
			            tmp = value.getGzsl().trim(); 
			            required = false;
			            tmp = checkNull(row, gzsl, tmp, required);
			            if(!tmp.equals("")) tmp = checkNoFormat(row, gzsl, tmp,deciformat);
			            value.setGzsl(tmp);	            
			            
			            //判空  单位(计量单位)
			            tmp = value.getDw().trim();
			            required = false;
			            tmp = checkNull(row, dw, tmp, required);
			            value.setDw(tmp);
			            
			            //判空 质量问题描述
			            tmp = value.getWtms().trim();
			            required = false;
			            tmp = checkNull(row, wtms, tmp, required);
			            value.setWtms(tmp);
			            
			            //判空 不合格品单据编号
			            tmp = value.getDjbh().trim();
			            required = false;
			            tmp = checkNull(row, djbh, tmp, required);
			            value.setDjbh(tmp);

			            //判空 不合格状态
			            tmp = value.getZt().trim();
			            required = false;
			            tmp = checkNull(row, zt, tmp, required);
			            value.setZt(tmp);
			            
			            //判空  工时损失
			            tmp = value.getGsss().trim();
			            required = false;
			            tmp = checkNull(row, gsss, tmp, required);
			            tmp = checkNoFormat(row, gsss, tmp, deciformat);
			            value.setGsss(tmp);
			     
			            //判空   材料损失
			            tmp = value.getClss().trim();
			            required = false;
			            tmp = checkNull(row, clss, tmp, required);
			            tmp = checkNoFormat(row, clss, tmp, deciformat);
			            value.setClss(tmp);
			            
			            //判空   其他损失
			            tmp = value.getQtss().trim();
			            required = false;
			            tmp = checkNull(row, qtss, tmp, required);
			            tmp = checkNoFormat(row, qtss, tmp, deciformat);
			            value.setQtss(tmp);
			            
			            //判空   预计损失金额 
			            tmp = value.getYjss().trim();
			            required = false;
			            tmp = checkNull(row, yjss, tmp, required);
			            tmp = checkNoFormat(row, yjss, tmp, deciformat);
			            value.setYjss(tmp);
			            
			           
						//判空    实际损失金额(元)  
						tmp = value.getSjsse().trim();
						required = true;
						tmp = checkNull(row, sjsse, tmp, required);
						if(!tmp.equals(""))  tmp = checkNumFormat(row, sjsse, tmp);
						value.setSjsse(tmp);
			            
			            
			            //判空 赔偿金额
						tmp = value.getPcje().trim();
						required = false;
						tmp = checkNull(row, pcje, tmp, required);
						tmp = checkNoFormat(row, pcje, tmp, deciformat);
						value.setPcje(tmp);
										

			            //判空 责任单位  
			            tmp = value.getZrdw().trim();
			            required = true;
			            tmp = checkNull(row, zrdw, tmp, required);
			            value.setZrdw(tmp);
			            
			            //判空 境内/境外
//			            tmp = value.getJnw().trim();
//			            required = false;
//			            tmp = checkNull(row, jnw, tmp, required);
//			            value.setJnw(tmp);
			            
			            //判空 新造/检修
//			            tmp = value.getXzjx().trim();
//			            required = false;
//			            tmp = checkNull(row, xzjx, tmp, required);
//			            value.setXzjx(tmp);
			            
			            //判空  订单号
//			            tmp = value.getDdh().trim();
//			            required = false;
//			            tmp = checkNull(row, ddh, tmp, required);
//			            value.setDdh(tmp);
			            
			            //判空  预留号
//			            tmp = value.getYlh().trim();
//			            required = false;
//			            tmp = checkNull(row, ylh, tmp, required);
//			            value.setDdh(tmp);
			            
			            
			            
			            //判空   合同是否规定赔偿金额、时间和付款方式(是/否);
//			            tmp = value.getIsfh().trim();
//			            required = false;
//			            tmp = checkNull(row, isfh, tmp, required);
//			            value.setIsfh(tmp);

			            //判空  索赔进展情况;
//			            tmp = value.getSpqk().trim();
//			            required = false;
//			            tmp = checkNull(row, spqk, tmp, required);
//			            value.setSpqk(tmp);
			            
			            
			            //判空  分析;
//			            tmp = value.getFx().trim();
//			            required = false;
//			            tmp = checkNull(row, fx, tmp, required);
//			            value.setFx(tmp);
			            
			            
			            //判空  采购员
//			            tmp = value.getCgy().trim();
//			            required = false;
//			            tmp = checkNull(row, cgy, tmp, required);
//			            value.setCgy(tmp);
			            
			            
			            //判空  项目类型   铁路电气   风电产品
			            tmp = value.getXmlx().trim();
			            required = true;
			            tmp = checkNull(row, xmlx, tmp, required);
			            if(!tmp.equals("")) tmp = checkMainData(row,xmlx,tmp,3);
			            value.setXmlx(tmp);
			            
			            //操作标识
			            tmp = value.getCzbs().trim();
			            tmp = tmp.trim();
			            switch (tmp){
			                case "新增":
			                	value.setCzbs("1");
			                	break;
			                case "修改":
			                	value.setCzbs("2");
			                	break;
			                case "删除":
			                	value.setCzbs("3");
			                	break;
			                default:listMap.put("OPERATEERROR","第" + (row + 3) + "行" + "-" + czbs+"操作标识填写错误，请填写：新增、更新、删除");
			            }
			          
			        }
			        return null;
			    }

			    
			    
			    private String checkMainData(int row,String col,String data,int codebs){
			        HashMap codeMap = new HashMap<>();
			        codeMap.put("code", data);
			        codeMap.put("codebs", codebs);
			        Map countIntable = ireportmapperkpi2227.isCodeIntable(codeMap);
			        //存在于数据库中
			        if (countIntable!=null&&Integer.parseInt(countIntable.get("count")+"")!=0) {
			            return data;
			        }else{
			            listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "数据不在范围内");
			            j++;
			            return "";
			        }
			    }
			    
			    
			    /**
			     * 必填项  许输入英文+数字+ - 符号的组合的内容格式校验
			     * @param row
			     * @param col
			     * @param data
			     * @return
			     */
			    private String checkCodeFormat(int row, String col, String data){
			        if (data.isEmpty() || data.trim().equals("") || data.equals(null)) {
			            return "";
			        }else {
			            try {
			            	//数字不为空的时候 做校验
			            	 Pattern p=Pattern.compile("^[A-Za-z0-9-]{0,200}$");
					    	   Matcher m=p.matcher(data.trim().replace("(","").replace(")","").replace("/", "").replace("-", ""));
					    	   if(m.find()){
					    		  //校验通过
					    	       return data;
					    	   }else{
					    		 //校验不通过
				    		    listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "数据不是有效编号");
				                j++;
				                return "";
					    	   }
			            } catch (NumberFormatException e){
			                listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "数据格式错误");
			                j++;
			                return "";
			            }
			        }
			    }

			    
			    
				/**
			     * 判断单元格内容是否为空，如果为空，返回“”，非空返回单元格本身内容
			     * @param row
			     * @param col
			     * @param data
			     * @return
			     */
			    private String checkNull(int row, String col, String data, Boolean required){
			        if (data.isEmpty() || data.trim().equals("") || data.equals(null)) {
			            if(required) {
			                listMap.put("NULLERROR" + i, "第" + (row + 3) + "行" + "-" + col + "-" + "内容不能为空。");
			                i++;
			            }
			            return "";
			        }else {
			            return data;
			        }
			    }
			    
			

			    /**
			     * 必填项数字加字母编号的内容格式校验
			     * @param row
			     * @param col
			     * @param data
			     * @return
			     */
			    private String checkFormat(int row, String col, String data){
			        String allMatch = "[a-zA-Z0-9]{1,}";
			        if(data.matches(allMatch) || (data.contains("E") && data.contains("."))){
//			            if(data.contains("E") && data.contains(".")){ //如果内容是纯数字并且被改编成科学计数法，则执行if中代码
			                try {
			                    BigDecimal bd = new BigDecimal(data);
			                    String str = bd.toPlainString();
			                    return str;
			                }catch (NumberFormatException e){
			                    return data;
			                }
//			            }
//			            return data;
			        }else {
			            listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "数据格式错误");
			            j++;
			            return "";
			        }
			    }

			    
			    /**
			     * 必填项的数字格式校验(只能是大于=0的正实数)
			     * @param row
			     * @param col
			     * @param data
			     * @param format
			     * @return
			     */
			    private String checkNumFormat(int row, String col, String data) {
	    				try {
			            	//数字不为空的时候 做校验
			            	 Pattern p=Pattern.compile("^[0-9].*$");
					    	   Matcher m=p.matcher(data);
					    	   if(m.find()){
					    		  //校验通过
					    	       return data;
					    	   }else{
					    		 //校验不通过
				    		    listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "数据不是正实数");
				                j++;
				                return "";
					    	   }
			            } catch (NumberFormatException e){
			                listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "数据格式错误");
			                j++;
			                return "";
			            }
			    }
			    
			    
			    /**
			     * 非必填项的数字格式校验  (只能是大于=0的正实数)
			     * @param row
			     * @param col
			     * @param data
			     * @param format
			     * @return
			     */
			    private String checkNoFormat(int row, String col, String data, DecimalFormat format) {
			        if (StringUtils.isEmpty(data)) {
			            return "0";
			        }else {
			            try {
			            	//数字不为空的时候 做校验  
			            	 Pattern p=Pattern.compile("^[0-9].*$"); //正数值
					    	   Matcher m=p.matcher(data);
					    	   if(m.find()){
					    		  //校验通过
					    	       return Math.abs(Double.parseDouble(data))+"";
					    	   }else{
					    		 listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "数据不是正实数");
					             j++;
				                return "";
					    	   }
			            } catch (NumberFormatException e){
			            	listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "数据格式错误");
			                j++;
			            	return "";
			            }
			        }
			    }
			    
			    
			    
			    /**
			     * 日期格式校验
			     * @param row
			     * @param col
			     * @param data
			     * @return
			     */
			    private  String checkDateFormat(int row, String col, String data){
			        boolean isValidDate=false;
			        try {
			            BigDecimal bd = new BigDecimal(data);
			            String str = bd.toPlainString();
			            if(str.length() == 8) {
			            	//判断时间是否是有效值
			            	isValidDate = FileUtils.isValidDate(str);
			            	if(isValidDate){
			            		return str;
			            	}
			            }else {
			                listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col+data + "-" + "日期格式错误");
			                j++;
			            }
			        }catch (NumberFormatException e){
			            listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col+data + "-" + "日期格式错误");
			            j++;
			            return "";
			        }
			        listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col +data+ "-" + "日期格式错误");
			        j++;
			        return "";
					}
			    
			   /* private String checkDateFormat(int row, String col, String data){
	                boolean isValidDate=false;
	                try {
	                    BigDecimal bd = new BigDecimal(data);
	                    String str = bd.toPlainString();
	                    if(str.length() == 8) {
	                    	//判断时间是否是有效值
	                    	isValidDate = FileUtils.isValidDate(str);
	                    	if(isValidDate){
	                    		return str;
	                    	}
	                    }else {
	                        listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "日期格式错误");
	                        j++;
	                    }
	                }catch (NumberFormatException e){
	                    listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "日期格式错误");
	                    j++;
	                    return "";
	                }
	                listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "日期格式错误");
	                j++;
	                return "";
	      		}*/
				
				
				
				//导出模板
			    @Override
			    public Object downloadTemplate() throws JsonProcessingException,IOException,DataAccessException{
			        List<String> headerList = new ArrayList<>();
			        
			        headerList.add(time);
			        headerList.add(yue);
			        headerList.add(lbbh);
			        headerList.add(cpxh);
			        headerList.add(lbdm);
			        headerList.add(yydm);
			        headerList.add(fsdw);
			        headerList.add(fxgx);
//			        headerList.add(th);
			        headerList.add(cpmc);
			        headerList.add(gzsl);
			        headerList.add(dw);
			        headerList.add(wtms);
			        headerList.add(djbh);
			        headerList.add(zt);
			        headerList.add(gsss);
			        headerList.add(clss);
			        headerList.add(qtss);
			        headerList.add(yjss);
			        headerList.add(sjsse);
			        headerList.add(pcje);
			        headerList.add(zrdw);
//			        headerList.add(jnw);
//			        headerList.add(xzjx);
//			        headerList.add(ddh);
//			        headerList.add(ylh);
			        headerList.add(xmlx);
			        headerList.add(czbs);
//			        headerList.add(isfh);
//			        headerList.add(spqk);
//			        headerList.add(fx);
//			        headerList.add(cgy);
         

			        String secondTitle = null;
			        ExcelExportUtil2227 ee = new ExcelExportUtil2227("厂内产品质量信息表模板", secondTitle, headerList);
			        return ee;

			    }



				@Override
				public ExcelExportUtil2227 getexportExcel(QueryPojo2 queryBean) {
					// TODO Auto-generated method stub
					return null;
				}


				@Override
				public String getLossTypeCode(String name, Integer start, Integer limit)
						throws JsonProcessingException, IOException, DataAccessException {
					List<String> accitypelList = new ArrayList<String>();
					accitypelList= ireportmapperkpi2227.getLossTypeCode(name,start,limit);
					return objectMapper.writeValueAsString(accitypelList);
				}
				
				
				@Override
				public String getOccurPlant(String name, Integer start, Integer limit)
						throws JsonProcessingException, IOException, DataAccessException {
					List<String> accitypelList = new ArrayList<String>();
					accitypelList= ireportmapperkpi2227.getOccurPlant(name,start,limit);
					return objectMapper.writeValueAsString(accitypelList);
				}

				@Override
				public String getDutyPlant(String name, Integer start, Integer limit)
						throws JsonProcessingException, IOException, DataAccessException {
					List<String> accitypelList = new ArrayList<String>();
					accitypelList= ireportmapperkpi2227.getDutyPlant(name,start,limit);
					return objectMapper.writeValueAsString(accitypelList);
				}

				
				

		



			

}
