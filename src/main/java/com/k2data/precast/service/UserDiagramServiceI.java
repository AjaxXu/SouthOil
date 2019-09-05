package com.k2data.precast.service;

import com.alibaba.fastjson.JSONObject;
import com.k2data.precast.entity.dto.PageParam;
import com.k2data.precast.entity.dto.PageResult;
import com.k2data.precast.entity.dto.UserDiagramDo;

/**
 * @author tianhao
 * @date 2018/12/5
 */
public interface UserDiagramServiceI {

    /**
     * 查询用户画像
     *
     * @param pageParam
     * @param userId
     * @return
     */
    PageResult<UserDiagramDo> queryForPage(PageParam pageParam, Long userId);

    /**
     * 执行参数
     *
     * @param userId 用户Id
     * @param param  执行参数
     */
    void exeModelAnalysis(JSONObject param, Long userId);

    /**
     * 更新模型得分比重
     *
     * @param value  要设置的值
     * @param userId 用户Id
     * @return
     */
    void updateModelScoreRadio(int value, Long userId);

    /**
     * 更新指标设置
     *
     * @param userId 用户Id
     * @param object
     */
    void updateSpecialMetrecs(String object, Long userId);

    /**
     * 获取模型得分比重
     *
     * @param userId
     * @return
     */
    Double getModalScoreRadio(Long userId);

    /**
     * 获取指标参数;
     *
     * @param userId
     * @return
     */
    String getSpecialMetrecs(Long userId);
}
