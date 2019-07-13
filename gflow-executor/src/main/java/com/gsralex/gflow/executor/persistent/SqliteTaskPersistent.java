package com.gsralex.gflow.executor.persistent;

import java.util.List;

public class SqliteTaskPersistent implements TaskPersistent {

    @Override
    public boolean addTask(long taskId, long actionId, String parmDesc) {
        return false;
    }

    @Override
    public TaskEntity getTask(long taskId) {
        return null;
    }

    @Override
    public boolean updateStatus(long taskId) {
        return false;
    }

    @Override
    public boolean incRetryCnt(long taskId) {
        return false;
    }

    @Override
    public List<TaskEntity> getTaskList(int status) {
        return null;
    }

    @Override
    public boolean delTask(long taskId) {
        return false;
    }
}
