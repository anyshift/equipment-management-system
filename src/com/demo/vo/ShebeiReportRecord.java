package com.demo.vo;

public class ShebeiReportRecord {
    private Long id;
    private String name;
    private String reason;
    private String status;
    private Boolean isFault;

    public Boolean getFault() {
        return isFault;
    }

    public void setFault(Boolean fault) {
        isFault = fault;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
