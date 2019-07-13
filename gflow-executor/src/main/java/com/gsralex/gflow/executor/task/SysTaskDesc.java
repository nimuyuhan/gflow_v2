package com.gsralex.gflow.executor.task;

import java.util.Map;

/**
 * @author gsralex
 * @version 2019-07-13
 */
public class SysTaskDesc {

    private Long taskId;
    private String className;
    private Integer type;
    private String bizParms;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBizParms() {
        return bizParms;
    }

    public void setBizParms(String bizParms) {
        this.bizParms = bizParms;
    }
}
