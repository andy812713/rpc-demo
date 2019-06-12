package com.andy.rpc.server.v3;

import com.andy.rpc.server.v2.ProcessHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p></p>
 *
 * @author AndyWang QQ:295268319
 * @date 2019/6/9 0009 17:42
 */
public class RpcServerBySpring implements
        ApplicationContextAware, InitializingBean {

    /**存储扫描到的接口和实现类*/
    Map<String,Object> serviceMap = new HashMap<>();

    private int port;



    ExecutorService executorService = Executors.newCachedThreadPool();


    RpcServerBySpring(int port){
        this.port = port;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true){
            //BIO阻塞
            executorService.execute(new ProcessHandlerV3(serviceMap, serverSocket.accept()));
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        Map<String, Object> map = applicationContext.getBeansWithAnnotation(RpcServerAnnotation.class);
        if(!map.isEmpty()){
            //将加了注解的类与实现类的关联关系保存起来
            for(Object service : map.values()){
                RpcServerAnnotation annotation = service.getClass().getAnnotation(RpcServerAnnotation.class);
                String serviceName = annotation.value().getName();
                String version = annotation.version();
                if(!StringUtils.isEmpty(version)){
                    serviceName += version;
                }
                serviceMap.put(serviceName, service);
            }
        }
    }
}
