package com.yjdj.view.core.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.entity.mybeans.MdmComPurtype;
import com.yjdj.view.core.entity.mybeans.MdmMatlGroup;
import com.yjdj.view.core.entity.mybeans.MdmTcompCode;
import com.yjdj.view.core.entity.mybeans.MdmTcustomer;
import com.yjdj.view.core.entity.mybeans.MdmTmaterial;
import com.yjdj.view.core.entity.mybeans.MdmTplant;
import com.yjdj.view.core.entity.mybeans.MdmTpurGroup;
import com.yjdj.view.core.entity.mybeans.MdmTsalesOff;
import com.yjdj.view.core.entity.mybeans.MdmTsalesOrg;
import com.yjdj.view.core.entity.mybeans.MdmTstorLoc;
import com.yjdj.view.core.entity.mybeans.MdmTvendor;
import com.yjdj.view.core.entity.mybeans.MdmTxasuppty;
import com.yjdj.view.core.entity.mybeans.MdmValClass;
import com.yjdj.view.core.entity.mybeans.MdmWorkcenter;
import com.yjdj.view.core.entity.mybeans.MdmZpsfs;
import com.yjdj.view.core.entity.mybeans.QueryBean;
import com.yjdj.view.core.entity.mybeans.TordType;
import com.yjdj.view.core.mapper.GenericMapper;
import com.yjdj.view.core.service.GenericService;

/**
 * Created by wangkai on 16/10/20.
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class GenericServiceImpl implements GenericService {

    @Autowired
    private GenericMapper genericMapper;

    private final int startNum = 0;
    private final int limitNum = 20;

    @Override
    public String getListValclass(String val_class, String txtmd, int start, int limit) throws JsonProcessingException, DataAccessException {
        List<MdmValClass> list = genericMapper.getListValclass(val_class,txtmd,start,limit);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(list);
    }

    @Override
    public String getListMatkl(String matl_group, String txtmd, int start, int limit) throws JsonProcessingException, DataAccessException {
        List<MdmMatlGroup> matklList = genericMapper.getListMatkl(matl_group,txtmd,start,limit);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(matklList);
    }

    @Override
    public String getListLgort(String stor_loc, String txtmd, int start, int limit) throws JsonProcessingException, DataAccessException {
        List<MdmTstorLoc> storLocList = genericMapper.getListLgort(stor_loc,txtmd,start,limit);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(storLocList);
    }

    @Override
    public String getVbeln(String tableName,String vbeln, int start, int limit) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> stringList = genericMapper.getVbeln(tableName,vbeln,start,limit);
        String json = mapper.writeValueAsString(stringList);
        return json;
    }

    @Override
    public String getListPerson(String person, String txtmd) throws DataAccessException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(genericMapper.getListPerson(person,txtmd));
    }

    @Override
    public String getListMrpctl(String mrpctl, String txtmd) throws DataAccessException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(genericMapper.getListMrpctl(mrpctl,txtmd));
    }

    @Override
    public String getListSalesDoc(String doc_type, String txtsh) throws DataAccessException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(genericMapper.getListSalesDoc(doc_type,txtsh));
    }

    @Override
    public String getListPurDoc(String doc_type, String txtsh) throws DataAccessException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(genericMapper.getListPurDoc(doc_type,txtsh));
    }

    @Override
    public String getAllAuart(String tableName) throws JsonProcessingException,DataAccessException {
        List<String> ccList = genericMapper.getAllAuart(tableName);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ccList);
    }

    @Override
    public String getAllTcustomer(String tableName, String customer, String txtmd) throws JsonProcessingException,DataAccessException {
        List<MdmTcustomer> ccList = genericMapper.getAllTcustomer(tableName,customer,txtmd);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ccList);
    }

    @Override
    public String getTplantJson(List<String> list) throws JsonProcessingException,DataAccessException {
        ObjectMapper mapper = new ObjectMapper();
        String strList = list.toString().replace("[","\'").replace("]","\'").replace(" ","");
        List<MdmTplant> tplants = genericMapper.getListTplantByList(strList);
        return mapper.writeValueAsString(tplants);
    }

    @Override
    public String getListTcompCodeJson(List<String> list) throws JsonProcessingException, DataAccessException {
        ObjectMapper mapper = new ObjectMapper();
        String strList = list.toString().replace("[","\'").replace("]","\'").replace(" ","");
        List<MdmTcompCode> codes = genericMapper.getListTcompCodeByList(strList);
        return mapper.writeValueAsString(codes);
    }

    @Override
    public String getListTcompCodeJson_Oversea(List<String> list) throws JsonProcessingException, DataAccessException {
        ObjectMapper mapper = new ObjectMapper();
        String strList = list.toString().replace("[","\'").replace("]","\'").replace(" ","");
        List<MdmTcompCode> codes = genericMapper.getListTcompCodeByList_Oversea(strList);
        for(int i = 0; i < codes.size(); i++){
            if(codes.get(i).getComp_code().equals("5500") || codes.get(i).getComp_code().equals("5026"))
                codes.remove(i);
        }
        return mapper.writeValueAsString(codes);
    }

    @Override
    public String getSalesOrgJson(List<String> list) throws JsonProcessingException,DataAccessException {
        ObjectMapper mapper = new ObjectMapper();
        String strList = list.toString().replace("[","\'").replace("]","\'").replace(" ","");
        List<MdmTsalesOrg> salesOrgs = genericMapper.getListTsalesOrgByList(strList);
        return mapper.writeValueAsString(salesOrgs);
    }

    @Override
    public String getSalesOffJson(List<String> list) throws JsonProcessingException, DataAccessException {
        ObjectMapper mapper = new ObjectMapper();
        String strList = list.toString().replace("[","\'").replace("]","\'").replace(" ","");
        List<MdmTsalesOff> salesOffs = genericMapper.getListTsalesOffByList(strList);
        return  mapper.writeValueAsString(salesOffs);
    }

    @Override
    public boolean isBool(List<String> compCodeValue, List<String> compCodeSelect, QueryBean queryBean) {
        boolean b = false;
        if(CollectionUtils.isEmpty(compCodeValue)){
            b = true;
        }else if(compCodeValue.size() == compCodeSelect.size()){
            b = true;
        }
        if(b){
//            List<String> list = new ArrayList<String>();
//            for(MdmTcompCode compCode : compCodeSelect){
//                list.add(compCode.getComp_code());
//            }
            queryBean.setCompCodeValue(compCodeSelect);
        }
        return b;
    }

    @Override
    public String getAllTcompCode(String tableName, String comp_code, String txtmd) throws JsonProcessingException,DataAccessException {
        List<MdmTcompCode> ccList = genericMapper.getAllTcompCode(tableName,comp_code,txtmd);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ccList);
    }

    @Override    
    public String getAllTpurGroup(String tableName, String pur_group, String txtsh,int start,int limit) throws JsonProcessingException,DataAccessException {
        List<MdmTpurGroup> ccList = genericMapper.getAllTpurGroup(tableName,pur_group,txtsh,start,limit);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ccList);
    }
    
    @Override
    public String getAllTpurGroup(String tableName, String id, String name, List<String> list) throws JsonProcessingException, DataAccessException {
        List<MdmTpurGroup> ccList = genericMapper.getTpurGroupList(tableName,id,name,list);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ccList);
    }

    @Override
    public String getAllTplant(String tableName, String plant, String txtmd) throws JsonProcessingException,DataAccessException {
        List<MdmTplant> ccList = genericMapper.getAllTplant(tableName,plant,txtmd);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ccList);
    }
    
    
    @Override
    public String getAllProducePlant(String tableName, String plant, String txtmd) throws JsonProcessingException,DataAccessException {
        List<MdmTplant> ccList = genericMapper.getAllProducePlant(tableName,plant,txtmd);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ccList);
    }
    
    @Override
    public String getAllOrderType(String tableName,String ordTypeCode,String txtmd) throws JsonProcessingException,DataAccessException {
        List<TordType> ccList = genericMapper.getAllOrderType(tableName,ordTypeCode,txtmd);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ccList);
    }

    @Override
    public String getAllTvendor(String tableName, String vendor, String txtmd,int start,int limit) throws JsonProcessingException,DataAccessException {
        List<MdmTvendor> ccList = genericMapper.getAllTvendor(tableName,vendor,txtmd,start,limit);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ccList);
    }
    
    //获取所有 工作中心
    @Override    
    public String getAllZhwcx(String tableName, String zhwcx, String txtmd) throws JsonProcessingException,DataAccessException {
        List<MdmWorkcenter> workerList = genericMapper.getAllZhwcx(tableName,zhwcx,txtmd);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(workerList);
    }
    
    
    

    //获取所有的组件采购类型
    @Override
    public String getAllCompPurtype(String tableName,String comppurtype,String txtmd) throws JsonProcessingException,DataAccessException {
        List<MdmComPurtype> ccList = genericMapper.getAllCompPurtype(tableName,comppurtype,txtmd);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ccList);
    }
    
    //获取所有的配送方式 
    @Override
    public String getAllZpsfs(String tableName,String zpsfs,String txtmd) throws JsonProcessingException,DataAccessException {
        List<MdmZpsfs> ccList = genericMapper.getAllZpsfs(tableName,zpsfs,txtmd);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ccList);
    }

    @Override
    public String getAllTmaterial(String tableName, String material, String txtmd) throws JsonProcessingException,DataAccessException {
        List<MdmTmaterial> ccList = genericMapper.getAllTmaterial(tableName,material,txtmd);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ccList);
    }
    
    //分页查询物料编码
    @Override
    public String getTmaterial(String tableName, String material, String txtmd,int start,int limit) throws JsonProcessingException,DataAccessException {
        List<MdmTmaterial> ccList = genericMapper.getTmaterialBylimit(tableName,material,txtmd,start,limit);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ccList);
    }
     
    
    //组件物料编码
    @Override
    public String getCopomatnum(String tableName, String material, String txtmd,int start,int limit) throws JsonProcessingException,DataAccessException {
        List<MdmTmaterial> ccList = genericMapper.getCopomatnum(tableName,material,txtmd,start,limit);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ccList);
    }

    @Override
    public String getAllTsalesOff(String tableName, String sales_off, String txtsh) throws JsonProcessingException,DataAccessException {
        List<MdmTsalesOff> ccList = genericMapper.getAllTsalesOff(tableName,sales_off,txtsh);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ccList);
    }

    @Override
    public String getAllTsalesOrg(String tableName, String salesOrg, String txtlg) throws JsonProcessingException,DataAccessException {
        List<MdmTsalesOrg> ccList = genericMapper.getAllTsalesOrg(tableName,salesOrg,txtlg);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ccList);
    }


    @Override
    public String getListTcompCode(int start) throws JsonProcessingException,DataAccessException {
        return getListTcompCode(start,limitNum);
    }

    @Override
    public String getListTcompCode(int start, int limit) throws JsonProcessingException,DataAccessException {
        List<MdmTcompCode> tcompCodeList = genericMapper.getListTcompCode(start,limit);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(tcompCodeList);
        return json;
    }

    @Override
    public String getListTcompCode(String comp_code, String txtmd) throws JsonProcessingException,DataAccessException {
        return getListTcompCode(comp_code,txtmd,startNum);
    }

    @Override
    public String getListTcompCode(String comp_code, String txtmd, int start) throws JsonProcessingException,DataAccessException {
        return getListTcompCode(comp_code,txtmd,start,limitNum);
    }

    @Override
    public String getListTcompCode(String comp_code, String txtmd, int start, int limit) throws JsonProcessingException,DataAccessException {
        List<MdmTcompCode> tcompCodeList = null;
        if(StringUtils.isNotBlank(comp_code) && StringUtils.isNotBlank(txtmd)){
            tcompCodeList = genericMapper.getListTcompCodeLikeLimit(comp_code,txtmd,start,limit);
        }else if(StringUtils.isNotBlank(comp_code) && !StringUtils.isNotBlank(txtmd)){
            tcompCodeList = genericMapper.getListTcompCodeLikeCodeLimit(comp_code,start,limit);
        }else if(!StringUtils.isNotBlank(comp_code) && StringUtils.isNotBlank(txtmd)){
            tcompCodeList = genericMapper.getListTcompCodeLikeTxtmdLimit(txtmd,start,limit);
        }else {
            tcompCodeList = genericMapper.getListTcompCode(start,limit);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(tcompCodeList);
        return json;
    }

    @Override
    public String getListTpurGroup(int start) throws JsonProcessingException,DataAccessException {
        return getListTpurGroup(start,limitNum);
    }

    @Override
    public String getListTpurGroup(int start, int limit) throws JsonProcessingException,DataAccessException {
        List<MdmTpurGroup> tpurGroupList = genericMapper.getListTpurGroup(start,limit);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(tpurGroupList);
        return json;
    }

    @Override
    public String getListTpurGroup(String pur_group, String txtsh) throws JsonProcessingException,DataAccessException {
        return getListTpurGroup(pur_group,txtsh,startNum);
    }

    @Override
    public String getListTpurGroup(String pur_group, String txtsh, int start) throws JsonProcessingException,DataAccessException {
        return getListTpurGroup(pur_group,txtsh,start,limitNum);
    }

    @Override
    public String getListTpurGroup(String pur_group, String txtsh, int start, int limit) throws JsonProcessingException,DataAccessException {
        List<MdmTpurGroup> tpurGroupList = null;
        if(StringUtils.isNotBlank(pur_group) && StringUtils.isNotBlank(txtsh)){
            tpurGroupList = genericMapper.getListTpurGroupLikeLimit(pur_group,txtsh,start,limit);
        }else if(StringUtils.isNotBlank(pur_group) && !StringUtils.isNotBlank(txtsh)){
            tpurGroupList = genericMapper.getListTpurGroupLikePurLimit(pur_group,start,limit);
        }else if(!StringUtils.isNotBlank(pur_group) && StringUtils.isNotBlank(txtsh)){
            tpurGroupList = genericMapper.getListTpurGroupLikeTxtshLimit(txtsh,start,limit);
        }else {
            tpurGroupList = genericMapper.getListTpurGroup(start,limit);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(tpurGroupList);
        return json;
    }

    @Override
    public String getListTplant(int start) throws JsonProcessingException,DataAccessException {
        return getListTplant(start,limitNum);
    }

    @Override
    public String getListTplant(int start, int limit) throws JsonProcessingException,DataAccessException {
        List<MdmTplant> tplantList = genericMapper.getListTplant(start,limit);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(tplantList);
        return json;
    }

    @Override
    public String getListTplant(String plant, String txtsh) throws JsonProcessingException,DataAccessException {
        return getListTplant(plant,txtsh,startNum);
    }

    @Override
    public String getListTplant(String plant, String txtsh, int start) throws JsonProcessingException,DataAccessException {
        return getListTplant(plant,txtsh,start,limitNum);
    }

    @Override
    public String getListTplant(String plant, String txtsh, int start, int limit) throws JsonProcessingException,DataAccessException {
        List<MdmTplant> tplantList = null;
        if(StringUtils.isNotBlank(plant) && StringUtils.isNotBlank(txtsh)){
            tplantList = genericMapper.getListTplantLikeLimit(plant,txtsh,start,limit);
        }else if(StringUtils.isNotBlank(plant) && !StringUtils.isNotBlank(txtsh)){
            tplantList = genericMapper.getListTplantLikePlantLimit(plant,start,limit);
        }else if(!StringUtils.isNotBlank(plant) && StringUtils.isNotBlank(txtsh)){
            tplantList = genericMapper.getListTplantLikeTxtshLimit(txtsh,start,limit);
        }else {
            tplantList = genericMapper.getListTplant(start,limit);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(tplantList);
        return json;
    }

    @Override
    public String getListTvendor(int start) throws JsonProcessingException,DataAccessException {
        return getListTvendor(start,limitNum);
    }

    @Override
    public String getListTvendor(int start, int limit) throws JsonProcessingException,DataAccessException {
        List<MdmTvendor> tvendorList = genericMapper.getListTvendor(start,limit);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(tvendorList);
        return json;
    }

    @Override
    public String getListTvendor(String vendor, String txtmd) throws JsonProcessingException,DataAccessException {
        return getListTvendor(vendor,txtmd,startNum);
    }

    @Override
    public String getListTvendor(String vendor, String txtmd, int start) throws JsonProcessingException,DataAccessException {
        return getListTvendor(vendor,txtmd,start,limitNum);
    }

    @Override
    public String getListTvendor(String vendor, String txtmd, int start, int limit) throws JsonProcessingException,DataAccessException {
        List<MdmTvendor> tvendorList = null;
        if(StringUtils.isNotBlank(vendor) && StringUtils.isNotBlank(txtmd)){
            tvendorList = genericMapper.getListTvendorLikeLimit(vendor,txtmd,start,limit);
        }else if(StringUtils.isNotBlank(vendor) && !StringUtils.isNotBlank(txtmd)){
            tvendorList = genericMapper.getListTvendorLikeVendorLimit(vendor,start,limit);
        }else if(!StringUtils.isNotBlank(vendor) && StringUtils.isNotBlank(txtmd)){
            tvendorList = genericMapper.getListTvendorLikeTxtmdLimit(txtmd,start,limit);
        }else {
            tvendorList = genericMapper.getListTvendor(start,limit);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(tvendorList);
        return json;
    }

    @Override
    public String getListTvendorType(String vendorType, String txtmd, int start, int limit) throws JsonProcessingException, DataAccessException {
        List<MdmTxasuppty> txasupptypeList = genericMapper.getListTxasuppty(vendorType,txtmd,start,limit);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(txasupptypeList);
        return json;
    }

    @Override
    public String getListTmaterial(int start) throws JsonProcessingException,DataAccessException {
        return getListTmaterial(start,limitNum);
    }

    @Override
    public String getListTmaterial(int start, int limit) throws JsonProcessingException,DataAccessException {
        List<MdmTmaterial> tmaterialList = genericMapper.getListTmaterial(start, limit);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(tmaterialList);
        return json;
    }

    @Override
    public String getListTmaterial(String material, String txtmd) throws JsonProcessingException,DataAccessException {
        return getListTmaterial(material,txtmd,startNum);
    }

    @Override
    public String getListTmaterial(String material, String txtmd, int start) throws JsonProcessingException,DataAccessException {
        return getListTmaterial(material,txtmd,start,limitNum);
    }

    @Override
    public String getListTmaterial(String material, String txtmd, int start, int limit) throws JsonProcessingException,DataAccessException {
        List<MdmTmaterial> tmaterialList = null;
        if(StringUtils.isNotBlank(material) && StringUtils.isNotBlank(txtmd)){
            tmaterialList = genericMapper.getListTmaterialLikeLimit(material,txtmd,start,limit);
        }else if(StringUtils.isNotBlank(material) && !StringUtils.isNotBlank(txtmd)){
            tmaterialList = genericMapper.getListTmaterialLikeMaterialLimit(material,start,limit);
        }else if(!StringUtils.isNotBlank(material) && StringUtils.isNotBlank(txtmd)){
            tmaterialList = genericMapper.getListTmaterialLikeTxtmdLimit(txtmd,start,limit);
        }else if(!StringUtils.isNotBlank(material) && !StringUtils.isNotBlank(txtmd)){
            tmaterialList = genericMapper.getListTmaterial(start,limit);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(tmaterialList);
        return json;
    }

    @Override
    public String getListTsalesOff(int start) throws JsonProcessingException,DataAccessException {
        return getListTsalesOff(start, limitNum);
    }

    @Override
    public String getListTsalesOff(int start, int limit) throws JsonProcessingException,DataAccessException {
        List<MdmTsalesOff> tsalesOfflist = genericMapper.getListTsalesOff(start,limit);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(tsalesOfflist);
        return json;
    }

    @Override
    public String getListTsalesOff(String sales_off, String txtsh) throws JsonProcessingException,DataAccessException {
        return getListTsalesOff(sales_off,txtsh,startNum);
    }

    @Override
    public String getListTsalesOff(String sales_off, String txtsh, int start) throws JsonProcessingException,DataAccessException {
        return getListTsalesOff(sales_off,txtsh,start,limitNum);
    }

    @Override
    public String getListTsalesOff(String sales_off, String txtsh, int start, int limit) throws JsonProcessingException,DataAccessException {
        List<MdmTsalesOff> tsalesOfflist = null;
        if(StringUtils.isNotBlank(sales_off) && StringUtils.isNotBlank(txtsh)){
            tsalesOfflist = genericMapper.getListTsalesOffLikeLimit(sales_off,txtsh,start,limit);
        }else if(StringUtils.isNotBlank(sales_off) && !StringUtils.isNotBlank(txtsh)){
            tsalesOfflist = genericMapper.getListTsalesOffLikeTsalesOffLimit(sales_off,start,limit);
        }else if(!StringUtils.isNotBlank(sales_off) && StringUtils.isNotBlank(txtsh)){
            tsalesOfflist = genericMapper.getListTsalesOffLikeTxtshLimit(txtsh,start,limit);
        }else{
            tsalesOfflist = genericMapper.getListTsalesOff(start,limit);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(tsalesOfflist);
        return json;
    }

    @Override
    public String getListTsalesOrg(int start) throws JsonProcessingException,DataAccessException {
        return getListTsalesOrg(start,limitNum);
    }

    @Override
    public String getListTsalesOrg(int start, int limit) throws JsonProcessingException,DataAccessException {
        List<MdmTsalesOrg> tsalesOrgList = genericMapper.getListTsalesOrg(start,limit);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(tsalesOrgList);
        return json;
    }

    @Override
    public String getListTsalesOrg(String salesOrg, String txtlg) throws JsonProcessingException,DataAccessException {
        return getListTsalesOrg(salesOrg,txtlg,startNum);
    }

    @Override
    public String getListTsalesOrg(String salesOrg, String txtlg, int start) throws JsonProcessingException,DataAccessException {
        return getListTsalesOrg(salesOrg,txtlg,start,limitNum);
    }

    @Override
    public String getListTsalesOrg(String salesOrg, String txtlg, int start, int limit) throws JsonProcessingException,DataAccessException {
        List<MdmTsalesOrg> tsalesOrgList = null;
        if(StringUtils.isNotBlank(salesOrg) && StringUtils.isNotBlank(txtlg)){
            tsalesOrgList = genericMapper.getListTsalesOrgLikeLimit(salesOrg,txtlg,start,limit);
        }else if(StringUtils.isNotBlank(salesOrg) && !StringUtils.isNotBlank(txtlg)){
            tsalesOrgList = genericMapper.getListTsalesOrgLikeSalesOrgLimit(salesOrg,start,limit);
        }else if(!StringUtils.isNotBlank(salesOrg) && StringUtils.isNotBlank(txtlg)){
            tsalesOrgList = genericMapper.getListTsalesOrgLikeTxtlgLimit(txtlg,start,limit);
        }else {
            tsalesOrgList = genericMapper.getListTsalesOrg(start,limit);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(tsalesOrgList);
        return json;
    }

    @Override
    public List<Map<String,String>> getCurrentUserPermissons(Long userId, Long moduleId) throws JsonProcessingException,DataAccessException{
        return genericMapper.findPermissionsByUserIdAndModuleId(userId, moduleId);
    }

    @Override
    public List<Map<String, String>> getCurrentUserPermissons(Long userId) throws JsonProcessingException, DataAccessException {
        return genericMapper.findPermissionsByUserId(userId);
    }
}
