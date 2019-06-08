package com.andy.rpc.server.v2;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>服务代理</p>
 *
 * @author AndyWang QQ:295268319
 * @date 2019/6/8 0008 21:22
 */
public class RpcServerProxy {

    ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 发布服务
     * @param port
     */
    public void publish(int port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while (true){
                executorService.execute(new ProcessHandler(serverSocket.accept()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        RpcServerProxy proxy = new RpcServerProxy();
        proxy.publish(9999);
    }
}
