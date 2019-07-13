package com.gsralex.gflow.executor.client;

import com.gsralex.gflow.executor.Result;

public interface ExecuteTaskService {

    Result doAction(long taskId, long actionId, String parmDesc) throws Exception;
}
