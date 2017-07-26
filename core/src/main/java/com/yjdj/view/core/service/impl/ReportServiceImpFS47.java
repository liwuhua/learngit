package com.yjdj.view.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yjdj.view.core.entity.mybeans.*;
import com.yjdj.view.core.mapper.IReportMapperFS47;
import com.yjdj.view.core.service.IReportServiceFS47;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.ExcelImportUtil;
import com.yjdj.view.core.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.util.*;

/**
 * Created by zhuxuan on 2017/3/29.
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS47 implements IReportServiceFS47{
    @Autowired
    private IReportMapperFS47 iReportMapperFS47;
    private ObjectMapper mapper = new ObjectMapper();
    private final static String EXCELNAME = "配属表";
    private final static String TOTAL_DISTANCE_STR = "total_distance";
    private final static String COL = "col";
    private final static String ROW = "row";
    private final static String NAME = "name";
    private final static String SUBHEADER = "subheader";
    private final static Integer GENERALCOL = 14;
    //表头
    private final static String SEQNUM = "序号";
    private final static String TRAINTYPE = "车型";
    private final static String TRAINNUM = "车号";
    private final static String MANUFACTURER = "造修厂家";
    private final static String BUREAU_SUBJECTION = "隶属路局";
    private final static String PLACE_SUBJECTION = "配属段、所";
    private final static String REPAIR_DATE = "车造/修日期";
    private final static String REPAIR_LEVEL = "车修程类别";
    private final static String DISTANCE_REACH_REPAIRLVL = "检修时走行里程";
    private final static String TOTAL_DISTANCE = "总走行里程";
    private final static String DISTANCE_AFTER_REPAIRLVL = "检后走行里程";
    private final static String REMARKS = "备注";
    private final static String REPORT_NAME = "信息提报人";
    private final static String REPORT_TIME = "信息提报日期";
    private final static String DATEFORMAT_Y_M_D = "yyyy-MM-dd";
    //以下为查询导出所需变量
    private Integer INIT = 0;
    private String train_type;//车型
    private List<String> train_num_List;//车号
    private List<String> manufacturer_List;//造修厂家
    private List<String> bureau_subjection_List;//隶属路局
    private List<String> place_subjection_List;//配属段所
    private List<String> repair_level_List;//车修程类别
    private List<String> motor_serial_num_List;//产品编号
    private ExcelExportUtil ee = null;
    //以下为导入所需变量
    private Map<String, Object> listMap = null;
    Map<String,List<String>> errorData = null;
    List<String> errorList = null;
    private StringBuffer errorStr = null;
    private final static Integer MOTORSERIALNUMSTART = 11;//产品编号在导入模板中开始于第10列
    private Integer motorSerialNumCount = null;//产品数量

    /**
     * 查询数据/导出数据
     * @param queryPojo
     * @param action  0下载模板;1查询;2导出;
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     * @throws DataAccessException
     */
    public Object getReportJson(QueryPojo queryPojo, int action) throws JsonProcessingException, IOException,DataAccessException{
        train_type = queryPojo.getTrain_type();//车型
        train_num_List = Lists.newArrayList();
        if(queryPojo.getTrain_num().isEmpty()){//车号
            try {
                List<Map<String, String>> train_numList = iReportMapperFS47.getTrainNum(train_type);
                for(Map<String,String> tmp : train_numList){
                    train_num_List.add(tmp.get("train_num"));
                }
            }catch (NullPointerException e){
                errorList.add("车型 "+train_type+" 无车号。");
            }
        }else {
            train_num_List = queryPojo.getTrain_num();
        }
        repair_level_List = Lists.newArrayList();
        if(queryPojo.getRepair_level().isEmpty()){//车修程类别
            List<Map<String,String>> repair_levelList = iReportMapperFS47.getAllRepairLevel();
            for(Map<String,String> tmp : repair_levelList){
                repair_level_List.add(tmp.get("repair_level"));
            }
        }else {
            repair_level_List = queryPojo.getRepair_level();
        }
        manufacturer_List = queryPojo.getManufacturer();//造修厂家
        bureau_subjection_List = queryPojo.getBureau_subjection();//隶属路局
        place_subjection_List = queryPojo.getPlace_subjection();//配属段所
        motor_serial_num_List = queryPojo.getMotor_serial_num();//产品编号
        //根据车型、车号、车修程类别 取数据，并组装成所需报表样式
        List<List<String>> dataList = Lists.newLinkedList();
        for(String train_num : train_num_List){
            for(String repair_level : repair_level_List) {
                //取总走行里程
                String total_distance = "";
                try {
                    Map<String, String> totalDistance = iReportMapperFS47.getTotalDistance(train_type, train_num);
                    total_distance = totalDistance.get(TOTAL_DISTANCE_STR);
                }catch (NullPointerException e){
                    total_distance = "";
                }
                //取电机编号
                List<THM_FS47_ALLOCATION> allocationDataBean = Lists.newArrayList();
                try {
                    allocationDataBean = iReportMapperFS47.getData(train_type, train_num, repair_level
                            , manufacturer_List, bureau_subjection_List, place_subjection_List, motor_serial_num_List);
                }catch (NullPointerException e){
                    errorList.add("现有查询条件无法查询到数据");
                }
                //将电机编号按顺序（顺序查询时已排序）存入List中
                List<String> motorSerialNumList = Lists.newLinkedList();
                for (THM_FS47_ALLOCATION tmp : allocationDataBean) {
                    motorSerialNumList.add(tmp.getMotor_serial_num());
                }
                //取通用部分的数据，并和电机编号部分合成一行数据
                List<String> fullData = Lists.newLinkedList();
                fullData.add(allocationDataBean.get(0).getTrain_type());//车型
                fullData.add(allocationDataBean.get(0).getTrain_num());//车号
                fullData.add(allocationDataBean.get(0).getManufacturer().isEmpty() ? "" : allocationDataBean.get(0).getManufacturer());//造修厂家
                fullData.add(allocationDataBean.get(0).getBureau_subjection().isEmpty() ? "" : allocationDataBean.get(0).getBureau_subjection());//隶属路局
                fullData.add(allocationDataBean.get(0).getPlace_subjection().isEmpty() ? "" : allocationDataBean.get(0).getPlace_subjection());// 配属段、所
                fullData.add(allocationDataBean.get(0).getRepair_date().isEmpty() ? "" : allocationDataBean.get(0).getRepair_date());//车造/修日期
                fullData.add(allocationDataBean.get(0).getRepair_level());//车修程类别
                fullData.add(allocationDataBean.get(0).getDistance_reach_repairlvl().isEmpty() ? "" : allocationDataBean.get(0).getDistance_reach_repairlvl());//检修时走行里程
                fullData.add(total_distance.isEmpty() ? "" : total_distance);//总走行里程
                fullData.add("");//检修后走行里程
                fullData.addAll(motorSerialNumList);//产品编号
                fullData.add(allocationDataBean.get(0).getRemarks().isEmpty() ? "" : allocationDataBean.get(0).getRemarks());//备注
                fullData.add(allocationDataBean.get(0).getReport_name().isEmpty() ? "" : allocationDataBean.get(0).getReport_name());//信息提报人
                fullData.add(allocationDataBean.get(0).getReport_time().isEmpty() ? "" : allocationDataBean.get(0).getReport_time());//信息提报时间
                //将完整的一行数据加入到总List中
                dataList.add(fullData);
            }
        }

        /********** 数据查询 **********/
        //构建表头
        List<THM_FS47_MAP> allocationList = iReportMapperFS47.getMotorMap(train_type);
        Map<String,Integer> colCountMap = Maps.newLinkedHashMap();
        for(THM_FS47_MAP tmp : allocationList) {
            colCountMap = checkRepeat(colCountMap, tmp.getMap_category());
        }
        Map<String,Map<String,Object>> headerMap = Maps.newLinkedHashMap();
        Integer key = 0;
        for(Map.Entry<String,Integer> entry : colCountMap.entrySet()){
            Map<String,Object> map = Maps.newHashMap();
            map.put(ROW,1);
            map.put(COL,entry.getValue());
            map.put(NAME,entry.getKey());
            List<String> subHeaderList = Lists.newLinkedList();
            for(THM_FS47_MAP tmp : allocationList){
                if(tmp.getMap_category().equals(entry.getKey())){
                    subHeaderList.add(tmp.getMap_position());
                }
            }
            map.put(SUBHEADER,subHeaderList);
            headerMap.put(key.toString(),map);
            key++;
        }
        //合并表头与数据
        Map<String,Object> resultMap = Maps.newHashMap();
        resultMap.put("DATA",dataList);
        resultMap.put("HEADER",headerMap);
        String result = mapper.writeValueAsString(resultMap);
        System.out.println(result);
        if(action == 1){//查询
            return result;
        }

        /********** 数据导出 **********/
        //初始化表头
        List<THM_FS47_MAP> allocationOrderList = iReportMapperFS47.getMotorMap(train_type);
        motorSerialNumCount = allocationOrderList.size();
        ee = getExcelHeader(queryPojo.getTrain_type(), motorSerialNumCount + GENERALCOL);//初始化表，添加大标题：表名、副标题：电机型号
        List<String> headerBeginList = Lists.newLinkedList();
        List<String> headerEndList = Lists.newLinkedList();
        headerBeginList = initHeaderBegin(headerBeginList);
        headerEndList = initHeaderEnd(headerEndList);
        List<String> headerListLine3 = Lists.newLinkedList();
        List<String> headerListLine4 = Lists.newLinkedList();
        //初始化第三行
        headerListLine3.addAll(headerBeginList);
        for(Map.Entry<String,Integer> entry : colCountMap.entrySet()){
            headerListLine3.add(entry.getKey());
            for(int i = 0; i < entry.getValue() - 1; i++)
                headerListLine3.add("");
        }
        headerListLine3.addAll(headerEndList);
        //初始化第四行
        for(int i = 0; i < headerBeginList.size(); i++)headerListLine4.add("");
        for(THM_FS47_MAP map : allocationList)headerListLine4.add(map.getMap_position());
        for(int i = 0; i < headerEndList.size(); i++)headerListLine4.add("");
        for(int i = 2; i < 4; i++) {
            ee.addRow();
            for(int j = 0; j < motorSerialNumCount + GENERALCOL; j++){
                ee.addCell(ee.getSheet().getRow(i),j, i == 2 ? headerListLine3.get(j) : headerListLine4.get(j) );
            }
        }
        //表头，合并所需单元格
        for(int i = 0; i < MOTORSERIALNUMSTART; i++)
            ee.addMergedRegion(2,3,i,i);
        for(int i = 3; i > 0; i--)
            ee.addMergedRegion(2,3,motorSerialNumCount + GENERALCOL - i,motorSerialNumCount + GENERALCOL - i);
        Integer start = MOTORSERIALNUMSTART;
        Integer end ;
        for(Map.Entry<String,Integer> entry : colCountMap.entrySet()){
            end = start + entry.getValue() - 1;
            ee.addMergedRegion(2,2,start,end);
            start = end + 1;
        }
        if(action == 2) {//下载模板
            return ee;
        }
        //数据部分
        Row dataRow = null;
        for(int r = 0; r < dataList.size(); r++){
            dataRow = ee.addRow();
            for(int c = 1; c <= dataList.get(0).size(); c++) {
                ee.addCell(dataRow, 0, r + 1);
                ee.addCell(dataRow, c, dataList.get(r).get(c-1));
            }
        }
        if(action == 0) {//导出数据
            return ee;
        }
        return null;
    }

    /**
     * 根据参数生成带表头的excel文件
     * @param trainType  电机型号
     * @param headerListLength  表头总长度（用来确定第一行需要合并的列数量）
     * @return
     */
    private ExcelExportUtil getExcelHeader(String trainType, int headerListLength){
        String secondTitle = "车型:"+trainType;
        ee = new ExcelExportUtil(EXCELNAME,secondTitle,headerListLength);
        return ee;
    }

    /**
     * 初始化表头通用部分（前11列）
     * @param list
     * @return
     */
    private List<String> initHeaderBegin(List<String> list){
        list.add(SEQNUM);
        list.add(TRAINTYPE);
        list.add(TRAINNUM);
        list.add(MANUFACTURER);
        list.add(BUREAU_SUBJECTION);
        list.add(PLACE_SUBJECTION);
        list.add(REPAIR_DATE);
        list.add(REPAIR_LEVEL);
        list.add(DISTANCE_REACH_REPAIRLVL);
        list.add(TOTAL_DISTANCE);
        list.add(DISTANCE_AFTER_REPAIRLVL);
        return list;
    }

    /**
     * 初始化表头通用部分（最后3列）
     * @param list
     * @return
     */
    private List<String> initHeaderEnd(List<String> list){
        list.add(REMARKS);
        list.add(REPORT_NAME);
        list.add(REPORT_TIME);
        return list;
    }


    /**
     * 导入数据
     * @param filePath
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     * @throws DataAccessException
     * @throws InvalidFormatException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Object importData(String filePath) throws JsonProcessingException, IOException ,DataAccessException,
            InvalidFormatException, InstantiationException, IllegalAccessException{
        errorStr = new StringBuffer();
        listMap = Maps.newHashMap();
        errorData = Maps.newHashMap();
        errorList = Lists.newArrayList();
        ExcelImportUtil ei = new ExcelImportUtil(filePath, 5);
        String motorModeltmp = ei.getCellValue(1, 0).toString().trim();//取电机型号
        String trainType = "";
        if(motorModeltmp.contains(":")) {
            trainType = motorModeltmp.trim().substring(motorModeltmp.indexOf(":") + 1);
        }else if(motorModeltmp.contains("：")){
            trainType = motorModeltmp.trim().substring(motorModeltmp.indexOf("：") + 1);
        }
        if(trainType.equals("")){
            errorStr.append("车型不能为空，请填写车型。\n");
            errorList.add(errorStr.toString());
        }
        if(!errorList.isEmpty()){
            errorData.put("ERRORDATA",errorList);
            listMap.put("ERROR",errorData);
            return mapper.writeValueAsString(listMap);
        }
        Map<String,Integer> isTrainTypeExist = iReportMapperFS47.getTrainTypeExist(trainType);
        if(isTrainTypeExist.get("isExist") == 0){
            errorList.add("主数据中无此车型，请检查导入文件或联系系统管理员.\n");
        }
        if(!errorList.isEmpty()){
            errorData.put("ERRORDATA",errorList);
            listMap.put("ERROR",errorData);
            return mapper.writeValueAsString(listMap);
        }
        //将用户导入的数据按数据库要求的存储方式读取并处理
        List<THM_FS47_MAP> allocationOrderList = iReportMapperFS47.getMotorMap(trainType);
        motorSerialNumCount = allocationOrderList.size();//取配属产品数量
        /** 校验表头 **/
        Map<String,Integer> colCountMap = Maps.newLinkedHashMap();
        for(THM_FS47_MAP tmp : allocationOrderList) {
            colCountMap = checkRepeat(colCountMap, tmp.getMap_category());
        }
        List<String> headerBeginList = Lists.newLinkedList();
        List<String> headerEndList = Lists.newLinkedList();
        headerBeginList = initHeaderBegin(headerBeginList);
        headerEndList = initHeaderEnd(headerEndList);
        List<String> headerListLine3 = Lists.newLinkedList();
        List<String> headerListLine4 = Lists.newLinkedList();
        //初始化第三行
        headerListLine3.addAll(headerBeginList);
        for(Map.Entry<String,Integer> entry : colCountMap.entrySet()){
            headerListLine3.add(entry.getKey());
            for(int i = 0; i < entry.getValue() - 1; i++)
                headerListLine3.add("");
        }
        headerListLine3.addAll(headerEndList);
        //初始化第四行
        for(int i = 0; i < headerBeginList.size(); i++)headerListLine4.add("");
        for(THM_FS47_MAP map : allocationOrderList)headerListLine4.add(map.getMap_position());
        for(int i = 0; i < headerEndList.size(); i++)headerListLine4.add("");
        //取excel里的表头
        List<List<String>> checkHeaderList = Lists.newLinkedList();
        for(int row = 2; row < 4; row ++){
            checkHeaderList.add(new LinkedList<String>());
            for(int col = 0; col < ei.getLastCellNum(); col++ ){
                checkHeaderList.get(row - 2).add(ei.getCellValue(row, col).toString());
            }
        }
        //校验表头
        if(checkHeaderList.get(0).size() != headerListLine3.size() || checkHeaderList.get(1).size() != headerListLine4.size()){
            errorList.add("导入表列数与该车型所需模板不相符。请重新下载模板进行导入。\n");
        }
        if(!errorList.isEmpty()){
            errorData.put("ERRORDATA",errorList);
            listMap.put("ERROR",errorData);
            return mapper.writeValueAsString(listMap);
        }
        for(int col = 0; col < headerListLine3.size(); col++){
            if(!checkHeaderList.get(0).get(col).equals(headerListLine3.get(col))){
                errorList.add("导入表标题与模板不符，请修改列："+checkHeaderList.get(0).get(col)+"  或重新下载模板。\n");
            }
            if(!checkHeaderList.get(1).get(col).equals(headerListLine4.get(col))){
                errorList.add("导入表标题与模板不符，请修改列："+checkHeaderList.get(1).get(col)+"  或重新下载模板。\n");
            }
        }
        if(!errorList.isEmpty()){
            errorData.put("ERRORDATA",errorList);
            listMap.put("ERROR",errorData);
            return mapper.writeValueAsString(listMap);
        }
        /** 处理导入数据 **/
        Map<String,Integer> checkMotorSerialNum = Maps.newHashMap();
        Map<String,Integer> checkTrainNum = Maps.newHashMap();
        List<THM_FS47_ALLOCATION> importDataList = Lists.newArrayList();
        int rowLength = ei.getLastDataRowNum();
        int colLength = ei.getLastCellNum();
        for(int row = 4; row < rowLength; row++){//第一层循环：导入数据按行循环
            String trainNum = null;
            for(int motorSerialNumPosition = MOTORSERIALNUMSTART;
                motorSerialNumPosition < ( MOTORSERIALNUMSTART + motorSerialNumCount );
                motorSerialNumPosition++ ){//第二层循环：导入数据的产品编号从第一个循环到最后一个
                THM_FS47_ALLOCATION importtmp = new THM_FS47_ALLOCATION();
                importtmp.setMotor_serial_num(ei.getCellValue(row,motorSerialNumPosition).toString());//产品编号
                checkMotorSerialNum = checkRepeat(checkMotorSerialNum,ei.getCellValue(row,motorSerialNumPosition).toString());//判断是否有重复产品编号
                if(ei.getCellValue(row,1).toString().equals(trainType)) {
                    importtmp.setTrain_type(ei.getCellValue(row, 1).toString());//车型
                }else {
                    errorList.add("第"+(row+1)+"行所填车型与所选车型不符。\n");
                }
                importtmp.setTrain_num(swithStr(ei.getCellValue(row, 2).toString()));//车号
                trainNum = ei.getCellValue(row, 2).toString();
                importtmp.setManufacturer(ei.getCellValue(row,3).toString());//造修厂家
                //隶属路局
                Map<String,Integer> isBureau_subjectionExist = iReportMapperFS47.getBureauPlace_subjectionExist(ei.getCellValue(row, 4).toString());
                if(isBureau_subjectionExist.get("isExits") !=0){
                    importtmp.setBureau_subjection(ei.getCellValue(row, 4).toString());
                }else {
                    errorList.add("第"+(row+1)+"行所填隶属路局："+ei.getCellValue(row, 4).toString()+"  在主数据表中不存在，请检查对应单元格或联系系统管理员。\n");
                }
                //配属段、所
                Map<String,Integer> isPlace_subjectionExist = iReportMapperFS47.getBureauPlace_subjectionExist(ei.getCellValue(row, 5).toString());
                if(isPlace_subjectionExist.get("isExits") !=0){
                    importtmp.setPlace_subjection(ei.getCellValue(row, 5).toString());
                }else {
                    errorList.add("第"+(row+1)+"行所填配属段、所："+ei.getCellValue(row, 5).toString()+"  在主数据表中不存在，请检查对应单元格或联系系统管理员。\n");
                }
                importtmp.setRepair_date(ei.getCellValue(row, 6).toString());//车造、修日期
                importtmp.setRepair_level(ei.getCellValue(row, 7).toString());//车修程类别
                importtmp.setDistance_reach_repairlvl(swithStr(ei.getCellValue(row, 8).toString()));//检修时走行里程
                importtmp.setReport_time(ei.getCellValue(row, colLength - 1).toString());//信息提报日期
                importtmp.setReport_name(ei.getCellValue(row, colLength - 2).toString());//信息提报人
                importtmp.setReport_name(ei.getCellValue(row,colLength - 3).toString());//备注
                int allocation_position = motorSerialNumPosition - MOTORSERIALNUMSTART;
                importtmp.setMap_category(allocationOrderList.get(allocation_position).getMap_category());//配属类别
                importtmp.setMap_position(allocationOrderList.get(allocation_position).getMap_position());//配属位置
                importDataList.add(importtmp);
            }
            checkTrainNum = checkRepeat(checkTrainNum, trainNum);//判断是否有重复车号
        }

        /* 数据校验 */
        //判断是否有重复产品编号

        String motorNumErr = null;
        motorNumErr = checkRepeat(motorNumErr,checkMotorSerialNum);
        if(!motorNumErr.isEmpty()){
            errorList.add("产品编号重复！重复的产品编号有："+motorNumErr+"\n");
        }
        String trainNumErr = null;
        trainNumErr = checkRepeat(trainNumErr,checkTrainNum);
        if(!trainNumErr.isEmpty()){
            errorList.add("车号重复！重复的车号有："+trainNumErr+"\n");
        }
        //返回错误信息
        if(!errorList.isEmpty()){
            errorData.put("ERRORDATA",errorList);
            listMap.put("ERROR",errorData);
            return mapper.writeValueAsString(listMap);
        }
        //将数据存入数据库
        for( int i = 0; i < importDataList.size(); i++ ){
            iReportMapperFS47.saveOrUpdateData(importDataList.get(i));
        }
        //导入成功
        if(errorList.isEmpty()) {
            listMap.put("SUCCESSES", "导入成功!");
            return mapper.writeValueAsString(listMap);
        }
        return null;
    }

    /**
     * 去掉小数点及之后的字符
     * @param str
     * @return
     */
    private String swithStr(String str){
        str = str.replaceAll("\\..*","");
        return str;
    }

    /**
     * 判断map中元素是否包含key，如果包含，对应元素值加一，否则新添加元素并设置值为一
     * @param map
     * @param key
     * @return
     */
    private Map<String,Integer> checkRepeat(Map<String,Integer> map, String key){
        map.put(key,map.containsKey(key)?map.get(key)+1:1);
        return map;
    }

    /**
     * 如果存在重复产品编号，将产品编号加入错误提示信息中
     * @param errorInf
     * @param map
     * @return
     */
    private String checkRepeat(String errorInf, Map<String,Integer> map){
        StringBuffer errorStr = new StringBuffer();
        for(Map.Entry<String,Integer> entry : map.entrySet()){
            if(entry.getValue() > 1){
                errorStr.append(entry.getKey());
                errorStr.append(",");
            }
        }
        if(errorStr.length() > 0){
            errorStr.deleteCharAt(errorStr.length() - 1);
        }
        errorInf = errorStr.toString();
        return errorInf;
    }


    /**
     * 查询条件-车型
     * @param train_type
     * @return
     * @throws JsonProcessingException
     * @throws DataAccessException
     */
    @Override
    public List<String> getListTrainModel(String train_type) throws JsonProcessingException,DataAccessException {
        List<THM_FS47_ALLOCATION> trainTypeList = iReportMapperFS47.getListTrainModel(train_type);
        List<String> jsonList = new ArrayList<>();
        for(THM_FS47_ALLOCATION tmp : trainTypeList){
            jsonList.add(tmp.getTrain_type());
        }
        return jsonList;
    }

    @Override
    public List<String> getAllTrainNum(String train_num) throws JsonProcessingException,DataAccessException {
        List<THM_FS47_ALLOCATION> trainNumList = iReportMapperFS47.getListTrainNum(train_num);
        List<String> jsonList = new ArrayList<>();
        for(THM_FS47_ALLOCATION tmp : trainNumList){
            jsonList.add(tmp.getTrain_num());
        }
        return jsonList;
    }

    @Override
    public List<String> getAllManufacturer(String manufacturer)throws JsonProcessingException, org.springframework.dao.DataAccessException{
        List<THM_FS47_ALLOCATION> manufacturerList = iReportMapperFS47.getListManufacturer(manufacturer);
        List<String> jsonList = new ArrayList<>();
        for(THM_FS47_ALLOCATION tmp : manufacturerList){
            jsonList.add(tmp.getTrain_num());
        }
        return jsonList;
    };

    @Override
    public List<String> getAllBureau_subjection(String bureau_subjection)throws JsonProcessingException, org.springframework.dao.DataAccessException{
        List<THM_FS47_ALLOCATION> bureau_subjectionList = iReportMapperFS47.getListBureau_subjection(bureau_subjection);
        List<String> jsonList = new ArrayList<>();
        for(THM_FS47_ALLOCATION tmp : bureau_subjectionList){
            jsonList.add(tmp.getTrain_num());
        }
        return jsonList;
    };

    @Override
    public List<String> getAllPlace_subjection(String place_subjection)throws JsonProcessingException, org.springframework.dao.DataAccessException{
        List<THM_FS47_ALLOCATION> place_subjectionList = iReportMapperFS47.getListPlace_subjection(place_subjection);
        List<String> jsonList = new ArrayList<>();
        for(THM_FS47_ALLOCATION tmp : place_subjectionList){
            jsonList.add(tmp.getTrain_num());
        }
        return jsonList;
    };

    @Override
    public List<String> getAllRepair_level(String repair_level)throws JsonProcessingException, org.springframework.dao.DataAccessException{
        List<THM_FS47_ALLOCATION> repair_levelList = iReportMapperFS47.getListRepair_level(repair_level);
        List<String> jsonList = new ArrayList<>();
        for(THM_FS47_ALLOCATION tmp : repair_levelList){
            jsonList.add(tmp.getTrain_num());
        }
        return jsonList;
    }

    @Override
    public List<String> getAllMotor_serial_num(String motor_serial_num)throws JsonProcessingException, org.springframework.dao.DataAccessException {
        List<THM_FS47_ALLOCATION> motor_serial_numList = iReportMapperFS47.getListMotor_serial_num(motor_serial_num);
        List<String> jsonList = new ArrayList<>();
        for (THM_FS47_ALLOCATION tmp : motor_serial_numList) {
            jsonList.add(tmp.getTrain_num());
        }
        return jsonList;
    }
}
