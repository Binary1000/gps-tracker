package com.cnqisoft.gps;

import java.util.BitSet;

/**
 * @author Binary on 2020/5/18
 */
public class GpsUtil {

    public static String parseDate(byte[] dataContent) {
        String year = "20" + dataContent[0];
        String month = Integer.toHexString(dataContent[1]);
        String day = Integer.toHexString(dataContent[2]);
        String hour = "" + (dataContent[3] + 8);
        String minute = "" + dataContent[4];
        String second = "" + dataContent[5];
        return year + "年" + month + "月" + day + "日" + hour + "时" + minute + "分" + second + "秒";
    }

    public static Location parseGpsData(byte[] dataContent) {
        String substring = Utils.byteToHexString(dataContent[6]);
        int gpsLength = Integer.parseInt(substring.substring(0, 1), 16);
        int satelliteCount = Integer.parseInt(substring.substring(1, 2), 16);

        StringBuilder latHexString = new StringBuilder();
        StringBuilder lonHexString = new StringBuilder();
        StringBuilder altHexString = new StringBuilder();
        for (int i = 7; i < 11; i++) {
            latHexString.append(Utils.byteToHexString(dataContent[i]));
        }
        for (int i = 11; i < 15; i++) {
            lonHexString.append(Utils.byteToHexString(dataContent[i]));
        }

        for (int i = 18; i < 20; i++) {
            altHexString.append(Utils.byteToHexString(dataContent[i]));
        }

        Location location = new Location();
        double rawLat = Integer.parseInt(latHexString.toString(), 16);
        double rawLon = Integer.parseInt(lonHexString.toString(), 16);
        int alt = Integer.parseInt(altHexString.toString(), 16);
        double lat = rawLat / 30000 / 60;
        double lon = rawLon / 30000 / 60;

        BitSet bitSet = BitSet.valueOf(new byte[]{dataContent[17], dataContent[16]});
        location.setLongitude(lon);
        location.setLatitude(lat);
        location.setSpeed(dataContent[15]);
        location.setSatellites(satelliteCount);
        location.setLatitudeDirection(bitSet.get(10) ? Latitude.NORTH : Latitude.SOUTH);
        location.setLongitudeDirection(bitSet.get(11) ? Longitude.WEST : Longitude.EAST);
        location.setAltitude(alt);
        location.setOrientation((int) bitSet.get(0, 10).toLongArray()[0]);
        location.setTime(parseDate(dataContent));
        location.setGpsDataLength(gpsLength);
        return location;
    }

    public static Long parseImei(byte[] dataContent) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            stringBuilder.append(Utils.byteToHexString(dataContent[i]));
        }
        return Long.parseLong(stringBuilder.toString());
    }

}
