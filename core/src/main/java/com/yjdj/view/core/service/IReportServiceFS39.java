package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import org.springframework.dao.DataAccessException;

import java.text.ParseException;
import java.util.List;
import java.util.Map;


public interface IReportServiceFS39 {

     public Object getGroes(String groes) throws JsonProcessingException,DataAccessException;

     public Object getLtxa1(String ltxa1) throws JsonProcessingException,DataAccessException;


     public Object getKdauf(String kdauf) throws JsonProcessingException,DataAccessException;

     public Object getKdpos(String kdpos) throws JsonProcessingException,DataAccessException;

     public Object getArbpl(String arbpl,String ktext) throws JsonProcessingException,DataAccessException;

     public Object getXqdw(String xqdw) throws JsonProcessingException,DataAccessException;

     public Object getAuart(String auart) throws JsonProcessingException,DataAccessException;

     public String getDataFS39(QueryBean queryBean) throws JsonProcessingException, DataAccessException, ParseException;

}
