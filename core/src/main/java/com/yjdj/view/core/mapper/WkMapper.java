package com.yjdj.view.core.mapper;

import com.yjdj.view.core.entity.mybeans.MdmTmaterial;
import com.yjdj.view.core.entity.mybeans.Wktest;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by wangkai on 16/10/24.
 */
@MyBatisRepository
public interface WkMapper {
    /**
     * 查询物料列表
     * @return
     */
//    @Select("select * from MDM_TMATERIAL limit #{start},#{limit}")
    public List<Wktest> testQuery();
}
