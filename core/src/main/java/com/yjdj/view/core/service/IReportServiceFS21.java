package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import org.springframework.dao.DataAccessException;

import java.io.IOException;

/**营销订单交付追踪表-甘特图（全部E件）
 * Created by chengxuan on 16/11/21.
 */

public interface IReportServiceFS21 {
	
     public Object getDataFs21(QueryBean queryBean) throws IOException, DataAccessException;

}
