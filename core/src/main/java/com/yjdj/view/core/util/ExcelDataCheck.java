package com.yjdj.view.core.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Lists;

public class ExcelDataCheck<E> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ExcelImportUtil.class);

    /**
     * FS05 数据校验
     *
     * @param row
     * @param column
     * @param val
     * @return
     */
    public List checkFS05Data(Row row, int column, Object val) {
        List<E> dataList = Lists.newArrayList();
        Map<String, Integer> map = new HashMap<String, Integer>();
        if (row != null) {
            if (row.getRowNum() == 15 || row.getRowNum() == 16 || row.getRowNum() == 78
                    || row.getRowNum() == 79) {
                if (column >= 3 && column <= 10) {
                    if (!FileUtils.isNumeric(val)) {
                        map.put("row", row.getRowNum() + 1);
                        map.put("column", column);
                    }
                }
            } else if (row.getRowNum() != 4 && row.getRowNum() != 9
                    && row.getRowNum() != 14 && row.getRowNum() != 15 && row.getRowNum() != 16
                    && row.getRowNum() != 17 && row.getRowNum() != 22 && row.getRowNum() != 27
                    && row.getRowNum() != 32 && row.getRowNum() != 37 && row.getRowNum() != 42
                    && row.getRowNum() != 47 && row.getRowNum() != 52 && row.getRowNum() != 57
                    && row.getRowNum() != 62 && row.getRowNum() != 67 && row.getRowNum() != 72
                    && row.getRowNum() != 77 && row.getRowNum() != 78 && row.getRowNum() != 79) {
                if (column == 9 || column == 10) {
                    if (!FileUtils.isNumeric(val)) {
                        logger.info("is not the number");
                    }
                }
            }
        }
        if (map.size() != 0) {
            dataList.add((E) map);
        }
        return dataList;
    }

    /**
     * FS15数据校验
     *
     * @param row
     * @param column
     * @param val
     * @return
     */
    public List checkFS15Data(Row row, int column, Object val) {
        List<E> dataList = Lists.newArrayList();
        Map<String, Integer> map = new HashMap<String, Integer>();
        if (row != null) {
            if (row.getRowNum() >= 4 && row.getRowNum() <= 54) {
                if (column == 3 || column == 4 || column == 7 || column == 8) {
                    if (!FileUtils.isNumeric(val)) {
                        map.put("row", row.getRowNum() + 1);
                        map.put("column", column);
                    }
                }
            }
        }
        if (map.size() != 0) {
            dataList.add((E) map);
        }
        return dataList;
    }

    /**
     * FS16数据校验
     *
     * @param row
     * @param column
     * @param val
     * @return
     */
    public List checkFS16Data(Row row, int column, Object val) {
        List<E> dataList = Lists.newArrayList();
        Map<String, Integer> map = new HashMap<String, Integer>();
        if (row != null) {
            if (row.getRowNum() >= 4 && row.getRowNum() <= 48) {
                if (column == 3 || column == 4 || column == 5) {
                    if (!FileUtils.isNumeric(val)) {
                        map.put("row", row.getRowNum() + 1);
                        map.put("column", column);
                    }
                }
            } else {
                logger.info(val + "is not the number!");
            }
        }
        if (map.size() != 0) {
            dataList.add((E) map);
        }
        return dataList;
    }

    /**
     * FS17 数据校验
     *
     * @param row
     * @param column
     * @param val
     * @return
     */
    public List checkFS17Data(Row row, int column, Object val) {
        List<E> dataList = Lists.newArrayList();
        Map<String, Integer> map = new HashMap<String, Integer>();
        if (row != null) {
            if(4 == row.getRowNum() && (4 == column || 3 == column)){
                return dataList;
            }
            if (row.getRowNum() >= 4 && row.getRowNum() <= 32) {
                if ((column == 3 || column == 4 || column == 7 || column == 8)) {
                    if (!FileUtils.isNumeric(val)) {
                        map.put("row", row.getRowNum() + 1);
                        map.put("column", column);
                    }
                } 
            } else {
                logger.info(val + "is not the number!");
            }
        }
        if (map.size() != 0) {
            dataList.add((E) map);
        }
        return dataList;
    }
    
    
    /**
     * FS17 数据校验
     *
     * @param row
     * @param column
     * @param val
     * @return
     */
    public List checkFS24Data(Row row, int column, Object val) {
        List<E> dataList = Lists.newArrayList();
        Map<String, Integer> map = new HashMap<String, Integer>();
        if (row != null) {
            if (row.getRowNum() >= 3) {
                if (column == 5 ||column == 6) {
                	if(val!=null){
	                    if (!FileUtils.isNumeric(val)) {
	                        map.put("row", row.getRowNum() + 1);
	                        map.put("column", column);
	                    }
                	}
                } 
            } else {
                logger.info(val + "is not the number!");
            }
        }
        if (map.size() != 0) {
            dataList.add((E) map);
        }
        return dataList;
    }

}
