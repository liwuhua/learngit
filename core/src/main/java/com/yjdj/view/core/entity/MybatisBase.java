
package com.yjdj.view.core.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author  xutao
 *
 */
@Setter
@Getter
public class MybatisBase implements Idable<Long>, Cloneable,Serializable{
	
	/**
	 * 对象复制
	 */
	public Object clone(){  
		MybatisBase mb = null;  
        try {  
        	mb = (MybatisBase)super.clone();  
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return mb;  
    } 
}
