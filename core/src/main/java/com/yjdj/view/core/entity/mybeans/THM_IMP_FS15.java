package com.yjdj.view.core.entity.mybeans;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.entity.MybatisBase;

/**
 * create on 2016/11/04
 * @author yangzhijie
 *
 */

@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_IMP_FS15 extends MybatisBase{

	private String itemname;//项目
	private String itemno;//行次
	private String finalmoney;//期末金额
	private String startmoney;//年初金额

	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("THM_IMP_FS15:[itemname='");
        sb.append(itemname);
        sb.append("',itemno='");
        sb.append(itemno);
        sb.append("',finalmoney='");
        sb.append(finalmoney);
        sb.append("',startmoney='");
        sb.append(startmoney);
        sb.append("']");

        return sb.toString();
	}
	
}
