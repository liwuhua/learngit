/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.yjdj.view.core.web.SecurityConstants.java
 * Class:			SecurityConstants
 * Date:			2012-8-9
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.yjdj.view.core.web;


/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-8-9 上午10:47:08 
 */

public interface SecurityConstants {

    /**
     * 显示菜单的标识
     */
    public final static String MENU_ID_DISPLAY = "menu_id_display";
    /**
     * 前台权限
     */
    public final static String MENU_MODULE = "menu_module";
    /**
     * 叶子菜单
     */
    public final static String LEAF_MODULE = "leaf_module";

    /**
     * 是否是管理员
     */
    public final static String IS_ADMIN = "is_admin";
    /**
	 * 登录用户
	 */
	public final static String LOGIN_USER = "login_user";    
	
	/**
	 * 验证码
	 */
	public final static String CAPTCHA_KEY = "captcha_key";
	
	/**
	 * 日志参数
	 */
	public final static String LOG_ARGUMENTS = "log_arguments";
	
	/**
	 * 动态查询，参数前缀
	 */
	public final static String SEARCH_PREFIX = "search_";

    /**
     * 系统标识常量
     */
    public final static String GENERAL = "general";
    public final static String SUBJECT = "subject";
    public final static String ALERT = "alert";
    public final static String RELATION = "relation";
    public final static String SEARCH = "search";
    public final static String REPORT = "report";
    public final static String GROUP = "group";
    public final static String ALERTMANAGER = "alertmanager";
    /**
     * 预警规则，用户信息过滤
     */
    public final static String ALERTUSERGROUPID = "alert_user_groupid";

    /**
     * 是否 前端菜单常量 1 是 0 否
     */
    public final static int ISDISPLAY = 1;
    /**
     * ORG_ID
     */
    public final static String ORG_ID = "org_id";

    /**
     * es_index_type 分行业es查询地址
     */
    public final static String ES_INDEX_TYPE = "es_index_type";
    /**
     * es_index_type 分行业es查询地址
     */
    public final static String ES_ALL = "all";
    /**
     * 栏目组根菜单  sn 配置
     */
    public final static String COLUMNS_ROOT_SN = "columngroup";
}
