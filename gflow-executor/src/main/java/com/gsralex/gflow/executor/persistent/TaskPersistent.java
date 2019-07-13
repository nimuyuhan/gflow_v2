package com.gsralex.gflow.executor.persistent;

import java.util.List;

public interface TaskPersistent {

    boolean addTask(long taskId,long actionId,String parmDesc);
    TaskEntity getTask(long taskId);
    boolean updateStatus(long taskId);
    boolean incRetryCnt(long taskId);
    List<TaskEntity> getTaskList(int status);

    boolean delTask(long taskId);

}
