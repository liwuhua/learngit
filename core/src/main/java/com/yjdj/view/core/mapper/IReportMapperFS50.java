package com.yjdj.view.core.mapper;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.InsertFieldTHM_FS50;
import com.yjdj.view.core.entity.mybeans.THM_FS50;

/**
 * Created by sunwan on 2017/1/3.
 */
@MyBatisRepository
public interface IReportMapperFS50 {
//通用查询
    List<THM_FS50> selectData_01(
            @Param("xxbhValue") List<String> xxbh,    //信息编号XXBH
            @Param("psjdValue") List<String> psjd,    //配属局段PSJD
            @Param("cpsmjdValue") List<String> cpsmjd,  //产品寿命阶段CPSMJD
            @Param("jxddValue") List<String> jxdd,    //检修地点JXDD
            @Param("startTime") String startTime,//发现时间上行
            @Param("endTime") String endTime,    //发现时间下行
            @Param("startYearMonth") String startYearMonth,//月份上行
            @Param("endYearMonth")   String endYearMonth, //月份下行
            @Param("cpxhValue") List<String> cpxh,    //产品型号CPXH
            @Param("cpbhValue") List<String> cpbh,    //产品编号CPBH
            @Param("jcbhValue") List<String> jcbh,    //机车编号JCBH
            @Param("gzbwValue") List<String> gzbw,    //故障部位GZBW
            @Param("sglbValue") List<String> sglb,    //事故类别SGLB
            @Param("yzdValue") List<String> yzd,     //严重度YZD
            @Param("jnwValue") List<String> jnw,     //境内/境外JNW
            @Param("xzjxValue") List<String> xzjx,    //新造/检修XZJX
            @Param("lifnrValue") List<String> lifnr,   //供应商LIFNR
            @Param("startitem") int startitem,
            @Param("pageitem") int pageitem,
            @Param("flag") int flag
    );
//国铁服务中心查询
    List<THM_FS50> selectData_02(
            @Param("xxbhValue") List<String> xxbh,    //信息编号XXBH
            @Param("psjdValue") List<String> psjd,    //配属局段PSJD
            @Param("cpsmjdValue") List<String> cpsmjd,  //产品寿命阶段CPSMJD
            @Param("jxddValue") List<String> jxdd,    //检修地点JXDD
            @Param("startTime") String startTime,//发现时间上行
            @Param("endTime") String endTime,    //发现时间下行
            @Param("startYearMonth") String startYearMonth,//月份上行
            @Param("endYearMonth")   String endYearMonth, //月份下行
            @Param("cpxhValue") List<String> cpxh,    //产品型号CPXH
            @Param("cpbhValue") List<String> cpbh,    //产品编号CPBH
            @Param("jcbhValue") List<String> jcbh,    //机车编号JCBH
            @Param("gzbwValue") List<String> gzbw,    //故障部位GZBW
            @Param("sglbValue") List<String> sglb,    //事故类别SGLB
            @Param("yzdValue") List<String> yzd,     //严重度YZD
            @Param("jnwValue") List<String> jnw,     //境内/境外JNW
            @Param("xzjxValue") List<String> xzjx,    //新造/检修XZJX
            @Param("lifnrValue") List<String> lifnr,   //供应商LIFNR
            @Param("lbbhValue") List<String> lbbh,    //质量损失类别编号LBBH
            @Param("bgbhValue") List<String> bgbh,    //报告编号BGBH
            @Param("startitem") int startitem,
            @Param("pageitem") int pageitem,
            @Param("flag") int flag
    );
//质保部查询
    List<THM_FS50> selectData_03(
            @Param("xxbhValue") List<String> xxbh,    //信息编号XXBH
            @Param("psjdValue") List<String> psjd,    //配属局段PSJD
            @Param("cpsmjdValue") List<String> cpsmjd,  //产品寿命阶段CPSMJD
            @Param("jxddValue") List<String> jxdd,    //检修地点JXDD
            @Param("startTime") String startTime,//发现时间上行
            @Param("endTime") String endTime,    //发现时间下行
            @Param("startYearMonth") String startYearMonth,//月份上行
            @Param("endYearMonth")   String endYearMonth, //月份下行
            @Param("cpxhValue") List<String> cpxh,    //产品型号CPXH
            @Param("cpbhValue") List<String> cpbh,    //产品编号CPBH
            @Param("jcbhValue") List<String> jcbh,    //机车编号JCBH
            @Param("gzbwValue") List<String> gzbw,    //故障部位GZBW
            @Param("sglbValue") List<String> sglb,    //事故类别SGLB
            @Param("yzdValue") List<String> yzd,     //严重度YZD
            @Param("jnwValue") List<String> jnw,     //境内/境外JNW
            @Param("xzjxValue") List<String> xzjx,    //新造/检修XZJX
            @Param("lifnrValue") List<String> lifnr,   //供应商LIFNR
            @Param("lbbhValue") List<String> lbbh,    //质量损失类别编号LBBH
            @Param("startitem") int startitem,
            @Param("pageitem") int pageitem,
            @Param("flag") int flag
    );
//插入数据
    public void insertData(@Param("DATA") InsertFieldTHM_FS50 DATA) throws DataAccessException;

//导入查询
    List<THM_FS50> selectData_04(
            @Param("xxbhValue") List<String> xxbh,    //信息编号XXBH
            @Param("psjdValue") List<String> psjd,    //配属局段PSJD
            @Param("cpsmjdValue") List<String> cpsmjd,  //产品寿命阶段CPSMJD
            @Param("jxddValue") List<String> jxdd,    //检修地点JXDD
            @Param("startTime") String startTime,//发现时间上行
            @Param("endTime") String endTime,    //发现时间下行
            @Param("startYearMonth") String startYearMonth,//月份上行
            @Param("endYearMonth")   String endYearMonth, //月份下行
            @Param("cpxhValue") List<String> cpxh,    //产品型号CPXH
            @Param("cpbhValue") List<String> cpbh,    //产品编号CPBH
            @Param("jcbhValue") List<String> jcbh,    //机车编号JCBH
            @Param("gzbwValue") List<String> gzbw,    //故障部位GZBW
            @Param("sglbValue") List<String> sglb,    //事故类别SGLB
            @Param("yzdValue") List<String> yzd,     //严重度YZD
            @Param("jnwValue") List<String> jnw,     //境内/境外JNW
            @Param("xzjxValue") List<String> xzjx,    //新造/检修XZJX
            @Param("lifnrValue") List<String> lifnr,   //供应商LIFNR
            @Param("lbbhValue") List<String> lbbh,    //质量损失类别编号LBBH
            @Param("bgbhValue") List<String> bgbh,    //报告编号BGBH
            @Param("startitem") int startitem,
            @Param("pageitem") int pageitem,
            @Param("flag") int flag
    );
     
    //事故类别
	List<String> getAccitype(@Param("name")String name);
	
	//故障名称
	List<String> getDefalocation(@Param("name")String name);
	
	//产品寿命阶段
	List<String> getProductLife(@Param("name")String name);	
	
	//产品型号
	List<String> getProductModel(@Param("name")String name);	
	
	//配属局段
	List<String> getAttaburea(@Param("name")String name);	
	
	//严重度
	List<String> getSeverity(@Param("name")String name);
	
	//信息编号
	List<String> getInfocode(  @Param("name")String name,
							   @Param("start")Integer start,
			                   @Param("limit")Integer limit);
	
	//产品编号
	List<String> getProducCode(  @Param("name")String name,
		    					 @Param("start")Integer start,
                                 @Param("limit")Integer limit);
	
	
	//机车编号
	List<String> getMotorCode(  @Param("name")String name,
							    @Param("start")Integer start,
					            @Param("limit")Integer limit );
	
	
	//报告编号
	List<String> getReportCode(  @Param("name")String name,
		                         @Param("start")Integer start,
                                 @Param("limit")Integer limit );
	
	//供应商
	List<String> getTvendor(  @Param("name")String name,
		                      @Param("start")Integer start,
                              @Param("limit")Integer limit );
	
	
	List<String> getLossTypeCode(  @Param("name")String name,
						           @Param("start")Integer start,
						           @Param("limit")Integer limit );

}

