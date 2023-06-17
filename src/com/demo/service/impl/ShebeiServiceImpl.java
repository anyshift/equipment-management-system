package com.demo.service.impl;

import com.demo.dao.ShebeiDAO;
import com.demo.dao.UserDAO;
import com.demo.dao.impl.ShebeiDAOImpl;
import com.demo.dao.impl.UserDAOImpl;
import com.demo.dao.impl.WeixiuDAOImpl;
import com.demo.service.ShebeiService;
import com.demo.vo.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 设备模块的Service层（业务层）的具体实现类，对ShebeiService接口中定义的抽象方法作出具体的功能实现
 */
public class ShebeiServiceImpl implements ShebeiService {
    //@Override
    public void add(Shebei vo) {
        ShebeiDAO shebeiDAO = new ShebeiDAOImpl();
        shebeiDAO.add(vo);
    }

    //@Override
    public void delete(long id) {
        ShebeiDAO shebeiDAO = new ShebeiDAOImpl();
        shebeiDAO.delete(id);
    }

    //@Override
    public void update(Shebei vo) {
        ShebeiDAO shebeiDAO = new ShebeiDAOImpl();
        shebeiDAO.update(vo);
    }

    //@Override
    public Shebei get(Serializable id) {
        ShebeiDAO shebeiDAO = new ShebeiDAOImpl();
        return shebeiDAO.get(id);
    }

    @Override
    public boolean faultReport(Long id, String reason) {
        ShebeiDAO shebeiDAO = new ShebeiDAOImpl();
        return shebeiDAO.faultReport(id, reason);
    }

    @Override
    public void updateReportStatus(Long id) {
        ShebeiDAO shebeiDAO = new ShebeiDAOImpl();
        shebeiDAO.updateReportStatus(id);
    }

    @Override
    public boolean updateShebeiStatus(Long id, String status, Boolean reportStatus) {
        ShebeiDAO shebeiDAO = new ShebeiDAOImpl();
        return shebeiDAO.updateShebeiStatus(id, status, reportStatus);
    }

    @Override
    public List<ShebeiReportRecord> getReportRecords() {
        ShebeiDAO shebeiDAO = new ShebeiDAOImpl();
        return shebeiDAO.getReportRecords();
    }

    @Override
    public boolean deleteReportRecord(Long id) {
        ShebeiDAO shebeiDAO = new ShebeiDAOImpl();
        return shebeiDAO.deleteReportRecord(id);
    }

    //@Override
    public Map<String, Object> list(Map<String, Object> params) {
        ShebeiDAO shebeiDAO = new ShebeiDAOImpl();
        return shebeiDAO.list(params);
    }

    @Override
    public boolean updateReportFaultStatus(Long deviceId, Boolean status) {
        ShebeiDAO shebeiDAO = new ShebeiDAOImpl();
        return shebeiDAO.updateReportFaultStatus(deviceId, status);
    }

    @Override
    public boolean addDispatchRepairer(Long deviceId, Long uid, String status) {
        ShebeiDAO shebeiDAO = new ShebeiDAOImpl();
        return shebeiDAO.addDispatchRepairer(deviceId, uid, status);
    }

    @Override
    public void addRepairRecord(Long uid, Long deviceId, String repairStatus, String repairText) {
        WeixiuDAOImpl weixiuDAO = new WeixiuDAOImpl();
        ShebeiDAO shebeiDao = new ShebeiDAOImpl();
        UserDAO userDao = new UserDAOImpl();

        ShebeiReportRecord reportRecord = shebeiDao.getReportRecord(deviceId);

        User repairer = userDao.get(uid);
        Shebei device = shebeiDao.get(deviceId);
        Weixiu weixiu = new Weixiu();

        weixiu.setWeixiuNo(String.valueOf(deviceId));
        weixiu.setWeixiuName(device.getShebeiName());
        weixiu.setWeixiuGuzhang(reportRecord.getReason());
        weixiu.setWeixiuFeiyong("暂定");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        weixiu.setWeixiuDate(LocalDateTime.now().format(formatter));

        weixiu.setWeixiuRen(repairer.getRealName());
        weixiu.setWeixiuPhone(repairer.getUserPhone());
        weixiu.setWeixiuStatus(repairStatus);

        if (repairText.isEmpty()) {
            weixiu.setWeixiuText("待接受任务");
        } else weixiu.setWeixiuText(repairText);

        weixiuDAO.add(weixiu);
    }

    @Override
    public boolean dispatchStaff(Long uid, Long deviceId, String reason, String now) {
        ShebeiDAO deviceDAO = new ShebeiDAOImpl();
        return deviceDAO.dispatchStaff(uid, deviceId, reason, now);
    }

    @Override
    public List<ShebeiDispatch> getDispatchStaffRecords(Long uid, String userType) {
        ShebeiDAO deviceDAO = new ShebeiDAOImpl();
        List<ShebeiDispatch> dispatchStaffRecords = deviceDAO.getDispatchStaffRecords(uid, userType);
        dispatchStaffRecords.forEach(deviceDispatch -> {
            Shebei device = deviceDAO.get(deviceDispatch.getDeviceId());
            deviceDispatch.setDevice(device);
        });
        return dispatchStaffRecords;
    }

    @Override
    public Integer countDispatchStaffRecord(Long uid) {
        ShebeiDAO deviceDAO = new ShebeiDAOImpl();
        return deviceDAO.countDispatchStaffRecord(uid);
    }

    @Override
    public boolean updateDeviceDispatchStatus(Long deviceId, Boolean status) {
        ShebeiDAO deviceDAO = new ShebeiDAOImpl();
        return deviceDAO.updateDeviceDispatchStatus(deviceId, status);
    }

    @Override
    public boolean updateDispatchFinished(Long deviceId, Boolean status, String text) {
        ShebeiDAO deviceDAO = new ShebeiDAOImpl();
        return deviceDAO.updateDispatchFinished(deviceId, status, text);
    }
}
