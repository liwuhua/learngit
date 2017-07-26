package com.yjdj.view.core.service;

import com.yjdj.view.core.entity.mybeans.Fs20TreeNode;
import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;

/**营销订单交付追踪表-甘特图（全部E件）
 * Created by liwuhua on 16/11/21.
 */

public interface IReportServiceFS20 {

     @Deprecated
     public String getListFs20(String matnr,String dwerk,String kdauf, String kdpos,String rsnum) throws JsonProcessingException,DataAccessException;

     public String getFs20(String kdauf, String kdpos, String baugr, String aufnr) throws JsonProcessingException, InterruptedException;
}
