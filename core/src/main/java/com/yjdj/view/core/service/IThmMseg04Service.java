package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.dao.DataAccessException;

/**
 * Created by wangkai on 2016/11/4.
 */
public interface IThmMseg04Service {
    /**
     * 获取采购订单
     * @return
     */
    public String getEbeln(String ebeln, int start, int limit) throws JsonProcessingException,DataAccessException;;
}
