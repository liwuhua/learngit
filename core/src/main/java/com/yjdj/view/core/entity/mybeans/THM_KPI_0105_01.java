package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.entity.MybatisBase;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by liuchengli on 2016/11/26.
 * 
 */
@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_KPI_0105_01 extends MybatisBase {

    private double incomeAll;//营业收入金额（当年累计）
    private double profitAll;//净利润金额（当年累计）
    private String burks;//公司代码
    private String comName;//公司名称
    private double profitBukrs;//每个公司净利润金额
    
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("incomeAll:");
        sb.append(incomeAll);
        sb.append(",burks:");
        sb.append(burks);
        sb.append(",comName:");
        sb.append(comName);
        sb.append(",profitBukrs:");
        sb.append(profitBukrs);
        sb.append("}");
        return sb.toString();
    }
}
