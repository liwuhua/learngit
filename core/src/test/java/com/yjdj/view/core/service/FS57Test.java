package com.yjdj.view.core.service;

import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.THM_FS57;
import com.yjdj.view.core.util.ExcelExportUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunwan on 2017/3/3.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS57Test {

    @Autowired
    private IReportServiceFS57 iReportServiceFS57;

    @Test
    public void test() throws IOException {

        List<String> vkorgValue = new ArrayList<>();//销售组织多值
        List<String> werksValue = new ArrayList<>();//工厂多值
        werksValue.add("1100");
        List<String> kunnrValue = new ArrayList<>();//客户编号多值
        kunnrValue.add("1000201000");
        kunnrValue.add("1000300400");
        String dateYearMonth = "201701";

        QueryBean queryBean = new QueryBean();
        queryBean.setVkorgValue(vkorgValue);
        queryBean.setWerksValue(werksValue);
        queryBean.setKunnrValue(kunnrValue);
        queryBean.setDateYearMonth(dateYearMonth);

//        查询
//        queryBean.setExport(false);
//        Object list = iReportServiceFS57.selectData(queryBean);
//        Object result = list.toString();
//        System.out.println(result);

        //数据导出
        queryBean.setExport(true);
        Object resultJson = iReportServiceFS57.selectData(queryBean);
        if (resultJson != null && queryBean.isExport()) {
            ExcelExportUtil ee = (ExcelExportUtil) resultJson;
            ee.writeFile("C://Users//zcyj//Desktop//export//检修统计表.xlsx");
            ee.dispose();
            System.out.println("Export success.");
        }

    }

}
