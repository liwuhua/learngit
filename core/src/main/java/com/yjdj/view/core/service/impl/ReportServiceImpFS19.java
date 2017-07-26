package com.yjdj.view.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yjdj.view.core.entity.mybeans.*;
import com.yjdj.view.core.mapper.IReportMapperFS19;
import com.yjdj.view.core.service.IReportServiceFS19;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.ExcelImportUtil;
import com.yjdj.view.core.util.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhuxuan on 2016/11/11.
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS19 implements IReportServiceFS19 {
    private final int ROW = 15;//行数
    private final int COL = 18;//列数
    private final int NOCOMBINECOL = 22;//未将公司合并的列数(如5005和5500)
    private final int COMPSUM = 8;//用户可选公司代码总数
    private final static String BZDWALL = "中车永济电机有限公司(合计)";
    private final static String TITLE = "货币情况明细表";
    DecimalFormat decimalFormat = new DecimalFormat("0.00");//格式化设置
    //公共变量设置
    private List<String> headerList = null;
    private List<String> secondHeaderList = null;
    private List<String> compCodeList = null;
    private List<THM_FS19> mapperList = null;
    List<THM_IMP_FS19> importList = null;
    List<InsertFieldTHM_FS19> insertList = null;
    ObjectMapper mapper = new ObjectMapper();
    String yearMonth = null;
    String json = null;
    Map<String, Object> listMap = new HashMap<String, Object>();


    @Autowired
    private IReportMapperFS19 iReportMapperFS19;

    @Override
    public Object getReportJson(QueryBean queryBean, List<String> compCodeCheck)throws JsonProcessingException{
        listMap = new HashMap<>();
        //定义期号，将用户选择时间改为期号
        String fiscper = toFiscper(queryBean.getDateYearMonth());
        compCodeList = Lists.newArrayList();
        //判断如果用户未输入公司代码，则默认选择所有公司代码
        if(!queryBean.getCompCodeValue().isEmpty()) {
            compCodeList = queryBean.getCompCodeValue();
        }else if(queryBean.getCompCodeValue().isEmpty()){
            compCodeList = compCodeCheck;
//            compCodeList = initCompCodeList(compCodeList);
        }

        // 权限代码不为空 遍历权限内的公司代码 后 和文件中的公司代码比较 查看是否在权限内
        if (compCodeCheck.size() == 0 || compCodeCheck == null) {
            listMap.put("ERRORCOMPANEY", "查询有误,没有该公司的查询权限");
            return checkError(listMap);

        } else {
            if (!compCodeCheck.containsAll(compCodeList)) {
                // 权限公司代码不包含 文件中的公司代码
                listMap.put("ERRORCOMPANEY", "查询有误,没有该公司的查询权限");
                return checkError(listMap);
            }
        }

        //判断编制单位
        Map<String,Object> bzdw = new LinkedHashMap<>();
        bzdw = getBZDW(bzdw,compCodeList);

        //将用户输入的公司代码转换成获取数据用的公司代码
        compCodeList = changeToGetData(compCodeList);

        //从数据库获取数据
        mapperList = iReportMapperFS19.getResultData(compCodeList,fiscper);
        importList = iReportMapperFS19.getUserImportData(compCodeList,fiscper);

        //建立中间报表noCombineResultList，并初始化
        List<List<Object>> noCombineResultList = new ArrayList<>();
        noCombineResultList = initList(noCombineResultList,ROW,NOCOMBINECOL);

        //将查询结果按条件放入noCombineResultList中
        for(THM_FS19 tmpBean:mapperList){
            noCombineResultList.get(getRow(tmpBean.getType())).set(getCol(tmpBean.getRbukrs(), tmpBean.getCurtype()), decimalFormat.format(tmpBean.getBalance()));
        }

        //建立最终结果报表resultFullList
        List<List<Object>> resultFullList;

        //将noCombineResultList数据处理后放入resultFullList中
        resultFullList = getResultFullList(noCombineResultList);

        //将resultFullList转换成用户需要看的公司对应的数据resultList并输出resultList
        compCodeList = changeToGetJson(compCodeList);
        List<List<Object>> resultList = getResultList(resultFullList,compCodeList);

        //返回结果或者返回excel表
        if(!queryBean.isExport()) {
            //输出resultJson
            String resultJson;
            resultJson = getResultJson(resultList, compCodeList, bzdw);
            return resultJson;
//        System.out.println(resultJson);
        }else{
            //导出excel
            headerList = Lists.newArrayList();
            headerList = getHeaderList(headerList);
            List<String> secondTitle;
            secondTitle = getSecondTitle(bzdw,queryBean.getDateYearMonth(),headerList.size());
            ExcelExportUtil ee = new ExcelExportUtil(TITLE, secondTitle, headerList);
            //对需要合并部分进行处理
            for(int i = 1; i < 19; i += 2) {
                ee.addMergedRegion(2, 2, i, i + 1);
            }
            ee.addMergedRegion(2,3,0,0);
            ee = getExcel(ee,resultFullList);
            return ee;
        }
    }

    /**
     * 通过公司代码判断编制单位
     * @param bzdw
     * @param compCodeList
     * @return
     */
    private Map<String,Object> getBZDW(Map<String,Object> bzdw, List<String> compCodeList){
        if(compCodeList.size() == 1){
            if(compCodeList.contains("India")||compCodeList.contains("SouthAfrica")){
                bzdw.put("BZDW",getcompName(compCodeList.get(0)));
            }else {
                bzdw.put("BZDW", iReportMapperFS19.getCompCode(compCodeList.get(0), null).get(0).getTxtmd());
            }
        }else if(compCodeList.size() == COMPSUM){
            bzdw.put("BZDW",BZDWALL);
        }else{
            bzdw.put("BZDW","");
        }
        return bzdw;
    }
    /**
     * 将用户输入的公司代码转换成本报表需要的公司代码（如果有5005就加上5500，如果有5025就加上5026）
     * @param compCodeList
     * @return
     */
    private List<String> changeToGetData(List<String> compCodeList){
        for(int i = 0; i < compCodeList.size(); i++){
            if(compCodeList.get(i).equals("5005"))compCodeList.add("5500");
            if(compCodeList.get(i).equals("5025"))compCodeList.add("5026");
        }
        return compCodeList;
    }

    /**
     * 将报表需要的公司代码转换成getResultJson需要的公司代码格式
     * @param compCodeList
     * @return
     */
    private List<String> changeToGetJson(List<String> compCodeList){
        int length = compCodeList.size();
        for(int i = length; i > 0; i--){
            if(compCodeList.get(i-1).equals("5500") || compCodeList.get(i-1).equals("5026"))compCodeList.remove(i-1);
        }
        return compCodeList;
    }
    /**
     * 将时间转换成期号
     * @param date
     * @return
     */
    private String toFiscper(String date){
        StringBuffer fiscper = new StringBuffer();
        fiscper.append(date);
        String year = fiscper.substring(0,4);
        String month = fiscper.substring(4);
        fiscper.setLength(0);
        fiscper.append(year);
        fiscper.append("0");
        fiscper.append(month);
        return fiscper.toString();
    }

    /**
     * 初始化结果List，其中excel表中合计行都赋值为0.00，非合计行都为空白
     * @param list
     * @param listrow
     * @param listcol
     * @return
     */
    private List<List<Object>> initList(List<List<Object>> list,Integer listrow,Integer listcol){
        for(int row = 0; row < listrow; row++){
            list.add(new ArrayList<Object>());
            for(int col = 0; col < listcol; col++){
                if(row == 0 || row == 3 || row == 11 || col == 16 || col ==17){
                    list.get(row).add(decimalFormat.format(0.00d));
                }else{
                    list.get(row).add("");
                }
            }
        }
        return list;
    }

    /**
     * 通过type确定行号并返回
     * @param type
     * @return
     */
    private Integer getRow(String type){
        switch (type){
            case "0":return 0; //"合计"
            case "1":return 1; //"其中：存放在境外的款项总额"
            case "2":return 2; //"1、现金"
            case "3":return 3; //"2、银行存款"
            case "4":return 4; //"其中：人民币"
            case "5":return 5; //"美元"
            case "6":return 6; //"日元"
            case "7":return 7; //"欧元"
            case "8":return 8; //"港币"
            case "9":return 9; //"南非兰特"
            case "10":return 10; //"印度卢比"
            case "11":return 11; //"3、其他货币资金（合计）"
            case "12":return 12; //"银行承兑汇票保证金"
            case "13":return 13; //"信用保证金"
            case "14":return 14; //"保函保证金"
            default:return null;
        }
    }

    /**
     * 通过rbukrs,curtype确定列号并返回
     * @param rbukrs
     * @param curtype
     * @return
     */
    private Integer getCol(String rbukrs,String curtype){
        final int ONE = 1;
        if(curtype.equals("00")) {
            switch (rbukrs) {
                case "5003":return 0;
                case "5006":return 2;
                case "5005":return 4;
                case "5004":return 6;
                case "5025":return 8;
                case "5142":return 10;
                case "SouthAfrica":return 12;
                case "India":return 14;
                case "5500":return 18;
                case "5026":return 20;
            }
        }else if(curtype.equals("10")){
            switch (rbukrs) {
                case "5003":return 0 + ONE;
                case "5006":return 2 + ONE;
                case "5005":return 4 + ONE;
                case "5004":return 6 + ONE;
                case "5025":return 8 + ONE;
                case "5142":return 10 + ONE;
                case "SouthAfrica":return 12 + ONE;
                case "India":return 14 + ONE;
                case "5500":return 18 + ONE;
                case "5026":return 20 + ONE;
            }
        }
        return null;
    }

    /**
     * 对noCombineResultList进行处理，变成最终所需的resltList
     * @param list
     * @return
     */
    private List<List<Object>> getResultFullList(List<List<Object>> list){
        List<List<Object>> resultList = new ArrayList<>();
        resultList = initList(resultList,ROW,COL);
        //将list中元素赋给resultList，合并5005,5500值，合并5025,5026值
        for(int row = 0; row < ROW; row++){
            for(int col = 0; col < COL; col++){
                if(col == 4 || col == 5){//如果公司为5005，将5005与5500数据相加
                    Double d1 = 0.00d;
                    Double d2 = 0.00d;
                    if(!list.get(row).get(col).equals("")){
                        d1 = Double.parseDouble(list.get(row).get(col).toString());//获取5005对应值并判断是否为""
                    }
                    if(!list.get(row).get(col+14).equals("")){
                        d2 = Double.parseDouble(list.get(row).get(col + 14).toString());//获取5500对应值并判断是否为""
                    }
                    if(list.get(row).get(col).equals("") && list.get(row).get(col+14).equals("")) {
                        resultList.get(row).set(col,"");//如果5005和5500对应值都为""，则当前元素为""
                    }else{
                        resultList.get(row).set(col, decimalFormat.format(d1 + d2));//如果5005和5500对应值有一个不为""，则取和并赋给当前元素
                    }
                }else if(col == 8 || col == 9) {//如果公司为5025，将5025与5026数据相加
                    Double d1 = 0.00d;
                    Double d2 = 0.00d;
                    if(!list.get(row).get(col).equals("")){
                        d1 = Double.parseDouble(list.get(row).get(col).toString());
                    }
                    if(!list.get(row).get(col+12).equals("")){
                        d2 = Double.parseDouble(list.get(row).get(col + 12).toString());
                    }
                    if(list.get(row).get(col).equals("") && list.get(row).get(col+12).equals("")) {
                        resultList.get(row).set(col,"");
                    }else{
                        resultList.get(row).set(col, decimalFormat.format(d1 + d2));
                    }
                }else{
                resultList.get(row).set(col, list.get(row).get(col));
                }
            }
        }

        /*
        如果要加印度和南非的数据，在这行注释后加，否则无法参与之后的合计运算
        */
        for(THM_IMP_FS19 tmpBean:importList){
            resultList.get(getRow(tmpBean.getType())).set(getCol(tmpBean.getRbukrs(), tmpBean.getCurtype()), decimalFormat.format(tmpBean.getBalance()));
        }

        //计算第一行的合计 、 银行存款合计 、 其他货币资金（合计）
        List<Integer> depositIndexList = new ArrayList<>();//银行存款合计行号
        List<Integer> otherIndexList = new ArrayList<>();//其他货币资金（合计）行号
        List<Integer> totalIndexList = new ArrayList<>();//合计行号
        for(int i = 4; i <= 10; i++)depositIndexList.add(i);//银行存款合计行号
        for(int i = 12; i <= 14; i++)otherIndexList.add(i);//其他货币资金（合计）行号
        totalIndexList.add(2);totalIndexList.add(3);totalIndexList.add(11);//合计行号
        for(int col = 0; col < COL; col++){
            resultList.get(3).set(col,decimalFormat.format(sumRow(resultList, col, depositIndexList)));
            resultList.get(11).set(col,decimalFormat.format(sumRow(resultList, col, otherIndexList)));
            resultList.get(0).set(col,decimalFormat.format(sumRow(resultList, col, totalIndexList)));
        }
        //计算最后一列合计
        for(int row = 0; row < ROW; row++){
            resultList.get(row).set(16, decimalFormat.format(sumCol(resultList.get(row), 0)));
            resultList.get(row).set(17, decimalFormat.format(sumCol(resultList.get(row), 1)));
        }
        return resultList;
    }
    /**
     * 输入resultList，列号，行号List 求合计
     * @param resultList
     * @param col
     * @param indexList
     * @return
     */
    private Double sumRow(List<List<Object>> resultList,int col,List<Integer> indexList){
        Double sum = 0.00d;
        for(int i = 0; i < indexList.size(); i++){
            if(!resultList.get(indexList.get(i)).get(col).equals("")) {
                sum += Double.parseDouble(resultList.get(indexList.get(i)).get(col).toString());
            }else if(resultList.get(indexList.get(i)).get(col).equals("")){
                sum += 0.00d;
            }
        }
        return sum;
    }

    /**
     * 输入List，和开始标签，返回List内对应元素的和
     * @param tmpList
     * @param startTag
     * @return
     */
    private Double sumCol(List<Object> tmpList,int startTag){
        Double sum = 0.00d;
        for( ; startTag < COL - 2; startTag += 2 ){
            if(!tmpList.get(startTag).equals("")) {
                sum += Double.parseDouble(tmpList.get(startTag).toString());
            }else if(tmpList.get(startTag).equals("")){
                sum += 0.00d;
            }
        }
        return sum;
    }

    /**
     * 返回最终的数据表resultList
     * @param resultFullList
     * @param compCodeList
     * @return
     */
    private List<List<Object>> getResultList(List<List<Object>> resultFullList,List<String> compCodeList) {
        compCodeList = sortCompCodeList(compCodeList);//将compCodeList中的公司代码按报表要求顺序排列好
        //将用户所需的公司代码对应的值取出来放在一个List里
        List<List<Object>> resultList = new ArrayList<>();
        for (int row = 0; row < ROW; row++) {
            resultList.add(new ArrayList<>());
            for (int i = 0; i < compCodeList.size(); i++) {
                resultList.get(row).add(resultFullList.get(row).get(Integer.parseInt(sortSwitch(compCodeList.get(i).toString()).toString())));
                resultList.get(row).add(resultFullList.get(row).get(Integer.parseInt(sortSwitch(compCodeList.get(i).toString()).toString()) + 1));
            }
            resultList.get(row).add(resultFullList.get(row).get(16));
            resultList.get(row).add(resultFullList.get(row).get(17));
        }
        return resultList;
    }

    /**
     * 返回最终所需Json格式
     * @param resultList
     * @param compCodeList
     * @param bzdw
     * @return
     */
    private String getResultJson(List<List<Object>> resultList,List<String> compCodeList,Map<String,Object> bzdw){
        //将resultList转为前端需要的jsonList
        List<Map<String,Object>> jsonList = new ArrayList<>();
        jsonList.add(bzdw);//添加编制单位
        List<String> compCodeWithSumList = sortCompCodeList(compCodeList);//将compCodeList中的公司代码按报表要求顺序排列好
        compCodeWithSumList.add("sum");//含有合计列的公司代码List
        Integer length = compCodeWithSumList.size() * 2;//resultList中的列数
        Map<String,Object> compMap = new LinkedHashMap<>();//用户选择的公司代码加名称的Map
        //将公司代码和名称装入jsonList
        for(int i = 0; i < compCodeWithSumList.size(); i++){
            if(!compCodeWithSumList.get(i).equals("sum")) {
                compMap.put(compCodeWithSumList.get(i), getcompName(compCodeWithSumList.get(i)));
            }
        }
        Map<String,Object> company = new HashMap<>();
        company.put("company",compMap);
        jsonList.add(company);//添加公司名称
        //将其他数据装入jsonList
        for(int row = 0; row < ROW; row++){
            Map<String,Object> rowMap = new LinkedHashMap<>();
            rowMap.put("lanmu",getLanMu(row));
            for(int col = 0; col < length; col +=2){
                List<Map<String,Object>> curtypeList = new ArrayList<>();
                Map<String,Object> curtypeMap = new LinkedHashMap<>();
                curtypeMap.put("yuanbi",resultList.get(row).get(col));
                curtypeMap.put("benweibi",resultList.get(row).get(col+1));
                curtypeList.add(curtypeMap);
                rowMap.put(compCodeWithSumList.get(col/2),curtypeList);
            }
            jsonList.add(rowMap);
        }
        //将jsonList转换为Json格式字符串并返回
        String resultJson = null;
        try {
            resultJson = mapper.writeValueAsString(jsonList);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return resultJson;
    }

    /**
     * 将传入的List中的公司代码按报表要求顺序排列好
     * @param compCodeList
     * @return
     */
    private List<String> sortCompCodeList(List<String> compCodeList){
        List<Integer> rowList = new ArrayList<>();
        for(String str:compCodeList){
            rowList.add(Integer.parseInt(sortSwitch(str).toString()));
        }
        Collections.sort(rowList);
        List<String> resultList = new LinkedList<>();
        for(Integer i:rowList){
            resultList.add(sortSwitch(i.toString()).toString());
        }
        return resultList;
    }

    /**
     * 公司代码与列序号的互相转换（配合sortCompCodeList方法使用）
     * @param str
     * @return
     */
    private Object sortSwitch(String str){
        switch (str){
            case "5003":return 0;
            case "5006":return 2;
            case "5005":return 4;
            case "5004":return 6;
            case "5025":return 8;
            case "5142":return 10;
            case "SouthAfrica":return 12;
            case "India":return 14;
            case "0":return "5003";
            case "2":return "5006";
            case "4":return "5005";
            case "6":return "5004";
            case "8":return "5025";
            case "10":return "5142";
            case "12":return "SouthAfrica";
            case "14":return "India";
            default:return null;
        }
    }

    /**
     * 通过公司代码获取公司名称
     * @param compCode
     * @return
     */
    private String getcompName(String compCode){
        switch (compCode){
            case "5003":return "永济公司";
            case "5006":return "修配公司";
            case "5005":return "西安捷力、贵阳分公司";
            case "5004":return "西安永电";
            case "5025":return "西安永电金风、新疆分公司";
            case "5142":return "托克逊公司";
            case "SouthAfrica":return "南非项目（公司）";
            case "India":return "印度中车先锋电气有限公司";
            case "栏目":return "lanmu";
            case "永济公司":return "5003";
            case "修配公司":return "5006";
            case "西安捷力、贵阳分公司":return "5005";
            case "西安永电":return "5004";
            case "西安永电金风、新疆分公司":return "5025";
            case "托克逊公司":return "5142";
            case "南非项目（公司）":return "SouthAfrica";
            case "印度中车先锋电气有限公司":return "India";
            case "合计":return "total";
            case "":return "";
            default:return null;
        }
    }

    /**
     * 根据行号返回栏目
     * @param row
     * @return
     */
    private String getLanMu(Integer row){
        return iReportMapperFS19.selectLanmu(row.toString());
    }

    /**
     * 返回导出表的secondTitle
     * @param bzdw
     * @param date
     * @return
     */
    private List<String> getSecondTitle(Map<String,Object> bzdw,String date,int headerLength){
        List<String> secondTitle = Lists.newArrayList();
        secondTitle.add("编制单位："+bzdw.get("BZDW"));
        for(int i = 0; i < headerLength - 2; i++){
            secondTitle.add("");
        }
        secondTitle.add(date.substring(0,4)+"年"+date.substring(4,6)+"月");

       // String secondTitle ="编制单位："+bzdw.get("BZDW")+"    "+date.substring(0,4)+"年"+date.substring(4,6)+"月";
        return secondTitle;
    }

    /**
     * 返回导出表的headerList
     * @return
     */
    private List<String> getHeaderList(List<String> headerList){
        headerList.add("栏目");
        headerList.add("永济公司");
        headerList.add("");
        headerList.add("修配公司");
        headerList.add("");
        headerList.add("西安捷力、贵阳分公司");
        headerList.add("");
        headerList.add("西安永电");
        headerList.add("");
        headerList.add("西安永电金风、新疆分公司");
        headerList.add("");
        headerList.add("托克逊公司");
        headerList.add("");
        headerList.add("南非项目（公司）");
        headerList.add("");
        headerList.add("印度中车先锋电气有限公司");
        headerList.add("");
        headerList.add("合计");
        headerList.add("");
        return headerList;
    }

    /**
     * 返回导出excel表
     * @param ee
     * @return
     */
    private ExcelExportUtil getExcel(ExcelExportUtil ee,List<List<Object>> resultList){

        for(int row = 0; row < ROW; row++) {
            resultList.get(row).add(0,getLanMu(row));
        }
        List<Object> firstRow = new ArrayList<>();
        firstRow.add(" ");
        for(int i = 0; i < 9; i++){
            firstRow.add("原币");
            firstRow.add("本位币");
        }
        resultList.add(0,firstRow);
        for(int row = 0; row < resultList.size(); row++) {
            Row eerow = ee.addRow();
            for(int col = 0; col < resultList.get(row).size(); col++) {
                ee.addCell(eerow,col,resultList.get(row).get(col));
            }
        }
        return ee;
    }

//    @Override
    /**
     * 获取所有公司代码（剔除不显示的公司代码）
     * @return
     */
//    public String getCompCode(String comp_code, String txtmd){
//
//        List<MdmTcompCode> compCodeList = iReportMapperFS19.getCompCode(comp_code,txtmd);
//        for(int i = 0; i < compCodeList.size(); i++){
//            if(compCodeList.get(i).getComp_code().equals("5500") || compCodeList.get(i).getComp_code().equals("5026"))
//            compCodeList.remove(i);
//        }
//        MdmTcompCode southAfrica = new MdmTcompCode();
//        southAfrica.setComp_code("SouthAfrica");
//        southAfrica.setTxtmd("南非项目（公司）");
//        MdmTcompCode india = new MdmTcompCode();
//        india.setComp_code("India");
//        india.setTxtmd("印度中车先锋电气有限公司");
//        compCodeList.add(southAfrica);
//        compCodeList.add(india);
//        ObjectMapper mapper = new ObjectMapper();
//        String resultString = null;
//        try{
//            resultString = mapper.writeValueAsString(compCodeList);
//        }catch (JsonProcessingException e){
//            e.printStackTrace();
//        }
//        return resultString;
//    }

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
    @Override
    public Object importData(String filePath, List<String> compCodeCheck) throws JsonProcessingException,IOException, DataAccessException,
            InvalidFormatException, InstantiationException, IllegalAccessException {
        // TODO Auto-generated method stub


        Matcher matcher = null;
        listMap = new HashMap<>();
        //数据导入部分
        ExcelImportUtil ei = new ExcelImportUtil(filePath, 3);
        String company = ei.getCellValue(1, 0).toString();
        String time = ei.getCellValue(1, 18).toString();
        Pattern p = Pattern.compile("[^0-9]");
        matcher = p.matcher(time);
        //验证编制单位
        if (company.indexOf(":") > 0) {
            company = company.substring(company.indexOf(":") + 1, company.length());
        } else if (company.indexOf("：") > 0) {
            company = company.substring(company.indexOf("：") + 1, company.length());
        }
        if (company.trim().length() == 0) {
            listMap.put("ERRORCOMPANEY", "未填写编制单位!");
            return checkError(listMap);
        }
        List<Map<String, String>> getCompList = iReportMapperFS19.selectCompanyCodeBytxtmd(company.trim());
//        if(!company.equals(BZDWALL)) {
            if (getCompList.size() <= 0) {
                listMap.put("ERRORCOMPANEY", "编制单位填写错误，没有该编制单位!");
                return checkError(listMap);
            }
//        }

        // 权限代码不为空 遍历权限内的公司代码 后 和文件中的公司代码比较 查看是否在权限内
        if (compCodeCheck.size() == 0 || compCodeCheck == null) {
            listMap.put("ERRORCOMPANEY", "查询有误,没有该公司的查询权限");
            return checkError(listMap);

        } else if(getCompList.size() != 0){
            if (!compCodeCheck.contains(getCompList.get(0).get("COMP_CODE"))) {
                // 权限公司代码不包含 文件中的公司代码
                listMap.put("ERRORCOMPANEY", "查询有误,没有该公司的查询权限");
                return checkError(listMap);
            }
        }
        //验证时间格式
        yearMonth = matcher.replaceAll("").trim();
        if (!FileUtils.isValidDate(yearMonth)) {
            listMap.put("ERRORTIME", "时间填写错误!");
            return checkError(listMap);
        }
        //验证公司一栏是否有错
        Iterator cellItr = ei.getRow(2).cellIterator();
        String isNull = "";
        while (cellItr.hasNext() && isNull != null){
            isNull = getcompName(cellItr.next().toString());
        };
        if(isNull == null){
            listMap.put("ERRORCOMPANEY","公司名称错误");
            return checkError(listMap);
        }
        //验证原币本位币一行是否有错
        secondHeaderList = Lists.newArrayList();
        secondHeaderList.add("");
        for(int i = 0; i < 9; i++){
            secondHeaderList.add("原币");
            secondHeaderList.add("本位币");
        }
        json = ei.checkHeadList(secondHeaderList, listMap);
        if (null != json) {
            return json;
        }

        //如果验证数据没有问题，则返回所有的数据，标志位为1，若是数据有问题，则返回不正确数据，标准为0
        List<InsertFieldTHM_FS19> list = ei.getDataList(InsertFieldTHM_FS19.class, "FS19");
        if ("ERROR".equals(list.get(list.size() - 1))) {
            for (int i = 0; i < list.size() - 1; i++) {
                listMap.put("ERROR" + i, list.get(i));
            }
            //如果返回的结果存在不合理的数据，那么就将数据结果以json格式返回
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ERROR", listMap);
            if (listMap.size() > 0) {
                return checkError(listMap);
            }
        }
        //对获取的excel表数据tmpDataList进行处理，变成导入mysql所需的数据
        insertList = Lists.newArrayList();
        InsertFieldTHM_FS19 tmp = null;
        switch (company) {
            case "南非项目（公司）":
            //取印度公司和南非公司的所有栏目对应的值
            for (Integer i = 0; i < list.size(); i++) {
                //南非、原币、栏目i
                tmp(tmp, i.toString(), getcompName("南非项目（公司）"), "00", toFiscper(yearMonth), list.get(i).getYuanbi_nf());
                //南非、本位币、栏目i
                tmp(tmp, i.toString(), getcompName("南非项目（公司）"), "10", toFiscper(yearMonth), list.get(i).getBenweibi_nf());
            };break;
            case "印度中车先锋电气有限公司":
            for (Integer i = 0; i < list.size(); i++) {
                //印度、原币、栏目i
                tmp(tmp, i.toString(), getcompName("印度中车先锋电气有限公司"), "00", toFiscper(yearMonth), list.get(i).getYuanbi_yd());
                //印度、本位币、栏目i
                tmp(tmp, i.toString(), getcompName("印度中车先锋电气有限公司"), "10", toFiscper(yearMonth), list.get(i).getBenweibi_yd());
            };break;
            case "中车永济电机有限公司":
            //取南非兰特 、 印度卢比 所有公司(除了印度公司和南非公司，这两个公司在上面循环中已经取到了）的对应值
            for (Integer i = 9; i <= 10; i++) {
                tmp(tmp, i.toString(), getcompName("永济公司"), "00", toFiscper(yearMonth), list.get(i).getYuanbi_yj());
                tmp(tmp, i.toString(), getcompName("永济公司"), "10", toFiscper(yearMonth), list.get(i).getBenweibi_yj());
            };break;
            case "永济中车电机电器修配有限公司":
            for (Integer i = 9; i <= 10; i++) {
                tmp(tmp, i.toString(), getcompName("修配公司"), "00", toFiscper(yearMonth), list.get(i).getYuanbi_xp());
                tmp(tmp, i.toString(), getcompName("修配公司"), "10", toFiscper(yearMonth), list.get(i).getBenweibi_xp());
            };break;
            case "西安中车永电捷力风能有限公司":
            case "西安中车永电捷力风能有限公司贵阳分公司":
            for (Integer i = 9; i <= 10; i++) {
                tmp(tmp, i.toString(), getcompName("西安捷力、贵阳分公司"), "00", toFiscper(yearMonth), list.get(i).getYuanbi_jl());
                tmp(tmp, i.toString(), getcompName("西安捷力、贵阳分公司"), "10", toFiscper(yearMonth), list.get(i).getBenweibi_jl());
            };break;
            case "西安中车永电电气有限公司":
            for (Integer i = 9; i <= 10; i++) {
                tmp(tmp, i.toString(), getcompName("西安永电"), "00", toFiscper(yearMonth), list.get(i).getYuanbi_xayd());
                tmp(tmp, i.toString(), getcompName("西安永电"), "10", toFiscper(yearMonth), list.get(i).getBenweibi_xayd());
            };break;
            case "西安中车永电金风科技有限公司":
            case "西安中车永电金风科技有限公司新疆分公司":
            for (Integer i = 9; i <= 10; i++) {
                tmp(tmp, i.toString(), getcompName("西安永电金风、新疆分公司"), "00", toFiscper(yearMonth), list.get(i).getYuanbi_jf());
                tmp(tmp, i.toString(), getcompName("西安永电金风、新疆分公司"), "10", toFiscper(yearMonth), list.get(i).getBenweibi_jf());
            };break;
            case "托克逊中车永电能源装备有限公司":
            for (Integer i = 9; i <= 10; i++) {
                tmp(tmp, i.toString(), getcompName("托克逊公司"), "00", toFiscper(yearMonth), list.get(i).getYuanbi_tkx());
                tmp(tmp, i.toString(), getcompName("托克逊公司"), "10", toFiscper(yearMonth), list.get(i).getBenweibi_tkx());
            };break;
        }
        if(!listMap.isEmpty()){
            return checkError(listMap);
        }
//        iReportMapperFS19.saveOrUpdateData(toFiscper(yearMonth),insertList);
        listMap.put("SUCCESSES", "导入成功!");
        json = mapper.writeValueAsString(listMap);

        return json;
    }

    /**
     * tmp重复步骤整合
     * @param type
     * @param rbukrs
     * @param curtype
     * @param fiscper
     * @return
     */
    public void tmp(InsertFieldTHM_FS19 tmp,String type,String rbukrs,String curtype,String fiscper,String value){
        tmp = new InsertFieldTHM_FS19();
        if(!value.trim().isEmpty()) {
            try {
                Double.parseDouble(value);
            } catch (NumberFormatException e) {
                listMap.put("ERRORCELLS", "数值格式错误");
                return;
            }
        }
        tmp.setType(type);
        tmp.setRbukrs(rbukrs);
        tmp.setCurtype(curtype);
        tmp.setFiscper(fiscper);
        if(!value.trim().isEmpty()) {
        tmp.setBalance(decimalFormat.format(Double.parseDouble(value)));
        iReportMapperFS19.saveOrUpdateData(toFiscper(yearMonth),tmp);
//        insertList.add(tmp);
        }else {
            iReportMapperFS19.deleteErrorData(tmp);
        }
    }

    /**
     * 包装返回的错误list
     * @param tmpMap
     * @return
     * @throws JsonProcessingException
     */
    private String checkError(Map<String,Object> tmpMap)throws JsonProcessingException{
        Map<String,Object> map = Maps.newHashMap();
        map.put("ERROR",tmpMap);
        json = mapper.writeValueAsString(map);
        return json;
    }

}