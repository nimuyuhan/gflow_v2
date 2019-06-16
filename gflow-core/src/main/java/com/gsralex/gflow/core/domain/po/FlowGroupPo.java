package com.gsralex.gflow.core.domain.po;

import com.gsralex.gdata.bean.annotation.Column;
import com.gsralex.gdata.bean.annotation.Table;

@Table("gflow_flowgroup")
public class FlowGroupPo {
    private Long id;
    private String name;
    private String description;
    @Column("create_time")
    private Long createTime;
    @Column("mod_time")
    private Long modTime;
    private Boolean del;
    @Column("flow_desc")
    private String flowDesc;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getModTime() {
        return modTime;
    }

    public void setModTime(Long modTime) {
        this.modTime = modTime;
    }

    public Boolean getDel() {
        return del;
    }

    public void setDel(Boolean del) {
        this.del = del;
    }

    public String getFlowDesc() {
        return flowDesc;
    }

    public void setFlowDesc(String flowDesc) {
        this.flowDesc = flowDesc;
    }
}
