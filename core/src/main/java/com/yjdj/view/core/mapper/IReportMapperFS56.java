package com.yjdj.view.core.mapper;

import com.yjdj.view.core.entity.mybeans.THM_FS56;
import com.yjdj.view.core.entity.mybeans.THM_FS56_MAP;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by zhuxuan on 2017/3/1.
 */
@MyBatisRepository
public interface IReportMapperFS56 extends BaseMapper{
    /**
     * 通过电机型号查找并返回需要显示的列
     * @param motormodel
     * @return
     */
    public List<THM_FS56_MAP> getColumn(@Param("MOTORMODEL")  String motormodel);

    /**
     * 通过电机型号和日期查找数据
     * @param motormodel
     * @return
     */
    public List<THM_FS56> getData(@Param("SELECTNAME") String selectName,
                                   @Param("MOTORMODEL") String motormodel,
                                   @Param("starttime") String starttime,
                                   @Param("endtime") String endtime,
                                   @Param("startitem") int startitem,
                                   @Param("pageitem") int pageitem,
                                   @Param("flag") int action
                                   );

    /**
     * 通过列描述返回codes
     * @param txt
     * @return
     */
    public Map<String,String> getColumn_Codes(@Param("TXT") String txt);

    /**
     * 导入数据
     * @param importColumns
     * @param importdata
     */
    public void importData(@Param("IMPORTCOLUMNS") String importColumns, @Param("IMPORTDATA") String importdata);

    /**
     * 获取MAP表中列对应的flag
     * @param motormodel
     * @param txt
     * @return
     */
    public Map<String,String> getMapFlag(@Param("MOTORMODEL") String motormodel, @Param("TXT") String txt);

    /**
     * 通过列描述获取flag
     * @param txt
     * @return
     */
    public Map<String,String> getFlagByTXT(@Param("TXT") String txt);

    /**
     * 设置MAP表中列对应的flag
     * @param motormodel
     * @param txt
     */
    public void setMapFlag(@Param("MOTORMODEL") String motormodel, @Param("TXT") String txt, @Param("FLAG") String Flag);

    /**
     * 通过描述和电机型号，返回tag，表示该描述是否有对应code
     * @param motormodel
     * @param txt
     * @param hasModel
     * @return
     */
    public Map<String,Object> getTag(@Param("MOTORMODEL") String motormodel, @Param("TXT") String txt, @Param("hasModel") boolean hasModel);

    /**
     * 获取MAP中目前还空白的codes
     * @return
     */
    public List<THM_FS56_MAP> getEmptyColumn();

    /**
     * 在MAP中添加新列
     * @param motormodel
     * @param txt
     * @param emptyCode
     */
    public void addNewCode(@Param("MOTORMODEL") String motormodel, @Param("TXT") String txt, @Param("EMPTYCODE") String emptyCode);

    /**
     * 在MAP中添加重复的code列（多种电机用一列的情况）
     * @param motormodel
     * @param txt
     * @param usedCode
     */
    public void addUsedCode(@Param("MOTORMODEL") String motormodel, @Param("TXT") String txt, @Param("USEDCODE") String usedCode, @Param("FLAG") String flag);

    /**
     * 获取所有固定列，用来判断新导入模板是否含有所有固定列
     * @return
     */
    public List<Map<String,String>> getFixedColumn();

    /**
     * 用户查询条件，返回电机型号
     * @return
     */
    public List<Map<String,String>> getMotorModel(@Param("MOTORMODEL") String motorModel);
}
