package com.yjdj.view.core.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * Created by wangkai on 16/11/26.
 */
@MyBatisRepository
public interface IReportMapperFS21 extends BaseMapper{

    /**
     * 获取fs21结果集
     * @param matnrValue        物料多值
     * @param matnrInterval     物料多区间
     * @param plwrkVaule        工厂多值
     * @param ekgrpValue        采购组多值
     * @param zcjryValue        创建者多值
     * @param zggryValue        更改者多值
     * @param cjDateStart       起始创建日期
     * @param cjDateEnd         截止创建日期
     * @param ggDateStart       起始更改日期
     * @param ggDateEnd         截止更改日期
     * @return
     * @throws DataAccessException
     */
    public List<Map<String,Object>>  getDataFs21(@Param("matnrValue") List<String> matnrValue,
                                  @Param("matnrInterval") List<Map<String,String>> matnrInterval,
                                  @Param("plwrkVaule") List<String> plwrkVaule,
                                  @Param("ekgrpValue") List<String> ekgrpValue,
                                  @Param("mrpctlValue") List<String> mrpctlValue,
//                                  @Param("zcjryValue") List<String> zcjryValue,
//                                  @Param("zggryValue") List<String> zggryValue,
//                                  @Param("cjDateStart") String cjDateStart,
//                                  @Param("cjDateEnd") String cjDateEnd,
//                                  @Param("ggDateStart") String ggDateStart,
//                                  @Param("ggDateEnd") String ggDateEnd,
                                  @Param("startitem") Integer startitem,
                                  @Param("pageitem") Integer pageitem
                                  ) throws DataAccessException;
    
}
