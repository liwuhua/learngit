
package com.yjdj.view.core.service;

import java.util.List;

import com.yjdj.view.core.entity.MybatisBase;
import com.yjdj.view.core.entity.mybeans.CarSalesCount;

public interface ChartService {


    List<MybatisBase> selectDailyCounts();
    Integer selectCountsAll() ;
    List<CarSalesCount>    selecSalescount( );
}
