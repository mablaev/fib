package com.mycompany;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class NamedThreadFactory implements ThreadFactory {

    private final ThreadFactory targetThreadFactory = Executors.defaultThreadFactory();
    private int threadNumber = 0;
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = targetThreadFactory.newThread(r);
        thread.setName("Calculation thread-" + threadNumber++);
        return thread;
    }
}
