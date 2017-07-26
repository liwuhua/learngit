package com.yjdj.view.core.service.impl;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

import com.yjdj.view.core.entity.mybeans.Fs20TreeNode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjdj.view.core.mapper.GenericMapper;
import com.yjdj.view.core.mapper.IReportMapperFS20;
import com.yjdj.view.core.service.IReportServiceFS20;

/**
 * Created by liwuhua on 16/11/7.
 */
@Service
@TransactionConfiguration(defaultRollback = false)
public class ReportServiceImpFS20 implements IReportServiceFS20 {

    @Autowired
    private IReportMapperFS20 ireportmapperfs20;
    private final Logger log = Logger.getLogger(ReportServiceImpFS20.class);

    private Map<String,Fs20TreeNode> tMap = new HashMap<String,Fs20TreeNode>();

    private LinkedBlockingQueue<Fs20TreeNode> treeList = new LinkedBlockingQueue<Fs20TreeNode>();

    private List<String> ltrmiList = new ArrayList<String>();


    private void clearAll(){
        tMap.clear();
        treeList.clear();
        ltrmiList.clear();
    }


    @Override
    public String getListFs20(String matnr,String dwerk,String kdauf, String kdpos, String rsnum) throws JsonProcessingException, DataAccessException {
        ObjectMapper mapper = new ObjectMapper();
        String resultJson = null;
        if (StringUtils.isNotBlank(matnr) &&StringUtils.isNotBlank(dwerk) &&StringUtils.isNotBlank(kdauf) && StringUtils.isNotBlank(kdpos)) {
            HashMap resultMap = new HashMap<>();
            HashMap paraMap = new HashMap<>();
            paraMap.put("matnr", matnr);
            paraMap.put("dwerk", dwerk);
            paraMap.put("kdauf", kdauf);
            paraMap.put("kdpos", kdpos);
            paraMap.put("rsnum", rsnum);
            List<HashMap> orderMarket = ireportmapperfs20.getOrderMarket(paraMap);
            resultMap.put("periodexpense", orderMarket);
            resultJson = mapper.writeValueAsString(resultMap);
        }

        return resultJson;
    }

    @Override
    public String getFs20(String kdauf,String kdpos,String baugr,String aufnr) throws JsonProcessingException, InterruptedException {
        Fs20TreeNode treeNode = itemTreeNode(getData3(null,kdauf,kdpos,baugr,aufnr,null,1));
        
        ObjectMapper mapper = new ObjectMapper();
        
        if(treeNode==null){
        	 return "{}";
        }
        
        List<Map<String, Object>> listMap = treeNode.getListMap();

        Collections.reverse(listMap);

        Map<String,Object> map = new HashMap<String,Object>();

        if(CollectionUtils.isNotEmpty(ltrmiList)){
            Collections.sort(ltrmiList);
            map.put("max",ltrmiList.get(ltrmiList.size()-1));
            map.put("min",ltrmiList.get(0));
        }

        map.put("data",listMap);

        //清除单例模式下的影响
        clearAll();

        return mapper.writeValueAsString(map);
    }

    //获取数据生成树
    public Fs20TreeNode getData3(Fs20TreeNode treeNode,String kdauf,String kdpos,String baugr,String aufnr,String maufnr,int num){
        if(null == treeNode){
            treeNode = new Fs20TreeNode();
        }

        Set<String> strList = ireportmapperfs20.getChildList(kdauf, kdpos, baugr, aufnr);

        if (CollectionUtils.isNotEmpty(strList)) {
            //获取自己数据
            Map<String, Object> map = ireportmapperfs20.getDataByMa(kdauf, kdpos, baugr, aufnr);
            if(map != null && map.size() > 0) {
                map.put("NUM", num);
                treeNode.setId(aufnr);
                treeNode.setFid(maufnr);
                treeNode.setNum(num);
                treeNode.setMap(map);
                treeNode.setChildId(strList);

                treeNode.addMap(map);

                ltrmiList.add(map.get("FTRMI").toString());
                ltrmiList.add(map.get("GLTRP").toString());
            }
            for(String id : strList){
                Fs20TreeNode node = new Fs20TreeNode();
                node = getData3(node,kdauf,kdpos,null,id,aufnr,num+1);

                if(null != node){
                    treeNode.addChildNode(node);
                }
            }


        } else {//如无子节点,则把自己当作节点加入上层结果树中
            Map<String, Object> map = null;
            if(num == 1){
                map = ireportmapperfs20.getDataByMa(kdauf, kdpos, baugr, aufnr);
            }else{
                map = ireportmapperfs20.getDataBySon(kdauf, kdpos, baugr, aufnr);
            }
            if(map != null && map.size() > 0){
                map.put("NUM",num);
                treeNode.setId(aufnr);
                treeNode.setFid(maufnr);
                treeNode.setNum(num);
                treeNode.setChildId(null);
                treeNode.setMap(map);
                treeNode.setChildNode(null);

                treeNode.addMap(map);

                ltrmiList.add(map.get("FTRMI").toString());
                ltrmiList.add(map.get("GLTRP").toString());
            }else{
                return null;
            }
        }


        return treeNode;

    }

    //遍历树
    public Fs20TreeNode itemTreeNode(Fs20TreeNode treeNode) throws InterruptedException {
    	
       if(treeNode==null ){
    	   return treeNode;
    	   
       }else{
    	   tMap.put(treeNode.getId(),treeNode);

           if(CollectionUtils.isNotEmpty(treeNode.getChildNode())){

               for(Fs20TreeNode node : treeNode.getChildNode()){
                   if(node.getChildNode() == null || node.getChildNode().size() == 0){
                       Map<String,Object> map = node.getMap();
                       treeNode.getListMap().add(map);

                       treeNode.addOperChildId(node.getId());

                       //如果所有子节点都挂上了,把当前节点挂在父节点上
                       compare(treeNode);
                   }else{
                       //itemTreeNode(node);
                       treeList.put(node);
                   }
               }

               if(treeList.size()>0){
                   itemTreeNode(treeList.take());
               }
           }

           return treeNode;
    	   
       }

    }


    private void compare(Fs20TreeNode treeNode){
        if(treeNode.comSize() && null != treeNode.getFid()){
            Fs20TreeNode fNode = tMap.get(treeNode.getFid());
            fNode.getListMap().addAll(treeNode.getListMap());

            fNode.addOperChildId(treeNode.getId());

            compare(fNode);
        }
    }



























}
