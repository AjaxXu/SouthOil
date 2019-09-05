package com.k2data.precast.utils;

import com.csvreader.CsvReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author tianhao
 */
public final class CsvReaderHelper {
    private static Log log = LogFactory.getLog(CsvReaderHelper.class);

    /**
     * @param paths  文件路径
     * @param charset 编码
     */
    public static void csvRead(String[] paths, String charset, CsvHandler csvHandler) throws IOException {
        File file;
        InputStream in = null;
        CsvReader csvReader = null;
        try {
            for (int i = 0; i < paths.length; i++) {
                file = new File(paths[i]);
                if (!file.exists()) {
                    log.error("file not exists:" + paths[i]);
                    System.exit(1);
                }
                file.lastModified();
                in = new FileInputStream(file);
                if (null == charset || charset.trim().length() == 0) {
                    charset = "utf-8";
                }
                csvReader = new CsvReader(in, Charset.forName(charset));
                // 读取文件头
                csvReader.readHeaders();
                csvHandler.transform(csvReader, i + 1);
            }
        } catch (IOException ioe) {
            log.error("csvRead error:", ioe);
        } finally {
            if (null != in) {
                in.close();
            }
            if (null != csvReader) {
                csvReader.close();
            }
        }
    }


}




