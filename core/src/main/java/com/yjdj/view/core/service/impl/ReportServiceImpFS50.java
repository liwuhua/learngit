package com.yjdj.view.core.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjdj.view.core.entity.mybeans.InsertFieldTHM_FS50;
import com.yjdj.view.core.entity.mybeans.QueryPojo;
import com.yjdj.view.core.entity.mybeans.THM_FS50;
import com.yjdj.view.core.mapper.IReportMapperFS50;
import com.yjdj.view.core.mapper.IReportMapperKPI2227;
import com.yjdj.view.core.service.IReportServiceFS50;
import com.yjdj.view.core.util.ExcelExportUtil;
import com.yjdj.view.core.util.ExcelExportUtil2227;
import com.yjdj.view.core.util.ExcelImportUtil;
import com.yjdj.view.core.util.FileUtils;

/**
 * Created by sunwan on 2017/1/3.
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS50 implements IReportServiceFS50{
    @Autowired
    private IReportMapperFS50 iReportMapperFS50;
    @Autowired
    private IReportMapperKPI2227 iReportMapperKPI2227;
    private ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> listMap = new HashMap<String, Object>();
    Map<String, Object> errorsMap = new HashMap<String, Object>();
    int i = 1;
    int j = 1;
    
    //列名
    private String xh      = "序号";
    private String month   ="月份";
    private String xxbh    = "信息编号";
    private String lbbh    = "质量损失类别编号";
    private String lbdm    = "质量损失类别代码";
    private String yydm    = "质量损失原因代码";
    private String psjd    = "配属局段";
    private String cpsmjd  = "产品寿命阶段";
    private String jxdd    = "检修地点";
    private String fxsj    = "发现时间";
    private String cpxh    = "产品型号";
    private String cpbh    = "产品编号";
    private String amount  = "数量";
    private String xzccrq  = "新造出厂日期";
    private String zjjxccrq= "最近检修出厂日期";
    private String jcbh    = "机车编号";
    private String zlc     = "总运行里程";
    private String jxhlc   = "检修后里程";
    private String gzbw    = "故障部位";
    private String gzms    = "故障描述";
    private String cljg    = "处理结果";
    private String clff    = "处理方法";
    private String gzwcrq  = "现场故障处理完成日期";
    private String sglb    = "事故类别";
    private String yzd     = "严重度";
    private String zrcp    = "责任初判";
    private String yyfx    = "原因分析";
    private String zgcs    = "整改措施";
    private String bgbh    = "报告编号";
    private String zrdw    ="责任单位";
    private String ss      = "损失费用合计";
    private String clfy    = "材料费用";
    private String ysfy    = "运输费用";
    private String rgfy    = "人工差旅费用";
    private String qtfy    = "其他费用";
    private String pc      = "赔偿";
    private String jnw     = "境内/境外";
    private String xzjx    = "新造/检修";
    private String lifnr   = "供应商";
    private String bz      = "备注";
    private String cpfl    ="产品分类";
    private String czbz    = "操作标识";
    private String gzlx	   ="故障类型";
    private String sf      ="合同是否规定赔偿金额、时间和付款方式（是/否)";
    private String spqk    ="索赔进展情况";
    private String fx      ="分析";
    private String cgy     ="采购员";
    
   
    @Override
    public Object selectData_01(QueryPojo queryPojo) throws JsonProcessingException,IOException,DataAccessException{
        int flag = 0;//查询flag为0，导出flag为1
        boolean isExport = queryPojo.isExport();
        if (isExport) {
            flag = 1;
        }
        List<String> headerList = new ArrayList<>();
        headerList.add(month);
//        headerList.add(xh);
        headerList.add(xxbh);
        headerList.add(psjd);
        headerList.add(cpsmjd);
        headerList.add(jxdd);
        headerList.add(fxsj);
        headerList.add(cpxh);
        headerList.add(cpbh);
        headerList.add(amount);
        headerList.add(xzccrq);
        headerList.add(zjjxccrq);
        headerList.add(jcbh);
        headerList.add(zlc);
        headerList.add(jxhlc);
        headerList.add(gzbw);
        headerList.add(gzms);
        headerList.add(cljg);
        headerList.add(clff);
        headerList.add(gzwcrq);
        headerList.add(sglb);
        headerList.add(yzd);
        headerList.add(zrcp);
        headerList.add(jnw);
        headerList.add(xzjx);
        headerList.add(lifnr);
        headerList.add(bz);
		/**
		 * 	
		 * month,xxbh,psjd,cpsmjd,jxdd,fxsj,cpxh,cpbh,amount,xzccrq,zjjxccrq,jcbh,zlc,jxhlc,gzbw,
		 * gzms,cljg,clff,gzwcrq,sglb,yzd,zrcp,jnw,xzjx,lifnr,bz
		 */
        List<THM_FS50> list_01=iReportMapperFS50.selectData_01(
                queryPojo.getXxbh(),
                queryPojo.getPsjd(),
                queryPojo.getCpsmjd(),
                queryPojo.getJxdd(),
                queryPojo.getStartTime(),
                queryPojo.getEndTime(),
                queryPojo.getStartYearMonth(),  
                queryPojo.getEndYearMonth(),
                queryPojo.getCpxh(),
                queryPojo.getCpbh(),
                queryPojo.getJcbh(),
                queryPojo.getGzbw(),
                queryPojo.getSglb(),
                queryPojo.getYzd(),
                queryPojo.getJnw(),
                queryPojo.getXzjx(),
                queryPojo.getLifnr(),
                queryPojo.getStartitem(),
                queryPojo.getPageitem(),
                flag
        );
        if (!isExport) {
            String resultJson_01 = mapper.writeValueAsString(list_01);
            return resultJson_01;
        } else {
            List<List<Object>> resultDataRowList = export_01(list_01);
            String secondTitle = null;
            ExcelExportUtil ee = new ExcelExportUtil("厂外铁路产品质量信息表-通用", secondTitle, headerList);
            List<List<Object>> dataList = Lists.newArrayList();
            for (int j = 0; j < resultDataRowList.size(); j++) {
                dataList.add(resultDataRowList.get(j));
            }
            for (int i = 0; i < resultDataRowList.size(); i++) {
                Row row = ee.addRow();
                for (int j = 0; j < dataList.get(i).size(); j++) {
//                    ee.addCell(row, j, dataList.get(i).get(j));
                    ee.addCell(row, row.getRowNum(), j, dataList.get(i).get(j), 2, true, String.class);
                }
            }
            return ee;
        }
    }
    @Override
    public Object selectData_02(QueryPojo queryPojo) throws JsonProcessingException,IOException,DataAccessException{
        int flag = 0;//查询flag为0，导出flag为1
        boolean isExport = queryPojo.isExport();
        if (isExport) {
            flag = 1;
        }
        List<String> headerList = new ArrayList<>();
        headerList.add(month);
//        headerList.add(xh);
        headerList.add(xxbh);
        headerList.add(lbbh);
        headerList.add("质量损失类别");
        headerList.add("质量损失原因");
        headerList.add(psjd);
        headerList.add(cpsmjd);
        headerList.add(jxdd);
        headerList.add(fxsj);
        headerList.add(cpxh);
        headerList.add(cpbh);
        headerList.add(amount);
        headerList.add(xzccrq);
        headerList.add(zjjxccrq);
        headerList.add(jcbh);
        headerList.add(zlc);
        headerList.add(jxhlc);
        headerList.add(gzbw);
        headerList.add(gzms);
        headerList.add(cljg);
        headerList.add(clff);
        headerList.add(gzwcrq);
        headerList.add(sglb);
        headerList.add(yzd);
        headerList.add(zrcp);
        headerList.add(yyfx);
        headerList.add(zgcs);
        headerList.add(bgbh);
        headerList.add(ss);
        
        //新增
        headerList.add(clfy);
        headerList.add(ysfy);
        headerList.add(rgfy);
        headerList.add(qtfy);

        headerList.add(pc);
        headerList.add(jnw);
        headerList.add(xzjx);
        headerList.add(lifnr);
        headerList.add(bz);


        List<THM_FS50> list_02=iReportMapperFS50.selectData_02(
                queryPojo.getXxbh(),
                queryPojo.getPsjd(),
                queryPojo.getCpsmjd(),
                queryPojo.getJxdd(),
                queryPojo.getStartTime(),
                queryPojo.getEndTime(),
                queryPojo.getStartYearMonth(),  
                queryPojo.getEndYearMonth(),
                queryPojo.getCpxh(),
                queryPojo.getCpbh(),
                queryPojo.getJcbh(),
                queryPojo.getGzbw(),
                queryPojo.getSglb(),
                queryPojo.getYzd(),
                queryPojo.getJnw(),
                queryPojo.getXzjx(),
                queryPojo.getLifnr(),
                queryPojo.getLbbh(),
                queryPojo.getBgbh(),
                queryPojo.getStartitem(),
                queryPojo.getPageitem(),
                flag
        );


        if (!isExport) {
            String resultJson_02 = mapper.writeValueAsString(list_02);
            return resultJson_02;
        } else {
            List<List<Object>> resultDataRowList = export_02(list_02);
            String secondTitle = null;
            ExcelExportUtil ee = new ExcelExportUtil("厂外铁路产品质量信息表-国铁服务中心", secondTitle, headerList);
            List<List<Object>> dataList = Lists.newArrayList();
            for (int j = 0; j < resultDataRowList.size(); j++) {
                dataList.add(resultDataRowList.get(j));
            }
            for (int i = 0; i < resultDataRowList.size(); i++) {
                Row row = ee.addRow();
                for (int j = 0; j < dataList.get(i).size(); j++) {
//                    ee.addCell(row, j, dataList.get(i).get(j));
                    ee.addCell(row, row.getRowNum(), j, dataList.get(i).get(j), 2, true, String.class);
                }
            }
            return ee;
        }

    }
    @Override
    public Object selectData_03(QueryPojo queryPojo) throws JsonProcessingException,IOException,DataAccessException{
        int flag = 0;//查询flag为0，导出flag为1
        boolean isExport = queryPojo.isExport();
        if (isExport) {
            flag = 1;
        }
        List<String> headerList = new ArrayList<>();
        headerList.add(month);
//        headerList.add(xh);
        headerList.add(xxbh);
        headerList.add(lbbh);
        /*     headerList.add(lbdm);
        headerList.add(yydm);*/
        headerList.add("质量损失类别");
        headerList.add("质量损失原因");
        headerList.add(psjd);
        headerList.add(cpsmjd);
        headerList.add(jxdd);
        headerList.add(fxsj);
        headerList.add(cpxh);
        headerList.add(cpbh);
        headerList.add(amount);
        headerList.add(xzccrq);
        headerList.add(zjjxccrq);
        headerList.add(jcbh);
        headerList.add(zlc);
        headerList.add(jxhlc);
        headerList.add(gzbw);
        headerList.add(gzms);
        headerList.add(cljg);
        headerList.add(clff);
        headerList.add(gzwcrq);
        headerList.add(sglb);
        headerList.add(yzd);
        headerList.add(zrcp);
        headerList.add(ss);
        
        //新增
        headerList.add(clfy);
        headerList.add(ysfy);
        headerList.add(rgfy);
        headerList.add(qtfy);
        
        headerList.add(pc);
        headerList.add(jnw);
        headerList.add(xzjx);
        headerList.add(lifnr);
        headerList.add(bz);
        List<THM_FS50> list_03=iReportMapperFS50.selectData_03(
                queryPojo.getXxbh(),
                queryPojo.getPsjd(),
                queryPojo.getCpsmjd(),
                queryPojo.getJxdd(),
                queryPojo.getStartTime(),
                queryPojo.getEndTime(),
                queryPojo.getStartYearMonth(),  
                queryPojo.getEndYearMonth(),
                queryPojo.getCpxh(),
                queryPojo.getCpbh(),
                queryPojo.getJcbh(),
                queryPojo.getGzbw(),
                queryPojo.getSglb(),
                queryPojo.getYzd(),
                queryPojo.getJnw(),
                queryPojo.getXzjx(),
                queryPojo.getLifnr(),
                queryPojo.getLbbh(),
                queryPojo.getStartitem(),
                queryPojo.getPageitem(),
                flag
        );

        if (!isExport) {
            String resultJson_03 = mapper.writeValueAsString(list_03);
            return resultJson_03;
        } else {
            List<List<Object>> resultDataRowList = export_03(list_03);
            String secondTitle = null;
            ExcelExportUtil ee = new ExcelExportUtil("厂外铁路产品质量信息表-质保部", secondTitle, headerList);
            List<List<Object>> dataList = Lists.newArrayList();
            for (int j = 0; j < resultDataRowList.size(); j++) {
                dataList.add(resultDataRowList.get(j));
            }
            for (int i = 0; i < resultDataRowList.size(); i++) {
                Row row = ee.addRow();
                for (int j = 0; j < dataList.get(i).size(); j++) {
//                    ee.addCell(row, j, dataList.get(i).get(j));
                    ee.addCell(row, row.getRowNum(), j, dataList.get(i).get(j), 2, true, String.class);
                }
            }
            return ee;
        }

    }

    @Override
    public Object selectData_04(QueryPojo queryPojo) throws JsonProcessingException,IOException,DataAccessException{
        int flag = 0;//查询flag为0，导出flag为1
        boolean isExport = queryPojo.isExport();
        if (isExport) {
            flag = 1;
        }
        List<String> headerList = new ArrayList<>();
        headerList.add(month);
//        headerList.add(xh);
        headerList.add(month);
        headerList.add(xxbh);
        headerList.add(lbbh);
   /*     headerList.add(lbdm);
        headerList.add(yydm);*/
        headerList.add("质量损失类别");
        headerList.add("质量损失原因");
        headerList.add(psjd);
        headerList.add(cpsmjd);
        headerList.add(jxdd);
        headerList.add(fxsj);
        headerList.add(cpxh);
        headerList.add(cpbh);
        headerList.add(amount);
        headerList.add(xzccrq);
        headerList.add(zjjxccrq);
        headerList.add(jcbh);
        headerList.add(zlc);
        headerList.add(jxhlc);
        headerList.add(gzbw);
        headerList.add(gzms);
        headerList.add(cljg);
        headerList.add(clff);
        headerList.add(gzwcrq);
        headerList.add(sglb);
        headerList.add(yzd);
        headerList.add(zrcp);
        headerList.add(yyfx);
        headerList.add(zgcs);
        headerList.add(bgbh);
        headerList.add(zrdw);
        headerList.add(ss);
        
        //新增
        headerList.add(clfy);
        headerList.add(ysfy);
        headerList.add(rgfy);
        headerList.add(qtfy);
        
        headerList.add(pc);
	    headerList.add(jnw);
	   	headerList.add(xzjx);
		headerList.add(cpfl);
	    headerList.add(gzlx);
	    headerList.add(sf);
	    headerList.add(spqk);
	    headerList.add(fx);
	    headerList.add(cgy);
	    headerList.add(lifnr);
		headerList.add(bz);
//        headerList.add(cnw);
//        headerList.add(xmlb);


        List<THM_FS50> list_04=iReportMapperFS50.selectData_04(
                queryPojo.getXxbh(),
                queryPojo.getPsjd(),
                queryPojo.getCpsmjd(),
                queryPojo.getJxdd(),
                queryPojo.getStartTime(),
                queryPojo.getEndTime(),
                queryPojo.getStartYearMonth(),  
                queryPojo.getEndYearMonth(),
                queryPojo.getCpxh(),
                queryPojo.getCpbh(),
                queryPojo.getJcbh(),
                queryPojo.getGzbw(),
                queryPojo.getSglb(),
                queryPojo.getYzd(),
                queryPojo.getJnw(),
                queryPojo.getXzjx(),
                queryPojo.getLifnr(),
                queryPojo.getLbbh(),
                queryPojo.getBgbh(),
                queryPojo.getStartitem(),
                queryPojo.getPageitem(),
                flag
        );


        if (!isExport) {
            String resultJson_04 = mapper.writeValueAsString(list_04);
            return resultJson_04;
        } else {
            List<List<Object>> resultDataRowList = export_04(list_04);
            String secondTitle = null;
            ExcelExportUtil ee = new ExcelExportUtil("厂外铁路产品质量信息表-国铁服务中心", secondTitle, headerList);
            List<List<Object>> dataList = Lists.newArrayList();
            for (int j = 0; j < resultDataRowList.size(); j++) {
                dataList.add(resultDataRowList.get(j));
            }
            for (int i = 0; i < resultDataRowList.size(); i++) {
                Row row = ee.addRow();
                for (int j = 0; j < dataList.get(i).size(); j++) {
//                    ee.addCell(row, j, dataList.get(i).get(j));
                    ee.addCell(row, row.getRowNum(), j, dataList.get(i).get(j), 2, true, String.class);
                }
            }
            return ee;
        }

    }

    //导出数据通用
    private List<List<Object>> export_01(List<THM_FS50> list_01) throws IOException {
        // TODO Auto-generated method stub
        //
        int num = 1;
        List<List<Object>> dataRowList = Lists.newArrayList();
        List<Object> list_ex = new ArrayList<Object>();
        for (THM_FS50 value : list_01) {

            list_ex.add(num);  //序号
            list_ex.add(value.getXxbh()); //信息编号
            list_ex.add(value.getPsjd()); //配属局段
            list_ex.add(value.getCpsmjd());  //产品寿命阶段
            list_ex.add(value.getJxdd()); //检修地点
            list_ex.add(value.getFxsj());  //发现时间
            list_ex.add(value.getCpxh()); //产品型号
            list_ex.add(value.getCpbh());  //产品编号
            list_ex.add(value.getAmount()); //数量
            list_ex.add(value.getXzccrq()); //新造出厂日期
            list_ex.add(value.getZjjxccrq()); //最近检修出厂日期
            list_ex.add(value.getJcbh()); //机车编号
            list_ex.add(value.getZlc()); //总运行里程
            list_ex.add(value.getJxhlc()); //检修后里程
            list_ex.add(value.getGzbw()); //故障部位
            list_ex.add(value.getGzms()); //故障描述
            list_ex.add(value.getCljg()); //处理结果
            list_ex.add(value.getClff()); //处理方法
            list_ex.add(value.getGzwcrq()); //现场故障处理完成日期
            list_ex.add(value.getSglb()); //事故类别
            list_ex.add(value.getYzd()); //严重度
            list_ex.add(value.getZrcp()); //责任初判
            list_ex.add(value.getJnw()); //境内/境外
            list_ex.add(value.getXzjx()); //新造/检修
            list_ex.add(value.getLifnr()); //供应商
            list_ex.add(value.getBz()); //备注
            num++;
            dataRowList.add(list_ex);
            list_ex = new ArrayList<Object>();
        }

        return dataRowList;
    }

    //导出数据国铁服务中心
    private List<List<Object>> export_02(List<THM_FS50> list_02) throws IOException {
        // TODO Auto-generated method stub
        //
        int num = 1;
        List<List<Object>> dataRowList = Lists.newArrayList();
        List<Object> list_ex = new ArrayList<Object>();
        for (THM_FS50 value : list_02) {

            list_ex.add(num);  //序号
            list_ex.add(value.getXxbh()); //信息编号
            list_ex.add(value.getLbbh());  //质量损失类别编号
            list_ex.add(value.getLbdm()); //质量损失类别代码
            list_ex.add(value.getYydm());  //质量损失原因代码
            list_ex.add(value.getPsjd()); //配属局段
            list_ex.add(value.getCpsmjd());  //产品寿命阶段
            list_ex.add(value.getJxdd()); //检修地点
            list_ex.add(value.getFxsj());  //发现时间
            list_ex.add(value.getCpxh()); //产品型号
            list_ex.add(value.getCpbh());  //产品编号
            list_ex.add(value.getAmount()); //数量
            list_ex.add(value.getXzccrq()); //新造出厂日期
            list_ex.add(value.getZjjxccrq()); //最近检修出厂日期
            list_ex.add(value.getJcbh()); //机车编号
            list_ex.add(value.getZlc()); //总运行里程
            list_ex.add(value.getJxhlc()); //检修后里程
            list_ex.add(value.getGzbw()); //故障部位
            list_ex.add(value.getGzms()); //故障描述
            list_ex.add(value.getCljg()); //处理结果
            list_ex.add(value.getClff()); //处理方法
            list_ex.add(value.getGzwcrq()); //现场故障处理完成日期
            list_ex.add(value.getSglb()); //事故类别
            list_ex.add(value.getYzd()); //严重度
            list_ex.add(value.getZrcp()); //责任初判
            list_ex.add(value.getYyfx()); //原因分析
            list_ex.add(value.getZgcs()); //整改措施
            list_ex.add(value.getBgbh()); //报告编号
            list_ex.add(value.getSs()); //损失费用合计
            
            //新增字段
            list_ex.add(value.getClfy()); //材料费用
            list_ex.add(value.getYsfy()); //运输费用
            list_ex.add(value.getRgfy()); //人工差旅费用
            list_ex.add(value.getQtfy()); //其他费用
            
            
            list_ex.add(value.getPc()); //赔偿
            list_ex.add(value.getJnw()); //境内/境外
            list_ex.add(value.getXzjx()); //新造/检修
            list_ex.add(value.getLifnr()); //供应商
            list_ex.add(value.getBz()); //备注
            num++;
            dataRowList.add(list_ex);
            list_ex = new ArrayList<Object>();
        }

        return dataRowList;
    }

    //导出数据质保部
    private List<List<Object>> export_03(List<THM_FS50> list_03) throws IOException {
        // TODO Auto-generated method stub
        //
        int num = 1;
        List<List<Object>> dataRowList = Lists.newArrayList();
        List<Object> list_ex = new ArrayList<Object>();
        for (THM_FS50 value : list_03) {

            list_ex.add(num);  //序号
            list_ex.add(value.getXxbh()); //信息编号
            list_ex.add(value.getLbbh());  //质量损失类别编号
            list_ex.add(value.getLbdm()); //质量损失类别代码
            list_ex.add(value.getYydm());  //质量损失原因代码
            list_ex.add(value.getPsjd()); //配属局段
            list_ex.add(value.getCpsmjd());  //产品寿命阶段
            list_ex.add(value.getJxdd()); //检修地点
            list_ex.add(value.getFxsj());  //发现时间
            list_ex.add(value.getCpxh()); //产品型号
            list_ex.add(value.getCpbh());  //产品编号
            list_ex.add(value.getAmount()); //数量
            list_ex.add(value.getXzccrq()); //新造出厂日期
            list_ex.add(value.getZjjxccrq()); //最近检修出厂日期
            list_ex.add(value.getJcbh()); //机车编号
            list_ex.add(value.getZlc()); //总运行里程
            list_ex.add(value.getJxhlc()); //检修后里程
            list_ex.add(value.getGzbw()); //故障部位
            list_ex.add(value.getGzms()); //故障描述
            list_ex.add(value.getCljg()); //处理结果
            list_ex.add(value.getClff()); //处理方法
            list_ex.add(value.getGzwcrq()); //现场故障处理完成日期
            list_ex.add(value.getSglb()); //事故类别
            list_ex.add(value.getYzd()); //严重度
            list_ex.add(value.getZrcp()); //责任初判
            list_ex.add(value.getSs()); //损失费用合计
            
            //新增字段
            list_ex.add(value.getClfy()); //材料费用
            list_ex.add(value.getYsfy()); //运输费用
            list_ex.add(value.getRgfy()); //人工差旅费用
            list_ex.add(value.getQtfy()); //其他费用
            
            list_ex.add(value.getPc()); //赔偿
            list_ex.add(value.getJnw()); //境内/境外
            list_ex.add(value.getXzjx()); //新造/检修
            list_ex.add(value.getLifnr()); //供应商
            list_ex.add(value.getBz()); //备注
            num++;
            dataRowList.add(list_ex);
            list_ex = new ArrayList<Object>();
        }

        return dataRowList;
    }

    //导出数据国铁服务中心
    private List<List<Object>> export_04(List<THM_FS50> list_04) throws IOException {
        // TODO Auto-generated method stub
        //
        int num = 1;
        List<List<Object>> dataRowList = Lists.newArrayList();
        List<Object> list_ex = new ArrayList<Object>();
        for (THM_FS50 value : list_04) {
            list_ex.add(num+"");  //序号
            list_ex.add(value.getMonth());//月份
            list_ex.add(value.getXxbh()); //信息编号
            list_ex.add(value.getLbbh());  //质量损失类别编号
            list_ex.add(value.getLbdm()); //质量损失类别代码
            list_ex.add(value.getYydm());  //质量损失原因代码
            list_ex.add(value.getPsjd()); //配属局段
            list_ex.add(value.getCpsmjd());  //产品寿命阶段
            list_ex.add(value.getJxdd()); //检修地点
            list_ex.add(value.getFxsj());  //发现时间
            list_ex.add(value.getCpxh()); //产品型号
            list_ex.add(value.getCpbh());  //产品编号
            list_ex.add(value.getAmount()+""); //数量
            list_ex.add(value.getXzccrq()); //新造出厂日期
            list_ex.add(value.getZjjxccrq()); //最近检修出厂日期
            list_ex.add(value.getJcbh()); //机车编号
            list_ex.add(value.getZlc()); //总运行里程
            list_ex.add(value.getJxhlc()); //检修后里程
            list_ex.add(value.getGzbw()); //故障部位
            list_ex.add(value.getGzms()); //故障描述
            list_ex.add(value.getCljg()); //处理结果
            list_ex.add(value.getClff()); //处理方法
            list_ex.add(value.getGzwcrq()); //现场故障处理完成日期
            list_ex.add(value.getSglb()); //事故类别
            list_ex.add(value.getYzd()); //严重度
            list_ex.add(value.getZrcp()); //责任初判
            list_ex.add(value.getYyfx()); //原因分析
            list_ex.add(value.getZgcs()); //整改措施
            list_ex.add(value.getBgbh()); //报告编号
            list_ex.add(value.getZrdw()); //责任单位
            list_ex.add(value.getSs()); //损失费用合计
            
            //新增字段
            list_ex.add(value.getClfy()); //材料费用
            list_ex.add(value.getYsfy()); //运输费用
            list_ex.add(value.getRgfy()); //人工差旅费用
            list_ex.add(value.getQtfy()); //其他费用
            list_ex.add(value.getPc()); //赔偿
            list_ex.add(value.getJnw()); //境内/境外
            list_ex.add(value.getXzjx()); //新造/检修
            list_ex.add(value.getCpfl()); //产品分类
            list_ex.add(value.getGzlx()); //故障类型
            list_ex.add(value.getSf()); //合同是否规定赔偿金额、时间和付款方式（是/否)
            list_ex.add(value.getSpqk()); //索赔进展情况
            list_ex.add(value.getFx()); //分析
            list_ex.add(value.getCgy()); //采购员
            list_ex.add(value.getLifnr()); //供应商
            list_ex.add(value.getBz()); //备注
            num++;
            dataRowList.add(list_ex);
            list_ex = new ArrayList<Object>();
        }

        return dataRowList;
    }

    //导入数据
    @Override
    public Object importData(String filePath)throws JsonProcessingException, IOException ,DataAccessException,
            InvalidFormatException, InstantiationException, IllegalAccessException {
        //定义变量
        ExcelImportUtil ei = new ExcelImportUtil(filePath, 1);

        Map<String,Object> errorMap = new HashMap<String, Object>();
        String json = null;
        List<String> headerList = new ArrayList<>();
      
        headerList.add(month);
        headerList.add(xxbh);
        headerList.add(lbbh);
        headerList.add(lbdm);
        headerList.add(yydm);
        headerList.add(psjd);
        headerList.add(cpsmjd);
        headerList.add(jxdd);
        headerList.add(fxsj);
        headerList.add(cpxh);
        headerList.add(cpbh);
        headerList.add(amount);
        headerList.add(xzccrq);
        headerList.add(zjjxccrq);
        headerList.add(jcbh);
        headerList.add(zlc);
        headerList.add(jxhlc);
        headerList.add(gzbw);
        headerList.add(gzms);
        headerList.add(cljg);
        headerList.add(clff);
        headerList.add(gzwcrq);
        headerList.add(sglb);
        headerList.add(yzd);
        headerList.add(zrcp);
        headerList.add(yyfx);
        headerList.add(zgcs);
        headerList.add(bgbh);
        headerList.add(zrdw);
        headerList.add(ss);
        headerList.add(clfy);
        headerList.add(ysfy);
        headerList.add(rgfy);
        headerList.add(qtfy);
        headerList.add(pc);
        headerList.add(jnw);
        headerList.add(xzjx);
        headerList.add(lifnr);
        headerList.add(bz);
        headerList.add(cpfl);
        headerList.add(czbz);
        headerList.add(gzlx);
        headerList.add(sf);
        headerList.add(spqk);
        headerList.add(fx);
        headerList.add(cgy);
        

        //检查列名
        listMap.clear();
        json = ei.checkHeadList(headerList, listMap);
        if (null != json) {
            return json;
        }

        List<InsertFieldTHM_FS50> list = ei.getDataList(InsertFieldTHM_FS50.class, "FS50");

        //检查数据
        listMap.clear();
        List<InsertFieldTHM_FS50> resultlist = checkData(list);
        if(listMap.size() > 0){
//        	errorMap.put("ERROR", listMap);
        	
        	errorsMap.put("ERRORDATA", listMap);
        	errorMap.put("ERROR", errorsMap);
            return mapper.writeValueAsString(errorMap);
        }

        for(InsertFieldTHM_FS50 value:list) {
            iReportMapperFS50.insertData(value);
        }
        listMap.put("SUCCESSES", "导入成功!");
        return mapper.writeValueAsString(listMap);

    }
    private List<InsertFieldTHM_FS50> checkData(List<InsertFieldTHM_FS50> list) {

        String tmp = "";
        String tmp1 = "";
        DecimalFormat format;
        Boolean required;

        for(int row = 0; row < list.size(); row++){
            InsertFieldTHM_FS50 value = list.get(row);

            //判空 月份
            tmp = value.getMonth().trim();
            required = true;
            tmp = checkNull(row, month, tmp, required);
            //判断月份是否是日期格式
			if(!tmp.equals(""))  tmp1 = checkDateFormat(row, month, tmp+"01");
            value.setMonth(tmp);
            

            //判空 信息编号
            tmp = value.getXxbh().trim().trim();
            required = true;
            tmp = checkNull(row, xxbh, tmp, required);
            //格式 信息编号
            if(!tmp.equals("")) tmp = checkFormat(row, xxbh, tmp);
            value.setXxbh(tmp); 

            //判空 质量损失类别编号
            tmp = value.getLbbh().trim();
            required = true;
            tmp = checkNull(row, lbbh, tmp, required);
            //格式 质量损失类别编号
//            if(!tmp.equals("")) tmp = checkThFormat(row, lbbh, tmp);
            value.setLbbh(tmp);

            //判空 质量损失类别代码
            tmp = value.getLbdm().trim();
            required = true;
            tmp = checkNull(row, lbdm, tmp, required);
            //格式 质量损失类别代码
            //判断是否是主数据内容
            if(!tmp.equals("")) tmp = checkMainData(row,lbdm,tmp,1);
            value.setLbdm(tmp);

            //判空 质量损失原因代码
            tmp = value.getYydm().trim();
            required = true;
            tmp = checkNull(row, yydm, tmp, required);
            //判断是否是主数据内容
            if(!tmp.equals("")) tmp = checkMainData(row,yydm,tmp,2);
            value.setYydm(tmp);

            //判空 配属局段
            tmp = value.getPsjd().trim();
            required = false;
            tmp = checkNull(row, psjd, tmp, required);
//            if(!tmp.equals("")) tmp = checkThFormat(row, psjd, tmp);
            value.setPsjd(tmp);

            //判空 产品寿命阶段
            tmp = value.getCpsmjd().trim();
            required = true;
            tmp = checkNull(row, cpsmjd, tmp, required);
//            if(!tmp.equals("")) tmp = checkThFormat(row, cpsmjd, tmp);
            value.setCpsmjd(tmp);

            //判空 检修地点
            tmp = value.getJxdd().trim();
            required = false;
            tmp = checkNull(row, jxdd, tmp, required);
//            if(!tmp.equals("")) tmp = checkThFormat(row, jxdd, tmp);
            value.setJxdd(tmp);

            //判空 发现时间
            tmp = value.getFxsj().trim();
            required = false;
            tmp = checkNull(row, fxsj, tmp, required);
            //格式 发现时间
            if(!tmp.equals("")) tmp = checkDateFormat(row, fxsj, tmp);
            value.setFxsj(tmp);

            //判空 产品型号
            tmp = value.getCpxh().trim();
            required = true;
            tmp = checkNull(row, cpxh, tmp, required);
            //格式 产品型号
//            if(!tmp.equals("")) tmp = checkThFormat(row, cpxh, tmp);
            value.setCpxh(tmp);

            //判空 产品编号
            tmp = value.getCpbh().trim();
            required = true;
            tmp = checkNull(row, cpbh, tmp, required);
            //格式 产品编号
//            if(!tmp.equals("")) tmp = checkThFormat(row, cpbh, tmp);
            value.setCpbh(tmp);

            //判空 数量  正整数
            tmp = value.getAmount().trim();
            required = true;
            tmp = checkNull(row, amount, tmp, required);
            //格式 数量
            if(!tmp.equals("")) tmp = checkNumFormat(row, amount, tmp);
            value.setAmount(tmp);

            //判空 新造出厂日期
            tmp = value.getXzccrq().trim();
            required = false;
            tmp = checkNull(row, xzccrq, tmp, required);
            //格式 新造出厂日期
//            if(!tmp.equals("")) tmp = checkDateFormat(row, xzccrq, tmp);
            value.setXzccrq(tmp);

            //判空 最近检修出厂日期
            tmp = value.getZjjxccrq().trim();
            required = false;
            tmp = checkNull(row, zjjxccrq, tmp, required);
            //格式 最近检修出厂日期
//            if(!tmp.equals("")) tmp = checkDateFormat(row, zjjxccrq, tmp);
            value.setZjjxccrq(tmp);

            //判空 机车编号
            tmp = value.getJcbh().trim();
            required = false;
            tmp = checkNull(row, jcbh, tmp, required);
//            if(!tmp.equals("")) tmp = checkThFormat(row, jcbh, tmp);
            value.setJcbh(tmp);

            //判空 总运行里程
            tmp = value.getZlc().trim();
            required = false;
            tmp = checkNull(row, zlc, tmp, required);
            //格式 总运行里程
//            format = new DecimalFormat("0.00");
//            if(!tmp.equals(""))  tmp = checkFormat(row, zlc, tmp, format);
            value.setZlc(tmp);

            //判空 检修后里程
            tmp = value.getJxhlc().trim();
            required = false;
            tmp = checkNull(row, jxhlc, tmp, required);
            //格式 检修后里程
//            format = new DecimalFormat("0.00");
//            if(!tmp.equals("")) tmp = checkFormat(row, jxhlc, tmp, format);
            value.setJxhlc(tmp);

            //判空 故障部位
            tmp = value.getGzbw().trim();
            required = true;
            tmp = checkNull(row,gzbw,tmp,required);
            value.setGzbw(tmp);

            // 故障描述
            tmp = value.getGzms().trim();
            required = false;
            tmp = checkNull(row,gzms,tmp,required);
            value.setGzms(tmp);
            
            //处理结果
            tmp = value.getCljg().trim();
            required = false;
            tmp = checkNull(row,cljg,tmp,required);
            value.setCljg(tmp);

            //处理方法
            tmp = value.getClff().trim();
            required = false;
            tmp = checkNull(row,clff,tmp,required);
//            if(!tmp.equals("")) tmp = checkThFormat(row, clff, tmp);
            value.setClff(tmp);

            //判空 现场故障处理完成日期
            tmp = value.getGzwcrq().trim();
            required = false;
            tmp = checkNull(row, gzwcrq, tmp, required);
            //格式 现场故障处理完成日期
//            if(!tmp.equals("")) tmp = checkDateFormat(row, gzwcrq, tmp);
            value.setGzwcrq(tmp);

            //判空 事故类别
            tmp = value.getSglb().trim();
            required = false;
            tmp = checkNull(row,sglb,tmp,required);
//            if(!tmp.equals("")) tmp = checkThFormat(row, sglb, tmp);
            value.setSglb(tmp);

            //严重度
            tmp = value.getYzd().trim();
            required = false;
            tmp = checkNull(row,yzd,tmp,required);
//            if(!tmp.equals("")) tmp = checkThFormat(row, yzd, tmp);
            value.setYzd(tmp);
            
            
            //责任初判
            tmp = value.getZrcp().trim();
            required = false;
            tmp = checkNull(row,zrcp,tmp,required);
//            if(!tmp.equals("")) tmp = checkThFormat(row, zrcp, tmp);
            value.setZrcp(tmp);
            
           
            //原因分析
            tmp = value.getYyfx().trim();
            required = false;
            tmp = checkNull(row,yyfx,tmp,required);
//            if(!tmp.equals("")) tmp = checkThFormat(row, yyfx, tmp);
            value.setYyfx(tmp);
            
            
            //整改措施
            tmp = value.getZgcs().trim();
            required = false;
            tmp = checkNull(row,zgcs,tmp,required);
//            if(!tmp.equals("")) tmp = checkThFormat(row, zgcs, tmp);
            value.setZgcs(tmp);
            
            
            //判空   报告编号
            tmp = value.getBgbh().trim();
            required = false;
            tmp = checkNull(row, bgbh, tmp, required);
//            if(!tmp.equals("")) tmp = checkThFormat(row, bgbh, tmp);
            value.setBgbh(tmp);

            //判空 责任单位
            tmp = value.getZrdw().trim();
            required = true;
            tmp = checkNull(row,zrdw,tmp,required);
//            if(!tmp.equals("")) tmp = checkThFormat(row, zrdw, tmp);
            value.setZrdw(tmp);

            
            //判空 材料费用
            tmp = value.getClfy().trim();
            required = false;
            tmp = checkNull(row, clfy, tmp, required);
            //格式 材料费用
            format = new DecimalFormat("0.00");
            tmp = checkFormat1(row, clfy, tmp, format);
            value.setClfy(tmp);
            
            
            //判空 运输费用
            tmp = value.getYsfy().trim();
            required = false;
            tmp = checkNull(row, ysfy, tmp, required);
            //格式 材料费用
            format = new DecimalFormat("0.00");
            tmp = checkFormat1(row, ysfy, tmp, format);
            value.setYsfy(tmp);
            
            
            //判空 人工差旅费用
            tmp = value.getRgfy().trim();
            required = false;
            tmp = checkNull(row, rgfy, tmp, required);
            //格式  人工差旅费用
            format = new DecimalFormat("0.00");
            tmp = checkFormat1(row, rgfy, tmp, format);
            value.setRgfy(tmp);
            
            //判空 其他费用
            tmp = value.getQtfy().trim();
            required = false;
            tmp = checkNull(row, qtfy, tmp, required);
            //格式  其他费用
            format = new DecimalFormat("0.00");
            tmp = checkFormat1(row, qtfy, tmp, format);
            value.setQtfy(tmp);
            
            
            //判空   损失费用合计  需做合计处理
            tmp = value.getSs().trim();
            required = true;
            //算法处理 求和  这五项必须为数值型且等于其他四项和   如果格式不对那么是""
            tmp = checkNull(row, ss, tmp, required);
            if(!tmp.equals("")){
             tmp = checkNumFormat1(row, ss, tmp);
             value.setSs(tmp); 
            }else{
            	double clfy1 =StringUtils.isNotEmpty(value.getClfy())?Double.parseDouble(value.getClfy()):0.00;
              	double ysfy1 =StringUtils.isNotEmpty(value.getYsfy())?Double.parseDouble(value.getYsfy()):0.00;
              	double rgfy1 =StringUtils.isNotEmpty(value.getRgfy())?Double.parseDouble(value.getRgfy()):0.00;
              	double qtfy1 =StringUtils.isNotEmpty(value.getQtfy())?Double.parseDouble(value.getQtfy()):0.00;
              	value.setSs(clfy1+ysfy1+rgfy1+qtfy1+""); 
            }	
            
            //判空 赔偿
            tmp = value.getPc().trim();
            required = false;
            tmp = checkNull(row, pc, tmp, required);
            //格式 赔偿
            format = new DecimalFormat("0.00");
            tmp = checkFormat1(row, pc, tmp, format);
            value.setPc(tmp);

            
            //境内外
            tmp = value.getJnw().trim();
            required = false;
            tmp = checkNull(row,jnw,tmp,required);
            value.setJnw(tmp);
            
            //新造检修
            tmp = value.getXzjx().trim();
            required = false;
            tmp = checkNull(row,xzjx,tmp,required);
            value.setXzjx(tmp);
            
            //供应商
            tmp = value.getLifnr().trim();
            required = false;
            tmp = checkNull(row,lifnr,tmp,required);
            value.setLifnr(tmp);
            
            
            //备注
            tmp = value.getBz().trim();
            required = false;
            tmp = checkNull(row,bz,tmp,required);
            value.setBz(tmp);
            
            
            //产品分类 == 项目类型 
            tmp = value.getCpfl().trim();
            required = true;
            tmp = checkNull(row,cpfl,tmp,required);
            //判断是否是主数据内容
            if(!tmp.equals("")) tmp = checkMainData(row,cpfl,tmp,3);
            value.setCpfl(tmp);
            
            //操作标识
            tmp = value.getCzbz().trim();
            tmp = tmp.trim();
            switch (tmp){
                case "新增":
                case "更新":
                case "删除":
                value.setCzbz(tmp);break;
                default:listMap.put("OPERATEERROR","第" + (row + 3) + "行" + "-" + czbz+"操作标识填写错误，请填写：新增、更新、删除");
            }
            
            //故障类型
            tmp = value.getGzlx().trim();
            required = true;
            tmp = checkNull(row,gzlx,tmp,required);
            value.setGzlx(tmp);
            
            // 合同是否规定赔偿金额、时间和付款方式（是/否)	
            tmp = value.getSf().trim();
            required = false;
            tmp = checkNull(row,sf,tmp,required);
            value.setSf(tmp);
            
            //索赔进展情况
            tmp = value.getSpqk().trim();
            required = false;
            tmp = checkNull(row,spqk,tmp,required);
            value.setSpqk(tmp);
            
            //分析  
            tmp = value.getFx().trim();
            required = false;
            tmp = checkNull(row,fx,tmp,required);
            value.setFx(tmp);
            
            //采购员
            tmp = value.getCgy().trim();
            required = false;
            tmp = checkNull(row,cgy,tmp,required);
            value.setCgy(tmp);
        }
        return null;
    }



    private String checkMainData(int row,String col,String data,int codebs){
        HashMap codeMap = new HashMap<>();
        codeMap.put("code", data);
        codeMap.put("codebs", codebs);
        Map countIntable = iReportMapperKPI2227.isCodeIntable(codeMap);
        //存在于数据库中
        if (countIntable!=null&&Integer.parseInt(countIntable.get("count")+"")!=0) {
            return data;
        }else{
            listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "数据不在范围内");
            j++;
            return "";
        }
    }
    
    
    

    /**
     * 判断单元格内容是否为空，如果为空，返回“”，非空返回单元格本身内容
     * @param row
     * @param col
     * @param data
     * @return
     */
    private String checkNull(int row, String col, String data, Boolean required){
        if (data.isEmpty() || data.trim().equals("") || data.equals(null)) {
            if(required) {
                listMap.put("NULLERROR" + i, "第" + (row + 3) + "行" + "-" + col + "-" + "内容不能为空。");
                i++;
            }
            return "";
        }else {
            return data;
        }
    }
    
    
    
    
    
    /**
     * 必填项数字加字母编号的内容格式校验
     * @param row
     * @param col
     * @param data
     * @return
     */
    private String checkFormat(int row, String col, String data){
    	String datatmp=data.replace("-", "");
        String allMatch = "[a-zA-Z0-9]{1,}";
        if(datatmp.matches(allMatch) || (datatmp.contains("E") && datatmp.contains("."))){
            if(datatmp.contains("E") && datatmp.contains(".")){//如果内容是纯数字并且被改编成科学计数法，则执行if中代码
                try {
                    BigDecimal bd = new BigDecimal(data);
                    String str = bd.toPlainString();
                    return str;
                }catch (NumberFormatException e){
                    return data;
                }
            }
            return data;
        }else {
            listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "数据格式错误");
            j++;
            return "";
        }
    }
    
    
    /**
     * 必填项的数字格式校验((只能是大于0的正整数))
     * @param row
     * @param col
     * @param data
     * @param format
     * @return
     */
    private String checkNumFormat(int row, String col, String data) {
        if (data.isEmpty() || data.trim().equals("") || data.equals(null)) {
        	 listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "数据不是正整数");
             j++;
        	 return "";
        }else {
            try {
            	//数字不为空的时候 做校验
            	 Pattern p=Pattern.compile("^\\d+$");
		    	   Matcher m=p.matcher(data);
		    	   if(m.find()){
		    		  //校验通过
		    	       return data;
		    	   }else{
		    		 //校验不通过
	    		    listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "数据不是正整数");
	                j++;
	                return "";
		    	   }
            } catch (NumberFormatException e){
                listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "数据不是正整数");
                j++;
                return "";
            }
        }
    }
    
    
    /**
     * 必填项的数字格式校验((只能是大于0的正实数))
     * @param row
     * @param col
     * @param data
     * @param format
     * @return
     */
    private String checkNumFormat1(int row, String col, String data) {
        if (data.isEmpty() || data.trim().equals("") || data.equals(null)) {
        	 listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "数据不是正实数");
             j++;
        	 return "";
        }else {
            try {
            	//数字不为空的时候 做校验
            	 Pattern p=Pattern.compile("^[0-9].*$");
		    	   Matcher m=p.matcher(data);
		    	   if(m.find()){
		    		  //校验通过
		    	       return data;
		    	   }else{
		    		 //校验不通过
	    		    listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "数据不是正实数");
	                j++;
	                return "";
		    	   }
            } catch (NumberFormatException e){
                listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "数据格式错误");
                j++;
                return "";
            }
        }
    }

    
    /**
     * 非必填项的数字格式校验
     * @param row
     * @param col
     * @param data
     * @param format
     * @return
     */
    private String checkFormat(int row, String col, String data, DecimalFormat format) {
        if (StringUtils.isNotEmpty(data)) {
            return "";
        }else {
        	   try {
	            	//数字不为空的时候 做校验  
	            	 Pattern p=Pattern.compile("^[0-9].*$"); //正数值
			    	   Matcher m=p.matcher(data);
			    	   if(m.find()){
			    		  //校验通过
			    	       return Math.abs(Double.parseDouble(data))+"";
			    	   }else{
			    		 listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "数据不是正实数");
			             j++;
		                return "";
			    	   }
	            } catch (NumberFormatException e){
	            	listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "数据格式错误");
	                j++;
	            	return "";
	            }
       /*     try {
            	//大于0的浮点数
            	if(Double.valueOf(data)<0){
            		    listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "数据不能为负数");
    	                j++;
    	                return "";
            	} 
                data = format.format(Double.parseDouble(data));
                return data;
            } catch (NumberFormatException e){
                listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "数据格式错误");
                j++;
                return "";
            }*/
        }
    }
    
    
    
    
    /**
     * 非必填项的数字格式校验  如过为非正数  那么为 0 
     * @param row
     * @param col
     * @param data
     * @param format
     * @return
     */
    private String checkFormat1(int row, String col, String data, DecimalFormat format) {
        if (data.isEmpty() || data.trim().equals("") || data.equals(null)) {
            return "0";
        }else {
            try {
            	//数字不为空的时候 做校验  
            	 Pattern p=Pattern.compile("^[0-9].*$"); //非负实数
		    	   Matcher m=p.matcher(data);
		    	   if(m.find()){
		    		  //校验通过
		    	       return Math.abs(Double.parseDouble(data))+"";
		    	   }else{
	                return "0";
		    	   }
            } catch (NumberFormatException e){
            	return "0";
            }
        }
    }
    
    
    /**
     * 日期格式校验
     * @param row
     * @param col
     * @param data
     * @return
     */
    private String checkDateFormat(int row, String col, String data){
        String allMatch = "[a-zA-Z0-9]{1,}";
        String numberMatch = "[0-9]{1,}";
        boolean isValidDate=true;
        if(data.matches(allMatch) || (data.contains("E") && data.contains("."))){
            if(data.contains("E") && data.contains(".")){//如果内容是纯数字并且被改编成科学计数法，则执行if中代码
                try {
                    BigDecimal bd = new BigDecimal(data);
                    String str = bd.toPlainString();
                    if(str.length() == 8) {
                    	//判断时间是否是有效值
                    	isValidDate = FileUtils.isValidDate(str);
                    	if(isValidDate){
                    		return str;
                    	}
                    }else {
                        listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "日期格式错误");
                        j++;
                    }
                }catch (NumberFormatException e){
                    listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "日期格式错误");
                    j++;
                    return "";
                }
            }else if(data.matches(numberMatch) && data.length() == 8){
                return data;
            }
            if(!isValidDate){
            	 listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "日期不是有效值");
		         j++;
		         return "";
            }
            listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "日期格式错误");
            j++;
            return "";
        }else {
            listMap.put("FORMATERROR"+j, "第" + (row + 3) + "行" + "-" + col + "-" + "日期格式错误");
            j++;
            return "";
        }
    }
	

    
    
    //导出模板
    @Override
    public Object downloadTemplate() throws JsonProcessingException,IOException,DataAccessException{
        List<String> headerList = new ArrayList<>();
//        headerList.add(date);
        headerList.add(month);
        headerList.add(xxbh);
        headerList.add(lbbh);
        headerList.add(lbdm);
        headerList.add(yydm);
        headerList.add(psjd);
        headerList.add(cpsmjd);
        headerList.add(jxdd);
        headerList.add(fxsj);
        headerList.add(cpxh);
        headerList.add(cpbh);
        headerList.add(amount);
        headerList.add(xzccrq);
        headerList.add(zjjxccrq);
        headerList.add(jcbh);
        headerList.add(zlc);
        headerList.add(jxhlc);
        headerList.add(gzbw);
        headerList.add(gzms);
        headerList.add(cljg);
        headerList.add(clff);
        headerList.add(gzwcrq);
        headerList.add(sglb);
        headerList.add(yzd);
        headerList.add(zrcp);
        headerList.add(yyfx);
        headerList.add(zgcs);
        headerList.add(bgbh);
        headerList.add(zrdw);
        headerList.add(ss);
        headerList.add(clfy);
        headerList.add(ysfy);
        headerList.add(rgfy);
        headerList.add(qtfy);
        headerList.add(pc);
        headerList.add(jnw);
        headerList.add(xzjx);
        headerList.add(lifnr);
        headerList.add(bz);
        headerList.add(cpfl);
        headerList.add(czbz);
        headerList.add(gzlx);
        headerList.add(sf);
        headerList.add(spqk);
        headerList.add(fx);
        headerList.add(cgy);
        

        String secondTitle = null;
//        ExcelExportUtil ee = new ExcelExportUtil("厂外铁路产品质量信息表-导入数据", secondTitle, headerList);
        ExcelExportUtil2227 ee = new ExcelExportUtil2227("厂外铁路产品质量信息表-导入数据", secondTitle, headerList);
        return ee;


    }
    
    
	@Override
	public String getAccitype(String name)  throws  JsonProcessingException,IOException,DataAccessException {
	       List<String> accitypelList = new ArrayList<String>();
	       accitypelList= iReportMapperFS50.getAccitype(name);
	       return mapper.writeValueAsString(accitypelList);
	}
	
	
	@Override
	public String getDefalocation(String name) throws JsonProcessingException, IOException, DataAccessException {
	       List<String> accitypelList = new ArrayList<String>();
	       accitypelList= iReportMapperFS50.getDefalocation(name);
	       return mapper.writeValueAsString(accitypelList);
	}
	
	
	@Override
	public String getProductLife(String name) throws JsonProcessingException, IOException, DataAccessException {
	       List<String> accitypelList = new ArrayList<String>();
	       accitypelList= iReportMapperFS50.getProductLife(name);
	       return mapper.writeValueAsString(accitypelList);
	}
	
	@Override
	public String getProductModel(String name) throws JsonProcessingException, IOException, DataAccessException {
		List<String> accitypelList = new ArrayList<String>();
		accitypelList= iReportMapperFS50.getProductModel(name);
		return mapper.writeValueAsString(accitypelList);
	}
	
	@Override
	public String getAttaburea(String name) throws JsonProcessingException, IOException, DataAccessException {
		List<String> accitypelList = new ArrayList<String>();
		accitypelList= iReportMapperFS50.getAttaburea(name);
		return mapper.writeValueAsString(accitypelList);
	}
	
	@Override
	public String getSeverity(String name) throws JsonProcessingException, IOException, DataAccessException {
		List<String> accitypelList = new ArrayList<String>();
		accitypelList= iReportMapperFS50.getSeverity(name);
		return mapper.writeValueAsString(accitypelList);
	}
	
	@Override
	public String getInfocode(String name,Integer start,Integer limit) throws JsonProcessingException, IOException, DataAccessException {
		List<String> accitypelList = new ArrayList<String>();
		accitypelList= iReportMapperFS50.getInfocode(name,start,limit);
		return mapper.writeValueAsString(accitypelList);
	}
	
	@Override
	public String getProducCode(String name,Integer start,Integer limit) throws JsonProcessingException, IOException, DataAccessException {
		List<String> accitypelList = new ArrayList<String>();
		accitypelList= iReportMapperFS50.getProducCode(name,start,limit);
		return mapper.writeValueAsString(accitypelList);
	}
	
	@Override
	public String getMotorCode(String name,Integer start,Integer limit) throws JsonProcessingException, IOException, DataAccessException {
		List<String> accitypelList = new ArrayList<String>();
		accitypelList= iReportMapperFS50.getMotorCode(name,start,limit);
		return mapper.writeValueAsString(accitypelList);
	}
	
	@Override
	public String getTvendor(String name, Integer start, Integer limit)
			throws JsonProcessingException, IOException, DataAccessException {
		List<String> accitypelList = new ArrayList<String>();
		accitypelList= iReportMapperFS50.getTvendor(name,start,limit);
		return mapper.writeValueAsString(accitypelList);
	}
	
	@Override
	public String getReportCode(String name, Integer start, Integer limit)
			throws JsonProcessingException, IOException, DataAccessException {
		List<String> accitypelList = new ArrayList<String>();
		accitypelList= iReportMapperFS50.getReportCode(name,start,limit);
		return mapper.writeValueAsString(accitypelList);
	}
	
	
	@Override
	public String getLossTypeCode(String name, Integer start, Integer limit)
			throws JsonProcessingException, IOException, DataAccessException {
		List<String> accitypelList = new ArrayList<String>();
		accitypelList= iReportMapperFS50.getLossTypeCode(name,start,limit);
		return mapper.writeValueAsString(accitypelList);
	}
	
	
}


