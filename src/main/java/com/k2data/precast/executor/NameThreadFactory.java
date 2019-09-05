package com.k2data.precast.executor;


import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author thinkpad
 */
public class NameThreadFactory implements ThreadFactory {
    private String name;
    private AtomicInteger threadNum = new AtomicInteger(1);

    public NameThreadFactory(String name) {
        this.name = name;
    }

    private NameThreadFactory() {

    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, name + threadNum.getAndIncrement());
    }

}
