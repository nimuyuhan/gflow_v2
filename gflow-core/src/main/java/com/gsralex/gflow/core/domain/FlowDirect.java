package com.gsralex.gflow.core.domain;

import java.util.List;

public class FlowDirect {

    private Integer directIndex;
    private List<Long> nextIndexes;



    public FlowDirect(){
    }

    public FlowDirect(int index){
        this.directIndex=index;
    }


    public FlowDirect next(long...nextIndexes){
        for(long index:nextIndexes) {
            this.nextIndexes.add(index);
        }
        return this;
    }

}
