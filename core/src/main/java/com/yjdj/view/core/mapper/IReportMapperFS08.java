package com.yjdj.view.core.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by wangkai on 16/10/25.
 */
@MyBatisRepository
public interface IReportMapperFS08  extends BaseMapper {
    /**
     * 获取fs08数据
     * @param dateStart
     * @param dateEnd
     * @return
     */
    public List<Map<String,Object>> getListFs08(@Param("dateStart") String dateStart,
                                                @Param("dateEnd") String dateEnd,
                                                @Param("dateBegin") String dateBegin,
                                                @Param("lastDateStart") String lastDateStart,
                                                @Param("lastDateEnd") String lastDateEnd,
                                                @Param("lastDateBegin") String lastDateBegin);
}
