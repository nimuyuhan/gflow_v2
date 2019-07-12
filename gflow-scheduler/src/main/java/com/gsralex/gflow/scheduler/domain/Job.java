package com.gsralex.gflow.scheduler.domain;

import java.util.Date;

public class Job {

    private Long id;
    private Date startTime;
    private Date endTime;
    private Date createTime;
    private Long flowId;
    private Long flowVersionId;
    private Integer status;
    private Long timerConfigId;
    private String parameter;
    private Integer startIndex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public Long getFlowVersionId() {
        return flowVersionId;
    }

    public void setFlowVersionId(Long flowVersionId) {
        this.flowVersionId = flowVersionId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTimerConfigId() {
        return timerConfigId;
    }

    public void setTimerConfigId(Long timerConfigId) {
        this.timerConfigId = timerConfigId;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }
}
