package com.cnqisoft;

import java.io.OutputStream;
import java.math.BigInteger;

/**
 * @author Binary on 2020/4/30
 */
public class MessageEntity {

    private int protocolNumber;

    private byte[] dataContent;

    private OutputStream outputStream;

    public MessageEntity(int protocolNumber, byte[] dataContent, OutputStream outputStream) {
        this.protocolNumber = protocolNumber;
        this.dataContent = dataContent;
        this.outputStream = outputStream;
    }

    public int getProtocolNumber() {
        return protocolNumber;
    }

    public void setProtocolNumber(int protocolNumber) {
        this.protocolNumber = protocolNumber;
    }

    public byte[] getDataContent() {
        return dataContent;
    }

    public void setDataContent(byte[] dataContent) {
        this.dataContent = dataContent;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public long getIMEI() {
        return byteToLong(dataContent);
    }

    public static long byteToLong(byte[] b) {
        long s;
        long s0 = b[0] & 0xff;
        long s1 = b[1] & 0xff;
        long s2 = b[2] & 0xff;
        long s3 = b[3] & 0xff;
        long s4 = b[4] & 0xff;
        long s5 = b[5] & 0xff;
        long s6 = b[6] & 0xff;
        long s7 = b[7] & 0xff;

        // s0不变
        s1 <<= 8;
        s2 <<= 16;
        s3 <<= 24;
        s4 <<= 8 * 4;
        s5 <<= 8 * 5;
        s6 <<= 8 * 6;
        s7 <<= 8 * 7;
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
        return s;
    }
}
