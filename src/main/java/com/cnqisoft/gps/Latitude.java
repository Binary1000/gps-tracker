package com.cnqisoft.gps;

/**
 * @author Binary on 2020/5/5
 */
public enum Latitude {

    /**
     * 北纬
     */
    NORTH("北纬"),

    /**
     * 南纬
     */
    SOUTH("南纬");

    private String name;

    Latitude(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
