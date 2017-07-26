package com.yjdj.view.core.entity.mybeans;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.entity.MybatisBase;

/**
 * create on 2016/11/21
 * @author yangzhijie
 *
 */

@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_IMP_FS17 extends MybatisBase{
/**
 
 * select ITEMNAME,ITEMNO,CURRENTMONEY,PREVIOUSMONEY
from 
THM_IMP_FS17
where YEARMONTH = (查询条件中的年月)  and BUKRS = (查询条件中的公司代码)
 * 
 */

	private String itemName;//项目
	private String itemNo;//行次
	private String currentMoney;//本期金额
	private String previousMoney;//上期金额

	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("THM_IMP_FS17:[itemName='");
        sb.append(itemName);
        sb.append("',itemNo='");
        sb.append(itemNo);
        sb.append("',currentMoney='");
        sb.append(currentMoney);
        sb.append("',previousMoney='");
        sb.append(previousMoney);
        sb.append("']");

        return sb.toString();
	}
	
}
