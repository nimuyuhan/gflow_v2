package com.gsralex.gflow.executor.persistent;

import java.util.Date;

public class TaskEntity {
    private Long taskId;
    private Long actionId;
    private String parmDesc;
    private Date startTime;
    private Date endTime;
    private Integer retyCnt;
    private Integer status;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public String getParmDesc() {
        return parmDesc;
    }

    public void setParmDesc(String parmDesc) {
        this.parmDesc = parmDesc;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getRetyCnt() {
        return retyCnt;
    }

    public void setRetyCnt(Integer retyCnt) {
        this.retyCnt = retyCnt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
