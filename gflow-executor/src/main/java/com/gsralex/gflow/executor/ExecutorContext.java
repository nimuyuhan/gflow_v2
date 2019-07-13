package com.gsralex.gflow.executor;

import com.gsralex.gflow.executor.spring.SpringContextHolder;
import com.gsralex.gflow.rpc.netty.client.RpcClientFactory;
import com.gsralex.gflow.rpc.netty.client.RpcClientManager;
import com.gsralex.gflow.scheduler.client.ScheduleService;

import java.util.Properties;

public class ExecutorContext {

    private Properties properties;
    private SpringContextHolder springContextHolder;


    public void ack(long taskId, boolean ok) {
        RpcClientManager rpcClientManager = new RpcClientManager();
        ScheduleService service = RpcClientFactory.create(ScheduleService.class, rpcClientManager);
        service.ack(taskId, ok);
    }


    public SpringContextHolder getSpringContextHolder() {
        return springContextHolder;
    }

    public Properties getProperties(){
        return properties;
    }

}
