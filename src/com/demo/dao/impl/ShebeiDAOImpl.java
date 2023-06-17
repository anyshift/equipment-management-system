package com.demo.dao.impl;

import com.demo.util.Util;
import com.demo.dao.ShebeiDAO;
import com.demo.vo.Shebei;
import com.demo.vo.ShebeiDispatch;
import com.demo.vo.ShebeiReportRecord;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备模块的DAO层（数据层）的具体实现类，对ShebeiDAO接口中定义的增删改查等抽象方法作出具体的功能实现
 */
public class ShebeiDAOImpl implements ShebeiDAO {

    //@Override
    public void add(Shebei vo) {
        String sql = "insert into `t_shebei` (`shebei_name`,`shebei_no`,`shebei_type`,`shebei_cangjia`,`shebei_indate`,`shebei_inprice`,`shebei_dept`,`shebei_status`," +
                " `shebei_image`, `shebei_report`, `shebei_dispatch`, `temperature_record`, `temperature_threshold`, `humidity_record`, `humidity_threshold`, `noise_record`, `noise_threshold`," +
                " `voltage_record`, `voltage_threshold`) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            
            ps.setString(1, vo.getShebeiName());
            ps.setString(2, vo.getShebeiNo());
            ps.setString(3, vo.getShebeiType());
            ps.setString(4, vo.getShebeiCangjia());
            ps.setString(5, vo.getShebeiIndate());
            ps.setString(6, vo.getShebeiInprice());
            ps.setString(7, vo.getShebeiDept());
            ps.setString(8, "正常");
            ps.setString(9, vo.getShebeiImage());
            ps.setBoolean(10, false);
            ps.setBoolean(11, false);
            ps.setBoolean(12, vo.getTemperatureRecord());
            ps.setDouble(13, vo.getTemperatureThreshold());
            ps.setBoolean(14, vo.getHumidityRecord());
            ps.setDouble(15, vo.getHumidityThreshold());
            ps.setBoolean(16, vo.getNoiseRecord());
            ps.setDouble(17, vo.getNoiseThreshold());
            ps.setBoolean(18, vo.getVoltageRecord());
            ps.setDouble(19, vo.getVoltageThreshold());
            ps.execute();
            ps.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@Override
    public void update(Shebei vo) {
        String sql = "update `t_shebei` set `shebei_name` = ?, `shebei_no` = ?, `shebei_type` = ?, `shebei_cangjia` = ?, `shebei_indate` = ?, `shebei_inprice` = ?," +
                " `shebei_dept` = ?, `shebei_status` = ?, `shebei_image` = ?, `temperature_record` = ?, `temperature_threshold` = ?, `humidity_record` = ?," +
                " `humidity_threshold` = ?, `noise_record` = ?, `noise_threshold` = ?, `voltage_record` = ?, `voltage_threshold` = ?  where `id` = ?";
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            
            ps.setString(1, vo.getShebeiName());
            ps.setString(2, vo.getShebeiNo());
            ps.setString(3, vo.getShebeiType());
            ps.setString(4, vo.getShebeiCangjia());
            ps.setString(5, vo.getShebeiIndate());
            ps.setString(6, vo.getShebeiInprice());
            ps.setString(7, vo.getShebeiDept());
            ps.setString(8, vo.getShebeiStatus());
            ps.setString(9, vo.getShebeiImage());
            ps.setBoolean(10, vo.getTemperatureRecord());
            ps.setDouble(11, vo.getTemperatureThreshold());
            ps.setBoolean(12, vo.getHumidityRecord());
            ps.setDouble(13, vo.getHumidityThreshold());
            ps.setBoolean(14, vo.getNoiseRecord());
            ps.setDouble(15, vo.getNoiseThreshold());
            ps.setBoolean(16, vo.getVoltageRecord());
            ps.setDouble(17, vo.getVoltageThreshold());
            ps.setLong(18, vo.getId());

            ps.execute();
            ps.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@Override
    public boolean delete(long id) {
        try {
            Connection c = Util.getConnection();
            Statement s = c.createStatement();
            String sql = "delete from `t_shebei` where id = " + id;
            s.execute(sql);
            s.close();
            c.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //@Override
    public Shebei get(Serializable id) {
        Shebei vo = null;
        try {
            Connection c = Util.getConnection();
            Statement s = c.createStatement();
            String sql = "select * from `t_shebei` where id = " + id;
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                vo = new Shebei();
                vo.setId(rs.getLong("id"));
                vo.setShebeiName(rs.getString("shebei_name"));
                vo.setShebeiNo(rs.getString("shebei_no"));
                vo.setShebeiType(rs.getString("shebei_type"));
                vo.setShebeiCangjia(rs.getString("shebei_cangjia"));
                vo.setShebeiIndate(rs.getString("shebei_indate"));
                vo.setShebeiInprice(rs.getString("shebei_inprice"));
                vo.setShebeiDept(rs.getString("shebei_dept"));
                vo.setShebeiStatus(rs.getString("shebei_status"));
                vo.setShebeiReport(rs.getBoolean("shebei_report"));
                vo.setShebeiDispatch(rs.getBoolean("shebei_dispatch"));
                vo.setShebeiImage(rs.getString("shebei_image"));
                vo.setTemperatureRecord(rs.getBoolean("temperature_record"));
                vo.setTemperatureThreshold(rs.getDouble("temperature_threshold"));
                vo.setHumidityRecord(rs.getBoolean("humidity_record"));
                vo.setHumidityThreshold(rs.getDouble("humidity_threshold"));
                vo.setNoiseRecord(rs.getBoolean("noise_record"));
                vo.setNoiseThreshold(rs.getDouble("noise_threshold"));
                vo.setVoltageRecord(rs.getBoolean("voltage_record"));
                vo.setVoltageThreshold(rs.getDouble("voltage_threshold"));
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    @Override
    public boolean faultReport(Long id, String reason) {
        String sql = "INSERT INTO `t_shebei_fault_report` (`shebei_id`, `shebei_fault`, `is_fault`) VALUES(?,?,?)";
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, id);
            ps.setString(2, reason);
            ps.setBoolean(3, false);
            ps.execute();
            ps.close();
            c.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void updateReportStatus(Long id) {
        String sql = "update `t_shebei` set `shebei_report` = ?  where `id` = ?";
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setBoolean(1, true);
            ps.setLong(2, id);
            ps.execute();
            ps.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateShebeiStatus(Long id, String status, Boolean reportStatus) {
        String sql = "update `t_shebei` set `shebei_report` = ?, `shebei_status` = ?  where `id` = ?";
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setBoolean(1, reportStatus);
            ps.setString(2, status);
            ps.setLong(3, id);
            ps.execute();
            ps.close();
            c.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<ShebeiReportRecord> getReportRecords() {
        List<ShebeiReportRecord> reportRecords = new ArrayList<>();
        try {
            Connection c = Util.getConnection();
            Statement s = c.createStatement();
            String sql = "select ts.id as id, ts.shebei_name as name, ts.shebei_status as status, tsf.shebei_fault as reason\n" +
                    "from t_shebei as ts\n" +
                    "join t_shebei_fault_report as tsf on ts.id = tsf.shebei_id and tsf.is_fault = 0";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                ShebeiReportRecord shebeiReportRecord = new ShebeiReportRecord();
                shebeiReportRecord.setId(rs.getLong("id"));
                shebeiReportRecord.setName(rs.getString("name"));
                shebeiReportRecord.setStatus(rs.getString("status"));
                shebeiReportRecord.setReason(rs.getString("reason"));
                reportRecords.add(shebeiReportRecord);
            }
            rs.close();
            c.close();
            return reportRecords;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ShebeiReportRecord getReportRecord(Long deviceId) {
        ShebeiReportRecord shebeiReportRecord = new ShebeiReportRecord();
        try {
            Connection c = Util.getConnection();
            Statement s = c.createStatement();
            String sql = "select `shebei_fault` from `t_shebei_fault_report` where `shebei_id` = " + deviceId;
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                shebeiReportRecord.setReason(rs.getString("shebei_fault"));
            }
            rs.close();
            c.close();
            return shebeiReportRecord;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteReportRecord(Long id) {
        try {
            Connection c = Util.getConnection();
            Statement s = c.createStatement();
            String sql = "delete from `t_shebei_fault_report` where `shebei_id` = " + id;
            s.execute(sql);
            s.close();
            c.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //@Override
    public Map<String, Object> list(Map<String, Object> params) {
        List<Shebei> list = new ArrayList<>();
        int totalCount = 0;
        String condition = "";
        String sqlList;
        if (params.get("searchColumn") != null && !"".equals(params.get("searchColumn"))) {
            condition += " and `" + params.get("searchColumn") + "` like '%" + params.get("keyword") + "%'";
        }
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            String limit = (params.get("startIndex") != null && params.get("pageSize") != null) ? " limit " + params.get("startIndex") + "," + params.get("pageSize") : "";
                sqlList = "select * from `t_shebei` where 1=1 " + condition + " order by id asc " + limit + ";";
                ps = c.prepareStatement(sqlList);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Shebei vo = new Shebei();
                    vo.setId(rs.getLong("id"));
                    vo.setShebeiName(rs.getString("shebei_name"));
                    vo.setShebeiNo(rs.getString("shebei_no"));
                    vo.setShebeiType(rs.getString("shebei_type"));
                    vo.setShebeiCangjia(rs.getString("shebei_cangjia"));
                    vo.setShebeiIndate(rs.getString("shebei_indate"));
                    vo.setShebeiInprice(rs.getString("shebei_inprice"));
                    vo.setShebeiDept(rs.getString("shebei_dept"));
                    vo.setShebeiStatus(rs.getString("shebei_status"));
                    vo.setShebeiReport(rs.getBoolean("shebei_report"));
                    vo.setShebeiDispatch(rs.getBoolean("shebei_dispatch"));
                    vo.setShebeiImage(rs.getString("shebei_image"));
                    vo.setTemperatureRecord(rs.getBoolean("temperature_record"));
                    vo.setTemperatureThreshold(rs.getDouble("temperature_threshold"));
                    vo.setHumidityRecord(rs.getBoolean("humidity_record"));
                    vo.setHumidityThreshold(rs.getDouble("humidity_threshold"));
                    vo.setNoiseRecord(rs.getBoolean("noise_record"));
                    vo.setNoiseThreshold(rs.getDouble("noise_threshold"));
                    vo.setVoltageRecord(rs.getBoolean("voltage_record"));
                    vo.setVoltageThreshold(rs.getDouble("voltage_threshold"));
                    list.add(vo);
                }
            String sqlCount = "select count(*) from `t_shebei` where 1=1 " + condition;
            ps = c.prepareStatement(sqlCount);
            rs = ps.executeQuery();
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            rs.close();
            ps.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("totalCount", totalCount);
        return result;
    }

    @Override
    public boolean updateReportFaultStatus(Long deviceId, Boolean status) {
        String sql = "update `t_shebei_fault_report` set `is_fault` = ? where `shebei_id` = ?";
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setBoolean(1, status);
            ps.setLong(2, deviceId);
            ps.execute();
            ps.close();
            c.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addDispatchRepairer(Long deviceId, Long uid, String status) {
        String sql = "INSERT INTO `t_weixiu_dispatch` (`shebei_id`, `user_id`, `status`) VALUES(?,?,?)";
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, deviceId);
            ps.setLong(2, uid);
            ps.setString(3, status);
            ps.execute();
            ps.close();
            c.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean dispatchStaff(Long uid, Long deviceId, String reason, String now) {
        String sql = "INSERT INTO `t_shebei_dispatch` (`user_id`, `device_id`, `reason`, `create_time`, `finished`) VALUES(?,?,?,?,?)";
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, uid);
            ps.setLong(2, deviceId);
            ps.setString(3, reason);
            ps.setString(4, now);
            ps.setBoolean(5, false);
            ps.execute();
            ps.close();
            c.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<ShebeiDispatch> getDispatchStaffRecords(Long uid, String userType) {
        List<ShebeiDispatch> deviceDispatchStaffRecords = new ArrayList<>();
        String sql = "";
        if ("admin".equalsIgnoreCase(userType)) {
            sql = "select * from `t_shebei_dispatch`";
        } else sql = "select * from `t_shebei_dispatch` where `user_id` = " + uid;
        try {
            Connection c = Util.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                ShebeiDispatch deviceDispatch = new ShebeiDispatch();
                deviceDispatch.setUid(rs.getLong("user_id"));
                deviceDispatch.setDeviceId(rs.getLong("device_id"));
                deviceDispatch.setReason(rs.getString("reason"));
                deviceDispatch.setFinished(rs.getBoolean("finished"));
                deviceDispatch.setCreateTime(rs.getString("create_time"));
                deviceDispatch.setFinishedText(rs.getString("finished_text"));
                deviceDispatchStaffRecords.add(deviceDispatch);
            }
            rs.close();
            c.close();
            return deviceDispatchStaffRecords;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer countDispatchStaffRecord(Long uid) {
        int totalCount = 0;
        try {
            Connection c = Util.getConnection();
            Statement s = c.createStatement();
            String sql = "select count(*) from `t_shebei_dispatch` where `user_id` = " + uid + " and `finished` = 0";
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            rs.close();
            c.close();
            return totalCount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateDeviceDispatchStatus(Long deviceId, Boolean status) {
        String sql = "update `t_shebei` set `shebei_dispatch` = ? where `id` = ?";
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setBoolean(1, status);
            ps.setLong(2, deviceId);
            ps.execute();
            ps.close();
            c.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateDispatchFinished(Long deviceId, Boolean status, String text) {
        String sql = "update `t_shebei_dispatch` set `finished` = ?, `finished_text` = ? where `device_id` = ?";
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setBoolean(1, status);
            ps.setString(2, text);
            ps.setLong(3, deviceId);
            ps.execute();
            ps.close();
            c.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
