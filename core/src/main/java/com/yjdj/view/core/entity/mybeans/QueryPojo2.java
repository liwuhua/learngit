package com.yjdj.view.core.entity.mybeans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangkai on 16/10/27.
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class QueryPojo2 {
   
    private List<String> lbbh;   	    //质量损失类别编号
    private List<String> lbdm;          //质量损失类别代码
    private List<String> yydm;          //质量损失原因代码
    private List<String> fsdw;          //质量问题发生单位
    private List<String> productCode;   //产品编码
    private List<String> zrdw;          //责任单位
    private List<String> jnw;           //境内/境外JNW
    private List<String> xzjx;          //新造/检修XZJX
    private List<String> xmlx;          //项目类型
    
    private String startTime;           //发现时间上行
    private String endTime;             //发现时间下行
    private Integer startitem =0 ;       //分页起始条件
    private Integer pageitem = 20;     //分页截取条件
    private boolean isExport=false;     //是否是导出功能 (默认不是导出)


}
