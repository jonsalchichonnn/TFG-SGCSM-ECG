package com.sgcsm_ecg.entity;

public class SensorModel {


    private String name;

    private String type;

    private String vendor;
    private int version;
    private float power;

    public SensorModel(){

    }

    public SensorModel(String name, String type, String vendor, int version, float power) {
        this.name = name;
        this.type = toTitleCase(type);
        this.vendor = vendor;
        this.version = version;
        this.power = power;
    }

    private String toTitleCase(String str) {
        char[] chars = str.toCharArray();
        StringBuilder res = new StringBuilder();
        int start = str.lastIndexOf(".") + 1;
        boolean convertNext = false;
        for (int i = start; i < chars.length; i++) {
            char ch = chars[i];
            if (ch == '_') {
                convertNext = true;
                ch = ' ';
            } else if (convertNext || i == start) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            }
            res.append(ch);
        }

        return res.toString();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getVendor() {
        return vendor;
    }

    public int getVersion() {
        return version;
    }

    public float getPower() {
        return power;
    }
}
