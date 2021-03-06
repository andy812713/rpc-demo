package com.andy.rpc.client;

import com.andy.rpc.server.api.IHelloService;
import com.andy.rpc.server.api.RpcRequest;
import com.andy.rpc.server.api.SerializableUtils;


import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * <p></p>
 *
 * @author AndyWang QQ:295268319
 * @date 2019/6/7 0007 20:37
 */
public class RpcClient {

    public static void main(String[] args) {
        RpcProxy rpcProxy = new RpcProxy();
        IHelloService helloService = rpcProxy.getInstance(IHelloService.class);
        System.out.println("getUser = [" + helloService.getUser("andy") + "]");
        System.out.println("sayHello = [" + helloService.sayHello("andy") + "]");
    }

    public static Object send(byte[] bs) throws Exception {
        Socket socket = new Socket("127.0.0.1", 9999);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(bs);
        outputStream.flush();
        InputStream in = socket.getInputStream();
        byte[] buf = new byte[1024];
        in.read(buf);
        Object formatDate = SerializableUtils.deSerialized(buf);
        
        in.close();
        outputStream.close();
        socket.close();
        return formatDate;
    }

    public static Object send(RpcRequest request) throws Exception {
        Socket socket = new Socket("127.0.0.1", 9999);
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(request);
        outputStream.flush();
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        Object obj = in.readObject();

        in.close();
        outputStream.close();
        socket.close();
        return obj;
    }
}
