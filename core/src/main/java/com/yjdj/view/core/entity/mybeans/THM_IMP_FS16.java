package com.yjdj.view.core.entity.mybeans;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.entity.MybatisBase;

/**
 * create on 2016/11/09
 * @author yangzhijie
 *
 */

@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_IMP_FS16 extends MybatisBase{
/**
 
 * select ITEMNAME,ITEMNO,MONTHMONEY,THISYEARSUM,LASTYEARSUM
from 
THM_IMP_FS16
where YEARMONTH = (查询条件中的年月)   and BUKRS = (查询条件中的公司代码)
 * 
 */

	private String itemName;//项目
	private String itemNo;//行次
	private String monthMoney;//本月金额
	private String thisYearSum;//本年累计
	private String lastYearSum;//上年同期累计

	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("THM_IMP_FS16:[itemname='");
        sb.append(itemName);
        sb.append("',itemno='");
        sb.append(itemNo);
        sb.append("',monthmoney='");
        sb.append(monthMoney);
        sb.append("',thisyearsum='");
        sb.append(thisYearSum);
        sb.append("',lastyearsum='");
        sb.append(lastYearSum);
        sb.append("']");

        return sb.toString();
	}
	
}
