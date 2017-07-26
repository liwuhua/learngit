package com.yjdj.view.core.cache;

/**
 * @author wangjunfeng
 * @date 2013-3-13
 */       
public interface IValueGetter {
    /**
     * 从缓存获取对象为空的时候，调用改接口来从数据库中加载对象
     *
     * @param key Key
     * @return 缓存对象
     * @author wangjunfeng
     * @date 2013-3-13
     */
    Object getValue(String key);
}
