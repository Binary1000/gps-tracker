package com.cnqisoft.gps;

/**
 * @author Binary on 2020/5/5
 */
public interface GpsEventListener {

    /**
     * 设备登录回调
     */
    void onLogin(Device device);

    /**
     * 状态包回调
     * @param status 状态包
     */
    void onStatus(Status status);

    /**
     * GPS定位数据回调
     * @param location 位置对象
     */
    void onLocation(Location location);

    /**
     * 充电连接
     */
    void onChargingConnected(long imei);

    /**
     * 充电断开
     */
    void onChargingDisconnected(long imei);
}
