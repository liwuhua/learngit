package com.yjdj.view.core.mapper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

@MyBatisRepository
public interface IReportMapperKPI20 extends BaseMapper{
	
	
//	public List<LinkedHashMap> getLedgercheck(HashMap map) throws DataAccessException;
	
	public Map getLedgercheck(HashMap map) throws DataAccessException;

	
	public List<Map> selectProCompCompany(@Param("yearMonth") String yearMonth) throws DataAccessException;
	
	
	
}
