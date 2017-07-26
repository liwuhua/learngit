package com.yjdj.view.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.Fs20TreeNode;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.mapper.GenericMapper;
import com.yjdj.view.core.mapper.IReportMapperFS20;
import com.yjdj.view.core.mapper.IReportMapperFS59;
import com.yjdj.view.core.service.GenericService;
import com.yjdj.view.core.service.IReportServiceFS20;
import com.yjdj.view.core.service.IReportServiceFS59;
import com.yjdj.view.core.util.DateUtils;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by liwuhua on 16/11/7.
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS59 implements IReportServiceFS59 {

    private final Logger log = Logger.getLogger(ReportServiceImpFS59.class);
    @Autowired
    private IReportMapperFS59 ireportmapperfs59;
    @Autowired
    private GenericService genericService;
    @Autowired
    private GenericMapper genericMapper;

    private ExcelExportUtil ee = null;

    @Override
    public Object getFs59(QueryBean queryBean,List<String> compCodeSelect) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        String resultJson = null;

        // 判断公司代码
        boolean b = genericService.isBool(queryBean.getCompCodeValue(),
                compCodeSelect, queryBean);

        List<Map<String,Object>> listMap = ireportmapperfs59.getDataFs59(queryBean.getCompCodeValue().toString().replace("[", "").replace("]", "").replace(" ", ""),
                                                                        DateUtils.formatStringDate(queryBean.getDateYearMonthStart()),
                                                                        DateUtils.formatStringDate(queryBean.getDateYearMonthEnd()));

        Map<String, Object> map = new HashMap<String, Object>();
                // 放入前端进行处理
        String bzdw = "BZDW";
        String str = "";
        if (b) {
            str = "中车永济电机有限公司合计";
        } else {
            if (queryBean.getCompCodeValue().size() > 1) {
                str = "";
            } else if (queryBean.getCompCodeValue().size() == 1) {
                String txtmd = genericMapper.getTcompCode(
                        queryBean.getCompCodeValue().get(0)).getTxtmd();
                str = txtmd;
            }
        }

        map.put(bzdw,str);
        map.put("data",listMap);

        if(!queryBean.isExport()){
            resultJson = mapper.writeValueAsString(map);
            return resultJson;
        }else{
            ee = downLoad(listMap,str);
            return ee;
        }
    }


    public ExcelExportUtil downLoad(List<Map<String,Object>> listMap,String str) throws IOException {
        List<String> headerList = getHeaderList();

        List<String> secondTitle = new ArrayList<String>();
        secondTitle.add("编制单位："+str);
        secondTitle.add(" ");
        secondTitle.add(" ");
        ee = new ExcelExportUtil("投入产出表", secondTitle, headerList,false);

        //获取sheet 合并单元格
        Sheet sheet = ee.getSheet();
        rangeCell(sheet,1,1,0,2,"编制单位："+str,false,1);

        if(CollectionUtils.isNotEmpty(listMap)){
            List<List<Object>> resultDataRowList = export1(listMap);
            NumberFormat decimalFormat = new DecimalFormat("###,###.##");

            for (int i = 0; i < resultDataRowList.size(); i++) {
                Row row = ee.addRow();
                for (int j = 0; j < resultDataRowList.get(i).size(); j++) {
                    String val = StringUtil.nullToStr(resultDataRowList.get(i).get(j));
                    if (j >= 2) {//数值处理
                        XSSFCellStyle cellStyle = getCellStyle1(false,3);
                        try{
                            ee.addCell(row, row.getRowNum(), j, decimalFormat.format(val), cellStyle, true, Double.class);
                        }catch(java.lang.IllegalArgumentException e){
                            ee.addCell(row, row.getRowNum(), j, val, cellStyle, true, Double.class);
                        }
                    } else {//行次
                        XSSFCellStyle cellStyle = getCellStyle(2);
                        ee.addCell(row, row.getRowNum(), j, val, cellStyle, false, String.class);
                    }
                }
            }
        }
        return ee;
    }

    /**
     * 获取excel表格样式
     * @return
     */
    private XSSFCellStyle getCellStyle(int align){
        SXSSFWorkbook wb = ee.getWb();
        XSSFCellStyle dataMFormatStyle = (XSSFCellStyle) wb.createCellStyle();
        dataMFormatStyle.setDataFormat(wb.createDataFormat().getFormat(BuiltinFormats.getBuiltinFormat(4)));
        if(align == 1){
            dataMFormatStyle.setAlignment(HorizontalAlignment.LEFT);
        }else if(align == 2){
            dataMFormatStyle.setAlignment(HorizontalAlignment.CENTER);
        }else if(align == 3){
            dataMFormatStyle.setAlignment(HorizontalAlignment.RIGHT);
        }
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

    private List<List<Object>> export1(List<Map<String,Object>> listMap)throws IOException {
//        int num = 1;
        List<List<Object>> dataRowList = Lists.newArrayList();
        List<Object> list_ex = new ArrayList<Object>();
        //为导出金额加千分位
        NumberFormat decimalFormat = new DecimalFormat("###,###.##");

        for(Map<String,Object> map : listMap){
            list_ex.add(map.get("ZTXT"));
            list_ex.add(map.get("ZNUM"));
            list_ex.add(decimalFormat.format(map.get("BALANCE")));

            dataRowList.add(list_ex);
            list_ex = new ArrayList<Object>();
        }

        return dataRowList;
    }

    private List<String> getHeaderList(){
        List<String> headerList = Lists.newArrayList();
        headerList.add("项目");
        headerList.add("栏次");
        headerList.add("本年累计发生额");

        return  headerList;
    }

    /**
     * 合并单元格
     * @param sheet
     * @param startRow
     * @param endRow
     * @param startCol
     * @param endCol
     * @num 1-靠左,2-居中,3-靠右
     * @param str
     */
    private void rangeCell(Sheet sheet,int startRow,int endRow,int startCol,int endCol,String str,boolean isSolid,int num){
        XSSFCellStyle cellStyle = getCellStyle1(isSolid,num);
        sheet.addMergedRegion(new CellRangeAddress(startRow,endRow,startCol,endCol));
        Cell cell = sheet.getRow(startRow).getCell(startCol);
        cell.setCellValue(str);
        cell.setCellStyle(cellStyle);
    }

    /**
     * 获取excel表格样式
     * @num 1-靠左,2-居中,3-靠右
     * @return
     */
    private XSSFCellStyle getCellStyle1(boolean isSolid,int num){
        SXSSFWorkbook wb = ee.getWb();
        XSSFCellStyle dataMFormatStyle = (XSSFCellStyle) wb.createCellStyle();
        if(num == 1){
            dataMFormatStyle.setAlignment(HorizontalAlignment.LEFT);
        }else if(num == 2){
            dataMFormatStyle.setAlignment(HorizontalAlignment.CENTER);
        }else if(num == 3){
            dataMFormatStyle.setAlignment(HorizontalAlignment.RIGHT);
        }
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
}
