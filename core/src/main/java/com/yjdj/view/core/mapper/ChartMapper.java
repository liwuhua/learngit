package com.yjdj.view.core.mapper;

import java.util.List;

import com.yjdj.view.core.entity.MybatisBase;
import com.yjdj.view.core.entity.mybeans.CarSalesCount;

/**
 * Created by tao.xu on 2014/12/5.
 */
@MyBatisRepository
public interface ChartMapper extends BaseMapper{

     //和mybatis中的TestMapper.xml文件对应  sql
    List<MybatisBase> selectDailyCounts();
     int          selectCountsAll();
     List<CarSalesCount> selecSalescount();
}


