package com.gsralex.gflow.executor;

import com.gsralex.gflow.executor.client.ExecuteTaskService;
import com.gsralex.gflow.executor.service.ExecuteTaskServiceImpl;
import com.gsralex.gflow.rpc.netty.server.RpcServer;

import java.util.Properties;

public class ExecutorServer {

    private Properties properties;
    private ExecutorContext context;

    public ExecutorServer() {
        context = new ExecutorContext();
    }

    public void setConfig(Properties properties) {
        this.properties = properties;
    }

    public void start() {
        RpcServer rpcServer = new RpcServer();
        rpcServer.start((int) properties.getOrDefault(Config.PORT, Config.PORT_DEFAULT));
        rpcServer.registerHandler(ExecuteTaskService.class, new ExecuteTaskServiceImpl(context));
    }
}
