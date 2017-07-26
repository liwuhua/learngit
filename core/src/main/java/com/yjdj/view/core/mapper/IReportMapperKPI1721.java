package com.yjdj.view.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.yjdj.view.core.entity.mybeans.THM_KIP1721;

@MyBatisRepository
public interface IReportMapperKPI1721 extends BaseMapper{
	
	/**
	 * 当月采购到货正点率
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String,Object> selectOrdersSumRate(@Param("time") String time) throws DataAccessException;
	public Map<String,Object> selectOrdersSumRateNow(@Param("time1") String time1, @Param("time2") String time2) throws DataAccessException;
	
	/**
	 *  供应商正点率
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP1721> selectLifnrOnTimeRate(@Param("time") String time) throws DataAccessException;
	public List<THM_KIP1721> selectLifnrOnTimeRateNow(@Param("time1") String time1, @Param("time2") String time2) throws DataAccessException;
	
	/**
	 * 采购组维度钻取逻辑 
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP1721> selectEkgrpGoDown(@Param("time") String time) throws DataAccessException;
	
	/**
	 * 采购组维度钻取逻辑 
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP1721> selectLifnrGoDown(@Param("time") String time) throws DataAccessException;	
	
	/**
	 * 采购组 采购项数
	 * @param putRatio1
	 * @param putRatio2
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP1721> selectEkgrpByRate(@Param("time") String time, @Param("putRatio1") int putRatio1, @Param("putRatio2") int putRatio2) throws DataAccessException;
	/**
	 * 采购组 采购项数
	 * @param putRatio1
	 * @param putRatio2
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP1721> selectEkgrpByRateSingle(@Param("time") String time, @Param("putRatio1") int putRatio1, @Param("putRatio2") int putRatio2) throws DataAccessException;
	/**
	 * 原材料总金额
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
	public List<THM_KIP1721> selectMaterialMoney(@Param("one") String one,@Param("two") String two,@Param("three") String three,
			@Param("four") String four,@Param("five") String five,@Param("six") String six,@Param("seven") String seven,
			@Param("eight") String eight,@Param("nine") String nine,@Param("ten") String ten,@Param("eleven") String eleven,
			@Param("twelve") String twelve,@Param("thirteen") String thirteen) throws DataAccessException;
	/**
	 * 原材料周转天数
	 * @param one
	 * @param two
	 * @param three
	 * @param four
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
	public List<THM_KIP1721> selectMaterialDays(@Param("one") String one,@Param("two") String two,@Param("three") String three,
			@Param("four") String four,@Param("five") String five,@Param("six") String six,@Param("seven") String seven,
			@Param("eight") String eight,@Param("nine") String nine,@Param("ten") String ten,@Param("eleven") String eleven,
			@Param("twelve") String twelve,@Param("thirteen") String thirteen) throws DataAccessException;
	
	/**
	 * 产成品总金额
	 * @param one
	 * @param two
	 * @param three
	 * @param four
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
	public List<THM_KIP1721> selectProductMoney(@Param("one") String one,@Param("two") String two,@Param("three") String three,
			@Param("four") String four,@Param("five") String five,@Param("six") String six,@Param("seven") String seven,
			@Param("eight") String eight,@Param("nine") String nine,@Param("ten") String ten,@Param("eleven") String eleven,
			@Param("twelve") String twelve,@Param("thirteen") String thirteen) throws DataAccessException;
	/**
	 * 产成品周转天数
	 * @param one
	 * @param two
	 * @param three
	 * @param four
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
	public List<THM_KIP1721> selectProductDays(@Param("one") String one,@Param("two") String two,@Param("three") String three,
			@Param("four") String four,@Param("five") String five,@Param("six") String six,@Param("seven") String seven,
			@Param("eight") String eight,@Param("nine") String nine,@Param("ten") String ten,@Param("eleven") String eleven,
			@Param("twelve") String twelve,@Param("thirteen") String thirteen) throws DataAccessException;
	/**
	 * 原材料评估类下钻
	 * @param time
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP1721>  selectMaterialGoDown(@Param("YEARMONTH") String YEARMONTH,@Param("FLAG") String FLAG) throws DataAccessException;
	/**
	 * 库存资金占用 下钻 , 无动态下钻
	 * @param time
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP1721>  selectMaterialDynamicGoDown(@Param("time") String time) throws DataAccessException;

	/**
	 * 库存资金占用 下钻 , 无动态下钻
	 * @param time
	 * @return
	 * @throws DataAccessException
	 */
	public List<THM_KIP1721>  selectProductDynamicGoDown(@Param("time") String time) throws DataAccessException;

}
