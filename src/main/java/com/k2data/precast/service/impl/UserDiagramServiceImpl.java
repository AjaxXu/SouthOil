package com.k2data.precast.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.k2data.precast.entity.dto.PageParam;
import com.k2data.precast.entity.dto.PageResult;
import com.k2data.precast.entity.dto.UserDiagramDo;
import com.k2data.precast.executor.ProcessExecutor;
import com.k2data.precast.mapper.UserDiagramMapper;
import com.k2data.precast.service.UserDiagramServiceI;
import com.k2data.precast.utils.CsvReaderHelper;
import com.k2data.precast.utils.CsvToDbHandler;
import com.k2data.precast.utils.OSTypeChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author thinkpad
 */
@Service("userDiagramService")
public class UserDiagramServiceImpl implements UserDiagramServiceI {
    private static Logger log = LoggerFactory.getLogger(UserDiagramServiceImpl.class);
    private static Map<String, String> map = new HashMap<>(20);
    private static final String MODELANALYSISCOMMAND = "\\xnyq_score_sys.exe";

    static {
        map.put("gasUsedRadio", "用气量比率");
        map.put("price", "用气价格");
        map.put("gasUsedGrowth", "用气增长量");
        map.put("gasUsedGrowthRadio", "用气增长率");
        map.put("acceptablePrice", "可承受气价");
        map.put("divergeRadio", "客户行业偏离系数");
        map.put("stabilization", "用气稳定系数");
        map.put("score", "自定义");
        map.put("category", "类别");
        map.put("developScore", "发展评分");
        map.put("artificialScore", "artificialScore");
        map.put("compositeScore", "compositeScore");
        map.put("guaranteedSupplyScore", "guaranteedSupplyScore");
        map.put("overFlowSupplyScore", "overFlowSupplyScore");
        map.put("insufficientSupplyScore", "insufficientSupplyScore");
    }

    @Autowired
    UserDiagramMapper mapper;

    @Override
    public PageResult<UserDiagramDo> queryForPage(PageParam pageParam, Long userId) {
        PageResult<UserDiagramDo> pageResult = new PageResult<>();
        log.info(JSONObject.toJSONString(pageParam));
        log.info(userId +" 用户id");

        List<UserDiagramDo> userDiagramDos = mapper.queryForPage(pageParam.getName(), pageParam.getOffset(), pageParam.getLimit(),
                pageParam.getType(), map.get(pageParam.getSort()), pageParam.getOrder(), pageParam.getBusinessType(),
                pageParam.getLoc(), userId, pageParam.getClusterCategory(), pageParam.getIndustryCategory());
        Integer count = mapper.queryCount(pageParam.getName(), pageParam.getType(), pageParam.getBusinessType(),
                pageParam.getLoc(), userId, pageParam.getClusterCategory(), pageParam.getIndustryCategory());
        log.info("总共：" + count);
        Object radioObj = mapper.querySystemParam(userId, "model_score_radio");
        Double radio = null == radioObj ? 0: Double.valueOf(radioObj.toString());
        double realRadio = radio / 100;
        int model = pageParam.getModel();
        for (UserDiagramDo userDiagramDo : userDiagramDos) {
            Double artificialScore = userDiagramDo.getArtificialScore();
            Double score = 0D;
            switch (model) {
                case 1:
                    score = userDiagramDo.getScore();
                    break;
                case 2:
                    score = userDiagramDo.getGuaranteedSupplyScore();
                    break;
                case 3:
                    score = userDiagramDo.getOverFlowSupplyScore();
                    break;
                case 4:
                    score = userDiagramDo.getInsufficientSupplyScore();
                    break;
                default:
                    score = userDiagramDo.getScore();
                    break;
            }

            if (null == artificialScore) {
                artificialScore = 0D;
            }
            if (null == score) {
                score = 0D;
            }
            userDiagramDo.setCompositeScore(realRadio * score + artificialScore * (1 - realRadio));
        }
        pageResult.setRows(userDiagramDos);
        pageResult.setTotal(count);

        return pageResult;
    }

    @Override
    public void exeModelAnalysis(JSONObject param, Long userId) {
        String path = System.getProperty("homeDir");
        if (OSTypeChecker.isMacOSX() || OSTypeChecker.isMacOS()) {
            path = "src/main/resources/csv";
        }
        String cmd = path + MODELANALYSISCOMMAND;
        String configPath = path + "/CONFIG.txt";
        String[] resultPath = new String[3];
        for (int i = 0; i < 3; i++) {
            resultPath[i] = path + "/real_data_with_scores" + (i + 1) + ".csv";
        }
        // 更新配置文件
//        updateFile(configPath, param.toString());
        // 程序执行
//        ProcessExecutor.execute(cmd, path);
        // 将结果导入数据库
        try {
            mapper.clearData(userId);
            CsvReaderHelper.csvRead(resultPath, "utf-8", new CsvToDbHandler(userId));
        } catch (IOException e) {
            log.error("transfer result to db error,", e);
        }
    }

    @Override
    public void updateModelScoreRadio(int value, Long userId) {
        if (null == getModalScoreRadio(userId)) {
            mapper.addScoreRadio(value, userId, "model_score_radio");
        } else {
            mapper.updateModelScoreRadio(value, userId);
        }

    }

    public void updateFile(String path, String value) {
        try (OutputStream outputStream = new FileOutputStream(path)) {
            outputStream.write(value.getBytes("utf-8"));
            outputStream.flush();
        } catch (IOException e) {
            log.error("updateConfig error:", e);
        }
    }

    @Override
    public void updateSpecialMetrecs(String object, Long userId) {
        if (null == getSpecialMetrecs(userId)) {
            mapper.addSpecialMetrics(object, userId, "special_metrecs");
        } else {
            mapper.updateSpecialMetrics(object, userId);
        }

    }

    @Override
    public Double getModalScoreRadio(Long userId) {
        Object ret = mapper.querySystemParam(userId, "model_score_radio");
        if (null != ret) {
            return Double.valueOf(ret.toString());
        }
        return null;
    }

    @Override
    public String getSpecialMetrecs(Long userId) {
        Object ret = mapper.querySystemParam(userId, "special_metrecs");
        if (null != ret) {
            return ret.toString();
        }
        return null;
    }
}
