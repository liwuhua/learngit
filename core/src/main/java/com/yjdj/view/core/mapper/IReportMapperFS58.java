package com.yjdj.view.core.mapper;

import com.yjdj.view.core.entity.mybeans.THM_FS58;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by sunwan on 2017/4/25.
 */
@MyBatisRepository
public interface IReportMapperFS58 extends BaseMapper{

	// 存储过程请求数据
	public void  getData(
            @Param("werksValue") String werksValue,
            @Param("groesValue") String groesValue,
            @Param("zlevelValue") String zlevelValue,
            @Param("matnrValue") String matnrValue,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime
            );
	//查询数据
    List<THM_FS58> selectData(
            @Param("startItem") int startItem,
            @Param("pageItem") int pageItem,
            @Param("flag") int flag
    );
    //查询电机型号
    public List<String> selectGroesJson(@Param("id") String id);
    //查询维修级别 
    public List<String> selectZlevelJson(@Param("id") String id); 
    //查询配件物料编码
    public List<Map<String,String>> selectPjwlbmJson(@Param("id") String id, @Param("name") String name, @Param("start") int start, @Param("limit") int limit);
    

}
