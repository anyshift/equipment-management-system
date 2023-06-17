package com.demo.service;

import com.demo.vo.Shebei;
import com.demo.vo.ShebeiDispatch;
import com.demo.vo.ShebeiReportRecord;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 设备模块的Service层（业务层）接口，提供业务方法的抽象
 */
public interface ShebeiService {
    /**
     * 增加设备
     *
     * @param vo
     * @return
     */
    void add(Shebei vo);

    /**
     * 删除设备
     *
     * @param id
     * @return
     */
    void delete(long id);

    /**
     * 修改设备
     *
     * @param vo
     * @return
     */
    void update(Shebei vo);

    /**
     * 根据主键Id查询设备详情
     *
     * @param id
     * @return
     */
    Shebei get(Serializable id);

    boolean faultReport(Long id, String reason);

    void updateReportStatus(Long id);

    boolean updateShebeiStatus(Long id, String status, Boolean reportStatus);

    List<ShebeiReportRecord> getReportRecords();

    boolean deleteReportRecord(Long id);

    /**
     * 根据条件查询设备的列表与数量
     *
     * @param params
     * @return
     */
    Map<String, Object> list(Map<String, Object> params);

    boolean updateReportFaultStatus(Long deviceId, Boolean status);

    boolean addDispatchRepairer(Long deviceId, Long uid, String status);

    void addRepairRecord(Long uid, Long deviceId, String repairStatus, String repairText);

    boolean dispatchStaff(Long uid, Long deviceId, String reason, String now);

    List<ShebeiDispatch> getDispatchStaffRecords(Long uid, String userType);

    Integer countDispatchStaffRecord(Long uid);

    boolean updateDeviceDispatchStatus(Long deviceId, Boolean status);

    boolean updateDispatchFinished(Long deviceId, Boolean status, String text);
}
