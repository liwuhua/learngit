package com.yjdj.view.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.entity.mybeans.MdmTmaterial;
import com.yjdj.view.core.entity.mybeans.MdmTsalesOrg;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.mapper.GenericMapper;
import com.yjdj.view.core.mapper.IReportMapperFS00;
import com.yjdj.view.core.service.IReportServiceFS00;
import com.yjdj.view.core.util.NumProcessor;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.util.*;

/**
 * Created by wangkai on 16/10/14.
 */  
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS00 implements IReportServiceFS00 {
    private final Logger log = Logger.getLogger(ReportServiceImpFS00.class);

    @Autowired
    private IReportMapperFS00 iReportMapperFS00;
    @Autowired
    private GenericMapper genericMapper;

    @Override
    public String getReportJson(QueryBean queryBean) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
//        Map<String,List<String>> map = mapper.readValue(json,Map.class);
//        List<String> matnrValue = null;
//        List<String> matnrInterval = null;
//        List<String> vkorgValue = null;
//        List<String> vkburValue = null;
//        if(map.keySet().contains("matnrValue")){
//            matnrValue = map.get("matnrValue");
//        }
//        if(map.keySet().contains("matnrInterval")){
//            matnrInterval = map.get("matnrInterval");
//        }
//        if(map.keySet().contains("vkorgValue")){
//            vkorgValue = map.get("vkorgValue");
//        }
//        if(map.keySet().contains("vkburValue")){
//            vkburValue = map.get("vkburValue");
//        }
        //第一步
//        List<Map<String,Object>> lips = getMapListByThmlips01(matnrValue,matnrInterval,vkorgValue,vkburValue);
        List<Map<String,Object>> lips = getMapListByThmlips01(queryBean.getMatnrValue(),queryBean.getMatnrIntervalMap(),queryBean.getVkorgValue(),queryBean.getVkburValue());
        //获取第一步的交货单号  对应字段 vbeln
        List<String> vbelnValue = getList(lips,"vbeln");
        //第二步
        List<Map<String,Object>> vbrp01 = getMapListByThmvbrp01(vbelnValue);
        //获取第二步中的 vbeln
        List<String> vbeln2Value = getList(vbrp01,"vbeln");
        //第三步
        List<Map<String,Object>> vbrp02 = getMapListByThmvbrp02(vbelnValue,vbeln2Value);
        //获取第三步中的 vgbel
        List<String> vgbelValue = getList(vbrp02,"vgbel");

        //第四步 将第一步与第三步的数据进行比对
        List<Map<String,Object>> mapList = compareList(lips,vbelnValue,vgbelValue);
        log.debug("将第一步与第三步的数据进行比对 mapList size : "+mapList.size());

        //分类统计销售组织
        Map<String,Object> resultMap = classifiStatisticsByVkorg(mapList);

        //科学技术法转化为字符串
//        resultMap = NumProcessor.transformation(resultMap);

        String resultJson = mapper.writeValueAsString(resultMap);

        return resultJson;
    }

    @Override
    public List<Map<String, Object>> getMapListByThmlips01(List<String> matnrValue, List<Map<String,String>> matnrInterval, List<String> vkorgValue, List<String> vkburValue) {
        return iReportMapperFS00.getMapListByThmlips01Test(matnrValue,matnrInterval,vkorgValue,vkburValue);
    }

    @Override
    public List<Map<String, Object>> getMapListByThmvbrp01(List<String> vbelnValue) {
        return iReportMapperFS00.getMapListByThmvbrp01(vbelnValue);
    }

    @Override
    public List<Map<String, Object>> getMapListByThmvbrp02(List<String> vbelnValue, List<String> vbeln2Value) {
        return iReportMapperFS00.getMapListByThmvbrp02(vbelnValue,vbeln2Value);
    }

    private List<String> getList(List<Map<String,Object>> mapList,String str){
        str = str.toUpperCase();
        List<String> list = new ArrayList<String>();
        for(Map<String,Object> map : mapList){
            String s = (String) map.get(str);
            if(StringUtils.isNotBlank(s)){
                list.add(s);
            }
        }
        return list;
    }

    private List<Map<String,Object>> compareList(List<Map<String,Object>> lips,List<String> vbelnValue,List<String> vgbelValue){
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();

        Set<String> vbelnSet = new HashSet<String>(vbelnValue);
        Set<String> vgbelSet = new HashSet<String>(vgbelValue);

        vbelnSet.removeAll(vgbelSet);

        for(Map<String,Object> map : lips){
            String s = (String) map.get("VBELN");
            if(vbelnSet.contains(s)){
                mapList.add(map);
            }
        }

        return mapList;
    }

    /**
     * 分类统计销售组织
     * @param mapList
     * @return
     */
    private Map<String,Object> classifiStatisticsByVkorg(List<Map<String,Object>> mapList){
        String lfimg = "LFIMG";     //数量
        String amount = "AMOUNT";   //金额
        String matnr = "MATNR";     //物料号
        String vkorg = "VKORG";     //销售组织

        Map<String,Object> resultMap = new HashMap<String,Object>();

        for(Map<String,Object> map : mapList){
            String vkorgNum = (String) map.get(vkorg);
            String matnrNum = (String) map.get(matnr);
            Double lfimgSum = (Double) map.get(lfimg);
            Double amountSum = (Double) map.get(amount);
            boolean augru = false;
            if(map.get("AUGRU").equals("Z02")){
                augru = true;
            }

            updateMap(resultMap,vkorgNum,matnrNum,lfimgSum,amountSum,augru);
        }

        return resultMap;
    }

    /**
     * 如果存在则更新,不存在则创建
     * @param map
     * @return
     */
    private void updateMap(Map<String,Object> map,String vkorgNum,String matnrNum,Double lfimgSum,Double amountSum,boolean augru){
        //总数量
        if(null != map.get("lfimgAll") && null != lfimgSum){
            map.put("lfimgAll",(Double) map.get("lfimgAll")+lfimgSum);
        }else if(null == map.get("lfimgAll") && null != lfimgSum){
            map.put("lfimgAll",lfimgSum);
        }
        //总金额
        if(null != map.get("amountAll") && null != amountSum){
            map.put("amountAll", (Double) map.get("amountAll")+amountSum);
        }else if(null == map.get("amountAll") && null != amountSum){
            map.put("amountAll",amountSum);
        }

        //组织
        if(!map.keySet().contains("vkorgMapList")){
            map.put("vkorgMapList",new ArrayList<Map<String,Object>>());
        }
        List<Map<String,Object>> vkorgMapList = (List<Map<String, Object>>) map.get("vkorgMapList");
        MdmTsalesOrg tsalesOrg = genericMapper.getTsalesOrg(vkorgNum);
        //找到了更新,找不到创建
        boolean vkorgBlog = false;
        for(Map<String,Object> vkorgMap : vkorgMapList){
            if (vkorgMap.get("vkorgNum").equals(vkorgNum)){
                vkorgBlog = true;
                vkorgMap.put("vkorgName",tsalesOrg.getTxtlg());
                vkorgMap.put("lfimg",(Double) vkorgMap.get("lfimg")+lfimgSum);
                vkorgMap.put("amount",(Double) vkorgMap.get("amount")+amountSum);
                //合同
                updateAugruMap(vkorgMap,vkorgNum,matnrNum,lfimgSum,amountSum,augru);
            }
        }
        if(!vkorgBlog){
            Map<String,Object> vkorgMap = new HashMap<String,Object>();
            vkorgMap.put("vkorgNum",vkorgNum);
            vkorgMap.put("vkorgName",tsalesOrg.getTxtlg());
            vkorgMap.put("lfimg",lfimgSum);
            vkorgMap.put("amount",amountSum);
            //合同
            updateAugruMap(vkorgMap,vkorgNum,matnrNum,lfimgSum,amountSum,augru);

            vkorgMapList.add(vkorgMap);
        }
    }

    /**
     * 更新合同
     * @param map
     * @param augru
     */
    private void updateAugruMap(Map<String,Object> map,String vkorgNum,String matnrNum,Double lfimgSum,Double amountSum,boolean augru){
        String augruKey = null;
        if(augru){
            augruKey = "augruTrue";
        }else {
            augruKey = "augruFalse";
        }

        //存在更新,不存在创建
        Map<String,Object> augruMap = null;
        if(map.keySet().contains(augruKey)){
            augruMap = (Map<String, Object>) map.get(augruKey);
            augruMap.put("lfimg",(Double) augruMap.get("lfimg")+lfimgSum);
            augruMap.put("amount",(Double) augruMap.get("amount")+amountSum);

            updateMatnrMap(augruMap,vkorgNum,matnrNum,lfimgSum,amountSum,augru);
        }else{
            augruMap = new HashMap<String,Object>();
            augruMap.put("vkorgNum",vkorgNum);
            augruMap.put("lfimg",lfimgSum);
            augruMap.put("amount",amountSum);

            updateMatnrMap(augruMap,vkorgNum,matnrNum,lfimgSum,amountSum,augru);

            map.put(augruKey,augruMap);
        }
    }

    //更新或创建型号
    private void updateMatnrMap(Map<String,Object> map,String vkorgNum,String matnrNum,Double lfimgSum,Double amountSum,boolean augru){
        //型号
        if(!map.keySet().contains("matnrMapList")){
            map.put("matnrMapList",new ArrayList<Map<String,Object>>());
        }
        List<Map<String,Object>> matnrMapList = (List<Map<String, Object>>) map.get("matnrMapList");
        MdmTmaterial tmaterial = genericMapper.getTmaterial(matnrNum);

        //找到了更新,找不到创建
        boolean matnrBlog = false;
        for(Map<String,Object> matnrMap : matnrMapList){
            if(matnrMap.get("matnrNum").equals(matnrNum)){
                matnrBlog = true;
                matnrMap.put("matnrName",tmaterial.getTxtmd());
                matnrMap.put("lfimg",(Double) matnrMap.get("lfimg")+lfimgSum);
                matnrMap.put("amount",(Double) matnrMap.get("amount")+amountSum);
            }
        }
        if(!matnrBlog){
            Map<String,Object> matnrMap = new HashMap<String,Object>();
            matnrMap = new HashMap<String,Object>();
            matnrMap.put("matnrNum",matnrNum);
            matnrMap.put("matnrName",tmaterial.getTxtmd());
            matnrMap.put("lfimg",lfimgSum);
            matnrMap.put("amount",amountSum);

            matnrMapList.add(matnrMap);
        }
    }
}
