package com.yjdj.view.core.entity.mybeans;

import com.yjdj.view.core.util.ExcelField;

/**
 * Created by zhuxuan on 2016/12/19.
 */
public class InsertFieldTHM_FS19 {
    //从excel读数据所需的变量
    private String lanmu;//栏目
    private String yuanbi_yj;//原币永济
    private String benweibi_yj;//本位币永济
    private String yuanbi_xp;//原币修配
    private String benweibi_xp;//本位币修配
    private String yuanbi_jl;//原币捷力、贵阳
    private String benweibi_jl;//本位币捷力、贵阳
    private String yuanbi_xayd;//原币西安永电
    private String benweibi_xayd;//本位币西安永电
    private String yuanbi_jf;//原币金风、新疆
    private String benweibi_jf;//本位币金风、新疆
    private String yuanbi_tkx;//原币托克逊
    private String benweibi_tkx;//本位币托克逊
    private String yuanbi_nf;//原币南非
    private String benweibi_nf;//本位币南非
    private String yuanbi_yd;//原币印度
    private String benweibi_yd;//本位币印度
    private String yuanbi_total;//原币合计
    private String benweibi_total;//本位币合计
    //向mysql导入数据所需的变量
    private String type;//行号，栏目类型
    private String rbukrs;//工厂代码
    private String curtype;//原币or本位币
    private String balance;//值，对应mysql主表THM_FS19中的balance
    private String fiscper;//期号,格式2016012

    @ExcelField(title = "栏目",type=0,sort=10)
    public String getLanmu() {
        return lanmu;
    }
    public void setLanmu(String lanmu) {
        this.lanmu = lanmu;
    }
    @ExcelField(title = "原币",type=0,sort=20)
    public String getYuanbi_yj() {
        return yuanbi_yj;
    }
    public void setYuanbi_yj(String yuanbi_yj) {
        this.yuanbi_yj = yuanbi_yj;
    }
    @ExcelField(title = "本位币",type=0,sort=30)
    public String getBenweibi_yj() {
        return benweibi_yj;
    }
    public void setBenweibi_yj(String benweibi_yj) {
        this.benweibi_yj = benweibi_yj;
    }
    @ExcelField(title = "原币",type=0,sort=40)
    public String getYuanbi_xp() {
        return yuanbi_xp;
    }
    public void setYuanbi_xp(String yuanbi_xp) {
        this.yuanbi_xp = yuanbi_xp;
    }
    @ExcelField(title = "本位币",type=0,sort=50)
    public String getBenweibi_xp() {
        return benweibi_xp;
    }
    public void setBenweibi_xp(String benweibi_xp) {
        this.benweibi_xp = benweibi_xp;
    }
    @ExcelField(title = "原币",type=0,sort=60)
    public String getYuanbi_jl() {
        return yuanbi_jl;
    }
    public void setYuanbi_jl(String yuanbi_jl) {
        this.yuanbi_jl = yuanbi_jl;
    }
    @ExcelField(title = "本位币",type=0,sort=70)
    public String getBenweibi_jl() {
        return benweibi_jl;
    }
    public void setBenweibi_jl(String benweibi_jl) {
        this.benweibi_jl = benweibi_jl;
    }
    @ExcelField(title = "原币",type=0,sort=80)
    public String getYuanbi_xayd() {
        return yuanbi_xayd;
    }
    public void setYuanbi_xayd(String yuanbi_xayd) {
        this.yuanbi_xayd = yuanbi_xayd;
    }
    @ExcelField(title = "本位币",type=0,sort=90)
    public String getBenweibi_xayd() {
        return benweibi_xayd;
    }
    public void setBenweibi_xayd(String benweibi_xayd) {
        this.benweibi_xayd = benweibi_xayd;
    }
    @ExcelField(title = "原币",type=0,sort=100)
    public String getYuanbi_jf() {
        return yuanbi_jf;
    }
    public void setYuanbi_jf(String yuanbi_jf) {
        this.yuanbi_jf = yuanbi_jf;
    }
    @ExcelField(title = "本位币",type=0,sort=110)
    public String getBenweibi_jf() {
        return benweibi_jf;
    }
    public void setBenweibi_jf(String benweibi_jf) {
        this.benweibi_jf = benweibi_jf;
    }
    @ExcelField(title = "原币",type=0,sort=120)
    public String getYuanbi_tkx() {
        return yuanbi_tkx;
    }
    public void setYuanbi_tkx(String yuanbi_tkx) {
        this.yuanbi_tkx = yuanbi_tkx;
    }
    @ExcelField(title = "本位币",type=0,sort=130)
    public String getBenweibi_tkx() {
        return benweibi_tkx;
    }
    public void setBenweibi_tkx(String benweibi_tkx) {
        this.benweibi_tkx = benweibi_tkx;
    }
    @ExcelField(title = "原币",type=0,sort=140)
    public String getYuanbi_nf() {
        return yuanbi_nf;
    }
    public void setYuanbi_nf(String yuanbi_nf) {
        this.yuanbi_nf = yuanbi_nf;
    }
    @ExcelField(title = "本位币",type=0,sort=150)
    public String getBenweibi_nf() {
        return benweibi_nf;
    }
    public void setBenweibi_nf(String benweibi_nf) {
        this.benweibi_nf = benweibi_nf;
    }
    @ExcelField(title = "原币",type=0,sort=160)
    public String getYuanbi_yd() {
        return yuanbi_yd;
    }
    public void setYuanbi_yd(String yuanbi_yd) {
        this.yuanbi_yd = yuanbi_yd;
    }
    @ExcelField(title = "本位币",type=0,sort=170)
    public String getBenweibi_yd() {
        return benweibi_yd;
    }
    public void setBenweibi_yd(String benweibi_yd) {
        this.benweibi_yd = benweibi_yd;
    }
    @ExcelField(title = "原币",type=0,sort=180)
    public String getYuanbi_total() {
        return yuanbi_total;
    }
    public void setYuanbi_total(String yuanbi_total) {
        this.yuanbi_total = yuanbi_total;
    }
    @ExcelField(title = "本位币",type=0,sort=190)
    public String getBenweibi_total() {
        return benweibi_total;
    }
    public void setBenweibi_total(String benweibi_total) {
        this.benweibi_total = benweibi_total;
    }

    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getRbukrs(){
        return this.rbukrs;
    }
    public void setRbukrs(String rbukrs){
        this.rbukrs = rbukrs;
    }
    public String getCurtype(){
        return this.curtype;
    }
    public void setCurtype(String curtype){
        this.curtype = curtype;
    }
    public String getBalance(){
        return this.balance;
    }
    public void setBalance(String balance){
        this.balance = balance;
    }
    public String getFiscper(){
        return this.fiscper;
    }
    public void setFiscper(String fiscper){
        this.fiscper = fiscper;
    }
}
