package com.k2data.precast.mapper;

import com.k2data.precast.entity.dto.TemperatureDo;
import com.k2data.precast.entity.dto.UserAmountDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TemperatureMapper {

    /**
     * 根据用户查询天气
     *
     * @param beginDate
     * @param endDate
     * @param userId
     * @return
     */
    List<TemperatureDo> queryTemperature(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("userId") Long userId);

    /**
     * 查询天气信息
     *
     * @param beginDate
     * @param endDate
     * @param city
     * @return
     */
    List<TemperatureDo> queryTemperatureByCity(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("city") String city);

    /**
     * 查询用户用气量
     *
     * @param beginDate
     * @param endDate
     * @param userId
     * @return
     */
    List<UserAmountDo> queryUserAmount(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("userId") Long userId);

    /**
     * 查询用户用气量
     *
     * @param beginDate
     * @param endDate
     * @param userName
     * @return
     */
    List<UserAmountDo> queryUserAmountPredict(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("userName") String userName);

    /**
     * 查询地区用气量
     *
     * @param beginDate
     * @param endDate
     * @param area
     * @param businesType
     * @return
     */
    List<UserAmountDo> queryAreaAmount(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("address") String area);

    /**
     * 查询地区
     *
     * @return
     */
    List<String> queryArea();

    /**
     * 查询业务名称
     *
     * @return
     */
    List<String> queryBusinessTypes(@Param("username") String userName);

    /**
     * 根据用户名查询用户ID
     *
     * @param userName
     * @return
     */
    List<Long> queryIndexByName(@Param("username") String userName);

    /**
     * 模糊查询名称
     *
     * @param name
     * @param area
     * @return
     */
    List<String> queryNameByArea(@Param("username") String name, @Param("area") String area);

    /**
     * 查询供气单位名称
     *
     * @param userName
     * @param areaName
     * @return
     */
    List<String> queryCompanyName(@Param("username") String userName, String areaName);


    /**
     * 根据用户查询预测天气
     *
     * @param beginDate
     * @param endDate
     * @param userId
     * @return
     */
    List<TemperatureDo> queryTemperaturePre(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("userId") Long userId);


    /**
     * 根据城市查询预测天气
     *
     * @param beginDate
     * @param endDate
     * @param city
     * @return
     */
    List<TemperatureDo> queryTemperaturePreByCity(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("city") String city);


}