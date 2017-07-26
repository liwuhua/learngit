package com.yjdj.view.core.mapper;

import com.yjdj.view.core.MapperSqlProvider.ReportSqlFS00;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by wangkai on 16/10/13.
 */
@MyBatisRepository
public interface IReportMapperFS00 extends BaseMapper{

    /**
     * 第一步:根据条件查询THM_LIPS_01表
     * @param matnrValue
     * @param matnrInterval
     * @param vkorgValue
     * @param vkburValue
     * @return
     */
    @SelectProvider(type = ReportSqlFS00.class, method = "getMapListByThmlips01")
    public List<Map<String,Object>> getMapListByThmlips01(@Param("matnrValue") List<String> matnrValue,@Param("matnrInterval") List<String> matnrInterval,@Param("vkorgValue") List<String> vkorgValue, @Param("vkburValue") List<String> vkburValue);

    public List<Map<String,Object>> getMapListByThmlips01Test(@Param("matnrValue") List<String> matnrValue,@Param("matnrInterval") List<Map<String,String>> matnrInterval,@Param("vkorgValue") List<String> vkorgValue, @Param("vkburValue") List<String> vkburValue);

    /**
     * 第二步:Select * from THM_VBRP_01 where VGBEL in (第一步中的交货单号)
     * @param vbelnValue 第一步中获取的交货单号
     * @return
     */
    @SelectProvider(type = ReportSqlFS00.class, method = "getMapListByThmvbrp01")
    public List<Map<String,Object>> getMapListByThmvbrp01(@Param("vbelnValue") List<String> vbelnValue);

    /**
     * 第三步:Select * from THM_VBRP_02 where VGBEL in (第一步中的交货单号)  and VBELN not in (第二步数据集中的VBELN)
     * @param vbelnValue    第一步中获取的交货单号
     * @param vbeln2Value   第二步中获取的交货单号
     * @return
     */
    @SelectProvider(type = ReportSqlFS00.class, method = "getMapListByThmvbrp02")
    public List<Map<String,Object>> getMapListByThmvbrp02(@Param("vbelnValue") List<String> vbelnValue,@Param("vbeln2Value") List<String> vbeln2Value);
}
