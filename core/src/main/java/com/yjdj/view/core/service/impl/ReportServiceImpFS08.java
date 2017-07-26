package com.yjdj.view.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.mapper.IReportMapperFS08;
import com.yjdj.view.core.service.IReportServiceFS08;
import com.yjdj.view.core.util.DateUtils;
import com.yjdj.view.core.util.NumProcessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangkai on 16/10/26.
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS08 implements IReportServiceFS08 {

    @Autowired
    private IReportMapperFS08 iReportMapperFS08;

    @Override
    public String getListFs08(QueryBean queryBean) throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
//        Map<String,Object> map = mapper.readValue(json,Map.class);

        String resultJson = null;
        if(StringUtils.isNotBlank(queryBean.getStartTime()) && StringUtils.isNotBlank(queryBean.getEndTime())){
            String dateStart = queryBean.getStartTime();
            String dateEnd = queryBean.getEndTime();
            String dateBegin = DateUtils.getDateBegin(dateStart);
            String lastDateStart = DateUtils.getLastDate(dateStart);
            String lastDateEnd = DateUtils.getLastDate(dateEnd);
            String lastDateBegin = DateUtils.getLastYearStart(dateStart);


            List<Map<String,Object>> mapList = iReportMapperFS08.getListFs08(dateStart,dateEnd,dateBegin,lastDateStart,lastDateEnd,lastDateBegin);
            //转换
            mapList = NumProcessor.transformation(mapList);
            resultJson = mapper.writeValueAsString(mapList);
        }
        return resultJson;
    }
}
