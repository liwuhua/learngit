package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

/**
 * Created by zhuxuan on 2017/4/7.
 */
@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_FS47_MAP {
    private String train_type;//车型
    private String map_category;//配属类别
    private String map_position;//配属位置
    private Integer map_category_order;//配属类别排序
    private Integer map_position_order;//配属位置排序
}
