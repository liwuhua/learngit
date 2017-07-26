package com.yjdj.view.core.mapper;

import com.yjdj.view.core.entity.MuserRole;
import com.yjdj.view.core.entity.mybeans.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * Created by wangkai on 16/10/20.
 */
@MyBatisRepository
public interface MuserRoleMapper extends BaseMapper{

    public List<MuserRole> getRoles(@Param("userId") String userId);
}
