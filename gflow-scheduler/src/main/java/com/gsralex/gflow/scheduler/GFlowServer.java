package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.core.rpc.netty.server.RpcServer;
import com.gsralex.gflow.scheduler.util.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GFlowServer {

    private static Logger LOG = LoggerFactory.getLogger(GFlowServer.class);
    private Properties defaultProperties;

    public GFlowServer() {
        try {
            loadFileProperties();
        } catch (IOException e) {
        }
    }

    private void loadFileProperties() throws IOException {
        defaultProperties = new Properties();
        InputStream in = null;
        try {
            in = Properties.class.getResourceAsStream("/gflow.properties");
            if (in != null) {
                defaultProperties.load(in);
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public void setProperties(Properties properties) {
        for (String key : properties.stringPropertyNames()) {
            defaultProperties.setProperty(key, properties.getProperty(key));
        }
    }

    public void start() {

    }

    public void startRpc() throws InterruptedException {
        RpcServer rpcServer = new RpcServer();
        int port = PropertyUtils.getInt(defaultProperties, GFlowConfig.SERVER_PORT, GFlowConfig.SERVER_PORT_DEFAULT);
        rpcServer.start(port);
    }

    public static void main(String[] args) {
        GFlowServer server = new GFlowServer();
        Properties properties = new Properties();
        properties.setProperty("gflow.zookeeper.enabled", "true");
        server.setProperties(properties);
        server.start();
    }

}
