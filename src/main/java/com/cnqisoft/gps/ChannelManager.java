package com.cnqisoft.gps;

import io.netty.channel.ChannelId;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Binary on 2020/5/6
 */
public class ChannelManager {

    private static Map<ChannelId, Long> channelMap = new ConcurrentHashMap<>();

    public static void put(ChannelId channelId, Long imei) {
        channelMap.put(channelId, imei);
    }

    public static Long get(ChannelId channelId) {
        return channelMap.get(channelId);
    }

    public static void remove(ChannelId channelId) {
        channelMap.remove(channelId);
    }
}
