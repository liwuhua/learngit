package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import org.springframework.dao.DataAccessException;

import java.text.ParseException;

/**销售订单完成统计表及需求统计表
 * Created by wangkai on 17/2/22.
 */

public interface IReportServiceFS14 {

     public Object getSttyp(String sttyp) throws JsonProcessingException,DataAccessException;

     public Object getSobkz(String sobkz) throws JsonProcessingException,DataAccessException;

     public Object getLgpbe(String lgpbe) throws JsonProcessingException,DataAccessException;
     
     public Object getData(QueryBean queryBean) throws JsonProcessingException, DataAccessException, ParseException;

}
