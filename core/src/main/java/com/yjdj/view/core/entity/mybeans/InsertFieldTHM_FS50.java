package com.yjdj.view.core.entity.mybeans;

import com.yjdj.view.core.util.ExcelField;

/**
 *
 * Created by sunwan on 2017/1/3.
 */
public class InsertFieldTHM_FS50 {

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
	    private String amount;  //数量AMOUNT
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
	    private String ss  ;     //"损失费用合计";   
	    private String clfy;      // "材料费用";    新增字段
	    private String ysfy;     // "运输费用";     新增字段
	    private String rgfy;      // "人工差旅费用"; 新增字段
	    private String qtfy;      // "人工差旅费用"; 新增字段
	    private String pc;      //赔偿PC
	    private String jnw;     //境内/境外JNW
	    private String xzjx;    //新造/检修XZJX
		private String lifnr;   //供应商LIFNR
		private String bz;      //备注BZ
	    private String cpfl;     //产品分类CPFL
		private String czbz;      //操作标识
	    private String gzlx;    //故障类型GZLX
	    private String sf;     //合同是否规定赔偿金额、时间和付款方式（是/否)SF
	    private String spqk;    //索赔进展情况SPQK
	    private String fx;     //分析FX
	    private String cgy;    //采购员CGY
	    
    
    @ExcelField(title = "月份",type=0,sort=10)
    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }
    
    @ExcelField(title = "信息编号",type=0,sort=20)
    public String getXxbh() {
        return xxbh;
    }
    public void setXxbh(String xxbh) {
        this.xxbh = xxbh;
    }
    
    
    @ExcelField(title = "质量损失类别编号",type=0,sort=30)
    public String getLbbh() {
        return lbbh;
    }
    public void setLbbh(String lbbh) {
        this.lbbh = lbbh;
    }
    
    
    @ExcelField(title = "质量损失类别代码",type=0,sort=40)
    public String getLbdm() {
        return lbdm;
    }
    public void setLbdm(String lbdm) {
        this.lbdm = lbdm;
    }
    
    
    @ExcelField(title = "质量损失原因代码",type=0,sort=50)
    public String getYydm() {
        return yydm;
    }
    public void setYydm(String yydm) {
        this.yydm = yydm;
    }
    
    
    @ExcelField(title = "配属局段",type=0,sort=60)
    public String getPsjd() {
        return psjd;
    }
    public void setPsjd(String psjd) {
        this.psjd = psjd;
    }
    
    @ExcelField(title = "产品寿命阶段",type=0,sort=70)
    public String getCpsmjd() {
        return cpsmjd;
    }
    public void setCpsmjd(String cpsmjd) {
        this.cpsmjd = cpsmjd;
    }
    
    
    @ExcelField(title = "检修地点",type=0,sort=80)
    public String getJxdd() {
        return jxdd;
    }
    public void setJxdd(String jxdd) {
        this.jxdd = jxdd;
    }
    
    
    @ExcelField(title = "发现时间",type=0,sort=90)
    public String getFxsj() {
        return fxsj;
    }
    public void setFxsj(String fxsj) {
        this.fxsj = fxsj;
    }
    
    
    @ExcelField(title = "产品型号",type=0,sort=100)
    public String getCpxh() {
        return cpxh;
    }
    public void setCpxh(String cpxh) {
        this.cpxh = cpxh;
    }
    
    
    @ExcelField(title = "产品编号",type=0,sort=110)
    public String getCpbh() {
        return cpbh;
    }
    public void setCpbh(String cpbh) {
        this.cpbh = cpbh;
    }


    @ExcelField(title = "数量",type=0,sort=120)
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    
    @ExcelField(title = "新造出厂日期",type=0,sort=130)
    public String getXzccrq() {
        return xzccrq;
    }
    public void setXzccrq(String xzccrq) {
        this.xzccrq = xzccrq;
    }
    
    
    @ExcelField(title = "最近检修出厂日期",type=0,sort=140)
    public String getZjjxccrq() {
        return zjjxccrq;
    }
    public void setZjjxccrq(String zjjxccrq) {
        this.zjjxccrq = zjjxccrq;
    }
    
    
    @ExcelField(title = "机车编号",type=0,sort=150)
    public String getJcbh() {
        return jcbh;
    }
    public void setJcbh(String jcbh) {
        this.jcbh = jcbh;
    }
    
    
    @ExcelField(title = "总运行里程",type=0,sort=160)
    public String getZlc() {
        return zlc;
    }
    public void setZlc(String zlc) {
        this.zlc = zlc;
    }
    
    
    @ExcelField(title = "检修后里程",type=0,sort=170)
    public String getJxhlc() {
        return jxhlc;
    }
    public void setJxhlc(String jxhlc) {
        this.jxhlc = jxhlc;
    }
    
    
    @ExcelField(title = "故障部位",type=0,sort=180)
    public String getGzbw() {
        return gzbw;
    }
    public void setGzbw(String gzbw) {
        this.gzbw = gzbw;
    }
    
    
    @ExcelField(title = "故障描述",type=0,sort=190)
    public String getGzms() {
        return gzms;
    }
    public void setGzms(String gzms) {
        this.gzms = gzms;
    }
    
    
    @ExcelField(title = "处理结果",type=0,sort=200)
    public String getCljg() {
        return cljg;
    }
    public void setCljg(String cljg) {
        this.cljg = cljg;
    }
    
    
    @ExcelField(title = "处理方法",type=0,sort=210)
    public String getClff() {
        return clff;
    }
    public void setClff(String clff) {
        this.clff = clff;
    }
    
    
    @ExcelField(title = "现场故障处理完成日期",type=0,sort=220)
    public String getGzwcrq() {
        return gzwcrq;
    }
    public void setGzwcrq(String gzwcrq) {
        this.gzwcrq = gzwcrq;
    }
    
    @ExcelField(title = "事故类别",type=0,sort=230)
    public String getSglb() {
        return sglb;
    }
    public void setSglb(String sglb) {
        this.sglb = sglb;
    }
    
    
    @ExcelField(title = "严重度",type=0,sort=240)
    public String getYzd() {
        return yzd;
    }
    public void setYzd(String yzd) {
        this.yzd = yzd;
    }
    
    
    @ExcelField(title = "责任初判",type=0,sort=250)
    public String getZrcp() {
        return zrcp;
    }
    public void setZrcp(String zrcp) {
        this.zrcp = zrcp;
    }
    
    
    @ExcelField(title = "原因分析",type=0,sort=260)
    public String getYyfx() {
        return yyfx;
    }
    public void setYyfx(String yyfx) {
        this.yyfx = yyfx;
    }
    
    
    @ExcelField(title = "整改措施",type=0,sort=270)
    public String getZgcs() {
        return zgcs;
    }
    public void setZgcs(String zgcs) {
        this.zgcs = zgcs;
    }
    
    
    @ExcelField(title = "报告编号",type=0,sort=280)
    public String getBgbh() {
        return bgbh;
    }
    public void setBgbh(String bgbh) {
        this.bgbh = bgbh;
    }
    
    @ExcelField(title = "责任单位",type=0,sort=290)
    public String getZrdw() {
        return zrdw;
    }
    public void setZrdw(String zrdw) {
        this.zrdw = zrdw;
    }
    
    
    @ExcelField(title = "损失费用合计",type=0,sort=300)
    public String getSs() {
        return ss;
    }
    public void setSs(String ss) {
        this.ss = ss;
    }
    
    
    @ExcelField(title = "材料费用",type=0,sort=310) 
    public String getClfy() {
		return clfy;
	}
	public void setClfy(String clfy) {
		this.clfy = clfy;
	}
	
	@ExcelField(title = "运输费用",type=0,sort=320) 
	public String getYsfy() {
		return ysfy;
	}
	public void setYsfy(String ysfy) {
		this.ysfy = ysfy;
	}
	
	@ExcelField(title = "人工差旅费用",type=0,sort=330)
	public String getRgfy() {
		return rgfy;
	}
	public void setRgfy(String rgfy) {
		this.rgfy = rgfy;
	}
	
	@ExcelField(title = "其他费用",type=0,sort=340)
	public String getQtfy() {
		return qtfy;
	}
	public void setQtfy(String qtfy) {
		this.qtfy = qtfy;
	}
    
    
    @ExcelField(title = "赔偿",type=0,sort=350)
    public String getPc() {
        return pc;
    }
	public void setPc(String pc) {
        this.pc = pc;
    }
   
    @ExcelField(title = "境内/境外",type=0,sort=360)
    public String getJnw() {
        return jnw;
    }
    public void setJnw(String jnw) {
        this.jnw = jnw;
    }
    
    
    @ExcelField(title = "新造/检修",type=0,sort=370)
    public String getXzjx() {
        return xzjx;
    }
    public void setXzjx(String xzjx) {
        this.xzjx = xzjx;
    }
    
    @ExcelField(title = "供应商",type=0,sort=380)
    public String getLifnr() {
        return lifnr;
    }
    public void setLifnr(String lifnr) {
        this.lifnr = lifnr;
    }
    
    @ExcelField(title = "备注",type=0,sort=390)
    public String getBz() {
        return bz;
    }
    public void setBz(String bz) {
        this.bz = bz;
    }
    
    @ExcelField(title = "产品分类",type=0,sort=400)
    public String getCpfl() {
        return cpfl;
    }
    public void setCpfl(String cpfl) {
        this.cpfl = cpfl;
    }
    
    @ExcelField(title = "操作标识",type=0,sort=410)
    public String getCzbz() {
        return czbz;
    }
    public void setCzbz(String czbz) {
        this.czbz = czbz;
    }
    
    @ExcelField(title = "故障类型",type=0,sort=420)
    public String getGzlx() {
        return gzlx;
    }
    public void setGzlx(String gzlx) {
        this.gzlx = gzlx;
    }
    
    
    @ExcelField(title = "合同是否规定赔偿金额、时间和付款方式（是/否)",type=0,sort=430)
    public String getSf() {
        return sf;
    }
    public void setSf(String sf) {
        this.sf = sf;
    }
    
    
    @ExcelField(title = "索赔进展情况",type=0,sort=440)
    public String getSpqk() {
        return spqk;
    }
    public void setSpqk(String spqk) {
        this.spqk = spqk;
    }
    
    
    @ExcelField(title = "分析",type=0,sort=450)
    public String getFx() {
        return fx;
    }
    public void setFx(String fx) {
        this.fx = fx;
    }
    
    
    @ExcelField(title = "采购员",type=0,sort=460)
    public String getCgy() {
        return cgy;
    }
    public void setCgy(String cgy) {
        this.cgy = cgy;
    }


}
