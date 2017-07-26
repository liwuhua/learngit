package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by sunwan on 2016/12/29.
 */

//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
@Setter
@Getter
public class THM_FS50 {
    
//  修改模板后的模板  
   private String month;    //月份MONTH
    private String xxbh;    //信息编号XXBH
    private String lbbh;    //质量损失类别编号LBBH
    private String lbdm;    //质量损失类别代码LBDM
    private String yydm;    //质量损失原因代码YYDM
    private String psjd;    //配属局段PSJD
    private String cpsmjd;  //产品寿命阶段CPSMJD
    private String jxdd;    //检修地点JXDD
    private String fxsj;    //发现时间FXSJ
    private String cpxh;    //产品型号CPXH
    private String cpbh;    //产品编号CPBH
    private double amount;  //数量AMOUNT
    private String xzccrq;  //新造出厂日期XZCCRQ
    private String zjjxccrq;    //最近检修出厂日期ZJJXCCRQ
    private String jcbh;    //机车编号JCBH
    private String zlc;     //总运行里程ZLC
    private String jxhlc;   //检修后里程JXHLC
    private String gzbw;    //故障部位GZBW
    private String gzms;    //故障描述GZMS
    private String cljg;    //处理结果CLJG
    private String clff;    //处理方法CLFF
    private String gzwcrq;  //现场故障处理完成日期GZWCRQ
    private String sglb;    //事故类别SGLB
    private String yzd;     //严重度YZD
    private String zrcp;    //责任初判ZRCP
    private String yyfx;    //原因分析YYFX
    private String zgcs;    //整改措施ZGCS
    private String bgbh;    //报告编号BGBH
    private String zrdw;    //责任单位ZRDW
    private String ss;      //损失费用合计SS
    private double clfy;      //材料费用
    private double ysfy;      //运输费用
    private double rgfy;      //人工差旅费用
    private double qtfy;      //其他费用
    private double pc;      //赔偿PC
    private String jnw;     //境内/境外JNW
    private String xzjx;    //新造/检修XZJX
    private String lifnr;   //供应商LIFNR
    private String bz;      //备注BZ
    private String cpfl;     //产品分类CPFL==项目类型
    private String gzlx;    //故障类型GZLX
    private String sf;     //合同是否规定赔偿金额、时间和付款方式（是/否)SF
    private String spqk;    //索赔进展情况SPQK
    private String fx;     //分析FX
    private String cgy;    //采购员CGY
	
    
    @Override
	public String toString() {
		return "THM_FS50 [month=" + month + ", xxbh=" + xxbh + ", lbbh=" + lbbh + ", lbdm=" + lbdm + ", yydm=" + yydm
				+ ", psjd=" + psjd + ", cpsmjd=" + cpsmjd + ", jxdd=" + jxdd + ", fxsj=" + fxsj + ", cpxh=" + cpxh
				+ ", cpbh=" + cpbh + ", amount=" + amount + ", xzccrq=" + xzccrq + ", zjjxccrq=" + zjjxccrq + ", jcbh="
				+ jcbh + ", zlc=" + zlc + ", jxhlc=" + jxhlc + ", gzbw=" + gzbw + ", gzms=" + gzms + ", cljg=" + cljg
				+ ", clff=" + clff + ", gzwcrq=" + gzwcrq + ", sglb=" + sglb + ", yzd=" + yzd + ", zrcp=" + zrcp
				+ ", yyfx=" + yyfx + ", zgcs=" + zgcs + ", bgbh=" + bgbh + ", zrdw=" + zrdw + ", ss=" + ss + ", clfy="
				+ clfy + ", ysfy=" + ysfy + ", rgfy=" + rgfy + ", qtfy=" + qtfy + ", pc=" + pc + ", jnw=" + jnw
				+ ", xzjx=" + xzjx + ", lifnr=" + lifnr + ", bz=" + bz + ", cpfl=" + cpfl + ", gzlx=" + gzlx + ", sf="
				+ sf + ", spqk=" + spqk + ", fx=" + fx + ", cgy=" + cgy + ", getMonth()=" + getMonth() + ", getXxbh()="
				+ getXxbh() + ", getLbbh()=" + getLbbh() + ", getLbdm()=" + getLbdm() + ", getYydm()=" + getYydm()
				+ ", getPsjd()=" + getPsjd() + ", getCpsmjd()=" + getCpsmjd() + ", getJxdd()=" + getJxdd()
				+ ", getFxsj()=" + getFxsj() + ", getCpxh()=" + getCpxh() + ", getCpbh()=" + getCpbh()
				+ ", getAmount()=" + getAmount() + ", getXzccrq()=" + getXzccrq() + ", getZjjxccrq()=" + getZjjxccrq()
				+ ", getJcbh()=" + getJcbh() + ", getZlc()=" + getZlc() + ", getJxhlc()=" + getJxhlc() + ", getGzbw()="
				+ getGzbw() + ", getGzms()=" + getGzms() + ", getCljg()=" + getCljg() + ", getClff()=" + getClff()
				+ ", getGzwcrq()=" + getGzwcrq() + ", getSglb()=" + getSglb() + ", getYzd()=" + getYzd()
				+ ", getZrcp()=" + getZrcp() + ", getYyfx()=" + getYyfx() + ", getZgcs()=" + getZgcs() + ", getBgbh()="
				+ getBgbh() + ", getZrdw()=" + getZrdw() + ", getSs()=" + getSs() + ", getClfy()=" + getClfy()
				+ ", getYsfy()=" + getYsfy() + ", getRgfy()=" + getRgfy() + ", getQtfy()=" + getQtfy() + ", getPc()="
				+ getPc() + ", getJnw()=" + getJnw() + ", getXzjx()=" + getXzjx() + ", getLifnr()=" + getLifnr()
				+ ", getBz()=" + getBz() + ", getCpfl()=" + getCpfl() + ", getGzlx()=" + getGzlx() + ", getSf()="
				+ getSf() + ", getSpqk()=" + getSpqk() + ", getFx()=" + getFx() + ", getCgy()=" + getCgy()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}



   
}

