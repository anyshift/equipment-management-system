package com.demo.dao.impl;

import com.demo.service.UserService;
import com.demo.service.impl.UserServiceImpl;
import com.demo.util.Util;
import com.demo.dao.WeixiuDAO;
import com.demo.vo.User;
import com.demo.vo.Weixiu;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 维修模块的DAO层（数据层）的具体实现类，对WeixiuDAO接口中定义的增删改查等抽象方法作出具体的功能实现
 */
public class WeixiuDAOImpl implements WeixiuDAO {

    //@Override
    public void add(Weixiu vo) {
        String sql = "insert into `t_weixiu` (`weixiu_no`,`weixiu_name`,`weixiu_guzhang`,`weixiu_feiyong`,`weixiu_date`,`weixiu_ren`,`weixiu_phone`,`weixiu_status`,`weixiu_text`) values(?,?,?,?,?,?,?,?,?)";
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            
            ps.setString(1, vo.getWeixiuNo());
            ps.setString(2, vo.getWeixiuName());
            ps.setString(3, vo.getWeixiuGuzhang());
            ps.setString(4, vo.getWeixiuFeiyong());
            ps.setString(5, vo.getWeixiuDate());
            ps.setString(6, vo.getWeixiuRen());
            ps.setString(7, vo.getWeixiuPhone());
            ps.setString(8, vo.getWeixiuStatus());
            ps.setString(9, vo.getWeixiuText());
            ps.execute();
            ps.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@Override
    public void update(Weixiu vo) {
        String sql = "update `t_weixiu` set `weixiu_no` = ? ,`weixiu_name` = ? ,`weixiu_guzhang` = ? ,`weixiu_feiyong` = ? ,`weixiu_date` = ? ,`weixiu_ren` = ? ,`weixiu_phone` = ? ,`weixiu_status` = ? ,`weixiu_text` = ?  where `id` = ?";
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            
            ps.setString(1, vo.getWeixiuNo());
            ps.setString(2, vo.getWeixiuName());
            ps.setString(3, vo.getWeixiuGuzhang());
            ps.setString(4, vo.getWeixiuFeiyong());
            ps.setString(5, vo.getWeixiuDate());
            ps.setString(6, vo.getWeixiuRen());
            ps.setString(7, vo.getWeixiuPhone());
            ps.setString(8, vo.getWeixiuStatus());
            ps.setString(9, vo.getWeixiuText());
            ps.setLong(10, vo.getId());
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
            String sql = "delete from `t_weixiu` where id = " + id;
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
    public Weixiu get(Serializable id) {
        Weixiu vo = null;
        try {
            Connection c = Util.getConnection();
            Statement s = c.createStatement();
            String sql = "select * from `t_weixiu` where id = " + id;
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                vo = new Weixiu();
                vo.setId(rs.getLong("id"));
                vo.setWeixiuNo(rs.getString("weixiu_no"));
                vo.setWeixiuName(rs.getString("weixiu_name"));
                vo.setWeixiuGuzhang(rs.getString("weixiu_guzhang"));
                vo.setWeixiuFeiyong(rs.getString("weixiu_feiyong"));
                vo.setWeixiuDate(rs.getString("weixiu_date"));
                vo.setWeixiuRen(rs.getString("weixiu_ren"));
                vo.setWeixiuPhone(rs.getString("weixiu_phone"));
                vo.setWeixiuStatus(rs.getString("weixiu_status"));
                vo.setWeixiuText(rs.getString("weixiu_text"));
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    //@Override
    public Map<String, Object> list(Map<String, Object> params, Boolean listMyRepairWork, String repairer) {
        List<Weixiu> list = new ArrayList();
        int totalCount = 0;
        String condition = "";
        String sqlList;
        if (params.get("searchColumn") != null && !"".equals(params.get("searchColumn"))) {
            condition += " and `" + params.get("searchColumn") + "` like '%" + params.get("keyword") + "%'";
        }
        if (listMyRepairWork) {
            condition += " and `weixiu_ren` = '" + repairer + "'";
        }
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            String limit = (params.get("startIndex") != null && params.get("pageSize") != null) ? " limit " + params.get("startIndex") + "," + params.get("pageSize") : "";
                sqlList = "select * from `t_weixiu` where 1=1 " + condition + " order by id asc " + limit + ";";
                ps = c.prepareStatement(sqlList);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Weixiu vo = new Weixiu();
                    vo.setId(rs.getLong("id"));
                    vo.setWeixiuNo(rs.getString("weixiu_no"));
                    vo.setWeixiuName(rs.getString("weixiu_name"));
                    vo.setWeixiuGuzhang(rs.getString("weixiu_guzhang"));
                    vo.setWeixiuFeiyong(rs.getString("weixiu_feiyong"));
                    vo.setWeixiuDate(rs.getString("weixiu_date"));
                    vo.setWeixiuRen(rs.getString("weixiu_ren"));
                    vo.setWeixiuPhone(rs.getString("weixiu_phone"));
                    vo.setWeixiuStatus(rs.getString("weixiu_status"));
                    vo.setWeixiuText(rs.getString("weixiu_text"));
                    list.add(vo);
                }
            String sqlCount = "select count(*) from `t_weixiu` where 1=1 " + condition;
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
        Map<String, Object> result = new HashMap();
        result.put("list", list);
        result.put("totalCount", totalCount);
        return result;
    }

    @Override
    public boolean updateRepairStatus(long deviceId, String status, String text, Double price) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sql = "";
        if (price != null) {
            sql = "update `t_weixiu` set `weixiu_status` = ?, `weixiu_text` = ?, `weixiu_feiyong` = ?, `weixiu_date` = ? where `weixiu_no` = ? and `weixiu_status` != '维修完成'";
        } else {
            sql = "update `t_weixiu` set `weixiu_status` = ?, `weixiu_text` = ?, `weixiu_date` = ? where `weixiu_no` = ? and `weixiu_status` != '维修完成'";
        }
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, status);
            ps.setString(2, text);
            if (price != null) {
                ps.setDouble(3, price);
                ps.setString(4, LocalDateTime.now().format(formatter));
                ps.setLong(5, deviceId);
            } else {
                ps.setString(3, LocalDateTime.now().format(formatter));
                ps.setLong(4, deviceId);
            }
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
    public boolean updateRepairDispatchStatus(long deviceId, String status) {
        String sql = "update `t_weixiu_dispatch` set `status` = ? where `shebei_id` = ?";
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, status);
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
    public boolean deleteRepairDispatchRecord(long deviceId) {
        try {
            Connection c = Util.getConnection();
            Statement s = c.createStatement();
            String sql = "delete from `t_weixiu_dispatch` where `shebei_id` = " + deviceId;
            s.execute(sql);
            s.close();
            c.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean transferTask(long deviceId, long uid) {
        String sql = "update `t_weixiu_dispatch` set `user_id` = ? where `shebei_id` = ?";
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setLong(1, uid);
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
    public boolean updateRepairer(long deviceId, String repairer, String repairerPhone) {
        String sql = "update `t_weixiu` set `weixiu_ren` = ?, `weixiu_phone` = ? where `weixiu_no` = ? and `weixiu_status` != '维修完成'";
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, repairer);
            ps.setString(2, repairerPhone);
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
