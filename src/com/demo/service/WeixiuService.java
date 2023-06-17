package com.demo.service;

import com.demo.vo.Weixiu;

import java.io.Serializable;
import java.util.Map;

/**
 * 维修模块的Service层（业务层）接口，提供业务方法的抽象
 */
public interface WeixiuService {
    /**
     * 增加维修
     *
     * @param vo
     * @return
     */
    void add(Weixiu vo);

    /**
     * 删除维修
     *
     * @param id
     * @return
     */
    void delete(long id);

    /**
     * 修改维修
     *
     * @param vo
     * @return
     */
    void update(Weixiu vo);

    /**
     * 根据主键Id查询维修详情
     *
     * @param id
     * @return
     */
    Weixiu get(Serializable id);

    boolean updateRepairStatus(long deviceId, String status, String text, Double price);

    boolean updateRepairDispatchStatus(long deviceId, String status);

    boolean deleteRepairDispatchRecord(long deviceId);

    /**
     * 根据条件查询维修的列表与数量
     *
     * @param params
     * @return
     */
    Map<String, Object> list(Map<String, Object> params, Boolean listMyRepairWork, String repairer);

    boolean transferTask(long deviceId, long uid);

    boolean updateRepairer(long deviceId, long uid);
}
