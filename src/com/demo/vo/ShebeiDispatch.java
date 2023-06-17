package com.demo.vo;

public class ShebeiDispatch {
    private Long uid;
    private Long deviceId;
    private String reason;
    private String createTime;
    private Boolean finished;
    private String finishedText;
    private Shebei device;

    public String getFinishedText() {
        return finishedText;
    }

    public void setFinishedText(String finishedText) {
        this.finishedText = finishedText;
    }

    public Shebei getDevice() {
        return device;
    }

    public void setDevice(Shebei device) {
        this.device = device;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
