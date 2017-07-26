package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.entity.MybatisBase;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

/**
 * Created by zhuxuan on 2017/3/29.
 */
@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_FS47_TOTAL_DISTANCE extends MybatisBase {
    private String train_type;//车型
    private String train_num;//车号
    private Integer total_distance;//总行走里程
    private Date record_date;//记录日期

}
