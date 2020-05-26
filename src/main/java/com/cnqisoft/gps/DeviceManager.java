package com.cnqisoft.gps;


import io.netty.channel.Channel;

/**
 * @author Binary on 2020/5/14
 */
public class DeviceManager {

    public static long getImeiByChannel(Channel channel) {
        return ChannelManager.get(channel.id());
    }
}
