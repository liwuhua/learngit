package com.yjdj.view.core.mapper;

import com.yjdj.view.core.entity.mybeans.MdmTcustomer;
import com.yjdj.view.core.entity.mybeans.THM_FS57;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * Created by sunwan on 2017/3/3.
 */
@MyBatisRepository
public interface IReportMapperFS57 extends BaseMapper{

    List<THM_FS57> selectData(
            @Param("vkorgValue") List<String> vkorgValue,
            @Param("werksValue") List<String> werksValue,
            @Param("kunnrValue") List<String> kunnrValue,
            @Param("dateYearMonth") String dateYearMonth,
            @Param("startitem") int startitem,
            @Param("pageitem") int pageitem,
            @Param("flag") int flag
    );

    /**
     * 获取客户编号
     * @param tableName
     * @param customer
     * @param txtmd
     * @return
     * @throws org.springframework.dao.DataAccessException
     */
    public List<MdmTcustomer> getAllTcustomer(@Param("tableName") String tableName,
                                              @Param("customer") String customer,
                                              @Param("txtmd") String txtmd)throws DataAccessException;

}
