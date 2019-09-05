package com.k2data.precast.controller;

import com.alibaba.fastjson.JSONObject;
import com.k2data.precast.entity.dto.ReplyInfo;
import com.k2data.precast.entity.dto.TemperatureDo;
import com.k2data.precast.entity.dto.UserAmountDo;
import com.k2data.precast.service.TemperatureServiceI;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * OilProController
 *
 * @author tianhao
 * @date 2018/12/5
 */
@RestController
public class TemperatureController {
    private static Logger logger = LoggerFactory.getLogger(TemperatureController.class);
    @Autowired
    private TemperatureServiceI temperatureService;

    @RequestMapping(value = "/drawCharts", method = RequestMethod.POST)
    public ReplyInfo queryTemperature(@RequestBody JSONObject body) {
        String beginDateStr = body.containsKey("beginDate") ? body.getString("beginDate") : null;
        String endDateStr = body.containsKey("endDate") ? body.getString("endDate") : null;
        String city = body.containsKey("city") ? body.getString("city") : null;
        String userName = body.containsKey("username") ? body.getString("username") : null;
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        ReplyInfo replyInfo;
        List<TemperatureDo> temperatureDos;
        List<TemperatureDo> temperaturePreDos;
        JSONObject data = new JSONObject();
        boolean exist = StringUtils.isNotBlank(city) || StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(beginDateStr) && StringUtils.isNotBlank(endDateStr);
        // 参数不能为空
        if (exist) {
            try {
                temperatureDos = temperatureService.queryMaxTemperature(dft.parse(beginDateStr), dft.parse(endDateStr), userName, city);
                temperaturePreDos = temperatureService.queryMaxTemperaturePre(dft.parse(beginDateStr), dft.parse(endDateStr), userName, city);
                List<UserAmountDo> userAmountDos = temperatureService.queryUserAmount(dft.parse(beginDateStr), dft.parse(endDateStr), userName, city);
                List<UserAmountDo> userAmountPredictDos = temperatureService.queryUserAmountPredict(dft.parse(beginDateStr), dft.parse(endDateStr), userName);
                List<String> xList = getXdata(dft.parse(beginDateStr), dft.parse(endDateStr));
                int length = xList.size();
                Map<String, TemperatureDo> map = new HashMap<>(length);
                Map<String, TemperatureDo> preMap = new HashMap<>(length);
                Date dateTmp = null;
                for (TemperatureDo temperatureDo : temperatureDos) {
                    dateTmp = dft.parse(temperatureDo.getDate());
                    map.put(dft.format(dateTmp), temperatureDo);
                }

                for (TemperatureDo temperaturePreDo : temperaturePreDos) {
                    dateTmp = dft.parse(temperaturePreDo.getDate());
                    preMap.put(dft.format(dateTmp), temperaturePreDo);
                }

                Map<String, UserAmountDo> userAmountDoMap = new HashMap<>(length);
                for (UserAmountDo userAmountDo : userAmountDos) {
                    dateTmp = dft.parse(userAmountDo.getDate());
                    userAmountDoMap.put(dft.format(dateTmp), userAmountDo);
                }

                Map<String, UserAmountDo> userAmountPredictDoMap = new HashMap<>(length);
                for (UserAmountDo userPredictAmountDo : userAmountPredictDos) {
                    dateTmp = dft.parse(userPredictAmountDo.getDate());
                    userAmountPredictDoMap.put(dft.format(dateTmp), userPredictAmountDo);
                }

                List<String> y1List = new ArrayList<>(length);
                List<String> y2List = new ArrayList<>(length);
                List<String> y3List = new ArrayList<>(length);
                List<String> y4List = new ArrayList<>(length);
                List<String> y5List = new ArrayList<>(length);
                List<String> y6List = new ArrayList<>(length);

                TemperatureDo tmp = null;
                TemperatureDo preTmp = null;
                UserAmountDo tmpUserAmount = null;
                UserAmountDo tmpUserPredictAmount = null;
                DecimalFormat decimalFormat = new DecimalFormat("0.##");
                Double maxAmount = 0D;
                Double maxTemperature = 0D;
                Double minTemperature = null;

                for (String dateStr : xList) {
                    tmp = map.get(dateStr);
                    preTmp = preMap.get(dateStr);
                    tmpUserAmount = userAmountDoMap.get(dateStr);
                    tmpUserPredictAmount = userAmountPredictDoMap.get(dateStr);
                    if (null == tmpUserAmount || null == tmpUserAmount.getUseAmount()) {
                        y3List.add("0");
                    } else {
                        if (tmpUserAmount.getUseAmount() > maxAmount) {
                            maxAmount = tmpUserAmount.getUseAmount();
                        }
                        y3List.add(null == tmpUserAmount.getUseAmount() ? "-" : decimalFormat.format(tmpUserAmount.getUseAmount()));
                    }

                    if (null == tmpUserPredictAmount || null == tmpUserPredictAmount.getUseAmount()) {
                        y6List.add("0");
                    } else {
                        y6List.add(null == tmpUserPredictAmount.getUseAmount() ? "-" : decimalFormat.format(tmpUserPredictAmount.getUseAmount()));
                    }

                    addToList(decimalFormat, tmp, y1List, y2List);
                    addToList(decimalFormat, preTmp, y4List, y5List);
                }

                data.put("xData", xList);
                data.put("y1Data", y1List);
                data.put("y2Data", y2List);
                data.put("y3Data", y3List);
                data.put("y4Data", y4List);
                data.put("y5Data", y5List);
                data.put("y6Data", y6List);

                // 获取供气单位
                String title = temperatureService.queryCompanyName(userName, city);
                // 获取行业
                String businessType = temperatureService.queryBusinessTypes(userName);
                data.put("title", title);
                data.put("businessType", businessType);
                data.put("name", userName);
                data.put("area", city);
            } catch (ParseException e) {
                logger.error("queryTemperature error", e);
                replyInfo = new ReplyInfo(false, e.getMessage());
                return replyInfo;
            }
        } else {
            data.put("xData", Collections.emptyList());
            data.put("y1Data", Collections.emptyList());
            data.put("y2Data", Collections.emptyList());
            data.put("y3Data", Collections.emptyList());
            data.put("y4Data", Collections.emptyList());
            data.put("y6Data", Collections.emptyList());
        }
        replyInfo = new ReplyInfo(true, data);
        return replyInfo;
    }

    @PostMapping("/areaList")
    public ReplyInfo getArea(@RequestBody JSONObject body) {
        List<String> ret = temperatureService.queryAreaInfo();
        JSONObject obj = new JSONObject();
        obj.put("areas", ret);
        return new ReplyInfo(true, obj);
    }


    @PostMapping("/getNameListByAreaAndName")
    public ReplyInfo getNameListByAreaAndName(@RequestBody JSONObject body) {
        String userName = body.containsKey("words") ? body.getString("words") : null;
        String area = body.containsKey("area") ? body.getString("area") : null;
        List<String> ret = temperatureService.queryNameByArea(userName, area);
        JSONObject obj = new JSONObject();
        obj.put("list", ret);
        return new ReplyInfo(true, obj);
    }


    public static List<String> getXdata(Date beginDate, Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        List<String> dateList = new ArrayList<>(365);

        while (!calendar.getTime().after(endDate)) {
            dateList.add(dft.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dateList;
    }

    public void addToList(DecimalFormat decimalFormat, TemperatureDo tmp, List<String> yMax, List<String> yMin) {

        if (null == tmp) {
            yMax.add("-");
            yMin.add("-");
        } else {
            // 摄氏＝5/9(°F－32)
            if (null == tmp.getMinTemperature()) {
                yMin.add("-");
            } else {
                yMin.add(decimalFormat.format(Double.valueOf(((tmp.getMinTemperature() - 32) * 5 / 9))));
            }

            if (null == tmp.getMaxTemperature()) {
                yMax.add("-");
            } else {
                yMax.add(decimalFormat.format(Double.valueOf(((tmp.getMaxTemperature() - 32) * 5 / 9))));
            }
        }
    }


}
