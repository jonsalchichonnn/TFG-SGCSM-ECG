package com.sgcsm_ecg.entity;

import lombok.Data;

import java.util.List;

@Data
public class DeviceDTO {
    // system info
    private String id;
    private String brand; // Build.BRAND.toUpperCase()
    private String model;
    private String os; // Build.VERSION.RELEASE
    private String api; // String.valueOf(Build.VERSION.SDK_INT)
    private String manufacturer;
    private String hardware;
    private String bootloader;

    // battery info
    private int batLoad;
    private float batTemp;
    private float batVoltage;
    private long batCapacity;
    private String batStatus;
    private String batPowerSrc;
    private String batHealth;
    private String batTech;

    // camera info
    private String frontResolution;
    private int frontWidth, frontHeight;
    private String frontFlash;
    private int rearWidth, rearHeight;
    private String rearResolution;
    private String rearFlash;
//    int rearWidth, rearHeight;

    // graphic info
    private String renderer;
    private String vendor;
    private String openglVersion;
    private int density;
    private int refreshRate;
    private int screenWidth;
    private int screenHeight;
    private double screenInches;
    private String hdrCapabilities;

    // location info
    private String latitude;
    private String longitude;

    // sensors
    private List<SensorModel> sensors;

    // storage
    private String totalRamGb;
    private String usedRamGb;
    private String freeRamGb;
    private double usedRamPct;
    private int totalMemGb;
    private String usedMem;
    private String freeMem;
    private double usedMemPct;

    // Network
    private String status;
    private int downSpeed;
    private int upSpeed;
    private int wifiStrength;
    private int wifiFreq;
    private int wifiFreqGhz;
    private String ip;
    private String realmac;
    private String bssid;
    private String ssid;
    private int linkSpeed;
    private String dns1;
    private String dns2;
    private String gateway;
    private String netmask;
    private int cellSignal;
    private String cellIp;
    private String cellNetworkType;

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", batLoad=" + batLoad +
                ", rearResolution='" + rearResolution + '\'' +
                ", screenInches=" + screenInches +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", sensors=" + sensors +
                ", totalRamGb='" + totalRamGb + '\'' +
                ", totalMemGb=" + totalMemGb +
                ", status='" + status + '\'' +
                '}';
    }

}
