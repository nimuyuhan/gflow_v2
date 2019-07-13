package com.gsralex.gflow.executor.task;

/**
 * @author gsralex
 * @version 2019-07-13
 */
public class ClassExecuteTask implements ExecuteTask {

    @Override
    public void doAction(long taskId, String parmDesc) {
        System.out.println("123456");
    }
}
