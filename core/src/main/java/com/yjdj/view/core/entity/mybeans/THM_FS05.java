package com.yjdj.view.core.entity.mybeans;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.entity.MybatisBase;

/**
 * create on 2016/11/1
 * @author zhangwenguo
 *
 */

@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_FS05 extends MybatisBase{
	
	private String item; 
	private String item_column; //行次
	private String rbukrs;  //公司编码
	private String total; //计算和数
	private String label;
	
	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("THM_FS05:[item='");
        sb.append(item);
        sb.append("',item_column='");
        sb.append(item_column);
        sb.append("',rbukrs='");
        sb.append(rbukrs);
        sb.append("',total='");
        sb.append(total);
        sb.append("',label='");
        sb.append(label);
        sb.append("']");
        
        return sb.toString();
	}

}
