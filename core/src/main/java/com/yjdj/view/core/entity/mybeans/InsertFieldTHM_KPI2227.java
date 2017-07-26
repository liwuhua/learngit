package com.yjdj.view.core.entity.mybeans;

import com.yjdj.view.core.util.ExcelField;

/**
 *
 * Created by liwuhua on 2017/1/3.
 */
public class InsertFieldTHM_KPI2227 {

     private String time     = "时间";
     private String yue    = "月份";
     private String lbbh    = "质量损失类别编号";
     private String cpxh    = "产品型号";
     private String lbdm    = "质量损失类别代码";
     private String yydm    = "质量损失原因代码";
     private String fsdw    = "质量问题发生单位";
     private String fxgx  = "发现工序";
//     private String th    = "图号";
     private String cpmc    = "产品名称";
     private String gzsl    = "不合格数量(故障数量)";
     private String dw    = "单位";  
     private String wtms  = "质量问题描述";
     private String djbh  = "不合格品单据编号";
     private String zt    = "不合格状态";
     private String gsss    = "工时损失";
     private String clss    = "材料损失";
     private String qtss    = "其他损失";
     private String yjss    = "预计损失金额(元)";
     private String sjsse    = "实际损失金额(元)";
     private String pcje    = "赔偿金额(元)";
     private String zrdw   = "责任单位";
//     private String jnw    = "境内/境外";
//     private String xzjx    = "新造/检修";
//     private String ddh    = "订单号";
//     private String ylh    = "预留号";
     private String xmlx     = "项目类型";
     private String czbs    = "操作标识";
//     private String isfh    = "合同是否规定赔偿金额、时间和付款方式(是/否)";
//     private String spqk    = "索赔进展情况";
//     private String fx  = "分析";
//     private String cgy    = "采购员";
    

    @ExcelField(title = "时间",type=0,sort=10)
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    
    @ExcelField(title ="月份",type=0,sort=20)
	public  String getYue() {
		return yue;
	}
	public  void setYue(String yue) {
		this.yue = yue;
	}
	
	@ExcelField(title ="质量损失类别编号",type=0,sort=30)
	public  String getLbbh() {
		return lbbh;
	}
	public  void setLbbh(String lbbh) {
		this.lbbh = lbbh;
	}
	
	@ExcelField(title ="产品型号",type=0,sort=40)
	public  String getCpxh() {
		return cpxh;
	}
	public  void setCpxh(String cpxh) {
		this.cpxh = cpxh;
	}
	
	@ExcelField(title ="质量损失类别代码",type=0,sort=50)
	public  String getLbdm() {
		return lbdm;
	}
	public  void setLbdm(String lbdm) {
		this.lbdm = lbdm;
	}
	
	@ExcelField(title ="质量损失原因代码",type=0,sort=60)
	public  String getYydm() {
		return yydm;
	}
	public  void setYydm(String yydm) {
		this.yydm = yydm;
	}
	
	@ExcelField(title ="质量问题发生单位",type=0,sort=70)
	public  String getFsdw() {
		return fsdw;
	}
	public  void setFsdw(String fsdw) {
		this.fsdw = fsdw;
	}
	
	@ExcelField(title ="发现工序",type=0,sort=80)
	public  String getFxgx() {
		return fxgx;
	}
	public  void setFxgx(String fxgx) {
		this.fxgx = fxgx;
	}
	
//	@ExcelField(title ="图号",type=0,sort=90)
//	public  String getTh() {
//		return th;
//	}
//	public  void setTh(String th) {
//		this.th = th;
//	}
	
	@ExcelField(title ="产品名称",type=0,sort=90)
	public  String getCpmc() {
		return cpmc;
	}
	public  void setCpmc(String cpmc) {
		this.cpmc = cpmc;
	}
	
	@ExcelField(title ="不合格数量(故障数量)",type=0,sort=100)
	public  String getGzsl() {
		return gzsl;
	}
	public  void setGzsl(String gzsl) {
		this.gzsl = gzsl;
	}
	
	@ExcelField(title ="单位"  ,type=0,sort=110)
	public  String getDw() {
		return dw;
	}
	public  void setDw(String dw) {
		this.dw = dw;
	}
	
	@ExcelField(title ="质量问题描述",type=0,sort=120)
	public  String getWtms() {
		return wtms;
	}
	public  void setWtms(String wtms) {
		this.wtms = wtms;
	}
	
	@ExcelField(title ="不合格品单据编号",type=0,sort=130)
	public  String getDjbh() {
		return djbh;
	}
	public  void setDjbh(String djbh) {
		this.djbh = djbh;
	}
	
	@ExcelField(title ="不合格状态",type=0,sort=140)
	public  String getZt() {
		return zt;
	}
	public  void setZt(String zt) {
		this.zt = zt;
	}
	
	@ExcelField(title ="工时损失",type=0,sort=150)
	public  String getGsss() {
		return gsss;
	}
	public  void setGsss(String gsss) {
		this.gsss = gsss;
	}
	
	@ExcelField(title ="材料损失",type=0,sort=160)
	public  String getClss() {
		return clss;
	}
	public  void setClss(String clss) {
		this.clss = clss;
	}
	
	@ExcelField(title ="其他损失",type=0,sort=170)
	public  String getQtss() {
		return qtss;
	}
	public  void setQtss(String qtss) {
		this.qtss = qtss;
	}
	
	@ExcelField(title ="预计损失金额(元)",type=0,sort=180)
	public  String getYjss() {
		return yjss;
	}
	public  void setYjss(String yjss) {
		this.yjss = yjss;
	}
	
	@ExcelField(title ="实际损失金额(元)",type=0,sort=190)
	public  String getSjsse() {
		return sjsse;
	}
	public  void setSjsse(String sjsse) {
		this.sjsse = sjsse;
	}
	
	@ExcelField(title ="赔偿金额(元)",type=0,sort=200)
	public  String getPcje() {
		return pcje;
	}
	public  void setPcje(String pcje) {
		this.pcje = pcje;
	}
	
	@ExcelField(title ="责任单位",type=0,sort=210)
	public  String getZrdw() {
		return zrdw;
	}
	public  void setZrdw(String zrdw) {
		this.zrdw = zrdw;
	}
	
//	@ExcelField(title ="境内/境外",type=0,sort=230)
//	public  String getJnw() {
//		return jnw;
//	}
//	public  void setJnw(String jnw) {
//		this.jnw = jnw;
//	}
//	
//	@ExcelField(title ="新造/检修",type=0,sort=240)
//	public  String getXzjx() {
//		return xzjx;
//	}
//	public  void setXzjx(String xzjx) {
//		this.xzjx = xzjx;
//	}
//	
//	@ExcelField(title ="订单号",type=0,sort=250)
//	public  String getDdh() {
//		return ddh;
//	}
//	public  void setDdh(String ddh) {
//		this.ddh = ddh;
//	}
//	
//	@ExcelField(title ="预留号",type=0,sort=260)
//	public  String getYlh() {
//		return ylh;
//	}
//	public  void setYlh(String ylh) {
//		this.ylh = ylh;
//	}
	
	@ExcelField(title ="项目类型",type=0,sort=220)
	public  String getXmlx() {
		return xmlx;
	}
	public  void setXmlx(String xmlx) {
		this.xmlx = xmlx;
	}
	
	@ExcelField(title ="操作标识",type=0,sort=230)
	public  String getCzbs() {
		return czbs;
	}
	public  void setCzbs(String czbs) {
		this.czbs = czbs;
	}
	
//	@ExcelField(title ="合同是否规定赔偿金额、时间和付款方式(是/否)",type=0,sort=290)
//	public  String getIsfh() {
//		return isfh;
//	}
//	public  void setIsfh(String isfh) {
//		this.isfh = isfh;
//	}
//	
//	@ExcelField(title ="索赔进展情况",type=0,sort=300)
//	public  String getSpqk() {
//		return spqk;
//	}
//	public  void setSpqk(String spqk) {
//		this.spqk = spqk;
//	}
//	
//	@ExcelField(title ="分析",type=0,sort=310)
//	public  String getFx() {
//		return fx;
//	}
//	public  void setFx(String fx) {
//		this.fx = fx;
//	}
//	
//	@ExcelField(title ="采购员",type=0,sort=320)
//	public  String getCgy() {
//		return cgy;
//	}
//	public  void setCgy(String cgy) {
//		this.cgy = cgy;
//	}

}
   