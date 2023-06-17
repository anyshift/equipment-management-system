package com.demo.vo;

import java.io.Serializable;

public class ShebeiMonitor {

    private Shebei device;
    private Double currentTemperature;
    private Double currentHumidity;
    private Double currentNoise;
    private Double currentVoltage;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ShebeiMonitor() {
        currentTemperature = 30.0;
        currentHumidity = 50.0;
        currentNoise = 20.0;
        currentVoltage = 220.0;

    }

    public Shebei getDevice() {
        return device;
    }

    public void setDevice(Shebei device) {
        this.device = device;
    }

    public Double getCurrentVoltage() {
        return currentVoltage;
    }

    public void setCurrentVoltage(Double currentVoltage) {
        this.currentVoltage = currentVoltage;
    }

    public Double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(Double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public Double getCurrentHumidity() {
        return currentHumidity;
    }

    public void setCurrentHumidity(Double currentHumidity) {
        this.currentHumidity = currentHumidity;
    }

    public Double getCurrentNoise() {
        return currentNoise;
    }

    public void setCurrentNoise(Double currentNoise) {
        this.currentNoise = currentNoise;
    }
}
