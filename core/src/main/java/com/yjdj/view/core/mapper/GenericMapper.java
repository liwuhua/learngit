package com.yjdj.view.core.mapper;

import java.util.List;

import com.yjdj.view.core.entity.mybeans.*;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import org.springframework.dao.DataAccessException;

/**
 * Created by wangkai on 16/10/20.
 */
@MyBatisRepository
public interface GenericMapper extends BaseMapper{

    /**
     * 获取所有评估类
     * @return
     */
    public List<MdmValClass> getListValclass(
            @Param("val_class") String val_class,
            @Param("txtmd") String txtmd,
            @Param("start") Integer start,
            @Param("limit") Integer limit);

    /**
     * 获取所有物料组
     * @return
     */
    public List<MdmMatlGroup> getListMatkl(
            @Param("matl_group") String matl_group,
            @Param("txtmd") String txtmd,
            @Param("start") Integer start,
            @Param("limit") Integer limit);

    /**
     * 获取所有库存地点
     * @return
     */
    public List<MdmTstorLoc> getListLgort(
            @Param("stor_loc") String stor_loc,
            @Param("txtmd") String txtmd,
            @Param("start") Integer start,
            @Param("limit") Integer limit);

    /**
     * 获取tableName中所有的销售订单vbeln
     * @return
     */
    public List<String> getVbeln(
            @Param("tableName") String tableName,
            @Param("vbeln") String vbeln,
            @Param("start") Integer start,
            @Param("limit") Integer limit);


    /**
     * 获取所有的供应商类型
     * @return
     */
    public List<MdmTxasuppty> getListTxasuppty(
            @Param("xasuppty") String xasuppty,
            @Param("txtmd") String txtmd,
            @Param("start") Integer start,
            @Param("limit") Integer limit);


    /**
     * 获取用户列表
     * @param person
     * @param txtmd
     * @return
     * @throws DataAccessException
     */
    public List<MdmTperson> getListPerson(@Param("person") String person,@Param("txtmd") String txtmd)  throws DataAccessException;

    /**
     * 获取MRP控制者
     * @param mrpctl
     * @param txtmd
     * @return
     * @throws DataAccessException
     */
    public List<MdmTmrpcontrl> getListMrpctl(@Param("mrpctl") String mrpctl,@Param("txtmd") String txtmd)  throws DataAccessException;


    /**
     * 获取销售合同类型
     * @param doc_type
     * @param txtsh
     * @return
     * @throws DataAccessException
     */
    public List<MdmTdocType> getListSalesDoc(@Param("doc_type") String doc_type,@Param("txtsh") String txtsh) throws DataAccessException;

    /**
     * 获取采购合同类型
     * @param doc_type
     * @param txtsh
     * @return
     * @throws DataAccessException
     */
    public List<MdmTdocType> getListPurDoc(@Param("doc_type") String doc_type,@Param("txtsh") String txtsh) throws DataAccessException;

    /**
     * 获取合同类型
     * @param tableName
     * @return
     * @throws DataAccessException
     */
    public List<String> getAllAuart(@Param("tableName") String tableName)throws DataAccessException;
    /**
     * 获取客户编号
     * @param tableName
     * @param customer
     * @param txtmd
     * @return
     * @throws DataAccessException
     */
    public List<MdmTcustomer> getAllTcustomer(@Param("tableName") String tableName,
                                              @Param("customer") String customer,
                                              @Param("txtmd") String txtmd)throws DataAccessException;

    /**
     * 获取销售组织权限字段
     * @param strList
     * @return
     */
    @Select("SELECT * from MDM_TSALESORG where FIND_IN_SET(SALESORG,${strList})")
    public List<MdmTsalesOrg> getListTsalesOrgByList(@Param("strList") String strList)throws DataAccessException;

    /**
     * 获取销售部门权限字段
     * @param strList
     * @return
     */
    @Select("SELECT * from MDM_TSALES_OFF where FIND_IN_SET(SALES_OFF,${strList})")
    public List<MdmTsalesOff> getListTsalesOffByList(@Param("strList") String strList)throws DataAccessException;

    /**
     * 查询所有公司代码
     * @param tableName
     * @param comp_code
     * @param txtmd
     * @return
     */
    public List<MdmTcompCode> getAllTcompCode(@Param("tableName") String tableName,
                                              @Param("comp_code") String comp_code,
                                             @Param("txtmd") String txtmd)throws DataAccessException;
    
    
    /**
     * 查询所有公司代码
     * @param tableName
     * @param comp_code
     * @param txtmd
     * @return
     */
    public List<MdmTcompCode> getAllTcompCode24(@Param("tableName") String tableName,
                                              @Param("comp_code") String comp_code,
                                              @Param("txtmd") String txtmd)throws DataAccessException;

    /**
     * 获取公司代码权限字段
     * @param strList
     * @return
     */
    @Select("SELECT * from MDM_TCOMP_CODE where FIND_IN_SET(COMP_CODE,${strList})")
    public List<MdmTcompCode> getListTcompCodeByList(@Param("strList") String strList)throws DataAccessException;

    /**
     * 获取海外公司代码权限字段
     * @param strList
     * @return
     */
    @Select("SELECT * from MDM_TCOMP_CODE where FIND_IN_SET(COMP_CODE,${strList})" +" UNION ALL "+
            "SELECT COMP_CODE,TXTMD from MDM_TCOMP_CODE_OVERSEA where FIND_IN_SET(COMP_CODE,${strList})")
    public List<MdmTcompCode> getListTcompCodeByList_Oversea(@Param("strList") String strList)throws DataAccessException;


    /**
     * 查询所有的采购组
     * @param tableName
     * @param pur_group
     * @param txtsh
     * @return
     */
    public List<MdmTpurGroup> 
    getAllTpurGroup(@Param("tableName") String tableName,
                                              @Param("pur_group") String pur_group,
                                              @Param("txtsh") String txtsh,
                                              @Param("start") int start,
                                              @Param("limit") int limit
                                              )throws DataAccessException;
    
  
    
    /**
     * 查询所有的采购组
     * @param tableName
     * @param pur_group
     * @param txtsh
     * @param list
     * @return
     */
    public List<MdmTpurGroup> getTpurGroupList(@Param("tableName") String tableName,
                                              @Param("pur_group") String pur_group,
                                              @Param("txtsh") String txtsh,
                                              @Param("pur_groups") List<String> list)throws DataAccessException;
    /**
     * 查询所有销售订单工厂
     * @param tableName
     * @param plant
     * @param txtmd
     * @return
     */
    public List<MdmTplant> getAllProducePlant(@Param("tableName") String tableName,
                                        @Param("plant") String plant,
                                        @Param("txtmd") String txtmd)throws DataAccessException;
    
    /**
     * 查询所有生产订单类型
     * @param tableName
     * @param ordTypeCode
     * @param ordTypeCode
     * @param txtmd
     * @return
     */
    public List<TordType> getAllOrderType(@Param("tableName") String tableName,
                                          @Param("plant") String ordTypeCode,
                                          @Param("txtmd") String txtmd)throws DataAccessException;
    
    
    /**
     * 查询所有公司
     * @param tableName
     * @param plant
     * @param txtmd
     * @return
     */
    public List<MdmTplant> getAllTplant(@Param("tableName") String tableName,
                                        @Param("plant") String plant,
                                        @Param("txtmd") String txtmd)throws DataAccessException;

    /**
     * 查询所有供应商列表
     * @param tableName
     * @param vendor
     * @param txtmd
     * @param limit 
     * @param start 
     * @return
     */
    public List<MdmTvendor> getAllTvendor(@Param("tableName") String tableName,
                                          @Param("vendor") String vendor,
                                          @Param("txtmd") String txtmd, 
                                          @Param("start") int start,
                                          @Param("limit") int limit)throws DataAccessException;
    
    
    /**
     * 查询所有工作中心列表   
     * @param tableName
     * @param vendor
     * @param txtmd
     * @return
     */
    public List<MdmWorkcenter> getAllZhwcx(@Param("tableName") String tableName,
                                           @Param("zhwcx") String zhwcx,
                                           @Param("txtmd") String txtmd)throws DataAccessException;
    
    
    
    /**
     * 查询所有配送方式
     * @param tableName
     * @param zpsfs
     * @param txtmd
     * @return
     */
    public List<MdmZpsfs> getAllZpsfs(@Param("tableName") String tableName,
                                      @Param("zpsfs")     String zpsfs,
                                      @Param("txtmd")     String txtmd)throws DataAccessException;

    /**
     * 查询所有组件采购类型
     * @param tableName
     * @param comppurtype
     * @param txtmd
     * @return
     */
    public List<MdmComPurtype> getAllCompPurtype(@Param("tableName") String tableName,
		                                         @Param("comppurtype") String comppurtype,
		                                         @Param("txtmd") String txtmd)throws DataAccessException;
    

    /**
     * 查询所有物料编码
     * @param tableName
     * @param material
     * @param txtmd
     * @return
     */
    public List<MdmTmaterial> getAllTmaterial(@Param("tableName") String tableName,
                                              @Param("material") String material,
                                              @Param("txtmd") String txtmd)throws DataAccessException;

    
    /**
     * 查询所有物料编码   分页展示
     * @param tableName
     * @param material
     * @param txtmd
     * @return
     */
    public List<MdmTmaterial> getTmaterialBylimit( @Param("tableName") String tableName,
                                            @Param("material") String material,
                                            @Param("txtmd") String txtmd,
                                            @Param("start") int start,
                                            @Param("limit") int limit)throws DataAccessException;
    /**
     * 查询所有组件物料编码
     * @param tableName
     * @param material
     * @param txtmd
     * @return
     */
    public List<MdmTmaterial> getCopomatnum(  @Param("tableName") String tableName,
                                              @Param("material") String material,
                                              @Param("txtmd") String txtmd,
                                              @Param("start") int start,
                                              @Param("limit") int limit)throws DataAccessException;
    
    /**
     * 查询所有的销售部门
     * @param tableName
     * @param sales_off
     * @param txtsh
     * @return
     */
    public List<MdmTsalesOff> getAllTsalesOff(@Param("tableName") String tableName,
                                              @Param("sales_off") String sales_off,
                                              @Param("txtsh") String txtsh)throws DataAccessException;

    /**
     * 查询所有的销售组织
     * @param tableName
     * @param salesOrg
     * @param txtlg
     * @return
     */
    public List<MdmTsalesOrg> getAllTsalesOrg(@Param("tableName") String tableName,
                                              @Param("salesOrg") String salesOrg,
                                              @Param("txtlg") String txtlg)throws DataAccessException;

    /**
     * 查询公司代码
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from MDM_TCOMP_CODE ORDER BY COMP_CODE limit #{start},#{limit}")
    public List<MdmTcompCode> getListTcompCode(@Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 根据公司代码编号查询公司代码
     * @param comp_code
     * @return
     */
    @Select("select * from MDM_TCOMP_CODE where COMP_CODE = '${comp_code}'")
    public MdmTcompCode getTcompCode(@Param("comp_code") String comp_code)throws DataAccessException;

    /**
     * 根据公司代码编号模糊查询公司代码
     * @param comp_code
     * @return
     */
    @Select("select * from MDM_TCOMP_CODE where COMP_CODE like '%${comp_code}%' ORDER BY COMP_CODE")
    public List<MdmTcompCode> getListTcompCodeLikeCode(@Param("comp_code") String comp_code)throws DataAccessException;

    /**
     * 根据公司代码编号模糊查询公司代码
     * @param comp_code
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from MDM_TCOMP_CODE where COMP_CODE like '%${comp_code}%' ORDER BY COMP_CODE limit #{start},#{limit}")
    public List<MdmTcompCode> getListTcompCodeLikeCodeLimit(@Param("comp_code") String comp_code,@Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 根据中间说明模糊查询公司代码
     * @param txtmd
     * @return
     */
    @Select("select * from MDM_TCOMP_CODE where TXTMD like '%${txtmd}%' ORDER BY COMP_CODE")
    public List<MdmTcompCode> getListTcompCodeLikeTxtmd(@Param("txtmd") String txtmd)throws DataAccessException;

    /**
     * 根据中间说明模糊查询公司代码
     * @param txtmd
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from MDM_TCOMP_CODE where TXTMD like '%${txtmd}%' ORDER BY COMP_CODE limit #{start},#{limit}")
    public List<MdmTcompCode> getListTcompCodeLikeTxtmdLimit(@Param("txtmd") String txtmd,@Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 根据公司代码编号和中间说明模糊查询公司代码
     * @param comp_code
     * @param txtmd
     * @return
     */
    @Select("select * from MDM_TCOMP_CODE where COMP_CODE like '%${comp_code}%' AND TXTMD like '%${txtmd}%' ORDER BY COMP_CODE")
    public List<MdmTcompCode> getListTcompCodeLike(@Param("comp_code") String comp_code,@Param("txtmd") String txtmd)throws DataAccessException;

    /**
     * 根据公司代码编号和中间说明模糊查询公司代码
     * @param comp_code
     * @param txtmd
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from MDM_TCOMP_CODE where COMP_CODE like '%${comp_code}%' AND TXTMD like '%${txtmd}%' ORDER BY COMP_CODE limit #{start},#{limit}")
    public List<MdmTcompCode> getListTcompCodeLikeLimit(@Param("comp_code") String comp_code,@Param("txtmd") String txtmd,@Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 查询采购组
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from MDM_TPUR_GROUP ORDER BY PUR_GROUP limit #{start},#{limit}")
    public List<MdmTpurGroup> getListTpurGroup(@Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 根据采购组编号查询采购组
     * @param pur_group
     * @return
     */
    @Select("select * from MDM_TPUR_GROUP where PUR_GROUP = '${pur_group}'")
    public MdmTpurGroup getTpurGroup(@Param("pur_group") String pur_group)throws DataAccessException;

    /**
     * 根据采购者编号模糊查询采购组
     * @param pur_group
     * @return
     */
    @Select("select * from MDM_TPUR_GROUP where PUR_GROUP like '%${pur_group}%' ORDER BY PUR_GROUP")
    public List<MdmTpurGroup> getListTpurGroupLikePur(@Param("pur_group") String pur_group)throws DataAccessException;

    /**
     * 根据采购这编号模糊查询采购者
     * @param pur_group
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from MDM_TPUR_GROUP where PUR_GROUP like '%${pur_group}%' ORDER BY PUR_GROUP limit #{start},#{limit}")
    public List<MdmTpurGroup> getListTpurGroupLikePurLimit(@Param("pur_group") String pur_group, @Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 根据简要说明模糊查询采购者
     * @param txtsh
     * @return
     */
    @Select("select * from MDM_TPUR_GROUP where TXTSH like '%${txtsh}%' ORDER BY PUR_GROUP")
    public List<MdmTpurGroup> getListTpurGroupLikeTxtsh(@Param("txtsh") String txtsh)throws DataAccessException;

    /**
     * 根据简要说明模糊查询采购者
     * @param txtsh
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from MDM_TPUR_GROUP where TXTSH like '%${txtsh}%' ORDER BY PUR_GROUP limit #{start},#{limit}")
    public List<MdmTpurGroup> getListTpurGroupLikeTxtshLimit(@Param("txtsh") String txtsh, @Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 模糊查询采购组
     * @param pur_group
     * @param txtsh
     * @return
     */
    @Select("select * from MDM_TPUR_GROUP where PUR_GROUP like '%${pur_group}%' AND TXTSH like '%${txtsh}%' ORDER BY PUR_GROUP")
    public List<MdmTpurGroup> getListTpurGroupLike(@Param("pur_group") String pur_group,@Param("txtsh") String txtsh)throws DataAccessException;

    /**
     * 模糊查询采购者
     * @param pur_group
     * @param txtsh
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from MDM_TPUR_GROUP where PUR_GROUP like '%${pur_group}%' AND TXTSH like '%${txtsh}%' ORDER BY PUR_GROUP limit #{start},#{limit}")
    public List<MdmTpurGroup> getListTpurGroupLikeLimit(@Param("pur_group") String pur_group,@Param("txtsh") String txtsh, @Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 查询工厂列表
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from MDM_TPLANT ORDER BY PLANT limit #{start},#{limit}")
    public List<MdmTplant> getListTplant(@Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 根据工厂编码查询工厂
     * @param plant
     * @return
     */
    @Select("select * from MDM_TPLANT where PLANT = '${plant}'")
    public MdmTplant getTplant(@Param("plant") String plant)throws DataAccessException;

    /**
     * 根据工厂编码列表查询工厂
     * @param strList
     * @return
     */
    @Select("SELECT * from MDM_TPLANT where FIND_IN_SET(plant,${strList})")
    public List<MdmTplant> getListTplantByList(@Param("strList") String strList)throws DataAccessException;

    /**
     * 根据工厂编码模糊查询工厂列表
     * @param plant
     * @return
     */
    @Select("select * from MDM_TPLANT where PLANT like '%${plant}%' ORDER BY PLANT")
    public List<MdmTplant> getListTplantLikePlant(@Param("plant") String plant)throws DataAccessException;

    /**
     * 根据工厂编码模糊查询工厂列表
     * @param plant
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from MDM_TPLANT where PLANT like '%${plant}%' ORDER BY PLANT limit #{start},#{limit}")
    public List<MdmTplant> getListTplantLikePlantLimit(@Param("plant") String plant,@Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 根据简要描述模糊查询工厂列表
     * @param txtsh
     * @return
     */
    @Select("select * from MDM_TPLANT where TXTSH like '%${txtsh}%' ORDER BY PLANT")
    public List<MdmTplant> getListTplantLikeTxtsh(@Param("txtsh") String txtsh)throws DataAccessException;

    /**
     * 根据简要描述模糊查询工厂列表
     * @param txtsh
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from MDM_TPLANT where TXTSH like '%${txtsh}%' ORDER BY PLANT limit #{start},#{limit}")
    public List<MdmTplant> getListTplantLikeTxtshLimit(@Param("txtsh") String txtsh,@Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 模糊查询工厂列表
     * @param plant
     * @param txtsh
     * @return
     */
    @Select("select * from MDM_TPLANT where PLANT like '%${plant}%' AND TXTSH like '%{txtsh}%' ORDER BY PLANT")
    public List<MdmTplant> getListTplantLike(@Param("material") String plant,@Param("txtsh") String txtsh)throws DataAccessException;

    /**
     * 模糊查询工厂列表
     * @param plant
     * @param txtsh
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from MDM_TPLANT where PLANT like '%${plant}%' AND TXTSH like '%{txtsh}%' ORDER BY PLANT limit #{start},#{limit}")
    public List<MdmTplant> getListTplantLikeLimit(@Param("material") String plant,@Param("txtsh") String txtsh,@Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 查询供销商列表
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from MDM_TVENDOR ORDER BY VENDOR limit #{start},#{limit}")
    public List<MdmTvendor> getListTvendor(@Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 根据供应商编号查询供应商
     * @param vendor
     * @return
     */
    @Select("select * from MDM_TVENDOR where VENDOR = '${vendor}'")
    public MdmTvendor getTvendor(@Param("vendor") String vendor)throws DataAccessException;

    /**
     * 根据供应商编号模糊查询供应商列表
     * @param vendor
     * @return
     */
    @Select("select * from MDM_TVENDOR where VENDOR like '%${vendor}%' ORDER BY VENDOR")
    public List<MdmTvendor> getListTvendorLikeVendor(@Param("vendor") String vendor)throws DataAccessException;

    /**
     * 根据供应商编号模糊查询供应商列表
     * @param vendor
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from MDM_TVENDOR where VENDOR like '%${vendor}%' ORDER BY VENDOR limit #{start},#{limit}")
    public List<MdmTvendor> getListTvendorLikeVendorLimit(@Param("vendor") String vendor, @Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 根据中间说明模糊查询供应商列表
     * @param txtmd
     * @return
     */
    @Select("select * from MDM_TVENDOR where TXTMD like '%${txtmd}%' ORDER BY VENDOR")
    public List<MdmTvendor> getListTvendorLikeTxtmd(@Param("txtmd") String txtmd)throws DataAccessException;

    /**
     * 根据中间说明模糊查询供应商列表
     * @param txtmd
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from MDM_TVENDOR where TXTMD like '%${txtmd}%' ORDER BY VENDOR limit #{start},#{limit}")
    public List<MdmTvendor> getListTvendorLikeTxtmdLimit(@Param("txtmd") String txtmd, @Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 根据供销商编码和中间说明模糊查询供销商列表
     * @param vendor
     * @param txtmd
     * @return
     */
    @Select("select * from MDM_TVENDOR where VENDOR like '%${vendor}%' AND TXTMD like '%${txtmd}%' ORDER BY VENDOR")
    public List<MdmTvendor> getListTvendorLike(@Param("vendor") String vendor, @Param("txtmd") String txtmd)throws DataAccessException;

    /**
     * 根据供销商编码和中间说明模糊查询供销商列表
     * @param vendor
     * @param txtmd
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from MDM_TVENDOR where VENDOR like '%${vendor}%' AND TXTMD like '%${txtmd}%' ORDER BY VENDOR limit #{start},#{limit}")
    public List<MdmTvendor> getListTvendorLikeLimit(@Param("vendor") String vendor, @Param("txtmd") String txtmd, @Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 查询物料列表
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from MDM_TMATERIAL ORDER BY MATERIAL limit #{start},#{limit}")
    public List<MdmTmaterial> getListTmaterial(@Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 根据物料编码查询物料
     * @param material
     * @return
     */
    @Select("SELECT * from MDM_TMATERIAL where MATERIAL = '${material}'")
    public MdmTmaterial getTmaterial(@Param("material") String material)throws DataAccessException;

    /**
     * 根据物料编码模糊查询物料列表
     * @param material
     * @return
     */
    @Select("SELECT * from MDM_TMATERIAL where MATERIAL like \"%${material}%\" ORDER BY MATERIAL")
    public List<MdmTmaterial> getListTmaterialLikeMaterial(@Param("material") String material)throws DataAccessException;

    /**
     * 根据物料编码模糊查询物料列表
     * @param material
     * @param start
     * @param limit
     * @return
     */
    @Select("SELECT * from MDM_TMATERIAL where MATERIAL like \"%${material}%\" ORDER BY MATERIAL limit #{start},#{limit}")
    public List<MdmTmaterial> getListTmaterialLikeMaterialLimit(@Param("material") String material, @Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 根据中间说明模糊查询物料列表
     * @param txtmd
     * @return
     */
    @Select("SELECT * from MDM_TMATERIAL where TXTMD like \"%${txtmd}%\" ORDER BY MATERIAL")
    public List<MdmTmaterial> getListTmaterialLikeTxtmd(@Param("txtmd") String txtmd)throws DataAccessException;

    /**
     * 根据中间说明模糊查询物料列表
     * @param txtmd
     * @return
     */
    @Select("SELECT * from MDM_TMATERIAL where TXTMD like \"%${txtmd}%\" ORDER BY MATERIAL limit #{start},#{limit}")
    public List<MdmTmaterial> getListTmaterialLikeTxtmdLimit(@Param("txtmd") String txtmd, @Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 模糊查询物料列表
     * @param material
     * @param txtmd
     * @return
     */
    @Select("SELECT * from MDM_TMATERIAL where MATERIAL like \"%${material}%\" AND TXTMD like \"%${txtmd}%\" ORDER BY MATERIAL")
    public List<MdmTmaterial> getListTmaterialLike(@Param("material") String material, @Param("txtmd") String txtmd)throws DataAccessException;

    /**
     * 模糊查询物料列表
     * @param material
     * @param txtmd
     * @param start
     * @param limit
     * @return
     */
    @Select("SELECT * from MDM_TMATERIAL where MATERIAL like \"%${material}%\" AND TXTMD like \"%${txtmd}%\" ORDER BY MATERIAL limit #{start},#{limit}")
    public List<MdmTmaterial> getListTmaterialLikeLimit(@Param("material") String material, @Param("txtmd") String txtmd, @Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 查询销售部门
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from MDM_TSALES_OFF ORDER BY SALES_OFF limit #{start},#{limit}")
    public List<MdmTsalesOff> getListTsalesOff(@Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 模糊查询销售部门
     * @param sales_off
     * @param txtsh
     * @return
     */
    @Select("SELECT * from MDM_TSALES_OFF where SALES_OFF like \"%${sales_off}%\" AND TXTSH like \"%${txtsh}%\" ORDER BY SALES_OFF")
    public List<MdmTsalesOff> getListTsalesOffLike(@Param("sales_off") String sales_off, @Param("txtsh") String txtsh)throws DataAccessException;

    /**
     * 模糊查询销售部门
     * @param sales_off
     * @param txtsh
     * @param start
     * @param limit
     * @return
     */
    @Select("SELECT * from MDM_TSALES_OFF where SALES_OFF like \"%${sales_off}%\" AND TXTSH like \"%${txtsh}%\" ORDER BY SALES_OFF limit #{start},#{limit}")
    public List<MdmTsalesOff> getListTsalesOffLikeLimit(@Param("sales_off") String sales_off, @Param("txtsh") String txtsh, @Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 根据销售部门编号模糊查询销售部门
     * @param sales_off
     * @return
     */
    @Select("SELECT * from MDM_TSALES_OFF where SALES_OFF like \"%${sales_off}%\" ORDER BY SALES_OFF")
    public List<MdmTsalesOff> getListTsalesOffLikeTsalesOff(@Param("sales_off") String sales_off)throws DataAccessException;

    /**
     * 根据销售部门编号模糊查询销售部门
     * @param sales_off
     * @param start
     * @param limit
     * @return
     */
    @Select("SELECT * from MDM_TSALES_OFF where SALES_OFF like \"%${sales_off}%\" ORDER BY SALES_OFF limit #{start},#{limit}")
    public List<MdmTsalesOff> getListTsalesOffLikeTsalesOffLimit(@Param("sales_off") String sales_off,@Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 根据简要描述模糊查询销售部门
     * @param txtsh
     * @return
     */
    @Select("SELECT * from MDM_TSALES_OFF where TXTSH like \"%${txtsh}%\" ORDER BY SALES_OFF")
    public List<MdmTsalesOff> getListTsalesOffLikeTxtsh(@Param("txtsh") String txtsh)throws DataAccessException;

    /**
     * 根据简要描述模糊查询销售部门
     * @param txtsh
     * @param start
     * @param limit
     * @return
     */
    @Select("SELECT * from MDM_TSALES_OFF where TXTSH like \"%${txtsh}%\" ORDER BY SALES_OFF limit #{start},#{limit}")
    public List<MdmTsalesOff> getListTsalesOffLikeTxtshLimit(@Param("txtsh") String txtsh, @Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 查询销售组织
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from MDM_TSALESORG ORDER BY SALESORG limit #{start},#{limit}")
    public List<MdmTsalesOrg> getListTsalesOrg(@Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 根据销售组织编号查询销售组织
     * @param salesOrg
     * @return
     */
    @Select("SELECT * from MDM_TSALESORG where SALESORG = '${salesOrg}'")
    public MdmTsalesOrg getTsalesOrg(@Param("salesOrg") String salesOrg)throws DataAccessException;

    /**
     * 模糊查询销售组织
     * @param salesOrg
     * @param txtlg
     * @return
     */
    @Select("SELECT * from MDM_TSALESORG where SALESORG like \"%${salesOrg}%\" AND TXTLG like \"%${txtlg}%\" ORDER BY SALESORG")
    public List<MdmTsalesOrg> getListTsalesOrgLike(@Param("salesOrg") String salesOrg, @Param("txtlg") String txtlg)throws DataAccessException;

    /**
     * 模糊查询销售组织
     * @param salesOrg
     * @param txtlg
     * @param start
     * @param limit
     * @return
     */
    @Select("SELECT * from MDM_TSALESORG where SALESORG like \"%${salesOrg}%\" AND TXTLG like \"%${txtlg}%\" ORDER BY SALESORG limit #{start},#{limit}")
    public List<MdmTsalesOrg> getListTsalesOrgLikeLimit(@Param("salesOrg") String salesOrg, @Param("txtlg") String txtlg, @Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 根据销售组织编号模糊查询销售组织
     * @param salesOrg
     * @return
     */
    @Select("SELECT * from MDM_TSALESORG where SALESORG like \"%${salesOrg}%\" ORDER BY SALESORG")
    public List<MdmTsalesOrg> getListTsalesOrgLikeSalesOrg(@Param("salesOrg") String salesOrg)throws DataAccessException;

    /**
     * 根据销售组织编号模糊查询销售组织
     * @param salesOrg
     * @param start
     * @param limit
     * @return
     */
    @Select("SELECT * from MDM_TSALESORG where SALESORG like \"%${salesOrg}%\" ORDER BY SALESORG limit #{start},#{limit}")
    public List<MdmTsalesOrg> getListTsalesOrgLikeSalesOrgLimit(@Param("salesOrg") String salesOrg, @Param("start") int start, @Param("limit") int limit)throws DataAccessException;

    /**
     * 根据长文本描述模糊查询销售组织
     * @param txtlg
     * @return
     */
    @Select("SELECT * from MDM_TSALESORG where TXTLG like \"%${txtlg}%\" ORDER BY SALESORG")
    public List<MdmTsalesOrg> getListTsalesOrgLikeTxtlg(@Param("txtlg") String txtlg)throws DataAccessException;

    /**
     * 根据长文本描述模糊查询销售组织
     * @param txtlg
     * @param start
     * @param limit
     * @return
     */
    @Select("SELECT * from MDM_TSALESORG where TXTLG like \"%${txtlg}%\" ORDER BY SALESORG limit #{start},#{limit}")
    public List<MdmTsalesOrg> getListTsalesOrgLikeTxtlgLimit(@Param("txtlg") String txtlg, @Param("start") int start, @Param("limit") int limit)throws DataAccessException;
    /**
     * 获取当前用户权限数据
     * @param userId
     * @param moduleId
     * @return 
     */
    public List<Map<String,String>> findPermissionsByUserIdAndModuleId(@Param("userId") Long userId, @Param("moduleId") Long moduleId)throws DataAccessException;
    /**
     * 获取当前用户权限
     * @param userId
     * @return 
     */
    public List<Map<String, String>> findPermissionsByUserId(Long userId)throws DataAccessException;
}
