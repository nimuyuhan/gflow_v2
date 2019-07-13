package com.gsralex.gflow.executor.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gsralex.gflow.executor.Constants;
import com.gsralex.gflow.executor.ExecutorContext;
import com.gsralex.gflow.executor.Result;
import com.gsralex.gflow.executor.client.ExecuteTaskService;
import com.gsralex.gflow.executor.persistent.SqliteTaskPersistent;
import com.gsralex.gflow.executor.persistent.TaskPersistent;
import com.gsralex.gflow.executor.task.ExecuteTask;
import com.gsralex.gflow.executor.task.SysTaskDesc;
import com.gsralex.gflow.executor.task.TaskPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gsralex
 * @version 2019-07-13
 */
public class ExecuteTaskServiceImpl implements ExecuteTaskService {

    private static final Logger LOG = LoggerFactory.getLogger(ExecuteTaskServiceImpl.class);
    private TaskPool taskPool;
    private Gson gson = new GsonBuilder().create();

    private ExecutorContext context;

    public ExecuteTaskServiceImpl(ExecutorContext context) {
        this.context = context;
        this.taskPool = new TaskPool(context);
    }

    @Override
    public Result doAction(long actionId, long taskId, String sysParmDesc) {
        //放入线程池执行
        SysTaskDesc taskDesc = gson.fromJson(sysParmDesc, SysTaskDesc.class);
        ExecuteTask executeTask = getExecuteTask(taskDesc.getClassName());
        if (executeTask == null) {
            LOG.error("taskId:{},className:{}", taskId, taskDesc.getClassName());
            return new Result(Constants.CODE_ERR, "");
        }
        //持久化
        TaskPersistent persistent = new SqliteTaskPersistent();
        persistent.addTask(taskId, actionId, sysParmDesc);
        taskPool.execute(taskId, executeTask, taskDesc.getBizParms());
        return new Result(1, "");
    }


    private ExecuteTask getExecuteTask(String className) {
        try {
            Class<ExecuteTask> clazz = (Class<ExecuteTask>) Class.forName(className);
            if (context.getSpringContextHolder().isSpring()) {
                return context.getSpringContextHolder().getBean(clazz);
            } else {
                return clazz.newInstance();
            }
        } catch (IllegalAccessException e) {
            LOG.error(className, e);
        } catch (InstantiationException e) {
            LOG.error(className, e);
        } catch (ClassNotFoundException e) {
            LOG.error(className, e);
        }
        return null;
    }
}
