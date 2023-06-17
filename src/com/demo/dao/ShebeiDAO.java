package com.demo.dao;
import com.demo.vo.Shebei;
import com.demo.vo.ShebeiDispatch;
import com.demo.vo.ShebeiReportRecord;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * 设备模块的DAO层（数据层）接口，提供增删改查等数据库操作的方法抽象
 */
public interface ShebeiDAO {
    /**
     * 增加设备表记录
     *
     * @param vo
     * @return
     */
    void add(Shebei vo);
    /**
     * 根据主键id，删除对应的设备表记录
     *
     * @param id
     * @return
     */
    boolean delete(long id);
    /**
     * 更新设备表记录
     *
     * @param vo
     * @return
     */
    void update(Shebei vo);
    /**
     * 根据主键id获取设备表记录的详情
     *
     * @param id
     * @return
     */
    Shebei get(Serializable id);

    boolean faultReport(Long id, String reason);

    void updateReportStatus(Long id);

    boolean updateShebeiStatus(Long id, String status, Boolean reportStatus);

    List<ShebeiReportRecord> getReportRecords();

    ShebeiReportRecord getReportRecord(Long deviceId);

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

    boolean dispatchStaff(Long uid, Long deviceId, String reason, String now);

    List<ShebeiDispatch> getDispatchStaffRecords(Long uid, String userType);

    Integer countDispatchStaffRecord(Long uid);

    boolean updateDeviceDispatchStatus(Long deviceId, Boolean status);

    boolean updateDispatchFinished(Long deviceId, Boolean status, String text);
}
