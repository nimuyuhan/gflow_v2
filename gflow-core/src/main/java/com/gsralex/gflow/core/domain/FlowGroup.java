package com.gsralex.gflow.core.domain;

import java.util.List;

public class FlowGroup {

    private String name;
    private String description;
    private List<FlowDirect> directs;
    private List<FlowItem> items;





    public FlowDirect addDirect(int index){
        FlowDirect direct=new FlowDirect(index);
        directs.add(direct);
        return direct;
    }

    public static void main(String[] args){
        FlowGroup flowGroup=new FlowGroup();
        flowGroup.addDirect(1).next(1);
    }



}
