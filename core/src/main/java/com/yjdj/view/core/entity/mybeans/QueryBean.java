package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

/**
 * Created by wangkai on 16/10/27.
 */
@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class QueryBean {
    private List<String> xqdwValue;    //需求单位多值

    private List<String> ltxa1Value;    //作业工序多值

    private List<String> kdaufValue;    //营销订单多值

    private List<String> kdposValue;    //行项目多值

    private List<String> oneValue;      //预留多值字段

    private List<String> twoValue;      //预留多值字段

    private List<String> sobkzValue;      //特殊库存标识

    private List<String> sttypValue;      //库存状态

    private List<String> matlGroupValue;      //物料组

    private List<String> valClassValue;      //评估类

    private List<String> matnrValue;    //物料多值
    
    private List<String> comppurtypeValue;    //组件采购类型

    private List<String> matnrInterval; //物料多区间

    private List<String> vkorgValue;    //销售组织多值

    private List<String> vkburValue;    //销售部门多值

    private List<String> plantValue;     //工厂多值

    private List<String> mrpctlValue;     //MRP控制者多值

    private List<String> vendorValue;    //供应商多值

    private List<String> vendorTypeValue; //供应商类型多值

    private List<String> purGroupValue;  //采购组多值

    private List<String> vbelnValue;     //销售订单多值
    
    private List<String> werksValue;     //销售订单工厂多值
    
    private List<String> dauatValue;      //订单类型多值
    
    private List<String> dwerkValue;     //生产工厂多值

    private List<String> ebelnValue;     //采购订单多值

    private List<String> timediffValue;  //时间差异多值

    private List<String> compCodeValue;  //公司代码编号多值

    private List<String> kunnrValue;     //客户多值

    private List<String> auartValue;    //合同类型多值

    private List<String> lgortValue;    //库存地点多值

    private List<String> lgpbeValue;    //管库员多值

    private String productType;          //产品类型

    private boolean isShow=false;       //是否显示
    
    private boolean isExport=false;     //是否是导出功能 (默认不是导出)
    
    private boolean isExclu1=false;     //是否排他(默认是不排他)

    private boolean isExclu2=false;     //是否排他(默认是不排他)

    private Integer dayNum;              //天数

    private String ylzd;                //预留字段

    private String startTime;           //时间上行

    private String endTime;             //时间下行

    private String startTimeTwo;        //时间TWO上行

    private String endTimeTwo;          //时间TWO下行
    
    private String startTimeThree;        //时间Three上行

    private String endTimeThree;          //时间Three下行

    private String dateTime;            //时间

    private String dateYearMonth;       //时间年月  格式:201611

    private String dateYearMonthStart; //年月区间上行

    private String dateYearMonthEnd;    //年月区间下行

    private Integer startitem = 0;       //分页起始条件

    private Integer pageitem = 20;     //分页截取条件

    private List<String> orderID; //研发费用项目号，订单号 多值

    private List<String> trainmodel;  //车型 多值

    private String motormodel;  //电机型号 单值
    
    private List<String> arbplValue;  //检修地点 多值
    
    private List<String> groesValue;  //电机型号 多值
    
    private List<String> sernrValue;  //电机编号 多值
    
    private List<String> zlevelValue;  //维修级别 多值
    
    private List<String> cpxh;  //产品型号
    
    private List<String> aname;  //配件名称
    
    private List<String> fwz;  //服务站
    
    private List<String> fwd;  //服务点
    

    public List<Map<String,String>> getMatnrIntervalMap(){
        List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
        if(CollectionUtils.isNotEmpty(matnrInterval)){
            for(String str : matnrInterval){
                Map<String,String> map = new HashMap<String,String>();
                if(str.indexOf(':')>0){
                    String[] ss = str.split(":");
                    map.put(ss[0],ss[1]);
                    mapList.add(map);
                }
            }
        }else{
        	mapList = null;
        }

        return mapList;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("QueryBean : [matnrValue='");
        sb.append(matnrValue);
        sb.append("',matnrInterval='");
        sb.append(matnrInterval);
        sb.append("',vkorgValue='");
        sb.append(vkorgValue);
        sb.append("',vkburValue='");
        sb.append(vkburValue);
        sb.append("',plantValue='");
        sb.append(plantValue);
        sb.append("',vendorValue='");
        sb.append(vendorValue);
        sb.append("',purGroupValue='");
        sb.append(purGroupValue);
        sb.append("',vbelnValue='");
        sb.append(vbelnValue);
     	sb.append("',werksValue='");
        sb.append(werksValue);
        sb.append("',dauatValue='");
        sb.append(dauatValue);
	    sb.append("',dwerkValue='");
        sb.append(dwerkValue);
        sb.append("',comppurtypeValue='");
        sb.append(comppurtypeValue);
        sb.append("',ebelnValue='");
        sb.append(ebelnValue);
        sb.append("',compCodeValue='");
        sb.append(compCodeValue);
        sb.append("',kunnrValue='");
        sb.append(kunnrValue);
        sb.append("',auartValue='");
        sb.append(auartValue);
        sb.append("',arbplValue='");
        sb.append(arbplValue);
        sb.append("',groesValue='");
        sb.append(groesValue);
        sb.append("',sernrValue='");
        sb.append(sernrValue);
        sb.append("',zlevelValue='");
        sb.append(zlevelValue);
        sb.append("',productType='");
        sb.append(productType);
        sb.append("',isShow='");
        sb.append(isShow);
        sb.append("',isExport='");
        sb.append(isExport);
        sb.append("',isExclu1='");
        sb.append(isExclu1);
        sb.append("',isExclu2='");
        sb.append(isExclu2);
        sb.append("',startTime='");
        sb.append(startTime);
        sb.append("',endTime='");
        sb.append(endTime);
        sb.append("',startTimeTwo='");
        sb.append(startTimeTwo);
        sb.append("',endTimeTwo='");
        sb.append(endTimeTwo);
	    sb.append("',startTimeThree='");
        sb.append(startTimeThree);
        sb.append("',endTimeThree='");
        sb.append(endTimeThree);
        sb.append("',dateTime='");
        sb.append(dateTime);
        sb.append("',dateYearMonth='");
        sb.append(dateYearMonth);
        sb.append("']");

        return sb.toString();
    }

}
