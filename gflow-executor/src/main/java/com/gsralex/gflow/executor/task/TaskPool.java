package com.gsralex.gflow.executor.task;

import com.gsralex.gflow.executor.Config;
import com.gsralex.gflow.executor.ExecutorContext;
import com.gsralex.gflow.executor.util.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class TaskPool {

    private static final Logger LOG = LoggerFactory.getLogger(TaskPool.class);

    private ThreadFactory namedThreadFactory = new NamedThreadFactory();
    private final ExecutorService pool;

    public TaskPool(ExecutorContext context) {
        int poolSize = PropertiesUtils.getInt(context.getProperties(), Config.POOL_SIZE, Config.POOL_SIZE_DEFAULT);
        int maxPoolSize = PropertiesUtils.getInt(context.getProperties(), Config.MAX_POOL_SIZE, Config.MAX_POOL_SIZE_DEFAULT);
        pool = new ThreadPoolExecutor(poolSize, maxPoolSize,
                50000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

    public void execute(long taskId, ExecuteTask executeTask, String parmDesc) {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    executeTask.doAction(taskId, parmDesc);
                } catch (Exception e) {
                    LOG.error("doAction taskId:{}", taskId, e);
                }
            }
        });
    }
}
