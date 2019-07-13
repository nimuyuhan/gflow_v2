package com.gsralex.gflow.executor.persistent;

import com.gsralex.gflow.executor.ExecutorContext;

/**
 * @author gsralex
 * @version 2019-07-13
 */
public class TaskReco {

    public TaskReco(ExecutorContext context) {

    }

    public void resume() {
        TaskPersistent persistent = new SqliteTaskPersistent();

    }
}
