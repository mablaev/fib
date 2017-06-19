package com.mycompany;


public class MeasurableTask implements Runnable {

    private final Runnable runnableTarget;

    private long elapsedTime = 0L;

    private MeasurableTask(Runnable target) {
        this.runnableTarget = target;
    }

    public static MeasurableTask of(Runnable target) {
        return new MeasurableTask(target);
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        try {
            runnableTarget.run();
        } finally {
            elapsedTime = System.currentTimeMillis() - start;
        }

    }

    public long getElapsedTime() {
        return elapsedTime;
    }


    @Override
    public String toString() {
        return "MeasurableTask{" +
                "elapsedTime=" + elapsedTime +
                '}';
    }
}
