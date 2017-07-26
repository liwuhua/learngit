package com.yjdj.view.core.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.QueryBean;

public interface IReportServiceFS54 {
	
	/**
	 * create on 2017/3/30
	 * @author yangzhijie
	 * 
	 * @param QueryBean
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws DataAccessException
	 */
    public Object getReportJson(QueryBean queryBean) throws JsonProcessingException, IOException ,DataAccessException;
    /**
     * 
     * @param filePath
     * @param queryPojo
     * @return 
     * @throws JsonProcessingException
     * @throws IOException
     * @throws DataAccessException
     * @throws InvalidFormatException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public Object importData(String filePath) throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException;
    
    public List<Map<String,String>> selectFwz(String fwzLike, String fwzIdLike) throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException;
    
    public List<Map<String,String>> selectFwd(String fwdLike, String fwdIdLike) throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException;
    
    public List<Map<String,String>> selectCx(String cxLike, String cxIdLike) throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException;
    
    public List<String> getCpxh(String cpxhLike) throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException;
    
    public List<String> getAname(String anameLike) throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException;
    
    public List<String> selectMaterialInList(List<String> materialList) throws JsonProcessingException, IOException ,DataAccessException, InvalidFormatException, InstantiationException, IllegalAccessException;
}
