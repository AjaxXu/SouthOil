package com.k2data.precast.utils;

import com.csvreader.CsvReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 导入CSV到数据库
 *
 * @author thinkpad
 */
public class CsvToDbHandler implements CsvHandler {

    private static Logger log = LoggerFactory.getLogger(CsvToDbHandler.class);

    private Long userId;
    private static final String COMMA = ",";
    private DataSource dataSource;

    public CsvToDbHandler(Long userId) {
        this.userId = userId;
    }

    @Override
    public void transform(CsvReader reader, int industryCategory) throws IOException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getDataSource().getConnection();
            statement = connection.createStatement();
            connection.setAutoCommit(false);
            String insertSql = "INSERT INTO real_data_with_score1 (`sys_user_id`,`用户名称`,`用气量比率`,`用气价格`,`用气计划匹配度`,`用气合同匹配度`,`用气增长量`,`用气增长率`,`可承受气价`,`发展评分`,`客户行业气价偏离系数`,`行业标识`,`用气稳定系数`,`温度气量敏感系数`,`类别`,`自定义类别`,`保供`,`供大于求`,`供小于求`,`自定义`, `行业类别`) values(";
            StringBuilder batch = null;
            while (reader.readRecord()) {
                batch = new StringBuilder(insertSql);
                batch.append(userId).append(COMMA)
                        .append("'").append(reader.get(0)).append("'").append(COMMA)
                        .append("'").append(reader.get("用气量比率")).append("'").append(COMMA)
                        .append("'").append(reader.get("用气价格")).append("'").append(COMMA)
                        .append("'").append(reader.get("用气计划匹配度")).append("'").append(COMMA)
                        .append("'").append(reader.get("用气合同匹配度")).append("'").append(COMMA)
                        .append("'").append(reader.get("用气增长量")).append("'").append(COMMA)
                        .append("'").append(reader.get("用气增长率")).append("'").append(COMMA)
                        .append("'").append(reader.get("可承受气价")).append("'").append(COMMA)
                        .append("'").append(reader.get("发展评分")).append("'").append(COMMA)
                        .append("'").append(reader.get("客户行业气价偏离系数")).append("'").append(COMMA)
                        .append("'").append(reader.get("行业标识")).append("'").append(COMMA)
                        .append("'").append(reader.get("用气稳定系数")).append("'").append(COMMA)
                        .append("'").append(reader.get("温度气量敏感系数")).append("'").append(COMMA)
                        .append("'").append(reader.get("类别")).append("'").append(COMMA)
                        .append("'").append(reader.get("自定义类别")).append("'").append(COMMA)
                        .append("'").append(reader.get("保供")).append("'").append(COMMA)
                        .append("'").append(reader.get("供大于求")).append("'").append(COMMA)
                        .append("'").append(reader.get("供小于求")).append("'").append(COMMA)
                        .append("'").append(reader.get("自定义")).append("'").append(COMMA)
                        .append("'").append(industryCategory).append("'").append(")");
                statement.addBatch(batch.toString());
            }
            statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            log.error("An error has occur when execute sql", e);
        } finally {
            try {
                if (null != statement) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                log.error("close connection or statement error,", e);
            }
        }
    }

    public DataSource getDataSource() {
        if (null == dataSource) {
            dataSource = (DataSource) SpringUtil.getBean("dataSource");
        }
        return dataSource;
    }
}
