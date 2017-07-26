package com.yjdj.view.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.THM_KIP1516;
import com.yjdj.view.core.entity.mybeans.THM_KIP1516_01;

@MyBatisRepository
public interface IReportMapperKPI1516 extends BaseMapper{
	/**
	 * 订单在产金额
	 * @param one
	 * @param two
	 * @param three
	 * @param foure
	 * @param five
	 * @param six
	 * @param seven
	 * @param eight
	 * @param nine
	 * @param ten
	 * @param eleven
	 * @param twelven
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP1516> selectOrderInProductionMoney(@Param("one") String one,@Param("two") String two,@Param("three") String three,
			@Param("four") String four,@Param("five") String five,@Param("six") String six,@Param("seven") String seven,
			@Param("eight") String eight,@Param("nine") String nine,@Param("ten") String ten,@Param("eleven") String eleven,
			@Param("twelve") String twelve,@Param("thirteen") String thirteen) throws DataAccessException;
	
	/**
	 * 在产资金周转天数
	 * @param one
	 * @param two
	 * @param three
	 * @param foure
	 * @param five
	 * @param six
	 * @param seven
	 * @param eight
	 * @param nine
	 * @param ten
	 * @param eleven
	 * @param twelven
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP1516> selectInProductionMoneyDays(@Param("one") String one,@Param("two") String two,@Param("three") String three,
			@Param("four") String four,@Param("five") String five,@Param("six") String six,@Param("seven") String seven,
			@Param("eight") String eight,@Param("nine") String nine,@Param("ten") String ten,@Param("eleven") String eleven,
			@Param("twelve") String twelve,@Param("thirteen") String thirteen) throws DataAccessException;

   /**
    * 订单在产金额,车间仓材料总金额 ，一级钻取，传入具体月份
    * @param yearmonth
    * @return
    * @throws DataAccessException
    */
	public THM_KIP1516_01 getFirstDetail(@Param("yearmonth") String yearmonth)  throws DataAccessException;
	
	/**
	 * 订单在产下钻2层-无动态材料下钻
	 * @param yearmonth
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP1516_01> getWuDongTaiDetail(@Param("yearmonth") String yearmonth)  throws DataAccessException;
	
	/**
	 * 按订单类别下钻
	 */
    public List<THM_KIP1516_01> getOrderDetail(@Param("yearmonth") String yearmonth)  throws DataAccessException;
		
   /**
    * 按评估类别下钻
    */
	public List<THM_KIP1516_01> getVClassShopDetail(@Param("yearmonth") String yearmonth)  throws DataAccessException;
	
}
