package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.entity.MybatisBase;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by liuchengli on 2017/01/10.
 * 
 */
@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_KPI_0105_02 extends MybatisBase {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String s_date;//年月
	private String bukrs;//公司代码
	private String comName;//公司名称
	private String customer;//客户
	private String cust_group;//客户类型编码
	private String cust_type;//客户类型
    private double dmbtr_sh;//应收账款金额（年度趋势）
    private double dmbtr_sh_all;//应收账款金额,没有扣除坏账的 
    private double dmbtr_s;//应收账款本期新增金额（年度趋势）
    private double dmbtr_h;//应收账款本期回款金额（年度趋势）
    private double badloan;//内部公司坏账
    private double hyys;//海外公司应收
    private double hyys_all;//海外公司应收原值
    private double hyhz;//海外坏账
    
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("s_date:");
        sb.append(s_date);
        sb.append(",bukrs:");
        sb.append(bukrs);
        sb.append(",comName:");
        sb.append(comName);
        sb.append(",cust_group:");
        sb.append(cust_group);
        sb.append(",cust_type:");
        sb.append(cust_type);
        sb.append(",customer:");
        sb.append(customer);
        sb.append(",dmbtr_sh_all:");
        sb.append(dmbtr_sh_all);
        sb.append(",dmbtr_sh:");
        sb.append(dmbtr_sh);
        sb.append(",dmbtr_s:");
        sb.append(dmbtr_s);
        sb.append(",dmbtr_h:");
        sb.append(dmbtr_h);
        
        sb.append(",badloan:");
        sb.append(badloan);
        
        sb.append(",hyys:");
        sb.append(hyys);
        sb.append(",hyys_all:");
        sb.append(hyys_all);
        sb.append(",hyhz:");
        sb.append(hyhz);
        sb.append("}");
        return sb.toString();
    }
}
