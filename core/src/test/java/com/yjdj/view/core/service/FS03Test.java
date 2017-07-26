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
 * Created by zcyj on 2016/10/24.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext_test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class FS03Test {
    @Autowired
    private IReportServiceFS03 iReportServiceFS03;

    @Test
    public void selectDataTrue_01() throws IOException {
        List<String> werksValue = new ArrayList<String>();
 //       if(ekgrpValue.get(0).equals(""))ekgrpValue = null;

        werksValue.add("1000");
        werksValue.add("2000");
        werksValue.add("3000");

        List<String> matnrValue = new ArrayList<String>();
        matnrValue.add("000000005020000038");
        matnrValue.add("Z500000095423");
        matnrValue.add("000000005020000070");
        matnrValue.add("000000005020000091");

       List<String> lifnrValue = new ArrayList<String>();
//        if(lifnrValue.get(0).equals(""))lifnrValue = null;
       lifnrValue.add("2000011275");
       lifnrValue.add("0000002000");
//       lifnrValue.add("");
//        lifnrValue = null;

       List<String> ekgrpValue = new ArrayList<String>();
 //       if(ekgrpValue.get(0).equals(""))ekgrpValue = null;
        ekgrpValue.add("C03");
        ekgrpValue.add("G01");
//       ekgrpValue.add("");
//        ekgrpValue = null;

        String start = "20160130";
        String end = "20161117";

        QueryBean queryBean = new QueryBean();
        queryBean.setWerksValue(werksValue);
        queryBean.setMatnrValue(matnrValue);
        queryBean.setKunnrValue(lifnrValue);
        queryBean.setVkorgValue(ekgrpValue);
        queryBean.setExport(true);
        queryBean.setStartTime(start);
        queryBean.setEndTime(end);

        Object resultJson = iReportServiceFS03.selectDataTrue(queryBean);
        if (resultJson != null && queryBean.isExport()) {
            ExcelExportUtil ee = (ExcelExportUtil) resultJson;
            ee.writeFile("C://Users//zcyj//Desktop//export//检修统计表.xlsx");
            ee.dispose();
            System.out.println("Export success.");
        }

//        }
    }

}


