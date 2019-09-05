package com.k2data.precast.executor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.*;

/**
 * @author thinkpad
 */
public final class ProcessExecutor {

    private static Logger logger = LoggerFactory.getLogger(ProcessExecutor.class);

    private static Long TIME_OUT = 1000L * 30;

    private static ExecutorService executorService = new ThreadPoolExecutor(2, 5, 60,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), new NameThreadFactory("Process Thread"),
            new DefaultRejectedHandler("Process Thread Pool"));

    private ProcessExecutor() {

    }

    public static void execute(String cmd, String dir) {
        // 执行命令构建
        ProcessBuilder processBuilder = new ProcessBuilder(cmd);

        // 开始执行
        FutureTask<String> futureTask = new FutureTask<String>(() -> {
            StringBuffer buffer = new StringBuffer();
            try {
                Process process = processBuilder.directory(new File(dir)).start();
                // 处理正常输出
                InputStream in = null;
                // 处理错误输出
                new Thread(() -> {
                    InputStream errorIn = null;
                    errorIn = process.getErrorStream();
                    StringBuffer errBuffer = new StringBuffer();
                    byte[] errBytes = new byte[1024];
                    try {
                        while (errorIn.read(errBytes) != -1) {
                            errBuffer.append(errBytes);
                        }
                    } catch (Exception e) {
                        logger.error("ErrorStream process error,", e);
                    } finally {
                        if (null != errorIn) {
                            try {
                                errorIn.close();
                            } catch (IOException e) {
                                logger.error("close errorInputStream error,", e);
                            }
                        }

                    }
                }).start();
                in = process.getInputStream();
                process.getOutputStream().write("123".getBytes("utf-8"));
                byte[] bytes = new byte[1024];
                while (in.read(bytes) != -1) {
                    buffer.append(bytes);
                }
                int value = process.waitFor();
                return buffer.toString();
            } catch (IOException ioe) {
                logger.error("ioe,", ioe);
            }
            return buffer.toString();
        });
        Future future = executorService.submit(futureTask);
        try {
            if (future != null) {
                future.get();
                executorService.submit(()->{

                });
                System.out.println("execute complete.");
            } else {
                logger.error("执行失败!");
            }
        } catch (InterruptedException e) {
            logger.error("execute has bean interrupted.",e);
        } catch (ExecutionException e) {
            logger.error("execute has an error,",e);
        }
    }


}
