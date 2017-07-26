package com.yjdj.view.core.mapper;

import com.yjdj.view.core.entity.mybeans.THM_FS46;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by zhuxuan on 2016/12/28.
 */
@MyBatisRepository
public interface IReportMapperFS46 extends BaseMapper{

    /**
     * 报表结果查询
     * @param train_type
     * @return
     * @throws DataAccessException
     */
    public List<THM_FS46> selectUserData(@Param("TRAIN_TYPE") List<String> train_type,
                                         @Param("startitem") int startitem,
                                         @Param("pageitem") int pageitem,
                                         @Param("flag") int flag) throws DataAccessException;

    /**
     * 导入数据
     * @param DATA
     * @return
     */
//    public void saveOrUpdateData(@Param("DATA") InsertFieldTHM_FS46 DATA);

    /**
     * 查询所有车型
     * @param trainmodel
     * */
    public List<THM_FS46> selectAllTrainModel(@Param("TRAINMODEL") String trainmodel);
}
