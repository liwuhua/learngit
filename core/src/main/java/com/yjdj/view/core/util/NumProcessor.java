package com.yjdj.view.core.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by wangkai on 16/10/26.
 */
public class NumProcessor {
    public static String getStrByDec(String str){
        BigDecimal bd = new BigDecimal(str);
        return bd.toString();
    }

    /**
     * 科学计数法转字符串
     * @param mapList
     * @return
     */
    public static List<Map<String,Object>> transformation(List<Map<String,Object>> mapList){
        for(Map<String,Object> map : mapList){
            map = transformation(map);
        }
        return mapList;
    }

    /**
     * 科学计数法转字符串
     * @param map
     * @return
     */
    public static Map<String,Object> transformation(Map<String,Object> map){
        for(Map.Entry<String,Object> entry : map.entrySet()){
            if(null != entry.getValue()){
                String value = null;
                try{
                    value = getStrByDec(entry.getValue().toString());
                }catch (Exception e){
                    value = entry.getValue().toString();
                }
                map.replace(entry.getKey(),entry.getValue(),value);
            }
        }
        return map;
    }
}
