package com.cnqisoft;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Binary on 2020/4/30
 */
public class Client {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 6789);
        byte[] bytes = new byte[] { 0X78, 0X78, 0X02, 0X01, 0x0A, 0X0D, 0x0A, 0X78, 0X78, 0X02, 0X01, 0X0B, 0X0D, 0X0A};
        socket.getOutputStream().write(bytes);
    }
}
