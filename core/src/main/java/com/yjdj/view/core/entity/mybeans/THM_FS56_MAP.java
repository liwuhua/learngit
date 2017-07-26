package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yjdj.view.core.entity.MybatisBase;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by zhuxuan on 2017/2/20.
 */
@Setter
@Getter
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class THM_FS56_MAP extends MybatisBase{
    private String codes; //列编码
    private String txt; //列名
    private String model; //电机型号
    private String codestype; //列数据类型
    private String flag; //是否显示列标签

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("THM_FS56_MAP:[");
        sb.append("{");
        sb.append("codes:");
        sb.append(codes);
        sb.append(",txt:");
        sb.append(txt);
        sb.append(",model:");
        sb.append(model);
        sb.append(",codestype:");
        sb.append(codestype);
        sb.append(",flag:");
        sb.append(flag);
        sb.append("}");
        sb.append("]");

        return sb.toString();
    }
}
