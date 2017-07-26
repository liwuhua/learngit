package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.Setter;
import lombok.libs.com.zwitserloot.cmdreader.Description;

/**
 * FS55实体类
 * Created by yangzhijie on 17/02/27
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_FS55 {
	
	@Description(value = "客户编码")
	private String parnr;

	@Description(value = "客户名称")
	private String txtmd;
	
	@Description(value = "物料编码")
	private String matnr;
	
	@Description(value = "物料描述")
	private String maktx;
	
	@Description(value = "工厂")
	private String iwerk;
	
	@Description(value = "进厂日期")
	private String zdate;
	
	@Description(value = "合同号")
	private String contnbr;
	
	@Description(value = "合同行项目")
	private String contitm;
	
	@Description(value = "采购合同编号")
	private String bstkd;
	
	@Description(value = "合同数量")
	private String connum;
	
	@Description(value = "合同剩余数")
	private String lastconnum;
	
	@Description(value = "已发货未开票数量")
	private String yfhwkpnum;
	
	@Description(value = "开票数量")
	private String fkimg;
	
	@Description(value = "开票金额")
	private String netwr;
	
	@Override
	public String toString() {
		return "THM_FS53 [parnr=" + parnr + ", txtmd=" + txtmd + ", matnr=" + matnr + ", maktx=" + maktx + ", iwerk=" + iwerk + ", zdate=" + zdate + ", contnbr=" + contnbr + 
				", contitm=" + contitm + ", bstkd=" + bstkd + ", connum=" + connum + ", lastconnum=" + lastconnum + ", yfhwkpnum=" + yfhwkpnum + ", fkimg=" + fkimg + 
				", netwr=" + netwr +"]";
	}

}


