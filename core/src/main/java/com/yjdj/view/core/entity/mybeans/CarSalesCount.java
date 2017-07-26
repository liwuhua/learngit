package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.entity.MybatisBase;

import lombok.Getter;
import lombok.Setter;


/**
 * 
 *作者:Administrator
 *日期:2016年8月15日 下午5:21:43
 *描述:
 *
 */

@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class CarSalesCount extends MybatisBase{
    private Long id;

    private String brand;

    private Integer salescount;

	@Override
	public String toString() {
		return "CarSalesCount [id=" + id + ", brand=" + brand + ", salescount=" + salescount + "]";
	}

}