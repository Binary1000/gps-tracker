package com.cnqisoft.gps;

import java.util.Date;

/**
 * @author Binary on 2020/5/5
 */
public class Location {

    private long imei;

    public long getImei() {
        return imei;
    }

    public void setImei(long imei) {
        this.imei = imei;
    }

    private double longitude;

    private double latitude;

    private int altitude;

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    private int gpsDataLength;

    public int getGpsDataLength() {
        return gpsDataLength;
    }

    public void setGpsDataLength(int gpsDataLength) {
        this.gpsDataLength = gpsDataLength;
    }

    private int speed;

    private int satellites;

    private String time;

    private int orientation;

    private Longitude longitudeDirection;

    private Latitude latitudeDirection;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSatellites() {
        return satellites;
    }

    public void setSatellites(int satellites) {
        this.satellites = satellites;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public Longitude getLongitudeDirection() {
        return longitudeDirection;
    }

    public void setLongitudeDirection(Longitude longitudeDirection) {
        this.longitudeDirection = longitudeDirection;
    }

    public Latitude getLatitudeDirection() {
        return latitudeDirection;
    }

    public void setLatitudeDirection(Latitude latitudeDirection) {
        this.latitudeDirection = latitudeDirection;
    }
}
