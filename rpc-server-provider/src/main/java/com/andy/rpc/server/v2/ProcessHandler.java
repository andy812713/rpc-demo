package com.andy.rpc.server.v2;

import com.andy.rpc.server.HelloServiceImpl;
import com.andy.rpc.server.api.IHelloService;
import com.andy.rpc.server.api.RpcRequest;
import com.andy.rpc.server.api.SerializableUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p></p>
 *
 * @author AndyWang QQ:295268319
 * @date 2019/6/8 0008 21:40
 */
public class ProcessHandler implements Runnable{

    private static ConcurrentHashMap<Class<?>, Class<?>> implMap = new ConcurrentHashMap();

    static {
        implMap.put(IHelloService.class, HelloServiceImpl.class);
    }

    private Socket socket;
    public ProcessHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            InputStream in = socket.getInputStream();
            byte[] buf = new byte[1024];
            in.read(buf);
            byte[] formatDate = formatData(buf);
            OutputStream out = socket.getOutputStream();
            out.write(formatDate);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static byte[] formatData(byte[] buf) throws Exception {
        RpcRequest request = (RpcRequest) SerializableUtils.deSerialized(buf);
        Class interfaceClazz = Class.forName(request.getServerName());
        Class implClazz = implMap.get(interfaceClazz);
        String[] types = request.getTypes();
        Class<?>[] typeClazzs = null;
        if (types != null) {
            typeClazzs = new Class[types.length];
            for (int i = 0; i < typeClazzs.length; i++) {
                typeClazzs[i] = Class.forName(types[i]);
            }
        }
        Method method = implClazz.getMethod(request.getMethod(), typeClazzs);
        Object object = method.invoke(implClazz.newInstance(), request.getArgs());
        return SerializableUtils.serialized(object);
    }
}
