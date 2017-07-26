package com.yjdj.view.core.mapper;

import java.util.List;
import java.util.Map;

import com.yjdj.view.core.entity.mybeans.THM_FS47_ALLOCATION;
import com.yjdj.view.core.entity.mybeans.THM_FS47_MAP;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;

/**
 * Created by zhuxuan on 2017/3/29.
 */
@MyBatisRepository
public interface IReportMapperFS47 extends BaseMapper{

    /**
     * 根据车型取所有的车号
     * @param train_type
     * @return
     */
    public List<Map<String,String>> getTrainNum(@Param("TRAIN_TYPE") String train_type);
    /**
     * 根据车型，从MAP表中查询该车型配属产品map（电机、变流柜等）
     * @param train_type
     * @return
     */
    public List<THM_FS47_MAP> getMotorMap(@Param("TRAIN_TYPE") String train_type);

    /**
     * 取总走行里程
     * @param train_type
     * @param train_num
     * @return
     */

    public Map<String,String> getTotalDistance(@Param("TRAIN_TYPE") String train_type, @Param("TRAIN_NUM") String train_num);
    /**
     * 插入或更新数据
     * @param data
     */
    public void saveOrUpdateData(@Param("DATA") THM_FS47_ALLOCATION data);

    /**
     * 根据查询条件取数据
     * @param train_type 车型
     * @param train_num 车号
     * @param repair_level 车修程类别
     * @return
     */
    public List<THM_FS47_ALLOCATION> getData(@Param("TRAIN_TYPE") String train_type,
                                              @Param("TRAIN_NUM") String train_num,
                                              @Param("REPAIR_LEVEL") String repair_level,
                                              @Param("MANUFACTURER") List<String> manufacturer_List,
                                              @Param("BUREAU_SUBJECTION") List<String> bureau_subjection_List,
                                              @Param("PLACE_SUBJECTION") List<String> place_subjection_List,
                                              @Param("MOTOR_SERIAL_NUM") List<String> motor_serial_num_List
                                              );


    /**
     * 获取所有车修程类别
     * @return
     */
    @Select("select distinct name repair_level from t_sys_code t1 join (select id from t_sys_code_type " +
            "where name in ( \"机车产品寿命阶段\",\"动车产品寿命阶段\" )) t2 on t1.codeTypeId = t2.id order by t2.id,t1.code4")
    public List<Map<String,String>> getAllRepairLevel();

    /**
     * 校验车型是否存在于主数据表中
     * @param train_type
     * @return
     */
    @Select("select count(1) isExist from t_sys_code t1 join (select id from t_sys_code_type where name in ( \"车型\" )) t2 " +
            "on t1.codeTypeId = t2.id where t1.name = #{TRAIN_TYPE}")
    public Map<String,Integer> getTrainTypeExist(@Param("TRAIN_TYPE") String train_type);

    /**
     * 校验配属局段
     * @param Bureau_subjection
     * @return
     */
    @Select("select count(1) isExist from t_sys_code t1 join (select id from t_sys_code_type where name in ( \"配属局段\" )) t2 " +
            "on t1.codeTypeId = t2.id where t1.name like '%#{BUREAU_SUBJECTION}%'")
    public Map<String,Integer> getBureauPlace_subjectionExist(@Param("BUREAU_SUBJECTION") String Bureau_subjection);

    /*
      查询条件
     */
    //车型
    public List<THM_FS47_ALLOCATION> getListTrainModel(@Param("TRAIN_TYPE") String train_type)throws DataAccessException;
    //车号
    public List<THM_FS47_ALLOCATION> getListTrainNum(@Param("TRAIN_NUM") String train_num)throws DataAccessException;
    //造修厂家
    public List<THM_FS47_ALLOCATION> getListManufacturer(@Param("MANUFACTURER") String manufacturer)throws DataAccessException;
    //隶属路局
    public List<THM_FS47_ALLOCATION> getListBureau_subjection(@Param("BUREAU_SUBJECTION") String bureau_subjection)throws DataAccessException;
    //配属段所
    public List<THM_FS47_ALLOCATION> getListPlace_subjection(@Param("PLACE_SUBJECTION") String place_subjection)throws DataAccessException;
    //车修程类别
    public List<THM_FS47_ALLOCATION> getListRepair_level(@Param("REPAIR_LEVEL") String repair_level)throws DataAccessException;
    //产品编号
    public List<THM_FS47_ALLOCATION> getListMotor_serial_num(@Param("MOTOR_SERIAL_NUM") String motor_serial_num)throws DataAccessException;

}
