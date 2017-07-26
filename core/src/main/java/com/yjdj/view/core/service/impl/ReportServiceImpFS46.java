package com.yjdj.view.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yjdj.view.core.entity.mybeans.QueryPojo;
import com.yjdj.view.core.entity.mybeans.THM_FS46;
import com.yjdj.view.core.mapper.IReportMapperFS46;
import com.yjdj.view.core.service.IReportServiceFS46;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.ExcelImportUtil;
import com.yjdj.view.core.util.StringUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.util.*;

/**
 * Created by zhuxuan on 2016/12/28.
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS46 implements IReportServiceFS46 {
    @Autowired
    private IReportMapperFS46 iReportMapperFS46;
    private boolean isExport = false; //如果是true则为导数据，false则为查数据
    private List<String> headerList = null;
    private String secondTitle = null;
    private ExcelExportUtil ee = null;
    private static final String NAME = "按车型检索配属情况";
    private ObjectMapper mapper = new ObjectMapper();
    private static final String xiaoji = "小计";
    private static final String zongji = "总计";
    Map<String, Object> listMap = null;
    private static final String cx = "车型";
    private static final String psds = "配属段所";
    private static final String yycsl = "运用车数量";
    private static final String mltczcsl = "每列/台车装车数量";
    private static final String djzsl = "电机总数量";
    int count = 1;

    /**
     * 获取报表或导出
     * @param queryPojo
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     * @throws DataAccessException
     */
    public  Object getReportJson(QueryPojo queryPojo) throws JsonProcessingException, IOException,DataAccessException{
        int flag = 0;//查询flag为0，导出flag为1
        isExport = queryPojo.isExport();
        if (isExport) {
            flag = 1;
        }
        List<THM_FS46> list = iReportMapperFS46.selectUserData(queryPojo.getTrainmodel(),
                                                                  queryPojo.getStartitem(),
                                                                  queryPojo.getPageitem(),
                                                                  flag);
        //进行合计计算
        List<THM_FS46> resultList = getSumList(list);
        if (!isExport) {
            String resultJson = mapper.writeValueAsString(resultList);
            return resultJson;
        } else {
            headerList = Lists.newArrayList();
            init(headerList);
            secondTitle = null;
            ee = new ExcelExportUtil(NAME,secondTitle,headerList);
            Row row = null;
            for(int i = 0; i < resultList.size(); i++) {
                row = ee.addRow();
                ee.addCell(row, 0, resultList.get(i).getTrain_type());
                ee.addCell(row, 1, resultList.get(i).getPlace_subjection());
                ee.addCell(row, 2, resultList.get(i).getTrainamount());
                ee.addCell(row, 3, resultList.get(i).getMotoramount());
                ee.addCell(row, 4, resultList.get(i).getMotoramountsum());
            }
            return ee;
        }
    };

    /**
     * 初始化headerList
     * @param headerList
     */
    private void init(List<String> headerList){
        headerList.add(cx);
        headerList.add(psds);
        headerList.add(yycsl);
        headerList.add(mltczcsl);
        headerList.add(djzsl);
    }

    /**
     * 计算合计值，并将合计行放到对应的合计值下
     * @param list
     * @return
     */
    private List<THM_FS46> getSumList(List<THM_FS46> list){
        THM_FS46 tmpBean = list.get(0);
        List<THM_FS46> resultList = new LinkedList<>();
        List<THM_FS46> sumList = new LinkedList<>();
        sumList.add(tmpBean);
        resultList.add(tmpBean);
        for(int i = 1; i < list.size(); i++){
            if(list.get(i).getTrain_type().equals(tmpBean.getTrain_type())){
                sumList.add(list.get(i));
                resultList.add(list.get(i));
            }else{
                resultList.add(sumList(sumList,xiaoji));
                tmpBean = list.get(i);
                resultList.add(tmpBean);
                sumList = Lists.newLinkedList();
                sumList.add(tmpBean);
            };
        }
        resultList.add(sumList(sumList,xiaoji));//最后一次sumList的合计值
        resultList.add(sumList(list,zongji));//所有数据的合计值
        return resultList;
    }

    /**
     * 将传入的list中所有的运用车数量、电机总数量相加，装入Bean中成一行数据，并返回Bean
     * @param sumList
     * @return
     */
    private THM_FS46 sumList(List<THM_FS46> sumList,String item){
        THM_FS46 sumBean = new THM_FS46();
        Integer trainamountSum = 0;//运用车数量
        Integer motoramountsumSum = 0;//电机总数量
        for(THM_FS46 tmp:sumList){
            trainamountSum += Integer.parseInt(tmp.getTrainamount());
            motoramountsumSum += Integer.parseInt(tmp.getMotoramountsum());
        }
        sumBean.setTrain_type(item);
        sumBean.setPlace_subjection("");
        sumBean.setTrainamount(trainamountSum.toString());
        sumBean.setMotoramount("/");
        sumBean.setMotoramountsum(motoramountsumSum.toString());
        return sumBean;
    }

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
/*    public Object importData(String filePath)throws JsonProcessingException, IOException ,DataAccessException,
            InvalidFormatException, InstantiationException, IllegalAccessException{
        //定义变量
        ExcelImportUtil ei = new ExcelImportUtil(filePath, 1);
        headerList = Lists.newArrayList();
        String json = null;
        init(headerList);
        //检查列名
        listMap = new HashMap<String, Object>();
        json = ei.checkHeadList(headerList,listMap);
        if(null!=json){
            return json;
        }
        //如果验证数据没有问题，则返回所有的数据，标志位为1，若是数据有问题，则返回不正确数据，标志位为0
        List<InsertFieldTHM_FS46> list = ei.getDataList(InsertFieldTHM_FS46.class, "FS46");
        if ("ERROR".equals(list.get(list.size() - 1))) {
            for (int i = 0; i < list.size() - 1; i++) {
                listMap.put("ERROR" + i, list.get(i));
            }
            //如果返回的结果存在不合理的数据，那么将数据结果以json格式返回
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ERROR", listMap);
            if (listMap.size() > 0) {
                json = mapper.writeValueAsString(map);
                return json;
            }
        }

        List<InsertFieldTHM_FS46> tmpDataList = new ArrayList<InsertFieldTHM_FS46>();
        InsertFieldTHM_FS46 tmp = new InsertFieldTHM_FS46();

        //导入数据处理
        for (int i = 0; i < list.size(); i++) {
            if (!StringUtil.isEmpty(list.get(i).getTrainmodel()) && !list.get(i).getTrainmodel().trim().equals("合计")) {
                if(list.get(i).getTrainmodel() != null && !list.get(i).getTrainmodel().trim().equals("")) {
                    tmp.setTrainmodel(list.get(i).getTrainmodel().trim());
                }else{
                    listMap.put("ERROR"+cx,cx+",第"+(i+3)+"行,数据格式有错误");
                    json = mapper.writeValueAsString(listMap);
                }
                if(list.get(i).getOrgnization() != null && !list.get(i).getOrgnization().trim().isEmpty()) {
                    tmp.setOrgnization(list.get(i).getOrgnization().trim());
                }else{
                    listMap.put("ERROR"+psds, psds+",第"+(i+3)+"行,数据格式有错误");
                    json = mapper.writeValueAsString(listMap);
                }
                if(list.get(i).getTrainamount() != null) {
                    tmp.setTrainamount(list.get(i).getTrainamount());
                }else{
                    listMap.put("ERROR"+yycsl, yycsl+",第"+(i+3)+"行,数据格式有错误");
                    json = mapper.writeValueAsString(listMap);
                }
                if(list.get(i).getMotoramount() != null) {
                    tmp.setMotoramount(list.get(i).getMotoramount());
                }else{
                    listMap.put("ERROR"+mltczcsl, mltczcsl+",第"+(i+3)+"行,数据格式有错误");
                    json = mapper.writeValueAsString(listMap);
                }
                tmpDataList.add(tmp);
                tmp = new InsertFieldTHM_FS46();
            }
        }
        if(!listMap.isEmpty()){
            Map<String,Object> map = Maps.newHashMap();
            map.put("ERROR",listMap);
            return mapper.writeValueAsString(map);
        }
        for(InsertFieldTHM_FS46 tmpBean:tmpDataList){
            iReportMapperFS46.saveOrUpdateData(tmpBean);
        }
        listMap.put("SUCCESSES", "导入成功!");
        json = mapper.writeValueAsString(listMap);
        return json;
    }*/

    /**
     * 获取查询条件-车型
     * @param trainmodel
     * @return
     * @throws JsonProcessingException
     */
    public List<String> getAllTrianModel(String trainmodel) throws JsonProcessingException{
        List<THM_FS46> trainModelList = iReportMapperFS46.selectAllTrainModel(trainmodel);
        List<String> jsonList = new ArrayList<>();
        for(THM_FS46 tmp : trainModelList){
            jsonList.add(tmp.getTrain_type());
        }
        return jsonList;
    }
}
