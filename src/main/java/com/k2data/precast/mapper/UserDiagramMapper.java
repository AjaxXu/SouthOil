package com.k2data.precast.mapper;

import com.k2data.precast.entity.dto.UserDiagramDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author thinkpad
 */
public interface UserDiagramMapper {

    /**
     * 分页查询用户画像相关
     *
     * @param name
     * @param pageSize
     * @param pageNum
     * @param type
     * @param order
     * @param asc
     * @param businessType
     * @param loc
     * @param userId
     * @return
     */
    List<UserDiagramDo> queryForPage(@Param("username") String name, @Param("offset") Integer pageSize, @Param("limit") Integer pageNum,
                                     @Param("type") String type, @Param("order") String order,
                                     @Param("asc") String asc, @Param("businessType") String businessType, @Param("loc") String loc,
                                     @Param("userId") Long userId, @Param("clusterCategory") Integer clusterCategory,
                                     @Param("industryCategory") Integer industryCategory);

    /**
     * 查询总数
     *
     * @param name
     * @param type
     * @param businessType
     * @param loc
     * @return
     */
    Integer queryCount(@Param("username") String name, @Param("type") String type, @Param("businessType") String businessType,
                       @Param("loc") String loc, @Param("userId") Long userId, @Param("clusterCategory") Integer clusterCategory,
                       @Param("industryCategory") Integer industryCategory);

    /**
     * 更新模型等分占比
     *
     * @param value
     * @param userId
     */
    void updateModelScoreRadio(@Param("radio") int value, @Param("userId") Long userId);

    /**
     * 更新指标参数
     *
     * @param value
     * @param userId
     */
    void updateSpecialMetrics(@Param("specialMetric") String value, @Param("userId") Long userId);


    /**
     * 查询系统参数
     *
     * @param userId
     * @param key
     * @return
     */
    Object querySystemParam(@Param("userId") Long userId, @Param("key") String key);

    /**
     * 插入新用户的参数设置
     *
     * @param value
     * @param key
     * @param userId
     */
    void addScoreRadio(@Param("value") Integer value, @Param("userId") Long userId, @Param("key") String key);


    /**
     * 新增一个分析参数
     * @param value
     * @param userId
     * @param key
     */
    void addSpecialMetrics(@Param("value") String value, @Param("userId") Long userId, @Param("key") String key);

    /**
     * 清空表
     *
     * @param userId
     * @return
     */
    void clearData(@Param("userId") Long userId);
}
