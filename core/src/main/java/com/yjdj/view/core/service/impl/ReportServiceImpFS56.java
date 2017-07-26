package com.yjdj.view.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS56;
import com.yjdj.view.core.entity.mybeans.THM_FS56_MAP;
import com.yjdj.view.core.mapper.IReportMapperFS56;
import com.yjdj.view.core.service.IReportServiceFS56;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.ExcelImportUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zhuxuan on 2017/2/17.
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS56 implements IReportServiceFS56 {
    @Autowired
    private IReportMapperFS56 iReportMapperFS56;
    private boolean isExport = false; //如果是true则为导数据，false则为查数据
    private ExcelExportUtil ee = null;
    private ObjectMapper mapper = new ObjectMapper();
    private final static String SPLITTER = "`";
    private final static String EMPTY = "此处空白";
    private final static String COL = "col";
    private final static String ROW = "row";
    private final static String NAME = "name";
    private final static String TITLE = "检修电机数据统计表";
    private final static String XUHAO = "code1";//序号列对应的code字段名
    Map<String, Object> listMap = null;
    StringBuffer errorStr = null;
    char l = '(';
    char r = ')';
    char ll = '（';
    char rr = '）';
    char _line = '_';

//查询部分xml没做分页操作

    /**
     * 查询或导出报表/导出模板
     * @param queryBean
     * @param action 0下载模板;1查询;2导出;
     * @return
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     * @throws java.io.IOException
     * @throws org.springframework.dao.DataAccessException
     */
    public Object getReportJson(QueryBean queryBean,int action) throws JsonProcessingException, IOException,DataAccessException {
        //通过电机型号，返回该型号对应的列的信息
        List<THM_FS56_MAP> columnList = iReportMapperFS56.getColumn(queryBean.getMotormodel());

        List<THM_FS56_MAP> columnListSort = new LinkedList<>();
        columnListSort = getColumnListSort(columnList);

        columnListSort = sortList(columnListSort,0);//将第一行表头列名相同的codes排列在一起
        columnListSort = sortList(columnListSort,1);//将第二行表头列名相同的codes排列在一起
        columnListSort = sortList(columnListSort,2);//将第三行表头列名相同的codes排列在一起

        List<String> columnName = new LinkedList<>();//需要查询的表头列名的集合
        for(THM_FS56_MAP tmp:columnListSort){
            columnName.add(tmp.getCodes());
        }
        StringBuffer selectName = new StringBuffer();
        for(String str:columnName){//生产查询条件所需列名字符串 如 code1,code2,code3……
            selectName.append(str);
            selectName.append(",");
        }
        selectName.deleteCharAt(selectName.length()-1);
//        System.out.println(selectName.toString());
        List<THM_FS56> dataList = iReportMapperFS56.getData(selectName.toString(),queryBean.getMotormodel(),
                queryBean.getStartTime(),queryBean.getEndTime(),queryBean.getStartitem(),queryBean.getPageitem(),action);

        /* ********************************************************************************************************** */
        /* 目前为止表头有了columnListSort，数据有了dataList，在此行之下的代码，用来实现 查询，导出，导出模板 三个功能 */
        /* ********************************************************************************************************** */

        /* 模板导出部分1 */
        List<String> lineList_0 = getLineList(columnListSort, 0);//第三行的部分，如果没有则为“此处空白”
        List<String> lineList_1 = getLineList(columnListSort, 1);//第四行的部分，同上
        List<String> lineList_2 = getLineList(columnListSort, 2);//第五行的部分，同上
        List<String> lineList_3 = getLineList(columnListSort, 3);//第六行的部分，同上
        List<int[]> mergeList_0 = getMergeList(lineList_0);//第三行横向合并所需数据
        List<int[]> mergeList_1 = getMergeList(lineList_1);//第四行横向合并所需数据
        List<int[]> mergeList_2 = getMergeList(lineList_2);//第五行横向合并所需数据
        List<int[]> mergeList_3 = getMergeList(lineList_3);//第六行横向合并所需数据
        //传给前端的表头  添加合并列COL参数
        Map<String,Map<String,Object>> showLineList_0 = getShowLineListCOL(lineList_0, mergeList_0, 0);
        Map<String,Map<String,Object>> showLineList_1 = getShowLineListCOL(lineList_1, mergeList_1, 1);
        Map<String,Map<String,Object>> showLineList_2 = getShowLineListCOL(lineList_2, mergeList_2, 2);
        Map<String,Map<String,Object>> showLineList_3 = getShowLineListCOL(lineList_3, mergeList_3, 3);
        //传给前端的表头  添加合并行ROW参数
        showLineList_0 = getShowLineListROW(4,showLineList_0,showLineList_1);
        showLineList_1 = getShowLineListROW(3,showLineList_1,showLineList_2);
        showLineList_2 = getShowLineListROW(2,showLineList_2,showLineList_3);
        showLineList_3 = getShowLineListROW(1,showLineList_3,showLineList_3);
        //将四行添加到一个List中形成表头Json
        List<Map> jsonList = new LinkedList<>();
        jsonList.add(showLineList_0);
        jsonList.add(showLineList_1);
        jsonList.add(showLineList_2);
        jsonList.add(showLineList_3);
        //将数据和表头合并到一个Json中
        Map<String,Object> header = new LinkedHashMap<>();
        header.put("HEADER",jsonList);
        //将电机型号对应的数据列取出来
        List<Map<String,String>> resultData =new LinkedList<>();
        boolean emptyTag = true;
        if(dataList.size() == 0){
            emptyTag = false;
        }
        for(int i = 0; i < dataList.size(); i++) {
            Map<String,String> newMap = new LinkedHashMap<>();
            resultData.add(newMap);
            for (String code : columnName) {
                if(code.equals(XUHAO)){
                    resultData.get(i).put(code, String.valueOf(i+1));
                }else {
                    resultData.get(i).put(code, dataList.get(i).getCode(code));
                }
            }
        }
        Map<String,Object> data = new LinkedHashMap<>();
        if(emptyTag) {
            data.put("DATA", resultData);
        }else {
            String[] str = new String[0];
            data.put("DATA",str);
        }
        //将总列数传给前台
        Map<String,Integer> num = new HashMap<>();
        String resultJson = "";
        List<Map> resultJsonList = new LinkedList<>();
            num.put("COL", columnList.size());
            num.put("ROW", resultData.size());
            resultJsonList.add(header);
            resultJsonList.add(data);
            resultJsonList.add(num);
            resultJson = mapper.writeValueAsString(resultJsonList);
        if(action == 1) {
            return resultJson;//action==1是查询，在此返回结果
        }

        /* 模板导出部分2 */
        ee = getExcelHeader(queryBean.getMotormodel(), columnListSort.size());//初始化表，添加大标题：表名、副标题：电机型号
        ee = getColumnMergeExcel(ee,2,mergeList_0);//对第三行进行合并操作
        ee = getColumnMergeExcel(ee,3,mergeList_1);//对第四行进行合并操作
        ee = getColumnMergeExcel(ee,4,mergeList_2);//对第五行进行合并操作
        for(int i = 0; i < columnListSort.size(); i++){//对每一列判断，并进行行合并
            if(lineList_1.get(i).equals(EMPTY) && lineList_2.get(i).equals(EMPTY) && lineList_3.get(i).equals(EMPTY)){
                ee.addMergedRegion(2,5,i,i);
            }else if(lineList_2.get(i).equals(EMPTY) && lineList_3.get(i).equals(EMPTY)){
                ee.addMergedRegion(3,5,i,i);
            }else if(lineList_3.get(i).equals(EMPTY)){
                ee.addMergedRegion(4,5,i,i);
            }
        }
        //为表头添加检测项目名称
        List<List<String>> lineList_ = new LinkedList<>();
        lineList_0 = mergeCellEmpty(lineList_0,0);
        lineList_1 = mergeCellEmpty(lineList_1,1);
        lineList_2 = mergeCellEmpty(lineList_2,2);
        lineList_3 = mergeCellEmpty(lineList_3,3);
        lineList_.add(lineList_0);
        lineList_.add(lineList_1);
        lineList_.add(lineList_2);
        lineList_.add(lineList_3);
        Row row = null;
        for(int r = 0; r < 4; r++){
            row = ee.addRow();
            for(int c = 0; c < columnListSort.size(); c++){
                String itemname = lineList_.get(r).get(c);
                String[] name = itemname.split(SPLITTER);
                if (itemname.isEmpty()) {
                    ee.addCell(row, c, "");
                } else if(!itemname.equals(EMPTY)) {
                    try {
                        ee.addCell(row, c, name[r]);
                    }catch (IndexOutOfBoundsException e){
                        ee.addCell(row, c, "");
                    }
                }else {
                    ee.addCell(row, c, "");
                }
            }
        }
        if(action == 0){
            return ee;//action==0是下载模板，在此返回
        }

        /* 数据导出部分 */
        Row dataRow = null;
        for(int r = 0; r < dataList.size(); r++){
            dataRow = ee.addRow();
            for(int c = 0; c < columnName.size(); c++){
                String str = dataList.get(r).getCode(columnName.get(c));
                ee.addCell(dataRow,c,str);
            }
        }
        if(action == 2){
            return ee;//action == 2 是导出查询结果，在此返回
        }
        return null;
    }

    /**
     * 将数据库中查询出来的表头列名排序
     * @param columnList
     * @return
     */
    private List<THM_FS56_MAP> getColumnListSort(List<THM_FS56_MAP> columnList){
        List<Integer> codesList = new LinkedList<>();
        for(THM_FS56_MAP tmp:columnList){
            String str = tmp.getCodes();
            str = str.substring(4);
            codesList.add(Integer.parseInt(str));
        }
        Collections.sort(codesList);
        List<THM_FS56_MAP> resultList = new LinkedList<>();
        for(Integer integer:codesList){
            for(THM_FS56_MAP tmp:columnList){
                if(tmp.getCodes().equals("code"+integer.toString()))resultList.add(tmp);
            }
        }
        return resultList;
    }
    /**
     * 将对应行中，名称相同属于一组的表头列名排列到一起。
     * @param columnListSort  表头列名
     * @param rowNum  行号
     * @return
     */
    private List<THM_FS56_MAP> sortList(List<THM_FS56_MAP> columnListSort, int rowNum){
        int length = columnListSort.size();
        List<THM_FS56_MAP> tmpList = columnListSort;
        List<THM_FS56_MAP> newList = new LinkedList<>();

        for(int i = 0; i < length; i = newList.size()){
            newList.add(tmpList.get(0));
            tmpList.remove(0);
            if(whichRow(newList.get(i).getTxt()) > rowNum) {
                for (int j = 0; j < tmpList.size(); ) {
                    if (whichRow(tmpList.get(j).getTxt()) > rowNum && getSubString(newList.get(i).getTxt(), rowNum).equals(getSubString(tmpList.get(j).getTxt(), rowNum))) {
                        newList.add(tmpList.get(j));
                        tmpList.remove(j);
                    }else {j++;}
                }
            }
        }

        return newList;
    }

    /**
     * 通过给定的rowNum，对传入的表头列名进行截取。
     * @param columnName
     * @param rowNum
     * @return
     */
    private String getSubString(String columnName, int rowNum){
        String[] tmpName = columnName.split(SPLITTER);
        String resultStr = "";
        for(int i = 0; i <= rowNum; i++){
            resultStr += tmpName[i];
        }
//        ArrayIndexOutOfBoundsException
        return resultStr;
    }

    /**
     * 判断给定的表头列名字符串中含有几个分割字符
     * @param columnName
     * @return
     */
    private int whichRow(String columnName){
        int count = 0;
        for(int i = 0; i < columnName.length(); i++){
            if(columnName.charAt(i) ==  SPLITTER.charAt(0))count++;
        }
        return count;
    }

    /**
     *
     * @param columnListSort
     * @param rowNum
     * @return
     */
    private List<String> getLineList(List<THM_FS56_MAP> columnListSort, int rowNum){
        List<String> lineList = new LinkedList<>();
        for(THM_FS56_MAP tmp:columnListSort){
            String[] str = tmp.getTxt().split(SPLITTER);
            StringBuffer addString = new StringBuffer();
            try {
                for(int i = 0; i <= rowNum; i++){
                    addString.append(str[i].toString());
                    addString.append(SPLITTER);
                }addString.deleteCharAt(addString.length()-1);
                lineList.add(addString.toString());
            }catch (ArrayIndexOutOfBoundsException e){
                lineList.add(EMPTY);
            }
        }
        return lineList;
    }

    /**
     * 获取需要合并的列的开始位置和结束位置
     * @param lineList
     * @return
     */
    private List<int[]> getMergeList(List<String> lineList){
        List<int[]> mergeList = new LinkedList<>();
        Integer count = 0;
        boolean b = false;
        for(int i = 0; i < lineList.size() - 1; i++){
            int end;
            b = isEqual(lineList.get(i),lineList.get(i+1));
            if(b){
                count++;
                if(i == lineList.size() - 2){
                    int[] merge = new int[2];
                    merge[0] = i + 1 - count;
                    merge[1] = i + 1;
                    mergeList.add(merge);
                }
            }else{
                end = i;
                if(count > 0){
                    int[] merge = new int[2];
                    merge[0] = end - count;
                    merge[1] = end;
                    mergeList.add(merge);
                    count = 0;
                }
            }
        }
        return mergeList;
    }

    /**
     * 判断两个字符串是否相同
     * @param last
     * @param next
     * @return
     */
    private boolean isEqual(String last, String next){
        if(last.equals(EMPTY)){
            return false;
        }else{
            if(last.equals(next)){
                return true;
            }else return false;
        }
    }
    /**
     * 返回前端做表头合并行所需的json格式
     * @param lineList
     * @param mergeList
     * @return
     */
    private Map<String,Map<String,Object>> getShowLineListCOL(List<String> lineList, List<int[]> mergeList, int rowNum){
        Map<String,Map<String,Object>> showLineList = new LinkedHashMap<>();
        if(rowNum == 0) {//将第一列序号加上
        Map<String,Object> xuhao = new LinkedHashMap<>();
        xuhao.put(NAME,"序号");
        xuhao.put(COL,1);
        xuhao.put(ROW,4);
        showLineList.put("序号",xuhao);
        }
        int mergeList_count = 0;
        for(int c = 0; c < lineList.size(); c++){
            String fullName = lineList.get(c);
            if(!fullName.equals(EMPTY)) {
                Map<String, Object> cellMap = new HashMap<>();
                String[] nameStr = fullName.split(SPLITTER);
                String name = nameStr[rowNum];
                cellMap.put(NAME, name);
                cellMap.put(COL, 1);
                if (mergeList_count < mergeList.size() && mergeList.get(mergeList_count)[0] == c) {
                    int start = mergeList.get(mergeList_count)[0];
                    int end = mergeList.get(mergeList_count)[1];
                    cellMap.put(COL,end + 1 - start);
                    cellMap.put(ROW,1);
                    c = end;
                    mergeList_count++;
                }
                fullName = fullName.replace(l, _line);
                fullName = fullName.replace(r,_line);
                fullName = fullName.replace(ll,_line);
                fullName = fullName.replace(rr,_line);
                showLineList.put(fullName, cellMap);
            }
        }
        return showLineList;
    }

    /**
     * 返回前端做表头合并列所需的json格式
     * @param rowMerge
     * @param showLineList
     * @return
     */
    private Map<String,Map<String,Object>>  getShowLineListROW(int rowMerge,Map<String,Map<String,Object>> showLineList, Map<String,Map<String,Object>> showLineList_Next){
         int row = rowMerge;
        for(Map.Entry<String,Map<String,Object>> entry : showLineList.entrySet()){
            Map<String,Object> tmpMap = entry.getValue();
            String key = entry.getKey();
            boolean hasNextRow = false;
            for(Map.Entry<String,Map<String,Object>> entry_Next : showLineList_Next.entrySet()){
                String[] key_Next = entry_Next .getKey().split(SPLITTER);
                String[] key_Pre = key.split(SPLITTER);
                int count = 0;
                for(int i = 0; i < key_Pre.length; i++){
                    if(key_Pre[i].equals(key_Next[i])){
                        count++;
                    }
                }
                if(count == (1 + 4 - row)) {
                    hasNextRow = true;
                }
            }
            if(!tmpMap.containsKey(ROW)){
                if(hasNextRow){
                    showLineList.get(entry.getKey()).put(ROW, 1);
                }else {
                    showLineList.get(entry.getKey()).put(ROW, rowMerge);
                }
            }
        }
        return showLineList;
    }

    /**
     * 根据参数生成带表头的excel文件
     * @param motorModel  电机型号
     * @param headerListLength  表头总长度（用来确定第一行需要合并的列数量）
     * @return
     */
    private ExcelExportUtil getExcelHeader(String motorModel, int headerListLength){
        String secondTitle = "电机型号:"+motorModel;
        ee = new ExcelExportUtil(TITLE,secondTitle,headerListLength);
        return ee;
    }

    /**
     * 传入Excel文件，对指定单元格进行合并，返回Excel文件
     * @param ee  Excel文件
     * @param row  指定行
     * @param mergeList  指定列的集合，元素为int数组格式，int[0]为开始列，int[1]为结束列
     * @return
     */
    private ExcelExportUtil getColumnMergeExcel(ExcelExportUtil ee,int row,List<int[]> mergeList){
        for(int[] i:mergeList){
            ee.addMergedRegion(row,row,i[0],i[1]);
        }
        return ee;
    }

    /**
     * 导入数据
     * @param filePath
     * @param flag  0为上传模板，1为上传数据
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     * @throws DataAccessException
     * @throws InvalidFormatException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Object importData(String filePath, int flag)throws JsonProcessingException, IOException ,DataAccessException,
            InvalidFormatException, InstantiationException, IllegalAccessException{
        errorStr = new StringBuffer();
        listMap = Maps.newHashMap();
        ExcelImportUtil ei = new ExcelImportUtil(filePath, 5);
        String motorModeltmp = ei.getCellValue(1, 0).toString().trim();//取电机型号
        String motorModel = "";
        if(motorModeltmp.contains(":")) {
            motorModel = motorModeltmp.trim().substring(motorModeltmp.indexOf(":") + 1);
        }else if(motorModeltmp.contains("：")){
            motorModel = motorModeltmp.trim().substring(motorModeltmp.indexOf("：") + 1);
        }
        if(motorModel.equals("")){
            errorStr.append("电机型号不能为空，请填写电机型号。\t");
        }
        if(!errorStr.toString().isEmpty()){
            listMap.put("ERROR",errorStr.toString());
            return mapper.writeValueAsString(listMap);
        }
        //取数据库中目前该电机型号对应的列的汉字描述
        List<THM_FS56_MAP> columnList = iReportMapperFS56.getColumn(motorModel);
        List<String> columnNameTXTOld = new LinkedList<>();//表头列名汉字描述部分的集合
        for(THM_FS56_MAP tmp:columnList){
            columnNameTXTOld.add(tmp.getTxt());
        }
        //取excel表中各个列的汉字描述，与数据库中的进行对比，看是否一致。
        int row = ei.getLastDataRowNum();
        int col = ei.getLastCellNum();
        List<String> columnNameTXTNew = Lists.newLinkedList();

        String row2_hasValue = new String();//用来存第3行的合并行有文字描述的单元格内容（就是合并行的第一格），其他单元格是空格
        String row3_hasValue = new String();//同上，存第4行
        String row4_hasValue = new String();//同上，存第5行
        for(int c = 0; c < col; c++){
            StringBuffer txt = new StringBuffer();
            String row2_present = new String();//用来存第3行当前列的文字描述
            String row3_present = new String();//用来存第4行当前列的文字描述
            String row4_present = new String();//用来存第5行当前列的文字描述
            String row5_present = new String();//用来存第6行当前列的文字描述
            row2_present = ei.getCellValue(2,c).toString().trim();
            row3_present = ei.getCellValue(3,c).toString().trim();
            row4_present = ei.getCellValue(4,c).toString().trim();
            row5_present = ei.getCellValue(5,c).toString().trim();
            //第三行
            if(!row2_present.isEmpty()){
                row2_hasValue = row2_present;
            }
            txt.append(row2_hasValue);
            //第四行
            if(!row3_present.isEmpty()){
                row3_hasValue = row3_present;
            }
            if(row3_present.isEmpty() && row4_present.isEmpty() && row5_present.isEmpty()){
                //如果当前列的4,5,6行全为空，说明该列是4行合并列。
            }else {
                txt.append(SPLITTER);
                txt.append(row3_hasValue);
            }
            //第五行
            if(!row4_present.isEmpty()){
                row4_hasValue = row4_present;
            }
            if(row4_present.isEmpty() && row5_present.isEmpty()){
                //如果当前列的5,6行全为空，说明该列是后3行合并列。
            }else {
                txt.append(SPLITTER);
                txt.append(row4_hasValue);
            }
            //第六行
            if(!row5_present.isEmpty()){
                txt.append(SPLITTER);
                txt.append(row5_present);
            }
            columnNameTXTNew.add(txt.toString());
        }
        //校验导入模板中的合并
        List<CellRangeAddress> rangeAddresses = ei.getSheet().getMergedRegions();
        for(CellRangeAddress address:rangeAddresses){
            int firstRow = address.getFirstRow();
            int lastRow = address.getLastRow();
            if(firstRow == 2 && lastRow == 3){
                errorStr.append("表头部分合并单元格错误：若检测项目包含子项目，如：检修后定子装配、介质损耗等，则该检测项目只能占一行，不能行与行合并（可合并多列）。" +
                        "若检测项目不包含子项目，如：检测日期、1KV、1.5KV等，可行与行合并。\t");
            }
            if(firstRow == 3 && lastRow == 4){
                errorStr.append("表头部分合并单元格错误：若检测项目包含子项目，如：检修后定子装配、介质损耗等，则该检测项目只能占一行，不能行与行合并（可合并多列）。" +
                        "若检测项目不包含子项目，如：检测日期、1KV、1.5KV等，可行与行合并。\t");
            }
            if(firstRow == 2 && lastRow == 4){
                errorStr.append("表头部分合并单元格错误：若检测项目包含子项目，如：检修后定子装配、介质损耗等，则该检测项目只能占一行，不能行与行合并（可合并多列）。" +
                        "若检测项目不包含子项目，如：检测日期、1KV、1.5KV等，可行与行合并。\t");
            }
            if(firstRow == 4 && lastRow == 4){
                errorStr.append("数据表头最后一行（第6行）不可以进行列合并。\t");
            }
        }
        if(!errorStr.toString().isEmpty()){
            listMap.put("ERROR",errorStr.toString());
            return mapper.writeValueAsString(listMap);
        }

        if(flag == 0){
            //验证新模板是否包含必填列
            List<Map<String,String>> fixedColumn = iReportMapperFS56.getFixedColumn();
            List<String> fixedColumnName = Lists.newLinkedList();
            for(Map<String,String> code:fixedColumn){
                fixedColumnName.add(code.get("TXT"));
            }
            boolean containsFixedColumn = columnNameTXTNew.containsAll(fixedColumnName);
            if(!containsFixedColumn){
                errorStr.append("模板包含列错误：导入的模板缺少必需列。必需列有：");
                for(String tmp:fixedColumnName){
                    errorStr.append(tmp);
                    errorStr.append("；");
                }
            }
            if(!errorStr.toString().isEmpty()) {
                listMap.put("ERROR", errorStr.toString());
                return mapper.writeValueAsString(listMap);
            }
            //模板变更操作
            List<String> TXTOld = Lists.newLinkedList();
            TXTOld.addAll(columnNameTXTOld);
            List<String> TXTNew = Lists.newLinkedList();
            TXTNew.addAll(columnNameTXTNew);
            TXTOld.removeAll(columnNameTXTNew);
            TXTNew.removeAll(columnNameTXTOld);
            //TXTOld剩余的部分，是新模板不需要的列
            if(!TXTOld.isEmpty()){
                for(int i = 0; i < TXTOld.size(); i++){
                    String mapFlag = iReportMapperFS56.getMapFlag(motorModel,TXTOld.get(i)).get("FLAG");//获取描述对应行的flag
                    if(mapFlag.equals("0")){
                        errorStr.append("必填列必须加入模板中。");
                    }else if(mapFlag.equals("1")) {
                        iReportMapperFS56.setMapFlag(motorModel, TXTOld.get(i), "2");//将flag变为2禁用列
                    }
                }
            }
            //TXTNew剩余的部分，是新模板比旧模板多的列
            if(!TXTNew.isEmpty()){
            /*3种情况：1全新增加列----使用最近的一个空白code  tag1==0,tag2==0
                       2.别的电机也用此列并且已经有对应code----使用该code  tag1<>0,tag2==0
                       3.本电机已经有对应code----改flag为在用列  tag1<>0,tag2<>0 */
                for(int i = 0; i < TXTNew.size(); i++){
                    String tag1 = iReportMapperFS56.getTag(motorModel,TXTNew.get(i),false).get("TAG").toString();//tag1表示该描述是否已存在？0为不存在，其他为存在
                    String tag2 = iReportMapperFS56.getTag(motorModel,TXTNew.get(i),true).get("TAG").toString();//tag2表示该电机型号是否包含该描述？0为不包含，其他为包含
                    if(tag1.equals("0") && tag2.equals("0")){
                        List<THM_FS56_MAP> emptyList = iReportMapperFS56.getEmptyColumn();//取目前空白的code的列表
                        emptyList = getColumnListSort(emptyList);//将列表排序
                        String emptyCode = emptyList.get(0).getCodes();//取出第一个空白的code
                        iReportMapperFS56.addNewCode(motorModel,TXTNew.get(i),emptyCode);//对该code添加数据
                    }
                    if(!tag1.equals("0") && tag2.equals("0")){
                        String usedCode = iReportMapperFS56.getColumn_Codes(TXTNew.get(i)).get("CODES");//取该描述对应的code（其他型号电机在用的code编号）
                        String oldFlag = iReportMapperFS56.getFlagByTXT(TXTNew.get(i)).get("FLAG");
                        oldFlag = oldFlag.equals("0")?"0":"1";
                        iReportMapperFS56.addUsedCode(motorModel,TXTNew.get(i),usedCode,oldFlag);//对该code添加该电机型号的数据
                    }
                    if(!tag1.equals("0") && !tag2.equals("0")){
                        iReportMapperFS56.setMapFlag(motorModel, TXTNew.get(i), "1");//将flag改为1在用列
                    }
                }
            }
        }

        //将导入数据对应的列依次查询出来
        List<String> columnName = Lists.newLinkedList();
        for(String txt:columnNameTXTNew){
            try {
                Map<String, String> codes = iReportMapperFS56.getColumn_Codes(txt);
                columnName.add(codes.get("CODES"));
            }catch (NullPointerException e){
                errorStr.append("未查询到项目："+txt+"；请下载最新模板填入数据后重新导入。或者请导入新模板。\t");
            }
        }
        if(!errorStr.toString().isEmpty()) {
            listMap.put("ERROR", errorStr.toString());
            return mapper.writeValueAsString(listMap);
        }
        StringBuffer importColumns = new StringBuffer();
        for(String str:columnName){//导入数据所需列名字符串 如 code1,code2,code3……
            importColumns.append(str);
            importColumns.append(",");
        }
        importColumns.deleteCharAt(importColumns.length()-1);
        int motorModelPosition = columnName.indexOf("code3");
        //导入数据
        if(flag == 1) {
            //验证电机型号
            for (int r = 6; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    String imp = ei.getCellValue(r,c).toString().trim();
                    if(motorModelPosition == c){
                        if(!imp.equals(motorModel)){
                            errorStr.append("电机型号错误：数据部分，第"+(r+1)+"行的电机型号与表头中给定的电机型号不相同。\t");
                        }
                    }
                }
            }
            if(!errorStr.toString().isEmpty()) {
                listMap.put("ERROR", errorStr.toString());
                return mapper.writeValueAsString(listMap);
            }
            //导入操作
            for (int r = 6; r < row; r++) {
                StringBuffer importdata = new StringBuffer();
                for (int c = 0; c < col; c++) {
                    String imp = ei.getCellValue(r,c).toString().trim();
                    if(imp.charAt(0) != '0') {
                        imp = dataFormat(imp);
                    }
                    importdata.append("\"");
                    importdata.append(imp);
                    importdata.append("\"");
                    if (c < col - 1) importdata.append(",");
                }
                try {
                    iReportMapperFS56.importData(importColumns.toString(), importdata.toString());
                }catch (BadSqlGrammarException e){
                    errorStr.append("数据导入错误：请下载最新模板填入数据后重新导入。或者请导入新模板。\t");
                }
            }
        }
        if(!errorStr.toString().isEmpty()){
            listMap.put("ERROR",errorStr.toString());
            return mapper.writeValueAsString(listMap);
        }else {
            listMap.put("SUCCESSES", "导入成功!");
        }
        return mapper.writeValueAsString(listMap);
    }

    /**
     * 修正数字格式，不要科学计数法
     * @param data
     * @return
     */
    private String dataFormat(String data){
        try {
            BigDecimal bd = new BigDecimal(data);
            String str = bd.toPlainString();
            return str;
        }catch (NumberFormatException e){
            return data;
        }
    }

    /**
     * 将合并单元格中要填入的内容进行修正，每个合并格只留一格有描述，其余格都为空
     * @param lineList  单元格要写入的描述集合
     * @param limit  在该方法中用作行号/限制长度等
     * @return
     */
    private List<String> mergeCellEmpty(List<String> lineList, int limit){
        List<String> resultList = Lists.newLinkedList();
        for(int i = 0; i < lineList.size(); i++){
            if(lineList.get(i).equals(EMPTY)){
                resultList.add(lineList.get(i));
            }else {
                resultList.add(lineList.get(i));
                String[] first = lineList.get(i).split(SPLITTER);
                for(int j = i + 1; j < lineList.size(); j++){
                    if(lineList.get(j).equals(EMPTY)){
                        i = j - 1;
                        break;
                    }
                    String[] others = lineList.get(j).split(SPLITTER);
                    Integer isEqual = 0;
                    for(int m = 0; m <= limit; m++){
                        if(first[m].equals(others[m])){
                            isEqual++;
                        }
                    }
                    if(isEqual == (limit + 1)){
                        if(limit == 0){
                            resultList.add("");
                        }else {
                            StringBuffer str = new StringBuffer();
                            for (int n = 0; n < limit; n++) {
                                str.append(others[n]);
                                str.append(SPLITTER);
                            }
                            str.append("");
                            resultList.add(str.toString());
                        }
                    }else {
                        i = j - 1;
                        break;
                    }
                }
            }
        }
        return resultList;
    }

    /**
     * 用户查询条件，电机型号
     * @return
     * @throws JsonProcessingException
     */
    public List<String> getMotorModel(String motorModel)throws JsonProcessingException{
        List<Map<String,String>> modelList = iReportMapperFS56.getMotorModel(motorModel);
        List<String> resultList = Lists.newArrayList();
        for(Map<String,String> tmp:modelList){
            if(!tmp.get("MODEL").isEmpty()){
                resultList.add(tmp.get("MODEL"));
            }
        }
        return resultList;
    };
}
