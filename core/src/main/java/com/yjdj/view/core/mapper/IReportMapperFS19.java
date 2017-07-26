package com.yjdj.view.core.mapper;

import com.yjdj.view.core.entity.mybeans.InsertFieldTHM_FS19;
import com.yjdj.view.core.entity.mybeans.MdmTcompCode;
import com.yjdj.view.core.entity.mybeans.THM_FS19;
import com.yjdj.view.core.entity.mybeans.THM_IMP_FS19;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * Created by zhuxuan on 2016/11/11.
 */
@MyBatisRepository
public interface IReportMapperFS19 extends BaseMapper{
    /**
     * 获取非用户导入的数据
     * @param rbukrs
     * @param fiscper
     * @return
     */
    public List<THM_FS19> getResultData(@Param("rbukrs")List<String> rbukrs, @Param("fiscper") String fiscper);

    /**
     * 取编制单位
     * @param comp_code
     * @param txtmd
     * @return
     */
    public List<MdmTcompCode> getCompCode(@Param("comp_code") String comp_code,@Param("txtmd") String txtmd);

    /**
     * 取所有公司代码和公司名称
     * @return
     */
    public List<MdmTcompCode> getAllTcompCode();

    /**
     * 取用户导入的数据
     * @param fiscper
     * @return
     */
    public List<THM_IMP_FS19> getUserImportData(@Param("rbukrs")List<String> rbukrs, @Param("fiscper") String fiscper);

    /**
     * 用户数据导入
     * @param fiscper
     * @param list
     * @return
     */
    public void saveOrUpdateData(@Param("fiscper") String fiscper,@Param("list") InsertFieldTHM_FS19 list);

    /**
     * 删除错误数据
     * @param list
     * @return
     */
    public void deleteErrorData(@Param("list") InsertFieldTHM_FS19 list);

    /**
     * 验证公司是否输入正确
     * @param company
     * @return
     * @throws DataAccessException
     */
    @Select("SELECT * from MDM_TCOMP_CODE where FIND_IN_SET(TXTMD,\"${company}\")" +" UNION ALL "+
            "SELECT COMP_CODE,TXTMD from MDM_TCOMP_CODE_OVERSEA where FIND_IN_SET(TXTMD,\"${company}\")")
    public List<Map<String, String>> selectCompanyCodeBytxtmd(@Param("company") String company)throws DataAccessException;

    /**
     * 通过type返回栏目
     * @param type
     * @return
     * @throws DataAccessException
     */
    @Select("SELECT LANMU from THM_IMP_FS19_FIELD where TYPE = ${TYPE}")
    public String selectLanmu(@Param("TYPE") String type)throws DataAccessException;
}
