package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.Setter;
import lombok.libs.com.zwitserloot.cmdreader.Description;

/**
 * FS54实体类
 * Created by yangzhijie on 17/03/30
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_FS54 {
	
	@Description(value = "年月")
	private String yearmonth;
	
	@Description(value = "产品类别")
	private String pclass;

	@Description(value = "产品名称")
	private String pname;
	
	@Description(value = "产品型号")
	private String pmodel;
	
	@Description(value = "配件名称")
	private String aname;
	
	@Description(value = "配件型号")
	private String amodel;
	
	@Description(value = "物料编码")
	private String matnr;
	
	@Description(value = "使用车型")
	private String traintype;
	
	@Description(value = "厂外储备量")
	private String outnum;
	
	@Description(value = "厂内库存")
	private String innum;
	
	@Description(value = "正常")
	private String pyes;
	
	@Description(value = "非正常")
	private String pno;
	
	@Description(value = "转销售")
	private String psale;
	
	@Description(value = "所属路局")
	private String railstation;
	
	@Description(value = "服务站")
	private String fwz;
	
	@Description(value = "服务点")
	private String fwd;
	
	
	
	@Override
	public String toString() {
		return "THM_FS53 [yearmonth=" + yearmonth + ", pclass=" + pclass +  ", pname=" + pname + ", pmodel=" + pmodel + ", aname=" + aname + ", amodel=" + amodel + ", matnr=" + matnr + ", traintype=" + traintype + 
				", outnum=" + outnum + ", innum=" + innum + ", pyes=" + pyes + ", pno=" + pno + ", psale=" + psale + ", railstation=" + railstation + ", fwz=" + fwz +
				", fwd=" + fwd +"]";
	}

}


