package com.gsralex.gflow.executor.task.hive;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gsralex.gflow.executor.task.ExecuteTask;

/**
 * @author gsralex
 * @version 2019-07-13
 */
public class HiveExecuteTask implements ExecuteTask {

    private Gson gson = new GsonBuilder().create();

    @Override
    public void doAction(long taskId, String parmDesc) {

    }


}
