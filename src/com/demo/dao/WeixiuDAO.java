package com.demo.dao;
import com.demo.vo.Weixiu;

import java.io.Serializable;
import java.util.Map;
/**
 * 维修模块的DAO层（数据层）接口，提供增删改查等数据库操作的方法抽象
 */
public interface WeixiuDAO {
    /**
     * 增加维修表记录
     *
     * @param vo
     * @return
     */
    void add(Weixiu vo);
    /**
     * 根据主键id，删除对应的维修表记录
     *
     * @param id
     * @return
     */
    boolean delete(long id);
    /**
     * 更新维修表记录
     *
     * @param vo
     * @return
     */
    void update(Weixiu vo);
    /**
     * 根据主键id获取维修表记录的详情
     *
     * @param id
     * @return
     */
    Weixiu get(Serializable id);
    /**
     * 根据条件查询维修的列表与数量
     *
     * @param params
     * @return
     */
    Map<String, Object> list(Map<String, Object> params, Boolean listMyRepairWork, String repairer);

    boolean updateRepairStatus(long deviceId, String status, String text, Double price);

    boolean updateRepairDispatchStatus(long deviceId, String status);

    boolean deleteRepairDispatchRecord(long deviceId);

    boolean transferTask(long deviceId, long uid);

    boolean updateRepairer(long deviceId, String repairer, String repairerPhone);
}
