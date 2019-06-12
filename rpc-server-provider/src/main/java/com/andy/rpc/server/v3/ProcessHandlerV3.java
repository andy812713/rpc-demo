package com.andy.rpc.server.v3;

import com.andy.rpc.server.api.RpcRequest;
import org.springframework.util.StringUtils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 *
 * @author AndyWang QQ:295268319
 * @date 2019/6/12 0012 19:22
 */
public class ProcessHandlerV3 implements Runnable{

    Map<String,Object> serviceMap = new HashMap<>();
    private Socket socket;

    ProcessHandlerV3(Map<String,Object> serviceMap, Socket socket){
        this.serviceMap = serviceMap;
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectInputStream input = null;
        ObjectOutputStream output = null;
        try{
            input = new ObjectInputStream(socket.getInputStream());
            RpcRequest request = (RpcRequest) input.readObject();
            Object obj = invoke(request);
            output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(obj);
            output.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private Object invoke(RpcRequest request) {
        try{
            String serviceName = request.getServerName();

            String version = request.getVersion();
            if(!StringUtils.isEmpty(version)){
                serviceName += version;
            }
            Object implClazz = serviceMap.get(serviceName);
            String[] types = request.getTypes();
            Class<?>[] typeClazzs = null;
            if (types != null) {
                typeClazzs = new Class[types.length];
                for (int i = 0; i < typeClazzs.length; i++) {
                    typeClazzs[i] = Class.forName(types[i]);
                }
            }


            Class interfaceClazz = Class.forName(request.getServerName());
            Method method = interfaceClazz.getMethod(request.getMethod(), typeClazzs);
            Object object = method.invoke(implClazz, request.getArgs());
            return object;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
