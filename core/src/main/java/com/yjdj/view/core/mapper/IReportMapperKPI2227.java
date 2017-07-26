package com.yjdj.view.core.mapper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.InsertFieldTHM_KPI2227;

/**
 * Created by liwuhua on 16/11/7.
 */
@MyBatisRepository
@SuppressWarnings("all")
public interface IReportMapperKPI2227 extends BaseMapper{

   
	
	
	//厂内一年中每个月的损失
	public Map  getInplantAccount(HashMap  map)throws DataAccessException;

	//厂外一年中每个月的损失
	public Map  getOutplantAccount(HashMap map)throws DataAccessException;
	
	//厂内外一年中每个月的损失
	public List<Map>  getPlantAccount(HashMap map)throws DataAccessException;
	
	//厂内外一年中质量损失总金额
	public Map  getTotal(HashMap map)throws DataAccessException;
	

//	//一年中每个月的收入
//	public List<Map>  getMonthIncome(HashMap map)throws DataAccessException;
	
	//计算损失率
//	public List<Map>  getLossMate(HashMap map)throws DataAccessException;
	public Map  getLossMate(HashMap map)throws DataAccessException;
	
	//厂内指定月所占明细数量
	public List<Map>  getInMonthDetail(HashMap map)throws DataAccessException;

	//厂外指定月所占明细数量
	public List<Map>  getOutMonthDetail(HashMap map)throws DataAccessException;
	
	// 厂内  各质量损失类别下钻至质量损失原因 
	public List<Map>  getIncauseByType(HashMap map)throws DataAccessException;
	
	// 厂外  各质量损失类别下钻至质量损失原因 
	public List<Map>  getOutcauseByType(HashMap map)throws DataAccessException;
	
	
	// 厂内   质量损失原因下钻至责任单位 
	public List<Map>  getInDutyunitByCause(HashMap map)throws DataAccessException;
	
	// 厂外   质量损失原因下钻至责任单位 
	public List<Map>  getOutDutyunitByCause(HashMap map)throws DataAccessException;
	
	
	//得到所有的项目类型
	public List<Map>  getProjecttypes( )throws DataAccessException;
	
	//产品故障统计  故障类型统计
	public List<Map>  getFaultCountByModel(HashMap map)throws  DataAccessException; 
	
	//产品故障统计  故障类型统计
	public List<Map>  getFaultCountByType(HashMap map)throws  DataAccessException; 
	
	
	//产品故障统计   对具体型号下钻1层  故障类型  故障数量 
	public List<Map>  getLayerByModel(HashMap map)throws  DataAccessException; 
		
	
	//产品故障统计  故障类型点击下钻展示格式   产品型号	故障数量
	public List<Map>  getLayerByType(HashMap map)throws  DataAccessException; 
	
	//质量损失 +营业收入的截止时间
    public List<Map>  getDeadline( )throws  DataAccessException;
	
	
	//查询代码是否是主数据中的数据
    public  Map isCodeIntable(HashMap map) throws DataAccessException;
    
    //查询数据库中是否有记录数
    public  Map isExists(HashMap map) throws DataAccessException;
        
    //条件查询中的质量损失类别代码
    public  List<LinkedHashMap> getCode( @Param("tableName")String tableName,
							    	     @Param("code")String categorycode,
							    	     @Param("txtmd") String categorytxt,
							    	     @Param("tablebs")     Integer tablebs )throws DataAccessException;
    
	
	//插入数据
    public void operateData(@Param("DATA") InsertFieldTHM_KPI2227 DATA) throws DataAccessException;

    //通用查询
    List<LinkedHashMap> getResult(
           @Param("lbbh") List<String> lbbh,     //质量损失类别编号 
           @Param("lbdm") List<String> lbdm,     //质量损失类别代码 
           @Param("yydm") List<String> yydm,   //质量损失原因代码
           @Param("fsdw") List<String> fsdw,     //质量问题发生单位
//           @Param("productCode") List<String> productCode,   //产品编码
           @Param("zrdw") List<String> zrdw,    //责任单位-------
//           @Param("jnw") List<String> jnw,     //境内/境外JNW--
//           @Param("xzjx") List<String> xzjx,    //新造/检修XZJX--
           @Param("xmlx") List<String> xmlx,    //项目类型
           @Param("startTime") String startTime,     //发现时间上行
           @Param("endTime") String endTime,         //发现时间下行
           @Param("startitem") Integer startitem,
           @Param("pageitem")  Integer pageitem,
           @Param("flag") int flag
  );
    
    /**
     * 查询外部公司最新记录的日期
     * @param map
     * @return
     * @throws DataAccessException
     */
    public   List<HashMap>  getLatestDate(String endYearMonth);
    
    
    /*
     *  质量损失类别编号
     */
	List<String> getLossTypeCode(  @Param("name")String name,
						           @Param("start")Integer start,
						           @Param("limit")Integer limit );

	
    /*
     *  质量问题发生单位
     */
	List<String> getOccurPlant(  @Param("name")String name,
						         @Param("start")Integer start,
						         @Param("limit")Integer limit );
	
	/*
     *  责任单位
     */
	List<String> getDutyPlant(   @Param("name")String name,
						         @Param("start")Integer start,
						         @Param("limit")Integer limit );
 
	
}
