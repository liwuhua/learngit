package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.entity.MybatisBase;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by liuchengli on 2016/11/26.
 */
@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_FS22_02 extends MybatisBase {

    private String aufnr;//项目编号
    private String all_detail;//所有二级明细编号和值以 ‘#’ 为分隔符组成的字符串
    
    
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("THM_FS22:[");
        sb.append("{");
        sb.append("aufnr:");
        sb.append(aufnr);
        sb.append(",all_detail:");
        sb.append(all_detail);
        
        sb.append("}");
        sb.append("]");

        return sb.toString();
    }
}
