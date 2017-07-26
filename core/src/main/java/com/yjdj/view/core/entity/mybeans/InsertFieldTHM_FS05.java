package com.yjdj.view.core.entity.mybeans;
import com.yjdj.view.core.util.ExcelField;


public class InsertFieldTHM_FS05 {

											
	private String project ;//项目
	private String lineNum ; //行次
	private String yongjiCompany ; //永济公司
	private String xiupeiCompany ; //修配公司
	private String xianjieliCompany ; //西安捷力
	private String xianyongdianCompany ; //西安永电
	private String xianjinfengCompany ; //西安永电金风
	private String tuokexunCompany ; //托克逊公司
	private String yinduCompany ;  //印度公司
	private String nanfeiCompany ; //南非公司
	private String RBUKRS; // 公司代码
	private String TOTAL; //导入值
	private String FISCPER; //导入年月
	private String CREATE_TIME; //首次导入时间
	private String LAST_UPDATE_TIME; //最近更新时间
	/**
	 * `RBUKRS` varchar(30) DEFAULT NULL,
`ITEM_COLUMN` varchar(10) NOT NULL,
`TOTAL` varchar(20) DEFAULT NULL,
`FISCPER` varchar(10) NOT NULL,
`CREATE_TIME` datetime NOT NULL,
`CREATE_BY` varchar(50) NOT NULL,
`LAST_UPDATE_TIME` datetime NOT NULL,
`LAST_UPDATE_BY` varchar(50) NOT NULL
	 */
	
	@ExcelField(title = "项目",type=0,sort=10)
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	@ExcelField(title = "行次",type=0,sort=20)
	public String getLineNum() {
		return lineNum;
	}
	public void setLineNum(String lineNum) {
		this.lineNum = lineNum;
	}
	@ExcelField(title = "中车永济电机有限公司",type=0,sort=30)
	public String getYongjiCompany() {
		return yongjiCompany;
	}
	public void setYongjiCompany(String yongjiCompany) {
		this.yongjiCompany = yongjiCompany;
	}
	@ExcelField(title = "西安中车永电电气有限公司",type=0,sort=40)
	public String getXianyongdianCompany() {
		return xianyongdianCompany;
	}
	public void setXianyongdianCompany(String xianyongdianCompany) {
		this.xianyongdianCompany = xianyongdianCompany;
	}
	@ExcelField(title = "西安中车永电捷力风能有限公司",type=0,sort=50)
	public String getXianjieliCompany() {
		return xianjieliCompany;
	}
	public void setXianjieliCompany(String xianjieliCompany) {
		this.xianjieliCompany = xianjieliCompany;
	}
	@ExcelField(title = "永济中车电机电器修配有限公司",type=0,sort=60)
	public String getXiupeiCompany() {
		return xiupeiCompany;
	}
	public void setXiupeiCompany(String xiupeiCompany) {
		this.xiupeiCompany = xiupeiCompany;
	}
	@ExcelField(title = "西安中车永电金风科技有限公司",type=0,sort=70)
	public String getXianjinfengCompany() {
		return xianjinfengCompany;
	}
	public void setXianjinfengCompany(String xianjinfengCompany) {
		this.xianjinfengCompany = xianjinfengCompany;
	}
	@ExcelField(title = "托克逊中车永电能源装备有限公司",type=0,sort=80)
	public String getTuokexunCompany() {
		return tuokexunCompany;
	}
	public void setTuokexunCompany(String tuokexunCompany) {
		this.tuokexunCompany = tuokexunCompany;
	}
	@ExcelField(title = "印度公司",type=0,sort=90)
	public String getYinduCompany() {
		return yinduCompany;
	}
	public void setYinduCompany(String yinduCompany) {
		this.yinduCompany = yinduCompany;
	}
	@ExcelField(title = "南非公司",type=0,sort=100)
	public String getNanfeiCompany() {
		return nanfeiCompany;
	}
	public void setNanfeiCompany(String nanfeiCompany) {
		this.nanfeiCompany = nanfeiCompany;
	}
	
	public String getRBUKRS() {
		return RBUKRS;
	}
	public void setRBUKRS(String rBUKRS) {
		RBUKRS = rBUKRS;
	}
	public String getTOTAL() {
		return TOTAL;
	}
	public void setTOTAL(String tOTAL) {
		TOTAL = tOTAL;
	}
	public String getFISCPER() {
		return FISCPER;
	}
	public void setFISCPER(String fISCPER) {
		FISCPER = fISCPER;
	}
	public String getCREATE_TIME() {
		return CREATE_TIME;
	}
	public void setCREATE_TIME(String cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}
	public String getLAST_UPDATE_TIME() {
		return LAST_UPDATE_TIME;
	}
	public void setLAST_UPDATE_TIME(String lAST_UPDATE_TIME) {
		LAST_UPDATE_TIME = lAST_UPDATE_TIME;
	}
	
	
}
