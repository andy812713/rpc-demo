package com.andy.rpc.client;

import java.lang.reflect.Proxy;

/**
 * <p>rpc代理类</p>
 *
 * @author AndyWang QQ:295268319
 * @date 2019/6/7 0007 20:28
 */
public class RpcProxy {

    public Object getInstance(Class<?> service){
        return Proxy.newProxyInstance(service.getClassLoader(),
                new Class[]{service},
                new RpcInvocationHandler());
    }
}
