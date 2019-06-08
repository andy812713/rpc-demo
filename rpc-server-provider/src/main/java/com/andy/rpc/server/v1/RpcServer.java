package com.andy.rpc.server.v1;

import com.andy.rpc.server.HelloServiceImpl;
import com.andy.rpc.server.api.RpcRequest;
import com.andy.rpc.server.api.SerializableUtils;
import com.andy.rpc.server.api.IHelloService;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p></p>
 *
 * @author AndyWang QQ:295268319
 * @date 2019/6/7 0007 21:40
 */
public class RpcServer {

    /**
     * 通过Map来做接口映射到实现类,从map中取
     * TODO implMap手动写死，可通过Spring自动扫描来进行优化
     */
    private static ConcurrentHashMap<Class<?>, Class<?>> implMap = new ConcurrentHashMap();

    static {
        implMap.put(IHelloService.class, HelloServiceImpl.class);
    }

    /**
     * 创建连接，服务端阻塞，
     * TODO 可采用多线程的方式，每收到一个请求，创建一个连接来优化
     * @throws Exception
     */
    public static void openSocket() throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999);
        try {
            while (true) {
                Socket socket = serverSocket.accept();

                InputStream in = socket.getInputStream();
                byte[] buf = new byte[1024];
                in.read(buf);
                byte[] formatDate = formatData(buf);
                OutputStream out = socket.getOutputStream();
                out.write(formatDate);
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            serverSocket.close();
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

    public static void main(String[] args) {
        try {
            openSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
