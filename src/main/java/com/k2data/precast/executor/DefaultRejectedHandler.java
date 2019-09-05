package com.k2data.precast.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;


public class DefaultRejectedHandler implements RejectedExecutionHandler {
    private static Logger logger = LoggerFactory.getLogger(DefaultRejectedHandler.class);

    private String  threadPoolName;

    public DefaultRejectedHandler(String name){
        threadPoolName = name;
    }
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        logger.warn(threadPoolName + "is full,info"+executor.toString());
    }
}
