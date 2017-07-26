package com.yjdj.view.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.mapper.IReportMapperFS25;
import com.yjdj.view.core.service.IReportServiceFS25;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by chengxuan on 2016/11/8.
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS25 implements IReportServiceFS25 {
    @Autowired
    private IReportMapperFS25 iReportMapperFS25;

    @Override
    public String getListFs25(String aufnr) throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String,Object>> listMap = iReportMapperFS25.getListMap(aufnr);
        return mapper.writeValueAsString(listMap);
    }
}
