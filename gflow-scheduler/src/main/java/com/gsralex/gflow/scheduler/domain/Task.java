package com.gsralex.gflow.scheduler.domain;

import java.util.Date;

public class Task {
    private Long id;
    private Long actionId;
    private Long jobId;
    private Date startTime;
    private Date endTime;
    private Integer retryCnt;
    private Integer status;
    private Long retryJobId;
    private Date createTime;
    private Date modTime;
    private Integer index;
    private String parmDesc;
    private String schedulerNode;
    private String executorNode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
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

    public Integer getRetryCnt() {
        return retryCnt;
    }

    public void setRetryCnt(Integer retryCnt) {
        this.retryCnt = retryCnt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getRetryJobId() {
        return retryJobId;
    }

    public void setRetryJobId(Long retryJobId) {
        this.retryJobId = retryJobId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModTime() {
        return modTime;
    }

    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getParmDesc() {
        return parmDesc;
    }

    public void setParmDesc(String parmDesc) {
        this.parmDesc = parmDesc;
    }

    public String getSchedulerNode() {
        return schedulerNode;
    }

    public void setSchedulerNode(String schedulerNode) {
        this.schedulerNode = schedulerNode;
    }

    public String getExecutorNode() {
        return executorNode;
    }

    public void setExecutorNode(String executorNode) {
        this.executorNode = executorNode;
    }
}
