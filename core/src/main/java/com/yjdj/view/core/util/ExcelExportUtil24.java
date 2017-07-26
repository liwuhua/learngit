package com.yjdj.view.core.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hwpf.usermodel.Field;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

public class ExcelExportUtil24 {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ExcelExportUtil24.class);
    /**
     * 工作薄对象
     */
    private SXSSFWorkbook wb;

    /**
     * 工作表对象
     */
    private org.apache.poi.ss.usermodel.Sheet sheet;

    /**
     * 样式列表
     */
    private Map<String, CellStyle> styles;

    /**
     * 当前行号
     */
    private int rownum;

    /**
     * 注解列表（Object[]{ ExcelField, Field/Method }）
     */
    List<Object[]> annotationList = Lists.newArrayList();




    /**
     * <p>
     * Discription:[构造函数]
     * </p>
     *
     * @param title 表格标题，传“空值”，表示无标题
     * @param headerList 表头列表
     */
    
    public ExcelExportUtil24(String title, String secondTitle, List<String> headerList) {
        initialize(title, secondTitle, headerList);
    }



    /**
     * <p>
     * Discription:[初始化函数]
     * </p>
     *
     * @param title 表格标题，传“空值”，表示无标题
     * @param headerList 表头列表
     */
    private void initialize(String title, String secondTitle, List<String> headerList) {
        this.wb = new SXSSFWorkbook(500);
        this.sheet = wb.createSheet("Export");
        this.styles = createStyles(wb);
        // Create title
        if (StringUtils.isNotBlank(title)) {
            Row titleRow = ((org.apache.poi.ss.usermodel.Sheet) sheet)
                    .createRow(rownum++);
            titleRow.setHeightInPoints(30);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(styles.get("title"));
            titleCell.setCellValue(title);
            ((org.apache.poi.ss.usermodel.Sheet) sheet)
                    .addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
                                    titleRow.getRowNum(), titleRow.getRowNum(),
                                    headerList.size() - 1));
        }
        //Creat second title
        if (StringUtils.isNotBlank(secondTitle)) {
        	String[] titleArr =  secondTitle.split("###");
            Row secondTitleRow = ((org.apache.poi.ss.usermodel.Sheet) sheet)
                    .createRow(rownum++);
            Cell secondTitleCell = secondTitleRow.createCell(0);
            secondTitleCell.setCellStyle(styles.get("secondTitle"));
            secondTitleCell.setCellValue(titleArr[0]);
            
            
            Cell secondTitleCell2 = secondTitleRow.createCell(1);
            XSSFCellStyle dateStyle = (XSSFCellStyle) wb.createCellStyle();
            dateStyle.setAlignment(HorizontalAlignment.CENTER);
            secondTitleCell2.setCellStyle(dateStyle);
            //"201601-201605"
            secondTitleCell2.setCellValue(titleArr[1]);
            

            ((org.apache.poi.ss.usermodel.Sheet) sheet)
            .addMergedRegion(new CellRangeAddress(secondTitleRow.getRowNum(),
            		secondTitleRow.getRowNum(), 1,6));
        }
        // Create header
        if (headerList == null) {
            throw new RuntimeException("headerList not null!");
        }
        Row headerRow = ((org.apache.poi.ss.usermodel.Sheet) sheet)
                .createRow(rownum++);
        headerRow.setHeightInPoints(16);
        for (int i = 0; i < headerList.size(); i++) {
            Cell cell = headerRow.createCell(i);
            HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
            XSSFCellStyle dateStyle = (XSSFCellStyle) wb.createCellStyle();
            dateStyle.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
            dateStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            dateStyle.setAlignment(HorizontalAlignment.CENTER);
            
            Font headerTitleFont = wb.createFont();
            headerTitleFont.setFontName("宋体");
            headerTitleFont.setColor(IndexedColors.WHITE.getIndex());
            headerTitleFont.setFontHeightInPoints((short) 9);
            dateStyle.setFont(headerTitleFont);
            cell.setCellStyle(dateStyle);
            
            String[] ss = StringUtils.split(headerList.get(i), "**", 2);
            if (ss.length == 2) {
                cell.setCellValue(ss[0]);
                org.apache.poi.ss.usermodel.Comment comment = ((org.apache.poi.ss.usermodel.Sheet) this.sheet)
                        .createDrawingPatriarch().createCellComment(
                                new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3,
                                        (short) 5, 6));
                comment.setString(new XSSFRichTextString(ss[1]));
                cell.setCellComment(comment);
            } else {
                cell.setCellValue(headerList.get(i));
            }
//			sheet.autoSizeColumn(i);
        }
        for (int i = 0; i < headerList.size(); i++) {
            int colWidth = ((org.apache.poi.ss.usermodel.Sheet) sheet)
                    .getColumnWidth(i) * 2;
            ((org.apache.poi.ss.usermodel.Sheet) sheet).setColumnWidth(i,
                    colWidth < 3000 ? 3000 : colWidth);
        }
        
        
        logger.info("Initialize success.");
    }
   
    /**
     * fs24 二级明细导出
     * @param dataList
     * @param ee
     * @param flag 1:24,2:26,3:28
     * @return
     * 
     * dataStyle
     */
    public ExcelExportUtil24 getFs24ExcelReport(List<List<Object>> dataList,ExcelExportUtil24 ee,int flag){
    	List<Integer> idList = null;
    	if(flag==1){
    		idList = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,19,20,34,35,36,56,57);
    	}else if(flag==2){
    		idList = Arrays.asList(14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,33,35,36,40);
    	}else{
    		idList = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,22,32,33,39,40,41,50,57,63);
    	}
    	
        for (int i = 0; i < dataList.size(); i++) {
        	Integer id = (Integer)dataList.get(i).get(1);
        	//if(idList.indexOf(id)!=-1){
                Row row = ee.addRow();
                for (int j = 0; j < dataList.get(i).size(); j++) {
                	if(j==4||j==5||j==6){
                		ee.addCell(row, row.getRowNum(), j, 3, true, dataList.get(i).get(j));
                	}else{
                		ee.addCell(row, row.getRowNum(), j, 1, true, dataList.get(i).get(j));
                	}
                    
                }
                /*}else{
        		Row row = ee.addRow();
        		for (int j = 0; j < dataList.get(i).size(); j++) {
        			if(j==4){
        		    ee.addCell(row, row.getRowNum(), j, 3, true, dataList.get(i).get(j));	
        			}else{
        			ee.addCell(row, row.getRowNum(), j, 1, true, dataList.get(i).get(j));	
        			}
                    
                }
            	
            }*/

        }
        return ee;
    }
    
    
    /**
     * Discription:[合并行列函数]
     *
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     * @param content
     */
    public void addMergedRegion(int firstRow, int lastRow, int firstCol, int lastCol, String content) {
        Row row = ((org.apache.poi.ss.usermodel.Sheet) sheet)
                .createRow(rownum++);
        Cell cell = row.createCell(0);
        cell.setCellStyle(styles.get("title"));
        cell.setCellValue(content);
        ((org.apache.poi.ss.usermodel.Sheet) sheet)
                .addMergedRegion(new CellRangeAddress(firstRow,
                                lastRow, firstCol, lastCol));
    }




    /**
     * <p>
     * Discription:[创建表格样式]
     * </p>
     *
     * @param wb 工作薄对象
     * @return 样式列表
     */
    @SuppressWarnings("deprecation")
    private Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        //标题格式设置
        XSSFCellStyle textTitleFormatStyle = (XSSFCellStyle) wb.createCellStyle();
        textTitleFormatStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("text"));
        textTitleFormatStyle.setAlignment(HorizontalAlignment.CENTER);
        textTitleFormatStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        textTitleFormatStyle.setBorderRight(BorderStyle.NONE);
        textTitleFormatStyle.setBorderLeft(BorderStyle.NONE);
        textTitleFormatStyle.setBorderTop(BorderStyle.NONE);
        textTitleFormatStyle.setBorderBottom(BorderStyle.NONE);
        Font titleFont = wb.createFont();
        titleFont.setFontName("黑体");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        textTitleFormatStyle.setFont(titleFont);
        styles.put("title", textTitleFormatStyle);

        //制表单位 期间
        XSSFCellStyle textUnitAndPeriodFormatStyle = (XSSFCellStyle) wb.createCellStyle();
        textUnitAndPeriodFormatStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("text"));
        textUnitAndPeriodFormatStyle.setAlignment(HorizontalAlignment.CENTER);
        textUnitAndPeriodFormatStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        textUnitAndPeriodFormatStyle.setBorderRight(BorderStyle.NONE);
        textUnitAndPeriodFormatStyle.setBorderLeft(BorderStyle.NONE);
        textUnitAndPeriodFormatStyle.setBorderTop(BorderStyle.NONE);
        textUnitAndPeriodFormatStyle.setBorderBottom(BorderStyle.NONE);
        Font secondTitleFont = wb.createFont();
        secondTitleFont.setFontName("宋体");
        secondTitleFont.setFontHeightInPoints((short) 11);
        textUnitAndPeriodFormatStyle.setFont(secondTitleFont);
        styles.put("secondTitle", textUnitAndPeriodFormatStyle);

        //列标题格式设置
        XSSFCellStyle headerTitleFormatStyle = (XSSFCellStyle) wb.createCellStyle();
        headerTitleFormatStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("text"));
        headerTitleFormatStyle.setAlignment(HorizontalAlignment.CENTER);
        headerTitleFormatStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerTitleFormatStyle.setBorderRight(BorderStyle.THIN);
        headerTitleFormatStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        headerTitleFormatStyle.setBorderLeft(BorderStyle.THIN);
        headerTitleFormatStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        headerTitleFormatStyle.setBorderTop(BorderStyle.THIN);
        headerTitleFormatStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        headerTitleFormatStyle.setBorderBottom(BorderStyle.THIN);
        headerTitleFormatStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        Font headerTitleFont = wb.createFont();
        headerTitleFont.setFontName("宋体");
        headerTitleFont.setFontHeightInPoints((short) 9);
        headerTitleFormatStyle.setFont(headerTitleFont);
        styles.put("header", headerTitleFormatStyle);

        //项目格式设置
        XSSFCellStyle data1FormatStyle = (XSSFCellStyle) wb.createCellStyle();
        data1FormatStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("text"));
        data1FormatStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        data1FormatStyle.setAlignment(HorizontalAlignment.LEFT);
        data1FormatStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        data1FormatStyle.setBorderRight(BorderStyle.THIN);
        data1FormatStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        data1FormatStyle.setBorderLeft(BorderStyle.THIN);
        data1FormatStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        data1FormatStyle.setBorderTop(BorderStyle.THIN);
        data1FormatStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        data1FormatStyle.setBorderBottom(BorderStyle.THIN);
        data1FormatStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        Font data1Font = wb.createFont();
        data1Font.setFontName("宋体");
        data1Font.setFontHeightInPoints((short) 9);
        data1FormatStyle.setFont(data1Font);
        styles.put("data1", data1FormatStyle);
        
        //行次格式设置
        XSSFCellStyle dataRnFormatStyle = (XSSFCellStyle) wb.createCellStyle();
        dataRnFormatStyle.setDataFormat(wb.createDataFormat().getFormat(BuiltinFormats.getBuiltinFormat(4)));
        dataRnFormatStyle.setAlignment(HorizontalAlignment.CENTER);
        dataRnFormatStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataRnFormatStyle.setBorderRight(BorderStyle.THIN);
        dataRnFormatStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        dataRnFormatStyle.setBorderLeft(BorderStyle.THIN);
        dataRnFormatStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        dataRnFormatStyle.setBorderTop(BorderStyle.THIN);
        dataRnFormatStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        dataRnFormatStyle.setBorderBottom(BorderStyle.THIN);
        dataRnFormatStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        Font dataRnFont = wb.createFont();
        dataRnFont.setFontName("宋体");
        dataRnFont.setFontHeightInPoints((short) 9);
        dataRnFormatStyle.setFont(dataRnFont);
        styles.put("data2", dataRnFormatStyle);
        
        //行次-粗体格式设置
        XSSFCellStyle dataRnBFormatStyle = (XSSFCellStyle) wb.createCellStyle();
        dataRnBFormatStyle.setDataFormat(wb.createDataFormat().getFormat(BuiltinFormats.getBuiltinFormat(4)));
        dataRnBFormatStyle.setAlignment(HorizontalAlignment.LEFT);
        dataRnBFormatStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataRnBFormatStyle.setBorderRight(BorderStyle.THIN);
        dataRnBFormatStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        dataRnBFormatStyle.setBorderLeft(BorderStyle.THIN);
        dataRnBFormatStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        dataRnBFormatStyle.setBorderTop(BorderStyle.THIN);
        dataRnBFormatStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        dataRnBFormatStyle.setBorderBottom(BorderStyle.THIN);
        dataRnBFormatStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        Font dataBFont = wb.createFont();
        dataBFont.setFontName("宋体");
        dataBFont.setFontHeightInPoints((short) 9);
        dataBFont.setBold(true);
        dataRnBFormatStyle.setFont(dataBFont);
        styles.put("data4", dataRnBFormatStyle);
        
        //金额格式设置
        XSSFCellStyle dataMFormatStyle = (XSSFCellStyle) wb.createCellStyle();
        dataMFormatStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("text"));
        dataMFormatStyle.setAlignment(HorizontalAlignment.LEFT);
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
        styles.put("data3", dataMFormatStyle);


        return styles;
    }

    /**
     * <p>
     * Discription:[添加一行]
     * </p>
     *
     * @return 行对象
     */
    public Row addRow() {
        return ((org.apache.poi.ss.usermodel.Sheet) sheet).createRow(rownum++);
    }

    /**
     * <p>
     * Discription:[添加一个单元格]
     * </p>
     *
     * @param row 添加的行
     * @param column 添加列号
     * @param val 添加值
     * @return 单元格对象
     */
    public Cell addCell(Row row, int column, Object val) {
        return this.addCell(row, column, val, 1, Class.class);
    }

    /**
     * <p>
     * Discription:[添加一个单元格]
     * </p>
     *
     * @param row 添加的行
     * @param rowNum
     * @param column 添加列号
     * @param flag
     * @param val 添加值
     * @return 单元格对象
     */
    public Cell addCell(Row row, int rowNum, int column,int dataStyle, boolean flag, Object val) {
        return this.addCell(row, rowNum, column, val, dataStyle, flag, Class.class);
    }

    /**
     * <p>
     * Discription:[添加一个单元格]
     * </p>
     *
     * @param row 添加的行
     * @param column 添加列号
     * @param val 添加值
     * @param align 对齐方式（1：靠左；2：居中；3：靠右）
     * @return 单元格对象
     */
    public Cell addCell(Row row, int column, Object val, int align, Class<?> fieldType) {
        Cell cell = row.createCell(column);
        CellStyle style = null;
        style = styles.get("data"
                + (align >= 1 && align <= 4 ? align : ""));

        try {
            if (val == null) {
                cell.setCellValue("");
            } else if (val instanceof String) {
                cell.setCellValue((String) val);
            } else if (val instanceof Integer) {
                cell.setCellValue((Integer) val);
            } else if (val instanceof Long) {
                cell.setCellValue((Long) val);
            } else if (val instanceof Double) {
                cell.setCellValue((Double) val);
            } else if (val instanceof Float) {
                cell.setCellValue((Float) val);
            } else if (val instanceof Date) {
                DataFormat format = wb.createDataFormat();
                style.setDataFormat(format.getFormat("yyyy-MM-dd"));
                cell.setCellValue((Date) val);
            } else {
                if (fieldType != Class.class) {
                    cell.setCellValue((String) fieldType.getMethod("setValue",
                            Object.class).invoke(null, val));
                } else {
                    cell.setCellValue((String) Class
                            .forName(
                                    this.getClass()
                                    .getName()
                                    .replaceAll(
                                            this.getClass()
                                            .getSimpleName(),
                                            "fieldtype."
                                            + val.getClass()
                                            .getSimpleName()
                                            + "Type"))
                            .getMethod("setValue", Object.class)
                            .invoke(null, val));
                }
            }
        } catch (Exception ex) {
            logger.error("Set cell value [" + row.getRowNum() + ","
                    + column + "] error: " + ex.toString());
            cell.setCellValue(val.toString());
        }
        cell.setCellStyle(style);
        return cell;
    }

    /**
     * <p>
     * Discription:[添加一个单元格]
     * </p>
     *
     * @param row 添加的行
     * @param rowNum
     * @param column 添加列号
     * @param val 添加值
     * @param align 对齐方式（1：靠左；2：居中；3：靠右）
     * @param flag
     * @param fieldType
     * @return 单元格对象
     */
    public Cell addCell(Row row, int rowNum, int column, Object val, int align, boolean flag,
            Class<?> fieldType) {
        Cell cell = row.createCell(column);
        CellStyle style = null;
        if (flag) {
            style = styles.get("data"
                    + (align >= 1 && align <= 4 ? align : ""));
        } else {
            style = styles.get("header");
        }
        

        try {
            if (val == null) {
                cell.setCellValue("");
            } else if (val instanceof String) {
                cell.setCellValue((String) val);
            } else if (val instanceof Integer) {
                cell.setCellValue((Integer) val);
            } else if (val instanceof Long) {
                cell.setCellValue((Long) val);
            } else if (val instanceof Double) {
                cell.setCellValue((Double) val);
            } else if (val instanceof Float) {
                cell.setCellValue((Float) val);
            } else if (val instanceof Date) {
                DataFormat format = wb.createDataFormat();
                style.setDataFormat(format.getFormat("yyyy-MM-dd"));
                cell.setCellValue((Date) val);
            } else {
                if (fieldType != Class.class) {
                    cell.setCellValue((String) fieldType.getMethod("setValue",
                            Object.class).invoke(null, val));
                } else {
                    cell.setCellValue((String) Class
                            .forName(
                                    this.getClass()
                                    .getName()
                                    .replaceAll(
                                            this.getClass()
                                            .getSimpleName(),
                                            "fieldtype."
                                            + val.getClass()
                                            .getSimpleName()
                                            + "Type"))
                            .getMethod("setValue", Object.class)
                            .invoke(null, val));
                }
            }
        } catch (Exception ex) {
            logger.error("Set cell value [" + row.getRowNum() + ","
                    + column + "] error: " + ex.toString());
            cell.setCellValue(val.toString());
        }
        cell.setCellStyle(style);
        return cell;
    }

    /**
     * <p>
     * Discription:[添加数据（通过annotation.ExportField添加数据）]
     * </p>
     *
     * @return list 数据列表
     */
    public <E> ExcelExportUtil24 setDataList(List<E> list) {
        for (E e : list) {
            int colunm = 0;
            Row row = this.addRow();
            StringBuilder sb = new StringBuilder();
            for (Object[] os : annotationList) {
                ExcelField ef = (ExcelField) os[0];
                Object val = null;
                // Get entity value
                try {
                    if (StringUtils.isNotBlank(ef.value())) {
                        val = Reflections.invokeGetter(e, ef.value());
                    } else {
                        if (os[1] instanceof Field) {
                            val = Reflections.invokeGetter(e,
                                    ((Executable) os[1]).getName());
                        } else if (os[1] instanceof Method) {
                            val = Reflections.invokeMethod(e,
                                    ((Method) os[1]).getName(), new Class[]{},
                                    new Object[]{});
                        }
                    }
                    // If is dict, get dict label
                    if (StringUtils.isNotBlank(ef.dictType())) {
                        // val =
                        // DictUtils.getDictLabel(val==null?"":val.toString(),
                        // ef.dictType(), "");
                    }
                } catch (Exception ex) {
                    // Failure to ignore
                    val = "";
                }
                this.addCell(row, row.getRowNum(), colunm++, val, ef.align(), true, ef.fieldType());
                sb.append(val + ", ");
            }
            logger.info("Write success: [" + row.getRowNum() + "] "
                    + sb.toString());
        }
        return this;
    }

    /**
     * <p>
     * Discription:[输出数据流]
     * </p>
     *
     * @param os 输出数据流
     */
    public ExcelExportUtil24 write(OutputStream os) throws IOException {
        wb.write(os);
        return this;
    }

    /**
     *
     * <p>
     * Discription:[输出到客户端]
     * </p>
     *
     * @param fileName 输出文件名
     */
    public void write(HttpServletRequest request, HttpServletResponse response, String fileName)
            throws IOException {
        response.reset();  //agent.contains("Firefox")

        if (request.getHeader("User-Agent").contains("Firefox")) {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");//firefox浏览器
        } else if (request.getHeader("User-Agent").contains("MSIE")) {
            fileName = URLEncoder.encode(fileName, "UTF-8");//IE浏览器 
        } else if (request.getHeader("User-Agent").contains("Chrome")) {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");//谷歌浏览器
        } else {
            fileName = URLEncoder.encode(fileName, "UTF-8");  //其他浏览器
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setHeader("Connection", "close");
        write(response.getOutputStream());
    }

    /**
     * <p>
     * Discription:[输出到文件]
     * </p>
     *
     * @param fileName 输出文件名
     */
    public ExcelExportUtil24 writeFile(String name) throws FileNotFoundException,
            IOException {
        FileOutputStream os = new FileOutputStream(name);
        this.write(os);
        return this;
    }

    /**
     * 清理临时文件
     */
    /**
     * <p>
     * Discription:[清理临时文件]
     * </p>
     */
    public ExcelExportUtil24 dispose() {
        wb.dispose();
        return this;
    }
    
    public SXSSFWorkbook getWb(){
        return wb;
    }


}
