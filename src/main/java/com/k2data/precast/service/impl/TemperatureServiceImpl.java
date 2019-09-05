package com.k2data.precast.service.impl;


import com.k2data.precast.entity.dto.TemperatureDo;
import com.k2data.precast.entity.dto.UserAmountDo;
import com.k2data.precast.mapper.TemperatureMapper;
import com.k2data.precast.service.TemperatureServiceI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.DateFormat;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author thinkpad
 */
@Service("temperatureService")
public class TemperatureServiceImpl implements TemperatureServiceI {

    @Autowired
    TemperatureMapper mapper;

    @Override
    public List<TemperatureDo> queryMaxTemperature(Date beginDate, Date endDate, String username, String city) throws ParseException {
        if (StringUtils.isNotBlank(username)) {
            // 查询出id
            List<Long> ids = mapper.queryIndexByName(username);
            if (null != ids && !ids.isEmpty()) {
                return mapper.queryTemperature(getTimeStr(beginDate), getTimeStr(endDate), ids.get(0));
            }
            return Collections.emptyList();
        } else {
            return mapper.queryTemperatureByCity(getTimeStr(beginDate), getTimeStr(endDate), city);
        }
    }

    @Override
    public List<UserAmountDo> queryUserAmount(Date beginDate, Date endDate, String username, String area) throws ParseException {
        if (StringUtils.isNotBlank(username)) {
            List<Long> ids = mapper.queryIndexByName(username);
            if (null != ids && !ids.isEmpty()) {
                return mapper.queryUserAmount(getTimeStr(beginDate), getTimeStr(endDate), ids.get(0));
            }
            return Collections.emptyList();
        } else {
            return mapper.queryAreaAmount(getTimeStr(beginDate), getTimeStr(endDate), area);
        }
    }

    @Override
    public List<String> queryAreaInfo() {
        return mapper.queryArea();
    }

    @Override
    public String queryBusinessTypes(String userName) {
        List<String> userNames = mapper.queryBusinessTypes(userName);
        if (null != userNames && !userNames.isEmpty()) {
            return userNames.get(0);
        }
        return "";
    }

    @Override
    public List<String> queryNameByArea(String userName, String areaName) {
        if (StringUtils.isNotBlank(userName)) {
            return mapper.queryNameByArea(userName, areaName);
        }
        return Collections.emptyList();
    }

    @Override
    public String queryCompanyName(String userName, String areaName) {
        if (!StringUtils.isNotBlank(userName)) {
            return "";
        }
        List<String> ret = mapper.queryCompanyName(userName, areaName);
        if (null != ret && !ret.isEmpty()) {
            return ret.get(0);
        }
        return "";
    }

    @Override
    public List<TemperatureDo> queryMaxTemperaturePre(Date beginDate, Date endDate, String userName, String city) throws ParseException {

        // 如果查询的结束时间小于今天的时间
        if (endDate.before(new Date())) {
            return Collections.emptyList();
        }
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.set(Calendar.HOUR, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);

        if (StringUtils.isNotBlank(userName)) {
            // 查询出id
            List<Long> ids = mapper.queryIndexByName(userName);
            if (null != ids && !ids.isEmpty()) {
                return mapper.queryTemperaturePre(getTimeStr(ca.getTime()), getTimeStr(endDate), ids.get(0));
            }
            return Collections.emptyList();
        } else {
            return mapper.queryTemperaturePreByCity(getTimeStr(ca.getTime()), getTimeStr(endDate), city);
        }
    }

    @Override
    public List<UserAmountDo> queryUserAmountPredict(Date beginDate, Date endDate, String userName) throws ParseException {
        return mapper.queryUserAmountPredict(getTimeStr(beginDate), getTimeStr(endDate), userName);
    }

    /**
     * @return
     */
    public static String getTimeStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        return dft.format(calendar.getTime());
    }
}
