package com.cnqisoft.gps;

/**
 * @author Binary on 2020/5/5
 */
public enum Longitude {

    /**
     * 东经
     */
    EAST("东经"),

    /**
     * 西经
     */
    WEST("西经");

    private String name;

    Longitude(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
