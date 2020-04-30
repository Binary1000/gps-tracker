package com.cnqisoft;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 * @author Binary
 */
public class Server {

    private Callback callback;

    public Server(Callback callback) {
        this.callback = callback;
    }

    public void start() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(6789);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        ExecutorService threadPool = Executors.newCachedThreadPool();
        while (true) {
            Socket socket;
            InputStream inputStream;
            try {
                socket = serverSocket.accept();
                inputStream = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            byte[] buffer = new byte[1024];

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            threadPool.execute(() -> {
                while (true) {
                    try {
                        int read = inputStream.read(buffer);
                        byteArrayOutputStream.write(buffer, 0, read);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        for (int i = 0; i < bytes.length;) {
                            if (bytes[i] == 0X78 && bytes[i + 1] == 0X78) {

                                int length = bytes[i + 2];
                                int protocolNumber = bytes[i + 3];
                                byte[] dataPackage = new byte[5 + length];
                                System.arraycopy(bytes, i, dataPackage, 0, 5 + length);
                                byte[] dataContent = new byte[length - 1];
                                System.arraycopy(bytes, i + 4, dataContent, 0, length - 1);
                                System.out.println(byteToHex(dataPackage));
                                System.out.println("数据长度" + length);
                                System.out.println("协议号" + protocolNumber);
                                System.out.println("数据内容" + byteToHex(dataContent));
                                System.out.println();
                                callback.onDataReceived(new MessageEntity(protocolNumber, dataContent, socket.getOutputStream()));
                                i = length + 4;
                            }
                        }
                        byteArrayOutputStream.reset();
                    } catch (IOException e) {
                        break;
                    }
                }
            });
        }
    }

    public static String byteToHex(byte[] bytes) {
        String strHex = "";
        StringBuilder sb = new StringBuilder("");
        for (byte aByte : bytes) {
            strHex = Integer.toHexString(aByte & 0xFF);
            sb.append("0x").append((strHex.length() == 1) ? "0" + strHex : strHex).append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }
}
