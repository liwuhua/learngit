package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.entity.MybatisBase;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by liuchengli on 2016/11/28.
 */
@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class MdmSecondCost extends MybatisBase {

    private String id;//项目编号
    private String description;//所有二级明细编号和值以 ‘#’ 为分隔符组成的字符串
    private int column_ex;//该项在Excel表中的列
    
    
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("THM_FS22:[");
        sb.append("{");
        sb.append("id:");
        sb.append(id);
        sb.append(",column_ex:");
        sb.append(column_ex);
        sb.append(",description:");
        sb.append(description);
        sb.append("}");
        sb.append("]");

        return sb.toString();
    }
}
