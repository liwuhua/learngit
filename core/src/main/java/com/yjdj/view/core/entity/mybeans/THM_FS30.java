package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by liuchengli on 2017/03.
 */

//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
@Setter
@Getter
public class THM_FS30 {

    private String bukrs;//公司代码
    private String compName;//公司名称
    private String lifnr;//供应商编号
    private String gys_txt;//供应商名称
    private String hkont;//科目号
    private double qcye;//期初余额
    private double cg_zg;//采购额（包含暂估）
    private double dqcge;//当期采购额
    private double jxsj;//当期采购额进项税金
    private double yfzk;//当期采购额应付账款
    private double yszk;//当期付款情况小计/应收账款
    private double fkxj;//当期付款情况现金
    private double yhcd;//当期付款银行承兑
    private double sycd;//当期付款商业承兑
    private double yx;//当期付款云信
    private double dz;//当期付款抵账
    private double qmye;//期末余额
    private double zl_sixmon;//应付账款账龄6个月以内
    private double zl_one;//应付账款账龄6个月-1年
    private double zl_two;//应付账款账龄1-2年
    private double zl_three;//应付账款账龄2-3年
    private double zl_four;//应付账款账龄3-4年
    private double zl_five;//应付账款账龄4-5年
    private double zl_five_more;//应付账款账龄5年以上
    private double zb_sixmonth;//质保金账龄6个月以内
    private double zb_sixone;//质保金账龄6个月-1年
    private double zb_onetwo;//质保金账龄，1-2年
    private double zb_twothree;//质保金账龄，2-3年
    private double zb_threefour;//质保金账龄，3-4年
    private double zb_fourfive;//质保金账龄，4-5年
    private double zb_fiveyear;//质保金账龄，5年以上
    private double zb_xiaoji;//质保金小计


    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("bukrs:");
        sb.append(bukrs);
        sb.append("',compName:'");
        sb.append(compName);
        sb.append("',lifnr:'");
        sb.append(lifnr);
        sb.append("',gys_txt:'");
        sb.append(gys_txt);
        sb.append("',hkont:'");
        sb.append(hkont);
        sb.append("',umskz:'");
        sb.append("',qcye:'");
        sb.append(qcye);
        sb.append("',cg_zg:'");
        sb.append(cg_zg);
        sb.append("',dqcge:'");
        sb.append(dqcge);
        sb.append("',jxsj:'");
        sb.append(jxsj);
        sb.append("',yfzk:'");
        sb.append(yfzk);
        sb.append("',yszk:'");
        sb.append(yszk);
        
        
        sb.append("',fkxj:'");
        sb.append(fkxj);
        sb.append("',yhcd:'");
        sb.append(yhcd);
        sb.append("',sycd:'");
        sb.append(sycd);
        sb.append("',yx:'");
        sb.append(yx);
        sb.append("',dz:'");
        sb.append(dz);
        sb.append("',qmye:'");
        sb.append(qmye);
        
        
        sb.append("',zl_sixmon:'");
        sb.append(zl_sixmon);
        sb.append("',zl_one:'");
        sb.append(zl_one);
        sb.append("',zl_two:'");
        sb.append(zl_two);
        sb.append("',zl_three:'");
        sb.append(zl_three);
        sb.append("',zl_four:'");
        sb.append(zl_four);
        sb.append("',zl_five:'");
        sb.append(zl_five);
        sb.append("',zl_five_more:'");
        sb.append(zl_five_more);
        sb.append("',zb_sixmonth:'");
        sb.append(zb_sixmonth);
        sb.append("',zb_sixone:'");
        sb.append(zb_sixone);
        sb.append("',zb_onetwo:'");
        sb.append(zb_onetwo);
        sb.append("',zb_twothree:'");
        sb.append(zb_twothree);
        sb.append("',zb_threefour:'");
        sb.append(zb_threefour);
        sb.append("',zb_fourfive:'");
        sb.append(zb_fourfive);
        sb.append("',zb_fiveyear:'");
        sb.append(zb_fiveyear);
        sb.append("',zb_xiaoji:'");
        sb.append(zb_xiaoji);
        sb.append("'}");


        return sb.toString();
    }
}
