package com.cnqisoft.gps;

import io.netty.channel.Channel;


/**
 * @author Binary on 2020/4/30
 */
public class GpsMessage {

    private int packageLength;

    private byte protocolNumber;

    private byte[] dataContent;

    private Channel channel;

    public GpsMessage(int packageLength, byte protocolNumber, byte[] dataContent, Channel channel) {
        this.packageLength = packageLength;
        this.protocolNumber = protocolNumber;
        this.dataContent = dataContent;
        this.channel = channel;
    }

    public int getPackageLength() {
        return packageLength;
    }

    public byte getProtocolNumber() {
        return protocolNumber;
    }

    public byte[] getDataContent() {
        return dataContent;
    }


    public Channel getChannel() {
        return channel;
    }

}
