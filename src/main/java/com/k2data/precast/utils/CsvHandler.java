package com.k2data.precast.utils;

import com.csvreader.CsvReader;

import java.io.IOException;

/**
 * csv 结果处理接口
 * @author thinkpad
 */
public interface CsvHandler {

    /**
     * CSV文件数据处理类
     * @param reader
     * @throws IOException
     */
    void transform(CsvReader reader, int industryCategory) throws IOException;
}
