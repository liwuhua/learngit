package com.yjdj.view.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.usermodel.Field;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.InsertFieldTHM_FS05;

public class ExcelImportUtil2227<E> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ExcelImportUtil2227.class);

    /**
     * 工作薄对象
     */
    private Workbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 标题行号
     */
    private int headerNum;

    /**
     * <p>
     * Discription:[构造函数]
     * </p>
     *
     * @param path 导入文件，读取第一个工作表
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ExcelImportUtil2227(String fileName, int headerNum)
            throws InvalidFormatException, IOException {
        this(new File(fileName), headerNum);
    }

    /**
     * <p>
     * Discription:[构造函数]
     * </p>
     *
     * @param path 导入文件对象，读取第一个工作表
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ExcelImportUtil2227(File file, int headerNum) throws InvalidFormatException,
            IOException {
        this(file, headerNum, 0);
    }

    /**
     * <p>
     * Discription:[构造函数]
     * </p>
     *
     * @param path 导入文件
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ExcelImportUtil2227(String fileName, int headerNum, int sheetIndex)
            throws InvalidFormatException, IOException {
        this(new File(fileName), headerNum, sheetIndex);
    }

    /**
     * <p>
     * Discription:[构造函数]
     * </p>
     *
     * @param path 导入文件对象
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ExcelImportUtil2227(File file, int headerNum, int sheetIndex)
            throws InvalidFormatException, IOException {
        this(file.getName(), new FileInputStream(file), headerNum, sheetIndex);
    }

    /**
     * <p>
     * Discription:[构造函数]
     * </p>
     *
     * @param file 导入文件对象
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ExcelImportUtil2227(MultipartFile multipartFile, int headerNum,
            int sheetIndex) throws InvalidFormatException, IOException {
        this(multipartFile.getOriginalFilename(), multipartFile
                .getInputStream(), headerNum, sheetIndex);
    }

    /**
     * <p>
     * Discription:[构造函数]
     * </p>
     *
     * @param path 导入文件对象
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ExcelImportUtil2227(String fileName, InputStream is, int headerNum,
            int sheetIndex) throws InvalidFormatException, IOException {
        if (StringUtils.isBlank(fileName)) {
            throw new RuntimeException("导入文档为空!");
        } else if (fileName.toLowerCase().endsWith("xls")) {
            this.wb = new HSSFWorkbook(is);
        } else if (fileName.toLowerCase().endsWith("xlsx")) {
            this.wb = new XSSFWorkbook(is);
        } else {
            throw new RuntimeException("文档格式不正确!");
        }
        if (this.wb.getNumberOfSheets() < sheetIndex) {
            throw new RuntimeException("文档中没有工作表!");
        }
        this.sheet = this.wb.getSheetAt(sheetIndex);
        this.headerNum = headerNum;
        logger.info("Initialize success.");
    }

    /**
     * 获取表单对象
     * @return
     */
    public Sheet getSheet(){
        return ((org.apache.poi.ss.usermodel.Sheet) this.sheet);
    }

    /**
     * <p>
     * Discription:[获取行对象]
     * </p>
     *
     * @param rownum
     * @return
     */
    public Row getRow(int rownum) {
        return ((org.apache.poi.ss.usermodel.Sheet) this.sheet).getRow(rownum);
    }

    /**
     * <p>
     * Discription:[获取行对象]
     * </p>
     *
     * @param rownum
     * @return
     */
    public int getDataRowNum() {
        return headerNum + 1;
    }

    /**
     * <p>
     * Discription:[获取最后一个数据行号]
     * </p>
     *
     * @return
     */
    public int getLastDataRowNum() {
        //return this.sheet.getLastRowNum() + headerNum;
        return this.sheet.getLastRowNum() + 1;
    }

    /**
     * <p>
     * Discription:[获取最后一个列号]
     * </p>
     *
     * @return
     */
    public int getLastCellNum() {
        return this.getRow(headerNum).getLastCellNum();
    }
     
    /**
     * <p>
     * Discription:[获取单元格值]
     * </p>
     *
     * @param row 获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     */
    
    /**
     * <p>
     * cell excel中输入值时     如果为文本类型的  那么包括数字都正常                             Cell.CELL_TYPE_STRING
     *                    其他类型的格式 那么数值为科学计数法,其他值正常显示   Cell.CELL_TYPE_NUMERIC
     * 
     * 
     */
    public Object getCellValue(Row row, int column) {
        Object val = "";
        try {
            Cell cell = row.getCell(column);
            if (cell != null) {
                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {//数字
                    val = cell.getNumericCellValue();
                	BigDecimal bigDecimal = new BigDecimal(val+""); //2.0161111E7  20161111
                	val=bigDecimal.toPlainString();
                    
                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {//字符创
                    val = cell.getStringCellValue();
                } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {//公式
                    val = cell.getCellFormula();
                } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {//布尔值
                    val = cell.getBooleanCellValue();
                } else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {//故障
                    val = cell.getErrorCellValue();
                }
            }

        } catch (Exception e) {
            return val;
        }
        return val;
    }

    /**
     * <p>
     * Discription:[取得指定行和列的值]
     * </p>
     *
     * @param cls 导入对象类型
     * @param groups 导入分组
     */
    public Object getCellValue(int rowNum, int column) {
        Object value = null;
        Row row = this.getRow(rowNum);
        if (rowNum >= 0 && column >= 0) {
            value = getCellValue(row, column);
        } else {
            throw new RuntimeException("The column " + column + " OR " + rowNum + "are error!");
        }
        return value;
    }

    /**
     * <p>
     * Discription:[获取导入数据列表]
     * </p>
     *
     * @param cls 导入对象类型
     * @param groups 导入分组
     */
    public <E> List<E> getDataList(Class<E> cls, String flag, int... groups)
            throws InstantiationException, IllegalAccessException {
        List<Object[]> annotationList = Lists.newArrayList();

        // Get annotation field
        java.lang.reflect.Field[] fs = cls.getDeclaredFields();
        for (java.lang.reflect.Field f : fs) {

            ExcelField ef = f.getAnnotation(ExcelField.class);
            if (ef != null && (ef.type() == 0 || ef.type() == 2)) {
                if (groups != null && groups.length > 0) {
                    boolean inGroup = false;
                    for (int g : groups) {
                        if (inGroup) {
                            break;
                        }
                        for (int efg : ef.groups()) {
                            if (g == efg) {
                                inGroup = true;
                                annotationList.add(new Object[]{ef, f});
                                break;
                            }
                        }
                    }
                } else {
                    annotationList.add(new Object[]{ef, f});
                }
            }
        }
        // Get annotation method
        Method[] ms = cls.getDeclaredMethods();
        for (Method m : ms) {
            ExcelField ef = m.getAnnotation(ExcelField.class);
            if (ef != null && (ef.type() == 0 || ef.type() == 2)) {
                if (groups != null && groups.length > 0) {
                    boolean inGroup = false;
                    for (int g : groups) {
                        if (inGroup) {
                            break;
                        }
                        for (int efg : ef.groups()) {
                            if (g == efg) {
                                inGroup = true;
                                annotationList.add(new Object[]{ef, m});
                                break;
                            }
                        }
                    }
                } else {
                    annotationList.add(new Object[]{ef, m});
                }
            }
        }
        // Field sorting
        Collections.sort(annotationList, new Comparator<Object[]>() {
            public int compare(Object[] o1, Object[] o2) {
                return new Integer(((ExcelField) o1[0]).sort())
                        .compareTo(new Integer(((ExcelField) o2[0]).sort()));
            }
        ;
        });
		logger.info("Import column count:" + annotationList.size());
        // Get excel data
        List<E> dataList = Lists.newArrayList();
        List<E> dataCheckList = Lists.newArrayList();
        List<E> dataTmpList = Lists.newArrayList();
        logger.info("all rows:" + this.getLastDataRowNum());
        ExcelDataCheck excelDataCheck = ExcelDataCheck.class.newInstance();
        for (int i = this.getDataRowNum(); i < this.getLastDataRowNum(); i++) {
            E e = (E) cls.newInstance();
            int column = 0;
            Row row = this.getRow(i);
            StringBuilder sb = new StringBuilder();
            for (Object[] os : annotationList) {
                Object val = this.getCellValue(row, column++);
                if (dataTmpList.size() != 0) {
                    dataCheckList.add((E) dataTmpList);
                }
                if (val != null) {
                    ExcelField ef = (ExcelField) os[0];
                    // If is dict type, get dict value
                    if (StringUtils.isNotBlank(ef.dictType())) {
                        // val = DictUtils.getDictValue(val.toString(),
                        // ef.dictType(), "");
                    }
                    // Get param type and type cast
                    Class<?> valType = Class.class;
                    if (os[1] instanceof Field) {
                        valType = ((java.lang.reflect.Field) os[1]).getType();
                    } else if (os[1] instanceof Method) {
                        Method method = ((Method) os[1]);
                        if ("get".equals(method.getName().substring(0, 3))) {
                            valType = method.getReturnType();
                        } else if ("set".equals(method.getName()
                                .substring(0, 3))) {
                            valType = ((Method) os[1]).getParameterTypes()[0];
                        }
                    }
                    logger.info("Import value type: [" + i + "," + column + "] " + valType);
                    try {
                        if (valType == String.class) {
                            String s = String.valueOf(val.toString());
                            if (StringUtils.endsWith(s, ".0")) {
                                val = StringUtils.substringBefore(s, ".0");
                            } else {
                                val = String.valueOf(val.toString());
                            }
                        } else if (valType == Integer.class) {
                        	if(val.toString().trim().equals("")){
                        		val = null;
                        	}else{
                        		val = Double.valueOf(val.toString()).intValue();
                        	}
                        } else if (valType == Long.class) {
                        	if(val.toString().trim().equals("")){
                        		val = null;
                        	}else{
                        		val = Double.valueOf(val.toString()).longValue();
                        	}
                        } else if (valType == Double.class) {
                        	if(val.toString().trim().equals("")){
                        		val = null;
                        	}else{
                        		val = Double.valueOf(val.toString()).doubleValue();
                        	}
                        } else if (valType == Float.class) {
                        	if(val.toString().trim().equals("")){
                        		val = null;
                        	}else{
                        		val = Float.valueOf(val.toString());;
                        	}
                        } else if (valType == Date.class) {
                        	if(val.toString().trim().equals("")){
                        		val = null;
                        	}else{
                        		val = DateUtil.getJavaDate((Double) val);
                        	}
                            
                        } else {
                            if (ef.fieldType() != Class.class) {
                                val = ef.fieldType()
                                        .getMethod("getValue", String.class)
                                        .invoke(null, val.toString());
                            } else {
                                val = Class
                                        .forName(
                                                this.getClass()
                                                .getName()
                                                .replaceAll(
                                                        this.getClass()
                                                        .getSimpleName(),
                                                        "fieldtype."
                                                        + valType
                                                        .getSimpleName()
                                                        + "Type"))
                                        .getMethod("getValue", String.class)
                                        .invoke(null, val.toString());
                            }
                        }
                    } catch (Exception ex) {
                        logger.info("Get cell value [" + i + "," + column + "] error: " + ex.toString());
                        val = null;
                    }
                    // set entity value
                    if (os[1] instanceof Field) {
                        Reflections.invokeSetter(e,
                                ((Method) os[1]).getName(), val);
                    } else if (os[1] instanceof Method) {
                        String mthodName = ((Method) os[1]).getName();
                        if ("get".equals(mthodName.substring(0, 3))) {
                            mthodName = "set"
                                    + StringUtils.substringAfter(mthodName,
                                            "get");
                        }
                        Reflections.invokeMethod(e, mthodName,
                                new Class[]{valType}, new Object[]{val});
                    }
                }
                sb.append(val + ", ");
            }
            dataList.add(e);
        }
        if (dataCheckList.size() != 0) {
            dataCheckList.add((E) "ERROR");
            return dataCheckList;
        }
        return dataList;
    }

    public int getHeaderNum() {
        return this.headerNum;
    }

    /**
     * 校验列头是否符合要求
     *
     * @param ei
     * @param listMap
     * @return
     * @throws JsonProcessingException
     */
    public String checkHeadList(List<String> headerList, Map<String, Object> listMap) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        List<Map<String, Integer>> checkHeaderList = Lists.newArrayList();
        if (headerList.size() != getRow(getHeaderNum()).getLastCellNum()) {
            listMap.put("ERRORCELLS", "列数量与模板不匹配");
            return mapper.writeValueAsString(listMap);
        }
        for (int i = 0; i < headerList.size(); i++) {
        	 
        	String cellValue = getRow(getHeaderNum()).getCell(i).getStringCellValue();
           
        	//正则表达式去除空格和换行符
        	if(cellValue!=null && !"".equals(cellValue)) {      
	             Pattern p = Pattern.compile("\\s*|\t|\r|\n");      
	             Matcher m = p.matcher(cellValue);      
                 cellValue = m.replaceAll(""); 
            }
            if (!headerList.get(i).equals(cellValue)) {
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("row", getHeaderNum() + 1);
                map.put("column ", i);
                checkHeaderList.add(map);
            }
        }
        if (checkHeaderList.size() > 0) {
            listMap.put("ERROR" + 0, checkHeaderList);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ERROR", listMap);
            json = mapper.writeValueAsString(map);
        }
        if(listMap.size() > 0 && json == null){
        	Map<String, Object> map = new HashMap<String, Object>();
        	map.put("ERROR", listMap);
        	json = mapper.writeValueAsString(map);
        	return json;
        }
        if(json != null){
            listMap.put("ERRORCELLS", "列数量与模板不匹配");
            return mapper.writeValueAsString(listMap);
        }
        return null;
    }

    /**
     * 导入测试
     *
     * @throws IOException
     * @throws InvalidFormatException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static void main(String[] args) throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
//		ExcelImportUtil ei = new ExcelImportUtil("C://Users//xiaozhang//Desktop//FS05数据导入导出模板.xlsx", 2);
        ExcelImportUtil2227 ei = new ExcelImportUtil2227("C://Users//xiaozhang//Desktop//file.xlsx", 2);
        List<InsertFieldTHM_FS05> list = ei.getDataList(InsertFieldTHM_FS05.class, "");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

    }
}
