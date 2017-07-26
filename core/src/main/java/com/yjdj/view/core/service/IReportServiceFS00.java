package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 报表Service
 * Created by wangkai on 16/10/14.
 */
public interface IReportServiceFS00 {

    /**
     * 解析json查询THM_LIPS_01表返回报表json
     * @param queryBean
     * @return
     */
//    public String getReportJson(String json) throws IOException;
    public String getReportJson(QueryBean queryBean) throws IOException;

    /**
     * 第一步:根据条件查询THM_LIPS_01表
     * @param matnrValue
     * @param matnrInterval
     * @param vkorgValue
     * @param vkburValue
     * @return
     */
    public List<Map<String,Object>> getMapListByThmlips01(List<String> matnrValue, List<Map<String,String>> matnrInterval, List<String> vkorgValue, List<String> vkburValue);

    /**
     * 第二步:Select * from THM_VBRP_01 where VGBEL in (第一步中的交货单号)
     * @param vbelnValue 第一步中获取的交货单号
     * @return
     */
    public List<Map<String,Object>> getMapListByThmvbrp01(List<String> vbelnValue);

    /**
     * 第三步:Select * from THM_VBRP_02 where VGBEL in (第一步中的交货单号)  and VBELN not in (第二步数据集中的VBELN)
     * @param vbelnValue    第一步中获取的交货单号
     * @param vbeln2Value   第二步中获取的交货单号
     * @return
     */
    public List<Map<String,Object>> getMapListByThmvbrp02(List<String> vbelnValue,List<String> vbeln2Value);

}
