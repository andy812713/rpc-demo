package com.andy.rpc.client;

import com.andy.rpc.server.api.RpcRequest;
import com.andy.rpc.server.api.SerializableUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <p>rpc代理类</p>
 *
 * @author AndyWang QQ:295268319
 * @date 2019/6/7 0007 20:28
 */
public class RpcProxy {

    public <T> T getInstance(final Class<T> service){
        return (T) Proxy.newProxyInstance(service.getClassLoader(),
                new Class[]{service},
                new RpcInvocationHandler());
    }

    class RpcInvocationHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            RpcRequest request = new RpcRequest();
            //jdk 动态代理
            Class<?>[] classes = proxy.getClass().getInterfaces();
            String className = classes[0].getName();
            request.setServerName(className);
            request.setArgs(args);
            request.setMethod(method.getName());
            String [] types = null;
            if(args!=null) {
                types = new String [args.length];
                for (int i = 0; i < types.length; i++) {
                    types[i] = args[i].getClass().getName();
                }
            }
            request.setTypes(types);
            byte[] byteArray = SerializableUtils.serialized(request);
            return RpcClient.send(byteArray);
        }
    }
}


