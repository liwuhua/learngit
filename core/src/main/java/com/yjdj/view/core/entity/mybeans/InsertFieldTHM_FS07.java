package com.yjdj.view.core.entity.mybeans;

import com.yjdj.view.core.util.ExcelField;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author WANGKAI
 * 
 *
 */
public class InsertFieldTHM_FS07 {

	private String id;
	private String bukrs;
	private String gs_txt;
	private String kunnr;
	private String kh_txt;
	private String hkont;
	private String dqxssr;
	private String qcye;
	private String xj_s;
	private String zyywsr;
	private String qtywsr;
	private String xxsj;
	private String qt;
	private String xj_h;
	private String xianjin;
	private String yhcd;
	private String sycd;
	private String yunxin;
	private String dizhang;
	private String qmye;
	private String ys_sixmonth;
	private String ys_sixone;
	private String ys_onetwo;
	private String ys_twothree;
	private String ys_threefour;
	private String ys_fourfive;
	private String ys_fiveyear;
	private String zb_sixmonth;
	private String zb_sixone;
	private String zb_onetwo;
	private String zb_twothree;
	private String zb_threefour;
	private String zb_fourfive;
	private String zb_fiveyear;
	private String zb_xiaoji;

	@ExcelField(title = "序号",type=0,sort=10)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@ExcelField(title = "公司代码",type=0,sort=20)
	public String getBukrs() {
		return bukrs;
	}

	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}
	@ExcelField(title = "公司名称",type=0,sort=30)
	public String getGs_txt() {
		return gs_txt;
	}

	public void setGs_txt(String gs_txt) {
		this.gs_txt = gs_txt;
	}
	@ExcelField(title = "客户编码",type=0,sort=40)
	public String getKunnr() {
		return kunnr;
	}

	public void setKunnr(String kunnr) {
		this.kunnr = kunnr;
	}
	@ExcelField(title = "客户名称",type=0,sort=50)
	public String getKh_txt() {
		return kh_txt;
	}

	public void setKh_txt(String kh_txt) {
		this.kh_txt = kh_txt;
	}
	@ExcelField(title = "统驭科目",type=0,sort=60)
	public String getHkont() {
		return hkont;
	}

	public void setHkont(String hkont) {
		this.hkont = hkont;
	}
	@ExcelField(title = "当期销售收入（不含税）",type=0,sort=70)
	public String getDqxssr() {
		return dqxssr;
	}

	public void setDqxssr(String dqxssr) {
		this.dqxssr = dqxssr;
	}
	@ExcelField(title = "期初余额",type=0,sort=80)
	public String getQcye() {
		return qcye;
	}

	public void setQcye(String qcye) {
		this.qcye = qcye;
	}
	@ExcelField(title = "小计",type=0,sort=90)
	public String getXj_s() {
		return xj_s;
	}

	public void setXj_s(String xj_s) {
		this.xj_s = xj_s;
	}
	@ExcelField(title = "主营业务收入",type=0,sort=100)
	public String getZyywsr() {
		return zyywsr;
	}

	public void setZyywsr(String zyywsr) {
		this.zyywsr = zyywsr;
	}
	@ExcelField(title = "其他业务收入",type=0,sort=110)
	public String getQtywsr() {
		return qtywsr;
	}

	public void setQtywsr(String qtywsr) {
		this.qtywsr = qtywsr;
	}
	@ExcelField(title = "销项税金",type=0,sort=120)
	public String getXxsj() {
		return xxsj;
	}

	public void setXxsj(String xxsj) {
		this.xxsj = xxsj;
	}
	@ExcelField(title = "其他",type=0,sort=130)
	public String getQt() {
		return qt;
	}

	public void setQt(String qt) {
		this.qt = qt;
	}
	@ExcelField(title = "小计",type=0,sort=140)
	public String getXj_h() {
		return xj_h;
	}

	public void setXj_h(String xj_h) {
		this.xj_h = xj_h;
	}
	@ExcelField(title = "现金",type=0,sort=150)
	public String getXianjin() {
		return xianjin;
	}

	public void setXianjin(String xianjin) {
		this.xianjin = xianjin;
	}
	@ExcelField(title = "银行承兑",type=0,sort=160)
	public String getYhcd() {
		return yhcd;
	}

	public void setYhcd(String yhcd) {
		this.yhcd = yhcd;
	}
	@ExcelField(title = "商业承兑",type=0,sort=170)
	public String getSycd() {
		return sycd;
	}

	public void setSycd(String sycd) {
		this.sycd = sycd;
	}
	@ExcelField(title = "云信",type=0,sort=180)
	public String getYunxin() {
		return yunxin;
	}

	public void setYunxin(String yunxin) {
		this.yunxin = yunxin;
	}
	@ExcelField(title = "抵账",type=0,sort=190)
	public String getDizhang() {
		return dizhang;
	}

	public void setDizhang(String dizhang) {
		this.dizhang = dizhang;
	}
	@ExcelField(title = "期末余额",type=0,sort=200)
	public String getQmye() {
		return qmye;
	}

	public void setQmye(String qmye) {
		this.qmye = qmye;
	}
	@ExcelField(title = "6个月以内",type=0,sort=210)
	public String getYs_sixmonth() {
		return ys_sixmonth;
	}

	public void setYs_sixmonth(String ys_sixmonth) {
		this.ys_sixmonth = ys_sixmonth;
	}
	@ExcelField(title = "6个月—1年",type=0,sort=220)
	public String getYs_sixone() {
		return ys_sixone;
	}

	public void setYs_sixone(String ys_sixone) {
		this.ys_sixone = ys_sixone;
	}
	@ExcelField(title = "1—2年",type=0,sort=230)
	public String getYs_onetwo() {
		return ys_onetwo;
	}

	public void setYs_onetwo(String ys_onetwo) {
		this.ys_onetwo = ys_onetwo;
	}
	@ExcelField(title = "2—3年",type=0,sort=240)
	public String getYs_twothree() {
		return ys_twothree;
	}

	public void setYs_twothree(String ys_twothree) {
		this.ys_twothree = ys_twothree;
	}
	@ExcelField(title = "3—4年",type=0,sort=250)
	public String getYs_threefour() {
		return ys_threefour;
	}

	public void setYs_threefour(String ys_threefour) {
		this.ys_threefour = ys_threefour;
	}
	@ExcelField(title = "4—5年",type=0,sort=260)
	public String getYs_fourfive() {
		return ys_fourfive;
	}

	public void setYs_fourfive(String ys_fourfive) {
		this.ys_fourfive = ys_fourfive;
	}
	@ExcelField(title = "5年以上",type=0,sort=270)
	public String getYs_fiveyear() {
		return ys_fiveyear;
	}

	public void setYs_fiveyear(String ys_fiveyear) {
		this.ys_fiveyear = ys_fiveyear;
	}
	@ExcelField(title = "6个月以内",type=0,sort=280)
	public String getZb_sixmonth() {
		return zb_sixmonth;
	}

	public void setZb_sixmonth(String zb_sixmonth) {
		this.zb_sixmonth = zb_sixmonth;
	}
	@ExcelField(title = "6个月—1年",type=0,sort=290)
	public String getZb_sixone() {
		return zb_sixone;
	}

	public void setZb_sixone(String zb_sixone) {
		this.zb_sixone = zb_sixone;
	}
	@ExcelField(title = "1—2年",type=0,sort=300)
	public String getZb_onetwo() {
		return zb_onetwo;
	}

	public void setZb_onetwo(String zb_onetwo) {
		this.zb_onetwo = zb_onetwo;
	}
	@ExcelField(title = "2—3年",type=0,sort=310)
	public String getZb_twothree() {
		return zb_twothree;
	}

	public void setZb_twothree(String zb_twothree) {
		this.zb_twothree = zb_twothree;
	}
	@ExcelField(title = "3—4年",type=0,sort=320)
	public String getZb_threefour() {
		return zb_threefour;
	}

	public void setZb_threefour(String zb_threefour) {
		this.zb_threefour = zb_threefour;
	}
	@ExcelField(title = "4—5年",type=0,sort=330)
	public String getZb_fourfive() {
		return zb_fourfive;
	}

	public void setZb_fourfive(String zb_fourfive) {
		this.zb_fourfive = zb_fourfive;
	}
	@ExcelField(title = "5年以上",type=0,sort=340)
	public String getZb_fiveyear() {
		return zb_fiveyear;
	}

	public void setZb_fiveyear(String zb_fiveyear) {
		this.zb_fiveyear = zb_fiveyear;
	}
	@ExcelField(title = "小计",type=0,sort=350)
	public String getZb_xiaoji() {
		return zb_xiaoji;
	}

	public void setZb_xiaoji(String zb_xiaoji) {
		this.zb_xiaoji = zb_xiaoji;
	}
}
