package com.yjdj.view.core.entity.mybeans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Created by wangkai on 2017/3/3.
 */

@Setter
@Getter
//Jackson 默认情况下不知道怎么去序列化  需加上如下注释
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class Fs20TreeNode {
    private String id;          //节点id

    private String fid;         //父节点id

    private int num;            //级别

    private Set<String> childId = new HashSet<String>();   //儿子节点id

    private Set<String> operChildId = new HashSet<String>();    //已加入父节点的子节点id

    private List<Fs20TreeNode> childNode = new ArrayList<Fs20TreeNode>();      //子节点

    private Map<String,Object> map = new HashMap<String,Object>();   //数据

    private List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();     //节点背包

    public void addClildId(String chid){
        childId.add(chid);
    }

    public boolean addOperChildId(String operChid){
        operChildId.add(operChid);

        return comSize();
    }

    public boolean comSize(){
        boolean b = false;

        if(childId.size() == operChildId.size()){
            b = true;
        }

        return b;
    }

    public void addChildNode(Fs20TreeNode node){
        childNode.add(node);
    }

    public void addMap(Map<String,Object> map){
        this.listMap.add(map);
    }
}
