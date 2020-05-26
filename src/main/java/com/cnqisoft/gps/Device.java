package com.cnqisoft.gps;

/**
 * @author Binary on 2020/5/6
 */
public class Device {

    private long imei;

    private int version;

    public Device(long imei, int version) {
        this.imei = imei;
        this.version = version;
    }

    public long getImei() {
        return imei;
    }

    public void setImei(long imei) {
        this.imei = imei;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
