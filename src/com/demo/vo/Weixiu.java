package com.demo.vo;

import java.io.Serializable;

/**
 * 维修（t_weixiu表对应的Java实体类）
 */
public class Weixiu implements Serializable {
    private Long id;//主键
    private String weixiuNo;//设备编号
    private String weixiuName;//设备名称
    private String weixiuGuzhang;//故障
    private String weixiuFeiyong;//维修费用
    private String weixiuDate;//维修日期
    private String weixiuRen;//维修人
    private String weixiuPhone;//维修人电话
    private String weixiuStatus;//状态:已修好/待维修
    private String weixiuText;//备注

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getWeixiuNo() {
        return weixiuNo;
    }

    public void setWeixiuNo(String weixiuNo) {
        this.weixiuNo = weixiuNo;
    }
    public String getWeixiuName() {
        return weixiuName;
    }

    public void setWeixiuName(String weixiuName) {
        this.weixiuName = weixiuName;
    }
    public String getWeixiuGuzhang() {
        return weixiuGuzhang;
    }

    public void setWeixiuGuzhang(String weixiuGuzhang) {
        this.weixiuGuzhang = weixiuGuzhang;
    }
    public String getWeixiuFeiyong() {
        return weixiuFeiyong;
    }

    public void setWeixiuFeiyong(String weixiuFeiyong) {
        this.weixiuFeiyong = weixiuFeiyong;
    }
    public String getWeixiuDate() {
        return weixiuDate;
    }

    public void setWeixiuDate(String weixiuDate) {
        this.weixiuDate = weixiuDate;
    }
    public String getWeixiuRen() {
        return weixiuRen;
    }

    public void setWeixiuRen(String weixiuRen) {
        this.weixiuRen = weixiuRen;
    }
    public String getWeixiuPhone() {
        return weixiuPhone;
    }

    public void setWeixiuPhone(String weixiuPhone) {
        this.weixiuPhone = weixiuPhone;
    }
    public String getWeixiuStatus() {
        return weixiuStatus;
    }

    public void setWeixiuStatus(String weixiuStatus) {
        this.weixiuStatus = weixiuStatus;
    }
    public String getWeixiuText() {
        return weixiuText;
    }

    public void setWeixiuText(String weixiuText) {
        this.weixiuText = weixiuText;
    }
}
