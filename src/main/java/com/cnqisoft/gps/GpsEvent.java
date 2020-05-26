package com.cnqisoft.gps;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Binary on 2020/4/30
 */
public enum GpsEvent {

    /**
     * 登录
     */
    LOGIN(0x01) {
        @Override
        void handle(GpsMessage gpsMessage) {

            respondGpsMessage(gpsMessage.getChannel(), LOGIN_RESPONSE);

            byte[] dataContent = gpsMessage.getDataContent();

            Long imei = GpsUtil.parseImei(dataContent);
            int version = dataContent[8];
            gpsEventListener.onLogin(new Device(imei, version));

            ChannelManager.put(gpsMessage.getChannel().id(), imei);

        }

    },

    /**
     * GPS数据
     */
    GPS_DATA(0XE3) {
        @Override
        void handle(GpsMessage gpsMessage) {
            onGps(gpsMessage);
        }
    },

    GPS_DATA_OFFLINE(0XE4) {
        @Override
        void handle(GpsMessage gpsMessage) {
            onGps(gpsMessage);
        }
    },

    /**
     * 状态包
     */
    STATUS(0X13) {
        @Override
        void handle(GpsMessage gpsMessage) {
            byte[] dataContent = gpsMessage.getDataContent();
            Long imei = ChannelManager.get(gpsMessage.getChannel().id());
            Status status = new Status();
            status.setBattery(dataContent[0]);
            status.setVersion(dataContent[1]);
            status.setTimeZone(dataContent[2]);
            status.setInterval(dataContent[3]);
            status.setSignal(dataContent[4]);
            status.setImei(imei);

            gpsEventListener.onStatus(status);
        }
    },

    CHARGING_CONNECTED(0X82) {
        @Override
        void handle(GpsMessage gpsMessage) {
            Long imei = ChannelManager.get(gpsMessage.getChannel().id());
            gpsEventListener.onChargingConnected(imei);
        }
    },

    CHARGING_DISCONNECTED(0X83) {
        @Override
        void handle(GpsMessage gpsMessage) {
            Long imei = ChannelManager.get(gpsMessage.getChannel().id());
            gpsEventListener.onChargingDisconnected(imei);
        }
    },

    /**
     * WIFI_LBS
     */
    WIFI_LBS(0X69) {
        @Override
        void handle(GpsMessage gpsMessage) {
            respondLocationMessage(gpsMessage);
        }
    },

    WIFI_LBS_EX(0XEA) {
        @Override
        void handle(GpsMessage gpsMessage) {
            respondLocationMessage(gpsMessage);

        }
    },

    WIFI_LBS_EX_OFFLINE(0XEB) {
        @Override
        void handle(GpsMessage gpsMessage) {
            respondLocationMessage(gpsMessage);
        }
    };

    abstract void handle(GpsMessage gpsMessage);

    private static Map<Byte, GpsEvent> gpsEventMap;

    private static final byte[] LOGIN_RESPONSE = new byte[]{0X78, 0X78, 0X01, 0X01, 0X0D, 0x0A};

    private final byte code;

    private static GpsEventListener gpsEventListener;

    public static void setGpsEventListener(GpsEventListener gpsEventListener) {
        GpsEvent.gpsEventListener = gpsEventListener;
    }

    static {
        GpsEvent[] gpsEvents = values();
        gpsEventMap = new HashMap<>(gpsEvents.length);
        for (GpsEvent gpsEvent : values()) {
            gpsEventMap.put(gpsEvent.code, gpsEvent);
        }
    }

    GpsEvent(int code) {
        this.code = (byte)code;
    }

    public static void respondLocationMessage(GpsMessage gpsMessage) {
        byte[] dataContent = gpsMessage.getDataContent();
        byte[] responseData = new byte[] {0X78, 0X78, 0X07, gpsMessage.getProtocolNumber(), 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0X0D, 0X0A};
        System.arraycopy(dataContent, 0, responseData, 4, 6);
        respondGpsMessage(gpsMessage.getChannel(), responseData);
    }

    private static void onGps(GpsMessage gpsMessage) {
        respondLocationMessage(gpsMessage);

        byte[] dataContent = gpsMessage.getDataContent();
        Long imei = ChannelManager.get(gpsMessage.getChannel().id());
        Location location = GpsUtil.parseGpsData(dataContent);
        location.setImei(imei);
        gpsEventListener.onLocation(location);
    }

    private static void respondGpsMessage(Channel channel, byte[] data) {
        ByteBuf buffer = channel.alloc().buffer(data.length);
        buffer.writeBytes(data);
        channel.writeAndFlush(buffer);
    }

    public static GpsEvent getInstance(byte code) {
        return gpsEventMap.get(code);
    }

}
