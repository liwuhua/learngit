package com.yjdj.view.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.mapper.IReportMapperFS27;
import com.yjdj.view.core.service.IReportServiceFS27;
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
public class ReportServiceImpFS27 implements IReportServiceFS27 {
    @Autowired
    private IReportMapperFS27 iReportMapperFS27;

    @Override
    public String getListFs27(String aufnr,String matnr) throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String,Object>> listMap = iReportMapperFS27.getListMap(aufnr,matnr);
        return mapper.writeValueAsString(listMap);
    }
}
