/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.yjdj.view.core.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.yjdj.view.core.entity.MybatisBase;
import com.yjdj.view.core.entity.mybeans.CarSalesCount;
import com.yjdj.view.core.mapper.ChartMapper;
import com.yjdj.view.core.service.ChartService;

@Service
@TransactionConfiguration(defaultRollback = false)
public class ChartServiceImpl  implements ChartService {
	
	@Autowired
	private ChartMapper chartMapper;


    @Override
    public List<MybatisBase> selectDailyCounts() {
        return chartMapper.selectDailyCounts();
    }
    
    @Override
    public Integer selectCountsAll() {
        return chartMapper.selectCountsAll();
    }
    @Override
    public   List<CarSalesCount> selecSalescount( ){
    	List<CarSalesCount> lists = chartMapper.selecSalescount();
    	return lists;
    }
    
    
}
