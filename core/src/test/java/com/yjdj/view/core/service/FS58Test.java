package com.yjdj.view.core.service;

import com.yjdj.view.core.entity.mybeans.QueryBean;
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
 * Created by sunwan on 2017/4/25.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS58Test {

    @Autowired
    private IReportServiceFS58 iReportServiceFS58;

    @Test
    public void testGetData() throws IOException {

    	//CALL FS58('IWERK','GROES',ZLEVEL,'PJWLBM',startTime,endTime);
        List<String> werksValue = new ArrayList<>();//工厂多值
        werksValue.add("");
        List<String> groesValue = new ArrayList<>();//产品型号多值
        groesValue.add("");
        List<String> zlevelValue = new ArrayList<>();//维修级别多值
        zlevelValue.add("");
        String startTime = "20170101";
        String endTime = "20170103";
        List<String> matnrValue = new ArrayList<>();
        matnrValue.add("");

        QueryBean queryBean = new QueryBean();
        queryBean.setWerksValue(werksValue);
        queryBean.setGroesValue(groesValue);
        queryBean.setZlevelValue(zlevelValue);
        queryBean.setStartTime(startTime);
        queryBean.setEndTime(endTime);
        queryBean.setMatnrValue(matnrValue);

//        查询
//        queryBean.setExport(false);
        //数据导出
      queryBean.setExport(true);
        
        Object resultJson = iReportServiceFS58.selectData(queryBean);
        Object result = resultJson.toString();
        System.out.println(result);
        if (resultJson != null && queryBean.isExport()) {
            ExcelExportUtil ee = (ExcelExportUtil) resultJson;
            ee.writeFile("C://Users//xiaozhang//Desktop//export//检修电机（产品）偶换件偶换率统计表.xlsx");
            ee.dispose();
            System.out.println("Export success.");
        }

    }
    
    @Test
    public void getGroesJson() throws IOException {
    	String id = "304";
        Object resultJson = iReportServiceFS58.getGroesJson(id);
        System.out.println("++++++++++++"+resultJson);

    }
    
    @Test
    public void getZlevelJson() throws IOException {
    	String id = "16";
        Object resultJson = iReportServiceFS58.getZlevelJson(id);
        System.out.println("++++++++++++"+resultJson);
    }
    
    @Test
    public void getPjwlbmJson() throws IOException {
    	int start = 0;
    	int limit = 10;
    	String id = "CNR0000231153";
    	String name = "";
        Object resultJson = iReportServiceFS58.getPjwlbmJson(id, name, start, limit);
        System.out.println("++++++++++++"+resultJson);
    }
    
}
