package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.entity.MybatisBase;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by liuchengli on 2016/11/26.
 */
@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_FS22_01 extends MybatisBase {

    private String aufnr;//项目编号
    private String auart;//订单类型 'Z002'
    private String ktext;//项目描述
    private String bukrs;//公司代码
    private double all_buget;//项目总预算
    private double bef_years_cost;//以前年度累计
    private String bug_aufnr;//当年项目预算的编号，有可能 aufnr 没有，
    private double wtjhv;//当年项目预算
    private double peroid_cost;//当期发生额
    private double curr_year_cost;//当年累计发生额
    private double cailiao;//材料费
    private double ranliao;//燃料费
    private double dongli;//动力费
    private double rengong;//人工工资
    private double zhejiu;//折旧费
    private double wuxingzichan;//无形资产摊销
    private double gongju;//工卡模具费
    private double shiyan;//试验检验费
    private double jianding;//一级明细9、研发成果论证、鉴定、评审、验收费用
    private double zhuanli;//一级明细10、专利费
    private double jishu;//一级明细11、技术开发费
    private double zhuanrang;//一级明细12、技术转让费
    private double ziliao;//一级明细13、技术资料费用
    private double chailv;//一级明细14、差旅费
    private double bangong;//一级明细15、办公费
    private double xiuli;//一级明细16、修理费
    private double qita;//一级明细17、其他
    
    
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("THM_FS22:[");
        sb.append("{");
        sb.append("aufnr:");
        sb.append(aufnr);
        sb.append(",auart:");
        sb.append(auart);
        sb.append(",ktext:");
        sb.append(ktext);
        sb.append(",bukrs:");
        sb.append(bukrs);
        
        sb.append(",all_buget:");
        sb.append(all_buget);
        sb.append(",bef_years_cost:");
        sb.append(bef_years_cost);
        sb.append(",bug_aufnr:");
        sb.append(bug_aufnr);
        sb.append(",wtjhv:");
        sb.append(wtjhv);
        sb.append(",peroid_cost:");
        sb.append(peroid_cost);
        sb.append(",curr_year_cost:");
        sb.append(curr_year_cost);
        
        
        sb.append(",cailiao:");
        sb.append(cailiao);
        sb.append(",ranliao:");
        sb.append(ranliao);
        sb.append(",dongli:");
        sb.append(dongli);
        sb.append(",rengong:");
        sb.append(rengong);
        sb.append(",zhejiu:");
        sb.append(zhejiu);
        sb.append(",wuxingzichan:");
        sb.append(wuxingzichan);
        sb.append(",gongju:");
        sb.append(gongju);
        sb.append(",shiyan:");
        sb.append(shiyan);
        sb.append(",jianding:");
        sb.append(jianding);
        sb.append(",zhuanli:");
        sb.append(zhuanli);
        sb.append(",jishu:");
        sb.append(jishu);
        sb.append(",zhuanrang:");
        sb.append(zhuanrang);
        sb.append(",ziliao:");
        sb.append(ziliao);
        sb.append(",chailv:");
        sb.append(chailv);
        sb.append(",bangong:");
        sb.append(bangong);
        sb.append(",xiuli:");
        sb.append(xiuli);
        sb.append(",qita:");
        sb.append(qita);
        
        sb.append("}");
        sb.append("]");

        return sb.toString();
    }
}
