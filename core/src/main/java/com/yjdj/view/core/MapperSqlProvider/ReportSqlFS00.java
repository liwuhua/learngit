package com.yjdj.view.core.MapperSqlProvider;

import com.yjdj.view.core.entity.mybeans.TableName;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SqlBuilder;

import java.util.List;
import java.util.Map;

/**
 * Created by wangkai on 16/10/17.
 */
public class ReportSqlFS00 extends SqlBuilder{
    public String getMapListByThmlips01(Map<String,Object> param){
        /**
         * SELECT * FROM THM_LIPS_01
         WHERE 1=1 AND (MATNR in ("Z500000007230","Z500000069198")
         OR MATNR BETWEEN "Z500000007230" AND "Z500000069198" OR MATNR BETWEEN "Z500000035715" AND "Z500000083055")
         AND VKORG in ("5005") AND VKBUR in ("5002","5005","5011") ORDER BY MATNR
         */
        BEGIN();
        SELECT("*");
        FROM(TableName.THM_LIPS_01);

        StringBuilder sbSqlWhere = new StringBuilder();
        sbSqlWhere.append("1=1");
        boolean b = false;
        if(CollectionUtils.isNotEmpty((List<String>)param.get("matnrValue"))){
            b = true;
            sbSqlWhere.append(" AND (MATNR in ("+getStr((List<String>)param.get("matnrValue"))+")");
        }
        if(CollectionUtils.isNotEmpty((List<String>)param.get("matnrInterval"))){
            sbSqlWhere.append(getStrInterval((List<String>)param.get("matnrInterval"),b));
        }
        sbSqlWhere.append(")");

        if(CollectionUtils.isNotEmpty((List<String>)param.get("vkorgValue"))){
            sbSqlWhere.append(" AND ");
            sbSqlWhere.append("VKORG in ("+getStr((List<String>)param.get("vkorgValue"))+")");
        }
        if(CollectionUtils.isNotEmpty((List<String>)param.get("vkburValue"))){
            sbSqlWhere.append(" AND ");
            sbSqlWhere.append("VKBUR in ("+getStr((List<String>)param.get("vkburValue"))+")");
        }
        WHERE(sbSqlWhere.toString());
        return SQL();
    }

    public String getMapListByThmvbrp01(Map<String,Object> param){
        /**
         *  Select * from THM_VBRP_01 where VGBEL in ("19033")
         */
        BEGIN();
        SELECT("*");
        FROM(TableName.THM_VBRP_01);
        StringBuilder sbSqlWhere = new StringBuilder();
        sbSqlWhere.append("1=1");
        if(StringUtils.isNotBlank(getStr((List<String>) param.get("vbelnValue")))){
            sbSqlWhere.append(" AND ");
            sbSqlWhere.append("VGBEL in ("+getStr((List<String>)param.get("vbelnValue"))+")");
        }
        WHERE(sbSqlWhere.toString());
        return SQL();
    }

    public String getMapListByThmvbrp02(Map<String,Object> param){
        /**
         *  Select * from THM_VBRP_02 where VGBEL in ("8000000238") AND VBELN NOT in ("9000000001")
         */
        BEGIN();
        SELECT("*");
        FROM(TableName.THM_VBRP_02);
        StringBuilder sbSqlWhere = new StringBuilder();
        sbSqlWhere.append("1=1");
        if(StringUtils.isNotBlank(getStr((List<String>) param.get("vbelnValue")))){
            sbSqlWhere.append(" AND ");
            sbSqlWhere.append("VGBEL in ("+getStr((List<String>)param.get("vbelnValue"))+")");
        }
        if(StringUtils.isNotBlank(getStr((List<String>) param.get("vbeln2Value")))){
            sbSqlWhere.append(" AND ");
            sbSqlWhere.append("VBELN NOT in ("+getStr((List<String>)param.get("vbeln2Value"))+")");
        }
        WHERE(sbSqlWhere.toString());
        return SQL();
    }

    /**
     * 获取多值
     * @param list
     * @return
     */
    private String getStr(List<String> list){
        StringBuilder sbStr = new StringBuilder();
        if(CollectionUtils.isNotEmpty(list)){
            for(String str : list){
                sbStr.append("\"");
                sbStr.append(str);
                sbStr.append("\",");
            }
            sbStr.deleteCharAt(sbStr.length()-1);
        }
        return sbStr.toString();
    }

    /**
     * 获取MATNR多区间
     * @param list
     * @return
     */
    private String getStrInterval(List<String> list,boolean b){
        StringBuilder sbStr = new StringBuilder();
        if(CollectionUtils.isNotEmpty(list)){
            for(String str : list){
                if(b){
                    sbStr.append(" OR");
                }else{
                    sbStr.append(" AND(");
                }
                String[] ss = str.split(":");
                sbStr.append(" MATNR BETWEEN \"");
                sbStr.append(ss[0]);
                sbStr.append("\" AND \"");
                sbStr.append(ss[1]);
                sbStr.append("\"");

                b = true;
            }
        }
        return sbStr.toString();
    }
}
