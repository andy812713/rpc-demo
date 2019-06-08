package com.andy.rpc.server.api;

import java.io.*;

/**
 * <p></p>
 *
 * @author AndyWang QQ:295268319
 * @date 2019/6/7 0007 20:59
 */
public class SerializableUtils {

    /**
     * 序列化
     *
     * @param object
     * @return
     */
    public static byte[] serialized(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(object);
            outputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return byteArray;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /**
     * 反序列号
     *
     * @param buf
     * @return
     */
    public static Object deSerialized(byte[] buf) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf);
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(byteArrayInputStream);
            Object o = null;
            try {
                o = inputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return o;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
