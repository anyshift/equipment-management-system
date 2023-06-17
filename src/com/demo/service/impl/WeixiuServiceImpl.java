package com.demo.service.impl;

import com.demo.dao.WeixiuDAO;
import com.demo.dao.impl.WeixiuDAOImpl;
import com.demo.service.UserService;
import com.demo.service.WeixiuService;
import com.demo.vo.User;
import com.demo.vo.Weixiu;

import java.io.Serializable;
import java.util.Map;

/**
 * 维修模块的Service层（业务层）的具体实现类，对WeixiuService接口中定义的抽象方法作出具体的功能实现
 */
public class WeixiuServiceImpl implements WeixiuService {
    //@Override
    public void add(Weixiu vo) {
        WeixiuDAO weixiuDAO = new WeixiuDAOImpl();
        weixiuDAO.add(vo);
    }

    //@Override
    public void delete(long id) {
        WeixiuDAO weixiuDAO = new WeixiuDAOImpl();
        weixiuDAO.delete(id);
    }

    //@Override
    public void update(Weixiu vo) {
        WeixiuDAO weixiuDAO = new WeixiuDAOImpl();
        weixiuDAO.update(vo);
    }

    //@Override
    public Weixiu get(Serializable id) {
        WeixiuDAO weixiuDAO = new WeixiuDAOImpl();
        return weixiuDAO.get(id);
    }

    @Override
    public boolean updateRepairStatus(long deviceId, String status, String text, Double price) {
        WeixiuDAO weixiuDAO = new WeixiuDAOImpl();
        return weixiuDAO.updateRepairStatus(deviceId, status, text, price);
    }

    @Override
    public boolean updateRepairDispatchStatus(long deviceId, String status) {
        WeixiuDAO weixiuDAO = new WeixiuDAOImpl();
        return weixiuDAO.updateRepairDispatchStatus(deviceId, status);
    }

    @Override
    public boolean deleteRepairDispatchRecord(long deviceId) {
        WeixiuDAO weixiuDAO = new WeixiuDAOImpl();
        return weixiuDAO.deleteRepairDispatchRecord(deviceId);
    }

    //@Override
    public Map<String, Object> list(Map<String, Object> params, Boolean listMyRepairWork, String repairer) {
        WeixiuDAO weixiuDAO = new WeixiuDAOImpl();
        return weixiuDAO.list(params, listMyRepairWork, repairer);
    }

    @Override
    public boolean transferTask(long deviceId, long uid) {
        WeixiuDAO weixiuDAO = new WeixiuDAOImpl();
        return weixiuDAO.transferTask(deviceId, uid);
    }

    @Override
    public boolean updateRepairer(long deviceId, long uid) {
        WeixiuDAO weixiuDAO = new WeixiuDAOImpl();

        UserService userService = new UserServiceImpl();
        User user = userService.get(uid);

        return weixiuDAO.updateRepairer(deviceId, user.getRealName(), user.getUserPhone());
    }
}
