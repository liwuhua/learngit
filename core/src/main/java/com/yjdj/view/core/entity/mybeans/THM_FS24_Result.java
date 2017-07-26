package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.entity.MybatisBase;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by liuchengli on 2016/12/13.
 * FS24，FS26，FS28 导出最终结果类，
 * 
 */
@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_FS24_Result extends MybatisBase {

    private String racct;//项目编号，内部订单号
    private int id;//在表中所属行号
    private String item_column;//订单描述
    private String txtmd;//项目描述
    private double last_year_cost;//上年发生额
    private double year_plan;//年度计划
    private double period_plan;//本期计划
    private double period_cost;//本期发生额
    private double exe_rate;//执行率
    
    
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("result:["); 
        sb.append("{");
        sb.append("racct:");
        sb.append(racct);
        sb.append(",id:");
        sb.append(id);
        sb.append(",item_column:");
        sb.append(item_column);
        sb.append(",txtmd:");
        sb.append(txtmd);
        sb.append(",last_year_cost:");
        sb.append(last_year_cost);
        sb.append(",year_plan:");
        sb.append(year_plan);
        sb.append(",period_plan:");
        sb.append(period_plan);
        sb.append(",period_cost:");
        sb.append(period_cost);
        sb.append(",exe_rate:");
        sb.append(exe_rate);
        sb.append("}");
        sb.append("]");

        return sb.toString();
    }
}
