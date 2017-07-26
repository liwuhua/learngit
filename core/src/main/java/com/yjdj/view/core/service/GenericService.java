package com.yjdj.view.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yjdj.view.core.entity.mybeans.MdmTcompCode;
import com.yjdj.view.core.entity.mybeans.QueryBean;


import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;

/**
 * Created by wangkai on 16/10/20.
 */
public interface GenericService {
    /**
     * 模糊查询评估类
     * @param val_class
     * @param txtmd
     * @param start
     * @param limit
     * @return
     * @throws JsonProcessingException
     * @throws DataAccessException
     */
    public String getListValclass(String val_class,String txtmd,int start,int limit) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询物料组
     * @param matl_group
     * @param txtmd
     * @param start
     * @param limit
     * @return
     * @throws JsonProcessingException
     * @throws DataAccessException
     */
    public String getListMatkl(String matl_group,String txtmd,int start,int limit) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询库存地点
     * @param stor_loc
     * @param txtmd
     * @param start
     * @param limit
     * @return
     * @throws JsonProcessingException
     * @throws DataAccessException
     */
    public String getListLgort(String stor_loc,String txtmd,int start,int limit) throws JsonProcessingException,DataAccessException;


    /**
     * 获取销售订单
     * @return
     */
    public String getVbeln(String tableName,String vbeln,int start,int limit) throws JsonProcessingException;

    /**
     * 获取用户列表
     * @param person
     * @param txtmd
     * @return
     * @throws DataAccessException
     * @throws JsonProcessingException
     */
    public String getListPerson(String person,String txtmd) throws DataAccessException, JsonProcessingException;

    /**
     * 获取MRP控制者
     * @param mrpctl
     * @param txtmd
     * @return
     * @throws DataAccessException
     * @throws JsonProcessingException
     */
    public String getListMrpctl(String mrpctl,String txtmd) throws DataAccessException, JsonProcessingException;


    /**
     * 获取销售合同类型
     * @param doc_type
     * @param txtsh
     * @return
     * @throws DataAccessException
     */
    public String getListSalesDoc(String doc_type, String txtsh) throws DataAccessException, JsonProcessingException;

    /**
     * 获取采购合同类型
     * @param doc_type
     * @param txtsh
     * @return
     * @throws DataAccessException
     */
    public String getListPurDoc(String doc_type,String txtsh) throws DataAccessException, JsonProcessingException;

    /**
     * 获取合同类型
     * @param tableName
     * @return
     * @throws JsonProcessingException
     * @throws DataAccessException
     */
    public String getAllAuart(String tableName) throws JsonProcessingException,DataAccessException;

    /**
     * 获取所有的客户代码
     * @param tableName
     * @param customer
     * @param txtmd
     * @return
     * @throws JsonProcessingException
     * @throws DataAccessException
     */
    public String getAllTcustomer(String tableName,String customer,String txtmd) throws JsonProcessingException,DataAccessException;
    /**
     * 获取工厂权限列表
     * @param list
     * @throws JsonProcessingException,DataAccessException
     * @return
     */
    public String getTplantJson(List<String> list) throws JsonProcessingException,DataAccessException;

    /**
     * 获取公司代码权限列表
     * @param list
     * @throws JsonProcessingException,DataAccessException
     * @return
     */
    public String getListTcompCodeJson(List<String> list) throws JsonProcessingException,DataAccessException;

    /**
     * 获取公司代码权限列表（FS19含有海外公司：印度、南非公司，需要从MDM_TCOMP_CODE和MDM_TCOMP_CODE_OVERSEA两个表中取数）
     * @param list
     * @return
     * @throws JsonProcessingException
     * @throws DataAccessException
     */

    public String getListTcompCodeJson_Oversea(List<String> list) throws JsonProcessingException, DataAccessException;
    /**
     * 获取销售组织权限列表
     * @param list
     * @throws JsonProcessingException,DataAccessException
     * @return
     */
    public String getSalesOrgJson(List<String> list) throws JsonProcessingException,DataAccessException;

    /**
     * 获取销售组织权限列表
     * @param list
     * @throws JsonProcessingException,DataAccessException
     * @return
     */
    public String getSalesOffJson(List<String> list) throws JsonProcessingException,DataAccessException;

    /**
     * 判断是否是全部公司代码,如果是,重置querybean
     * @param compCodeValue
     * @param compCodeSelect
     * @param queryBean
     * @throws JsonProcessingException,DataAccessException
     * @return
     */
    public boolean isBool(List<String> compCodeValue, List<String> compCodeSelect, QueryBean queryBean);
    /**
     * 获取所有的公司代码
     * @param tableName
     * @param comp_code
     * @param txtmd
     * @throws JsonProcessingException,DataAccessException
     * @return
     */
    public String getAllTcompCode(String tableName,String comp_code,String txtmd) throws JsonProcessingException,DataAccessException;

    /**
     * 获取所有的采购组
     * @param tableName
     * @param pur_group
     * @param txtsh
     * @param limit 
     * @param start 
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    public String getAllTpurGroup(String tableName,String pur_group,String txtsh, int start, int limit) throws JsonProcessingException,DataAccessException;

    /**
     * 查询所有销售订单工厂
     * @param tableName
     * @param plant
     * @param txtmd
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    public String getAllTplant(String tableName,String plant,String txtmd) throws JsonProcessingException,DataAccessException;

    /**
     * 查询所有生产工厂
     * @param tableName
     * @param plant
     * @param txtmd
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    public String getAllProducePlant(String tableName,String plant,String txtmd) throws JsonProcessingException,DataAccessException;
    
    
    
    /**
     * 查询所有生产订单类型
     * @param tableName
     * @param ordTypeCode
     * @param txtmd
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    public String getAllOrderType(String tableName,String ordTypeCode,String txtmd) throws JsonProcessingException,DataAccessException;
 

    
    /**
     * 查询所有供应商列表
     * @param tableName
     * @param vendor
     * @param txtmd
     * @param limit 
     * @param start 
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    public String getAllTvendor(String tableName,String vendor,String txtmd, int start, int limit) throws JsonProcessingException,DataAccessException;

    
    /**
     * 查询所有工作中心
     * @param tableName
     * @param zhwcx
     * @param txtmd
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    public String getAllZhwcx(String tableName,String zhwcx,String txtmd) throws JsonProcessingException,DataAccessException;

      
    /**
     * 查询所有配送方式
     * @param tableName
     * @param zpsfs
     * @param txtmd
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    public String getAllZpsfs(String tableName,String zpsfs,String txtmd) throws JsonProcessingException,DataAccessException;
    
    
    /**
     * 查询所有 组件采购类型
     * @param tableName
     * @param comppurtype
     * @param txtmd
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    public String getAllCompPurtype(String tableName,String comppurtype,String txtmd) throws JsonProcessingException,DataAccessException;
    
   
    
    /**
     * 查询所有的物料编码   只针对字段名为  matnr\
     * @param tableName
     * @param material
     * @param txtmd
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    public String getAllTmaterial(String tableName,String material,String txtmd) throws JsonProcessingException,DataAccessException;

    
    /**
     * 查询所有的物料编码   只针对字段名为  matnr  分页查询
     * @param tableName
     * @param material
     * @param txtmd
     * @param start
     * @param limit
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    public String getTmaterial(String tableName,String material,String txtmd,int start,int limit) throws JsonProcessingException,DataAccessException;
    
    
    
    
    /**
     * 查询所有的物料编码   针对字段名不为  matnr
     * @param tableName
     * @param material
     * @param txtmd
     * @param limit 
     * @param start 
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    public String getCopomatnum(String tableName,String material,String txtmd, int start, int limit) throws JsonProcessingException,DataAccessException;
    
    
    
    
    /**
     * 查询所有的销售部门
     * @param tableName
     * @param sales_off
     * @param txtsh
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    public String getAllTsalesOff(String tableName,String sales_off,String txtsh) throws JsonProcessingException,DataAccessException;

    /**
     * 查询所有的销售组织
     * @param tableName
     * @param salesOrg
     * @param txtlg
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    public String getAllTsalesOrg(String tableName,String salesOrg,String txtlg) throws JsonProcessingException,DataAccessException;

    /**
     * 查询公司代码
     * @param start
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTcompCode(int start) throws JsonProcessingException,DataAccessException;

    /**
     * 查询公司代码
     * @param start
     * @param limit
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTcompCode(int start,int limit) throws JsonProcessingException,DataAccessException;

    /**
     * 根据公司代码编号和中间说明查询公司代码
     * @param comp_code
     * @param txtmd
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTcompCode(String comp_code,String txtmd) throws JsonProcessingException,DataAccessException;

    /**
     * 根据公司代码编号和中间说明查询公司代码
     * @param comp_code
     * @param txtmd
     * @param start
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTcompCode(String comp_code,String txtmd,int start) throws JsonProcessingException,DataAccessException;

    /**
     * 根据公司代码编号和中间说明查询公司代码
     * @param comp_code
     * @param txtmd
     * @param start
     * @param limit
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTcompCode(String comp_code,String txtmd,int start, int limit) throws JsonProcessingException,DataAccessException;

    /**
     * 查询采购组
     * @param start
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTpurGroup(int start) throws JsonProcessingException,DataAccessException;

    /**
     * 查询采购组
     * @param start
     * @param limit
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTpurGroup(int start,int limit) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询采购组
     * @param pur_group
     * @param txtsh
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTpurGroup(String pur_group,String txtsh) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询采购组
     * @param pur_group
     * @param txtsh
     * @param start
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTpurGroup(String pur_group,String txtsh,int start) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询采购组
     * @param pur_group
     * @param txtsh
     * @param start
     * @param limit
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTpurGroup(String pur_group,String txtsh,int start,int limit) throws JsonProcessingException,DataAccessException;

    /**
     * 查询工厂
     * @param start
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTplant(int start) throws JsonProcessingException,DataAccessException;

    /**
     * 查询工厂
     * @param start
     * @param limit
     * @throws JsonProcessingException,DataAccessException
     * @return
     */
    @Deprecated
    public String getListTplant(int start,int limit) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询工厂列表
     * @param plant
     * @param txtsh
     * @throws JsonProcessingException,DataAccessException
     * @return
     */
    @Deprecated
    public String getListTplant(String plant,String txtsh) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询工厂列表
     * @param plant
     * @param txtsh
     * @param start
     * @throws JsonProcessingException,DataAccessException
     * @return
     */
    @Deprecated
    public String getListTplant(String plant,String txtsh,int start) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询工厂列表
     * @param plant
     * @param txtsh
     * @param start
     * @param limit
     * @throws JsonProcessingException,DataAccessException
     * @return
     */
    @Deprecated
    public String getListTplant(String plant,String txtsh,int start,int limit) throws JsonProcessingException,DataAccessException;

    /**
     * 查询供应商列表
     * @param start
     * @throws JsonProcessingException,DataAccessException
     * @return
     */
    @Deprecated
    public String getListTvendor(int start) throws JsonProcessingException,DataAccessException;

    /**
     * 查询供应商列表
     * @param start
     * @param limit
     * @throws JsonProcessingException,DataAccessException
     * @return
     */
    @Deprecated
    public String getListTvendor(int start,int limit) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询供应商列表
     * @param vendor
     * @param txtmd
     * @throws JsonProcessingException,DataAccessException
     * @return
     */
    @Deprecated
    public String getListTvendor(String vendor,String txtmd) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询供应商列表
     * @param vendor
     * @param txtmd
     * @param start
     * @throws JsonProcessingException,DataAccessException
     * @return
     */
    @Deprecated
    public String getListTvendor(String vendor,String txtmd,int start) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询供应商列表
     * @param vendor
     * @param txtmd
     * @param start
     * @param limit
     * @throws JsonProcessingException,DataAccessException
     * @return
     */
    @Deprecated
    public String getListTvendor(String vendor,String txtmd,int start,int limit) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询供应商类别
     * @param vendorType
     * @param txtmd
     * @param start
     * @param limit
     * @return
     * @throws JsonProcessingException
     * @throws DataAccessException
     */
    public String getListTvendorType(String vendorType,String txtmd,int start,int limit) throws JsonProcessingException,DataAccessException;



    /**
     * 查询物料查询
     * @param start
     * @throws JsonProcessingException,DataAccessException
     * @return
     * 
     */
    public String getListTmaterial(int start) throws JsonProcessingException,DataAccessException;

    /**
     * 查询物料列表
     * @param start
     * @param limit
     * @throws JsonProcessingException,DataAccessException
     * @return
     */
    public String getListTmaterial(int start,int limit) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询物料列表
     * @param material
     * @param txtmd
     * @param start
     * @throws JsonProcessingException,DataAccessException
     * @return
     */
    public String getListTmaterial(String material,String txtmd,int start) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询物料列表
     * @param material
     * @param txtmd
     * @param start
     * @param limit
     * @throws JsonProcessingException,DataAccessException
     * @return
     */
    public String getListTmaterial(String material,String txtmd,int start,int limit) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询物料列表
     * @param material
     * @param txtmd
     * @throws JsonProcessingException,DataAccessException
     * @return
     */
    public String getListTmaterial(String material,String txtmd) throws JsonProcessingException,DataAccessException;

    /**
     * 查询销售部门
     * @param start
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTsalesOff(int start) throws JsonProcessingException,DataAccessException;

    /**
     * 查询销售部门
     * @param start
     * @param limit
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTsalesOff(int start,int limit) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询销售部门
     * @param sales_off
     * @param txtsh
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTsalesOff(String sales_off,String txtsh) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询销售部门
     * @param sales_off
     * @param txtsh
     * @param start
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTsalesOff(String sales_off,String txtsh,int start) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询销售部门
     * @param sales_off
     * @param txtsh
     * @param start
     * @param limit
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTsalesOff(String sales_off,String txtsh,int start,int limit) throws JsonProcessingException,DataAccessException;

    /**
     * 查询销售组织
     * @param start
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTsalesOrg(int start) throws JsonProcessingException,DataAccessException;

    /**
     * 查询销售组织
     * @param start
     * @param limit
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTsalesOrg(int start,int limit) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询销售组织
     * @param salesOrg
     * @param txtlg
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTsalesOrg(String salesOrg,String txtlg) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询销售组织
     * @param salesOrg
     * @param txtlg
     * @param start
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTsalesOrg(String salesOrg,String txtlg,int start) throws JsonProcessingException,DataAccessException;

    /**
     * 模糊查询销售组织
     * @param salesOrg
     * @param txtlg
     * @param start
     * @param limit
     * @return
     * @throws JsonProcessingException,DataAccessException
     */
    @Deprecated
    public String getListTsalesOrg(String salesOrg,String txtlg,int start,int limit) throws JsonProcessingException,DataAccessException;

    /**
     * 获取当前用户权限数据
     * @param userId
     * @param moduleId
     * @return 
     * @throws com.fasterxml.jackson.core.JsonProcessingException 
     */
    public List<Map<String,String>> getCurrentUserPermissons(Long userId,Long moduleId)throws JsonProcessingException,DataAccessException;

    /**
     * 获取当前用户权限范围内的采购组
     * @param tableName
     * @param id
     * @param name
     * @param list
     * @return 
     * @throws com.fasterxml.jackson.core.JsonProcessingException 
     */
    public String getAllTpurGroup(String tableName, String id, String name, List<String> list)throws JsonProcessingException,DataAccessException;
    /**
     * 获取当前用户权限数据
     * @param userId
     * @return
     * @throws JsonProcessingException
     * @throws DataAccessException 
     */
    public List<Map<String, String>> getCurrentUserPermissons(Long userId)throws JsonProcessingException,DataAccessException;



	
}
