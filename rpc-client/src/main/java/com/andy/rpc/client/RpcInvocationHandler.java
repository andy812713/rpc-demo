package com.andy.rpc.client;

import com.andy.rpc.server.api.RpcRequest;
import com.andy.rpc.server.api.SerializableUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <p></p>
 *
 * @author AndyWang QQ:295268319
 * @date 2019/6/7 0007 20:37
 */
public class RpcInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
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
