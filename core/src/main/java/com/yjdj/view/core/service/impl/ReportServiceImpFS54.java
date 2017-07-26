package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS54;

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
import com.yjdj.view.core.mapper.IReportMapperFS54;
import com.yjdj.view.core.service.IReportServiceFS54;
import com.yjdj.view.core.util.ExcelExportUtil54;
import com.yjdj.view.core.util.ExcelImportUtil54;

/**
 * create on 2013/3/30
 * @author yangzhijie
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS54 implements IReportServiceFS54 {

    private final Logger log = Logger.getLogger(ReportServiceImpFS54.class);
    
    @Autowired
    private IReportMapperFS54 iReportMapperFS54;
    
    private List<String> cpxh = null;//产品型号
    private List<String> aname = null; //配件名称
    private List<String> matnrValue = null;//物料多值
    private List<Map<String, String>> matnrIntervalMap = null;//物料多区间
    private List<String> trainmodel = null; //使用车型
    private List<String> fwz = null; //服务站
    private List<String> fwd = null; //服务点 
    private boolean isExport = false;
    private String yearMonth = null;//年月
    
    @Override
    public Object getReportJson(QueryBean queryBean) throws JsonProcessingException, DataAccessException, IOException {
        Object mapList = null;
        //初始化数据
        init(queryBean);
        mapList = getMapList(cpxh,aname,matnrValue,matnrIntervalMap,trainmodel,fwz,fwd,isExport,yearMonth);
		return mapList;
    }

    private void initTitleRow1(List<Map<String, String>> titleRow1) {
    	Map<String,String> e1 = new HashMap<String,String>();
    	e1.put("序号", "1");
    	Map<String,String> e2 = new HashMap<String,String>();
    	e2.put("年月", "1");
    	Map<String,String> e3 = new HashMap<String,String>();
    	e3.put("产品类别", "1");
    	Map<String,String> e4 = new HashMap<String,String>();
    	e4.put("产品名称", "1");
    	Map<String,String> e5 = new HashMap<String,String>();
    	e5.put("产品型号", "1");
    	Map<String,String> e6 = new HashMap<String,String>();
    	e6.put("配件名称", "1");
    	Map<String,String> e7 = new HashMap<String,String>();
    	e7.put("配件型号", "1");
    	Map<String,String> e8 = new HashMap<String,String>();
    	e8.put("物料编码", "1");
    	Map<String,String> e9 = new HashMap<String,String>();
    	e9.put("使用车型", "1");
    	Map<String,String> e10 = new HashMap<String,String>();
    	e10.put("厂外储备总数量", "1");
    	Map<String,String> e11 = new HashMap<String,String>();
    	e11.put("厂内库存", "1");
    	titleRow1.add(e1);
    	titleRow1.add(e2);
    	titleRow1.add(e3);
    	titleRow1.add(e4);
    	titleRow1.add(e5);
    	titleRow1.add(e6);
    	titleRow1.add(e7);
    	titleRow1.add(e8);
    	titleRow1.add(e9);
    	titleRow1.add(e10);
    	titleRow1.add(e11);
	}

	/**
     * 初始化数据
     *
     * @param queryBean
     */
    private void init(QueryBean queryBean) {
        cpxh = queryBean.getCpxh();//产品型号
        aname = queryBean.getAname();
        List<String> matnrValueTemp = queryBean.getMatnrValue();//物料多值临时（未去掉前置0）
        List<Map<String, String>> matnrIntervalMapTemp = queryBean.getMatnrIntervalMap(); //物料多区间临时（未去掉前置0）
        matnrValue = new LinkedList<String>();
        matnrIntervalMap = new LinkedList<Map<String, String>>();
       
        //将物料编码的前置0去掉
        if(matnrValueTemp != null){
        	for(String value:matnrValueTemp){
            	String str = value.replaceAll("^(0+)", "");
            	matnrValue.add(str);
            }
        }else{
        	matnrValue = null;
        }
        
        if(matnrIntervalMapTemp != null){
        	for(Map<String,String> value:matnrIntervalMapTemp){
            	Set<Entry<String,String>> entrySet = value.entrySet();
            	Iterator<Entry<String,String>> it =entrySet.iterator();
            	Map<String,String> tempMap = new HashMap<String,String>();
            	while(it.hasNext()){
            		Map.Entry<String, String> me = it.next();
            		String str1 = me.getKey().replaceAll("^(0+)", "");
            		String str2 = me.getValue().replaceAll("^(0+)", "");
            		tempMap.put(str1, str2);
            		matnrIntervalMap.add(tempMap);
            	}
            }
        }else{
        	matnrIntervalMap = null;
        }        
        
//        System.out.println("------------------" + matnrValue);
//        System.out.println("+++++++++++++++++" + matnrIntervalMap);
        
		trainmodel = queryBean.getTrainmodel();//使用车型
		fwz = queryBean.getFwz();//服务站
		fwd = queryBean.getFwd();//服务点        
		isExport = queryBean.isExport();		
		if(queryBean.getDateYearMonth()!=null){
			yearMonth = queryBean.getDateYearMonth();//年月
		}else{
			Date d =new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			yearMonth = sdf.format(d);
		}
    }
    

    /**
     * 查询数据的主方法
     *
     */
    public Object getMapList(List<String> cpxh,List<String> aname,List<String> matnrValue,List<Map<String, String>> matnrIntervalMap,
    		List<String> trainmodel,List<String> fwz,List<String> fwd,boolean isExport,String yearMonth) throws JsonProcessingException, DataAccessException, IOException {
        // TODO Auto-generated method stub
    	List<Map<String, String>> titleRow1 = new LinkedList<Map<String, String>>();
        List<Map<String, String>> titleRow2 = new LinkedList<Map<String, String>>();
        List<Map<String, String>> titleRow3 = new LinkedList<Map<String, String>>();
        List<Map<String, String>> titleRow4 = new LinkedList<Map<String, String>>();
        
        List<String> resultDataRowList = new LinkedList<String>();    
        Map<String,List<String>> resultDataRowMap = new HashMap<String,List<String>>();
        Map<String,Object> listMap = new HashMap<String,Object>();//最终结果        
        int resultTypeNum = 1;//一共有几行
        int resultColNum =11;//一共有几列
        int resultDataRowNum = 1;//为resultDataRowMap服务，标记当前是第几行数据
    	
    	List<THM_FS54> resultData = new LinkedList<THM_FS54>();
    	String json = null;
    	ObjectMapper mapper = new ObjectMapper();
    	
    	//初始化titleRow1;
        initTitleRow1(titleRow1);
        Map<String,String> titleRowE = new HashMap<String,String>();
        
    	resultData = iReportMapperFS54.selectData(cpxh, aname, matnrValue, matnrIntervalMap, trainmodel, fwz, fwd,yearMonth);    	
    	
    	if(resultData.isEmpty()){    		
    		json = mapper.writeValueAsString(null);
    		return json;
    	}
    	
    	//初始化resultTypeNum
    	for(int i=1;i<resultData.size();i++){
    		if(resultData.get(i).getPclass().equals(resultData.get(i-1).getPclass()) && 
    		   resultData.get(i).getPname().equals(resultData.get(i-1).getPname()) && 
    		   resultData.get(i).getPmodel().equals(resultData.get(i-1).getPmodel()) && 
    		   resultData.get(i).getPclass().equals(resultData.get(i-1).getPclass()) && 
    		   resultData.get(i).getPclass().equals(resultData.get(i-1).getPclass()) && 
    		   resultData.get(i).getPname().equals(resultData.get(i-1).getPname()) && 
    		   resultData.get(i).getPmodel().equals(resultData.get(i-1).getPmodel()) && 
    		   resultData.get(i).getPclass().equals(resultData.get(i-1).getPclass()) && 
    		   resultData.get(i).getPclass().equals(resultData.get(i-1).getPclass())){
    			continue;
    		}else{
    			resultTypeNum = resultTypeNum+1;
    		}
    	}
    	
    	resultColNum += resultData.size()/resultTypeNum*3;

    	//标题栏第一行
    	int x = 3;
    	for(int i=1;i<resultData.size()/resultTypeNum;i++){
    		if(resultData.get(i).getRailstation().equals(resultData.get(i-1).getRailstation())){
    			x = x + 3;
    		}else{
    			titleRowE.put(resultData.get(i-1).getRailstation(), String.valueOf(x));
    			titleRow1.add(titleRowE);
    			titleRowE = new HashMap<String,String>();
    			x = 3;
    		}
    	}
    	titleRowE.put(resultData.get(resultData.size()/resultTypeNum-1).getRailstation(), String.valueOf(x));
		titleRow1.add(titleRowE);
		titleRowE = new HashMap<String,String>();
		listMap.put("title1", titleRow1);
		
		//标题栏第二行
		x = 3;
		for(int i=1;i<resultData.size()/resultTypeNum;i++){
    		if(resultData.get(i).getFwz().equals(resultData.get(i-1).getFwz()) && resultData.get(i).getRailstation().equals(resultData.get(i-1).getRailstation())){
    			x = x + 3;
    		}else{
    			titleRowE.put(resultData.get(i-1).getFwz(), String.valueOf(x));
    			titleRow2.add(titleRowE);
    			titleRowE = new HashMap<String,String>();
    			x = 3;
    		}
    	}
    	titleRowE.put(resultData.get(resultData.size()/resultTypeNum-1).getFwz(), String.valueOf(x));
		titleRow2.add(titleRowE);
		titleRowE = new HashMap<String,String>();
		listMap.put("title2", titleRow2);
    	
		//标题栏第三行
		for(int i=0;i<resultData.size()/resultTypeNum;i++){
			titleRowE.put(resultData.get(i).getFwd(), "3");
			titleRow3.add(titleRowE);
			titleRowE = new HashMap<String,String>();
		}
		listMap.put("title3", titleRow3);
		
		//标题栏第四行
		for(int i=0;i<resultData.size()/resultTypeNum;i++){
			titleRowE.put("正常", "1");
			titleRow4.add(titleRowE);
			titleRowE = new HashMap<String,String>();
			titleRowE.put("非正常", "1");
			titleRow4.add(titleRowE);
			titleRowE = new HashMap<String,String>();
			titleRowE.put("转销售", "1");
			titleRow4.add(titleRowE);
			titleRowE = new HashMap<String,String>();
		}
		listMap.put("title4", titleRow4);
		
		//内容
		resultDataRowList.add(resultData.get(0).getYearmonth());
		resultDataRowList.add(resultData.get(0).getPclass());
		resultDataRowList.add(resultData.get(0).getPname());
		resultDataRowList.add(resultData.get(0).getPmodel());
		resultDataRowList.add(resultData.get(0).getAname());
		resultDataRowList.add(resultData.get(0).getAmodel());
		resultDataRowList.add(resultData.get(0).getMatnr());
		resultDataRowList.add(resultData.get(0).getTraintype());
		resultDataRowList.add(resultData.get(0).getOutnum());
		resultDataRowList.add(resultData.get(0).getInnum());
		resultDataRowList.add(resultData.get(0).getPyes());
		resultDataRowList.add(resultData.get(0).getPno());
		resultDataRowList.add(resultData.get(0).getPsale());
		
		for(int i=1;i<resultData.size();i++){
			if(resultData.get(i).getPclass().equals(resultData.get(i-1).getPclass()) && 
			    resultData.get(i).getPname().equals(resultData.get(i-1).getPname()) && 
			    resultData.get(i).getPmodel().equals(resultData.get(i-1).getPmodel()) && 
			    resultData.get(i).getPclass().equals(resultData.get(i-1).getPclass()) && 
			    resultData.get(i).getPclass().equals(resultData.get(i-1).getPclass()) && 
			    resultData.get(i).getPname().equals(resultData.get(i-1).getPname()) && 
			    resultData.get(i).getPmodel().equals(resultData.get(i-1).getPmodel()) && 
			    resultData.get(i).getPclass().equals(resultData.get(i-1).getPclass()) && 
			    resultData.get(i).getPclass().equals(resultData.get(i-1).getPclass())){
			    resultDataRowList.add(resultData.get(i).getPyes());
				resultDataRowList.add(resultData.get(i).getPno());
				resultDataRowList.add(resultData.get(i).getPsale());
			}else{
				resultDataRowMap.put(String.valueOf(resultDataRowNum), resultDataRowList);
				resultDataRowNum = resultDataRowNum + 1;
				resultDataRowList = new LinkedList<String>();
				resultDataRowList.add(resultData.get(i).getYearmonth());
				resultDataRowList.add(resultData.get(i).getPclass());
				resultDataRowList.add(resultData.get(i).getPname());
				resultDataRowList.add(resultData.get(i).getPmodel());
				resultDataRowList.add(resultData.get(i).getAname());
				resultDataRowList.add(resultData.get(i).getAmodel());
				resultDataRowList.add(resultData.get(i).getMatnr());
				resultDataRowList.add(resultData.get(i).getTraintype());
				resultDataRowList.add(resultData.get(i).getOutnum());
				resultDataRowList.add(resultData.get(i).getInnum());
				resultDataRowList.add(resultData.get(i).getPyes());
				resultDataRowList.add(resultData.get(i).getPno());
				resultDataRowList.add(resultData.get(i).getPsale());
			}			
		}
		resultDataRowMap.put(String.valueOf(resultDataRowNum), resultDataRowList);
		listMap.put("content", resultDataRowMap);
		if(isExport){
			//导出数据
			ExcelExportUtil54 ee = new ExcelExportUtil54("备品配件统计表",null,resultColNum);
			List<String> exportList1 = new LinkedList<String>();
			List<String> exportList2 = new LinkedList<String>();
			List<String> exportList3 = new LinkedList<String>();
			
			List<int[]> mergeList_1 = new LinkedList<int[]>();//第1行横向合并所需数据
	        List<int[]> mergeList_2 = new LinkedList<int[]>();//第2行横向合并所需数据
	        List<int[]> mergeList_3 = new LinkedList<int[]>();//第3行横向合并所需数据
			
	        //导出第一行
			int indexRow1 = 0;
			for(Map<String,String> value:titleRow1){
				Set<Entry<String, String>> entrySet = value.entrySet();
				Iterator<Entry<String, String>> it1 = entrySet.iterator();				
				while(it1.hasNext()){
					Map.Entry<String, String> me = it1.next();
					String k = me.getKey();
					String v = me.getValue();
					int intV = Integer.valueOf(v);
					
					int[] merge = new int[2];
                    merge[0] = indexRow1;
                    merge[1] = indexRow1 + intV - 1;
                    if(merge[0]!=merge[1]){
                    	mergeList_1.add(merge);
                    }                    
					indexRow1 = merge[1] + 1;
					exportList1.add(k);
					for(int i=1;i<intV;i++){
						exportList1.add("");
					}
				}				
			}
//			System.out.println(exportList1);
//			for(int[] i:mergeList_1){
//	            System.out.print(i[0] + " + ");
//	            System.out.print(i[1]);
//	            System.out.println();
//	        }
			Row row = ee.addRow();
			for (int i = 0; i < exportList1.size(); i++) {				
	    		ee.addCell(row, i, exportList1.get(i));
	    	}
			ee = getColumnMergeExcel(ee,1,mergeList_1);//对第1行进行合并操作
			//对第1至4行，1至11列进行合并
			for(int i=0;i<11;i++){
				ee.addMergedRegion(1,4,i,i);
			}
			
			//导出第二行
			int indexRow2 = 11;
			for(Map<String,String> value:titleRow2){
				Set<Entry<String, String>> entrySet = value.entrySet();
				Iterator<Entry<String, String>> it2 = entrySet.iterator();				
				while(it2.hasNext()){
					Map.Entry<String, String> me = it2.next();
					String k = me.getKey();
					String v = me.getValue();
					int intV = Integer.valueOf(v);
					
					int[] merge = new int[2];
                    merge[0] = indexRow2;
                    merge[1] = indexRow2 + intV - 1;
                    if(merge[0]!=merge[1]){
                    	mergeList_2.add(merge);
                    }
					indexRow2 = merge[1] + 1;
					exportList2.add(k);
					for(int i=1;i<intV;i++){
						exportList2.add("");
					}
				}				
			}
			
			Row row2 = ee.addRow();
			for (int i = 0;i<11;i++){
				ee.addCell(row2, i, "");
			}
			for (int i = 0; i < exportList2.size(); i++) {
	    		ee.addCell(row2, i+11, exportList2.get(i));
	    	}
			ee = getColumnMergeExcel(ee,2,mergeList_2);//对第2行进行合并操作
			
			//导出第三行
			int indexRow3 = 11;
			for(Map<String,String> value:titleRow3){
				Set<Entry<String, String>> entrySet = value.entrySet();
				Iterator<Entry<String, String>> it3 = entrySet.iterator();				
				while(it3.hasNext()){
					Map.Entry<String, String> me = it3.next();
					String k = me.getKey();
					String v = me.getValue();
					int intV = Integer.valueOf(v);
					
					int[] merge = new int[2];
                    merge[0] = indexRow3;
                    merge[1] = indexRow3 + intV - 1;
                    if(merge[0]!=merge[1]){
                    	mergeList_3.add(merge);
                    }
					indexRow3 = merge[1] + 1;
					exportList3.add(k);
					for(int i=1;i<intV;i++){
						exportList3.add("");
					}
				}				
			}
			
			
			Row row3 = ee.addRow();
			for (int i = 0;i<11;i++){
				ee.addCell(row3, i, "");
			}
			for (int i = 0; i < exportList3.size(); i++) {
	    		ee.addCell(row3, i+11, exportList3.get(i));
	    	}
			ee = getColumnMergeExcel(ee,3,mergeList_3);//对第3行进行合并操作
			
			//导出第四行
			Row row4 = ee.addRow();
			for (int i = 0;i<11;i++){
				ee.addCell(row4, i, "");
			}
			for(int i=0;i<resultData.size()/resultTypeNum*3;){
				ee.addCell(row4, i+11, "正常");
				ee.addCell(row4, i+12, "非正常");
				ee.addCell(row4, i+13, "转销售");
				i = i +3;
			}
			
			//导出数据
			for(int i=1;i<=resultDataRowNum;i++){
				List<String> tempList = new LinkedList<String>();
				tempList = resultDataRowMap.get(String.valueOf(i));
				Row rowContent = ee.addRow();
				ee.addCell(rowContent, 0, String.valueOf(i));
				for(int j=0;j<tempList.size();j++){
					ee.addCell(rowContent, j+1, tempList.get(j));
				}
			}			
			
			return ee;
		}else{
			json = mapper.writeValueAsString(listMap);
			return json; 
		}		
    }
    
    
    /**
     * 传入Excel文件，对指定单元格进行合并，返回Excel文件
     * @param ee  Excel文件
     * @param row  指定行
     * @param mergeList  指定列的集合，元素为int数组格式，int[0]为开始列，int[1]为结束列
     * @return
     */
    private ExcelExportUtil54 getColumnMergeExcel(ExcelExportUtil54 ee,int row,List<int[]> mergeList){
        for(int[] i:mergeList){
            ee.addMergedRegion(row,row,i[0],i[1]);
        }
        return ee;
    }
    
    

    
    /**
     * 导入数据
     */
    @Override
    public Object importData(String filePath) throws JsonProcessingException,
            IOException, DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException {
        // TODO Auto-generated method stub
        String yearMonth = null;
        String json = null;
        String tempCell = null;
        List<String> headerList1 = new ArrayList<String>();//路局，从第11位开始，前面的不用
        List<String> headerList2 = new ArrayList<String>();//服务站，从第11位开始，前面的不用
        List<String> headerList3 = new ArrayList<String>();//服务点，从第11位开始，前面的不用
        List<String> headerList4 = new ArrayList<String>();//正非转，从第11位开始，前面的不用
        List<String> preData = new ArrayList<String>();//前置数据
        List<String> everyData = new ArrayList<String>();//每条数据
        List<List<String>> rowData = Lists.newArrayList();//每行的所有条数据
        
        List<Map<String, String>> fwzMapList = new LinkedList<Map<String, String>>();//所有的服务站MapList
        List<Map<String, String>> fwdMapList = new LinkedList<Map<String, String>>();//所有的服务点MapList
        List<Map<String, String>> cxMapList = new LinkedList<Map<String, String>>();//所有的车型MapList
        List<String> fwzList = new LinkedList<String>();//所有的服务站
        List<String> fwdList = new LinkedList<String>();//所有的服务点
        List<String> cxList = new LinkedList<String>();//所有的车型
        List<String> yesNoList = new LinkedList<String>();//正常非正常转销售校验        
        List<String> matnrListInExcel = new LinkedList<String>();//待检查物料编码
        List<String> matnrRight = new LinkedList<String>();//正确的物料编码
        
        List<String> dataError = new LinkedList<String>();//服务站服务点校验错误信息
        
        //初始化yesNoList
        yesNoList.add("正常");
        yesNoList.add("非正常");
        yesNoList.add("转销售");
        
        fwzMapList = selectFwz("","");
        for(Map<String,String> value:fwzMapList){
        	String TempFwz = value.get("FWZ");
        	fwzList.add(TempFwz);
        }
        
        fwdMapList = selectFwd("","");
        for(Map<String,String> value:fwdMapList){
        	String TempFwd = value.get("FWD");
        	fwdList.add(TempFwd);
        }
        
        cxMapList = selectCx("","");
        for(Map<String,String> value:cxMapList){
        	String TempCx = value.get("CX");
        	cxList.add(TempCx);
        }
        
        Map<String, Object> listMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        //导入数据
        ExcelImportUtil54<?> ei = new ExcelImportUtil54(filePath, 1);
        int lastCellNum = ei.getLastCellNum();//总列数
        int lastRowNum = ei.getLastDataRowNum();//总行数
        yearMonth = (String) ei.getCellValue(5, 1);
        
        //初始化matnrListInExcel
        for(int i=5;i<lastRowNum;i++){
        	String tempMatnr = (String) ei.getCellValue(i, 7);
        	matnrListInExcel.add(tempMatnr);
        }
        matnrRight = selectMaterialInList(matnrListInExcel);
        
        //校验年月格式是否正确
        for(int i=5;i<lastRowNum;i++){
        	String tempYearMonth = (String) ei.getCellValue(i, 1);
        	if(!isValidYearMonth(tempYearMonth)){
        		String tempStr = "年月格式错误在第" + (i+1) +"行(" + tempYearMonth +")";
    			dataError.add(tempStr);
        	}
        } 
      //校验是否是同一月份
        for(int i=6;i<lastRowNum;i++){
        	String tempYearMonth = (String) ei.getCellValue(i, 1);
        	if(!tempYearMonth.equals(yearMonth)){
        		listMap.put("ERRORTIME", "所填月份不一致!");
            	json = mapper.writeValueAsString(listMap);
            	return json;
        	}
        }
      //校验车型是否正确
        for(int i=5;i<lastRowNum;i++){
        	String tempCx = (String) ei.getCellValue(i, 8);
        	String[] tempCxArray = null;
        	int index = tempCx.indexOf("、");
        	if(index > -1){
        		tempCxArray = tempCx.split("、");        		
        		for(String a:tempCxArray){
            		if(!findInList(a,cxList)){
                		String tempStr = "不存在的车型在第" + (i+1) +"行(" + a +")";
            			dataError.add(tempStr);
                	}
            	}
        	}else{
        		if(!findInList(tempCx,cxList)){
            		String tempStr = "不存在的车型在第" + (i+1) +"行(" + tempCx +")";
        			dataError.add(tempStr);
            	}
        	}
        	
        } 
        
      //校验物料编码是否正确
        for(int i=5;i<lastRowNum;i++){
        	String tempMatnr = (String) ei.getCellValue(i, 7);
        	if(!(matnrRight.contains(tempMatnr))){
        		String tempStr = "不存在的物料编码在第" + (i+1) +"行(" + tempMatnr +")";
    			dataError.add(tempStr);
        	}
        } 
        
      //第一行表头 路局
        for(int i=0;i<lastCellNum;i++){
        	if((String)ei.getCellValue(1, i)!=""){
        		tempCell = (String)ei.getCellValue(1, i);
        		headerList1.add(tempCell);
        	}else{
        		headerList1.add(tempCell);
        	}
        }
        tempCell = null;
        //第二行表头 服务站
        for(int i=0;i<lastCellNum;i++){
        	if((String)ei.getCellValue(2, i)!=""){
        		tempCell = (String)ei.getCellValue(2, i);
        		if(findInList(tempCell,fwzList)){
        			headerList2.add(tempCell);
        		}else{
        			String tempStr = "不存在的服务站在第" + (i+1) +"列(" + tempCell +")";
        			dataError.add(tempStr);
        		}        		
        	}else{
        		headerList2.add(tempCell);
        	}
        }        
        tempCell = null;
        
        //第三行表头 服务点
        for(int i=0;i<lastCellNum;i++){
        	if((String)ei.getCellValue(3, i)!=""){
        		tempCell = (String)ei.getCellValue(3, i);
        		if(findInList(tempCell,fwdList)){
        			headerList3.add(tempCell);
        		}else{
        			String tempStr = "不存在的服务点在第" + (i+1) +"列(" + tempCell +")";
        			dataError.add(tempStr);
        		}
        	}else{
        		headerList3.add(tempCell);
        	}
        }
        tempCell = null;        
        
        //第四行表头 正非转
        for(int i=0;i<lastCellNum;i++){
        	if((String)ei.getCellValue(4, i)!=""){
        		tempCell = (String)ei.getCellValue(4, i);
        		if(findInList(tempCell,yesNoList)){
        			headerList4.add(tempCell);
        		}else{
        			String tempStr = "表头信息错误在第" + (i+1) +"列(" + tempCell +")";
        			dataError.add(tempStr);
        		}
        	}else{
        		headerList4.add(tempCell);
        	}
        }
        
        if(dataError.size()!=0){
        	listMap.put("ERRORDATA", dataError);
            json = mapper.writeValueAsString(listMap);
            return json;
        }
        
        //数据部分
        for(int i=5;i<lastRowNum;i++){
        	for(int j=1;j<=2;j++){
        		preData.add((String) ei.getCellValue(i, j));
        	}
        	//读取产品名称并排除斜杠
        	String tempStrPname = (String) ei.getCellValue(i, 3) + " ";
        	preData.add(tempStrPname);
        	
        	for(int j=4;j<=10;j++){
        		preData.add((String) ei.getCellValue(i, j));
        	}
        	
        	int k=11;
        	int rowCount = 0;
        	while(k<lastCellNum-1){
        		everyData.addAll(preData);
        		everyData.add((String) ei.getCellValue(i, k));
        		everyData.add((String) ei.getCellValue(i, k+1));
        		everyData.add((String) ei.getCellValue(i, k+2));
        		everyData.add(headerList1.get(k));
        		everyData.add(headerList2.get(k));
        		everyData.add(headerList3.get(k));
        		rowData.add(everyData);
        		rowCount++;
        		if(rowCount == 2000){
        			iReportMapperFS54.saveData(rowData, yearMonth);
        			rowData.clear();
        			rowCount = 0;
        		}
            	everyData = new ArrayList<String>();
        		k=k+3;
        	}
        	preData = new ArrayList<String>();
        }
        iReportMapperFS54.saveData(rowData, yearMonth);
        listMap.put("SUCCESSES", "导入成功!");
        json = mapper.writeValueAsString(listMap);
        return json;
        
    }
    
    /**
     * 从主数据表查询所有服务站
     */
    public List<Map<String,String>> selectFwz(String fwzLike,String fwzIdLike) throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException{
    	List<Map<String,String>> fwzList = new ArrayList<Map<String,String>>();    	
    	fwzList = iReportMapperFS54.selectFwz(fwzLike,fwzIdLike);
    	return fwzList;
    }
    
    /**
     * 从主数据表查询所有服务点
     */
    public List<Map<String,String>> selectFwd(String fwdLike,String fwdIdLike) throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException{
    	List<Map<String,String>> fwdList = new ArrayList<Map<String,String>>();
    	fwdList = iReportMapperFS54.selectFwd(fwdLike,fwdIdLike);
    	return fwdList;
    }
    
    /**
     * 从主数据表查询所有车型
     */
    public List<Map<String,String>> selectCx(String cxLike,String cxIdLike) throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException{
    	List<Map<String,String>> cxList = new ArrayList<Map<String,String>>();
    	cxList = iReportMapperFS54.selectCx(cxLike,cxIdLike);
    	return cxList;
    }
    
    /**
     * 从THM_FS54查询所有产品型号
     */
    public List<String> getCpxh(String cpxhLike) throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException{
    	List<String> cpxhList = new ArrayList<String>();
    	cpxhList = iReportMapperFS54.getCpxh(cpxhLike);
    	return cpxhList;
    }
    
    /**
     * 从THM_FS54查询所有配件名称
     */
    public List<String> getAname(String anameLike) throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException{
    	List<String> anameList = new ArrayList<String>();
    	anameList = iReportMapperFS54.getAname(anameLike);
    	return anameList;
    }
    
    /**
     * 自定义findInList方法
     * @param str  待查字符串
     * @param strList 字符串List
     */
    public boolean findInList(String str,List<String> strList){
    	boolean b = false;
    	for(int i=0;i<strList.size();i++){
    		if(strList.get(i).equals(str)){
    			b = true;
    			break;
    		}
    	}
    	return b;
    }
    
    /**
     * 自定义正则校验年月方法
     */
    public boolean isValidYearMonth(String str){
    	boolean b = false;
    	String regEx = "[1|2]{1}[0-9]{3}(0[1-9]|1[0-2]){1}";
    	Pattern pattern = Pattern.compile(regEx);
    	Matcher matcher = pattern.matcher(str);
    	b = matcher.matches();
    	return b;
    }
    
    /**
     * 从MDM_TMATERIAL查询自定义物料List，找出哪些是存在的
     */
    public List<String> selectMaterialInList(List<String> materialList) throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException{
    	List<String> matnrInList = new ArrayList<String>();
    	matnrInList = iReportMapperFS54.selectMaterialInList(materialList);
    	return matnrInList;
    }
    

    
}
