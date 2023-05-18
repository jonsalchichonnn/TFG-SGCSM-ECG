package com.sgcsm_ecg.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.Data;

import java.util.List;

@Data
public class DeviceDTO {
    // system info
    @Excel(name = "Device ID", needMerge = true)
    private String id;

    @Excel(name = "Brand", needMerge = true)
    private String brand; // Build.BRAND.toUpperCase()
    @Excel(name = "Model", needMerge = true)
    private String model;

    @Excel(name = "OS Version", needMerge = true)
    private String os; // Build.VERSION.RELEASE

    @Excel(name = "Android API level", needMerge = true)
    private String api; // String.valueOf(Build.VERSION.SDK_INT)

    @Excel(name = "Manufacturer", needMerge = true)
    private String manufacturer;

    @Excel(name = "Hardware", needMerge = true)
    private String hardware;

    @Excel(name = "Bootloader", needMerge = true)
    private String bootloader;

    // battery info

    @Excel(name = "Battery Load", needMerge = true)
    private int batLoad;

    @Excel(name = "Bat Temperature", needMerge = true)
    private float batTemp;

    @Excel(name = "Bat Temperature", needMerge = true)
    private float batVoltage;

    @Excel(name = "Bat Voltage", needMerge = true)
    private long batCapacity;

    @Excel(name = "Bat Status", needMerge = true)
    private String batStatus;

    @Excel(name = "Bat Power Source", needMerge = true)
    private String batPowerSrc;

    @Excel(name = "Bat Health", needMerge = true)
    private String batHealth;

    @Excel(name = "Bat Technology", needMerge = true)
    private String batTech;


    // camera info

    @Excel(name = "Front Camera Pixels", needMerge = true)
    private String frontResolution;

    @Excel(name = "F Cam Width", needMerge = true)
    private int frontWidth;

    @Excel(name = "F Cam Height", needMerge = true)
    private int frontHeight;

    @Excel(name = "F Cam Flash", needMerge = true)
    private String frontFlash;

    @Excel(name = "Rear Cam Pixels", needMerge = true)
    private String rearResolution;

    @Excel(name = "R Cam Width", needMerge = true)
    private int rearWidth;

    @Excel(name = "R Cam Height", needMerge = true)
    private int rearHeight;

    @Excel(name = "R Cam Flash", needMerge = true)
    private String rearFlash;


    // graphic info

    @Excel(name = "Renderer", needMerge = true)
    private String renderer;

    @Excel(name = "Graphic Vendor", needMerge = true)
    private String vendor;

    @Excel(name = "OpenGL Version", needMerge = true)
    private String openglVersion;

    @Excel(name = "HDR Capabilities", needMerge = true)
    private String hdrCapabilities;

    @Excel(name = "Screen Density", needMerge = true)
    private int density;

    @Excel(name = "Refresh Rate", needMerge = true)
    private int refreshRate;

    @Excel(name = "Screen Width", needMerge = true)
    private int screenWidth;

    @Excel(name = "Screen Height", needMerge = true)
    private int screenHeight;

    @Excel(name = "Screen Inches", needMerge = true)
    private double screenInches;

    // location info

    @Excel(name = "Latitude", needMerge = true)
    private String latitude;

    @Excel(name = "Longitude", needMerge = true)
    private String longitude;

    // sensors
    @ExcelCollection(name = "Sensors")
    private List<SensorModel> sensors;

    // storage
    @Excel(name = "Total RAM", needMerge = true)
    private String totalRamGb;

    @Excel(name = "Used RAM", needMerge = true)
    private String usedRamGb;

    @Excel(name = "Free RAM", needMerge = true)
    private String freeRamGb;

    @Excel(name = "Used RAM %", needMerge = true)
    private double usedRamPct;

    @Excel(name = "Total Mem", needMerge = true)
    private int totalMemGb;

    @Excel(name = "Used Mem", needMerge = true)
    private String usedMem;

    @Excel(name = "Free Mem", needMerge = true)
    private String freeMem;

    @Excel(name = "Used Mem %", needMerge = true)
    private double usedMemPct;

    // Network

    @Excel(name = "Status", needMerge = true)
    private String status;

    @Excel(name = "Download Speed", needMerge = true)
    private int downSpeed;

    @Excel(name = "Upload Speed", needMerge = true)
    private int upSpeed;

    @Excel(name = "WiFi Signal", needMerge = true)
    private int wifiStrength;

    @Excel(name = "WiFi Frequncy MHz", needMerge = true)
    private int wifiFreq;

    @Excel(name = "WiFi Frequncy GHz", needMerge = true)
    private int wifiFreqGhz;

    @Excel(name = "WiFi IP", needMerge = true)
    private String ip;

    @Excel(name = "MAC", needMerge = true)
    private String realmac;

    @Excel(name = "BSSID", needMerge = true)
    private String bssid;

    @Excel(name = "SSID", needMerge = true)
    private String ssid;

    @Excel(name = "Link Speed", needMerge = true)
    private int linkSpeed;

    @Excel(name = "DNS1", needMerge = true)
    private String dns1;

    @Excel(name = "DNS2", needMerge = true)
    private String dns2;

    @Excel(name = "Gateway", needMerge = true)
    private String gateway;

    @Excel(name = "Netmask", needMerge = true)
    private String netmask;

    @Excel(name = "Phone Signal", needMerge = true)
    private int cellSignal;

    @Excel(name = "Phone IP", needMerge = true)
    private String cellIp;

    @Excel(name = "Phone Network", needMerge = true)
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
