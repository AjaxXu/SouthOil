package com.k2data.precast.service;

import com.k2data.precast.entity.dto.TemperatureDo;
import com.k2data.precast.entity.dto.UserAmountDo;


import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface TemperatureServiceI {
    /**
     * 查询出某个地方一段时间内 每天的最大温度和最低温度
     * @param beginDate
     * @param endDate
     * @param userId
     * @return
     * @throws ParseException
     */
    List<TemperatureDo> queryMaxTemperature(Date beginDate, Date endDate,String userName,String city) throws ParseException;

    /**
     * 查询出某个地方一段时间内 每天的用气量
     * @param beginDate
     * @param endDate
     * @param userId
     * @param area
     * @return
     * @throws ParseException
     */
    List<UserAmountDo> queryUserAmount(Date beginDate, Date endDate, String userId,String area) throws ParseException;

    /**
     * 查询出某个地方一段时间内 每天的用气量
     * @param beginDate
     * @param endDate
     * @param userName
     * @return
     * @throws ParseException
     */
    List<UserAmountDo> queryUserAmountPredict(Date beginDate, Date endDate, String userName) throws ParseException;

    /**
     * 查询出某个地方一段时间内 每天的最大温度和最低温度
     * @param beginDate
     * @param endDate
     * @param userId
     * @return
     * @throws ParseException
     */
    List<TemperatureDo> queryMaxTemperaturePre(Date beginDate, Date endDate,String userName,String city) throws ParseException;

    /**
     * 查询地区信息
     * @return
     */
    List<String>  queryAreaInfo();

    /**
     * 查询地区信息
     * @return
     */
    String   queryBusinessTypes(String usname);


    /**
     * 查询地区信息
     * @return
     */
    List<String>  queryNameByArea(String userName,String areaName);

    /**
     * 查询供气单位
     * @param userName
     * @param areaName
     * @return
     */
   String queryCompanyName( String userName, String areaName);
}
