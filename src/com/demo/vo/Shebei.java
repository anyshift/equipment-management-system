package com.demo.vo;

import java.io.Serializable;

/**
 * 设备（t_shebei表对应的Java实体类）
 */
public class Shebei implements Serializable {
    private Long id;//主键
    private String shebeiName;//名称
    private String shebeiNo;//编号
    private String shebeiType;//类型
    private String shebeiCangjia;//生产厂家
    private String shebeiIndate;//采购日期
    private String shebeiInprice;//采购价格
    private String shebeiDept;//使用部门
    private String shebeiStatus;//状态:正常/故障/已报废
    private Boolean shebeiReport; //设备是否已上报故障
    private Boolean shebeiDispatch; //是否已派员工检查设备
    private String shebeiImage; //设备图片
    private Boolean temperatureRecord;
    private Double temperatureThreshold;
    private Boolean humidityRecord;
    private Double humidityThreshold;
    private Boolean noiseRecord;
    private Double noiseThreshold;
    private Boolean voltageRecord;
    private Double voltageThreshold;

    public Boolean getShebeiDispatch() {
        return shebeiDispatch;
    }

    public void setShebeiDispatch(Boolean shebeiDispatch) {
        this.shebeiDispatch = shebeiDispatch;
    }

    public Boolean getTemperatureRecord() {
        return temperatureRecord;
    }

    public void setTemperatureRecord(Boolean temperatureRecord) {
        this.temperatureRecord = temperatureRecord;
    }

    public Double getTemperatureThreshold() {
        return temperatureThreshold;
    }

    public void setTemperatureThreshold(Double temperatureThreshold) {
        this.temperatureThreshold = temperatureThreshold;
    }

    public Boolean getHumidityRecord() {
        return humidityRecord;
    }

    public void setHumidityRecord(Boolean humidityRecord) {
        this.humidityRecord = humidityRecord;
    }

    public Double getHumidityThreshold() {
        return humidityThreshold;
    }

    public void setHumidityThreshold(Double humidityThreshold) {
        this.humidityThreshold = humidityThreshold;
    }

    public Boolean getNoiseRecord() {
        return noiseRecord;
    }

    public void setNoiseRecord(Boolean noiseRecord) {
        this.noiseRecord = noiseRecord;
    }

    public Double getNoiseThreshold() {
        return noiseThreshold;
    }

    public void setNoiseThreshold(Double noiseThreshold) {
        this.noiseThreshold = noiseThreshold;
    }

    public Boolean getVoltageRecord() {
        return voltageRecord;
    }

    public void setVoltageRecord(Boolean voltageRecord) {
        this.voltageRecord = voltageRecord;
    }

    public Double getVoltageThreshold() {
        return voltageThreshold;
    }

    public void setVoltageThreshold(Double voltageThreshold) {
        this.voltageThreshold = voltageThreshold;
    }

    public String getShebeiImage() {
        return shebeiImage;
    }

    public void setShebeiImage(String shebeiImage) {
        this.shebeiImage = shebeiImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getShebeiName() {
        return shebeiName;
    }

    public void setShebeiName(String shebeiName) {
        this.shebeiName = shebeiName;
    }
    public String getShebeiNo() {
        return shebeiNo;
    }

    public void setShebeiNo(String shebeiNo) {
        this.shebeiNo = shebeiNo;
    }
    public String getShebeiType() {
        return shebeiType;
    }

    public void setShebeiType(String shebeiType) {
        this.shebeiType = shebeiType;
    }
    public String getShebeiCangjia() {
        return shebeiCangjia;
    }

    public void setShebeiCangjia(String shebeiCangjia) {
        this.shebeiCangjia = shebeiCangjia;
    }
    public String getShebeiIndate() {
        return shebeiIndate;
    }

    public void setShebeiIndate(String shebeiIndate) {
        this.shebeiIndate = shebeiIndate;
    }
    public String getShebeiInprice() {
        return shebeiInprice;
    }

    public void setShebeiInprice(String shebeiInprice) {
        this.shebeiInprice = shebeiInprice;
    }
    public String getShebeiDept() {
        return shebeiDept;
    }

    public void setShebeiDept(String shebeiDept) {
        this.shebeiDept = shebeiDept;
    }
    public String getShebeiStatus() {
        return shebeiStatus;
    }

    public Boolean getShebeiReport() {
        return shebeiReport;
    }
    public void setShebeiReport(Boolean shebeiReport) {
        this.shebeiReport = shebeiReport;
    }

    public void setShebeiStatus(String shebeiStatus) {
        this.shebeiStatus = shebeiStatus;
    }

    @Override
    public String toString() {
        return "Shebei{" +
                "id=" + id +
                ", shebeiName='" + shebeiName + '\'' +
                ", shebeiNo='" + shebeiNo + '\'' +
                ", shebeiType='" + shebeiType + '\'' +
                ", shebeiCangjia='" + shebeiCangjia + '\'' +
                ", shebeiIndate='" + shebeiIndate + '\'' +
                ", shebeiInprice='" + shebeiInprice + '\'' +
                ", shebeiDept='" + shebeiDept + '\'' +
                ", shebeiStatus='" + shebeiStatus + '\'' +
                ", shebeiReport=" + shebeiReport +
                ", shebeiImage='" + shebeiImage + '\'' +
                ", temperatureRecord=" + temperatureRecord +
                ", temperatureThreshold=" + temperatureThreshold +
                ", humidityRecord=" + humidityRecord +
                ", humidityThreshold=" + humidityThreshold +
                ", noiseRecord=" + noiseRecord +
                ", noiseThreshold=" + noiseThreshold +
                ", voltageRecord=" + voltageRecord +
                ", voltageThreshold=" + voltageThreshold +
                '}';
    }
}