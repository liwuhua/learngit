package com.yjdj.view.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.InsertFieldTHM_FS07;
import com.yjdj.view.core.entity.mybeans.THM_FS07;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.mapper.GenericMapper;
import com.yjdj.view.core.mapper.IReportMapperFS07;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS07;
import com.yjdj.view.core.util.*;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.text.ParseException;
import java.util.List;

/**
 * Created by zhaomenglu on 2016/12/14
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS07 implements IReportServiceFS07 {
    @Autowired
    private IReportMapperFS07 iReportMapperFS07;

    @Autowired
    private GenericService genericService;

    @Autowired
    private GenericMapper genericMapper;

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");//格式化设置

    private boolean isExport = false; //如果是true则为导数据，false则为查数据
    private List<List<Object>> resultDataRowList = null;
//    private List<String> headerList = null;
    private ExcelExportUtil ee = null;
//    private String startTime = null;
//    private String endTime = null;

    @Override
    public Object selectDataTrue(QueryBean queryBean,List<String> compCodeList) throws JsonProcessingException, IOException, DataAccessException, ParseException {

//        headerList = Lists.newArrayList();
//        headerList.add("序号");
//        headerList.add("公司代码");
//        headerList.add("公司名称");
//        headerList.add("客户编码");
//        headerList.add("客户名称");
//        headerList.add("统驭科目");
//        headerList.add("当期销售收入（不含税）");
//        headerList.add("期初余额");
//        headerList.add("小计");
//        headerList.add("主营业务收入");
//        headerList.add("其他业务收入");
//        headerList.add("销项税金");
//        headerList.add("其他");
//        headerList.add("小计");
//        headerList.add("现金");
//        headerList.add("银行承兑");
//        headerList.add("商业承兑");
//        headerList.add("云信");
//        headerList.add("抵账");
//        headerList.add("期末余额");
//        headerList.add("6个月以内");
//        headerList.add("6个月—1年");
//        headerList.add("1—2年");
//        headerList.add("2—3年");
//        headerList.add("3—4年");
//        headerList.add("4—5年");
//        headerList.add("5年以上");
//        headerList.add("6个月以内");
//        headerList.add("6个月—1年");
//        headerList.add("1—2年");
//        headerList.add("2—3年");
//        headerList.add("3—4年");
//        headerList.add("4—5年");
//        headerList.add("5年以上");
//        headerList.add("小计");

        List<String> headerList = getHeaderList();

        int flag = 0;//查询flag为0，导出flag为1
        isExport = queryBean.isExport();
        if (isExport) {
            flag = 1;
        }
        ObjectMapper mapper = new ObjectMapper();
//获
        String budatstart = queryBean.getDateYearMonthStart();
        String budatend = queryBean.getDateYearMonthEnd();
//给年月日期中间补0
        String start1 = DateUtils.formatStringDate(queryBean.getDateYearMonthStart());
        String end1 = DateUtils.formatStringDate(queryBean.getDateYearMonthEnd());

//取截止月最后一天
        String end2 = DateUtils.getDateStr(queryBean.getDateYearMonthEnd());

//计算账龄日期
        String date1;
        date1 = DateUtils.getDayBeforeStr(end2, 0);
        String date2;
        date2 = DateUtils.getDayBeforeStr(end2, 180);
        String date3;
        date3 = DateUtils.getDayBeforeStr(end2, 181);
        String date4;
        date4 = DateUtils.getDayBeforeStr(end2, 360);
        String date5;
        date5 = DateUtils.getDayBeforeStr(end2, 361);
        String date6;
        date6 = DateUtils.getDayBeforeStr(end2, 720);
        String date7;
        date7 = DateUtils.getDayBeforeStr(end2, 721);
        String date8;
        date8 = DateUtils.getDayBeforeStr(end2, 1080);
        String date9;
        date9 = DateUtils.getDayBeforeStr(end2, 1081);
        String date10;
        date10 = DateUtils.getDayBeforeStr(end2, 1440);
        String date11;
        date11 = DateUtils.getDayBeforeStr(end2, 1441);
        String date12;
        date12 = DateUtils.getDayBeforeStr(end2, 1800);
        String date13;
        date13 = DateUtils.getDayBeforeStr(end2, 1801);



        boolean b = genericService.isBool(queryBean.getCompCodeValue(),compCodeList,queryBean);
        String bzdwStr = "";
        if(b){
            bzdwStr = "中车永济电机有限公司合计";
        }else{
            if(queryBean.getCompCodeValue().size() > 1){
                bzdwStr = "";
            }else if(queryBean.getCompCodeValue().size() == 1){
                String txtmd = genericMapper.getTcompCode(queryBean.getCompCodeValue().get(0)).getTxtmd();
                bzdwStr =txtmd;
            }
        }


        List<THM_FS07> list2 = iReportMapperFS07.selectDataTrue_07(queryBean.getCompCodeValue(), queryBean.getKunnrValue(), queryBean.getKunnrValue(),budatstart,budatend,start1,end1,date1, date2, date3, date4, date5, date6, date7, date8,date9,date10,date11,date12,date13,queryBean.getStartitem(),queryBean.getPageitem(),flag);
//        List<THM_FS07> result = new ArrayList<THM_FS07>();

        if (!isExport) {
            String resultJson1 = mapper.writeValueAsString(list2);
            return resultJson1;
        } else {
            //导出数据
            resultDataRowList = export1(list2);

            String secondStr = "期间：" + queryBean.getDateYearMonthStart().substring(0, 4) + "年"
                    + queryBean.getDateYearMonthStart().substring(4, 6) + "月 — "
                    + queryBean.getDateYearMonthEnd().substring(0, 4) + "年" + queryBean.getDateYearMonthEnd().substring(4, 6)
                    + "月";

            List<String> secondTitle = new ArrayList<String>();
            //给导出数据加千分位
            NumberFormat decimalFormat = new DecimalFormat("###,###.##");
            secondTitle.add("编制单位："+bzdwStr);
            secondTitle.add(" ");
            secondTitle.add(" ");
            secondTitle.add(" ");
            secondTitle.add(" ");
            secondTitle.add(" ");
            secondTitle.add(" ");
            secondTitle.add(" ");
            secondTitle.add(" ");
            secondTitle.add(" ");
            secondTitle.add(" ");
            secondTitle.add(" ");
            secondTitle.add(" ");
            secondTitle.add(" ");
            secondTitle.add(" ");
            secondTitle.add(" ");
            secondTitle.add(" ");
            secondTitle.add(secondStr);

            ee = new ExcelExportUtil("应收账款统计分析表", secondTitle, headerList,true);
//            List<List<Object>> dataList = Lists.newArrayList();
//            for (int j = 0; j < resultDataRowList.size(); j++) {
//                dataList.add(resultDataRowList.get(j));
//            }
//            for (int i = 0; i < resultDataRowList.size(); i++) {
//                Row row = ee.addRow();
//                for (int j = 0; j < resultDataRowList.get(i).size(); j++) {
//                    ee.addCell(row, j, resultDataRowList.get(i).get(j));
//                }
//            }


            //获取sheet 合并单元格
            Sheet sheet = ee.getSheet();
            rangeCell(sheet,1,1,0,1,"编制单位："+bzdwStr,false);
            rangeCell(sheet,1,1,2,34,secondStr,false);

            rangeCell(sheet,2,3,0,0,"序号",true);
            rangeCell(sheet,2,3,1,1,"公司代码",true);
            rangeCell(sheet,2,3,2,2,"公司名称",true);
            rangeCell(sheet,2,3,3,3,"客户编码",true);
            rangeCell(sheet,2,3,4,4,"客户名称",true);
            rangeCell(sheet,2,3,5,5,"统驭科目",true);
            rangeCell(sheet,2,3,6,6,"当期销售收入（不含税）",true);
            rangeCell(sheet,2,3,7,7,"期初余额",true);
            rangeCell(sheet,2,2,8,12,"当期销售收入",true);
            rangeCell(sheet,2,2,13,18,"当期回款情况",true);
            rangeCell(sheet,2,3,19,19,"期末余额",true);
            rangeCell(sheet,2,2,20,26,"应收账款账龄",true);
            rangeCell(sheet,2,2,27,34,"其中：质保金账龄",true);


            for (int i = 0; i < resultDataRowList.size(); i++) {
                Row row = ee.addRow();
                for (int j = 0; j < resultDataRowList.get(i).size(); j++) {
                    String val = StringUtil.nullToStr(resultDataRowList.get(i).get(j));
                    if (j >= 6) {//数值处理
                        try{
                            ee.addCell(row, row.getRowNum(), j, decimalFormat.format(val), 3, true, Double.class);
                        }catch(java.lang.IllegalArgumentException e){
                            ee.addCell(row, row.getRowNum(), j, val, 3, true, Double.class);
                        }
                    } else {//行次
                        XSSFCellStyle cellStyle = getCellStyle();
                        ee.addCell(row, row.getRowNum(), j, val, cellStyle, true, String.class);
                    }
                }
            }
            return ee;
        }
    }

    //导出数据
    private List<List<Object>> export1(List<THM_FS07> list2)throws IOException {
        // TODO Auto-generated method stub
        //
        int num = 1;
        List<List<Object>> dataRowList = Lists.newArrayList();
        List<Object> list_ex = new ArrayList<Object>();
        //为导出金额加千分位
        NumberFormat decimalFormat = new DecimalFormat("###,###.##");

        for (THM_FS07 value : list2) {
            list_ex.add(num);  //序号
            list_ex.add(value.getBukrs()); //公司代码
            list_ex.add(value.getGs_txt());  //公司名称
            list_ex.add(value.getKunnr()); //客户编码
            list_ex.add(value.getKh_txt());  //客户名称
            list_ex.add(value.getHkont()); //统驭科目
            list_ex.add(decimalFormat.format(value.getDqxssr()));  //当期销售收入（不含税）
            list_ex.add(decimalFormat.format(value.getQcye())); //期初余额
            list_ex.add(decimalFormat.format(value.getXj_s()));  //当期销售收入小计
            list_ex.add(decimalFormat.format(value.getZyywsr())); //主营业务收入
            list_ex.add(decimalFormat.format(value.getQtywsr()));  //其他业务收入
            list_ex.add(decimalFormat.format(value.getXxsj())); //销项税金
            list_ex.add(decimalFormat.format(value.getQt())); //其他
            list_ex.add(decimalFormat.format(value.getXj_h())); //当期回款情况小计
            list_ex.add(decimalFormat.format(value.getXianjin())); //现金
            list_ex.add(decimalFormat.format(value.getYhcd())); //银行承兑
            list_ex.add(decimalFormat.format(value.getSycd())); //商业承兑
            list_ex.add(decimalFormat.format(value.getYunxin())); //云信
            list_ex.add(decimalFormat.format(value.getDizhang())); //抵账
            list_ex.add(decimalFormat.format(value.getQmye())); //期末余额
            list_ex.add(decimalFormat.format(value.getYs_sixmonth())); //应收账龄-6个月以内
            list_ex.add(decimalFormat.format(value.getYs_sixone())); //应收账龄-6个月—1年
            list_ex.add(decimalFormat.format(value.getYs_onetwo())); //应收账龄1年—2年
            list_ex.add(decimalFormat.format(value.getYs_twothree())); //应收账龄2年—3年
            list_ex.add(decimalFormat.format(value.getYs_threefour())); //应收账龄3年—4年
            list_ex.add(decimalFormat.format(value.getYs_fourfive())); //应收账龄4年—5年
            list_ex.add(decimalFormat.format(value.getYs_fiveyear())); //应收账龄5年以上
            list_ex.add(decimalFormat.format(value.getZb_sixmonth())); //质保账龄-6个月以内
            list_ex.add(decimalFormat.format(value.getZb_sixone())); //质保账龄-6个月—1年
            list_ex.add(decimalFormat.format(value.getZb_onetwo())); //质保账龄1年—2年
            list_ex.add(decimalFormat.format(value.getZb_twothree())); //质保账龄2年—3年
            list_ex.add(decimalFormat.format(value.getZb_threefour())); //质保账龄3年—4年
            list_ex.add(decimalFormat.format(value.getZb_fourfive())); //质保账龄4年—5年
            list_ex.add(decimalFormat.format(value.getZb_fiveyear())); //质保账龄5年以上
            list_ex.add(decimalFormat.format(value.getZb_xiaoji())); //质保账龄小计
            num++;
            dataRowList.add(list_ex);
            list_ex = new ArrayList<Object>();
        }

        return dataRowList;
    }

    /**
     * 获取excel表格样式
     * @return
     */
    private XSSFCellStyle getCellStyle(){
        SXSSFWorkbook wb = ee.getWb();
        XSSFCellStyle dataMFormatStyle = (XSSFCellStyle) wb.createCellStyle();
        dataMFormatStyle.setDataFormat(wb.createDataFormat().getFormat(BuiltinFormats.getBuiltinFormat(4)));
        dataMFormatStyle.setAlignment(HorizontalAlignment.CENTER);
        dataMFormatStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataMFormatStyle.setBorderRight(BorderStyle.THIN);
        dataMFormatStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        dataMFormatStyle.setBorderLeft(BorderStyle.THIN);
        dataMFormatStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        dataMFormatStyle.setBorderTop(BorderStyle.THIN);
        dataMFormatStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        dataMFormatStyle.setBorderBottom(BorderStyle.THIN);
        dataMFormatStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        dataMFormatStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        dataMFormatStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        Font dataMFont = wb.createFont();
        dataMFont.setFontName("宋体");
        dataMFont.setFontHeightInPoints((short) 9);
        dataMFormatStyle.setFont(dataMFont);

        return dataMFormatStyle;
    }

    /**
     * 获取excel表格样式
     * @return
     */
    private XSSFCellStyle getCellStyle1(boolean isSolid){
        SXSSFWorkbook wb = ee.getWb();
        XSSFCellStyle dataMFormatStyle = (XSSFCellStyle) wb.createCellStyle();
        dataMFormatStyle.setAlignment(HorizontalAlignment.CENTER);
        dataMFormatStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        if(isSolid){
            dataMFormatStyle.setBorderRight(BorderStyle.THIN);
            dataMFormatStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
            dataMFormatStyle.setBorderLeft(BorderStyle.THIN);
            dataMFormatStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            dataMFormatStyle.setBorderTop(BorderStyle.THIN);
            dataMFormatStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
            dataMFormatStyle.setBorderBottom(BorderStyle.THIN);
            dataMFormatStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        }
        Font dataMFont = wb.createFont();
        dataMFont.setFontName("宋体");
        dataMFormatStyle.setFont(dataMFont);

        return dataMFormatStyle;
    }

    /**
     * 合并单元格
     * @param sheet
     * @param startRow
     * @param endRow
     * @param startCol
     * @param endCol
     * @param str
     */
    private void rangeCell(Sheet sheet,int startRow,int endRow,int startCol,int endCol,String str,boolean isSolid){
        XSSFCellStyle cellStyle = getCellStyle1(isSolid);
        sheet.addMergedRegion(new CellRangeAddress(startRow,endRow,startCol,endCol));
        Cell cell = sheet.getRow(startRow).getCell(startCol);
        cell.setCellValue(str);
        cell.setCellStyle(cellStyle);
    }

    @Override
    public Object importData(String filePath, List<String> compCodeList) throws JsonProcessingException, InvalidFormatException, IOException, InstantiationException, IllegalAccessException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
        String json = null;

        Map<String, Object> listMap = new HashMap<String, Object>();
        Map<String, Object> errorMap = new HashMap<String, Object>();

        ObjectMapper mapper = new ObjectMapper();
        ExcelImportUtil ei = new ExcelImportUtil(filePath, 3);

        List<String> headerList = getHeaderList();

        json = ei.checkHeadList(headerList, listMap);
        if (null != json) {
            return json;
        }


        String time = ei.getCellValue(1,2).toString();
        time=time.replace("期间：","").replace("年","").replace("月","").replace(" ","");
        String impdate = time.split("-|—")[0];
        if (!FileUtils.isValidDate(impdate)) {
            listMap.put("ERRORTIME", "日期填写错误!");
        }
        impdate=impdate.substring(0,4)+"0"+impdate.substring(4,6);

        List<InsertFieldTHM_FS07> list = ei.getDataList(InsertFieldTHM_FS07.class,"FS07");
//        if ("ERROR".equals(list.get(list.size() - 1))) {
//            for (int i = 0; i < list.size() - 1; i++) {
//                listMap.put("ERROR" + i, list.get(i));
//            }
//            //如果返回的结果存在不合理的数据，那么将数据结果以json格式返回
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("ERROR", listMap);
//            if (listMap.size() > 0) {
//                json = mapper.writeValueAsString(map);
//                return json;
//            }
//        }

        List<String> valids = checkData(list);
        if(valids.size() > 0){
            listMap.put("ERRORDATA", valids);
            errorMap.put("ERROR", listMap);
            return mapper.writeValueAsString(errorMap);
        }


        HashMap<String,String> map = null;
        List<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
        for (InsertFieldTHM_FS07 insert: list) {
            map = new HashMap<String,String>();
            map.put("bukrs",insert.getBukrs());
            map.put("gs_txt",insert.getGs_txt());
            map.put("kunnr",insert.getKunnr());
            map.put("kh_txt",insert.getKh_txt());
            map.put("hkont",insert.getHkont());
            map.put("dqxssr",insert.getDqxssr());
            map.put("qcye",insert.getQcye());
            map.put("xj_s",insert.getXj_s());
            map.put("zyywsr",insert.getZyywsr());
            map.put("qtywsr",insert.getQtywsr());
            map.put("xxsj",insert.getXxsj());
            map.put("qt",insert.getQt());
            map.put("xj_h",insert.getXj_h());
            map.put("xianjin",insert.getXianjin());
            map.put("yhcd",insert.getYhcd());
            map.put("sycd",insert.getSycd());
            map.put("yunxin",insert.getYunxin());
            map.put("dizhang",insert.getDizhang());
            map.put("qmye",insert.getQmye());
            map.put("ys_sixmonth",insert.getYs_sixmonth());
            map.put("ys_sixone",insert.getYs_sixone());
            map.put("ys_onetwo",insert.getYs_onetwo());
            map.put("ys_twothree",insert.getYs_twothree());
            map.put("ys_threefour",insert.getYs_threefour());
            map.put("ys_fourfive",insert.getYs_fourfive());
            map.put("ys_fiveyear",insert.getYs_fiveyear());
            map.put("zb_sixmonth",insert.getZb_sixmonth());
            map.put("zb_sixone",insert.getZb_sixone());
            map.put("zb_onetwo",insert.getZb_onetwo());
            map.put("zb_twothree",insert.getZb_onetwo());
            map.put("zb_threefour",insert.getZb_twothree());
            map.put("zb_fourfive",insert.getZb_fourfive());
            map.put("zb_fiveyear",insert.getZb_fiveyear());
            map.put("zb_xiaoji",insert.getZb_xiaoji());
            map.put("impdate",impdate);
            /**
             * 判断当前项目对应的公司代码是否在该登录用户的权限之内，如果不在，返回错误提示
             */
            String bukrs = insert.getBukrs();
            if(compCodeList.indexOf(bukrs)==-1&&bukrs!=null&&!insert.getBukrs().equals("合计")){
                listMap.put("ERROR", "项目号 ："+insert.getBukrs()+" 没有导入权限！");
                json = mapper.writeValueAsString(listMap);
                return json;
            }else{
                data.add(map);

            }

        }
        for (HashMap<String, String> hash : data) {
            iReportMapperFS07.saveOrUpdateData(hash);
        }

        listMap.put("SUCCESSES", "导入成功!");
        json = mapper.writeValueAsString(listMap);
        return json;
    }

    private List<String> checkData(List<InsertFieldTHM_FS07> list) throws IllegalAccessException {
        List<String> valids = new ArrayList<String>();
        int rownum = 4;
        for (InsertFieldTHM_FS07 insert : list) {
            rownum++;
            Class cls = insert.getClass();
            Field[] fs = cls.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                if (i > 5) {
                    Field f = fs[i];
                    //这行代码把对象的字段设置为public访问属性.
                    f.setAccessible(true);
                    if (!FileUtils.isNumeric(f.get(insert))) {
                        valids.add("第 " + rownum + " 行第 " + (i + 1) + " 列数据格式错误!");
                        if (!FileUtils.isNumeric(insert.getDqxssr().replaceAll(",", "").replace("，", ""))) {
                            valids.add("第" + rownum + "行第" + (i + 1) + "列数据格式错误!");
                        }
                    }
                }
            }
        }
        return valids;
    }
    private List<String> getHeaderList(){
        List<String> headerList = Lists.newArrayList();
        headerList.add("序号");
        headerList.add("公司代码");
        headerList.add("公司名称");
        headerList.add("客户编码");
        headerList.add("客户名称");
        headerList.add("统驭科目");
        headerList.add("当期销售收入（不含税）");
        headerList.add("期初余额");
        headerList.add("小计");
        headerList.add("主营业务收入");
        headerList.add("其他业务收入");
        headerList.add("销项税金");
        headerList.add("其他");
        headerList.add("小计");
        headerList.add("现金");
        headerList.add("银行承兑");
        headerList.add("商业承兑");
        headerList.add("云信");
        headerList.add("抵账");
        headerList.add("期末余额");
        headerList.add("6个月以内");
        headerList.add("6个月—1年");
        headerList.add("1—2年");
        headerList.add("2—3年");
        headerList.add("3—4年");
        headerList.add("4—5年");
        headerList.add("5年以上");
        headerList.add("6个月以内");
        headerList.add("6个月—1年");
        headerList.add("1—2年");
        headerList.add("2—3年");
        headerList.add("3—4年");
        headerList.add("4—5年");
        headerList.add("5年以上");
        headerList.add("小计");
        return  headerList;
    }

}



